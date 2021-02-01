package pep.per.mint.common.monitor;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
 

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ApplicationEvent.class),
        @JsonSubTypes.Type(value=ThroughputEvent.class)
})
abstract public class MonitorEvent<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3199007250098481439L;

	@JsonProperty
	String eventKey;
	
	@JsonProperty
	MonitorApplication source;
	
	@JsonProperty
	MonitorData<T> data;
	
	@JsonProperty
	long eventTime;
	
	public MonitorEvent(){
		super();
	}
	
	public MonitorEvent(String eventKey, MonitorApplication source, MonitorData<T> data, long eventTime) {
		this.eventKey = eventKey;
		this.source = source;
		this.data = data;
		this.eventTime = eventTime;
	}

	/**
	 * @return the eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * @param eventKey the eventKey to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * @return the source
	 */
	public MonitorApplication getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(MonitorApplication source) {
		this.source = source;
	}

	/**
	 * @return the data
	 */
	public MonitorData<T> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(MonitorData<T> data) {
		this.data = data;
	}

	/**
	 * @return the eventTime
	 */
	public long getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}
	
	

}
