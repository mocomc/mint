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
 * @since Jul 9, 2020
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppModelAttribute  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@JsonProperty
	String  interfaceMid = defaultStringValue;
	
	@JsonProperty
	String  appMid = defaultStringValue;
	
	@JsonProperty
	String  appType = defaultStringValue;

	@JsonProperty
	String  aid = defaultStringValue;
	
	
	@JsonProperty
	String  name = defaultStringValue;
	
	@JsonProperty
	String  cd = defaultStringValue;
	
	@JsonProperty
	String  type = defaultStringValue;

	@JsonProperty
	String  inputType = defaultStringValue;

	
	@JsonProperty
	int  seq = 0;
	 
	@JsonProperty
	String  val = defaultStringValue;
	
	@JsonProperty
	String  tag = defaultStringValue;

	/**
	 * @return the interfaceMid
	 */
	public String getInterfaceMid() {
		return interfaceMid;
	}

	/**
	 * @param interfaceMid the interfaceMid to set
	 */
	public void setInterfaceMid(String interfaceMid) {
		this.interfaceMid = interfaceMid;
	}

	/**
	 * @return the appMid
	 */
	public String getAppMid() {
		return appMid;
	}

	/**
	 * @param appMid the appMid to set
	 */
	public void setAppMid(String appMid) {
		this.appMid = appMid;
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
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
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
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
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
 
	
	
	
}
