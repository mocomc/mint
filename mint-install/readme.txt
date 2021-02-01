======================================================================================================
- project mint-install
------------------------------------------------------------------------------------------------------
- subject : 배포파일 빌드 수작업 배제를 위한 프로젝트
- author  : whoana
- begin   : 2019.01
------------------------------------------------------------------------------------------------------
1.개요
	IIP  서버 에이전트 설치파일 빌드 및 배포를 위한 프로젝트

2.디렉토리 설명
	2.1.OS별 리소스
		src/main/resources/agent-3.0.0/dependency/unix/bin	  --------------- 에이전트 실행 쉘 위치(유닉스용)
		src/main/resources/agent-3.0.0/dependency/windows/bin --------------- 에이전트 서비스등록 및 실행 쉘 위치(윈도우용)
		src/main/resources/agent-3.0.0/dependency/windows/bin/readme.txt ---- 에이전트 윈도우 서비스 등록 방법

	2.2.최종 배포버전 파일 생성 디렉토리
		mint-install/target/agent-3.0.0 ------------------------------------- 에이전트 3.0.0 설치 파일 ROOT
										/unix		------------------------- unix 버전
										/windows	------------------------- windows 버전
										/iipagent-uni-3.0.0.zip ------------- unix 배포압축파일
										/iipagent-win-3.0.0.zip ------------- windows 배포압축파일

		mint-install/target/server-3.0.0 ------------------------------------ 서버 3.0.0 설치 파일 ROOT
										/mint-front-product-3.0.0		----- 서버 3.0.0 파일
										/mint-front-product-3.0.0.war   ----- 서버 3.0.0 WAR 파일

		mint-install/target/agent-2.0.0 ------------------------------------- 에이전트 2.0.0 설치 파일 ROOT
										/unix		------------------------- unix 버전
										/windows	------------------------- windows 버전
										/iipagent-uni-2.0.0.zip ------------- unix 배포압축파일
										/iipagent-win-2.0.0.zip ------------- windows 배포압축파일

		mint-install/target/server-2.0.0 ------------------------------------ 서버 2.0.0 설치 파일 ROOT
										/mint-front-product-2.0.0		----- 서버 2.0.0 파일
										/mint-front-product-2.0.0.war   ----- 서버 2.0.0 WAR 파일

3.빌드 방법
	* 현재(201901) 버전 3.0.0 만 작업됨.
	3.1.mint-3.0.0 서버 배포 파일 및 mint-agent-3.0.0 배포 파일 빌드
		메이븐 명령 실행 (프로파일 : mint-3.0.0)
			mvn clean package -pl mint-common,mint-solution-mi,mint-database,mint-batch,mint-inhouse,mint-front,mint-agent,mint-agent-boot,mint-install -P mint-3.0.0

	3.2.mint-2.0.0 서버 배포 파일 및 mint-agent-2.0.0 배포 파일 빌드
		작성 예정(필요시)
