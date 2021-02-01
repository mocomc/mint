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
public class Trigger  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 1242180279336283971L;


	@JsonProperty
	String status = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String ipAddress = defaultStringValue;

	@JsonProperty
	String execDate = defaultStringValue;

	@JsonProperty
	String clCnt = defaultStringValue;

	@JsonProperty
	String crrCnt = defaultStringValue;

	@JsonProperty
	String maxDate = defaultStringValue;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getExecDate() {
		return execDate;
	}

	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}

	public String getClCnt() {
		return clCnt;
	}

	public void setClCnt(String clCnt) {
		this.clCnt = clCnt;
	}

	public String getCrrCnt() {
		return crrCnt;
	}

	public void setCrrCnt(String crrCnt) {
		this.crrCnt = crrCnt;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}



}
