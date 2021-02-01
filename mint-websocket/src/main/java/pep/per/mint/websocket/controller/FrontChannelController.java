package pep.per.mint.websocket.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.event.ServiceEvent;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 *
 * <pre>
 * pep.per.mint.websocket.controller
 * FrontChannelController.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
@Controller
@ServiceRouter
public class FrontChannelController {

	Logger logger = LoggerFactory.getLogger(FrontChannelController.class);

	@Autowired
	FrontChannelService frontChannelService;

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	WebsocketEnvironments envs;

	@ServiceCode(code="serviceOn", type=ServiceCode.MSG_TYPE_ON)
	public void serviceOn(ServiceEvent se) throws Exception {

		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		String roleKey = null;
		boolean ok = false;
		try {
			msg = se.getRequest();

			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());
			serviceCd = se.getRequest().getExtension().getServiceCd();
			roleKey = (String)se.getSession().getAttributes().get("roleKey");
			int serviceCount = frontChannelService.getServiceAccessRoleCount(roleKey, serviceCd);

			if(serviceCount <= envs.maxServiceCount) {
				frontChannelService.serviceOn(serviceCd, se.getSession());
				msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
				msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
				ok = true;
			}else {
				msg.setErrorCd(messageSource.getMessage("error.cd.ws.too.much.service.count", null, Locale.getDefault()));
				Object [] errorMsgParams = {envs.maxServiceCount, serviceCount};
				msg.setErrorMsg(messageSource.getMessage("error.msg.ws.too.much.service.count", errorMsgParams, Locale.getDefault()));
				ok = false;
			}

		} catch (Exception e) {
			logger.error("", e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.on.fail", null, Locale.getDefault()));
			Object [] errorMsgParams = {serviceCd};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.on.fail", errorMsgParams, Locale.getDefault()));
			ok = false;

		} finally {
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK);
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);
			if(ok) frontChannelService.sendFirstData(sessionId, serviceCd, roleKey);
		}

	}

	@ServiceCode(code="serviceOff", type=ServiceCode.MSG_TYPE_OFF)
	public void serviceOff(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());
			serviceCd = se.getRequest().getExtension().getServiceCd();
			frontChannelService.serviceOff(serviceCd, se.getSession());
			msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
			msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
		} catch (Exception e) {
			logger.error("", e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.on.fail", null, Locale.getDefault()));
			Object [] errorMsgParams = {serviceCd};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.on.fail", errorMsgParams, Locale.getDefault()));
			//setErrorDetail(msg, e);
		} finally {
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK);
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);
		}

	}

	/**
	 * @param msg
	 */
	private void setErrorDetail(ComMessage msg,Exception e) {
		PrintWriter pw = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			pw = new PrintWriter(baos);
			e.printStackTrace(pw);
			pw.flush();
			if(pw != null)  {
				msg.setErrorDetail(baos.toString());
			}
		}finally{
			if(pw != null) pw.close();
		}

	}

	public void login(ServiceEvent<?, ?> se) throws Exception {
		int loginCount = frontChannelService.getLoginCount();
		if(loginCount > envs.maxLoginCount) {

			//"1013 indicates that the service is experiencing overload.
			//A client should only connect to a different IP (when there are multiple for the target) or reconnect to the same IP upon user action."
			se.getSession().close(CloseStatus.SERVICE_OVERLOAD);
			return;
		}
		frontChannelService.login(se.getSession());
	}

	public void logout(ServiceEvent<?, ?> se) {
		frontChannelService.logout(se.getSession());
	}




}
