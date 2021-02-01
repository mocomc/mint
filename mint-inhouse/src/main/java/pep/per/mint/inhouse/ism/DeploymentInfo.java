package pep.per.mint.inhouse.ism;

import java.io.Serializable;
import java.util.List;

import pep.per.mint.common.data.basic.InterfaceProperties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties( ignoreUnknown = true )
public class DeploymentInfo implements Serializable{

	/**
	 * 1.개발/검증/운영/DR
	 * “0”:개발,”1”,검증,”2”:운영,”3”:DR
	 *
	 */
	@JsonProperty
	String targetType = "0";

	/**
	 * 2.요청사용자
	 *
	 */
	@JsonProperty
	String requesterId = "";

	/**
	 * 3.요청사용자명
	 *
	 */
	@JsonProperty
	String requesterNm = "";

	/**
	 * 4.IIP인터페이스KEY
	 */
	@JsonProperty
	String requestKey = "";

	/**
	 * 5.Version
	 */
	@JsonProperty
	String version = "";

	/**
	 * 6.인터페이스ID
	 * interface.integrationId
	 */
	@JsonProperty
	String interfaceId = "";

	/**
	* 7.인터페이스명
	* interface.interfaceNm
	*/
	@JsonProperty
	String interfaceNm = "";

	/**
	 * 8.송신시스템
	 * interface.senderSystemInfoList[0].systemCd
	 */
	@JsonProperty
	String senderSystemCd = "";

	/**
	 * 9.수신시스템
	 * interface.receiverSystemInfoList[0].systemCd
	 */
	@JsonProperty
	String receiverSystemCd = "";

	/**
	 * 10.인터페이스TYPE
	 * interface.senderSystemInfoList[0]. resourceCd
	 * interface.receiverSystemInfoList[0]. resourceCd
	 * “0”:DB “1”:FILE “2”:RFCR “3”:Proxy “4”:WS “5”:APP “6”:HTTP
	 */
	@JsonProperty
	String senderResourceCd = "";

	@JsonProperty
	String receiverResourceCd = "";

	/**
	 * 11.동기/비동기
	 * interface.appPrMethod
	 * “0”:동기, “1”:비동기
	 */
	@JsonProperty
	String appPrMethod = "";

	/**
	 * 12.요청,응답 전문객체 (현대해상 기준)
	 * 13.테이블정보
	 * 14.파일경로,파일명정보
	 * interface.receiverSystemInfoList[0].service
	 */
	@JsonProperty
	String receiverService;

	@JsonProperty
	String senderService;

	/**
	 * 15.호출 서비스정보 (현대해상 기준)
	 * servicedesc
	 */
	@JsonProperty
	String senderServiceNm;

	@JsonProperty
	String receiverServiceNm;

	/**
	 * 16.data 처리방식 => I/F 유형 (온라인,배치,디퍼드)
	 * interface.properties.rcvDestination
	 */
	@JsonProperty
	String dataPrMethod = "";

	/**
	 * 17.data 처리방향  => 처리방향 (조회,등록)
	 * dataPrDir
	 */
	@JsonProperty
	String dataPrDir = "";

	/**
	 * 18.인터페이스 사용여부 => 사용여부 (사용,미사용)
	 * Importance
	 */
	@JsonProperty
	String If_Use = "";


	@JsonProperty
	List<InterfacePropertiesInfo> interfaceProperties = null;

	@JsonProperty
	String useYn = "Y";

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequesterNm() {
		return requesterNm;
	}

	public void setRequesterNm(String requesterNm) {
		this.requesterNm = requesterNm;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	public String getSenderSystemCd() {
		return senderSystemCd;
	}

	public void setSenderSystemCd(String senderSystemCd) {
		this.senderSystemCd = senderSystemCd;
	}

	public String getReceiverSystemCd() {
		return receiverSystemCd;
	}

	public void setReceiverSystemCd(String receiverSystemCd) {
		this.receiverSystemCd = receiverSystemCd;
	}

	public String getSenderResourceCd() {
		return senderResourceCd;
	}

	public void setSenderResourceCd(String senderResourceCd) {
		this.senderResourceCd = senderResourceCd;
	}

	public String getReceiverResourceCd() {
		return receiverResourceCd;
	}

	public void setReceiverResourceCd(String receiverResourceCd) {
		this.receiverResourceCd = receiverResourceCd;
	}

	public String getAppPrMethod() {
		return appPrMethod;
	}

	public void setAppPrMethod(String appPrMethod) {
		this.appPrMethod = appPrMethod;
	}

	public String getReceiverService() {
		return receiverService;
	}

	public void setReceiverService(String receiverService) {
		this.receiverService = receiverService;
	}

	public String getSenderService() {
		return senderService;
	}

	public void setSenderService(String senderService) {
		this.senderService = senderService;
	}

	public String getSenderServiceNm() {
		return senderServiceNm;
	}

	public void setSenderServiceNm(String senderServiceNm) {
		this.senderServiceNm = senderServiceNm;
	}

	public String getReceiverServiceNm() {
		return receiverServiceNm;
	}

	public void setReceiverServiceNm(String receiverServiceNm) {
		this.receiverServiceNm = receiverServiceNm;
	}

	public String getDataPrMethod() {
		return dataPrMethod;
	}

	public void setDataPrMethod(String dataPrMethod) {
		this.dataPrMethod = dataPrMethod;
	}

	public String getDataPrDir() {
		return dataPrDir;
	}

	public void setDataPrDir(String dataPrDir) {
		this.dataPrDir = dataPrDir;
	}

	public String getIf_Use() {
		return If_Use;
	}

	public void setIf_Use(String if_Use) {
		If_Use = if_Use;
	}

	public List<InterfacePropertiesInfo> getInterfaceProperties() {
		return interfaceProperties;
	}

	public void setInterfaceProperties(
			List<InterfacePropertiesInfo> interfaceProperties) {
		this.interfaceProperties = interfaceProperties;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}



}
