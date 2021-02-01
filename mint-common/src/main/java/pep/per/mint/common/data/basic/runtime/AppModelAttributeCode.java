/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppModelAttributeCode extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	String  attrName = defaultStringValue;	
	
	@JsonProperty
	String  attrCd = defaultStringValue;	

	@JsonProperty
	int attrSeq = 0;
	
	@JsonProperty
	String comments;
	
	@JsonProperty
	String delYn;

	@JsonProperty
	String regDate;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String regId;

	@JsonProperty
	String modDate;

	@JsonProperty
	String modId;
	
	public AppModelAttributeCode() {
		super();
	}
 


	/**
	 * @param attrName
	 * @param attrCd
	 * @param attrSeq
	 */
	public AppModelAttributeCode(String attrCd, String attrName, int attrSeq) {
		this();
		this.attrName = attrName;
		this.attrCd = attrCd;
		this.attrSeq = attrSeq;
		this.comments = attrName;
		this.delYn = "N";
	}



	/**
	 * 
	 * @return
	 */
	public String getAttrName() {
		return attrName;
	}

	 
	/**
	 * 
	 * @param attrName
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	 
	/**
	 * 
	 * @return
	 */
	public String getAttrCd() {
		return attrCd;
	}

	 
	/**
	 * 
	 * @param attrCd
	 */
	public void setAttrCd(String attrCd) {
		this.attrCd = attrCd;
	}

	
	/**
	 *  
	 * @return
	 */
	public int getAttrSeq() {
		return attrSeq;
	}
 
	/**
	 * 
	 * @param attrSeq
	 */
	public void setAttrSeq(int attrSeq) {
		this.attrSeq = attrSeq;
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
