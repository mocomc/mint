/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MapPath {
	
	public static String PATH_CHAR_CURRENT = ".";
	
	@JsonProperty
	FieldPath parent;
	
	@JsonProperty
	FieldPath current;

	@JsonProperty
	FieldPath fullPath;
	
	public MapPath(){
		super();
	}
	
	public MapPath(String parent, String current) {
		this();
		this.parent = new FieldPath(parent);
		this.current = new FieldPath(current);
		if(PATH_CHAR_CURRENT.equals(current)){
			fullPath = new FieldPath(parent);
		}else{
			fullPath = new FieldPath(parent);
			fullPath.addPath(current);
		}
	}
	
	public MapPath(MapPath parent, String current) {
		this(parent.getFullPathString(), current);
	}
	
	public void setParent(FieldPath parent) {
		this.parent = parent;
	}

	/**
	 * @return the path
	 */
	public FieldPath getParent() {
		return parent;
	}
	
	@JsonIgnore
	public String getParentString() {
		return parent.toString();
	}

	 

	public void setCurrent(FieldPath current) {
		this.current = current;
	}

	
	/**
	 * @return the current
	 */
	public FieldPath getCurrent() {
		return current;
	}
	
	@JsonIgnore
	public String getCurrentString() {
		return current.toString();
	}
	
	/**
	 * @return the fullPath
	 */
	public FieldPath getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(FieldPath fullPath) {
		this.fullPath = fullPath;
	}

	@JsonIgnore
	public String getFullPathString() {
		if(PATH_CHAR_CURRENT.equals(current.toString())){
			return parent.toString();
		}
		return Util.join(parent.toString(), ".", current.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(PATH_CHAR_CURRENT.equals(current.toString())){
			return parent.toString();
		}
		return Util.join(parent.toString(), ".", current.toString());
	}

}
