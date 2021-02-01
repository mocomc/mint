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
 * 인터페이스패턴 Data Object 
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ErrorHistory  extends CacheableObject{

  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6389835248583014113L;
	
	
	/*** 인터페이스ID */
	String interfaceId = defaultStringValue;
	
	/*** 장애발생일시 */
	String errorDateTime = defaultStringValue;
	
	/*** 장애/오류내역 */
	String errorReport = defaultStringValue;
	
	/*** 처리결과 */
	List<Interface> actionResult = new ArrayList<Interface>();
	
	/*** 장애분류 */
	String errorClass = defaultStringValue;
	
	/*** 처리자 */
	User actionUser;
	
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
 
 
	

	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the errorDateTime
	 */
	public String getErrorDateTime() {
		return errorDateTime;
	}

	/**
	 * @param errorDateTime the errorDateTime to set
	 */
	public void setErrorDateTime(String errorDateTime) {
		this.errorDateTime = errorDateTime;
	}

	/**
	 * @return the errorReport
	 */
	public String getErrorReport() {
		return errorReport;
	}

	/**
	 * @param errorReport the errorReport to set
	 */
	public void setErrorReport(String errorReport) {
		this.errorReport = errorReport;
	}

	/**
	 * @return the actionResult
	 */
	public List<Interface> getActionResult() {
		return actionResult;
	}

	/**
	 * @param actionResult the actionResult to set
	 */
	public void setActionResult(List<Interface> actionResult) {
		this.actionResult = actionResult;
	}

	/**
	 * @return the errorClass
	 */
	public String getErrorClass() {
		return errorClass;
	}

	/**
	 * @param errorClass the errorClass to set
	 */
	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	/**
	 * @return the actionUser
	 */
	public User getActionUser() {
		return actionUser;
	}

	/**
	 * @param actionUser the actionUser to set
	 */
	public void setActionUser(User actionUser) {
		this.actionUser = actionUser;
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
	               
	
	
	
}
