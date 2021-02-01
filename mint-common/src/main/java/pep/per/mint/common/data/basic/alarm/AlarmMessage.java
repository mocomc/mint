/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.alarm;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.common.data.basic.alarm
 * AlarmMessage.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 17.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class AlarmMessage extends CacheableObject{

	public static final String TYPE_TRACKING = "00";
	public static final String TYPE_RS_CPU = "01";
	public static final String TYPE_RS_MEM = "02";
	public static final String TYPE_RS_DISK = "03";
	public static final String TYPE_PROCESS = "04";
	public static final String TYPE_QMGR = "05";
	public static final String TYPE_CHANNEL = "06";
	public static final String TYPE_QUEUE = "07";
	public static final String TYPE_IIP_AGENT = "08";
	public static final String TYPE_MI_AGENT = "09";
	public static final String TYPE_MI_RUNNER = "10";

	@JsonProperty
	String messageId = defaultStringValue;

	@JsonProperty
	String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
	/**
	 * 공통코드
	 * LEVEL1 = 'SU' and LEVEL2 = '06' :
	 * 00 : 인터페이스 메시지
	 * 01 : 리소스 - CPU
	 * 02 : 리소스 - MEMORY
	 * 03 : 리소스 - DISK
	 * 04 : 프로세스
	 * 05 : 큐매니저
	 * 06 : 채널
	 * 07 : 큐
	 * 08 : IIP Agent
	 * 09 : MI Agent
	 * 10 : MI Runner
	 */
	@JsonProperty
	String type = TYPE_TRACKING;

	@JsonProperty
	String message = defaultStringValue;

	@JsonProperty
	String regUser = defaultStringValue;

	@JsonProperty
	List<String> roles = new ArrayList<String>();

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}



}
