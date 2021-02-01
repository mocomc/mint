/**
 * 
 */
package pep.per.mint.common.data.basic.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 통계 - 장애 유형별 통계 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class StatisticsFailureType extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1959432527867008736L;

	
	
	@JsonProperty("level1")
	String level1;
	
	@JsonProperty("level2")
	String level2;
	
	@JsonProperty("classCd")
	String classCd;
	
	@JsonProperty("failureTypeCd")
	String failureTypeCd;
	
	@JsonProperty("failureTypeNm")
	String failureTypeNm;
	
	@JsonProperty("failureTypeDepth")
	String failureTypeDepth;
	
	@JsonProperty("failureTypeCnt")
	int failureTypeCnt = 0;

	
	
	/**
	 * @return the level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * @return the level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
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
	 * @return the failureTypeCd
	 */
	public String getFailureTypeCd() {
		return failureTypeCd;
	}

	/**
	 * @param failureTypeCd the failureTypeCd to set
	 */
	public void setFailureTypeCd(String failureTypeCd) {
		this.failureTypeCd = failureTypeCd;
	}

	/**
	 * @return the failureTypeNm
	 */
	public String getFailureTypeNm() {
		return failureTypeNm;
	}

	/**
	 * @param failureTypeNm the failureTypeNm to set
	 */
	public void setFailureTypeNm(String failureTypeNm) {
		this.failureTypeNm = failureTypeNm;
	}

	/**
	 * @return the faulureTypeDepth
	 */
	public String getFailureTypeDepth() {
		return failureTypeDepth;
	}

	/**
	 * @param faulureTypeDepth the faulureTypeDepth to set
	 */
	public void setFailureTypeDepth(String failureTypeDepth) {
		this.failureTypeDepth = failureTypeDepth;
	}

	/**
	 * @return the failureTypeCnt
	 */
	public int getFailureTypeCnt() {
		return failureTypeCnt;
	}

	/**
	 * @param failureTypeCnt the failureTypeCnt to set
	 */
	public void setFailureTypeCnt(int failureTypeCnt) {
		this.failureTypeCnt = failureTypeCnt;
	}
	
	
	
}
