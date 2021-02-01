package pep.per.mint.websocket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

/**
 *
 * <pre>
 * pep.per.mint.websocket.service
 * FrontChannelService.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
@Service
public class FrontChannelService {

	Logger logger = LoggerFactory.getLogger(FrontChannelService.class);

	public final static char SESSION_DELIM = '@';

	@Autowired
	CommonService commonService;


	/**
	 * 서비스코드, 액세스롤키별 초기PUSH데이터 매핑
	 * <serviceCd, <roleKey,data>>
	 */
	Map<String, Map<String,Object>> pushDataMap = new ConcurrentHashMap<String, Map<String,Object>>();

	/**
	 * HTTP 세션ID,  웹소켓 세션ID 매핑
	 * <HttpSessionId, WebsocketSessionId>
	 */
	Map<String, String> sessionIdMap = new ConcurrentHashMap<String, String>();

	/**
	 * 서비스코드, 액세스롤키별 웹소켓 세션ID 매핑
	 * <serviceCd, Map<roleKey,sessionId[]>>
	 */
	Map<String, Map<String, List<String>>> serviceAccessRoleMap = new ConcurrentHashMap<String, Map<String, List<String>>>();

	/**
	 * 세션ID, 웹소켓 매핑
	 * <sessionId,WebSocketSession>
	 */
	Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();

	public Map<String, List<String>> getAccessRoleMap(String serviceCd) {
		return serviceAccessRoleMap.get(serviceCd);
	}

	/**
	 *
	 * @param session
	 */
	public void login(WebSocketSession session) throws Exception {



		if (session.getAttributes().get("user") == null) {
			throw new Exception(Util.join("Exception:Not found User object in websocket session"));
		}

		User user = (User) session.getAttributes().get("user");
		String httpSessionId = (String)session.getAttributes().get("httpSessionId");

		String sessionId = sessionId(user.getUserId(), session.getId());
		sessionMap.put(sessionId, session);

		sessionIdMap.put(httpSessionId, sessionId);


		List<DataAccessRole> roles = user.getDataAccessRoleList();


		if(roles != null && roles.size() > 0) {
			String roleKey = roles.get(0).getRoleCd();
			for (int i = 1; i < roles.size(); i++) {
				roleKey = roleKey + "-" + roles.get(i).getRoleCd();
			}
			session.getAttributes().put("roleKey", roleKey);
		}




		logger.info(
				Util.join(
						"\n---------------------------------------------------",
						"\nfront channel login user(sessionId:",sessionId, "httpSessionId:",httpSessionId,"):",
						"\n---------------------------------------------------"));

		logger.debug("--------------------------------------------------");
		logger.debug(getFrontChannelInfo());
		logger.debug("--------------------------------------------------");
	}

	/**
	 *
	 * @param session
	 */
	public void logout(WebSocketSession session) {

		if (session.getAttributes().get("user") == null) {
			logger.info(Util.join("The websocket session:", session.getId() , " have no user."));
		}else {

			User user = (User) session.getAttributes().get("user");
			String httpSessionId = (String)session.getAttributes().get("httpSessionId");
			String sessionId = sessionId(user.getUserId(), session.getId());
			try {
				/*
				List<DataAccessRole> roles = user.getDataAccessRoleList();
				if(roles == null || roles.size() == 0) {
					return;
				}
				String roleKey = roles.get(0).getRoleCd();
				for (int i = 1; i < roles.size(); i++) {
					roleKey = roleKey + "-" + roles.get(i).getRoleCd();
				}
				*/

				String roleKey = (String)session.getAttributes().get("roleKey");
				Iterator<String> services = serviceAccessRoleMap.keySet().iterator();
				while (services.hasNext()) {
					String serviceCd = services.next();

					try {
						serviceOff(roleKey, serviceCd, sessionId);
					} catch (Exception e) {
						e.printStackTrace();
					}


				}
			}finally {
				logger.info(
						Util.join(
								"\n---------------------------------------------------",
								"\nfront channel logout user(", sessionId, "):",
								"\n---------------------------------------------------"));
				sessionMap.remove(sessionId);
				sessionIdMap.remove(httpSessionId);

				logger.debug("--------------------------------------------------");
				logger.debug(getFrontChannelInfo());
				logger.debug("--------------------------------------------------");
			}
		}
	}


	public String getFrontChannelInfo() {
		StringBuffer info = new StringBuffer("\nFrontChannelInfo\n");
		try {
			StringBuffer sessionMapInfo = new StringBuffer();
			StringBuffer sessionIdMapInfo = new StringBuffer();
			StringBuffer serviceAccessRoleMapInfo = new StringBuffer();

			sessionMapInfo.append("1.sessionMap info\n");
			sessionMapInfo.append("	sessionMap.size():").append(sessionMap.size()).append("\n");
			sessionMapInfo.append("	sessionMap.keys:").append(Util.toJSONString(sessionMap.keySet().toArray())).append("\n");
			info.append(sessionMapInfo);

			sessionIdMapInfo.append("2.sessionIdMap\n");
			sessionIdMapInfo.append("	sessionIdMap.size():").append(sessionIdMap.size()).append("\n");
			sessionIdMapInfo.append("	sessionIdMap.keys:").append(Util.toJSONString(sessionIdMap.keySet().toArray())).append("\n");
			sessionIdMapInfo.append("	sessionIdMap.values:").append(Util.toJSONString(sessionIdMap.values().toArray())).append("\n");
			info.append(sessionIdMapInfo);

			serviceAccessRoleMapInfo.append("3.serviceAccessRoleMap info\n");
			serviceAccessRoleMapInfo.append("	serviceAccessRoleMap.size():").append(serviceAccessRoleMap.size()).append("\n");
			serviceAccessRoleMapInfo.append("	serviceAccessRoleMap.keys:").append(Util.toJSONString(serviceAccessRoleMap.keySet().toArray())).append("\n");
			serviceAccessRoleMapInfo.append("	serviceAccessRoleMap.values:").append(Util.toJSONString(serviceAccessRoleMap.values().toArray())).append("\n");
			info.append(serviceAccessRoleMapInfo);

		}catch(Exception e) {}
		return info.toString();
	}

	/**
	 * @param roleKey
	 * @param serviceCd
	 * @param session
	 */
	private void serviceOff(String roleKey, String serviceCd, String sessionId) throws Exception{
		// TODO Auto-generated method stub
		Map<String, List<String>> roleMap = serviceAccessRoleMap.get(serviceCd);
		if(roleMap != null && roleMap.size() > 0) {
			List<String> userSessionList = roleMap.get(roleKey);
			if (userSessionList != null && userSessionList.size() > 0) {
				boolean ok = userSessionList.remove(sessionId);
				if(userSessionList.size() == 0) {
					roleMap.remove(roleKey);
					if(roleMap.size() == 0) {
						serviceAccessRoleMap.remove(serviceCd);
					}
				}
			}
		}
	}

	/**
	 *
	 * @param serviceCd
	 * @param session
	 * @throws Exception
	 */
	public void serviceOn(String serviceCd, WebSocketSession session) throws Exception {

		User user = (User) session.getAttributes().get("user");
		if (user == null) {
			throw new WebServiceException(Util.join("Exception:Not found User object in websocket session"));
		}

		if (!serviceAccessRoleMap.containsKey(serviceCd)) {
			serviceAccessRoleMap.put(serviceCd, new HashMap<String, List<String>>());
		}

		String roleKey = (String)session.getAttributes().get("roleKey");
		if(roleKey == null ) {
			throw new WebServiceException(Util.join("Exception:User(", user.getUserId(), ") have no any access role for service(",serviceCd ,") on."));
		}

		Map<String, List<String>> roleMap = serviceAccessRoleMap.get(serviceCd);
		if (!roleMap.containsKey(roleKey)) {
			roleMap.put(roleKey, new ArrayList<String>());
		}

		List<String> userSessionList = roleMap.get(roleKey);
		String sessionId = sessionId(user.getUserId(), session.getId());
		if (!userSessionList.contains(sessionId)) {
			userSessionList.add(sessionId);
		}

		logger.info(
				Util.join(
						"\n---------------------------------------------------",
						"\nfront channel serviceOn user(sessionId:", sessionId, ",serviceCd:", serviceCd, ",roleKey:", roleKey,"):",
						"\n---------------------------------------------------"));
		logger.debug("--------------------------------------------------");
		logger.debug(getFrontChannelInfo());
		logger.debug("--------------------------------------------------");
	}

	/**
	 *
	 * @param serviceCd
	 * @param session
	 * @throws Exception
	 */
	public void serviceOff(String serviceCd, WebSocketSession session) throws Exception {
		User user = (User) session.getAttributes().get("user");
		if (user == null) {
			throw new Exception(Util.join("Exception:Not found User object in websocket session"));
		}

		String roleKey = "";
		List<DataAccessRole> roles = user.getDataAccessRoleList();
		if(roles == null || roles.size() == 0) {
			return;
			//throw new WebServiceException(Util.join("Exception:User(", user.getUserId(), ") have no any access role for service on."));
		}

		roleKey = roles.get(0).getRoleCd();
		for (int i = 1; i < roles.size(); i++) {
			roleKey = roleKey + "-" + roles.get(i).getRoleCd();
		}

		String sessionId = sessionId(user.getUserId(), session.getId());

		serviceOff(roleKey, serviceCd, sessionId);

		logger.info(
				Util.join(
						"\n---------------------------------------------------",
						"\nfront channel serviceOff user(sessionId:", sessionId, ",serviceCd:", serviceCd, ",roleKey:", roleKey,"):",
						"\n---------------------------------------------------"));
		logger.debug("--------------------------------------------------");
		logger.debug(getFrontChannelInfo());
		logger.debug("--------------------------------------------------");
	}

	/**
	 *
	 * @param sessionId
	 * @param msg
	 * @throws Exception
	 */
	public void sendMessage(String sessionId, String msg) throws Exception {
		WebSocketSession session = sessionMap.get(sessionId);

		if (session != null) {
			synchronized (session) {
				TextMessage tmsg = new TextMessage(msg);
				session.sendMessage(tmsg);
				logger.debug(Util.join("send to ", sessionId, ", msg:\n", msg));
			}
		} else {
			logger.debug(Util.join("Exception:Not found userId(", sessionId, ")'s websocket session","\n 아마도 세션이 만료된 후 메시지 전송을 시도하는 것이므로 이경우 세션ID를 정리해주면 어떨까?"));
		}
	}

	public String getUserId(String sessionId) {
		if (Util.isEmpty(sessionId))
			return "";
		int lastIndex = sessionId.lastIndexOf(SESSION_DELIM);
		String userId = sessionId.substring(0, lastIndex);
		return userId;
	}

	public String sessionId(String userId, String wssId) {
		return Util.join(userId, SESSION_DELIM, wssId);
	}

	public String getSessionId(String httpSessionId) {
		return sessionIdMap.get(httpSessionId);
	}

	public void logout(String httpSessionId) {
		logout(httpSessionId, CloseStatus.GOING_AWAY);
	}

	public void logout(String httpSessionId, CloseStatus status) {
		try {
			String sessionId = getSessionId(httpSessionId);
			if(sessionId != null) {
				WebSocketSession session = sessionMap.get(sessionId);
				if(session != null) session.close(status);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param pushAccessRoleUser
	 */
	public void setPreAccessRoleUser(User user, String sessionId, List<String> serviceCdList) {

		for(String serviceCd : serviceCdList) {

			if (!serviceAccessRoleMap.containsKey(serviceCd)) {
				serviceAccessRoleMap.put(serviceCd, new HashMap<String, List<String>>());
			}

			List<DataAccessRole> roles = user.getDataAccessRoleList();


			if(roles == null || roles.size() == 0) {
				throw new WebServiceException(Util.join("Exception:User(", user.getUserId(), ") have no any access role for service(",serviceCd ,") on."));
			}


			String roleKey = roles.get(0).getRoleCd();
			for (int i = 1; i < roles.size(); i++) {
				roleKey = roleKey + "-" + roles.get(i).getRoleCd();
			}

			Map<String, List<String>> roleMap = serviceAccessRoleMap.get(serviceCd);
			if (!roleMap.containsKey(roleKey)) {
				roleMap.put(roleKey, new ArrayList<String>());
			}

			List<String> userSessionList = roleMap.get(roleKey);
			if (!userSessionList.contains(sessionId)) {
				userSessionList.add(sessionId);
			}

			logger.info(
					Util.join(
							"\n---------------------------------------------------",
							"\nfront channel serviceOn user(sessionId:", sessionId, ",serviceCd:", serviceCd, ",roleKey:", roleKey,"):",
							"\n---------------------------------------------------"));
			logger.debug("--------------------------------------------------");
			logger.debug(getFrontChannelInfo());
			logger.debug("--------------------------------------------------");


		}

	}

	/**
	 * @param serviceCd
	 * @param msg
	 */
	public void savePushData(String serviceCd, String roleKey, String msg) {
		if(!pushDataMap.containsKey(serviceCd)) {
			pushDataMap.put(serviceCd, new HashMap<String,Object>());
		}
		Map<String,Object> dataMap = pushDataMap.get(serviceCd);
		dataMap.put(roleKey, msg);
	}

	/**
	 * @param sessionId
	 * @param serviceCd
	 */
	public void sendFirstData(String sessionId, String serviceCd, String roleKey) {

		if(pushDataMap.containsKey(serviceCd)) {
			String msg = (String)pushDataMap.get(serviceCd).get(roleKey);
			if(msg != null) {
				try {
					sendMessage(sessionId, msg);
				} catch (Exception e) {
					logger.debug("무시해도 되는 에러:",e);
				}
			}
		}
	}

	/**
	 * @return
	 */
	public int getLoginCount() {
		return sessionMap.size();
	}


	public int getServiceAccessRoleCount(String roleKey, String serviceCd) throws Exception {
		int res = 1;
		if(serviceAccessRoleMap.get(serviceCd) != null) {
			Map<String, List<String>> accessRoleMap = serviceAccessRoleMap.get(serviceCd);
			if(!accessRoleMap.containsKey(roleKey)) {
				res = accessRoleMap.size();
			}
		}
		return res;
	}


	public Map<String, WebSocketSession>  getSessionMap(){
		return this.sessionMap;
	}


}
