/**
 * 
 */
package pep.per.mint.common.data.basic.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 통계 - 장애 발생율 통계 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class StatisticsFailureRate extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7808267686052866488L;

	
	@JsonProperty("categoryId")
	String categoryId;
	
	@JsonProperty("categoryNm")
	String categoryNm;
	
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
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryNm
	 */
	public String getCategoryNm() {
		return categoryNm;
	}

	/**
	 * @param categoryNm the categoryNm to set
	 */
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
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
