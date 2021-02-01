package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class QmgrStatusLog extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -903428443449731723L;
	public final static String STATUS_NORMAL = "0";
	public final static String STATUS_ABNORMAL = "9";

	public final static String ALERT_NOT_SEND = "0";
	public final static String ALERT_SEND 	= "1";

	@JsonProperty
	QmgrInfo qmgrInfo;

	@JsonProperty
	String qmgrId  = defaultStringValue;

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

	public QmgrInfo getQmgrInfo() {
		return qmgrInfo;
	}

	public void setQmgrInfo(QmgrInfo qmgrInfo) {
		this.qmgrInfo = qmgrInfo;
	}

	public String getQmgrId() {
		return qmgrId;
	}

	public void setQmgrId(String qmgrId) {
		this.qmgrId = qmgrId;
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

}
