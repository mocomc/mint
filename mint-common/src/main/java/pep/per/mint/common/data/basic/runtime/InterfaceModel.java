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
public class InterfaceModel extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String mid = defaultStringValue;
	
	@JsonProperty
	String interfaceId = defaultStringValue;
	
	@JsonProperty
	String integrationId = defaultStringValue;
	
	@JsonProperty
	String name = defaultStringValue;
	
	@JsonProperty
	String stage = defaultStringValue;
	
	@JsonProperty
	String version = defaultStringValue;
	
	@JsonProperty
	String std = "Y"; //표준 비표준 구분 : Y(default value) / N
	
	@JsonProperty
	String stageName = defaultStringValue;
	
	@JsonProperty
	String deployStatus = defaultStringValue;
	
	@JsonProperty
	String deployStatusName = defaultStringValue;
	
	@JsonProperty
	String lastDeployDate = defaultStringValue;
	
	@JsonProperty
	String lastDeployUser = defaultStringValue;
	
	@JsonProperty
	String lastDeployUserNm = defaultStringValue;
	
	@JsonProperty
	int layoutCount = 0;
	
	@JsonProperty
	int mappingCount = 0;
	
	
	@JsonProperty
	String comments = defaultStringValue;
	
	@JsonProperty
	String delYn  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regId  = defaultStringValue;

	@JsonProperty
	String regNm  = defaultStringValue;
	
	@JsonProperty
	String modDate  = defaultStringValue;

	@JsonProperty
	String modId  = defaultStringValue;

	@JsonProperty
	String modNm  = defaultStringValue;
	
	@JsonProperty
	List<AppModel> appModels = new ArrayList<AppModel>();
	
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
	 * @return the integrationId
	 */
	public String getIntegrationId() {
		return integrationId;
	}

	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
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
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	
	
	
	/**
	 * @return the std
	 */
	public String getStd() {
		return std;
	}

	/**
	 * @param std the std to set
	 */
	public void setStd(String std) {
		this.std = std;
	}

	/**
	 * @return the stageName
	 */
	public String getStageName() {
		return stageName;
	}

	/**
	 * @param stageName the stageName to set
	 */
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	/**
	 * @return the deployStatus
	 */
	public String getDeployStatus() {
		return deployStatus;
	}

	/**
	 * @param deployStatus the deployStatus to set
	 */
	public void setDeployStatus(String deployStatus) {
		this.deployStatus = deployStatus;
	}

	/**
	 * @return the deployStatusName
	 */
	public String getDeployStatusName() {
		return deployStatusName;
	}

	/**
	 * @param deployStatusName the deployStatusName to set
	 */
	public void setDeployStatusName(String deployStatusName) {
		this.deployStatusName = deployStatusName;
	}

	/**
	 * @return the lastDeployDate
	 */
	public String getLastDeployDate() {
		return lastDeployDate;
	}

	/**
	 * @param lastDeployDate the lastDeployDate to set
	 */
	public void setLastDeployDate(String lastDeployDate) {
		this.lastDeployDate = lastDeployDate;
	}


	
	
	/**
	 * @return the lastDeployUser
	 */
	public String getLastDeployUser() {
		return lastDeployUser;
	}

	/**
	 * @param lastDeployUser the lastDeployUser to set
	 */
	public void setLastDeployUser(String lastDeployUser) {
		this.lastDeployUser = lastDeployUser;
	}
	
	
	

	/**
	 * @return the layoutCount
	 */
	public int getLayoutCount() {
		return layoutCount;
	}

	/**
	 * @param layoutCount the layoutCount to set
	 */
	public void setLayoutCount(int layoutCount) {
		this.layoutCount = layoutCount;
	}

	/**
	 * @return the mappingCount
	 */
	public int getMappingCount() {
		return mappingCount;
	}

	/**
	 * @param mappingCount the mappingCount to set
	 */
	public void setMappingCount(int mappingCount) {
		this.mappingCount = mappingCount;
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
	 * @return the appModels
	 */
	public List<AppModel> getAppModels() {
		return appModels;
	}

	/**
	 * @param appModels the appModels to set
	 */
	public void setAppModels(List<AppModel> appModels) {
		this.appModels = appModels;
	}

	/**
	 * @return the lastDeployUserNm
	 */
	public String getLastDeployUserNm() {
		return lastDeployUserNm;
	}

	/**
	 * @param lastDeployUserNm the lastDeployUserNm to set
	 */
	public void setLastDeployUserNm(String lastDeployUserNm) {
		this.lastDeployUserNm = lastDeployUserNm;
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

	/**
	 * @return the modNm
	 */
	public String getModNm() {
		return modNm;
	}

	/**
	 * @param modNm the modNm to set
	 */
	public void setModNm(String modNm) {
		this.modNm = modNm;
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
	 
	

}
