
# The Collection of Chinese Modern Poetry

![](https://img.shields.io/badge/only-bot-green)
![](https://img.shields.io/badge/zh-poetry-red)
![](https://img.shields.io/badge/%E2%9D%A4-academic-blue)
![](https://img.shields.io/badge/poems-1160-yellowgreen)
![](https://img.shields.io/badge/poets-21-lightgrey)
![](https://img.shields.io/github/repo-size/sheepzh/poetry)

[简体中文](README.md)

## Introduction

+ To collect as many excellent Chinese modern poems as far.
+ The copyright of all works under this project belongs to the original authors or poets. **`NO COMMERCIAL USE IS ALLOWED`**.
+ Contracts
	+ Include but create the poet.
	+ Check the content of works carefully.
	+ Edit the catalog of poet in this document when a new one is included.
+ File format
	+ The name of file: "TITLE(_set).pt"
	+ The content of file
		+ The firset line："title:[TITLE]"
		+ The second line："date:[YYYYMMDD/YYYYMM/YYYY/EMPTY]"
		+ The third line is empty, and the content of poem starts with the fourth line.
		+ One blank line is required between the end of last section and the title of the next section, if these sections own titles or section numbers, and also required between the section title and the next text.
		+ No blank or meaningless line can be appended at the end of this file.
+ Feecback
	+ E-mail：returnzhy1996@outlook.com
	
## Tool

>The <u>**JDK10**</u> needs to be installed first, and added to the path of your OS.

+ Windows

```batch
cd %Project Directory%
poem
```

+ Linux/MacOS
```shell
cd ${Project Directory}
sh poem.sh
```

+ Parameters

	+ help：Show the help info.
	
	+ count：Query or count.

	+ clean：Format the poem files after editing.

## Catalog

|Name or pen Name|Works or work sets
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

## Future

+ Rewrite this tool with bash or shell. The JDK won't be required anymore.
+ Provide UI, html or others, for poem lovers who wants to join this project but is poorly skilled at the Git.
