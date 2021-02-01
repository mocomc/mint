package pep.per.mint.common.data.broker;

import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ListenerServiceInfo extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1798296942278106589L;
	 
	@JsonProperty
	private String name;
	
	@JsonProperty
	private int type;
	
	@JsonProperty
	private String listenerServiceClass;
	
	@JsonProperty
	private Properties properties;
	
	@JsonProperty
	String description;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
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
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the listenerServiceClass
	 */
	public String getListenerServiceClass() {
		return listenerServiceClass;
	}

	/**
	 * @param listenerServiceClass the listenerServiceClass to set
	 */
	public void setListenerServiceClass(String listenerServiceClass) {
		this.listenerServiceClass = listenerServiceClass;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	
	
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
 

}
