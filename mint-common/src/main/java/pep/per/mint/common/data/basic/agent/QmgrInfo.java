package pep.per.mint.common.data.basic.agent;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class QmgrInfo extends CacheableObject {
	private static final long serialVersionUID = -2591337978336516277L;

	public static final String TYPE_ILINK = "0";
	public static final String TYPE_MQ = "1";
	/**
	 *
	 */

	@JsonProperty
	String qmgrId  = defaultStringValue;

	@JsonProperty
	String qmgrNm  = defaultStringValue;


	@JsonProperty
	String type  = TYPE_ILINK;

	@JsonProperty
	String typeNm  = defaultStringValue;

	@JsonProperty
	String ip  = defaultStringValue;

	@JsonProperty
	String port  = defaultStringValue;

	@JsonProperty
	String status  = defaultStringValue;

	@JsonProperty
	List<ChannelInfo> channels;

	@JsonProperty
	List<QueueInfo> queues;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	Map<String, ChannelInfo> channelMap;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	Map<String, QueueInfo> queueMap;


	@JsonProperty
	String comments  = defaultStringValue;

	@JsonProperty
	String delYn  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regId  = defaultStringValue;

	@JsonProperty
	String modDate  = defaultStringValue;

	@JsonProperty
	String modId  = defaultStringValue;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("system")
	System system;

	public String getQmgrId() {
		return qmgrId;
	}

	public void setQmgrId(String qmgrId) {
		this.qmgrId = qmgrId;
	}

	public String getQmgrNm() {
		return qmgrNm;
	}

	public void setQmgrNm(String qmgrNm) {
		this.qmgrNm = qmgrNm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ChannelInfo> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelInfo> channels) {
		this.channels = channels;
	}

	public List<QueueInfo> getQueues() {
		return queues;
	}

	public void setQueues(List<QueueInfo> queues) {
		this.queues = queues;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public Map<String, ChannelInfo> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, ChannelInfo> channelMap) {
		this.channelMap = channelMap;
	}

	public Map<String, QueueInfo> getQueueMap() {
		return queueMap;
	}

	public void setQueueMap(Map<String, QueueInfo> queueMap) {
		this.queueMap = queueMap;
	}



}
