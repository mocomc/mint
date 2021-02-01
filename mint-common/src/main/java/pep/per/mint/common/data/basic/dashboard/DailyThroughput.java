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
public class DailyThroughput extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909412269653369087L;
	
	@JsonProperty("overallThroughput")
	String overallThroughput;
	
	@JsonProperty("overallThroughput_yesterday")
	String overallThroughput_yesterday;
	
	@JsonProperty("overallThroughput_prevMonth")
	String overallThroughput_prevMonth;
	
	@JsonProperty("overallThroughput_prevMonth_AVG")
	String overallThroughput_prevMonth_AVG;
	
	@JsonProperty("overallThroughput_prevWeek")
	String overallThroughput_prevWeek;
	
	@JsonProperty("overallThroughput_prevWeek_AVG")
	String overallThroughput_prevWeek_AVG;
	
	@JsonProperty("overallThroughput_rate")
	String overallThroughput_rate;
	
	
	@JsonProperty("completeThroughput")
	String completeThroughput;
	
	@JsonProperty("errorThroughput")
	String errorThroughput;
	
	@JsonProperty("pendingThroughput")
	String pendingThroughput;
	
	

	/**
	 * @return the overallThroughput
	 */
	public String getOverallThroughput() {
		return overallThroughput;
	}

	/**
	 * @param overallThroughput the overallThroughput to set
	 */
	public void setOverallThroughput(String overallThroughput) {
		this.overallThroughput = overallThroughput;
	}

	/**
	 * @return the overallThroughput_yesterday
	 */
	public String getOverallThroughput_yesterday() {
		return overallThroughput_yesterday;
	}

	/**
	 * @param overallThroughput_yesterday the overallThroughput_yesterday to set
	 */
	public void setOverallThroughput_yesterday(String overallThroughput_yesterday) {
		this.overallThroughput_yesterday = overallThroughput_yesterday;
	}

	/**
	 * @return the overallThroughput_prevMonth
	 */
	public String getOverallThroughput_prevMonth() {
		return overallThroughput_prevMonth;
	}

	/**
	 * @param overallThroughput_prevMonth the overallThroughput_prevMonth to set
	 */
	public void setOverallThroughput_prevMonth(String overallThroughput_prevMonth) {
		this.overallThroughput_prevMonth = overallThroughput_prevMonth;
	}

	/**
	 * @return the overallThroughput_prevWeek
	 */
	public String getOverallThroughput_prevWeek() {
		return overallThroughput_prevWeek;
	}

	/**
	 * @param overallThroughput_prevWeek the overallThroughput_prevWeek to set
	 */
	public void setOverallThroughput_prevWeek(String overallThroughput_prevWeek) {
		this.overallThroughput_prevWeek = overallThroughput_prevWeek;
	}

	/**
	 * @return the overallThroughput_prevMonth_AVG
	 */
	public String getOverallThroughput_prevMonth_AVG() {
		return overallThroughput_prevMonth_AVG;
	}

	/**
	 * @param overallThroughput_prevMonth_AVG the overallThroughput_prevMonth_AVG to set
	 */
	public void setOverallThroughput_prevMonth_AVG(
			String overallThroughput_prevMonth_AVG) {
		this.overallThroughput_prevMonth_AVG = overallThroughput_prevMonth_AVG;
	}

	/**
	 * @return the overallThroughput_prevWeek_AVG
	 */
	public String getOverallThroughput_prevWeek_AVG() {
		return overallThroughput_prevWeek_AVG;
	}

	/**
	 * @param overallThroughput_prevWeek_AVG the overallThroughput_prevWeek_AVG to set
	 */
	public void setOverallThroughput_prevWeek_AVG(
			String overallThroughput_prevWeek_AVG) {
		this.overallThroughput_prevWeek_AVG = overallThroughput_prevWeek_AVG;
	}

	/**
	 * @return the overallThroughput_rate
	 */
	public String getOverallThroughput_rate() {
		return overallThroughput_rate;
	}

	/**
	 * @param overallThroughput_rate the overallThroughput_rate to set
	 */
	public void setOverallThroughput_rate(String overallThroughput_rate) {
		this.overallThroughput_rate = overallThroughput_rate;
	}

	/**
	 * @return the completeThroughput
	 */
	public String getCompleteThroughput() {
		return completeThroughput;
	}

	/**
	 * @param completeThroughput the completeThroughput to set
	 */
	public void setCompleteThroughput(String completeThroughput) {
		this.completeThroughput = completeThroughput;
	}

	/**
	 * @return the errorThroughput
	 */
	public String getErrorThroughput() {
		return errorThroughput;
	}

	/**
	 * @param errorThroughput the errorThroughput to set
	 */
	public void setErrorThroughput(String errorThroughput) {
		this.errorThroughput = errorThroughput;
	}

	/**
	 * @return the pendingThroughput
	 */
	public String getPendingThroughput() {
		return pendingThroughput;
	}

	/**
	 * @param pendingThroughput the pendingThroughput to set
	 */
	public void setPendingThroughput(String pendingThroughput) {
		this.pendingThroughput = pendingThroughput;
	}

}
