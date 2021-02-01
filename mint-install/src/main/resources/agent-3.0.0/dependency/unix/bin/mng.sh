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
# management command
# ------------------
${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} -cp ${CLASSPATH} pep.per.mint.agent.console.AgentManagementConsole -cmd $1

