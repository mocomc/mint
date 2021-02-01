package pep.per.mint.common.data.basic.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;


/**
 * EAI Engine Status Data Object
 *
 * @author isjang
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class EngineDashboard  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 7231047060881928880L;

	@JsonProperty("engineLimitCount")
	EngineLimitCount engineLimitCount;

	public EngineLimitCount getEngineLimitCount() {
		return engineLimitCount;
	}

	public void setEngineLimitCount(EngineLimitCount engineLimitCount) {
		this.engineLimitCount = engineLimitCount;
	}



}
