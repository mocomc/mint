package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FixedLengthFieldDefinition extends FieldDefinition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5943293749749172594L;

	@JsonProperty
	int length;
	
	@JsonProperty
	boolean includeLengthFieldSize = false;
	
	@JsonProperty
	String lengthFieldName;

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the includeLengthFieldSize
	 */
	public boolean isIncludeLengthFieldSize() {
		return includeLengthFieldSize;
	}

	/**
	 * @param includeLengthFieldSize the includeLengthFieldSize to set
	 */
	public void setIncludeLengthFieldSize(boolean includeLengthFieldSize) {
		this.includeLengthFieldSize = includeLengthFieldSize;
	}

	/**
	 * @return the lengthFieldName
	 */
	public String getLengthFieldName() {
		return lengthFieldName;
	}

	/**
	 * @param lengthFieldName the lengthFieldName to set
	 */
	public void setLengthFieldName(String lengthFieldName) {
		this.lengthFieldName = lengthFieldName;
	}
	 
    
	
	
}
