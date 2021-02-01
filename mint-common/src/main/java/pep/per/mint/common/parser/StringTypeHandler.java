package pep.per.mint.common.parser;

import static pep.per.mint.common.util.Util.leftPad;
import static pep.per.mint.common.util.Util.rightPad;

import java.nio.ByteOrder;

import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.util.Util;

public class StringTypeHandler implements TypeHandler<String> {

	@Override
	public String deserialize(int type, byte[] value, String ccsid, String defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		String result = Util.toString(value, ccsid);
		result = (Util.isEmpty(result) && !Util.isEmpty(defaultValue)) ? defaultValue : Util.isEmpty(result) ? "" : result;
		result = justify == FixedLengthFieldDefinition.FIELD_JUSTIFY_LEFT ? rightPad(result, length, padding) : leftPad(result, length, padding);
		return result;
	}

	@Override
	public byte[] serialize(int type, String value, String ccsid, String defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception {
		String str = (Util.isEmpty(value) && !Util.isEmpty(defaultValue)) ? defaultValue : Util.isEmpty(value) ? "" : value;
		str = justify == FixedLengthFieldDefinition.FIELD_JUSTIFY_LEFT ? rightPad(str, length, padding) : leftPad(str, length, padding);
		byte[] b = Util.isEmpty(ccsid) ? str.getBytes() : str.getBytes(ccsid);
		return b;
	}
}
