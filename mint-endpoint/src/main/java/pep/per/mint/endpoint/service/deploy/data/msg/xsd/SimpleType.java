/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class SimpleType {
	
	Restriction restriction;
	
	String name;

	/**
	 * @return the restriction
	 */
	public Restriction getRestriction() {
		return restriction;
	}

	/**
	 * @param restriction the restriction to set
	 */
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
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
