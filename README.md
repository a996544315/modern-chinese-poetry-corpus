
# 华语现代诗歌集合

![](https://img.shields.io/badge/only-%E2%9D%A4-green)
![](https://img.shields.io/badge/%E4%B8%AD%E6%96%87-%E7%8E%B0%E4%BB%A3%E8%AF%97-red)
![](https://img.shields.io/badge/poems-5560-yellowgreen)
![](https://img.shields.io/badge/poets-525-lightgrey)
![](https://img.shields.io/github/repo-size/sheepzh/poetry)
![visitors](https://visitor-badge.glitch.me/badge?page_id=sheepzh.poetry)
![](https://img.shields.io/github/last-commit/sheepzh/poetry)

## 前言

+ 该目录及子目录下所有作品著作权归原作者所有。**`禁止用于任何商业用途`**。
+ 维护公约
	+ 收录诗人而不创造诗人。
+ 数据格式见 
+ 反馈
	+ 邮箱：returnzhy1996@outlook.com
	+ ISSUES：[<u>Github</u>](https://github.com/sheepzh/poetry/issues)

## 目录


```
+++ data     # 诗歌数据：原始数据、分词数据、意象分析数据
|  
+++ doc      # 文档相关
|
+++ lib      # 相关工具库
|	|
|	+++ tool.jar    # 命令行工具依赖
|	|
|	+++ imagery     # 意象探测 Python3 脚本
|	|
|	+++ jieba       # 结巴分词相关 Python3 脚本，以及自定义字典
|
+++ tool     # tool.jar 项目源码
|
+++ poem.bat # Windows 命令行工具
|
+++ poem.sh  # unix 命令行工具
```

## 工具

>需要<u>**JDK8**</u>以上，且已将java指令添加至环境变量

+ Windows

```batch
cd %项目根目录%
poem
```

+ Linux/MacOS
```shell
cd ${项目根目录}
sh poem.sh
```

+ 具体命令

	+ help：查看帮助信息
	
	+ count：查询或统计

	+ clean：格式化文本

## 诗人目录

|笔/姓名|作品（集）
|:-:|:-|
|阿斐|《以垃圾的名义》
|曹疏影|《拉线木偶》《茱萸箱》《金雪》
|还叫悟空|《兰花和兰花的影子》《乔小慧》
|海子|《土地》《海子、骆一禾作品集》《海子的诗》
|韩东|《吉祥的老虎》《爸爸在天上看我》
|黄灿然|《十年诗选》《世界的隐喻》《游泳池畔的冥想》《奇迹集》《发现集》《我的灵魂》
|骆一禾|《骆一禾诗全编》
|脱脱不花|《我是使爸妈衰老的诸多事件之一》
|王家新|《中国画》《长江组诗》《告别》《纪念》
|巫昂|《什么把我弄醒》《从亲人开始糟蹋》《正午的巫昂》《春药》
|夏宇|《备忘录》《腹语术》《摩擦·无以名状》《Salsa》
|许立志|《新的一天》
|杨炼|《艳诗》《礼魂》《大海停止之处》《土地》
|伊蕾|《爱的火焰》《爱的方式》《女性年龄》《独身女人的卧室》《伊蕾爱情诗》《叛逆的手》《伊蕾诗选》
|于坚|《0档案》《飞行》《尚义街六号》
|余真|《小叶榕》
|张二棍|《旷野》
|张枣|《春秋来信》《张枣的诗》
|周欣祺|《退化》

## 将来

+ 多维度的——诗人性别，写作年代，流派等——云词图展示。
+ 为不会使用 git 但有意愿参与项目维护的众好们，提供更友好的操作页面。
+ WebHook 实现自动化整理增量数据。
