package pep.per.mint.inhouse.ism;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pep.per.mint.common.data.basic.InterfaceProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterfacePropertiesInfo implements Serializable{


	@JsonProperty
	String attrCd = "";
	
	@JsonProperty
	String attrId = "";
	
	@JsonProperty
	String attrNm = "";
	
	@JsonProperty
	String attrValue = "";
	
	@JsonProperty
	String channelId = "";
	
	@JsonProperty
	int Idx;
	
	@JsonProperty
	String interfaceId = "";

	public String getAttrCd() {
		return attrCd;
	}

	public void setAttrCd(String attrCd) {
		this.attrCd = attrCd;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrNm() {
		return attrNm;
	}

	public void setAttrNm(String attrNm) {
		this.attrNm = attrNm;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	  
	
	
	
}