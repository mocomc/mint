/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.util.Util;

/**
 * 자바스크립트 사용자정의 함수를 호출하기위한 용도
 * @author Solution TF
 *
 */
public class JavascriptFunction implements Function {

	public static Logger logger = LoggerFactory.getLogger(JavascriptFunction.class);
	
	//static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
	ScriptEngine engine;
	
	String name;
	/**
	 * 
	 * @param scriptFile
	 * @throws FileNotFoundException
	 * @throws ScriptException
	 */
	public JavascriptFunction(ScriptEngine engine, String functionName, File scriptFile) throws FileNotFoundException, ScriptException {
		FileReader reader = null;
		try{
			this.engine = engine;
			this.name = functionName;
			reader = new FileReader(scriptFile);
			engine.eval(reader); 
		}finally{
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public JavascriptFunction(ScriptEngine engine, String functionName, String contents) throws FileNotFoundException, ScriptException {
		this.engine = engine;
		this.name = functionName;
		engine.eval(contents); 
		if(logger.isDebugEnabled()) logger.debug(Util.join("function defined:",contents));
	}
	

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.function.Function#call(java.util.List)
	 */
	@Override
	public Serializable call(List<Serializable> params1, Map params2) throws Exception {
		Object result = null;
		String jsonParams1 = params1 == null ? "[]" : Util.toJSONString(params1);
		String jsonParams2 = params2 == null ? "{}" : Util.toJSONString(params2);
		String functionString = Util.join(name,"(",jsonParams1, ",", jsonParams2,")");
		logger.debug(Util.join("javascript function call:",functionString));
		result = engine.eval(functionString);
		return (Serializable)result;
	}

	  

}

