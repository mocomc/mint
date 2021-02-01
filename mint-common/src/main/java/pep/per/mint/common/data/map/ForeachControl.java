/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.FieldPath;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ForeachControl extends MapControl {
	
	
	/**
	 * @throws Exception 
	 * 
	 */
	public ForeachControl() throws Exception {
		super();
	}
	
	public ForeachControl(MapPath inputPath, MapPath outputPath) throws Exception {
		this();
		if(inputPathList == null) inputPathList = new ArrayList<MapPath>();
		inputPathList.add(inputPath);
		this.outputPath = outputPath;
	}

}
