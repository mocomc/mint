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
 * 배치 Schedule Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class BatchSchedule  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = 6230640019182413117L;

	/*** Batch Schedule ID */
	@JsonProperty("scheduleId")
	String scheduleId = defaultStringValue;

	/*** Batch Schedule  Name */
	@JsonProperty("scheduleNm")
	String scheduleNm = defaultStringValue;

	/*** 사용여부 */
	@JsonProperty("usage")
	String usage = defaultStringValue;

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleNm() {
		return scheduleNm;
	}

	public void setScheduleNm(String scheduleNm) {
		this.scheduleNm = scheduleNm;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
