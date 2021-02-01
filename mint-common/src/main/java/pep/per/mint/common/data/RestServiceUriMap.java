package pep.per.mint.common.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RestServiceUriMap extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3336539759980514916L;

	@JsonProperty
	Map<String, RestServiceUri> serviceUriMap = new HashMap<String,RestServiceUri>();
	
	public void put(String id, RestServiceUri restServiceUri){
		serviceUriMap.put(id, restServiceUri);
	}
	
	public RestServiceUri get(String id){
		return serviceUriMap.get(id);
	}

	/**
	 * @return the serviceUriMap
	 */
	public Map<String, RestServiceUri> getServiceUriMap() {
		return serviceUriMap;
	}

	/**
	 * @param serviceUriMap the serviceUriMap to set
	 */
	public void setServiceUriMap(Map<String, RestServiceUri> serviceUriMap) {
		this.serviceUriMap = serviceUriMap;
	}
	
	
	
}
