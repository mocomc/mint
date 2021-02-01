/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ZoneInfo  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -270236179039470625L;

	@JsonProperty("countryInfo")
	CountryInfo countryInfo;
	
	@JsonProperty("zoneId")
	String zoneId = defaultStringValue;
	
	@JsonProperty("zoneCd")
	String zoneCd = defaultStringValue;
	
	@JsonProperty("zoneNm")
	String zoneNm = defaultStringValue;
	
	@JsonProperty("geoCd")
	String geoCd = defaultStringValue;
	
	/*** 삭제여부 */
	@JsonProperty("delYn")
	String delYn = "N";
	
	/*** 등록일 */
	@JsonProperty("regDate")
	String regDate = defaultStringValue;
	
	/*** 등록자 */
	@JsonProperty("regId")
	String regId = defaultStringValue;
	
	/*** 수정일 */
	@JsonProperty("modDate")
	String modDate = defaultStringValue;
	
	/*** 수정자 */
	@JsonProperty("modId")
	String modId = defaultStringValue;

	/**
	 * @return the countryInfo
	 */
	public CountryInfo getCountryInfo() {
		return countryInfo;
	}

	/**
	 * @param countryInfo the countryInfo to set
	 */
	public void setCountryInfo(CountryInfo countryInfo) {
		this.countryInfo = countryInfo;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	/**
	 * @return the zoneCd
	 */
	public String getZoneCd() {
		return zoneCd;
	}

	/**
	 * @param zoneCd the zoneCd to set
	 */
	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	/**
	 * @return the zoneNm
	 */
	public String getZoneNm() {
		return zoneNm;
	}

	/**
	 * @param zoneNm the zoneNm to set
	 */
	public void setZoneNm(String zoneNm) {
		this.zoneNm = zoneNm;
	}

	/**
	 * @return the geoCd
	 */
	public String getGeoCd() {
		return geoCd;
	}

	/**
	 * @param geoCd the geoCd to set
	 */
	public void setGeoCd(String geoCd) {
		this.geoCd = geoCd;
	}

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
	
	
	
}
