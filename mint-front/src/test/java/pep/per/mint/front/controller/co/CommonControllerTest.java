/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.controller.co;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.LoginInfo;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.test.RestUtil;

/**
 * @author Solution TF
 *
 */
public class CommonControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonControllerTest.class);

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#login(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testLogin() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testLogin");
			 
			LoginInfo requestObject = new LoginInfo();
			requestObject.setId("iip");
			requestObject.setUserId("iip");
			requestObject.setPassword("iip");
			//requestObject.setPassword("********");
			request.setRequestObject(requestObject);
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/login", request, "POST", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testLogin(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestLogin() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testTestGetCommonCodeList");
			 
			LoginInfo requestObject = new LoginInfo();
			requestObject.setId("iip");
			requestObject.setUserId("iip");
			requestObject.setPassword("iip");
			request.setRequestObject(requestObject);
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/login", request, "POST", true);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getSystemList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetSystemList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetSystemList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("systemNm", "SAP");
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/systems", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetSystemList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetSystemList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetSystemList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("systemNm", "SAP");
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/systems", request, "GET", true);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getServerList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetServerList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetServerList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/servers", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetServerList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetServerList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getBusinessList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetBusinessList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetBusinessList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/businesses", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetBusinessList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetBusinessList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getUserList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetUserList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetUserList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("userNm", "김");
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/users", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetUserList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetUserList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfacePatternList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfacePatternList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testTestGetInterfacePatternList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("patternNm", "양");
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interface-patterns", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfacePatternList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfacePatternList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testTestGetInterfacePatternList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("patternNm", "양");
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interface-patterns", request, "GET", true);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfaceList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfaceList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetInterfaceList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			request.setRequestObject(requestObject);
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interfaces", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfaceList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfaceList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfaceDetail(javax.servlet.http.HttpSession, java.lang.String, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfaceDetail() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetInterfaceDetail");
			
			//-------------------------------------------------
			//URL PARAMS 세팅
			//-------------------------------------------------
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("interfaceId", "A0CM0001_SO"); 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interfaces/{interfaceId}", params, request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfaceDetail(javax.servlet.http.HttpSession, java.lang.String, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfaceDetail() {
		
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfaceDetailRequiremnets(java.lang.String, javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfaceDetailRequiremnets() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetInterfaceDetailRequiremnets");
			
			//-------------------------------------------------
			//URL PARAMS 세팅
			//-------------------------------------------------
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("interfaceId", "A0CM0001_SO"); 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interfaces/{interfaceId}/requirements", params, request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfaceDetailRequiremnets(java.lang.String, javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfaceDetailRequiremnets() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getSystemCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetSystemCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetSystemCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/systems", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetSystemCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetSystemCdList() {
		//fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getServerCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetServerCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetServerCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/servers", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetServerCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetServerCdList() {
		//fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getBusinessCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetBusinessCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetBusinessCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/businesses", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetBusinessCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetBusinessCdList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfaceCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfaceCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetInterfaceCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/interfaces", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfaceCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfaceCdList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getCommonCodeList(javax.servlet.http.HttpSession, java.lang.String, java.lang.String, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetCommonCodeList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetCommonCodeList");
			 
			//-------------------------------------------------
			//uil params 세팅 : {requirementId}
			//-------------------------------------------------
			Map params = new HashMap();
			params.put("level1", "IM");
			params.put("level2", "04");
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/common-codes/{level1}/{level2}", params, request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetCommonCodeList(javax.servlet.http.HttpSession, java.lang.String, java.lang.String, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetCommonCodeList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testTestGetCommonCodeList");
			 
			//-------------------------------------------------
			//uil params 세팅 : {requirementId}
			//-------------------------------------------------
			Map params = new HashMap();
			params.put("level1", "IM");
			params.put("level2", "04");
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/common-codes/{level1}/{level2}", params, request, "GET", true);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getChannelCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetChannelCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetChannelCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/channels", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetChannelCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetChannelCdList() {
		//fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getServiceList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetServiceList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetServiceList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/services", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetServiceList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetServiceList() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getInterfaceTagList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetInterfaceTagList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetInterfaceTagList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/interface-tags", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetInterfaceTagList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetInterfaceTagList() {
		//fail("Not yet implemented");
	}
	
	
	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#getRequirementCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetRequirementCdList() {
		try{
			
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testGetRequirementCdList");
			
	 
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/co/cds/requirements", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.co.CommonController#testGetRequirementCdList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testTestGetRequirementCdList() {
		//fail("Not yet implemented");
	}



}
