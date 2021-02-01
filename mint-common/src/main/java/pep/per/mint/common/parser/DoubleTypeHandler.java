package pep.per.mint.common.parser;


import java.nio.ByteOrder;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.exception.NotSupportedDeserializeTypeException;
import pep.per.mint.common.exception.NotSupportedSerializeTypeException;
import pep.per.mint.common.util.Util;

/**
 * 문자열 또는 바이트 배열 입력데이타를 지정된 더블형 타입의 객체로 변화하는 TypeHandler.
 * @author Solution TF
 *
 */
public class DoubleTypeHandler implements TypeHandler<Double> {

	@Override
	public Double deserialize(int type, byte[] value, String ccsid, Double defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception{
		if(Util.isEmpty(value) && !Util.isEmpty(defaultValue)){
			return defaultValue;
		}
		Double result = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_DOUBLE :
			result = Util.bytes2Double(value, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			String strValue = Util.toString(value,ccsid);
			strValue = Util.trim(strValue);
			result = Util.toDouble(strValue);
			break;
		default :
			throw new NotSupportedDeserializeTypeException(this.getClass().getSimpleName() +" deserialize data type is not unsupported(type:"+type+")");
		}
		return result;
	}

	 

	@Override
	public byte[] serialize(int type, Double value, String ccsid, Double defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		byte[] b = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BIN_DOUBLE :
			Double tmp = (Util.isEmpty(value) && !Util.isEmpty(defaultValue)) ? defaultValue : value;
			b = Util.double2Bytes(tmp, order);
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
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
