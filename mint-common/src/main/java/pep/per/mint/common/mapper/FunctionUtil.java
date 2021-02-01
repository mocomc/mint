/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import pep.per.mint.common.data.map.MapFunctionDefinition;
import pep.per.mint.common.mapper.function.*;
import pep.per.mint.common.util.Util;


/**
 * <blockquote>
 * 매핑함수 호출을 위한 유틸리티 클래스
 * </blockquote> 
 * 
 * @author Solution TF
 *
 */
public class FunctionUtil {

	static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
	
	static Map<String,Function> javaFunctionMap = new HashMap<String,Function>();
	
	//-------------------------------------------------
	//아래 함수부분은 외부 정의에 의해 로드될수 있도록 처리해보자. 
	//-------------------------------------------------
	static{
		
		Function[] functions = {
				   new Count()
				  ,new Concat()
				  ,new Trim()
				  ,new SetValue()
		};
		
		for(int i = 0 ; i < functions.length ; i ++){
			javaFunctionMap.put(functions[i].getClass().getSimpleName(), functions[i]);
		}
	}

	 
	
	public static Serializable call(MapFunctionDefinition fd, List<Serializable> valueObjects) throws Exception {
		//그룹함수호출부구현
		Function function = getFunction(fd);
		
		Serializable res = function.call(valueObjects, fd.getParams());
		return res;
	}
	
	public static Serializable call(MapFunctionDefinition fd, Serializable param) throws Exception {
		//그룹함수호출부구현
		List<Serializable> valueObjects = new ArrayList<Serializable>();
		if(param != null) valueObjects.add(param);
		 
		return call(fd, valueObjects);
	}
	
	 
	static Function getFunction(MapFunctionDefinition fd)throws Exception{
		String name = fd.getName();
		String type = fd.getType();
		Function function = null;
		if(MapFunctionDefinition.TYPE_JAVA.equals(type)){
			function = javaFunctionMap.get(name);
		}else if(MapFunctionDefinition.TYPE_REF.equals(type)){
			File reference = new File(fd.getRef());
			function = new JavascriptFunction(engine, name, reference);
		}else if(MapFunctionDefinition.TYPE_CONTENTS.equals(type)){
			function = new JavascriptFunction(engine, name, fd.getContents());
		}else{
			throw new Exception(Util.join("함수호출예외:함수유형이 정의되지 않았습니다.",",함수명:",name,", 함수유형:",type));
		}
		if(function == null) throw new Exception(Util.join("함수호출예외:함수가 존재하지 았습니다.",",함수명:",name,", 함수유형:",type));
		return function;
	}
	
}
