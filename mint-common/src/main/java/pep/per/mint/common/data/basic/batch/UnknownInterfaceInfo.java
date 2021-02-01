package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class UnknownInterfaceInfo extends CacheableObject {

	@JsonProperty
	private String checkDate = "";
	@JsonProperty
	private String interfaceId = "";
	@JsonProperty
	private String consumerSystem = "";
	@JsonProperty
	private String consumerService = "";
	@JsonProperty
	private String providerService = "";
	@JsonProperty
	private long txCount = 0L;
	
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getConsumerSystem() {
		return consumerSystem;
	}
	public void setConsumerSystem(String consumerSystem) {
		this.consumerSystem = consumerSystem;
	}
	public String getConsumerService() {
		return consumerService;
	}
	public void setConsumerService(String consumerService) {
		this.consumerService = consumerService;
	}
	public String getProviderService() {
		return providerService;
	}
	public void setProviderService(String providerService) {
		this.providerService = providerService;
	}
	public long getTxCount() {
		return txCount;
	}
	public void setTxCount(long txCount) {
		this.txCount = txCount;
	}
}
