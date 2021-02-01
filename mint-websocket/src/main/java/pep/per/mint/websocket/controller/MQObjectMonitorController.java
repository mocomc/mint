/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.qmgr.QueueProperty;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.event.ServiceEvent;
import pep.per.mint.websocket.exception.WebsocketException;
import pep.per.mint.websocket.service.AgentChannelService;
import pep.per.mint.websocket.service.FrontChannelService;
import pep.per.mint.websocket.service.MQObjectMonitorService;

/**
 * <pre>
 * pep.per.mint.websocket.controller
 * MQObjectMonitorService.java
 * </pre>
 * @author whoana
 * @date 2018. 9. 27.
 */
@Controller
@ServiceRouter
public class MQObjectMonitorController {

	Logger logger = LoggerFactory.getLogger(MQObjectMonitorController.class);

	@Autowired
	MQObjectMonitorService mqObjectMonitorService;

	@Autowired
	FrontChannelService frontChannelService;

	@Autowired
	AgentChannelService agentChannelService;

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	WebsocketEnvironments envs;




	/**
	 * 큐매니저 List 조회 요청(WS0051)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0051, type=ServiceCode.MSG_TYPE_REQ)
	public void reqQmgrList(ServiceEvent se) throws Exception {

		if(se.getType() == ServiceEvent.TYPE_AGENT) {
			logger.error(Util.join("This service (Variables.SERVICE_CD_WS0051, type=ServiceCode.MSG_TYPE_REQ) type(",se.getType(),") must be TYPE_FRONT(1). so can't execute the service."));
			return;
		}

		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			Map params = (Map) msg.getRequestObject();

			if(params == null ) {
				throw new WebsocketException(Util.join("The service[",serviceCd,"] message from front have no field value agentNm."));
			}
			String agentNm = (String) params.get("agentNm");

			if(agentNm == null || agentNm.length() == 0) {
				throw new WebsocketException(Util.join("The service[",serviceCd,"] message from front have no field value agentNm."));
			}

			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());

			String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);

			if(agentSessionId == null) {
				throw new WebsocketException(Util.join("can't send the service[",serviceCd,"] message to the agent. The agent[",agentNm,"] did not login."));
			}

			se.getRequest().getExtension().setFrontSessionId(sessionId);

			String strMsg = messageHandler.serialize(msg);

			agentChannelService.sendMessage(agentSessionId, strMsg);

		} catch (Exception e) {
			logger.error("", e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.fail", null, Locale.getDefault()));
			Object [] errorMsgParams = {serviceCd, e!= null ? e.getMessage() : ""};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.fail", errorMsgParams, Locale.getDefault()));

			boolean setDetail = false;
			if(setDetail) {
				String errorDetail = "";
				PrintWriter pw = null;
				try{
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					pw = new PrintWriter(baos);
					e.printStackTrace(pw);
					pw.flush();
					if(pw != null)  errorDetail = baos.toString();
				}finally{
					if(pw != null) pw.close();
				}
				msg.setErrorDetail(errorDetail);
			}
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_RES);
			String strMsg = messageHandler.serialize(msg);
			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());
			frontChannelService.sendMessage(sessionId, strMsg);

		} finally {

		}
	}

	/**
	 * 큐매니저 List 조회 응답(WS0051)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0051, type=ServiceCode.MSG_TYPE_RES)
	public void resQmgrList(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			sessionId = msg.getExtension().getFrontSessionId();
			if(sessionId == null || sessionId.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value frontSessionId"));
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);
		} catch (Exception e) {
			logger.error("", e);
		} finally {

		}
	}

	/**
	 * 큐 List 조회 요청(WS0052)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0052, type=ServiceCode.MSG_TYPE_REQ)
	public void reqQueueList(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			Map params = (Map) msg.getRequestObject();
			String agentNm = (String) params.get("agentNm");
			if(agentNm == null || agentNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from front have no field value agentNm."));
			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());

			String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
			if(agentSessionId == null) {
				throw new WebsocketException(Util.join("can't send the service[",serviceCd,"] message to the agent. The agent[",agentNm,"] did not login."));
			}

			se.getRequest().getExtension().setFrontSessionId(sessionId);
			String strMsg = messageHandler.serialize(msg);
			agentChannelService.sendMessage(agentSessionId, strMsg);

		} catch (Exception e) {
			logger.error("", e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.fail", null, Locale.getDefault()));
			Object [] errorMsgParams = {serviceCd, e!= null ? e.getMessage() : ""};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.fail", errorMsgParams, Locale.getDefault()));

			String errorDetail = "";
			PrintWriter pw = null;
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pw = new PrintWriter(baos);
				e.printStackTrace(pw);
				pw.flush();
				if(pw != null)  errorDetail = baos.toString();
			}finally{
				if(pw != null) pw.close();
			}
			msg.setErrorDetail(errorDetail);

			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_RES);
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);

		} finally {

		}
	}

	/**
	 * 큐 List 조회 응답(WS0052)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0052, type=ServiceCode.MSG_TYPE_RES)
	public void resQueueList(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			sessionId = msg.getExtension().getFrontSessionId();
			if(sessionId == null || sessionId.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value frontSessionId"));
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);
		} catch (Exception e) {
			logger.error(Util.join("The service[",serviceCd,"] ack exception:"), e);
		} finally {

		}
	}


	/**
	 * 큐 IN/OUT COUNT 정보 요청 SERVICE ON(WS0053)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_ON)
	public void serviceOnQueueInfo(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();


			Map params = (Map) msg.getRequestObject();
			String agentNm = (String) params.get("agentNm");
			String qmgrNm = (String) params.get("qmgrNm");
			String queueNm = (String) params.get("queueNm");

			if(agentNm == null || agentNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value agentNm"));
			if(qmgrNm == null || qmgrNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value qmgrNm"));
			if(queueNm == null || queueNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value queueNm"));

			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());

			/*if(mqObjectMonitorService.existQueueMonitor(qmgrNm, queueNm)) {

				mqObjectMonitorService.serviceOnQueueMonitor(sessionId, qmgrNm, queueNm);

				msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK);
				msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
				msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
				String strMsg = messageHandler.serialize(msg);
				frontChannelService.sendMessage(sessionId, strMsg);


			}else {
				//----------------------------------------------------------
				// 에이전트로 요청을 매번 보내는 것으로 변경해준다.(대신 에이전트에서 이미 쓰레드가 떠있으면 스킵하도록 처리
				//-----------------------------------------------------------
				String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
				if(agentSessionId == null) {
					throw new WebsocketException(Util.join("can't send the service[",serviceCd,"] message to the agent. The agent[",agentNm,"] did not login."));
				}

				se.getRequest().getExtension().setFrontSessionId(sessionId);
				String strMsg = messageHandler.serialize(msg);
				agentChannelService.sendMessage(agentSessionId, strMsg);
			}*/

			//----------------------------------------------------------
			// 에이전트로 요청을 매번 보내는 것으로 변경해준다.(대신 에이전트에서 이미 쓰레드가 떠있으면 스킵하도록 처리
			//-----------------------------------------------------------
			String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
			if(agentSessionId == null) {
				throw new WebsocketException(Util.join("can't send the service[",serviceCd,"] message to the agent. The agent[",agentNm,"] did not login."));
			}

			se.getRequest().getExtension().setFrontSessionId(sessionId);
			agentChannelService.sendMessage(agentSessionId, messageHandler.serialize(msg));

			mqObjectMonitorService.serviceOnQueueMonitor(sessionId, qmgrNm, queueNm);
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK);
			msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
			msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
			frontChannelService.sendMessage(sessionId, messageHandler.serialize(msg));


		} catch (Exception e) {

			logger.error(Util.join("The service[",serviceCd,"] on exception:"), e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.on.fail", null, Locale.getDefault()));
			Object [] errorMsgParams = {serviceCd};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.on.fail", errorMsgParams, Locale.getDefault()));
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK);
			frontChannelService.sendMessage(sessionId, messageHandler.serialize(msg));

		} finally {

		}

	}

	/**
	 * 큐 IN/OUT COUNT 정보 PUSH 요청 ACK(WS0053)
	 * 에이전트로 부터 큐 모니터링 서비스온 요청에 대한 ACK 도착시 처리
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_ACK)
	public void ackQueueInfo(ServiceEvent se) throws Exception {

		ComMessage msg = null;
		String serviceCd = "";
		String sessionId = null;
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			sessionId = msg.getExtension().getFrontSessionId();


			Map params = (Map) msg.getRequestObject();
			String qmgrNm = (String) params.get("qmgrNm");
			String queueNm = (String) params.get("queueNm");


			if(qmgrNm == null || qmgrNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value qmgrNm"));
			if(queueNm == null || queueNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value queueNm"));

			String errorCd = msg.getErrorCd();

			if(!"0".equals(errorCd)) {
				//mqObjectMonitorService.serviceOffAllQueueMonitor(qmgrNm, queueNm);
				mqObjectMonitorService.serviceOffQueueMonitor(sessionId, qmgrNm, queueNm);
				msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.on.fail", null, Locale.getDefault()));
				Object [] errorMsgParams = {serviceCd};
				msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.on.fail", errorMsgParams, Locale.getDefault()));


			}else {
				mqObjectMonitorService.serviceOnQueueMonitor(sessionId, qmgrNm, queueNm);
				msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
				msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
			}

			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

			if(sessionId != null && sessionId.length() > 0) {
				try {
					String strMsg = messageHandler.serialize(msg);
					frontChannelService.sendMessage(sessionId, strMsg);
				}catch(Exception e) {
					logger.error("예외처리필요:큐모니터-서버에서프론트ACK전송시:",e);
				}
			}


		} catch (Exception e) {
			logger.error(Util.join("The service[",serviceCd,"] ack exception:"), e);
		} finally {

		}

	}


	/**
	 * 큐 IN/OUT COUNT 정보 요청 SERVICE ON(WS0053)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_OFF)
	public void serviceOffQueueInfo(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();

			Map params = (Map) msg.getRequestObject();
			String agentNm = (String) params.get("agentNm");
			String qmgrNm = (String) params.get("qmgrNm");
			String queueNm = (String) params.get("queueNm");

			if(agentNm == null || agentNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value agentNm"));
			if(qmgrNm == null || qmgrNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value qmgrNm"));
			if(queueNm == null || queueNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value queueNm"));

			sessionId = frontChannelService.sessionId(msg.getUserId(), se.getSession().getId());

			mqObjectMonitorService.serviceOffQueueMonitor(sessionId, qmgrNm, queueNm);

			if(!mqObjectMonitorService.existQueueMonitor(qmgrNm, queueNm)) {
				//큐모니터가 존재하지 않으면 에이전트에게 SERVICE OFF 보낸다.
				String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
				if(agentSessionId != null) {
					msg.getExtension().setMsgType(Extension.MSG_TYPE_OFF);
					se.getRequest().getExtension().setFrontSessionId(sessionId);
					agentChannelService.sendMessage(agentSessionId, messageHandler.serialize(msg));
				}
			}

			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK_OFF);
			msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
			msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
			frontChannelService.sendMessage(sessionId, messageHandler.serialize(msg));


		} catch (Exception e) {

			logger.error(Util.join("The service[",serviceCd,"] ack exception:"), e);
			msg.setErrorCd(messageSource.getMessage("error.cd.ws.service.off.fail", null, Locale.getDefault()));
			serviceCd = se.getRequest().getExtension().getServiceCd();
			Object [] errorMsgParams = {serviceCd, e!= null ? e.getMessage() : ""};
			msg.setErrorMsg(messageSource.getMessage("error.msg.ws.service.off.fail", errorMsgParams, Locale.getDefault()));
			msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			msg.getExtension().setMsgType(Extension.MSG_TYPE_ACK_OFF);
			String strMsg = messageHandler.serialize(msg);
			frontChannelService.sendMessage(sessionId, strMsg);

		} finally {

		}

	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_ACK_OFF)
	public void accOffQueueInfo(ServiceEvent se) throws Exception {


		ComMessage msg = null;
		String sessionId = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();

			Map params = (Map) msg.getRequestObject();
			String agentNm = (String) params.get("agentNm");
			String qmgrNm = (String) params.get("qmgrNm");
			String queueNm = (String) params.get("queueNm");

			if(agentNm == null || agentNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value agentNm"));
			if(qmgrNm == null || qmgrNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value qmgrNm"));
			if(queueNm == null || queueNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] message from agent have no field value queueNm"));

			logger.info(Util.join("The service[",serviceCd,"], qmgrNm[",qmgrNm,"],queueNm[",queueNm,"] ack(off) received from agent:",agentNm));

		} catch (Exception e) {

			logger.error(Util.join("The service[",serviceCd,"] ack(off) exception:"), e);

		} finally {

		}

	}


	/**
	 * 큐 IN/OUT COUNT 정보 PUSH (WS0053)
	 * 에이전트로 부터 큐정보가 도착했을때 처리(프론트로 PUSH 처리)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0053, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushQueueInfo(ServiceEvent se) throws Exception {
		ComMessage msg = null;
		String serviceCd = "";
		try {
			msg = se.getRequest();
			serviceCd = msg.getExtension().getServiceCd();
			QueueProperty queue = (QueueProperty)msg.getResponseObject();

			if(msg == null || msg.getRequestObject() == null) throw new WebsocketException(Util.join("The service[",serviceCd,"]push message from agent have no field value agentNm"));
			Map params = (Map) msg.getRequestObject();

			String agentNm = (String) params.get("agentNm");
			if(agentNm == null || agentNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"]push message from agent have no field value agentNm"));

			params.put("delay", 1);

			if(queue != null) {
				String qmgrNm = queue.getQmgrName();
				String queueNm = queue.getName();

				if(qmgrNm == null || qmgrNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] push message from agent have no field value qmgrNm"));
				if(queueNm == null || queueNm.length() == 0) throw new WebsocketException(Util.join("The service[",serviceCd,"] push message from agent have no field value queueNm"));

				List<String> rawFormatData = null;

				if(!envs.rawFormatObjectMonitor) {
					msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					msg.setErrorCd(messageSource.getMessage("success.cd.ok", null, Locale.getDefault()));
					msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));
				}else {
					rawFormatData = new ArrayList<String>();
					int depth = queue.getCurrentDepth();
					int inputCount = queue.getOpenInputCount();
					int outputCount = queue.getOpenOutputCount();
					rawFormatData.add(qmgrNm);
					rawFormatData.add(queueNm);
					rawFormatData.add(depth + "");
					rawFormatData.add(inputCount + "");
					rawFormatData.add(outputCount + "");
					rawFormatData.add(msg.getStartTime());
				}

				List<String> sessionList = mqObjectMonitorService.getFrontSessionIdList(qmgrNm, queueNm);
				if(sessionList != null && sessionList.size() > 0) {
					for(String sessionId : sessionList) {
						try {
							if(!envs.rawFormatObjectMonitor) {
								msg.getExtension().setMsgType(Extension.MSG_TYPE_PUSH);
								frontChannelService.sendMessage(sessionId, messageHandler.serialize(msg));
							}else {
								frontChannelService.sendMessage(sessionId, messageHandler.serialize(rawFormatData));
							}
						}catch(Exception e) {
							//push할 프론트세션이 유효하지 않아 SERVICE OFF 처리
							try{
								mqObjectMonitorService.serviceOffQueueMonitor(sessionId, qmgrNm, queueNm);

								if(!mqObjectMonitorService.existQueueMonitor(qmgrNm, queueNm)) {
									//큐모니터가 존재하지 않으면 에이전트에게 SERVICE OFF 보낸다.

									String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
									msg.getExtension().setMsgType(Extension.MSG_TYPE_OFF);
									se.getRequest().getExtension().setFrontSessionId(sessionId);
									String strMsg = messageHandler.serialize(msg);
									agentChannelService.sendMessage(agentSessionId, strMsg);
								}
							}catch(Exception ex) {
								logger.error(Util.join("The service[",serviceCd,"] push할 프론트세션이 유효하지 않아 SERVICE OFF 처리시 exception:"), ex);
							}

							continue;
						}
					}
				}else {
					try{

						logger.info(mqObjectMonitorService.getMQObjectMonitorChannelInfo());
						logger.info(Util.join("The service[",serviceCd,"] push할 프론트세션이 존재하지 않아 SERVICE OFF 처리함:agentNm:", agentNm,", qmgrNm:", qmgrNm, ", queueNm:", queueNm));

						if(!mqObjectMonitorService.existQueueMonitor(qmgrNm, queueNm)) {
							//큐모니터가 존재하지 않으면 에이전트에게 SERVICE OFF 보낸다.
							String agentSessionId = agentChannelService.findSessionIdAtFirst(agentNm);
							msg.getExtension().setMsgType(Extension.MSG_TYPE_OFF);

							se.getRequest().getExtension().setFrontSessionId("server-session");
							String strMsg = messageHandler.serialize(msg);
							agentChannelService.sendMessage(agentSessionId, strMsg);
						}
					}catch(Exception ex) {
						logger.error(Util.join("The service[",serviceCd,"] push할 프론트세션이 유효하지 않아 SERVICE OFF 처리시 exception:"), ex);
					}
				}
			}


		} catch (Exception e) {

			logger.error(Util.join("The service[",serviceCd,"] push exception:"), e);

		} finally {

		}

	}


}
