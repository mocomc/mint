package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ServiceApplication extends MonitorApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865923372607640359L;
	@JsonProperty
	String serviceGroupId;
	
	public ServiceApplication(){
		super();
	}
	
	public ServiceApplication(String serviceId, String name) {
		super(serviceId, name);
	}
	
	public ServiceApplication(String serviceId, String name, String serviceGroupId) {
		this(serviceId, name);
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

}
