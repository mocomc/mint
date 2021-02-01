/*
ee * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
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
 * 인터페이스패턴 Data Object 
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfacePattern  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6349816315122479642L;
 

	/*** 패턴ID */
	@JsonProperty("patternId")
	String patternId = defaultStringValue;
	
	/*** 패턴명 */
	@JsonProperty("patternNm")
	String patternNm = defaultStringValue;
	
	/*** 패턴코드 */
	@JsonProperty("patternCd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String patternCd = defaultStringValue;
	
	
	
	/*** 패턴이미지 */
	@JsonProperty("patternImg")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String patternImg = defaultStringValue;
	
	/*** 데이터처리방향 */
	@JsonProperty("dataPrDir")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataPrDir = defaultStringValue;
	
	/*** 데이터처리방향명 */
	@JsonProperty("dataPrDirNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataPrDirNm = defaultStringValue;
	
	/*** APP처리방식 */
	@JsonProperty("appPrMethod")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String appPrMethod = defaultStringValue;
	
	/*** APP처리방식명 */
	@JsonProperty("appPrMethodNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String appPrMethodNm = defaultStringValue;
	
	
	/*** 데이터처리방식 */
	@JsonProperty("dataPrMethod")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataPrMethod = defaultStringValue;
	
	
	/*** 데이터처리방식명 */
	@JsonProperty("dataPrMethodNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataPrMethodNm = defaultStringValue;
	
	/*** 송수신관계*/
	@JsonProperty("sndRcvRel")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String sndRcvRel = "1:1";
	
	/*** 시작리소스유형*/
	@JsonProperty("startResType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String startResType = defaultStringValue;
	
	/*** 시작리소스유형명*/
	@JsonProperty("startResTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String startResTypeNm = defaultStringValue;
	
	/*** 종료리소스유형*/
	@JsonProperty("endResType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String endResType = defaultStringValue;
	
	/*** 종료리소스유형명*/
	@JsonProperty("endResTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String endResTypeNm = defaultStringValue;
	
	
	/*** 설명 */
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;
	
	/*** 개발가이드 */
	@JsonProperty("guide")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	InterfaceGuide guide;
	
	/*** 삭제여부 */
	@JsonProperty("delYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String delYn = "N";
	
	/*** 등록일 */
	@JsonProperty("regDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regDate = defaultStringValue;
	
	/*** 등록자 */
	@JsonProperty("regId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regId = defaultStringValue;
	
	/*** 수정일 */
	@JsonProperty("modDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modDate = defaultStringValue;
	
	/*** 수정자 */
	@JsonProperty("modId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modId = defaultStringValue;

	/**
	 * @return the patternId
	 */
	public String getPatternId() {
		return patternId;
	}

	/**
	 * @param patternId the patternId to set
	 */
	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	/**
	 * @return the patternNm
	 */
	public String getPatternNm() {
		return patternNm;
	}

	/**
	 * @param patternNm the patternNm to set
	 */
	public void setPatternNm(String patternNm) {
		this.patternNm = patternNm;
	}

	
	
	/**
	 * @return the patternCd
	 */
	public String getPatternCd() {
		return patternCd;
	}

	/**
	 * @param patternCd the patternCd to set
	 */
	public void setPatternCd(String patternCd) {
		this.patternCd = patternCd;
	}

	/**
	 * @return the patternImg
	 */
	public String getPatternImg() {
		return patternImg;
	}

	/**
	 * @param patternImg the patternImg to set
	 */
	public void setPatternImg(String patternImg) {
		this.patternImg = patternImg;
	}

	/**
	 * @return the dataPrDir
	 */
	public String getDataPrDir() {
		return dataPrDir;
	}

	/**
	 * @param dataPrDir the dataPrDir to set
	 */
	public void setDataPrDir(String dataPrDir) {
		this.dataPrDir = dataPrDir;
	}

	/**
	 * @return the dataPrDirNm
	 */
	public String getDataPrDirNm() {
		return dataPrDirNm;
	}

	/**
	 * @param dataPrDirNm the dataPrDirNm to set
	 */
	public void setDataPrDirNm(String dataPrDirNm) {
		this.dataPrDirNm = dataPrDirNm;
	}

	/**
	 * @return the appPrMethod
	 */
	public String getAppPrMethod() {
		return appPrMethod;
	}

	/**
	 * @param appPrMethod the appPrMethod to set
	 */
	public void setAppPrMethod(String appPrMethod) {
		this.appPrMethod = appPrMethod;
	}

	/**
	 * @return the appPrMethodNm
	 */
	public String getAppPrMethodNm() {
		return appPrMethodNm;
	}

	/**
	 * @param appPrMethodNm the appPrMethodNm to set
	 */
	public void setAppPrMethodNm(String appPrMethodNm) {
		this.appPrMethodNm = appPrMethodNm;
	}

	/**
	 * @return the dataPrMethod
	 */
	public String getDataPrMethod() {
		return dataPrMethod;
	}

	/**
	 * @param dataPrMethod the dataPrMethod to set
	 */
	public void setDataPrMethod(String dataPrMethod) {
		this.dataPrMethod = dataPrMethod;
	}

	/**
	 * @return the dataPrMethodNm
	 */
	public String getDataPrMethodNm() {
		return dataPrMethodNm;
	}

	/**
	 * @param dataPrMethodNm the dataPrMethodNm to set
	 */
	public void setDataPrMethodNm(String dataPrMethodNm) {
		this.dataPrMethodNm = dataPrMethodNm;
	}
	
	/**
	 * @return the sndRcvRel
	 */
	public String getSndRcvRel() {
		return sndRcvRel;
	}

	/**
	 * @param sndRcvRel the sndRcvRel to set
	 */
	public void setSndRcvRel(String sndRcvRel) {
		this.sndRcvRel = sndRcvRel;
	}

	
	
	
	/**
	 * @return the startResType
	 */
	public String getStartResType() {
		return startResType;
	}

	/**
	 * @param startResType the startResType to set
	 */
	public void setStartResType(String startResType) {
		this.startResType = startResType;
	}

	/**
	 * @return the startResTypeNm
	 */
	public String getStartResTypeNm() {
		return startResTypeNm;
	}

	/**
	 * @param startResTypeNm the startResTypeNm to set
	 */
	public void setStartResTypeNm(String startResTypeNm) {
		this.startResTypeNm = startResTypeNm;
	}

	/**
	 * @return the endResType
	 */
	public String getEndResType() {
		return endResType;
	}

	/**
	 * @param endResType the endResType to set
	 */
	public void setEndResType(String endResType) {
		this.endResType = endResType;
	}

	/**
	 * @return the endResTypeNm
	 */
	public String getEndResTypeNm() {
		return endResTypeNm;
	}

	/**
	 * @param endResTypeNm the endResTypeNm to set
	 */
	public void setEndResTypeNm(String endResTypeNm) {
		this.endResTypeNm = endResTypeNm;
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
	 * @return the guide
	 */
	public InterfaceGuide getGuide() {
		return guide;
	}

	/**
	 * @param guide the guide to set
	 */
	public void setGuide(InterfaceGuide guide) {
		this.guide = guide;
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
