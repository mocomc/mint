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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 인터페이스 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interface  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -7528441589882152440L;

	public final static String CD_DATA_PR_DIR_ONEWAY = "0";

	public final static String CD_DATA_PR_DIR_TWOWAY = "1";

	/*** 인터페이스ID */
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;

	/*** 통합인터페이스ID */
	@JsonProperty
	String integrationId = defaultStringValue;

	/*** 인터페이스명 */
	@JsonProperty("interfaceNm")
	String interfaceNm = defaultStringValue;

	/*** 연계채널 */
	@JsonProperty("channel")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Channel channel;

	@JsonProperty
	String channelChangeComments = defaultStringValue;

	@JsonProperty
	String channelChangeCd = defaultStringValue;

	@JsonProperty
	String channelChangeNm = defaultStringValue;


	/*** 상태(인터페이스요건의 상태) */
	@JsonProperty("status")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String status = defaultStringValue;

	/*** 상태(인터페이스요건의 상태명) */
	@JsonProperty("statusNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String statusNm = defaultStringValue;


	/*** 중요도 */
	@JsonProperty("importance")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String importance = defaultStringValue;

	@JsonProperty("importanceNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String importanceNm = defaultStringValue;

	//인터페이스맵핑 설명
	//Channel.mapRole 구분에 따라 mapCd를 사용 할지 InterfaceMapping.asisInterfaceId를 맵핑코드로 사용할지 판단한다.
	/*** 맵핑코드 */
	@JsonProperty("mapCd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String mapCd = defaultStringValue;

	@JsonProperty("interfaceMapping")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	InterfaceMapping interfaceMapping;


	/*** 지연임계시간 */
	@JsonProperty("maxDelayTime")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxDelayTime = defaultStringValue;

	/*** 장애임계시간 */
	@JsonProperty("maxErrorTime")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxErrorTime = defaultStringValue;

	/*** 지연임계시간단위  */
	@JsonProperty("maxDelayUnit")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxDelayUnit = defaultStringValue;

	@JsonProperty("maxDelayUnitNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxDelayUnitNm = defaultStringValue;


	/*** 장애임계시간단위  */
	@JsonProperty("maxErrorUnit")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxErrorUnit = defaultStringValue;

	@JsonProperty("maxErrorUnitNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String maxErrorUnitNm = defaultStringValue;

	/*** 패턴 */
	@JsonProperty("refPattern")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	InterfacePattern refPattern;

	/*** 데이터처리방향 */
	@JsonProperty("dataPrDir")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String dataPrDir = defaultStringValue;

	/*** 데이터처리방향명 */
	@JsonProperty("dataPrDirNm")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String dataPrDirNm = defaultStringValue;

	/*** APP처리방식 */
	@JsonProperty("appPrMethod")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String appPrMethod = defaultStringValue;

	/*** APP처리방식명 */
	@JsonProperty("appPrMethodNm")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String appPrMethodNm = defaultStringValue;

	/*** 데이터처리방식 */
	@JsonProperty("dataPrMethod")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String dataPrMethod = defaultStringValue;

	/*** 데이터처리방식명 */
	@JsonProperty("dataPrMethodNm")
	//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	String dataPrMethodNm = defaultStringValue;


	/*** 발생주기 */
	@JsonProperty("dataFrequency")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataFrequency = defaultStringValue;

	@JsonProperty
	String dataFrequencyInput = defaultStringValue;

	/*** 발생주기명 */
	@JsonProperty("dataFrequencyNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataFrequencyNm = defaultStringValue;

	/*** 건당사이즈 */
	@JsonProperty("sizePerTran")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	long sizePerTran;

	/*** 주기별건수 */
	@JsonProperty("cntPerFrequency")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int cntPerFrequency;

	/*** 일일발생회수*/
	@JsonProperty("cntPerDay")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int cntPerDay;

	/*** 일일총전송량*/
	@JsonProperty("qttPerDay")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	long qttPerDay;


	/*** 시작리소스유형 */
	@JsonProperty("startResType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String startResType = defaultStringValue;

	/*** 시작리소스유형명 */
	@JsonProperty("startResTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String startResTypeNm = defaultStringValue;

	/*** 종료리스스유형 */
	@JsonProperty("endResType")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String endResType = defaultStringValue;

	/*** 종료리소스유형명 */
	@JsonProperty("endResTypeNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String endResTypeNm = defaultStringValue;

	/*** 시작노드개수 */
	@JsonProperty("startNodeCnt")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int startNodeCnt;

	/*** 종료노드개수 */
	@JsonProperty("endNodeCnt")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int endNodeCnt;

	/*** 순차보장여부 */
	@JsonProperty("dataSeqYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String dataSeqYn = "N";

	/*** 삭제구분 */
	@JsonProperty("delYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String delYn = "N";

	/*** 설명 */
	@JsonProperty("comments")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String comments = defaultStringValue;

	/*** 등록일 */
	@JsonProperty("regDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty("regId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regId = defaultStringValue;

	/*** 수정일 */
	@JsonProperty("modDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modDate = defaultStringValue;

	/*** 수정자 */
	@JsonProperty("modId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modId = defaultStringValue;

	/*** 시스템 리스트 */
	@JsonProperty("systemList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<System> systemList = new ArrayList<System>();

	/*** 비지니스 리스트 */
	@JsonProperty("businessList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Business> businessList = new ArrayList<Business>();

	/*** 디스플레이용 송신 시스템/비지니스 리스트 */
	@JsonProperty("senderSystemInfoList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<DisplaySystemInfo> senderSystemInfoList = new ArrayList<DisplaySystemInfo>();

	/*** 디스플레이용 수신 시스템/비지니스 리스트 */
	@JsonProperty("receiverSystemInfoList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<DisplaySystemInfo> receiverSystemInfoList = new ArrayList<DisplaySystemInfo>();

	/*** 연계채널별프로퍼티 맵 */
	@JsonProperty("properties")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<InterfaceProperties> properties = new ArrayList<InterfaceProperties>();

	/*** 담당자리스트 */
	@JsonProperty("relUsers")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<RelUser> relUsers = new ArrayList<RelUser>();

	/***인터페이스 TAG 리스트 */
	@JsonProperty("tagList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<InterfaceTag> tagList = new ArrayList<InterfaceTag>();


	/***인터페이스 TAG 리스트 */
	@JsonProperty("dataAccessRoleList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<DataAccessRole> dataAccessRoleList = new ArrayList<DataAccessRole>();


	/*** 전문 맵핑 여부 */
	@JsonProperty("hasDataMap")
	String hasDataMap = defaultStringValue;

	/*** 전문 맵핑 여부 설명 */
	@JsonProperty("hasDataMapNm")
	String hasDataMapNm = defaultStringValue;
	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the interfaceNm
	 */
	public String getInterfaceNm() {
		return interfaceNm;
	}

	/**
	 * @param interfaceNm the interfaceNm to set
	 */
	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
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
	 * @return the mapCd
	 */
	public String getMapCd() {
		return mapCd;
	}

	/**
	 * @param mapCd the mapCd to set
	 */
	public void setMapCd(String mapCd) {
		this.mapCd = mapCd;
	}



	/**
	 * @return the interfaceMapping
	 */
	public InterfaceMapping getInterfaceMapping() {
		return interfaceMapping;
	}

	/**
	 * @param interfaceMapping the interfaceMapping to set
	 */
	public void setInterfaceMapping(InterfaceMapping interfaceMapping) {
		this.interfaceMapping = interfaceMapping;
	}

	/**
	 * @return the maxDelayTime
	 */
	public String getMaxDelayTime() {
		return maxDelayTime;
	}

	/**
	 * @param maxDelayTime the maxDelayTime to set
	 */
	public void setMaxDelayTime(String maxDelayTime) {
		this.maxDelayTime = maxDelayTime;
	}

	/**
	 * @return the maxErrorTime
	 */
	public String getMaxErrorTime() {
		return maxErrorTime;
	}

	/**
	 * @param maxErrorTime the maxErrorTime to set
	 */
	public void setMaxErrorTime(String maxErrorTime) {
		this.maxErrorTime = maxErrorTime;
	}



	/**
	 * @return the importanceNm
	 */
	public String getImportanceNm() {
		return importanceNm;
	}

	/**
	 * @param importanceNm the importanceNm to set
	 */
	public void setImportanceNm(String importanceNm) {
		this.importanceNm = importanceNm;
	}

	/**
	 * @return the maxDelayUnitNm
	 */
	public String getMaxDelayUnitNm() {
		return maxDelayUnitNm;
	}

	/**
	 * @param maxDelayUnitNm the maxDelayUnitNm to set
	 */
	public void setMaxDelayUnitNm(String maxDelayUnitNm) {
		this.maxDelayUnitNm = maxDelayUnitNm;
	}

	/**
	 * @return the maxErrorUnitNm
	 */
	public String getMaxErrorUnitNm() {
		return maxErrorUnitNm;
	}

	/**
	 * @param maxErrorUnitNm the maxErrorUnitNm to set
	 */
	public void setMaxErrorUnitNm(String maxErrorUnitNm) {
		this.maxErrorUnitNm = maxErrorUnitNm;
	}



	/**
	 * @return the dataPrDir
	 */
	public String getDataPrDir() {
		return dataPrDir;
	}

	/**
	 * @param dataPrDir the dataPrDir to set
	 */
	public void setDataPrDir(String dataPrDir) {
		this.dataPrDir = dataPrDir;
	}

	/**
	 * @return the dataPrDirNm
	 */
	public String getDataPrDirNm() {
		return dataPrDirNm;
	}

	/**
	 * @param dataPrDirNm the dataPrDirNm to set
	 */
	public void setDataPrDirNm(String dataPrDirNm) {
		this.dataPrDirNm = dataPrDirNm;
	}

	/**
	 * @return the appPrMethod
	 */
	public String getAppPrMethod() {
		return appPrMethod;
	}

	/**
	 * @param appPrMethod the appPrMethod to set
	 */
	public void setAppPrMethod(String appPrMethod) {
		this.appPrMethod = appPrMethod;
	}

	/**
	 * @return the appPrMethodNm
	 */
	public String getAppPrMethodNm() {
		return appPrMethodNm;
	}

	/**
	 * @param appPrMethodNm the appPrMethodNm to set
	 */
	public void setAppPrMethodNm(String appPrMethodNm) {
		this.appPrMethodNm = appPrMethodNm;
	}

	/**
	 * @return the dataPrMethod
	 */
	public String getDataPrMethod() {
		return dataPrMethod;
	}

	/**
	 * @param dataPrMethod the dataPrMethod to set
	 */
	public void setDataPrMethod(String dataPrMethod) {
		this.dataPrMethod = dataPrMethod;
	}

	/**
	 * @return the dataPrMethodNm
	 */
	public String getDataPrMethodNm() {
		return dataPrMethodNm;
	}

	/**
	 * @param dataPrMethodNm the dataPrMethodNm to set
	 */
	public void setDataPrMethodNm(String dataPrMethodNm) {
		this.dataPrMethodNm = dataPrMethodNm;
	}

	/**
	 * @return the dataFrequency
	 */
	public String getDataFrequency() {
		return dataFrequency;
	}

	/**
	 * @param dataFrequency the dataFrequency to set
	 */
	public void setDataFrequency(String dataFrequency) {
		this.dataFrequency = dataFrequency;
	}

	/**
	 * @return the dataFrequencyNm
	 */
	public String getDataFrequencyNm() {
		return dataFrequencyNm;
	}

	/**
	 * @param dataFrequencyNm the dataFrequencyNm to set
	 */
	public void setDataFrequencyNm(String dataFrequencyNm) {
		this.dataFrequencyNm = dataFrequencyNm;
	}

	/**
	 * @return the startResType
	 */
	public String getStartResType() {
		return startResType;
	}

	/**
	 * @param startResType the startResType to set
	 */
	public void setStartResType(String startResType) {
		this.startResType = startResType;
	}

	/**
	 * @return the startResTypeNm
	 */
	public String getStartResTypeNm() {
		return startResTypeNm;
	}

	/**
	 * @param startResTypeNm the startResTypeNm to set
	 */
	public void setStartResTypeNm(String startResTypeNm) {
		this.startResTypeNm = startResTypeNm;
	}

	/**
	 * @return the endResType
	 */
	public String getEndResType() {
		return endResType;
	}

	/**
	 * @param endResType the endResType to set
	 */
	public void setEndResType(String endResType) {
		this.endResType = endResType;
	}

	/**
	 * @return the endResTypeNm
	 */
	public String getEndResTypeNm() {
		return endResTypeNm;
	}

	/**
	 * @param endResTypeNm the endResTypeNm to set
	 */
	public void setEndResTypeNm(String endResTypeNm) {
		this.endResTypeNm = endResTypeNm;
	}

	/**
	 * @return the startNodeCnt
	 */
	public int getStartNodeCnt() {
		return startNodeCnt;
	}

	/**
	 * @param startNodeCnt the startNodeCnt to set
	 */
	public void setStartNodeCnt(int startNodeCnt) {
		this.startNodeCnt = startNodeCnt;
	}

	/**
	 * @return the endNodeCnt
	 */
	public int getEndNodeCnt() {
		return endNodeCnt;
	}

	/**
	 * @param endNodeCnt the endNodeCnt to set
	 */
	public void setEndNodeCnt(int endNodeCnt) {
		this.endNodeCnt = endNodeCnt;
	}

	/**
	 * @return the dataSeqYn
	 */
	public String getDataSeqYn() {
		return dataSeqYn;
	}

	/**
	 * @param dataSeqYn the dataSeqYn to set
	 */
	public void setDataSeqYn(String dataSeqYn) {
		this.dataSeqYn = dataSeqYn;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the statusNm
	 */
	public String getStatusNm() {
		return statusNm;
	}

	/**
	 * @param statusNm the statusNm to set
	 */
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	/**
	 * @return the refPattern
	 */
	public InterfacePattern getRefPattern() {
		return refPattern;
	}

	/**
	 * @param refPattern the refPattern to set
	 */
	public void setRefPattern(InterfacePattern refPattern) {
		this.refPattern = refPattern;
	}

	/**
	 * @return the businessList
	 */
	public List<Business> getBusinessList() {
		return businessList;
	}

	/**
	 * @param businessList the businessList to set
	 */
	public void setBusinessList(List<Business> businessList) {
		this.businessList = businessList;
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
	 * @return the systemList
	 */
	public List<System> getSystemList() {
		return systemList;
	}

	/**
	 * @param systemList the systemList to set
	 */
	public void setSystemList(List<System> systemList) {
		this.systemList = systemList;
	}

	/**
	 * @return the relUsers
	 */
	public List<RelUser> getRelUsers() {
		return relUsers;
	}

	/**
	 * @param relUsers the relUsers to set
	 */
	public void setRelUsers(List<RelUser> relUsers) {
		this.relUsers = relUsers;
	}



	/**
	 * @return the tagList
	 */
	public List<InterfaceTag> getTagList() {
		return tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<InterfaceTag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the maxDelayUnit
	 */
	public String getMaxDelayUnit() {
		return maxDelayUnit;
	}

	/**
	 * @param maxDelayUnit the maxDelayUnit to set
	 */
	public void setMaxDelayUnit(String maxDelayUnit) {
		this.maxDelayUnit = maxDelayUnit;
	}

	/**
	 * @return the maxErrorUnit
	 */
	public String getMaxErrorUnit() {
		return maxErrorUnit;
	}

	/**
	 * @param maxErrorUnit the maxErrorUnit to set
	 */
	public void setMaxErrorUnit(String maxErrorUnit) {
		this.maxErrorUnit = maxErrorUnit;
	}

	/**
	 * @return the sizePerTran
	 */
	public long getSizePerTran() {
		return sizePerTran;
	}

	/**
	 * @param sizePerTran the sizePerTran to set
	 */
	public void setSizePerTran(long sizePerTran) {
		this.sizePerTran = sizePerTran;
	}

	/**
	 * @return the cntPerFrequency
	 */
	public int getCntPerFrequency() {
		return cntPerFrequency;
	}

	/**
	 * @param cntPerFrequency the cntPerFrequency to set
	 */
	public void setCntPerFrequency(int cntPerFrequency) {
		this.cntPerFrequency = cntPerFrequency;
	}

	/**
	 * @return the cntPerDay
	 */
	public int getCntPerDay() {
		return cntPerDay;
	}

	/**
	 * @param cntPerDay the cntPerDay to set
	 */
	public void setCntPerDay(int cntPerDay) {
		this.cntPerDay = cntPerDay;
	}

	/**
	 * @return the qttPerDay
	 */
	public long getQttPerDay() {
		return qttPerDay;
	}

	/**
	 * @param qttPerDay the qttPerDay to set
	 */
	public void setQttPerDay(long qttPerDay) {
		this.qttPerDay = qttPerDay;
	}


	public List<DisplaySystemInfo> getSenderSystemInfoList() {
		return senderSystemInfoList;
	}

	public void setSenderSystemInfoList(List<DisplaySystemInfo> senderSystemInfoList) {
		this.senderSystemInfoList = senderSystemInfoList;
	}

	public List<DisplaySystemInfo> getReceiverSystemInfoList() {
		return receiverSystemInfoList;
	}

	public void setReceiverSystemInfoList(List<DisplaySystemInfo> receiverSystemInfoList) {
		this.receiverSystemInfoList = receiverSystemInfoList;
	}


	public String getChannelChangeComments() {
		return channelChangeComments;
	}

	public void setChannelChangeComments(String channelChangeComments) {
		this.channelChangeComments = channelChangeComments;
	}

	public String getChannelChangeCd() {
		return channelChangeCd;
	}

	public void setChannelChangeCd(String channelChangeCd) {
		this.channelChangeCd = channelChangeCd;
	}

	public String getChannelChangeNm() {
		return channelChangeNm;
	}

	public void setChannelChangeNm(String channelChangeNm) {
		this.channelChangeNm = channelChangeNm;
	}

	public String getDataFrequencyInput() {
		return dataFrequencyInput;
	}

	public void setDataFrequencyInput(String dataFrequencyInput) {
		this.dataFrequencyInput = dataFrequencyInput;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public List<InterfaceProperties> getProperties() {
		return properties;
	}

	public void setProperties(List<InterfaceProperties> properties) {
		this.properties = properties;
	}

	public List<DataAccessRole> getDataAccessRoleList() {
		return dataAccessRoleList;
	}

	public void setDataAccessRoleList(List<DataAccessRole> dataAccessRoleList) {
		this.dataAccessRoleList = dataAccessRoleList;
	}

	public String getHasDataMap() {
		return hasDataMap;
	}

	public void setHasDataMap(String hasDataMap) {
		this.hasDataMap = hasDataMap;
	}

	public String getHasDataMapNm() {
		return hasDataMapNm;
	}

	public void setHasDataMapNm(String hasDataMapNm) {
		this.hasDataMapNm = hasDataMapNm;
	}

}
