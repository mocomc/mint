/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 인터페이스그룹 Data Object
 * @author Solution TF
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceGroup  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1951701448063261512L;

	/*** 인터페이스ID */
	String interfaceGroupId = defaultStringValue;
	
	/*** 인터페이스그룹 */
	String interfaceGroupNm = defaultStringValue;
	
	/*** 루트여부 */
	String rootYn = "N";
	
	/*** 인터페이스리스트 */
	List<Interface> interfaceList;
	
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
	 * @return the interfaceGroupId
	 */
	public String getInterfaceGroupId() {
		return interfaceGroupId;
	}

	/**
	 * @param interfaceGroupId the interfaceGroupId to set
	 */
	public void setInterfaceGroupId(String interfaceGroupId) {
		this.interfaceGroupId = interfaceGroupId;
	}

	/**
	 * @return the interfaceGroupNm
	 */
	public String getInterfaceGroupNm() {
		return interfaceGroupNm;
	}

	/**
	 * @param interfaceGroupNm the interfaceGroupNm to set
	 */
	public void setInterfaceGroupNm(String interfaceGroupNm) {
		this.interfaceGroupNm = interfaceGroupNm;
	}

	/**
	 * @return the rootYn
	 */
	public String getRootYn() {
		return rootYn;
	}

	/**
	 * @param rootYn the rootYn to set
	 */
	public void setRootYn(String rootYn) {
		this.rootYn = rootYn;
	}

	/**
	 * @return the interfaceList
	 */
	public List<Interface> getInterfaceList() {
		return interfaceList;
	}

	/**
	 * @param interfaceList the interfaceList to set
	 */
	public void setInterfaceList(List<Interface> interfaceList) {
		this.interfaceList = interfaceList;
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
