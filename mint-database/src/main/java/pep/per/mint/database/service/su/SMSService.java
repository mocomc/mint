package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.sms.Recipient;
import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.data.basic.sms.SmsResource;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.SmsServiceMapper;

@Service
public class SMSService {

	public static final int DEFAULT_SMS_FETCH_CNT = 1000;

	public static final int DEFAULT_SMS_LOAD_CNT = 1000;

    public static final int DEFAULT_SAMPLING_HOUR = 3;


	Logger logger = LoggerFactory.getLogger(SMSService.class);

	@Autowired
	SmsServiceMapper smsServiceMapper;

	public static int fetchCount = DEFAULT_SMS_FETCH_CNT;

	public static int loadCount = DEFAULT_SMS_LOAD_CNT;

    public int samplingHour = DEFAULT_SAMPLING_HOUR;

    public boolean existSmsResource(String key1, String key2) throws Exception{
        String rs = smsServiceMapper.existSmsResource(key1, key2);
        return Util.isEmpty(rs) ? false : true;
    }

	public List<SmsResource> getSmsResourceForTracking() throws Exception{
		String stDate = smsServiceMapper.selectStDateSmsResourceForTracking();
        String samplingDate = Util.getHourAdd(Util.DEFAULT_DATE_FORMAT_MI, stDate, -1 * samplingHour) + "000";
		logger.debug("stDate:" + stDate);
        logger.debug("samplingDate:" + samplingDate);
        //String stDate = "20170823003221254001";
		return smsServiceMapper.getSmsResourceForTracking(loadCount, samplingDate);
	}


	@Transactional
	public void loadSmsForTracking(List<Sms> smslist) throws Exception{
		for (Sms sms : smslist) {
			sms.setSmsId(UUID.randomUUID().toString());
			smsServiceMapper.insertSms(sms);
			SmsResource resource = (SmsResource) sms.getResource();
			resource.setSmsId(sms.getSmsId());
			resource.setType(sms.getType());
			smsServiceMapper.insertSmsResource(resource);
		}
	}

	public List<Sms>getSmsForTracking(Map params) throws Exception{
		String fromDate = Util.join(Util.getFormatedDate("yyyyMMdd"),"000000000");
		params.put("fetchCount", fetchCount);
		params.put("fromDate", fromDate);
		return smsServiceMapper.getSmsForTracking( params);
	}

	@Transactional
	public void updateSmsSendResult(Sms sms) throws Exception{
		smsServiceMapper.updateSms(sms);
	}


	public List<String> getSmsRecipientsByQmgrId(String qmgrId) throws Exception {
		return smsServiceMapper.getSmsRecipientsByQmgrId(qmgrId);
	}

	public List<String> getSmsRecipientsByProcessId(String processId) throws Exception {
		return smsServiceMapper.getSmsRecipientsByProcessId(processId);
	}

	public List<String> getSmsRecipientsByResourceId(String resourceId) throws Exception {
		return smsServiceMapper.getSmsRecipientsByResourceId(resourceId);
	}

	public List<String> getSmsRecipients() throws Exception {
		return smsServiceMapper.getSmsRecipients();
	}

	/**
	 * @param sms
	 * @throws Exception
	 */
	public void addSms(Sms sms) throws Exception {
		sms.setSmsId(UUID.randomUUID().toString());
		smsServiceMapper.insertSms(sms);
		SmsResource resource = (SmsResource) sms.getResource();
		resource.setSmsId(sms.getSmsId());
		smsServiceMapper.insertSmsResource(resource);
	}

}
