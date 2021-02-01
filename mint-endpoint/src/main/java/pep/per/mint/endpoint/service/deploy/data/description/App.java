/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author whoana
 * @since Jul 30, 2020
 */
public class App {

	@JsonProperty
	String id;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	AppType type;
	
	@JsonProperty
	SystemInfo system;
	
	@JsonProperty
	ServerInfo server;
	
	@JsonProperty
	Map<String, Object> props = new HashMap<String, Object>();
	
	@JsonProperty
	List<Map<String, String>> layouts = new ArrayList<Map<String, String>>();

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
	public AppType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AppType type) {
		this.type = type;
	}

	/**
	 * @return the system
	 */
	public SystemInfo getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(SystemInfo system) {
		this.system = system;
	}

	/**
	 * @return the server
	 */
	public ServerInfo getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(ServerInfo server) {
		this.server = server;
	}

	/**
	 * @return the props
	 */
	public Map<String, Object> getProps() {
		return props;
	}

	/**
	 * @param props the props to set
	 */
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	/**
	 * @return the layouts
	 */
	public List<Map<String, String>> getLayouts() {
		return layouts;
	}

	/**
	 * @param layouts the layouts to set
	 */
	public void setLayouts(List<Map<String, String>> layouts) {
		this.layouts = layouts;
	}
	
	

}
