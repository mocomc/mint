/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.parser;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.exception.ParserException;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * 파서의 최상위 추상 클래스 
 * </blockquote>
 * @author Solution TF
 * @see FixedLengthParser
 * @see XmlDomParser
 * @see DelimiterParser
 * @see JsonParser
 * @param <T> 파싱할 입력 메시지 타입 (예:byte[])
 * @param <W> 파싱결과값을 담아낼 출력 메시지 객체 타입(예:{@link pep.per.mint.common.message.Message} )
 */
abstract public class Parser<T,W> {
	
	final static Logger logger = LoggerFactory.getLogger(Parser.class); 
	
	/**
	 * <blockquote>
	 * 입력으로 받은 메시지를 파서에 지정된 메시지셋의 정의에 의해 필드값을 파싱하여 출력 메시지의 논리적인 구조에 담아 리턴한다. 
	 * </blockquote>
	 * @param bytesMsg 입력 메시지(파싱대상 메시지 소스) 
	 * @return
	 * @throws ParserException
	 */
	//abstract public W parse(T bytesMsg) throws ParserException;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력으로 받은 메시지를 파서에 지정된 메시지셋의 정의에 의해 필드값을 파싱하여 출력 메시지의 논리적인 구조에 담아 리턴한다. 
	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 	
	 * 	여기다 sample code 작성해라.
	 * 	
	 * 	<li>[fixed]
	 * 
	 * 	<li>[xml]
	 * 
	 * 	<li>[delimiter]
	 * 
	 * 	<li>[json]
	 * 
	 * </div>
	 * 
	 * 
	 * </pre>
	 * </blockquote>
	 * @param bytesMsg 입력 메시지(파싱대상 메시지 소스)
	 * @param parseLog 파싱과정을 로깅하여 남길 버퍼 객체 
	 * @return
	 * @throws ParserException
	 */
	abstract public W parse(T bytesMsg, StringBuffer parseLog) throws ParserException;
	
	/**
	 * <blockquote>
	 * 입력으로 받은 메시지를 파서에 지정된 메시지셋의 정의에 의해 리턴타입의 메시지포멧으로 조립하여 결과를 반환한다. 
	 * </blockquote>
	 * @param msg 메시지셋의 논리구조대로 필드값을 저장하고 있는 메시지 객체 
	 * @return 최종적으로 빌딩하여 내보낼 메시지 데이터 타입 
	 * @throws ParserException
	 */
	//abstract public T build(W msg) throws ParserException;
	
	/**
	 * <blockquote>
	 * 입력으로 받은 메시지를 파서에 지정된 메시지셋의 정의에 의해 리턴타입의 메시지포멧으로 조립하여 결과를 반환한다. 
	 * </blockquote>
	 * @param msg 메시지셋의 논리구조대로 필드값을 저장하고 있는 메시지 객체 
	 * @param parseLog 파싱과정을 로깅하여 남길 버퍼 객체 
	 * @return 최종적으로 빌딩하여 내보낼 메시지 데이터 타입 
	 * @throws ParserException
	 */
	abstract public T build(W msg, StringBuffer parseLog) throws ParserException;
	
	MessageSet messageSet;
	
	TypeHandler<Short> shortTypeHandler;
	TypeHandler<Integer> integerTypeHandler;
	TypeHandler<Long> longTypeHandler;
	TypeHandler<Float> floatTypeHandler;
	TypeHandler<Double> doubleTypeHandler;
	TypeHandler<Boolean> booleanTypeHandler;
	TypeHandler<String> stringTypeHandler;
	
	public Parser(MessageSet messageSet) throws ParserException{
		
		StringBuffer debugBuff = null;
		if(logger.isDebugEnabled()){
			debugBuff = new StringBuffer("").append(Util.LINE_SEPARATOR);
			debugBuff.append("#=========================================================================================").append(Util.LINE_SEPARATOR);
			debugBuff.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(this.getClass().getName()).append(" constructor start.").append(Util.LINE_SEPARATOR);
		}
		try{
			if(Util.isEmpty(messageSet)){
				throw new ParserException(ExceptionResource.getString("PAR0001", Parser.class.getName()), "PAR0001");
			}
			
			LinkedHashMap<Object, FieldDefinition> fdm = messageSet.getFieldDefinitionMap();
			if(Util.isEmpty(fdm)){
				throw new ParserException(ExceptionResource.getString("PAR0002", FixedLengthParser.class.getName(), messageSet.getName()), "PAR0002");
			}
			
			
			this.messageSet = messageSet;
			
			initializeTypeHandler();
			
			//initializePath(logger.isDebugEnabled() ? debugBuff : null);
			//PathInitializer.initializePath(messageSet, this, debugBuff);
			
		}finally{
			if(logger.isDebugEnabled()){
				debugBuff.append("# [").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ").append(this.getClass().getName()).append(" constructor end.").append(Util.LINE_SEPARATOR);
				logger.debug(debugBuff.toString());
			}
		}
	}

	private void initializeTypeHandler() {
		// TODO Auto-generated method stub
		shortTypeHandler = new ShortTypeHandler();
		integerTypeHandler = new IntegerTypeHandler();
		longTypeHandler = new LongTypeHandler();
		floatTypeHandler = new FloatTypeHandler();
		doubleTypeHandler = new DoubleTypeHandler();
		stringTypeHandler = new StringTypeHandler();
		booleanTypeHandler = new BooleanTypeHandler();
		
	}
 
	/**
	 * <blockquote>
	 * msgSet에 지정된 ccsid 코드로 구성된 바이너리 입력 데이터를 받아 msgSet의 필드 정의에 따라 
	 * <br>메시지를 파싱하고 Message 인스턴스의 논리 스트럭쳐에 담아 리턴한다.
	 * </blockquote>
	 * @param inputMsg msgSet에 지정된 ccsid 코드로 구성된 바이너리 입력 데이터
	 *                 <br> ccsid문자코드와 틀린 바이너리 배열 메시지가 입력으로 
	 *                 <br> 들어올 경우 예외를 발생시킨다.
	 *  
	 * @return 파싱한 필드값들을 메시지셋에서 정의한 논리구조대로 저장하여 Message 타입으로 리턴한다.
	 * 
	 */
	public W parse(T inputMsg) throws ParserException {
		
		StringBuffer parseLog = null;
		if(logger.isDebugEnabled()) parseLog = new StringBuffer("");
		return parse(inputMsg, parseLog);
	}

	/**
	 * <blockquote>
	 * 입력으로 받은 메시지를 파서에 지정된 메시지셋의 정의에 의해 리턴타입의 메시지포멧으로 조립하여 결과를 반환한다. 
	 * </blockquote>
	 * @param msg 메시지셋의 논리구조대로 필드값을 저장하고 있는 메시지 객체 
	 * @return 최종적으로 빌딩하여 내보낼 메시지 데이터 타입 
	 * @throws ParserException
	 */	
	public T build(W msg) throws ParserException {
		StringBuffer parseLog = null;
		if(logger.isDebugEnabled()) parseLog = new StringBuffer("");
		return build(msg, parseLog);
	}

	
	
	/**
	 * @return the shortTypeHandler
	 */
	public TypeHandler<Short> getShortTypeHandler() {
		return shortTypeHandler;
	}

	/**
	 * @param shortTypeHandler the shortTypeHandler to set
	 */
	public void setShortTypeHandler(TypeHandler<Short> shortTypeHandler) {
		this.shortTypeHandler = shortTypeHandler;
	}

	/**
	 * @return the integerTypeHandler
	 */
	public TypeHandler<Integer> getIntegerTypeHandler() {
		return integerTypeHandler;
	}

	/**
	 * @param integerTypeHandler the integerTypeHandler to set
	 */
	public void setIntegerTypeHandler(TypeHandler<Integer> integerTypeHandler) {
		this.integerTypeHandler = integerTypeHandler;
	}

	/**
	 * @return the longTypeHandler
	 */
	public TypeHandler<Long> getLongTypeHandler() {
		return longTypeHandler;
	}

	/**
	 * @param longTypeHandler the longTypeHandler to set
	 */
	public void setLongTypeHandler(TypeHandler<Long> longTypeHandler) {
		this.longTypeHandler = longTypeHandler;
	}

	/**
	 * @return the floatTypeHandler
	 */
	public TypeHandler<Float> getFloatTypeHandler() {
		return floatTypeHandler;
	}

	/**
	 * @param floatTypeHandler the floatTypeHandler to set
	 */
	public void setFloatTypeHandler(TypeHandler<Float> floatTypeHandler) {
		this.floatTypeHandler = floatTypeHandler;
	}

	/**
	 * @return the doubleTypeHandler
	 */
	public TypeHandler<Double> getDoubleTypeHandler() {
		return doubleTypeHandler;
	}

	/**
	 * @param doubleTypeHandler the doubleTypeHandler to set
	 */
	public void setDoubleTypeHandler(TypeHandler<Double> doubleTypeHandler) {
		this.doubleTypeHandler = doubleTypeHandler;
	}

	/**
	 * @return the booleanTypeHandler
	 */
	public TypeHandler<Boolean> getBooleanTypeHandler() {
		return booleanTypeHandler;
	}

	/**
	 * @param booleanTypeHandler the booleanTypeHandler to set
	 */
	public void setBooleanTypeHandler(TypeHandler<Boolean> booleanTypeHandler) {
		this.booleanTypeHandler = booleanTypeHandler;
	}

	/**
	 * @return the stringTypeHandler
	 */
	public TypeHandler<String> getStringTypeHandler() {
		return stringTypeHandler;
	}

	/**
	 * @param stringTypeHandler the stringTypeHandler to set
	 */
	public void setStringTypeHandler(TypeHandler<String> stringTypeHandler) {
		this.stringTypeHandler = stringTypeHandler;
	}
	
	//Message 클래스 디자인 필요
	
	

}
