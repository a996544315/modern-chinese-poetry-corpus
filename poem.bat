@echo off

set version=0.0.1
set vmOptions=-Dfile.encoding=UTF-8
set jarpath=.\lib\poem-tool-%version%.jar

set allparam=

:param
set str=%1
if "%str%"=="" (
    goto end
)
set allparam=%allparam% %str%
shift /0
goto param

:end
if "%allparam%"=="" (
    goto eof
)

rem remove left right blank
:intercept_left
if "%allparam:~0,1%"==" " set "allparam=%allparam:~1%"&goto intercept_left

:intercept_right
if "%allparam:~-1%"==" " set "allparam=%allparam:~0,-1%"&goto intercept_right

:eof
java %vmOptions% -jar %jarpath% %allparam%