package pep.per.mint.common.parser;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.exception.ParserException;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;
import static pep.per.mint.common.util.Util.*;

public class FixedLengthParser extends Parser<byte[], Message> {

	final static Logger logger = LoggerFactory.getLogger(FixedLengthParser.class);
	final static String debugSerperator = "#-------------------------------------------------------------------------------------------------------------\n";
	
	public FixedLengthParser(MessageSet messageSet) throws ParserException {
		super(messageSet);
	} 
	
	
	@Override
	public Message parse(byte[] inputMsg, StringBuffer parseLog) throws ParserException {
		
		long elapsed = 0l;
		
		if(logger.isDebugEnabled() && parseLog != null){
			parseLog = new StringBuffer("").append(Util.LINE_SEPARATOR);
			parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
			parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) start").append(Util.LINE_SEPARATOR);
		}
		
		try{
			if(Util.isEmpty(inputMsg)) {
				throw new ParserException(ExceptionResource.getString("PAR0002", FixedLengthParser.class.getName(), messageSet.getName()),"PAR0002");
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" parameter byte[] inputMsg's length :[").append(inputMsg.length).append("]").append(Util.LINE_SEPARATOR);
			}
			
			Message msg = new Message(inputMsg);
			String name = messageSet.getName();
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" MessageSet's name :[").append(name).append("]").append(Util.LINE_SEPARATOR);
			}
	
			Element fixedLength = new Element(name);

			MessageUtil.addMsgElement(msg, fixedLength);
			
			LinkedHashMap<Object, FieldDefinition> fdm = messageSet.getFieldDefinitionMap();
			
			
			Collection<FieldDefinition> c = fdm.values();
			
			
			try{
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg toString:[").append(Util.toString(inputMsg, messageSet.getCcsid())).append("]").append(Util.LINE_SEPARATOR);
			}catch(UnsupportedEncodingException e){}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg hexdump:").append(Util.LINE_SEPARATOR);
				String dump = Util.hexdump(inputMsg, messageSet.getCcsid());
				//String dump = Util.hexdump(inputMsg);
				parseLog.append(dump).append("").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				parseLog.append("# start field parsing ").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
			}
			elapsed = System.currentTimeMillis();
			
			
			int fieldOffset = 0;
			
			for (FieldDefinition fd : c) {
				
				fieldOffset = parseField(fd, inputMsg, msg, fixedLength, fieldOffset, parseLog);
				
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) result:success!").append(Util.LINE_SEPARATOR);
			}
			return msg;
		}catch(ParserException e){
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) result:exception occured!\n").append(e.getMessage()).append(Util.LINE_SEPARATOR);
			}
			throw e;
		}finally{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
		}
	}
	
	private int parseField(FieldDefinition fd, byte[] inputMsg, Message msg, Element fixedLengthElement, int fieldOffset, StringBuffer debugBuff)  throws ParserException{
		
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			String fieldName = fsd.getName();
			
			int repeatCount = getRepeatCount(fsd.getRepeatCount(), fsd, msg); 
			
			if(logger.isDebugEnabled()){
				debugBuff.append(debugSerperator);
			}
			
			for(int i = 0 ; i < repeatCount + 1 ; i ++){
				
				if(logger.isDebugEnabled()){
					debugBuff
					.append("# [")
					.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
					.append("] ")
					.append(Util.leftPad("{fieldSet :" + fsd.getFieldPath().toString(),50," "))
					.append("}\n")
					.append(debugSerperator);
				}
				
				Element childElement = new Element(fieldName);
				fixedLengthElement.addChild(childElement);
				Collection<FieldDefinition> fc = fsd.getFieldDefinitionMap().values();
				for (FieldDefinition fieldDefinition : fc) {
					fieldOffset = parseField(fieldDefinition, inputMsg, msg, childElement, fieldOffset, debugBuff);
				}
				
				if(logger.isDebugEnabled()){
					debugBuff.append(debugSerperator);
				}
			}
		}else{
			FixedLengthFieldDefinition flfd= (FixedLengthFieldDefinition)fd;
			
			int length = getLength(flfd.getLength(), flfd, msg);
			
			int justify = flfd.getJustify();
			String fieldName = flfd.getName();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(flfd.getPaddingValue());
			Object defaultValue = flfd.getDefaultValue();
			int type = flfd.getType();
			int repeatCount = getRepeatCount(flfd.getRepeatCount(), flfd, msg);
			for(int i = 0 ; i < repeatCount + 1 ; i ++){
				byte[] data = new byte[length];
				try{
					System.arraycopy(inputMsg, fieldOffset, data, 0, length);
				}catch(ArrayIndexOutOfBoundsException e){
					String errmsgKey = "PAR0004";
					if(fieldOffset + length > inputMsg.length ) errmsgKey = "PAR0005";
					Object [] arguments = {
							FixedLengthParser.class.getName(), 
							messageSet.getName(),
							flfd.getFieldPath(),
							fieldOffset,
							length,
							inputMsg.length,
							new String(inputMsg)
					};
					String errmsg = ExceptionResource.getString(errmsgKey, arguments);
					ParserException pe = new ParserException(errmsg, e, errmsgKey);
					pe.setMessageSet(messageSet);
					pe.setFieldDefinition(flfd);
					throw pe;
				}catch(Exception e){
					//PAR0004=메시지파싱시 필드 배열  copy중 예외발생되었습니다 ParserException.getFieldDefinition() 할수를 통해 좀터 상세한 필드정보를 확인해 볼 수 있습니다.
					//(파서:{0}, 메시지셋:{1}, 필드:{2}, 파싱offset:{3}, 원메시지길이:{4}, 원메시지String:[{5}])	
					Object [] arguments = {
							FixedLengthParser.class.getName(), 
							messageSet.getName(),
							flfd.getFieldPath(),
							fieldOffset,
							length,
							inputMsg.length,
							new String(inputMsg)
					};
					String errmsg = ExceptionResource.getString("PAR0004", arguments);
					ParserException pe = new ParserException(errmsg, e, "PAR0004");
					pe.setMessageSet(messageSet);
					pe.setFieldDefinition(flfd);
					throw pe;
				}
				
				fieldOffset = fieldOffset + length;
				
				Serializable value = null;
				try {
					value = deserialize(data, type, padding, ccsid, justify,length, defaultValue, flfd);
				} catch (ParserException pe){
					pe.setMessageSet(messageSet);
					pe.setFieldDefinition(flfd);
					throw pe;
				} catch (Exception e) {
					ParserException pe = new ParserException(e);
					pe.setMessageSet(messageSet);
					pe.setFieldDefinition(flfd);
					throw pe;
				}
				Element element = new Element(fieldName, value);
				fixedLengthElement.addChild(element);

				if(logger.isDebugEnabled()){
					debugBuff
					.append("# [")
					.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
					.append("] ")
					.append(Util.leftPad("{field :" + flfd.getFieldPath(),50," "))
					.append(", value:")
					.append(Util.toString(value))
					.append("}").append(Util.LINE_SEPARATOR);
				}
			}
			
			
		}
		
		return fieldOffset;
	}
	
	private Serializable deserialize(byte[] data, int type, String padding, String ccsid, int justify,int length, Object defaultValue, FixedLengthFieldDefinition flfd) throws Exception {
		Serializable result = null;
		String strValue = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BINARY :
			result = data;
			break;
		case FieldDefinition.FIELD_TYPE_STR_SHORT :
			
			try{
				result = shortTypeHandler.deserialize(type, data, ccsid, (Short)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			}catch(NumberFormatException e){
				strValue = Util.toString(data);
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getFieldPath(),
						strValue,
						"Short"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e,"PAR0100");
				throw pe;
			}
			
			break;			
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			
			try{
				result = integerTypeHandler.deserialize(type, data, ccsid, (Integer)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			}catch(NumberFormatException e){
				strValue = Util.toString(data);
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getFieldPath(),
						strValue,
						"Integer"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e, "PAR0100");
				throw pe;
			}
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_LONG :
			
			try{
				result = longTypeHandler.deserialize(type, data, ccsid, (Long)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			}catch(NumberFormatException e){
				strValue = Util.toString(data);
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getFieldPath(),
						strValue,
						"Long"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e,"PAR0100");
				throw pe;
			}
			
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			
			try{
				result = floatTypeHandler.deserialize(type, data, ccsid, (Float)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			}catch(NumberFormatException e){
				strValue = Util.toString(data);
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getFieldPath(),
						strValue,
						"Float"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e,"PAR0100");
				throw pe;
			}
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			
			try{
				strValue = Util.toString(data);
				result = doubleTypeHandler.deserialize(type, data, ccsid, (Double)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			}catch(NumberFormatException e){
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getFieldPath(),
						strValue,
						"Double"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e,"PAR0100");
				throw pe;
			}			
			break;
		case FieldDefinition.FIELD_TYPE_BIN_SHORT :
			result = shortTypeHandler.deserialize(type, data, ccsid, (Short)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;			
		case FieldDefinition.FIELD_TYPE_BIN_INTEGER :
			result = integerTypeHandler.deserialize(type, data, ccsid, (Integer)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_LONG :
			result = longTypeHandler.deserialize(type, data, ccsid, (Long)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_FLOAT :
			result = floatTypeHandler.deserialize(type, data, ccsid, (Float)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_DOUBLE :
			result = doubleTypeHandler.deserialize(type, data, ccsid, (Double)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BOOLEAN :
			result = booleanTypeHandler.deserialize(type, data, ccsid, (Boolean)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_STRING :
			result = stringTypeHandler.deserialize(type, data, ccsid, (String)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		default:
			strValue = Util.toString(data);
			Object [] arguments = {
					FixedLengthParser.class.getName(), 
					messageSet.getName(),
					flfd.getFieldPath(),
					strValue,
					type
			};
			String errmsg = ExceptionResource.getString("PAR0101", arguments);
			ParserException pe = new ParserException(errmsg,"PAR0101");
			throw pe;
			
		}
		
		
		return result;
	}
	
	private int getRepeatCount(int repeatCount, FieldDefinition fd, Message msg) throws ParserException {
		String repeatFieldName = fd.getRepeatFieldName();
		if(isEmpty(repeatFieldName)) return repeatCount;
		FieldPath path = new FieldPath(repeatFieldName);
		//Element<Integer> element = msg.getMsgElement(path,0);
		Element<Integer> element = null;
		try{
			element = MessageUtil.getElement(msg, path);
		}catch(Exception e){
			ParserException pe = new ParserException("필드반복레퍼런스객체를 얻을 수 없습니다.필드반복레퍼런스객체를 얻는중 예외가발생되었습니다.",e);
			pe.setMessageSet(messageSet);
			pe.setFieldDefinition(fd);
			throw pe;
		}
		
		if(isEmpty(element)) {
			//PAR0005=필드반복레퍼런스객체를 얻을 수 없습니다.반복필드이름이 제대로 세팅되었는지 확인필요합니다.(파서:{0}, 메시지셋:{1}, 필드:{2}, 반복필드명:{3})
			Object [] arguments = {
					FixedLengthParser.class.getName(), 
					messageSet.getName(),
					fd.getName(),
					repeatFieldName
			};
			String errmsg = ExceptionResource.getString("PAR0006", arguments);
			ParserException pe = new ParserException(errmsg,"PAR0006");
			pe.setMessageSet(messageSet);
			pe.setFieldDefinition(fd);
			throw pe;
		}
		
		Integer repeatFieldValue = element.getValue();
		
		if(isEmpty(repeatFieldValue)) {
			return repeatCount;
		}
		
		return repeatFieldValue;
	}

	private int getLength(int length, FixedLengthFieldDefinition fd, Message msg) throws ParserException{
		String lengthFieldName = fd.getLengthFieldName();
		if(isEmpty(lengthFieldName)) return length;
		FieldPath path = new FieldPath(lengthFieldName);
		//Element<Integer> element = msg.getMsgElement(path,0);
		Element<Integer> element = null;
		try{
			element = MessageUtil.getElement(msg, path);
		}catch(Exception e){
			ParserException pe = new ParserException("필드길이레퍼런스객체를 얻는중 예외가 발생하였습니다.", e);
			pe.setMessageSet(messageSet);
			pe.setFieldDefinition(fd);
			throw pe;
		}
		
		if(isEmpty(element)) {
			//PAR0006=필드길이레퍼런스객체를 얻을 수 없습니다.길이필드이름이 제대로 세팅되었는지 확인필요합니다.(파서:{0}, 메시지셋:{1}, 필드:{2}, 길이필드명:{3})
			Object [] arguments = {
					FixedLengthParser.class.getName(), 
					messageSet.getName(),
					fd.getName(),
					lengthFieldName
			};
			String errmsg = ExceptionResource.getString("PAR0007", arguments);
			ParserException pe = new ParserException(errmsg,"PAR0007");
			pe.setMessageSet(messageSet);
			pe.setFieldDefinition(fd);
			throw pe;
		}
		
		
		Integer lengthFieldValue = element.getValue();
		if(isEmpty(lengthFieldValue)){
			 return length;
		}
		return lengthFieldValue;
	}

	@Override
	public byte[] build(Message msg, StringBuffer parseLog) throws ParserException {
		long elapsed = System.currentTimeMillis();
		try{
			if(logger.isDebugEnabled() && parseLog!= null){
				parseLog = new StringBuffer("").append(Util.LINE_SEPARATOR);
				parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(FixedLengthParser.class.getName()).append(".build(Message msg) start").append(Util.LINE_SEPARATOR);
			}
			
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
	
			
			Element root = msg.getMsgElement();
			String messageSetName = messageSet.getName();
			Element fixed = root.getChildAtFirst(messageSetName);
			
			Collection<FieldDefinition> col = messageSet.getFieldDefinitionMap().values();
			for (FieldDefinition fd : col) {
				String name = fd.getName();
				List<Element> list = fixed.getChildList(name);
				
				for (Element element : list) {
					buildField(msg, element, fd, output, parseLog);
				}
			}
			
			return output.toByteArray();
		}finally{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
		} 
	}
	
	private ByteArrayOutputStream buildField(Message msg, Element currentFieldElement, FieldDefinition fd, ByteArrayOutputStream output, StringBuffer debugBuff) throws ParserException{
		
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			 	
			Collection<FieldDefinition> collection = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : collection) {
				String name = fieldDefinition.getName();
				List<Element> list = currentFieldElement.getChildList(name);
				if((list == null) ){
					if(fieldDefinition.isRequired()) throw new ParserException(Util.join("메시지빌드예외:반드시 있어야하는 필드[", fieldDefinition.getFieldPath(),"] 값이 존재하지 안습니다."));
					else
						continue;
				}
				for (Element element : list) {
					buildField(msg, element, fieldDefinition, output, debugBuff);
				}
			}
			 
		}else{
			FixedLengthFieldDefinition flfd= (FixedLengthFieldDefinition)fd;
			
			int length = getLength(flfd.getLength(), flfd, msg);
			
			int justify = flfd.getJustify();
			String fieldName = flfd.getName();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(flfd.getPaddingValue());
			Object defaultValue = flfd.getDefaultValue();
			int type = flfd.getType();

			
			Object value = currentFieldElement.getValue();
			try{
			
				byte[] b= serialize(value, type, padding, ccsid, justify,length, defaultValue, flfd);
				output.write(b);
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}catch(Exception e){
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
		}
		 
		
		
		return output;
	}

	private byte[] serialize(Object value, int type, String padding, String ccsid, int justify,int length, Object defaultValue, FixedLengthFieldDefinition flfd) throws Exception {


		byte[] result = null;
		//String strValue = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BINARY :
			result = (byte[])value;
			break;
		case FieldDefinition.FIELD_TYPE_STR_SHORT :
			result = shortTypeHandler.serialize(type, (Short)value, ccsid, (Short)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			if(result.length > length){
				//필드[{2}]의 값["{3}"]의 길이[{4}]가 필드셋에 정의된 길이[{5}] 보다 길어서 값을 세팅할 수 없습니다.\n(파서:{0}, 메시지셋:{1}, 필드:{2})
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getName(),
						value,
						result.length,
						length
				};
				String errmsg = ExceptionResource.getString("BLD0100", arguments);
				ParserException pe = new ParserException(errmsg,"BLD0100");
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			result = integerTypeHandler.serialize(type, (Integer)value, ccsid, (Integer)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			if(result.length > length){
				//필드[{2}]의 값["{3}"]의 길이[{4}]가 필드셋에 정의된 길이[{5}] 보다 길어서 값을 세팅할 수 없습니다.\n(파서:{0}, 메시지셋:{1}, 필드:{2})
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getName(),
						value,
						result.length,
						length
				};
				String errmsg = ExceptionResource.getString("BLD0100", arguments);
				ParserException pe = new ParserException(errmsg,"BLD0100");
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_LONG :
			
			result = longTypeHandler.serialize(type, (Long)value, ccsid, (Long)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			
			if(result.length > length){
				//필드[{2}]의 값["{3}"]의 길이[{4}]가 필드셋에 정의된 길이[{5}] 보다 길어서 값을 세팅할 수 없습니다.\n(파서:{0}, 메시지셋:{1}, 필드:{2})
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getName(),
						value,
						result.length,
						length
				};
				String errmsg = ExceptionResource.getString("BLD0100", arguments);
				ParserException pe = new ParserException(errmsg,"BLD0100");
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
			
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			
			result = floatTypeHandler.serialize(type, (Float)value, ccsid, (Float)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			
			if(result.length > length){
				//필드[{2}]의 값["{3}"]의 길이[{4}]가 필드셋에 정의된 길이[{5}] 보다 길어서 값을 세팅할 수 없습니다.\n(파서:{0}, 메시지셋:{1}, 필드:{2})
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getName(),
						value,
						result.length,
						length
				};
				String errmsg = ExceptionResource.getString("BLD0100", arguments);
				ParserException pe = new ParserException(errmsg,"BLD0100");
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
			
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			
			result = doubleTypeHandler.serialize(type, (Double)value, ccsid, (Double)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			
			if(result.length > length){
				//필드[{2}]의 값["{3}"]의 길이[{4}]가 필드셋에 정의된 길이[{5}] 보다 길어서 값을 세팅할 수 없습니다.\n(파서:{0}, 메시지셋:{1}, 필드:{2})
				Object [] arguments = {
						FixedLengthParser.class.getName(), 
						messageSet.getName(),
						flfd.getName(),
						value,
						result.length,
						length
				};
				String errmsg = ExceptionResource.getString("BLD0100", arguments);
				ParserException pe = new ParserException(errmsg,"BLD0100");
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(flfd);
				throw pe;
			}
		
			break;
		case FieldDefinition.FIELD_TYPE_BIN_SHORT :
			result = shortTypeHandler.serialize(type, (Short)value, ccsid, (Short)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_INTEGER :
			result = integerTypeHandler.serialize(type, (Integer)value, ccsid, (Integer)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_LONG :
			result = longTypeHandler.serialize(type, (Long)value, ccsid, (Long)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_FLOAT :
			result = floatTypeHandler.serialize(type, (Float)value, ccsid, (Float)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_DOUBLE :
			result = doubleTypeHandler.serialize(type, (Double)value, ccsid, (Double)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_BOOLEAN :
			result = booleanTypeHandler.serialize(type, (Boolean)value, ccsid, (Boolean)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		case FieldDefinition.FIELD_TYPE_STRING :
			result = stringTypeHandler.serialize(type, (String)value, ccsid, (String)defaultValue, justify, length, padding, ByteOrder.BIG_ENDIAN);
			break;
		default:
			String strValue = Util.toString(value);
			Object [] arguments = {
					FixedLengthParser.class.getName(), 
					messageSet.getName(),
					flfd.getFieldPath(),
					strValue,
					type
			};
			String errmsg = ExceptionResource.getString("PAR0101", arguments);
			ParserException pe = new ParserException(errmsg,"PAR0101");
			throw pe;
			
		}
		
		
		return result;
		
		
	}
	 
	
	
	 
}
