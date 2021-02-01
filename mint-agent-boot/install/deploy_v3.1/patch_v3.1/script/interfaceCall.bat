@echo off
rem java_home
rem ------------------
rem set "JAVA_HOME=C:/Java/jdk170_79/"
rem ------------------
rem memory option
rem ------------------
set "JAVA_OPTS=-Xms64m -Xmx1024m"
rem ------------------
rem sigar lib option
rem ------------------
set "CMD_OPTS= "
rem ------------------
rem set classpath
rem ------------------
set "CLASSPATH=.;%CLASSPATH%"
rem ------------------
rem run iip agent
rem ------------------
start /B java %JAVA_OPTS% %CMD_OPTS% -classpath %CLASSPATH% jcall  %1 1


