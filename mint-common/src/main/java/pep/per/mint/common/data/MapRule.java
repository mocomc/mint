package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MapRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7204313038411565619L;
	
	@JsonProperty
	Object mapId;

	public Object getMapId() {
		return mapId;
	}

	public void setMapId(Object mapId) {
		this.mapId = mapId;
	}
	

}
