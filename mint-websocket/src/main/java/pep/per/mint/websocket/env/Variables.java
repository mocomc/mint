/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.env;

/**
 * <pre>
 * 오타방지용 상수
 * pep.per.mint.websocket.env
 * Variables.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 5.
 */
public final class Variables {

	// ---------------------------------------------------------------------
	// 오다방지용 서비스 SERVER CD
	// 미정의 상수는 신규건발생시 재사용하세요.
	// ---------------------------------------------------------------------
	/** 처리건수(기관별) */
	public static final String SERVICE_CD_WS0001 = "WS0001";// 처리건수(기관별)
	/** 오류인터페이스 조회 */
	public static final String SERVICE_CD_WS0002 = "WS0002";// 오류인터페이스 조회
	/** 지연인터페이스 조회 */
	public static final String SERVICE_CD_WS0003 = "WS0003";// 지연인터페이스 조회
	/** 실시간-처리현황(전체) */
	public static final String SERVICE_CD_WS0004 = "WS0004";// 실시간-처리현황(전체)
	/** 실시간-처리현황(관심) */
	public static final String SERVICE_CD_WS0005 = "WS0005";// 실시간-처리현황(관심)
	/** 처리량추이(전일/금일) */
	public static final String SERVICE_CD_WS0006 = "WS0006";// 처리량추이(전일/금일)
	/** 처리량추이(최근4개월) */
	public static final String SERVICE_CD_WS0007 = "WS0007";// 처리량추이(최근4개월)
	/** 처리량추이(최근4년) */
	public static final String SERVICE_CD_WS0008 = "WS0008";// 처리량추이(최근4년)
	/** 알람이벤트-CPU */
	public static final String SERVICE_CD_WS0009 = "WS0009";// 알람이벤트-CPU
	/** 알람이벤트-MEMORY */
	public static final String SERVICE_CD_WS0010 = "WS0010";// 알람이벤트-MEMORY
	/** 알람이벤트-DISK */
	public static final String SERVICE_CD_WS0011 = "WS0011";// 알람이벤트-DISK
	/** 알람이벤트-PROCESS */
	public static final String SERVICE_CD_WS0012 = "WS0012";// 알람이벤트-PROCESS
	/** 알람이벤트-AGENT */
	public static final String SERVICE_CD_WS0013 = "WS0013";// 알람이벤트-AGENT
	/** 알람이벤트-RUNNER */
	public static final String SERVICE_CD_WS0014 = "WS0014";// 알람이벤트-RUNNER
	/** 알람이벤트-큐매니저 */
	public static final String SERVICE_CD_WS0015 = "WS0015";// 알람이벤트-큐매니저
	/** 알람이벤트-채널 */
	public static final String SERVICE_CD_WS0016 = "WS0016";// 알람이벤트-채널
	/** 알람이벤트-큐깊이 */
	public static final String SERVICE_CD_WS0017 = "WS0017";// 알람이벤트-큐깊이
	/** 알람이벤트-주요배치 */
	public static final String SERVICE_CD_WS0018 = "WS0018";// 알람이벤트-주요배치
	/** 알람이벤트-IIP AGENT */
	public static final String SERVICE_CD_WS0019 = "WS0019";// 알람이벤트-IIP AGENT
	/** 에이전트정보리로드 요청 PUSH */
	public static final String SERVICE_CD_WS0020 = "WS0020";// 에이전트정보리로드 요청 PUSH
	/** 에이전트클래스리로드 요청 PUSH */
	public static final String SERVICE_CD_WS0021 = "WS0021";// 에이전트클래스리로드 요청 PUSH
	/** 파일배포 */
	public static final String SERVICE_CD_WS0022 = "WS0022";// 파일배포
	/** 파일조회 */
	public static final String SERVICE_CD_WS0023 = "WS0023";// 파일조회
	/** 에이전트 얼라이브 체크 요청 PUSH */
	public static final String SERVICE_CD_WS0024 = "WS0024";// 에이전트 얼라이브 체크 요청 PUSH
	/** 에이전트 로그인 서비스 */
	public static final String SERVICE_CD_WS0025 = "WS0025";// 에이전트 로그인 서비스
	/** 에이전트 로그아웃 서비스 */
	public static final String SERVICE_CD_WS0026 = "WS0026";// 에이전트 로그아웃 서비스
	/** CPU 리소스 로그 PUSH */
	public static final String SERVICE_CD_WS0027 = "WS0027";// CPU 리소스 로그 PUSH
	/** MEMORY 리소스 로그 PUSH */
	public static final String SERVICE_CD_WS0028 = "WS0028";// MEMORY 리소스 로그 PUSH
	/** DISK 리소스 로그 PUSH */
	public static final String SERVICE_CD_WS0029 = "WS0029";// DISK 리소스 로그 PUSH
	/** 프로세스 로그 PUSH */
	public static final String SERVICE_CD_WS0030 = "WS0030";// 프로세스 로그 PUSH
	/** 큐매니저로그 PUSH */
	public static final String SERVICE_CD_WS0031 = "WS0031";// 큐매니저로그 PUSH
	/** 콘솔정지명령요청 서비스 */
	public static final String SERVICE_CD_WS0032 = "WS0032";// 콘솔정지명령요청 서비스
	/** 콘솔얼라이브체크요청 서비스 */
	public static final String SERVICE_CD_WS0033 = "WS0033";// 콘솔얼라이브체크요청 서비스
	/** 시뮬레이터 실행 */
	public static final String SERVICE_CD_WS0034 = "WS0034";// 시뮬레이터 실행
	/** 데이터처리방식별 금일누적건수 */
	public static final String SERVICE_CD_WS0035 = "WS0035";// 데이터처리방식별 금일누적건수
	/** NH농협-초당처리건수 */
	public static final String SERVICE_CD_WS0037 = "WS0037";// NH농협-초당처리건수
	/** SMS전송로그 */
	public static final String SERVICE_CD_WS0038 = "WS0038";// SMS전송로그
	/** NH농협-오류인터페이스TOP-N */
	public static final String SERVICE_CD_WS0039 = "WS0039";// NH농협-오류인터페이스TOP-N
	/** NH농협-시스템별 거래현황 */
	public static final String SERVICE_CD_WS0040 = "WS0040";// NH농협-시스템별 거래현황
	/** 시스템별 거래현황-금일누적 */
	public static final String SERVICE_CD_WS0041 = "WS0041";// 시스템별 거래현황-금일누적
	/** WMQ종합상황판(GroupBy System) */
	public static final String SERVICE_CD_WS0042 = "WS0042";// WMQ종합상황판(GroupBy System)
	/** netstat */
	public static final String SERVICE_CD_WS0043 = "WS0043";// netstat 로그(감정원 최초요청)
	/** 미정의 */
	public static final String SERVICE_CD_WS0044 = "WS0044";// netstat 체크리스트 조회 (agent --> server)
	/** 미정의 */
	public static final String SERVICE_CD_WS0045 = "WS0045";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0047 = "WS0047";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0048 = "WS0048";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0049 = "WS0049";// 미정의
	/** NH농협-서버어뎁터정보PUSH(에이전트->IIP서버) */
	public static final String SERVICE_CD_WS0050 = "WS0050";// NH농협-서버어뎁터정보PUSH(에이전트->IIP서버)
	/** MQObject 모니터-큐매니저 리스트 가져오기  */
	public static final String SERVICE_CD_WS0051 = "WS0051";// MQObject 모니터-큐매니저 리스트 가져오기
	/** MQObject 모니터-큐 리스트 가져오기  */
	public static final String SERVICE_CD_WS0052 = "WS0052";// MQObject 모니터-큐 리스트 가져오기
	/** MQObject 모니터-큐프로퍼티 PUSH  */
	public static final String SERVICE_CD_WS0053 = "WS0053";// MQObject 모니터-큐프로퍼티 PUSH
	/** 농협 인터페이스 Config 정보 업데이트 */
	public static final String SERVICE_CD_WS0054 = "WS0054";// 농협 인터페이스 Config 정보 업데이트
	/** 미정의 */
	public static final String SERVICE_CD_WS0055 = "WS0055";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0056 = "WS0056";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0057 = "WS0057";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0058 = "WS0058";// 미정의
	/** 미정의 */
	public static final String SERVICE_CD_WS0059 = "WS0059";// 미정의

}
