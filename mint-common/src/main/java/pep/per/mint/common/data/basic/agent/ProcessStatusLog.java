package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ProcessStatusLog extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6137338510383186738L;


	public static final String Abnormal = "9";
	public static final String Normal = "0";

	public final static String ALERT_NOT_SEND = "0";
	public final static String ALERT_SEND 	= "1";

	@JsonProperty
	ProcessInfo processInfo;

	@JsonProperty
	String serverId  = defaultStringValue;

	@JsonProperty
	String processId  = defaultStringValue;

	@JsonProperty
	String processNo  = defaultStringValue;

	@JsonProperty
	String getDate  = defaultStringValue;

	@JsonProperty
	int cnt = 1;

	@JsonProperty
	String status  = defaultStringValue;

	@JsonProperty
	String msg  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regApp  = defaultStringValue;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	String alertVal  = ALERT_SEND;



	public ProcessInfo getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(ProcessInfo processInfo) {
		this.processInfo = processInfo;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}



	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
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

	public String getAlertVal() {
		return alertVal;
	}

	public void setAlertVal(String alertVal) {
		this.alertVal = alertVal;
	}



}
