/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

/**
 * @author whoana
 * @since Aug 6, 2020
 */
public class RestrictionValue {
	
	/*
	enumeration		Defines a list of acceptable values
	fractionDigits	Specifies the maximum number of decimal places allowed. Must be equal to or greater than zero
	length			Specifies the exact number of characters or list items allowed. Must be equal to or greater than zero
	maxExclusive	Specifies the upper bounds for numeric values (the value must be less than this value)
	maxInclusive	Specifies the upper bounds for numeric values (the value must be less than or equal to this value)
	maxLength		Specifies the maximum number of characters or list items allowed. Must be equal to or greater than zero
	minExclusive	Specifies the lower bounds for numeric values (the value must be greater than this value)
	minInclusive	Specifies the lower bounds for numeric values (the value must be greater than or equal to this value)
	minLength		Specifies the minimum number of characters or list items allowed. Must be equal to or greater than zero
	pattern			Defines the exact sequence of characters that are acceptable
	totalDigits		Specifies the exact number of digits allowed. Must be greater than zero
	whiteSpace		Specifies how white space (line feeds, tabs, spaces, and carriage returns) is handled
	*/
	
	public static final String enumeration		= "enumeration";
	public static final String fractionDigits	= "fractionDigits";
	public static final String length			= "length";
	public static final String maxExclusive		= "maxExclusive";
	public static final String maxInclusive		= "maxInclusive";
	public static final String maxLength		= "maxLength";
	public static final String minExclusive		= "minExclusive";
	public static final String minInclusive		= "minInclusive";
	public static final String minLength		= "minLength";
	public static final String pattern			= "pattern";
	public static final String totalDigits		= "totalDigits";
	public static final String whiteSpace		= "whiteSpace";
	
	String name; 
	String value;
	
	
	public RestrictionValue(String name, String value) {
		this.name = name;
		this.value = value;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
