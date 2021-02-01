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
public class Organization  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469508541279577768L;

	/*** 기관ID */
	@JsonProperty("organizationId")
	String organizationId = defaultStringValue;
	
	/*** 기관명 */
	@JsonProperty("organizationNm")
	String organizationNm = defaultStringValue;
	
	/*** 기관코드 */
	@JsonProperty("organizationCd")
	String organizationCd = defaultStringValue;
	
	/*** parent */
	@JsonProperty("parentOrganization")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Organization parent;
	
	/*** 그룹여부 */
	@JsonProperty("grpYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String grpYn = defaultStringValue;
	
	/*** ROOT여부 */
	@JsonProperty("rootYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String rootYn = defaultStringValue;
	
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
	
	
	/*** 시스템리스트 */
	@JsonProperty("systemList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<System> systemList = new ArrayList<System>();
	
	/*** 담당자리스트 */
	@JsonProperty("relUsers")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RelUser> relUsers  = new ArrayList<RelUser>();
	
	
	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the organizationNm
	 */
	public String getOrganizationNm() {
		return organizationNm;
	}

	/**
	 * @param organizationNm the organizationNm to set
	 */
	public void setOrganizationNm(String organizationNm) {
		this.organizationNm = organizationNm;
	}

	/**
	 * @return the organizationCd
	 */
	public String getOrganizationCd() {
		return organizationCd;
	}

	/**
	 * @param organizationCd the organizationCd to set
	 */
	public void setOrganizationCd(String organizationCd) {
		this.organizationCd = organizationCd;
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
	 * @return the systemList
	 */
	public List<System> getSystemList() {
		return this.systemList;
	}

	/**
	 * @param systemList the systemList to set
	 */
	public void setSystemList(List<System> systemList) {
		this.systemList = systemList;
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
	 * @return the parent
	 */
	public Organization getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Organization parent) {
		this.parent = parent;
	}


	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}
}
