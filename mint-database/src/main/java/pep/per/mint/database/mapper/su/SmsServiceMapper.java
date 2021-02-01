package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.sms.Recipient;
import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.data.basic.sms.SmsResource;

public interface SmsServiceMapper {

	public String selectStDateSmsResourceForTracking() throws Exception;

    public String existSmsResource(@Param("key1")String key1, @Param("key2")String key2) throws Exception;

	public List<SmsResource> getSmsResourceForTracking(@Param("fetchCount")int fetchCount, @Param("stDate")String stDate) throws Exception;

	public int insertSms(Sms sms) throws Exception;

	public int insertSmsResource(SmsResource resource) throws Exception;

//	public List<Sms>getSmsForTracking(@Param("fetchCount")int fetchCount, @Param("fromDate")String fromDate) throws Exception;
	public List<Sms>getSmsForTracking(Map params) throws Exception;

	public int updateSms(Sms sms) throws Exception;

	public List<String> getSmsRecipients()throws Exception;

	/**
	 *
	 * @param qmgrId
	 * @return
	 * @throws Exception
	 * @deprecated 201808
	 */
	public List<String> getSmsRecipientsByQmgrId(@Param("qmgrId")String qmgrId)throws Exception;

	/**
	 * @deprecated 201808
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public List<String> getSmsRecipientsByResourceId(@Param("resourceId")String resourceId)throws Exception;

	/**
	 * @deprecated 201808
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public List<String> getSmsRecipientsByProcessId(@Param("processId")String processId)throws Exception;


}