package pep.per.mint.common.data.basic.agent;

//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class CommandConsole extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 8612689932888128018L;

	@Deprecated
	public static final  String OK = "0";

	@Deprecated
	public static final String FAIL = "-1";


	public static final  String RS_INIT = "0";

	public static final  String RS_OK = "1";

	public static final  String RS_ING = "8";

	public static final String RS_FAIL = "9";

	public static final  String RS_MSG_INIT = "등록";

	public static final  String RS_MSG_OK = "성공";

	public static final  String RS_MSG_ING = "처리중";

	public static final String RS_MSG_FAIL = "실패";


	@JsonProperty
	String agentId = defaultStringValue;

	@JsonProperty
	String commandId = defaultStringValue;

	@JsonProperty
	int seq;

	@JsonProperty
	String paramValue = defaultStringValue;

	@JsonProperty
	Command command = null;

	@JsonProperty
	String commandDate = defaultStringValue;

	@JsonProperty
	String executeDate = defaultStringValue;

	@JsonProperty
	String resultCd = defaultStringValue;

	@JsonProperty
	String resultMsg = defaultStringValue;

	@JsonProperty
	String resultDate = defaultStringValue;

	@JsonProperty
	String regUser = defaultStringValue;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getCommandDate() {
		return commandDate;
	}

	public void setCommandDate(String commandDate) {
		this.commandDate = commandDate;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}



}
