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
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApplicationEvent<T> extends MonitorEvent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2136723872550711094L;
	public static final int STEP_START = 0;
	public static final int STEP_PROCESSING = 1;
	public static final int STEP_END = 99999;

	public static final int STATUS_PROCESS_SUCCESS = 9;
	public static final int STATUS_PROCESS_NONE = 0;
	public static final int STATUS_PROCESS_FAIL = -1;
	
	@JsonProperty
	int step;
	
	@JsonProperty
	int status;
	
	public ApplicationEvent() {
		super();
	}
	
	/**
	 * @param eventKey
	 * @param source
	 * @param data
	 * @param eventTime
	 */
	public ApplicationEvent(String eventKey, MonitorApplication source, MonitorData<T> data, long eventTime) {
		super(eventKey, source, data, eventTime);
		this.step = STEP_START;
		this.status = STATUS_PROCESS_SUCCESS;
	}

	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
