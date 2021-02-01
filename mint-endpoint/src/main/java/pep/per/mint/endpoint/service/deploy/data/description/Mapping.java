/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author whoana
 * @since Jul 30, 2020
 */
public class Mapping {
	@JsonProperty
	String id;
	
	@JsonProperty
	String name;
	
	
	
	@JsonProperty
	String type; 
	
	@JsonProperty
	String definition;
	/**
	 * 
	 */
	public Mapping() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param inputLayout
	 * @param outputLayout
	 * @param definition
	 */
	public Mapping(String id, String name, String type, String definition) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.definition = definition;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	 
	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	
}
