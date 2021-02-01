/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Solution TF
 *
 */
public class MappingValue {
	
	//Map<String, Serializable> valueMap = new LinkedHashMap<String, Serializable>();
	
	/*public void put(String key, Serializable value){
		valueMap.put(key, value);
	}
	
	public Serializable getFirstValue() {
		// TODO Auto-generated method stub
		Iterator<Serializable> iterator = valueMap.values().iterator();
		if(iterator.hasNext()){
			return iterator.next();
		}
		return null;
	}
	
	public Serializable get(String key){
		return valueMap.get(key);
	}
	
	public int size(){
		return valueMap.size();
	}
	
	
	public Map<String, Serializable> getValueMap(){
		return this.valueMap;
	}
	*/
	
	List<Serializable> valueList = new ArrayList<Serializable>();
	
	public void add(Serializable value){
		valueList.add(value);
	}
	
	public List<Serializable> getValueList(){
		return this.valueList;
	}
	
	public int size(){
		return valueList.size();
	}
}