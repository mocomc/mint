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
 * Queue.java
 * </pre>
 * @author whoana
 * @date 2018. 9. 28.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType",visible=true)
public class QueueProperty extends CacheableObject {


	/**
	 *
	 */
	private static final long serialVersionUID = -4963481134057932714L;

	@JsonProperty
	String name = defaultStringValue;

	@JsonProperty
	String type = defaultStringValue;

	@JsonProperty
	String desc = defaultStringValue;

	@JsonProperty
	int currentDepth = 0;

	@JsonProperty
	int openInputCount = 0;

	@JsonProperty
	int openOutputCount = 0;

	@JsonProperty
	String qmgrName = defaultStringValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCurrentDepth() {
		return currentDepth;
	}

	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

	public int getOpenInputCount() {
		return openInputCount;
	}

	public void setOpenInputCount(int openInputCount) {
		this.openInputCount = openInputCount;
	}

	public int getOpenOutputCount() {
		return openOutputCount;
	}

	public void setOpenOutputCount(int openOutputCount) {
		this.openOutputCount = openOutputCount;
	}

	public String getQmgrName() {
		return qmgrName;
	}

	public void setQmgrName(String qmgrName) {
		this.qmgrName = qmgrName;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueueProperty [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", currentDepth=");
		builder.append(currentDepth);
		builder.append(", openInputCount=");
		builder.append(openInputCount);
		builder.append(", openOutputCount=");
		builder.append(openOutputCount);
		builder.append(", qmgrName=");
		builder.append(qmgrName);
		builder.append("]");
		return builder.toString();
	}


}
