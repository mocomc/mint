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
 * 담당자 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RelUser  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7124581335081166910L;

	/*** 담당자 */
	@JsonProperty("user")
	User user;

	/***담당자유형 */ 
	@JsonProperty("roleType") 
	String roleType = defaultStringValue; 
	
	/***담당자유형명 */ 
	@JsonProperty("roleTypeNm") 
	String roleTypeNm = defaultStringValue; 
	
	/***순번*/
	@JsonProperty("seq") 
	int seq; 
	
	/***담당시스템ID*/
	@JsonProperty
	String systemId = defaultStringValue;

	/***담당시스템명*/
	@JsonProperty
	String systemNm = defaultStringValue;

	/***담당시스템CD*/
	@JsonProperty
	String systemCd = defaultStringValue;

	/***삭제여부*/
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	@JsonProperty("delYn")
	String delYn = "N";
	
	/*** 등록일 */
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	@JsonProperty("regDate")
	String regDate = defaultStringValue;
	
	/*** 등록자 */
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	@JsonProperty("regId")
	String regId = defaultStringValue;
	
	/*** 수정일 */
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	@JsonProperty("modDate")
	String modDate = defaultStringValue;
	
	/*** 수정자 */
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	@JsonProperty("modId")
	String modId = defaultStringValue;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	
	
	/**
	 * @return the roleTypeNm
	 */
	public String getRoleTypeNm() {
		return roleTypeNm;
	}

	/**
	 * @param roleTypeNm the roleTypeNm to set
	 */
	public void setRoleTypeNm(String roleTypeNm) {
		this.roleTypeNm = roleTypeNm;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getSystemCd() {
		return systemCd;
	}

	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
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
	
	
	
}
