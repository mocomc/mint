/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.im;


import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.ResourceInfo;

import java.util.List;
import java.util.Map;


public interface EngineMonitorMapper {


	/**
	 * REST-R01-IM-02-01  IIP Agent 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<IIPAgentInfo> getAgentList(Map params) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<IIPAgentInfo> existAgent(Map params) throws Exception;


	public int insertAgent(IIPAgentInfo agent) throws Exception;

	public int updateAgent(IIPAgentInfo server) throws  Exception;


	public int deleteAgent(@Param("agentId")String agentId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;


	/**
	 * REST-R02-IM-02-01  모니터링  리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public IIPAgentInfo getEngineMonitor(IIPAgentInfo agent) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<IIPAgentInfo> existEngineMonitor(Map params) throws Exception;


	public int insertEngineMonitor(IIPAgentInfo server) throws Exception;

	public int updateEngineMonitor(IIPAgentInfo server) throws  Exception;


	public int deleteEngineMonitor(@Param("agentId")String agentId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;


	public int insertEngineMonitorMapping(@Param("agentId")String agentId,
			 @Param("itemId")String itemId, @Param("itemType")String itemType );

	public int insertEngineMonitorResource(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("serverResource")ResourceInfo serverResource);

	public int insertEngineMonitorProcess(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("serverProcess")ProcessInfo serverProcess);

	public int insertEngineMonitorQmgr(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("qmgr")QmgrInfo qmgr);

	public int insertEngineMonitorQueue(@Param("agentId")String agentId, @Param("serverId")String serverId,
			@Param("qmgrId")String qmgrId, @Param("queue")QueueInfo queue);

	public int insertEngineMonitorChannel(@Param("agentId")String agentId, @Param("serverId")String serverId,
			@Param("qmgrId")String qmgrId, @Param("qmgrChannel")ChannelInfo channel);


	public int updateEngineMonitorResource(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("serverResource")ResourceInfo  serverResource);

	public int updateEngineMonitorProcess(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("serverProcess")ProcessInfo serverProcess);

	public int updateEngineMonitorQmgr(@Param("agentId")String agentId, @Param("serverId")String serverId, @Param("qmgr")QmgrInfo qmgr);

	public int updateEngineMonitorQueue(@Param("agentId")String agentId, @Param("serverId")String serverId,
			@Param("qmgrId")String qmgrId, @Param("queue")QueueInfo queue);

	public int updateEngineMonitorChannel(@Param("agentId")String agentId, @Param("serverId")String serverId,
			@Param("qmgrId")String qmgrId, @Param("qmgrChannel")ChannelInfo channel);



	public int deleteEngineMonitorMapping(@Param("agentId")String agentId, @Param("itemId")String itemId, @Param("itemType")String itemType );

	public int deleteEngineMonitorResource(@Param("agentId")String agentId,
			 @Param("resourceId")String resourceId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public int deleteEngineMonitorProcess(@Param("agentId")String agentId,
			 @Param("processId")String processId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public int deleteEngineMonitorQmgr(@Param("agentId")String agentId,
			@Param("qmgrId")String qmgrId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public int deleteEngineMonitorQueue(@Param("agentId")String agentId,
			@Param("qmgrId")String qmgrId, @Param("queueId")String queueId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public int deleteEngineMonitorChannel(@Param("agentId")String agentId,
			@Param("qmgrId")String qmgrId, @Param("channelId")String channelId,  @Param("modId")String modId, @Param("modDate")String modDate);

	public List<ResourceInfo> getEngineMoitorResource(IIPAgentInfo agent) throws Exception;

	public List<ProcessInfo> getEngineMoitorProcess(IIPAgentInfo agent) throws Exception;

	public List<QmgrInfo> getEngineMoitorQmgr(IIPAgentInfo agent) throws Exception;

	public List<ChannelInfo> getEngineMoitorChannel(Map params) throws Exception;

	public List<QueueInfo> getEngineMoitorQueue(Map params) throws Exception;


	public int deleteAllEngineMonitorResource(@Param("agentId")String agentId ) throws Exception;

	public int deleteAllEngineMonitorProcess(@Param("agentId")String agentId ) throws Exception;

	public int deleteAllEngineMonitorChannel(@Param("agentId")String agentId ) throws Exception;

	public int deleteAllEngineMonitorQueue(@Param("agentId")String agentId ) throws Exception;

	public int deleteAllEngineMonitorQmgr(@Param("agentId")String agentId ) throws Exception;

	public int deleteAllEngineMonitorMapping(@Param("agentId")String agentId ) throws Exception;

	public String getAgentIDInfo(@Param("agentNm")String agentNm);

	public int updateEngineMonitorResourceforServer(@Param("agentId")String agentId, @Param("serverId")String serverId);

	public int updateEngineMonitorProcessforServer(@Param("agentId")String agentId, @Param("serverId")String serverId);

	public int updateEngineMonitorQmgrforServer(@Param("agentId")String agentId, @Param("serverId")String serverId);

	public int deleteEngineMonitorMappingChannel(@Param("agentId")String agentId, @Param("qmgrId")String qmgrId);

	public int deleteEngineMonitorMappingQueue(@Param("agentId")String agentId, @Param("qmgrId")String qmgrId);

	public int deleteEngineMonitorQueueOfQmgr(@Param("agentId")String agentId, @Param("qmgrId")String qmgrId, @Param("modId")String modId, @Param("modDate")String modDate);

	public int deleteEngineMonitorChannelOfQmgr(@Param("agentId")String agentId, @Param("qmgrId")String qmgrId, @Param("modId")String modId, @Param("modDate")String modDate);

	public List<QmgrInfo> getEngineMoitorAllQmgr() throws Exception;

	public List<QmgrInfo> getEngineMoitorQmgrWithSystem(IIPAgentInfo agent) throws Exception;

	public int deleteEngineMonitorQmgrWidthSystem(@Param("qmgrId")String qmgrId) ;

	public int insertEngineMonitorQmgrSystemMapping(@Param("systemId")String systemId, @Param("qmgrId")String qmgrId);

	public int updateEngineMonitorQmgrSystemMapping(@Param("systemId")String systemId, @Param("qmgrId")String qmgrId);
}
