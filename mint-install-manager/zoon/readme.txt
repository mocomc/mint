================================================================================
- Project for mint install manager
--------------------------------------------------------------------------------
- author : whoana
- begin  : 2018.03.27
--------------------------------------------------------------------------------
- update : 2018.10.02
		   농협 마이그레이션 기능 추가 개발
		   [참고] 	2.3.데이터 마이크레이션 실행(농협)
--------------------------------------------------------------------------------
1.설치관리자 설명 / 파일 구성 / 실행

    1.1.설명
        IIP 설치작업을 자동화하기 위해 설치관리자 프로그램을 작성하였다.
        주요 기능으로 아래 3가지 기능이 있다.
        1) 데이터 빌드
            IIP 설치를 위한 기초코드(100번 데이터), 사이트코드(200번 데이터) 를 각각 사이트관리 시스
            템에서 조회하여 설치위치 mint-data-create-100, mint-data-create-200 에 각각
            저장한다.
            데이터 빌드 기능은 멀티사이트관리 시스템상에 개발되어야 할 기능으로 임시적으로 사용한다.
        2) 트레킹 테이블 설치
            IFM 트레킹 테이블 설치를 진행한다. 함수 프로시저는 생성하지 않는다.
        3) IIP 설치
            IIP 웹애플리케이션 실행에 필요한 요소들인 mint-front-product-2.0.0.war
            기초코드(100번 데이터), 사이트코드(200번 데이터) 들을 copy 하고 설치 디비에 insert
            한다.

    1.2.설치 관리자 파일 구성
        {ZOON_HOME}
            |_ install.properties  --------------------------------- 설치전 작성이 필요한 설치 프로퍼티(별도 설명:1.3.5.1.프로퍼티)
            |_ install-info.json   --------------------------------- 설치관리자 JOB, TASK 스크립트
            |_ mint-install-manager-3.0.0.jar ---------------------- 설치관리자 소스
            |_ log  ------------------------------------------------ 설치관리자 실행 로그
            |_ dist+  ---------------------------------------------- IIP 설치를 위해 사용되는 리소스 파일 위치
            |      |_ mint-front-product-2.0.0.war  ---------------- IIP 웹애플리케이션 소스 WAR
            |      |_ mint-schema-create.sql ----------------------- IIP 테이블 생성 스크립트
            |      |_ mint-schema-drop.sql  ------------------------ IIP 테이블 드롭 스크립트
            |      |_ mint-data-delete-100 -+ ---------------------- 100번 데이터 삭제 스크립트 폴더
            |      |                        |_ truncate-100.sql ---- 100번 데이터 삭제 스크립트
            |      |- mint-data-delete-200 -+  --------------------- 200번 데이터 삭제 스크립트 폴더
            |      |                        |_ truncate-200.sql ---- 200번 데이터 삭제 스크립트
            |      |_ mint-data-create-100 -+  --------------------- 100번 데이터 생성 스크립트
            |      |                        |_ 101-TSU0301.sql  ---- 공토코드 생성 스크립트
            |      |                        |_ 102-TSU0302.sql  ---- 포털환경설정 생성 스크립트
            |      |                        |_ 103-TSU0217.sql  ---- 애플리케이션메시지 생성 스크립트
            |      |                        |_ 104-TSU0401.sql  ---- 공지사항카테고리 생성 스크립트
            |      |                        |_ 111-TBA0002.sql  ---- 배치잡그룹 생성 스크립트
            |      |                        |_ 112-TBA0001.sql  ---- 배치잡 생성 스크립트
            |      |                        |_ 113-TBA0003.sql  ---- 배치잡스케쥴 생성 스크립트
            |      |                        |_ 114-TBA0004.sql  ---- 배치잡스케쥴매핑 생성 스크립트
            |      |_ mint-data-create-200 -+  --------------------- 200번 데이터 생성 스크립트
            |                               |_ 201-TSU0204.sql  ---- 프로그램 생성 스크립트
            |                               |_ 202-TSU0207.sql  ---- 프로그램메뉴 생성 스크립트
            |                               |_ 203-TSU0208.sql  ---- 프로그램메뉴경로 생성 스크립트
            |                               |_ 204-TSU0201.sql  ---- 사용자롤 생성 스크립트
            |                               |_ 205-TSU0214.sql  ---- 프로그램메뉴매핑 생성 스크립트
            |                               |_ 206-TSU0101.sql  ---- 사용자 생성 스크립트
            |                               |_ 207-TSU0202.sql  ---- 사용자롤별프로그램권한 생성 스크립트
            |                               |_ 208-TSU0203.sql  ---- 사용자롤별메뉴권한 생성 스크립트
            |
            |_ create-mte-schema.sh  ------------------------------- 설치관리자 트레킹테이블 생성 실행 샘플 쉘
            |_ get-data-mint.sh      ------------------------------- 설치관리자 데이터빌드 실행 샘플 쉘
            |_ install-mint.sh       ------------------------------- 설치관리자 IIP설치 실행 샘플 쉘

    1.3.설치관리자 실행
        1.3.1.VM Params:
            ZOON_HOME : 설치관리자 홈디렉토리 패스
        1.3.2.main 함수 args :
            실행 옵션 값으로 사용됨
                실행 옵션 값 :
                    G    - 설치 데이터 생성 모드 : 데이터 생성 JOB[GetInstallData]만 수행
                    M    - 트레킹 테이블 설치 모드 : 트레킹 테이블 설치만 진행한다. IFM 제품상의 함수 프로시저는 생성하지 않는다.
                    I    - 설치 모드 : 데이터 생성 JOB을 제외한 모든 JOB 리스트 수행
                    A    - 모두 실행 : install-info.json 에 설정된대로 JOB 리스트 수행
        1.3.3.실행 예:
            #설치데이터빌드
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar G
            #트레킹테이블생성
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar M
            #IIP설치
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar I
            #모두수행
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar A
			#농협 데이터 마이그레이션
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar D

        1.3.4.실행 쉘스크립트
            별도의 프로그램 실행을 위한 쉘 스크립트는 재공하지 않으며 필요하다면 샘플 쉘을 응용하거나 "1.3.의 실행 예"를 참고하여 작성 요함.


        1.3.5.IIP 설치 실행
            IIP설치를 위해 아래 순서에 따라 진행한다.

                [프로퍼티 설정] --> [설치관리자 실행] --> [로그 확인]

                1.3.5.1.프로퍼티[install.properties] 설정
                    {ZOON_HOME}/install.properties 를 열고 필요한 부분을 편집한다.
                    1) ~ 9) 내용을 사이트에서 식별된 배포 정보로 변경한다.(필수 등록을 요하는 항목들임)
                    [*주 1)번 항목에서 ${HOME}부분은 설치관리자 실행시 지정한 ZOON_HOME으로 대체됨]
                    install.jdbc.* 값들은 설치관리자가 설치 전 해당 JDBC로 접속 테스트를 해보므로 정확한 정보 입력을 요한다.

                    [프로퍼티파일 내용]
                    #===============================================================================
                    # 설치 프로퍼티
                    #-------------------------------------------------------------------------------
                    install.site.code=DMC                                           --------------1) 사이트코드
                    install.war.file=${HOME}/dist/mint-front-product-2.0.0.war      --------------2) IIP 웹애플리케이션 소스
                    install.war.tar.path=/home/iip/tomcat8/webapps/mint.war         --------------3) IIP 웹애플리케이션 소스가 배포된 와스 타겟 위치 파일명
                    install.jdbc.driver.class.name=oracle.jdbc.driver.OracleDriver  --------------4) 설치될 IIP가 참조할 데이터베이스 JDBC 드라이버 정보
                    install.jdbc.url=jdbc:oracle:thin:@10.10.1.35:1521:IIP          --------------5) 설치될 IIP가 참조할 데이터베이스 JDBC URL
                    install.jdbc.username=celly                                     --------------6) 설치될 IIP가 참조할 데이터베이스 JDBC 로그인 USER
                    install.jdbc.password=celly                                     --------------7) 설치될 IIP가 참조할 데이터베이스 JDBC 로그인 PASSWORD
                    #-------------------------------------------------------------------------------
                    # 사이트 포털환경설정 (포털환경설정[TSU0302]으로 대체됨)
                    #-------------------------------------------------------------------------------
                    env.system.batch.basic.hostname=116-TAIIPCI-JCH                 --------------8) 설치서버 hostname
                    env.system.file.upload.path=/home/iip/upload                    --------------9) IIP 파일 업로드 물리 패스

                1.3.5.2.설치관리자 실행
                    아래 자바 명령 또는 쉘 상에 자바홈을 설정하여 실행한다.
                    실행 명령에 VM 옵션("-DZOON_HOME=") 을 설정하지 않으면 콘솔 입력 모드에서 직접 입력해 주어야 설치 작업이 진행된다.

                    java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar I
                        or
                    java -jar /home/iip/zoon/mint-install-manager-3.0.0.jar
                        or
                    install-mint.sh

                    설치작업 진행중 중간에 아래와 같은 프로프트 질문에 진행여부를 답하여 다음으로 넘어 가도록 하자.(실수로 잘못 진행하는 것을 방지하기 위함)
                    IIP Install을 진행하시겠습니까?(y/n):


                1.3.5.3.로그 확인
                    설치를 문제 없이 마치면 아래와 같은 내용의 로그확인이 가능하다.
                    아래 로그는 리눅스 개발서버에서 실행한 내용이며,
                    리눅스 서버
                    10.10.1.116 로 접속하여 직접 실행 및 로그확인이 가능하다.
                    리눅스접속 및 실행 방법은  아래와 같다.

                        ssh iip@10.10.1.116
                        패스워드 : iip
                        cd zoon
                        ./install-mint.sh

                    설치 서버 환경에 따라 차이를 보이겠지만 스키마 생성 JOB[200.DropAndCreateSchema]수행 시 1분이상 소요된다.

                    설치가 완료되면 아래 경로에서 톰캣을 실행하여 IIP로그인이 가능한 것을 확인할 수 있다.
                        [톰캣실행]
                            /home/iip/tomcat8/bin/catalina.sh run
                        [브라우저 접속 URL]
                            http://10.10.1.116:38080/mint

                    파일 로그 위치: ${ZOON_HOME}/log/logFile.log
                    [116-TAIIPCI-JCH@iip:~/zoon]./install-mint.sh
                    16:14:50,260 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
                    16:14:50,260 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
                    16:14:50,260 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Found resource [logback.xml] at [jar:file:/home/iip/zoon/mint-install-manager-3.0.0.jar!/logback.xml]
                    16:14:50,277 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@545997b1 - URL [jar:file:/home/iip/zoon/mint-install-manager-3.0.0.jar!/logback.xml] is not of type file
                    16:14:50,441 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - debug attribute not set
                    16:14:50,454 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - Will scan for changes in [jar:file:/home/iip/zoon/mint-install-manager-3.0.0.jar!/logback.xml]
                    16:14:50,454 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - Setting ReconfigureOnChangeTask scanning period to 30 seconds
                    16:14:50,466 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
                    16:14:50,469 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [STDOUT]
                    16:14:50,525 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - This appender no longer admits a layout as a sub-component, set an encoder instead.
                    16:14:50,525 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - To ensure compatibility, wrapping your layout in LayoutWrappingEncoder.
                    16:14:50,525 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - See also http://logback.qos.ch/codes.html#layoutInsteadOfEncoder for details
                    16:14:50,526 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.rolling.RollingFileAppender]
                    16:14:50,528 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [FILE]
                    16:14:50,538 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@1291113768 - Archive files will be limited to [30 MB] each.
                    16:14:50,540 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@1291113768 - No compression will be used
                    16:14:50,542 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@1291113768 - Will use the pattern /home/iip/zoon//log//logFile.%d{yyyy-MM-dd}.%i.log for the active file
                    16:14:50,545 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@77846d2c - The date pattern is 'yyyy-MM-dd' from file name pattern '/home/iip/zoon//log//logFile.%d{yyyy-MM-dd}.%i.log'.
                    16:14:50,545 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@77846d2c - Roll-over at midnight.
                    16:14:50,550 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@77846d2c - Setting initial period to Wed Mar 28 16:06:13 KST 2018
                    16:14:50,550 |-WARN in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@77846d2c - SizeAndTimeBasedFNATP is deprecated. Use SizeAndTimeBasedRollingPolicy instead
                    16:14:50,553 |-INFO in ch.qos.logback.core.joran.action.NestedComplexPropertyIA - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property
                    16:14:50,557 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[FILE] - Active log file name: /home/iip/zoon//log//logFile.log
                    16:14:50,557 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[FILE] - File property is set to [/home/iip/zoon//log//logFile.log]
                    16:14:50,559 |-INFO in ch.qos.logback.classic.joran.action.LevelAction - pep.per.mint level set to DEBUG
                    16:14:50,559 |-INFO in ch.qos.logback.classic.joran.action.LevelAction - pep.per.mint.common level set to DEBUG
                    16:14:50,559 |-INFO in ch.qos.logback.classic.joran.action.LevelAction - pep.per.mint.install.manager level set to DEBUG
                    16:14:50,560 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to DEBUG
                    16:14:50,560 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [STDOUT] to Logger[ROOT]
                    16:14:50,560 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [FILE] to Logger[ROOT]
                    16:14:50,560 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
                    16:14:50,561 |-INFO in ch.qos.logback.classic.joran.JoranConfigurator@548ad73b - Registering current configuration as safe fallback point

                    SLF4J: The requested version 1.7.16 by your slf4j binding is not compatible with [1.6]
                    SLF4J: See http://www.slf4j.org/codes.html#version_mismatch for further details.
                    properties:{"install.war.tar.path":"/home/iip/tomcat8/webapps/mint.war","install.war.file":"/home/iip/zoon/dist/mint-front-product-2.0.0.war","install.jdbc.url":"jdbc:oracle:thin:@10.10.1.35:1521:IIP","install.jdbc.password":"celly","env.system.file.upload.path":"/home/iip/upload","install.jdbc.driver.class.name":"oracle.jdbc.driver.OracleDriver","install.jdbc.username":"celly","install.site.code":"DMC","env.system.batch.basic.hosthame":"116-TAIIPCI-JCH"}
                    ================================================================================
                    install.jdbc 프로퍼티 체크합니다.
                    --------------------------------------------------------------------------------
                     jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                     jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                     jdbcUsername:[celly]
                     jdbcPassword:[celly]
                     jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 체크 성공하였습니다.
                    181428 16:14:51.026 >================================================================================
                    181428 16:14:51.030 > IIP 설치 작업을 진행한다.
                    181428 16:14:51.050 > date : 20180328161451
                    181428 16:14:51.051 >--------------------------------------------------------------------------------
                    181428 16:14:51.051 > args list:
                    181428 16:14:51.052 >--------------------------------------------------------------------------------
                    181428 16:14:51.053 > args[0]:I
                    181428 16:14:51.053 > runOption:I
                    181428 16:14:51.053 >--------------------------------------------------------------------------------
                    IIP Install을 진행하시겠습니까?(y/n):
                    y
                    181428 16:14:56.761 > JOB 설정을 시작한다.
                    181428 16:14:56.762 >--------------------------------------------------------------------------------
                    181428 16:14:56.765 > JOB[100.CopyWarToDestination] 설정을 시작합니다.
                    181428 16:14:56.765 >--------------------------------------------------------------------------------
                    181428 16:14:56.765 > TASK[101.CopyWar] 설정을 시작합니다.
                    181428 16:14:56.766 >--------------------------------------------------------------------------------
                    181428 16:14:56.768 > task executor[pep.per.mint.install.job.task.CopyFileTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.771 > source:[/home/iip/zoon/dist/mint-front-product-2.0.0.war]
                    181428 16:14:56.772 > destination:[/home/iip/tomcat8/webapps/mint.war]
                    181428 16:14:56.772 > task executor[pep.per.mint.install.job.task.CopyFileTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.772 >--------------------------------------------------------------------------------
                    181428 16:14:56.772 > TASK[101.CopyWar] 설정을 완료하였습니다.
                    181428 16:14:56.772 >--------------------------------------------------------------------------------
                    181428 16:14:56.773 > JOB[100.CopyWarToDestination] 설정을 완료하였습니다.
                    181428 16:14:56.773 > JOB[200.DropAndCreateSchema] 설정을 시작합니다.
                    181428 16:14:56.773 >--------------------------------------------------------------------------------
                    181428 16:14:56.773 > TASK[201.DropAndCreateSchema] 설정을 시작합니다.
                    181428 16:14:56.774 >--------------------------------------------------------------------------------
                    181428 16:14:56.776 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.777 > source:[files]
                    181428 16:14:56.777 > files:[[/home/iip/zoon/dist/mint-schema-drop.sql, /home/iip/zoon/dist/mint-schema-create.sql]]
                    181428 16:14:56.777 > path:[]
                    181428 16:14:56.778 > order:[]
                    181428 16:14:56.778 > onError:[continue]
                    181428 16:14:56.778 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181428 16:14:56.778 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181428 16:14:56.779 > jdbcUsername:[celly]
                    181428 16:14:56.779 > jdbcPassword:[celly]
                    181428 16:14:56.779 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.779 >--------------------------------------------------------------------------------
                    181428 16:14:56.779 > TASK[201.DropAndCreateSchema] 설정을 완료하였습니다.
                    181428 16:14:56.780 >--------------------------------------------------------------------------------
                    181428 16:14:56.780 > JOB[200.DropAndCreateSchema] 설정을 완료하였습니다.
                    181428 16:14:56.783 > JOB[300.RunBasicCodeData] 설정을 시작합니다.
                    181428 16:14:56.783 >--------------------------------------------------------------------------------
                    181428 16:14:56.783 > TASK[301.DeleteBasicCode] 설정을 시작합니다.
                    181428 16:14:56.783 >--------------------------------------------------------------------------------
                    181428 16:14:56.784 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.786 > source:[directory]
                    181428 16:14:56.787 > files:[]
                    181428 16:14:56.787 > path:[/home/iip/zoon/dist/mint-data-delete-100]
                    181428 16:14:56.787 > order:[NONE]
                    181428 16:14:56.788 > onError:[stop]
                    181428 16:14:56.788 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181428 16:14:56.798 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181428 16:14:56.798 > jdbcUsername:[celly]
                    181428 16:14:56.798 > jdbcPassword:[celly]
                    181428 16:14:56.799 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.799 >--------------------------------------------------------------------------------
                    181428 16:14:56.799 > TASK[301.DeleteBasicCode] 설정을 완료하였습니다.
                    181428 16:14:56.799 > TASK[302.InsertBasicCode] 설정을 시작합니다.
                    181428 16:14:56.799 >--------------------------------------------------------------------------------
                    181428 16:14:56.800 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.800 > source:[directory]
                    181428 16:14:56.800 > files:[]
                    181428 16:14:56.801 > path:[/home/iip/zoon/dist/mint-data-create-100]
                    181428 16:14:56.801 > order:[NONE]
                    181428 16:14:56.801 > onError:[stop]
                    181428 16:14:56.801 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181428 16:14:56.802 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181428 16:14:56.802 > jdbcUsername:[celly]
                    181428 16:14:56.802 > jdbcPassword:[celly]
                    181428 16:14:56.802 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.802 >--------------------------------------------------------------------------------
                    181428 16:14:56.803 > TASK[302.InsertBasicCode] 설정을 완료하였습니다.
                    181428 16:14:56.806 >--------------------------------------------------------------------------------
                    181428 16:14:56.806 > JOB[300.RunBasicCodeData] 설정을 완료하였습니다.
                    181428 16:14:56.806 > JOB[400.RunSiteCodeData] 설정을 시작합니다.
                    181428 16:14:56.807 >--------------------------------------------------------------------------------
                    181428 16:14:56.807 > TASK[401.DeleteSiteCode] 설정을 시작합니다.
                    181428 16:14:56.807 >--------------------------------------------------------------------------------
                    181428 16:14:56.807 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.808 > source:[directory]
                    181428 16:14:56.808 > files:[]
                    181428 16:14:56.809 > path:[/home/iip/zoon/dist/mint-data-delete-200]
                    181428 16:14:56.809 > order:[NONE]
                    181428 16:14:56.809 > onError:[stop]
                    181428 16:14:56.809 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181428 16:14:56.810 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181428 16:14:56.810 > jdbcUsername:[celly]
                    181428 16:14:56.810 > jdbcPassword:[celly]
                    181428 16:14:56.810 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.811 >--------------------------------------------------------------------------------
                    181428 16:14:56.811 > TASK[401.DeleteSiteCode] 설정을 완료하였습니다.
                    181428 16:14:56.811 > TASK[402.InsertSiteCode] 설정을 시작합니다.
                    181428 16:14:56.811 >--------------------------------------------------------------------------------
                    181428 16:14:56.812 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.812 > source:[directory]
                    181428 16:14:56.812 > files:[]
                    181428 16:14:56.812 > path:[/home/iip/zoon/dist/mint-data-create-200]
                    181428 16:14:56.813 > order:[NONE]
                    181428 16:14:56.813 > onError:[stop]
                    181428 16:14:56.813 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181428 16:14:56.813 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181428 16:14:56.814 > jdbcUsername:[celly]
                    181428 16:14:56.814 > jdbcPassword:[celly]
                    181428 16:14:56.814 > task executor[pep.per.mint.install.job.task.RunSqlTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.814 >--------------------------------------------------------------------------------
                    181428 16:14:56.814 > TASK[402.InsertSiteCode] 설정을 완료하였습니다.
                    181428 16:14:56.815 >--------------------------------------------------------------------------------
                    181428 16:14:56.815 > JOB[400.RunSiteCodeData] 설정을 완료하였습니다.
                    181428 16:14:56.815 > JOB[500.UpdatePortalEnv] 설정을 시작합니다.
                    181428 16:14:56.815 >--------------------------------------------------------------------------------
                    181428 16:14:56.816 > TASK[501.UpdatePortalEnv] 설정을 시작합니다.
                    181428 16:14:56.816 >--------------------------------------------------------------------------------
                    181428 16:14:56.818 > task executor[pep.per.mint.install.job.task.UpdatePortalEnvTaskExecutor] 파라메터 초기화 시작합니다.
                    181428 16:14:56.818 > task executor[pep.per.mint.install.job.task.UpdatePortalEnvTaskExecutor] 파라메터 초기화 종료하였습니다.
                    181428 16:14:56.819 >--------------------------------------------------------------------------------
                    181428 16:14:56.819 > TASK[501.UpdatePortalEnv] 설정을 완료하였습니다.
                    181428 16:14:56.819 >--------------------------------------------------------------------------------
                    181428 16:14:56.819 > JOB[500.UpdatePortalEnv] 설정을 완료하였습니다.
                    181428 16:14:56.820 >--------------------------------------------------------------------------------
                    181428 16:14:56.820 > JOB 설정을 성공적으로 완료함.
                    181428 16:14:56.820 > JOB 설정 개수:5
                    181428 16:14:56.820 > 실행할 JOB 리스트:
                    181428 16:14:56.821 > JobExecutor:100.CopyWarToDestination
                    181428 16:14:56.821 > JobExecutor:200.DropAndCreateSchema
                    181428 16:14:56.821 > JobExecutor:300.RunBasicCodeData
                    181428 16:14:56.821 > JobExecutor:400.RunSiteCodeData
                    181428 16:14:56.822 > JobExecutor:500.UpdatePortalEnv
                    181428 16:14:56.822 >--------------------------------------------------------------------------------
                    181428 16:14:56.822 > JOB 설정을 완료한다.
                    181428 16:14:56.823 >
                    181428 16:14:57.823 > 설치를 시작합니다(대략 2~10분 소요됩니다. 환경에 따라 달라질 수 있습니다.)
                    181428 16:14:57.824 >--------------------------------------------------------------------------------
                    181428 16:14:58.824 > JOB[100.CopyWarToDestination]을 실행합니다.
                    181428 16:14:58.825 >--------------------------------------------------------------------------------
                    181428 16:14:58.826 > TASK[101.CopyWar]를 실행합니다.
                    181428 16:14:58.826 >--------------------------------------------------------------------------------
                    181428 16:14:58.826 > source to copy:[/home/iip/zoon/dist/mint-front-product-2.0.0.war]
                    181428 16:14:58.826 > destination to copy:[/home/iip/tomcat8/webapps/mint.war]
                    181428 16:14:59.051 > copy 완료
                    181428 16:14:59.052 >--------------------------------------------------------------------------------
                    181428 16:14:59.052 > TASK[101.CopyWar]를 종료합니다.
                    181428 16:14:59.052 >--------------------------------------------------------------------------------
                    181428 16:14:59.052 > JOB[100.CopyWarToDestination]을 종료합니다.
                    181528 16:15:00.053 > JOB[200.DropAndCreateSchema]을 실행합니다.
                    181528 16:15:00.053 >--------------------------------------------------------------------------------
                    181528 16:15:00.054 > TASK[201.DropAndCreateSchema]를 실행합니다.
                    181528 16:15:00.054 >--------------------------------------------------------------------------------
                    181528 16:15:00.098 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 성공
                    181528 16:15:00.098 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-schema-drop.sql]
                    181528 16:15:22.130 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-schema-drop.sql]
                    181528 16:15:22.131 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-schema-create.sql]
                    181628 16:16:13.550 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-schema-create.sql]
                    181628 16:16:13.553 > TASK[201.DropAndCreateSchema] 성공
                    181628 16:16:13.568 >--------------------------------------------------------------------------------
                    181628 16:16:13.568 > TASK[201.DropAndCreateSchema]를 종료합니다.
                    181628 16:16:13.568 >--------------------------------------------------------------------------------
                    181628 16:16:13.568 > JOB[200.DropAndCreateSchema]을 종료합니다.
                    181628 16:16:14.569 > JOB[300.RunBasicCodeData]을 실행합니다.
                    181628 16:16:14.569 >--------------------------------------------------------------------------------
                    181628 16:16:14.570 > TASK[301.DeleteBasicCode]를 실행합니다.
                    181628 16:16:14.582 >--------------------------------------------------------------------------------
                    181628 16:16:14.630 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 성공
                    181628 16:16:14.631 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-delete-100/truncate-100.sql]
                    181628 16:16:14.708 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-delete-100/truncate-100.sql]
                    181628 16:16:14.710 > TASK[301.DeleteBasicCode] 성공
                    181628 16:16:14.725 >--------------------------------------------------------------------------------
                    181628 16:16:14.725 > TASK[301.DeleteBasicCode]를 종료합니다.
                    181628 16:16:14.726 > TASK[302.InsertBasicCode]를 실행합니다.
                    181628 16:16:14.726 >--------------------------------------------------------------------------------
                    181628 16:16:14.786 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 성공
                    181628 16:16:14.786 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/101-TSU0301.sql]
                    181628 16:16:15.129 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/101-TSU0301.sql]
                    181628 16:16:15.129 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/102-TSU0302.sql]
                    181628 16:16:15.239 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/102-TSU0302.sql]
                    181628 16:16:15.240 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/103-TSU0217.sql]
                    181628 16:16:17.504 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/103-TSU0217.sql]
                    181628 16:16:17.505 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/104-TSU0401.sql]
                    181628 16:16:17.524 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/104-TSU0401.sql]
                    181628 16:16:17.524 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/111-TBA0002.sql]
                    181628 16:16:17.534 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/111-TBA0002.sql]
                    181628 16:16:17.535 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/112-TBA0001.sql]
                    181628 16:16:17.564 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/112-TBA0001.sql]
                    181628 16:16:17.565 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/113-TBA0003.sql]
                    181628 16:16:17.577 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/113-TBA0003.sql]
                    181628 16:16:17.577 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-100/114-TBA0004.sql]
                    181628 16:16:17.590 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-100/114-TBA0004.sql]
                    181628 16:16:17.619 > TASK[302.InsertBasicCode] 성공
                    181628 16:16:17.629 >--------------------------------------------------------------------------------
                    181628 16:16:17.630 > TASK[302.InsertBasicCode]를 종료합니다.
                    181628 16:16:17.630 >--------------------------------------------------------------------------------
                    181628 16:16:17.630 > JOB[300.RunBasicCodeData]을 종료합니다.
                    181628 16:16:18.631 > JOB[400.RunSiteCodeData]을 실행합니다.
                    181628 16:16:18.631 >--------------------------------------------------------------------------------
                    181628 16:16:18.631 > TASK[401.DeleteSiteCode]를 실행합니다.
                    181628 16:16:18.632 >--------------------------------------------------------------------------------
                    181628 16:16:18.705 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 성공
                    181628 16:16:18.705 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-delete-200/truncate-200.sql]
                    181628 16:16:18.773 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-delete-200/truncate-200.sql]
                    181628 16:16:18.774 > TASK[401.DeleteSiteCode] 성공
                    181628 16:16:18.804 >--------------------------------------------------------------------------------
                    181628 16:16:18.804 > TASK[401.DeleteSiteCode]를 종료합니다.
                    181628 16:16:18.804 > TASK[402.InsertSiteCode]를 실행합니다.
                    181628 16:16:18.805 >--------------------------------------------------------------------------------
                    181628 16:16:18.857 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 성공
                    181628 16:16:18.857 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/201-TSU0204.sql]
                    181628 16:16:18.988 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/201-TSU0204.sql]
                    181628 16:16:18.988 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/202-TSU0207.sql]
                    181628 16:16:19.113 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/202-TSU0207.sql]
                    181628 16:16:19.114 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/203-TSU0208.sql]
                    181628 16:16:19.313 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/203-TSU0208.sql]
                    181628 16:16:19.314 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/204-TSU0201.sql]
                    181628 16:16:19.329 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/204-TSU0201.sql]
                    181628 16:16:19.329 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/205-TSU0214.sql]
                    181628 16:16:19.408 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/205-TSU0214.sql]
                    181628 16:16:19.409 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/206-TSU0101.sql]
                    181628 16:16:19.421 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/206-TSU0101.sql]
                    181628 16:16:19.422 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/207-TSU0202.sql]
                    181628 16:16:19.777 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/207-TSU0202.sql]
                    181628 16:16:19.777 > SQL 파일 실행 시작:[/home/iip/zoon/dist/mint-data-create-200/208-TSU0203.sql]
                    181628 16:16:20.300 > SQL 파일 실행 종료:[/home/iip/zoon/dist/mint-data-create-200/208-TSU0203.sql]
                    181628 16:16:20.321 > TASK[402.InsertSiteCode] 성공
                    181628 16:16:20.332 >--------------------------------------------------------------------------------
                    181628 16:16:20.333 > TASK[402.InsertSiteCode]를 종료합니다.
                    181628 16:16:20.334 >--------------------------------------------------------------------------------
                    181628 16:16:20.334 > JOB[400.RunSiteCodeData]을 종료합니다.
                    181628 16:16:21.334 > JOB[500.UpdatePortalEnv]을 실행합니다.
                    181628 16:16:21.334 >--------------------------------------------------------------------------------
                    181628 16:16:21.335 > TASK[501.UpdatePortalEnv]를 실행합니다.
                    181628 16:16:21.336 >--------------------------------------------------------------------------------
                    181628 16:16:21.337 > jdbcDriver:[oracle.jdbc.driver.OracleDriver]
                    181628 16:16:21.337 > jdbcUrl:[jdbc:oracle:thin:@10.10.1.35:1521:IIP]
                    181628 16:16:21.337 > jdbcUsername:[celly]
                    181628 16:16:21.337 > jdbcPassword:[celly]
                    181628 16:16:21.383 > jdbc connection[jdbc:oracle:thin:@10.10.1.35:1521:IIP] 테스트를 성공하였습니다.
                    181628 16:16:21.383 > 포털환경설정 업데이트를 시작합니다.:update TSU0302 set ATTRIBUTE_VALUE = '116-TAIIPCI-JCH' where PACKAGE = 'system' and ATTRIBUTE_ID = 'batch.basic.hostname'
                    181628 16:16:21.391 > 포털환경설정 업데이트를 종료합니다. : result code(1)
                    181628 16:16:21.391 > 포털환경설정 업데이트를 시작합니다.:update TSU0302 set ATTRIBUTE_VALUE = '/home/iip/upload' where PACKAGE = 'system' and ATTRIBUTE_ID = 'file.upload.path'
                    181628 16:16:21.394 > 포털환경설정 업데이트를 종료합니다. : result code(1)
                    181628 16:16:21.410 >--------------------------------------------------------------------------------
                    181628 16:16:21.410 > TASK[501.UpdatePortalEnv]를 종료합니다.
                    181628 16:16:21.410 >--------------------------------------------------------------------------------
                    181628 16:16:21.410 > JOB[500.UpdatePortalEnv]을 종료합니다.
                    181628 16:16:21.411 >--------------------------------------------------------------------------------
                    181628 16:16:21.411 > 축하합니다. 설치를 성공적으로 마쳤습니다.
                    181628 16:16:21.411 >--------------------------------------------------------------------------------
                    181628 16:16:21.411 > date : 20180328161621
                    181628 16:16:21.411 > 설치를 종료합니다.

2.그 밖에 것들

    2.1.데이터 빌드(get-data-mint.sh)
        멀티사이트 지원을 위한 데이터 관리 시스템인 MSM(MustSupportMananger) 상에서 배포데이터를
        내려받아 설치관리자를 통해 사이트에 설치가능한 데이터 스크립트를 생성하는 명령이다.

        아래 쉘명령
            get-data-mint.sh
        또는
        "1.3.3.실행 예"를 참고한다.
            #설치데이터빌드
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar G

    2.2.트레킹 테이블 설치(create-mte-schma.sh)
        MTE 또는 IM 를 통해 구축된 사이트의 트레킹 테이블을 생성하는 기능으로 설치가 필요한 경우에만 수행한다.
        보통 신규 사이트의 경우 MD사업부 기술지원1팀 또는 프로젝트 담당자와 협의 거쳐 설치 여부를 결정한다.
        테이블에 대한 오너십은 MD사업부 기술지원1팀에 있다.
        본 설치 실행으로 생성되지 않는 부분은 함수 및 프로시저이다.
        함수 및 프로시저는 별도 설치가 필요하다.

        "1.3.3.실행 예"를 참고한다.
            #트레킹테이블생성
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar M

	2.3.데이터 마이크레이션 실행(농협)
		현재 농협사이트의 경우 데이터 마이그레이션을 개발 제공함.
		기존 일집계테이블[MASTERLOG_STAT_DAY]을 소스로 하여 마이그레이션 진행한다.
		[일별집계 마이그레이션]
			MASTERLOG_STAT_DAY --> TSU0804
		[월별집계] 월테이블은 마이그레이션 대신 마이그레이션을 통해 생성된 일집계데이터[TSU0804]을 소스로 하여 집계하는 것으로 진행함.
			TSU0804 --> TSU0805
		#실행전에 {ZOON_HOME}/install-info.json 파일을 오픈하여
		 Job [MigrationNHData] 부분의 파라메터값 fromDay, toDay 값에 마이그레이션 시작 종료일자로 입력해준다.
			{
		      "objectType":"Job",
		      "name":"MigrationNHData",
		      "use":false,
		      "doNextOnError":false,
		      "init":"init",
		      "tasks":[
		        {
		          "objectType":"Task",
		          "name":"MigrationNHDataTask",
		          "use":true,
		          "executor":"pep.per.mint.install.job.task.NHMigExecutor",
		          "doNextOnError":false,
		          "params":{
		          	"fromDay" : "20181002",
		          	"toDay"   : "20181002",
		          	"commitCount" : 1000
		          }
		        }
		      ]
		    }
		#농협 데이터 마이그레이션 실행(옵션값 : D)
		 실행시 주의사항 : mint-install-manager-3.0.0.jar 을 이용하라(mint-install-manager-2.0.0.jar 는 아님)
            java -DZOON_HOME=/home/iip/zoon -jar /home/iip/zoon/mint-install-manager-3.0.0.jar D



