/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since 2020. 10. 15.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppType extends CacheableObject{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	String appType = defaultStringValue;
	
	@JsonProperty
	String appTypeName = defaultStringValue;
	
	@JsonProperty
	String resourceCd = defaultStringValue;
	
	@JsonProperty
	String resourceNm = defaultStringValue;
	
	@JsonProperty
	String comments = defaultStringValue;
	
	@JsonProperty
	String delYn  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regId  = defaultStringValue;

	@JsonProperty
	String modDate  = defaultStringValue;

	@JsonProperty
	String modId  = defaultStringValue;

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return the appTypeName
	 */
	public String getAppTypeName() {
		return appTypeName;
	}

	/**
	 * @param appTypeName the appTypeName to set
	 */
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
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
	
	
}
