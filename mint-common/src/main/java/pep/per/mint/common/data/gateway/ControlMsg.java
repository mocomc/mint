/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.gateway;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.SystemInfo;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ControlMsg implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3205995632110289862L;

	public static final String TYPE_CACHE = "CACHE";
	
	public static final String TYPE_CMD = "CMD";
	
	public static final int CMD_CACHE_UPDATE = 0;
	public static final int CMD_CACHE_ADD = 1;
	public static final int CMD_CACHE_DELETE = 2;
	
	public static final String RESULT_FAIL = "F";
	public static final String RESULT_SUCCESS = "S";
	
	
	@JsonProperty
	String id;
	
	@JsonProperty
	String type;
	
	@JsonProperty
	int cmd;
	
	@JsonProperty
	String key;
	
	@JsonProperty
	String groupId;
	
	@JsonProperty
	int seq;
	
	@JsonProperty
	String resultCode;
	
	@JsonProperty
	String resultMsg;
	
	@JsonProperty
	String processId;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	@JsonProperty
	String routeSystemId;
	
	
	SystemInfo routeSystemInfo;
	
	/**
	 * 
	 */
	public ControlMsg() {
		// TODO Auto-generated constructor stub
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
	 * @return the cmd
	 */
	public int getCmd() {
		return cmd;
	}



	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}



	/**
	 * @return the routeSystem
	 */
	public SystemInfo getRouteSystemInfo() {
		return routeSystemInfo;
	}



	/**
	 * @param routeSystem the routeSystem to set
	 */
	public void setRouteSystemInfo(SystemInfo routeSystemInfo) {
		this.routeSystemInfo = routeSystemInfo;
	}



	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}



	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}



	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}



	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
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
	 * @return the routeSystemId
	 */
	public String getRouteSystemId() {
		return routeSystemId;
	}



	/**
	 * @param routeSystemId the routeSystemId to set
	 */
	public void setRouteSystemId(String routeSystemId) {
		this.routeSystemId = routeSystemId;
	}



	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}



	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}



	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}



	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}



	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}



	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
	
	
	
}
