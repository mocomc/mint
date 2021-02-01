/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 거래로그상세 Diagram Data Object
 *
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRLogDetailDiagram  extends CacheableObject{


	/**
	 *
	 */
	private static final long serialVersionUID = 4346572307172815734L;

	/*** 로그키1 */
	@JsonProperty("logKey1")
	String logKey1 = defaultStringValue;

	/*** 로그키2 */
	@JsonProperty("logKey2")
	String logKey2 = defaultStringValue;

	/*** 로그키3 */
	@JsonProperty("logKey3")
	String logKey3 = defaultStringValue;

	/*** Node 리스트 */
	@JsonProperty("nodeList")
	List<TRNodeInfo> nodeList = new ArrayList<TRNodeInfo>();


	/*** Node 리스트 */
	@JsonProperty("sNodeList")
	List<TRNodeInfo> sNodeList = new ArrayList<TRNodeInfo>();

	/*** Node 리스트 */
	@JsonProperty("rNodeList")
	List<TRNodeInfo> rNodeList = new ArrayList<TRNodeInfo>();

	/*** Node 리스트 */
	@JsonProperty("qNodeList")
	List<TRNodeInfo> qNodeList = new ArrayList<TRNodeInfo>();

	/*** Node 리스트 */
	@JsonProperty("shNodeList")
	List<TRNodeInfo> shNodeList = new ArrayList<TRNodeInfo>();

	/*** Node 리스트 */
	@JsonProperty("rhNodeList")
	List<TRNodeInfo> rhNodeList = new ArrayList<TRNodeInfo>();

	/*** Link 리스트 */
	@JsonProperty("linkList")
	List<TRLinkInfo> linkList  = new ArrayList<TRLinkInfo>();


	/**
	 * @return the logKey1
	 */
	public String getLogKey1() {
		return logKey1;
	}

	/**
	 * @param logKey1 the logKey1 to set
	 */
	public void setLogKey1(String logKey1) {
		this.logKey1 = logKey1;
	}

	/**
	 * @return the logKey2
	 */
	public String getLogKey2() {
		return logKey2;
	}

	/**
	 * @param logKey2 the logKey2 to set
	 */
	public void setLogKey2(String logKey2) {
		this.logKey2 = logKey2;
	}

	/**
	 * @return the logKey3
	 */
	public String getLogKey3() {
		return logKey3;
	}

	/**
	 * @param logKey3 the logKey3 to set
	 */
	public void setLogKey3(String logKey3) {
		this.logKey3 = logKey3;
	}

	public List<TRNodeInfo> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<TRNodeInfo> nodeList) {
		this.nodeList = nodeList;
	}

	public List<TRLinkInfo> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<TRLinkInfo> linkList) {
		this.linkList = linkList;
	}

	public List<TRNodeInfo> getsNodeList() {
		return sNodeList;
	}

	public void setsNodeList(List<TRNodeInfo> sNodeList) {
		this.sNodeList = sNodeList;
	}

	public List<TRNodeInfo> getrNodeList() {
		return rNodeList;
	}

	public void setrNodeList(List<TRNodeInfo> rNodeList) {
		this.rNodeList = rNodeList;
	}

	public List<TRNodeInfo> getqNodeList() {
		return qNodeList;
	}

	public void setqNodeList(List<TRNodeInfo> qNodeList) {
		this.qNodeList = qNodeList;
	}

	public List<TRNodeInfo> getShNodeList() {
		return shNodeList;
	}

	public void setShNodeList(List<TRNodeInfo> shNodeList) {
		this.shNodeList = shNodeList;
	}

	public List<TRNodeInfo> getRhNodeList() {
		return rhNodeList;
	}

	public void setRhNodeList(List<TRNodeInfo> rhNodeList) {
		this.rhNodeList = rhNodeList;
	}

}
