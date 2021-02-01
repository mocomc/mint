/**
 * 
 */
package pep.per.mint.common.data.basic.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SolutionsStatus extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690886032873538634L;
	
	

	@JsonProperty("solutionsNm")
	String solutionsNm;
	
	@JsonProperty("processDate")
	String processDate;
	
	@JsonProperty("processCount")
	String processCount;
	
	@JsonProperty("processTime")
	String processTime;
	
//	
//	@JsonProperty("processCount00")
//	String processCount00;
//	
//	@JsonProperty("processCount01")
//	String processCount01;
//	
//	@JsonProperty("processCount02")
//	String processCount02;
//	
//	@JsonProperty("processCount03")
//	String processCount03;
//	
//	@JsonProperty("processCount04")
//	String processCount04;
//	
//	@JsonProperty("processCount05")
//	String processCount05;
//	
//	@JsonProperty("processCount06")
//	String processCount06;
//	
//	@JsonProperty("processCount07")
//	String processCount07;
//	
//	@JsonProperty("processCount08")
//	String processCount08;
//	
//	@JsonProperty("processCount09")
//	String processCount09;
//	
//	@JsonProperty("processCount10")
//	String processCount10;
//	
//	@JsonProperty("processCount11")
//	String processCount11;
//	
//	@JsonProperty("processCount12")
//	String processCount12;
//	
//	@JsonProperty("processCount13")
//	String processCount13;
//	
//	@JsonProperty("processCount14")
//	String processCount14;
//	
//	@JsonProperty("processCount15")
//	String processCount15;
//	
//	@JsonProperty("processCount16")
//	String processCount16;
//	
//	@JsonProperty("processCount17")
//	String processCount17;
//	
//	@JsonProperty("processCount18")
//	String processCount18;
//	
//	@JsonProperty("processCount19")
//	String processCount19;
//	
//	@JsonProperty("processCount20")
//	String processCount20;
//	
//	@JsonProperty("processCount21")
//	String processCount21;
//	
//	@JsonProperty("processCount22")
//	String processCount22;
//	
//	@JsonProperty("processCount23")
//	String processCount23;
	
	/**
	 * @return the processCount
	 */
	public String getProcessCount() {
		return processCount;
	}

	/**
	 * @param processCount the processCount to set
	 */
	public void setProcessCount(String processCount) {
		this.processCount = processCount;
	}

	/**
	 * @return the processTime
	 */
	public String getProcessTime() {
		return processTime;
	}

	/**
	 * @param processTime the processTime to set
	 */
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	/**
	 * @return the solutionsNm
	 */
	public String getSolutionsNm() {
		return solutionsNm;
	}

	/**
	 * @param solutionsNm the solutionsNm to set
	 */
	public void setSolutionsNm(String solutionsNm) {
		this.solutionsNm = solutionsNm;
	}

	/**
	 * @return the processDate
	 */
	public String getProcessDate() {
		return processDate;
	}

	/**
	 * @param processDate the processDate to set
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

//	/**
//	 * @return the processCount00
//	 */
//	public String getProcessCount00() {
//		return processCount00;
//	}
//
//	/**
//	 * @param processCount00 the processCount00 to set
//	 */
//	public void setProcessCount00(String processCount00) {
//		this.processCount00 = processCount00;
//	}
//
//	/**
//	 * @return the processCount01
//	 */
//	public String getProcessCount01() {
//		return processCount01;
//	}
//
//	/**
//	 * @param processCount01 the processCount01 to set
//	 */
//	public void setProcessCount01(String processCount01) {
//		this.processCount01 = processCount01;
//	}
//
//	/**
//	 * @return the processCount02
//	 */
//	public String getProcessCount02() {
//		return processCount02;
//	}
//
//	/**
//	 * @param processCount02 the processCount02 to set
//	 */
//	public void setProcessCount02(String processCount02) {
//		this.processCount02 = processCount02;
//	}
//
//	/**
//	 * @return the processCount03
//	 */
//	public String getProcessCount03() {
//		return processCount03;
//	}
//
//	/**
//	 * @param processCount03 the processCount03 to set
//	 */
//	public void setProcessCount03(String processCount03) {
//		this.processCount03 = processCount03;
//	}
//
//	/**
//	 * @return the processCount04
//	 */
//	public String getProcessCount04() {
//		return processCount04;
//	}
//
//	/**
//	 * @param processCount04 the processCount04 to set
//	 */
//	public void setProcessCount04(String processCount04) {
//		this.processCount04 = processCount04;
//	}
//
//	/**
//	 * @return the processCount05
//	 */
//	public String getProcessCount05() {
//		return processCount05;
//	}
//
//	/**
//	 * @param processCount05 the processCount05 to set
//	 */
//	public void setProcessCount05(String processCount05) {
//		this.processCount05 = processCount05;
//	}
//
//	/**
//	 * @return the processCount06
//	 */
//	public String getProcessCount06() {
//		return processCount06;
//	}
//
//	/**
//	 * @param processCount06 the processCount06 to set
//	 */
//	public void setProcessCount06(String processCount06) {
//		this.processCount06 = processCount06;
//	}
//
//	/**
//	 * @return the processCount07
//	 */
//	public String getProcessCount07() {
//		return processCount07;
//	}
//
//	/**
//	 * @param processCount07 the processCount07 to set
//	 */
//	public void setProcessCount07(String processCount07) {
//		this.processCount07 = processCount07;
//	}
//
//	/**
//	 * @return the processCount08
//	 */
//	public String getProcessCount08() {
//		return processCount08;
//	}
//
//	/**
//	 * @param processCount08 the processCount08 to set
//	 */
//	public void setProcessCount08(String processCount08) {
//		this.processCount08 = processCount08;
//	}
//
//	/**
//	 * @return the processCount09
//	 */
//	public String getProcessCount09() {
//		return processCount09;
//	}
//
//	/**
//	 * @param processCount09 the processCount09 to set
//	 */
//	public void setProcessCount09(String processCount09) {
//		this.processCount09 = processCount09;
//	}
//
//	/**
//	 * @return the processCount10
//	 */
//	public String getProcessCount10() {
//		return processCount10;
//	}
//
//	/**
//	 * @param processCount10 the processCount10 to set
//	 */
//	public void setProcessCount10(String processCount10) {
//		this.processCount10 = processCount10;
//	}
//
//	/**
//	 * @return the processCount11
//	 */
//	public String getProcessCount11() {
//		return processCount11;
//	}
//
//	/**
//	 * @param processCount11 the processCount11 to set
//	 */
//	public void setProcessCount11(String processCount11) {
//		this.processCount11 = processCount11;
//	}
//
//	/**
//	 * @return the processCount12
//	 */
//	public String getProcessCount12() {
//		return processCount12;
//	}
//
//	/**
//	 * @param processCount12 the processCount12 to set
//	 */
//	public void setProcessCount12(String processCount12) {
//		this.processCount12 = processCount12;
//	}
//
//	/**
//	 * @return the processCount13
//	 */
//	public String getProcessCount13() {
//		return processCount13;
//	}
//
//	/**
//	 * @param processCount13 the processCount13 to set
//	 */
//	public void setProcessCount13(String processCount13) {
//		this.processCount13 = processCount13;
//	}
//
//	/**
//	 * @return the processCount14
//	 */
//	public String getProcessCount14() {
//		return processCount14;
//	}
//
//	/**
//	 * @param processCount14 the processCount14 to set
//	 */
//	public void setProcessCount14(String processCount14) {
//		this.processCount14 = processCount14;
//	}
//
//	/**
//	 * @return the processCount15
//	 */
//	public String getProcessCount15() {
//		return processCount15;
//	}
//
//	/**
//	 * @param processCount15 the processCount15 to set
//	 */
//	public void setProcessCount15(String processCount15) {
//		this.processCount15 = processCount15;
//	}
//
//	/**
//	 * @return the processCount16
//	 */
//	public String getProcessCount16() {
//		return processCount16;
//	}
//
//	/**
//	 * @param processCount16 the processCount16 to set
//	 */
//	public void setProcessCount16(String processCount16) {
//		this.processCount16 = processCount16;
//	}
//
//	/**
//	 * @return the processCount17
//	 */
//	public String getProcessCount17() {
//		return processCount17;
//	}
//
//	/**
//	 * @param processCount17 the processCount17 to set
//	 */
//	public void setProcessCount17(String processCount17) {
//		this.processCount17 = processCount17;
//	}
//
//	/**
//	 * @return the processCount18
//	 */
//	public String getProcessCount18() {
//		return processCount18;
//	}
//
//	/**
//	 * @param processCount18 the processCount18 to set
//	 */
//	public void setProcessCount18(String processCount18) {
//		this.processCount18 = processCount18;
//	}
//
//	/**
//	 * @return the processCount19
//	 */
//	public String getProcessCount19() {
//		return processCount19;
//	}
//
//	/**
//	 * @param processCount19 the processCount19 to set
//	 */
//	public void setProcessCount19(String processCount19) {
//		this.processCount19 = processCount19;
//	}
//
//	/**
//	 * @return the processCount20
//	 */
//	public String getProcessCount20() {
//		return processCount20;
//	}
//
//	/**
//	 * @param processCount20 the processCount20 to set
//	 */
//	public void setProcessCount20(String processCount20) {
//		this.processCount20 = processCount20;
//	}
//
//	/**
//	 * @return the processCount21
//	 */
//	public String getProcessCount21() {
//		return processCount21;
//	}
//
//	/**
//	 * @param processCount21 the processCount21 to set
//	 */
//	public void setProcessCount21(String processCount21) {
//		this.processCount21 = processCount21;
//	}
//
//	/**
//	 * @return the processCount22
//	 */
//	public String getProcessCount22() {
//		return processCount22;
//	}
//
//	/**
//	 * @param processCount22 the processCount22 to set
//	 */
//	public void setProcessCount22(String processCount22) {
//		this.processCount22 = processCount22;
//	}
//
//	/**
//	 * @return the processCount23
//	 */
//	public String getProcessCount23() {
//		return processCount23;
//	}
//
//	/**
//	 * @param processCount23 the processCount23 to set
//	 */
//	public void setProcessCount23(String processCount23) {
//		this.processCount23 = processCount23;
//	}
	
}
