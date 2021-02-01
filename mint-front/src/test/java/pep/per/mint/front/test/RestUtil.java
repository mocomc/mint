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
package pep.per.mint.front.test;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.controller.an.RequirementControllerTest;

/**
 * @author Solution TF
 *
 */
public class RestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

	static RestTemplate rest = new RestTemplate();

	public static void main(String[] args){

		try{

			//byte[] response = rest.postForObject("http://dev.fiss.co.kr:9080/WebTest/iip_publish", "", byte[].class);
			//Map result = Util.jsonToObject(response, Map.class);
			//Map<String,String> response = rest.postForObject("http://dev.fiss.co.kr:9080/WebTest/iip_publish", "", Map.class);
			//System.out.println(Util.toJSONString(response));


				String a = "<script>alert('adfasdfasdfdasfadsf');</script>";
				logger.debug("error msg param:" + removeScriptTag(a));


		}catch(Exception e){
			e.printStackTrace();
		}

	}
	static String removeScriptTag(String errorMsg) {
		if(!Util.isEmpty(errorMsg)) {
			errorMsg = errorMsg.replaceAll("<script>","");
			errorMsg = errorMsg.replaceAll("</script>","");
			errorMsg = errorMsg.replaceAll("<javascript>","");
			errorMsg = errorMsg.replaceAll("</javascript>","");
		}
		return errorMsg;
	}
	/**
	 * <pre>
	 * REST 호출함수
	 * 요청 ComMessage 인수로 호출하면 응답을 돌려준다.
	 * 사용예)
	 * <code>
	 * 	//-------------------------------------------------
	 *	//request 세팅
	 *	//-------------------------------------------------
	 *	ComMessage request = new ComMessage();
	 *	request.setUserId("Solution TF");
	 *	request.setAppId("testGetRequirementList");
	 *
	 *	Map<String,Object> requestObject = new HashMap<String,Object>();
	 *	requestObject.put("requirementNm", "싱글");
	 *	request.setRequestObject(requestObject);
	 *
	 *	//-------------------------------------------------
	 *	//REST 호출
	 *	//-------------------------------------------------
	 *	ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/an/requirements", request, "GET", false);
	 * </code>
	 * </pre>
	 * @param url
	 * @param request
	 * @param method
	 * @param isTest
	 * @return
	 * @throws Exception
	 */
	public static ComMessage postForObject(String url, ComMessage request, String method, boolean isTest) throws Exception{
		return postForObject(url, null, request, method, isTest);
	}

	/**
	 * <pre>
	 * REST 호출함수
	 * 요청 ComMessage 인수로 호출하면 응답을 돌려준다.
	 * 사용예)
	 * <code>
	 * 	//-------------------------------------------------
	 *	//request 세팅
	 *	//-------------------------------------------------
	 *	ComMessage request = new ComMessage();
	 *	request.setUserId("Solution TF");
	 *	request.setAppId("testGetRequirementList");
	 *
	 *	Map<String,Object> requestObject = new HashMap<String,Object>();
	 *	requestObject.put("requirementNm", "싱글");
	 *	request.setRequestObject(requestObject);
	 *
	 *	//-------------------------------------------------
	 *	//REST 호출
	 *	//-------------------------------------------------
	 *	ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/an/requirements", request, "GET", false);
	 * </code>
	 * </pre>
	 * @param url
	 * @param params
	 * @param request
	 * @param method
	 * @param isTest
	 * @return
	 * @throws Exception
	 */
	public static ComMessage postForObject(String url, Map params, ComMessage request, String method, boolean isTest) throws Exception{

		Class responseType = ComMessage.class;
		//-------------------------------------------------
		//request 세팅
		//-------------------------------------------------
		// TODO  영향도 파악 필요 CacheableObject 에   ObjectType 주석처리
		//request.setObjectType("ComMessage");
		request.setStartTime(Util.getFormatedDate());
		url = Util.join(url,"?method=",method,"&isTest=",(isTest? "true" : "false"));
		logger.debug(Util.join("postForObject>url:", url));
		ComMessage response = (ComMessage) (params == null ? rest.postForObject(url, request, responseType) : rest.postForObject(url, request, responseType, params));

		return response;
	}
}
