#!/bin/sh
# ------------------
# iip_agent_home
# ------------------
export IIPAGENT_HOME=/home/iip/nhiip/agent/linux001/iipagent
# ------------------
# run environment shell
# ------------------
. ${IIPAGENT_HOME}/bin/setenv.sh
# ------------------
# exit before iip agent start
# ------------------
${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} -cp ${CLASSPATH} -DiipAgent pep.per.mint.agent.console.AgentManagementConsole -cmd EXT
# ------------------
# run iip agent
# ------------------
arg1=$1
if [ "$arg1" = "-f" ]; then
        echo "forground start..."
        ${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} ${NAME_OPTS} -cp ${CLASSPATH} pep.per.mint.agent.IIPAgentStartUp
else
        echo "background start..."
        nohup ${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} ${NAME_OPTS} -cp ${CLASSPATH} pep.per.mint.agent.IIPAgentStartUp > /dev/null &
fi
