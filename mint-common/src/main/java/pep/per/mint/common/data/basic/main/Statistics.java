/**
 * 
 */
package pep.per.mint.common.data.basic.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Statistics extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377951484678813495L;

	@JsonProperty("year")
	String year;
	
	@JsonProperty("month")
	String month;
	
	@JsonProperty("day")
	String day;
	
	@JsonProperty("hour")
	String hour;
	
	@JsonProperty("tCnt")
	int tCnt;
	
	@JsonProperty("eCnt")
	int eCnt;
	
	@JsonProperty("eCntReg")
	int eCntReg;
	
	@JsonProperty("eCntNoreg")
	int eCntNoreg;
	
	@JsonProperty("ePt")
	float ePt;
	
	@JsonProperty("codeName")
	String codeName;
	
	@JsonProperty("cnt")
	int cnt;
	
	@JsonProperty("devCnt")
	int devCnt;
	
	@JsonProperty("testCnt")
	int testCnt;
	
	@JsonProperty("realCnt")
	int realCnt;
	
	@JsonProperty("comCnt")
	int comCnt;

	public int getDevCnt() {
		return devCnt;
	}

	public void setDevCnt(int devCnt) {
		this.devCnt = devCnt;
	}

	public int getTestCnt() {
		return testCnt;
	}

	public void setTestCnt(int testCnt) {
		this.testCnt = testCnt;
	}

	public int getRealCnt() {
		return realCnt;
	}

	public void setRealCnt(int realCnt) {
		this.realCnt = realCnt;
	}

	public int getComCnt() {
		return comCnt;
	}

	public void setComCnt(int comCnt) {
		this.comCnt = comCnt;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int gettCnt() {
		return tCnt;
	}

	public void settCnt(int tCnt) {
		this.tCnt = tCnt;
	}

	public int geteCnt() {
		return eCnt;
	}

	public void seteCnt(int eCnt) {
		this.eCnt = eCnt;
	}

	/**
	 * @return the eCntReg
	 */
	public int geteCntReg() {
		return eCntReg;
	}

	/**
	 * @param eCntReg the eCntReg to set
	 */
	public void seteCntReg(int eCntReg) {
		this.eCntReg = eCntReg;
	}

	/**
	 * @return the eCntNoreg
	 */
	public int geteCntNoreg() {
		return eCntNoreg;
	}

	/**
	 * @param eCntNoreg the eCntNoreg to set
	 */
	public void seteCntNoreg(int eCntNoreg) {
		this.eCntNoreg = eCntNoreg;
	}

	public float getePt() {
		return ePt;
	}

	public void setePt(float ePt) {
		this.ePt = ePt;
	}
}
