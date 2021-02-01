/**
 * 
 */
package pep.per.mint.common.data.basic.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 통계 - 인터페이스 유형별 통계 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class StatisticsInterfaceType extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6139292383572667957L;

	
	@JsonProperty("dataProcessMethodCd")
	String dataProcessMethodCd;
	
	@JsonProperty("startResourceTypeCd")
	String startResourceTypeCd;
	
	@JsonProperty("endResourceTypeCd")
	String endResourceTypeCd;
	
	@JsonProperty("dataProcessMethodNm")
	String dataProcessMethodNm;
	
	@JsonProperty("startResourceTypeNm")
	String startResourceTypeNm;
	
	@JsonProperty("endResourceTypeNm")
	String endResourceTypeNm;
	
	@JsonProperty("interfaceId")
	String interfaceId;
	
	@JsonProperty("integrationId")
	String integrationId;
	
	@JsonProperty("interfaceNm")
	String interfaceNm;
	
	@JsonProperty("totalCnt")
	int totalCnt = 0;
	
	@JsonProperty("finishedCnt")
	int finishedCnt = 0;
	
	@JsonProperty("processingCnt")
	int processingCnt = 0;
	
	@JsonProperty("errorCnt")
	int errorCnt = 0;

	@JsonProperty("dataSize")
	long dataSize = 0;

	/**
	 * @return the dataProcessMethodCd
	 */
	public String getDataProcessMethodCd() {
		return dataProcessMethodCd;
	}

	/**
	 * @param dataProcessMethodCd the dataProcessMethodCd to set
	 */
	public void setDataProcessMethodCd(String dataProcessMethodCd) {
		this.dataProcessMethodCd = dataProcessMethodCd;
	}

	/**
	 * @return the startResourceTypeCd
	 */
	public String getStartResourceTypeCd() {
		return startResourceTypeCd;
	}

	/**
	 * @param startResourceTypeCd the startResourceTypeCd to set
	 */
	public void setStartResourceTypeCd(String startResourceTypeCd) {
		this.startResourceTypeCd = startResourceTypeCd;
	}

	/**
	 * @return the endResourceTypeCd
	 */
	public String getEndResourceTypeCd() {
		return endResourceTypeCd;
	}

	/**
	 * @param endResourceTypeCd the endResourceTypeCd to set
	 */
	public void setEndResourceTypeCd(String endResourceTypeCd) {
		this.endResourceTypeCd = endResourceTypeCd;
	}

	/**
	 * @return the dataProcessMethodNm
	 */
	public String getDataProcessMethodNm() {
		return dataProcessMethodNm;
	}

	/**
	 * @param dataProcessMethodNm the dataProcessMethodNm to set
	 */
	public void setDataProcessMethodNm(String dataProcessMethodNm) {
		this.dataProcessMethodNm = dataProcessMethodNm;
	}

	/**
	 * @return the startResourceTypeNm
	 */
	public String getStartResourceTypeNm() {
		return startResourceTypeNm;
	}

	/**
	 * @param startResourceTypeNm the startResourceTypeNm to set
	 */
	public void setStartResourceTypeNm(String startResourceTypeNm) {
		this.startResourceTypeNm = startResourceTypeNm;
	}

	/**
	 * @return the endResourceTypeNm
	 */
	public String getEndResourceTypeNm() {
		return endResourceTypeNm;
	}

	/**
	 * @param endResourceTypeNm the endResourceTypeNm to set
	 */
	public void setEndResourceTypeNm(String endResourceTypeNm) {
		this.endResourceTypeNm = endResourceTypeNm;
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
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return the finishedCnt
	 */
	public int getFinishedCnt() {
		return finishedCnt;
	}

	/**
	 * @param finishedCnt the finishedCnt to set
	 */
	public void setFinishedCnt(int finishedCnt) {
		this.finishedCnt = finishedCnt;
	}

	/**
	 * @return the processingCnt
	 */
	public int getProcessingCnt() {
		return processingCnt;
	}

	/**
	 * @param processingCnt the processingCnt to set
	 */
	public void setProcessingCnt(int processingCnt) {
		this.processingCnt = processingCnt;
	}

	/**
	 * @return the errorCnt
	 */
	public int getErrorCnt() {
		return errorCnt;
	}

	/**
	 * @param errorCnt the errorCnt to set
	 */
	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}

	/**
	 * @return the dataSize
	 */
	public long getDataSize() {
		return dataSize;
	}

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
	
	
}
