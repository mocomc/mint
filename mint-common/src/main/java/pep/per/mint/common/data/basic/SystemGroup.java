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
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

import pep.per.mint.common.data.CacheableObject;


/**
 * 시스템 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SystemGroup  extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469508541279577768L;

	
	/*** system */
	@JsonProperty("system")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	System system;
	
	/*** systemPath */
	@JsonProperty("systemPath")
	//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	SystemPath systemPath;
	

	/**
	 * @return the system
	 */
	public System getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(System system) {
		this.system = system;
	}

	/**
	 * @return the systemPath
	 */
	public SystemPath getSystemPath() {
		return systemPath;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemPath(SystemPath systemPath) {
		this.systemPath = systemPath;
	}
}
