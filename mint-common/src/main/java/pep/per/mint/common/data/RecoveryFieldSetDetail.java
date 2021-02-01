package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RecoveryFieldSetDetail extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7919477167147625476L;
	
	@JsonProperty
	private Object fieldSetId;
	
	@JsonProperty
	private String fieldPath;
	
	@JsonProperty
	private String fieldType;
	
	@JsonProperty
	private int seq;
	
	@JsonProperty
	private String regId;
	
	@JsonProperty
	private String regDate;
	
	@JsonProperty
	private String modId;
	
	@JsonProperty
	private String modDate;

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

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	



}
