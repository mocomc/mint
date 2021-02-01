/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

/**
 * @author whoana
 * @since Aug 6, 2020
 */
public abstract class ElementType {
/*

 https://www.w3.org/TR/2012/REC-xmlschema11-2-20120405/datatypes.html#string
 
 3.3 Primitive Datatypes
        3.3.1 string
        3.3.2 boolean
        3.3.3 decimal
        3.3.4 float
        3.3.5 double
        3.3.6 duration
        3.3.7 dateTime
        3.3.8 time
        3.3.9 date
        3.3.10 gYearMonth
        3.3.11 gYear
        3.3.12 gMonthDay
        3.3.13 gDay
        3.3.14 gMonth
        3.3.15 hexBinary
        3.3.16 base64Binary
        3.3.17 anyURI
        3.3.18 QName
        3.3.19 NOTATION
 */
	
	//2020.08
	//일단 이정도만 구현 범위로 지정한다.
	final static String TYPE_STRING  	= "string";
	final static String TYPE_DECIMAL 	= "decimal";
	final static String TYPE_INTEGER 	= "integer";
	final static String TYPE_LONG 	 	= "long";
	final static String TYPE_FLOAT 	 	= "long";
	final static String TYPE_DOUBLE  	= "double";
	final static String TYPE_BOOLEAN 	= "boolean";
	final static String TYPE_DATETIME	= "dateTime";
	final static String TYPE_DATE 	 	= "date";
	final static String TYPE_TIME 	 	= "time";
	
	
	
	String type;
	
	
	public ElementType() {
		super();
	}
	
	/**
	 * 
	 */
	public ElementType(String type) {
		this();
		this.type = type;
	}
	
	public void setType(String type) {this.type = type;}
	public String getType(String type) {return type;}

}
