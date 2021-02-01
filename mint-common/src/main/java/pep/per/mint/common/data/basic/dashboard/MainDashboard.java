package pep.per.mint.common.data.basic.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;


/**
 * 메인대시보드 Data Object
 * 
 * @author isjang
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MainDashboard  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8551100007929529255L;

	@JsonProperty("checkTimestamp")
	String checkTimestamp;
	
	@JsonProperty("dailyThroughput")
	DailyThroughput dailyThroughput;
	
	@JsonProperty("delayInterfaceList")
	List<DelayInterface> delayInterfaceList;
	
	@JsonProperty("errorInterfaceListCount")
	int errorInterfaceListCount;
	
	@JsonProperty("errorInterfaceList")
	List<ErrorInterface> errorInterfaceList;
	
	@JsonProperty("errorEAIComponentList")
	List<ErrorEAIComponent> errorEAIComponentList;
	
	@JsonProperty("systemResourceStatus")
	List<SystemResourceStatus> systemResourceStatusList;
	
	@JsonProperty("systemDiskStatus")
	List<SystemResourceStatus> systemDiskStatusList;
	
	@JsonProperty("solutionsStatusList")
	List<SolutionsStatus> solutionsStatusList;
	
	@JsonProperty("solutionsStatusListFromDateTime")
	String solutionsStatusListFromDateTime;
	
	@JsonProperty("solutionsStatusListToDateTime")
	String solutionsStatusListToDateTime;
	
	@JsonProperty("interfaceElapsedTimeStatusList")
	List<InterfaceElapsedTimeStatus> interfaceElapsedTimeStatusList;
	
	@JsonProperty("interfaceElapsedTimeStatusListFromDateTime")
	String interfaceElapsedTimeStatusListFromDateTime;
	
	@JsonProperty("interfaceElapsedTimeStatusListToDateTime")
	String interfaceElapsedTimeStatusListToDateTime;
	
	
	@JsonProperty("problemLedger")
	List<ProblemLedger> problemLedger;
	
	/**
	 * @return the dailyThroughput
	 */
	public DailyThroughput getDailyThroughput() {
		return dailyThroughput;
	}

	/**
	 * @param dailyThroughput the dailyThroughput to set
	 */
	public void setDailyThroughput(DailyThroughput dailyThroughput) {
		this.dailyThroughput = dailyThroughput;
	}

	/**
	 * @return the checkTimestamp
	 */
	public String getCheckTimestamp() {
		return checkTimestamp;
	}

	/**
	 * @param checkTimestamp the checkTimestamp to set
	 */
	public void setCheckTimestamp(String checkTimestamp) {
		this.checkTimestamp = checkTimestamp;
	}

	/**
	 * @return the delayInterfaceList
	 */
	public List<DelayInterface> getDelayInterfaceList() {
		return delayInterfaceList;
	}

	/**
	 * @param delayInterfaceList the delayInterfaceList to set
	 */
	public void setDelayInterfaceList(List<DelayInterface> delayInterfaceList) {
		this.delayInterfaceList = delayInterfaceList;
	}

	/**
	 * @return the errorInterfaceListCount
	 */
	public int getErrorInterfaceListCount() {
		return errorInterfaceListCount;
	}

	/**
	 * @param errorInterfaceListCount the errorInterfaceListCount to set
	 */
	public void setErrorInterfaceListCount(int errorInterfaceListCount) {
		this.errorInterfaceListCount = errorInterfaceListCount;
	}

	/**
	 * @return the errorInterfaceList
	 */
	public List<ErrorInterface> getErrorInterfaceList() {
		return errorInterfaceList;
	}

	/**
	 * @param errorInterfaceList the errorInterfaceList to set
	 */
	public void setErrorInterfaceList(List<ErrorInterface> errorInterfaceList) {
		this.errorInterfaceList = errorInterfaceList;
	}

	/**
	 * @return the errorEAIComponentList
	 */
	public List<ErrorEAIComponent> getErrorEAIComponentList() {
		return errorEAIComponentList;
	}

	/**
	 * @param errorEAIComponentList the errorEAIComponentList to set
	 */
	public void setErrorEAIComponentList(List<ErrorEAIComponent> errorEAIComponentList) {
		this.errorEAIComponentList = errorEAIComponentList;
	}

	/**
	 * @return the systemResourceStatusList
	 */
	public List<SystemResourceStatus> getSystemResourceStatusList() {
		return systemResourceStatusList;
	}

	/**
	 * @param systemResourceStatusList the systemResourceStatusList to set
	 */
	public void setSystemResourceStatusList(List<SystemResourceStatus> systemResourceStatusList) {
		this.systemResourceStatusList = systemResourceStatusList;
	}

	/**
	 * @return the systemDiskStatusList
	 */
	public List<SystemResourceStatus> getSystemDiskStatusList() {
		return systemDiskStatusList;
	}

	/**
	 * @param systemDiskStatusList the systemDiskStatusList to set
	 */
	public void setSystemDiskStatusList(List<SystemResourceStatus> systemDiskStatusList) {
		this.systemDiskStatusList = systemDiskStatusList;
	}

	/**
	 * @return the solutionsStatusList
	 */
	public List<SolutionsStatus> getSolutionsStatusList() {
		return solutionsStatusList;
	}

	/**
	 * @param solutionsStatusList the solutionsStatusList to set
	 */
	public void setSolutionsStatusList(List<SolutionsStatus> solutionsStatusList) {
		this.solutionsStatusList = solutionsStatusList;
	}

	/**
	 * @return the solutionsStatusListFromDateTime
	 */
	public String getSolutionsStatusListFromDateTime() {
		return solutionsStatusListFromDateTime;
	}

	/**
	 * @param solutionsStatusListFromDateTime the solutionsStatusListFromDateTime to set
	 */
	public void setSolutionsStatusListFromDateTime(String solutionsStatusListFromDateTime) {
		this.solutionsStatusListFromDateTime = solutionsStatusListFromDateTime;
	}

	/**
	 * @return the solutionsStatusListToDateTime
	 */
	public String getSolutionsStatusListToDateTime() {
		return solutionsStatusListToDateTime;
	}

	/**
	 * @param solutionsStatusListToDateTime the solutionsStatusListToDateTime to set
	 */
	public void setSolutionsStatusListToDateTime(String solutionsStatusListToDateTime) {
		this.solutionsStatusListToDateTime = solutionsStatusListToDateTime;
	}

	/**
	 * @return the interfaceElapsedTimeStatusList
	 */
	public List<InterfaceElapsedTimeStatus> getInterfaceElapsedTimeStatusList() {
		return interfaceElapsedTimeStatusList;
	}

	/**
	 * @param interfaceElapsedTimeStatusList the interfaceElapsedTimeStatusList to set
	 */
	public void setInterfaceElapsedTimeStatusList(List<InterfaceElapsedTimeStatus> interfaceElapsedTimeStatusList) {
		this.interfaceElapsedTimeStatusList = interfaceElapsedTimeStatusList;
	}

	/**
	 * @return the interfaceElapsedTimeStatusListFromDateTime
	 */
	public String getInterfaceElapsedTimeStatusListFromDateTime() {
		return interfaceElapsedTimeStatusListFromDateTime;
	}

	/**
	 * @param interfaceElapsedTimeStatusListFromDateTime the interfaceElapsedTimeStatusListFromDateTime to set
	 */
	public void setInterfaceElapsedTimeStatusListFromDateTime(String interfaceElapsedTimeStatusListFromDateTime) {
		this.interfaceElapsedTimeStatusListFromDateTime = interfaceElapsedTimeStatusListFromDateTime;
	}

	/**
	 * @return the interfaceElapsedTimeStatusListToDateTime
	 */
	public String getInterfaceElapsedTimeStatusListToDateTime() {
		return interfaceElapsedTimeStatusListToDateTime;
	}

	/**
	 * @param interfaceElapsedTimeStatusListToDateTime the interfaceElapsedTimeStatusListToDateTime to set
	 */
	public void setInterfaceElapsedTimeStatusListToDateTime(String interfaceElapsedTimeStatusListToDateTime) {
		this.interfaceElapsedTimeStatusListToDateTime = interfaceElapsedTimeStatusListToDateTime;
	}

	/**
	 * @return the problemManagement
	 */
	public List<ProblemLedger> getProblemLedger() {
		return problemLedger;
	}

	/**
	 * @param ProblemLedger the ProblemLedger to set
	 */
	public void setProblemLedger(List<ProblemLedger> problemLedger) {
		this.problemLedger = problemLedger;
	}

	
}
