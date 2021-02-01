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

import java.util.Date;
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
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.main.DevelopmentProceed;
import pep.per.mint.common.data.basic.main.Statistics;
import pep.per.mint.common.data.basic.main.StatisticsResult;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.co.MainService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * <blockquote>
 * <pre>
 * <B>메인(Main) 서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;"></td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">오류 / 지연 현황</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getErrorDelayList(HttpSession, ComMessage, Locale) getErrorDelayList}</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/mains/errors</td>
 * </tr>
 * </table>
 * </pre>
 * <blockquote>
 * @author Solution TF
 */
@Controller
@RequestMapping("/co")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	/**
	 * The Main service.
	 */
	@Autowired
	MainService mainService;

	/**
	 * The Common service.
	 */
	@Autowired
	CommonService commonService;

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;

	/**
	 * <pre>
	 * 오류 / 지연 현황 조회
	 *
	 * </pre>
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
    @RequestMapping(value = "/mains/delays", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<TRLog>> getErrorDelayList(
			HttpSession httpSession,
			@RequestBody ComMessage<?, List<TRLog>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			String errorCd = "";
			String errorMsg = "";

			Map<String, Object> params = new HashMap<String, Object>();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			List<TRLog> list = mainService.getErrorDelayList(params);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			logger.debug("--------------getErrorDelayList--------------");
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

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 전일/금일 처리 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param searchCnt the search cnt
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/proceeds/{searchCnt}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, StatisticsResult> getProceedList(
			HttpSession httpSession,
			@PathVariable("searchCnt") int searchCnt,
			@RequestBody ComMessage<Map<String, Object>, StatisticsResult> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			StatisticsResult result = new StatisticsResult();
			String pattern = "yyyyMMddHH";

			//현재 시간 세팅 (yyyyMMddHH24)
			searchCnt = 0 - searchCnt;
			String currentDate = Util.getFormatedDate(pattern);
			String searchStartDate = Util.getDateAdd(pattern, currentDate, 0);
			String searchEndDate = Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, 0), searchCnt);

			params.put("searchCnt", searchCnt);
			params.put("searchStartDate", searchStartDate);
			params.put("searchEndDate", searchEndDate);


			List<Statistics> today = mainService.getProceedList(params);

			//어제 날짜 세팅(yyyyMMddHH24)
			params.put("searchStartDate", Util.getDateAdd(pattern, currentDate, -1));
			params.put("searchEndDate", Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, -1), searchCnt));

			List<Statistics> yesterday = mainService.getProceedList(params);

			result.setSt_today(today);
			result.setSt_yesterday(yesterday);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			logger.debug("--------------getProceedList--------------");
			if (today == null || today.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {today.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(result);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 시스템 별 처리 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param searchCnt the search cnt
	 * @param searchSystem the search system
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/systems/{searchCnt}/{searchSystem}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, StatisticsResult> getSystemList(
			HttpSession httpSession,
			@PathVariable("searchCnt") int searchCnt,
			@PathVariable("searchSystem") String searchSystem,
			@RequestBody ComMessage<Map<String, Object>, StatisticsResult> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			StatisticsResult result = new StatisticsResult();
			String pattern = "yyyyMMddHH";

			//현재 시간 세팅 (yyyyMMddHH24)
			searchCnt = 0 - searchCnt;
			String currentDate = Util.getFormatedDate(pattern);
			String searchStartDate = Util.getDateAdd(pattern, currentDate, 0);
			String searchEndDate = Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, 0), searchCnt);

			if("".equals(searchSystem) || searchSystem == null || "all".equals(searchSystem)) {
				searchSystem = "all";
			}

			params.put("searchCnt", searchCnt);
			params.put("searchStartDate", searchStartDate);
			params.put("searchEndDate", searchEndDate);
			params.put("searchSystem", searchSystem);

			List<Statistics> today = mainService.getSystemList(params);

			//어제 날짜 세팅(yyyyMMddHH24)
			params.put("searchStartDate", Util.getDateAdd(pattern, currentDate, -1));
			params.put("searchEndDate", Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, -1), searchCnt));

			List<Statistics> yesterday = mainService.getSystemList(params);

			result.setSt_today(today);
			result.setSt_yesterday(yesterday);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			logger.debug("--------------getSystemList--------------");
			if (today == null || today.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {today.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(result);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 인터페이스 별 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param searchCnt the search cnt
	 * @param searchInterface the search interface
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/interfaces/{searchCnt}/{searchInterface}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, StatisticsResult> getInterfaceList(
			HttpSession httpSession,
			@PathVariable("searchCnt") int searchCnt,
			@PathVariable("searchInterface") String searchInterface,
			@RequestBody ComMessage<Map<String, Object>, StatisticsResult> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			StatisticsResult result = new StatisticsResult();

			String pattern = "yyyyMMddHH";

			//현재 시간 세팅 (yyyyMMddHH24)
			searchCnt = 0 - searchCnt;
			String currentDate = Util.getFormatedDate(pattern);
			String searchStartDate = Util.getDateAdd(pattern, currentDate, 0);
			String searchEndDate = Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, 0), searchCnt);

			if("".equals(searchInterface) || searchInterface == null || "all".equals(searchInterface)) {
				searchInterface = "all";
			}

			params.put("searchCnt", searchCnt);
			params.put("searchStartDate", searchStartDate);
			params.put("searchEndDate", searchEndDate);
			params.put("searchInterface", searchInterface);
			params.put("userId", user.getUserId());


			List<Statistics> today = mainService.getInterfaceList(params);

			//어제 날짜 세팅(yyyyMMddHH24)
			params.put("searchStartDate", Util.getDateAdd(pattern, currentDate, -1));
			params.put("searchEndDate", Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, -1), searchCnt));

			List<Statistics> yesterday = mainService.getInterfaceList(params);

			result.setSt_today(today);
			result.setSt_yesterday(yesterday);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			logger.debug("--------------getInterfaceList--------------");
			if (today == null || today.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {today.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(result);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 프로세스 별 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param searchCnt the search cnt
	 * @param searchBusiness the search business
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/business/{searchCnt}/{searchBusiness}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, StatisticsResult> getProcessList(
			HttpSession httpSession,
			@PathVariable("searchCnt") int searchCnt,
			@PathVariable("searchBusiness") String searchBusiness,
			@RequestBody ComMessage<Map<String, Object>, StatisticsResult> comMessage,
			Locale locale, HttpServletRequest request) throws Exception {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			StatisticsResult result = new StatisticsResult();

			String pattern = "yyyyMMddHH";

			//현재 시간 세팅 (yyyyMMddHH24)
			searchCnt = 0 - searchCnt;
			String currentDate = Util.getFormatedDate(pattern);
			String searchStartDate = Util.getDateAdd(pattern, currentDate, 0);
			String searchEndDate = Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, 0), searchCnt);

			if("".equals(searchBusiness) || searchBusiness == null || "all".equals(searchBusiness)) {
				searchBusiness = "all";
			}

			comMessage.getRequestObject().put("searchStartDate", searchStartDate);
			comMessage.getRequestObject().put("searchEndDate", searchEndDate);
			comMessage.getRequestObject().put("searchBusiness", searchBusiness);


			List<Statistics> today = mainService.getProcessList(comMessage.getRequestObject());

			//어제 날짜 세팅(yyyyMMddHH24)
			comMessage.getRequestObject().put("searchStartDate", Util.getDateAdd(pattern, currentDate, -1));
			comMessage.getRequestObject().put("searchEndDate", Util.getHourAdd(pattern, Util.getDateAdd(pattern, currentDate, -1), searchCnt));

			List<Statistics> yesterday = mainService.getProcessList(comMessage.getRequestObject());

			result.setSt_today(today);
			result.setSt_yesterday(yesterday);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			logger.debug("--------------getProcessList--------------");
			if (today == null || today.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {today.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(result);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 장애 발생 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/errors", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, List<Statistics>> getErrorList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String, Object>, List<Statistics>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
    	{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.

			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());



			String pattern = "yyyyMMdd";
			//현재 시간 세팅 (yyyyMMddHH24)
			String currentDate = Util.getFormatedDate(pattern);
			//String searchEndDate = Util.getDateAdd(pattern, currentDate, -4);

			String startDate = Util.getDateAdd(pattern, currentDate, -1);
			String endDate = Util.getDateAdd(pattern, currentDate, -5);

			params.put("startDate", startDate);
			params.put("endDate", endDate);

			List<Statistics> today = mainService.getErrorList(params);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.

			logger.debug("--------------getErrorList--------------");
			if (today == null || today.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {today.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(today);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 개발 진행 현황
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/mains/devProceed", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, DevelopmentProceed> getDevProceedList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String, Object>, DevelopmentProceed> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
    	{
			logger.debug("[SESSION] : " + httpSession.getId());
			//note : 필수값 체크 혹은 디버깅으로 requestObject()의 값을 출력하도록.
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			DevelopmentProceed result = mainService.getDevProceedList(params);

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			logger.debug("--------------getDevProceedList--------------");
			if (result == null) {// 결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(result);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}


	/**
	 * Gets interface list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface list
	 * @throws Exception the exception
     */
	@RequestMapping(value = "/mains/interface", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<Map, List<Interface>> getInterfaceList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Interface>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
            String errorMsg = "";
            Map params = null;
            List list = null;
            //-------------------------------------------------
            //파라메터 체크
            //-------------------------------------------------
            {
                params = (Map)comMessage.getRequestObject();
                FieldCheckUtil.checkNullAndSkip(params);
                String paramString = FieldCheckUtil.paramString(params);
                logger.debug(Util.join("\nparamString", paramString));
            }
            //-------------------------------------------------
            //리스트를 조회한다.
            //-------------------------------------------------
            {
        		list = commonService.getInterfaceList(params);
            }

            //-------------------------------------------------
            //서비스 처리 종료시간을 얻어 CM에 세팅한다.
            //-------------------------------------------------
            {
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
            }
            //-------------------------------------------------
            //통신메시지에 처리결과 코드/메시지를 등록한다.
            //-------------------------------------------------
            {
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
            }

			return comMessage;
		}
	}
}
