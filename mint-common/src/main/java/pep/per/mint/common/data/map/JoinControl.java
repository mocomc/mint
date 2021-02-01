/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class JoinControl extends MapControl {

	@JsonProperty
	Map<String, JoinCondition> joinConditionMap = new HashMap<String, JoinCondition>();
	
	/**
	 * 
	 */
	public JoinControl() throws Exception {
		super();
	}

	/**
	 * @return the joinConditionMap
	 */
	public Map<String, JoinCondition> getJoinConditionMap() {
		return joinConditionMap;
	}

	/**
	 * @param joinConditionMap the joinConditionMap to set
	 */
	public void setJoinConditionMap(Map<String, JoinCondition> joinConditionMap) {
		this.joinConditionMap = joinConditionMap;
	}
	
	@JsonIgnore
	public JoinCondition getJoinCondition(String fieldPathString){
		return joinConditionMap.get(fieldPathString);
	}

	@JsonIgnore
	public boolean hasJoinCondition(List<MapPath> mp) {
		for (MapPath mapPath : mp) {
			String key = mapPath.getParentString();
			boolean contain = joinConditionMap.containsKey(key);
			if(contain) return true;
		}
		return false;
	}
	
	 
}
