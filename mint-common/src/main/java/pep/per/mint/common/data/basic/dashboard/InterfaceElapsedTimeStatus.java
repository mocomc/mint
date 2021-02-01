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
public class InterfaceElapsedTimeStatus extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7135434982219048239L;

	
	@JsonProperty("channelId")
	String channelId;
	
	@JsonProperty("channelNm")
	String channelNm;
	
	@JsonProperty("msgDateTime")
	String msgDateTime;
	
	@JsonProperty("elapsedTime")
	String elapsedTime;
	
	@JsonProperty("masterLogId")
	String masterLogId;
	
	@JsonProperty("globalId")
	String globalId;

	@JsonProperty("interfaceId")
	String interfaceId;
	
	@JsonProperty("integrationId")
	String integrationId;
	
	
	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelNm
	 */
	public String getChannelNm() {
		return channelNm;
	}

	/**
	 * @param channelNm the channelNm to set
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
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
	 * @return the elapsedTime
	 */
	public String getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

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

	
}
