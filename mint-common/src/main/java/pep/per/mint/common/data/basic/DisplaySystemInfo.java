/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisplaySystemInfo extends CacheableObject {

	@JsonProperty
	int seq;

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String systemCd = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;


	@JsonProperty
	String nodeType  = defaultStringValue;

	@JsonProperty
	String nodeTypeNm  = defaultStringValue;

	@JsonProperty
	String service = defaultStringValue;

	@JsonProperty
	String serviceDesc = defaultStringValue;

	@JsonProperty
	String resourceCd  = defaultStringValue;

	@JsonProperty
	String resourceNm  = defaultStringValue;

	@JsonProperty
	String businessId = defaultStringValue;

	@JsonProperty
	String businessCd  = defaultStringValue;

	@JsonProperty
	String businessNm  = defaultStringValue;

	@JsonProperty
	String orgId = defaultStringValue;

	@JsonProperty
	String orgCd  = defaultStringValue;

	@JsonProperty
	String orgNm  = defaultStringValue;

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
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the systemCd
	 */
	public String getSystemCd() {
		return systemCd;
	}

	/**
	 * @param systemCd the systemCd to set
	 */
	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	/**
	 * @return the systemNm
	 */
	public String getSystemNm() {
		return systemNm;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the nodeTypeNm
	 */
	public String getNodeTypeNm() {
		return nodeTypeNm;
	}

	/**
	 * @param nodeTypeNm the nodeTypeNm to set
	 */
	public void setNodeTypeNm(String nodeTypeNm) {
		this.nodeTypeNm = nodeTypeNm;
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the resourceCd
	 */
	public String getResourceCd() {
		return resourceCd;
	}

	/**
	 * @param resourceCd the resourceCd to set
	 */
	public void setResourceCd(String resourceCd) {
		this.resourceCd = resourceCd;
	}

	/**
	 * @return the resourceNm
	 */
	public String getResourceNm() {
		return resourceNm;
	}

	/**
	 * @param resourceNm the resourceNm to set
	 */
	public void setResourceNm(String resourceNm) {
		this.resourceNm = resourceNm;
	}

	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the businessCd
	 */
	public String getBusinessCd() {
		return businessCd;
	}

	/**
	 * @param businessCd the businessCd to set
	 */
	public void setBusinessCd(String businessCd) {
		this.businessCd = businessCd;
	}

	/**
	 * @return the businessNm
	 */
	public String getBusinessNm() {
		return businessNm;
	}

	/**
	 * @param businessNm the businessNm to set
	 */
	public void setBusinessNm(String businessNm) {
		this.businessNm = businessNm;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}


	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgCd
	 */
	public String getOrgCd() {
		return orgCd;
	}

	/**
	 * @param orgCd the orgCd to set
	 */
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	/**
	 * @return the orgNm
	 */
	public String getOrgNm() {
		return orgNm;
	}

	/**
	 * @param orgNm the orgNm to set
	 */
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}




}
