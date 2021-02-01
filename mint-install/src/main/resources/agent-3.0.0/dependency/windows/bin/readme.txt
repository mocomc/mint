===========================================================================================
- HOW TO INSTALL IIP AGENT WINDOW SERVICE         										  =
-------------------------------------------------------------------------------------------
- date   : 20190115                   													  -
- author : whoana                     													  -
-------------------------------------------------------------------------------------------

1.서비스 환경변수 설정하기
  아래 윈도우 서비스 설정파일 mint-agent-boot.xml 를 열어서 환경변수를 수정한다.
  아래 윈도우 서비스 설정파일은 {IIPAGENT_HOME}\bin 아래 두도록 한다.
	{IIPAGENT_HOME}\bin\mint-agent-boot.xml

  설정해야할 환경변수는 AGENT_HOME, JAVA_HOME, JAVA_OPTS 등 3가지 이다.
 	1.1.AGENT_HOME
		에이전트 설치 홈 위치를 지정해 준다.
		설정 예)
			<env name="AGENT_HOME" 	value="C:\Software\iipagent" />

	1.2.JAVA_HOME
		자바홈을 지정해 준다.
		설정 예)
			<env name="JAVA_HOME"	value="C:\Software\Java\JDK7_64" />

	1.3.JAVA_OPTS
		힙메모리 등 자바 실행 옵션을 지정해 준다.
		설정 예)
			<env name="JAVA_OPTS" 	value="-Xms64m -Xmx1024m -Xverify:none" />

2.서비스 등록하기
	2.1.서비스 등록
		아래 명령을 수행하여 서비스 등록을 완료한다.
			{IIPAGENT_HOME}\bin\mint-agent-boot.exe install
		실행 예)
			C:\Software\iipagent>mint-agent-boot.exe install
			2019-01-15 13:18:24,656 INFO  - Installing the service with id 'mint-agent-boot'

	2.2.서비스 로그
		서비스 등록/삭제/실행/종료 로그
			{IIPAGENT_HOME}\bin\mint-agent-boot.wrapper.log
		서비스 등록/실행 에러 로그
			{IIPAGENT_HOME}\bin\mint-agent-boot.err.log
		mint-agent-boot 시스템 OUT 로그
			{IIPAGENT_HOME}\bin\mint-agent-boot.out.log

	2.3.서비스 테스트/상태확인/실행/종료/삭제
		2.3.1.테스트
			서비스 설치 후 테스트 명령
				{IIPAGENT_HOME}\bin\mint-agent-boot.exe testWait
			실행 예)
				C:\Software\iipagent>mint-agent-boot.exe testWait
				2019-01-15 14:24:57,518 INFO  - Starting C:\Software\Java\JDK7_64\bin\java -Xms64m -Xmx1024m -Xverify:none -classpath .;C:\Software\iipagent\mint-a
				2019-01-15 14:24:57,534 INFO  - Starting C:\Software\Java\JDK7_64\bin\java -Xms64m -Xmx1024m -Xverify:none -classpath .;C:\Software\iipagent\mint-a
				2019-01-15 14:24:57,612 INFO  - Started process 3780
				Press any key to stop the service...
				2019-01-15 14:25:47,532 INFO  - Stopping mint-agent-boot
				2019-01-15 14:25:47,641 INFO  - Stopping process 3780
				2019-01-15 14:25:47,656 INFO  - Send SIGINT 3780
				2019-01-15 14:25:47,656 WARN  - SIGINT to 3780 failed - Killing as fallback
				2019-01-15 14:25:47,672 INFO  - Finished mint-agent-boot

		2.3.2.실행
			서비스 설치 후 서비스 시작 명령
				{IIPAGENT_HOME}\bin\mint-agent-boot.exe start
			*윈도우즈 서비스관리화면에서 실행해되 됨(일반적인 사용법),
			 서비스가 설치되면 기본적인 옵션은 자동(Auto)이며 최초 실행을 제외하고는 OS 기동시 자동 실행됨.
			실행 예)
				C:\Software\iipagent>mint-agent-boot.exe start
				2019-01-15 14:26:36,985 INFO  - Starting the service with id 'mint-agent-boot'

		2.3.3.종료
			서비스 시작 후 서비스 종료 명령
				{IIPAGENT_HOME}\bin\mint-agent-boot.exe stop
			*윈도우즈 서비스관리화면에서 종료해되 됨
			실행 예)
				C:\Software\iipagent>bin\mint-agent-boot.exe stop
				2019-01-15 14:27:07,080 INFO  - Stopping the service with id 'mint-agent-boot'

		2.3.4.상태확인
			서비스 상태를 확인한다.
				{IIPAGENT_HOME}\bin\mint-agent-boot.exe status
			실행 예)
				C:\Software\iipagent>mint-agent-boot.exe status
				Stopped

		2.3.5.삭제
			{IIPAGENT_HOME}\bin\mint-agent-boot.exe uninstall
			실행 예)
				C:\Software\iipagent>mint-agent-boot.exe uninstall
				2019-01-15 14:30:26,360 INFO  - Uninstalling the service with id 'mint-agent-boot'


