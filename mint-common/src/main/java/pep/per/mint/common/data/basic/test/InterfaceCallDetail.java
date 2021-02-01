/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pep.per.mint.common.data.CacheableObject;

/**
 * <pre>
 * pep.per.mint.common.data.basic.test
 * InterfaceCallDetail.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 1.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceCallDetail extends CacheableObject{

	@JsonProperty
	String testId = defaultStringValue;

	@JsonProperty
	String testDate = defaultStringValue;

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	String systemCd = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String serverId = defaultStringValue;

	@JsonProperty
	String serverNm = defaultStringValue;

	@JsonProperty
	String interfaceId = defaultStringValue;

	@JsonProperty
	String integrationId = defaultStringValue;

	@JsonProperty
	int seq = 1;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	String resourceType = defaultStringValue;


	@JsonProperty
	String reqDate = defaultStringValue;

	@JsonProperty
	String resDate = defaultStringValue;

	@JsonProperty
	String status = InterfaceCallMaster.STATUS_ING;

	@JsonProperty
	String statusNm = defaultStringValue;


	@JsonProperty
	String agentNm = defaultStringValue;

	@JsonProperty
	String message = defaultStringValue;

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemCd() {
		return systemCd;
	}

	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}


	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getResDate() {
		return resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getServerNm() {
		return serverNm;
	}

	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}



	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceCallDetail [testId=");
		builder.append(testId);
		builder.append(", testDate=");
		builder.append(testDate);
		builder.append(", systemId=");
		builder.append(systemId);
		builder.append(", systemNm=");
		builder.append(systemNm);
		builder.append(", serverId=");
		builder.append(serverId);
		builder.append(", serverNm=");
		builder.append(serverNm);
		builder.append(", interfaceId=");
		builder.append(interfaceId);
		builder.append(", integrationId=");
		builder.append(integrationId);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", reqDate=");
		builder.append(reqDate);
		builder.append(", resDate=");
		builder.append(resDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusNm=");
		builder.append(statusNm);
		builder.append(", agentNm=");
		builder.append(agentNm);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
