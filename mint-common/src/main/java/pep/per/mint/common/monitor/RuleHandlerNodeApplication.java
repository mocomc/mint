package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RuleHandlerNodeApplication extends MonitorApplication {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2038219901423774987L;

	@JsonProperty
	public String brokerName;
	
	@JsonProperty
	public String dataFlowEngineName;
	
	@JsonProperty
	public String messageFlowName;
	
	public RuleHandlerNodeApplication(){
		super();
	}
	
	public RuleHandlerNodeApplication(String id, String name) {
		super(id, name);
	}
 
	
	/**
	 * @return the brokerName
	 */
	public String getBrokerName() {
		return brokerName;
	}

	/**
	 * @param brokerName the brokerName to set
	 */
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	/**
	 * @return the dataFlowEngineName
	 */
	public String getDataFlowEngineName() {
		return dataFlowEngineName;
	}

	/**
	 * @param dataFlowEngineName the dataFlowEngineName to set
	 */
	public void setDataFlowEngineName(String dataFlowEngineName) {
		this.dataFlowEngineName = dataFlowEngineName;
	}

	/**
	 * @return the messageFlowName
	 */
	public String getMessageFlowName() {
		return messageFlowName;
	}

	/**
	 * @param messageFlowName the messageFlowName to set
	 */
	public void setMessageFlowName(String messageFlowName) {
		this.messageFlowName = messageFlowName;
	}
	
	
}
