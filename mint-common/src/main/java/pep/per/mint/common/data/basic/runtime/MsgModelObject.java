package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.util.Util;

public class MsgModelObject extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8189896466154420400L;

	@JsonProperty
	String interfaceMid = defaultStringValue;

	@JsonProperty
	String appMid = defaultStringValue;

	@JsonProperty
	String msgMid = defaultStringValue;

	@JsonProperty
	String msgModelNm = defaultStringValue;

	@JsonProperty
	String dataSetId = defaultStringValue;

	@JsonProperty
	String msgIoType = defaultStringValue;

	@JsonProperty
	DataSet dataSet = new DataSet();

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

	public String getMsgMid() {
		return msgMid;
	}

	public void setMsgMid(String msgMid) {
		this.msgMid = msgMid;
	}

	public String getMsgModelNm() {
		return msgModelNm;
	}

	public void setMsgModelNm(String msgModelNm) {
		this.msgModelNm = msgModelNm;
	}

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getMsgIoType() {
		return msgIoType;
	}

	public void setMsgIoType(String msgIoType) {
		this.msgIoType = msgIoType;
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
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
