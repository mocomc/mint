/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, TA), Inc.  All Rights Reserved.
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
 * 사이트별 특성화 컬럼 ( 인터페이스 추가 속성 정의 )
 * @author TA
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceAdditionalAttribute  extends CacheableObject{
	/**
	 *
	 */
	private static final long serialVersionUID = -363865748275486031L;

	/*** 속성 ID */
	@JsonProperty("attrId")
	String attrId = defaultStringValue;

	/*** 속성 Cd */
	@JsonProperty("attrCd")
	String attrCd = defaultStringValue;

	/*** 속성 명 */
	@JsonProperty("attrNm")
	String attrNm = defaultStringValue;

	/*** 설명 */
	@JsonProperty("comments")
	String comments = defaultStringValue;

	/*** 삭제여부 */
	@JsonProperty("delYn")
	String delYn = "N";

	/*** 등록일 */
	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty("regId")
	String regId = defaultStringValue;

	/*** 수정일 */
	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	/*** 수정자 */
	@JsonProperty("modId")
	String modId = defaultStringValue;

	/**
	 * @return the attrId
	 */
	public String getAttrId() {
		return attrId;
	}

	/**
	 * @param attrId the attrId to set
	 */
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}


	/**
	 * @return the attrCd
	 */
	public String getAttrCd() {
		return attrCd;
	}

	/**
	 * @param attrCd the attrCd to set
	 */
	public void setAttrCd(String attrCd) {
		this.attrCd = attrCd;
	}

	/**
	 * @return the attrNm
	 */
	public String getAttrNm() {
		return attrNm;
	}

	/**
	 * @param attrNm the attrNm to set
	 */
	public void setAttrNm(String attrNm) {
		this.attrNm = attrNm;
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
