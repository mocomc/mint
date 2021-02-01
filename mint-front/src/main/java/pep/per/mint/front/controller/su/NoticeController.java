/**
 *
 */
package pep.per.mint.front.controller.su;

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
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.notification.Notice;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.NoticeService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>공지사항 CRUD  서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-C01-SU-02-01</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공지사항 등록</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #createNotice(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/su/notices</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-U01-SU-02-01</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공지사항 변경</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #updateNotice(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/su/notices/{noticeId}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-SU-02-01</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공지사항 리스트 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getNoticeList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/su/notices</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R02-SU-02-01</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공지사항 상세 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getNoticeDetail(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/su/notices/{noticeId}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-D01-SU-02-01</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공지사항 삭제</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #deleteNotice(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/su/notices/{noticeId}</td>
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
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	/**
	 * The Message source.
	 */
// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Notice service.
	 */
// 공지사항관련 데이터 서비스를 구현한 객체
	@Autowired
	NoticeService noticeService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	/**
	 * <pre>
	 * 공지사항 등록
	 * API ID : REST-C01-SU-02-01
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
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> createNotice(
			HttpSession httpSession,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Notice notice = comMessage.getRequestObject();
			/*
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("NoticeController.createNotice", notice, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createNotice notice param dump:\n", FieldCheckUtil.jsonDump(notice)));

			}
			*/

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				notice.setRegUser(regId);
				notice.setRegDate(regDate);
			}

			//----------------------------------------------------------------------------
			//[임시버전-20200520]
			//- NH농협 보안 취약점 대응 수준에서 보완
			//- Interface Admin 만 CUD 가능
			//----------------------------------------------------------------------------
			{
				User user = (User) httpSession.getAttribute("user");
				if( user == null || !user.getRole().getIsInterfaceAdmin().equals("Y") ) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
					String[] errorMsgParams = {"Notice.create","Not Authorized"};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}
			}

			try {
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = noticeService.createNotice(notice);
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(notice);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}
			catch (Throwable e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"NoticeController.createNotice",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);

				throw new ControllerException(errorCd, errorMsg, e);
			}
			finally {
				//
			}
		}
	}


	/**
	 * <pre>
	 * 공지사항 등록 - 테스트 유닛 메소드
	 * API ID : REST-C01-SU-02-01
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
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices", params = {"method=POST", "isTest=true"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> testCreateNotice(
			HttpSession httpSession,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			return null;
		}
	}


	/**
	 * <pre>
	 * 공지사항 수정
	 * API ID : REST-U01-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항 ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> updateNotice(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Notice notice = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			//[임시버전-20200520]
			//- NH농협 보안 취약점 대응 수준에서 보완
			//- Interface Admin 만 CUD 가능
			//----------------------------------------------------------------------------
			{
				User user = (User) httpSession.getAttribute("user");
				if( user == null || !user.getRole().getIsInterfaceAdmin().equals("Y") ) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
					String[] errorMsgParams = {"Notice.update","Not Authorized"};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				String modId = regId;
				String modDate = regDate;
				notice.setNoticeId(noticeId);
				notice.setModUser(modId);
				notice.setModDate(modDate);
				notice.setRegUser(regId);
				notice.setRegDate(regDate);
			}
			try {
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = noticeService.updateNotice(notice);
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(notice);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}
			catch (Throwable e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"NoticeController.updateNotice",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);
			}
			finally {
				//
			}
		}
	}


	/**
	 * <pre>
	 * 공지사항 수정 - 테스트 유닛 메소드
	 * API ID : REST-U01-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항 ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = {"method=PUT", "isTest=true"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> testUpdateNotice(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			return null;
		}
	}


	/**
	 * <pre>
	 * 공지사항 리스트 조회
	 * API ID : REST-R01-SU-02-01
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
     * @since version 1.0(2015.09)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/notices", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Notice>> getNoticeList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Notice>> comMessage,
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

			List<Notice> list = null;
			//--------------------------------------------------
			//공지사항 리스트 조회 실행
			//--------------------------------------------------
			{
				list = noticeService.getNoticeList(params);
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
	 * 공지사항 리스트 조회 - 테스트 유닛 메소드
	 * API ID : REST-R01-SU-02-01
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
     * @since version 1.0(2015.09)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/notices", params = {"method=GET", "isTest=true"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Notice>> testGetNoticeList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Notice>> comMessage,
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

			List<Notice> list = null;
			//--------------------------------------------------
			//공지사항 리스트 조회 실행
			//--------------------------------------------------
			{
				list = noticeService.getNoticeList(params);
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
	 * 공지사항 리스트 상세 조회
	 * API ID : REST-R02-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> getNoticeDetail(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Notice noticeDetail = null;
			//--------------------------------------------------
			//공지사항 조회 실행
			//--------------------------------------------------
			{
				noticeDetail = noticeService.getNoticeDetail(noticeId);
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
				if (noticeDetail == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(noticeDetail);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 공지사항 리스트 상세 조회 - 테스트 유닛 메소드
	 * API ID : REST-R02-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = {"method=GET", "isTest=true"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> testGetNoticeDetail(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Notice noticeDetail = null;
			//--------------------------------------------------
			//공지사항 조회 실행
			//--------------------------------------------------
			{
				noticeDetail = noticeService.getNoticeDetail(noticeId);
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
				if (noticeDetail == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(noticeDetail);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 공지사항 삭제
	 * API ID : REST-D01-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> deleteNotice(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//----------------------------------------------------------------------------
			//[임시버전-20200520]
			//- NH농협 보안 취약점 대응 수준에서 보완
			//- Interface Admin 만 CUD 가능
			//----------------------------------------------------------------------------
			{
				User user = (User) httpSession.getAttribute("user");
				if( user == null || !user.getRole().getIsInterfaceAdmin().equals("Y") ) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
					String[] errorMsgParams = {"Notice.delete","Not Authorized"};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}
			}

			try {
				//----------------------------------------------------------------------------
				//삭제실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					String modUser = comMessage.getUserId();
					String modDate = Util.getFormatedDate();
					res = noticeService.deleteNotice(noticeId, modUser, modDate );
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}
			catch (Throwable e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.delete.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"NoticeController.deleteNotice",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);
			}
			finally {
				//
			}
		}
	}


	/**
	 * <pre>
	 * 공지사항 삭제 - 테스트 유닛 메소드
	 * API ID : REST-D01-SU-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param noticeId 공지사항ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author INSEONG
     * @since version 1.0(2015.09)
     */
	@RequestMapping(value = "/notices/{noticeId}", params = {"method=DELETE", "isTest=true"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Notice, Notice> testDeleteNotice(
			HttpSession httpSession,
			@PathVariable("noticeId") String noticeId,
			@RequestBody ComMessage<Notice, Notice> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			return null;
		}
	}


}
