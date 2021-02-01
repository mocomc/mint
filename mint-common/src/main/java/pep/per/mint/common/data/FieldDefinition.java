/**
 * 
 */
package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author mint
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value=FixedLengthFieldDefinition.class),
    @JsonSubTypes.Type(value=XMLFieldDefinition.class),
    @JsonSubTypes.Type(value=DelimiterFieldDefinition.class),
    @JsonSubTypes.Type(value=JsonFieldDefinition.class),
    @JsonSubTypes.Type(value=FieldSetDefinition.class)
})
public class FieldDefinition extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3396610574623559818L;
	
	/*public static final int FIELD_USAGE_INTERFACE_ID = 0;
	public static final int FIELD_USAGE_GID = 1;
	public static final int FIELD_USAGE_RECOVERY_KEY = 2;
	public static final int FIELD_USAGE_INTERFACE_MAP_KEY = 3;
	public static final int FIELD_USAGE_SEND_TYPE = 4;
	public static final int FIELD_USAGE_SENDER_ID = 5;
	public static final int FIELD_USAGE_NORMAL = 99;*/
	
	public final static int FIELD_TYPE_STRING = 0;
	public final static int FIELD_TYPE_STR_SHORT = 1;
	public final static int FIELD_TYPE_STR_INTEGER = 2;
	public final static int FIELD_TYPE_STR_LONG = 3;
	public final static int FIELD_TYPE_STR_FLOAT = 4;
	public final static int FIELD_TYPE_STR_DOUBLE = 5;
	public final static int FIELD_TYPE_BIN_SHORT = 6;
	public final static int FIELD_TYPE_BIN_INTEGER = 7;
	public final static int FIELD_TYPE_BIN_LONG = 8;
	public final static int FIELD_TYPE_BIN_FLOAT = 9;
	public final static int FIELD_TYPE_BIN_DOUBLE = 10;
	public final static int FIELD_TYPE_BOOLEAN = 11;
	public final static int FIELD_TYPE_BINARY = 12;
	
	public final static int FIELD_JUSTIFY_LEFT = 0;
	public final static int FIELD_JUSTIFY_RIGHT = 1;
	
	
	public final static int OCCURS_TYPE_11 = 0;//필수 1번 발생
	public final static int OCCURS_TYPE_01 = 1;//옵션 0또는 1번 발생
	public final static int OCCURS_TYPE_0N = 2;//0또는 1번 이상 발생
	public final static int OCCURS_TYPE_1N = 3;//1번 이상 발생
	
	@JsonProperty
	String name;
	
	@JsonProperty
	int seq;
	
	@JsonProperty
	boolean required = true;
	
	
	@JsonProperty
	FieldPath fieldPath;
	
	@JsonProperty
	String repeatFieldName;
	
	@JsonProperty
	int repeatCount = 0;
	
	@JsonProperty
	boolean isFieldSet = false;
	
	@JsonProperty
	String name2;
	
	@JsonProperty
	int type;	
	
	@JsonProperty
	int justify;
	
	@JsonProperty
	Object defaultValue;
	
	@JsonProperty
	Object paddingValue;

	@JsonProperty
	int occursType = OCCURS_TYPE_11;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	 * @return the repeatFieldId
	 */
	public String getRepeatFieldName() {
		return repeatFieldName;
	}

	/**
	 * @param repeatFieldId the repeatFieldId to set
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
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2 the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the justify
	 */
	public int getJustify() {
		return justify;
	}

	/**
	 * @param justify the justify to set
	 */
	public void setJustify(int justify) {
		this.justify = justify;
	}

	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the paddingValue
	 */
	public Object getPaddingValue() {
		return paddingValue;
	}

	/**
	 * @param paddingValue the paddingValue to set
	 */
	public void setPaddingValue(Object paddingValue) {
		this.paddingValue = paddingValue;
	}

	
	
	
	/**
	 * @return the occursType
	 */
	public int getOccursType() {
		return occursType;
	}

	/**
	 * @param occursType the occursType to set
	 */
	public void setOccursType(int occursType) {
		this.occursType = occursType;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
