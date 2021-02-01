package pep.per.mint.inhouse.mail;

import java.util.Map;

public interface SendMailService {

	public String buildContents(Map<String,?> params) throws Exception;

	public void sendMail(Map<String, ?> params) throws Exception;

	public String getSendMailAddress();

	public String getSubject(Map<String, ?> params);


}
