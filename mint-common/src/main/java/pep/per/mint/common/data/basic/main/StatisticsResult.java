/**
 * 
 */
package pep.per.mint.common.data.basic.main;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class StatisticsResult extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377951484678813495L;

	@JsonProperty("st_today")
	List<Statistics> st_today;
	
	@JsonProperty("st_yesterday")
	List<Statistics> st_yesterday;

	public List<Statistics> getSt_today() {
		return st_today;
	}

	public void setSt_today(List<Statistics> st_today) {
		this.st_today = st_today;
	}

	public List<Statistics> getSt_yesterday() {
		return st_yesterday;
	}

	public void setSt_yesterday(List<Statistics> st_yesterday) {
		this.st_yesterday = st_yesterday;
	}
}
