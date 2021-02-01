/**
 * 
 */
package pep.per.mint.common.data.basic.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 통계 - 인터페이스 기간별 통계 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class StatisticsInterfacePeriod extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4113770132833020130L;

	
	@JsonProperty("categoryId")
	String categoryId;
	
	@JsonProperty("categoryNm")
	String categoryNm;
	
	@JsonProperty("interfaceStatusType")
	String interfaceStatusType;
	
	@JsonProperty("cnt00")
	int cnt00 = 0;
	
	@JsonProperty("cnt01")
	int cnt01 = 0;
	
	@JsonProperty("cnt02")
	int cnt02 = 0;
	
	@JsonProperty("cnt03")
	int cnt03 = 0;
	
	@JsonProperty("cnt04")
	int cnt04 = 0;
	
	@JsonProperty("cnt05")
	int cnt05 = 0;
	
	@JsonProperty("cnt06")
	int cnt06 = 0;
	
	@JsonProperty("cnt07")
	int cnt07 = 0;
	
	@JsonProperty("cnt08")
	int cnt08 = 0;
	
	@JsonProperty("cnt09")
	int cnt09 = 0;
	
	@JsonProperty("cnt10")
	int cnt10 = 0;
	
	@JsonProperty("cnt11")
	int cnt11 = 0;
	
	@JsonProperty("cnt12")
	int cnt12 = 0;
	
	@JsonProperty("cnt13")
	int cnt13 = 0;
	
	@JsonProperty("cnt14")
	int cnt14 = 0;
	
	@JsonProperty("cnt15")
	int cnt15 = 0;
	
	@JsonProperty("cnt16")
	int cnt16 = 0;
	
	@JsonProperty("cnt17")
	int cnt17 = 0;
	
	@JsonProperty("cnt18")
	int cnt18 = 0;
	
	@JsonProperty("cnt19")
	int cnt19 = 0;
	
	@JsonProperty("cnt20")
	int cnt20 = 0;
	
	@JsonProperty("cnt21")
	int cnt21 = 0;
	
	@JsonProperty("cnt22")
	int cnt22 = 0;
	
	@JsonProperty("cnt23")
	int cnt23 = 0;
	
	@JsonProperty("cnt24")
	int cnt24 = 0;
	
	@JsonProperty("cnt25")
	int cnt25 = 0;
	
	@JsonProperty("cnt26")
	int cnt26 = 0;
	
	@JsonProperty("cnt27")
	int cnt27 = 0;
	
	@JsonProperty("cnt28")
	int cnt28 = 0;
	
	@JsonProperty("cnt29")
	int cnt29 = 0;
	
	@JsonProperty("cnt30")
	int cnt30 = 0;
	
	@JsonProperty("cnt31")
	int cnt31 = 0;
	
	@JsonProperty("rowSum")
	int rowSum = 0;

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
	 * @return the interfaceStatusType
	 */
	public String getInterfaceStatusType() {
		return interfaceStatusType;
	}

	/**
	 * @param interfaceStatusType the interfaceStatusType to set
	 */
	public void setInterfaceStatusType(String interfaceStatusType) {
		this.interfaceStatusType = interfaceStatusType;
	}

	/**
	 * @return the cnt00
	 */
	public int getCnt00() {
		return cnt00;
	}

	/**
	 * @param cnt00 the cnt00 to set
	 */
	public void setCnt00(int cnt00) {
		this.cnt00 = cnt00;
	}

	/**
	 * @return the cnt01
	 */
	public int getCnt01() {
		return cnt01;
	}

	/**
	 * @param cnt01 the cnt01 to set
	 */
	public void setCnt01(int cnt01) {
		this.cnt01 = cnt01;
	}

	/**
	 * @return the cnt02
	 */
	public int getCnt02() {
		return cnt02;
	}

	/**
	 * @param cnt02 the cnt02 to set
	 */
	public void setCnt02(int cnt02) {
		this.cnt02 = cnt02;
	}

	/**
	 * @return the cnt03
	 */
	public int getCnt03() {
		return cnt03;
	}

	/**
	 * @param cnt03 the cnt03 to set
	 */
	public void setCnt03(int cnt03) {
		this.cnt03 = cnt03;
	}

	/**
	 * @return the cnt04
	 */
	public int getCnt04() {
		return cnt04;
	}

	/**
	 * @param cnt04 the cnt04 to set
	 */
	public void setCnt04(int cnt04) {
		this.cnt04 = cnt04;
	}

	/**
	 * @return the cnt05
	 */
	public int getCnt05() {
		return cnt05;
	}

	/**
	 * @param cnt05 the cnt05 to set
	 */
	public void setCnt05(int cnt05) {
		this.cnt05 = cnt05;
	}

	/**
	 * @return the cnt06
	 */
	public int getCnt06() {
		return cnt06;
	}

	/**
	 * @param cnt06 the cnt06 to set
	 */
	public void setCnt06(int cnt06) {
		this.cnt06 = cnt06;
	}

	/**
	 * @return the cnt07
	 */
	public int getCnt07() {
		return cnt07;
	}

	/**
	 * @param cnt07 the cnt07 to set
	 */
	public void setCnt07(int cnt07) {
		this.cnt07 = cnt07;
	}

	/**
	 * @return the cnt08
	 */
	public int getCnt08() {
		return cnt08;
	}

	/**
	 * @param cnt08 the cnt08 to set
	 */
	public void setCnt08(int cnt08) {
		this.cnt08 = cnt08;
	}

	/**
	 * @return the cnt09
	 */
	public int getCnt09() {
		return cnt09;
	}

	/**
	 * @param cnt09 the cnt09 to set
	 */
	public void setCnt09(int cnt09) {
		this.cnt09 = cnt09;
	}

	/**
	 * @return the cnt10
	 */
	public int getCnt10() {
		return cnt10;
	}

	/**
	 * @param cnt10 the cnt10 to set
	 */
	public void setCnt10(int cnt10) {
		this.cnt10 = cnt10;
	}

	/**
	 * @return the cnt11
	 */
	public int getCnt11() {
		return cnt11;
	}

	/**
	 * @param cnt11 the cnt11 to set
	 */
	public void setCnt11(int cnt11) {
		this.cnt11 = cnt11;
	}

	/**
	 * @return the cnt12
	 */
	public int getCnt12() {
		return cnt12;
	}

	/**
	 * @param cnt12 the cnt12 to set
	 */
	public void setCnt12(int cnt12) {
		this.cnt12 = cnt12;
	}

	/**
	 * @return the cnt13
	 */
	public int getCnt13() {
		return cnt13;
	}

	/**
	 * @param cnt13 the cnt13 to set
	 */
	public void setCnt13(int cnt13) {
		this.cnt13 = cnt13;
	}

	/**
	 * @return the cnt14
	 */
	public int getCnt14() {
		return cnt14;
	}

	/**
	 * @param cnt14 the cnt14 to set
	 */
	public void setCnt14(int cnt14) {
		this.cnt14 = cnt14;
	}

	/**
	 * @return the cnt15
	 */
	public int getCnt15() {
		return cnt15;
	}

	/**
	 * @param cnt15 the cnt15 to set
	 */
	public void setCnt15(int cnt15) {
		this.cnt15 = cnt15;
	}

	/**
	 * @return the cnt16
	 */
	public int getCnt16() {
		return cnt16;
	}

	/**
	 * @param cnt16 the cnt16 to set
	 */
	public void setCnt16(int cnt16) {
		this.cnt16 = cnt16;
	}

	/**
	 * @return the cnt17
	 */
	public int getCnt17() {
		return cnt17;
	}

	/**
	 * @param cnt17 the cnt17 to set
	 */
	public void setCnt17(int cnt17) {
		this.cnt17 = cnt17;
	}

	/**
	 * @return the cnt18
	 */
	public int getCnt18() {
		return cnt18;
	}

	/**
	 * @param cnt18 the cnt18 to set
	 */
	public void setCnt18(int cnt18) {
		this.cnt18 = cnt18;
	}

	/**
	 * @return the cnt19
	 */
	public int getCnt19() {
		return cnt19;
	}

	/**
	 * @param cnt19 the cnt19 to set
	 */
	public void setCnt19(int cnt19) {
		this.cnt19 = cnt19;
	}

	/**
	 * @return the cnt20
	 */
	public int getCnt20() {
		return cnt20;
	}

	/**
	 * @param cnt20 the cnt20 to set
	 */
	public void setCnt20(int cnt20) {
		this.cnt20 = cnt20;
	}

	/**
	 * @return the cnt21
	 */
	public int getCnt21() {
		return cnt21;
	}

	/**
	 * @param cnt21 the cnt21 to set
	 */
	public void setCnt21(int cnt21) {
		this.cnt21 = cnt21;
	}

	/**
	 * @return the cnt22
	 */
	public int getCnt22() {
		return cnt22;
	}

	/**
	 * @param cnt22 the cnt22 to set
	 */
	public void setCnt22(int cnt22) {
		this.cnt22 = cnt22;
	}

	/**
	 * @return the cnt23
	 */
	public int getCnt23() {
		return cnt23;
	}

	/**
	 * @param cnt23 the cnt23 to set
	 */
	public void setCnt23(int cnt23) {
		this.cnt23 = cnt23;
	}

	/**
	 * @return the cnt24
	 */
	public int getCnt24() {
		return cnt24;
	}

	/**
	 * @param cnt24 the cnt24 to set
	 */
	public void setCnt24(int cnt24) {
		this.cnt24 = cnt24;
	}

	/**
	 * @return the cnt25
	 */
	public int getCnt25() {
		return cnt25;
	}

	/**
	 * @param cnt25 the cnt25 to set
	 */
	public void setCnt25(int cnt25) {
		this.cnt25 = cnt25;
	}

	/**
	 * @return the cnt26
	 */
	public int getCnt26() {
		return cnt26;
	}

	/**
	 * @param cnt26 the cnt26 to set
	 */
	public void setCnt26(int cnt26) {
		this.cnt26 = cnt26;
	}

	/**
	 * @return the cnt27
	 */
	public int getCnt27() {
		return cnt27;
	}

	/**
	 * @param cnt27 the cnt27 to set
	 */
	public void setCnt27(int cnt27) {
		this.cnt27 = cnt27;
	}

	/**
	 * @return the cnt28
	 */
	public int getCnt28() {
		return cnt28;
	}

	/**
	 * @param cnt28 the cnt28 to set
	 */
	public void setCnt28(int cnt28) {
		this.cnt28 = cnt28;
	}

	/**
	 * @return the cnt29
	 */
	public int getCnt29() {
		return cnt29;
	}

	/**
	 * @param cnt29 the cnt29 to set
	 */
	public void setCnt29(int cnt29) {
		this.cnt29 = cnt29;
	}

	/**
	 * @return the cnt30
	 */
	public int getCnt30() {
		return cnt30;
	}

	/**
	 * @param cnt30 the cnt30 to set
	 */
	public void setCnt30(int cnt30) {
		this.cnt30 = cnt30;
	}

	/**
	 * @return the cnt31
	 */
	public int getCnt31() {
		return cnt31;
	}

	/**
	 * @param cnt31 the cnt31 to set
	 */
	public void setCnt31(int cnt31) {
		this.cnt31 = cnt31;
	}

	/**
	 * @return the rowSum
	 */
	public int getRowSum() {
		return rowSum;
	}

	/**
	 * @param rowSum the rowSum to set
	 */
	public void setRowSum(int rowSum) {
		this.rowSum = rowSum;
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
