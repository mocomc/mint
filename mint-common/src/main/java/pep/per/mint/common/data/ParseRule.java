package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ParseRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4832779183842420895L;

	@JsonProperty
	Object messageSetId;
	
	@JsonProperty
	String mapPoint;

	public Object getMessageSetId() {
		return messageSetId;
	}

	public void setMessageSetId(Object messageSetId) {
		this.messageSetId = messageSetId;
	}

	public String getMapPoint() {
		return mapPoint;
	}

	public void setMapPoint(String mapPoint) {
		this.mapPoint = mapPoint;
	}	
	
}
