/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.agent.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComMessage <X, Y>  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -6406334624709383770L;

	public final static String ERR_NO_LIST = "1";

	@JsonProperty("requestObject")
	X requestObject;

	@JsonProperty("responseObject")
	Y responseObject;

	@JsonProperty("startTime")
	String startTime = defaultStringValue;

	@JsonProperty("endTime")
	String endTime = defaultStringValue;

	@JsonProperty("errorCd")
	String errorCd = defaultStringValue;

	@JsonProperty("errorMsg")
	String errorMsg = defaultStringValue;

	@JsonProperty("errorDetail")
	String errorDetail = defaultStringValue;

	@JsonProperty("userId")
	String userId = defaultStringValue;

	@JsonProperty("appId")
	String appId = defaultStringValue;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("extension")
	Extension extension = null;

	@JsonProperty("checkSession")
	boolean checkSession = true;

	public boolean getCheckSession() {
		return checkSession;
	}

	public void setCheckSession(boolean checkSession) {
		this.checkSession = checkSession;
	}


	/**
	 * @return the requestObject
	 */
	public X getRequestObject() {
		return requestObject;
	}
	/**
	 * @param requestObject the requestObject to set
	 */
	public void setRequestObject(X requestObject) {
		this.requestObject = requestObject;
	}
	/**
	 * @return the responseObject
	 */
	public Y getResponseObject() {
		return responseObject;
	}
	/**
	 * @param responseObject the responseObject to set
	 */
	public void setResponseObject(Y responseObject) {
		this.responseObject = responseObject;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the errorCd
	 */
	public String getErrorCd() {
		return errorCd;
	}
	/**
	 * @param errorCd the errorCd to set
	 */
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
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
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the errorDetail
	 */
	public String getErrorDetail() {
		return errorDetail;
	}
	/**
	 * @param errorDetail the errorDetail to set
	 */
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}





}
