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
import pep.per.mint.common.data.basic.User;

import pep.per.mint.common.util.Util; 
import pep.per.mint.database.service.op.GSSPDashboardService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
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
public class GSSPDashboardController {

	private static final Logger logger = LoggerFactory.getLogger(GSSPDashboardController.class);

	/**
	 * The Dashboard service.
	 */
	@Autowired
	GSSPDashboardService dashboardService;

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
	 * 지연건수 - TOP num
	 * API ID : REST-R03-OP-02-00-002
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/top/delay-list",
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
	 * 통신불가포스 카운트
	 * API ID : REST-R11-OP-02-00-002
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/dead-pos-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Integer> getDeadPosCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Integer> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = ""; 
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Integer count = new Integer(0);
			{
				count = dashboardService.getDeadPosCount();
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
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok",null, locale);
                comMessage.setResponseObject(count);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
    
    
    /**
	 * <pre>
	 * 통신불가포스 LIST
	 * API ID : REST-R12-OP-02-00-002
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/dead-pos-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getDeadPosList(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getDeadPosList();
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
	 * 거래미발생포스 카운트
	 * API ID : REST-R13-OP-02-00-002
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/notran-pos-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Integer> getNoTransactionPosCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Integer> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
 
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Integer count = new Integer(0);
			{
				count = dashboardService.getNoTransactionPosCount();
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
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok",null, locale);
                comMessage.setResponseObject(count);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
    
    /**
	 * <pre>
	 * 거래미발생포스 LIST
	 * API ID : REST-R14-OP-02-00-002
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/notran-pos-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getNoTransactionPosList(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = dashboardService.getNoTransactionPosList();
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
	 * 토탈포스거래카운트
	 * API ID : REST-R15-OP-02-00-002
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/gssp/pos-totoal-tran-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Integer> getTotalPosTransactionCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Integer> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
 
            Map params = comMessage.getRequestObject();
            String[] fieldNameList = {"startDate","endDate"};           
            FieldCheckUtil.checkRequired(this.getClass().getSimpleName(), fieldNameList, params, messageSource, locale);
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Integer count = null;
			{
				count = dashboardService.getTotalPosTransactionCount(params);
                count = count == null ? new Integer(0) : count;
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
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok",null, locale);
                comMessage.setResponseObject(count);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
    
    
    	/**
	 * <pre>
	 * 실시간 처리건수 - 관심인터페이스
	 * API ID : REST-R16-OP-02-00-002
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
			value="/dashboard/gssp/realtime/favorite-count",
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
    
    
}
