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

import pep.per.mint.common.data.CacheableObject;

/**
 * 연계채널 특성 값
 * @author TA
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceProperties  extends CacheableObject{

	public static final String TYPE_CHANNEL_ATTR 	= "0";
	public static final String TYPE_INTERFACE_ATTR 	= "1";

	/**
	 *
	 */
	private static final long serialVersionUID = -363865748275486031L;

	/*** 연계채널ID */
	@JsonProperty("channelId")
	String channelId = defaultStringValue;

	/*** interfaceID */
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;

	/*** IDX */
	@JsonProperty("idx")
	int idx = 0;

	/*** 속성 ID */
	@JsonProperty("attrId")
	String attrId = defaultStringValue;

	/*** 속성 Cd */
	@JsonProperty("attrCd")
	String attrCd = defaultStringValue;

	/*** 속성 명 */
	@JsonProperty("attrNm")
	String attrNm = defaultStringValue;

	/*** 속성 값 */
	@JsonProperty("attrValue")
	String attrValue = defaultStringValue;

	/**
	 * Type
	 * "0" : Channel Attribute
	 * "1" " Interface Additional Attribute
	 */
	@JsonProperty("type")
	String type = "0";


	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrCd() {
		return attrCd;
	}

	public void setAttrCd(String attrCd) {
		this.attrCd = attrCd;
	}

	public String getAttrNm() {
		return attrNm;
	}

	public void setAttrNm(String attrNm) {
		this.attrNm = attrNm;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
