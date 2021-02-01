package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ParseSystemFieldRule extends Rule {


	/**
	 * 
	 */
	private static final long serialVersionUID = 110275766351018894L;
	
	@JsonProperty
	Object messageSetId;
	
	@JsonProperty
	boolean hasInterfaceMapping = false;
 
	public ParseSystemFieldRule(){
		super();
	}
	
	public Object getMessageSetId() {
		return messageSetId;
	}

	public void setMessageSetId(Object messageSetId) {
		this.messageSetId = messageSetId;
	}

	public boolean isHasInterfaceMapping() {
		return hasInterfaceMapping;
	}

	public void setHasInterfaceMapping(boolean hasInterfaceMapping) {
		this.hasInterfaceMapping = hasInterfaceMapping;
	}
	
	
}
