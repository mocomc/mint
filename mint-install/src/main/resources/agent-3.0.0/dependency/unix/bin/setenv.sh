# ------------------
# iip_agent_home
# ------------------
export IIPAGENT_HOME=/home/iip/nhiip/agent/linux001/iipagent
# ------------------
# java_home
# ------------------
export JAVA_HOME=/opt/jdk1.8.0_161
# ------------------
# memory option
# ------------------
export JAVA_OPTS="-Xms64m -Xmx128m"
# ------------------
# name option
# ------------------
# ------------------
# sigar lib option
# ------------------
export CMD_OPTS="-Dfile.encoding=utf-8 -DIIPAGENT_HOME=${IIPAGENT_HOME}  -Xverify:none"
# ------------------
# export classpath
# ------------------
export CLASSPATH=.:${IIPAGENT_HOME}/classes
export CLASSPATH=${CLASSPATH}:${IIPAGENT_HOME}/mint-agent-boot-0.0.1-SNAPSHOT.jar
