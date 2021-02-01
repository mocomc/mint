/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.mapping;

/**
 * @author whoana
 * @since Aug 13, 2020
 */
public class FieldPath {
	
	String fieldId;
	
	String fieldCd;
	
	String dataSetId;
	
	String dataSetCd;
	
	/** field name*/
	String name;
	
	/** field path*/
	String path;

	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the fieldCd
	 */
	public String getFieldCd() {
		return fieldCd;
	}

	/**
	 * @param fieldCd the fieldCd to set
	 */
	public void setFieldCd(String fieldCd) {
		this.fieldCd = fieldCd;
	}

	/**
	 * @return the dataSetId
	 */
	public String getDataSetId() {
		return dataSetId;
	}

	/**
	 * @param dataSetId the dataSetId to set
	 */
	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	/**
	 * @return the dataSetCd
	 */
	public String getDataSetCd() {
		return dataSetCd;
	}

	/**
	 * @param dataSetCd the dataSetCd to set
	 */
	public void setDataSetCd(String dataSetCd) {
		this.dataSetCd = dataSetCd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
}
