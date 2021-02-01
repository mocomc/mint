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
 * 서버 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRNodeInfo  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = -7816671784208210134L;



	@JsonProperty("nodeId")
	String nodeId = defaultStringValue;

	@JsonProperty("nodeNm")
	String nodeNm = defaultStringValue;

	@JsonProperty("nodeType")
	String nodeType = defaultStringValue;

	@JsonProperty("nodeGubun")
	String nodeGubun = defaultStringValue;

	@JsonProperty("nodeIndex")
	int nodeIndex = 0;

	@JsonProperty("posX")
	String posX = defaultStringValue;

	@JsonProperty("posY")
	String posY = defaultStringValue;

	@JsonProperty("hostId")
	String hostId = defaultStringValue;

	@JsonProperty("processId")
	String processId = defaultStringValue;

	@JsonProperty("processMode")
	String processMode = defaultStringValue;

	@JsonProperty("status")
	String status = defaultStringValue;


	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeNm() {
		return nodeNm;
	}

	public void setNodeNm(String nodeNm) {
		this.nodeNm = nodeNm;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	public String getNodeGubun() {
		return nodeGubun;
	}

	public void setNodeGubun(String nodeGubun) {
		this.nodeGubun = nodeGubun;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public String getProcessMode() {
		return processMode;
	}

	public void setProcessMode(String processMode) {
		this.processMode = processMode;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
