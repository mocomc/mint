/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.controller.envs;

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

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.endpoint.service.deploy.ModelDeployService; 

/**
 * @author whoana
 * @since Sep 14, 2020
 */
@RequestMapping("/rt")
@Controller
public class EnvironmentalController {
	
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentalController.class);

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	private ServletContext servletContext;	
	
	@Autowired
	ModelDeployService modelDeployService;
	
	
	@Autowired
	CommonService commonService;
	
	/**
	 * <pre>
	 * mint-bridge-apps 애플리케이션이 요청하는 환경변수 값을 조회한다.
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "/environmentals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, String> getEnvironmentVariables(
		HttpSession httpSession,
		@RequestBody ComMessage<Map<String,String>, String> comMessage,
		Locale locale, HttpServletRequest request) throws Exception {
	 
		String errorCd = "";
		String errorMsg = "";
		String value = null;
		Map<String, String> params = comMessage.getRequestObject();
		{
			if(params == null || params.get("attributeId") == null) throw new Exception("have no required filed value:attributeId");
			if(params == null || params.get("package") == null) throw new Exception("have no required filed value:package");
		}
		//--------------------------------------------------
		// 모델 contents 조회 및 배포 실행
		//--------------------------------------------------
		{ 
			value = commonService.getEnvironmentalValue(params.get("package"), params.get("attributeId"), null);
			
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
			if (Util.isEmpty(value)) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = messageSource.getMessage("error.cd.retrieve.none", null, locale);
				errorMsg = messageSource.getMessage("error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				
				//logger.debug("bridgeProviderService.getInterfaces:\n" + Util.toJSONPrettyString(list));
				
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.retrieve.list.ok", null, locale);
				comMessage.setResponseObject(value);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
		}
		return comMessage;
	}
}


