package pep.per.mint.inhouse.mail.samsung;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
/**
 *    "sendMailVO" : {
 *      "contentText":"<STRING><H1>추가된 사용자 계정으로 메일전송 테스트해봅니다.</H1></STRING><P>잘 받았는지 아래 주소로 답장 한번 주세요.</P><P><a href='mailto:whoana@gmail.com?Subject=주말 잘 보내세요.' target='_top'>Reply Mail</a></P>",
 *      "subject":"S-Printing 이메일 연동 건",
 *      "docSecuType":"PERSONAL",
 *      "contentType":"html",
 *      "from":{"email":"inah.yoo@samsungsquare.com"},
 *      "attachs":[],
 *      "recipients":[
 *        {"email":"whoana@gmail.com","recipientType":"TO"},
 *        {"email":"inah.yoo@samsungsquare.com","recipientType":"TO"}
 *      ]
 *    }
 * @author whoana
 *
 */
public class SendMailVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4431172153113601124L;

	@JsonProperty
	String contentText = "";

	@JsonProperty
	String subject = "";

	@JsonProperty
	String docSecuType = "PERSONAL";

	@JsonProperty
	String contentType = "html";

	@JsonProperty
	FromInfo from;

	@JsonProperty
	List attachs;

	@JsonProperty
	List<RecipientInfo> recipients;

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDocSecuType() {
		return docSecuType;
	}

	public void setDocSecuType(String docSecuType) {
		this.docSecuType = docSecuType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public FromInfo getFrom() {
		return from;
	}

	public void setFrom(FromInfo from) {
		this.from = from;
	}

	public List getAttachs() {
		return attachs;
	}

	public void setAttachs(List attachs) {
		this.attachs = attachs;
	}

	public List<RecipientInfo> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<RecipientInfo> recipients) {
		this.recipients = recipients;
	}



}
