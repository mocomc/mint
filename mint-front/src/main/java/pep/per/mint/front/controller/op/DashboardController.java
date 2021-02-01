package pep.per.mint.front.controller.op;

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
import pep.per.mint.common.data.basic.IIPAgent;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.dashboard.EAIEngine;
import pep.per.mint.common.data.basic.dashboard.EngineDashboard;
import pep.per.mint.common.data.basic.dashboard.QmgrChannel;
import pep.per.mint.common.data.basic.dashboard.Queue;
import pep.per.mint.common.data.basic.dashboard.QueueManager;
import pep.per.mint.common.data.basic.dashboard.ServerProcess;
import pep.per.mint.common.data.basic.dashboard.ServerResource;
import pep.per.mint.common.data.basic.dashboard.Trigger;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.op.DashboardService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * <blockquote>
 *
 * <pre>
 * <B>메인대시보드 CRUD  서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-OP-02-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">메인대시보드 통합 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getMainDashboardInfo(HttpSession, ComMessage, Locale) getMainDashboardInfo}</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/op/dashboard/maindashboards</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author isjang </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	/**
	 * The Dashboard service.
	 */
	@Autowired
	DashboardService dashboardService;

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * 서블리컨텍스트 관련정보 참조를 위한 객체
	 * 예를 들어 servletContext를 이용하여 웹어플리케이션이
	 * 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	 */
	@Autowired
	private ServletContext servletContext;





	/**
	 * <pre>
	 * 처리건수 조회(금일)
	 * API ID : REST-R01-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/throughput/daily",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getDailyThroughput(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getDailyThroughput(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 에러건수(금일) - TOP num
	 * API ID : REST-R02-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/top/error-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getErrorListTop(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getErrorListTop(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 지연건수 - TOP num
	 * API ID : REST-R03-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/top/delay-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getDelayListTop(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getDelayListTop(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 실시간 처리건수 - 전체
	 * API ID : REST-R04-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/realtime/total-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getRealTimeTotalCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getRealTimeTotalCount(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 실시간 처리건수 - 관심인터페이스
	 * API ID : REST-R05-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/realtime/favorite-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getRealTimeFavoriteCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getRealTimeFavoriteCount(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 처리량 추이(전일/금일)
	 * API ID : REST-R08-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/stats/daily",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getCountStatsDaily(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list = null;
			{
				list = dashboardService.getCountStatsDaily(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 처리량 추이(최근3개월)
	 * API ID : REST-R09-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/stats/monthly",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, Map > getCountStatsMonthly(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Map > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Map resultMap = null;
			{
				resultMap = dashboardService.getCountStatsMonthly(params);
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
				//if(list == null || list.size() == 0) {
				if( resultMap == null || resultMap.size() == 0 ) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {resultMap.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(resultMap);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 처리량 추이(최근3년)
	 * API ID : REST-R10-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/stats/yearly",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, Map > getCountStatsYearly(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Map > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Map resultMap = null;
			{
				resultMap = dashboardService.getCountStatsYearly(params);
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
				if(resultMap == null || resultMap.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {resultMap.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(resultMap);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 주요배치처리상태 - 카운트
	 * API ID : REST-R06-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/state/important-batch-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getImportantBatchStateCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getImportantBatchStateCount(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * 주요배치처리상태 - 리스트
	 * API ID : REST-R07-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/state/important-batch-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getImportantBatchStateList(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getImportantBatchStateList(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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
	 * EAI Engine Limit  조회
	 * API ID : REST-R10-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/engine/limits",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, EngineDashboard> getEngineLimitInfo(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, EngineDashboard> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			EngineDashboard engineDashboard = null;
			{
				engineDashboard = dashboardService.getEngineLimitInfo(params);
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
				if(engineDashboard == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(engineDashboard);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * CPU  임계치 초과목록  조회
	 * API ID : REST-R01-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/cpu",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ServerResource>> getCPULimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ServerResource>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<ServerResource> serverResource = null;
			{
				serverResource = dashboardService.getCPULimitList(params);
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
				if(serverResource == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(serverResource);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



	/**
	 * <pre>
	 * Memory  임계치 초과목록  조회
	 * API ID : REST-R02-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/memory",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ServerResource>> getMemoryLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ServerResource>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<ServerResource> serverResource = null;
			{
				serverResource = dashboardService.getMemoryLimitList(params);
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
				if(serverResource == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(serverResource);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



	/**
	 * <pre>
	 * Disk  임계치 초과목록  조회
	 * API ID : REST-R03-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/disk",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ServerResource>> getDiskLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ServerResource>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<ServerResource> serverResource = null;
			{
				serverResource = dashboardService.getDiskLimitList(params);
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
				if(serverResource == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(serverResource);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



	/**
	 * <pre>
	 * 프로세스  임계치 초과목록  조회
	 * API ID : REST-R04-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/process",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ServerProcess>> getProcessLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ServerProcess>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<ServerProcess> serverProcess = null;
			{
				serverProcess = dashboardService.getProcessLimitList(params);
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
				if(serverProcess == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(serverProcess);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 큐관리자  임계치 초과목록  조회
	 * API ID : REST-R05-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/qmgr",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<QueueManager>> getQmgrLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<QueueManager>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<QueueManager> qmgr = null;
			{
				qmgr = dashboardService.getQmgrLimitList(params);
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
				if(qmgr == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(qmgr);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 큐  임계치 초과목록  조회
	 * API ID : REST-R06-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/queue",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Queue>> getQueueLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Queue>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<Queue> queue = null;
			{
				queue = dashboardService.getQueueLimitList(params);
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
				if(queue == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(queue);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



	/**
	 * <pre>
	 * 채널  임계치 초과목록  조회
	 * API ID : REST-R07-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/channel",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<QmgrChannel>> getChannelLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<QmgrChannel>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<QmgrChannel> channel = null;
			{
				channel = dashboardService.getChannelLimitList(params);
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
				if(channel == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(channel);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * EAI Agent  임계치 초과목록  조회
	 * API ID : REST-R08-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/agent",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<EAIEngine>> getEAIAgentLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<EAIEngine>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<EAIEngine> engine = null;
			{
				engine = dashboardService.getAgentLimitList(params);
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
				if(engine == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(engine);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}




	/**
	 * <pre>
	 * EAI Runner  임계치 초과목록  조회
	 * API ID : REST-R09-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/broker",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<EAIEngine>> getEAIRunnerLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<EAIEngine>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<EAIEngine> engine = null;
			{
				engine = dashboardService.getRunnerLimitList(params);
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
				if(engine == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(engine);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * IIP Agent  임계치 초과목록  조회
	 * API ID : REST-R11-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/iipagent",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<IIPAgent>> getIIPAgentLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<IIPAgent>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<IIPAgent> engine = null;
			{
				engine = dashboardService.getIIPAgentLimitList(params);
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
				if(engine == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(engine);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * TRIGGER  임계치 초과목록  조회
	 * API ID : REST-R12-OP-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/system-resource/trigger",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Trigger>> getTriggerLimitList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Trigger>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// 통합 조회 실행
			//--------------------------------------------------
			List<Trigger> engine = null;
			{
				engine = dashboardService.getTriggerLimitList(params);
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
				if(engine == null) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {0};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(engine);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

}
