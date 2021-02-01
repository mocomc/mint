/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;


/**
 * 시스템 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class System  extends CacheableObject{
	
	public static final String NODE_TYPE_SENDER = "0";
	public static final String NODE_TYPE_HUB = "1";
	public static final String NODE_TYPE_RECEIVER = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469508541279577768L;

	/*** 시스템ID */
	@JsonProperty("systemId")
	String systemId = defaultStringValue;
	
	/*** 시스템명 */
	@JsonProperty("systemNm")
	String systemNm = defaultStringValue;
	
	/*** 시스템코드 */
	@JsonProperty("systemCd")
	String systemCd = defaultStringValue;
	
	/*** parent */
	@JsonProperty("parentSystem")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	System parent;
	
	/*** 그룹여부 */
	@JsonProperty("grpYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String grpYn = defaultStringValue;
	
	/*** ROOT여부 */
	@JsonProperty("rootYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String rootYn = defaultStringValue;
	
	/*** 대내대외구분 */
	@JsonProperty("externalYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String externalYn = defaultStringValue;
	
	/*** 지역 */
	@JsonProperty("zoneInfo")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	ZoneInfo zoneInfo;
	
	/*** 지역입력값 */
	@JsonProperty("areaInput")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String areaInput = defaultStringValue;
	
	/*** 설명 */
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;

	@JsonProperty
	int childCnt = 0;

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
	
	/*** 순번  */
	@JsonProperty("seq")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int seq;
	
	/*** 노드유형  */
	@JsonProperty("nodeType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String nodeType = defaultStringValue;
	
	/*** 노드유형명  */
	@JsonProperty("nodeTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String nodeTypeNm = defaultStringValue;
	
	
	/*** 서버리스트 */
	@JsonProperty("serverList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Server> serverList = new ArrayList<Server>();
	
	/*** 담당자리스트 */
	@JsonProperty("relUsers")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RelUser> relUsers  = new ArrayList<RelUser>();
	
	/*** 서비스 */
	@JsonProperty("service")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String service = defaultStringValue;
	
	/*** 서비스 */
	@JsonProperty("serviceDesc")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String serviceDesc = defaultStringValue;	
	
	/*** 리소스코드 */
	@JsonProperty("resourceCd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String resourceCd = defaultStringValue;
	
	/*** 리소스명 */
	@JsonProperty("resourceNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String resourceNm = defaultStringValue;

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
	 * @return the grpYn
	 */
	public String getGrpYn() {
		return grpYn;
	}

	/**
	 * @param grpYn the grpYn to set
	 */
	public void setGrpYn(String grpYn) {
		this.grpYn = grpYn;
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
	 * @return the zoneInfo
	 */
	public ZoneInfo getZoneInfo() {
		return zoneInfo;
	}

	/**
	 * @param zoneInfo the zoneInfo to set
	 */
	public void setZoneInfo(ZoneInfo zoneInfo) {
		this.zoneInfo = zoneInfo;
	}

	/**
	 * @return the areaInput
	 */
	public String getAreaInput() {
		return areaInput;
	}

	/**
	 * @param areaInput the areaInput to set
	 */
	public void setAreaInput(String areaInput) {
		this.areaInput = areaInput;
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
	 * @return the serverList
	 */
	public List<Server> getServerList() {
		return serverList;
	}

	/**
	 * @param serverList the serverList to set
	 */
	public void setServerList(List<Server> serverList) {
		this.serverList = serverList;
	}

	/**
	 * @return the relUsers
	 */
	public List<RelUser> getRelUsers() {
		return relUsers;
	}

	/**
	 * @param relUsers the relUsers to set
	 */
	public void setRelUsers(List<RelUser> relUsers) {
		this.relUsers = relUsers;
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
	 * @return the parent
	 */
	public System getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(System parent) {
		this.parent = parent;
	}


	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
}
