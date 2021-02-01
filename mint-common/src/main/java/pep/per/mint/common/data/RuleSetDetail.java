package pep.per.mint.common.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RuleSetDetail{

	@JsonProperty
	Object ruleSetId;
	
	@JsonProperty
	Object ruleId;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	int seq;
	
	@JsonProperty
	Object nextRuleSetId;
	
	@JsonProperty
	Object nextRuleId;
	 	

	@JsonProperty
	int nextRuleType;
	 	

	@JsonProperty
	int nextRuleSeq;
	 	
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;

	/**
	 * @return the ruleSetId
	 */
	public Object getRuleSetId() {
		return ruleSetId;
	}

	/**
	 * @param ruleSetId the ruleSetId to set
	 */
	public void setRuleSetId(Object ruleSetId) {
		this.ruleSetId = ruleSetId;
	}

	/**
	 * @return the ruleId
	 */
	public Object getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(Object ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the nextRuleSetId
	 */
	public Object getNextRuleSetId() {
		return nextRuleSetId;
	}

	/**
	 * @param nextRuleSetId the nextRuleSetId to set
	 */
	public void setNextRuleSetId(Object nextRuleSetId) {
		this.nextRuleSetId = nextRuleSetId;
	}

	/**
	 * @return the nextRuleId
	 */
	public Object getNextRuleId() {
		return nextRuleId;
	}

	/**
	 * @param nextRuleId the nextRuleId to set
	 */
	public void setNextRuleId(Object nextRuleId) {
		this.nextRuleId = nextRuleId;
	}

	
	
	
	/**
	 * @return the nextRuleType
	 */
	public int getNextRuleType() {
		return nextRuleType;
	}

	/**
	 * @param nextRuleType the nextRuleType to set
	 */
	public void setNextRuleType(int nextRuleType) {
		this.nextRuleType = nextRuleType;
	}

	
	
	/**
	 * @return the nextRuleSeq
	 */
	public int getNextRuleSeq() {
		return nextRuleSeq;
	}

	/**
	 * @param nextRuleSeq the nextRuleSeq to set
	 */
	public void setNextRuleSeq(int nextRuleSeq) {
		this.nextRuleSeq = nextRuleSeq;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	 	
	
}
