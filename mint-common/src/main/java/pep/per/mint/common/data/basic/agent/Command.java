package pep.per.mint.common.data.basic.agent;

import java.util.Map;

//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Command extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	String commandId = defaultStringValue;

	@JsonProperty
	String commandCd = defaultStringValue;

	@JsonProperty
	String commandNm =defaultStringValue;

	@JsonProperty
	String params = defaultStringValue;

	@JsonProperty
	String comments = defaultStringValue;

	@JsonProperty
	String delYn = "N";

	@JsonProperty
	String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

	@JsonProperty
	String regUser = defaultStringValue;

	@JsonProperty
	String modDate = defaultStringValue;

	@JsonProperty
	String modUser = defaultStringValue;

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandCd() {
		return commandCd;
	}

	public void setCommandCd(String commandCd) {
		this.commandCd = commandCd;
	}

	public String getCommandNm() {
		return commandNm;
	}

	public void setCommandNm(String commandNm) {
		this.commandNm = commandNm;
	}



	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}


}
