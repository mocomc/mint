#!/bin/sh

fromDate="$1"
toDate="$2"
echo "-------------------------------------------------"
echo "migration data between : ${fromDate} ~ ${toDate}"
echo "-------------------------------------------------"

# ------------------
# iip_agent_home
# ------------------
export MIG_HOME=/Users/whoana/DEV/mig
# ------------------
# java_home
# ------------------
export JAVA_HOME=/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home
# ------------------
# memory option
# ------------------
export JAVA_OPTS="-Xms512m -Xmx1024m"
# ------------------
# name option
# ------------------
export NAME_OPTS="-DMigrationManager"
# ------------------
# encoding
# ------------------
export CMD_OPTS="-Dfile.encoding=utf-8"
# ------------------
# export classpath
# ------------------
export CLASSPATH=.:${MIG_HOME}/classes
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/aopalliance-1.0.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/aspectjrt-1.6.10.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-codec-1.9.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-collections-20040616.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-dbcp-20030825.184428.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-io-2.4.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-lang3-3.1.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-logging-1.1.1.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/commons-pool-20030825.183949.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/jackson-annotations-2.3.4.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/jackson-core-2.3.4.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/jackson-databind-2.3.4.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/jcl-over-slf4j-1.6.6.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/jstl-1.2.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/log4j-1.2.16.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/mint-common-0.0.1-SNAPSHOT.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/mybatis-3.1.0.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/mybatis-spring-1.2.0.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/ojdbc6-11.2.0.3.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/servlet-api-2.5.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/slf4j-api-1.6.6.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/slf4j-log4j12-1.6.6.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-aop-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-beans-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-context-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-context-support-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-core-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-expression-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-jdbc-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-orm-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-tx-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-web-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/spring-webmvc-3.2.4.RELEASE.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/xercesImpl-2.11.0.jar
export CLASSPATH=${CLASSPATH}:${MIG_HOME}/lib/xml-apis-1.4.01.jar
# ------------------
# run mig
# ------------------
${JAVA_HOME}/bin/java ${JAVA_OPTS} ${CMD_OPTS} ${NAME_OPTS} -cp ${CLASSPATH} pep.per.mint.migration.MigrationGenerator -from ${fromDate} -to ${toDate}
