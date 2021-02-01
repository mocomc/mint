/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppModelAttributeId extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String INPUT_TYPE_COMBO = "4";
	
	
	@JsonProperty
	String  aid = defaultStringValue;

	
	@JsonProperty
	String  appType = defaultStringValue;
	
	@JsonProperty
	String appTypeName = defaultStringValue;
	
	@JsonProperty
	String cd = defaultStringValue;	
	
	@JsonProperty
	int seq = 0;
	
	@JsonProperty
	String name = defaultStringValue;	
	
	@JsonProperty
	String type = defaultStringValue;	
	
	
	@JsonProperty
	String typeName = defaultStringValue;	
	
	@JsonProperty
	String tagYn = "N";	
	
	@JsonProperty
	String inputType = defaultStringValue;

	@JsonProperty
	String inputTypeName = defaultStringValue;
	
	@JsonProperty	
	List<AppModelAttributeCode> cds = new ArrayList<AppModelAttributeCode>();
	
	@JsonProperty
	String requiredYn = "Y";
	
	@JsonProperty
	String defaultValue = defaultStringValue;
	
	@JsonProperty
	String comments;
	
	@JsonProperty
	String delYn;

	@JsonProperty
	String regDate;

	@JsonProperty
	String regId;

	@JsonProperty
	String modDate;

	@JsonProperty
	String modId;

	public AppModelAttributeId() {
		super();
	}
	
	public AppModelAttributeId(String aid, String appType, String name, String cd, int seq, String type, String tagYn, String inputType) {
		this.aid = aid;
		this.appType = appType;
		this.name = name;
		this.cd = cd;
		this.seq = seq;
		this.type = type;
		this.tagYn = tagYn;
		this.inputType = inputType;
		this.comments = name;
		this.delYn = "N";
	}
	
	/**
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * @param aid the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}
	
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
	 * @return the inputTypeName
	 */
	public String getInputTypeName() {
		return inputTypeName;
	}

	/**
	 * @param inputTypeName the inputTypeName to set
	 */
	public void setInputTypeName(String inputTypeName) {
		this.inputTypeName = inputTypeName;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
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
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the tagYn
	 */
	public String getTagYn() {
		return tagYn;
	}

	/**
	 * @param tagYn the tagYn to set
	 */
	public void setTagYn(String tagYn) {
		this.tagYn = tagYn;
	}

	
	
	
	/**
	 * @return the inputType
	 */
	public String getInputType() {
		return inputType;
	}

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	/**
	 * @return the cds
	 */
	public List<AppModelAttributeCode> getCds() {
		return cds;
	}

	/**
	 * @param cds the cds to set
	 */
	public void setCds(List<AppModelAttributeCode> cds) {
		this.cds = cds;
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
	 * @return the requiredYn
	 */
	public String getRequiredYn() {
		return requiredYn;
	}

	/**
	 * @param requiredYn the requiredYn to set
	 */
	public void setRequiredYn(String requiredYn) {
		this.requiredYn = requiredYn;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	
	
}
