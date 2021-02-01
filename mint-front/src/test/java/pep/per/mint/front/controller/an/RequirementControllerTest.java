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
package pep.per.mint.front.controller.an;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.test.RestUtil;

/**
 * @author Solution TF
 *
 */
public class RequirementControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(RequirementControllerTest.class);

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.front.controller.an.RequirementController#getRequirementList(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetRequirementList() {
		
		try{
		
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetRequirementList");
			
			Map<String,Object> requestObject = new HashMap<String,Object>();
			requestObject.put("requirementNm", "싱글");
			request.setRequestObject(requestObject);
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/an/requirements", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}
	
	
	/**
	 * Test method for {@link pep.per.mint.front.controller.an.RequirementController#getRequirementDetail(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testGetRequirementDetail() {
		
		try{
		
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetRequirementDetail");
			
			//-------------------------------------------------
			//uil params 세팅 : {requirementId}
			//-------------------------------------------------
			Map params = new HashMap();
			params.put("requirementId", "REQ_PO_0001");
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/an/requirements/{requirementId}",params, request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}
	
	
	/**
	 * Test method for {@link pep.per.mint.front.controller.an.RequirementController#createRequirement(javax.servlet.http.HttpSession, pep.per.mint.common.data.basic.ComMessage, java.util.Locale)}.
	 */
	@Test
	public void testCreateRequirement() {
		
		try{
		
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("iip");
			request.setAppId("testCreateRequirement");
			
			Requirement requirement = (Requirement) Util.readObjectFromJson(new File("data/test/REST-C01-AN-01-00.json"), Requirement.class, "UTF-8");
			//request.setRequestObject(requirement);
			 
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/an/requirements", request, "POST", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}
	
}
