package pep.per.mint.common.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FieldSetDetail{

 
	//MSGSET_ID
	@JsonProperty
	Object fieldSetId;
	
	//FIELD_ID
	@JsonProperty
	Object fieldId;
	
	//SEQ
	@JsonProperty
	int seq;
	
	//FIELDSET_YN
	@JsonProperty
	boolean isFieldSet = false;

	@JsonProperty
	boolean required = true;
	
	@JsonProperty
	String lengthFieldName;
	
	@JsonProperty
	String repeatFieldName;
	
	@JsonProperty
	int repeatCount = 0;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;


	/**
	 * @return the fieldSetId
	 */
	public Object getFieldSetId() {
		return fieldSetId;
	}

	/**
	 * @param fieldSetId the fieldSetId to set
	 */
	public void setFieldSetId(Object fieldSetId) {
		this.fieldSetId = fieldSetId;
	}

	/**
	 * @return the fieldId
	 */
	public Object getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(Object fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the isFieldSet
	 */
	public boolean isFieldSet() {
		return isFieldSet;
	}

	/**
	 * @param isFieldSet the isFieldSet to set
	 */
	public void setFieldSet(boolean isFieldSet) {
		this.isFieldSet = isFieldSet;
	}

	
	
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	
	
	
	/**
	 * @return the lengthFieldName
	 */
	public String getLengthFieldName() {
		return lengthFieldName;
	}

	/**
	 * @param lengthFieldName the lengthFieldName to set
	 */
	public void setLengthFieldName(String lengthFieldName) {
		this.lengthFieldName = lengthFieldName;
	}

	/**
	 * @return the repeatFieldName
	 */
	public String getRepeatFieldName() {
		return repeatFieldName;
	}

	/**
	 * @param repeatFieldName the repeatFieldName to set
	 */
	public void setRepeatFieldName(String repeatFieldName) {
		this.repeatFieldName = repeatFieldName;
	}

	/**
	 * @return the repeatCount
	 */
	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * @param repeatCount the repeatCount to set
	 */
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
	 
	
	
}
