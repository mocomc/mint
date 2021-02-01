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

import java.util.ArrayList;
import java.util.List;

/**
 * 연계채널 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ConfigInfo  extends CacheableObject{

	public static final String RS_INIT = "0";
	public static final String RS_EQUAL = "1";
	public static final String RS_DIFFERENT = "9";

	/**
	 *
	 */
	private static final long serialVersionUID = -7483422006340685688L;


	@JsonProperty
	String configId = defaultStringValue;

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String serverId = defaultStringValue;

	@JsonProperty
	String serverNm = defaultStringValue;

	@JsonProperty
	String productNm = defaultStringValue;

	@JsonProperty
	int seq = 0;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String typeNm = defaultStringValue;

	@JsonProperty
	String fileNm = defaultStringValue;

	@JsonProperty
	String result = RS_INIT;

	@JsonProperty
	String resultNm = defaultStringValue;

	@JsonProperty
	String agentNm = defaultStringValue;

	@JsonProperty
	String delYn = "N";

	@JsonProperty
	String regDate = defaultStringValue;

	@JsonProperty
	String regId = defaultStringValue;

	@JsonProperty
	String modDate = defaultStringValue;

	@JsonProperty
	String modId = defaultStringValue;

	@JsonProperty
	List<String> intfList  = new ArrayList<String>();

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerNm() {
		return serverNm;
	}

	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultNm() {
		return resultNm;
	}

	public void setResultNm(String resultNm) {
		this.resultNm = resultNm;
	}



	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public List<String> getIntfList() {
		return intfList;
	}

	public void setIntfList(List<String> intfList) {
		this.intfList = intfList;
	}




}
