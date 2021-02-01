/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;

/**
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Server  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -267877595492598356L;

	@JsonProperty("serverId")
	String serverId = defaultStringValue;

	@JsonProperty("serverNm")
	String serverNm = defaultStringValue;

	@JsonProperty("serverCd")
	String serverCd = defaultStringValue;

	@JsonProperty("hostNm")
	String hostNm = defaultStringValue;

	@JsonProperty("useType")
	String useType = defaultStringValue;

	@JsonProperty("useTypeNm")
	String useTypeNm = defaultStringValue;

	@JsonProperty("ip")
	String ip = defaultStringValue;

	@JsonProperty("redundancyYn")
	String redundancyYn = defaultStringValue;

	@JsonProperty("hwInstPosition")
	String hwInstPosition = defaultStringValue;

	@JsonProperty("os")
	String os = defaultStringValue;

	@JsonProperty("firewallYn")
	String firewallYn = defaultStringValue;

	@JsonProperty("zoneInfo")
	ZoneInfo zoneInfo;

	@JsonProperty("areaInput")
	String areaInput = defaultStringValue;

	@JsonProperty("comments")
	String comments = defaultStringValue;

	@JsonProperty("delYn")
	String delYn = "N";

	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	String regId = defaultStringValue;

	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	String modId = defaultStringValue;

	/*** 순번  */
	@JsonProperty("seq")
	int seq;

	@JsonProperty("relUsers")
	List<RelUser> relUsers = new ArrayList<RelUser>();

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
	 * @return the serverNm
	 */
	public String getServerNm() {
		return serverNm;
	}

	/**
	 * @param serverNm the serverNm to set
	 */
	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
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
	 * @return the hostNm
	 */
	public String getHostNm() {
		return hostNm;
	}

	/**
	 * @param hostNm the hostNm to set
	 */
	public void setHostNm(String hostNm) {
		this.hostNm = hostNm;
	}

	/**
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}

	/**
	 * @param useType the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}

	/**
	 * @return the userTypeNm
	 */
	public String getUserTypeNm() {
		return useTypeNm;
	}

	/**
	 * @param userTypeNm the userTypeNm to set
	 */
	public void setUserTypeNm(String userTypeNm) {
		this.useTypeNm = userTypeNm;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the redundancyYn
	 */
	public String getRedundancyYn() {
		return redundancyYn;
	}

	/**
	 * @param redundancyYn the redundancyYn to set
	 */
	public void setRedundancyYn(String redundancyYn) {
		this.redundancyYn = redundancyYn;
	}

	/**
	 * @return the hwInstPosition
	 */
	public String getHwInstPosition() {
		return hwInstPosition;
	}

	/**
	 * @param hwInstPosition the hwInstPosition to set
	 */
	public void setHwInstPosition(String hwInstPosition) {
		this.hwInstPosition = hwInstPosition;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the firewallYn
	 */
	public String getFirewallYn() {
		return firewallYn;
	}

	/**
	 * @param firewallYn the firewallYn to set
	 */
	public void setFirewallYn(String firewallYn) {
		this.firewallYn = firewallYn;
	}

	/**
	 * @return the zoneInfo
	 */
	public ZoneInfo getZoneInfo() {
		return zoneInfo;
	}

	/**
	 * @param zoneInfo the zoneInfo to set
	 */
	public void setZoneInfo(ZoneInfo zoneInfo) {
		this.zoneInfo = zoneInfo;
	}

	/**
	 * @return the areaInput
	 */
	public String getAreaInput() {
		return areaInput;
	}

	/**
	 * @param areaInput the areaInput to set
	 */
	public void setAreaInput(String areaInput) {
		this.areaInput = areaInput;
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
	 * @return the relUser
	 */
	public List<RelUser> getRelUser() {
		return relUsers;
	}

	/**
	 * @param relUser the relUser to set
	 */
	public void setRelUser(List<RelUser> relUser) {
		this.relUsers = relUser;
	}
 

}
