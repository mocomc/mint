/**
 * 
 */
package pep.per.mint.common.data.basic.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DelayInterface extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377951484678813495L;

	@JsonProperty("masterLogId")
	String masterLogId;
	
	@JsonProperty("msgDateTime")
	String msgDateTime;

	@JsonProperty("masterLogCount")
	String masterLogCount;
	
	@JsonProperty("interfaceId")
	String interfaceId;
	
	@JsonProperty("integrationId")
	String integrationId;
	
	@JsonProperty("interfaceNm")
	String interfaceNm;
	
	@JsonProperty("sndSystemNm")
	String sndSystemNm;
	
	@JsonProperty("recentPrDt")
	String recentPrDt;

	@JsonProperty("globalId")
	String globalId;
	
	/**
	 * @return the masterLogId
	 */
	public String getMasterLogId() {
		return masterLogId;
	}

	/**
	 * @param masterLogId the masterLogId to set
	 */
	public void setMasterLogId(String masterLogId) {
		this.masterLogId = masterLogId;
	}

	/**
	 * @return the msgDateTime
	 */
	public String getMsgDateTime() {
		return msgDateTime;
	}

	/**
	 * @param msgDateTime the msgDateTime to set
	 */
	public void setMsgDateTime(String msgDateTime) {
		this.msgDateTime = msgDateTime;
	}
	
	/**
	 * @return the masterLogCount
	 */
	public String getMasterLogCount() {
		return masterLogCount;
	}

	/**
	 * @param masterLogCount the masterLogCount to set
	 */
	public void setMasterLogCount(String masterLogCount) {
		this.masterLogCount = masterLogCount;
	}

	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the integrationId
	 */
	public String getIntegrationId() {
		return integrationId;
	}

	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	/**
	 * @return the interfaceNm
	 */
	public String getInterfaceNm() {
		return interfaceNm;
	}

	/**
	 * @param interfaceNm the interfaceNm to set
	 */
	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	/**
	 * @return the sndSystemNm
	 */
	public String getSndSystemNm() {
		return sndSystemNm;
	}

	/**
	 * @param sndSystemNm the sndSystemNm to set
	 */
	public void setSndSystemNm(String sndSystemNm) {
		this.sndSystemNm = sndSystemNm;
	}

	/**
	 * @return the recentPrDt
	 */
	public String getRecentPrDt() {
		return recentPrDt;
	}

	/**
	 * @param recentPrDt the recentPrDt to set
	 */
	public void setRecentPrDt(String recentPrDt) {
		this.recentPrDt = recentPrDt;
	}

	/**
	 * @return the globalId
	 */
	public String getGlobalId() {
		return globalId;
	}

	/**
	 * @param globalId the globalId to set
	 */
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}


}
