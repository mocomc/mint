package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ThroughputEvent<T> extends MonitorEvent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -189809133894079622L;
	@JsonProperty
	long elapsed;
	
	public ThroughputEvent(){
		super();
	}
	
	public ThroughputEvent(String eventKey, MonitorApplication source, MonitorData<T> data, long eventTime) {
		super(eventKey, source, data, eventTime);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the elapsed
	 */
	public long getElapsed() {
		return elapsed;
	}

	/**
	 * @param elapsed the elapsed to set
	 */
	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}
	
	

}
