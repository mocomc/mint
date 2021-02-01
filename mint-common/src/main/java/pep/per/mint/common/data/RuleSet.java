package pep.per.mint.common.data;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RuleSet extends CacheableObject{
	
	public static final int RULESET_TYPE_REQ = 0;
	public static final int RULESET_TYPE_RES = 1;
	public static final int RULESET_TYPE_NON = 99;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087000326194773277L;

	@JsonProperty
	String name;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	String description = "";
 	
	@JsonProperty
	List<Rule> ruleList;
	
	@JsonIgnore
	Map<String, Rule> ruleMap;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	
	/**
	 * @return the ruleMap
	 */
	public Map<String, Rule> getRuleMap() {
		return ruleMap;
	}

	/**
	 * @param ruleMap the ruleMap to set
	 */
	public void setRuleMap(Map<String, Rule> ruleMap) {
		this.ruleMap = ruleMap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}
	
	
	

}
