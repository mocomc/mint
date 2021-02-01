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
 * 거래로그상세 Data Object
 *
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class GSTRLogDetail  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 180026811274790133L;
	
	
	/*송/수신 구분*/
	@JsonProperty("snrcSpNm")
	String snrcSpNm = defaultStringValue;
	
	/*송/수신 시스템코드*/
	@JsonProperty("sysCd")
	String sysCd = defaultStringValue;
	
	/*송/수신 시스템명*/
	@JsonProperty("sysNm")
    String sysNm = defaultStringValue;
	
	/*본부/점포 구분*/
	@JsonProperty("mntrInfoInqSpCd")
    String mntrInfoInqSpCd = defaultStringValue;
	
	/*처리시간*/
	@JsonProperty("procDttm")
    String procDttm = defaultStringValue;
	
	/*처리상태 코드*/
	@JsonProperty("status")
    String status = defaultStringValue;
	
	/*처리상태*/
	@JsonProperty("statusNm")
    String statusNm = defaultStringValue;
	
	/*총레코드 건수*/
	@JsonProperty("totTrnsRecCnt")
	int totTrnsRecCnt = 0;
	
	/*작업레코드 건수*/
	@JsonProperty("wrkRecCnt")
    int wrkRecCnt = 0;
	
	/*에러내용*/
	@JsonProperty("errorMsg")
    String errorMsg = defaultStringValue;
	
	

	public String getSnrcSpNm() {
		return snrcSpNm;
	}

	public void setSnrcSpNm(String snrcSpNm) {
		this.snrcSpNm = snrcSpNm;
	}

	public String getSysCd() {
		return sysCd;
	}

	public void setSysCd(String sysCd) {
		this.sysCd = sysCd;
	}

	public String getSysNm() {
		return sysNm;
	}

	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
	}

	public String getMntrInfoInqSpCd() {
		return mntrInfoInqSpCd;
	}

	public void setMntrInfoInqSpCd(String mntrInfoInqSpCd) {
		this.mntrInfoInqSpCd = mntrInfoInqSpCd;
	}

	public String getProcDttm() {
		return procDttm;
	}

	public void setProcDttm(String procDttm) {
		this.procDttm = procDttm;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getTotTrnsRecCnt() {
		return totTrnsRecCnt;
	}

	public void setTotTrnsRecCnt(int totTrnsRecCnt) {
		this.totTrnsRecCnt = totTrnsRecCnt;
	}

	public int getWrkRecCnt() {
		return wrkRecCnt;
	}

	public void setWrkRecCnt(int wrkRecCnt) {
		this.wrkRecCnt = wrkRecCnt;
	}
	
}
