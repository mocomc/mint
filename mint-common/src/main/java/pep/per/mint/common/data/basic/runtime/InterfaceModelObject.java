package pep.per.mint.common.data.basic.runtime;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterfaceModelObject extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -9157663754438514915L;

	@JsonProperty
	String interfaceMid = defaultStringValue;

	@JsonProperty
	String interfaceModelNm = defaultStringValue;

	@JsonProperty
	String interfaceId = defaultStringValue;

	@JsonProperty
	String stage = defaultStringValue;

	@JsonProperty
	String stageNm = defaultStringValue;

	@JsonProperty
	String deployStatus = defaultStringValue;

	@JsonProperty
	String deployStatusNm = defaultStringValue;

	@JsonProperty
	List<AppModelObject> appModelList = new ArrayList<AppModelObject>();

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

	public String getInterfaceModelNm() {
		return interfaceModelNm;
	}

	public void setInterfaceModelNm(String interfaceModelNm) {
		this.interfaceModelNm = interfaceModelNm;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStageNm() {
		return stageNm;
	}

	public void setStageNm(String stageNm) {
		this.stageNm = stageNm;
	}

	public String getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(String deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getDeployStatusNm() {
		return deployStatusNm;
	}

	public void setDeployStatusNm(String deployStatusNm) {
		this.deployStatusNm = deployStatusNm;
	}

	public List<AppModelObject> getAppModelList() {
		return appModelList;
	}

	public void setAppModelList(List<AppModelObject> appModelList) {
		this.appModelList = appModelList;
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
