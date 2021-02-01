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

import pep.per.mint.common.data.CacheableObject;
/**
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MIAgent  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -1334320663312022876L;


	@JsonProperty("agentId")
	String agentId = defaultStringValue;

	@JsonProperty("agentNm")
	String agentNm = defaultStringValue;

	@JsonProperty("agentCd")
	String agentCd = defaultStringValue;

	@JsonProperty("status")
	String status = defaultStringValue;

	@JsonProperty("server")
	Server server = null;

	@JsonProperty("comments")
	String comments = defaultStringValue;

	@JsonProperty("delYn")
	String delYn = "N";

	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	String regId = defaultStringValue;

	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	String modId = defaultStringValue;

	@JsonProperty("brokerList")
	List<MIRunner> brokerList;


	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
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

	public List<MIRunner> getBrokerList() {
		return brokerList;
	}

	public void setBrokerList(List<MIRunner> brokerList) {
		this.brokerList = brokerList;
	}


}
