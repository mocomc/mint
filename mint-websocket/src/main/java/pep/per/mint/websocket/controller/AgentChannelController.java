package pep.per.mint.websocket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.ChannelStatusLog;
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.data.basic.agent.MonitorItem;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.data.basic.email.Recipient;
import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.data.basic.sms.SmsResource;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.QmgrStatusLog;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.QueueStatusLog;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.im.ConfigMngService;
import pep.per.mint.database.service.im.EngineMonitorService;
import pep.per.mint.database.service.im.ServerAppMngService;
import pep.per.mint.database.service.im.TestInterfaceCallService;
import pep.per.mint.database.service.op.AlarmService;
import pep.per.mint.database.service.op.IIPAgentService;
import pep.per.mint.database.service.op.LocalCmdLogService;
import pep.per.mint.database.service.su.EmailService;
import pep.per.mint.database.service.su.SMSService;
import pep.per.mint.inhouse.mail.SendMailService;
import pep.per.mint.inhouse.sms.SendSMSService;
import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.event.ServiceEvent;
import pep.per.mint.websocket.exception.WebsocketException;
import pep.per.mint.websocket.service.AgentChannelService;
import pep.per.mint.websocket.service.SystemStatusService;

/**
 *
 * <pre>
 * pep.per.mint.websocket.controller
 * AgentChannelController.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 2.
 */
@Controller
@ServiceRouter
public class AgentChannelController {

	Logger logger = LoggerFactory.getLogger(AgentChannelController.class);

	@Autowired
	TestInterfaceCallService testInterfaceCallService;

	@Autowired
	AgentChannelService agentChannelService;

	@Autowired
	EngineMonitorService engineMonitorService;

	@Autowired
	IIPAgentService iipAgentService;

	@Autowired
	AlarmService alarmService;

	@Autowired
	ServerAppMngService serverAppMngService;

	@Autowired
	SMSService smsService;

	@Autowired
	SendSMSService sendSMSService;

	@Autowired
	EmailService emailService;

	@Autowired
	@Qualifier("simpleSendMailService")
	SendMailService sendMailService;


	@Autowired
	MessageHandler messageHandler;

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	SystemStatusService systemStatusService;

	@Autowired
	LocalCmdLogService localCmdLogService;

	/**
	 * login
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0025, type=ServiceCode.MSG_TYPE_REQ)
	@Transactional
	public void login(ServiceEvent se) throws Exception {

		ComMessage msg = null;
		String sessionId = null;
		IIPAgentInfo agentInfo = null;
		IIPAgentLog log = new IIPAgentLog();

		String getDate = Util.getFormatedDate("yyyyMMddHHmmssSSS");
		log.setGetDate(getDate);
		log.setRegApp(this.getClass().getName());
		log.setRegDate(getDate);

		try {
			msg = se.getRequest();
			String agentNm = msg.getUserId();
			//sessionId = agentNm;
			sessionId = agentChannelService.sessionId(agentNm, se.getSession().getId());

			String agentId = engineMonitorService.getAgentInfo(agentNm);
			agentInfo = engineMonitorService.getAgentDetailInfoWithQmgrInfoMap(agentId);

			if(agentId == null || agentId.length() == 0 || agentInfo == null) {

				//msg.setErrorCd(messageSource.getMessage("eerror.cd.login.invalid.userId",null, Locale.getDefault()));
				//msg.setErrorMsg(messageSource.getMessage("error.msg.login.invalid.userId", null, Locale.getDefault()));
				//log.setMsg(Util.join("agentNm[",agentNm,"] 으로 에이전트 정보를 조회할 수 없음"));
				//log.setStatus(IIPAgentInfo.AGENT_STAT_ERROR);
				throw new WebsocketException(Util.join(this.getClass().getSimpleName() , ".login agentNm[",agentNm,"] 으로 에이전트 정보를 조회할 수 없음"));

			}else {

				msg.setResponseObject(agentInfo);

				WebSocketSession session = se.getSession();
				session.getAttributes().put("agentInfo", agentInfo);

				log.setAgentId(agentId);
				log.setMsg("login ok");
				log.setStatus(IIPAgentInfo.AGENT_STAT_LOGON);

				agentChannelService.login(se.getSession());

				if(envs.smsOn) {
					String hostname = "HOSTNAME";
					if(se.getSession().getAttributes().get("agentInfo") != null) {
						IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
						hostname = info.getHostname();
					}
					addIIPAgentAlarm(log, hostname);
				}


				msg.setErrorCd(messageSource.getMessage("success.cd.ok",null, Locale.getDefault()));
				msg.setErrorMsg(messageSource.getMessage("success.msg.ok", null, Locale.getDefault()));

				msg.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				msg.getExtension().setMsgType(Extension.MSG_TYPE_RES);
				String strMsg = messageHandler.serialize(msg);
				agentChannelService.sendMessage(sessionId, strMsg);

				if(envs.isAgentServerIpUpdate) {
					try{
						long elapsed = System.currentTimeMillis();
						String serverIp = session.getRemoteAddress().getAddress().getHostAddress();
						String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
						String modId = this.getClass().getSimpleName();
						if(serverIp != null && agentId != null) {
							iipAgentService.updateServerAddress(agentId, serverIp, modDate, modId);
							logger.info(Util.join("7203.login agent[" , agentNm, "]'s hostname :", serverIp, "[",(System.currentTimeMillis() - elapsed),"]"));
						}
					}catch(Exception e) {
						logger.error("서버IP업데이트에러:",e);
					}finally {
//
//<<<<<<< .mine
//				if(envs.isAgentServerIpUpdate) {
//
//					try{
//						long elapsed = System.currentTimeMillis();
//						String serverIp = session.getRemoteAddress().getAddress().getHostAddress();
//						String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
//						String modId = this.getClass().getSimpleName();
//						if(serverIp != null && agentId != null) {
//							iipAgentService.updateServerAddress(agentId, serverIp, modDate, modId);
//							logger.info(Util.join("7203.login agent[" , agentNm, "]'s hostname :", serverIp, "[",(System.currentTimeMillis() - elapsed),"]"));
//						}
//					}catch(Exception e) {
//						logger.error("서버IP업데이트에러:",e);
//					}finally {
//
//=======
//					}
//>>>>>>> .r3465
					}
				}

			}

		}catch(Exception e) {
			//msg.setErrorCd(messageSource.getMessage("error.cd.fail",null, Locale.getDefault()));
			//msg.setErrorMsg(messageSource.getMessage("error.msg.fail", null, Locale.getDefault()));
			log.setMsg(e.getMessage());
			log.setStatus(IIPAgentInfo.AGENT_STAT_ERROR);
			throw e;
		}finally {
			if(agentInfo != null) {
				int res = 0;
				if(envs.addAgentResourceLog) {
					res = iipAgentService.insertIIPAgentLog(log);
				}else {
					res = iipAgentService.upsertIIPAgentLog(log);
				}
				logger.info(Util.join("login-check-", sessionId,"-",agentInfo.getAgentId() ,"-", log.getStatus(),"-update-",res));
			}else {
				logger.info(Util.join("login-check-", sessionId,"-agentInfoNotFound"));
			}
		}

	}

	/**
	 * logout
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0026, type=ServiceCode.MSG_TYPE_REQ)
	public void logout(ServiceEvent se) throws Exception {
		try {
			IIPAgentInfo agentInfo = (IIPAgentInfo)se.getSession().getAttributes().get("agentInfo");
			if(agentInfo != null) {
				String getDate = Util.getFormatedDate("yyyyMMddHHmmssSSS");
				IIPAgentLog log = new IIPAgentLog();
				log.setAgentId(agentInfo.getAgentId());
				log.setGetDate(getDate);
				log.setMsg("logout");
				log.setRegApp(this.getClass().getName());
				log.setRegDate(getDate);
				log.setStatus(IIPAgentInfo.AGENT_STAT_ERROR);
				int res = 0;
				if(envs.addAgentResourceLog) {
					res = iipAgentService.insertIIPAgentLog(log);
				}else {
					res = iipAgentService.upsertIIPAgentLog(log);
				}
				agentChannelService.logout(se.getSession());
				String sessionId = agentChannelService.sessionId(agentInfo.getAgentNm(), se.getSession().getId());
				logger.info(Util.join("logout-check-", sessionId,"-",agentInfo.getAgentId() ,"-", log.getStatus(),"-update-",res));

				if(envs.smsOn) {
					String hostname = "HOSTNAME";
					if(se.getSession().getAttributes().get("agentInfo") != null) {
						IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
						hostname = info.getHostname();
					}
					addIIPAgentAlarm(log, hostname);
				}

			}
		}catch(Exception e) {
			logger.error("",e);
		}finally {

		}
	}

	/**
	 * @param log
	 * @param hostname
	 */
	private void addIIPAgentAlarm(IIPAgentLog log, String hostname) {
		// TODO Auto-generated method stub

	}

	/**
	 * 에이전트 PUSH CPU LOG 적재
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0027, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveCpuLog(ServiceEvent se) throws Exception {

		ComMessage request = se.getRequest();
		if(request != null) {
			List<ResourceUsageLog> logs = (List<ResourceUsageLog>) request.getRequestObject();

			if(envs.addAgentResourceLog) {
				int res = iipAgentService.addResourceLogs(logs);
			}else {
				int res = iipAgentService.upsertResourceLogs(logs);
			}

			String hostname = "HOSTNAME";
			if(se.getSession().getAttributes().get("agentInfo") != null) {
				IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
				hostname = info.getHostname();
			}

			if(envs.smsOn) addResourceAlarm(logs, hostname);
		}

	}

	/**
	 * @param logs
	 * @throws Exception
	 */
	private void addResourceAlarm(List<ResourceUsageLog> logs, String hostname) throws Exception {
		String recipientString = "";
		{
			List<String> recipients = smsService.getSmsRecipients();
			if(recipients != null && recipients.size() > 0) {
				String recipient = recipients.get(0);
				if(recipient != null) recipientString = recipient;
				for(int i = 1 ; i < recipients.size() ; i ++) {
					recipient = recipients.get(i);
					if(recipientString.length() == 0) {
						if(recipient != null) recipientString = recipient;
					}else {
						if(recipient != null) recipientString = recipientString + "," + recipient;
					}
				}
			}
		}
		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);


		List<Email> emails = null;
		String emailRecipients = "none";

		if(envs.emailOn) {
			emailRecipients = emailService.getEmailRecipientsForAlarm();
			if(!"none".equals(emailRecipients)) emails = new ArrayList<Email>();
		}

		for(ResourceUsageLog log : logs) {
			if(log.getAlertVal().equalsIgnoreCase(ResourceUsageLog.ALERT_SEND)){
				ResourceInfo resourceInfo = log.getResourceInfo();
				String status = resourceInfo.getStatus();


				Map params = new HashMap();
				params.put("ObjectType", Sms.TYPE_RESOURCE);
				params.put("ObjectName", resourceInfo.getResourceName());
				params.put("Status", status);
				params.put("RegTime", regDate);
				params.put("QmgrName", "");
				params.put("HostName", hostname);
				params.put("Info1", Util.join(log.getUsedPer() , "/", resourceInfo.getLimit()));//디스크 위치
				if(ResourceInfo.TYPE_CPU.equals(resourceInfo.getType())){

				}else if(ResourceInfo.TYPE_MEMORY.equals(resourceInfo.getType())){

				}else if(ResourceInfo.TYPE_DISK.equals(resourceInfo.getType())){
					params.put("Info2", resourceInfo.getInfo1());//디스크 위치
				}

				if(envs.smsOn) {
					String contents = sendSMSService.buildContents(params);

					Sms sms = new Sms();
					sms.setType(Sms.TYPE_RESOURCE);
					sms.setSubject(contents);
					sms.setContents(contents);
					sms.setSender(envs.smsSenderNumber);
					sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
					sms.setRetry(envs.smsRetry);
					sms.setFormat(Sms.FORMAT_TEXT);
					sms.setRegDate(regDate);
					sms.setRegId("iip");
					sms.setDelYn("N");

					if(!"none".equals(sms.getRecipients())) {
						sms.setSndYn("N");
					}else {
						sms.setSndYn("Y");
						sms.setResCd("7");
						sms.setResMsg("recipients is null");
					}

					SmsResource resource = new SmsResource();
					resource.setKey1(resourceInfo.getResourceId());
					resource.setKey2(log.getGetDate());
					resource.setKey3(resourceInfo.getServerId());
					resource.setType(Sms.TYPE_RESOURCE);

					sms.setResource(resource);
					smsService.addSms(sms);
				}


				//--------------------------
				// email 발송 추가
				//--------------------------
				if(envs.emailOn && !"none".equals(emailRecipients)) {
					Email email = null;
					if(ResourceInfo.TYPE_CPU.equals(resourceInfo.getType())) {
						params.put("ObjectType", Email.TYPE_CPU);
						email = buildMail(emailRecipients, params, Email.TYPE_CPU);
					}else if(ResourceInfo.TYPE_MEMORY.equals(resourceInfo.getType())) {
						params.put("ObjectType", Email.TYPE_MEM);
						email = buildMail(emailRecipients, params, Email.TYPE_MEM);
					}else if(ResourceInfo.TYPE_DISK.equals(resourceInfo.getType())) {
						params.put("ObjectType", Email.TYPE_DISK);
						email = buildMail(emailRecipients, params, Email.TYPE_DISK);
					}else {

					}

					if(email != null) {
						emails.add(email);
						logger.debug("add emails" + Util.toJSONString(email));
					}

				}else {
					logger.debug("envs.emailOn && !\"none\".equals(emailRecipients) is false");
				}
			}

			if(envs.emailOn && emails != null && emails.size() > 0) {
				logger.debug("start loadEmailsForAlarm");
				emailService.loadEmailsForAlarm(emails);
				logger.debug("end loadEmailsForAlarm");
			}
		}

	}


	/**
	 * @param emailRecipients
	 * @param params
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Email buildMail(String emailRecipients, Map params, String type) throws Exception {
		Email email = new Email();
		String contents = sendMailService.buildContents(params);
		String subject  = sendMailService.getSubject(params);
		email.setContents(contents);
		email.setSubject(subject);
		String sender = sendMailService.getSendMailAddress();
		email.setSender(sender);
		email.setType(type);
		email.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		email.setRegId("iip");
		email.setRecipients(emailRecipients);
		return email;
	}

	/**
	 * 에이전트 PUSH MEMORY LOG 적개
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0028, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveMemoryLog(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			List<ResourceUsageLog> logs = (List<ResourceUsageLog>) request.getRequestObject();
			if(envs.addAgentResourceLog) {
				int res = iipAgentService.addResourceLogs(logs);
			}else {
				int res = iipAgentService.upsertResourceLogs(logs);
			}
			String hostname = "HOSTNAME";
			if(se.getSession().getAttributes().get("agentInfo") != null) {
				IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
				hostname = info.getHostname();
			}

			if(envs.smsOn) addResourceAlarm(logs, hostname);
		}
	}

	/**
	 * 에이전트 PUSH DISK LOG 적개
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0029, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveDiskLog(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			List<ResourceUsageLog> logs = (List<ResourceUsageLog>) request.getRequestObject();

			if(envs.addAgentResourceLog) {
				int res = iipAgentService.addResourceLogs(logs);
			}else {
				int res = iipAgentService.upsertResourceLogs(logs);
			}

			String hostname = "HOSTNAME";
			if(se.getSession().getAttributes().get("agentInfo") != null) {
				IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
				hostname = info.getHostname();
			}

			if(envs.smsOn) addResourceAlarm(logs, hostname);

		}
	}

	/**
	 * 에이전트 PUSH PROCESS LOG 적재
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0030, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveProcessLog(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			List<ProcessStatusLog> logs = (List<ProcessStatusLog>) request.getRequestObject();

			String trackDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
			try {
				logger.debug(Util.join("WS0030-S-",trackDate,":", Util.toJSONString(logs)));
				if(envs.addAgentResourceLog) {
					int res = iipAgentService.addProcessLogs(logs);
				}else {
					int res = iipAgentService.upsertProcessLogs(logs);
				}
			}finally {
				logger.debug(Util.join("WS0030-E-",trackDate ));
			}

			String hostname = "HOSTNAME";
			if(se.getSession().getAttributes().get("agentInfo") != null) {
				IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
				hostname = info.getHostname();
			}


			if(envs.smsOn || envs.emailOn) addProcessAlarm(logs,hostname);
		}
	}

	/**
	 * @param logs
	 * @param typeResMon
	 * @param typeProcess
	 * @throws Exception
	 */
	private void addProcessAlarm(List<ProcessStatusLog> logs, String hostname) throws Exception {
		String recipientString = "";
		if(envs.smsOn){
			List<String> recipients = smsService.getSmsRecipients();
			if(recipients != null && recipients.size() > 0) {
				recipientString = recipients.get(0) ;
				for(int i = 1 ; i < recipients.size() ; i ++) {
					recipientString = recipientString + "," + recipients.get(i);
				}
			}
		}

		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

		List<Email> emails = null;
		String emailRecipients = "none";
		if(envs.emailOn) {
			emailRecipients = emailService.getEmailRecipientsForAlarm();
			if(!"none".equals(emailRecipients)) emails = new ArrayList<Email>();
		}

		for(ProcessStatusLog log : logs) {
			if(log.getAlertVal().equalsIgnoreCase(ProcessStatusLog.ALERT_SEND)){
				String status = log.getStatus();

				ProcessInfo processInfo = log.getProcessInfo();

				Map params = new HashMap();
				params.put("ObjectType", Sms.TYPE_PROCESS);
				params.put("ObjectName", processInfo.getProcessNm());
				params.put("Status", status);
				params.put("RegTime", regDate);
				params.put("HostName", hostname);
				params.put("QmgrName", "");
				params.put("Info1", "");
				params.put("Info2", "");


				if(envs.smsOn) {
					String contents = sendSMSService.buildContents(params);
					Sms sms = new Sms();
					sms.setType(Sms.TYPE_PROCESS);
					sms.setSubject(contents);
					sms.setContents(contents);
					sms.setSender(envs.smsSenderNumber);
					sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
					sms.setRetry(envs.smsRetry);
					sms.setFormat(Sms.FORMAT_TEXT);
					sms.setRegDate(regDate);
					sms.setRegId("iip");
					sms.setDelYn("N");

					if(!"none".equals(sms.getRecipients())) {
						sms.setSndYn("N");
					}else {
						sms.setSndYn("Y");
						sms.setResCd("7");
						sms.setResMsg("recipients is null");
					}

					SmsResource resource = new SmsResource();
					resource.setKey1(log.getProcessId());
					resource.setKey2(log.getGetDate());
					resource.setKey3(log.getGetDate());
					resource.setType(Sms.TYPE_PROCESS);
					sms.setResource(resource);

					smsService.addSms(sms);
				}

				//--------------------------
				// email 발송 추가
				//--------------------------
				if(envs.emailOn && !"none".equals(emailRecipients)) {
					params.put("ObjectType", Email.TYPE_PROCESS);
					Email email = buildMail(emailRecipients, params, Email.TYPE_PROCESS);
					emails.add(email);
					logger.debug("add emails" + Util.toJSONString(email));
				}else {
					logger.debug("envs.emailOn && !\"none\".equals(emailRecipients) is false");
				}
			}

			if(envs.emailOn && emails != null && emails.size() > 0) {
				logger.debug("start loadEmailsForAlarm");
				emailService.loadEmailsForAlarm(emails);
				logger.debug("end loadEmailsForAlarm");
			}

		}
	}

	/**
	 * 에이전트 PUSH QMGR LOG 적개
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0031, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveQmgrLog(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			List<QmgrLogs> logs = (List<QmgrLogs>) request.getRequestObject();

			if(envs.addAgentResourceLog) {
				int res = iipAgentService.addQmgrLogs(logs);
			}else {
				int res = iipAgentService.upsertQmgrLogs(logs);
			}

			String hostname = "HOSTNAME";
			Map<String, QmgrInfo> qmgrInfoMap = null;
			if(se.getSession().getAttributes().get("agentInfo") != null) {
				IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
				hostname = info.getHostname();

				List<MonitorItem> items = info.getMonitorItems();
				for(MonitorItem item : items) {
					if(MonitorItem.ITEM_TYPE_QMGR.equals(item.getItemType())){
						qmgrInfoMap = item.getQmgrInfoMap();
						break;
					}
				}
			}
			addQmgrAlarm(logs,hostname,qmgrInfoMap);
		}
	}




	/**
	 * @param logs
	 * @param typeResMon
	 * @param typeProcess
	 */
	private void addQmgrAlarm(List<QmgrLogs> logs, String hostname, Map<String, QmgrInfo> qmgrInfoMap) throws Exception {



		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

		String  recipientString = "";
		List<String> recipients = smsService.getSmsRecipients();
		if(recipients != null && recipients.size() > 0) {
			recipientString = recipients.get(0) ;
			for(int i = 1 ; i < recipients.size() ; i ++) {
				recipientString = recipientString + "," + recipients.get(i);
			}
		}


		List<Email> emails = null;
		String emailRecipients = "none";
		if(envs.emailOn) {
			emailRecipients = emailService.getEmailRecipientsForAlarm();
			if(!"none".equals(emailRecipients)) emails = new ArrayList<Email>();
		}


		for(QmgrLogs log : logs) {


			//QMGR
			QmgrStatusLog qmgrLog = log.getQmgrStatusLog();
			if(qmgrLog != null) {

				if(qmgrLog.getAlertVal().equalsIgnoreCase(QmgrStatusLog.ALERT_SEND)){

					QmgrInfo qmgrInfo = qmgrInfoMap.get(qmgrLog.getQmgrId());

					Map params = new HashMap();
					params.put("ObjectType", Sms.TYPE_QMGR);
					params.put("ObjectName", qmgrInfo.getQmgrNm());
					params.put("Status", qmgrLog.getStatus());
					params.put("RegTime", regDate);
					params.put("HostName", hostname);
					params.put("QmgrName", qmgrInfo.getQmgrNm());
					params.put("Info1", "");
					params.put("Info2", "");
					if(envs.smsOn) {
						String contents = sendSMSService.buildContents(params);


						Sms sms = new Sms();
						sms.setType(Sms.TYPE_QMGR);
						sms.setSubject(contents);
						sms.setContents(contents);
						sms.setSender(envs.smsSenderNumber);
						sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
						sms.setRetry(envs.smsRetry);
						sms.setFormat(Sms.FORMAT_TEXT);
						sms.setRegDate(regDate);
						sms.setRegId("iip");
						sms.setDelYn("N");

						if(!"none".equals(sms.getRecipients())) {
							sms.setSndYn("N");
						}else {
							sms.setSndYn("Y");
							sms.setResCd("7");
							sms.setResMsg("recipients is null");
						}

						SmsResource resource = new SmsResource();
						resource.setKey1(qmgrLog.getQmgrId());
						resource.setKey2(qmgrLog.getGetDate());
						resource.setKey3(qmgrLog.getGetDate());
						resource.setType(Sms.TYPE_QMGR);
						sms.setResource(resource);

						smsService.addSms(sms);
					}

					//--------------------------
					// email 발송 추가
					//--------------------------
					if(envs.emailOn && !"none".equals(emailRecipients)) {
						params.put("ObjectType", Email.TYPE_QMGR);
						Email email = buildMail(emailRecipients, params, Email.TYPE_QMGR);
						emails.add(email);
						logger.debug("add emails" + Util.toJSONString(email));
					}else {
						logger.debug("envs.emailOn && !\"none\".equals(emailRecipients) is false");
					}

				}
			}



			//CHANNEL
			List<ChannelStatusLog> channelLogs = log.getChannelStatusLogs();
			if(channelLogs != null && channelLogs.size() > 0) {

				QmgrInfo qmgrInfo = qmgrInfoMap.get(channelLogs.get(0).getQmgrId());

				for(ChannelStatusLog channelLog : channelLogs) {
					if(channelLog.getAlertVal().equalsIgnoreCase(ChannelStatusLog.ALERT_SEND)){
						ChannelInfo channeInfo = qmgrInfo.getChannelMap().get(channelLog.getChannelId());

						Map params = new HashMap();
						params.put("ObjectType", Sms.TYPE_CHANNEL);
						params.put("ObjectName", channeInfo.getChannelNm());
						params.put("Status", channelLog.getStatus());
						params.put("RegTime", regDate);
						params.put("HostName", hostname);
						params.put("QmgrName", qmgrInfo.getQmgrNm());
						params.put("Info1", "");
						params.put("Info2", "");
						if(envs.smsOn) {
							String contents = sendSMSService.buildContents(params);

							Sms sms = new Sms();
							sms.setType(Sms.TYPE_CHANNEL);
							sms.setSubject(contents);
							sms.setContents(contents);
							sms.setSender(envs.smsSenderNumber);
							sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
							sms.setRetry(envs.smsRetry);
							sms.setFormat(Sms.FORMAT_TEXT);
							sms.setRegDate(regDate);
							sms.setRegId("iip");
							sms.setDelYn("N");

							if(!"none".equals(sms.getRecipients())) {
								sms.setSndYn("N");
							}else {
								sms.setSndYn("Y");
								sms.setResCd("7");
								sms.setResMsg("recipients is null");
							}

							SmsResource resource = new SmsResource();
							resource.setKey1(channelLog.getQmgrId());
							resource.setKey2(channelLog.getChannelId());
							resource.setKey3(channelLog.getGetDate());
							resource.setType(Sms.TYPE_CHANNEL);
							sms.setResource(resource);

							smsService.addSms(sms);
						}

						//--------------------------
						// email 발송 추가
						//--------------------------
						if(envs.emailOn && !"none".equals(emailRecipients)) {
							params.put("ObjectType", Email.TYPE_CHANNEL);
							Email email = buildMail(emailRecipients, params, Email.TYPE_CHANNEL);
							emails.add(email);
							logger.debug("add emails" + Util.toJSONString(email));
						}else {
							logger.debug("envs.emailOn && !\"none\".equals(emailRecipients) is false");
						}


					}

				}

			}
			//QUEUE
			List<QueueStatusLog> queueLogs = log.getQueueStatusLogs();
			if(queueLogs != null && queueLogs.size() > 0) {

				QmgrInfo qmgrInfo = qmgrInfoMap.get(queueLogs.get(0).getQmgrId());

				for(QueueStatusLog queueLog : queueLogs) {
					if(queueLog.getAlertVal().equalsIgnoreCase(QueueStatusLog.ALERT_SEND)){


						Map params = new HashMap();
						params.put("ObjectType", Sms.TYPE_QUEUE);

						QueueInfo queueInfo = qmgrInfo.getQueueMap().get(queueLog.getQueueId());
						String queueName = queueInfo.getQueueNm();

						String limitStr = queueInfo.getLimit();
						int limit = 0;
						try{
							limit = Integer.parseInt(Util.isEmpty(limitStr) ? "0" : limitStr);
						}catch(Exception e){
							//logger.debug("ignorable exception:",e);
						}
						String status = (queueLog.getDepth() <= limit) ? QueueStatusLog.STATUS_NORMAL : QueueStatusLog.STATUS_ABNORMAL;

						params.put("ObjectName", queueName);
						params.put("Status", status);
						params.put("RegTime", regDate);
						params.put("HostName", hostname);
						params.put("QmgrName", qmgrInfo.getQmgrNm());
						params.put("Info1", Util.join(queueLog.getDepth(), "/", limitStr));
						params.put("Info2", "");

						if(envs.smsOn) {
							String contents = sendSMSService.buildContents(params);

							Sms sms = new Sms();
							sms.setType(Sms.TYPE_QUEUE);
							sms.setSubject(contents);
							sms.setContents(contents);
							sms.setSender(envs.smsSenderNumber);
							sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
							sms.setRetry(envs.smsRetry);
							sms.setFormat(Sms.FORMAT_TEXT);
							sms.setRegDate(regDate);
							sms.setRegId("iip");
							sms.setDelYn("N");

							if(!"none".equals(sms.getRecipients())) {
								sms.setSndYn("N");
							}else {
								sms.setSndYn("Y");
								sms.setResCd("7");
								sms.setResMsg("recipients is null");
							}

							SmsResource resource = new SmsResource();
							resource.setKey1(queueLog.getQmgrId());
							resource.setKey2(queueLog.getQueueId());
							resource.setKey3(queueLog.getGetDate());
							resource.setType(Sms.TYPE_QUEUE);
							sms.setResource(resource);

							smsService.addSms(sms);
						}

						//--------------------------
						// email 발송 추가
						//--------------------------
						if(envs.emailOn && !"none".equals(emailRecipients)) {
							params.put("ObjectType", Email.TYPE_QUEUE);
							Email email = buildMail(emailRecipients, params, Email.TYPE_QUEUE);
							emails.add(email);
							logger.debug("add emails" + Util.toJSONString(email));
						}else {
							logger.debug("envs.emailOn && !\"none\".equals(emailRecipients) is false");
						}

					}
				}

			}

		}

		if(envs.emailOn && emails != null && emails.size() > 0) {
			logger.debug("start loadEmailsForAlarm");
			emailService.loadEmailsForAlarm(emails);
			logger.debug("end loadEmailsForAlarm");
		}

	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0020, type=ServiceCode.MSG_TYPE_REQ)
	public void requestReloadAgentInfo(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}

		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();

		String agentNm = (String)params.get("agentNm");
		int res = iipAgentService.getExecuteCommandCount(agentNm, IIPAgentInfo.CMD_CD_RELOAD_INFO);
		if(res > 0) {

			//String agentId = engineMonitorService.getAgentInfo(agentNm);
			agentInfo = engineMonitorService.getAgentDetailInfoWithQmgrInfoMap(agentInfo.getAgentId());
			se.getSession().getAttributes().put("agentInfo", agentInfo);
			request.setRequestObject(agentInfo);
			String msg = messageHandler.serialize(request);
			//String sessionId = agentNm;
			String sessionId = agentChannelService.sessionId(agentNm, se.getSession().getId());
			agentChannelService.sendMessage(sessionId, msg);
			//---------------------------------------------------------
			//명령처리결과업데이트
			//---------------------------------------------------------
			String agentId = agentInfo.getAgentId();
			String rsMsg = CommandConsole.RS_MSG_ING;
			String rsCd = CommandConsole.RS_ING;
			String rsDate = Util.getFormatedDate();
			String cmdCd = IIPAgentInfo.CMD_CD_RELOAD_INFO;
			iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);
		}else {
			logger.debug(Util.join(Util.join("The agent[", agentNm, "] did not have any reloading info command.")));
		}


	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0020, type=ServiceCode.MSG_TYPE_RES)
	public void responseReloadAgentInfo(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}

		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();

		//---------------------------------------------------------
		//명령처리결과업데이트
		//---------------------------------------------------------
		String agentId = agentInfo.getAgentId();
		String rsMsg = request.getErrorMsg();
		String rsCd = "0".equals(request.getErrorCd()) ? CommandConsole.RS_OK : CommandConsole.RS_FAIL;
		String rsDate = request.getEndTime();
		String cmdCd = IIPAgentInfo.CMD_CD_RELOAD_INFO;
		iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);

	}




	@ServiceCode(code=Variables.SERVICE_CD_WS0021, type=ServiceCode.MSG_TYPE_REQ)
	public void requestClassReload(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}

		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();
		String agentNm = (String)params.get("agentNm");

		int res = iipAgentService.getExecuteCommandCount(agentNm, IIPAgentInfo.CMD_CD_DEPLOY);
		if(res > 0) {
			String msg = messageHandler.serialize(request);
			//String sessionId = agentNm;
			String sessionId = agentChannelService.sessionId(agentNm, se.getSession().getId());
			agentChannelService.sendMessage(sessionId, msg);
			//---------------------------------------------------------
			//명령처리결과업데이트
			//---------------------------------------------------------
			String agentId = agentInfo.getAgentId();
			String rsMsg = CommandConsole.RS_MSG_ING;
			String rsCd = CommandConsole.RS_ING;
			String rsDate = Util.getFormatedDate();
			String cmdCd = IIPAgentInfo.CMD_CD_DEPLOY;
			iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);

		}else {
			logger.debug(Util.join(Util.join("The agent[", agentNm, "] did not have any reloading class command.")));
		}


	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0021, type=ServiceCode.MSG_TYPE_RES)
	public void responseClassReload(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}

		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();

		//---------------------------------------------------------
		//명령처리결과업데이트
		//---------------------------------------------------------
		String agentId = agentInfo.getAgentId();
		String rsMsg = request.getErrorMsg();
		String rsCd = "0".equals(request.getErrorCd()) ? CommandConsole.RS_OK : CommandConsole.RS_FAIL;
			String rsDate = request.getEndTime();
			String cmdCd = IIPAgentInfo.CMD_CD_DEPLOY;
			iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);

	}




	/**
	 * @deprecated 얼라이브체크는 에이전트까지 보내지 않고 서버 세션을 체크하는 것으로 대체한다.(대체 : AgentSessionCheckScheduler())
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0024, type=ServiceCode.MSG_TYPE_REQ)
	public void requestCheckAlive(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}


		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();
		String agentNm = (String)params.get("agentNm");
		int res = iipAgentService.getExecuteCommandCount(agentNm, IIPAgentInfo.CMD_CD_CHECK_ALIVE);
		if(res > 0) {
			String msg = messageHandler.serialize(request);
			//String sessionId = agentNm;
			String sessionId = agentChannelService.sessionId(agentNm, se.getSession().getId());
			agentChannelService.sendMessage(sessionId, msg);
			//---------------------------------------------------------
			//명령처리결과업데이트
			//---------------------------------------------------------
			String agentId = agentInfo.getAgentId();
			String rsMsg = CommandConsole.RS_MSG_ING;
			String rsCd = CommandConsole.RS_ING;
			String rsDate = Util.getFormatedDate();
			String cmdCd = IIPAgentInfo.CMD_CD_CHECK_ALIVE;
			iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);

		}else {
			logger.debug(Util.join(Util.join("The agent[", agentNm, "] did not have any check alive command.")));
		}
	}

	/**
	 * @deprecated 얼라이브체크는 에이전트까지 보내지 않고 서버 세션을 체크하는 것으로 대체한다.(대체 : AgentSessionCheckScheduler())
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0024, type=ServiceCode.MSG_TYPE_RES)
	public void pushCmdCheckAlive(ServiceEvent se) throws Exception{

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}

		String a = IIPAgentInfo.AGENT_STAT_LOGON;

		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();


		String agentId = agentInfo.getAgentId();
		String msg = request.getErrorMsg();
		String status = "9";

		if(request.getResponseObject() != null) {
			boolean isAlive = (boolean)request.getResponseObject();
			status = isAlive ? IIPAgentInfo.AGENT_STAT_LOGON : IIPAgentInfo.AGENT_STAT_ERROR;
		}

		String getDate = request.getEndTime();
		IIPAgentLog iipAgentLog = new IIPAgentLog();
		iipAgentLog.setAgentId(agentId);
		iipAgentLog.setGetDate(getDate);
		iipAgentLog.setMsg(msg);
		iipAgentLog.setRegApp(this.getClass().getName());
		iipAgentLog.setRegDate(getDate);
		iipAgentLog.setStatus(status);
		if(envs.addAgentResourceLog) {
			iipAgentService.insertIIPAgentLog(iipAgentLog);
		}else {
			iipAgentService.upsertIIPAgentLog(iipAgentLog);
		}
		//---------------------------------------------------------
		//명령처리결과업데이트
		//---------------------------------------------------------
		String rsMsg = request.getErrorMsg();
		String rsCd = "0".equals(request.getErrorCd()) ? CommandConsole.RS_OK : CommandConsole.RS_FAIL;
		String rsDate = request.getEndTime();
		String cmdCd = IIPAgentInfo.CMD_CD_CHECK_ALIVE;
		iipAgentService.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg,rsDate);
	}


	/**
	 * NH농협-TEST 인터페이스 CALL 결과 처리(에이전트->IIP서버)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0034, type=ServiceCode.MSG_TYPE_RES)
	public void receiveTestCallResponse(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			if(request.getResponseObject() != null) {

				InterfaceCallDetail detail = (InterfaceCallDetail) request.getResponseObject();

				String trackDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
				logger.debug(Util.join("WS0034-S-",trackDate,":", Util.toJSONString(detail)));
				testInterfaceCallService.updateInterfaceCallDetail(detail);
				logger.debug(Util.join("WS0034-E-",trackDate));
			}else {
				logger.error(Util.join("NH농협-TEST 인터페이스 CALL 결과 처리(에이전트->IIP서버) 서비스[", Variables.SERVICE_CD_WS0034, "] responseObject NULL"));
			}
		}
	}

	/**
	 * NH농협-서버어뎁터정보PUSH(에이전트->IIP서버)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0050, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveAdapterVersionInfo(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) serverAppMngService.upsertAppInfos((List<ApplicationInfo>) request.getRequestObject());

	}


	@Autowired
	ConfigMngService configMngService;
	/**
	 * 농협 인터페이스 Config 정보 업데이트 응답 처리
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0054, type=ServiceCode.MSG_TYPE_RES)
	public void receiveInterfaceConfigResponse(ServiceEvent se) throws Exception {
		ComMessage request = se.getRequest();
		if(request != null) {
			if(request.getResponseObject() != null) {

				ConfigInfo config = (ConfigInfo) request.getResponseObject();
				configMngService.storeAndUpdateResult(config);

			}else {
				logger.error(Util.join("농협 인터페이스 Config 정보 업데이트 응답 처리  서비스[", Variables.SERVICE_CD_WS0054, "] responseObject NULL"));
			}
		}
	}


	/**
	 * netstat 결과 수신 (에이전트->IIP서버)
	 * @param se
	 * @throws Exception
	 */
	@ServiceCode(code=Variables.SERVICE_CD_WS0043, type=ServiceCode.MSG_TYPE_PUSH)
	public void receiveNetstatLog(ServiceEvent se) throws Exception {


		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}
		if(agentInfo != null) {
			String agentId = agentInfo.getAgentId();


			ComMessage request = se.getRequest();
			if(request != null) {

				String trackDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
				logger.debug(Util.join("WS0043-S-",trackDate,":", ""));

				List<Map<String, String>> logs = (List<Map<String, String>>)request.getRequestObject();
				localCmdLogService.logNetstat(agentId, logs);

				logger.debug(Util.join("WS0043-E-",trackDate));

				String hostname = "HOSTNAME";
				if(se.getSession().getAttributes().get("agentInfo") != null) {
					IIPAgentInfo info = (IIPAgentInfo) se.getSession().getAttributes().get("agentInfo");
					hostname = info.getHostname();
				}
				if(envs.smsOn) addNetstatAlarm(logs,hostname);
			}
		}
	}

	/**
	 * @param logs
	 * @param typeResMon
	 * @param typeProcess
	 * @throws Exception
	 */
	private void addNetstatAlarm(List<Map<String, String>> logs, String hostname) throws Exception {
		String recipientString = "";
		if(envs.smsOn) {
			List<String> recipients = smsService.getSmsRecipients();
			if(recipients != null && recipients.size() > 0) {
				recipientString = recipients.get(0);
				for(int i=1; i<recipients.size(); i++) {
					recipientString = recipientString + "," + recipients.get(i);
				}
			}
		}

		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

		for(Map<String, String> log : logs) {
			String status = log.get("state");

			Map params = new HashMap();
			params.put("ObjectType", Sms.TYPE_NETSTAT);
			params.put("ObjectName", log.get("serverNm"));
			params.put("Status", log.get("state"));
			params.put("RegTime", regDate);
			params.put("HostName", hostname);
			params.put("QmgrName", "");
			params.put("Info1", "");
			params.put("Info2", "");

			if(envs.smsOn) {
				String contents = sendSMSService.buildContents(params);
				Sms sms = new Sms();
				sms.setType(Sms.TYPE_NETSTAT);
				sms.setSubject(contents);
				sms.setContents(contents);
				sms.setSender(envs.smsSenderNumber);
				sms.setRecipients(recipientString.length() == 0 ? "none" : recipientString);
				sms.setRetry(envs.smsRetry);
				sms.setFormat(Sms.FORMAT_TEXT);
				sms.setRegDate(regDate);
				sms.setRegId("iip");
				sms.setDelYn("N");

				if(!"none".equals(sms.getRecipients())) {
					sms.setSndYn("N");
				} else {
					sms.setSndYn("Y");
					sms.setResCd("7");
					sms.setResMsg("recipients is null");
				}

				SmsResource resource = new SmsResource();
				resource.setKey3(log.get("serverId"));
				resource.setKey1(log.get("regDate"));
				resource.setKey2(log.get("regDate"));
				resource.setType(Sms.TYPE_PROCESS);
				sms.setResource(resource);

				smsService.addSms(sms);
			}
		}

	}



	@ServiceCode(code=Variables.SERVICE_CD_WS0044, type=ServiceCode.MSG_TYPE_REQ)
	public void requestNetstatCheckList(ServiceEvent se) throws Exception{

		String trackDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
		logger.debug(Util.join("WS0044-S-",trackDate,":", ""));

		IIPAgentInfo agentInfo = null;
		Object obj = se.getSession().getAttributes().get("agentInfo");
		if(obj != null) {
			agentInfo = (IIPAgentInfo)obj;
		}
		se.getId();
		ComMessage request = se.getRequest();
		String msgType = request.getExtension().getMsgType();
		Map params = request.getExtension().getParams();
		String agentNm = (String)params.get("agentNm");
		String agentCd = agentInfo.getAgentCd();
		String agentId = agentInfo.getAgentId();
		logger.debug(Util.join("WS0044-S-",trackDate,":agentId:", agentId));
		logger.debug(Util.join("WS0044-S-",trackDate,":agentCd:", agentCd));

		List<Map<String, String>> list = localCmdLogService.getNetstatCheckList(agentId, agentCd);
		logger.debug(Util.join("WS0044-S-",trackDate,":netstat check list", Util.toJSONPrettyString(list)));

		request.setResponseObject(list);

		String msg = messageHandler.serialize(request);
		//String sessionId = agentNm;
		String sessionId = agentChannelService.sessionId(agentNm, se.getSession().getId());
		agentChannelService.sendMessage(sessionId, msg);

		logger.debug(Util.join("WS0044-E-",trackDate));
	}



}
