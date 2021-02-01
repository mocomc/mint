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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Interface;

/**
 * @author yusikKim
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ProblemManagement extends CacheableObject {
	
	@JsonProperty("problemTypeNm")
	String problemTypeNm;
	
	@JsonProperty("systemNm")
	String systemNm;
	
	@JsonProperty("interfaceNm")
	String interfaceNm;
	
	@JsonProperty("startDate")
	String startDate;
	
	@JsonProperty("endDate")
	String endDate;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("statusNm")
	String statusNm;
	
	@JsonProperty("problemId")
	String problemId;
	
	@JsonProperty("problemSubject")
	String problemSubject;
	
	@JsonProperty("problemContents")
	String problemContents;
	
	@JsonProperty("errorMsg")
	String errorMsg;
	
	@JsonProperty("planContents")
	String planContents;
	
	@JsonProperty("causeContents")
	String causeContents;
	
	@JsonProperty("importance")
	String importance;
	
	@JsonProperty("importanceNm")
	String importanceNm;
	
	@JsonProperty("level1Nm")
	String level1Nm;
	
	@JsonProperty("level2Nm")
	String level2Nm;
	
	@JsonProperty("level1")
	String level1;
	
	@JsonProperty("level2")
	String level2;
	
	@JsonProperty("classCd")
	String classCd;
	
	@JsonProperty("interfaceId")
	String interfaceId;
	
	@JsonProperty("nodeName")
	String nodeName;
	
	@JsonProperty("resultContents")
	String resultContents;
	
	@JsonProperty("delYn")
	String delYn;
	
	@JsonProperty("regDate")
	String regDate;
	
	@JsonProperty("regUser")
	String regUser;
	
	@JsonProperty("modDate")
	String modDate;
	
	@JsonProperty("modUser")
	String modUser;
	
	@JsonProperty("Interface")
	Interface Interface;
	
	@JsonProperty("problemClass")
	ProblemClass problemClass;
	
	@JsonProperty("type")
	String type;
	
	@JsonProperty("typeNm")
	String typeNm;
	
	@JsonProperty("classCdNm")
	String classCdNm;
	
	@JsonProperty("problemAttachFile")
	List<ProblemAttachFile> problemAttachFile;
	
	@JsonProperty("comment")
	String comment;
	
	@JsonProperty("channelId")
	String channelId;
	
	@JsonProperty("isRegUser")
	String isRegUser;
	
	@JsonProperty("approvalStatus")
	String approvalStatus;
	
	@JsonProperty("approvalStatusNm")
	String approvalStatusNm;
	

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalStatusNm() {
		return approvalStatusNm;
	}

	public void setApprovalStatusNm(String approvalStatusNm) {
		this.approvalStatusNm = approvalStatusNm;
	}

	public String getIsRegUser() {
		return isRegUser;
	}

	public void setIsRegUser(String isRegUser) {
		this.isRegUser = isRegUser;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ProblemAttachFile> getProblemAttachFile() {
		return problemAttachFile;
	}

	public void setProblemAttachFile(List<ProblemAttachFile> problemAttachFile) {
		this.problemAttachFile = problemAttachFile;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getImportanceNm() {
		return importanceNm;
	}

	public void setImportanceNm(String importanceNm) {
		this.importanceNm = importanceNm;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassCdNm() {
		return classCdNm;
	}

	public void setClassCdNm(String classCdNm) {
		this.classCdNm = classCdNm;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getResultContents() {
		return resultContents;
	}

	public void setResultContents(String resultContents) {
		this.resultContents = resultContents;
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

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public Interface getInterface() {
		return Interface;
	}

	public void setInterface(Interface interface1) {
		Interface = interface1;
	}

	public ProblemClass getProblemClass() {
		return problemClass;
	}

	public void setProblemClass(ProblemClass problemClass) {
		this.problemClass = problemClass;
	}

	public String getProblemTypeNm() {
		return problemTypeNm;
	}

	public void setProblemTypeNm(String problemTypeNm) {
		this.problemTypeNm = problemTypeNm;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPlanContents() {
		return planContents;
	}

	public void setPlanContents(String planContents) {
		this.planContents = planContents;
	}

	public String getCauseContents() {
		return causeContents;
	}

	public void setCauseContents(String causeContents) {
		this.causeContents = causeContents;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getLevel1Nm() {
		return level1Nm;
	}

	public void setLevel1Nm(String level1Nm) {
		this.level1Nm = level1Nm;
	}

	public String getLevel2Nm() {
		return level2Nm;
	}

	public void setLevel2Nm(String level2Nm) {
		this.level2Nm = level2Nm;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getClassCd() {
		return classCd;
	}

	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
}
