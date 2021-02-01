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
 * 배치 Schedule  Job  Mapping Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class BatchMapping  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = 6230640019182413117L;
	/*** Batch JOB ID */
	@JsonProperty("jobId")
	String jobId = defaultStringValue;

	/*** Batch Schedule ID */
	@JsonProperty("batchJob")
	BatchJob batchJob  ;

	/*** Batch Schedule  Name */
	@JsonProperty("batchSchedule")
	BatchSchedule batchSchedule ;

	/*** GroupID */
	@JsonProperty("grpId")
	String grpId = defaultStringValue;

	/*** 사용여부 */
	@JsonProperty("seq")
	int seq = 0;

	/*** type */
	@JsonProperty("type")
	String type = defaultStringValue;

	/*** value */
	@JsonProperty("value")
	String value = defaultStringValue;


	/*** 스케줄  -  사용여부 */
	@JsonProperty("usage")
	String usage = defaultStringValue;

	/*** Batch JOB  Name */
	@JsonProperty("jobNm")
	String jobNm = defaultStringValue;

	/*** Batch JOB  구현  Class */
	@JsonProperty("implClass")
	String implClass = defaultStringValue;

	/*** Batch JOB  table Nm */
	@JsonProperty("tableNm")
	String tableNm = defaultStringValue;

	/***ㅍBatch JOB  설명 */
	@JsonProperty("comments")
	String comments = defaultStringValue;



	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public BatchJob getBatchJob() {
		return batchJob;
	}

	public void setBatchJob(BatchJob batchJob) {
		this.batchJob = batchJob;
	}

	public BatchSchedule getBatchSchedule() {
		return batchSchedule;
	}

	public void setBatchSchedule(BatchSchedule batchSchedule) {
		this.batchSchedule = batchSchedule;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
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
