package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TaskApplication extends MonitorApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2584476436130027433L;

	@JsonProperty
	String serviceGroupId;
	
	@JsonProperty
	String serviceId;
	
	public TaskApplication(){
		super();
	}
	
	public TaskApplication(String id, String name) {
		super(id, name);
	}
	
	public TaskApplication(String id, String name, String serviceId, String serviceGroupId) {
		this(id, name);
		this.serviceId = serviceId;
		this.serviceGroupId = serviceGroupId;
	}

	/**
	 * @return the serviceGroupId
	 */
	public String getServiceGroupId() {
		return serviceGroupId;
	}

	/**
	 * @param serviceGroupId the serviceGroupId to set
	 */
	public void setServiceGroupId(String serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	
	

}
