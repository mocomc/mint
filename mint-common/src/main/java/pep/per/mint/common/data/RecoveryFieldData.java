package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RecoveryFieldData extends CacheableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5830407947230673582L;

	@JsonProperty
	private String recoveryKey;
	
	@JsonProperty
	private Object fieldSetId;
	
	@JsonProperty
	private String fieldPath;
	
	@JsonProperty
	private Object fieldValue;
	
	@JsonProperty
	private String regDate;

	public String getRecoveryKey() {
		return recoveryKey;
	}
	
	public void setRecoveryKey(String recoveryKey) {
		this.recoveryKey = recoveryKey;
	}
	
	public Object getFieldSetId() {
		return fieldSetId;
	}
	public void setFieldSetId(Object fieldSetId) {
		this.fieldSetId = fieldSetId;
	}
	public String getFieldPath() {
		return fieldPath;
	}
	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
