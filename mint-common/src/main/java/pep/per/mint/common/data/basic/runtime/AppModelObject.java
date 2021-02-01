package pep.per.mint.common.data.basic.runtime;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

public class AppModelObject extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -6794590961498885163L;

	@JsonProperty
	String interfaceMid = defaultStringValue;

	@JsonProperty
	String appMid = defaultStringValue;

	@JsonProperty
	String appModelNm = defaultStringValue;

	@JsonProperty
	String appModelCd = defaultStringValue;

	@JsonProperty
	String appModelType = defaultStringValue;

	@JsonProperty
	String appModelTypeNm = defaultStringValue;

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String systemCd = defaultStringValue;

	@JsonProperty
	String systemType = defaultStringValue;

	@JsonProperty
	String systemTypeNm = defaultStringValue;

	@JsonProperty
	String seq = defaultStringValue;

	@JsonProperty
	String serverId = defaultStringValue;

	@JsonProperty
	String serverNm = defaultStringValue;

	@JsonProperty
	String serverCd = defaultStringValue;

	@JsonProperty
	String hostNm = defaultStringValue;

	@JsonProperty
	String ip = defaultStringValue;

	@JsonProperty
	List<MsgModelObject> msgModelList = new ArrayList<MsgModelObject>();

	@JsonProperty
	List<MapModelObject> mapModelList = new ArrayList<MapModelObject>();

	@JsonProperty
	String comments = defaultStringValue;

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

    @JsonProperty
    String regId = defaultStringValue;

    @JsonProperty
    String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

    @JsonProperty
    String modId = defaultStringValue;

    @JsonProperty
    User regUser;

    @JsonProperty
    User modUser;

	public String getInterfaceMid() {
		return interfaceMid;
	}

	public void setInterfaceMid(String interfaceMid) {
		this.interfaceMid = interfaceMid;
	}

	public String getAppMid() {
		return appMid;
	}

	public void setAppMid(String appMid) {
		this.appMid = appMid;
	}

	public String getAppModelNm() {
		return appModelNm;
	}

	public void setAppModelNm(String appModelNm) {
		this.appModelNm = appModelNm;
	}

	public String getAppModelCd() {
		return appModelCd;
	}

	public void setAppModelCd(String appModelCd) {
		this.appModelCd = appModelCd;
	}

	public String getAppModelType() {
		return appModelType;
	}

	public void setAppModelType(String appModelType) {
		this.appModelType = appModelType;
	}

	public String getAppModelTypeNm() {
		return appModelTypeNm;
	}

	public void setAppModelTypeNm(String appModelTypeNm) {
		this.appModelTypeNm = appModelTypeNm;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getSystemCd() {
		return systemCd;
	}

	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getSystemTypeNm() {
		return systemTypeNm;
	}

	public void setSystemTypeNm(String systemTypeNm) {
		this.systemTypeNm = systemTypeNm;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerNm() {
		return serverNm;
	}

	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	public String getServerCd() {
		return serverCd;
	}

	public void setServerCd(String serverCd) {
		this.serverCd = serverCd;
	}

	public String getHostNm() {
		return hostNm;
	}

	public void setHostNm(String hostNm) {
		this.hostNm = hostNm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<MsgModelObject> getMsgModelList() {
		return msgModelList;
	}

	public void setMsgModelList(List<MsgModelObject> msgModelList) {
		this.msgModelList = msgModelList;
	}

	public List<MapModelObject> getMapModelList() {
		return mapModelList;
	}

	public void setMapModelList(List<MapModelObject> mapModelList) {
		this.mapModelList = mapModelList;
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

	public User getRegUser() {
		return regUser;
	}

	public void setRegUser(User regUser) {
		this.regUser = regUser;
	}

	public User getModUser() {
		return modUser;
	}

	public void setModUser(User modUser) {
		this.modUser = modUser;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
