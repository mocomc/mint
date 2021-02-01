/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.data.basic.security.UserSecurityProperty;

/**
 * 사용자 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class User  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 124337473828593564L;

	/*** 사용자ID */
	@JsonProperty("userId")
	String userId = defaultStringValue;

	/*** 패스워드 */
	@JsonProperty("password")
	String password = defaultStringValue;

	/*** 이름 */
	@JsonProperty("userNm")
	String userNm = defaultStringValue;

	/*** 핸드폰 */
	@JsonProperty("cellPhone")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String cellPhone = defaultStringValue;

	/*** 전화번호 */
	@JsonProperty("phone")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String phone = defaultStringValue;

	/*** 이메일 */
	@JsonProperty("email")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String email = defaultStringValue;

	@JsonProperty
	String companyNm = defaultStringValue;

	@JsonProperty
	String departmentNm = defaultStringValue;

	@JsonProperty
	String positionNm = defaultStringValue;

	@JsonProperty
	String retired = "N";

	/*** 사용자롤 */
	@JsonProperty("role")
	UserRole role;

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

	/*** 사용자 DataAccessRoleList */
	@JsonProperty("dataAccessRoleList")
	List<DataAccessRole> dataAccessRoleList = null;
	
	/*** (아시아나) 로그인 히스토리 관리 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	LoginHistory loginHistory;
	
	/*** (아시아나) 계정 IP Account Lock 등 보안 관리 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	UserSecurityProperty userSecurityProperty;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userNm
	 */
	public String getUserNm() {
		return userNm;
	}

	/**
	 * @param userNm the userNm to set
	 */
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
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

	public String getCompanyNm() {
		return companyNm;
	}

	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}

	public String getDepartmentNm() {
		return departmentNm;
	}

	public void setDepartmentNm(String departmentNm) {
		this.departmentNm = departmentNm;
	}

	public String getPositionNm() {
		return positionNm;
	}

	public List<DataAccessRole> getDataAccessRoleList() {
		return dataAccessRoleList;
	}

	public void setDataAccessRoleList(List<DataAccessRole> dataAccessRoleList) {
		this.dataAccessRoleList = dataAccessRoleList;
	}

	public void setPositionNm(String positionNm) {
		this.positionNm = positionNm;
	}

	public String isRetired() {
		return retired;
	}

	public void setRetired(String retired) {
		this.retired = retired;
	}
	
	public LoginHistory getLoginHistory(){
		return loginHistory;		
	}
	
	public void setLoginHistory(LoginHistory loginHistory){
		this.loginHistory = loginHistory;
	}

	public UserSecurityProperty getUserSecurityProperty() {
		return userSecurityProperty;
	}

	public void setUserSecurityProperty(UserSecurityProperty userSecurityProperty) {
		this.userSecurityProperty = userSecurityProperty;
	}	
}
