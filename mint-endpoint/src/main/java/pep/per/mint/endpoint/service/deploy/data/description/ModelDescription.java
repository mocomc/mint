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
 * <pre>
 * 인터페이스 모델 배포 Discriptor
 * </pre>
 * @author whoana
 * @since Jul 30, 2020
 */
public class ModelDescription {

	@JsonProperty
	String id;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	String version;	
	
	@JsonProperty
	String stage;	
	
	@JsonProperty
	String createDate;
	
	@JsonProperty
	String interfaceId;
	
	@JsonProperty
	List<App> apps = new ArrayList<App>();
	
	@JsonProperty
	Map<String, Layout> layouts = new HashMap<String, Layout>();
	
	@JsonProperty
	Map<String, Mapping> mappings = new HashMap<String, Mapping>();

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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the apps
	 */
	public List<App> getApps() {
		return apps;
	}

	/**
	 * @param apps the apps to set
	 */
	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	/**
	 * @return the layouts
	 */
	public Map<String, Layout> getLayouts() {
		return layouts;
	}

	/**
	 * @param layouts the layouts to set
	 */
	public void setLayouts(Map<String, Layout> layouts) {
		this.layouts = layouts;
	}

	/**
	 * @return the mappings
	 */
	public Map<String, Mapping> getMappings() {
		return mappings;
	}

	/**
	 * @param mappings the mappings to set
	 */
	public void setMappings(Map<String, Mapping> mappings) {
		this.mappings = mappings;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	
}
