package pep.per.mint.common.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class InterfaceList{

	List<Interface> list;

	/**
	 * @return the list
	 */
	@JsonProperty
	public List<Interface> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	@JsonProperty
	public void setList(List<Interface> list) {
		this.list = list;
	}

	
	
}
