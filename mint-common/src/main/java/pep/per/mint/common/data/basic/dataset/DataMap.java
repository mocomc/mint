package pep.per.mint.common.data.basic.dataset;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

public class DataMap extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3525823795516493944L;


	@JsonProperty
	String mapId = defaultStringValue;

	@JsonProperty
	String name = defaultStringValue;

	@JsonProperty
	String cd = defaultStringValue;

	@JsonProperty
	String srcDataSetId = defaultStringValue;

	@JsonProperty
	String srcDataSetNm1 = defaultStringValue;

	@JsonProperty
	String srcDataSetNm2 = defaultStringValue;

	@JsonProperty
	String srcSystemId = defaultStringValue;

	@JsonProperty
	String srcSystemCd = defaultStringValue;

	@JsonProperty
	String srcSystemNm1 = defaultStringValue;

	@JsonProperty
	String srcSystemNm2 = defaultStringValue;

	@JsonProperty
	String srcDataSetCd = defaultStringValue;

	@JsonProperty
	String tagDataSetId = defaultStringValue;

	@JsonProperty
	String tagDataSetNm1 = defaultStringValue;

	@JsonProperty
	String tagDataSetNm2 = defaultStringValue;

	@JsonProperty
	String tagDataSetCd = defaultStringValue;

	@JsonProperty
	String tagSystemId = defaultStringValue;

	@JsonProperty
	String tagSystemCd = defaultStringValue;

	@JsonProperty
	String tagSystemNm1 = defaultStringValue;

	@JsonProperty
	String tagSystemNm2 = defaultStringValue;

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

    @JsonProperty
    String regId = defaultStringValue;

    @JsonProperty
    String modDate = defaultStringValue;

    @JsonProperty
    String modId = defaultStringValue;

    @JsonProperty
    User regUser;

    @JsonProperty
    User modUser;

    @JsonProperty
    String interfaceId = defaultStringValue;

    @JsonProperty
    String isInterfaceMapped = "N";

	@JsonProperty
	List<DataMapItem> dataMapItemList = new ArrayList<DataMapItem>();

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getSrcDataSetId() {
		return srcDataSetId;
	}

	public void setSrcDataSetId(String srcDataSetId) {
		this.srcDataSetId = srcDataSetId;
	}

	public String getSrcDataSetNm1() {
		return srcDataSetNm1;
	}

	public void setSrcDataSetNm1(String srcDataSetNm1) {
		this.srcDataSetNm1 = srcDataSetNm1;
	}

	public String getSrcDataSetNm2() {
		return srcDataSetNm2;
	}

	public void setSrcDataSetNm2(String srcDataSetNm2) {
		this.srcDataSetNm2 = srcDataSetNm2;
	}

	public String getSrcSystemId() {
		return srcSystemId;
	}

	public void setSrcSystemId(String srcSystemId) {
		this.srcSystemId = srcSystemId;
	}

	public String getSrcSystemCd() {
		return srcSystemCd;
	}

	public void setSrcSystemCd(String srcSystemCd) {
		this.srcSystemCd = srcSystemCd;
	}

	public String getSrcSystemNm1() {
		return srcSystemNm1;
	}

	public void setSrcSystemNm1(String srcSystemNm1) {
		this.srcSystemNm1 = srcSystemNm1;
	}

	public String getSrcSystemNm2() {
		return srcSystemNm2;
	}

	public void setSrcSystemNm2(String srcSystemNm2) {
		this.srcSystemNm2 = srcSystemNm2;
	}

	public String getSrcDataSetCd() {
		return srcDataSetCd;
	}

	public void setSrcDataSetCd(String srcDataSetCd) {
		this.srcDataSetCd = srcDataSetCd;
	}

	public String getTagDataSetId() {
		return tagDataSetId;
	}

	public void setTagDataSetId(String tagDataSetId) {
		this.tagDataSetId = tagDataSetId;
	}

	public String getTagDataSetNm1() {
		return tagDataSetNm1;
	}

	public void setTagDataSetNm1(String tagDataSetNm1) {
		this.tagDataSetNm1 = tagDataSetNm1;
	}

	public String getTagDataSetNm2() {
		return tagDataSetNm2;
	}

	public void setTagDataSetNm2(String tagDataSetNm2) {
		this.tagDataSetNm2 = tagDataSetNm2;
	}

	public String getTagDataSetCd() {
		return tagDataSetCd;
	}

	public void setTagDataSetCd(String tagDataSetCd) {
		this.tagDataSetCd = tagDataSetCd;
	}

	public String getTagSystemId() {
		return tagSystemId;
	}

	public void setTagSystemId(String tagSystemId) {
		this.tagSystemId = tagSystemId;
	}

	public String getTagSystemCd() {
		return tagSystemCd;
	}

	public void setTagSystemCd(String tagSystemCd) {
		this.tagSystemCd = tagSystemCd;
	}

	public String getTagSystemNm1() {
		return tagSystemNm1;
	}

	public void setTagSystemNm1(String tagSystemNm1) {
		this.tagSystemNm1 = tagSystemNm1;
	}

	public String getTagSystemNm2() {
		return tagSystemNm2;
	}

	public void setTagSystemNm2(String tagSystemNm2) {
		this.tagSystemNm2 = tagSystemNm2;
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

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getIsInterfaceMapped() {
		return isInterfaceMapped;
	}

	public void setIsInterfaceMapped(String isInterfaceMapped) {
		this.isInterfaceMapped = isInterfaceMapped;
	}

	public List<DataMapItem> getDataMapItemList() {
		return dataMapItemList;
	}

	public void setDataMapItemList(List<DataMapItem> dataMapItemList) {
		this.dataMapItemList = dataMapItemList;
	}



}
