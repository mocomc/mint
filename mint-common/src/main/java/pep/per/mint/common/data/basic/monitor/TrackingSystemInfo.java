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
package pep.per.mint.common.data.basic.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author yusikKim
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TrackingSystemInfo extends CacheableObject {
	
	/*** 로그ID */
	@JsonProperty("logId")
	String logId = defaultStringValue;

	/*** 로그키1 */
	@JsonProperty("logKey1")
	String logKey1 = defaultStringValue;

	/*** 로그키2 */
	@JsonProperty("logKey2")
	String logKey2 = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 상태 */
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;
	
	/*** 처리시작시간 */
	@JsonProperty("logTime")
	String logTime = defaultStringValue;

	/*** 연계채널명 */
	@JsonProperty("channelId")
	String channelId = defaultStringValue;

	/*** 연계채널ID */
	@JsonProperty("channelNm")
	String channelNm = defaultStringValue;

	/*** 인터페이스ID */
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;

	/*** 통합인터페이스ID */
	@JsonProperty("integrationId")
	String integrationId = defaultStringValue;

	/*** 인터페이스명 */
	@JsonProperty("interfaceNm")
	String interfaceNm = defaultStringValue;

	/*** 업무ID */
	@JsonProperty("businessId")
	String businessId = defaultStringValue;

	/*** 업무명 */
	@JsonProperty("businessNm")
	String businessNm = defaultStringValue;
	
	

	/*** 디폴트 시스템 ID */
	@JsonProperty("defSystemId")
	String defSystemId = defaultStringValue;

	/*** 디폴트 시스템 명 */
	@JsonProperty("defSystemNm")
	String defSystemNm = defaultStringValue;
	
	/*** 디폴트 시스템 코드 */
	@JsonProperty("defSystemCd")
	String defSystemCd = defaultStringValue;
	
	/*** 디폴트 시퀀스 */
	@JsonProperty("defSeq")
	String defSeq = defaultStringValue;	
	
	/*** 디폴트 노드 타입 */
	@JsonProperty("defNodeType")
	String defNodeType = defaultStringValue;
	
	/*** 디폴트 노드 명 */
	@JsonProperty("defNodeTypeNm")
	String defNodeTypeNm = defaultStringValue;	
	
	/*** 디폴트 서비스 */
	@JsonProperty("defService")
	String defService = defaultStringValue;
	
	/*** 디폴트 리소스 타입 */
	@JsonProperty("defResourceType")
	String defResourceType = defaultStringValue;
	
	/*** 디폴트 리소스 명 */
	@JsonProperty("defResourceTypeNm")
	String defResourceTypeNm = defaultStringValue;
	
	
	
	/*** 시스템 ID */
	@JsonProperty("systemId")
	String systemId = defaultStringValue;

	/*** 시스템 명 */
	@JsonProperty("systemNm")
	String systemNm = defaultStringValue;
	
	/*** 시스템 코드 */
	@JsonProperty("systemCd")
	String systemCd = defaultStringValue;
	
	/*** 시퀀스 */
	@JsonProperty("seq")
	String seq = defaultStringValue;	
	
	/*** 노드 타입 */
	@JsonProperty("nodeType")
	String nodeType = defaultStringValue;
	
	/*** 노드 명 */
	@JsonProperty("nodeTypeNm")
	String nodeTypeNm = defaultStringValue;		
	
	/*** 서비스 */
	@JsonProperty("service")
	String service = defaultStringValue;
	
	/*** 리소스 타입 */
	@JsonProperty("resourceType")
	String resourceType = defaultStringValue;
	
	/*** 리소스 명 */
	@JsonProperty("resourceTypeNm")
	String resourceTypeNm = defaultStringValue;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogKey1() {
		return logKey1;
	}

	public void setLogKey1(String logKey1) {
		this.logKey1 = logKey1;
	}

	public String getLogKey2() {
		return logKey2;
	}

	public void setLogKey2(String logKey2) {
		this.logKey2 = logKey2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelNm() {
		return channelNm;
	}

	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessNm() {
		return businessNm;
	}

	public void setBusinessNm(String businessNm) {
		this.businessNm = businessNm;
	}

	public String getDefSystemId() {
		return defSystemId;
	}

	public void setDefSystemId(String defSystemId) {
		this.defSystemId = defSystemId;
	}

	public String getDefSystemNm() {
		return defSystemNm;
	}

	public void setDefSystemNm(String defSystemNm) {
		this.defSystemNm = defSystemNm;
	}

	public String getDefSystemCd() {
		return defSystemCd;
	}

	public void setDefSystemCd(String defSystemCd) {
		this.defSystemCd = defSystemCd;
	}

	public String getDefSeq() {
		return defSeq;
	}

	public void setDefSeq(String defSeq) {
		this.defSeq = defSeq;
	}

	public String getDefNodeType() {
		return defNodeType;
	}

	public void setDefNodeType(String defNodeType) {
		this.defNodeType = defNodeType;
	}

	public String getDefService() {
		return defService;
	}

	public void setDefService(String defService) {
		this.defService = defService;
	}

	public String getDefResourceType() {
		return defResourceType;
	}

	public void setDefResourceType(String defResourceType) {
		this.defResourceType = defResourceType;
	}

	public String getDefResourceTypeNm() {
		return defResourceTypeNm;
	}

	public void setDefResourceTypeNm(String defResourceTypeNm) {
		this.defResourceTypeNm = defResourceTypeNm;
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

	public String getSystemCd() {
		return systemCd;
	}

	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeNm() {
		return resourceTypeNm;
	}

	public void setResourceTypeNm(String resourceTypeNm) {
		this.resourceTypeNm = resourceTypeNm;
	}

	public String getDefNodeTypeNm() {
		return defNodeTypeNm;
	}

	public void setDefNodeTypeNm(String defNodeTypeNm) {
		this.defNodeTypeNm = defNodeTypeNm;
	}

	public String getNodeTypeNm() {
		return nodeTypeNm;
	}

	public void setNodeTypeNm(String nodeTypeNm) {
		this.nodeTypeNm = nodeTypeNm;
	}	
	

}
