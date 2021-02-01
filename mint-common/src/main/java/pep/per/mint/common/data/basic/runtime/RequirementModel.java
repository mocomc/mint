/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.runtime;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataSet;

/**
 * <pre>
 * Requirement 등록과 InterfaceModel 등록 트랜잭션을 통합하기 위한 클래스 정의 
 * </pre>
 * @author whoana
 * @since 2020. 11. 3.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequirementModel extends CacheableObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	@JsonProperty("regId")
	String regId = defaultStringValue;
	
	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	@JsonProperty("modId")
	String modId = defaultStringValue;
	
	
	@JsonProperty
	Requirement requirement;
	
	@JsonProperty
	InterfaceModel interfaceModel;

	@JsonProperty
	Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
	
	@JsonProperty
	Map<String, DataMap> mappingMap = new HashMap<String, DataMap>();
	
	/**
	 * @return the requirement
	 */
	public Requirement getRequirement() {
		return requirement;
	}

	/**
	 * @param requirement the requirement to set
	 */
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
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

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the dataSetMap
	 */
	public Map<String, DataSet> getDataSetMap() {
		return dataSetMap;
	}

	/**
	 * @param dataSetMap the dataSetMap to set
	 */
	public void setDataSetMap(Map<String, DataSet> dataSetMap) {
		this.dataSetMap = dataSetMap;
	}

	/**
	 * @return the mappingMap
	 */
	public Map<String, DataMap> getMappingMap() {
		return mappingMap;
	}

	/**
	 * @param mappingMap the mappingMap to set
	 */
	public void setMappingMap(Map<String, DataMap> mappingMap) {
		this.mappingMap = mappingMap;
	}
	
	
	
	

}
