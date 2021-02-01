package pep.per.mint.common.data.basic.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Email extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6358740620985264466L;

	public final static String FORMAT_TEXT = "0";
	public final static String FORMAT_HTML = "1";
	/**
	 * {
	 *	"objectType" : "공통코드" ,
	 *	"name" : "알람 메시지 유형",
	 *	"level1": "SU",
	 *	"level2": "03",
	 */
	public final static String TYPE_TRACKING = "0";
	public final static String TYPE_RES_MON = "1";
	public final static String TYPE_EAI_MON = "2";

	public static final String TYPE_MI_RUNNER 	= "3";
	public static final String TYPE_PROCESS 	= "4";
	public static final String TYPE_QMGR 		= "5";
	public static final String TYPE_CHANNEL 	= "6";
	public static final String TYPE_QUEUE 		= "7";
	public static final String TYPE_IIPAGENT 	= "8";

	public final static String TYPE_GENERAL = "9";
	public static final String TYPE_CPU		= "10";
	public static final String TYPE_MEM		= "11";
	public static final String TYPE_DISK  	= "12";

	@JsonProperty
	String emailId = defaultStringValue;

	@JsonProperty
	String type = TYPE_TRACKING;

	@JsonProperty
	String subject = defaultStringValue;

	@JsonProperty
	String contents = defaultStringValue;

	@JsonProperty
	String sender = defaultStringValue;

	@JsonProperty
	String recipients = defaultStringValue;

	@JsonProperty
	String refers = defaultStringValue;

	@JsonProperty
	String hiddenRefers = defaultStringValue;

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

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getRefers() {
		return refers;
	}

	public void setRefers(String refers) {
		this.refers = refers;
	}

	public String getHiddenRefers() {
		return hiddenRefers;
	}

	public void setHiddenRefers(String hiddenRefers) {
		this.hiddenRefers = hiddenRefers;
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
