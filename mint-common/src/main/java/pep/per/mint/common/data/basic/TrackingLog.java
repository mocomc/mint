package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 거래로그 Data Object
 * @author Solution Interface Portal
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TrackingLog  extends CacheableObject{

	private static final long serialVersionUID = -4854074087253861062L;

	/*** 인티그레이션ID */
	@JsonProperty("integrationId")
	String integrationId = defaultStringValue;

	/*** 트래킹일시 */
	@JsonProperty("trackingDate")
	String trackingDate = defaultStringValue;

	/*** 원호스트ID */
	@JsonProperty("orgHostId")
	String orgHostId = defaultStringValue;

	/*** 인터페이스ID */
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;

	/*** 상태 */
	@JsonProperty("status")
	String status = defaultStringValue;

	/*** 상태명 */
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;

	/*** 인터페이스매치 */
	@JsonProperty("match")
	String match = defaultStringValue;

	/*** 레코드처리건수 */
	@JsonProperty("recordCnt")
	int recordCnt = 0;

	/*** 데이터처리량 */
	@JsonProperty("dataAmt")
	int dataAmt = 0;

	/*** 데이터압축구분 */
	@JsonProperty("cmp")
	String cmp = defaultStringValue;

	/*** 처리소요시간 */
	@JsonProperty("cst")
	int cst = 0;

	/*** 처리할노드수 */
	@JsonProperty("tdc")
	int tdc = 0;

	/*** 초리한노드수 */
	@JsonProperty("fnc")
	int fnc = 0;

	/*** 에러노드수 */
	@JsonProperty("erc")
	int erc = 0;

	/*** 에러코드 */
	@JsonProperty("errorCd")
	String errorCd = defaultStringValue;

	/*** 에러메시지 */
	@JsonProperty("errorMsg")
	String errorMsg = defaultStringValue;

	/*** 업무ID */
	@JsonProperty("businessId")
	String businessId = defaultStringValue;

	/*** 업무명 */
	@JsonProperty("businessNm")
	String businessNm = defaultStringValue;

	/*** 인터페이스명 */
	@JsonProperty("interfaceNm")
	String interfaceNm = defaultStringValue;

	/*** 연계방식ID */
	@JsonProperty("channelId")
	String channelId = defaultStringValue;

	/*** 연계방식명 */
	@JsonProperty("channelNm")
	String channelNm = defaultStringValue;

	/*** 데이터처리방향 */
	@JsonProperty("dataPrDir")
	String dataPrDir = defaultStringValue;

	/*** 데이터처리방향명 */
	@JsonProperty("dataPrDirNm")
	String dataPrDirNm = defaultStringValue;

	/*** 데이터처리방식 */
	@JsonProperty("dataPrMethod")
	String dataPrMethod = defaultStringValue;

	/*** 데이터처리방식명 */
	@JsonProperty("dataPrMethodNm")
	String dataPrMethodNm = defaultStringValue;

	/*** 처리방식 */
	@JsonProperty("appPrMethod")
	String appPrMethod = defaultStringValue;

	/*** 처리방식명 */
	@JsonProperty("appPrMethodNm")
	String appPrMethodNm = defaultStringValue;

	/*** 송신시스템ID */
	@JsonProperty("sndSystemId")
	String sndSystemId = defaultStringValue;

	/*** 송신시스템명 */
	@JsonProperty("sndSystemNm")
	String sndSystemNm = defaultStringValue;

	/*** 송신리소스 */
	@JsonProperty("sndResType")
	String sndResType = defaultStringValue;

	/*** 송신리소스명 */
	@JsonProperty("sndResNm")
	String sndResNm = defaultStringValue;

	/*** 수신시스템ID */
	@JsonProperty("rcvSystemId")
	String rcvSystemId = defaultStringValue;

	/*** 수신시스템명 */
	@JsonProperty("rcvSystemNm")
	String rcvSystemNm = defaultStringValue;

	/*** 수신리소스 */
	@JsonProperty("rcvResType")
	String rcvResType = defaultStringValue;

	/*** 수신리소스명 */
	@JsonProperty("rcvResNm")
	String rcvResNm = defaultStringValue;



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

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public int getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(int recordCnt) {
		this.recordCnt = recordCnt;
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

	public int getCst() {
		return cst;
	}

	public void setCst(int cst) {
		this.cst = cst;
	}

	public int getTdc() {
		return tdc;
	}

	public void setTdc(int tdc) {
		this.tdc = tdc;
	}

	public int getFnc() {
		return fnc;
	}

	public void setFnc(int fnc) {
		this.fnc = fnc;
	}

	public int getErc() {
		return erc;
	}

	public void setErc(int erc) {
		this.erc = erc;
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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessNm() {
		return businessNm;
	}

	public void setBusinessNm(String businessNm) {
		this.businessNm = businessNm;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelNm() {
		return channelNm;
	}

	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	public String getDataPrDir() {
		return dataPrDir;
	}

	public void setDataPrDir(String dataPrDir) {
		this.dataPrDir = dataPrDir;
	}

	public String getDataPrDirNm() {
		return dataPrDirNm;
	}

	public void setDataPrDirNm(String dataPrDirNm) {
		this.dataPrDirNm = dataPrDirNm;
	}

	public String getDataPrMethod() {
		return dataPrMethod;
	}

	public void setDataPrMethod(String dataPrMethod) {
		this.dataPrMethod = dataPrMethod;
	}

	public String getDataPrMethodNm() {
		return dataPrMethodNm;
	}

	public void setDataPrMethodNm(String dataPrMethodNm) {
		this.dataPrMethodNm = dataPrMethodNm;
	}

	public String getAppPrMethod() {
		return appPrMethod;
	}

	public void setAppPrMethod(String appPrMethod) {
		this.appPrMethod = appPrMethod;
	}

	public String getAppPrMethodNm() {
		return appPrMethodNm;
	}

	public void setAppPrMethodNm(String appPrMethodNm) {
		this.appPrMethodNm = appPrMethodNm;
	}

	public String getSndSystemId() {
		return sndSystemId;
	}

	public void setSndSystemId(String sndSystemId) {
		this.sndSystemId = sndSystemId;
	}

	public String getSndSystemNm() {
		return sndSystemNm;
	}

	public void setSndSystemNm(String sndSystemNm) {
		this.sndSystemNm = sndSystemNm;
	}

	public String getSndResType() {
		return sndResType;
	}

	public void setSndResType(String sndResType) {
		this.sndResType = sndResType;
	}

	public String getSndResNm() {
		return sndResNm;
	}

	public void setSndResNm(String sndResNm) {
		this.sndResNm = sndResNm;
	}

	public String getRcvSystemId() {
		return rcvSystemId;
	}

	public void setRcvSystemId(String rcvSystemId) {
		this.rcvSystemId = rcvSystemId;
	}

	public String getRcvSystemNm() {
		return rcvSystemNm;
	}

	public void setRcvSystemNm(String rcvSystemNm) {
		this.rcvSystemNm = rcvSystemNm;
	}

	public String getRcvResType() {
		return rcvResType;
	}

	public void setRcvResType(String rcvResType) {
		this.rcvResType = rcvResType;
	}

	public String getRcvResNm() {
		return rcvResNm;
	}

	public void setRcvResNm(String rcvResNm) {
		this.rcvResNm = rcvResNm;
	}

}
