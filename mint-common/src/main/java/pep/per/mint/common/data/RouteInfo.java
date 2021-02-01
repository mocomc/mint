package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RouteInfo extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4516537957979226118L;
	
	@JsonProperty
	Object routeId;

	public Object getRouteId() {
		return routeId;
	}

	public void setRouteId(Object routeId) {
		this.routeId = routeId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return routeId.toString();
	}

}
