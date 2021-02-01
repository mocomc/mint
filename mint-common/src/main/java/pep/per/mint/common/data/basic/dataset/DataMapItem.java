package pep.per.mint.common.data.basic.dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

public class DataMapItem extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 918356419459893213L;

	@JsonProperty
	String mapId = defaultStringValue;

	@JsonProperty
	String mapItemId = defaultStringValue;

// TODO :: 삭제예정
//	@JsonProperty
//	int seq = 0;

	@JsonProperty
	String mapCtrlType = defaultStringValue;

	@JsonProperty
	String src = defaultStringValue;

	@JsonProperty
	String tag = defaultStringValue;

	@JsonProperty
	Map srcMap = new LinkedHashMap();

	@JsonProperty
	Map tagMap = new LinkedHashMap();

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
	List<DataMapItemField> items = new ArrayList<DataMapItemField>();

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

// TODO : 삭제예정
//	public int getSeq() {
//		return seq;
//	}
//
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}

	public String getMapCtrlType() {
		return mapCtrlType;
	}

	public void setMapCtrlType(String mapCtrlType) {
		this.mapCtrlType = mapCtrlType;
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

	public List<DataMapItemField> getItems() {
		return items;
	}

	public void setItems(List<DataMapItemField> items) {
		this.items = items;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map getSrcMap() {
		return srcMap;
	}

	public void setSrcMap(Map srcMap) {
		this.srcMap = srcMap;
	}

	public Map getTagMap() {
		return tagMap;
	}

	public void setTagMap(Map tagMap) {
		this.tagMap = tagMap;
	}


}
