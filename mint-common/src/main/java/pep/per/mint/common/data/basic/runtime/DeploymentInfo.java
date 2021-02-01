/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since 2021. 1. 12.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeploymentInfo extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	String integrationId;

	@JsonProperty
	String interfaceNm;

	@JsonProperty
	String businessCd;

	@JsonProperty
	String senderSystemCd;

	@JsonProperty
	String senderSystemNm;

	@JsonProperty
	String receiverSystemCd;

	@JsonProperty
	String receiverSystemNm;

	@JsonProperty
	String channelCd;

	@JsonProperty
	String channelNm;

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
	 * @return the businessCd
	 */
	public String getBusinessCd() {
		return businessCd;
	}

	/**
	 * @param businessCd the businessCd to set
	 */
	public void setBusinessCd(String businessCd) {
		this.businessCd = businessCd;
	}

	/**
	 * @return the senderSystemCd
	 */
	public String getSenderSystemCd() {
		return senderSystemCd;
	}

	/**
	 * @param senderSystemCd the senderSystemCd to set
	 */
	public void setSenderSystemCd(String senderSystemCd) {
		this.senderSystemCd = senderSystemCd;
	}

	/**
	 * @return the senderSystemNm
	 */
	public String getSenderSystemNm() {
		return senderSystemNm;
	}

	/**
	 * @param senderSystemNm the senderSystemNm to set
	 */
	public void setSenderSystemNm(String senderSystemNm) {
		this.senderSystemNm = senderSystemNm;
	}

	/**
	 * @return the receiverSystemCd
	 */
	public String getReceiverSystemCd() {
		return receiverSystemCd;
	}

	/**
	 * @param receiverSystemCd the receiverSystemCd to set
	 */
	public void setReceiverSystemCd(String receiverSystemCd) {
		this.receiverSystemCd = receiverSystemCd;
	}

	/**
	 * @return the receiverSystemNm
	 */
	public String getReceiverSystemNm() {
		return receiverSystemNm;
	}

	/**
	 * @param receiverSystemNm the receiverSystemNm to set
	 */
	public void setReceiverSystemNm(String receiverSystemNm) {
		this.receiverSystemNm = receiverSystemNm;
	}

	/**
	 * @return the channelCd
	 */
	public String getChannelCd() {
		return channelCd;
	}

	/**
	 * @param channelCd the channelCd to set
	 */
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
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



}
