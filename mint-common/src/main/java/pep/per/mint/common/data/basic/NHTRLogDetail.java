/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 거래로그상세 Data Object
 *
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class NHTRLogDetail  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 180026811274790133L;

	@JsonProperty("msgHostId")
	String msgHostId = defaultStringValue;

	@JsonProperty("hostId")
	String hostId = defaultStringValue;

    @JsonProperty("groupId")
	String groupId = defaultStringValue;

    @JsonProperty("integrationId")
    String integrationId = defaultStringValue;

    /*** 로그키2(기본파라미터를 활용한다) */
	@JsonProperty("logKey2")
	String logKey2 = defaultStringValue;

	@JsonProperty("processMode")
	String processMode = defaultStringValue;

	@JsonProperty("processId")
	String processId = defaultStringValue;

	/*** 처리시간 */
	@JsonProperty("processTime")
	String processTime = defaultStringValue;

	@JsonProperty("statusNm")
	String statusNm;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	@JsonProperty("recordCnt")
	int recordCnt;

	/*** 파일크기 */
	@JsonProperty("fileSize")
	int fileSize;

	@JsonProperty("recordSize")
	int recordSize;

	@JsonProperty("dataSize")
	int dataSize;

	@JsonProperty("convSize")
	int convSize;

	@JsonProperty("compressYn")
	String compressYn = defaultStringValue;

	/*** 에러메시지 */
	@JsonProperty("errorMsg")
	String errorMsg = defaultStringValue;

	/*** 메시지 */
	@JsonProperty("msg")
	Blob msg;

	/*** 디렉토리명 */
	@JsonProperty("directoryNm")
	String directoryNm = defaultStringValue;

	/*** 파일명 */
	@JsonProperty("fileNm")
	String fileNm = defaultStringValue;

	/*** 처리순번 */
	@JsonProperty("hopCnt")
	int hopCnt = 0;

	/*** 소요시간 */
	@JsonProperty("turnaroundTime")
	long turnaroundTime = 0;

	/*** 총 소요시간 */
	@JsonProperty("totalTurnaroundTime")
	long totalTurnaroundTime = 0;

	/*** 오류건수 */
	@JsonProperty("errorCnt")
	long errorCnt = 0;

	public String getMsgHostId() {
		return msgHostId;
	}

	/*** 큐매니저 */
	@JsonProperty("mqmdQmgr")
	String mqmdQmgr = defaultStringValue;

	/*** T GID */
	@JsonProperty("tGid")
	String tGid = defaultStringValue;

	/*** O GID */
	@JsonProperty("oGid")
	String oGid = defaultStringValue;

	/*** S GID */
	@JsonProperty("sGid")
	String sGid = defaultStringValue;

	public void setMsgHostId(String msgHostId) {
		this.msgHostId = msgHostId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getLogKey2() {
		return logKey2;
	}

	public void setLogKey2(String logKey2) {
		this.logKey2 = logKey2;
	}

	public String getProcessMode() {
		return processMode;
	}

	public void setProcessMode(String processMode) {
		this.processMode = processMode;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(int recordCnt) {
		this.recordCnt = recordCnt;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getConvSize() {
		return convSize;
	}

	public void setConvSize(int convSize) {
		this.convSize = convSize;
	}

	public String getCompressYn() {
		return compressYn;
	}

	public void setCompressYn(String compressYn) {
		this.compressYn = compressYn;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Blob getMsg() {
		return msg;
	}

	public void setMsg(Blob msg) {
		this.msg = msg;
	}

	public String getDirectoryNm() {
		return directoryNm;
	}

	public void setDirectoryNm(String directoryNm) {
		this.directoryNm = directoryNm;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public int getHopCnt() {
		return hopCnt;
	}

	public void setHopCnt(int hopCnt) {
		this.hopCnt = hopCnt;
	}

	public long getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(long turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public long getTotalTurnaroundTime() {
		return totalTurnaroundTime;
	}

	public void setTotalTurnaroundTime(long totalTurnaroundTime) {
		this.totalTurnaroundTime = totalTurnaroundTime;
	}

	public String getMqmdQmgr() {
		return mqmdQmgr;
	}

	public void setMqmdQmgr(String mqmdQmgr) {
		this.mqmdQmgr = mqmdQmgr;
	}

	public String gettGid() {
		return tGid;
	}

	public void settGid(String tGid) {
		this.tGid = tGid;
	}

	public String getoGid() {
		return oGid;
	}

	public void setoGid(String oGid) {
		this.oGid = oGid;
	}

	public String getsGid() {
		return sGid;
	}

	public void setsGid(String sGid) {
		this.sGid = sGid;
	}

	public long getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(long errorCnt) {
		this.errorCnt = errorCnt;
	}
}
