/**
 * 
 */
package pep.per.mint.common.data.basic.notification;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * Frequently Asked Questions(자주하는질문) Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FAQ extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3644879770407924098L;
	
	
	@JsonProperty("faqId")
	String faqId = defaultStringValue;
	
	@JsonProperty("categoryId")
	String categoryId = defaultStringValue;
	
	@JsonProperty("categoryNm")
	String categoryNm = defaultStringValue;
	
	@JsonProperty("startDate")
	String startDate = defaultStringValue;
	
	@JsonProperty("question")
	String question = defaultStringValue;
	
	@JsonProperty("answer")
	String answer = defaultStringValue;
	
	@JsonProperty("importance")
	String importance = defaultStringValue;
	
	@JsonProperty("flag")
	String flag = defaultStringValue;
	
	@JsonProperty("html")
	String html = defaultStringValue;
	
	@JsonProperty("endDate")
	String endDate = defaultStringValue;
	
	@JsonProperty("delYn")
	String delYn = defaultStringValue;
	
	@JsonProperty("regDate")
	String regDate = defaultStringValue;
	
	@JsonProperty("regUser")
	String regUser = defaultStringValue;
	
	@JsonProperty("modDate")
	String modDate = defaultStringValue;
	
	@JsonProperty("modUser")
	String modUser = defaultStringValue;

	@JsonProperty("regUserNm")
	String regUserNm = defaultStringValue;
	
	@JsonProperty("faqAttachFile")
	List<FaqAttachFile> faqAttachFile;
	
	
	
	/**
	 * @return the faqId
	 */
	public String getFaqId() {
		return faqId;
	}

	/**
	 * @param faqId the faqId to set
	 */
	public void setFaqId(String faqId) {
		this.faqId = faqId;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryNm
	 */
	public String getCategoryNm() {
		return categoryNm;
	}

	/**
	 * @param categoryNm the categoryNm to set
	 */
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the importance
	 */
	public String getImportance() {
		return importance;
	}

	/**
	 * @param importance the importance to set
	 */
	public void setImportance(String importance) {
		this.importance = importance;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regUser
	 */
	public String getRegUser() {
		return regUser;
	}

	/**
	 * @param regUser the regUser to set
	 */
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modUser
	 */
	public String getModUser() {
		return modUser;
	}

	/**
	 * @param modUser the modUser to set
	 */
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	/**
	 * @return the regUserNm
	 */
	public String getRegUserNm() {
		return regUserNm;
	}

	/**
	 * @param regUserNm the regUserNm to set
	 */
	public void setRegUserNm(String regUserNm) {
		this.regUserNm = regUserNm;
	}

	/**
	 * @return the faqAttachFile
	 */
	public List<FaqAttachFile> getFaqAttachFile() {
		return faqAttachFile;
	}

	/**
	 * @param faqAttachFile the faqAttachFile to set
	 */
	public void setFaqAttachFile(List<FaqAttachFile> faqAttachFile) {
		this.faqAttachFile = faqAttachFile;
	}

	
	
}
