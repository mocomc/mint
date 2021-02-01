/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.common.data.basic.qmgr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * <pre>
 * pep.per.mint.common.data.basic.qmgr
 * Qmgr.java
 * </pre>
 * @author whoana
 * @date 2018. 9. 28.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType",visible=true)
public class QmgrProperty  extends CacheableObject {

	@JsonProperty
	String name = defaultStringValue;

	@JsonProperty
	String status = defaultStringValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
