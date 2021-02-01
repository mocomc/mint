package pep.per.mint.inhouse.sms;

import java.util.Map;

public interface SendSMSService {

	public void init() throws Exception;

	public String buildContents(Map<String,?> params) throws Exception;

	public void sendSMS(Map<String, Object> params) throws Exception;

	public String getSendSMSAddress();

	public String getSubject(Map<String, ?> params);

	public boolean checkSendSMS(Map<String, ?> params) throws Exception;

	public int getFromCheckTime();

}
