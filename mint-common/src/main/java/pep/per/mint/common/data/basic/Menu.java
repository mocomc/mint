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
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * 프로그램 메뉴 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Menu  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -7526147540703896830L;

	@JsonProperty("menuId")
	String menuId = defaultStringValue;


	@JsonProperty("menuNm")
	String menuNm = defaultStringValue;

	@JsonProperty("menuCd")
	String menuCd = defaultStringValue;

	@JsonProperty("menuOpt")
	String menuOpt = defaultStringValue;

	@JsonProperty("rootYn")
	String rootYn = "N";

	@JsonProperty("comments")
	String comments = defaultStringValue;

	@JsonProperty("seq")
	String seq = "0";

	@JsonProperty("childMenuList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Menu> childMenuList = new ArrayList<Menu>();

	@JsonProperty("appList")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<Application> appList = new ArrayList<Application>();


	/*** 삭제여부 */
	@JsonProperty("delYn")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String delYn = "N";

	/*** 등록일 */
	@JsonProperty("regDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty("regId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String regId = defaultStringValue;

	/*** 수정일 */
	@JsonProperty("modDate")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modDate = defaultStringValue;

	/*** 수정자 */
	@JsonProperty("modId")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	String modId = defaultStringValue;

	@JsonProperty("hasYn")
	String hasYn = "Y";
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuNm
	 */
	public String getMenuNm() {
		return menuNm;
	}

	/**
	 * @param menuNm the menuNm to set
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}



	public String getMenuCd() {
		return menuCd;
	}

	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

	public String getMenuOpt() {
		return menuOpt;
	}

	public void setMenuOpt(String menuOpt) {
		this.menuOpt = menuOpt;
	}

	/**
	 * @return the rootYn
	 */
	public String getRootYn() {
		return rootYn;
	}

	/**
	 * @param rootYn the rootYn to set
	 */
	public void setRootYn(String rootYn) {
		this.rootYn = rootYn;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the childMenuList
	 */
	public List<Menu> getChildMenuList() {
		return childMenuList;
	}

	/**
	 * @param childMenuList the childMenuList to set
	 */
	public void setChildMenuList(List<Menu> childMenuList) {
		this.childMenuList = childMenuList;
	}

	/**
	 * @return the appList
	 */
	public List<Application> getAppList() {
		return appList;
	}

	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<Application> appList) {
		this.appList = appList;
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

	public void addChild(Menu menu){
		if(childMenuList == null) childMenuList = new ArrayList<Menu>();
		childMenuList.add(menu);
	}

	public String getHasYn() {
		return hasYn;
	}

	public void setHasYn(String hasYn) {
		this.hasYn = hasYn;
	}


}
