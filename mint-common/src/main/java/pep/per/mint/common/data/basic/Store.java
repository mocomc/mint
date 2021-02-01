/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Store  extends CacheableObject{

	private static final long serialVersionUID = -267877595492598356L;

	@JsonProperty("storeId")
	String storeId = defaultStringValue;

	@JsonProperty("storeCd")
	String storeCd = defaultStringValue;

	@JsonProperty("posNo")
	String posNo = defaultStringValue;

	@JsonProperty("storeNm")
	String storeNm = defaultStringValue;

	@JsonProperty("custStoreCd")
	String custStoreCd = defaultStringValue;

	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regUser")
	String regUser = defaultStringValue;

	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modUser")
	String modUser = defaultStringValue;

	@JsonProperty("posMonitorYn")
	String posMonitorYn = defaultStringValue;

	/* 점포주소 */
	@JsonProperty("bizplAddr")
	String bizplAddr = defaultStringValue;

	/* 인덱스 */
	@JsonProperty("idx")
	String idx = defaultStringValue;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getStoreNm() {
		return storeNm;
	}

	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
	}

	public String getCustStoreCd() {
		return custStoreCd;
	}

	public void setCustStoreCd(String custStoreCd) {
		this.custStoreCd = custStoreCd;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getPosMonitorYn() {
		return posMonitorYn;
	}

	public void setPosMonitorYn(String posMonitorYn) {
		this.posMonitorYn = posMonitorYn;
	}

	public String getBizplAddr() {
		return bizplAddr;
	}

	public void setBizplAddr(String bizplAddr) {
		this.bizplAddr = bizplAddr;
	}
	
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

}
