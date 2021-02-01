package pep.per.mint.common.data.basic.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Recipient extends CacheableObject {



	/**
	 *
	 */
	private static final long serialVersionUID = 3785432996207341132L;

	/**
	 *{
	 *	"objectType" : "공통코드" ,
	 *	"name" : "이메일 수신자 유형",
	 *	"level1": "SU",
	 *	"level2": "04",
	 *	"cds" : [
	 *		{"cd":"0", "nm" : "인터페이스 에러 수신자"},
	 *		{"cd":"1", "nm" : "리소스 모니터 수신자"},
	 *		{"cd":"2", "nm" : "EAI 엔진 모니터 수신자"},
	 *		{"cd":"9", "nm" : "모든 수신자"}
	 *	]
	 *}
	*/
	public static final String TYPE_RCV_TRACKING = "0";
	public static final String TYPE_RCV_RESOURCE = "1";
	public static final String TYPE_RCV_EAI = "2";
	public static final String TYPE_RCV_ALL = "9";


	/**
	 *  EMAIL_ADDRESS  NOT NULL VARCHAR2(255)
	 *	RECIPIENT_TYPE NOT NULL VARCHAR2(255)
	 *	USER_ID                 VARCHAR2(100)
	 *	NICKNAME                VARCHAR2(100)
	 *	REG_DATE       NOT NULL VARCHAR2(17)
	 *	REG_USER       NOT NULL VARCHAR2(100)
	 *	MOD_DATE                VARCHAR2(17)
	 *	MOD_USER                VARCHAR2(100)
	 *	DEL_YN         NOT NULL VARCHAR2(1)
     *
	 */

	@JsonProperty
	String address = defaultStringValue;

	@JsonProperty
	String type = TYPE_RCV_TRACKING;

	@JsonProperty
	String userId = defaultStringValue;

	@JsonProperty
	String nickName = defaultStringValue;

	@JsonProperty
	String delYn = "N";

	@JsonProperty
	String regDate = defaultStringValue;

	@JsonProperty
	String regId = defaultStringValue;

	@JsonProperty
	String modDate = defaultStringValue;

	@JsonProperty
	String modId = defaultStringValue;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}


}
