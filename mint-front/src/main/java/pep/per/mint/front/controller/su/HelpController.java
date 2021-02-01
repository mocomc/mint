/**
 *
 */
package pep.per.mint.front.controller.su;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Tooltip;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.HelpService;
import pep.per.mint.database.service.su.StatisticsService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * <blockquote>
 *
 * <pre>
 * <B>통계 - 도움말 툴팁 CRUD 서비스 제공 RESTful Controller</B>
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
 * @author Solution TF
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes" })
public class HelpController {

	private static final Logger logger = LoggerFactory.getLogger(HelpController.class);

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
	 * The Help service.
	 */
	@Autowired
	HelpService helpService;

	/**
	 * <pre>
	 * 도움말 등록
	 * API ID : REST-C01-SU-02-15-000
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
	@RequestMapping(value = "/help", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	ComMessage<Help, Help> createHelp(
			HttpSession httpSession,
			@RequestBody ComMessage<Help, Help> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Help help = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createHelp param dump:\n", FieldCheckUtil.jsonDump(help)));
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				help.setRegId(regId);
				help.setRegDate(regDate);
			}

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					{
						Map params = new HashMap();
						params.put("appId", help.getAppId());
						params.put("helpId", help.getHelpId());
						params.put("langId", help.getLangId());
						List<Help> list = helpService.existHelp(params);
						if(list == null || list.size() == 0) {
							res = helpService.createHelp(help);
						}else{
							errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.help.create.already.exist", locale);
							String[] errorMsgParams = {};
							errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.help.create.already.exist", errorMsgParams, locale);
							throw new ControllerException(errorCd, errorMsg);

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

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(help);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}
		}
	}

	/**
	 * <pre>
	 * 도움말 수정
	 * API ID : REST-U01-SU-02-15-000
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
	@RequestMapping(value = "/help", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	ComMessage<Help, Help> updateHelp(
			HttpSession httpSession,
			@RequestBody ComMessage<Help, Help> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Help help = comMessage.getRequestObject();


			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateHelp param dump:\n", FieldCheckUtil.jsonDump(help)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				help.setModId(regId);
				help.setModDate(Util.getFormatedDate());
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = helpService.updateHelp(help);
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
					comMessage.setResponseObject(help);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 도움말 조회
	 * API ID : REST-R01-SU-02-15-000
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param appId the app id
	 * @param helpId the help id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/help/{appId}/{helpId}/{langId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Help> getHelp(
			HttpSession httpSession,
			@PathVariable("appId") String appId,
			@PathVariable("helpId") String helpId,
			@PathVariable("langId") String langId,
			@RequestBody ComMessage<?, Help> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Help help = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				help = helpService.getHelp(appId, helpId, langId);
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
				if (help == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(help);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 도움말 list 조회
	 * API ID : REST-R02-SU-02-15-000
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
	@RequestMapping(value = "/help", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Help>> getHelpList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Help>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";



			List<Help> list = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				list = helpService.getHelpList(params);
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
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
	 * 도움말 삭제
	 * API ID : REST-D01-SU-02-15-000
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
     * @since version 1.0(2020.07)
     */
	@RequestMapping(value = "/help/{appId}/{helpId}/{langId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteHelp(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = null;

			try {
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					params = (Map) comMessage.getRequestObject();
					params.put("modId", comMessage.getUserId());
					params.put("modDate", Util.getFormatedDate());
					res = helpService.deleteHelpList(params);
				}
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}
				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);

					comMessage.setResponseObject(null); //null을 넣는게 이상하다고 생각...
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			} catch(Throwable e) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"HelpController.deleteHelpList",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			} finally {

			}


		}
	}




	/**
	 * <pre>
	 * 툴팁 등록
	 * API ID : REST-C01-SU-02-16-000
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
	@RequestMapping(value = "/tooltips", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	ComMessage<Tooltip, Tooltip> createTooltip(
			HttpSession httpSession,
			@RequestBody ComMessage<Tooltip, Tooltip> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Tooltip tooltip = comMessage.getRequestObject();


			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createTooltip param dump:\n", FieldCheckUtil.jsonDump(tooltip)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				tooltip.setRegId(regId);
				tooltip.setRegDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = helpService.createTooltip(tooltip);
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
					comMessage.setResponseObject(tooltip);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 툴팁  list 조회
	 * API ID : REST-R02-SU-02-16-000
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
	@RequestMapping(value = "/tooltips", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Tooltip>> getTooltipList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Tooltip>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";



			List<Tooltip> list = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				list = helpService.getTooltipList(params);
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
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
	 * 툴팁 조회
	 * API ID : REST-R01-SU-02-16-000
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param appId the app id
	 * @param tooltipId the tooltip id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/tooltips/{appId}/{tooltipId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Tooltip> getTooltip(
			HttpSession httpSession,
			@PathVariable("appId") String appId,
			@PathVariable("tooltipId") String tooltipId,
			@RequestBody ComMessage<?, Tooltip> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";



			Tooltip tooltip = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				tooltip = helpService.getTooltip(appId, tooltipId);
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
				if (tooltipId == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(tooltip);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 툴팁  수정
	 * API ID : REST-U01-SU-02-16-000
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
	@RequestMapping(value = "/tooltips", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	ComMessage<Tooltip, Tooltip> updateTooltip(
			HttpSession httpSession,
			@RequestBody ComMessage<Tooltip, Tooltip> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Tooltip tooltip = comMessage.getRequestObject();


			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateHelp param dump:\n", FieldCheckUtil.jsonDump(tooltip)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				tooltip.setModId(regId);
				tooltip.setModDate(Util.getFormatedDate());
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = helpService.updateTooltip(tooltip);
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
					comMessage.setResponseObject(tooltip);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}
		}
	}


	/**
	 * The Env.
	 */
	@Autowired
	FrontEnvironments env;

	/**
	 * <pre>
	 * export Help
	 * API ID : REST-E01-SU-02-15-000
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
	@RequestMapping(value = "/help/export/{langId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> exportHelp(
			HttpSession httpSession,
			@PathVariable("langId") String langId,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";



			List<Map> list = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				list = helpService.exportHelp();
				if(list != null && list.size() > 0){

					String root = servletContext.getRealPath("/");
					//String root = env.getAttachFilePath();

					File rootDir =  new File(root,Util.join("helpdata-",langId));
					rootDir.deleteOnExit();
					rootDir.mkdirs();

					logger.debug(Util.join("rootDir:",rootDir));
					File file = null;
					for (int i = 0 ; i < list.size() ;i ++){
						Map help = list.get(i);

						if(!langId.equals((String)help.get("langId"))) continue;

						String fileId = (String)help.get("fileId");
						String contents = (String)help.get("contents");
						FileOutputStream fos = null;
						try {



							file =  new File(rootDir,  fileId + ".data");
							file.deleteOnExit();
							file.createNewFile();

							logger.debug(Util.join("file:", file, "\n-----------"));


							fos = new FileOutputStream(file);
							fos.write(contents.getBytes("utf-8"));
							fos.flush();
						}catch (Exception e){

							logger.error(Util.join("Excepiton:file:[", file, "]"), e);
							throw e;

						}finally {
							if(fos != null) fos.close();
						}
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
				if (list == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					//comMessage.setResponseObject(tooltip);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * export Help
	 * API ID : REST-E01-SU-02-15-000
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
	@RequestMapping(value = "/help/import/{langId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> importHelp(
			HttpSession httpSession,
			@PathVariable("langId") String langId,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			//--------------------------------------------------
			//실행
			//--------------------------------------------------
			{
				String root = servletContext.getRealPath("/");

				//String root = env.getAttachFilePath();

				File rootDir =  new File(root, Util.join("helpdata-",langId));
				logger.error(Util.join("*** help-data-path:",rootDir.toString()));
				File[] files = rootDir.listFiles();

				if(files == null){
					logger.error(Util.join("*** help data files is null"));
				}else{
					logger.error(Util.join("*** help data files size:", files.length));
				}

				List<Help> list = new ArrayList<Help>();
				for (File file : files){
					FileInputStream fis = null;
					try{

						fis = new FileInputStream(file);
						byte[] b = new byte[(int)file.length()];
						fis.read(b);
						String data = new String(b,"utf-8");
						String[] columns = Util.split(data,"$$$");

						String appId = columns[0];
						String helpId = columns[1];
						//String langId = columns[2];
						String subject = columns[3];
						String contents = columns[4];

						Help help = new Help();
						help.setAppId(appId);
						help.setHelpId(helpId);
						help.setLangId(langId);
						help.setSubject(subject);
						help.setContents(contents);
						help.setRegId(comMessage.getUserId());
						help.setRegDate(Util.getFormatedDate());
						list.add(help);
						logger.debug(Util.join("file:", file));
						logger.debug(Util.join("help:",Util.toJSONString(help)));

						logger.debug("-----------------------------------");
					}catch (Exception e){
						throw e;
					}finally {
						if( fis != null) fis.close();
					}
				}

				helpService.importHelp(langId, list);
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
			}

			return comMessage;
		}
	}

}
