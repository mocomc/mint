/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.co;

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
 *
 * @author ta
 *
 */
public interface WidgetMapper {

	/**
	 * getWidgetConfig
	 * Widget Config Info
	 * @param params
	 * @return
	 */
	public List<Map> getWidgetConfig(Map params);

	/**
	 * getWS0040Target
	 * 시스템별 거래 현황 목록
	 * @param params
	 * @return
	 */
	public List<Map> getWS0040Target(Map params);


	/**
	 * getWS0042Target
	 * WMQ종합상황판 대상시스템조회
	 * @param params
	 * @return
	 */
	public List<Map> getWS0042Target(Map params);


	/**
	 * getSystemTreeWithModel
	 * WMQ Explorer Tree - SystemList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getSystemTreeWithModel(Map params);

	/**
	 * getServerList
	 * WMQ Explorer Tree - ServerList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getServerList(Map params);

	/**
	 * insertPersonalizationByUser
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertPersonalizationByUser(Map params) throws Exception;

	/**
	 * updatePersonalizationByUser
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updatePersonalizationByUser(Map params) throws Exception;

	/**
	 * deletePersonalizationByUser
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deletePersonalizationByUser(Map params) throws Exception;

	/**
	 * getPersonalizationByUserList
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getPersonalizationByUserList(Map params);

	/**
	 * getPersonalizationByUserList
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getPersonalizationBySharedList(Map params);


	/**
	 * getPersonalizationByUser
	 *
	 * @param params
	 * @return
	 */
	public Map getPersonalization(Map params);


}
