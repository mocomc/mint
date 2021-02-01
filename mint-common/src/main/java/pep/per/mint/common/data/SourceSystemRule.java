package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SourceSystemRule extends Rule {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 263518372316443764L;
	
	@JsonProperty
	Object sourceSystemId;
	
	public SourceSystemRule() {
		super();
	}
	
	public Object getSourceSystemId() {
		return sourceSystemId;
	}
	
	public void setSourceSystemId(Object sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}
	
	

}
