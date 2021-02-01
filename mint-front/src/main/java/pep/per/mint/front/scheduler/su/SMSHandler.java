package pep.per.mint.front.scheduler.su;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.su.SMSService;
import pep.per.mint.front.scheduler.ScheduleHandler;
import pep.per.mint.inhouse.sms.SendSMSService;

@Component
public class SMSHandler extends ScheduleHandler{

	private static final Logger logger = LoggerFactory.getLogger(SMSHandler.class);


	@Autowired
	CommonService commonService;

	@Autowired
	SendSMSService sendSMSService;

	@Autowired
	SMSService smsService;

//	@Autowired
//	MonitorService monitorService;

	List<String> defaultRecipents = null;

	public void setEnvironment(){

	//--------------------------------------------------------------
    	// set schedule.sms.handler.fixed.delay var
    	//--------------------------------------------------------------
    	{
	    	long fixedDelayAsOneSecond = 0;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.sms.handler.fixed.delay");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.sms.handler.fixed.delay");
	    			if(!Util.isEmpty(envs)){
	    				fixedDelayAsOneSecond = Long.parseLong((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	this.fixedDelayAsOneSecond = fixedDelayAsOneSecond == 0 ? ScheduleHandler.DEFAULT_FIXED_DELAY_AS_SEC : fixedDelayAsOneSecond;
	    	logger.debug(Util.join("The env [support.schedule.sms.handler.fixed.delay]:",fixedDelayAsOneSecond));
    	}
    	try {
			sendSMSService.init();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	public void loadTrackingSMS(){
		// TODO
    }

	public void sendTrackingSMS(){

    	//--------------------------------------------------------------
    	// set schedule.sms.send.tracking.sms var
    	//--------------------------------------------------------------
    	{
	    	boolean sendSms = false;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.sms.send.tracking.sms");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.sms.send.tracking.sms");
	    			if(!Util.isEmpty(envs)){
	    				sendSms = Boolean.parseBoolean((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.sms.send.tracking.sms]:",sendSms));
	    	if(!sendSms) return;
    	}

    	//--------------------------------------------------------------
    	// set schedule.sms.fetch.tracking.sms.cnt var
    	//--------------------------------------------------------------
    	{
	    	int fetchCnt = 0;
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.sms.fetch.tracking.sms.cnt");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.sms.fetch.tracking.sms.cnt");
	    			if(!Util.isEmpty(envs)){
	    				fetchCnt = Integer.parseInt((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.sms.fetch.tracking.sms.cnt]:",fetchCnt));

	    	smsService.fetchCount = fetchCnt == 0 ? smsService.DEFAULT_SMS_FETCH_CNT : fetchCnt;
    	}

    	//--------------------------------------------------------------
    	// set schedule.sms.fetch.tracking.sms.cnt var
    	//--------------------------------------------------------------
    	int retryCnt = 1;
    	{

	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("support", "schedule.sms.retry.cnt");
	    		if(envm != null){
	    			List<String> envs = envm.get("support.schedule.sms.retry.cnt");
	    			if(!Util.isEmpty(envs)){
	    				retryCnt = Integer.parseInt((envs.get(0)));
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [support.schedule.sms.retry.cnt]:",retryCnt));
    	}


    	try{
    		logger.info(Util.join(SMSHandler.class.getName(),".sendTrackingSMS() schedule start"));

    		Map listParams = new HashMap();
    		listParams.put("retry", retryCnt);
    		List<Sms> smses = smsService.getSmsForTracking(listParams);
    		if(!Util.isEmpty(smses)){
    			for (Sms sms : smses) {
    				try{

    					Map checkMap = new HashMap();
    					checkMap.put("smsId", sms.getSmsId());
    					checkMap.put("type", sms.getType());
    					checkMap.put("key1", sms.getRegId());
    					checkMap.put("key2", sms.getModId());

    					if(sendSMSService.checkSendSMS(checkMap)){
        					sms.setSndReqDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

        					String recipientsString = sms.getRecipients();
        					String [] recipientsArray = recipientsString.split(",");
        					List<String> recipients = Arrays.asList(recipientsArray);
        					Map params = new HashMap();
        					params.put("subject", sms.getSubject());
        					params.put("contents", sms.getContents());

        					for(String recv : recipients){
        						if(!(recv == null || recv.length()<=0)){
        							params.put("recipients", recv);
                					sendSMSService.sendSMS(params);
        						}
        					}
        					sms.setResCd("0");
        					sms.setResMsg("success");
        					sms.setSndYn("Y");
    						logger.debug(Util.join(SMSHandler.class.getName(),".sendSms OK:smsId:[",sms.getSmsId(),"]"));

    					}else{
    						sms.setRetry(retryCnt+1);
    						sms.setResCd("8");
        					sms.setResMsg("skip sms sending.");
        					sms.setSndYn("Y");
    					}
    				}catch(Exception ex){
    					sms.setResCd("9");
    					sms.setRetry(sms.getRetry()+1);
    					sms.setResMsg(ex.getMessage());
    					sms.setSndYn("N");
						logger.error(Util.join(SMSHandler.class.getName(),".sendSms error:smsId:[",sms.getSmsId(),"]"), ex);
    				}finally{
    					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
    					sms.setModDate(modDate);
    					sms.setModId("iip");
    					sms.setResDate(modDate);
						smsService.updateSmsSendResult(sms);
    					logger.debug("done updateSMSSendResult");
    				}
				}
    		}
    	}catch(Throwable e){
    		logger.error(Util.join(SMSHandler.class.getName(),".sendTrackingSMS() schedule error:"), e);
    	}finally{
    		logger.info(Util.join(SMSHandler.class.getName(),".sendTrackingSMS() schedule end"));
    	}

    }

	@Override
	public void doFixedDelaySchedlueAsOneSecond() throws Exception {
		loadTrackingSMS();
		Thread.sleep(10*1000);
		sendTrackingSMS();
	}

}
