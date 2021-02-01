package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ParseSystemFieldRule.class),
        @JsonSubTypes.Type(value=LogRule.class),
        @JsonSubTypes.Type(value=RouteRule.class),
        @JsonSubTypes.Type(value=RouteByInterfaceRule.class),
        @JsonSubTypes.Type(value=UserDefineRule.class),
        @JsonSubTypes.Type(value=MonitorRule.class),
        @JsonSubTypes.Type(value=SourceSystemRule.class),
        @JsonSubTypes.Type(value=ParseRule.class),
        @JsonSubTypes.Type(value=RecoveryRule.class),
        @JsonSubTypes.Type(value=MapRule.class)
})
public class Rule extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7311009919455680788L;
	
	public final static int RULE_TYPE_SYSTEM_FIELD_PARSING = 0;
	public final static int RULE_TYPE_ROUTING = 1;
	public final static int RULE_TYPE_ROUTING_BY_INT = 2;
	public final static int RULE_TYPE_USER_DEFINE = 3;
	public final static int RULE_TYPE_MAPPING = 4;
	public final static int RULE_TYPE_PARSING = 5;
	public final static int RULE_TYPE_RECOVERY = 6;
	public final static int RULE_TYPE_LOGGING = 7;
	public final static int RULE_TYPE_MONITORING = 8;
	public final static int RULE_TYPE_SOURCE_SYSTEM = 9;
	
	
	@JsonProperty
	String name;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	int seq;
	
	@JsonProperty
	Object nextRuleSetId;
	
	@JsonProperty
	Object nextRuleId;
	
	@JsonProperty
	int nextRuleSeq;
	
	
	@JsonProperty
	boolean hasMonitor = false;

	@JsonProperty
	String description;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	/*public Rule(Object id, String name, int type){
		this.id = id;
		this.name = name;
		this.type = type;
	}*/

	public Rule(){
		Class<?> clazz = this.getClass();
		if(clazz == LogRule.class){
			this.type = RULE_TYPE_LOGGING; 
		}else if(clazz == MonitorRule.class){
			this.type = RULE_TYPE_MONITORING;
		}else if(clazz == ParseSystemFieldRule.class){
			this.type = RULE_TYPE_SYSTEM_FIELD_PARSING;
		}else if(clazz == RouteByInterfaceRule.class){
			this.type = RULE_TYPE_ROUTING_BY_INT;
		}else if(clazz == RouteRule.class){
			this.type = RULE_TYPE_ROUTING;
		}else if(clazz == SourceSystemRule.class){
			this.type = RULE_TYPE_SOURCE_SYSTEM;
		}else if(clazz == UserDefineRule.class){
			this.type = RULE_TYPE_USER_DEFINE;
		}else if(clazz == ParseRule.class){
			this.type = RULE_TYPE_PARSING;
		}else if(clazz == RecoveryRule.class){
			this.type = RULE_TYPE_RECOVERY;
		}else if(clazz == MapRule.class){
			this.type = RULE_TYPE_MAPPING;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Object getNextRuleSetId() {
		return nextRuleSetId;
	}

	public void setNextRuleSetId(Object nextRuleSetId) {
		this.nextRuleSetId = nextRuleSetId;
	}

	public Object getNextRuleId() {
		return nextRuleId;
	}

	public void setNextRuleId(Object nextRuleId) {
		this.nextRuleId = nextRuleId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHasMonitor() {
		return hasMonitor;
	}

	public void setHasMonitor(boolean hasMonitor) {
		this.hasMonitor = hasMonitor;
	}
	
	public String toString(){
		StringBuffer sb  = new StringBuffer();
		sb.append("{class:").append(this.getClass().getName()).append(",name:").append(name).append(",id:").append(id).append("}");
		return sb.toString();
	}
	
}
