/**
 * 
 */
package pep.per.mint.common.data.basic.monitor;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Interface;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ProblemLedger extends CacheableObject  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5242391637718966052L;

	@JsonProperty("problemId")
	String problemId = defaultStringValue;
	
	@JsonProperty("status")
	String status = defaultStringValue;
	
	@JsonProperty("statusNm")
	String statusNm = defaultStringValue;
	
	@JsonProperty("approvalStatus")
	String approvalStatus = defaultStringValue;

	@JsonProperty("approvalStatusNm")
	String approvalStatusNm = defaultStringValue;
	
	@JsonProperty("type")
	String type = defaultStringValue;
	
	@JsonProperty("typeNm")
	String typeNm = defaultStringValue;
	
	@JsonProperty("startDate")
	String startDate = defaultStringValue;
	
	@JsonProperty("endDate")
	String endDate = defaultStringValue;
	
	@JsonProperty("problemSubject")
	String problemSubject = defaultStringValue;
	
	@JsonProperty("problemContents")
	String problemContents = defaultStringValue;
	
	@JsonProperty("resultContents")
	String resultContents = defaultStringValue;
	
	@JsonProperty("planContents")
	String planContents = defaultStringValue;
	
	@JsonProperty("causeContents")
	String causeContents = defaultStringValue;
	
	@JsonProperty("importance")
	String importance = defaultStringValue;
	
	@JsonProperty("importanceNm")
	String importanceNm = defaultStringValue;
	
	@JsonProperty("nodeName")
	String nodeName = defaultStringValue;
	
	@JsonProperty("classCd")
	String classCd = defaultStringValue;

	@JsonProperty("classCdNm")
	String classCdNm = defaultStringValue;
	
	@JsonProperty("classCdPath")
	String classCdPath = defaultStringValue;
	
	@JsonProperty("delYn")
	String delYn = defaultStringValue;
	
	@JsonProperty("regDate")
	String regDate = defaultStringValue;
	
	@JsonProperty("regUser")
	String regUser = defaultStringValue;
	
	@JsonProperty("modDate")
	String modDate = defaultStringValue;
	
	@JsonProperty("modUser")
	String modUser = defaultStringValue;
	
	@JsonProperty("key1")
	String key1 = defaultStringValue;	

	@JsonProperty("key2")
	String key2 = defaultStringValue;
	
	@JsonProperty("key3")
	String key3 = defaultStringValue;	
	
	// expand values
	@JsonProperty("interfaceId")
	String interfaceId = defaultStringValue;
	
	@JsonProperty("integrationId")
	String integrationId = defaultStringValue;
	
	@JsonProperty("interfaceNm")
	String interfaceNm = defaultStringValue;
	
	@JsonProperty("seq")
	String seq = defaultStringValue;
	
	@JsonProperty("errorMsg")
	String errorMsg = defaultStringValue;
	
	@JsonProperty("channelId")
	String channelId = defaultStringValue;
	
	@JsonProperty("channelNm")
	String channelNm = defaultStringValue;
	
	@JsonProperty("isRegUser")
	String isRegUser = defaultStringValue;
	
	@JsonProperty("Interface")
	Interface Interface;
	
	@JsonProperty("interfaceCnt")
	int interfaceCnt = 0;
	
	@JsonProperty("systemId")
	String systemId = defaultStringValue;	
	
	@JsonProperty("systemCd")
	String systemCd = defaultStringValue;
	
	@JsonProperty("systemNm")
	String systemNm = defaultStringValue;
	
	@JsonProperty("problemAttachFile")
	List<ProblemAttachFile> problemAttachFile;
	
	@JsonProperty("problemInterface")
	List<Map> problemInterface;
	
	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the problemId
	 */
	public String getProblemId() {
		return problemId;
	}

	/**
	 * @param problemId the problemId to set
	 */
	public void setProblemId(String problemId) {
		this.problemId = problemId;
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
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the approvalStatusNm
	 */
	public String getApprovalStatusNm() {
		return approvalStatusNm;
	}

	/**
	 * @param approvalStatusNm the approvalStatusNm to set
	 */
	public void setApprovalStatusNm(String approvalStatusNm) {
		this.approvalStatusNm = approvalStatusNm;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeNm
	 */
	public String getTypeNm() {
		return typeNm;
	}

	/**
	 * @param typeNm the typeNm to set
	 */
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the problemSubject
	 */
	public String getProblemSubject() {
		return problemSubject;
	}

	/**
	 * @param problemSubject the problemSubject to set
	 */
	public void setProblemSubject(String problemSubject) {
		this.problemSubject = problemSubject;
	}

	/**
	 * @return the problemContents
	 */
	public String getProblemContents() {
		return problemContents;
	}

	/**
	 * @param problemContents the problemContents to set
	 */
	public void setProblemContents(String problemContents) {
		this.problemContents = problemContents;
	}

	/**
	 * @return the resultContents
	 */
	public String getResultContents() {
		return resultContents;
	}

	/**
	 * @param resultContents the resultContents to set
	 */
	public void setResultContents(String resultContents) {
		this.resultContents = resultContents;
	}

	/**
	 * @return the planContents
	 */
	public String getPlanContents() {
		return planContents;
	}

	/**
	 * @param planContents the planContents to set
	 */
	public void setPlanContents(String planContents) {
		this.planContents = planContents;
	}

	/**
	 * @return the causeContents
	 */
	public String getCauseContents() {
		return causeContents;
	}

	/**
	 * @param causeContents the causeContents to set
	 */
	public void setCauseContents(String causeContents) {
		this.causeContents = causeContents;
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
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the classCd
	 */
	public String getClassCd() {
		return classCd;
	}

	/**
	 * @param classCd the classCd to set
	 */
	public void setClassCd(String classCd) {
		this.classCd = classCd;
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
	 * @return the regUser
	 */
	public String getRegUser() {
		return regUser;
	}

	/**
	 * @param regUser the regUser to set
	 */
	public void setRegUser(String regUser) {
		this.regUser = regUser;
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
	 * @return the modUser
	 */
	public String getModUser() {
		return modUser;
	}

	/**
	 * @param modUser the modUser to set
	 */
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	/**
	 * @return the problemAttachFile
	 */
	public List<ProblemAttachFile> getProblemAttachFile() {
		return problemAttachFile;
	}

	/**
	 * @param problemAttachFile the problemAttachFile to set
	 */
	public void setProblemAttachFile(List<ProblemAttachFile> problemAttachFile) {
		this.problemAttachFile = problemAttachFile;
	}
	
	/**
	 * @return the problemInterface
	 */
	public List<Map> getProblemInterface() {
		return problemInterface;
	}

	/**
	 * @param problemInterface the problemInterface to set
	 */
	public void setProblemInterface(List<Map> problemInterface) {
		this.problemInterface = problemInterface;
	}
	

	

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
	 * @return the integrationId
	 */
	public String getIntegrationId() {
		return integrationId;
	}

	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
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
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	 * @return the classCdNm
	 */
	public String getClassCdNm() {
		return classCdNm;
	}

	/**
	 * @param classCdNm the classCdNm to set
	 */
	public void setClassCdNm(String classCdNm) {
		this.classCdNm = classCdNm;
	}

	/**
	 * @return the classCdPath
	 */
	public String getClassCdPath() {
		return classCdPath;
	}

	/**
	 * @param classCdPath the classCdPath to set
	 */
	public void setClassCdPath(String classCdPath) {
		this.classCdPath = classCdPath;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * @return the channelNm
	 */
	public String getChannelNm() {
		return channelNm;
	}

	/**
	 * @param channelNm the channelNm to set
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}	

	/**
	 * @return the isRegUser
	 */
	public String getIsRegUser() {
		return isRegUser;
	}

	/**
	 * @param isRegUser the isRegUser to set
	 */
	public void setIsRegUser(String isRegUser) {
		this.isRegUser = isRegUser;
	}

	/**
	 * @return the interface
	 */
	public Interface getInterface() {
		return Interface;
	}

	/**
	 * @param interface1 the interface to set
	 */
	public void setInterface(Interface interface1) {
		Interface = interface1;
	}

	/**
	 * @return the interfaceCnt
	 */
	public int getInterfaceCnt() {
		return interfaceCnt;
	}

	/**
	 * @param interfaceCnt the interfaceCnt to set
	 */
	public void setInterfaceCnt(int interfaceCnt) {
		this.interfaceCnt = interfaceCnt;
	}

	/**
	 * @return the systemCd
	 */
	public String getSystemCd() {
		return systemCd;
	}

	/**
	 * @param systemCd the systemCd to set
	 */
	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}
	
	/**
	 * @return the systemNm
	 */
	public String getSystemNm() {
		return systemNm;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}	

	
}
