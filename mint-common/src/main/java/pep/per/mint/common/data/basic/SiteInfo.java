/*
d * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;

/**
 * IIP Agent Site Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SiteInfo  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -5557403828100924429L;


	/**
	 *
	 */


	@JsonProperty("applicationInfo")
	List<ApplicationInfo> applicationInfoList = new ArrayList<ApplicationInfo>();


	@JsonProperty("siteHomeInfo")
	List<SiteHomeInfo> siteHomeInfoList = new ArrayList<SiteHomeInfo>();

	String defInterfaceCallPath = "";

	public List<ApplicationInfo> getApplicationInfoList() {
		return applicationInfoList;
	}


	public void setApplicationInfoList(List<ApplicationInfo> applicationInfoList) {
		this.applicationInfoList = applicationInfoList;
	}


	public List<SiteHomeInfo> getSiteHomeInfoList() {
		return siteHomeInfoList;
	}


	public void setSiteHomeInfoList(List<SiteHomeInfo> siteHomeInfoList) {
		this.siteHomeInfoList = siteHomeInfoList;
	}


	public String getDefInterfaceCallPath() {
		return defInterfaceCallPath;
	}


	public void setDefInterfaceCallPath(String defInterfaceCallPath) {
		this.defInterfaceCallPath = defInterfaceCallPath;
	}


}
