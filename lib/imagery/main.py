import os

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

    def increaseFrequency(self, lineNum, startIndex, imageryIndex):
        self.apperances.append((lineNum, startIndex, imageryIndex))

    def apperancesJson(self):
        appStrArr = map(lambda app: '{"lineNum":"%s","lineIndex":"%s","imageryIndex":"%s"}' % (
            app[0], app[1], app[2]), self.apperances)
        appStr = ",".join(appStrArr)
        return "[%s]" % (appStr)


jsons = []

for path, dir_list, file_list in seged_dir:
    for poem_dir in dir_list:
        poem_dir_path = os.path.join(path, poem_dir)
        json_path = poem_dir_path.replace(
            seged_dir_path, imagery_dir_path)+'.txt'

        jsons.clear()

        for pp, dd, ff in os.walk(poem_dir_path):
            for file_name in ff:
                # not seged file
                if not file_name.endswith('.pt'):
                    continue

                seged_file = open(os.path.join(pp, file_name),
                                  mode='rt', encoding='utf-8')

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
                            if p in ['n', 'ns', 'nr', 'nt', 'nw', 'nz'] or p.endswith('_i'):
                                # all NOUNs and those marked with _i are imgaries
                                imagery = None
                                if w not in imageries:
                                    imagery = Imagery(w)
                                else:
                                    imagery = imageries[w]
                                imagery.increaseFrequency(
                                    lineNum, wordIndex, imageryIndex)
                                imageries[w] = imagery
                                imageryIndex = imageryIndex + 1
                            wordIndex = wordIndex + len(w)
                    index = index + 1
                seged_file.close()
                jsonStr = '{"title":"%s","date":"%s","imageries":{%s}}' % (title, date, ','.join(
                    map(lambda e: '"%s":%s' % (e, imageries[e].apperancesJson()), imageries)))
                jsons.append(jsonStr)
        jsons.sort()
        json_file = open(json_path, 'w', encoding='utf-8', newline=None)
        for json in jsons:
            json_file.write(json+'\n')
        json_file.close()

print('all imageries are detected')
