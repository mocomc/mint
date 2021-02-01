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
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.XMLFieldDefinition;
import pep.per.mint.common.exception.ParserException;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * 지정된 메시지셋에 의해 입력으로 받아들인 xml 메시지를 파싱한다.
 * <br>파서는 내부적으로 DOM(Document Object Model) 형식으로 메시지 파싱하는 javax.xml.parsers.DocumentBuilder를 이용한다.
 * </blockquote>
 * @author Solution TF
 * @since 2004
 * @see FixedLengthParser
 * @see DelimiterParser
 * @see JsonParser
 */
public class XmlDomParser extends Parser<byte[], Message> {

	public static Logger logger = LoggerFactory.getLogger(XmlDomParser.class); 
	final static String debugSerperator = "#-------------------------------------------------------------------------------------------------------------\n";
	DocumentBuilder builder;
	Transformer transformer;
	/**
	 * @param messageSet
	 * @throws ParserException
	 * @throws ParserConfigurationException 
	 * @throws TransformerConfigurationException 
	 */
	public XmlDomParser(MessageSet messageSet) throws ParserException, ParserConfigurationException, TransformerConfigurationException {
		super(messageSet);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		// TODO Auto-generated constructor stub
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformer = transformerFactory.newTransformer();
		String ccsid = messageSet.getCcsid();
		transformer.setOutputProperty(OutputKeys.ENCODING, Util.isEmpty(ccsid) ? "utf-8" : ccsid);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	}
	
	
	
	
	@Override
	public Message parse(byte[] inputMsg, StringBuffer parseLog) throws ParserException {
		long totoalElapsed = System.currentTimeMillis();
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
					parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg toString:[").append(Util.toString(inputMsg,messageSet.getCcsid())).append("]").append(Util.LINE_SEPARATOR);
				}catch(UnsupportedEncodingException e){}
				
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("]").append(" inputMsg hexdump:").append(Util.LINE_SEPARATOR);
				String dump = Util.hexdump(inputMsg, messageSet.getCcsid());
				parseLog.append(dump).append("").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				parseLog.append("# start field parsing ").append(Util.LINE_SEPARATOR);
				parseLog.append(debugSerperator);
				
			}
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] messageSet prepare elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
			}
			
			elapsed = System.currentTimeMillis(); 
			//xml dom parsing
			Document document = builder.parse(bais);
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] xml document parse elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
			}
			
			elapsed = System.currentTimeMillis(); 
			
			for (FieldDefinition fd : c) {
				//fd
				String tagName = fd.getName();
				
				NodeList nodeList = document.getDocumentElement().getElementsByTagName(tagName);
				
				logger.debug(Util.join("tagName:", tagName, ",nodeList length:", nodeList.getLength()));
				
				for(int i = 0 ; i < nodeList.getLength() ; i ++){
					Node node = nodeList.item(i);
					if(logger.isDebugEnabled()) logger.debug(Util.join("node[", i ,"]:",node.getNodeName(), " being parsed..."));
					parseField(node, rootElement, fd, parseLog);
				}
			}
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed time of constructing Message tree:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
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
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - totoalElapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".parse(byte[] inputMsg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
			
		}
	}
	
	private void parseField(Node currentNode, Element currentElement, FieldDefinition fd, StringBuffer parseLog) throws ParserException{
		
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
				NodeList nodeList = ((org.w3c.dom.Element)currentNode).getElementsByTagName(tagName);
				for(int i = 0 ; i < nodeList.getLength() ; i ++){
					Node node = nodeList.item(i);
					parseField(node, childElement, fieldDefinition, parseLog);
				}
			
				
			}
			
			
		}else{
			
			
			
			if(logger.isDebugEnabled()) logger.debug(Util.join(currentNode.getNodeName(), " is leaf node"));
			
			XMLFieldDefinition xfd= (XMLFieldDefinition)fd;
			
			String fieldName = xfd.getName();
			int justify = xfd.getJustify();
			String ccsid = messageSet.getCcsid();
			String padding = Util.toString(xfd.getPaddingValue());
			Object defaultValue = xfd.getDefaultValue();
			int type = xfd.getType();
			
			
			String nodeValue = currentNode.getTextContent();
			short nodeType = currentNode.getNodeType();
			int childNodeLength = currentNode.getChildNodes().getLength();
			int length = nodeValue.getBytes().length;
			//String nodeValue = currentNode.getChildNodes().item(0).getNodeValue();
			//short nodeType = currentNode.getChildNodes().item(0).getNodeType();
			if(logger.isDebugEnabled()) {
				logger.debug(Util.join("node[", currentNode.getNodeName(), "]'s value is " , nodeValue)); 
				logger.debug(Util.join("node[", currentNode.getNodeName(), "]'s type is " , nodeType)); 
				logger.debug(Util.join("node[", currentNode.getNodeName(), "]'s child length is " , childNodeLength)); 
			}
			
			
			
			Serializable value = null;
			try {
				 
				
				//---------------------------------------------------
				//xml 데이터는 모두 문자열 기준으로 처리하되 바이너리 데이터의 경우
				//문자열을 Base64로 decode하여 바이너리데이터로 처리하도록 한다.
				//---------------------------------------------------
				byte [] data = null;
				if(type == FieldDefinition.FIELD_TYPE_BINARY) {
					data =  Base64.decodeBase64(currentNode.getTextContent());
				}else{
					data = currentNode.getTextContent().getBytes(ccsid);
				}
				
				value = deserialize(data, type, padding, ccsid, justify, length, defaultValue, xfd);
				
			} catch (ParserException pe){
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(xfd);
				throw pe;
			} catch (Exception e) {
				ParserException pe = new ParserException(e);
				pe.setMessageSet(messageSet);
				pe.setFieldDefinition(xfd);
				throw pe;
			}
			
			
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(Util.leftPad("{field :" + xfd.getFieldPath(),50," "))
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
		//StringBuffer xmlContents = new StringBuffer();
		
		long elapsed = System.currentTimeMillis();
		try{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog = new StringBuffer("").append(Util.LINE_SEPARATOR);
				parseLog.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) start").append(Util.LINE_SEPARATOR);
			}
			
			
	
			
			Element root = msg.getMsgElement();
			String messageSetName = messageSet.getName();
			Element xml = root.getChildAtFirst(messageSetName);
			
			 
			
			Document xmlDoc = builder.newDocument();
			Node rootNode = xmlDoc.createElement(messageSetName);
			xmlDoc.appendChild(rootNode);
			
			/*if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
			}*/
			
			Collection<FieldDefinition> col = messageSet.getFieldDefinitionMap().values();
			for (FieldDefinition fd : col) {
				String name = fd.getName();
				List<Element> list = xml.getChildList(name);
				
				for (Element element : list) {
					buildField(xmlDoc, rootNode, msg, element, fd, parseLog);
				}
			}
			
			//xmlDoc.
			try {
	 
				DOMSource domSource = new DOMSource(xmlDoc);
	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				StreamResult outputTarget = new StreamResult(output);
				transformer.transform(domSource, outputTarget);
	
				return output.toByteArray();
				
			} catch (Exception e) {
				throw new ParserException("mint:exception",e);
			}
		}finally{
			if(logger.isDebugEnabled() && parseLog != null){
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] elapsed:").append((System.currentTimeMillis() - elapsed)).append("(ms)").append(Util.LINE_SEPARATOR);
				parseLog.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(getClass().getName()).append(".build(Message msg) end").append(Util.LINE_SEPARATOR);
				//parseLog.append("# ").append(getClass().getName()).append(".build(Message msg) end").append(Util.LINE_SEPARATOR);
				logger.debug(parseLog.toString());
			}
		}
	}

	private void buildField(Document doc, Node currentNode, Message msg, Element currentFieldElement, FieldDefinition fd, StringBuffer debugBuff) throws ParserException{
		
		
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			 	
			String tagName = fsd.getName();
			Node node = doc.createElement(tagName);
			currentNode.appendChild(node);
			
			
			Collection<FieldDefinition> collection = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : collection) {
				String name = fieldDefinition.getName();
				List<Element> list = currentFieldElement.getChildList(name);
				for (Element element : list) {
					buildField(doc, node, msg, element, fieldDefinition, debugBuff);
				}
			}
			 
		}else{
			XMLFieldDefinition xfd = (XMLFieldDefinition)fd;
			
			
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
				 
				Node node = doc.createElement(fieldName);
				currentNode.appendChild(node);
				//node.setTextContent(new String(b,ccsid));
				//node.appendChild(doc.createCDATASection(new String(b,ccsid)));
				node.appendChild(doc.createTextNode(strValue));
				
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
