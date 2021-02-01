/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * <pre>
 * pep.per.mint.common.data.basic.status
 * SystemStatus.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 16.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SystemStatus extends CacheableObject{

	@JsonProperty
	String systemId;

	@JsonProperty
	String agentStatus;

	@JsonProperty
	String qmgrStatus;

	@JsonProperty
	String channelStatus;

	@JsonProperty
	String queueStatus;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getQmgrStatus() {
		return qmgrStatus;
	}

	public void setQmgrStatus(String qmgrStatus) {
		this.qmgrStatus = qmgrStatus;
	}

	public String getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(String channelStatus) {
		this.channelStatus = channelStatus;
	}

	public String getQueueStatus() {
		return queueStatus;
	}

	public void setQueueStatus(String queueStatus) {
		this.queueStatus = queueStatus;
	}



}
