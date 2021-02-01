package pep.per.mint.database.mapper.su;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.data.basic.email.EmailResourceForTracking;
import pep.per.mint.common.data.basic.email.Recipient;

public interface EmailServiceMapper {

	public String selectStDateEmailResourceForTracking() throws Exception;

    public String existEmailResource(@Param("key1")String key1, @Param("key2")String key2) throws Exception;

	public List<EmailResourceForTracking> getEmailResourceForTracking(@Param("fetchCount")int fetchCount, @Param("stDate")String stDate) throws Exception;

	public int insertEmail(Email email) throws Exception;

	public int insertEmailResourceForTracking(EmailResourceForTracking resource) throws Exception;

	public List<Email>getEmailsForTracking(@Param("fetchCount")int fetchCount, @Param("fromDate")String fromDate) throws Exception;

	public int updateEmail(Email email) throws Exception;

	public List<Recipient> getEmailRecipients(String type) throws Exception;

	/**
	 * 알람 이벤트 수신자 이메일 리스트 조회
	 * @return
	 */
	public List<String> getEmailRecipientsForAlarm() throws Exception;

}