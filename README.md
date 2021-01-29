
# 华语现代诗歌集合

<div align="center">
	<img src="./doc/image/poet_cloud.png" width="100%">
</div>

![](https://img.shields.io/badge/%E4%B8%AD%E6%96%87-%E7%8E%B0%E4%BB%A3%E8%AF%97-red)
![](https://img.shields.io/badge/poems-5695-yellowgreen)
![](https://img.shields.io/badge/poets-524-lightgrey)
![](https://img.shields.io/github/repo-size/sheepzh/poetry)
![visitors](https://visitor-badge.glitch.me/badge?page_id=sheepzh.poetry)
![](https://img.shields.io/github/last-commit/sheepzh/poetry)
![](https://img.shields.io/github/commit-activity/w/sheepzh/poetry)

## 前言

+ 该目录及子目录下所有作品著作权归原作者所有。**`禁止用于任何商业用途`**。
+ 收录诗人而不创造诗人。
+ 数据格式见 [<u>data/README.md</u>](./data/README.md)
+ 反馈
	+ 邮箱：returnzhy1996@outlook.com
	+ ISSUES：[<u>Github</u>](https://github.com/sheepzh/poetry/issues) | [<u>码云</u>](https://gitee.com/make-zero/poetry/issues)

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
|	|
|	+++ simplify # 繁体转简体 Python3 脚本
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

## 将来

+ 使用 Python 替代 Java 工具包，见分支 tool_python
+ 实体抽取ing...
+ 多维度的——诗人性别，写作年代，流派等——云词图展示。
+ 共指消解
+ 意象间关系推理
