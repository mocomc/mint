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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 연계채널 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Channel  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7419176547442090344L;

	/*** 연계채널ID */
	@JsonProperty("channelId")
	String channelId = defaultStringValue;
	
	/*** 연계채널명 */
	@JsonProperty("channelNm")
	String channelNm = defaultStringValue;

	@JsonProperty("channelCd")
	String channelCd = defaultStringValue;

	/*** 대내대외구분 */
	@JsonProperty("externalYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)	
	String externalYn = "N";
	
	/*** 인터페이스 맵핑룰 0:맵핑없음, 1: ,2:*/
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)	
	@JsonProperty("mapRule")
	String mapRule = "0";
	
	/*** 설명 */
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;
	
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


	/*** 담당자리스트 */
	@JsonProperty("relUsers")
	List<RelUser> relUsers  = new ArrayList<RelUser>();
	
	/**
	 * 채널별 속성.
	 * List<ChannelAttribute> 대신 
	 * 코드값만 설정될 필요가 있어서 List<Map> 으로 정의함.
	 */
	@JsonProperty("relChlAttributes")
	List<Map> relChlAttributes = new ArrayList<Map>();


	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelNm
	 */
	public String getChannelNm() {
		return channelNm;
	}

	/**
	 * @param channelNm the channelNm to set
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	/**
	 * @return the externalYn
	 */
	public String getExternalYn() {
		return externalYn;
	}

	/**
	 * @param externalYn the externalYn to set
	 */
	public void setExternalYn(String externalYn) {
		this.externalYn = externalYn;
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


	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getMapRule() {
		return mapRule;
	}

	public void setMapRule(String mapRule) {
		this.mapRule = mapRule;
	}

	public List<RelUser> getRelUsers() {
		return relUsers;
	}

	public void setRelUsers(List<RelUser> relUsers) {
		this.relUsers = relUsers;
	}

	public List<Map> getRelChlAttributes() {
		return relChlAttributes;
	}

	public void setRelChlAttributes(List<Map> relChlAttributes) {
		this.relChlAttributes = relChlAttributes;
	}

}
