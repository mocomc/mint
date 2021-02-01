package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SystemField extends CacheableObject{

	public static final int FIELD_USAGE_INTERFACE_ID = 0;
	public static final int FIELD_USAGE_GLOBAL_ID = 1;
	public static final int FIELD_USAGE_RECOVERY_KEY = 2;
	public static final int FIELD_USAGE_INTERFACE_MAP_KEY = 3;
	public static final int FIELD_USAGE_SEND_TYPE = 4;
	public static final int FIELD_USAGE_SEND_TIME = 5;
	public static final int FIELD_USAGE_SENDER_ID = 6;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6472203339428136316L;

	@JsonProperty
	Object messageSetId;
	
	@JsonProperty
	String fieldPathString;
	
	@JsonProperty
	int usage;
	
	@JsonProperty
	String name;

	@JsonProperty
	FieldPath fieldPath;

	@JsonProperty
	int seq;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	public SystemField(){
		super();
	}
	
	public SystemField(String fieldPathString) {
		this();
		fieldPath = new FieldPath(fieldPathString);
	}
	
	/**
	 * @return the messageSetId
	 */
	public Object getMessageSetId() {
		return messageSetId;
	}

	/**
	 * @param messageSetId the messageSetId to set
	 */
	public void setMessageSetId(Object messageSetId) {
		this.messageSetId = messageSetId;
	}

	/**
	 * @return the fieldPathString
	 */
	public String getFieldPathString() {
		return fieldPathString;
	}

	/**
	 * @param fieldPathString the fieldPath to set
	 */
	public void setFieldPathString(String fieldPathString) {
		this.fieldPathString = fieldPathString;
	}

	
	
	/**
	 * @return the fieldPath
	 */
	public FieldPath getFieldPath() {
		return fieldPath;
	}

	/**
	 * @param fieldPath the fieldPath to set
	 */
	public void setFieldPath(FieldPath fieldPath) {
		this.fieldPath = fieldPath;
	}

	/**
	 * @return the usage
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(int usage) {
		this.usage = usage;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
