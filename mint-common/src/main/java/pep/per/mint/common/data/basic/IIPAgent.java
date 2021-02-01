/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;

/**
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class IIPAgent  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -1837407118944823363L;

	@JsonProperty("agentId")
	String agentId = defaultStringValue;

	@JsonProperty("agentNm")
	String agentNm = defaultStringValue;

	@JsonProperty("agentCd")
	String agentCd = defaultStringValue;

	@JsonProperty("type")
	String type = defaultStringValue;

	@JsonProperty("typeNm")
	String typeNm = defaultStringValue;

	@JsonProperty("hostname")
	String hostname = defaultStringValue;

	@JsonProperty("port")
	int port = 0;

	@JsonProperty("logLevel")
	String logLevel = defaultStringValue;

	@JsonProperty("logLevelNm")
	String logLevelNm = defaultStringValue;

	@JsonProperty("status")
	String status = defaultStringValue;

	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;

	@JsonProperty("server")
	Server server = null;

	@JsonProperty("comments")
	String comments = defaultStringValue;

	@JsonProperty("delYn")
	String delYn = "N";

	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	String regId = defaultStringValue;

	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	String modId = defaultStringValue;

	@JsonProperty("itemType")
	String itemType = defaultStringValue;

	/*** 큐관리자 리스트 */
	@JsonProperty("qmgrList")
	List<QueueManager> qmgrList = new ArrayList<QueueManager>();

	/*** 시스템 리소스 리스트 */
	@JsonProperty("serverResourceList")
	List<ServerResource> serverResourceList = new ArrayList<ServerResource>();

	/*** 시스템 프로세스 리스트 */
	@JsonProperty("serverProcessList")
	List<ServerProcess> serverProcessList = new ArrayList<ServerProcess>();

	/*** 순번  */
	@JsonProperty("seq")
	int seq;

	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogLevelNm() {
		return logLevelNm;
	}

	public void setLogLevelNm(String logLevelNm) {
		this.logLevelNm = logLevelNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public List<QueueManager> getQmgrList() {
		return qmgrList;
	}

	public void setQmgrList(List<QueueManager> qmgrList) {
		this.qmgrList = qmgrList;
	}

	public List<ServerResource> getServerResourceList() {
		return serverResourceList;
	}

	public void setServerResourceList(List<ServerResource> serverResourceList) {
		this.serverResourceList = serverResourceList;
	}

	public List<ServerProcess> getServerProcessList() {
		return serverProcessList;
	}

	public void setServerProcessList(List<ServerProcess> serverProcessList) {
		this.serverProcessList = serverProcessList;
	}




}
