@echo off

##set subl="D:\Monkey\Sublime Text 3\sublime_text.exe"

chcp 65001

set title=%1
set write_date = %2
set file_name=%title%.pt
echo title:%title%>%file_name%
echo date:%write_date%>>%file_name%

##%subl% %file_name%>nul

notepad %file_name%>nul

