/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.controller.rest.im;


import java.util.Locale;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.data.basic.Extension;

import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.AgentChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.controller.rest.im
 * ConfigManageController.java
 * </pre>
 * @author whoana
 * @date Nov 1, 2018
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigManageController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigManageController.class);

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


	@RequestMapping(value = "/nh/config/update", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<ConfigInfo, ?> updateConfig(HttpSession httpSession, @RequestBody ComMessage<ConfigInfo, ?> comMessage, Locale locale, HttpServletRequest request) throws Exception {

			String errorCd = "";
			String errorMsg = "";


			try {

				ConfigInfo config = comMessage.getRequestObject();
				if (config == null){
					throw new Exception(Util.join("에이전트요청실패:ConfigInfo = null."));
				}

				String agentNm = config.getAgentNm();

				if (Util.isEmpty(agentNm)){
					throw new Exception(Util.join("에이전트요청실패:ConfigInfo.agentNm = null."));
				}

				Extension ext = new Extension();
				ext.setServiceCd(Variables.SERVICE_CD_WS0054);
				ext.setMsgType(Extension.MSG_TYPE_REQ);
				comMessage.setExtension(ext);


				String msg = messageHandler.serialize(comMessage);
				String sessionId = agentChannelService.findSessionIdAtFirst(agentNm);
				if(Util.isEmpty(sessionId)) throw new Exception(Util.join("에이전트요청실패:에이전트[",agentNm,"] 로그오프상태."));
				agentChannelService.sendMessage(sessionId, msg);
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.ok", null, locale);

			}catch(Exception e) {
				logger.error("",e);
				errorCd = messageSource.getMessage("error.cd.update.fail", null, locale);
				Object[] errorMsgParams = {"농협인터페이스Config비교결과update",e.getMessage()};
				errorMsg = messageSource.getMessage("error.msg.update.fail", errorMsgParams, locale);
			}finally {
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;


	}

}
