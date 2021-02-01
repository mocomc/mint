package pep.per.mint.common.data.basic.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class LoginHistory extends CacheableObject{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@JsonProperty
	String userId = defaultStringValue;
	
	@JsonProperty
	String sessionId = defaultStringValue;
	
	@JsonProperty
	String loginDate = defaultStringValue;
	
	@JsonProperty
	String logoutDate = defaultStringValue;
	
	@JsonProperty
	String ip = defaultStringValue;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}	
}
