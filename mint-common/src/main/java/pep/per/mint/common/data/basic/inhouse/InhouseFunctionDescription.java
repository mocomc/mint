/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.inhouse;

import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author whoana
 * @since 2020. 11. 5.
 */
public class InhouseFunctionDescription extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	String initFunctionName;
	
	@JsonProperty
	String []initFuncitonParamTypeNames;
	
	@JsonProperty
	Object []initFuncitonParams;
	
	@JsonProperty
	String functionName;
	
	@JsonProperty
	String className;
	
	@JsonProperty
	String returnTypeName;
	
	@JsonProperty
	String []paramTypeNames;

	/**
	 * @return the initFunctionName
	 */
	public String getInitFunctionName() {
		return initFunctionName;
	}

	/**
	 * @param initFunctionName the initFunctionName to set
	 */
	public void setInitFunctionName(String initFunctionName) {
		this.initFunctionName = initFunctionName;
	}

	/**
	 * @return the initFuncitonParamTypeNames
	 */
	public String[] getInitFuncitonParamTypeNames() {
		return initFuncitonParamTypeNames;
	}

	/**
	 * @param initFuncitonParamTypeNames the initFuncitonParamTypeNames to set
	 */
	public void setInitFuncitonParamTypeNames(String[] initFuncitonParamTypeNames) {
		this.initFuncitonParamTypeNames = initFuncitonParamTypeNames;
	}

	
	
	/**
	 * @return the initFuncitonParams
	 */
	public Object[] getInitFuncitonParams() {
		return initFuncitonParams;
	}

	/**
	 * @param initFuncitonParams the initFuncitonParams to set
	 */
	public void setInitFuncitonParams(Object[] initFuncitonParams) {
		this.initFuncitonParams = initFuncitonParams;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the returnTypeName
	 */
	public String getReturnTypeName() {
		return returnTypeName;
	}

	/**
	 * @param returnTypeName the returnTypeName to set
	 */
	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}

	/**
	 * @return the paramTypeNames
	 */
	public String[] getParamTypeNames() {
		return paramTypeNames;
	}

	/**
	 * @param paramTypeNames the paramTypeNames to set
	 */
	public void setParamTypeNames(String[] paramTypeNames) {
		this.paramTypeNames = paramTypeNames;
	}
	
	
	
}
