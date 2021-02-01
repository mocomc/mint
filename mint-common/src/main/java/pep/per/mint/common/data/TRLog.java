package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRLog {
	
	@JsonProperty
	private String logId;
	
	@JsonProperty
	private String interfaceId;
	
	@JsonProperty
	private String interfaceNm;
	
	@JsonProperty
	private String logDate;
	
	@JsonProperty
	private String serverId;
	
	@JsonProperty
	private String serverNm;
	
	
	@JsonProperty
	private String step;
	
	@JsonProperty
	private String groupKey;
	
	@JsonProperty
	private String errCd;
	
	@JsonProperty
	private String errMsg;
	
	@JsonProperty
	private String log;
	
	@JsonProperty
	private String regDate;
	
	
	/**
	 * @return logId
	 */
	
	public String getLogId() {
		return logId;
	}
	/**
	 * @param logId 설정하려는 logId
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}
	/**
	 * @return interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}
	/**
	 * @param interfaceId 설정하려는 interfaceId
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	/**
	 * @return logDate
	 */
	public String getLogDate() {
		return logDate;
	}
	/**
	 * @param logDate 설정하려는 logDate
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	/**
	 * @return serverId
	 */
	public String getServerId() {
		return serverId;
	}
	/**
	 * @param serverId 설정하려는 serverId
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return step
	 */
	public String getStep() {
		return step;
	}
	/**
	 * @param step 설정하려는 step
	 */
	public void setStep(String step) {
		this.step = step;
	}
	/**
	 * @return groupKey
	 */
	public String getGroupKey() {
		return groupKey;
	}
	/**
	 * @param groupKey 설정하려는 groupKey
	 */
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	/**
	 * @return errCd
	 */
	public String getErrCd() {
		return errCd;
	}
	/**
	 * @param errCd 설정하려는 errCd
	 */
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
	/**
	 * @return errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}
	/**
	 * @param errMsg 설정하려는 errMsg
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	/**
	 * @return log
	 */
	public String getLog() {
		return log;
	}
	/**
	 * @param log 설정하려는 log
	 */
	public void setLog(String log) {
		this.log = log;
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
	
	

}
