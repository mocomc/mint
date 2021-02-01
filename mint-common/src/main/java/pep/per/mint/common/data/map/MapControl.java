/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value=MoveControl.class),
    @JsonSubTypes.Type(value=ForeachControl.class),
    @JsonSubTypes.Type(value=JoinControl.class),
    @JsonSubTypes.Type(value=FunctionControl.class)
})
public abstract class MapControl {

	public final static int CTRL_TYPE_MOVE = 0;
	public final static int CTRL_TYPE_FOREACH = 1;
	public final static int CTRL_TYPE_JOIN = 2;
	public final static int CTRL_TYPE_FUNCTION = 3;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	List<MapPath> inputPathList;
	
	@JsonProperty
	MapPath outputPath;
	
	@JsonProperty
	List<MapControl> children;
	
	/**
	 * @throws Exception 
	 * 
	 */
	public MapControl() throws Exception {
		Class<?> clazz = this.getClass();
		if(clazz == MoveControl.class){
			this.type = CTRL_TYPE_MOVE;
		}else if(clazz == ForeachControl.class){
			this.type = CTRL_TYPE_FOREACH;
		}else if(clazz == JoinControl.class){
			this.type = CTRL_TYPE_JOIN;
		}else if(clazz == FunctionControl.class){
			this.type = CTRL_TYPE_FUNCTION;
		}else{
			throw new Exception(Util.join("MapControlCreateException:not supported type[",type,"]"));
		}
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the inputPathList
	 */
	public List<MapPath> getInputPathList() {
		return inputPathList;
	}

	/**
	 * @param inputPathList the inputPathList to set
	 */
	public void setInputPathList(List<MapPath> inputPathList) {
		this.inputPathList = inputPathList;
	}

	/**
	 * @return the outputPath
	 */
	public MapPath getOutputPath() {
		return outputPath;
	}

	/**
	 * @param outputPath the outputPath to set
	 */
	public void setOutputPath(MapPath outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * @return the children
	 */
	public List<MapControl> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MapControl> children) {
		this.children = children;
	}

	 
	
	
}
