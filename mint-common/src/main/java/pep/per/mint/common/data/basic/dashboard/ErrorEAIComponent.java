/**
 * 
 */
package pep.per.mint.common.data.basic.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ErrorEAIComponent extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8812387335772048076L;
	

	@JsonProperty("hostId")
	String hostId;
	
	@JsonProperty("hostNm")
	String hostNm;
	
	@JsonProperty("hostIp")
	String hostIp;
	
	@JsonProperty("qmgrNm")
	String qmgrNm;
	
	@JsonProperty("objectNm")
	String objectNm;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("curDepth")
	String curDepth;
	
	@JsonProperty("item")
	String item;
	
	@JsonProperty("alertId")
	String alertId;
	
	@JsonProperty("monitorId")
	String monitorId;
	
	@JsonProperty("color")
	String color;

	
	
	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @param hostId the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/**
	 * @return the hostNm
	 */
	public String getHostNm() {
		return hostNm;
	}

	/**
	 * @param hostNm the hostNm to set
	 */
	public void setHostNm(String hostNm) {
		this.hostNm = hostNm;
	}

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/**
	 * @return the qmgrNm
	 */
	public String getQmgrNm() {
		return qmgrNm;
	}

	/**
	 * @param qmgrNm the qmgrNm to set
	 */
	public void setQmgrNm(String qmgrNm) {
		this.qmgrNm = qmgrNm;
	}

	/**
	 * @return the objectNm
	 */
	public String getObjectNm() {
		return objectNm;
	}

	/**
	 * @param objectNm the objectNm to set
	 */
	public void setObjectNm(String objectNm) {
		this.objectNm = objectNm;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the curDepth
	 */
	public String getCurDepth() {
		return curDepth;
	}

	/**
	 * @param curDepth the curDepth to set
	 */
	public void setCurDepth(String curDepth) {
		this.curDepth = curDepth;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the alertId
	 */
	public String getAlertId() {
		return alertId;
	}

	/**
	 * @param alertId the alertId to set
	 */
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	/**
	 * @return the monitorId
	 */
	public String getMonitorId() {
		return monitorId;
	}

	/**
	 * @param monitorId the monitorId to set
	 */
	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
}
