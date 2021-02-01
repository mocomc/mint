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
 * 시스템 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class QueueManager  extends CacheableObject{

	public static final String STATUS_ON = "0";
	public static final String STATUS_OFF = "9";
	/**
	 *
	 */
	private static final long serialVersionUID = -4336673019616392263L;

	/*** 서버*/
	@JsonProperty("server")
	Server server = null;

	/*** 큐관리자 명 */
	@JsonProperty("qmgrNm")
	String qmgrNm = defaultStringValue;

	/*** 큐관리자 IP */
	@JsonProperty("qmgrIp")
	String qmgrIp = defaultStringValue;

	/*** 큐관리자 PORT */
	@JsonProperty("qmgrPort")
	String qmgrPort = defaultStringValue;

	/*** STATUS */
	@JsonProperty("status")
	String status = STATUS_OFF;

	/*** 상태*/
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;

	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getQmgrNm() {
		return qmgrNm;
	}

	public void setQmgrNm(String qmgrNm) {
		this.qmgrNm = qmgrNm;
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

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getQmgrIp() {
		return qmgrIp;
	}

	public void setQmgrIp(String qmgrIp) {
		this.qmgrIp = qmgrIp;
	}

	public String getQmgrPort() {
		return qmgrPort;
	}

	public void setQmgrPort(String qmgrPort) {
		this.qmgrPort = qmgrPort;
	}

}
