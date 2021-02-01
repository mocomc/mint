package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TransactionLogScaleInfo extends CacheableObject {

	@JsonProperty
	private String interfaceId = "";
	@JsonProperty
	private long sizePerTran = 0L;
	@JsonProperty
	private long countPerDay = 0L;
	
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public long getSizePerTran() {
		return sizePerTran;
	}
	public void setSizePerTran(long sizePerTran) {
		this.sizePerTran = sizePerTran;
	}
	public long getCountPerDay() {
		return countPerDay;
	}
	public void setCountPerDay(long countPerDay) {
		this.countPerDay = countPerDay;
	}
}
