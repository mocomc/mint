/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.im;


import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.MIAgent;
import pep.per.mint.common.data.basic.MIRunner;

import java.util.List;
import java.util.Map;


public interface EngineMapper {


	/**
	 * REST-R01-IM-02-01  IIP Agent 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MIAgent> getEngineAgentList(Map params) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MIAgent> existEngineAgent(Map params) throws Exception;


	public int insertEngineAgent(MIAgent agent) throws Exception;

	public int updateEngineAgent(MIAgent server) throws  Exception;


	public int deleteEngineAgent(@Param("agentId")String agentId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;


	/**
	 * REST-R02-IM-02-01  모니터링  리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MIRunner> getEngineRunner(@Param("agentId")String agentId) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MIRunner> existEngineRunner(Map params) throws Exception;


	public int insertEngineRunner(MIRunner runner);

	public int deleteEngineRunner(@Param("runnerId")String  runnerId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public int updateEngineRunner(MIRunner runner);


	public int deleteAllEngineRunner(@Param("agentId")String  agentId,  @Param("modId")String modId, @Param("modDate")String modDate);

}
