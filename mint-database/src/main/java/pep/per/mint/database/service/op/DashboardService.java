package pep.per.mint.database.service.op;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.IIPAgent;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.data.basic.dashboard.EAIEngine;
import pep.per.mint.common.data.basic.dashboard.EngineDashboard;
import pep.per.mint.common.data.basic.dashboard.EngineLimitCount;
import pep.per.mint.common.data.basic.dashboard.QmgrChannel;
import pep.per.mint.common.data.basic.dashboard.Queue;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;
import pep.per.mint.common.data.basic.dashboard.Trigger;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.op.DashboardMapper;
import pep.per.mint.database.mapper.op.ProblemLedgerMapper;

/*z
 * 대시보드 관련 서비스
 * @author isjang
 *
 */

@Service
public class DashboardService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DashboardMapper dashboardMapper;

	@Autowired
	ProblemLedgerMapper problemLedgerMapper;

	/**
	 * 데이터 접근 권한 첫번째 depth 조회
	 * @param params
	 * @return
	 */
	public List<Map> getDataAccessRoleFirstDepth(Map params) {
		return dashboardMapper.getDataAccessRoleFirstDepth(params);
	}

	/**
	 * 처리건수(금일)
	 * @param params
	 * @return
	 */
	public List<Map> getDailyThroughput(Map params) {
		{
			List searchRoleList = new ArrayList();
			searchRoleList.add('0');
			searchRoleList.add('1');
			params.put("depth", searchRoleList);
		}

		List<Map> firstAccessRoleList = dashboardMapper.getDataAccessRoleDepth(params);
		if( firstAccessRoleList != null && firstAccessRoleList.size() > 0 ) {

			List dataAccessList = new ArrayList();
			for(Map role : firstAccessRoleList) {
				dataAccessList.add(role.get("ROLE_ID"));
			}

			params.put("dataAccessList", dataAccessList);
		}


		return dashboardMapper.getDailyThroughput(params);
	}

	/**
	 * 에러건수(금일) - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getErrorListTop(Map params) {
		return dashboardMapper.getErrorListTop(params);
	}

	/**
	 * 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getDelayListTop(Map params) {
		return dashboardMapper.getDelayListTop(params);
	}

	/**
	 * 실시간 처리건수 - 전체
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeTotalCount(Map params) {

		List<Map> firstAccessRoleList = dashboardMapper.getDataAccessRoleFirstDepth(params);
		if( firstAccessRoleList != null && firstAccessRoleList.size() > 0 ) {

			List dataAccessList = new ArrayList();
			for(Map role : firstAccessRoleList) {
				dataAccessList.add(role.get("ROLE_ID"));
			}

			params.put("dataAccessList", dataAccessList);
		}

		return dashboardMapper.getRealTimeTotalCount(params);
	}

	/**
	 * 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeFavoriteCount(Map params) {
		return dashboardMapper.getRealTimeFavoriteCount(params);
	}

	/**
	 * 처리량추이(전일/금일)
	 * @param params
	 * @return
	 */
	public List<Map> getCountStatsDaily(Map params) {
		return dashboardMapper.getCountStatsDaily(params);
	}

	/**
	 * 처리량추이(최근4개월)
	 * @param params
	 * @return
	 */
	public Map getCountStatsMonthly(Map params) {
		return dashboardMapper.getCountStatsMonthly(params);
	}

	/**
	 * 처리량추이(최근4년)
	 * @param params
	 * @return
	 */
	public Map getCountStatsYearly(Map params) {
		return dashboardMapper.getCountStatsYearly(params);
	}



	/**
	 * 주요배치처리상태 - 카운트
	 * @param params
	 * @return
	 */
	public List<Map> getImportantBatchStateCount(Map params) {
		return dashboardMapper.getImportantBatchStateCount(params);
	}

	/**
	 * 주요배치처리상태 - 리스트
	 * @param params
	 * @return
	 */
	public List<Map> getImportantBatchStateList(Map params) {
		return dashboardMapper.getImportantBatchStateList(params);
	}

	/**
	 * 알림이벤트 - 카운트
	 * @param params
	 * @return
	 */
	public EngineDashboard getEngineLimitInfo(Map params) {
		EngineDashboard engineDashboard = new EngineDashboard();
		EngineLimitCount limitCount = new EngineLimitCount();

		Object obj = params.get("resourceList");
		if( obj != null ) {
			List<String> resourceList = (List)obj;
			for(String resource : resourceList) {

				if( !Util.isEmpty(resource) && resource.equals("cpu") ) {
					limitCount.setCpuLimitCnt(getCpuLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("memory") ) {
					limitCount.setMemoryLimitCnt(getMemoryLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("disk") ) {
					limitCount.setDiskLimitCnt(getDiskLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("process") ) {
					limitCount.setProcessLimitCnt(getProcessLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("agent") ) {
					limitCount.setAgentLimitCnt(getAgentLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("runner") ) {
					limitCount.setBrokerLimitCnt(getBrokerLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("qmgr") ) {
					limitCount.setQmgrLimitCnt(getQmgrLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("channel") ) {
					limitCount.setChannelLimitCnt(getChannelLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("qdepth") ) {
					limitCount.setQueueLimitCnt(getQueueLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("trigger") ) {
					limitCount.setTriggerLimitCnt(getTriggerLimitCount(params));
					continue;
				}

				if( !Util.isEmpty(resource) && resource.equals("iipagent") ) {
					limitCount.setIipAgentLimitCnt(getIIPAgentLimitCount(params));
					continue;
				}
			}
		}


		engineDashboard.setEngineLimitCount(limitCount);
		return engineDashboard;
	}

	/**
	 * 알림이벤트 - IIPAgent Limit 카운트
	 * @param params
	 * @return
	 */
	public int getIIPAgentLimitCount(Map params) {
		List<IIPAgent> list = getIIPAgentLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Queue Depth Limit 카운트
	 * @param params
	 * @return
	 */
	public int getQueueLimitCount(Map params) {
		List<Queue> list = getQueueLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Channel Limit 카운트
	 * @param params
	 * @return
	 */
	public int getChannelLimitCount(Map params) {
		List<QmgrChannel> list = getChannelLimitList(params);
		return list == null ? 0 : list.size();
	}


	/**
	 * 알림이벤트 - QManager Limit 카운트
	 * @param params
	 * @return
	 */
	public int getQmgrLimitCount(Map params) {
		List<QueueManager> list = getQmgrLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Disk Limit 카운트
	 * @param params
	 * @return
	 */
	public int getDiskLimitCount(Map params) {
		params.put("type", ResourceInfo.TYPE_DISK);
		List<ServerResource> list = dashboardMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Memory Limit 카운트
	 * @param params
	 * @return
	 */
	public int getMemoryLimitCount(Map params) {
		params.put("type", ResourceInfo.TYPE_MEMORY);
		List<ServerResource> list = dashboardMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - CPU Limit 카운트
	 * @param params
	 * @return
	 */
	public int getCpuLimitCount(Map params) {
		params.put("type", ResourceInfo.TYPE_CPU);
		List<ServerResource> list = dashboardMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Process Limit 카운트
	 * @param params
	 * @return
	 */
	public int getProcessLimitCount(Map params) {
		List<ServerProcess> list = getProcessLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - RUNNER(BROKER) Limit 카운트
	 * @param params
	 * @return
	 */
	public int getBrokerLimitCount(Map params) {
		List<EAIEngine> list = getRunnerLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Agent Limit 카운트
	 * @param params
	 * @return
	 */
	public int getAgentLimitCount(Map params) {
		List<EAIEngine> list = getAgentLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * 알림이벤트 - Trigger Limit 카운트
	 * @param params
	 * @return
	 */
	public int getTriggerLimitCount(Map params) {
		List<Trigger> list = getTriggerLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * CPU Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getCPULimitList(Map params) {
		params.put("type", ResourceInfo.TYPE_CPU);
		return dashboardMapper.getResourceLimitList(params);
	}

	/**
	 * Memory Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getMemoryLimitList(Map params) {
		params.put("type", ResourceInfo.TYPE_MEMORY);
		return dashboardMapper.getResourceLimitList(params);
	}

	/**
	 * Disk Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getDiskLimitList(Map params) {
		params.put("type", ResourceInfo.TYPE_DISK);
		return dashboardMapper.getResourceLimitList(params);
	}


	public List<EAIEngine> getAgentLimitList(Map params) {
		return dashboardMapper.getAgentLimitList(params);
	}

	/**
	 * Runner/Broker Limit List
	 * @param params
	 * @return
	 */
	public List<EAIEngine> getRunnerLimitList(Map params) {
		return dashboardMapper.getRunnerLimitList(params);
	}

	/**
	 * Process Limit List
	 * @param params
	 * @return
	 */
	public List<ServerProcess> getProcessLimitList(Map params) {
		return dashboardMapper.getProcessLimitList(params);
	}

	/**
	 * QManager Limit List
	 * @param params
	 * @return
	 */
	public List<QueueManager> getQmgrLimitList(Map params) {
		return dashboardMapper.getQmgrLimitList(params);
	}

	/**
	 * Queue Depth Limit List
	 * @param params
	 * @return
	 */
	public List<Queue> getQueueLimitList(Map params) {
		return dashboardMapper.getQueueLimitList(params);
	}

	/**
	 * Channel Limit List
	 * @param params
	 * @return
	 */
	public List<QmgrChannel> getChannelLimitList(Map params) {
		return dashboardMapper.getChannelLimitList(params);
	}

	/**
	 * Trigger Limit List
	 * @param params
	 * @return
	 */
	public List<Trigger> getTriggerLimitList(Map params) {
		return dashboardMapper.getTriggerLimitList(params);
	}

	/**
	 * IIPAgent Limit List
	 * @param params
	 * @return
	 */
	public List<IIPAgent> getIIPAgentLimitList(Map params) {
		return dashboardMapper.getIIPAgentLimitList(params);
	}

	/**
	 * 관심인터페이스 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<String> getFavoriteInterfaceList(String userId) throws Exception{
		return dashboardMapper.getFavoriteInterfaceList(userId);

	}
}
