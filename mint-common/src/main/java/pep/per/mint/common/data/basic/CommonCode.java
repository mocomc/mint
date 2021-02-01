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
 * 공통코드 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class CommonCode  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3851010558761349668L;

	@JsonProperty("level1")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String level1 = defaultStringValue;
	
	@JsonProperty("level2")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String level2 = defaultStringValue;
	
	@JsonProperty("cd")
	String cd = defaultStringValue;
	
	@JsonProperty("level1Nm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String level1Nm = defaultStringValue;
	
	@JsonProperty("level2Nm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String level2Nm = defaultStringValue;
	
	@JsonProperty("nm")
	String nm = defaultStringValue;
	
	@JsonProperty("nm2")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String nm2 = defaultStringValue;
	
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;
	
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

	/**
	 * @return the level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * @return the level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the level1Nm
	 */
	public String getLevel1Nm() {
		return level1Nm;
	}

	/**
	 * @param level1Nm the level1Nm to set
	 */
	public void setLevel1Nm(String level1Nm) {
		this.level1Nm = level1Nm;
	}

	/**
	 * @return the level2Nm
	 */
	public String getLevel2Nm() {
		return level2Nm;
	}

	/**
	 * @param level2Nm the level2Nm to set
	 */
	public void setLevel2Nm(String level2Nm) {
		this.level2Nm = level2Nm;
	}

	/**
	 * @return the nm
	 */
	public String getNm() {
		return nm;
	}

	/**
	 * @param nm the nm to set
	 */
	public void setNm(String nm) {
		this.nm = nm;
	}

	/**
	 * @return the nm2
	 */
	public String getNm2() {
		return nm2;
	}

	/**
	 * @param nm2 the nm2 to set
	 */
	public void setNm2(String nm2) {
		this.nm2 = nm2;
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
	
	
	
	
}
