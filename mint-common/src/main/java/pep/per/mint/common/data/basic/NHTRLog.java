package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class NHTRLog {

	/*** Interface ID */
	@JsonProperty
	private String interfaceId;

	/*** Integration ID */
	@JsonProperty
	private String integrationId;

	/*** 인터페이스 명 */
	@JsonProperty
	private String interfaceNm;

	/*** 시작시간 */
	@JsonProperty
	private String msgDt;

	/*** 시작시간 */
	@JsonProperty
	private String msgDateTime;

	/*** 시작시간 */
	@JsonProperty
	private String minPrDt;

	/*** 상태명 */
	@JsonProperty
	private String status;

	/*** 상태명 */
	@JsonProperty
	private String statusNm;

	/*** 그룹ID */
	@JsonProperty
	private String groupId;

	/*** HOST ID */
	@JsonProperty
	private String hostId;

	/*** 송신시스템 ID */
	@JsonProperty
	private String sndSystemId;

	/*** 송신시스템 코드 */
	@JsonProperty
	private String sndSystemCd;

	/*** 송신시스템 명 */
	@JsonProperty
	private String sndSystemNm;

	/*** 수신시스템 ID */
	@JsonProperty
	private String rcvSystemId;

	/*** 수신시스템 코드 */
	@JsonProperty
	private String rcvSystemCd;

	/*** 수신시스템 명 */
	@JsonProperty
	private String rcvSystemNm;

	/*** 메세지 번호 */
	@JsonProperty
	private String msgNo;

	/*** 수신서비스명 */
	@JsonProperty
	private String rmsSvc;

	/*** 결과수신서비스명 */
	@JsonProperty
	private String rztRmsSvc;

	/*** O GID */
	@JsonProperty
	private String oGid;

	/*** T GID */
	@JsonProperty
	private String tGid;

	/*** S GID */
	@JsonProperty
	private String sGid;



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

	public String getMsgDt() {
		return msgDt;
	}

	public void setMsgDt(String msgDt) {
		this.msgDt = msgDt;
	}

	public String getMsgDateTime() {
		return msgDateTime;
	}

	public void setMsgDateTime(String msgDateTime) {
		this.msgDateTime = msgDateTime;
	}

	public String getMinPrDt() {
		return minPrDt;
	}

	public void setMinPrDt(String minPrDt) {
		this.minPrDt = minPrDt;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getSndSystemId() {
		return sndSystemId;
	}

	public void setSndSystemId(String sndSystemId) {
		this.sndSystemId = sndSystemId;
	}

	public String getSndSystemCd() {
		return sndSystemCd;
	}

	public void setSndSystemCd(String sndSystemCd) {
		this.sndSystemCd = sndSystemCd;
	}

	public String getSndSystemNm() {
		return sndSystemNm;
	}

	public void setSndSystemNm(String sndSystemNm) {
		this.sndSystemNm = sndSystemNm;
	}

	public String getRcvSystemId() {
		return rcvSystemId;
	}

	public void setRcvSystemId(String rcvSystemId) {
		this.rcvSystemId = rcvSystemId;
	}

	public String getRcvSystemCd() {
		return rcvSystemCd;
	}

	public void setRcvSystemCd(String rcvSystemCd) {
		this.rcvSystemCd = rcvSystemCd;
	}

	public String getRcvSystemNm() {
		return rcvSystemNm;
	}

	public void setRcvSystemNm(String rcvSystemNm) {
		this.rcvSystemNm = rcvSystemNm;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getRmsSvc() {
		return rmsSvc;
	}

	public void setRmsSvc(String rmsSvc) {
		this.rmsSvc = rmsSvc;
	}

	public String getRztRmsSvc() {
		return rztRmsSvc;
	}

	public void setRztRmsSvc(String rztRmsSvc) {
		this.rztRmsSvc = rztRmsSvc;
	}

	public String getoGid() {
		return oGid;
	}

	public void setoGid(String oGid) {
		this.oGid = oGid;
	}

	public String gettGid() {
		return tGid;
	}

	public void settGid(String tGid) {
		this.tGid = tGid;
	}

	public String getsGid() {
		return sGid;
	}

	public void setsGid(String sGid) {
		this.sGid = sGid;
	}



}