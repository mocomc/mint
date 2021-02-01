/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 인터페이스요건 Data Object
 *
 * @author Solution TF
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Requirement  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 2122421623849894931L;

	@JsonProperty
	PageInfo pageInfo;

	@JsonProperty("requirementId")
	String requirementId = defaultStringValue;

	@JsonProperty("requirementNm")
	String requirementNm = defaultStringValue;

	@JsonProperty("status")
	String status = defaultStringValue;

	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;

	@JsonProperty("business")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Business business;

	@JsonProperty("interface")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Interface interfaceInfo;

	@JsonProperty("devExpYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String devExpYmd = defaultStringValue;

	@JsonProperty("devFinYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String devFinYmd = defaultStringValue;

	@JsonProperty("testExpYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String testExpYmd = defaultStringValue;

	@JsonProperty("testFinYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String testFinYmd = defaultStringValue;

	@JsonProperty("realExpYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String realExpYmd = defaultStringValue;

	@JsonProperty("realFinYmd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String realFinYmd = defaultStringValue;

	@JsonProperty("commentList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RequirementComment> commentList = new ArrayList<RequirementComment>();

	@JsonProperty("attatchFileList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RequirementAttatchFile> attatchFileList  = new ArrayList<RequirementAttatchFile>();


	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;

	@JsonProperty("regUser")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	User regUser;

	@JsonProperty("delYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String delYn = "N";

	@JsonProperty("regDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regId = defaultStringValue;

	@JsonProperty("modDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modId = defaultStringValue;

	@JsonProperty
	User tester;

	@JsonProperty
	User developer;

	@JsonProperty
	User mover;

	/**
	 * @return the requirementId
	 */
	public String getRequirementId() {
		return requirementId;
	}

	/**
	 * @param requirementId the requirementId to set
	 */
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	/**
	 * @return the requirementNm
	 */
	public String getRequirementNm() {
		return requirementNm;
	}

	/**
	 * @param requirementNm the requirementNm to set
	 */
	public void setRequirementNm(String requirementNm) {
		this.requirementNm = requirementNm;
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
	 * @return the business
	 */
	public Business getBusiness() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(Business business) {
		this.business = business;
	}

	/**
	 * @return the interfaceInfo
	 */
	public Interface getInterfaceInfo() {
		return interfaceInfo;
	}

	/**
	 * @param interfaceInfo the interfaceInfo to set
	 */
	public void setInterfaceInfo(Interface interfaceInfo) {
		this.interfaceInfo = interfaceInfo;
	}





	/**
	 * @return the devExpYmd
	 */
	public String getDevExpYmd() {
		return devExpYmd;
	}

	/**
	 * @param devExpYmd the devExpYmd to set
	 */
	public void setDevExpYmd(String devExpYmd) {
		this.devExpYmd = devExpYmd;
	}

	/**
	 * @return the devFinYmd
	 */
	public String getDevFinYmd() {
		return devFinYmd;
	}

	/**
	 * @param devFinYmd the devFinYmd to set
	 */
	public void setDevFinYmd(String devFinYmd) {
		this.devFinYmd = devFinYmd;
	}

	/**
	 * @return the testExpYmd
	 */
	public String getTestExpYmd() {
		return testExpYmd;
	}

	/**
	 * @param testExpYmd the testExpYmd to set
	 */
	public void setTestExpYmd(String testExpYmd) {
		this.testExpYmd = testExpYmd;
	}

	/**
	 * @return the testFinYmd
	 */
	public String getTestFinYmd() {
		return testFinYmd;
	}

	/**
	 * @param testFinYmd the testFinYmd to set
	 */
	public void setTestFinYmd(String testFinYmd) {
		this.testFinYmd = testFinYmd;
	}

	/**
	 * @return the realExpYmd
	 */
	public String getRealExpYmd() {
		return realExpYmd;
	}

	/**
	 * @param realExpYmd the realExpYmd to set
	 */
	public void setRealExpYmd(String realExpYmd) {
		this.realExpYmd = realExpYmd;
	}

	/**
	 * @return the realFinYmd
	 */
	public String getRealFinYmd() {
		return realFinYmd;
	}

	/**
	 * @param realFinYmd the realFinYmd to set
	 */
	public void setRealFinYmd(String realFinYmd) {
		this.realFinYmd = realFinYmd;
	}

	/**
	 * @return the commentList
	 */
	public List<RequirementComment> getCommentList() {
		return commentList;
	}

	/**
	 * @param commentList the commentList to set
	 */
	public void setCommentList(List<RequirementComment> commentList) {
		this.commentList = commentList;
	}

	/**
	 * @return the attatchFileList
	 */
	public List<RequirementAttatchFile> getAttatchFileList() {
		return attatchFileList;
	}

	/**
	 * @param attatchFileList the attatchFileList to set
	 */
	public void setAttatchFileList(List<RequirementAttatchFile> attatchFileList) {
		this.attatchFileList = attatchFileList;
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
	 * @return the regUser
	 */
	public User getRegUser() {
		return regUser;
	}

	/**
	 * @param regUser the regUser to set
	 */
	public void setRegUser(User regUser) {
		this.regUser = regUser;
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


	public User getTester() {
		return tester;
	}

	public void setTester(User tester) {
		this.tester = tester;
	}

	public User getDeveloper() {
		return developer;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	public User getMover() {
		return mover;
	}

	public void setMover(User mover) {
		this.mover = mover;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}


}
