package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ResourceUsageLog extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -5185593088906770484L;

	public final static String STATUS_NORMAL = "0";
	public final static String STATUS_ABNORMAL = "9";

	public final static String ALERT_NOT_SEND = "0";
	public final static String ALERT_SEND 	= "1";

	@JsonProperty
	ResourceInfo resourceInfo;

	@JsonProperty
	String getDate = defaultStringValue;

	@JsonProperty
	String totalAmt = "0.0";

	@JsonProperty
	String usedAmt = "0.0";

	@JsonProperty
	String usedPer = "0.0";

	@JsonProperty
	String msg;

	@JsonProperty
	String regDate;

	@JsonProperty
	String regApp;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	String alertVal  = ALERT_SEND;

	public ResourceInfo getResourceInfo() {
		return resourceInfo;
	}

	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getUsedAmt() {
		return usedAmt;
	}

	public void setUsedAmt(String usedAmt) {
		this.usedAmt = usedAmt;
	}

	public String getUsedPer() {
		return usedPer;
	}

	public void setUsedPer(String usedPer) {
		this.usedPer = usedPer;
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
