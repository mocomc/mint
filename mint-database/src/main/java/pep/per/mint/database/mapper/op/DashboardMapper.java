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

import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.IIPAgent;
import pep.per.mint.common.data.basic.dashboard.EAIEngine;
import pep.per.mint.common.data.basic.dashboard.ErrorEAIComponent;
import pep.per.mint.common.data.basic.dashboard.ErrorInterface;
import pep.per.mint.common.data.basic.dashboard.SolutionsStatus;
import pep.per.mint.common.data.basic.dashboard.SystemResourceStatus;
import pep.per.mint.common.data.basic.dashboard.Trigger;
import pep.per.mint.common.data.basic.dashboard.InterfaceElapsedTimeStatus;
import pep.per.mint.common.data.basic.dashboard.QmgrChannel;
import pep.per.mint.common.data.basic.dashboard.Queue;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;

/**
 * @author isjang
 *
 */
public interface DashboardMapper {



	public List<String> getFavoriteInterfaceList(String userId) throws Exception;

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
	 * 처리건수(금일)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDailyThroughput(Map params);

	/**
	 * 에러건수(금일) - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getErrorListTop(Map params);

	/**
	 * 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getDelayListTop(Map params);

	/**
	 * 실시간 처리건수 - 전체
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeTotalCount(Map params);

	/**
	 * 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeFavoriteCount(Map params);

	/**
	 * 처리량추이(전일/금일)
	 * @param params
	 * @return
	 */
	public List<Map> getCountStatsDaily(Map params);

	/**
	 * 처리량추이(최근4개월)
	 * @param params
	 * @return
	 */
	public Map getCountStatsMonthly(Map params);

	/**
	 * 처리량추이(최근4년)
	 * @param params
	 * @return
	 */
	public Map getCountStatsYearly(Map params);

	/**
	 * 주요배치처리상태 - 카운트
	 * @param params
	 * @return
	 */
	public List<Map> getImportantBatchStateCount(Map params);

	/**
	 * 주요배치처리상태 - 리스트
	 * @param params
	 * @return
	 */
	public List<Map> getImportantBatchStateList(Map params);

	public List<ServerResource> getResourceLimitList(Map params);

	public List<EAIEngine> getAgentLimitList(Map params);

	public List<EAIEngine> getRunnerLimitList(Map params);

	public List<ServerProcess> getProcessLimitList(Map params);

	public List<QueueManager> getQmgrLimitList(Map params);

	public List<Queue> getQueueLimitList(Map params);

	public List<Trigger> getTriggerLimitList(Map params);

	public List<QmgrChannel> getChannelLimitList(Map params);

	public List<IIPAgent> getIIPAgentLimitList(Map params);

}
