/*
 * Copyright 2013u ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
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
 * 업무 Data Object
 * 
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Business  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193818090588735734L;

	@JsonProperty("businessId")
	String businessId = defaultStringValue;
	
	@JsonProperty("businessNm")
	String businessNm = defaultStringValue;
	
	@JsonProperty("businessCd")
	String businessCd = defaultStringValue;
	
	/*** 순번  */
	@JsonProperty("seq")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int seq;
	
	
	@JsonProperty("grpYn")
	String grpYn = "N";
	
	@JsonProperty("rootYn")
	String rootYn = "N";
	
	/*** 노드유형  */
	@JsonProperty("nodeType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String nodeType = defaultStringValue;
	
	/*** 노드유형명  */
	@JsonProperty("nodeTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String nodeTypeNm = defaultStringValue;
	
	/*** parent */
	@JsonProperty("parentBusiness")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Business parent;
	
	
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments   = defaultStringValue;
	
	@JsonProperty("delYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String delYn = "N"    ;
	
	@JsonProperty("regDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regDate    = defaultStringValue;
	
	@JsonProperty("regId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regId      = defaultStringValue;
	
	@JsonProperty("modDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modDate    = defaultStringValue;
	
	@JsonProperty("modId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modId      = defaultStringValue;
	
	@JsonProperty("relUsers")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RelUser> relUsers = new ArrayList<RelUser>();
	
	@JsonProperty
	int childCnt = 0;

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
	public Business getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Business parent) {
		this.parent = parent;
	}


	public int getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(int childCnt) {
		this.childCnt = childCnt;
	}
	
	

}
