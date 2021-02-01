/**
 *
 */
package pep.per.mint.front.controller.su;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.statistics.StatisticsDevelopmentStatus;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureRate;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureType;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfacePeriod;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfaceType;
import pep.per.mint.common.data.basic.statistics.SubjectStatus;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.StatisticsService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>통계 - 인터페이스 현황 CRUD 서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author INSEONG
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	/**
	 * The Message source.
	 */
// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Statistics service.
	 */
	@Autowired
	StatisticsService statisticsService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;

	/**
	 * <pre>
	 * 종합과제현황(솔루션별)조회
	 * API ID : REST-R01-AN-08-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/channel", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<SubjectStatus>> getSubjectStatusByChannel(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<SubjectStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<SubjectStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusGroupByChannel(params);
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

	/**
	 * <pre>
	 * 종합과제현황(채널)조회  -
	 * API ID : REST-R01-AN-08-01-601
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/channel/dev", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getSubjectStatusByChannelDev(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusGroupByChannelDev(params);
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


	/**
	 * <pre>
	 * 종합과제현황(시스템별)조회
	 * API ID : REST-R01-AN-08-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/system", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<SubjectStatus>> getSubjectStatusBySystem(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<SubjectStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<SubjectStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusGroupBySystem(params);
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


	/**
	 * <pre>
	 * 종합과제현황(리소스별)조회
	 * API ID : REST-R01-AN-08-03
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/resource", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<SubjectStatus>> getSubjectStatusByResource(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<SubjectStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<SubjectStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusGroupByResource(params);
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


	/**
	 * <pre>
	 * 종합과제현황(담당자별)조회
	 * API ID : REST-R01-AN-08-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/user", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<SubjectStatus>> getSubjectStatusByUser(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<SubjectStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<SubjectStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusGroupByUser(params);
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

	/**
	 * <pre>
	 * 종합과제현황상세조회
	 * API ID : REST-R01-AN-08-05
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
	 * @since version 1.0(2015.07)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/subject-status/detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getSubjectStatusDetail(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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

			List<Requirement> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getSubjectStatusDetail(params);
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

	/**
	 * <pre>
	 * 개발 진척 현황 조회
	 * API ID : REST-R01-AN-03-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/development-status", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsDevelopmentStatus>> getDevelopmentStatus(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsDevelopmentStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsDevelopmentStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getDevelopmentStatus(params);

				if (list.size() > 0) {
					if (params.get("searchMode").equals("DEFAULT")) {

						StatisticsDevelopmentStatus tmpStatisticsDevelopmentStatus = new StatisticsDevelopmentStatus();


						int review_C_sum = 0;
						int approval_C_sum = 0;
						int dev_C_sum = 0;
						int test_C_sum = 0;
						int real_approval_C_sum = 0;
						int real_C_sum = 0;
						int total_R_sum = 0;
						int total_sum = 0;

						for (int i=0; i<list.size(); i++) {

							tmpStatisticsDevelopmentStatus = list.get(i);

							review_C_sum += tmpStatisticsDevelopmentStatus.getReview_C();
							approval_C_sum += tmpStatisticsDevelopmentStatus.getApproval_C();
							dev_C_sum += tmpStatisticsDevelopmentStatus.getDev_C();
							test_C_sum += tmpStatisticsDevelopmentStatus.getTest_C();
							real_approval_C_sum += tmpStatisticsDevelopmentStatus.getReal_approval_C();
							real_C_sum += tmpStatisticsDevelopmentStatus.getReal_C();
							total_R_sum += tmpStatisticsDevelopmentStatus.getTotal_R_Cnt();
							total_sum += tmpStatisticsDevelopmentStatus.getTotal_Cnt();

						}

						tmpStatisticsDevelopmentStatus = new StatisticsDevelopmentStatus();

						tmpStatisticsDevelopmentStatus.setCriteriaDetailId("TOTAL_SUM");
						tmpStatisticsDevelopmentStatus.setCriteriaDetailNm("총 계");

						tmpStatisticsDevelopmentStatus.setReview_C(review_C_sum);
						tmpStatisticsDevelopmentStatus.setApproval_C(approval_C_sum);
						tmpStatisticsDevelopmentStatus.setDev_C(dev_C_sum);
						tmpStatisticsDevelopmentStatus.setTest_C(test_C_sum);
						tmpStatisticsDevelopmentStatus.setReal_approval_C(real_approval_C_sum);
						tmpStatisticsDevelopmentStatus.setReal_C(real_C_sum);
						tmpStatisticsDevelopmentStatus.setTotal_R_Cnt(total_R_sum);
						tmpStatisticsDevelopmentStatus.setTotal_Cnt(total_sum);


						list.add(tmpStatisticsDevelopmentStatus);
					}
				}

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


	/**
	 * <pre>
	 * 인터페이스 현황 조회 - 테스트 유닛 메소드
	 * API ID : REST-R01-AN-03-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/development-status", params = { "method=GET",
	"isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsDevelopmentStatus>> testGetDevelopmentStatus(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsDevelopmentStatus>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsDevelopmentStatus> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				/*System.out.println("\n\n\n\n");
				System.out.println("criteriaId["+params.get("criteriaId")+"]\n" +
								   "criteriaNm["+params.get("criteriaNm")+"]\n" +
								   "criteriaDetailId["+params.get("criteriaDetailId")+"]\n" +
								   "criteriaDetailNm["+params.get("criteriaDetailNm")+"]\n");
				System.out.println("\n\n\n\n");*/

				list = statisticsService.getDevelopmentStatus(params);

				if (list.size() > 0) {
					if (params.get("searchMode").equals("DEFAULT")) {
						//List<StatisticsDevelopmentStatus> tmpList = null;
						StatisticsDevelopmentStatus tmpStatisticsDevelopmentStatus = new StatisticsDevelopmentStatus();

						//tmpList = statisticsService.getInterfaceStatus(params);


						//DecimalFormat df = new DecimalFormat("#####.##");

						int dev_R_total_sum = 0;
						int dev_total_sum = 0;
						int test_total_sum = 0;
						int real_total_sum = 0;
						int total_sum = 0;

						//list = new ArrayList<StatisticsDevelopmentStatus>();
						for (int i=0; i<list.size(); i++) {

							tmpStatisticsDevelopmentStatus = list.get(i);

							dev_R_total_sum += tmpStatisticsDevelopmentStatus.getDev_R();
							dev_total_sum += tmpStatisticsDevelopmentStatus.getDev_Cnt();
							test_total_sum += tmpStatisticsDevelopmentStatus.getTest_Cnt();
							real_total_sum += tmpStatisticsDevelopmentStatus.getReal_Cnt();
							total_sum += tmpStatisticsDevelopmentStatus.getTotal_Cnt();

						}

						tmpStatisticsDevelopmentStatus = new StatisticsDevelopmentStatus();

						tmpStatisticsDevelopmentStatus.setCriteriaDetailId("TOTAL_SUM");
						tmpStatisticsDevelopmentStatus.setCriteriaDetailNm("총 계");

						tmpStatisticsDevelopmentStatus.setDev_R(dev_R_total_sum);
						tmpStatisticsDevelopmentStatus.setDev_Cnt(dev_total_sum);
						tmpStatisticsDevelopmentStatus.setTest_Cnt(test_total_sum);
						tmpStatisticsDevelopmentStatus.setReal_Cnt(real_total_sum);
						tmpStatisticsDevelopmentStatus.setTotal_Cnt(total_sum);

						list.add(tmpStatisticsDevelopmentStatus);
					}
				}

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


	/**
	 * <pre>
	 * 개발 진척 현황 - 상태별 요건 리스트 조회
	 * API ID : REST-R02-AN-03-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.11)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/development-status-requirements", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getDevelopmentStatusRequirementList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Requirement> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getDevelopmentStatusRequirementList(params);
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



	/**
	 * <pre>
	 * 인터페이스 기간별 조회 summary
	 * API ID : REST-R05-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period/summary", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfacePeriodSummary(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfacePeriodSummary(params);
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
				if (list == null || list.size() <= 1) {// 결과가 없을 경우 비지니스 예외 처리
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



	/**
	 * <pre>
	 * 인터페이스 기간별 조회 리스트(상세)
	 * API ID : REST-R06-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfacePeriodList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfacePeriodList(params);
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
				if (list == null || list.size() <= 1) {// 결과가 없을 경우 비지니스 예외 처리
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




	/**
	 * <pre>
	 * 인터페이스 기간별 조회 totals
	 * API ID : REST-R08-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period/totals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfacePeriodTotals(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfacePeriodTotal(params);
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
				if (list == null || list.size() <= 1) {// 결과가 없을 경우 비지니스 예외 처리
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





	/**
	 * <pre>
	 * 인터페이스 송신기관별 조회 totals
	 * API ID : REST-R09-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-org/totals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfaceOrgTotals(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				params.put("searchP", "'201703','201704'");
				list = statisticsService.getStatisticsInterfaceOrgTotal(params);
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
				if (list == null || list.size() < 1) {// 결과가 없을 경우 비지니스 예외 처리
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




	/**
	 * <pre>
	 * 인터페이스 송신기관별 년도 조회 totals
	 * API ID : REST-R10-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-org/year/totals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfaceOrgYearTotals(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfaceOrgYearTotal(params);
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
				if (list == null || list.size() < 1) {// 결과가 없을 경우 비지니스 예외 처리
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


	/**
	 * <pre>
	 * 인터페이스 송신기관별 월 조회 totals
	 * API ID : REST-R11-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-org/month/totals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfaceOrgMonthTotals(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfaceOrgMonthTotal(params);
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
				if (list == null || list.size() < 1) {// 결과가 없을 경우 비지니스 예외 처리
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



	/**
	 * <pre>
	 * 인터페이스 기관-인터페이스별 월 조회 totals
	 * API ID : REST-R12-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/organization/interface/totals", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfaceOrganizationTotals(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getStatisticsInterfaceOrganizationTotal(params);
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
				if (list == null || list.size() < 1) {// 결과가 없을 경우 비지니스 예외 처리
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


	/**
	 * <pre>
	 * 인터페이스 기간별 조회
	 * API ID : REST-R01-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsInterfacePeriod>> getStatisticsInterafacePeriod(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsInterfacePeriod>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsInterfacePeriod> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsInterfacePeriod(params);

				if (list.size() > 0) {

					StatisticsInterfacePeriod tmpStatisticsInterfacePeriod = new StatisticsInterfacePeriod();

					int total_sum = 0;
					long total_dataSize = 0;
					int sum00 = 0;
					int sum01 = 0;
					int sum02 = 0;
					int sum03 = 0;
					int sum04 = 0;
					int sum05 = 0;
					int sum06 = 0;
					int sum07 = 0;
					int sum08 = 0;
					int sum09 = 0;
					int sum10 = 0;
					int sum11 = 0;
					int sum12 = 0;
					int sum13 = 0;
					int sum14 = 0;
					int sum15 = 0;
					int sum16 = 0;
					int sum17 = 0;
					int sum18 = 0;
					int sum19 = 0;
					int sum20 = 0;
					int sum21 = 0;
					int sum22 = 0;
					int sum23 = 0;
					int sum24 = 0;
					int sum25 = 0;
					int sum26 = 0;
					int sum27 = 0;
					int sum28 = 0;
					int sum29 = 0;
					int sum30 = 0;
					int sum31 = 0;

					int listLen = list.size();
					String chkCategoryId = "";
					for (int i=0; i<listLen; i++) {

						tmpStatisticsInterfacePeriod = list.get(i);

						total_sum += tmpStatisticsInterfacePeriod.getRowSum();
						if (!chkCategoryId.equals(tmpStatisticsInterfacePeriod.getCategoryId())) {
							chkCategoryId = tmpStatisticsInterfacePeriod.getCategoryId();

							total_dataSize += tmpStatisticsInterfacePeriod.getDataSize();
						}
						sum00 += tmpStatisticsInterfacePeriod.getCnt00();
						sum01 += tmpStatisticsInterfacePeriod.getCnt01();
						sum02 += tmpStatisticsInterfacePeriod.getCnt02();
						sum03 += tmpStatisticsInterfacePeriod.getCnt03();
						sum04 += tmpStatisticsInterfacePeriod.getCnt04();
						sum05 += tmpStatisticsInterfacePeriod.getCnt05();
						sum06 += tmpStatisticsInterfacePeriod.getCnt06();
						sum07 += tmpStatisticsInterfacePeriod.getCnt07();
						sum08 += tmpStatisticsInterfacePeriod.getCnt08();
						sum09 += tmpStatisticsInterfacePeriod.getCnt09();
						sum10 += tmpStatisticsInterfacePeriod.getCnt10();
						sum11 += tmpStatisticsInterfacePeriod.getCnt11();
						sum12 += tmpStatisticsInterfacePeriod.getCnt12();
						sum13 += tmpStatisticsInterfacePeriod.getCnt13();
						sum14 += tmpStatisticsInterfacePeriod.getCnt14();
						sum15 += tmpStatisticsInterfacePeriod.getCnt15();
						sum16 += tmpStatisticsInterfacePeriod.getCnt16();
						sum17 += tmpStatisticsInterfacePeriod.getCnt17();
						sum18 += tmpStatisticsInterfacePeriod.getCnt18();
						sum19 += tmpStatisticsInterfacePeriod.getCnt19();
						sum20 += tmpStatisticsInterfacePeriod.getCnt20();
						sum21 += tmpStatisticsInterfacePeriod.getCnt21();
						sum22 += tmpStatisticsInterfacePeriod.getCnt22();
						sum23 += tmpStatisticsInterfacePeriod.getCnt23();
						sum24 += tmpStatisticsInterfacePeriod.getCnt24();
						sum25 += tmpStatisticsInterfacePeriod.getCnt25();
						sum26 += tmpStatisticsInterfacePeriod.getCnt26();
						sum27 += tmpStatisticsInterfacePeriod.getCnt27();
						sum28 += tmpStatisticsInterfacePeriod.getCnt28();
						sum29 += tmpStatisticsInterfacePeriod.getCnt29();
						sum30 += tmpStatisticsInterfacePeriod.getCnt30();
						sum31 += tmpStatisticsInterfacePeriod.getCnt31();
					}

					tmpStatisticsInterfacePeriod = new StatisticsInterfacePeriod();

					tmpStatisticsInterfacePeriod.setCategoryId("TOTAL_SUM");
					tmpStatisticsInterfacePeriod.setCategoryNm("TOTAL_SUM");
					tmpStatisticsInterfacePeriod.setInterfaceStatusType("");

					tmpStatisticsInterfacePeriod.setRowSum(total_sum);
					tmpStatisticsInterfacePeriod.setDataSize(total_dataSize);
					tmpStatisticsInterfacePeriod.setCnt00(sum00);
					tmpStatisticsInterfacePeriod.setCnt01(sum01);
					tmpStatisticsInterfacePeriod.setCnt02(sum02);
					tmpStatisticsInterfacePeriod.setCnt03(sum03);
					tmpStatisticsInterfacePeriod.setCnt04(sum04);
					tmpStatisticsInterfacePeriod.setCnt05(sum05);
					tmpStatisticsInterfacePeriod.setCnt06(sum06);
					tmpStatisticsInterfacePeriod.setCnt07(sum07);
					tmpStatisticsInterfacePeriod.setCnt08(sum08);
					tmpStatisticsInterfacePeriod.setCnt09(sum09);
					tmpStatisticsInterfacePeriod.setCnt10(sum10);
					tmpStatisticsInterfacePeriod.setCnt11(sum11);
					tmpStatisticsInterfacePeriod.setCnt12(sum12);
					tmpStatisticsInterfacePeriod.setCnt13(sum13);
					tmpStatisticsInterfacePeriod.setCnt14(sum14);
					tmpStatisticsInterfacePeriod.setCnt15(sum15);
					tmpStatisticsInterfacePeriod.setCnt16(sum16);
					tmpStatisticsInterfacePeriod.setCnt17(sum17);
					tmpStatisticsInterfacePeriod.setCnt18(sum18);
					tmpStatisticsInterfacePeriod.setCnt19(sum19);
					tmpStatisticsInterfacePeriod.setCnt20(sum20);
					tmpStatisticsInterfacePeriod.setCnt21(sum21);
					tmpStatisticsInterfacePeriod.setCnt22(sum22);
					tmpStatisticsInterfacePeriod.setCnt23(sum23);
					tmpStatisticsInterfacePeriod.setCnt24(sum24);
					tmpStatisticsInterfacePeriod.setCnt25(sum25);
					tmpStatisticsInterfacePeriod.setCnt26(sum26);
					tmpStatisticsInterfacePeriod.setCnt27(sum27);
					tmpStatisticsInterfacePeriod.setCnt28(sum28);
					tmpStatisticsInterfacePeriod.setCnt29(sum29);
					tmpStatisticsInterfacePeriod.setCnt30(sum30);
					tmpStatisticsInterfacePeriod.setCnt31(sum31);

					list.add(tmpStatisticsInterfacePeriod);

				}

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


	/**
	 * <pre>
	 * 인터페이스 기간별 조회 - 테스트 유닛 메소드
	 * API ID : REST-R01-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsInterfacePeriod>> testGetStatisticsInterafacePeriod(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsInterfacePeriod>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsInterfacePeriod> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsInterfacePeriod(params);

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


	/**
	 * <pre>
	 * 인터페이스 유형별 조회
	 * API ID : REST-R02-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-type", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> getStatisticsInterafaceType(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsInterfaceType> list = null;
			List<StatisticsInterfaceType> customList = new ArrayList<StatisticsInterfaceType>();
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsInterfaceType(params);

				if (list.size() > 0) {

					StatisticsInterfaceType tmpStatisticsInterfaceType = new StatisticsInterfaceType();
					StatisticsInterfaceType customStatisticsInterfaceType = new StatisticsInterfaceType();

					int total_sum = 0;
					int finished_sum = 0;
					int processing_sum = 0;
					int error_sum = 0;
					long total_dataSize = 0;

					int category_total_sum = 0;
					int category_finished_sum = 0;
					int category_processing_sum = 0;
					int category_error_sum = 0;
					long category_total_dataSize = 0;

					String categoryCd = "";
					String categoryNm = "";

					int listLen = list.size();
					for (int i=0; i<listLen; i++) {

						tmpStatisticsInterfaceType = list.get(i);

						total_sum += tmpStatisticsInterfaceType.getTotalCnt();
						finished_sum += tmpStatisticsInterfaceType.getFinishedCnt();
						processing_sum += tmpStatisticsInterfaceType.getProcessingCnt();
						error_sum += tmpStatisticsInterfaceType.getErrorCnt();
						total_dataSize += tmpStatisticsInterfaceType.getDataSize();

						if (i > 0) {
							if (!categoryNm.equals(tmpStatisticsInterfaceType.getDataProcessMethodNm())) {

								customStatisticsInterfaceType = new StatisticsInterfaceType();

								customStatisticsInterfaceType.setDataProcessMethodCd(categoryCd);
								customStatisticsInterfaceType.setDataProcessMethodNm(categoryNm);
								customStatisticsInterfaceType.setStartResourceTypeCd("");
								customStatisticsInterfaceType.setStartResourceTypeNm("TOTAL_SUM");
								customStatisticsInterfaceType.setEndResourceTypeCd("");
								customStatisticsInterfaceType.setEndResourceTypeNm("");
								customStatisticsInterfaceType.setTotalCnt(category_total_sum);
								customStatisticsInterfaceType.setFinishedCnt(category_finished_sum);
								customStatisticsInterfaceType.setProcessingCnt(category_processing_sum);
								customStatisticsInterfaceType.setErrorCnt(category_error_sum);
								customStatisticsInterfaceType.setDataSize(category_total_dataSize);

								customList.add(customStatisticsInterfaceType);

								category_total_sum = 0;
								category_finished_sum = 0;
								category_processing_sum = 0;
								category_error_sum = 0;
								category_total_dataSize = 0;
							}
						}

						customList.add(tmpStatisticsInterfaceType);

						categoryCd = tmpStatisticsInterfaceType.getDataProcessMethodCd();
						categoryNm = tmpStatisticsInterfaceType.getDataProcessMethodNm();

						category_total_sum += tmpStatisticsInterfaceType.getTotalCnt();
						category_finished_sum += tmpStatisticsInterfaceType.getFinishedCnt();
						category_processing_sum += tmpStatisticsInterfaceType.getProcessingCnt();
						category_error_sum += tmpStatisticsInterfaceType.getErrorCnt();
						category_total_dataSize += tmpStatisticsInterfaceType.getDataSize();

					}

					customStatisticsInterfaceType = new StatisticsInterfaceType();

					customStatisticsInterfaceType.setDataProcessMethodCd(tmpStatisticsInterfaceType.getDataProcessMethodCd());
					customStatisticsInterfaceType.setDataProcessMethodNm(tmpStatisticsInterfaceType.getDataProcessMethodNm());
					customStatisticsInterfaceType.setStartResourceTypeCd("");
					customStatisticsInterfaceType.setStartResourceTypeNm("TOTAL_SUM");
					customStatisticsInterfaceType.setEndResourceTypeCd("");
					customStatisticsInterfaceType.setEndResourceTypeNm("");
					customStatisticsInterfaceType.setTotalCnt(category_total_sum);
					customStatisticsInterfaceType.setFinishedCnt(category_finished_sum);
					customStatisticsInterfaceType.setProcessingCnt(category_processing_sum);
					customStatisticsInterfaceType.setErrorCnt(category_error_sum);
					customStatisticsInterfaceType.setDataSize(category_total_dataSize);

					customList.add(customStatisticsInterfaceType);



					tmpStatisticsInterfaceType = new StatisticsInterfaceType();

					tmpStatisticsInterfaceType.setDataProcessMethodCd("TOTAL_SUM");
					tmpStatisticsInterfaceType.setDataProcessMethodNm("TOTAL_SUM");
					tmpStatisticsInterfaceType.setStartResourceTypeCd("");
					tmpStatisticsInterfaceType.setStartResourceTypeNm("");
					tmpStatisticsInterfaceType.setEndResourceTypeCd("");
					tmpStatisticsInterfaceType.setEndResourceTypeNm("");
					tmpStatisticsInterfaceType.setTotalCnt(total_sum);
					tmpStatisticsInterfaceType.setFinishedCnt(finished_sum);
					tmpStatisticsInterfaceType.setProcessingCnt(processing_sum);
					tmpStatisticsInterfaceType.setErrorCnt(error_sum);
					tmpStatisticsInterfaceType.setDataSize(total_dataSize);

					customList.add(tmpStatisticsInterfaceType);

				}

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
					//comMessage.setResponseObject(list);
					comMessage.setResponseObject(customList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 인터페이스 유형별 조회 - 테스트 유닛 메소드
	 * API ID : REST-R02-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-type", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> testGetStatisticsInterafaceType(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsInterfaceType> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsInterfaceType(params);

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


	/**
	 * <pre>
	 * 인터페이스 유형별 - 상세 조회
	 * API ID : REST-R04-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.11)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-type-detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> getStatisticsInterfaceTypeDetail(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsInterfaceType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsInterfaceType> list = null;
			List<StatisticsInterfaceType> customList = new ArrayList<StatisticsInterfaceType>();
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsInterfaceTypeDetail(params);

				if (list.size() > 0) {

					StatisticsInterfaceType tmpStatisticsInterfaceType = new StatisticsInterfaceType();

					int total_sum = 0;
					int finished_sum = 0;
					int processing_sum = 0;
					int error_sum = 0;
					long total_dataSize = 0;

					int listLen = list.size();
					for (int i=0; i<listLen; i++) {

						tmpStatisticsInterfaceType = list.get(i);

						total_sum += tmpStatisticsInterfaceType.getTotalCnt();
						finished_sum += tmpStatisticsInterfaceType.getFinishedCnt();
						processing_sum += tmpStatisticsInterfaceType.getProcessingCnt();
						error_sum += tmpStatisticsInterfaceType.getErrorCnt();
						total_dataSize += tmpStatisticsInterfaceType.getDataSize();

						customList.add(tmpStatisticsInterfaceType);
					}

					tmpStatisticsInterfaceType = new StatisticsInterfaceType();

					tmpStatisticsInterfaceType.setDataProcessMethodCd("TOTAL_SUM");
					tmpStatisticsInterfaceType.setDataProcessMethodNm("TOTAL_SUM");
					tmpStatisticsInterfaceType.setStartResourceTypeCd("");
					tmpStatisticsInterfaceType.setStartResourceTypeNm("");
					tmpStatisticsInterfaceType.setEndResourceTypeCd("");
					tmpStatisticsInterfaceType.setEndResourceTypeNm("");
					tmpStatisticsInterfaceType.setTotalCnt(total_sum);
					tmpStatisticsInterfaceType.setFinishedCnt(finished_sum);
					tmpStatisticsInterfaceType.setProcessingCnt(processing_sum);
					tmpStatisticsInterfaceType.setErrorCnt(error_sum);
					tmpStatisticsInterfaceType.setDataSize(total_dataSize);

					customList.add(tmpStatisticsInterfaceType);

				}

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
					//comMessage.setResponseObject(list);
					comMessage.setResponseObject(customList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 장애발생율 통계 조회 - 테스트 유닛 메소드
	 * API ID : REST-R01-SU-03-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-failure-rate", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsFailureRate>> getStatisticsFailureRate(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsFailureRate>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsFailureRate> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsFailureRate(params);

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


	/**
	 * <pre>
	 * 기준 조건별 장애 발생 인터페이스 리스트를 조회
	 * API ID : REST-R03-SU-03-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-failure-interface-list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsFailureInterfaceList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsFailureInterfaceList(params);

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


	/**
	 * <pre>
	 * 장애발생율 통계 조회 - 테스트 유닛 메소드
	 * API ID : REST-R01-SU-03-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-failure-rate", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsFailureRate>> testGetStatisticsFailureRate(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsFailureRate>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsFailureRate> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsFailureRate(params);

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


	/**
	 * <pre>
	 * 장애유형별 통계 조회 - 테스트 유닛 메소드
	 * API ID : REST-R02-SU-03-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-failure-type", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsFailureType>> getStatisticsFailureType(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsFailureType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsFailureType> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsFailureType(params);

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


	/**
	 * <pre>
	 * 장애유형별 통계 조회 - 테스트 유닛 메소드
	 * API ID : REST-R02-SU-03-04
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-failure-type", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<StatisticsFailureType>> testGetStatisticsFailureType(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<StatisticsFailureType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<StatisticsFailureType> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getStatisticsFailureType(params);

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


	/**
	 * <pre>
	 * 기간별 비교 통계 조회
	 * API ID : REST-R03-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.10)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period-compare", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsCompareInterfaceStatus(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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

			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				String scCriteria = String.valueOf(params.get("scCriteria"));

				String paramChkDate_prev = "";
				String paramYear_prev = "";
				String paramMonth_prev = "";
				String paramDay_prev = "";
				String paramChkDate_curr = "";
				String paramYear_curr = "";
				String paramMonth_curr = "";
				String paramDay_curr = "";

				String paramFromDate_prev = "";
				String paramToDate_prev = "";
				String paramFromDate_curr = "";
				String paramToDate_curr = "";

				String pattern = "yyyyMMdd";
				if (scCriteria.equals("Days")) {

					pattern = "yyyyMMdd";
					// 금일
					String currentDate = Util.getFormatedDate(pattern);

					paramChkDate_curr = currentDate;
					paramYear_curr = currentDate.substring(0, 4);
					paramMonth_curr = currentDate.substring(4, 6);
					paramDay_curr = currentDate.substring(6, 8);

					// 전일
					String previousDate = Util.getDateAdd(pattern, currentDate, -1);

					paramChkDate_prev = previousDate;
					paramYear_prev = previousDate.substring(0, 4);
					paramMonth_prev = previousDate.substring(4, 6);
					paramDay_prev = previousDate.substring(6, 8);

				}
				else if (scCriteria.equals("Weeks")) {

					pattern = "yyyyMMdd";
					// 금주
					Calendar cal_curr = Calendar.getInstance();
					List<String> dayList_curr = new ArrayList<String>();

					dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					while(cal_curr.get(Calendar.DAY_OF_WEEK) != 1) {
						cal_curr.add(Calendar.DATE, -1);
						dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					}

					paramFromDate_curr = dayList_curr.get(dayList_curr.size()-1);;
					paramToDate_curr = dayList_curr.get(0);

					// 전주
					Calendar cal_prev = Calendar.getInstance();
					List<String> dayList_prev = new ArrayList<String>();

					while(cal_prev.get(Calendar.DAY_OF_WEEK) != 1){
						cal_prev.add(Calendar.DATE, -1);
					}
					cal_prev.add(Calendar.DATE, -1);
					dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					while(cal_prev.get(Calendar.DAY_OF_WEEK) != 1){
						cal_prev.add(Calendar.DATE, -1);
						dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					}

					paramFromDate_prev = dayList_prev.get(dayList_prev.size()-1);
					paramToDate_prev = dayList_prev.get(0);
				}
				else if (scCriteria.equals("Months")) {

					pattern = "yyyyMMdd";

					// 금월
					Calendar cal_curr = Calendar.getInstance();
					List<String> dayList_curr = new ArrayList<String>();

					dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					while(!"01".equals(new SimpleDateFormat("dd").format(cal_curr.getTime()))){
						cal_curr.add(Calendar.DATE, -1);
						dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					}

					paramYear_curr = dayList_curr.get(0).substring(0, 4);
					paramMonth_curr = dayList_curr.get(0).substring(4, 6);

					//전월
					Calendar cal_prev = Calendar.getInstance();
					List<String> dayList_prev = new ArrayList<String>();

					while(!"01".equals(new SimpleDateFormat("dd").format(cal_prev.getTime()))){
						cal_prev.add(Calendar.DATE, -1);
					}
					cal_prev.add(Calendar.DATE, -1);
					dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					while(!"01".equals(new SimpleDateFormat("dd").format(cal_prev.getTime()))){
						cal_prev.add(Calendar.DATE, -1);
						dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					}

					paramYear_prev = dayList_prev.get(0).substring(0, 4);
					paramMonth_prev = dayList_prev.get(0).substring(4, 6);
				}
				else {
					//
				}


				// set params

				if (params.get("searchMode").equals("DEFAULT")) {
					if (scCriteria.equals("Days")) {

						params.put("paramYearPrev", paramYear_prev);
						params.put("paramMonthPrev", paramMonth_prev);
						params.put("paramDayPrev", paramDay_prev);

						params.put("paramYearCurr", paramYear_curr);
						params.put("paramMonthCurr", paramMonth_curr);
						params.put("paramDayCurr", paramDay_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
					else if (scCriteria.equals("Weeks")) {

						params.put("paramFromDatePrev", paramFromDate_prev);
						params.put("paramToDatePrev", paramToDate_prev);

						params.put("paramFromDateCurr", paramFromDate_curr);
						params.put("paramToDateCurr", paramToDate_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
					else if (scCriteria.equals("Months")) {

						params.put("paramYearPrev", paramYear_prev);
						params.put("paramMonthPrev", paramMonth_prev);

						params.put("paramYearCurr", paramYear_curr);
						params.put("paramMonthCurr", paramMonth_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
				}
				else if (params.get("searchMode").equals("DETAIL")) {
					List<Map> prev_List = null;
					List<Map> curr_List = null;
					if (scCriteria.equals("Days")) {
						// 전일 조회
						params.put("paramChkDate", paramChkDate_prev);
						params.put("paramYear", paramYear_prev);
						params.put("paramMonth", paramMonth_prev);
						params.put("paramDay", paramDay_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금일 조회
						params.put("paramChkDate", paramChkDate_curr);
						params.put("paramYear", paramYear_curr);
						params.put("paramMonth", paramMonth_curr);
						params.put("paramDay", paramDay_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}
					else if (scCriteria.equals("Weeks")) {
						// 전주 조회
						params.put("paramChkDate", paramFromDate_prev);
						params.put("paramFromDate", paramFromDate_prev);
						params.put("paramToDate", paramToDate_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금주 조회
						params.put("paramChkDate", paramFromDate_curr);
						params.put("paramFromDate", paramFromDate_curr);
						params.put("paramToDate", paramToDate_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}
					else if (scCriteria.equals("Months")) {
						// 전월 조회
						params.put("paramChkDate", paramYear_prev + paramMonth_prev);
						params.put("paramYear", paramYear_prev);
						params.put("paramMonth", paramMonth_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금월 조회
						params.put("paramChkDate", paramYear_curr + paramMonth_curr);
						params.put("paramYear", paramYear_curr);
						params.put("paramMonth", paramMonth_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}

					int prevCnt = prev_List == null ? 0 : prev_List.size();
					int currCnt = curr_List == null ? 0 : curr_List.size();
					int loopLength = 0;
					if (prevCnt > currCnt) {
						loopLength = prevCnt;
					}
					else if (prevCnt < currCnt) {
						loopLength = currCnt;
					}
					else {
						loopLength = prevCnt;
					}

					list = new ArrayList<Map>();
					Map<String, Object> listMap = null;
					Map<String, Object> prevMap = null;
					Map<String, Object> currMap = null;
					String tmpDefaultValue = "0";
					for (int i=0; i<loopLength; i++) {
						listMap = new HashMap<String, Object>();

						if (prevCnt > 0) {
							if (i >= prevCnt) {
								prevMap = new HashMap<String, Object>();
								tmpDefaultValue = "-";
							}
							else {
								prevMap = prev_List.get(i);
								tmpDefaultValue = "0";
							}
						}
						else {
							prevMap = new HashMap<String, Object>();
						}

						listMap.put("procDate_prev", (prevMap.get("CHK_DATE") == null)?"-":String.valueOf(prevMap.get("CHK_DATE")));
						listMap.put("totalCount_prev", (prevMap.get("TOTAL_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("TOTAL_CNT"))));
						listMap.put("finishedCount_prev", (prevMap.get("FINISHED_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("FINISHED_CNT"))));
						listMap.put("errorCount_prev", (prevMap.get("ERROR_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("ERROR_CNT"))));
						listMap.put("dataSize_prev", (prevMap.get("DATA_SIZE") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("DATA_SIZE"))));


						if (currCnt > 0) {
							if (i >= currCnt) {
								currMap = new HashMap<String, Object>();
								tmpDefaultValue = "-";
							}
							else {
								currMap = curr_List.get(i);
								tmpDefaultValue = "0";
							}
						}
						else {
							currMap = new HashMap<String, Object>();
						}

						listMap.put("procDate_curr", (currMap.get("CHK_DATE") == null)?"-":String.valueOf(currMap.get("CHK_DATE")));
						listMap.put("totalCount_curr", (currMap.get("TOTAL_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("TOTAL_CNT"))));
						listMap.put("finishedCount_curr", (currMap.get("FINISHED_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("FINISHED_CNT"))));
						listMap.put("errorCount_curr", (currMap.get("ERROR_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("ERROR_CNT"))));
						listMap.put("dataSize_curr", (currMap.get("DATA_SIZE") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("DATA_SIZE"))));


						listMap.put("categoryId", (prevMap.get("CATEGORY_ID") == null)?currMap.get("CATEGORY_ID"):prevMap.get("CATEGORY_ID"));
						listMap.put("categoryNm", (prevMap.get("CATEGORY_NM") == null)?currMap.get("CATEGORY_NM"):prevMap.get("CATEGORY_NM"));


						list.add(listMap);
					}
				}
				else {
					//
				}


				// set Period title
				Map periodTitle = new HashMap<String, String>();
				if (scCriteria.equals("Days")) {
					periodTitle.put("prevTitle", paramYear_prev + "/" + paramMonth_prev + "/" + paramDay_prev);
					periodTitle.put("currTitle", paramYear_curr + "/" + paramMonth_curr + "/" + paramDay_curr);
				}
				else if (scCriteria.equals("Weeks")) {
					periodTitle.put("prevTitle", paramFromDate_prev + " ~ " + paramToDate_prev);
					periodTitle.put("currTitle", paramFromDate_curr + " ~ " + paramToDate_curr);
				}
				else if (scCriteria.equals("Months")) {
					periodTitle.put("prevTitle", paramYear_prev + "/" + paramMonth_prev);
					periodTitle.put("currTitle", paramYear_curr + "/" + paramMonth_curr);
				}
				else {
					//
				}

				list.add(periodTitle);
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


	/**
	 * <pre>
	 * 기간별 비교 통계 조회 - 테스트 유닛 메소드
	 * API ID : REST-R03-SU-03-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.10)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period-compare", params = { "method=GET","isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> testGetStatisticsCompareInterfaceStatus(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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

			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				String scCriteria = String.valueOf(params.get("scCriteria"));

				String paramChkDate_prev = "";
				String paramYear_prev = "";
				String paramMonth_prev = "";
				String paramDay_prev = "";
				String paramChkDate_curr = "";
				String paramYear_curr = "";
				String paramMonth_curr = "";
				String paramDay_curr = "";

				String paramFromDate_prev = "";
				String paramToDate_prev = "";
				String paramFromDate_curr = "";
				String paramToDate_curr = "";

				String pattern = "yyyyMMdd";
				if (scCriteria.equals("Days")) {

					pattern = "yyyyMMdd";
					// 금일
					String currentDate = Util.getFormatedDate(pattern);

					paramChkDate_curr = currentDate;
					paramYear_curr = currentDate.substring(0, 4);
					paramMonth_curr = currentDate.substring(4, 6);
					paramDay_curr = currentDate.substring(6, 8);

					// 전일
					String previousDate = Util.getDateAdd(pattern, currentDate, -1);

					paramChkDate_prev = previousDate;
					paramYear_prev = previousDate.substring(0, 4);
					paramMonth_prev = previousDate.substring(4, 6);
					paramDay_prev = previousDate.substring(6, 8);

				}
				else if (scCriteria.equals("Weeks")) {

					pattern = "yyyyMMdd";
					// 금주
					Calendar cal_curr = Calendar.getInstance();
					List<String> dayList_curr = new ArrayList<String>();

					dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					while(cal_curr.get(Calendar.DAY_OF_WEEK) != 1) {
						cal_curr.add(Calendar.DATE, -1);
						dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					}

					paramFromDate_curr = dayList_curr.get(dayList_curr.size()-1);;
					paramToDate_curr = dayList_curr.get(0);

					// 전주
					Calendar cal_prev = Calendar.getInstance();
					List<String> dayList_prev = new ArrayList<String>();

					while(cal_prev.get(Calendar.DAY_OF_WEEK) != 1){
						cal_prev.add(Calendar.DATE, -1);
					}
					cal_prev.add(Calendar.DATE, -1);
					dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					while(cal_prev.get(Calendar.DAY_OF_WEEK) != 1){
						cal_prev.add(Calendar.DATE, -1);
						dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					}

					paramFromDate_prev = dayList_prev.get(dayList_prev.size()-1);
					paramToDate_prev = dayList_prev.get(0);
				}
				else if (scCriteria.equals("Months")) {

					pattern = "yyyyMMdd";

					// 금월
					Calendar cal_curr = Calendar.getInstance();
					List<String> dayList_curr = new ArrayList<String>();

					dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					while(!"01".equals(new SimpleDateFormat("dd").format(cal_curr.getTime()))){
						cal_curr.add(Calendar.DATE, -1);
						dayList_curr.add(new SimpleDateFormat(pattern).format(cal_curr.getTime()));
					}

					paramYear_curr = dayList_curr.get(0).substring(0, 4);
					paramMonth_curr = dayList_curr.get(0).substring(4, 6);

					//전월
					Calendar cal_prev = Calendar.getInstance();
					List<String> dayList_prev = new ArrayList<String>();

					while(!"01".equals(new SimpleDateFormat("dd").format(cal_prev.getTime()))){
						cal_prev.add(Calendar.DATE, -1);
					}
					cal_prev.add(Calendar.DATE, -1);
					dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					while(!"01".equals(new SimpleDateFormat("dd").format(cal_prev.getTime()))){
						cal_prev.add(Calendar.DATE, -1);
						dayList_prev.add(new SimpleDateFormat(pattern).format(cal_prev.getTime()));
					}

					paramYear_prev = dayList_prev.get(0).substring(0, 4);
					paramMonth_prev = dayList_prev.get(0).substring(4, 6);
				}
				else {
					//
				}


				// set params

				if (params.get("searchMode").equals("DEFAULT")) {
					if (scCriteria.equals("Days")) {

						params.put("paramYearPrev", paramYear_prev);
						params.put("paramMonthPrev", paramMonth_prev);
						params.put("paramDayPrev", paramDay_prev);

						params.put("paramYearCurr", paramYear_curr);
						params.put("paramMonthCurr", paramMonth_curr);
						params.put("paramDayCurr", paramDay_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
					else if (scCriteria.equals("Weeks")) {

						params.put("paramFromDatePrev", paramFromDate_prev);
						params.put("paramToDatePrev", paramToDate_prev);

						params.put("paramFromDateCurr", paramFromDate_curr);
						params.put("paramToDateCurr", paramToDate_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
					else if (scCriteria.equals("Months")) {

						params.put("paramYearPrev", paramYear_prev);
						params.put("paramMonthPrev", paramMonth_prev);

						params.put("paramYearCurr", paramYear_curr);
						params.put("paramMonthCurr", paramMonth_curr);

						list = statisticsService.getStatisticsInterfacePeriodForCompareDefault(params);
					}
				}
				else if (params.get("searchMode").equals("DETAIL")) {
					List<Map> prev_List = null;
					List<Map> curr_List = null;
					if (scCriteria.equals("Days")) {
						// 전일 조회
						params.put("paramChkDate", paramChkDate_prev);
						params.put("paramYear", paramYear_prev);
						params.put("paramMonth", paramMonth_prev);
						params.put("paramDay", paramDay_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금일 조회
						params.put("paramChkDate", paramChkDate_curr);
						params.put("paramYear", paramYear_curr);
						params.put("paramMonth", paramMonth_curr);
						params.put("paramDay", paramDay_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}
					else if (scCriteria.equals("Weeks")) {
						// 전주 조회
						params.put("paramChkDate", paramFromDate_prev);
						params.put("paramFromDate", paramFromDate_prev);
						params.put("paramToDate", paramToDate_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금주 조회
						params.put("paramChkDate", paramFromDate_curr);
						params.put("paramFromDate", paramFromDate_curr);
						params.put("paramToDate", paramToDate_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}
					else if (scCriteria.equals("Months")) {
						// 전월 조회
						params.put("paramChkDate", paramYear_prev + paramMonth_prev);
						params.put("paramYear", paramYear_prev);
						params.put("paramMonth", paramMonth_prev);

						prev_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);

						// 금월 조회
						params.put("paramChkDate", paramYear_curr + paramMonth_curr);
						params.put("paramYear", paramYear_curr);
						params.put("paramMonth", paramMonth_curr);

						curr_List = statisticsService.getStatisticsInterfacePeriodForCompareDetail(params);
					}

					//보안코드(null check 추가)
					int prevCnt = prev_List == null ? 0 : prev_List.size();
					int currCnt = curr_List == null ? 0 : curr_List.size();
					int loopLength = 0;
					if (prevCnt > currCnt) {
						loopLength = prevCnt;
					}
					else if (prevCnt < currCnt) {
						loopLength = currCnt;
					}
					else {
						loopLength = prevCnt;
					}

					list = new ArrayList<Map>();
					Map<String, Object> listMap = null;
					Map<String, Object> prevMap = null;
					Map<String, Object> currMap = null;
					String tmpDefaultValue = "0";
					for (int i=0; i<loopLength; i++) {
						listMap = new HashMap<String, Object>();

						if (prevCnt > 0) {
							if (i >= prevCnt) {
								prevMap = new HashMap<String, Object>();
								tmpDefaultValue = "-";
							}
							else {
								prevMap = prev_List.get(i);
								tmpDefaultValue = "0";
							}
						}
						else {
							prevMap = new HashMap<String, Object>();
						}

						listMap.put("procDate_prev", (prevMap.get("CHK_DATE") == null)?"-":String.valueOf(prevMap.get("CHK_DATE")));
						listMap.put("totalCount_prev", (prevMap.get("TOTAL_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("TOTAL_CNT"))));
						listMap.put("finishedCount_prev", (prevMap.get("FINISHED_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("FINISHED_CNT"))));
						listMap.put("errorCount_prev", (prevMap.get("ERROR_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("ERROR_CNT"))));
						listMap.put("dataSize_prev", (prevMap.get("DATA_SIZE") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(prevMap.get("DATA_SIZE"))));


						if (currCnt > 0) {
							if (i >= currCnt) {
								currMap = new HashMap<String, Object>();
								tmpDefaultValue = "-";
							}
							else {
								currMap = curr_List.get(i);
								tmpDefaultValue = "0";
							}
						}
						else {
							currMap = new HashMap<String, Object>();
						}

						listMap.put("procDate_curr", (currMap.get("CHK_DATE") == null)?"-":String.valueOf(currMap.get("CHK_DATE")));
						listMap.put("totalCount_curr", (currMap.get("TOTAL_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("TOTAL_CNT"))));
						listMap.put("finishedCount_curr", (currMap.get("FINISHED_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("FINISHED_CNT"))));
						listMap.put("errorCount_curr", (currMap.get("ERROR_CNT") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("ERROR_CNT"))));
						listMap.put("dataSize_curr", (currMap.get("DATA_SIZE") == null)?tmpDefaultValue:Long.parseLong(String.valueOf(currMap.get("DATA_SIZE"))));


						listMap.put("categoryId", (prevMap.get("CATEGORY_ID") == null)?currMap.get("CATEGORY_ID"):prevMap.get("CATEGORY_ID"));
						listMap.put("categoryNm", (prevMap.get("CATEGORY_NM") == null)?currMap.get("CATEGORY_NM"):prevMap.get("CATEGORY_NM"));


						list.add(listMap);
					}
				}
				else {
					//
				}


				// set Period title
				Map periodTitle = new HashMap<String, String>();
				if (scCriteria.equals("Days")) {
					periodTitle.put("prevTitle", paramYear_prev + "/" + paramMonth_prev + "/" + paramDay_prev);
					periodTitle.put("currTitle", paramYear_curr + "/" + paramMonth_curr + "/" + paramDay_curr);
				}
				else if (scCriteria.equals("Weeks")) {
					periodTitle.put("prevTitle", paramFromDate_prev + " ~ " + paramToDate_prev);
					periodTitle.put("currTitle", paramFromDate_curr + " ~ " + paramToDate_curr);
				}
				else if (scCriteria.equals("Months")) {
					periodTitle.put("prevTitle", paramYear_prev + "/" + paramMonth_prev);
					periodTitle.put("currTitle", paramYear_curr + "/" + paramMonth_curr);
				}
				else {
					//
				}

				list.add(periodTitle);
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


	/**
	 * <pre>
	 * 일솔루션별개발진척률조회
	 * API ID : REST-R01-SU-05-05-000
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/development-status/daily/channel", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getDevelopmentDailyStatusByChannel(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) {
				params = new HashMap();
			}
			String stMonth = (String)params.get("stMonth");

			if(Util.isEmpty(stMonth)) {
				stMonth = Util.getFormatedDate("yyyyMM");
			}

			int year = Integer.parseInt(stMonth.substring(0,4));
			int month = Integer.parseInt(stMonth.substring(4,6)) - 1;
			int dayOfMonth = Util.getLastDayOfMonth(year, month);

			params.put("startDate", Util.join(stMonth, "01"));
			params.put("endDate", Util.join(stMonth, Util.leftPad(Integer.toString(dayOfMonth), 2, "0")));

			List<Map> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getDevelopmentDailyStatusByChannel(params);

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

					List rebuildList = new ArrayList();
					//------------------------------------------
					//data 리빌딩(차트에 맞게...)
					//------------------------------------------
					{
						Map rebuildMap = new LinkedHashMap();
						for (Map record : list){
							String stDate = (String)record.get("stDate");
							String channelId = (String)record.get("channelId");
							String channelNm = (String)record.get("channelNm");

							String totCnt = (String)record.get("totCnt");
							String channelCnt = (String)record.get("channelCnt");
							String channelPer = (String)record.get("channelPer");

							String type = (String)record.get("type");
							String cnt = (String)record.get("cnt");
							String per = (String)record.get("per");

							String key = Util.join(channelId,type);
							Map rebuildRecord = (Map)rebuildMap.get(key);

							List<Integer> cntList = null;
							List<Integer> perList = null;
							if(rebuildRecord == null){
								rebuildRecord = new LinkedHashMap();
								rebuildRecord.put("channelId",channelId);
								rebuildRecord.put("channelNm",channelNm);
								rebuildRecord.put("type",type);
								rebuildRecord.put("totCnt",Integer.parseInt(totCnt));
								rebuildRecord.put("channelCnt",Integer.parseInt(channelCnt));
								rebuildRecord.put("channelPer",Integer.parseInt(channelPer));
								cntList = new ArrayList<Integer>();


								rebuildRecord.put("cntList",cntList);
								perList = new ArrayList<Integer>();


								for(int day = 1 ; day <= dayOfMonth ; day ++){
									cntList.add(0);
									perList.add(0);
								}


								rebuildRecord.put("perList", perList);
								rebuildMap.put(key,rebuildRecord);
							}
							cntList = (List)rebuildRecord.get("cntList");
							perList = (List)rebuildRecord.get("perList");
							int idx =  Integer.parseInt(stDate.substring(6));

							cntList.set((idx - 1), Integer.parseInt(cnt));
							perList.set((idx - 1), Integer.parseInt(per));

						}


						Iterator<String> ito = rebuildMap.keySet().iterator();
						while(ito.hasNext()){
							String key = ito.next();
							rebuildList.add(rebuildMap.get(key));
						}


					}

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(rebuildList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 일리소스별개발진척률조회
	 * API ID : REST-R02-SU-05-05-000
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/development-status/daily/resource", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getDevelopmentDailyStatusByResource(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) {
				params = new HashMap();
			}


			String stMonth = (String)params.get("stMonth");

			if(Util.isEmpty(stMonth)) {
				stMonth = Util.getFormatedDate("yyyyMM");
			}

			int year = Integer.parseInt(stMonth.substring(0,4));
			int month = Integer.parseInt(stMonth.substring(4,6)) - 1;
			int dayOfMonth = Util.getLastDayOfMonth(year, month);

			params.put("startDate", Util.join(stMonth, "01"));
			params.put("endDate", Util.join(stMonth, Util.leftPad(Integer.toString(dayOfMonth),2,"0")));


			List<Map> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getDevelopmentDailyStatusByResource(params);

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

					List rebuildList = new ArrayList();
					//------------------------------------------
					//data 리빌딩(차트에 맞게...)
					//------------------------------------------
					{
						Map rebuildMap = new LinkedHashMap();
						for (Map record : list){
							String stDate = (String)record.get("stDate");
							String resourceCd = (String)record.get("resourceCd");
							String resourceNm = (String)record.get("resourceNm");

							String totCnt = (String)record.get("totCnt");
							String resourceCnt = (String)record.get("resourceCnt");
							String resourcePer = (String)record.get("resourcePer");

							String type = (String)record.get("type");
							String cnt = (String)record.get("cnt");
							String per = (String)record.get("per");

							String key = Util.join(resourceCd,type);
							Map rebuildRecord = (Map)rebuildMap.get(key);

							List<Integer> cntList = null;
							List<Integer> perList = null;
							if(rebuildRecord == null){
								rebuildRecord = new LinkedHashMap();
								rebuildRecord.put("resourceCd",resourceCd);
								rebuildRecord.put("resourceNm",resourceNm);
								rebuildRecord.put("type",type);
								rebuildRecord.put("totCnt",Integer.parseInt(totCnt));
								rebuildRecord.put("resourceCnt",Integer.parseInt(resourceCnt));
								rebuildRecord.put("resourcePer",Integer.parseInt(resourcePer));
								cntList = new ArrayList<Integer>();


								rebuildRecord.put("cntList",cntList);
								perList = new ArrayList<Integer>();

								for(int day = 1 ; day <= dayOfMonth ; day ++){
									cntList.add(0);
									perList.add(0);
								}


								rebuildRecord.put("perList", perList);
								rebuildMap.put(key,rebuildRecord);
							}
							cntList = (List)rebuildRecord.get("cntList");
							perList = (List)rebuildRecord.get("perList");
							int idx =  Integer.parseInt(stDate.substring(6));

							cntList.set((idx - 1), Integer.parseInt(cnt));
							perList.set((idx - 1), Integer.parseInt(per));

						}


						Iterator<String> ito = rebuildMap.keySet().iterator();
						while(ito.hasNext()){
							String key = ito.next();
							rebuildList.add(rebuildMap.get(key));
						}


					}

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(rebuildList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 일시스템별개발진척률조회
	 * API ID : REST-R03-SU-05-05-000
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/development-status/daily/system", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getDevelopmentDailyStatusBySystem(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) {
				params = new HashMap();
			}

			String stMonth = (String)params.get("stMonth");

			if(Util.isEmpty(stMonth)) {
				stMonth = Util.getFormatedDate("yyyyMM");
			}

			int year = Integer.parseInt(stMonth.substring(0,4));
			int month = Integer.parseInt(stMonth.substring(4,6)) - 1;
			int dayOfMonth = Util.getLastDayOfMonth(year, month);

			params.put("startDate", Util.join(stMonth, "01"));
			params.put("endDate", Util.join(stMonth, Util.leftPad(Integer.toString(dayOfMonth),2,"0")));

			List<Map> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{

				list = statisticsService.getDevelopmentDailyStatusByProvider(params);

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

					List rebuildList = new ArrayList();
					//------------------------------------------
					//data 리빌딩(차트에 맞게...)
					//------------------------------------------
					{
						Map rebuildMap = new LinkedHashMap();
						for (Map record : list){
							String stDate = (String)record.get("stDate");
							String systemId = (String)record.get("systemId");
							String systemNm = (String)record.get("systemNm");

							String totCnt = (String)record.get("totCnt");
							String systemCnt = (String)record.get("systemCnt");
							String systemPer = (String)record.get("systemPer");

							String type = (String)record.get("type");
							String cnt = (String)record.get("cnt");
							String per = (String)record.get("per");

							String key = Util.join(systemId,type);
							Map rebuildRecord = (Map)rebuildMap.get(key);

							List<Integer> cntList = null;
							List<Integer> perList = null;
							if(rebuildRecord == null){
								rebuildRecord = new LinkedHashMap();
								rebuildRecord.put("systemId",systemId);
								rebuildRecord.put("systemNm",systemNm);
								rebuildRecord.put("type",type);
								rebuildRecord.put("totCnt",Integer.parseInt(totCnt));
								rebuildRecord.put("systemCnt",Integer.parseInt(systemCnt));
								rebuildRecord.put("systemPer",Integer.parseInt(systemPer));
								cntList = new ArrayList<Integer>();


								rebuildRecord.put("cntList",cntList);
								perList = new ArrayList<Integer>();

								for(int day = 1 ; day <= dayOfMonth ; day ++){
									cntList.add(0);
									perList.add(0);
								}


								rebuildRecord.put("perList", perList);
								rebuildMap.put(key,rebuildRecord);
							}
							cntList = (List)rebuildRecord.get("cntList");
							perList = (List)rebuildRecord.get("perList");
							int idx =  Integer.parseInt(stDate.substring(6));

							cntList.set((idx - 1), Integer.parseInt(cnt));
							perList.set((idx - 1), Integer.parseInt(per));

						}


						Iterator<String> ito = rebuildMap.keySet().iterator();
						while(ito.hasNext()){
							String key = ito.next();
							rebuildList.add(rebuildMap.get(key));
						}


					}

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(rebuildList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 일별개발진척률리스트조회
	 * API ID : REST-R04-SU-05-05-000
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/development-status/daily/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getDevelopmentDailyStatusList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 * 1.pep.per.mint.database.service.an.RequirementService.method 작성
		 * 2.pep.per.mint.front.controller.an.StatisticsController.method 작성
		 * 3.pep.per.mint.database.mapper.an.RequirementMapper.method 작성
		 * 4.pep.per.mint.database.mapper.an.RequirementMapper.xml 상의 맵핑 sql 작성
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<Requirement> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getDevelopmentDailyStatusList(params);
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






	/**
	 * <pre>
	 * 월별 인터페이스 변화량 조회
	 * API ID : REST-R07-SU-03-02
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/interface-count/monthly", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getMonthlyChangeInterfaceCount(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
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


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = statisticsService.getMonthlyChangeInterfaceCount(params);
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
