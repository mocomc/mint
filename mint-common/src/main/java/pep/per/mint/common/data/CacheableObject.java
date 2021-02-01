package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
//import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pep.per.mint.common.cache.Cacheable;

public abstract class CacheableObject implements Cacheable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8042631621681169L;
	
	/**
	 * <pre>
	 * Default String Value fro Javascript
	 * 모든 자바스크립트 String  변수에 NULL 체크를 
	 * 수행해야하는 버거로움 때문에 지정함.
	 * </pre>
	 */
	public static final String defaultStringValue = ""; 
	
	@JsonProperty
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	protected String objectType = this.getClass().getSimpleName();

	@JsonProperty
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Object id;
	
	public Object getId() {
		return id;
	}
	
	public void setId(Object id) {
		this.id = id;
	}
	
	/**
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Override
	@JsonIgnore
	public Object getIdentifier() {
		// TODO Auto-generated method stub
		return new StringBuffer().append(this.getClass().getName())
										.append("@")
										.append(id)
										.toString();
	}
	@Override
	@JsonIgnore
	public boolean isExpired() {
		// TODO Auto-generated method stub
		return false;
	}

}
