package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ChannelStatusLog extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3286473376836128201L;

	public final static String STATUS_NORMAL = "0";
	public final static String STATUS_ABNORMAL = "9";


	public final static String ALERT_NOT_SEND = "0";
	public final static String ALERT_SEND 	= "1";

	@JsonProperty
	ChannelInfo channelInfo;


	@JsonProperty
	String qmgrId  = defaultStringValue;

	@JsonProperty
	String channelId  = defaultStringValue;

	@JsonProperty
	String getDate  = defaultStringValue;

	@JsonProperty
	String serverId  = defaultStringValue;

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


	public String getQmgrId() {
		return qmgrId;
	}

	public void setQmgrId(String qmgrId) {
		this.qmgrId = qmgrId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
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

	public ChannelInfo getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(ChannelInfo channelInfo) {
		this.channelInfo = channelInfo;
	}



}
