/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.IIPAgent;
import pep.per.mint.common.data.basic.dashboard.EAIEngine;
import pep.per.mint.common.data.basic.dashboard.QmgrChannel;
import pep.per.mint.common.data.basic.dashboard.Queue;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;

/**
 *
 * @author ta
 *
 */
public interface DashboardPushMapper {

	/**
	 * 데이터 접근 권한 첫번째 depth 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDataAccessRoleFirstDepth(Map params);

	/**
	 * 데이터 접근 권한 depth 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDataAccessRoleDepth(Map params);

	/**
	 * WS0001 :: 처리건수(금일)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getWS0001(Map params);

	/**
	 * WS0002 :: 에러건수(금일) - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getWS0002(Map params);

	/**
	 * WS0003 :: 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getWS0003(Map params);

	/**
	 * WS0004 :: 실시간 처리건수 - 전체
	 * @param params
	 * @return
	 */
	public List<Map> getWS0004(Map params);

	/**
	 * WS0005 :: 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getWS0005(Map params);

	/**
	 * WS0006 :: 처리량추이(전일/금일)
	 * @param params
	 * @return
	 */
	public List<Map> getWS0006(Map params);

	/**
	 * WS0007 :: 처리량추이(최근4개월)
	 * @param params
	 * @return
	 */
	public Map getWS0007(Map params);

	/**
	 * WS0008 :: 처리량추이(최근4년)
	 * @param params
	 * @return
	 */
	public Map getWS0008(Map params);

	/**
	 * WS0009 :: CPU
	 * WS0010 :: MEMORY
	 * WS0011 :: DISK
	 * @param params
	 * @return
	 */
	public List<ServerResource> getResourceLimitList(Map params);

	/**
	 * WS0012 :: Process
	 * @param params
	 * @return
	 */
	public List<ServerProcess> getWS0012(Map params);

	/**
	 * WS0013 :: MIAgent
	 * @param params
	 * @return
	 */
	public List<EAIEngine> getWS0013(Map params);

	/**
	 * WS0014 :: MIRunner
	 * @param params
	 * @return
	 */
	public List<EAIEngine> getWS0014(Map params);

	/**
	 * WS0015 :: QManager
	 * @param params
	 * @return
	 */
	public List<QueueManager> getWS0015(Map params);

	/**
	 * WS0016 :: Channel
	 * @param params
	 * @return
	 */
	public List<QmgrChannel> getWS0016(Map params);

	/**
	 * WS0017 :: Queue
	 * @param params
	 * @return
	 */
	public List<Queue> getWS0017(Map params);

	/**
	 * WS0018 :: 주요배치처리상태 - 리스트
	 * @param params
	 * @return
	 */
	public List<Map> getWS0018(Map params);

	/**
	 * WS0019 :: IIPAgent
	 * @param params
	 * @return
	 */
	public List<IIPAgent> getWS0019(Map params);

	/**
	 * WS0035 :: 데이터처리방식별 금일누적건수
	 * @param params
	 * @return
	 */
	public List<Map> getWS0035(Map params);


	/**
	 * WS0037 :: 초당처리건수
	 * @param params
	 * @return
	 */
	public int getWS0037(Map params);

	/**
	 * WS0038 :: SMS전송로그
	 * @param params
	 * @return
	 */
	public List<Map> getWS0038(Map params);

	/**
	 * WS0039 :: 오류인터페이스TOP-N
	 * @param params
	 * @return
	 */
	public List<Map> getWS0039(Map params);

	/**
	 * WS0040 :: 시스템별 거래 현황(NH농협)
	 * @param params
	 * @return
	 */
	public List<Map> getWS0040(Map params);


	/**
	 * WS0041 :: 시스템별 거래 현황-금일누적
	 * @param params
	 * @return
	 */
	public List<Map> getWS0041(Map params);

	/**
	 * WS0042 :: WMQ종합상황판
	 * @param params
	 * @return
	 */
	public List<Map> getWS0042(Map params);


	/**
	 * WMQ종함상황판-큐매니져알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getQmgrAlertListBySystem(Map params);

	/**
	 * WMQ종함상황판-큐알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getQueueAlertListBySystem(Map params);

	/**
	 * WMQ종함상황판-채널알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getChannelAlertListBySystem(Map params);
}
