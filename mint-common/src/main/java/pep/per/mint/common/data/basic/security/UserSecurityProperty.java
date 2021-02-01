package pep.per.mint.common.data.basic.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class UserSecurityProperty extends CacheableObject{

	@JsonProperty	
	String userId = defaultStringValue;
	
	@JsonProperty
	String ipList = defaultStringValue;
	
	@JsonProperty
	String isAccountLock = defaultStringValue;
	
	@JsonProperty
	int psFailCount = 0;
	
	@JsonProperty
	String lastLoginDate = defaultStringValue;
	
	@JsonProperty
	String lastLoginIp = defaultStringValue;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpList() {
		return ipList;
	}

	public void setIpList(String ipList) {
		this.ipList = ipList;
	}

	public String getIsAccountLock() {
		return isAccountLock;
	}

	public void setIsAccountLock(String isAccountLock) {
		this.isAccountLock = isAccountLock;
	}

	public int getPsFailCount() {
		return psFailCount;
	}

	public void setPsFailCnt(int psFailCount) {
		this.psFailCount = psFailCount;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}


}
