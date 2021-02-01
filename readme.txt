======================================================================================================
- project mint
------------------------------------------------------------------------------------------------------
- author : whoana
- begin  : 2014.01.01
- update : 2020.09.22
- update-contents :
	모듈 추가 및 기타 내용 현행화
------------------------------------------------------------------------------------------------------

1.프로젝트 개요
	1) 프로젝트명 : mint
	2) 개       요 : 인터페이스 룰처리 시스템 개발을 위한 모듈단위 프로젝트를
	                  springframework, maven 기반으로 구성한다.

2.프로젝트 사전 작업
	1) 자바설치 : 1.6 이상 설치
	2) maven 3.X 설치
	3) 개발툴 설치 :
		스프링 프레임워크3.2 및 빌드툴 maven 플러그인을 지원하는 eclipse
		예) STS 3.X(Spring Tool Suite)

3.모듈 구성
	[개발Directory]/mint						-->  1) 모듈 최상위 위치(maven parent POM)
					 /mint-batch			-->  2) 배치처리  모듈 PROJECT
					 /mint-common 			-->  3) 공통 모듈 PROJECT
					 /mint-database			-->  4) 데이터베이스 처리 모듈 PROJECT
					 /mint-front  			-->  5) IIP 화면 모듈 PROJECT
					 /mint-inhouse  		-->  6) 개별사이트 별 처리 모듈 PROJECT
					 /mint-solution-mi		-->  7) MI 에이전트 관련 모듈 PROJECT
					 /mint-migration		-->  8) 데이터이관관련 모듈 PROJECT
					 /mint-install-manager 	-->  9) IIP 초기 설치관리자 모듈 PROJECT
					 /mint-websocket		--> 10) 웹소켓 모듈 PROJECT
					 /mint-agent			--> 11) 에이전트 모듈 PROJECT 
					 /mint-agent-boot		--> 12) 에이전트 부트 모듈 PROJECT
					 /mint-install			--> 13) 배포파일 빌드 자동화 모듈 PROJECT (진행중)
					 /mint-batch-manager	--> 14) 배치 Stand-alone 모듈 PROJECT
					 /mint-fetch			--> 15) 소스패치 자동화 모듈 PROJECT (진행중)
					 /mint-endpoint			--> 16) 엔드포인트 서비스 모듈 PROJECT (진행중)
					 /mint-rest-client		--> 17) IIP External App Call 모듈 PROJECT (진행중)

4.모듈 빌드 방법
	1) mint-front-2.0.0 버전 빌드
		mvn -pl mint-common,mint-solution-mi,mint-database,mint-batch,mint-inhouse,mint-front clean package -P mint-2.0.0 -Dmaven.test.skip=true -X
		
	2) mint-front-3.0.0 버전 빌드
		mvn -pl mint-common,mint-solution-mi,mint-database,mint-batch,mint-inhouse,mint-websocket,mint-fetch,mint-endpoint,mint-front clean package -P mint-3.0.0 -Dmaven.test.skip=true -X

5.mint-3.0 업그레이드 내역
	1) 2018.04.13 시작
	   2018.05.04 기존 2.0.0 SVN 소스 브랜치 및 백업 (SVN/branches/backup-2.0)
	   2018.05.10 SVN 3.0.0 최종 커밋

	2) 주요 변경 내역
		-.자바 6 -> 7 (mint-3.0은 버전 자바버전 7.0 권장)
		-.주요 라이브러리 버전 변경
			springframework 3.2.4.RELEASE -> 4.3.15.RELEASE
		 	jackson 1.9 -> 2.9.5
		-.PUSH방식(websocket 이용) 프로토콜 추가

	3) 소스 변경 추가 내역
		-.메이븐 빌드 프로파일 추가
			버전 2.0.0 프로파일 mint-2.0.0
			버전 3.0.0 프로파일 mint-3.0.0
			프로파일 추가 POM 소스 리스트
				mint/pom.xml
				mint/mint-batch/pom.xml
				mint/mint-common/pom.xml
				mint/mint-database/pom.xml
				mint/mint-front/pom.xml
				mint/mint-inhouse/pom.xml
				mint/mint-solution-mi/pom.xml

		-.mint-front 리소스 분리 및 추가
			mint-front/src/main/resources 		<-- 기존 버전 2.0.0 리소스 위치
			mint-front/src/main/resources3.0.0 	<-- 신규 버전 3.0.0 리소스 위치
			분리 사유 : 버전별로 배포 리소스를 별도로 가져가야할 사유 발생함.
			내용 차이 :
					  버전 3.0.0의 config/mint-context.xml 파일에는 PUSH 기능을 위한 bean 정의 추가함.
					  버전 3.0.0의 versions/version.properties 내의 버전 정보를 3.0.0으로 변경함.

	4) 버전별 빌드 방법
		-.버전 2.0.0 빌드
			메이븐 빌드시 프로파일 mint-2.0.0 을 명시한다.
			빌드 후 최종 배포소스 위치
				mint/mint-front/target-version-2.0.0

		-.버전 3.0.0 빌드
			메이븐 빌드시 프로파일 mint-3.0.0 을 명시한다.
			빌드 후 최종 배포소스 위치
				mint/mint-front/target-version-3.0.0

		-.디폴트
			프로파일을 명시하지 않고 메이븐 빌드를 할경우 버전 3.0.0으로 빌드됨

		-.프로파일 적용 방법
			이클립스 메이븐 빌드 실행시 프로파일 명기 방법
				eclipse > Run > Run Configurations > Maven builds 창 > 기존 mint-front 빌드 설정 윈도우에서 Porfiles 상에 빌드 프로파일을 명기한다.

			명령 콘솔 상에서 메이븐 직접 실행시 프로파일 명기 방법
				버전 2.0.0 빌드
					mvn -pl mint-common,mint-solution-mi,mint-database,mint-batch,mint-inhouse,mint-front clean package -P mint-2.0.0 -Dmaven.test.skip=true -X
				버전 3.0.0 빌드
					mvn -pl mint-common,mint-solution-mi,mint-database,mint-batch,mint-inhouse,mint-websocket,mint-fetch,mint-endpoint,mint-front clean package -P mint-3.0.0 -Dmaven.test.skip=true -X

6.endpoint 부문 추가 개발 내역 
	1) mint-endpoint 모듈 추가 
		인터페이스 모델 배포 및 기타 외부 연계 구현 계층  
	2) mint-database 모듈 내용 추가                  
		pep.per.mint.database.service.rt
		pep.per.mint.database.mapper.rt
	3) mint-common 모듈 내용 추가
		pep.per.mint.common.data.basic.runtime
	4) mint-front
		pep.per.mint.front.controller.rt
		resources3.0/mint-context.xml 내에 mint-endpoint 모듈 scan 추가 
	5) 그 밖에 프론트 화면 개발 추가 
		
		
