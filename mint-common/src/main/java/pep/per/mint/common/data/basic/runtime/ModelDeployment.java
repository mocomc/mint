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
 * @since 2020. 11. 23.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelDeployment extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	String deployDate;

	@JsonProperty
	String interfaceMid;

	@JsonProperty
	int seq;

	@JsonProperty
	String systemId;

	@JsonProperty
	String systemNm;

	@JsonProperty
	String systemCd;


	@JsonProperty
	String serverId;

	@JsonProperty
	String serverCd;

	@JsonProperty
	String serverNm;


	@JsonProperty
	String method;

	@JsonProperty
	String resultCd;

	@JsonProperty
	String resultMsg;

	@JsonProperty
	String deployUser;

	@JsonProperty
	String contents;

	@JsonProperty
	DeploymentInfo deploymentInfo;

	/**
	 * @return the deployDate
	 */
	public String getDeployDate() {
		return deployDate;
	}

	/**
	 * @param deployDate the deployDate to set
	 */
	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}

	/**
	 * @return the interfaceMid
	 */
	public String getInterfaceMid() {
		return interfaceMid;
	}

	/**
	 * @param interfaceMid the interfaceMid to set
	 */
	public void setInterfaceMid(String interfaceMid) {
		this.interfaceMid = interfaceMid;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return resultCd;
	}

	/**
	 * @param resultCd the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the deployUser
	 */
	public String getDeployUser() {
		return deployUser;
	}

	/**
	 * @param deployUser the deployUser to set
	 */
	public void setDeployUser(String deployUser) {
		this.deployUser = deployUser;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the systemNm
	 */
	public String getSystemNm() {
		return systemNm;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	/**
	 * @return the systemCd
	 */
	public String getSystemCd() {
		return systemCd;
	}

	/**
	 * @param systemCd the systemCd to set
	 */
	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	/**
	 * @return the serverCd
	 */
	public String getServerCd() {
		return serverCd;
	}

	/**
	 * @param serverCd the serverCd to set
	 */
	public void setServerCd(String serverCd) {
		this.serverCd = serverCd;
	}

	/**
	 * @return the serverNm
	 */
	public String getServerNm() {
		return serverNm;
	}

	/**
	 * @param serverNm the serverNm to set
	 */
	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	/**
	 * @return the deploymentInfo
	 */
	public DeploymentInfo getDeploymentInfo() {
		return deploymentInfo;
	}

	/**
	 * @param deploymentInfo the deploymentInfo to set
	 */
	public void setDeploymentInfo(DeploymentInfo deploymentInfo) {
		this.deploymentInfo = deploymentInfo;
	}



}
