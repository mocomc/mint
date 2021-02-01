/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MsgMap;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.parser.FixedLengthParser;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MsgMapperTest {

	static Logger logger = LoggerFactory.getLogger(MsgMapperTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}


	
	static byte [] input = null;
	
	
	public static void buildSampleInputData() throws UnsupportedEncodingException{
		
		
		
		/**
		 * Sample Data 형식
		 * =========================================
		 * 헤더	head	
		 * -----------------------------------------
		 * 전문길이	 	msgLength	     8		N
		 * 인터페이스ID	interfaceId		30		S
		 * 글로벌ID	 	globalId	    30		S
		 * 전송시간	 	sendTime	    20		S
		 * 전송APP	 	senderId	    30		S
		 * -----------------------------------------
		 * 데이터 data
		 * -----------------------------------------
		 * 데이터길이	 	dataLength		 8		N
		 * 데이터		 	data		    -1	    S
		 * -----------------------------------------
		 * 종료		 	tail			 3		S	
		 */
		//----------------------------------------------------
		// head
		//----------------------------------------------------
		StringBuffer hBuffer = new StringBuffer();
		//hBuffer.append("00000000");//length[8]
		hBuffer.append(Util.rightPad("intf00001",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("gid000000000001",30," "));//gid[30]
		hBuffer.append(Util.rightPad(Util.getFormatedDate(),20," "));//senderTime[20]
		hBuffer.append(Util.rightPad("mint-admin",30," "));//senderId[20]
		//----------------------------------------------------
		// data
		//----------------------------------------------------
		StringBuffer dBuffer = new StringBuffer();
		dBuffer.append("00001000"); //dataLength[3]
		int len = Integer.parseInt(dBuffer.toString());
		for(int i = 0 ; i < (len/100) ; i++ ){
			dBuffer.append("한글567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
		}
		//----------------------------------------------------
		// tail
		//----------------------------------------------------
		String tailer = "[*]"; //tail[3]
		 
		StringBuffer totalBuffer = new StringBuffer();
		totalBuffer.append(hBuffer).append(dBuffer).append(tailer);
		int totlen = totalBuffer.toString().getBytes().length;
		
		String lenStr = Util.leftPad(totlen +"",8, "0");
		totalBuffer.insert(0, lenStr);
		
		input = totalBuffer.toString().getBytes("EUC-KR");
	}
	
	public static void buildSampleInputDataForMove() throws UnsupportedEncodingException{
		
		
		
		/**
		 * Sample Data 형식
		 * =========================================
		 * 헤더	head	
		 * -----------------------------------------
		 * 전문길이	 	msgLength	     8		N
		 * 인터페이스ID	interfaceId		30		S
		 * 인터페이스ID	interfaceId		30		S
		 * 인터페이스ID	interfaceId		30		S
		 * 인터페이스ID	interfaceId		30		S
		 * 인터페이스ID	interfaceId		30		S
		 * 글로벌ID	 	globalId	    30		S
		 * 전송시간	 	sendTime	    20		S
		 * 전송APP	 	senderId	    30		S
		 * -----------------------------------------
		 * 데이터 data
		 * -----------------------------------------
		 * 데이터길이	 	dataLength		 8		N
		 * 데이터		 	data		    -1	    S
		 * -----------------------------------------
		 * 종료		 	tail			 3		S	
		 */
		//----------------------------------------------------
		// head
		//----------------------------------------------------
		StringBuffer hBuffer = new StringBuffer();
		//hBuffer.append("00000000");//length[8]
		hBuffer.append(Util.rightPad("intf00001",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("intf00002",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("intf00003",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("intf00004",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("intf00005",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("gid000000000001",30," "));//gid[30]
		hBuffer.append(Util.rightPad(Util.getFormatedDate(),20," "));//senderTime[20]
		hBuffer.append(Util.rightPad("mint-admin",30," "));//senderId[20]
		//----------------------------------------------------
		// data
		//----------------------------------------------------
		StringBuffer dBuffer = new StringBuffer();
		dBuffer.append("00001000"); //dataLength[3]
		int len = Integer.parseInt(dBuffer.toString());
		for(int i = 0 ; i < (len/100) ; i++ ){
			dBuffer.append("한글567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
			dBuffer.append("1234567890");
		}
		//----------------------------------------------------
		// tail
		//----------------------------------------------------
		String tailer = "[*]"; //tail[3]
		 
		StringBuffer totalBuffer = new StringBuffer();
		totalBuffer.append(hBuffer).append(dBuffer).append(tailer);
		int totlen = totalBuffer.toString().getBytes().length;
		
		String lenStr = Util.leftPad(totlen +"",8, "0");
		totalBuffer.insert(0, lenStr);
		
		input = totalBuffer.toString().getBytes("EUC-KR");
	}
	
	 
	@Test
	/**
	 * 메시지셋 STDMSGV001 - STDMSGV002 간 매핑 테스트 샘플
	 */
	public void testMapMove() {
	
		MessageSet srcMsgSet = null;
		
		MessageSet tagMsgSet = null;
		
		MsgMap map = null;
		
		
		StringBuffer logBuffer = new StringBuffer();
		
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid1.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid2.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/msgmap-mapid1.json"), MsgMap.class, "UTF-8");
			logger.debug(Util.join("srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join("tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join("map:", Util.toJSONString(map)));

			buildSampleInputDataForMove();
			FixedLengthParser parser = new FixedLengthParser(srcMsgSet);
			srcMsg = parser.parse(input);
			logger.debug(Util.join("srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//mapControllerFacade.initialize();
			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join("target message:", Util.toJSONString(tagMsg)));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	@Test
	public void testMapFunctionOfTrim() {
	
		MessageSet srcMsgSet = null;
		
		MessageSet tagMsgSet = null;
		
		MsgMap map = null;
		
		
		StringBuffer logBuffer = new StringBuffer();
		
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid1.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid2.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/msgmap-mapid1-function-trim.json"), MsgMap.class, "UTF-8");
			logger.debug(Util.join("srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join("tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join("map:", Util.toJSONString(map)));

			buildSampleInputDataForMove();
			FixedLengthParser parser = new FixedLengthParser(srcMsgSet);
			srcMsg = parser.parse(input);
			logger.debug(Util.join("srcMsg:",Util.toJSONString(srcMsg)));

			 	
			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join("target message:", Util.toJSONString(tagMsg)));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	@Test
	public void testMapForeach() {
	
		MessageSet srcMsgSet = null;
		
		MessageSet tagMsgSet = null;
		
		MsgMap map = null;
		
		
		StringBuffer logBuffer = new StringBuffer();
		
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid1.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid2.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/msgmap-mapid1-foreach.json"), MsgMap.class, "UTF-8");
			logger.debug(Util.join("srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join("tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join("map:", Util.toJSONString(map)));

			buildSampleInputDataForMove();
			FixedLengthParser parser = new FixedLengthParser(srcMsgSet);
			srcMsg = parser.parse(input);
			logger.debug(Util.join("srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join("target message:", Util.toJSONString(tagMsg)));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	
	@Test
	public void testMapForeachField() {
	
		MessageSet srcMsgSet = null;
		
		MessageSet tagMsgSet = null;
		
		MsgMap map = null;
		
		
		StringBuffer logBuffer = new StringBuffer();
		
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid1.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid2.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/msgmap-mapid1-foreach-field.json"), MsgMap.class, "UTF-8");
			logger.debug(Util.join("srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join("tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join("map:", Util.toJSONString(map)));

			buildSampleInputDataForMove();
			FixedLengthParser parser = new FixedLengthParser(srcMsgSet);
			srcMsg = parser.parse(input);
			logger.debug(Util.join("srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join("target message:", Util.toJSONString(tagMsg)));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	@Test
	public void testMapForeachConcat() {
	
		MessageSet srcMsgSet = null;
		
		MessageSet tagMsgSet = null;
		
		MsgMap map = null;
		
		
		StringBuffer logBuffer = new StringBuffer();
		
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid1.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/msgset-mid2.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/msgmap-mapid1-foreach-concat.json"), MsgMap.class, "UTF-8");
			logger.debug(Util.join("srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join("tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join("map:", Util.toJSONString(map)));

			buildSampleInputDataForMove();
			FixedLengthParser parser = new FixedLengthParser(srcMsgSet);
			srcMsg = parser.parse(input);
			logger.debug(Util.join("srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join("target message:", Util.toJSONString(tagMsg)));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	
	@Test
	public void testMapForechMoveFunctionSampleCompanies() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-foreach-move-function-companies.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			logger.debug(logBuffer.toString());
			if(logBuffer != null){
			FileOutputStream los = new FileOutputStream("./data/sample/testMapForechMoveFunctionSampleCompanies.log");
			los.write(logBuffer.toString().getBytes());
			los.flush();
			los.close();
			}
			logBuffer = new StringBuffer();
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	@Test
	public void testMapSampleCompanies() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-companies.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(logBuffer.toString());
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}

	
	
	@Test
	public void testMapMoveSampleCompany() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-company.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			logger.debug(logBuffer.toString());
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			
			
			logBuffer = new StringBuffer();
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			/*FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();*/
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	@Test
	public void testMapMoveAndFunctionSampleCompany() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-function-company.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			/*FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();*/
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	@Test
	public void testMapMoveAndFunctionSetValueSampleCompany() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-company-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-function-setvalue-company.json"), MsgMap.class, "UTF-8");

			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			/*FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();*/
			
			logger.debug(Util.join(LogVariables.logPrefix, "mapping process:"));
			logger.debug(logBuffer.toString());
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	@Test
	public void testMapSampleCompaniesJoin() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies2-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-companies-join.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src-join.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			//logger.debug(logBuffer.toString());
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag-join.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	
	
	@Test
	public void testMapSampleCompaniesJoin2() {
	
		MessageSet srcMsgSet = null;
		MessageSet tagMsgSet = null;
		MsgMap map = null;
		StringBuffer logBuffer = new StringBuffer();
		Message srcMsg = null;
		Message tagMsg = null;
		try {
			
			srcMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies3-src.json"), MessageSet.class, "UTF-8");
			tagMsgSet = (MessageSet)Util.readObjectFromJson(new File("./data/sample/sample-msgset-companies-tag.json"), MessageSet.class, "UTF-8");
			map = (MsgMap)Util.readObjectFromJson(new File("./data/sample/sample-msgmap-companies-join.json"), MsgMap.class, "UTF-8");
			
			logger.debug(LogVariables.logSerperator2);
			logger.debug(Util.join(LogVariables.logPrefix,"msg mapping test - msgMap:["+ map.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:["+ srcMsgSet.getId(), "]"));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:["+ tagMsgSet.getId(), "]"));
			logger.debug(LogVariables.logSerperator1);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsgSet:", Util.toJSONString(srcMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"tagMsgSet:", Util.toJSONString(tagMsgSet)));
			logger.debug(Util.join(LogVariables.logPrefix,"map:", Util.toJSONString(map)));
			logger.debug(LogVariables.logSerperator1);
			
			byte[] b = buildSampleCompaniesMsg(new File("./data/sample/sample-msg-src-join2.fix"));
 
			FixedLengthParser srcParser = new FixedLengthParser(srcMsgSet);
			srcMsg = srcParser.parse(b);
			logger.debug(Util.join(LogVariables.logPrefix,"srcMsg:",Util.toJSONString(srcMsg)));

			//MapControllerFacade mapControllerFacade = MapControllerFacade.getInstance();
			//MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map, mapControllerFacade);
			MsgMapper mapper = new MsgMapper(srcMsgSet, tagMsgSet, map);
			tagMsg = mapper.map(srcMsg, logBuffer);
			
			logger.debug(logBuffer.toString());
			
			logger.debug(Util.join(LogVariables.logPrefix, "target message:", Util.toJSONString(tagMsg)));
			
			FixedLengthParser tagParser = new FixedLengthParser(tagMsgSet);
			byte tagMsgBytes[] = tagParser.build(tagMsg, logBuffer);
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msg-tag-join.fix");
			fos.write(tagMsgBytes);
			fos.flush();
			fos.close();
			logger.debug(Util.join(LogVariables.logPrefix, "target message text:", "\n", new String(tagMsgBytes,tagMsgSet.getCcsid())));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	
	}
	
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private byte[] buildSampleCompaniesMsg(File file) throws IOException  {
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[(int)file.length()];
		fis.read(b);
		fis.close();
		return b;
	}
	
	
	
	

}
