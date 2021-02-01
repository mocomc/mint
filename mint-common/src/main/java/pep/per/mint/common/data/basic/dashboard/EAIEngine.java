/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic.dashboard;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Server;


/**
 *  Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class EAIEngine  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -2223829665116607255L;

	/*** 서버*/
	@JsonProperty("server")
	Server server = null;

	/*** AgentNm */
	@JsonProperty("agentNm")
	String agentNm = defaultStringValue;

	/*** RunnerNm */
	@JsonProperty("runnerNm")
	String runnerNm = defaultStringValue;

	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;

	/*** 상태*/
	@JsonProperty("status")
	String status = defaultStringValue;
	
	/*** 상태*/
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;	

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getRunnerNm() {
		return runnerNm;
	}

	public void setRunnerNm(String runnerNm) {
		this.runnerNm = runnerNm;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
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

}
