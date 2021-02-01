package pep.per.mint.common.data.broker;

import java.util.LinkedHashMap;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TaskInfo extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1958577513713723018L;
	
	public final static int OPT_THREAD_CNT_TASK = 0;
	public final static int OPT_THREAD_CNT_SERVICE = 1;
	
	public final static int TYPE_NORMAL = 0;
	public final static int TYPE_INPUT = 1;
	public final static int TYPE_EVENT_DRIVEN_INPUT = 2;
	
	public final static int COMMIT_AUTO = 0;
	public final static int COMMIT_TRANSACTION = 1;
	public final static int COMMIT_NOW = 2;
	 
	@JsonProperty
	private String name;
	
	@JsonProperty
	private int type;
	
	@JsonProperty
	private String taskClass;
	
	@JsonProperty
	private int additionalThreadCountOption;

	@JsonProperty
	private int additionalThreadCount;
	
	@JsonProperty
	private int commit;
	
	@JsonProperty
	private Properties properties;
	
	@JsonProperty
	private LinkedHashMap<Object,OutputTerminalInfo> outputTerminalInfoMap;
	
	@JsonProperty
	private FailTerminalInfo failTerminalInfo;
	
	@JsonProperty
	private String listenerServiceId;
	
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
	 * @return the taskClass
	 */
	public String getTaskClass() {
		return taskClass;
	}

	/**
	 * @param taskClass the taskClass to set
	 */
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the additionalThreadCountOption
	 */
	public int getAdditionalThreadCountOption() {
		return additionalThreadCountOption;
	}

	/**
	 * @param additionalThreadCountOption the additionalThreadCountOption to set
	 */
	public void setAdditionalThreadCountOption(int additionalThreadCountOption) {
		this.additionalThreadCountOption = additionalThreadCountOption;
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
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	
	
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/*public InputTerminalInfo getInputTerminalInfo() {
		return inputTerminalInfo;
	}

	public void setInputTerminalInfo(InputTerminalInfo inputTerminalInfo) {
		this.inputTerminalInfo = inputTerminalInfo;
	}*/

	/**
	 * @return the outputTerminalInfoMap
	 */
	public LinkedHashMap<Object, OutputTerminalInfo> getOutputTerminalInfoMap() {
		return outputTerminalInfoMap;
	}

	/**
	 * @param outputTerminalInfoMap the outputTerminalInfoMap to set
	 */
	public void setOutputTerminalInfoMap(
			LinkedHashMap<Object, OutputTerminalInfo> outputTerminalInfoMap) {
		this.outputTerminalInfoMap = outputTerminalInfoMap;
	}

	/**
	 * @return the failTerminalInfo
	 */
	public FailTerminalInfo getFailTerminalInfo() {
		return failTerminalInfo;
	}

	/**
	 * @param failTerminalInfo the failTerminalInfo to set
	 */
	public void setFailTerminalInfo(FailTerminalInfo failTerminalInfo) {
		this.failTerminalInfo = failTerminalInfo;
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

	/**
	 * @return the listenerServiceId
	 */
	public String getListenerServiceId() {
		return listenerServiceId;
	}

	/**
	 * @param listenerServiceId the listenerServiceId to set
	 */
	public void setListenerServiceId(String listenerServiceId) {
		this.listenerServiceId = listenerServiceId;
	}

	 


}
