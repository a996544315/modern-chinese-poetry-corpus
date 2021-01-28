import os
import re

import jieba
import jieba.analyse
import jieba.posseg
import time
import multiprocessing
import sys

cache_dir = '.'
cache_name = 'cache'
cach_path = os.path.join(cache_dir, cache_name)

if os.path.exists(cach_path):
    os.remove(cach_path)

jieba.dt.tmp_dir = cache_dir
jieba.dt.cache_file = cache_name
origin_dir_path = os.path.join('..', '..', 'data', 'origin')
seged_dir_path = os.path.join('..', '..', 'data', 'seged')
custom_freq_dir_path = os.path.join('..', '..', 'lib', 'jieba', 'dict')

jieba.enable_parallel(multiprocessing.cpu_count() * 4)

# copy from jieba, but no tag
re_userdict = re.compile('^(.+?)( [0-9]+)?( [a-z]+)?$', re.U)

# 全局字典
jieba.load_userdict('dictionary.txt')


def resolve_freq(custom_freq_path):
    """
        处理自定义词频
    """
    if not os.path.exists(custom_freq_path):
        return
    result = {}

    with open(custom_freq_path, 'r') as file:
        for line in file.readlines():
            line = line.strip()
            if not line:
                continue
            word, freq, tag = re_userdict.match(line).groups()
            if not freq:
                print("no freq: " + word + "  in "+custom_freq_path)
                return
            if tag:
                tag = tag.strip()

            freq = freq.strip()
            exist_freq = jieba.get_FREQ(word, 0)
            result[word] = int(exist_freq)
            jieba.add_word(word, exist_freq + int(freq), tag)
    return result


def back_freq(freq_map):
    """
        还原词频
    """
    if not freq_map:
        return
    for word in freq_map:
        freq = freq_map[word]
        if freq:
            jieba.add_word(word, freq)
        else:
            jieba.del_word(word)


def seg(file_name, path):
    if(file_name.endswith('.pt')):
        f_ts = time.time()
        origin_file = open(os.path.join(path, file_name), 'rt')
        seged_path = path.replace(origin_dir_path, seged_dir_path)
        if not os.path.exists(seged_path):
            os.makedirs(seged_path)
        seged_file = open(os.path.join(seged_path, file_name), 'w')

        custom_freq_path = os.path.join(path.replace(
            origin_dir_path, custom_freq_dir_path), file_name).replace('.pt', '.txt')

        freq_map = resolve_freq(custom_freq_path)
        print(freq_map)

        index = 0
        for line in origin_file.readlines():
            if index < 2 or line.isnumeric():
                seged_file.write(line)
            elif len(line) > 0:
                line = line.strip()
                seged = jieba.posseg.cut(line)
                seged_file.write(
                    '|'.join(map(lambda x: "{}/{}".format(x.word, x.flag), seged))+'\r\n')
            index = index + 1
        origin_file.close()
        seged_file.close()
        back_freq(freq_map)
        print(str(int((time.time()-f_ts)*1000))+'ms \t' + file_name)
        return True


start = time.time()

argv = sys.argv
file_name_filter = None
poet_name_filter = None
if len(argv) > 1:
    file_name_filter = argv[1]
if len(argv) > 2:
    poet_name_filter = argv[2]

origin_dir = os.walk(origin_dir_path)

for path, dir_list, file_list in origin_dir:
    for file_name in file_list:
        if not file_name_filter or file_name == file_name_filter+'.pt':
            seg(file_name, path)
print('total used ' + str(int((time.time()-start)*1000)) + 'ms')
