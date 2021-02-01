package pep.per.mint.common.data.basic.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class EmailResourceForTracking extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4315939588990520162L;


	@JsonProperty
	String emailId = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String key1 = defaultStringValue;

	@JsonProperty
	String key2 = defaultStringValue;

	@JsonProperty
	String interfaceId = defaultStringValue;

	@JsonProperty
	String integrationId = defaultStringValue;

	@JsonProperty
	String interfaceNm = defaultStringValue;



	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
