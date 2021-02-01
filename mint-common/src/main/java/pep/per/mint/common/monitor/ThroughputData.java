package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ThroughputData<T> extends MonitorData<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6470681456070741757L;

	public ThroughputData(){
		super();
	}
	
	
	
}
