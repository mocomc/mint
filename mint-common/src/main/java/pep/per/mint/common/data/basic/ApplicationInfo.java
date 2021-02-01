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
 * ApplicationInfo.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApplicationInfo extends CacheableObject{

	public static final String TYPE_WMQ = "00";
	public static final String TYPE_MTE = "10";
	public static final String TYPE_TMAX = "20";

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String serverId = defaultStringValue;

	@JsonProperty
	String serverNm = defaultStringValue;

	@JsonProperty
	String applicationId = defaultStringValue;


	@JsonProperty
	String applicationNm = defaultStringValue;

	@JsonProperty
	String home = defaultStringValue;


	@JsonProperty
	String version = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
    String delYn = "N";

	@JsonProperty
    String regDate = defaultStringValue;

	@JsonProperty
    String regUser = defaultStringValue;

	@JsonProperty
    String modDate = defaultStringValue;

	@JsonProperty
    String modUser = defaultStringValue;

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

	public String getServerNm() {
		return serverNm;
	}

	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}



	public String getApplicationNm() {
		return applicationNm;
	}

	public void setApplicationNm(String applicationNm) {
		this.applicationNm = applicationNm;
	}



	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regUser;
	}

	public void setRegId(String regId) {
		this.regUser = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modUser;
	}

	public void setModId(String modId) {
		this.modUser = modId;
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
		builder.append("ApplicationInfo [systemId=");
		builder.append(systemId);
		builder.append(", systemNm=");
		builder.append(systemNm);
		builder.append(", serverId=");
		builder.append(serverId);
		builder.append(", serverNm=");
		builder.append(serverNm);
		builder.append(", applicationId=");
		builder.append(applicationId);
		builder.append(", applicationNm=");
		builder.append(applicationNm);
		builder.append(", home=");
		builder.append(home);
		builder.append(", version=");
		builder.append(version);
		builder.append(", type=");
		builder.append(type);
		builder.append(", delYn=");
		builder.append(delYn);
		builder.append(", regDate=");
		builder.append(regDate);
		builder.append(", regUser=");
		builder.append(regUser);
		builder.append(", modDate=");
		builder.append(modDate);
		builder.append(", modUser=");
		builder.append(modUser);
		builder.append(", binaryPath=");
		builder.append(binaryPath);
		builder.append("]");
		return builder.toString();
	}




}
