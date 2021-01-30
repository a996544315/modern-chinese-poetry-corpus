# 诗人词云生成
# pip install pyecharts
import os.path as path
import os

import random
import numpy as np
from pyecharts.charts import WordCloud
import json

imagery_dir_path = path.join('..', '..', 'data', 'imagery')

frequencies = {}

for poet_path, dir_list, file_list in os.walk(imagery_dir_path):
    for imagery_file_name in file_list:
        # not poem file
        if not imagery_file_name.endswith('.txt'):
            continue
        imagery_file = open(path.join(poet_path, imagery_file_name),
                            mode='rt', encoding='utf-8')

        for line in imagery_file.readlines():
            content = json.loads(line)
            imageries = content['imageries']
            for imagery in imageries:
                f = len(imageries[imagery])
                if imagery not in frequencies:
                    frequencies[imagery] = f
                else:
                    frequencies[imagery] = frequencies[imagery]+f

frequencies = sorted(frequencies.items(), reverse=True,  key=lambda k: k[1])
print(frequencies)

# frequencies.sort(reverse=True, key=lambda bi: bi[1])
# frequencies = frequencies[0:100]

# wc = (
#     WordCloud().add(shape='circle', series_name="分布",
#                     data_pair=frequencies, rotate_step=10, width=1000, height=600)
# )
# wc.render('poetcloud.html')
