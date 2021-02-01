/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Service  extends CacheableObject{

	public static final String SERVICE_TYPE_REST = "0";
	public static final String SERVICE_TYPE_WS = "1";
	public static final String SERVICE_TYPE_FPS = "2";
	public static final String SERVICE_TYPE_APS = "3";

	public static final String SCHEDULE_TYPE_FIXED_DELAY = "0";
	public static final String SCHEDULE_TYPE_FIXED_RATE = "1";
	public static final String SCHEDULE_TYPE_CRON = "2";

	/**
	 *
	 */
	private static final long serialVersionUID = -267877595492598356L;

	@JsonProperty("serviceId")
	String serviceId = defaultStringValue;

	@JsonProperty("serviceCd")
	String serviceCd = defaultStringValue;

	@JsonProperty("serviceNm")
	String serviceNm = defaultStringValue;

	@JsonProperty("serviceUri")
	String serviceUri = defaultStringValue;

	@JsonProperty("serviceType")
	String serviceType = defaultStringValue;


	@JsonProperty
	String scheduleType = SCHEDULE_TYPE_FIXED_DELAY;

	@JsonProperty
	String schedule = defaultStringValue;

	@JsonProperty
	String useYn = "N";

	@JsonProperty("comments")
	String comments = defaultStringValue;

	@JsonProperty("delYn")
	String delYn = "N";

	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	String regId = defaultStringValue;

	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	String modId = defaultStringValue;

	@JsonProperty
	Map params = new HashMap();

	@JsonProperty
	String paramsString = "{}";

	/**
	 * @return the serverId
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
	 * @return the serviceCd
	 */
	public String getServiceCd() {
		return serviceCd;
	}

	/**
	 * @param serviceCd the serviceCd to set
	 */
	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}


	/**
	 * @return the serviceNm
	 */
	public String getServiceNm() {
		return serviceNm;
	}

	/**
	 * @param serviceNm the serviceNm to set
	 */
	public void setServiceNm(String serviceNm) {
		this.serviceNm = serviceNm;
	}

	/**
	 * @return the serviceUri
	 */
	public String getServiceUri() {
		return serviceUri;
	}

	/**
	 * @param serviceUri the serviceUri to set
	 */
	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}



	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String userYn) {
		this.useYn = userYn;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getParamsString() {
		return paramsString;
	}

	public void setParamsString(String paramsString) {
		this.paramsString = paramsString;
	}



}
