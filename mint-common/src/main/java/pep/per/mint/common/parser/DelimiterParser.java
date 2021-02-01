/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.parser;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.DelimiterFieldDefinition;
import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.exception.ParserException;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * 지정된 메시지셋에 의해 입력으로 받아들인 delimiter 구분 메시지를 파싱한다.
 * </blockquote>
 * @author Solution TF
 * @since 2004
 * @see FixedLengthParser
 * @see XmlDomParser
 * @see DelimiterParser
 */
public class DelimiterParser extends Parser<byte[], Message> {

	public static Logger logger = LoggerFactory.getLogger(DelimiterParser.class); 
	final static String debugSerperator = "#-------------------------------------------------------------------------------------------------------------\n";
	
	public final static String PK_DELIMITER = "msg.delim.delimiter";
	public final static String DEFAULT_DELIMITER = ",";
	  
	/**
	 * @param messageSet
	 * @throws ParserException
	 * @throws ParserConfigurationException 
	 */
	public DelimiterParser(MessageSet messageSet) throws ParserException, ParserConfigurationException {
		super(messageSet);
	}
	 

	@Override
	public Message parse(byte[] inputMsg, StringBuffer parseLog) throws ParserException {
		long elapsed = System.currentTimeMillis();
		if(logger.isDebugEnabled() && parseLog != null){
			parseLog.append(Util.LINE_SEPARATOR);
			parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
			parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(String inputMsg) start").append(Util.LINE_SEPARATOR);
		}
		
		try{
			if(Util.isEmpty(inputMsg)) {
				throw new ParserException(ExceptionResource.getString("PAR0002", getClass().getName(), messageSet.getName()),"PAR0002");
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" parameter String inputMsg's length :[").append(inputMsg.length).append("]").append(Util.LINE_SEPARATOR);
			}
			Message msg = new Message(inputMsg);
			String name = messageSet.getName();
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" MessageSet's name :[").append(name).append("]").append(Util.LINE_SEPARATOR);
			}
	
			Element rootElement = new Element(name);
			//msg.addMsgElement(rootElement);
			MessageUtil.addMsgElement(msg, rootElement);
			
			LinkedHashMap<Object, FieldDefinition> fdm = messageSet.getFieldDefinitionMap();
			
			
			Collection<FieldDefinition> c = fdm.values();
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				try{
					parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg toString:[").append(Util.toString(inputMsg, messageSet.getCcsid())).append("]").append(Util.LINE_SEPARATOR);
				}catch(UnsupportedEncodingException e){}
				
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg hexdump:").append(Util.LINE_SEPARATOR);
				String dump = Util.hexdump(inputMsg);
				parseLog.append(dump).append("").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				parseLog.append("# start field parsing ").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
			}
			
			Properties properties = messageSet.getProperties();
			String delimeter = properties.getProperty(PK_DELIMITER, DEFAULT_DELIMITER);
			//spliting delimiter msg
			
			elapsed = System.currentTimeMillis();
			String ccsid = messageSet.getCcsid();
		
			//-------------------------------------------------------------------------------------
			//delimiter 메시지 파싱은 모든 필드를 문자로 인식하고 파싱하도록 한다.
			//문자셋은 메시지셋의 ccsid값을 기준으로 한다. 
			//-------------------------------------------------------------------------------------
			StringTokenizer values  = new StringTokenizer(new String(inputMsg, ccsid ), delimeter);
			logger.debug("StringTokenizer elapsed:" + (System.currentTimeMillis() - elapsed) + " ms");
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# delimeter: [").append(delimeter).append("]").append(Util.LINE_SEPARATOR);
				//parseLog.append(debugSerperator);
			}
			
			//구분자로 끊어 읽 메시지의 필드 순서가 메시지셋에 정의된 leaf 필드순서와 동일한 것으로 기준을 잡고 파싱한다.
			
			for (FieldDefinition fd : c) {
				//fd
				parseField(values, rootElement, fd, parseLog);
				
			}
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(byte[] inputMsg) result:success!").append(Util.LINE_SEPARATOR);
			}
			return msg;
		}catch(ParserException e){
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").
				append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) result:exception occured!").
				append(Util.LINE_SEPARATOR).append(e.getMessage()).append(Util.LINE_SEPARATOR);
			}
			throw e;
		 
		} catch (Exception e) {
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").
				append(FixedLengthParser.class.getName()).append(".parse(byte[] inputMsg) result:exception occured!").
				append(Util.LINE_SEPARATOR).append(e.getMessage()).append(Util.LINE_SEPARATOR);
			}
			throw new ParserException(e);
		}finally{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(byte[] inputMsg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
			
		}
	}
	
	
	private void parseField(StringTokenizer values, Element currentElement, FieldDefinition fd, StringBuffer parseLog) throws ParserException{
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			String fieldName = fsd.getName();
			
			 
			Element childElement = new Element(fieldName);
			currentElement.addChild(childElement);
			Collection<FieldDefinition> fc = fsd.getFieldDefinitionMap().values();
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(debugSerperator);
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(Util.leftPad("{fieldSet :" + fsd.getFieldPath(),50," "))
				.append("}\n")
				.append(debugSerperator);
			}
			
			for (FieldDefinition fieldDefinition : fc) {
					parseField(values, childElement, fieldDefinition, parseLog);
			}
			
			
		}else{
			
			
			
			if(logger.isDebugEnabled()) logger.debug(Util.join(fd.getName(), " is leaf node and reqired:", fd.isRequired()));
			
			DelimiterFieldDefinition dfd= (DelimiterFieldDefinition)fd;
			
			String fieldName = dfd.getName();
			int justify = dfd.getJustify();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(dfd.getPaddingValue());
			Object defaultValue = dfd.getDefaultValue();
			int type = dfd.getType();
			
			
			
			String fieldValue = values.hasMoreTokens() ? values.nextToken() : null;
			if(dfd.isRequired() && fieldValue == null) {
				ParserException pe = new ParserException("해당필드값필수입니다. NULL이면 아니되옵니다.");
				
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(dfd);
				throw pe;
			}
				
				 
			 
			int length = fieldValue.getBytes().length;
			
			
			Serializable value = null;
			try {
				
				//---------------------------------------------------
				//delimiter 데이터는 모두 문자열 기준으로 처리하되 바이너리 데이터의 경우
				//문자열을 Base64로 decode하여 바이너리데이터로 처리하도록 한다.(XML,JSON 파서와 동일기준)
				//---------------------------------------------------
				byte [] data = null;
				if(type == FieldDefinition.FIELD_TYPE_BINARY) {
					data =  Base64.decodeBase64(fieldValue);
				}else{
					data = fieldValue.getBytes(ccsid);
				}
				
				
				
				value = deserialize(data, type, padding, ccsid, justify, length, defaultValue, dfd);
				
				
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(dfd);
				throw pe;
			} catch (Exception e) {
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(dfd);
				throw pe;
			}
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(Util.leftPad("{field :" + dfd.getFieldPath(),50," "))
				.append(", value:")
				.append(Util.toString(value))
				.append("}").append(Util.LINE_SEPARATOR);
			}
			
			Element element = new Element(fieldName, value);
			currentElement.addChild(element);
			
			
		}
		
	}
 
	 
	
	private Serializable deserialize(byte[] data, int type, String padding, String ccsid, int justify, int length , Object defaultValue, FieldDefinition xfd) throws Exception {
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
						getClass().getName(), 
						messageSet.getName(),
						xfd.getFieldPath(),
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
						getClass().getName(),
						messageSet.getName(),
						xfd.getFieldPath(),
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
						getClass().getName(), 
						messageSet.getName(),
						xfd.getFieldPath(),
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
						getClass().getName(), 
						messageSet.getName(),
						xfd.getFieldPath(),
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
						getClass().getName(),
						messageSet.getName(),
						xfd.getFieldPath(),
						strValue,
						"Double"
				};
				String errmsg = ExceptionResource.getString("PAR0100", arguments);
				ParserException pe = new ParserException(errmsg, e,"PAR0100");
				throw pe;
			}			
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
					getClass().getName(), 
					messageSet.getName(),
					xfd.getFieldPath(),
					strValue,
					type
			};
			String errmsg = ExceptionResource.getString("PAR0101", arguments);
			ParserException pe = new ParserException(errmsg,"PAR0101");
			throw pe;
			
		}
		
		
		return result;
	}


	@Override
	public byte[] build(Message msg, StringBuffer parseLog) throws ParserException {
		
		
		long elapsed = System.currentTimeMillis();
		
		try{
		
		
			if(logger.isDebugEnabled()){
				parseLog = new StringBuffer("").append(Util.LINE_SEPARATOR);
				parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) start").append(Util.LINE_SEPARATOR);
			}
			
			
	
			
			Properties properties = messageSet.getProperties();
			String delimeter = properties.getProperty(PK_DELIMITER, DEFAULT_DELIMITER);
			//spliting delimiter msg
			
			
			Element root = msg.getMsgElement();
			String messageSetName = messageSet.getName();
			Element delimiterRoot = root.getChildAtFirst(messageSetName);
			
			StringBuffer msgBuffer = new StringBuffer(); 
			 
			Collection<FieldDefinition> col = messageSet.getFieldDefinitionMap().values();
			for (FieldDefinition fd : col) {
				String name = fd.getName();
				List<Element> list = delimiterRoot.getChildList(name);
				
				for (Element element : list) {
					buildField(msgBuffer, delimeter, msg, element, fd, parseLog);
				}
			}
			
			 
			String msgValue = msgBuffer.toString();
			String ccsid = messageSet.getCcsid();
			try {
				return msgValue.getBytes(ccsid);
			} catch (Exception e) {
				throw new ParserException("mint:exception",e);
			}
		}finally{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
		} 
	}

	private void buildField(StringBuffer msgBuffer, String delimeter, Message msg, Element currentFieldElement, FieldDefinition fd, StringBuffer debugBuff) throws ParserException{
		
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			//String tagName = fsd.getName();
			Collection<FieldDefinition> collection = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : collection) {
				String name = fieldDefinition.getName();
				List<Element> list = currentFieldElement.getChildList(name);
				for (Element element : list) {
					buildField(msgBuffer, delimeter, msg, element, fieldDefinition, debugBuff);
				}
			}
			 
		}else{
			DelimiterFieldDefinition xfd = (DelimiterFieldDefinition)fd;
			
			
			int justify = xfd.getJustify();
			String fieldName = xfd.getName();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(xfd.getPaddingValue());
			Object defaultValue = xfd.getDefaultValue();
			int type = xfd.getType();

			
			Object value = currentFieldElement.getValue();

			int length = Util.isEmpty(value) ? 0 : Util.toString(value).getBytes().length;
			
			try{
				
				
				String strValue = serialize(value, type, padding, ccsid, justify,length, defaultValue, xfd);
				if(!Util.isEmpty(strValue)) msgBuffer.append(strValue).append(delimeter);
				
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(xfd);
				throw pe;
			}catch(Exception e){
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(xfd);
				throw pe;
			}
		}
		 
		 
	}

	
	private String serialize(Object value, int type, String padding, String ccsid, int justify,int length, Object defaultValue, FieldDefinition fd) throws Exception {

		
		String result = null;
		switch(type){
		case FieldDefinition.FIELD_TYPE_BINARY :
			result =  Base64.encodeBase64String((byte[])value);
			break;
		case FieldDefinition.FIELD_TYPE_STR_SHORT :
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
		case FieldDefinition.FIELD_TYPE_STR_LONG :
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			result = Util.toString(value);
			break;
		case FieldDefinition.FIELD_TYPE_BOOLEAN :
			result = Util.toString(value);
			break;	
		case FieldDefinition.FIELD_TYPE_STRING :
			result = (String)value;
			break;
		default:
			String strValue = Util.toString(value);
			Object [] arguments = {
					FixedLengthParser.class.getName(), 
					messageSet.getName(),
					fd.getFieldPath(),
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
