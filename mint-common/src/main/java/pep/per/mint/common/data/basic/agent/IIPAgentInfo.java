package pep.per.mint.common.data.basic.agent;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.Service;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class IIPAgentInfo extends CacheableObject{

	public static final String LOG_LEVEL_DEBUG = "DEBUG";

	public static final int DEFAULT_AGENT_PORT = 58080;

	public static final String IIP_AGENT_SERVICE_GET_AGENT_INFO = "IIP_AGENT_SERVICE_GET_AGENT_INFO";
	public static final String IIP_AGENT_SERVICE_RESOURCE_CHECK = "IIP_AGENT_SERVICE_RESOURCE_CHECK";
	public static final String IIP_AGENT_SERVICE_PROCESS_CHECK  = "IIP_AGENT_SERVICE_PROCESS_CHECK";
	public static final String IIP_AGENT_SERVICE_QMGR_CHECK = "IIP_AGENT_SERVICE_QMGR_CHECK";
	public static final String IIP_AGENT_SERVICE_GET_CMD = "IIP_AGENT_SERVICE_GET_CMD";
	public static final String IIP_AGENT_SERVICE_SND_CMD_RESULT = "IIP_AGENT_SERVICE_SND_CMD_RESULT";

	public static final String AGENT_STAT_LOGOFF = "0";
	public static final String AGENT_STAT_LOGON  = "1";
	public static final String AGENT_STAT_PAUSE  = "3";
	public static final String AGENT_STAT_ERROR  = "9";

	public static final String CMD_CD_DEPLOY = "DEP";
	public static final String CMD_CD_PAUSE = "PAS";
	public static final String CMD_CD_RESUME = "RSM";
	public static final String CMD_CD_CHECK_ALIVE = "CHK";
	public static final String CMD_CD_RELOAD_INFO = "RLD";
	public static final String CMD_CD_EXIT = "EXT";


	/**
	 *
	 */
	private static final long serialVersionUID = -7274413713080639951L;


	@JsonProperty
	String agentId = defaultStringValue;


	@JsonProperty
	String type  = "0";

	@JsonProperty
	int logDelay  = 10;

	@JsonProperty
	Server server = null;

	@JsonProperty
	List<MonitorItem> monitorItems = null;

	@JsonProperty
	Map<String, Service> agentServiceMap = null;

	@JsonProperty
	String agentCd  = defaultStringValue;

	@JsonProperty
	String agentNm  = defaultStringValue;

	@JsonProperty
	String spContextRoot  = defaultStringValue;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	String spWebSocketUrl  = null;

	@JsonProperty
	String spIp  = defaultStringValue;

	@JsonProperty
	String spPort  = defaultStringValue;

	@JsonProperty
	String spProtocol  = defaultStringValue;


	@JsonProperty
	String status  = AGENT_STAT_LOGOFF;

	@JsonProperty
	String hostname  = defaultStringValue;

	@JsonProperty
	int port  = DEFAULT_AGENT_PORT;

	@JsonProperty
	String logLevel  = LOG_LEVEL_DEBUG;

	@JsonProperty
	String comments  = defaultStringValue;

	@JsonProperty
	String delYn  = defaultStringValue;

	@JsonProperty
	String regDate  = defaultStringValue;

	@JsonProperty
	String regId  = defaultStringValue;

	@JsonProperty
	String modDate  = defaultStringValue;

	@JsonProperty
	String modId  = defaultStringValue;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpContextRoot() {
		return spContextRoot;
	}

	public void setSpContextRoot(String spContextRoot) {
		this.spContextRoot = spContextRoot;
	}

	public String getSpIp() {
		return spIp;
	}

	public void setSpIp(String spIp) {
		this.spIp = spIp;
	}

	public String getSpPort() {
		return spPort;
	}

	public void setSpPort(String spPort) {
		this.spPort = spPort;
	}

	public String getSpProtocol() {
		return spProtocol;
	}

	public void setSpProtocol(String spProtocol) {
		this.spProtocol = spProtocol;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public List<MonitorItem> getMonitorItems() {
		return monitorItems;
	}

	public void setMonitorItems(List<MonitorItem> monitorItems) {
		this.monitorItems = monitorItems;
	}



	public Map<String, Service> getAgentServiceMap() {
		return agentServiceMap;
	}

	public void setAgentServiceMap(Map<String, Service> agentServiceMap) {
		this.agentServiceMap = agentServiceMap;
	}

	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
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

	public int getLogDelay() {
		return logDelay;
	}

	public void setLogDelay(int logDelay) {
		this.logDelay = logDelay;
	}

	public String getSpWebSocketUrl() {
		return spWebSocketUrl;
	}

	public void setSpWebSocketUrl(String spWebSocketUrl) {
		this.spWebSocketUrl = spWebSocketUrl;
	}



}
