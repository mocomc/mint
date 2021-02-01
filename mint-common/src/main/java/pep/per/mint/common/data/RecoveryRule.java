package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RecoveryRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1656488685897152952L;
	
	@JsonProperty
	boolean isSessionRecovery  = false;
	
	@JsonProperty
	boolean isDataRecovery = false;
	
	@JsonProperty
	boolean isReqServerRecovery = false;
	
	@JsonProperty
	Object fieldSetId;

	public boolean isSessionRecovery() {
		return isSessionRecovery;
	}

	public void setSessionRecovery(boolean isSessionRecovery) {
		this.isSessionRecovery = isSessionRecovery;
	}

	public boolean isDataRecovery() {
		return isDataRecovery;
	}

	public void setDataRecovery(boolean isDataRecovery) {
		this.isDataRecovery = isDataRecovery;
	}

	public boolean isReqServerRecovery() {
		return isReqServerRecovery;
	}

	public void setReqServerRecovery(boolean isReqServerRecovery) {
		this.isReqServerRecovery = isReqServerRecovery;
	}

	public Object getFieldSetId() {
		return fieldSetId;
	}

	public void setFieldSetId(Object fieldSetId) {
		this.fieldSetId = fieldSetId;
	}
	

}
