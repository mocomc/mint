package pep.per.mint.common.data.broker;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.RuleSet;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ServiceInfo extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3877202489952197455L;

	
	public final static int COMMIT_AUTO = 0;
	public final static int COMMIT_TRANSACTION = 1;
	public final static int COMMIT_NOW = 2;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private int commit = COMMIT_TRANSACTION;
	
	@JsonProperty
	private long serviceDelay;
	
	@JsonProperty
	private long exceptionDelay;
	
	@JsonProperty
    private int additionalThreadCount;
	
	@JsonProperty
	private String ruleSetId;
	
	@JsonProperty
	private RuleSet ruleSet;
	
	@JsonProperty
	private boolean isPersistent = true;
	
	@JsonProperty
	private List<TaskInfo> inputTaskInfoList;
	
	
	@JsonProperty
	String description;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the commit
	 */
	public int getCommit() {
		return commit;
	}
	/**
	 * @param commit the commit to set
	 */
	public void setCommit(int commit) {
		this.commit = commit;
	}
	/**
	 * @return the serviceDelay
	 */
	public long getServiceDelay() {
		return serviceDelay;
	}
	/**
	 * @param serviceDelay the serviceDelay to set
	 */
	public void setServiceDelay(long serviceDelay) {
		this.serviceDelay = serviceDelay;
	}
	
	
	
	/**
	 * @return the exceptionDelay
	 */
	public long getExceptionDelay() {
		return exceptionDelay;
	}
	/**
	 * @param exceptionDelay the exceptionDelay to set
	 */
	public void setExceptionDelay(long exceptionDelay) {
		this.exceptionDelay = exceptionDelay;
	}
	/**
	 * @return the additionalThreadCount
	 */
	public int getAdditionalThreadCount() {
		return additionalThreadCount;
	}
	
	/**
	 * @param additionalThreadCount the additionalThreadCount to set
	 */
	public void setAdditionalThreadCount(int additionalThreadCount) {
		this.additionalThreadCount = additionalThreadCount;
	}
	
	 
	
	
	/**
	 * @return the ruleSetId
	 */
	public String getRuleSetId() {
		return ruleSetId;
	}
	/**
	 * @param ruleSetId the ruleSetId to set
	 */
	public void setRuleSetId(String ruleSetId) {
		this.ruleSetId = ruleSetId;
	}
	
	
	/**
	 * @return the ruleSet
	 */
	public RuleSet getRuleSet() {
		return ruleSet;
	}
	/**
	 * @param ruleSet the ruleSet to set
	 */
	public void setRuleSet(RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}
	

	
	public boolean getIsPersistent() {
		return isPersistent;
	}

	
	 
	/**
	 * @param persistent the persistent to set
	 */
	public void setIsPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}
	
	
	
	/**
	 * @return the inputTaskInfoList
	 */
	public List<TaskInfo> getInputTaskInfoList() {
		return inputTaskInfoList;
	}
	
	/**
	 * @param inputTaskInfoList the inputTaskInfoList to set
	 */
	public void setInputTaskInfoList(List<TaskInfo> inputTaskInfoList) {
		this.inputTaskInfoList = inputTaskInfoList;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}
	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}
	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	
	
	
}
