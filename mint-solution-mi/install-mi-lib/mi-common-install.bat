set MVN_CMD=mvn
set INSTALL_FILE=C:\project\workspace\iip_com\mint\mint-solution-mi\install-mi-lib\lib\mi_common.jar
set GROUP_ID=com.mocomsys
set ARTIFACT_ID=mi-common
set VERSION=1.0
%MVN_CMD% install:install-file -Dfile=%INSTALL_FILE% -DgroupId=%GROUP_ID% -DartifactId=%ARTIFACT_ID% -Dversion=%VERSION% -Dpackaging=jar -DgeneratePom=true
