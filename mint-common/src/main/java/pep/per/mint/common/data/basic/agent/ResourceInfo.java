package pep.per.mint.common.data.basic.agent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ResourceInfo extends CacheableObject {
	
	private static final long serialVersionUID = -5962052384203641555L;

	/**
	 *
	 * 0	개발	서버자원유형	CPU
     * 1	개발	서버자원유형	MEMORY
     * 2	개발	서버자원유형	DISK
	 */
	public final static String TYPE_CPU = "0";
	public final static String TYPE_MEMORY = "1";
	public final static String TYPE_DISK = "2";
 

	@JsonProperty
	String serverId = defaultStringValue;

	@JsonProperty
	String resourceId = defaultStringValue;

	@JsonProperty
	String resourceName = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String typeNm = defaultStringValue;


	@JsonProperty
	String limit = defaultStringValue;

	/**
	 * Disk mountpoint
	 */
	@JsonProperty
	String info1 = defaultStringValue;

	@JsonProperty
	String info2 = defaultStringValue;

	@JsonProperty
	String info3 = defaultStringValue;


	@JsonProperty
	String status = defaultStringValue;

	
	/*** 설명 */
	@JsonProperty("comments")
	String comments = defaultStringValue;

	/*** 등록일 */
	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty("regId")
	String regId = defaultStringValue;

	/*** 수정일 */
	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	/*** 수정자 */
	@JsonProperty("modId")
	String modId = defaultStringValue;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
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


}
