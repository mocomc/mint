/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class ComplexType {

	String name;
	 
	
	Sequence sequence;

	Map<String, DataSetElement> elementMap = new LinkedHashMap<String, DataSetElement>();
	
	public ComplexType() {
		super();
	}
	
	public ComplexType(String name) {
		this();
		this.name = name;
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
	 * @return the sequence
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	} 
 
	public void addElement(DataSetElement element) {
		elementMap.put(element.getName(), element);
	}
	
	public Collection<DataSetElement> getElements(){
		return elementMap.values();
	}

	 
}
