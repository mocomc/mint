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
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

import java.util.List;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class UserRole  extends CacheableObject{



	/**
	 *
	 */
	private static final long serialVersionUID = -9111870598882194034L;

	@JsonProperty("roleId")
	String roleId = defaultStringValue;

	@JsonProperty("roleNm")
	String roleNm = defaultStringValue;

	@JsonProperty("isInterfaceAdmin")
	String isInterfaceAdmin = "N";

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

	@JsonProperty
	String isChannelAdmin = "N";
	
	@JsonProperty
	String isInterfaceViewer = "N";	

	@JsonProperty
	List<String> channelIdList = null;

	@JsonProperty
	List<Menu> menuList = null;

	@JsonProperty
	List<Application> appList = null;

	@JsonProperty
	List<Service> serviceList = null;
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
	 * @return the roleNm
	 */
	public String getRoleNm() {
		return roleNm;
	}



	/**
	 * @return the isInterfaceAdmin
	 */
	public String getIsInterfaceAdmin() {
		return isInterfaceAdmin;
	}

	/**
	 * @param isInterfaceAdmin the isInterfaceAdmin to set
	 */
	public void setIsInterfaceAdmin(String isInterfaceAdmin) {
		this.isInterfaceAdmin = isInterfaceAdmin;
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

	public String getIsChannelAdmin() {
		return isChannelAdmin;
	}

	public void setIsChannelAdmin(String isChannelAdmin) {
		this.isChannelAdmin = isChannelAdmin;
	}

	public List<String> getChannelIdList() {
		return channelIdList;
	}

	public void setChannelIdList(List<String> channelIdList) {
		this.channelIdList = channelIdList;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<Application> getAppList() {
		return appList;
	}

	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public String getIsInterfaceViewer() {
		return isInterfaceViewer;
	}

	public void setIsInterfaceViewer(String isInterfaceViewer) {
		this.isInterfaceViewer = isInterfaceViewer;
	}


}
