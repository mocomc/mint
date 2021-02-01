package pep.per.mint.front.controller.su;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.TrackingStatisticsService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <pre>
 * <B>트레킹 통계 서비스 제공 RESTful Controller</B>
 * pep.per.mint.front.controller.su
 * TrackingStatisticsController.java
 * </pre>
 * @author whoana
 * @date Jun 14, 2019
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TrackingStatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(TrackingStatisticsController.class);

	//test 하고 false 로 바꿔주세요.
	private final static boolean isTest = false;
	private final static String testUser = "iip";

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	@Autowired
	private TrackingStatisticsService trackingStatisticsService;

	/**
	 * <pre>
	 * 인터페이스별 처리 레코드 건수 조회
	 * API ID : SU0810R01
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/tracking/interface-record-cnt", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getRecordCount(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String serviceId = "SU0810R01";
			String errorCd = "";
			String errorMsg = "";
			//ComMessageCheckUtil.check(serviceId, comMessage, messageSource, locale);
			Map params = comMessage.getRequestObject();
			if(params == null) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
				String []msgParams = {"serviceId", "scCriteriaType", "scPeriodFrom"};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", msgParams, locale);
				throw new ControllerException(errorCd, errorMsg);
			}else {
				String [] fields = {"scCriteriaType", "scPeriodFrom"};
				FieldCheckUtil.checkRequired(serviceId, fields, params, messageSource, locale);
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			if(!isTest) {
				User user = (User) httpSession.getAttribute("user");
				params.put("userId", user.getUserId());
				params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
			}else {
				params.put("userId", testUser);
				params.put("isInterfaceAdmin", "Y");
			}

			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = trackingStatisticsService.getRecordCount(params);

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
	 * 기간별(시간별/일별/월별/연도별) 조회
	 * API ID : REST-R01-SU-03-12
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
     * @since version 4.0(2020.04)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/time-period", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsTimePeriod(
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
				list = trackingStatisticsService.getStatisticsTimePeriod(params);
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
				if (list == null || list.size() <= 0) {// 결과가 없을 경우 비지니스 예외 처리
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
	 * 기간별(시간별/일별/월별/연도별) 조회 - 상세
	 * API ID : REST-R02-SU-03-12
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
	 * @since version 4.0(2020.04)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics/time-period-detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsTimePeriodDetail(
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
				list = trackingStatisticsService.getStatisticsTimePeriodDetail(params);
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
				if (list == null || list.size() <= 0) {// 결과가 없을 경우 비지니스 예외 처리
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
