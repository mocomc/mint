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
import pep.per.mint.common.data.basic.dashboard.QmgrChannel;
import pep.per.mint.common.data.basic.dashboard.Queue;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;
import pep.per.mint.database.mapper.op.DashboardPushMapper;

/**
 *
 * @author ta
 *
 */

@Service
public class DashboardPushService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DashboardPushMapper dashboardPushMapper;

	/**
	 * 데이터 접근 권한 첫번째 depth 조회
	 * @param params
	 * @return
	 */
	public List<Map> getDataAccessRoleFirstDepth(Map params) {
		return dashboardPushMapper.getDataAccessRoleFirstDepth(params);
	}

	/**
	 * WS0001 :: 처리건수(금일)
	 * @param params
	 * @return
	 */
	public List<Map> getWS0001(Map params) {
		{
			List searchRoleList = new ArrayList();
			searchRoleList.add('0');
			searchRoleList.add('1');
			params.put("depth", searchRoleList);
		}

		List<Map> firstAccessRoleList = dashboardPushMapper.getDataAccessRoleDepth(params);
		if( firstAccessRoleList != null && firstAccessRoleList.size() > 0 ) {

			List dataAccessList = new ArrayList();
			for(Map role : firstAccessRoleList) {
				dataAccessList.add(role.get("ROLE_ID"));
			}

			params.put("dataAccessList", dataAccessList);
		}
		return dashboardPushMapper.getWS0001(params);
	}

	/**
	 * WS0002 :: 에러건수(금일) - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getWS0002(Map params) {
		return dashboardPushMapper.getWS0002(params);
	}

	/**
	 * WS0003 :: 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getWS0003(Map params) {
		return dashboardPushMapper.getWS0003(params);
	}

	/**
	 * WS0004 :: 실시간 처리건수 - 전체
	 * @param params
	 * @return
	 */
	public List<Map> getWS0004(Map params) {

		List<Map> firstAccessRoleList = dashboardPushMapper.getDataAccessRoleFirstDepth(params);
		if( firstAccessRoleList != null && firstAccessRoleList.size() > 0 ) {

			List dataAccessList = new ArrayList();
			for(Map role : firstAccessRoleList) {
				dataAccessList.add(role.get("ROLE_ID"));
			}

			params.put("dataAccessList", dataAccessList);
		}

		return dashboardPushMapper.getWS0004(params);
	}

	/**
	 * WS0005 :: 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getWS0005(Map params) {
		return dashboardPushMapper.getWS0005(params);
	}

	/**
	 * WS0006 :: 처리량추이(전일/금일)
	 * @param params
	 * @return
	 */
	public List<Map> getWS0006(Map params) {
		return dashboardPushMapper.getWS0006(params);
	}

	/**
	 * WS0007 :: 처리량추이(최근4개월)
	 * @param params
	 * @return
	 */
	public Map getWS0007(Map params) {
		return dashboardPushMapper.getWS0007(params);
	}

	/**
	 * WS0008 :: 처리량추이(최근4년)
	 * @param params
	 * @return
	 */
	public Map getWS0008(Map params) {
		return dashboardPushMapper.getWS0008(params);
	}

	/**
	 * WS0009 :: 알림이벤트 - CPU Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0009(Map params) {
		params.put("type", ResourceInfo.TYPE_CPU);
		List<ServerResource> list = dashboardPushMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0009 :: CPU Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getWS0009List(Map params) {
		params.put("type", ResourceInfo.TYPE_CPU);
		return dashboardPushMapper.getResourceLimitList(params);
	}

	/**
	 * WS0010 :: 알림이벤트 - Memory Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0010(Map params) {
		params.put("type", ResourceInfo.TYPE_MEMORY);
		List<ServerResource> list = dashboardPushMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0010 :: Memory Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getWS0010List(Map params) {
		params.put("type", ResourceInfo.TYPE_MEMORY);
		return dashboardPushMapper.getResourceLimitList(params);
	}

	/**
	 * WS0011 :: 알림이벤트 - Disk Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0011(Map params) {
		params.put("type", ResourceInfo.TYPE_DISK);
		List<ServerResource> list = dashboardPushMapper.getResourceLimitList(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0011 :: Disk Limit List
	 * @param params
	 * @return
	 */
	public List<ServerResource> getWS0011List(Map params) {
		params.put("type", ResourceInfo.TYPE_DISK);
		return dashboardPushMapper.getResourceLimitList(params);
	}

	/**
	 * WS0012 :: 알림이벤트 - Process Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0012(Map params) {
		List<ServerProcess> list = getWS0012List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0012 :: Process Limit List
	 * @param params
	 * @return
	 */
	public List<ServerProcess> getWS0012List(Map params) {
		return dashboardPushMapper.getWS0012(params);
	}

	/**
	 * WS0013 :: 알림이벤트 - Agent Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0013(Map params) {
		List<EAIEngine> list = getWS0013List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0013 :: MIAgent
	 * @param params
	 * @return
	 */
	public List<EAIEngine> getWS0013List(Map params) {
		return dashboardPushMapper.getWS0013(params);
	}

	/**
	 * WS0014 :: 알림이벤트 - RUNNER(BROKER) Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0014(Map params) {
		List<EAIEngine> list = getWS0014List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0014 :: MIRunner
	 * @param params
	 * @return
	 */
	public List<EAIEngine> getWS0014List(Map params) {
		return dashboardPushMapper.getWS0014(params);
	}

	/**
	 * WS0015 :: 알림이벤트 - QManager Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0015(Map params) {
		List<QueueManager> list = getWS0015List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0015 :: QManager Limit List
	 * @param params
	 * @return
	 */
	public List<QueueManager> getWS0015List(Map params) {
		return dashboardPushMapper.getWS0015(params);
	}

	/**
	 * WS0016 :: 알림이벤트 - Channel Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0016(Map params) {
		List<QmgrChannel> list = getWS0016List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0016 :: Channel Limit List
	 * @param params
	 * @return
	 */
	public List<QmgrChannel> getWS0016List(Map params) {
		return dashboardPushMapper.getWS0016(params);
	}

	/**
	 * WS0017 :: 알림이벤트 - Queue Depth Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0017(Map params) {
		List<Queue> list = getWS0017List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0017 :: Queue Depth Limit List
	 * @param params
	 * @return
	 */
	public List<Queue> getWS0017List(Map params) {
		return dashboardPushMapper.getWS0017(params);
	}

	/**
	 * WS0018 :: 주요배치처리상태 - 카운트
	 * @param params
	 * @return
	 */
	public int getWS0018(Map params) {
		List<Map> list = getWS0018List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0018 :: 주요배치처리상태 - 리스트
	 * @param params
	 * @return
	 */
	public List<Map> getWS0018List(Map params) {
		return dashboardPushMapper.getWS0018(params);
	}

	/**
	 * WS0019 :: 알림이벤트 - IIPAgent Limit 카운트
	 * @param params
	 * @return
	 */
	public int getWS0019(Map params) {
		List<IIPAgent> list = getWS0019List(params);
		return list == null ? 0 : list.size();
	}

	/**
	 * WS0019 :: IIPAgent Limit List
	 * @param params
	 * @return
	 */
	public List<IIPAgent> getWS0019List(Map params) {
		return dashboardPushMapper.getWS0019(params);
	}

	/**
	 * WS0035 :: 데이터처리방식별 금일누적건수
	 * @param params
	 * @return
	 */
	public List<Map> getWS0035(Map params) {
		return dashboardPushMapper.getWS0035(params);
	}

	/**
	 * WS0037 :: 초당처리건수
	 * @param params
	 * @return
	 */
	public int getWS0037(Map params) {
		return dashboardPushMapper.getWS0037(params);
	}

	/**
	 * WS0038 :: SMS전송로그
	 * @param params
	 * @return
	 */
	public List<Map> getWS0038(Map params) {
		return dashboardPushMapper.getWS0038(params);
	}


	/**
	 * WS0039 :: 오류인터페이스TOP-N
	 * @param params
	 * @return
	 */
	public List<Map> getWS0039(Map params) {
		return dashboardPushMapper.getWS0039(params);
	}


	/**
	 * WS0040 :: 시스템별 거래 현황(NH농협)
	 * @param params
	 * @return
	 */
	public List<Map> getWS0040(Map params) {
		return dashboardPushMapper.getWS0040(params);
	}

	/**
	 * WS0041 :: 시스템별 거래 현황-금일누적
	 * @param params
	 * @return
	 */
	public List<Map> getWS0041(Map params) {
		return dashboardPushMapper.getWS0041(params);
	}

	/**
	 * WS0042 :: WMQ종합상황판
	 * @param params
	 * @param params
	 * @return
	 */
	public List<Map> getWS0042(Map params) {
		return dashboardPushMapper.getWS0042(params);
	}

	/**
	 * WS0042 :: WMQ종함상황판-큐매니져알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getQmgrAlertListBySystem(Map params) {
		return dashboardPushMapper.getQmgrAlertListBySystem(params);
	}


	/**
	 * WS0042 :: WMQ종함상황판-큐알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getQueueAlertListBySystem(Map params) {
		return dashboardPushMapper.getQueueAlertListBySystem(params);
	}


	/**
	 * WS0042 :: WMQ종함상황판-채널알림리스트
	 * @param params
	 * @return
	 */
	public List<Map> getChannelAlertListBySystem(Map params) {
		return dashboardPushMapper.getChannelAlertListBySystem(params);
	}

}
