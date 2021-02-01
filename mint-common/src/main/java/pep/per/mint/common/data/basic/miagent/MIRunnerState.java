package pep.per.mint.common.data.basic.miagent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MIRunnerState extends CacheableObject {

	public final static String STAT_NOT_READY = "0";
	public final static String STAT_ONLINE = "1";
	public final static String STAT_OFFLINE = "9";

	@JsonProperty
	String agentId = defaultStringValue;

	@JsonProperty
	String runnerId = defaultStringValue;

	@JsonProperty
	String getDate  = defaultStringValue;

	@JsonProperty
	String status  = defaultStringValue;

	@JsonProperty
	String msg  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regApp  = defaultStringValue;


	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}



	public String getRunnerId() {
		return runnerId;
	}

	public void setRunnerId(String runnerId) {
		this.runnerId = runnerId;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegApp() {
		return regApp;
	}

	public void setRegApp(String regApp) {
		this.regApp = regApp;
	}



}
