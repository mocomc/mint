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
public class Queue  extends CacheableObject{



	/**
	 *
	 */
	private static final long serialVersionUID = 1242180279336283971L;

	/*** 서버*/
	@JsonProperty("server")
	Server server = null;

	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;

	/*** 큐관리자 명 */
	@JsonProperty("qmgrNm")
	String qmgrNm = defaultStringValue;

	/*** 큐 명 */
	@JsonProperty("queueNm")
	String queueNm = defaultStringValue;

	/*** 임계값 */
	@JsonProperty("limit")
	String limit = defaultStringValue;

	/*** 입계값 비교 옵션 */
	@JsonProperty("limitOpt")
	String limitOpt = defaultStringValue;

	/*** 최종큐깁이 */
	@JsonProperty("depth")
	int depth = 0;

	/*** 최종상대깊이 */
	@JsonProperty("perDepth")
	int perDepth = 0;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;


	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getQmgrNm() {
		return qmgrNm;
	}

	public void setQmgrNm(String qmgrNm) {
		this.qmgrNm = qmgrNm;
	}

	public String getQueueNm() {
		return queueNm;
	}

	public void setQueueNm(String queueNm) {
		this.queueNm = queueNm;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimitOpt() {
		return limitOpt;
	}

	public void setLimitOpt(String limitOpt) {
		this.limitOpt = limitOpt;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPerDepth() {
		return perDepth;
	}

	public void setPerDepth(int perDepth) {
		this.perDepth = perDepth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
