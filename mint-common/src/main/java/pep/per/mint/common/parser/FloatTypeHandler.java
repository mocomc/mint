package pep.per.mint.common.parser;


import java.nio.ByteOrder;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.exception.NotSupportedDeserializeTypeException;
import pep.per.mint.common.exception.NotSupportedSerializeTypeException;
import pep.per.mint.common.util.Util;

public class FloatTypeHandler implements TypeHandler<Float> {

	@Override
	public Float deserialize(int type, byte[] value, String ccsid, Float defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception{
		if(Util.isEmpty(value) && !Util.isEmpty(defaultValue)){
			return defaultValue;
		}
		Float result = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_FLOAT :
			result = Util.bytes2Float(value, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			String strValue = Util.toString(value,ccsid);
			strValue = Util.trim(strValue);
			result = Util.toFloat(strValue);
			break;
		default :
			throw new NotSupportedDeserializeTypeException(this.getClass().getSimpleName() +" deserialize data type is not unsupported(type:"+type+")");
		}
		return result;
	}

	 

	@Override
	public byte[] serialize(int type, Float value, String ccsid, Float defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		byte[] b = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_FLOAT :
			Float tmp = (Util.isEmpty(value) && !Util.isEmpty(defaultValue)) ? defaultValue : value;
			b = Util.float2Bytes(tmp, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			String strValue = Util.isEmpty(value) && !Util.isEmpty(defaultValue) ? Util.toString(defaultValue) : Util.isEmpty(value) ? "0" : Util.toString(value);
			strValue = justify == FieldDefinition.FIELD_JUSTIFY_LEFT ? Util.rightPad(strValue, length, padding) : Util.leftPad(strValue, length, padding);
			b = strValue.getBytes(ccsid);
			break;
		default :
			
			throw new NotSupportedSerializeTypeException(this.getClass().getSimpleName() +" This serialize data type is not unsupported(type:"+type+")");
		}
		return b;
	}
}
