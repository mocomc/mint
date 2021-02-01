/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.controller.deploy;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.dataset.DataSet;

import pep.per.mint.common.data.basic.runtime.ModelDeployment;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.su.BridgeProviderService;
import pep.per.mint.endpoint.service.deploy.ModelDeployService;

/**
 * @author whoana
 * @since Sep 14, 2020
 */
@RequestMapping("/rt")
@Controller
public class ModelDeployController {

	private static final Logger logger = LoggerFactory.getLogger(ModelDeployController.class);

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	private ServletContext servletContext;

	@Autowired
	ModelDeployService modelDeployService;

	@RequestMapping(value = "/models/deploys/xmls", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>,String> getDeployXml(
		HttpSession httpSession,
		@RequestBody ComMessage<Map<String,Object>, String> comMessage,
		Locale locale, HttpServletRequest request) throws Exception {

		String errorCd = "";
		String errorMsg = "";
		String xml = null;
		Map params = comMessage.getRequestObject();
		if(params == null || params.get("interfaceMid") == null) {
			String [] errorMsgParams = {this.getClass().toString(), "interfaceMid"};
			errorMsg = messageSource.getMessage("error.msg.request.required.parame", errorMsgParams, locale);
			throw new Exception(errorMsg);
		}
		//--------------------------------------------------
		// 조회 실행
		//--------------------------------------------------
		{
			String interfaceMid = (String)params.get("interfaceMid");
			xml = modelDeployService.getDeployModelXml(interfaceMid, comMessage.getUserId());
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		}
		//--------------------------------------------------
		// 통신메시지에 처리결과 코드/메시지를 등록한다.
		//--------------------------------------------------
		{
			if (xml == null ) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = messageSource.getMessage("error.cd.retrieve.none", null, locale);
				errorMsg = messageSource.getMessage("error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.retrieve.list.ok", null, locale);
				comMessage.setResponseObject(xml);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
		}
		return comMessage;
	}


	/**
	 * <pre>
	 * mint-bridge-apps 애플리케이션을 통해 모델을 배포한다.
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "/models/deploys/actions", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<ModelDeployment, ModelDeployment> deploy(
		HttpSession httpSession,
		@RequestBody ComMessage<ModelDeployment, ModelDeployment> comMessage,
		Locale locale, HttpServletRequest request) throws Exception {

		String errorCd = "";
		String errorMsg = "";

		ModelDeployment md = comMessage.getRequestObject();
		if(md == null) {
			String [] errorMsgParams = {this.getClass().toString(), "ModelDeployment"};
			errorMsg = messageSource.getMessage("error.msg.request.required.parame", errorMsgParams, locale);
			throw new Exception(errorMsg);
		}


		try {
			ComMessage response = modelDeployService.deploy(comMessage);
			response.setRequestObject(null);
			return response;
		}catch(org.springframework.web.client.ResourceAccessException  e) {

			errorCd = messageSource.getMessage("error.cd.system.bridge.connect.fail", null, locale);
			errorMsg = messageSource.getMessage("error.msg.system.bridge.connect.fail", null, locale);
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			logger.error("fail to connect to mint-bridge-apps",e);
			return comMessage;

		}catch(Exception e) {
			throw e;
		}
	}

	@Autowired
	BridgeProviderService bridgeProviderService;
	/**
	 * <pre>
	 * mint-bridge-apps 애플리케이션이 요청하는 service-cds를 조회한다.
	 * 사용처 : 신한생명
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "/models/deploys/bridge/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map<String,String>>> getInterfaces(
		HttpSession httpSession,
		@RequestBody ComMessage<Map<String,String>, List<Map<String,String>>> comMessage,
		Locale locale, HttpServletRequest request) throws Exception {

		String errorCd = "";
		String errorMsg = "";
		List<Map<String,String>> list = null;
		Map params = comMessage.getRequestObject();
		{
			if(params == null || params.get("businessCd") == null) throw new Exception("have no required filed value:businessCd");
		}
		//--------------------------------------------------
		// 모델 contents 조회 및 배포 실행
		//--------------------------------------------------
		{
			list = bridgeProviderService.getInterfaces(params);

		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		}
		//--------------------------------------------------
		// 통신메시지에 처리결과 코드/메시지를 등록한다.
		//--------------------------------------------------
		{
			if (list == null ) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = messageSource.getMessage("error.cd.retrieve.none", null, locale);
				errorMsg = messageSource.getMessage("error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과

				//logger.debug("bridgeProviderService.getInterfaces:\n" + Util.toJSONPrettyString(list));

				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.retrieve.list.ok", null, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
		}
		return comMessage;
	}



	@RequestMapping(value = "/models/deploys/bridge/datasets", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String,List<DataSet>>> getDatasets(
		HttpSession httpSession,
		@RequestBody ComMessage<Map<String,String>, Map<String,List<DataSet>>> comMessage,
		Locale locale, HttpServletRequest request) throws Exception {

		String errorCd = "";
		String errorMsg = "";
		Map<String,List<DataSet>> res = null;
		Map<String,String> params = comMessage.getRequestObject();
		{
			if(params == null || params.get("integrationId") == null) throw new Exception("have no required filed value:integrationId");
			if(params.get("systemCd") == null) throw new Exception("have no required filed value:systemCd");
		}
		//--------------------------------------------------
		// 모델 contents 조회 및 배포 실행
		//--------------------------------------------------
		{
			res = bridgeProviderService.getDataSets(params.get("integrationId"), params.get("systemCd"));
			//logger.debug("DataSets:\n" + Util.toJSONPrettyString(res));
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		}
		//--------------------------------------------------
		// 통신메시지에 처리결과 코드/메시지를 등록한다.
		//--------------------------------------------------
		{
			if (res == null ) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = messageSource.getMessage("error.cd.retrieve.none", null, locale);
				errorMsg = messageSource.getMessage("error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.retrieve.list.ok", null, locale);
				comMessage.setResponseObject(res);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
		}
		return comMessage;
	}



	/*
	@RequestMapping(value = "/models/deploys/datasets", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>,List<DataSet>> getDataSetList(
		HttpSession httpSession,
		@RequestBody ComMessage<Map<String,Object>,List<DataSet>> comMessage,
		Locale locale, HttpServletRequest request) throws Exception, Exception {

		String errorCd = "";
		String errorMsg = "";
		String xml = null;
		Map<String, Object> params = comMessage.getRequestObject();
		if(params == null || params.get("integrationId") == null) {
			String [] errorMsgParams = {this.getClass().toString(), "integrationId"};
			errorMsg = messageSource.getMessage("error.msg.request.required.parame", errorMsgParams, locale);
			throw new Exception(errorMsg);
		}
		//--------------------------------------------------
		// 조회 실행
		//--------------------------------------------------
		{
			String integrationId = (String)params.get("integrationId");
			//xml = modelDeployService.getDeployModelXml(interfaceMid);
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		}
		//--------------------------------------------------
		// 통신메시지에 처리결과 코드/메시지를 등록한다.
		//--------------------------------------------------
		{
			if (xml == null ) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = messageSource.getMessage("error.cd.retrieve.none", null, locale);
				errorMsg = messageSource.getMessage("error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.retrieve.list.ok", null, locale);
				comMessage.setResponseObject(xml);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
		}
		return comMessage;
	}
	*/
}


