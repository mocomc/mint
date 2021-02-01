/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-7105 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.CountryInfo;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceGuide;
import pep.per.mint.common.data.basic.InterfacePattern;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.ZoneInfo;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class InterfaceDataBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(InterfaceDataBuilder.class);
	
	@Test
	public void build(){
		
		Interface interfaceVO = new Interface();
		
		interfaceVO.setInterfaceId("PITSP0001");
		interfaceVO.setInterfaceNm("진행상태전송");
		
		Channel channel = new Channel();
		
		channel.setChannelId("sfgwefwe");
		channel.setChannelNm("SAP PO");
		channel.setExternalYn("N");
		channel.setComments("efwefweF");
		channel.setDelYn("N");
		channel.setRegDate("20150724130102");
		channel.setRegId("asdfwef");
		channel.setModDate("20150724130102");
		channel.setModId("Wefwef");
		
		
		interfaceVO.setChannel(channel);
		interfaceVO.setStatus("00");
		interfaceVO.setImportance("asefwef");
		interfaceVO.setMaxDelayTime("1");
		interfaceVO.setMaxErrorTime("1");
		interfaceVO.setMaxDelayUnit("fwewef");
		interfaceVO.setMaxErrorUnit("!2312");
		
		InterfacePattern pattern = new InterfacePattern();
		
		pattern.setPatternId("asdfasdf");
		pattern.setPatternNm("fwefwef");
		pattern.setPatternImg("wefwefwe");
		pattern.setDataPrDir("wefwefwE");
		pattern.setDataPrDirNm("단방향");
		pattern.setAppPrMethod("123123");
		pattern.setAppPrMethodNm("Asynchronous");
		pattern.setDataPrMethod("Batch");
		pattern.setDataPrMethodNm("Batch");
		pattern.setSndRcvRel("1:1");
		pattern.setComments("fwefwf");
		
		InterfaceGuide guide = new InterfaceGuide();
		guide.setGuideId("wefwef");
		guide.setSubject("wefwefw");
		guide.setContents("wefwefwef");
		guide.setPage("wefwef");
		guide.setDelYn("N");
		guide.setRegDate("20150724130102");
		guide.setRegId("wefwef");
		guide.setModDate("20150724130102");
		guide.setModId("wefwef");
		
		
		
		pattern.setGuide(guide);
		pattern.setDelYn("N");
		pattern.setRegDate("20150724130102");
		pattern.setRegId("swefgwer");
		pattern.setModDate("20150724130102");
		pattern.setModId("wefwef");
		
		interfaceVO.setRefPattern(pattern);
		interfaceVO.setDataFrequency("Wefwe");
		interfaceVO.setDataFrequencyNm("wefwef");
		interfaceVO.setSizePerTran(23);
		interfaceVO.setCntPerFrequency(11);
		interfaceVO.setCntPerDay(23);
		interfaceVO.setQttPerDay(23);
		interfaceVO.setStartResType("wefwef");
		interfaceVO.setStartResTypeNm("werwerf");
		interfaceVO.setEndResType("ergerge");
		interfaceVO.setEndResTypeNm("ergerger");
		interfaceVO.setStartNodeCnt(123);
		interfaceVO.setEndNodeCnt(456);
		interfaceVO.setDataSeqYn("N");
		interfaceVO.setDelYn("N");
		interfaceVO.setComments("Werfewrfg");
		interfaceVO.setRegDate("20150724130102");
		interfaceVO.setRegId("wefwef");
		interfaceVO.setModDate("20150724130102");
		interfaceVO.setModId("wsefwe");
		
		List<System> systemList = new ArrayList<System>();
		
		System system = new System();

		system.setSystemId("wefwef");
		system.setSystemNm("솔맨");
		system.setSystemCd("wefwef");
		system.setGrpYn("N");
		system.setExternalYn("N");
		
		ZoneInfo zoneInfo = new ZoneInfo();
		
		CountryInfo countryInfo = new CountryInfo();
		
		countryInfo.setCountryId("KOR");
		countryInfo.setCountryCd("KOR");
		countryInfo.setCountryNm("wfwef");
		countryInfo.setLangCd("CD");
		countryInfo.setDelYn("N");
		countryInfo.setRegDate("20150724130102");
		countryInfo.setRegId("ewfwef");
		countryInfo.setModDate("20150724130102");
		countryInfo.setModId("wefwe");
		
		
		
		zoneInfo.setCountryInfo(countryInfo);
		zoneInfo.setZoneId("wfefwef");
		zoneInfo.setZoneCd("wefw");
		zoneInfo.setZoneNm("wefwef");
		zoneInfo.setGeoCd("wefw");
		zoneInfo.setDelYn("N");
		zoneInfo.setRegDate("20150724130102");
		zoneInfo.setRegId("wefwef");
		zoneInfo.setModDate("20150724130102");
		zoneInfo.setModId("swefwef");
		
		
		
		system.setZoneInfo(zoneInfo);
		system.setAreaInput("wefwef");
		system.setComments("wefwef");
		system.setDelYn("N");
		system.setRegDate("20150724130102");
		system.setRegId("wefweF");
		system.setModDate("20150724130102");
		system.setModId("wefwerf");
		system.setSeq(1);
		system.setNodeType("0");
		system.setNodeTypeNm("wefweF");
		
		List<Server> serverList = new ArrayList<Server>();
		Server server = new Server();
		
		
		system.setServerList(null);
		system.setRelUsers(null);
		system.setService("Z_SORFC001");
		system.setResourceCd("wefwef");
		system.setResourceNm("SAP");
		
		
		System system1 = new System();

		system1.setSystemId("wefwef");
		system1.setSystemNm("ITMS");
		system1.setSystemCd("wefwef");
		system1.setGrpYn("N");
		system1.setExternalYn("N");
		
		
		
		system1.setZoneInfo(zoneInfo);
		system1.setAreaInput("wefwef");
		system1.setComments("wefwef");
		system1.setDelYn("N");
		system1.setRegDate("20150724130102");
		system1.setRegId("wefweF");
		system1.setModDate("20150724130102");
		system1.setModId("wefwerf");
		system1.setSeq(1);
		system1.setNodeType("2");
		system1.setNodeTypeNm("wefweF");
		system1.setServerList(null);
		system1.setRelUsers(null);
		system1.setService("URL");
		system1.setResourceCd("wefwef");
		system1.setResourceNm("WS");
		
		
		systemList.add(system);
		systemList.add(system1);
		
		
		List<Business> businessList = new ArrayList<Business>();
		Business business = new Business();
		
		business.setBusinessId("dfgerf");
		business.setBusinessNm("영업");
		business.setBusinessCd("wfewef");
		business.setGrpYn("N");
		business.setNodeType("erger");
		business.setNodeTypeNm("wefwef");
		business.setComments("fwefwE");
		business.setDelYn("N");
		business.setRegDate("20150724130102");
		business.setRegId("efwe");
		business.setModDate("20150724130102");
		business.setModId("wefwef");
		business.setRelUsers(null);
		
		businessList.add(business);
		
		Business business1 = new Business();
		business1.setBusinessId("dfgerf");
		business1.setBusinessNm("영업");
		business1.setBusinessCd("wfewef");
		business1.setGrpYn("N");
		business1.setNodeType("erger");
		business1.setNodeTypeNm("wefwef");
		business1.setComments("fwefwE");
		business1.setDelYn("N");
		business1.setRegDate("20150724130102");
		business1.setRegId("efwe");
		business1.setModDate("20150724130102");
		business1.setModId("wefwef");
		business1.setRelUsers(null);
		
		businessList.add(business1);
		
		interfaceVO.setSystemList(systemList);
		interfaceVO.setBusinessList(businessList); 
		interfaceVO.setRelUsers(null);
		
		
		
		//------------------------------------------
		//JSON DATA Building
		//------------------------------------------
		//String jsonString = Util.toJSONString(basicInfo);
		//logger.debug(Util.join("data:\n",jsonString));
		
		String fileName = "REST-R02-OP-01-01-001.json";
		String path = "./src/main/webapp/WEB-INF/test-data/op";
		DataBuilderUtil.saveToLocal(path,fileName, interfaceVO);
		
		logger.debug(Util.join("build success BasicInfoData:",path,"/",fileName));
		
		
	}
}