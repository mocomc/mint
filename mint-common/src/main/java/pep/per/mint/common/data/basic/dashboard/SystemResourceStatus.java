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
public class SystemResourceStatus extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1029303235582455860L;

	
	
	@JsonProperty("systemIp")
	String systemIp;
	
	@JsonProperty("systemNm")
	String systemNm;
	
	@JsonProperty("cpuUsage")
	String cpuUsage;
	
	@JsonProperty("memoryUsage")
	String memoryUsage;
	
	@JsonProperty("diskUsage")
	String diskUsage;
	
	@JsonProperty("diskName")
	String diskName;
	
	@JsonProperty("dateTime")
	String dateTime;

	
	
	
	/**
	 * @return the systemIp
	 */
	public String getSystemIp() {
		return systemIp;
	}

	/**
	 * @param systemIp the systemIp to set
	 */
	public void setSystemIp(String systemIp) {
		this.systemIp = systemIp;
	}

	/**
	 * @return the systemNm
	 */
	public String getSystemNm() {
		return systemNm;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	/**
	 * @return the cpuUsage
	 */
	public String getCpuUsage() {
		return cpuUsage;
	}

	/**
	 * @param cpuUsage the cpuUsage to set
	 */
	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	/**
	 * @return the memoryUsage
	 */
	public String getMemoryUsage() {
		return memoryUsage;
	}

	/**
	 * @param memoryUsage the memoryUsage to set
	 */
	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	/**
	 * @return the diskUsage
	 */
	public String getDiskUsage() {
		return diskUsage;
	}

	/**
	 * @param diskUsage the diskUsage to set
	 */
	public void setDiskUsage(String diskUsage) {
		this.diskUsage = diskUsage;
	}

	/**
	 * @return the diskName
	 */
	public String getDiskName() {
		return diskName;
	}

	/**
	 * @param diskName the diskName to set
	 */
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
}
