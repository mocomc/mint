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
public class ServerProcess  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -7473750907944085770L;

	/*** 서버*/
	@JsonProperty("server")
	Server server = null;

	/*** 프로세스 ID */
	@JsonProperty("processId")
	String processId = defaultStringValue;	
	
	/*** 프로세스 명 */
	@JsonProperty("processNm")
	String processNm = defaultStringValue;

	/*** 프로세스 체크값(패턴) */
	@JsonProperty("checkValue")
	String checkValue = defaultStringValue;

	/*** 프로세스갯수(기준값) */
	@JsonProperty("checkCnt")
	int checkCnt  = 0;

	/*** 상태*/
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 프로세스갯수(현재값) */
	@JsonProperty("cnt")
	int cnt  = 0;
	
	/*** 프로세스NO(PID) */
	@JsonProperty("processNo")
	String processNo  = defaultStringValue;	
	
	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;	

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}	

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getProcessNm() {
		return processNm;
	}

	public void setProcessNm(String processNm) {
		this.processNm = processNm;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public int getCheckCnt() {
		return checkCnt;
	}

	public void setCheckCnt(int checkCnt) {
		this.checkCnt = checkCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}


}
