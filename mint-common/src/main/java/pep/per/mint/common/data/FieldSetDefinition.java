package pep.per.mint.common.data;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FieldSetDefinition extends FieldDefinition{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6003648131158845265L;

	@JsonProperty
	LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap = new LinkedHashMap<Object, FieldDefinition>();

	
	public FieldSetDefinition() {
		super();
		// TODO Auto-generated constructor stub
		isFieldSet = true;
	}

	/**
	 * @return the fieldDefinitionMap
	 */
	public LinkedHashMap<Object, FieldDefinition> getFieldDefinitionMap() {
		return fieldDefinitionMap;
	}

	/**
	 * @param fieldDefinitionMap the fieldDefinitionMap to set
	 */
	public void setFieldDefinitionMap(
			LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap) {
		this.fieldDefinitionMap = fieldDefinitionMap;
	}

	
	
}
