/**
 * 
 */
package pep.per.mint.front.controller.su;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

import pep.per.mint.database.service.su.PotalStatisticsService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>포털 통계 조회 서비스 제공 RESTful Controller</B>
 *
 * </pre>
 *
 * <blockquote>
 *
 * @author Solution TA
 */
@Controller
@RequestMapping("/su")
public class PotalStatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(PotalStatisticsController.class);
	
	
	
	/**
	 * 서블리컨텍스트 관련정보 참조를 위한 객체
	 * 예를 들어 servletContext를 이용하여 웹어플리케이션이
	 * 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	 */
	@Autowired
	private ServletContext servletContext;	

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * 
	 */
	@Autowired
	PotalStatisticsService potalStatisticsService;
	
	
	
	/**
	 * <pre>
	 * 일자별 포털 로그인횟수 조회 #1
	 * API ID : REST-R01-SU-04-03-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
     * @since version 1.0(2016.08)
     */
	@RequestMapping(value = "/potal/statistics/login-count/1", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getLoginStatistics1(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale, 
			HttpServletRequest request
			) throws Exception, ControllerException {


		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";  
			
			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call 
			//--------------------------------------------------
			{
				list = potalStatisticsService.getLoginStatistics1(params);
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
	
	/**
	 * <pre>
	 * 일자별 포털 로그인횟수 조회 #2
	 * API ID : REST-R02-SU-04-03-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
     * @since version 1.0(2016.08)
     */
	@RequestMapping(value = "/potal/statistics/login-count/2", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getLoginStatistics2(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale, 
			HttpServletRequest request
			) throws Exception, ControllerException {


		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";  
			
			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call 
			//--------------------------------------------------
			{
				list = potalStatisticsService.getLoginStatistics2(params);
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

	/**
	 * <pre>
	 * 일자별 포털 로그인/로그아웃 조회
	 * API ID : REST-R01-SU-04-03-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
     * @since version 1.0(2016.08)
     */
	@RequestMapping(value = "/potal/statistics/login-count/detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getLoginStatisticsDetail(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale, 
			HttpServletRequest request
			) throws Exception, ControllerException {


		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";  
			
			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call 
			//--------------------------------------------------
			{
				list = potalStatisticsService.getLoginStatisticsDetail(params);
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
