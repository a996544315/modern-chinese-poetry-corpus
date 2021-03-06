## 目录结构

```
+++ origin   # 原始文本
|      |
|      +++ [诗人名]_[诗人名拼音]
|      |      |
|      |      --- [诗歌标题](_节选)(：[之[诗歌序号]]|[诗歌副标题]).pt
|      |      |
|      |      --- ...
|      +++ ...
+++ seged   # 已分词的文本，内部目录结构同 origin
|
+++ imagery # 意象结果
       |
       +++ [诗人名]_[诗人拼音].txt
```
## 数据格式

> 所有文件编码格式为 UTF-8

### 原始文本

+ 文件后缀：.pt
+ 每个文件存储一首诗歌

```
title:[标题]
date:[写作日期, YYYYMMDD/YYYYMM/YYYY/空]
# 空一行
[正文]
```
### 分词文本

+ 文件后缀：.pt
+ 每个文件存储一首诗歌
+ 正文行数与原始文本相同
+ 每行内容：[(词)/(词性)]|...|[(词)/(词性)]。如果词性的后缀为 _i，则表示该词已被手工标注为意象实体。如果该词性以下划线开始，则是被手工标注为非意象的名词。
+ 纯数字的节序无词性，词性含义见 [<u>jieba/README.md</u>](https://github.com/fxsjy/jieba/blob/master/README.md)

```
title:[标题]
date:[写作日期, YYYYMMDD/YYYYMM/YYYY/空]
# 空一行
[正文]
# 每行内容示例：
和/c|时光/n|作对/v|的/uj|永远/d|是/v|挖土机/n
```

### 意象文本

+ 文件后缀：.txt
+ 每个文件存储一位诗人的所有诗歌
+ 每行使用 json 存储一首诗歌的意象信息

```json
{
    // 标题，同原始文本
    "title":"八月之杯",       
    // 写作日期，同原始文本
    "date":"",             
    "imageries":{
        // 意象名称
        "杯子":[
            // 出现位置   
            {   
                // 词性
                "part":"n",
                // 行数，对应原始文本正文的行数，从 1 开始计数
                // 原始文本正文内的分节空行也占一行
                "lineNum":"11",
                // 作为所在行的子串所对应的起始位置，以 0 开始计数
                "lineIndex":"3",
                // 该行的第几个意象，以 0 开始计数
                "imageryIndex":"1"
            },
            // ...,
            {
                "part":"n",
                "lineNum":"14",
                "lineIndex":"3",
                "imageryIndex":"1"
            }
        ],
        // ...,
        "鞭子":[
            {
                "part":"n",
                "lineNum":"15",
                "lineIndex":"3",
                "imageryIndex":"1"
            }
        ]
    }
}
```

