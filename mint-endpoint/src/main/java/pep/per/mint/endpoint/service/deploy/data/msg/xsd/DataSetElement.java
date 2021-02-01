/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;
 
/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class DataSetElement {
	
	public final static int PRIMITIVE_TYPE = 0;
	public final static int SIMPLE_TYPE   	= 1;
	public final static int COMPLEX_TYPE 	= 2;
	
	String name;
	
	String type;
	
	String defaultValue;
	
	Annotation annotation;

	String minOccurs = "1";
	
	String maxOccurs = "1";
	
	int typeKind = PRIMITIVE_TYPE;
	
	SimpleType simpleType;
	
	ComplexType complexType;
	 
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
	 * @return the annotation
	 */
	public Annotation getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation the annotation to set
	 */
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}



	/**
	 * @return the minOccurs
	 */
	public String getMinOccurs() {
		return minOccurs;
	}

	/**
	 * @param minOccurs the minOccurs to set
	 */
	public void setMinOccurs(String minOccurs) {
		this.minOccurs = minOccurs;
	}

	/**
	 * @return the maxOccurs
	 */
	public String getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * @param maxOccurs the maxOccurs to set
	 */
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	/**
	 * @return the complexType
	 */
	public ComplexType getComplexType() {
		return complexType;
	}

	/**
	 * @param complexType the complexType to set
	 */
	public void setComplexType(ComplexType complexType) {
		this.complexType = complexType;
	}

	/**
	 * @return the typeKind
	 */
	public int getTypeKind() {
		return typeKind;
	}

	/**
	 * @param typeKind the typeKind to set
	 */
	public void setTypeKind(int typeKind) {
		this.typeKind = typeKind;
	}

	/**
	 * @return the simpleType
	 */
	public SimpleType getSimpleType() {
		return simpleType;
	}

	/**
	 * @param simpleType the simpleType to set
	 */
	public void setSimpleType(SimpleType simpleType) {
		this.simpleType = simpleType;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
 
	
}
