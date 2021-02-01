package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.util.Util;

/**
 * 배포관리 데이터 오브젝트 클래스 
 * @author whoana
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Deployment extends CacheableObject {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2476097496050275742L;
	
	//@JsonProperty
	//String objectType = "Deployment";
	
	@JsonProperty
	String reqDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
	
	@JsonProperty("interface")
	Interface interfaze;
	
	@JsonProperty
	int seq;
	
	@JsonProperty
	String version = "1";
	
	@JsonProperty
	String targetType = defaultStringValue;
	
	@JsonProperty
	String targetTypeNm = defaultStringValue;
	
	@JsonProperty
	String requesterId = defaultStringValue;
	
	@JsonProperty
	String requesterNm = defaultStringValue;
	
	@JsonProperty
	String requestKey = defaultStringValue;
	
	@JsonProperty
	String resultCd = defaultStringValue;
	
	@JsonProperty
	String resultMsg = defaultStringValue;
	
	@JsonProperty
	String resDate = defaultStringValue;

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public Interface getInterface() {
		return interfaze;
	}

	public void setInterface(Interface interfaze) {
		this.interfaze = interfaze;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetTypeNm() {
		return targetTypeNm;
	}

	public void setTargetTypeNm(String targetTypeNm) {
		this.targetTypeNm = targetTypeNm;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequesterNm() {
		return requesterNm;
	}

	public void setRequesterNm(String requesterNm) {
		this.requesterNm = requesterNm;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResDate() {
		return resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
 
	
	
	

}
