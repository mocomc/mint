/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.controller.rest.im;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.AgentChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.controller.im
 * AgentChannelManagementController.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 29.
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AgentChannelManagementController {

	private static final Logger logger = LoggerFactory.getLogger(AgentChannelManagementController.class);

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	AgentChannelService agentChannelService;



	@Autowired
	MessageHandler messageHandler;


	@RequestMapping(value = "/agent-channels/infos", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> getSessionInfo(
			HttpSession httpSession,
			@RequestBody ComMessage<?, String> comMessage,
			Locale locale,
			HttpServletRequest request) throws Exception {

		String info = agentChannelService.getAgentChannelInfo();
		comMessage.setResponseObject(info);

		return comMessage;

	}


	@RequestMapping(value = "/agent-channels/sessionids/{agentnm}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<String>> getSessionIdMap(
			HttpSession httpSession,
			@RequestBody ComMessage<?, List<String>> comMessage,
			@PathVariable("agentnm") String agentNm,
			Locale locale,
			HttpServletRequest request) throws Exception {

		List<String> sessionIds = agentChannelService.getSessionIdList(agentNm);
		comMessage.setResponseObject(sessionIds);

		return comMessage;

	}

	@RequestMapping(value = "/agent-channels/logout/{agentnm}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<String>> logoutAgent(
			HttpSession httpSession,
			@RequestBody ComMessage<?, List<String>> comMessage,
			@PathVariable("agentnm") String agentNm,
			Locale locale,
			HttpServletRequest request) throws Exception {

		List<String> sessionIds = agentChannelService.getSessionIdList(agentNm);
		comMessage.setResponseObject(sessionIds);
		if(sessionIds != null && sessionIds.size() > 0 ) {
			for(String sessionId : sessionIds) {
				WebSocketSession session = agentChannelService.getSessionMap().get(sessionId);
				if(session != null) {
					agentChannelService.logout(session);
					logger.info(Util.join("session:",sessionId," logout by front command"));
				}

			}
		}
		return comMessage;

	}



}
