package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RouteRule extends Rule{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3236479736584784170L;
	
	@JsonProperty
	Object targetSystemId;

	public RouteRule(){
		super();
	}
	
	public Object getTargetSystemId() {
		return targetSystemId;
	}

	public void setTargetSystemId(Object targetSystemId) {
		this.targetSystemId = targetSystemId;
	}
	
	

}
