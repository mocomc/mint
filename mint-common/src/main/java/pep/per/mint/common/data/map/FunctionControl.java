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

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FunctionControl extends MapControl {

	MapFunctionDefinition functionDefinition;
	
	/**
	 * 
	 */
	public FunctionControl() throws Exception {
		super();
	}
	
	 	
	public FunctionControl(MapPath inputPath, MapPath outputPath) throws Exception {
		this();
		if(inputPathList == null) inputPathList = new ArrayList<MapPath>();
		if(inputPath != null) inputPathList.add(inputPath);
		this.outputPath = outputPath;
	}

	/**
	 * @param inputPaths
	 * @param mapPath
	 */
	public FunctionControl(List<MapPath> inputPaths, MapPath outputPath) throws Exception {
		this();
		inputPathList = inputPaths;
		this.outputPath = outputPath;
	}

	/**
	 * @return the functionDefinitionList
	 */
	public MapFunctionDefinition getFunctionDefinition() {
		return functionDefinition;
	}

	/**
	 * @param functionDefinitionList the functionDefinitionList to set
	 */
	public void setFunctionDefinition(MapFunctionDefinition functionDefinition) {
		this.functionDefinition = functionDefinition;
	}
	
	

}
