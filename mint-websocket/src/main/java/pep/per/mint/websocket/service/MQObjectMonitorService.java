/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.websocket.service
 * MQObjectMonitorService.java
 * </pre>
 * @author whoana
 * @date 2018. 9. 28.
 */
@Service
public class MQObjectMonitorService {

	Logger logger = LoggerFactory.getLogger(MQObjectMonitorService.class);

	/**
	 * <pre>
	 * 큐정보 브로드캐스팅을 위한 맵
	 * queue, 웹소켓세션ID 매핑
	 * <qmgrNm@queueNm, List<sessionId>>
	 *
	 * 리소스 정리 시점 및 방법:
	 * 	1.서버에서 큐정보 PUSH 시점에 프론트 session 이 유효하지 않아 전송 실패시 serviceOffQueueMonitor 처리하고
	 *    해당 큐정보로 서비스 ON 한 프론트 session 이 더이상 존재하지 않을 경우 에이전트로 serviceOff 전송
	 *    [구현위치]
	 *    MQObjectMonitorService --> @ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_OFF)
	 *  2.프론트 session에서 명시적으로 요청한 경우
	 *    [구현위치]
	 *    MQObjectMonitorService --> @ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_PUSH)
	 * </pre>
	 */
	Map<String, List<String>>  queueSessionIdMap = new ConcurrentHashMap<String, List<String>>();




	public void serviceOnQueueMonitor(String sessionId, String qmgrNm, String queueNm) throws Exception{
		try {
			StringBuffer queueId = new StringBuffer();
			queueId.append(qmgrNm).append("@").append(queueNm);
			if(!queueSessionIdMap.containsKey(queueId.toString())) {
				queueSessionIdMap.put(queueId.toString(), new ArrayList<String>());
			}
			List<String> list = queueSessionIdMap.get(queueId.toString());
			if(!list.contains(sessionId)) list.add(sessionId);
		}finally {
			logger.debug("--------------------------------------------------");
			logger.debug(getMQObjectMonitorChannelInfo());
			logger.debug("--------------------------------------------------");
		}
	}


	public void serviceOffQueueMonitor(String sessionId, String qmgrNm, String queueNm) {
		try {
			StringBuffer queueId = new StringBuffer();
			queueId.append(qmgrNm).append("@").append(queueNm);
			List<String> list = queueSessionIdMap.get(queueId.toString());
			if(list != null && list.size() > 0) {
				list.remove(sessionId);
				if(list.size() == 0) queueSessionIdMap.remove(queueId.toString());
			}


		}finally {
			logger.debug("--------------------------------------------------");
			logger.debug(getMQObjectMonitorChannelInfo());
			logger.debug("--------------------------------------------------");
		}
	}


	public void serviceOnChannelMonitor(String sessionId, String qmgrNm, String queueNm) {

	}


	public void serviceOffChannelMonitor(String sessionId, String qmgrNm, String queueNm) {

	}

	public boolean existQueueMonitor(String qmgrNm, String queueNm) throws Exception {
		StringBuffer queueId = new StringBuffer();
		queueId.append(qmgrNm).append("@").append(queueNm);
		return queueSessionIdMap.containsKey(queueId.toString());
	}


	/**
	 * @param qmgrNm
	 * @param queueNm
	 */
	public void serviceOffAllQueueMonitor(String qmgrNm, String queueNm) {
		StringBuffer queueId = new StringBuffer();
		queueId.append(qmgrNm).append("@").append(queueNm);
		List<String> list = queueSessionIdMap.get(queueId.toString());
		if(list != null && list.size() > 0) {
			list.clear();
			queueSessionIdMap.remove(queueId.toString());
		}
	}

	public List<String> getFrontSessionIdList(String qmgrNm, String queueNm) {
		StringBuffer queueId = new StringBuffer();
		queueId.append(qmgrNm).append("@").append(queueNm);
		List<String> list = queueSessionIdMap.get(queueId.toString());
		return list;
	}



	public String getMQObjectMonitorChannelInfo() {
		StringBuffer info = new StringBuffer("\nMQObjectMonitorChannelInfo\n");
		try {
			StringBuffer queueSessionIdMapInfo = new StringBuffer();
			queueSessionIdMapInfo.append("1.queueSessionIdMapInfo info\n");
			queueSessionIdMapInfo.append("	queueSessionIdMapInfo.size():").append(queueSessionIdMap.size()).append("\n");
			queueSessionIdMapInfo.append("	queueSessionIdMapInfo.keys:").append(Util.toJSONString(queueSessionIdMap.keySet().toArray())).append("\n");
			info.append(queueSessionIdMapInfo);
			info.append(queueSessionIdMapInfo);
		}catch(Exception e) {}
		return info.toString();
	}

}
