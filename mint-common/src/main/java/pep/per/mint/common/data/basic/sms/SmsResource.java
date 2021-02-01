package pep.per.mint.common.data.basic.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SmsResource extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4315939588990520162L;


	@JsonProperty
	String smsId = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String key1 = defaultStringValue;

	@JsonProperty
	String key2 = defaultStringValue;
	
	@JsonProperty
	String key3 = defaultStringValue;

	@JsonProperty
	String interfaceId = defaultStringValue;

	@JsonProperty
	String integrationId = defaultStringValue;

	@JsonProperty
	String interfaceNm = defaultStringValue;



	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	
	
	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}
	
	


}
