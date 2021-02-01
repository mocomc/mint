package pep.per.mint.common.data.basic.upload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadSummary extends CacheableObject{

	@JsonProperty
	String batchId = defaultStringValue;

	@JsonProperty
	int batchCount = 0;

	@JsonProperty
	int resultCount = 0;

	@JsonProperty
	String resultCd = defaultStringValue;

	@JsonProperty
	String resultMsg = defaultStringValue;

	@JsonProperty
	String fileName = defaultStringValue;

	@JsonProperty
	String filePath = defaultStringValue;

	/*** 설명 */
	@JsonProperty
	String comments = defaultStringValue;

	/*** 등록일 */
	@JsonProperty
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty
	String regId = defaultStringValue;

	@JsonProperty
	String regUserNm = defaultStringValue;

	/*** 수정일 */
	@JsonProperty
	String modDate = defaultStringValue;

	/*** 수정자 */
	@JsonProperty
	String modId = defaultStringValue;

	@JsonProperty
	String modUserNm = defaultStringValue;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}



	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getRegUserNm() {
		return regUserNm;
	}

	public void setRegUserNm(String regUserNm) {
		this.regUserNm = regUserNm;
	}

	public String getModUserNm() {
		return modUserNm;
	}

	public void setModUserNm(String modUserNm) {
		this.modUserNm = modUserNm;
	}




}
