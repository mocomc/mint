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
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 거래로그 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRLog  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -4854074087253861062L;

	/*** 로그ID */
	@JsonProperty("logId")
	String logId = defaultStringValue;

	/*** 로그키1 */
	@JsonProperty("logKey1")
	String logKey1 = defaultStringValue;

	/*** 로그키2 */
	@JsonProperty("logKey2")
	String logKey2 = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 상태 */
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;

	/*** 상태 */
	@JsonProperty("dataPrMethod")
	String dataPrMethod = defaultStringValue;

	/*** 상태 */
	@JsonProperty("dataPrMethodNm")
	String dataPrMethodNm = defaultStringValue;

	/*** 처리시작시간 */
	@JsonProperty("logTime")
	String logTime = defaultStringValue;

	/*** 연계채널명 */
	@JsonProperty("channelId")
	String channelId = defaultStringValue;

	/*** 연계채널ID */
	@JsonProperty("channelNm")
	String channelNm = defaultStringValue;

	/*** 인터페이스ID */
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;

	/*** 통합인터페이스ID */
	@JsonProperty("integrationId")
	String integrationId = defaultStringValue;

	/*** 인터페이스명 */
	@JsonProperty("interfaceNm")
	String interfaceNm = defaultStringValue;

	/*** 업무ID */
	@JsonProperty("businessId")
	String businessId = defaultStringValue;

	/*** 업무명 */
	@JsonProperty("businessNm")
	String businessNm = defaultStringValue;

	/*** 송신시스템ID */
	@JsonProperty("sndSystemId")
	String sndSystemId = defaultStringValue;

	/*** 송신시스템명 */
	@JsonProperty("sndSystemNm")
	String sndSystemNm = defaultStringValue;

	/*** 송신시스템코드 */
	@JsonProperty("sndSystemCd")
	String sndSystemCd = defaultStringValue;

	/*** 수신시스템ID */
	@JsonProperty("rcvSystemId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String rcvSystemId = defaultStringValue;

	/*** 수신시스템명 */
	@JsonProperty("rcvSystemNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String rcvSystemNm = defaultStringValue;

	/*** 수신시스템코드 */
	@JsonProperty("rcvSystemCd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String rcvSystemCd = defaultStringValue;

	/*** 글로벌ID */
	@JsonProperty("globalId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String globalId = defaultStringValue;

	/*** 에러메시지ID */
	@JsonProperty("errorMsg")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String errorMsg = defaultStringValue;

	/*** 송신업무서비스 명 */
	@JsonProperty("sndBizServiceNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String sndBizServiceNm = defaultStringValue;


	@JsonProperty("minPrDt")
	String minPrDt = defaultStringValue;

	@JsonProperty("maxPrDt")
	String maxPrDt = defaultStringValue;

	@JsonProperty("elapsedTime")
	String elapsedTime = defaultStringValue;

	@JsonProperty("dataSize")
	String dataSize = defaultStringValue;

	@JsonProperty("recordCnt")
	String recordCnt = defaultStringValue;

	public String getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
	}

	public String getDataSize() {
		return dataSize;
	}

	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}

	public String getMinPrDt() {
		return minPrDt;
	}

	public void setMinPrDt(String minPrDt) {
		this.minPrDt = minPrDt;
	}

	public String getMaxPrDt() {
		return maxPrDt;
	}

	public void setMaxPrDt(String maxPrDt) {
		this.maxPrDt = maxPrDt;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getSndBizServiceNm() {
		return sndBizServiceNm;
	}

	public void setSndBizServiceNm(String sndBizServiceNm) {
		this.sndBizServiceNm = sndBizServiceNm;
	}

	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * @return the logKey1
	 */
	public String getLogKey1() {
		return logKey1;
	}

	/**
	 * @param logKey1 the logKey1 to set
	 */
	public void setLogKey1(String logKey1) {
		this.logKey1 = logKey1;
	}

	/**
	 * @return the logKey2
	 */
	public String getLogKey2() {
		return logKey2;
	}

	/**
	 * @param logKey2 the logKey2 to set
	 */
	public void setLogKey2(String logKey2) {
		this.logKey2 = logKey2;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusNm
	 */
	public String getStatusNm() {
		return statusNm;
	}

	/**
	 * @param statusNm the statusNm to set
	 */
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	/**
	 * @return the dataPrMethod
	 */
	public String getDataPrMethod() {
		return dataPrMethod;
	}

	/**
	 * @param dataPrMethod the dataPrMethod to set
	 */
	public void setDataPrMethod(String dataPrMethod) {
		this.dataPrMethod = dataPrMethod;
	}

	/**
	 * @return the dataPrMethodNm
	 */
	public String getDataPrMethodNm() {
		return dataPrMethodNm;
	}

	/**
	 * @param dataPrMethodNm the dataPrMethodNm to set
	 */
	public void setDataPrMethodNm(String dataPrMethodNm) {
		this.dataPrMethodNm = dataPrMethodNm;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelNm
	 */
	public String getChannelNm() {
		return channelNm;
	}

	/**
	 * @param channelNm the channelNm to set
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the integrationId
	 */
	public String getIntegrationId() {
		return integrationId;
	}

	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	/**
	 * @return the interfaceNm
	 */
	public String getInterfaceNm() {
		return interfaceNm;
	}

	/**
	 * @param interfaceNm the interfaceNm to set
	 */
	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the businessNm
	 */
	public String getBusinessNm() {
		return businessNm;
	}

	/**
	 * @param businessNm the businessNm to set
	 */
	public void setBusinessNm(String businessNm) {
		this.businessNm = businessNm;
	}

	/**
	 * @return the sndSystemId
	 */
	public String getSndSystemId() {
		return sndSystemId;
	}

	/**
	 * @param sndSystemId the sndSystemId to set
	 */
	public void setSndSystemId(String sndSystemId) {
		this.sndSystemId = sndSystemId;
	}

	/**
	 * @return the sndSystemNm
	 */
	public String getSndSystemNm() {
		return sndSystemNm;
	}

	/**
	 * @param sndSystemNm the sndSystemNm to set
	 */
	public void setSndSystemNm(String sndSystemNm) {
		this.sndSystemNm = sndSystemNm;
	}

	/**
	 * @return the rcvSystemId
	 */
	public String getRcvSystemId() {
		return rcvSystemId;
	}

	/**
	 * @param rcvSystemId the rcvSystemId to set
	 */
	public void setRcvSystemId(String rcvSystemId) {
		this.rcvSystemId = rcvSystemId;
	}

	/**
	 * @return the rcvSystemNm
	 */
	public String getRcvSystemNm() {
		return rcvSystemNm;
	}

	/**
	 * @param rcvSystemNm the rcvSystemNm to set
	 */
	public void setRcvSystemNm(String rcvSystemNm) {
		this.rcvSystemNm = rcvSystemNm;
	}

	/**
	 * @return the sndSystemCd
	 */
	public String getSndSystemCd() {
		return sndSystemCd;
	}

	/**
	 * @param sndSystemCd the sndSystemCd to set
	 */
	public void setSndSystemCd(String sndSystemCd) {
		this.sndSystemCd = sndSystemCd;
	}

	/**
	 * @return the rcvSystemCd
	 */
	public String getRcvSystemCd() {
		return rcvSystemCd;
	}

	/**
	 * @param rcvSystemCd the rcvSystemCd to set
	 */
	public void setRcvSystemCd(String rcvSystemCd) {
		this.rcvSystemCd = rcvSystemCd;
	}

	/**
	 * @return the globalId
	 */
	public String getGlobalId() {
		return globalId;
	}

	/**
	 * @param globalId the globalId to set
	 */
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
