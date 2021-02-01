package pep.per.mint.common.data.basic.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

public class DataMapItemField extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 2231386475207422386L;

	@JsonProperty
	String dataSetId = defaultStringValue;

	@JsonProperty
	String dataFieldId = defaultStringValue;

	@JsonProperty
	int dataFieldSeq = 0;

	@JsonProperty
	String mapId = defaultStringValue;

	@JsonProperty
	String mapItemId = defaultStringValue;

// TODO :: 삭제예정
//	@JsonProperty
//	int seq = 0;

	@JsonProperty
	String ioType = defaultStringValue;

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

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getDataFieldId() {
		return dataFieldId;
	}

	public void setDataFieldId(String dataFieldId) {
		this.dataFieldId = dataFieldId;
	}

	public int getDataFieldSeq() {
		return dataFieldSeq;
	}

	public void setDataFieldSeq(int dataFieldSeq) {
		this.dataFieldSeq = dataFieldSeq;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getMapItemId() {
		return mapItemId;
	}

	public void setMapItemId(String mapItemId) {
		this.mapItemId = mapItemId;
	}

// TODO :: 삭제예정
//	public int getSeq() {
//		return seq;
//	}
//
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}

	public String getIoType() {
		return ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
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



}
