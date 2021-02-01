package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MonitorRule extends Rule{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181004704898981966L;
	
	@JsonProperty
	boolean monitorApplication = false;
	
	@JsonProperty
	boolean monitorApplicationThroughput = false;
	
	@JsonProperty
	boolean monitorInterface = false;
	
	@JsonProperty
	boolean monitorInterfaceThroughput = false;
	
	@JsonProperty
	boolean includeUserData  = false;
	
	@JsonProperty
	boolean includeException = false;
	
	@JsonProperty
	int monitorSampleTime = 1000;
	
	@JsonProperty
	Object broadcastServerId;
	
	public MonitorRule() {
		super();
	}

	public boolean monitorApplication() {
		return monitorApplication;
	}

	public void setMonitorApplication(boolean monitorApplication) {
		this.monitorApplication = monitorApplication;
	}

	public boolean monitorApplicationThroughput() {
		return monitorApplicationThroughput;
	}

	public void setMonitorApplicationThroughput(boolean monitorApplicationThroughput) {
		this.monitorApplicationThroughput = monitorApplicationThroughput;
	}

	public boolean monitorInterface() {
		return monitorInterface;
	}

	public void setMonitorInterface(boolean monitorInterface) {
		this.monitorInterface = monitorInterface;
	}

	public boolean monitorInterfaceThroughput() {
		return monitorInterfaceThroughput;
	}

	public void setMonitorInterfaceThroughput(boolean monitorInterfaceThroughput) {
		this.monitorInterfaceThroughput = monitorInterfaceThroughput;
	}

	public boolean includeUserData() {
		return includeUserData;
	}

	public void setIncludeUserData(boolean includeUserData) {
		this.includeUserData = includeUserData;
	}

	public boolean includeException() {
		return includeException;
	}

	public void setIncludeException(boolean includeException) {
		this.includeException = includeException;
	}

	public int getMonitorSampleTime() {
		return monitorSampleTime;
	}

	public void setMonitorSampleTime(int monitorSampleTime) {
		this.monitorSampleTime = monitorSampleTime;
	}

	public Object getBroadcastServerId() {
		return broadcastServerId;
	}

	public void setBroadcastServerId(Object broadcastServerId) {
		this.broadcastServerId = broadcastServerId;
	}
	
	

}
