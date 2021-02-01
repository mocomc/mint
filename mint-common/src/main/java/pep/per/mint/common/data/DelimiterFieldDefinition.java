/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.data;
 
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DelimiterFieldDefinition extends FieldDefinition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3609103044378385214L;

	
	
	
}
