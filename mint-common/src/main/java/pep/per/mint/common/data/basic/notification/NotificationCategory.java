/**
 * 
 */
package pep.per.mint.common.data.basic.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 공지사항 & FAQ(자주하는질문) 카테고리 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class NotificationCategory extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8585655999784035311L;
	
	
	@JsonProperty("categoryId")
	String categoryId = defaultStringValue;

	@JsonProperty("categoryNm")
	String categoryNm = defaultStringValue;
	
	@JsonProperty("type")
	String type = defaultStringValue;
	
	@JsonProperty("comments")
	String comments = defaultStringValue;
	
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	
	
	
}
