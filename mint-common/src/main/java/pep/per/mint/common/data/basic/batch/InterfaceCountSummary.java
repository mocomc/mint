package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceCountSummary extends CacheableObject {

	@JsonProperty
	private String integrationId = "";
	@JsonProperty
	private String requirementName = "";
	@JsonProperty
	private int todayTotal = 0;
	@JsonProperty
	private int todayCurHour = 0;
	@JsonProperty
	private int yesterdayTotal = 0;
	@JsonProperty
	private int yesterdayCurHour = 0;
	@JsonProperty
	private String subject = "";
	@JsonProperty
	private String content = "";
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIntegrationId() {
		return integrationId;
	}
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public int getTodayTotal() {
		return todayTotal;
	}
	public void setTodayTotal(int todayTotal) {
		this.todayTotal = todayTotal;
	}
	public int getTodayCurHour() {
		return todayCurHour;
	}
	public void setTodayCurHour(int todayCurHour) {
		this.todayCurHour = todayCurHour;
	}
	public int getYesterdayTotal() {
		return yesterdayTotal;
	}
	public void setYesterdayTotal(int yesterdayTotal) {
		this.yesterdayTotal = yesterdayTotal;
	}
	public int getYesterdayCurHour() {
		return yesterdayCurHour;
	}
	public void setYesterdayCurHour(int yesterdayCurHour) {
		this.yesterdayCurHour = yesterdayCurHour;
	}
	
}
