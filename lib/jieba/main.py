import os

import jieba
import jieba.analyse
import jieba.posseg

origin_dir_path = os.path.join('..', '..', 'data', 'origin')
seged_dir_path = os.path.join('..', '..', 'data', 'seged')
origin_dir = os.walk(origin_dir_path)

for path, dir_list, file_list in origin_dir:  
  for file_name in file_list:
    if(file_name.endswith('.pt')):
      origin_file = open(os.path.join(path, file_name),'rt')
      seged_path = path.replace(origin_dir_path,seged_dir_path)
      if not os.path.exists(seged_path):
        os.makedirs(seged_path)
      seged_file = open(os.path.join(seged_path,file_name), 'w')
      index = 0
      for line in origin_file.readlines():
        line = line.strip()
        if index < 2 or line.isnumeric():
          seged_file.write(line+'\r\n')
        else:
          seged = jieba.posseg.cut(line)
          seged_file.write('|'.join(map(lambda x: "{}/{}".format(x.word,x.flag),seged))+'\r\n')
        index = index + 1
      origin_file.close()
      seged_file.close()
      print('seged:'+file_name)
        
