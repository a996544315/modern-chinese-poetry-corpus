
# 华语现代诗歌集合

![](https://img.shields.io/badge/only-bot-green)
![](https://img.shields.io/badge/%E4%B8%AD%E6%96%87-%E7%8E%B0%E4%BB%A3%E8%AF%97-red)
![](https://img.shields.io/badge/%E2%9D%A4-%E5%AD%A6%E9%99%A2%E5%90%91-blue)
![](https://img.shields.io/badge/poems-5560-yellowgreen)
![](https://img.shields.io/badge/poets-525-lightgrey)
![](https://img.shields.io/github/repo-size/sheepzh/poetry)
![visitors](https://visitor-badge.glitch.me/badge?page_id=sheepzh.poetry)

[English](README.en.md)

## 前言

+ 该目录及子目录下所有作品著作权归原作者所有。**`禁止用于任何商业用途`**。
+ 维护公约
	+ 收录诗人而不创造诗人。
	+ 作品应认真校核。
	+ 新增诗人请修改该文档内的诗人目录（按字母序）。
+ 作品格式
	+ 文件名格式：诗歌标题(_组诗).pt
	+ 内容格式：
		+ 首行：title:[标题]
		+ 次行：date:[YYYYMMDD/YYYYMM/YYYY/留空，创作日期]
		+ 空一行开始正文
		+ 如果小节有节序或者标题，每一小节的节尾与次节标题或节序之间空一行，节序或者小节标题与该节内容之间空一行。
		+ 正文末不空行
+ 反馈
	+ 邮箱：returnzhy1996@outlook.com
	
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

	+ clean：格式化文本，编辑时使用

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

+ 将工具脚本改为 bash/shell 编写，不再依赖 JDK 环境。
+ 通过 Github 提供的 API，为不会使用 git 但有意愿参与项目维护的众好们，提供更友好的操作页面。
