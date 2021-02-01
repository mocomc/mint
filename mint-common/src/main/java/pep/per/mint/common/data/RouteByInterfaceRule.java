package pep.per.mint.common.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RouteByInterfaceRule extends Rule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4247035754595988892L;
	
	/**
	 * Map<interfaceId, servierId>
	 * 
	 */
	@JsonProperty
	Map routeMap;

	public RouteByInterfaceRule(){
		super();
	}

	public Map getRouteMap() {
		return routeMap;
	}


	public void setRouteMap(Map routeMap) {
		this.routeMap = routeMap;
	}
	
	
	
}
