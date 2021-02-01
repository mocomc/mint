/*****************************************************************************
 * Program Name : mint.rest.js
 * Description
 *   - mint 서비스에 관련된 REST Service URL 을 관리한다
 *
 *   - Access 방법
 *     mint.rest.{함수명};
 *     mint.rest.getServiceUrl(p1);
 *
 ****************************************************************************/
var mint_rest = function() {

};

mint_rest.services = {
	 'REST-S01-CO-00-00-003' : '/co/login?method=POST&isXY=true'										//로그인
	,'REST-S02-CO-00-00-003' : '/co/logout?method=POST&isXY=true'                                   //로그아웃
	,'REST-R01-CO-01-00-000' : '/co/healthcheck?method=POST'											//HealthCheck
	,'REST-R01-CO-01-00-001' : '/co/systems?method=GET'													//팝업-시스템 조회
	,'REST-R01-CO-01-00-002' : '/co/servers?method=GET'													//팝업-서버 조회
	,'REST-R01-CO-01-00-003' : '/co/business?method=GET'													//팝업-업무 조회
	,'REST-R01-CO-01-00-004' : '/co/users?method=GET'														//팝업-사용자 조회
	,'REST-R01-CO-01-00-006' : '/co/interface-patterns?method=GET'									//팝업-인터페이스 패턴 조회
	,'REST-R01-CO-01-00-008' : '/co/interfaces?method=GET'												//팝업-인터페이스 조회
	,'REST-R04-CO-01-00-008' : '/co/interfaces/interface/exist?method=GET'							//팝업-인터페이스 조회(삼성전기용)
	,'REST-R02-CO-01-00-008' : '/co/interfaces/{interfaceId}?method=GET'							//팝업-인터페이스 상세 조회
	,'REST-R03-CO-01-00-008' : '/co/interfaces/{interfaceId}/requirements?method=GET'			//팝업-인터페이스 상세 요건 리스트
	,'REST-R04-CO-02-00-001' : '/co/systems/root?method=GET'											//팝업-시스템 조회(검색조건)
	,'REST-R05-CO-02-00-001' : '/co/systems/child?method=GET'											//팝업-시스템 조회(검색조건)
	,'REST-R01-CO-02-00-000' : '/co/basic-info/{userId}/?method=GET&isCompress=true'			//코드-공통코드 리스트(코드값전체)
	,'REST-R02-CO-02-00-000' : '/co/search-info?method=GET&isCompress=true'						//코드-검색 조건 정보
	,'REST-R03-CO-02-00-000' : '/co/login-count-info?method=GET'										//로그인사용자현황조회
	,'REST-R01-CO-02-00-001' : '/co/cds/systems?method=GET'											//코드-시스템 코드 검색
	,'REST-R01-CO-02-00-002' : '/co/cds/servers?method=GET'												//코드-서버 코드 검색
	,'REST-R01-CO-02-00-003' : '/co/cds/businesses?method=GET'										//코드-업무 코드 검색
	,'REST-R01-CO-02-00-012' : '/co/cds/organizations?method=GET'										//기관 조회
	,'REST-R02-CO-02-00-012' : '/co/organization/systems/{organizationId}?method=GET'			//기관에 따른 시스템 조회
	,'REST-R03-CO-02-00-012' : '/co/system/organization/{systemId}?method=GET'					//시스템에 대한 기관  조회   //deprecated ...
	,'REST-R04-CO-02-00-012' : '/co/system-organization?method=GET'								//시스템 기관 맵핑정보 조회
	,'REST-R02-CO-02-00-003' : '/co/cds/businesses/root?method=GET'									//업무 코드 검색 - 루트
	,'REST-R03-CO-02-00-003' : '/co/cds/businesses/{parentBusinessId}/child?method=GET'		//업무 코드 검색 - 자식
	,'REST-R01-CO-02-00-004' : '/co/cds/interfaces?method=GET'											//코드-인터페이스 코드 조회
	,'REST-R01-CO-02-00-005' : '/co/cds/common-codes/{level1}/{level2}?method=GET'			//코드-공통코드 조회
	,'REST-R01-CO-02-00-006' : '/co/channels?method=GET'													//코드-채널검색
	,'REST-R01-CO-02-00-007' : '/co/services?method=GET'													//코드-서비스검색
	,'REST-R01-CO-02-00-008' : '/co/interface-tags?method=GET'											//코드-TAG검색
	,'REST-R01-CO-02-00-009' : '/co/cds/requirements?method=GET'										//코드-요건검색

	,'REST-R05-CO-01-00-008' : '/co/interfaces/asis/{channelId}?method=GET'						//AS-IS 인터페이스 리스트 조회(인터페이스맵핑키)
	,'REST-R01-CO-02-00-011' : '/co/problems?method=GET'													//팝업-장애유형 - lv1
	,'REST-R01-CO-02-00-013' : '/co/dataaccessroles?method=GET'										//코드-데이터접근권한검색
	,'REST-R01-CO-03-00-001' : '/co/menus/?method=GET'											        //메뉴
	,'REST-R01-CO-03-00-002' : '/co/menus/path/{appCd}?method=GET'								//Application Cd에 따른 화면 매뉴 경로 가져오기.

	,'REST-C01-CO-02-00-001' : '/co/cds/common-codes?method=POST'								//공통코드 등록
	,'REST-U01-CO-02-00-001' : '/co/cds/common-codes?method=PUT'									//공통코드 변경
	,'REST-D01-CO-02-00-001' : '/co/cds/common-codes?method=DELETE'								//공통코드 삭제
	,'REST-C01-SU-01-07'	 : '/su/environments?method=POST'    											//포털환경설정  등록.


	//인터페이스
	,'REST-R01-AN-01-00'     : '/an/requirements?method=GET&isCompress=true'						//요건 리스트 조회
	,'REST-R02-AN-01-00'     : '/an/requirements/{requirementId}?method=GET'						//요건 상세 조회
	,'REST-R99-AN-01-00'     : '/an/requirements/temp-save/{requirementId}?method=GET'		//요건 상세 조회(임시저장 조회)
	,'REST-R06-AN-01-00'     : '/an/requirements/{requirementId}/versions?method=GET'			//요건 히스토리 리스트
	,'REST-R07-AN-01-00'     : '/an/requirements/{requirementId}/versions/{version}?method=GET'		//요건 히스토리 상세
	,'REST-R08-AN-01-00'     : '/an/requirements/by-page?method=GET&isCompress=true'			        //요건 리스트 조회(페이지기능버전)
	,'REST-R09-AN-01-00'	 : '/an/requirements/detail?method=GET&isCompress=true'			        	//요건 리스트 상세리스트조회(입력된 요건ID 리스트에 대한)


	,'REST-C01-AN-01-00'	 : '/an/requirements?method=POST'												//요건 등록
	,'REST-U01-AN-01-00'	 : '/an/requirements/{requirementId}?method=PUT'							//요건 변경
	,'REST-U02-AN-01-00'	 : '/an/requirements/{requirementId}/{status}?method=PUT'			//요건 상태 변경(심의요청, 심의확정, 결재요청, 결재확정등..) - 사용 X(REST-C01-CO-02-00-010 대체)
	,'REST-U05-AN-01-00'	 : '/an/requirements/{requirementId}/restore?method=PUT'				//요건 복원
	,'REST-D01-AN-01-00'	 : '/an/requirements/{requirementId}?method=DELETE'					//요건 삭제

	//라이프사이클-결재
	,'REST-R01-CO-02-00-010' : '/co/approval/{approvalItemType}/{approvalItemId}?method=GET'			//결재내역조회(가장최근)-현재 CommentView 용도로 사용됨
	,'REST-R02-CO-02-00-010' : '/co/approval/link/select?method=GET'									//결재 링크 키 발번
	,'REST-R03-CO-02-00-010' : '/co/approval/link?method=GET'											//결재링크정보조회
	,'REST-R04-CO-02-00-010' : '/co/approval/users?method=GET'											//결재자리스트조회(왼쪽 리스트)
	,'REST-R05-CO-02-00-010' : '/co/approval/line/users?method=GET'									//결재자리스트조회(오른쪽 리스트)
	,'REST-C01-CO-02-00-010' : '/co/approval?method=POST'												//결제요청(등록/삭제/이행)
	,'REST-C02-CO-02-00-010' : '/co/approval-and-update?method=POST'								//결재요청(수정)
	,'REST-C03-CO-02-00-010' : '/co/approval/{apply}?method=POST'									//결재처리(승인/반려)
	,'REST-U03-AN-01-00'	 : '/an/requirements/{requirementId}/{status}/{date}?method=PUT'	//요건개발테스트이행상태변경
	,'REST-U04-AN-01-00'	 : '/an/requirements/{requirementId}/{status}/cancel?method=PUT'	//요건개발테스트이행상태취소

	//라이프사이클-TO-DO LIST
	,'REST-R01-AN-04-00-001' : '/an/requirements/todo-list/approval-target/{userId}/?method=GET'		//TO-DO LIST(결재대상)
	,'REST-R02-AN-04-00-001' : '/an/requirements/todo-list/approval-ing/{userId}/?method=GET'			//TO-DO LIST(결재진행)
	,'REST-R03-AN-04-00-001' : '/an/requirements/todo-list/approval-reject/{userId}/?method=GET'		//TO-DO LIST(결재반려)
	,'REST-R04-AN-04-00-001' : '/an/requirements/todo-list/development-list/{userId}/?method=GET'		//TO-DO LIST(개발대상)
	,'REST-R05-AN-04-00-001' : '/an/requirements/todo-list/test-list/{userId}/?method=GET'					//TO-DO LIST(테스트대상)
	,'REST-R06-AN-04-00-001' : '/an/requirements/todo-list/real-list/{userId}/?method=GET'					//TO-DO LIST(이행대상)

	,'REST-R11-AN-01-00'	 : '/an/requirements/dtm-list/{userId}/?method=GET'						//TO-DO LIST(인터페이스 개발/테스트/이행)
	,'REST-R04-AN-01-00'	 : '/an/requirements/change-list/{userId}/?method=GET'					//TO-DO LIST(인터페이스 결재 처리현황)
	//,'REST-R05-AN-01-00'	 : '/an/requirements/{requirementId}/approval-info?method=GET'		//요건 승인 내역 조회 서비스

	//개발진척현황
	,'REST-R01-AN-03-01'     : '/su/development-status?method=GET'										//개발진척 현황 조회
	,'REST-R02-AN-03-01'     : '/su/development-status-requirements?method=GET'						//개발진척 현황 - 상태별 요건 리스트 조회
	,'REST-R01-SU-08-01-000' : '/su/statistics/subject-status/channel?method=GET'					//개발진척 현황 조회 - 솔루션별.
	,'REST-R01-SU-08-02-000' : '/su/statistics/subject-status/system?method=GET'						//개발진척 현황 조회 - 시스템별.
	,'REST-R01-SU-08-03-000' : '/su/statistics/subject-status/resource?method=GET'					//개발진척 현황 조회 - 리소스별.
	,'REST-R01-SU-08-04-000' : '/su/statistics/subject-status/user?method=GET'							//개발진척 현황 조회 - 사용자별.
	,'REST-R01-SU-08-05-000' : '/su/statistics/subject-status/detail?method=GET'						//개발진척 현황 조회 - 사용자별.
	,'REST-R01-SU-08-01-601' : '/su/statistics/subject-status/channel/dev?method=GET'				//개발진척 현황 조회 - 채널별


	// 진척현황
	,'REST-R01-SU-05-05-000' : '/su/statistics/development-status/daily/channel?method=GET'			//일솔루션개발진척률조회
	,'REST-R02-SU-05-05-000' : '/su/statistics/development-status/daily/resource?method=GET'			//리소스별일개발진척률조회
	,'REST-R03-SU-05-05-000' : '/su/statistics/development-status/daily/system?method=GET'			//일시스템별개발진척률조회
	,'REST-R04-SU-05-05-000' : '/su/statistics/development-status/daily/list?method=GET'					//일별개발진척률리스트조회

	// main
	,'REST-R01-CO-00-00-001' : '/co/mains/delays?method=GET'													//메인-오류 지연현황
	,'REST-R02-CO-00-00-001' : '/co/mains/proceeds/{searchCnt}?method=GET'								//메인-전일/금일 처리 현황
	,'REST-R03-CO-00-00-001' : '/co/mains/systems/{searchCnt}/{searchSystem}?method=GET'		//메인-업무 시스템 별 현황
	,'REST-R04-CO-00-00-001' : '/co/mains/interfaces/{searchCnt}/{searchInterface}?method=GET'	//메인-인터페이스별 현황
	,'REST-R05-CO-00-00-001' : '/co/mains/devProceed?method=GET'											//메인-개발 진행 현황
	,'REST-R06-CO-00-00-001' : '/co/mains/errors?method=GET'													//메인-장애 발생 현황

	// interface tracking
	,'REST-R01-OP-01-01'	 : '/op/tracking/logs?method=GET'											//트래킹 - 트래킹 목록 조회
	,'REST-R02-OP-01-01'	 : '/op/tracking/logs/{logKey1}/{logKey2}?method=GET'				//트래킹 - 트래킹 상세 정보 조회
	,'REST-R03-OP-01-01'	 : '/op/sap-po/payload?method=GET'										//트래킹 - 트래킹 에러 정보 조회(SAP-PO)
	,'REST-R04-OP-01-01'	 : '/op/tracking/systemInfo/{logKey1}/{logKey2}?method=GET'		//트래킹 - 트래킹 시스템 조회
	,'REST-R05-OP-01-01'	 : '/op/tracking/diagrams/{logKey1}/{logKey2}?method=GET'		//트래킹 - 트래킹 다이어그램 조회
	// interface tracking - dev
	,'REST-R01-OP-01-01-DEV' : '/op/dev/tracking/logs?method=GET'											//트래킹(dev) - 트래킹 목록 조회
	,'REST-R02-OP-01-01-DEV' : '/op/dev/tracking/logs/{logKey1}/{logKey2}?method=GET'			//트래킹(dev) - 트래킹 상세 정보 조회
	,'REST-R03-OP-01-01-DEV' : '/op/dev/sap-po/payload?method=GET'										//트래킹(dev) - 트래킹 에러 정보 조회(SAP-PO)
	,'REST-R04-OP-01-01-DEV' : '/op/dev/tracking/systemInfo/{logKey1}/{logKey2}?method=GET'	//트래킹(dev) - 트래킹 시스템 조회
	,'REST-R05-OP-01-01-DEV' : '/op/dev/tracking/diagrams/{logKey1}/{logKey2}?method=GET'		//트래킹(dev) - 트래킹 다이어그램 조회
	// interface tracking - qa
	,'REST-R01-OP-01-01-QA'  : '/op/qa/tracking/logs?method=GET'											//트래킹(qa) - 트래킹 목록 조회
	,'REST-R02-OP-01-01-QA'  : '/op/qa/tracking/logs/{logKey1}/{logKey2}?method=GET'				//트래킹(qa) - 트래킹 상세 정보 조회
	,'REST-R03-OP-01-01-QA'  : '/op/qa/sap-po/payload?method=GET'										//트래킹(qa) - 트래킹 에러 정보 조회(SAP-PO)
	,'REST-R04-OP-01-01-QA'  : '/op/qa/tracking/systemInfo/{logKey1}/{logKey2}?method=GET'		//트래킹(qa) - 트래킹 시스템 조회
	,'REST-R05-OP-01-01-QA'  : '/op/qa/tracking/diagrams/{logKey1}/{logKey2}?method=GET'		//트래킹(qa) - 트래킹 다이어그램 조회
	// interface tracking - ver
	,'REST-R01-OP-01-01-VER' : '/op/ver/tracking/logs?method=GET'											//트래킹(ver) - 트래킹 목록 조회
	,'REST-R02-OP-01-01-VER' : '/op/ver/tracking/logs/{logKey1}/{logKey2}?method=GET'				//트래킹(ver) - 트래킹 상세 정보 조회
	,'REST-R03-OP-01-01-VER' : '/op/ver/sap-po/payload?method=GET'										//트래킹(ver) - 트래킹 에러 정보 조회(SAP-PO)
	,'REST-R04-OP-01-01-VER' : '/op/ver/tracking/systemInfo/{logKey1}/{logKey2}?method=GET'	//트래킹(ver) - 트래킹 시스템 조회
	,'REST-R05-OP-01-01-VER' : '/op/ver/tracking/diagrams/{logKey1}/{logKey2}?method=GET'		//트래킹(ver) - 트래킹 다이어그램 조회
	// interface tracking - gssp
	,'REST-R01-OP-01-01-GSSP' : '/op/gssp/tracking/logs?method=GET'										//트래킹(gssp) - 트래킹 목록 조회
	,'REST-R02-OP-01-01-GSSP' : '/op/gssp/tracking/logs/{logKey1}/{logKey2}/{logKey3}/{logKey4}/{logKey5}/{logKey6}?method=GET' //트래킹 - 트래킹 상세 정보 조회

	// interface tracking - nh
	,'REST-R01-OP-01-01-NH' : '/op/nh/tracking/logs?method=GET'										//트래킹(NH) - 트래킹 목록 조회
	,'REST-R02-OP-01-01-NH' : '/op/nh/tracking/logs/{logKey1}/{logKey2}/{logKey3}/{logKey4}?method=GET' //트래킹(NH) - 트래킹 상세 정보 조회
	,'REST-R03-OP-01-01-NH' : '/op/nh/tracking/error-logs/{logKey1}/{logKey2}?method=GET'						//트래킹(NH) - 트래킹 에러 상세 목록 조회
	,'REST-R05-OP-01-01-NH'	: '/op/nh/tracking/diagrams/{logKey1}/{logKey2}/{logKey3}/{logKey4}?method=GET'		//트래킹 - 트래킹 다이어그램 조회
	,'REST-R99-OP-01-01-NH' : '/op/nh/tracking/logs/export-excel?method=POST'										//트래킹(NH) - 트래킹 목록 조회

	// interface tracking - kab
	,'REST-R02-OP-01-01-KAB' : '/op/kab/tracking/logs/{logKey1}/{logKey2}/{isDecrypt}?method=GET'				//트래킹(한국감정원) - 트래킹 상세 정보 조회
	,'REST-R01-OP-01-01-KAB' : '/op/kab/message/logs?method=GET'			// 온라인전문이력(한국감정원) - 온라인전문이력 목록 조회
	,'REST-R03-OP-01-01-KAB' : '/op/kab/message/logs/{logKey1}/{logKey2}/{isDecrypt}?method=GET'			// 온라인전문이력(한국감정원) - 온라인전문이력 상세 정보 조회

	// interface tracking - IIP 4.0
	,'REST-R01-OP-01-10'	 : '/op/tracking/log/list?method=GET'											//트래킹(IIP4.0) - 트래킹 목록 조회
	,'REST-R02-OP-01-10'	 : '/op/tracking/log/detail/{integrationId}/{trackingDate}/{orgHostId}?method=GET'				//트래킹(IIP4.0) - 트래킹 상세 정보 조회

	// statistics
	,'REST-R01-SU-03-02'	 : '/su/statistics-period?method=GET'										//집계 - 기간별 집계(@deprecated)
	,'REST-R02-SU-03-02'	 : '/su/statistics-type?method=GET'											//집계 - 유형별 집계
	,'REST-R03-SU-03-02'	 : '/su/statistics-period-compare?method=GET'							//집계 - 기간별 비교
	,'REST-R04-SU-03-02'	 : '/su/statistics-type-detail?method=GET'								//집계 - 유형별 집계(상세) - 인터페이스 목록
	,'REST-R05-SU-03-02'	 : '/su/statistics-period/summary?method=GET'							//집계 - 기간별 집계
	,'REST-R06-SU-03-02'	 : '/su/statistics-period/list?method=GET'									//집계 - 기간별 집계
	,'REST-R07-SU-03-02'	 : '/su/statistics/interface-count/monthly?method=GET'				//집계 - 월별인터페이스변화량
    ,'REST-R08-SU-03-02'	 : '/su/statistics-period/totals?method=GET'								//집계 - 인터페이스별 집계(신규 M-D 포함)
    ,'REST-R09-SU-03-02'	 : '/su/statistics-org/totals?method=GET'									//집계 - 기관별 집계(신규 M-D 포함)
    ,'REST-R10-SU-03-02'	 : '/su/statistics-org/year/totals?method=GET'							//집계 - 기관별- 연도 집계(신규 M-D 포함)
    ,'REST-R11-SU-03-02'	 : '/su/statistics-org/month/totals?method=GET'						//집계 - 기관별- 월 집계(신규 M-D 포함)
    ,'REST-R12-SU-03-02'	 : '/su/statistics/organization/interface/totals?method=GET'			//집계 - 기관-인터페이스별- 월 집계(신규 M-D 포함)

	// statistics - IIP 4.0
    ,'REST-R01-SU-03-12'	 : '/su/statistics/time-period?method=GET'										//집계 - 기간별 집계
    ,'REST-R02-SU-03-12'	 : '/su/statistics/time-period-detail?method=GET'								//집계 - 기간별 집계(상세)

    ,'REST-R08-SU-03-02-001' : '/su/statistics-period/totals/hdins/hour?method=GET'			//집계 - HDINS 기간(시간별)
    ,'REST-R08-SU-03-02-002' : '/su/statistics-period/totals/hdins/day?method=GET'			//집계 - HDINS 기간(일별)
    ,'REST-R08-SU-03-02-003' : '/su/statistics-period/totals/kics?method=GET'					//집계 - KICS 기간(년별통계)
    ,'SU0810R01' : '/su/statistics/tracking/interface-record-cnt?method=GET'						//집계 - 레코드건수

	,'REST-R01-SU-03-04'	 : '/su/statistics-failure-rate?method=GET'								//집계 - 장애발생율 집계
	,'REST-R03-SU-03-04'	 : '/su/statistics-failure-interface-list?method=GET'					//집계 - 장애발생 인터페이스 목록(상세)
	,'REST-R02-SU-03-04'	 : '/su/statistics-failure-type?method=GET'								//집계 - 장애유형별 집계

	// 장애 대장
	//,'REST-R01-OP-03-02'     : '/op/problems?method=GET'												//장애관리 - 장애 목록 조회
	//,'REST-R02-OP-03-02'     : '/op/problems/todo-list/{userId}?method=GET'					//장애관리 - To-Do List 장애 목록 조회
	//,'REST-C01-OP-03-02'     : '/op/problems?method=POST'											//장애관리 - 장애 등록
	//,'REST-U01-OP-03-02'     : '/op/problems/{problemId}?method=POST'							//장애관리 - 장애 수정
	//,'REST-D01-OP-03-02'	 : '/op/problems/{problemId}?method=DELETE'						//장애관리 - 장애 삭제
	//,'REST-R01-OP-03-03'     : '/op/problems/{problemId}?method=GET'							//장애관리 - 장애 상세 조회

	,'REST-R11-OP-03-02'     : '/op/problems-ledger?method=GET'										//오류/장애 관리 - 장애 목록 조회
	,'REST-R12-OP-03-02'	 : '/op/register-management-problems?method=GET'					//오류/장애 관리 - 등록 관리 조회
	,'REST-R13-OP-03-02'     : '/op/problems-ledger/detail?method=GET'								//오류/장애 관리 - 장애 상세 조회
	,'REST-R14-OP-03-02'     : '/op/problems-ledger/todo-list/{userId}/?method=GET'			//오류/장애 관리 - To-Do List 장애 목록 조회
	,'REST-R15-OP-03-02'     : '/op/problems-ledger/tracking-detail?method=GET'					//오류/장애 관리 - 트레킹 상세 - 인터페이스 오류/장애 이력 조회
	,'REST-R16-OP-03-02'	 : '/op/problems-template?method=GET'									//오류/장애 관리 - 장애 내용 템플릿 목록.
	,'REST-R17-OP-03-02'	 : '/op/problems-template-detail?method=GET'							//오류/장애 관리 - 장애 내용 템플릿 상세.
	,'REST-R18-OP-03-02'     : '/op/problems-ledger-count?method=GET'								//오류/장애 관리 - 장애 목록 카운트(조치중/조치완료)
	,'REST-R19-OP-03-02'     : '/op/register-management-problems-count?method=GET'			//오류/장애 관리 - 장애 목록 카운트(미조치)
	,'REST-R20-OP-03-02'     : '/op/problems-ledger/list?method=GET'									//오류/장애 관리 - 장애 목록 조회(미조치/조치중/조치완료)
	,'REST-R21-OP-03-02'     : '/op/problems-ledger/list-count?method=GET'						//오류/장애 관리 - 장애 목록 카운트(미조치/조치중/조치완료)

	,'REST-C11-OP-03-02'     : '/op/problems-ledger?method=POST'									//오류/장애 관리 - 장애 등록
	,'REST-D11-OP-03-02'	 : '/op/problems-ledger/{problemId}?method=DELETE'					//오류/장애 관리 - 장애 삭제
	,'REST-U11-OP-03-02'     : '/op/problems-ledger/{problemId}?method=POST'					//오류/장애 관리 - 장애 수정
	,'REST-U12-OP-03-02'     : '/op/problems-template-crud?method=POST'							//오류/장애 관리 - 장애 내용 템플릿 CRUD.

	,'REST-R01-OP-03-04'     : '/op/notran-pos-history?method=GET'							//점포/POS통신이력 조회


	//협업
	,'REST-R01-SU-02-00'	 : '/su/notification-categorys?method=GET'								//헙업 - 공지FAQ 카테고리 조회
	,'REST-C01-SU-02-01' 	 : '/su/notices?method=POST'													//헙업 - 공지사항 - 공지사항 등록
	,'REST-R01-SU-02-01'	 : '/su/notices?method=GET'													//협업 - 공지사항 - 공지사항 조회(List)
	,'REST-R02-SU-02-01'	 : '/su/notices/{noticeId}?method=GET'									//협업 - 공지사항 - 공지사항 상세 조회
	,'REST-U01-SU-02-01'	 : '/su/notices/{noticeId}?method=PUT'									//협업 - 공지사항 - 공지사항 수정
	,'REST-D01-SU-02-01'	 : '/su/notices/{noticeId}?method=DELETE'								//협업 - 공지사항 - 공지사항 삭제
	,'REST-C01-SU-02-02' 	 : '/su/faqs?method=POST'														//헙업 - FAQ - FAQ 등록
	,'REST-R01-SU-02-02'	 : '/su/faqs?method=GET'														//협업 - FAQ - FAQ 조회(List)
	,'REST-R02-SU-02-02'	 : '/su/faqs/{faqId}?method=GET'											//협업 - FAQ - FAQ 상세 조회
	,'REST-U01-SU-02-02'	 : '/su/faqs/{faqId}?method=PUT'											//협업 - FAQ - FAQ 수정
	,'REST-D01-SU-02-02'	 : '/su/faqs/{faqId}?method=DELETE'										//협업 - FAQ - FAQ 삭제
	,'REST-R01-SU-04-01-001' : '/ba/job/results?method=GET'											//배치JOB 처리 결과 조회
	,'REST-R01-SU-99-99-002' : '/en/frontlogs?method=GET'												//프론트로그조회
	,'REST-R01-SU-99-99-999' : '/su/version?method=GET'												//IIP배포버전정보조회(20170922 추가, whoana)

	//KPI
	,'REST-R01-SU-03-05-000' : '/su/kpi/expectation/problems?method=GET'									//메인 - 장애사전예방 아이템 조회
	,'REST-R01-SU-03-05-001' : '/su/kpi/no-reg-interfaces?method=GET'											//KPI - 인터페이스 관리지표 - 미등록 인터페이스 조회
	,'REST-U01-SU-03-05-001' : '/su/kpi/no-reg-interfaces/{checkDate}/{interfaceId}?method=PUT'	//KPI - 인터페이스 관리지표 - 미등록 인터페이스 수정
	,'REST-R02-SU-03-05-001' : '/su/kpi/reg-interfaces-count-rate?method=GET'								//KPI - 인터페이스 관리지표 - 인터페이스 등록율 , KPI 현황 - 인터페이스 등록율
	,'REST-R01-SU-03-05-002' : '/su/kpi/no-use-interfaces?method=GET'										//KPI - 인터페이스 관리지표 - 인터페이스 사용율 상세(미사용 인터페이스 조회)
	,'REST-R02-SU-03-05-002' : '/su/kpi/use-interfaces-count-rate-channel?method=GET'					//KPI - 인터페이스 관리지표 - 인터페이스 사용율 , KPI 현황 - 인터페이스 사용율
	,'REST-R01-SU-03-05-003' : '/su/kpi/reused-interfaces?method=GET'											//KPI - 인터페이스 관리지표 - 재사용 인터페이스 조회(상세)
	,'REST-R02-SU-03-05-003' : '/su/kpi/reused-interfaces-count-rate?method=GET'							//KPI - 인터페이스 관리지표 - 인터페이스 재사용율, KPI 현황 - 인터페이스 재사용율
	,'REST-R01-SU-03-05-004' : '/su/kpi/status-requriement-comply-rate?method=GET'						//KPI - 인터페이스 관리지표 - 납기준수율, KPI 현황 - 납기준수율 조회
	,'REST-R02-SU-03-05-004' : '/su/kpi/requriement-not-comply-list?method=GET'							//KPI - 인터페이스 관리지표 - 납기준수율 상세 (납기 지연(미준수) 요건 리스트 조회)
	,'REST-R02-SU-03-00-001' : '/su/kpi/status-interfaces-count-rate-channel?method=GET'				//KPI - KPI 현황 - 인터페이스 처리 현황(일/월)
	,'REST-R03-SU-03-00-001' : '/su/kpi/over-usage-count-cpu?method=GET'									//KPI - KPI 현황 - CPU Over Usage 건수
	,'REST-R04-SU-03-00-001' : '/su/kpi/over-usage-count-memory?method=GET'							//KPI - KPI 현황 - Memory Over Usage 건수
	,'REST-R05-SU-03-00-001' : '/su/kpi/interface-rate?method=GET'												//KPI - KPI 현황 - 인터페이스 비율
	,'REST-R06-SU-03-00-001' : '/su/kpi/data-size?method=GET'													//KPI - KPI 현황 - 데이터 사이즈
	,'REST-R01-SU-03-06-001' : '/su/kpi/server-info?method=GET'													//KPI - 운영관리지표 - CPU/MEMORY 현황 : 서버 정보 조회
	,'REST-R02-SU-03-06-001' : '/su/kpi/over-usage-list?method=GET'											//KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(그리드)
	,'REST-R03-SU-03-06-001' : '/su/kpi/over-usage-list-hour?method=GET'										//KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(차트)


	//Help
	,'REST-R01-SU-99-99-001' : '/co/cds/apps?method=GET'									//App 리스트 검색
	,'REST-R02-SU-02-15-000' : '/su/help?method=GET'										//도움말 리스트 조회
	,'REST-R01-SU-02-15-000' : '/su/help/{appId}/{helpId}/{langId}?method=GET'				//도움말조회
	,'REST-C01-SU-02-15-000' : '/su/help?method=POST'										//도움말저장
	,'REST-U01-SU-02-15-000' : '/su/help?method=PUT'										//도움말수정
	,'REST-D01-SU-02-15-000' : '/su/help/{appId}/{helpId}/{langId}?method=DELETE'			//도움말삭제
	,'REST-R01-SU-02-16-000' : '/su/tooltips/{appId}/{tooltipId}?method=GET'				//툴팁조회
	,'REST-C01-SU-02-16-000' : '/su/tooltips?method=POST'									//툴팁조회

	//퇴사자 관리
	,'REST-R01-AN-02-19-000' : '/an/interface-users?method=GET'										//인터페이스 유저 리스트 조회
	,'REST-R02-AN-02-19-000' : '/an/interface-users/{userId}/interfaces?method=GET'				//담당자 인터페이스 리스트 조회
	,'REST-U01-AN-02-19-000' : '/an/interface-users/{userId}/interfaces/move?method=POST'	//인터페이스 담당자 업무이관

	//인터페이스 담당자 관리
	,'REST-R03-AN-02-19-000' : '/an/interface-users/interfaces?method=GET'							//이관대상 인터페이스 리스트 조회
	,'REST-U02-AN-02-19-000' : '/an/interface-users/add?method=POST'								//인터페이스 담당자 일괄 추가
	,'REST-U03-AN-02-19-000' : '/an/interface-users/delete?method=POST'								//인터페이스 담당자 일괄 삭제

	//사용자 관리
	,'REST-R02-SU-01-01-000' : '/su/management/user-roles?method=GET'								//사용자롤리스트조회(이름으로)
	,'REST-R03-SU-01-01-000' : '/su/management/user-roles/channel?method=GET'					//솔루션담당자조회
	,'REST-R04-SU-01-01-000' : '/su/management/user-roles/approval?method=GET'					//결재자리스트조회
	,'REST-U01-SU-01-01-000' : '/su/management/user-roles/channel?method=POST'				//솔루션담당자 등록,수정,삭제
	,'REST-U02-SU-01-01-000' : '/su/management/user-roles/approval?method=POST'				//결재담당자 등록,삭제
	,'REST-U03-SU-01-01-000' : '/su/management/user-roles/approval-group?method=POST'		//결재 그룹 등록,수정,삭제
	,'REST-U04-SU-01-01-000' : '/su/management/user-roles/approval-authorities?method=POST'	//결재자 리스트 등록/수정/삭제

	,'REST-TEST-EXPORT-EXCEL' 	: '/su/export-excel?method=POST'										//엑셀 파일 다운로드

	//Screen-Label 조회
	,'REST-R01-SU-02-17-000' : '/su/management/app/messages?method=GET'						//LangId 별로 Label 정보 조회
	,'REST-R02-SU-02-17-000' : '/su/management/app/messages-by-lang?method=GET'				//All Label 정보 조회

	//시스템 관리.
	,'REST-R05-IM-01-01'	 : '/im/systems/root?method=GET'												//시스템(그룹) ROOT 리스트 조회.
	,'REST-R06-IM-01-01'	 : '/im/systems/{parentSystemId}/child?method=GET'						//시스템(그룹) Child 리스트 조회.
	,'REST-C01-IM-01-01'	 : '/im/systems?method=POST'													//시스템(그룹) 등록.
	,'REST-C02-IM-01-01'	 : '/im/systems/path?method=POST'												//시스템그룹 <-> 시스템 맵핑 등록.
	,'REST-U01-IM-01-01'	 : '/im/systems?method=PUT'														//시스템(그룹) 수정.
	,'REST-D01-IM-01-01'	 : '/im/systems/{systemId}?method=DELETE'									//시스템(그룹) 삭제.
	,'REST-D02-IM-01-01'	 : '/im/systems/{systemId}/path?method=DELETE'							//시스템그룹 <-> 시스템 맵핑 해제.

	,'REST-R03-IM-01-01'	 : '/im/systems/treemodel?method=GET'										//트리모델
	,'REST-R02-IM-01-01'	 : '/im/systems/{systemId}?method=GET'               						//시스템  상세 조회
	,'REST-C03-IM-01-01'	 : '/im/systems/systemgroup?method=POST'           						//시스템 정봐와 시스템 패스 동시 처리 	  2017-01-10 bill

	//B2Bi
	,'REST-R01-IM-01-04-001' : '/su/b2bi/interface/metadata/list?method=GET'						//B2Bi Interface MetaData 조회.
	,'REST-R02-IM-01-04-001' : '/su/b2bi/interface/metadata/matching-count?method=GET'		//B2Bi Interface MetaData 중복 체크.
	,'REST-C01-IM-01-04-001' : '/su/b2bi/interface/metadata/insert?method=POST'					//B2Bi Interface MetaData 등록.
	,'REST-U01-IM-01-04-001' : '/su/b2bi/interface/metadata/update?method=POST'				//B2Bi Interface MetaData 수정.

	,'REST-R01-SU-03-07'     : '/su/b2bi/search-info?method=GET'                  						//B2Bi 조회조건 리스트 및 연관 검색.
	,'REST-R01-SU-03-07-001' : '/su/b2bi/state/main?method=GET'                  						//B2Bi 현황판.
	,'REST-R01-SU-03-07-002' : '/su/b2bi/state/docnm?method=GET'                  					//B2Bi 문서별 현황.
	,'REST-R01-SU-03-07-003' : '/su/b2bi/period-search?method=GET'                  					//B2Bi 기간별 조회.


	,'REST-R01-AN-01-00-008' : '/ut/excel/masterinfo/search?method=GET'                  			//Excel Upload - MasterInfo search
	,'REST-R02-AN-01-00-008' : '/ut/excel/detailinfo/search?method=GET'                  				//Excel Upload - DetailInfo search
	,'REST-D01-AN-01-00-008' : '/ut/excel/delete?method=GET'                  							//Excel Upload - Delete


	,'REST-R01-SU-04-03-001' : '/su/potal/statistics/login-count/1?method=GET'						// Potal 통계 - 접속자 이력 조회 #1
	,'REST-R02-SU-04-03-001' : '/su/potal/statistics/login-count/2?method=GET'						// Potal 통계 - 접속자 이력 조회 #2
	,'REST-R03-SU-04-03-001' : '/su/potal/statistics/login-count/detail?method=GET'				// Potal 통계 - 접속자 이력 조회 상세

	//서버 관리.
	,'REST-R01-IM-01-02'	 : '/im/servers?method=GET'								//서버 	 리스트 조회.
	,'REST-R02-IM-01-02'	 : '/im/servers/{serverId}?method=GET'				//서버 상세 조회.
	,'REST-C01-IM-01-02'	 : '/im/servers?method=POST'								//서버 등록.
	,'REST-U01-IM-01-02'	 : '/im/servers?method=PUT'								//서버 수정.
	,'REST-D01-IM-01-02'	 : '/im/servers/{serverId}?method=DELETE'			//서버 삭제.

	//사용자 관리
	,'REST-R01-IM-01-05'	 : '/im/users?method=GET'									//사용자 조회
	,'REST-R02-IM-01-05'	 : '/im/users/{userId}/?method=GET'					//사용자 상세조회
	,'REST-R03-IM-01-05'	 : '/im/users/{userId}/checkid?method=GET'			//사용자 ID 중복체크
	,'REST-C01-IM-01-05'	 : '/im/users?method=POST'								//사용자 등록.
	,'REST-U01-IM-01-05'	 : '/im/users?method=PUT'									//사용자 수정
	,'REST-D01-IM-01-05'	 : '/im/users/{userId}/?method=DELETE'				//사용자 삭제.

	,'REST-R04-IM-01-05'	 : '/im/roles?method=GET'									//Role 조회



	//REST 서비스 관리
	,'REST-R01-SU-10-02'	 : '/su/restservices?method=GET'												//서비스 조회
	,'REST-R02-SU-10-02'	 : '/su/restservices/{serviceId}?method=GET'               					//서비스 상세조회
	,'REST-C01-SU-10-02'	 : '/su/restservices?method=POST'               								//서비스 등록.
	,'REST-U01-SU-10-02'	 : '/su/restservices?method=PUT' 		              							//서비스 수정
	,'REST-D01-SU-10-02'	 : '/su/restservices/{serviceId}?method=DELETE' 		              		//서비스 삭제.

	//기관 관리.
	//,'REST-R05-IM-01-06'	 : '/im/organizations/root?method=GET'               							//@depecated 시스템(그룹) ROOT 리스트 조회.
	//,'REST-R06-IM-01-06'	 : '/im/organizations/{parentOrganizationId}/child?method=GET'		//@depecated 시스템(그룹) Child 리스트 조회.
	,'REST-C01-IM-01-06'	 : '/im/organizations?method=POST'               							    //기관(그룹) 등록.
	,'REST-C02-IM-01-06'	 : '/im/organizations/path?method=POST'               						//기관그룹 <-> 기관 맵핑 등록.
	,'REST-U01-IM-01-06'	 : '/im/organizations?method=PUT' 		              							//기관(그룹) 수정.
	,'REST-D01-IM-01-06'	 : '/im/organizations/{organizationId}?method=DELETE' 		            //기관(그룹) 삭제.
	,'REST-D02-IM-01-06'	 : '/im/organizations/{organizationId}/path?method=DELETE'            //기관그룹 <-> 기관 맵핑 해제.

	,'REST-R03-IM-01-06'	 : '/im/organizations/treemodel?method=GET'				                    //트리모델
	,'REST-R02-IM-01-06'	 : '/im/organizations/{organizationId}?method=GET'               			//기관  상세 조회
	,'REST-R04-IM-01-06'	 : '/im/organizations/system/treemodel/?method=GET'				        //트리모델(기관 - 시스템)

	// 업무 관리.
	,'REST-C01-IM-01-03'	 : '/im/business?method=POST'               							        //업무(그룹) 등록.
	,'REST-C02-IM-01-03'	 : '/im/business/path?method=POST'               							    //업무그룹 <-> 업무 맵핑 등록.
	,'REST-U01-IM-01-03'	 : '/im/business?method=PUT' 		              							    //업무(그룹) 수정.
	,'REST-D01-IM-01-03'	 : '/im/business/{businessId}?method=DELETE' 		              			//업무(그룹) 삭제.
	,'REST-D02-IM-01-03'	 : '/im/business/{businessId}/path?method=DELETE'                      	//업무그룹 <-> 업무 맵핑 해제.

	,'REST-R03-IM-01-03'	 : '/im/business/treemodel?method=GET'				                        //트리모델
	,'REST-R02-IM-01-03'	 : '/im/business/{businessId}?method=GET'               					//업무  상세 조회

	// 점포/pos 관리.
	,'REST-R01-IM-05-01'	 : '/im/stores?method=GET'               									//점포/POS 조회
	,'REST-C01-IM-05-01'	 : '/im/stores?method=POST'    											//점포/POS 모니터링여부 등록(설정).

	// 화면 라벨 관리
	,'REST-R01-SU-10-01'	 : '/su/labels?method=GET'               									//화면라벨 조회
	,'REST-R02-SU-10-01'	 : '/su/labels/{langId}/{labelId}?method=GET'               			//화면라벨 상세조회
	,'REST-C01-SU-10-01'	 : '/su/labels?method=POST'               								//화면라벨 등록.
	,'REST-U01-SU-10-01'	 : '/su/labels?method=PUT' 		              							//화면라벨 수정
	,'REST-D01-SU-10-01'	 : '/su/labels/{langId}/{labelId}?method=DELETE' 		            //화면라벨 삭제.

	// 데이타 셋 관리
	,'REST-R01-AN-05-01'	 : '/an/datasets?method=GET'					 						//데이타 셋 리스트 조회.
	,'REST-R02-AN-05-01'	 : '/an/datasets/{dataSetId}?method=GET'		 						//데이타 셋 조회.
	,'REST-C01-AN-05-01'	 : '/an/datasets?method=POST'					 						//데이타 셋 등록.
	,'REST-D01-AN-05-01'	 : '/an/datasets/{dataSetId}?method=DELETE'		 						//데이타 셋 삭제.
	,'REST-U01-AN-05-01'	 : '/an/datasets/?method=PUT'					 						//데이타 셋 수정.
	,'REST-S01-AN-05-01'	 : '/an/datasets/service/approval/{dataSetId}?method=PUT'				//데이타 사용 승인.
	,'REST-R09-AN-05-01'	 : '/an/datasets/meta-fields?method=GET'								//메타필드 검색.
	,'REST-S02-AN-05-01'	 : '/an/datasets/service/save-temporary?method=POST'					//데이터셋 임시저장
	,'REST-R08-AN-05-01'	 : '/an/datasets/temporary-list/{userId}?method=GET'					//데이터셋 임시저장 불러오기

	,'REST-R04-AN-05-01'	 : '/an/datasets/cds/reg-users?method=GET'								//검색조건조회-등록자리스트
	,'REST-R05-AN-05-01'	 : '/an/datasets/cds/interfaces?method=GET'								//검색조건조회-인터페이스리스트
	,'REST-R06-AN-05-01'	 : '/an/datasets/cds/nms?method=GET'									//검색조건조회-데이터셋코드리스트

	,'REST-R01-AN-06-00'	 : '/an/datasets/simple?method=GET'					 					//데이타 셋 리스트 조회 - simple
	,'REST-R02-AN-06-00'	 : '/an/datasets/simple/{dataSetId}?method=GET'		 					//데이타 셋 조회 - simple
	,'REST-R03-AN-06-00'	 : '/an/datasets/simple/metadata/list?method=GET'					 	//META 리스트 조회 - simple
	,'REST-R04-AN-06-00'	 : '/an/datasets/simple/metadata/check/{isCamel}?method=GET'			//META Check - simple
	,'REST-R05-AN-06-00'	 : '/an/datasets/simple/exist/dataSetCd/{dataSetCd}?method=GET'			//데이타 셋 Exist Check - simple
	,'REST-R06-AN-06-00'	 : '/an/datasets/simple/used/dataSetId/{dataSetId}?method=GET'			//데이타 셋 Used Check - simple
	,'REST-C01-AN-06-00'	 : '/an/datasets/simple?method=POST'					 				//데이타 셋 등록 - simple
	,'REST-U01-AN-06-00'	 : '/an/datasets/simple?method=PUT'					 					//데이타 셋 수정 - simple
	,'REST-D01-AN-06-00'	 : '/an/datasets/simple/{dataSetId}?method=DELETE'		 				//데이타 셋 삭제 - simple
	,'REST-F01-AN-06-00'	 : '/ut/excel/upload/layout?method=POST'		 				        //데이타 셋 Upload - simple


	,'REST-R00-AN-06-01'	 : '/an/datamaps/cds?method=GET'					 					//데이타 맵핑 검색조건조회 - simple
	,'REST-R01-AN-06-01'	 : '/an/datamaps/simple?method=GET'					 					//데이타 맵핑 리스트 조회 - simple
	,'REST-R02-AN-06-01'	 : '/an/datamaps/simple/{mapId}?method=GET'					 			//데이타 맵핑 조회 - simple
	,'REST-R03-AN-06-01'	 : '/an/datamaps/simple/exist/mapCd/{mapCd}?method=GET'					//데이타 맵핑 Exist Check - simple
	,'REST-R04-AN-06-01'	 : '/an/datamaps/simple/used/mapId/{mapId}?method=GET'					//데이타 맵핑 Used Check - simple
	,'REST-C01-AN-06-01'	 : '/an/datamaps/simple?method=POST'					 				//데이타 맵핑 등록 - simple
	,'REST-U01-AN-06-01'	 : '/an/datamaps/simple?method=PUT'					 					//데이타 맵핑 수정 - simple
	,'REST-D01-AN-06-01'	 : '/an/datamaps/simple/{mapId}?method=DELETE'		 					//데이타 맵핑 삭제 - simple

	,'REST-R01-AN-06-03'	 : '/an/datamaps/interface-map?method=POST'
	,'REST-R02-AN-06-03'	 : '/rt/models/interface/detail?method=GET'
	,'REST-C01-AN-06-03'	 : '/rt/models/msg/create?method=POST'
	,'REST-U01-AN-06-03'	 : '/rt/models/msgmap/update?method=PUT'


	// 채널 속성 관리
	,'REST-R01-SU-01-06'	 : '/su/channel/attributes?method=GET'    								//채널속성 조회
	,'REST-C01-SU-01-06'	 : '/su/channel/attributes?method=POST'    								//채널속성 등록.
	,'REST-U01-SU-01-06'	 : '/su/channel/attributes?method=PUT'        							//채널속성 수정
	,'REST-D01-SU-01-06'	 : '/su/channel/attributes/{channelId}/{attrId}?method=DELETE'  //채널속성 삭제.

	// 관심/주요배치 인터페이스 관리
	,'REST-R01-AN-02-01'     : '/an/requirements/principal/{typeCd}?method=GET'						//관심/주요배치 인터페이스 리스트 조회
	,'REST-C01-AN-02-01'     : '/an/requirements/principal/{typeCd}?method=POST'						//관심/주요배치 인터페이스 등록
	,'REST-U01-AN-02-01'     : '/an/requirements/principal/{interfaceId}/{typeCd}?method=PUT'		//관심/주요배치 인터페이스 수정
	,'REST-D01-AN-02-01'     : '/an/requirements/principal/{typeCd}?method=DELETE'					//관심/주요배치 인터페이스 삭제
	,'REST-U02-AN-02-01'     : '/an/requirements/principal/{typeCd}?method=PUT'						//관심/주요배치 인터페이스 일괄수정

	// 배포관리
	,'REST-C01-OP-04-01'     : '/op/deploy/interfaces?method=POST'											//인터페이스 배포(현대해상용)
	,'REST-R01-OP-04-01'     : '/op/deploy/interfaces?method=GET'											//인터페이스 배포이력조회(현대해상용)
	// 이관관리
	,'REST-R01-OP-04-02'     : '/op/transfer/interfaces?method=GET'											//인터페이스 이관이력조회(현대해상용)-삭제예정
	,'REST-S01-IM-04-03'     : '/im/export-interfaces?method=POST'											//인터페이스 내보내기 서버처리(현대해상)-170711
	,'REST-S02-IM-04-04'     : '/im/import-interfaces?method=POST'											//인터페이스 들여오기 서버처리(현대해상)-170711

	// 데이타접근권한
	,'REST-R01-SU-01-02'     : '/su/data-access/roles?method=GET'											//데이타접근권한 조회
	,'REST-C01-SU-01-02'     : '/su/data-access/roles?method=POST'											//데이타접근권한 등록
	,'REST-U01-SU-01-02'     : '/su/data-access/roles/{roleId}?method=PUT'								//데이타접근권한 수정
	,'REST-D01-SU-01-02'     : '/su/data-access/roles/{roleId}?method=DELETE'							//데이타접근권한 삭제
	,'REST-R02-SU-01-02'     : '/su/data-access/roles/{roleId}/users?method=GET'						//데이타접근권한-사용자 조회
	,'REST-C02-SU-01-02'     : '/su/data-access/roles/{roleId}/users?method=POST'						//데이타접근권한-사용자 등록
	,'REST-D02-SU-01-02'     : '/su/data-access/roles/{roleId}/users?method=DELETE'					//데이타접근권한-사용자 삭제
	,'REST-R10-SU-01-02'     : '/su/data-access/roles/interface/{interfaceId}?method=GET'			//데이타접근권한-인터페이스 조회
	,'REST-C10-SU-01-02'     : '/su/data-access/roles/interface/{interfaceId}?method=POST'			//데이타접근권한-인터페이스 등록
	,'REST-D10-SU-01-02'     : '/su/data-access/roles/interface/{interfaceId}?method=DELETE'		//데이타접근권한-인터페이스 삭제

	,'REST-C04-SU-01-02'     : '/su/data-access/roles/path?method=POST'									//데이타접근권한그룹 <-> 데이타접근권한 맵핑 등록.
	,'REST-U06-SU-01-02'     : '/su/data-access/roles?method=PUT'											//데이타접근권한(그룹) 수정
	,'REST-D04-SU-01-02'     : '/su/data-access/roles/{roleId}/path?method=DELETE'					//데이타접근권한그룹 <-> 데이타접근권한 맵핑 해제
	,'REST-R05-SU-01-02'     : '/su/data-access/roles/treemodel?method=GET'								//데이타접근권한 트리모델
	,'REST-R06-SU-01-02'     : '/su/data-access/roles/{roleId}?method=GET'								//데이타접근권한 상세 조회

	,'REST-R03-SU-01-02'     : '/su/roles?method=GET'														//기능권한 조회
	,'REST-C03-SU-01-02'     : '/su/roles?method=POST'													//기능권한 등록
	,'REST-U03-SU-01-02'     : '/su/roles/{roleId}?method=PUT'											//기능권한 수정
	,'REST-D03-SU-01-02'     : '/su/roles/{roleId}?method=DELETE'									//기능권한 삭제
	,'REST-R04-SU-01-02'     : '/su/roles/{roleId}?method=GET'											//기능권한 상세조회
	,'REST-U04-SU-01-02'     : '/su/roles/{roleId}/menus?method=PUT'								//기능권한 - 매뉴 수정
	,'REST-U05-SU-01-02'     : '/su/roles/{roleId}/apps?method=PUT'

	//프로그램 관리
	,'REST-R01-SU-01-05'	 : '/su/applications?method=GET'    											//프로그램  조회
	,'REST-C01-SU-01-05'	 : '/su/applications?method=POST'    										//프로그램  등록.
	,'REST-U01-SU-01-05'	 : '/su/applications?method=PUT'        										//프로그램  수정
	,'REST-D01-SU-01-05'	 : '/su/applications/{appId}?method=DELETE'        					//프로그램  삭제.

	//매뉴 관리
	,'REST-R01-SU-01-04'	 : '/su/menus?method=GET'    												//매뉴  조회
	,'REST-C01-SU-01-04'	 : '/su/menus?method=POST'    												//매뉴  등록.
	,'REST-U01-SU-01-04'	 : '/su/menus?method=PUT'        											//매뉴  수정
	,'REST-D01-SU-01-04'	 : '/su/menus/{menuId}?method=DELETE'        							//매뉴  삭제.
	,'REST-C02-SU-01-04'	 : '/su/menus/path?method=POST'	 	       								//매뉴그룹 <-> 매뉴 맵핑 등록.
	,'REST-D02-SU-01-04'	 : '/su/menus/{menuId}/path?method=DELETE'        					//매뉴그룹 <-> 매뉴 맵핑 삭제.
	,'REST-R03-SU-01-04'	 : '/su/menus/treemodel?method=GET'        								//매뉴 트리모델.
	,'REST-R02-SU-01-04'	 : '/su/menus/{menuId}?method=GET'        								//매뉴 상세 조회.
	,'REST-R04-SU-01-04'	 : '/su/menus/notuse-treemodel?method=GET'        					//매뉴 미적용 메뉴.
	,'REST-D03-SU-01-04'	 : '/su/menus/{menuId}/treemodel?method=DELETE'    				//매뉴그룹 <-> 매뉴 맵핑 삭제.
	,'REST-U02-SU-01-04'	 : '/su/menus/apps?method=PUT'        										//매뉴-프로그램  수정
	//IIP Agent 관리
	,'REST-R01-IM-02-01'	 : '/im/agents?method=GET'    													//IIPAgent  조회
	,'REST-C01-IM-02-01'	 : '/im/agents?method=POST'    													//IIPAgent  등록.
	,'REST-U01-IM-02-01'	 : '/im/agents?method=PUT'        												//IIPAgent  수정
	,'REST-D01-IM-02-01'	 : '/im/agents/{agentId}?method=DELETE'        							//IIPAgent  삭제.
	,'REST-R02-IM-02-01'	 : '/im/agents/{agentId}/monitors?method=GET'    							//IIPAgent-모니터항목  조회
	,'REST-C02-IM-02-01'	 : '/im/agents/{agentId}/monitors?method=POST'    						//IIPAgent-모니터항목  등록.
	,'REST-U02-IM-02-01'	 : '/im/agents/{agentId}/monitors?method=PUT'        						//IIPAgent-모니터항목  수정
	,'REST-D02-IM-02-01'	 : '/im/agents/{agentId}/monitors?method=DELETE'        				//IIPAgent-모니터항목  삭제
	,'REST-R03-IM-02-01'	 : '/im/agents/{agentId}/monitors/resource?method=GET'				//IIPAgent-모니터항목(리소스)  조회		.
	,'REST-R04-IM-02-01'	 : '/im/agents/{agentId}/monitors/process?method=GET'					//IIPAgent-모니터항목(프로세스)  조회		.
	,'REST-R05-IM-02-01'	 : '/im/agents/{agentId}/monitors/qmgr?method=GET'						//IIPAgent-모니터항목(큐관리자)  조회		.
	,'REST-R06-IM-02-01'	 : '/im/agents/{agentId}/monitors/{qmgrId}/channel?method=GET'	//IIPAgent-모니터항목(채널)  조회		.
	,'REST-R07-IM-02-01'	 : '/im/agents/{agentId}/monitors/{qmgrId}/queue?method=GET'		//IIPAgent-모니터항목(큐)  조회		.
	,'REST-R08-IM-02-01'	 : '/im/agents/treemodel?method=GET'											//IIPAgent-모니터항목(큐)  조회		.
	,'REST-R09-IM-02-01'	 : '/im/agents/{agentId}?method=GET'        									//IIPAgent  상세조회.
	,'REST-R13-IM-02-01'	 : '/im/agents/monitors/qmgrs?method=GET'        								//IIPAgent  모든 큐관리자
	,'REST-R15-IM-02-01'	 : '/im/agents/{agentId}/monitors/qmgr-system?method=GET'						//IIPAgent-모니터항목(큐관리자 : SystemMapping)  조회		.
	// IIP Agent 배포.
	,'REST-U01-OP-02-02'     : '/op/agents/services/cmds?method=POST'										//에이전트배포명령일괄발행
	,'REST-U02-OP-02-02'     : '/op/agents/services/cmds/{agentId}?method=POST'								//에이전트배포명령일괄발행 / 개별 Agent
	,'REST-R01-OP-02-02'     : '/op/agents/services/cmds/results?method=GET'								//에이전트배포명령결과 조회

	,'REST-R10-IM-02-01'	 : '/im/agents/solutions?method=GET'    										//EAI Agent  조회
	,'REST-C03-IM-02-01'	 : '/im/agents/solutions?method=POST'    										//EAI Agent  등록.
	,'REST-U03-IM-02-01'	 : '/im/agents/solutions?method=PUT'        									//EAI Agent  수정
	,'REST-D03-IM-02-01'	 : '/im/agents/solutions/{agentId}?method=DELETE'        				//EAI Agent  삭제.
	,'REST-R11-IM-02-01'	 : '/im/agents/solutions/{agentId}/brokers?method=GET'    				//EAI Agent-Runner  조회
	,'REST-C04-IM-02-01'	 : '/im/agents/solutions/{agentId}/brokers?method=POST'    			//EAI Agent-Runner  등록.
	,'REST-U04-IM-02-01'	 : '/im/agents/solutions/{agentId}/brokers?method=PUT'        			//EAI Agent-Runner  수정
	,'REST-D04-IM-02-01'	 : '/im/agents/solutions/{agentId}/brokers?method=DELETE'     		//EAI Agent-Runner  수정
	,'REST-R12-IM-02-01'	 : '/im/agents/solutions/treemodel?method=GET'    							//EAI Agent TreeModel



	//Dashboard
	,'REST-R01-OP-02-00'     : '/op/dashboard/throughput/daily?method=GET'							//처리건수(금일)
	,'REST-R02-OP-02-00'     : '/op/dashboard/top/error-list?method=GET'								//에러건수(금일) - TOP num
	,'REST-R03-OP-02-00'     : '/op/dashboard/top/delay-list?method=GET'								//지연건수 - TOP num
	,'REST-R04-OP-02-00'     : '/op/dashboard/realtime/total-count?method=GET'						//실시간 처리건수 - 전체
	,'REST-R05-OP-02-00'     : '/op/dashboard/realtime/favorite-count?method=GET'					//실시간 처리건수 - 관심인터페이스
	,'REST-R06-OP-02-00'     : '/op/dashboard/state/important-batch-count?method=GET'			//주요배치처리상태 - 카운트
	,'REST-R07-OP-02-00'     : '/op/dashboard/state/important-batch-list?method=GET'				//주요배치처리상태 - 리스트
	,'REST-R08-OP-02-00'     : '/op/dashboard/stats/daily?method=GET'						            //처리량 추이 - 전일/금일
	,'REST-R09-OP-02-00'     : '/op/dashboard/stats/monthly?method=GET'						        //처리량 추이 - 최근3개월
	,'REST-R10-OP-02-00'     : '/op/dashboard/stats/yearly?method=GET'						        //처리량 추이 - 최근3년

	//Dashboard(ISM)- HDINS
	,'REST-R01-OP-02-00-001' : '/op/dashboard/ism/throughput/daily?method=GET'					//ISM 처리건수(금일)
	,'REST-R02-OP-02-00-001' : '/op/dashboard/ism/top/error-list?method=GET'						//ISM 에러건수(금일) - TOP num
	,'REST-R03-OP-02-00-001' : '/op/dashboard/ism/top/delay-list?method=GET'						//ISM 지연건수 - TOP num
	,'REST-R04-OP-02-00-001' : '/op/dashboard/ism/realtime/total-count?method=GET'				//ISM 실시간 처리건수 - 전체
	,'REST-R05-OP-02-00-001' : '/op/dashboard/ism/realtime/favorite-count?method=GET'			//ISM 실시간 처리건수
	,'REST-R08-OP-02-00-001' : '/op/dashboard/ism/stats/daily?method=GET'						    //ISM 처리량 추이 - 전일/금일
	,'REST-R09-OP-02-00-001' : '/op/dashboard/ism/stats/monthly?method=GET'						//ISM 처리량 추이 - 최근3개월
	,'REST-R10-OP-02-00-001' : '/op/dashboard/ism/stats/interface-used?method=GET'				//ISM IF 사용현황.

	//Dashboard - GSSP
	,'REST-R03-OP-02-00-002' : '/op/dashboard/gssp/top/delay-list?method=GET'						//GSSP 지연건수 - TOP num
	,'REST-R11-OP-02-00-002' : '/op/dashboard/gssp/dead-pos-count?method=GET'					//통신불가포스 상태 - 카운트
	,'REST-R12-OP-02-00-002' : '/op/dashboard/gssp/dead-pos-list?method=GET'						//통신불가포스 LIST
	,'REST-R13-OP-02-00-002' : '/op/dashboard/gssp/notran-pos-count?method=GET'				//거래미발생포스 상태 - 카운트
	,'REST-R14-OP-02-00-002' : '/op/dashboard/gssp/notran-pos-list?method=GET'					//통신불가포스 LIST
	,'REST-R15-OP-02-00-002' : '/op/dashboard/gssp/pos-totoal-tran-count?method=GET'			//GSSP TR로그처리현황
	,'REST-R16-OP-02-00-002' : '/op/dashboard/gssp/realtime/favorite-count?method=GET'			//GSSP 실시간 처리건수 - 관심인터페이스

	,'REST-R01-OP-02-00-KAB' : '/op/dashboard/kab/timeout-count?method=GET'					//KAB TIME OUT - 카운트
	,'REST-R11-OP-02-00-KAB' : '/op/dashboard/kab/connect-status?method=GET'				//KAB 연계기관 통신상태 LIST

	//Dashboard All limit
	,'REST-R01-OP-02-01'	 : '/op/dashboard/system-resource/cpu?method=GET'						//시스템자원(CPU)임계치 초과목록
	,'REST-R02-OP-02-01'	 : '/op/dashboard/system-resource/disk?method=GET'						//시스템자원(DISK)임계치 초과목록
	,'REST-R03-OP-02-01'	 : '/op/dashboard/system-resource/memory?method=GET'				//시스템자원(MEMORY)임계치 초과목록
	,'REST-R04-OP-02-01'	 : '/op/dashboard/system-resource/process?method=GET'				//시스템자원(PROCESS)임계치 초과목록
	,'REST-R05-OP-02-01'	 : '/op/dashboard/system-resource/qmgr?method=GET'					//시스템자원(QMGR)임계치 초과목록
	,'REST-R06-OP-02-01'	 : '/op/dashboard/system-resource/queue?method=GET'					//시스템자원(QUEUE)임계치 초과목록
	,'REST-R07-OP-02-01'	 : '/op/dashboard/system-resource/channel?method=GET'				//시스템자원(CHANNEL)임계치 초과목록
	,'REST-R08-OP-02-01'	 : '/op/dashboard/system-resource/agent?method=GET'					//EAI 엔진(AGENT)임계치 초과목록
	,'REST-R09-OP-02-01'	 : '/op/dashboard/system-resource/broker?method=GET'					//EAI 엔진(BROKER)임계치 초과목록
	,'REST-R10-OP-02-01'	 : '/op/dashboard/engine/limits?method=GET'									//DASHBOAD LIMIT   조회
	,'REST-R11-OP-02-01'	 : '/op/dashboard/system-resource/iipagent?method=GET'				//IIP Agent 임계치 초과목록
	,'REST-R12-OP-02-01'	 : '/op/dashboard/system-resource/trigger?method=GET'					//시스템자원(TRIGGER)임계치 초과목록

	//포탈환경설정
	,'REST-R01-SU-01-07'	 : '/su/environments?method=GET'    											//포털환경설정  조회
	,'REST-C01-SU-01-07'	 : '/su/environments?method=POST'    											//포털환경설정  등록.
	,'REST-U01-SU-01-07'	 : '/su/environments?method=PUT'        										//포털환경설정  수정
	,'REST-D01-SU-01-07'	 : '/su/environments?method=DELETE'        									//포털환경설정  삭제.

	//보드목록관리
	,'REST-R01-SU-01-10'	 : '/su/management/dashboard?method=GET'    								//보드관리목록  조회.
	,'REST-C01-SU-01-10'	 : '/su/management/dashboard?method=POST'    							//보드관리목록  등록.
	,'REST-U01-SU-01-10'	 : '/su/management/dashboard?method=PUT'	    							//보드관리목록  수정.
	,'REST-D01-SU-01-10'	 : '/su/management/dashboard?method=DELETE'   							//보드관리목록  삭제.

	// ISM 통계
	,'REST-R08-SU-03-02-601'     : '/su/statistics-period/totals/hdins/hour?method=GET'           //ISM25  집계 시간별
	,'REST-R08-SU-03-02-602'     : '/su/statistics-period/totals/hdins/day?method=GET'            //ISM25  집게 일별

	// ISM  로그
	,'REST-R01-OP-01-01-601'     : '/op/ism/custom/log/online?method=POST'                         //ISM25  로그 리스트
	,'REST-R01-OP-01-01-602'     : '/op/ism/log/online/detail?method=POST'                           	//ISM25  로그 리스트 상세
	,'REST-R01-OP-01-01-603'     : '/op/ism/log/batch?method=POST'                                 	//ISM25  로그 리스트(배치 처리결과)
	,'REST-R01-OP-01-01-604'     : '/op/ism/log/batch/detail?method=POST'                           	//ISM25  로그 리스트 상세(배치 처리결과 상세)
	,'REST-R01-OP-01-01-605'     : '/op/ism/log/online/message?method=POST'                        //ISM25  로그 리스트  (온라인 처리결과 전문보기 팝업)
	,'REST-R01-OP-01-01-606'     : '/op/ism/log/online/stacktrace?method=POST'                     //ISM25  로그 리스트  (온라인 처리결과 StackTrace 팝엉)
	,'REST-R06-OP-01-01-600'     : '/op/ism/log/modelId?method=GET'                           			//ISM25  로그 리스트  (온라인 처리결과 모델ID)

	// 스케줄 조회
	,'REST-R01-OP-05-01'     : '/op/schedules/site/kics?method=GET'										//스케줄조회

	// 연계경로관리
	,'REST-R01-AN-02-03'     : '/an/requirements/nodes/paths?method=GET'							//연계경로관리

	//인터페이스 속성 관리
	,'REST-R01-SU-01-08'	 : '/su/interface/attributes?method=GET'    									//인터페이스속성 조회
	,'REST-C01-SU-01-08'	 : '/su/interface/attributes?method=POST'    								//인터페이스속성 등록.
	,'REST-U01-SU-01-08'	 : '/su/interface/attributes?method=PUT'        								//인터페이스속성 수정
	,'REST-D01-SU-01-08'	 : '/su/interface/attributes/{attrId}?method=DELETE'        				//인터페이스속성 삭제.

	//배치스케줄 관리
	,'REST-R01-SU-01-03'	 : '/su/batchApplications?method=GET'    								//배치프로그램  조회
	,'REST-C01-SU-01-03'	 : '/su/batchApplications?method=POST'    								//배치프로그램  등록.
	,'REST-U01-SU-01-03'	 : '/su/batchApplications?method=PUT'        							//배치프로그램  수정
	,'REST-D01-SU-01-03'	 : '/su/batchApplications/{jobId}/{scheduleId}?method=DELETE'    		//배치프로그램  삭제.

	,'REST-R99-CO-99-00'	 : '/co/widget/config/list?method=POST'    	    //Widget Config 조회
	,'REST-R99-CO-99-01'	 : '/co/widget/WS0040/01?method=POST'    		//WS004001(시스템별거래현황 대상시스템조회)
	,'REST-R99-CO-99-02'	 : '/co/widget/WS0042/01?method=POST'    		//WS004201(WMQ종합상황판 대상시스템조회)
	,'REST-R99-CO-99-03'	 : '/co/widget/WS0042/51?method=POST'    		//WS004251(WMQ종함상황판 큐매니져알림리스트)
	,'REST-R99-CO-99-04'	 : '/co/widget/WS0042/52?method=POST'    		//WS004252(WMQ종함상황판 큐알림리스트)
	,'REST-R99-CO-99-05'	 : '/co/widget/WS0042/53?method=POST'    		//WS004253(WMQ종함상황판 채널알림리스트)

	,'REST-R99-CO-99-06'	 : '/co/widget/systems/treemodel?method=POST'   //System Tree Model(WMQ Object Monitor)
	,'REST-R99-CO-99-07'	 : '/co/widget/servers/treemodel?method=POST'   //Server Tree Model(WMQ Object Monitor)

	,'REST-R99-CO-99-08'	 : '/co/widget/personalization/insert?method=POST'      //personalization save(WMQ Object Monitor)
	,'REST-R99-CO-99-09'	 : '/co/widget/personalization/update?method=POST'      //personalization update(WMQ Object Monitor)
	,'REST-R99-CO-99-10'	 : '/co/widget/personalization/delete?method=POST'      //personalization delete(WMQ Object Monitor)
	,'REST-R99-CO-99-11'	 : '/co/widget/personalization/list/owner?method=POST'  //personalization list-my(WMQ Object Monitor)
	,'REST-R99-CO-99-12'	 : '/co/widget/personalization/list/shared?method=POST' //personalization list-shared(WMQ Object Monitor)
	,'REST-R99-CO-99-13'	 : '/co/widget/personalization/load?method=POST'        //personalization load(WMQ Object Monitor)

	// 버전관리
	,'REST-R01-IM-02-02'	 : '/im/adapters?method=GET'												//버전관리 	 리스트 조회.

	//종함상황판 - 시스템 : 우선순위 맴핑
	,'REST-R14-IM-02-01'	 : '/im/system/priority/?method=GET'    								//시스템 : 큐관리자 맴핑  조회
	,'REST-C05-IM-02-01'	 : '/im/system/priority/?method=POST'    								//시스템 : 큐관리자 맴핑  등록.


	//인터페이스 Test Call
	,'REST-C01-IM-02-03'	 : '/im/interface-tests?method=POST'   									//NH 인터페이스 요청
	,'REST-R01-IM-02-03'	 : '/im/interface-tests/{testDate}?method=GET'    						//시스템 : 큐관리자 맴핑  등록.
	,'REST-R02-IM-02-03'	 : '/im/interface-system-tests/{testDate}/{testId}/{systemId}?method=GET'		//시스템 : 큐관리자 맴핑  등록.

	// 이벤트 알림
	,'REST-R01-IM-02-05'	 : '/im/sms-events?method=GET'												//이벤트 내역 조회
	,'REST-R01-IM-02-04'	 : '/im/sms-events/notis?method=GET'										//이벤트 통제 조회
	,'REST-U01-IM-02-04'	 : '/im/sms-events/notis?method=PUT'										//이벤트 통제 조회
	,'REST-U02-IM-02-04'	 : '/im/sms-events/env?method=POST'											//이벤트 통제 포털 설정

	//보안
	,'REST-R01-CO-04-03'     : '/co/security/login/histories/{userId}?method=GET'      	    //이전로그인정보조회
	,'REST-R02-CO-04-03'     : '/co/security/passwds/check/{userId}?method=GET&isXY=true'   //패스워드변경체크
	,'REST-U01-CO-04-03'     : '/co/security/passwds?method=PUT&isXY=true'   			    //패스워드변경


	// Config관리
	,'REST-R01-IM-02-06'	 : '/im/nh/config?method=GET'												//Config 정보리스트 조회
	,'REST-R02-IM-02-06'	 : '/im/nh/config/compare?method=GET'										//Config 정보 비교결과 조회
	,'REST-U01-IM-02-06'	 : '/im/nh/config?method=PUT'												//Config 정보 수정
	,'REST-C01-IM-02-06'	 : '/im/nh/config?method=POST'												//Config 정보 등록
	,'REST-D01-IM-02-06'	 : '/im/nh/config?method=DELETE'											//Config 정보 삭제
	,'REST-U02-IM-02-06'	 : '/im/nh/config/update?method=PUT'										//Config 정보 수정


	//연계채널 관리
	,'REST-R01-SU-01-11'	 : '/su/channels?method=GET'    											//연계채널  조회
	,'REST-C01-SU-01-11'	 : '/su/channels?method=POST'    											//연계채널  등록.
	,'REST-U01-SU-01-11'	 : '/su/channels?method=PUT'        										//연계채널  수정
	,'REST-D01-SU-01-11'	 : '/su/channels/{channelId}?method=DELETE'     			   				//연계채널  삭제.

	,'REST-R01-RT-01-00'	: '/rt/models/apps/attributes/list?method=GET'								// APP 모델속성 LIST 조회
	,'REST-R02-RT-01-00'	: '/rt/models/apps/attributes/map?method=GET'								// APP 모델속성 LIST 조회
	,'REST-R03-RT-01-00'	: '/rt/models/apps/types?method=GET'										// APP 모델유형 조회
	,'REST-C01-RT-01-00'	: '/rt/models/apps/attributes?method=POST'									// APP모델속성 등록
	,'REST-U01-RT-01-00'	: '/rt/models/apps/attributes?method=PUT'									// APP모델속성 수정
	,'REST-D01-RT-01-00'	: '/rt/models/apps/attributes?method=DELETE'								// APP모델속성 삭제

	,'REST-R01-RT-02-00'	: '/rt/models/interfaces?method=GET'										// 인터페이스 모델 LIST 조회
	,'REST-R01-RT-02-01'	: '/rt/models/deploys/actions?method=GET'									// 인터페이스 모델 배포
	,'REST-C01-RT-02-00'	: '/rt/models/interfaces?method=POST'										// 인터페이스 모델 등록
	,'REST-U01-RT-02-00'	: '/rt/models/interfaces?method=PUT'										// 인터페이스 모델 수정
	,'REST-D01-RT-02-00'	: '/rt/models/interfaces/{interfaceMid}?method=DELETE'						// 인터페이스 모델 삭제
	,'REST-R02-RT-02-00'	: '/rt/models/interfaces/{interfaceMid}?method=GET'							// 인터페이스 모델 구성 - 상세조회
	,'REST-R03-RT-02-00'	: '/rt/models/apps/new?method=GET'											// NEW APP모델 LIST

	,'REST-R02-AN-01-01'	: '/an/requirement-models/{requirementId}?method=GET'						// 요건+모델 상세 조회
	,'REST-R02-AN-01-02'	: '/an/requirement-models-by-integration-id?method=GET'						// 요건+모델 상세 조회
	,'REST-C01-AN-01-01'	: '/an/requirement-models?method=POST'										// 요건 + 인터페이스 모델 등록
	,'REST-U01-AN-01-01'	: '/an/requirement-models/{requirementId}?method=PUT'						// 요건 + 인터페이스 모델 수정
};

/**
 * 화면 아이디 기준의 서비스 호출 주소를 가져온다.<p>
 * @param restId {String} REST API ID
 * @param hasRouter {boolean} Restful Service 라우터 사용 구분 (기본값 옵션 : false), true 로 바꾸면 라우터를 사용하게 된다.(2020.10 추가)
 */

mint_rest.prototype.getServiceUrl = function(restId, hasRouter) {

	try {
		var url = !hasRouter ? mint_rest.services[restId] : "/routers/"+restId;

		if( !url ) {
		//	alert("서비스 아이디에 해당하는 호출 주소가 존재하지 않습니다. 확인하여 주십시오.");
			return null;
		}

		return mint.baseServiceUrl + url + (!hasRouter ? "&isTest=" : "?isTest=") + mint.isTestMode + "";


	} catch( e ){
		mint.common.errorLog(e, {"fnName" : "mint.rest.getServiceUrl"});
	}
};

/**
 * mint 객체에 추가한다.<p>
 */
mint.addConstructor(function() {
	try{
	    if (typeof mint.rest === "undefined") {
	        mint.rest = new mint_rest();
	    }
	} catch( e ) {

	}
});
