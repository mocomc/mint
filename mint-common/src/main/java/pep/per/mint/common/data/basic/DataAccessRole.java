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
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DataAccessRole  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -2037979901814881131L;

	@JsonProperty("roleId")
	String roleId = defaultStringValue;

	@JsonProperty("roleCd")
	String roleCd = defaultStringValue;

	@JsonProperty("roleNm")
	String roleNm = defaultStringValue;

	@JsonProperty("comments")
	String comments   = defaultStringValue;

	@JsonProperty("delYn")
	String delYn = "N"    ;

	@JsonProperty("regDate")
	String regDate    = defaultStringValue;

	@JsonProperty("regId")
	String regId      = defaultStringValue;

	@JsonProperty("modDate")
	String modDate    = defaultStringValue;

	@JsonProperty("modId")
	String modId      = defaultStringValue;

	@JsonProperty("seq")
	String seq      = defaultStringValue;

	@JsonProperty("grpYn")
	String grpYn = "N";

	@JsonProperty("rootYn")
	String rootYn = "N";

	/*** 시스템리스트 */
	@JsonProperty("systemList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<System> systemList = new ArrayList<System>();

	@JsonProperty("relUsers")
	List<RelUser> relUsers = new ArrayList<RelUser>();

	/*** parent */
	@JsonProperty("parentDataAccessRole")
	DataAccessRole parent;

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleCd
	 */
	public String getRoleCd() {
		return roleCd;
	}

	/**
	 * @param roleCd the roleCd to set
	 */
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	/**
	 * @return the roleNm
	 */
	public String getRoleNm() {
		return roleNm;
	}

	/**
	 * @param roleNm the roleNm to set
	 */
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public List<RelUser> getRelUsers() {
		return relUsers;
	}

	public void setRelUsers(List<RelUser> relUsers) {
		this.relUsers = relUsers;
	}

	public DataAccessRole getParent() {
		return parent;
	}

	public void setParent(DataAccessRole parent) {
		this.parent = parent;
	}

	public String getGrpYn() {
		return grpYn;
	}

	public void setGrpYn(String grpYn) {
		this.grpYn = grpYn;
	}

	public String getRootYn() {
		return rootYn;
	}

	public List<System> getSystemList() {
		return systemList;
	}

	public void setSystemList(List<System> systemList) {
		this.systemList = systemList;
	}

	public void setRootYn(String rootYn) {
		this.rootYn = rootYn;
	}

}
