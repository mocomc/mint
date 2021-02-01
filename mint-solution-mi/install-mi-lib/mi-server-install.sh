MVN_CMD=mvn
INSTALL_FILE=/Users/whoana/DEV/NetBeansProjects/mint/mint-solution-mi/install-mi-lib/lib/mi_server_api.jar
GROUP_ID=com.mocomsys
ARTIFACT_ID=mi-server
VERSION=1.0
${MVN_CMD} install:install-file -Dfile=${INSTALL_FILE} -DgroupId=${GROUP_ID} -DartifactId=${ARTIFACT_ID} -Dversion=${VERSION} -Dpackaging=jar -DgeneratePom=true
