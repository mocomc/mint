package pep.per.mint.agent.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Extension extends CacheableObject{


	public static final String MSG_TYPE_ACK = "ACK";
	public static final String MSG_TYPE_REQ = "REQ";
	public static final String MSG_TYPE_RES = "RES";
	public static final String MSG_TYPE_PUSH = "PUSH";
	public static final String MSG_TYPE_ON = "ON";
	public static final String MSG_TYPE_OFF = "OFF";

	@JsonProperty
	String msgType = defaultStringValue;

	@JsonProperty
	String serviceCd = defaultStringValue;

	@JsonProperty
	String frontSessionId = defaultStringValue;

	@JsonProperty
	String agentSessionId = defaultStringValue;

	@JsonProperty
	Map<String, String> params = new HashMap<String, String>();

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getServiceCd() {
		return serviceCd;
	}

	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

	public String getFrontSessionId() {
		return frontSessionId;
	}

	public void setFrontSessionId(String frontSessionId) {
		this.frontSessionId = frontSessionId;
	}

	public String getAgentSessionId() {
		return agentSessionId;
	}

	public void setAgentSessionId(String agentSessionId) {
		this.agentSessionId = agentSessionId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}



}
