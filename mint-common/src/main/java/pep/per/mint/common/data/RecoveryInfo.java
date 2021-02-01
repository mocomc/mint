package pep.per.mint.common.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RecoveryInfo extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1199259212920319680L;
	
	@JsonProperty
	private Object sessionId; //Session 저장/복원이 필요할 경우
	
	@JsonProperty
	private String senderId;  //원 요청서버정보 저장/복원이 필요할 경우
	
	@JsonProperty
	//private LinkedHashMap<String, RecoveryFieldData> fieldDataMap; //요청 메세지 저장/복원이 필요할 경우
	//private List<Map<String,Object>> fieldData = new LinkedList<Map<String,Object>>();
	private List<RecoveryFieldData> fieldData;
	
	private String regId;
	
	private String regDate;
	
	private String modId;
	
	private String modDate;
	
	public void addFieldData(List<RecoveryFieldData> fieldData) {
		this.fieldData = fieldData;
	}
	
	public List<RecoveryFieldData> getFieldData() { 
		return this.fieldData;
	}
	
	@JsonIgnore
	public String getFieldDataToString() {
		String str = "";
		if(fieldData == null || fieldData.isEmpty() ) {
			str = "";
		} else {
			for(RecoveryFieldData fd : fieldData) {
				str = str + fd.getFieldValue();
			}
		}
		return str;
	}
	@JsonIgnore
	public byte[] getFieldDataToBytes() {
		return getFieldDataToString().getBytes();
	}
	@JsonIgnore
	public String[] getFieldDataToFiledName() {
		String [] str = null;
		if(fieldData == null || fieldData.isEmpty() ) {
			str = null;
		} else {
			int idx = fieldData.size();
			str = new String[idx];
			int i = 0;
			for(RecoveryFieldData fd : fieldData) {
				str[i++] = fd.getFieldPath();
			}
		}
		return str;
	}

	public String getRecoveryKey() {
		return id.toString();
	}
	
	public void setRecoveryKey(String recoveryKey) {
		id = recoveryKey;
	}	

	public Object getSessionId() {
		return sessionId;
	}

	public void setSessionId(Object sessionId) {
		this.sessionId = sessionId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
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
