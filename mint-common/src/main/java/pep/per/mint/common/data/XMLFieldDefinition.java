package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class XMLFieldDefinition extends FieldDefinition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5943293749749172594L;
 
	
	//xml 의 경우 필드속성을 정의하는 맵을 멤버로 두도록 작업한다.
	//Map<String, FieldAttributeDefinition> fadm = new LinkedHashMap<String, FieldAttributeDefinition>();
	
}
