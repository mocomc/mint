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
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class JoinCondition {

	public final static int JC_SAME_INDEX = 0;
	public final static int JC_SOME_INDEX = 1;
	public final static int JC_FILTER = 2;
	public final static int JC_NONE = 3;
	
	@JsonProperty
	int type = 0;
	
	@JsonProperty
	List<Integer> someIndeces = null;
	
	
	/**
	 * 
	 */
	public JoinCondition() {
		super();
	}


	/**
	 * @param jcSameIndex
	 */
	public JoinCondition(int type) {
		this();
		this.type = type;
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
	 * @return the someIndeces
	 */
	public List<Integer> getSomeIndeces() {
		return someIndeces;
	}


	/**
	 * @param someIndeces the someIndeces to set
	 */
	public void setSomeIndeces(List<Integer> someIndeces) {
		this.someIndeces = someIndeces;
	}

	
	
}
