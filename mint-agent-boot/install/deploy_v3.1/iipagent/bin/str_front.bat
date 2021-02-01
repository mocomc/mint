@echo off
rem ------------------
rem iip_agent_home
rem ------------------
set "IIPAGENT_HOME=C:\WORK\IIP_V3\mint\mint-agent-boot\install\iipagent"
rem ------------------
rem java_home
rem ------------------
set "JAVA_HOME=C:/Java/jdk170_79/"
rem ------------------
rem memory option
rem ------------------
set "JAVA_OPTS=-Xms64m -Xmx1024m"
rem ------------------
rem sigar lib option
rem ------------------
set "CMD_OPTS=-Djava.library.path=%IIPAGENT_HOME%/lib  -DIIPAGENT_HOME=C:\WORK\IIP_V3\mint\mint-agent-boot\install\iipagent -Xverify:none"
rem ------------------
rem set classpath
rem ------------------
set "CLASSPATH=.;%CLASSPATH%;%IIPAGENT_HOME%\classes"
set "CLASSPATH=%CLASSPATH%;%IIPAGENT_HOME%/mint-agent-boot-0.0.1-SNAPSHOT.jar"
rem ------------------
rem run iip agent
rem ------------------
%JAVA_HOME%/bin/java %JAVA_OPTS% %CMD_OPTS% -classpath %CLASSPATH% pep.per.mint.agent.IIPAgentStartUp


