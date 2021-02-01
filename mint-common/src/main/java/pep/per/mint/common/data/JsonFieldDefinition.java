package pep.per.mint.common.data;
 
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class JsonFieldDefinition extends FieldDefinition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480068317260503427L;

	 
}
