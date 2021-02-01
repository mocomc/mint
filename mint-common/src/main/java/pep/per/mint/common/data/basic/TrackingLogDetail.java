package pep.per.mint.common.data.basic;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 거래로그상세 Data Object
 *
 * @author Solution TA
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TrackingLogDetail  extends CacheableObject{

	private static final long serialVersionUID = 180026811274790133L;

	/*** 트래킹ID */
	@JsonProperty("id")
	String id = defaultStringValue;

	/*** 인티그레이션ID */
	@JsonProperty("integrationId")
	String integrationId = defaultStringValue;

	/*** 트래킹일시 */
	@JsonProperty("trackingDate")
	String trackingDate = defaultStringValue;

	/*** 원호스트ID */
	@JsonProperty("orgHostId")
	String orgHostId = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 노드유형 */
	@JsonProperty("nodeType")
	String nodeType = defaultStringValue;

	/*** 노드시작시간 */
	@JsonProperty("startDate")
	String startDate = defaultStringValue;

	/*** 노드종료시간 */
	@JsonProperty("endDate")
	String endDate = defaultStringValue;

	/*** 순번 */
	@JsonProperty("seq")
	int seq = 0;

	/*** 호스트ID */
	@JsonProperty("hostId")
	String hostId = defaultStringValue;

	/*** 프로세스ID */
	@JsonProperty("processId")
	String processId = defaultStringValue;

	/*** 노드IP */
	@JsonProperty("ip")
	String ip = defaultStringValue;

	/*** OS */
	@JsonProperty("os")
	String os = defaultStringValue;

	/*** 애플리케이션 */
	@JsonProperty("appNm")
	String appNm = defaultStringValue;

	/*** 에러코드 */
	@JsonProperty("errorCd")
	String errorCd = defaultStringValue;

	/*** 에러메시지 */
	@JsonProperty("errorMsg")
	String errorMsg = defaultStringValue;

	/*** 레코드처리건수 */
	@JsonProperty("recordCnt")
	int recordCnt = 0;

	/*** 메시지 */
	@JsonProperty("msg")
	Blob msg;

	@JsonProperty("msgToByte")
	byte[] msgToByte;

	@JsonProperty("msgToString")
	String msgToString;

	/*** 데이터처리량 */
	@JsonProperty("dataAmt")
	int dataAmt = 0;

	/*** 데이터압축구분 */
	@JsonProperty("cmp")
	String cmp = defaultStringValue;

	/*** 디렉토리 */
	@JsonProperty("directory")
	String directory = defaultStringValue;

	/*** 파일명 */
	@JsonProperty("fileNm")
	String fileNm = defaultStringValue;

	/*** 파일사이즈 */
	@JsonProperty("fileSize")
	int fileSize = 0;




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getTrackingDate() {
		return trackingDate;
	}

	public void setTrackingDate(String trackingDate) {
		this.trackingDate = trackingDate;
	}

	public String getOrgHostId() {
		return orgHostId;
	}

	public void setOrgHostId(String orgHostId) {
		this.orgHostId = orgHostId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppNm() {
		return appNm;
	}

	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(int recordCnt) {
		this.recordCnt = recordCnt;
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

	public String getMsgToString() {
		return msgToString;
	}

	public void setMsgToString(String msgToString) {
		this.msgToString = msgToString;
	}

	public int getDataAmt() {
		return dataAmt;
	}

	public void setDataAmt(int dataAmt) {
		this.dataAmt = dataAmt;
	}

	public String getCmp() {
		return cmp;
	}

	public void setCmp(String cmp) {
		this.cmp = cmp;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


}
