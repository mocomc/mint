/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.common.data.basic.dataset
 * DataSetInterfaceMap.java
 * </pre>
 * @author whoana
 * @date Apr 17, 2019
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DataSetInterfaceMap extends CacheableObject{

	@JsonProperty
	String dataSetId = defaultStringValue;

	@JsonProperty
	String systemId = defaultStringValue;

	@JsonProperty
	String interfaceId = defaultStringValue;

	@JsonProperty
    int seq = 0;

	@JsonProperty
	String ioType = defaultStringValue;

	@JsonProperty
	String dataSetNm = defaultStringValue;

	@JsonProperty
	String dataSetCd = defaultStringValue;

	@JsonProperty
	String systemCd = defaultStringValue;

	@JsonProperty
	String systemNm = defaultStringValue;

	@JsonProperty
	String integrationId = defaultStringValue;

	@JsonProperty
	String interfaceNm = defaultStringValue;

	@JsonProperty
	String mapId = defaultStringValue;

	@JsonProperty
	String mapNm = defaultStringValue;

	@JsonProperty
	String mapCd = defaultStringValue;

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String regDate  = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getIoType() {
		return ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public String getDataSetNm() {
		return dataSetNm;
	}

	public void setDataSetNm(String dataSetNm) {
		this.dataSetNm = dataSetNm;
	}

	public String getDataSetCd() {
		return dataSetCd;
	}

	public void setDataSetCd(String dataSetCd) {
		this.dataSetCd = dataSetCd;
	}

	public String getSystemCd() {
		return systemCd;
	}

	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getInterfaceNm() {
		return interfaceNm;
	}

	public void setInterfaceNm(String interfaceNm) {
		this.interfaceNm = interfaceNm;
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

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getMapNm() {
		return mapNm;
	}

	public void setMapNm(String mapNm) {
		this.mapNm = mapNm;
	}

	public String getMapCd() {
		return mapCd;
	}

	public void setMapCd(String mapCd) {
		this.mapCd = mapCd;
	}


}
