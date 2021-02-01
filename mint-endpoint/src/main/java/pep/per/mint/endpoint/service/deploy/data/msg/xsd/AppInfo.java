/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class AppInfo {

	List<Property> properties = new ArrayList<Property>();
	
	
	public void addProperty(String key, String value) {
		properties.add(new Property(key, value));	
	}
	
	public void addProperty(Property property) {
		properties.add(property);	
	}
	
	public List<Property> getProperties() {
		return properties;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}
