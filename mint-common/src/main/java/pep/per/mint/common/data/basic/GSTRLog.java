package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class GSTRLog {
	
	/*** 번호 */
	@JsonProperty
	private String idx;
	
	/*** 상태 */
	@JsonProperty
	private String statusNm;
	
	/*** Interface ID */
	@JsonProperty
	private String interfaceId;
	
	/*** Integration ID */
	@JsonProperty
	private String integrationId;
	
	/*** 인터페이스 명 */
	@JsonProperty
	private String interfaceNm;
	
	/*** 송신시스템 명 */
	@JsonProperty
	private String sndSystemNm;
	
	/*** 송신시스템 코드 */
	@JsonProperty
	private String sndSystemCd;
	
	/*** 송신시스템명 + 송신시스템코드 */
	@JsonProperty
	private String sndSystem;
	
	/*** 수신시스템 명 */
	@JsonProperty
	private String rcvSystemNm;
	
	/*** 수신시스템 코드 */
	@JsonProperty
	private String rcvSystemCd;
	
	/*** 수신시스템명 + 수신시스템코드 */
	@JsonProperty
	private String rcvSystem;
	
	@JsonProperty
	private String rcvStoreNm;
	
	@JsonProperty
	private String rcvPosNo;
	
	@JsonProperty
	private String processTime;
	
	/*** 트랜젝션ID */
	@JsonProperty
	private String tranId;
	

	public String getIdx() {
		return idx;
	}
	
	public void setIdx(String idx) {
		this.idx = idx;
	}
	
	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getInterfaceId() {
		return interfaceId;
	}
	
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
	}

	public String getSndSystemNm() {
		return sndSystemNm;
	}

	public void setSndSystemNm(String sndSystemNm) {
		this.sndSystemNm = sndSystemNm;
	}

	public String getSndSystemCd() {
		return sndSystemCd;
	}

	public void setSndSystemCd(String sndSystemCd) {
		this.sndSystemCd = sndSystemCd;
	}

	public String getSndSystem() {
		return sndSystem;
	}
	
	public void setSndSystem(String sndSystem) {
		this.sndSystem = sndSystem;
	}
	
	public String getRcvSystemNm() {
		return rcvSystemNm;
	}

	public void setRcvSystemNm(String rcvSystemNm) {
		this.rcvSystemNm = rcvSystemNm;
	}

	public String getRcvSystemCd() {
		return rcvSystemCd;
	}

	public void setRcvSystemCd(String rcvSystemCd) {
		this.rcvSystemCd = rcvSystemCd;
	}
	
	public String getRcvSystem() {
		return rcvSystem;
	}

	public void setRcvSystem(String rcvSystem) {
		this.rcvSystem = rcvSystem;
	}

	public String getRcvStoreNm() {
		return rcvStoreNm;
	}

	public void setRcvStoreNm(String rcvStoreNm) {
		this.rcvStoreNm = rcvStoreNm;
	}

	public String getRcvPosNo() {
		return rcvPosNo;
	}

	public void setRcvPosNo(String rcvPosNo) {
		this.rcvPosNo = rcvPosNo;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	
	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
}