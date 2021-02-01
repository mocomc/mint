/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.parser;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
//import org.codehaus.jackson.JsonFactory;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.io.JsonStringEncoder;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser.Feature;
//import com.fasterxml.jackson.core.JsonGenerator.Feature;
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.JsonFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.exception.ParserException;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * 지정된 메시지셋에 의해 입력으로 받아들인 json 메시지를 파싱한다.
 * </blockquote>
 * @author Solution TF
 * @since 2004
 * @see FixedLengthParser
 * @see XmlDomParser
 * @see DelimiterParser
 */
public class JsonParser extends Parser<byte[], Message> {

	public static Logger logger = LoggerFactory.getLogger(JsonParser.class); 
	final static String debugSerperator = "#-------------------------------------------------------------------------------------------------------------\n";
	ObjectMapper objectMapper;
	JsonStringEncoder encoder = JsonStringEncoder.getInstance();
	JsonFactory jsonFactory;
	/**
	 * @param messageSet
	 * @throws ParserException
	 * @throws ParserConfigurationException 
	 */
	public JsonParser(MessageSet messageSet) throws ParserException, ParserConfigurationException {
		super(messageSet);
		objectMapper = new ObjectMapper();
		
//		objectMapper.configure(Feature.INDENT_OUTPUT, true);
//		objectMapper.configure(Feature.SORT_PROPERTIES_ALPHABETICALLY, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//		objectMapper.configure(SerializationFeature.SORT_PROPERTIES_ALPHABETICALLY, false);

		
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        objectMapper.setDateFormat(outputFormat);
		jsonFactory = new JsonFactory();
	}
	
	
	@Override
	public Message parse(byte[] inputMsg, StringBuffer parseLog) throws ParserException {
		long elapsed = System.currentTimeMillis();
		if(logger.isDebugEnabled() && parseLog != null){
			parseLog.append(Util.LINE_SEPARATOR);
			parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
			parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(byte[] inputMsg) start").append(Util.LINE_SEPARATOR);
		}
		
		ByteArrayInputStream bais = null;
		try{
			if(Util.isEmpty(inputMsg)) {
				throw new ParserException(ExceptionResource.getString("PAR0002", getClass().getName(), messageSet.getName()),"PAR0002");
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" parameter byte[] inputMsg's length :[").append(inputMsg.length).append("]").append(Util.LINE_SEPARATOR);
			}
			bais = new ByteArrayInputStream(inputMsg);
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
				String dump = Util.hexdump(inputMsg, messageSet.getCcsid());
				parseLog.append(dump).append("").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				parseLog.append("# start field parsing ").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				
			}
			
			//UTF8 인코딩으로 메시지 변환, 이유는 JacksonJson 라이브러리가 UTF-8 코드셋을 기준으로 동작한다.
			inputMsg = encoder.encodeAsUTF8(new String(inputMsg,messageSet.getCcsid()));
			//inputMsg = new String(inputMsg,messageSet.getCcsid()).getBytes("UTF-8");

			JsonNode rootNode = objectMapper.readTree(inputMsg);
			
			for (FieldDefinition fd : c) {
				//fd
				String tagName = fd.getName();
				List<JsonNode> list = rootNode.findValues(tagName);
				if(list != null){
					logger.debug(Util.join("tagName:", tagName, ",nodeList length:", list.size()));
					
					for (JsonNode node : list) {
						parseField(node, rootElement, fd, parseLog);
					}
					
					 
				}
			}
			
			
			
			parseLog.append(debugSerperator);
			parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(byte[] inputMsg) result:success!").append(Util.LINE_SEPARATOR);
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
	
	 
	
	private void parseField(JsonNode currentNode, Element currentElement, FieldDefinition fd, StringBuffer parseLog) throws ParserException{
		
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
				
				
				
				String tagName = fieldDefinition.getName();
				List<JsonNode> list = currentNode.findValues(tagName);
				for (JsonNode node : list) {
					parseField(node, childElement, fieldDefinition, parseLog);
				}
				 
			}
			
			
		}else{
			
			
			
			if(logger.isDebugEnabled()) logger.debug(Util.join(fd.getName(), " is leaf node"));
			
			JsonFieldDefinition jfd= (JsonFieldDefinition)fd;
			
			String fieldName = jfd.getName();
			int justify = jfd.getJustify();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(jfd.getPaddingValue());
			Object defaultValue = jfd.getDefaultValue();
			int type = jfd.getType();
			int length = 0;
			
			 			
			Serializable value = null;
			try {
				
				value = deserialize(currentNode, type, padding, ccsid, justify, length, defaultValue, jfd);
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(jfd);
				throw pe;
			} catch (Exception e) {
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(jfd);
				throw pe;
			}
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(Util.leftPad("{field :" + jfd.getFieldPath(),50," "))
				.append(", value:")
				.append(Util.toString(value))
				.append("}").append(Util.LINE_SEPARATOR);
			}
			
			Element element = new Element(fieldName, value);
			currentElement.addChild(element);
			
			
		}
		
	}
	
	
	private Serializable deserialize(JsonNode node, int type, String padding, String ccsid, int justify, int length , Object defaultValue, FieldDefinition xfd) throws Exception {
		Serializable result = null;
		String strValue = null; 
		switch(type){
		case FieldDefinition.FIELD_TYPE_BINARY :
			if(node.isBinary()) throw new Exception("mint:exception:필드 데이터가 바이너리 데이터가 아님.");
			result = node.binaryValue(); //  node.getBinaryValue();
			break;
		case FieldDefinition.FIELD_TYPE_STR_SHORT :
			result = node.asInt();
			break;			
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			result = node.asInt();
			break;
		case FieldDefinition.FIELD_TYPE_STR_LONG :
			result = node.asLong();
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			result = node.asDouble();
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			result = node.asDouble();
			break;
		case FieldDefinition.FIELD_TYPE_BOOLEAN :
			if(node.isBoolean()) throw new Exception("mint:exception:필드 데이터가 boolean 타입이 아님.");
			result = node.booleanValue();   // getBooleanValue();
			break;
		case FieldDefinition.FIELD_TYPE_STRING :
			//jackson json library가 utf8기준이므로 
			//해당 메시지셋 ccsid값으로 변환하여 저장이 필요함.
			if("UTF-8".equalsIgnoreCase(ccsid)){//UTF-8일경우 변환 불필요 
				result = node.textValue();//  getTextValue();
			}else{
//				result = new String(node.getTextValue().getBytes(ccsid),ccsid);
				result = new String(node.textValue().getBytes(ccsid),ccsid);
			}
			break;
		default:
//			byte[] b = node.getTextValue().getBytes(ccsid);
			byte[] b = node.textValue().getBytes(ccsid);
			strValue = new String(b,ccsid);
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
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append(Util.LINE_SEPARATOR);
				parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) start").append(Util.LINE_SEPARATOR);
			}
			
			
	
			
			Element root = msg.getMsgElement();
			String messageSetName = messageSet.getName();
			Element json = root.getChildAtFirst(messageSetName);
			
			
			Map<String,Object> jsonRoot = new HashMap<String,Object>();
			
			Collection<FieldDefinition> col = messageSet.getFieldDefinitionMap().values();
			for (FieldDefinition fd : col) {
				String name = fd.getName();
				List<Element> list = json.getChildList(name);
				
				Object node = null;
				if(list == null || list.size() == 0){
					continue;
				}else if(list.size() == 1){
					node = jsonRoot;
				}else{
					node = new ArrayList();
					jsonRoot.put(name, node);
				}
						
				for (Element element : list) {
					buildField(node, element, fd, parseLog);
				}
			}
			//--------------------------------------------------------
			//generate json text.
			//--------------------------------------------------------
			try{
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				String ccsid = messageSet.getCcsid();
				JsonGenerator generator = jsonFactory.createJsonGenerator(out);
				objectMapper.writeValue(generator, jsonRoot);
				
				return out.toString().getBytes(ccsid);
				//return out.toByteArray();
				
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
	
	private void buildField(Object currentNode, Element currentFieldElement, FieldDefinition fd, StringBuffer debugBuff) throws ParserException{
		
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			 	
			String tagName = fsd.getName();
			Map<String,Object> node = new HashMap<String,Object>();
			
			if(currentNode instanceof List){
				((List)currentNode).add(node);
			}else{
				((Map<String,Object>)currentNode).put(tagName,node);
			}
			
			Collection<FieldDefinition> collection = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : collection) {
				String name = fieldDefinition.getName();
				List<Element> list = currentFieldElement.getChildList(name);
				
				
				
				Object processNode = null;
				if(list == null || list.size() == 0){
					continue;
				}else if(list.size() == 1){
					processNode = node;
				}else{
					processNode = new ArrayList();
					node.put(name, processNode);
				}
				
				for (Element element : list) {
					buildField(processNode, element, fieldDefinition, debugBuff);
				}
			}
			 
		}else{
			JsonFieldDefinition jfd = (JsonFieldDefinition)fd;
			
			
			int justify = jfd.getJustify();
			String fieldName = jfd.getName();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(jfd.getPaddingValue());
			Object defaultValue = jfd.getDefaultValue();
			int type = jfd.getType();

			
			Object value = currentFieldElement.getValue();

			int length = Util.isEmpty(value) ? 0 : Util.toString(value).getBytes().length;
			
			try{
				
				
				String strValue = serialize(value, type, padding, ccsid, justify,length, defaultValue, jfd);
				//currentNode.put(fieldName, strValue);
				 
				if(currentNode instanceof List){
					Map<String,Object> node = new HashMap<String,Object>();
					((List)currentNode).add(strValue);
				}else{
					((Map<String,Object>)currentNode).put(fieldName,strValue);
				}
				
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(jfd);
				throw pe;
			}catch(Exception e){
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(jfd);
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
