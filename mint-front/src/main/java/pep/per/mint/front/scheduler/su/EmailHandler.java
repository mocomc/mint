package pep.per.mint.front.scheduler.su;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.data.basic.email.EmailResourceForTracking;
import pep.per.mint.common.data.basic.email.Recipient;
import pep.per.mint.common.data.basic.monitor.TrackingSystemInfo;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.MonitorService;
import pep.per.mint.database.service.su.EmailService;
import pep.per.mint.front.scheduler.ScheduleHandler;
import pep.per.mint.inhouse.mail.SendMailService;

@Component
public class EmailHandler extends ScheduleHandler{

	private static final Logger logger = LoggerFactory.getLogger(EmailHandler.class);

	@Autowired
	CommonService commonService;

	@Autowired
	@Qualifier("trackingMailService")
	SendMailService sendMailService;

	@Autowired
	EmailService emailService;

	@Autowired
	MonitorService monitorService;

	List<String> defaultRecipents = null;

	public void setEnvironment(){

	//--------------------------------------------------------------
    	// set schedule.mail.handler.fixed.delay var
    	//--------------------------------------------------------------
    	{
	    	long fixedDelayAsOneSecond = 0;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.mail.handler.fixed.delay");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.mail.handler.fixed.delay");
	    			if(!Util.isEmpty(envs)){
	    				fixedDelayAsOneSecond = Long.parseLong((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	this.fixedDelayAsOneSecond = fixedDelayAsOneSecond == 0 ? ScheduleHandler.DEFAULT_FIXED_DELAY_AS_SEC : fixedDelayAsOneSecond;
	    	logger.debug(Util.join("The env [support.schedule.mail.handler.fixed.delay]:",fixedDelayAsOneSecond));
    	}

	}

    //@Scheduled(fixedDelay = 60*1000, initialDelay = 30 * 1000) // 1000 * 60, 즉 1분마다 실행한다.
	public void loadTrackingEmail(){

    	//--------------------------------------------------------------
    	// set support.schedule.mail.load.tracking.email var
    	//--------------------------------------------------------------
    	{
	    	boolean loadTrackingEmail = false;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.mail.load.tracking.email");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.mail.load.tracking.email");
	    			if(!Util.isEmpty(envs)){
	    				loadTrackingEmail = Boolean.parseBoolean((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.mail.load.tracking.email]:",loadTrackingEmail));
	    	if(!loadTrackingEmail) return;
    	}

    	//--------------------------------------------------------------
    	// set schedule.mail.load.tracking.email.cnt var
    	//--------------------------------------------------------------
    	{
	    	int loadCnt = 0;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.mail.load.tracking.email.cnt");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.mail.load.tracking.email.cnt");
	    			if(!Util.isEmpty(envs)){
	    				loadCnt = Integer.parseInt((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.mail.load.tracking.email.cnt]:",loadCnt));

	    	emailService.loadCount = loadCnt == 0 ? EmailService.DEFAULT_EMAIL_LOAD_CNT : loadCnt;
    	}

    	//--------------------------------------------------------------
    	// set support.mail.default.recipients var
    	//--------------------------------------------------------------
    	{
    		defaultRecipents = new ArrayList<String>();//support.mail.default.recipients
    		try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "mail.default.recipients");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.mail.default.recipients");
	    			if(!Util.isEmpty(envs)){

	    				for (String address : envs) {
	    					if(!defaultRecipents.contains(address)){
	    						defaultRecipents.add(address);
	    					}
						}
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.mail.default.recipients]:",Util.toJSONString(defaultRecipents)));
    	}

    	try{
    		logger.info(Util.join(EmailHandler.class.getName(),".loadTrackingEmail() schedule start"));

    		List<EmailResourceForTracking> resources = emailService.getEmailResourceForTracking();

    		List<Email> emails = new ArrayList<Email>();
    		for (EmailResourceForTracking resource : resources) {
    			String interfaceId = resource.getInterfaceId();
    			String key1 = resource.getKey1();
    			String key2 = resource.getKey2();

                //존재하는지 key1, key2로 체크하여 있으면 continue
                //boolean exist = emailService.existEmailResource(key1, key2);
                //if(exist) {
                //    logger.debug(Util.join("loadTrackingEmail:key1:",key1, " , key2:", key2," 존재하므로 skip..."));
                //    continue;
                //}

    			Interface interfaze = commonService.getInterfaceDetail(interfaceId);

    			String integrationId = resource.getIntegrationId();
    			String interfaceNm = resource.getInterfaceNm();
    			String channelNm = "";
    			String businessNm = "";
    			String dataPrMethodNm = "";
    			String dataPrDirNm = "";
    			String appPrMethodNm = "";
    			String dataFrequency = "";

    			//String msgContents = "";
    			//String errorContents = "";

    			if(interfaze != null){
	    			integrationId	= resource.getIntegrationId();
	    			interfaceNm 	= resource.getInterfaceNm();
	    			channelNm 		= interfaze.getChannel().getChannelNm();
	    			businessNm 		= interfaze.getBusinessList().get(0).getBusinessNm();
	    			dataPrMethodNm 	= interfaze.getDataPrMethodNm();
	    			dataPrDirNm 	= interfaze.getDataPrDirNm();
	    			appPrMethodNm 	= interfaze.getAppPrMethodNm();
	    			dataFrequency 	= interfaze.getDataFrequencyNm();
    			}

    			List<TRLogDetail> nodeList = null;
    			Map<String, String> logParams = new HashMap<String, String>();
    			logParams.put("logKey1", key1);
    			logParams.put("logKey2", key2);
    			logParams.put("statusCodeLv1", "OP");
    			logParams.put("statusCodeLv2", "01");
    			{

    				nodeList = monitorService.getTrackingLogDetail(logParams);

    				for(TRLogDetail detail : nodeList) {
    					if(detail.getMsg() != null) {
    						byte[] bdata = detail.getMsg().getBytes(1, (int) detail.getMsg().length());
    						detail.setMsgToString(new String(bdata));
    					}
    					if(detail.getMsgToByte() != null) {
    						logger.debug(new String(detail.getMsgToByte()));
    						detail.setMsgToString(new String(detail.getMsgToByte(), "UTF-8"));
    					}
    				}
    			}

    			List<pep.per.mint.common.data.basic.System> systemList = null;
    			{
    				if(interfaze != null) systemList = interfaze.getSystemList();
    			}

    			Map<String, Object> params = new HashMap<String, Object>();
    			params.put("integrationId", integrationId);
    			params.put("interfaceNm", interfaceNm);
    			params.put("channelNm", channelNm);
    			params.put("businessNm", businessNm);
    			params.put("dataPrMethodNm", dataPrMethodNm);
    			params.put("dataPrDirNm", dataPrDirNm);
    			params.put("appPrMethodNm", appPrMethodNm);
    			params.put("dataFrequency", dataFrequency);
    			params.put("systemList", systemList);
    			params.put("nodeList", nodeList);

    			String contents = sendMailService.buildContents(params);
    			String subject = sendMailService.getSubject(params);
    			//String subject = Util.join("인터페이스 에러발생 알림[",integrationId,"]");

    			Email email = new Email();
				email.setResource(resource);
				email.setContents(contents);
				email.setSubject(subject);

				List<Recipient> recipients = emailService.getEmailRecipientsForTracking();
				if(recipients == null || recipients.size() == 0){
					if(defaultRecipents == null && defaultRecipents.size() == 0){
						throw new Exception(Util.join("There is no email recipient!"));
					}
					for(String recipient : defaultRecipents){
						String recipientsValue = email.getRecipients();
						if(!recipientsValue.contains(recipient)){
							email.addRecipient(recipient);
						}
					}
				}else{
					for (Recipient recipient : recipients) {
						String recipientsValue = email.getRecipients();
						if(!recipientsValue.contains(recipient.getAddress())){
							email.addRecipient(recipient.getAddress());
						}
					}
				}

				//----------------------------------------------------------
				// 수시자목록에 인터페이스 담당자 추가
				//----------------------------------------------------------
				if(interfaze != null){
					List<RelUser> relUsers = interfaze.getRelUsers();
					if(!Util.isEmpty(relUsers)){
						for (RelUser relUser : relUsers) {
							User user = relUser.getUser();
							if(!Util.isEmpty(user)) {
								String address = user.getEmail();
								if(!Util.isEmpty(address)){
									String recipientsValue = email.getRecipients();
									if(!recipientsValue.contains(address)){
										email.addRecipient(address);
									}
								}
							}
						}
					}
				}


				String sender = sendMailService.getSendMailAddress();
				email.setSender(sender);
				email.setType(Email.TYPE_TRACKING);
				email.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				email.setRegId("iip");


				emails.add(email);

			}
    		emailService.loadEmailsForTracking(emails);


    	}catch(Throwable e){
    		e.printStackTrace();
    		logger.error(Util.join(EmailHandler.class.getName(),".loadTrackingEmail() schedule error:"), e);
    	}finally{
    		logger.info(Util.join(EmailHandler.class.getName(),".loadTrackingEmail() schedule end"));
    	}

    }

    //@Scheduled(fixedDelay = 60*1000, initialDelay = 60 * 1000) // 1000 * 60, 즉 1분마다 실행한다.
	public void sendTrackingEmail(){

    	//--------------------------------------------------------------
    	// set schedule.mail.send.tracking.email var
    	//--------------------------------------------------------------
    	{
	    	boolean sendEmail = false;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.mail.send.tracking.email");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.mail.send.tracking.email");
	    			if(!Util.isEmpty(envs)){
	    				sendEmail = Boolean.parseBoolean((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.mail.send.tracking.email]:",sendEmail));
	    	if(!sendEmail) return;
    	}

    	//--------------------------------------------------------------
    	// set schedule.mail.fetch.tracking.email.cnt var
    	//--------------------------------------------------------------
    	{
	    	int fetchCnt = 0;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.mail.fetch.tracking.email.cnt");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.mail.fetch.tracking.email.cnt");
	    			if(!Util.isEmpty(envs)){
	    				fetchCnt = Integer.parseInt((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.mail.fetch.tracking.email.cnt]:",fetchCnt));

	    	emailService.fetchCount = fetchCnt == 0 ? EmailService.DEFAULT_EMAIL_FETCH_CNT : fetchCnt;
    	}



    	try{
    		logger.info(Util.join(EmailHandler.class.getName(),".sendTrackingEmail() schedule start"));

    		List<Email> emails = emailService.getEamilsForTracking();
    		if(!Util.isEmpty(emails)){
    			for (Email email : emails) {
    				try{
    					email.setSndReqDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

    					String recipientsString = email.getRecipients();
    					String [] recipientsArray = recipientsString.split(",");
    					List<String> recipients = Arrays.asList(recipientsArray);
    					Map params = new HashMap();
    					params.put("subject", email.getSubject());
    					params.put("contents", email.getContents());
    					params.put("recipients", recipients);
    					sendMailService.sendMail(params);

						email.setResCd("0");
						email.setResMsg("success");
						email.setSndYn("Y");
						logger.debug(Util.join(EmailHandler.class.getName(),".sendEmail OK:emailId:[",email.getEmailId(),"]"));
    				}catch(Exception ex){
    					email.setResCd("9");
						email.setResMsg(ex.getMessage());
						email.setSndYn("N");
						//change-when : 170927
						//change-what :
						//에러나도 다음메일 계속 보내도록 예외를 던지지 않도록 수정함.
    					//throw ex;
						logger.error(Util.join(EmailHandler.class.getName(),".sendEmail error:emailId:[",email.getEmailId(),"]"), ex);
    				}finally{
    					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
    					email.setModDate(modDate);
						email.setModId("iip");
						email.setResDate(modDate);
    					emailService.updateEmailSendResult(email);
    					logger.debug("done updateEmailSendResult");
    				}
				}
    		}
    	}catch(Throwable e){
    		logger.error(Util.join(EmailHandler.class.getName(),".sendTrackingEmail() schedule error:"), e);
    	}finally{
    		logger.info(Util.join(EmailHandler.class.getName(),".sendTrackingEmail() schedule end"));
    	}

    }

	@Override
	public void doFixedDelaySchedlueAsOneSecond() throws Exception {

		loadTrackingEmail();
		Thread.sleep(10*1000);
		sendTrackingEmail();
	}

}
