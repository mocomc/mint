/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 *
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author mint
 * @param <T>
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApplicationData<T> extends MonitorData<T> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3805437433522453529L;
	@JsonProperty
	//Exception exception;
	Object exception;
	/**
	 * 
	 */
	public ApplicationData() {
		super();
	}

	
	/**
	 * @return the exception
	 */
/*	public Exception getException() {
		return exception;
	}
*/
	public Object getException() {
		return exception;
	}
	
	/**
	 * @param exception the exception to set
	 */
	/*public void setException(Exception exception) {
		this.exception = exception;
	}*/
	public void setException(Object exception) {
		this.exception = exception;
	}
	
	
}
