/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * <pre>
 * pep.per.mint.common.data.basic
 * SiteHomeInfo.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SiteHomeInfo extends CacheableObject{


	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String systemCd = defaultStringValue;

	@JsonProperty
	String home = defaultStringValue;


	@JsonProperty
	String binaryPath = defaultStringValue;



	public String getSystemId() {
		return systemId;
	}



	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}



	public String getSystemNm() {
		return systemNm;
	}



	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}



	public String getSystemCd() {
		return systemCd;
	}



	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}



	public String getHome() {
		return home;
	}



	public void setHome(String home) {
		this.home = home;
	}



	public String getBinaryPath() {
		return binaryPath;
	}



	public void setBinaryPath(String binaryPath) {
		this.binaryPath = binaryPath;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiteHomeInfo [systemId=");
		builder.append(systemId);
		builder.append(", systemNm=");
		builder.append(systemNm);
		builder.append(", systemCd=");
		builder.append(systemCd);
		builder.append(", home=");
		builder.append(home);
		builder.append(", binaryPath=");
		builder.append(binaryPath);
		builder.append("]");
		return builder.toString();
	}



}
