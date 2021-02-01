package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.util.Util;

public class MapModelObject extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5953937343111105756L;

	@JsonProperty
	String interfaceMid = defaultStringValue;

	@JsonProperty
	String appMid = defaultStringValue;

	@JsonProperty
	String mapMid = defaultStringValue;

	@JsonProperty
	String mapModelNm = defaultStringValue;

	@JsonProperty
	String mapId = defaultStringValue;

	@JsonProperty
	String mapIoType = defaultStringValue;

	@JsonProperty
	DataMap dataMap = new DataMap();

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

	public String getMapMid() {
		return mapMid;
	}

	public void setMapMid(String mapMid) {
		this.mapMid = mapMid;
	}

	public String getMapModelNm() {
		return mapModelNm;
	}

	public void setMapModelNm(String mapModelNm) {
		this.mapModelNm = mapModelNm;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getMapIoType() {
		return mapIoType;
	}

	public void setMapIoType(String mapIoType) {
		this.mapIoType = mapIoType;
	}

	public DataMap getDataMap() {
		return dataMap;
	}

	public void setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
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
