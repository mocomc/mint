package pep.per.mint.common.parser;

import java.nio.ByteOrder;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.exception.NotSupportedDeserializeTypeException;
import pep.per.mint.common.exception.NotSupportedSerializeTypeException;
import pep.per.mint.common.util.Util;

public class IntegerTypeHandler implements TypeHandler<Integer> {

	@Override
	public Integer deserialize(int type, byte[] value, String ccsid, Integer defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception{
		if(Util.isEmpty(value) && !Util.isEmpty(defaultValue)){
			return defaultValue;
		}
		Integer result = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_INTEGER :
			result = Util.bytes2Int(value, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			String strValue = Util.toString(value,ccsid);
			strValue = Util.trim(strValue);
			result = Util.toInteger(strValue);
			break;
		default :
			throw new NotSupportedDeserializeTypeException("This deserialize data type is not unsupported(type:"+type+")");
		}
		return result;
	}

	 

	@Override
	public byte[] serialize(int type, Integer value, String ccsid, Integer defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		byte[] b = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_INTEGER :
			Integer tmp = (Util.isEmpty(value) && !Util.isEmpty(defaultValue)) ? defaultValue : Util.isEmpty(value) ? 0 : value;
			b = Util.int2Bytes(tmp, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			String strValue = Util.isEmpty(value) && !Util.isEmpty(defaultValue) ? Util.toString(defaultValue) : Util.isEmpty(value) ? "0" : Util.toString(value);
			strValue = justify == FieldDefinition.FIELD_JUSTIFY_LEFT ? Util.rightPad(strValue, length, padding) : Util.leftPad(strValue, length, padding);
			b = strValue.getBytes(ccsid);
			break;
		default :
			throw new NotSupportedSerializeTypeException("This serialize data type is not unsupported(type:"+type+")");
		}
		return b;
	}
 
	
}
