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
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRLinkInfo  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = 1723717915739071968L;

	@JsonProperty("fromNodeId")
	String fromNodeId = defaultStringValue;

	@JsonProperty("toNodeId")
	String toNodeId = defaultStringValue;


	@JsonProperty("fromNodeIdx")
	int fromNodeIdx = 0;

	@JsonProperty("toNodeIdx")
	int toNodeIdx = 0;

	public String getFromNodeId() {
		return fromNodeId;
	}

	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	public String getToNodeId() {
		return toNodeId;
	}

	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}

	public int getFromNodeIdx() {
		return fromNodeIdx;
	}

	public void setFromNodeIdx(int fromNodeIdx) {
		this.fromNodeIdx = fromNodeIdx;
	}

	public int getToNodeIdx() {
		return toNodeIdx;
	}

	public void setToNodeIdx(int toNodeIdx) {
		this.toNodeIdx = toNodeIdx;
	}


}
