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
 * 배치 JOB Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class BatchJob  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = 6230640019182413117L;

	/*** Batch JOB ID */
	@JsonProperty("jobId")
	String jobId = defaultStringValue;

	/*** Batch JOB GroupID */
	@JsonProperty("groupId")
	String groupId = defaultStringValue;

	/*** Batch JOB  Name */
	@JsonProperty("jobNm")
	String jobNm = defaultStringValue;

	/*** 구현  Class */
	@JsonProperty("implClass")
	String implClass = defaultStringValue;

	/***  type  */
	@JsonProperty("type")
	String type = defaultStringValue;

	/***  table Nm */
	@JsonProperty("tableNm")
	String tableNm = defaultStringValue;

	/*** 설명 */
	@JsonProperty("comments")
	String comments = defaultStringValue;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getJobNm() {
		return jobNm;
	}

	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}

	public String getImplClass() {
		return implClass;
	}

	public void setImplClass(String implClass) {
		this.implClass = implClass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTableNm() {
		return tableNm;
	}

	public void setTableNm(String tableNm) {
		this.tableNm = tableNm;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}
