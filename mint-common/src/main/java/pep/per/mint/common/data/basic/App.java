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

//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 프론트 어플리케이션 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType",visible=true)
public class App extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3097109012386490905L;


	@JsonProperty("appId")
	String appId = defaultStringValue;

	@JsonProperty("seq")
	int seq;

	@JsonProperty("appNm")
	String appNm = defaultStringValue;

	@JsonProperty("appCd")
	String appCd = defaultStringValue;

    @JsonProperty
    String appOpt = defaultStringValue;

	@JsonProperty("appUri")
	String appUri = defaultStringValue;

	@JsonProperty("cmdString")
	String cmdString = defaultStringValue;

	@JsonProperty
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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
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

	/**
	 * @return the appNm
	 */
	public String getAppNm() {
		return appNm;
	}

	/**
	 * @param appNm the appNm to set
	 */
	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}

	/**
	 * @return the appCd
	 */
	public String getAppCd() {
		return appCd;
	}

	/**
	 * @param appCd the appCd to set
	 */
	public void setAppCd(String appCd) {
		this.appCd = appCd;
	}

	/**
	 * @return the appUri
	 */
	public String getAppUri() {
		return appUri;
	}

	/**
	 * @param appUri the appUri to set
	 */
	public void setAppUri(String appUri) {
		this.appUri = appUri;
	}

	/**
	 * @return the cmdString
	 */
	public String getCmdString() {
		return cmdString;
	}

	/**
	 * @param cmdString the cmdString to set
	 */
	public void setCmdString(String cmdString) {
		this.cmdString = cmdString;
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


	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    public String getAppOpt() {
        return appOpt;
    }

    public void setAppOpt(String appOpt) {
        this.appOpt = appOpt;
    }


}
