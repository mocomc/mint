/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppModel extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * VDS  Vitria DB 서비스 
	 * VFS  Vitria File 서비스
	 * VOL Vitria 전문 서비스  
	 */
	public static final String TYPE_VITRIA_DB_SERVICE 	= "VDS";
	public static final String TYPE_VITRIA_FILE_SERVICE = "VFS";
	public static final String TYPE_VITRIA_OLTP_SERVICE = "VOL";
	
	
	@JsonProperty
	String interfaceMid = defaultStringValue;	
	
	@JsonProperty
	String mid = defaultStringValue;

	@JsonProperty
	String name = defaultStringValue;
	
	@JsonProperty
	String cd = defaultStringValue;
	
	@JsonProperty
	String type = defaultStringValue;	

	@JsonProperty
	String typeName = defaultStringValue;
	
	@JsonProperty
	String systemId = defaultStringValue;
	
	@JsonProperty
	String systemName = defaultStringValue;
	
	@JsonProperty
	String systemCd = defaultStringValue;
	
	@JsonProperty
	int systemSeq = 0;
	
	@JsonProperty
	String systemType = defaultStringValue;
	
	@JsonProperty
	String systemTypeName = defaultStringValue;
	
	@JsonProperty
	String serverId = defaultStringValue;
	
	@JsonProperty
	String serverName = defaultStringValue;
	
	@JsonProperty
	String serverCd = defaultStringValue;
	
	@JsonProperty
	String tagInfo = defaultStringValue;
	
	/**
	 * <pre>
	 * variable's structure 
	 * key, values(array)
	 * </pre>
	 */
	@JsonProperty
	Map<String, List<AppModelAttribute>> attributes = new HashMap<String, List<AppModelAttribute>>();  
	  
	@JsonProperty
	String comments = defaultStringValue;
	
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

	@JsonProperty
	List<MessageModel> msgs = new ArrayList<MessageModel>();
	
	@JsonProperty
	List<MappingModel> mappings = new ArrayList<MappingModel>();
	
	
	
	/**
	 * @return the interfaceMid
	 */
	public String getInterfaceMid() {
		return interfaceMid;
	}

	/**
	 * @param interfaceMid the interfaceMid to set
	 */
	public void setInterfaceMid(String interfaceMid) {
		this.interfaceMid = interfaceMid;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
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
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
 
 
	public int getSystemSeq() {
		return systemSeq;
	}
 
	public void setSystemSeq(int systemSeq) {
		this.systemSeq = systemSeq;
	}

	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	
	
	/**
	 * @return the systemCd
	 */
	public String getSystemCd() {
		return systemCd;
	}

	/**
	 * @param systemCd the systemCd to set
	 */
	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
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
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
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
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the systemTypeName
	 */
	public String getSystemTypeName() {
		return systemTypeName;
	}

	/**
	 * @param systemTypeName the systemTypeName to set
	 */
	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
	
	
	/**
	 * @return the serverCd
	 */
	public String getServerCd() {
		return serverCd;
	}

	/**
	 * @param serverCd the serverCd to set
	 */
	public void setServerCd(String serverCd) {
		this.serverCd = serverCd;
	}

	/**
	 * @return the tagInfo
	 */
	public String getTagInfo() {
		return tagInfo;
	}

	/**
	 * @param tagInfo the tagInfo to set
	 */
	public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, List<AppModelAttribute>> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, List<AppModelAttribute>> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the msgs
	 */
	public List<MessageModel> getMsgs() {
		return msgs;
	}

	/**
	 * @param msgs the msgs to set
	 */
	public void setMsgs(List<MessageModel> msgs) {
		this.msgs = msgs;
	}

	/**
	 * @return the mappings
	 */
	public List<MappingModel> getMappings() {
		return mappings;
	}

	/**
	 * @param mappings the mappings to set
	 */
	public void setMappings(List<MappingModel> mappings) {
		this.mappings = mappings;
	}
	
	
	
	
	
}
