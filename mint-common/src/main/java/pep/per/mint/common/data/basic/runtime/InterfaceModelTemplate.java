/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Interface;


/**
 * @author whoana
 * @since 2020. 10. 26.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterfaceModelTemplate extends CacheableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	Interface interfceInfo;
	
	@JsonProperty
	InterfaceModel interfaceModel;

	/**
	 * @return the interfze
	 */
	public Interface getInterfceInfo() {
		return interfceInfo;
	}

	/**
	 * @param interfze the interfze to set
	 */
	public void setInterfceInfo(Interface interfceInfo) {
		this.interfceInfo = interfceInfo;
	}

	/**
	 * @return the interfaceModel
	 */
	public InterfaceModel getInterfaceModel() {
		return interfaceModel;
	}

	/**
	 * @param interfaceModel the interfaceModel to set
	 */
	public void setInterfaceModel(InterfaceModel interfaceModel) {
		this.interfaceModel = interfaceModel;
	}
	
	
	
}
