package pep.per.mint.common.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RecoveryFieldSet extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8379952296885141730L;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private Object messageSetId;
	
	private List<Map<String,Object>> fieldPathList = new LinkedList<Map<String,Object>>();
	
	//private List<RecoveryFieldSetDetail> fieldIdList = new LinkedList<RecoveryFieldSetDetail>();
	
	//private LinkedHashMap<Object, RecoveryFieldSetDetail> fieldIdList = new LinkedHashMap<Object, RecoveryFieldSetDetail>();
	
	@JsonProperty
	private String regId;
	
	@JsonProperty
	private String regDate;
	
	@JsonProperty
	private String modId;
	
	@JsonProperty
	private String modDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Object getMessageSetId() {
		return messageSetId;
	}
	
	public void setMessageSetId(Object messageSetId) {
		this.messageSetId = messageSetId;
	}

	public List<Map<String,Object>> getFieldPathList() {
		return fieldPathList;
	}

	public void setFieldPathList(List<Map<String,Object>> fieldPathList) {
		this.fieldPathList = fieldPathList;
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
