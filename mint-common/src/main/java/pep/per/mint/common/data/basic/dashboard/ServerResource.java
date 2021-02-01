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
public class ServerResource  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -8182520282425572339L;

	/*** 서버*/
	@JsonProperty("server")
	Server server = null;
	
	/*** Resource Id*/
	@JsonProperty("resourceId")
	String resourceId = defaultStringValue;	
	
	/*** Resource Nm*/
	@JsonProperty("resourceNm")
	String resourceNm = defaultStringValue;	
	
	/*** 수집시간 */
	@JsonProperty("getDate")
	String getDate = defaultStringValue;
	
	/*** 전체사용량 */
	@JsonProperty("totalAmount")
	String totalAmount = defaultStringValue;	

	/*** 사용량 */
	@JsonProperty("usedAmount")
	String usedAmount = defaultStringValue;

	/*** 사용률*/
	@JsonProperty("usedPer")
	String usedPer = defaultStringValue;

	/*** 상태*/
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 임계값 */
	@JsonProperty("limit")
	String limit  = defaultStringValue;

	/*** 추가정보 1 */
	@JsonProperty("info1")
	String info1 = defaultStringValue;

	/*** 추가정보 2 */
	@JsonProperty("info2")
	String info2 = defaultStringValue;

	/*** 추가정보 3 */
	@JsonProperty("info3")
	String info3 = defaultStringValue;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceNm() {
		return resourceNm;
	}

	public void setResourceNm(String resourceNm) {
		this.resourceNm = resourceNm;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(String usedAmount) {
		this.usedAmount = usedAmount;
	}

	public String getUsedPer() {
		return usedPer;
	}

	public void setUsedPer(String usedPer) {
		this.usedPer = usedPer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}



}
