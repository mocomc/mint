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
 * @author yusikKim
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ProblemTemplate extends CacheableObject {
	
	@JsonProperty("channelId")
	String channelId;
	
	@JsonProperty("templateId")
	int templateId;
	
	@JsonProperty("problemSubject")
	String problemSubject;
	
	@JsonProperty("problemContents")
	String problemContents;
	
	@JsonProperty("causeContents")
	String causeContents;
	
	@JsonProperty("resultContents")
	String resultContents;
	
	@JsonProperty("planContents")
	String planContents;
	
	@JsonProperty("delYn")
	String delYn;
	
	@JsonProperty("regDate")
	String regDate;
	
	@JsonProperty("crud")
	String crud;
	
	public String getCrud() {
		return crud;
	}

	public void setCrud(String crud) {
		this.crud = crud;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getProblemSubject() {
		return problemSubject;
	}

	public void setProblemSubject(String problemSubject) {
		this.problemSubject = problemSubject;
	}

	public String getProblemContents() {
		return problemContents;
	}

	public void setProblemContents(String problemContents) {
		this.problemContents = problemContents;
	}

	public String getCauseContents() {
		return causeContents;
	}

	public void setCauseContents(String causeContents) {
		this.causeContents = causeContents;
	}

	public String getResultContents() {
		return resultContents;
	}

	public void setResultContents(String resultContents) {
		this.resultContents = resultContents;
	}

	public String getPlanContents() {
		return planContents;
	}

	public void setPlanContents(String planContents) {
		this.planContents = planContents;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
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

	@JsonProperty("regUser")
	String regUser;
	
	@JsonProperty("modDate")
	String modDate;
	
	@JsonProperty("modUser")
	String modUser;
}
