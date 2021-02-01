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
package pep.per.mint.front.controller.en;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.monitor.FrontLog;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.en.FrontLogService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.service.FrontManagementService;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * <blockquote>
 *
 * <pre>
 * <B>포털의 환경설정값 실시간 변경 서비스 제공 RESTful Controller</B>
 *
 * </pre>
 *
 * <blockquote>
 *
 * @author Solution TF
 */
@Controller
@RequestMapping("/en")
public class PortalController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PortalController.class);


	/**
	 * The Front management service.
	 */
	@Autowired
	FrontManagementService frontManagementService;


	/**
	 * The Message source.
	 */
// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
 

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	/**
	 * The Front log service.
	 */
	@Autowired
	FrontLogService frontLogService;

	/**
	 * <pre>
	 * 포털 환경변수 적용
	 * API ID : REST-S01-EN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param key the key
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value = "/set-env/{key}", 
			params = "method=POST", 
			method = RequestMethod.POST, 
			headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> setEnv(
			HttpSession httpSession,
			@PathVariable("key") String key,
			@RequestBody ComMessage<?,?> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";  
			Object envValue = comMessage.getRequestObject();
			//--------------------------------------------------
			//파라메터 체크 
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("PortalController.setEnv", "key", key, messageSource, locale);
				FieldCheckUtil.checkRequired("PortalController.setEnv", "envValue", envValue, messageSource, locale);
			}
			
		 
			
			try{
				//--------------------------------------------------
				//환경값 설정 
				//--------------------------------------------------
				frontManagementService.setEnvironmentValue(key, envValue);
				
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				}
				//--------------------------------------------------
				// 통신메시지에 성공 코드/메시지를 등록한다.
				//--------------------------------------------------
				{
					comMessage.setErrorCd(MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale));
					comMessage.setErrorMsg(MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale));
				}
				
				return comMessage;
				
			}catch(Exception e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.portal.env.apply.fail", locale);
				Object[] errorMsgParams = {e != null ? e.getMessage() : ""};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.portal.env.apply.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg);
			}
			
		}
	}


	/**
	 * <pre>
	 * 포털 프론트로그 조회
	 * API ID : REST-R01-EN-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(
			value = "/frontlogs", 
			params = "method=GET", 
			method = RequestMethod.POST, 
			headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<FrontLog>> getFrontLogList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<FrontLog>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";  
			List<FrontLog> list = null;
			Map params = null;
			//--------------------------------------------------
			//파라메터 체크 
			//--------------------------------------------------
			{	
				params = comMessage.getRequestObject();
				//FieldCheckUtil.checkRequired( "PortalController.getFrontLogList", "fromDate", params, messageSource, locale);
				//FieldCheckUtil.checkRequired( "PortalController.getFrontLogList", "toDate", params, messageSource, locale);
			}
			
		 
			//--------------------------------------------------
			//로그 조회 설정 
			//--------------------------------------------------
			{
				list = frontLogService.getFrontLogList(params == null ? new HashMap() : params);
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
				if (list == null || list.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			
			
			
			return comMessage;
			 
			
		}
	}

	
	
	
	

}
