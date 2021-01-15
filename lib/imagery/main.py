import os

import jieba
import jieba.analyse
import jieba.posseg
import json

imagery_dir_path = os.path.join('..', '..', 'data', 'imagery')
seged_dir_path = os.path.join('..', '..', 'data', 'seged')
seged_dir = os.walk(seged_dir_path)

if not os.path.exists(imagery_dir_path):
  os.makedirs(imagery_dir_path)

class Imagery():
  '''
    v1.0
    @date 20200114
  '''
  def __init__(self, name):
    '''
      构造函数：名称，词性
    '''
    self.name = name
    # apperances 存元组(lineNum, textStartIndex, imageryIndex)
    self.apperances = []

  def increaseFrequency(self, part, lineNum, startIndex, imageryIndex):
    self.apperances.append((part, lineNum, startIndex, imageryIndex))

  def apperancesJson(self):
    appStrArr = map(lambda app: '{"part":"%s","lineNum":"%s","lineIndex":"%s","imageryIndex":"%s"}'%(app[0],app[1],app[2],app[3]), self.apperances)
    appStr = ",".join(appStrArr)
    return "[%s]"%(appStr)

for path, dir_list, file_list in seged_dir:
  for poem_dir in dir_list:
    poem_dir_path = os.path.join(path,poem_dir)
    json_path = poem_dir_path.replace(seged_dir_path, imagery_dir_path)+'.txt'
    json_file = open(json_path,'w')

    for pp, dd, ff in os.walk(poem_dir_path):
      for file_name in ff:
        # not seged file
        if not file_name.endswith('.pt'):
          continue
        
        seged_file = open(os.path.join(pp, file_name),'rt')

        index = 0
        imageries = {}
        title = file_name[:-3]
        date = ''
        for line in seged_file.readlines():
          line = line.strip()
          if index == 1:
            date = line[5:]
          elif not(index < 2 or line.isnumeric() or len(line) == 0):
            lineNum = index - 2
            words = line.split('|')
            wordIndex = 0
            imageryIndex = 0
            for word in words:
              wordPart = word.split('/')
              if len(wordPart) != 2:
                continue
              w = wordPart[0]
              p = wordPart[1]
              if p in ['n', 'ns']:
                # all NOUNs are imgaries
                imagery = None
                if w not in imageries:
                  imagery = Imagery(w)
                else:
                  imagery = imageries[w]
                imagery.increaseFrequency(p, lineNum, wordIndex, imageryIndex)
                imageries[w] = imagery
                imageryIndex = imageryIndex + 1
              wordIndex = wordIndex + len(w)
          index = index + 1
        seged_file.close()
        jsonStr = '{"title":"%s","date":"%s","imageries":{%s}}'%(title,date,','.join(map(lambda e: '"%s":%s'%(e,imageries[e].apperancesJson()), imageries)))        
        json_file.write(jsonStr+'\r\n')
    json_file.close()

print('all imageries are detected')
