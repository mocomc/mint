/*
d * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 로그인 결과 기초정보 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class BasicInfo  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -5188269270344784367L;

	@JsonProperty("user")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	User user;


	@JsonProperty("channelList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Channel> channelList = new ArrayList<Channel>();


	@JsonProperty("menuList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Menu> menuList = new ArrayList<Menu>();

	//콩통코드맵
	@JsonProperty("cds")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Map<String,List<CommonCode>> cds = new HashMap<String,List<CommonCode>>();

	@JsonProperty("businessCdList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Business> businessCdList = new ArrayList<Business>();


	@JsonProperty("requirementCdList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Requirement> requirementCdList  = new ArrayList<Requirement>();


	@JsonProperty("interfaceCdList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Interface> interfaceCdList = new ArrayList<Interface>();

	@JsonProperty("systemCdList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<System> systemCdList = new ArrayList<System>();

	@JsonProperty
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<System> providerCdList = new ArrayList<System>();

	@JsonProperty("serviceList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<String> serviceList = new ArrayList<String>();

	@JsonProperty("serviceDescList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<String> serviceDescList = new ArrayList<String>();

	@JsonProperty("interfaceTagList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<InterfaceTag> interfaceTagList = new ArrayList<InterfaceTag>();

	@JsonProperty("userList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<User> userList = new ArrayList<User>();

	@JsonProperty("roleList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<UserRole> roleList = new ArrayList<UserRole>();

	@JsonProperty("environmentalValues")
	Map<String, List<String>> environmentalValues = new LinkedHashMap<String, List<String>>();

	@JsonProperty("organizationList")
	List<Organization> organizationList = new ArrayList<Organization>();

	@JsonProperty("interfaceAdditionalAttributeList")
	List<InterfaceAdditionalAttribute> interfaceAdditionalAttributeList = new ArrayList<InterfaceAdditionalAttribute>();


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the menuList
	 */
	public List<Menu> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}


	/**
	 * @return the businessCdList
	 */
	public List<Business> getBusinessCdList() {
		return businessCdList;
	}

	/**
	 * @param businessCdList the businessCdList to set
	 */
	public void setBusinessCdList(List<Business> businessCdList) {
		this.businessCdList = businessCdList;
	}

	/**
	 * @return the requirementCdList
	 */
	public List<Requirement> getRequirementCdList() {
		return requirementCdList;
	}

	/**
	 * @param requirementCdList the requirementCdList to set
	 */
	public void setRequirementCdList(List<Requirement> requirementCdList) {
		this.requirementCdList = requirementCdList;
	}

	/**
	 * @return the interfaceCdList
	 */
	public List<Interface> getInterfaceCdList() {
		return interfaceCdList;
	}

	/**
	 * @param interfaceCdList the interfaceCdList to set
	 */
	public void setInterfaceCdList(List<Interface> interfaceCdList) {
		this.interfaceCdList = interfaceCdList;
	}

	/**
	 * @return the systemCdList
	 */
	public List<System> getSystemCdList() {
		return systemCdList;
	}

	/**
	 * @param systemCdList the systemCdList to set
	 */
	public void setSystemCdList(List<System> systemCdList) {
		this.systemCdList = systemCdList;
	}

	/**
	 * @return the serviceList
	 */
	public List<String> getServiceList() {
		return serviceList;
	}

	/**
	 * @param serviceList the serviceList to set
	 */
	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}


	/**
	 * @return the interfaceTagList
	 */
	public List<InterfaceTag> getInterfaceTagList() {
		return interfaceTagList;
	}

	/**
	 * @param interfaceTagList the interfaceTagList to set
	 */
	public void setInterfaceTagList(List<InterfaceTag> interfaceTagList) {
		this.interfaceTagList = interfaceTagList;
	}

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return the roleList
	 */
	public List<UserRole> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<UserRole> roleList) {
		this.roleList = roleList;
	}


	/**
	 * @return the cds
	 */
	public Map<String, List<CommonCode>> getCds() {
		return cds;
	}

	/**
	 * @param cds the cds to set
	 */
	public void setCds(Map<String, List<CommonCode>> cds) {
		this.cds = cds;
	}

	/**
	 * @return the channelList
	 */
	public List<Channel> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	/**
	 *
	 * @return
	 */
	public Map<String, List<String>> getEnvironmentalValues() {
		return environmentalValues;
	}

	/**
	 *
	 * @param environmentalValues
	 */
	public void setEnvironmentalValues(Map<String, List<String>> environmentalValues) {
		this.environmentalValues = environmentalValues;
	}

	public List<System> getProviderCdList() {
		return providerCdList;
	}

	public void setProviderCdList(List<System> providerCdList) {
		this.providerCdList = providerCdList;
	}

	public List<String> getServiceDescList() {
		return serviceDescList;
	}

	public void setServiceDescList(List<String> serviceDescList) {
		this.serviceDescList = serviceDescList;
	}

	/**
	 * @return the organizationList
	 */
	public List<Organization> getOrganizationList() {
		return organizationList;
	}

	/**
	 * @param systemCdList the systemCdList to set
	 */
	public void setOrganizationList(List<Organization> organizationList) {
		this.organizationList = organizationList;
	}

	public List<InterfaceAdditionalAttribute> getInterfaceAdditionalAttributeList() {
		return interfaceAdditionalAttributeList;
	}

	public void setInterfaceAdditionalAttributeList(List<InterfaceAdditionalAttribute> interfaceAdditionalAttributeList) {
		this.interfaceAdditionalAttributeList = interfaceAdditionalAttributeList;
	}

}
