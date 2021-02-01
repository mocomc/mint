/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whoana
 * @since Aug 6, 2020
 */
public class Restriction {
	
	String base;
	
	/*  RestrictionValue
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
	
	List<RestrictionValue> values;
	
	public Restriction(String base) {
		this.base = base;
	}
	
	public Restriction(String base, RestrictionValue value) {
		this(base, Arrays.asList(value));
	}
	
	public Restriction(String base, List<RestrictionValue> values) {
		this.base = base;
		this.values = values;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the values
	 */
	public List<RestrictionValue> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<RestrictionValue> values) {
		this.values = values;
	}

	
	
}
