package pep.per.mint.websocket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.util.Util;

/**
 *
 * <pre>
 * pep.per.mint.websocket.service
 * AgentChannelService.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
@Service
public class AgentChannelService {

	Logger logger = LoggerFactory.getLogger(AgentChannelService.class);

	Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();

	Map<String,List<String>> sessionIdMap = new  ConcurrentHashMap<String, List<String>>();

	final static char SESSION_DELIM = '@';
	/**
	 *
	 * @param session
	 */
	public void login(WebSocketSession session) throws Exception {

		IIPAgentInfo agentInfo = (IIPAgentInfo) session.getAttributes().get("agentInfo");
		if (agentInfo == null) {
			throw new Exception(Util.join("Exception:Not found IIPAgentInfo object in websocket session"));
		}
		String agentNm = agentInfo.getAgentNm();
		String sessionId = sessionId(agentNm, session.getId());
		//String sessionId = agentInfo.getAgentNm();

		if(sessionMap.containsKey(sessionId)) {
			//기존 세션 정리 , 에이전트이름 당 하나의 세션 정책
			//sessionMap.remove(sessionId).close();
			//throw new WebServiceException(Util.join("로그인하는 에이전트가 이미 존재하여 강제 종료합니다.(", "agentNm:",sessionId , ")"));
			List<String> sessionIdList = sessionIdMap.get(agentNm);
			if(sessionIdList != null && sessionIdList.size() > 0) {
				for(String oldSessionId : sessionIdList) {
					WebSocketSession oldSession  = sessionMap.get(oldSessionId);
					logger.info(Util.join("close oldSessionId:",oldSessionId, ", 기존세션정리(에이전트이름 당 하나의 세션 정책)"));
					if(oldSession != null) logout(oldSession);
				}
			}

		}
		sessionMap.put(sessionId, session);


		if(!sessionIdMap.containsKey(agentNm)) {
			sessionIdMap.put(agentNm, new ArrayList<String>());
		}
		sessionIdMap.get(agentNm).add(sessionId);


		logger.info(
				Util.join(
						"\n---------------------------------------------------",
						"\nagent channel login agent(", sessionId, ")",
						"\n---------------------------------------------------"));
		logger.debug("--------------------------------------------------");
		logger.debug(getAgentChannelInfo());
		logger.debug("--------------------------------------------------");
	}

	/**
	 *
	 * @param session
	 */
	public void logout(WebSocketSession session) {

		IIPAgentInfo agentInfo = (IIPAgentInfo) session.getAttributes().get("agentInfo");
		if(agentInfo != null) {
			//String sessionId = agentInfo.getAgentNm();
			String sessionId = sessionId(agentInfo.getAgentNm(), session.getId());
			sessionMap.remove(sessionId);

			String agentNm = agentInfo.getAgentNm();
			List<String> list = sessionIdMap.get(agentNm);
			if(list != null && list.size() > 0 ) {
				list.remove(sessionId);
			}
			logger.info(
					Util.join(
							"\n---------------------------------------------------",
							"\nagent channel logout agent(",sessionId, ")",
							"\n---------------------------------------------------"));
		}

		logger.debug("--------------------------------------------------");
		logger.debug(getAgentChannelInfo());
		logger.debug("--------------------------------------------------");
	}

	/**
	 *
	 * @param sessionId
	 * @param msg
	 * @throws Exception
	 */
	public void sendMessage(String sessionId, String msg) throws Exception {

//		logger.debug("agentSessionId:"+sessionId);
//		logger.debug(getAgentChannelInfo());

		WebSocketSession session = sessionMap.get(sessionId);
		if (session != null) {
			synchronized (session) {
				TextMessage tmsg = new TextMessage(msg);
				session.sendMessage(tmsg);
				logger.debug(Util.join("send to ", sessionId, ", msg:\n", msg));
			}
		} else {
			throw new Exception(Util.join("Exception:Not found agentId(", sessionId, ")'s websocket session"));
		}
	}


	public Map<String, WebSocketSession> getSessionMap() {
		return sessionMap;
	}



	public String getAgentChannelInfo() {
		StringBuffer info = new StringBuffer("\nAgentChannelInfo\n");
		try {
			StringBuffer sessionMapInfo = new StringBuffer();
			sessionMapInfo.append("sessionMap info\n");
			sessionMapInfo.append("	sessionMap.size():").append(sessionMap.size()).append("\n");
			sessionMapInfo.append("	sessionMap.keys:").append(Util.toJSONString(sessionMap.keySet().toArray())).append("\n");
			info.append(sessionMapInfo);
		}catch(Exception e) {}
		return info.toString();
	}

	public String getAgentNm(String sessionId) {
		if (Util.isEmpty(sessionId))
			return "";
		int lastIndex = sessionId.lastIndexOf(SESSION_DELIM);
		String agentNm = sessionId.substring(0, lastIndex);
		return agentNm;
	}

	public String sessionId(String agentNm, String wssId) {
		return Util.join(agentNm, SESSION_DELIM, wssId);
	}

	public String findSessionIdAtFirst(String agentNm) {
		List<String> list = sessionIdMap.get(agentNm);
		if(list != null && list.size() > 0 ) {
			return list.get(0);
		}else {
			return null;
		}
	}

	public List<String> getSessionIdList(String agentNm) {
		return sessionIdMap.get(agentNm);
	}

}
