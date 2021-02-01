#!/bin/sh
# ------------------
# iip_agent_home
# ------------------
export IIPAGENT_HOME=/Users/whoana/DEV/iipagent
# ------------------
# run environment shell
# ------------------
. ${IIPAGENT_HOME}/bin/setenv.sh
# ------------------
# management command
# ------------------
${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} -cp ${CLASSPATH} pep.per.mint.agent.console.AgentManagementConsole -cmd $1


