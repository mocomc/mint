/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.data.basic.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FrontLogOption extends CacheableObject {
	
	public final static FrontLogOption OPT_NNN = new FrontLogOption();
	public final static FrontLogOption OPT_YNN = new FrontLogOption("Y","N","N");
	public final static FrontLogOption OPT_YYN = new FrontLogOption("Y","Y","N");
	public final static FrontLogOption OPT_YYY = new FrontLogOption("Y","Y","Y");
	
	@JsonProperty
	String serviceId;
	
	@JsonProperty
	String loggingYn = "N";
	
	@JsonProperty
	String saveRequestMsg = "N";
	
	@JsonProperty
	String saveResponseMsg = "N";
	
	public FrontLogOption(){
		super();
	}
	
	public FrontLogOption(String loggingYn, String saveRequestMsg, String saveResponseMsg){
		this(null, loggingYn,saveRequestMsg, saveResponseMsg);
	}
	
	public FrontLogOption(String serviceId, String loggingYn, String saveRequestMsg, String saveResponseMsg){
		this();
		this.serviceId = serviceId;
		this.loggingYn = loggingYn;
		this.saveRequestMsg = saveRequestMsg;
		this.saveResponseMsg = saveResponseMsg;
	}

	
	

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the loggingYn
	 */
	public String getLoggingYn() {
		return loggingYn;
	}

	/**
	 * @param loggingYn the loggingYn to set
	 */
	public void setLoggingYn(String loggingYn) {
		this.loggingYn = loggingYn;
	}

	/**
	 * @return the saveRequestMsg
	 */
	public String getSaveRequestMsg() {
		return saveRequestMsg;
	}

	/**
	 * @param saveRequestMsg the saveRequestMsg to set
	 */
	public void setSaveRequestMsg(String saveRequestMsg) {
		this.saveRequestMsg = saveRequestMsg;
	}

	/**
	 * @return the saveResponseMsg
	 */
	public String getSaveResponseMsg() {
		return saveResponseMsg;
	}

	/**
	 * @param saveResponseMsg the saveResponseMsg to set
	 */
	public void setSaveResponseMsg(String saveResponseMsg) {
		this.saveResponseMsg = saveResponseMsg;
	}
	
	
	
}
