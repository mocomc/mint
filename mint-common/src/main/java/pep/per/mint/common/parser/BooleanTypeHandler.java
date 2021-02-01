package pep.per.mint.common.parser;


import static pep.per.mint.common.util.Util.leftPad;
import static pep.per.mint.common.util.Util.rightPad;

import java.nio.ByteOrder;

import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.util.Util;

public class BooleanTypeHandler implements TypeHandler<Boolean> {

 	
	@Override
	public Boolean deserialize(int type, byte[] value, String ccsid, Boolean defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		
		if(Util.isEmpty(value) && !Util.isEmpty(defaultValue)){
			return defaultValue;
		} 
		String strValue = Util.toString(value,ccsid);
		strValue = Util.trim(strValue);
		Boolean result = Util.toBoolean(strValue);
		return result;
	}

	@Override
	public byte[] serialize(int type, Boolean value, String ccsid, Boolean defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		String str = (Util.isEmpty(value) && !Util.isEmpty(defaultValue)) ? defaultValue.toString() : Util.isEmpty(value) ? "false" : value.toString();
		str = justify == FixedLengthFieldDefinition.FIELD_JUSTIFY_LEFT ? rightPad(str, length, padding) : leftPad(str, length, padding);
		byte[] b = str.getBytes(ccsid);
		return b;
	}
	
}
