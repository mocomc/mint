/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.data.basic.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FrontLog extends CacheableObject{
	
	@JsonProperty
	String logKey;
	
	@JsonProperty
	String userId;
	
	@JsonProperty
	String appId;
	
	@JsonProperty
	String serviceId;
	
	@JsonProperty
	String reqDate;
	
	@JsonProperty
	String resDate;
	
	@JsonProperty
	String errCd;
	
	@JsonProperty
	String errMsg;
	
	@JsonProperty
	String reqLog;
	
	@JsonProperty
	String resLog;
	
	@JsonProperty
	String httpRemoteAddr; 
	
	@JsonProperty
	String httpRemotePort;
	
	@JsonProperty
	String httpLocalAddr;
	
	@JsonProperty
	String httpLocalPort;
	
	@JsonProperty
	String httpServerName;
	
	@JsonProperty
	String httpServerPort;
	
	@JsonProperty
	String httpServletPath;
	
	@JsonProperty
	String httpRequestURI;
	
	@JsonProperty
	String httpMethod;
	
	@JsonProperty
	String httpContentType;
	
	@JsonProperty
	String httpCharacterEncoding;
	
	@JsonProperty
	String httpContextPath;
	
	@JsonProperty
	String httpQueryString;

	/**
	 * @return the logKey
	 */
	public String getLogKey() {
		return logKey;
	}

	/**
	 * @param logKey the logKey to set
	 */
	public void setLogKey(String logKey) {
		this.logKey = logKey;
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
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the reqDate
	 */
	public String getReqDate() {
		return reqDate;
	}

	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	/**
	 * @return the resDate
	 */
	public String getResDate() {
		return resDate;
	}

	/**
	 * @param resDate the resDate to set
	 */
	public void setResDate(String resDate) {
		this.resDate = resDate;
	}

	/**
	 * @return the errCd
	 */
	public String getErrCd() {
		return errCd;
	}

	/**
	 * @param errCd the errCd to set
	 */
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the reqLog
	 */
	public String getReqLog() {
		return reqLog;
	}

	/**
	 * @param reqLog the reqLog to set
	 */
	public void setReqLog(String reqLog) {
		this.reqLog = reqLog;
	}

	/**
	 * @return the resLog
	 */
	public String getResLog() {
		return resLog;
	}

	/**
	 * @param resLog the resLog to set
	 */
	public void setResLog(String resLog) {
		this.resLog = resLog;
	}

	/**
	 * @return the httpRemoteAddr
	 */
	public String getHttpRemoteAddr() {
		return httpRemoteAddr;
	}

	/**
	 * @param httpRemoteAddr the httpRemoteAddr to set
	 */
	public void setHttpRemoteAddr(String httpRemoteAddr) {
		this.httpRemoteAddr = httpRemoteAddr;
	}

	/**
	 * @return the httpRemotePort
	 */
	public String getHttpRemotePort() {
		return httpRemotePort;
	}

	/**
	 * @param httpRemotePort the httpRemotePort to set
	 */
	public void setHttpRemotePort(String httpRemotePort) {
		this.httpRemotePort = httpRemotePort;
	}

	/**
	 * @return the httpLocalAddr
	 */
	public String getHttpLocalAddr() {
		return httpLocalAddr;
	}

	/**
	 * @param httpLocalAddr the httpLocalAddr to set
	 */
	public void setHttpLocalAddr(String httpLocalAddr) {
		this.httpLocalAddr = httpLocalAddr;
	}

	/**
	 * @return the httpLocalPort
	 */
	public String getHttpLocalPort() {
		return httpLocalPort;
	}

	/**
	 * @param httpLocalPort the httpLocalPort to set
	 */
	public void setHttpLocalPort(String httpLocalPort) {
		this.httpLocalPort = httpLocalPort;
	}

	/**
	 * @return the httpServerName
	 */
	public String getHttpServerName() {
		return httpServerName;
	}

	/**
	 * @param httpServerName the httpServerName to set
	 */
	public void setHttpServerName(String httpServerName) {
		this.httpServerName = httpServerName;
	}

	/**
	 * @return the httpServerPort
	 */
	public String getHttpServerPort() {
		return httpServerPort;
	}

	/**
	 * @param httpServerPort the httpServerPort to set
	 */
	public void setHttpServerPort(String httpServerPort) {
		this.httpServerPort = httpServerPort;
	}

	/**
	 * @return the httpServletPath
	 */
	public String getHttpServletPath() {
		return httpServletPath;
	}

	/**
	 * @param httpServletPath the httpServletPath to set
	 */
	public void setHttpServletPath(String httpServletPath) {
		this.httpServletPath = httpServletPath;
	}

	/**
	 * @return the httpRequestURI
	 */
	public String getHttpRequestURI() {
		return httpRequestURI;
	}

	/**
	 * @param httpRequestURI the httpRequestURI to set
	 */
	public void setHttpRequestURI(String httpRequestURI) {
		this.httpRequestURI = httpRequestURI;
	}

	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the httpContentType
	 */
	public String getHttpContentType() {
		return httpContentType;
	}

	/**
	 * @param httpContentType the httpContentType to set
	 */
	public void setHttpContentType(String httpContentType) {
		this.httpContentType = httpContentType;
	}

	/**
	 * @return the httpCharacterEncoding
	 */
	public String getHttpCharacterEncoding() {
		return httpCharacterEncoding;
	}

	/**
	 * @param httpCharacterEncoding the httpCharacterEncoding to set
	 */
	public void setHttpCharacterEncoding(String httpCharacterEncoding) {
		this.httpCharacterEncoding = httpCharacterEncoding;
	}

	/**
	 * @return the httpContextPath
	 */
	public String getHttpContextPath() {
		return httpContextPath;
	}

	/**
	 * @param httpContextPath the httpContextPath to set
	 */
	public void setHttpContextPath(String httpContextPath) {
		this.httpContextPath = httpContextPath;
	}

	/**
	 * @return the httpQueryString
	 */
	public String getHttpQueryString() {
		return httpQueryString;
	}

	/**
	 * @param httpQueryString the httpQueryString to set
	 */
	public void setHttpQueryString(String httpQueryString) {
		this.httpQueryString = httpQueryString;
	}

	
	
}
