package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class QueueInfo extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -4141447862378029810L;

	@JsonProperty
	String qmgrId  = defaultStringValue;

	@JsonProperty
	String queueId  = defaultStringValue;

	@JsonProperty
	String serverId  = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String typeNm = defaultStringValue;

	@JsonProperty
	String queueNm  = defaultStringValue;

	@JsonProperty
	String limit  = defaultStringValue;

	@JsonProperty
	String limitOpt  = defaultStringValue;

	@JsonProperty
	String limitOptNm  = defaultStringValue;

	@JsonProperty
	int depth = 0;


	@JsonProperty
	String status = defaultStringValue;

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


	public String getQmgrId() {
		return qmgrId;
	}

	public void setQmgrId(String qmgrId) {
		this.qmgrId = qmgrId;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getQueueNm() {
		return queueNm;
	}

	public void setQueueNm(String queueNm) {
		this.queueNm = queueNm;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimitOpt() {
		return limitOpt;
	}

	public void setLimitOpt(String limitOpt) {
		this.limitOpt = limitOpt;
	}

	public String getLimitOptNm() {
		return limitOptNm;
	}

	public void setLimitOptNm(String limitOptNm) {
		this.limitOptNm = limitOptNm;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}


}
