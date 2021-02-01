/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author whoana
 * @since Jul 30, 2020
 */
public class ServerInfo {
	
	@JsonProperty
	String cd;
	
	@JsonProperty
	String name;
	
	/**
	 * 
	 */
	public ServerInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param cd
	 * @param name
	 */
	public ServerInfo(String cd, String name) {
		this();
		this.cd = cd;
		this.name = name;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
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
	
	
}
