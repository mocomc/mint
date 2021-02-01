package pep.per.mint.database.service.su;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.data.basic.email.EmailResourceForTracking;
import pep.per.mint.common.data.basic.email.Recipient;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.EmailServiceMapper;

@Service
public class EmailService {

	public static final int DEFAULT_EMAIL_FETCH_CNT = 1000;

	public static final int DEFAULT_EMAIL_LOAD_CNT = 1000;

    public static final int DEFAULT_SAMPLING_HOUR = 3;


	Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	EmailServiceMapper emailServiceMapper;

	public static int fetchCount = DEFAULT_EMAIL_FETCH_CNT;

	public static int loadCount = DEFAULT_EMAIL_LOAD_CNT;

    public int samplingHour = DEFAULT_SAMPLING_HOUR;

    public boolean existEmailResource(String key1, String key2) throws Exception{
        String rs = emailServiceMapper.existEmailResource(key1, key2);
        return Util.isEmpty(rs) ? false : true;
    }

	public List<EmailResourceForTracking> getEmailResourceForTracking() throws Exception{
		String stDate = emailServiceMapper.selectStDateEmailResourceForTracking();
        String samplingDate = Util.getHourAdd(Util.DEFAULT_DATE_FORMAT_MI, stDate, -1 * samplingHour) + "000";
		logger.debug("stDate:" + stDate);
        logger.debug("samplingDate:" + samplingDate);
        //String stDate = "20170823003221254001";
		return emailServiceMapper.getEmailResourceForTracking(loadCount, samplingDate);
	}

	public List<Recipient> getEmailRecipients(String type) throws Exception{
		return emailServiceMapper.getEmailRecipients(type);
	}

	public List<Recipient> getEmailRecipientsForTracking() throws Exception{
		return emailServiceMapper.getEmailRecipients(Recipient.TYPE_RCV_TRACKING);
	}


	public String getEmailRecipientsForAlarm() throws Exception{
		//return emailServiceMapper.getEmailRecipientsForAdmin(Recipient.TYPE_RCV_TRACKING);
		List<String> list = emailServiceMapper.getEmailRecipientsForAlarm();
		if(list == null || list.size() == 0) {
			return "none";
		}else {
			StringBuffer recipientString = new StringBuffer();
			recipientString.append(list.get(0));
			for(int i = 1 ; i < list.size() ; i ++) {
				recipientString.append(",").append(list.get(i));
			}
			return recipientString.toString();
		}
	}

	@Transactional
	public void loadEmailsForTracking(List<Email> emails) throws Exception{
		for (Email email : emails) {
			email.setEmailId(UUID.randomUUID().toString());
			emailServiceMapper.insertEmail(email);
			if(email.getResource() != null) {
				EmailResourceForTracking resource = (EmailResourceForTracking) email.getResource();
				resource.setEmailId(email.getEmailId());
				resource.setType(email.getType());
				emailServiceMapper.insertEmailResourceForTracking(resource);
			}
		}
	}

	@Transactional
	public void loadEmailsForAlarm(List<Email> emails) throws Exception{
		loadEmailsForTracking(emails);
	}


	public List<Email>getEamilsForTracking() throws Exception{
		String fromDate = Util.join(Util.getFormatedDate("yyyyMMdd"),"000000000");
		return emailServiceMapper.getEmailsForTracking(fetchCount, fromDate);
	}

	@Transactional
	public void updateEmailSendResult(Email email) throws Exception{
		emailServiceMapper.updateEmail(email);
	}

}
