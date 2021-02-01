/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MsgMap extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2069649769732977292L;

	@JsonProperty
	String inputMsgSetId;
	
	@JsonProperty
	String outputMsgSetId;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	List<MapControl> mapControls = new ArrayList<MapControl>();

	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	/**
	 * @return the inputMsgSetId
	 */
	public String getInputMsgSetId() {
		return inputMsgSetId;
	}

	/**
	 * @param inputMsgSetId the inputMsgSetId to set
	 */
	public void setInputMsgSetId(String inputMsgSetId) {
		this.inputMsgSetId = inputMsgSetId;
	}

	/**
	 * @return the outputMsgSetId
	 */
	public String getOutputMsgSetId() {
		return outputMsgSetId;
	}

	/**
	 * @param outputMsgSetId the outputMsgSetId to set
	 */
	public void setOutputMsgSetId(String outputMsgSetId) {
		this.outputMsgSetId = outputMsgSetId;
	}

	/**
	 * @return the mapControls
	 */
	public List<MapControl> getMapControls() {
		return mapControls;
	}

	/**
	 * @param mapControls the mapControls to set
	 */
	public void setMapControls(List<MapControl> mapControls) {
		this.mapControls = mapControls;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
