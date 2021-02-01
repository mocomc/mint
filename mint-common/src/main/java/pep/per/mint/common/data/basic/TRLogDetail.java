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
public class TRLogDetail  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 180026811274790133L;

	/*** 로그키1 */
	@JsonProperty("logKey1")
	String logKey1 = defaultStringValue;

	/*** 로그키2 */
	@JsonProperty("logKey2")
	String logKey2 = defaultStringValue;

	/*** 로그키3 */
	@JsonProperty("logKey3")
	String logKey3 = defaultStringValue;

	/*** 노드ID */
	@JsonProperty("nodeId")
	String nodeId = defaultStringValue;

	/*** 처리시간 */
	@JsonProperty("processTime")
	String processTime = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 처리순번 */
	@JsonProperty("hopCnt")
	int hopCnt = 0;

	/*** 데이터크기 */
	@JsonProperty("dataSize")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int dataSize;

	/*** 에러코드 */
	@JsonProperty("errorCd")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String errorCd = defaultStringValue;

	/*** 에러메시지 */
	@JsonProperty("errorMsg")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String errorMsg = defaultStringValue;

	/*** 메시지 */
	@JsonProperty("msg")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	Blob msg;

	/*** 메시지 */
	@JsonProperty("msgToByte")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	byte[] msgToByte;

	/*** 디렉토리명 */
	@JsonProperty("directoryNm")
	String directoryNm = defaultStringValue;


	/*** 파일명 */
	@JsonProperty("fileNm")
	String fileNm = defaultStringValue;




	@JsonProperty("processMode")
	String processMode = defaultStringValue;

	@JsonProperty("hostId")
	String hostId = defaultStringValue;

	@JsonProperty("processId")
	String processId = defaultStringValue;

	@JsonProperty("compressYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String compressYn = defaultStringValue;

	@JsonProperty("recordCnt")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int recordCnt;

	@JsonProperty("recordSize")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int recordSize;

	@JsonProperty("recordErrorCnt")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int recordErrorCnt;

	@JsonProperty("recordSuccessCnt")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	int recordSuccessCnt;

	@JsonProperty("detail01")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String detail01;

	@JsonProperty("msgToString")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String msgToString;

	@JsonProperty("statusNm")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String statusNm;

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getMsgToString() {
		return msgToString;
	}

	public void setMsgToString(String msgToString) {
		this.msgToString = msgToString;
	}

	public Blob getMsg() {
		return msg;
	}

	public void setMsg(Blob msg) {
		this.msg = msg;
	}

	public byte[] getMsgToByte() {
		return msgToByte;
	}

	public void setMsgToByte(byte[] msgToByte) {
		this.msgToByte = msgToByte;
	}

	public String getDetail01() {
		return detail01;
	}

	public void setDetail01(String detail01) {
		this.detail01 = detail01;
	}

	/**
	 * @return the logKey1
	 */
	public String getLogKey1() {
		return logKey1;
	}

	/**
	 * @param logKey1 the logKey1 to set
	 */
	public void setLogKey1(String logKey1) {
		this.logKey1 = logKey1;
	}

	/**
	 * @return the logKey2
	 */
	public String getLogKey2() {
		return logKey2;
	}

	/**
	 * @param logKey2 the logKey2 to set
	 */
	public void setLogKey2(String logKey2) {
		this.logKey2 = logKey2;
	}

	/**
	 * @return the logKey3
	 */
	public String getLogKey3() {
		return logKey3;
	}

	/**
	 * @param logKey3 the logKey3 to set
	 */
	public void setLogKey3(String logKey3) {
		this.logKey3 = logKey3;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the processTime
	 */
	public String getProcessTime() {
		return processTime;
	}

	/**
	 * @param processTime the processTime to set
	 */
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
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
	 * @return the hopCnt
	 */
	public int getHopCnt() {
		return hopCnt;
	}

	/**
	 * @param hopCnt the hopCnt to set
	 */
	public void setHopCnt(int hopCnt) {
		this.hopCnt = hopCnt;
	}

	/**
	 * @return the dataSize
	 */
	public int getDataSize() {
		return dataSize;
	}

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	/**
	 * @return the errorCd
	 */
	public String getErrorCd() {
		return errorCd;
	}

	/**
	 * @param errorCd the errorCd to set
	 */
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the processMode
	 */
	public String getProcessMode() {
		return processMode;
	}

	/**
	 * @param processMode the processMode to set
	 */
	public void setProcessMode(String processMode) {
		this.processMode = processMode;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @param hostId the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
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

	/**
	 * @return the compressYn
	 */
	public String getCompressYn() {
		return compressYn;
	}

	/**
	 * @param compressYn the compressYn to set
	 */
	public void setCompressYn(String compressYn) {
		this.compressYn = compressYn;
	}

	/**
	 * @return the recordCnt
	 */
	public int getRecordCnt() {
		return recordCnt;
	}

	/**
	 * @param recordCnt the recordCnt to set
	 */
	public void setRecordCnt(int recordCnt) {
		this.recordCnt = recordCnt;
	}

	/**
	 * @return the recordSize
	 */
	public int getRecordSize() {
		return recordSize;
	}

	/**
	 * @param recordSize the recordSize to set
	 */
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	/**
	 * @return the recordErrorCnt
	 */
	public int getRecordErrorCnt() {
		return recordErrorCnt;
	}

	/**
	 * @param recordErrorCnt the recordErrorCnt to set
	 */
	public void setRecordErrorCnt(int recordErrorCnt) {
		this.recordErrorCnt = recordErrorCnt;
	}

	/**
	 * @return the recordSuccessCnt
	 */
	public int getRecordSuccessCnt() {
		return recordSuccessCnt;
	}

	/**
	 * @param recordSuccessCnt the recordSuccessCnt to set
	 */
	public void setRecordSuccessCnt(int recordSuccessCnt) {
		this.recordSuccessCnt = recordSuccessCnt;
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



}
