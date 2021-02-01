/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterfaceModelHistory extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	String mid = defaultStringValue;
	
	@JsonProperty
	InterfaceModel interfaceModel; 
	 
	@JsonProperty
	String version = defaultStringValue;
	
	@JsonProperty
	String contents = defaultStringValue;
	
	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regId  = defaultStringValue;

	@JsonProperty
	String regNm  = defaultStringValue;
	

	
	public InterfaceModelHistory() {
		super();
	}
	
	public InterfaceModelHistory(InterfaceModel interfaceModel) {
		this();
		this.interfaceModel = interfaceModel;
		this.mid = interfaceModel.getMid();
		this.regDate = Util.isEmpty(interfaceModel.getModDate()) ? interfaceModel.getRegDate() : interfaceModel.getModDate();
		this.regId = Util.isEmpty(interfaceModel.getModId()) ? interfaceModel.getRegId() : interfaceModel.getModId();
		this.contents = Util.toJSONString(interfaceModel);
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the interfaceModel
	 */
	public InterfaceModel getInterfaceModel() {
		return interfaceModel;
	}

	/**
	 * @param interfaceModel the interfaceModel to set
	 */
	public void setInterfaceModel(InterfaceModel interfaceModel) {
		this.interfaceModel = interfaceModel;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
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
	 * @return the regNm
	 */
	public String getRegNm() {
		return regNm;
	}

	/**
	 * @param regNm the regNm to set
	 */
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	

}
