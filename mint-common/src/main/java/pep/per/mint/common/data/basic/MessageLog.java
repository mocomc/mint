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
 * 온라인전문이력 Data Object
 * @author Solution TA
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MessageLog  extends CacheableObject{

	private static final long serialVersionUID = -4854074087253861062L;

	/*** 전문관리번호 */
	@JsonProperty("messageLogId")
	String messageLogId = defaultStringValue;

	/*** 전문관리번호 */
	@JsonProperty("messageManageNo")
	String messageManageNo = defaultStringValue;

	/*** 기관코드 */
	@JsonProperty("insttCode")
	String insttCode = defaultStringValue;

	/*** 거래구분코드 */
	@JsonProperty("delngSeCode")
	String delngSeCode = defaultStringValue;

	/*** 관리구분 */
	@JsonProperty("manageSe")
	String manageSe = defaultStringValue;

	/*** 요청전문 */
	@JsonProperty("reqMessage")
	String reqMessage = defaultStringValue;

	/*** 요청전문처리시간 */
	@JsonProperty("reqProcessDt")
	String reqProcessDt = defaultStringValue;

	/*** 응답전문 */
	@JsonProperty("resMessage")
	String resMessage = defaultStringValue;

	/*** 응답전문처리시간 */
	@JsonProperty("resProcessDt")
	String resProcessDt = defaultStringValue;

	/*** 실제응답코드 */
	@JsonProperty("responseCode")
	String responseCode = defaultStringValue;

	/*** 메시지발생일자 */
	@JsonProperty("trnsmisDt")
	String trnsmisDt = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;



	public String getMessageLogId() {
		return messageLogId;
	}

	public void setMessageLogId(String messageLogId) {
		this.messageLogId = messageLogId;
	}

	public String getMessageManageNo() {
		return messageManageNo;
	}

	public void setMessageManageNo(String messageManageNo) {
		this.messageManageNo = messageManageNo;
	}

	public String getInsttCode() {
		return insttCode;
	}

	public void setInsttCode(String insttCode) {
		this.insttCode = insttCode;
	}

	public String getDelngSeCode() {
		return delngSeCode;
	}

	public void setDelngSeCode(String delngSeCode) {
		this.delngSeCode = delngSeCode;
	}

	public String getManageSe() {
		return manageSe;
	}

	public void setManageSe(String manageSe) {
		this.manageSe = manageSe;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public String getReqProcessDt() {
		return reqProcessDt;
	}

	public void setReqProcessDt(String reqProcessDt) {
		this.reqProcessDt = reqProcessDt;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public String getResProcessDt() {
		return resProcessDt;
	}

	public void setResProcessDt(String resProcessDt) {
		this.resProcessDt = resProcessDt;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTrnsmisDt() {
		return trnsmisDt;
	}

	public void setTrnsmisDt(String trnsmisDt) {
		this.trnsmisDt = trnsmisDt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
