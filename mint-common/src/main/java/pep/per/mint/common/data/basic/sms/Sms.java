package pep.per.mint.common.data.basic.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Sms extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6358740620985264466L;

	public final static String FORMAT_TEXT = "0";
	public final static String FORMAT_HTML = "1";

	public final static String TYPE_TRACKING = "0";
	public final static String TYPE_RESOURCE = "1";
	public final static String TYPE_MI_AGENT = "2";
	public final static String TYPE_MI_RUNNER = "3";
	public final static String TYPE_PROCESS = "4";
	public final static String TYPE_QMGR = "5";
	public final static String TYPE_CHANNEL = "6";
	public final static String TYPE_QUEUE = "7";
	public final static String TYPE_IIP_AGENT = "8";
	public final static String TYPE_GENERAL = "9";
	public final static String TYPE_NETSTAT = "13";


	@JsonProperty
	String smsId = defaultStringValue;

	@JsonProperty
	String type = TYPE_TRACKING;

	@JsonProperty
	String typeNm = defaultStringValue;


	@JsonProperty
	String subject = defaultStringValue;

	@JsonProperty
	String contents = defaultStringValue;

	@JsonProperty
	String sender = defaultStringValue;

	@JsonProperty
	String recipients = defaultStringValue;

	@JsonProperty
	int retry = 0;

	@JsonProperty
	String format = FORMAT_HTML;

	@JsonProperty
	String sndReqDate = defaultStringValue;

	@JsonProperty
	String sndYn = "N";

	@JsonProperty
	String resDate = defaultStringValue;

	@JsonProperty
	String resCd = defaultStringValue;

	@JsonProperty
	String resMsg = defaultStringValue;

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

	@JsonProperty
	Object resource;


	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSndReqDate() {
		return sndReqDate;
	}

	public void setSndReqDate(String sndReqDate) {
		this.sndReqDate = sndReqDate;
	}

	public String getSndYn() {
		return sndYn;
	}

	public void setSndYn(String sndYn) {
		this.sndYn = sndYn;
	}

	public String getResDate() {
		return resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}

	public String getResCd() {
		return resCd;
	}

	public void setResCd(String resCd) {
		this.resCd = resCd;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
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

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public void addRecipient(String recipient) {
		if(!Util.isEmpty(recipients)){
			recipients = Util.join(recipients, ",", recipient);
		}else{
			recipients = recipient;
		}
	}



}
