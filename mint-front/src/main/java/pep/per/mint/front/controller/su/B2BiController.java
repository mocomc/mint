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

import pep.per.mint.database.service.su.B2BiService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * @author TA
 */
@Controller
@RequestMapping("/su")
public class B2BiController {

	private static final Logger logger = LoggerFactory.getLogger(B2BiController.class);



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
	B2BiService b2biService;



	/**
	 * <pre>
	 * B2Bi 조회조건 리스트 및 연관 검색
	 * API ID : REST-R01-SU-03-07
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
	@RequestMapping(value = "/b2bi/search-info", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getB2BiSearchInfo(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = b2biService.getB2BiSearchInfo(params);
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
	 * B2Bi Interface MetaData 조회
	 * API ID : REST-R01-IM-01-04-001
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
	@RequestMapping(value = "/b2bi/interface/metadata/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getB2BiInterfaceMetaDataList(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = b2biService.getB2BiInterfaceMetaDataList(params);
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
	 * B2Bi 기간별 조회
	 * API ID : REST-R01-SU-03-07-003
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
	@RequestMapping(value = "/b2bi/period-search", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getB2BiPeriodState(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = b2biService.getB2BiPeriodSearch(params);
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
	 * B2Bi 문서별 현황
	 * API ID : REST-R01-SU-03-07-002
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
	@RequestMapping(value = "/b2bi/state/docnm", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getB2BiStateByDocNm(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = b2biService.getB2BiStateByDocNm(params);
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
	 * B2Bi 현황판.
	 * API ID : REST-R01-SU-03-07-001
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
	@RequestMapping(value = "/b2bi/state/main", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getB2BiStateMain(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = b2biService.getB2BiStateMain(params);
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
	 * B2Bi Interface MetaData 중복 체크 조회
	 * API ID : REST-R02-IM-01-04-001
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
	@RequestMapping(value = "/b2bi/interface/metadata/matching-count", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getMatchingCount(
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

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String [] field = {"companyNm","docNm","bizNm"};
				FieldCheckUtil.checkRequired("B2BiController.insertB2BiInterfaceMetaData", field, params, messageSource, locale);
			}



			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				//화면상에 체크로직 복잡도를 개선하기 위해 서비스에서 제어하도록 수정.
				//중복 체크 항목중에 하나라도 공백이 있으면 skip

				Object companyNm = params.get("companyNm");
				Object docNm     = params.get("docNm");
				Object bizNm     = params.get("bizNm");

				int cnt = 0;
				Map<String,Object> map = new LinkedHashMap<String, Object>();

				if( !Util.isEmpty(companyNm) && !Util.isEmpty(docNm) && !Util.isEmpty(bizNm) ) {
					cnt = b2biService.getMatchingCount(params);
				} else {
					cnt = 0;
				}
				map.put("cnt", String.valueOf(cnt));
				list.add(map);

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
	 * B2Bi Interface MetaData 등록
	 * API ID : REST-C01-IM-01-04-001
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
	@RequestMapping(value = "/b2bi/interface/metadata/insert", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Object> insertB2BiInterfaceMetaData(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, Object> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String, Object> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//String [] field = {"interfaceId","companyNm","docNm","bizNm","direction","protocol"};
				String [] field = {"interfaceId"};
				FieldCheckUtil.checkRequired("B2BiController.insertB2BiInterfaceMetaData", field, params, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("insertB2BiInterfaceMetaData requirement param dump:\n", FieldCheckUtil.jsonDump(params)));

			}

			//----------------------------------------------------------------------------
			// 등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				params.put("regId", regId);
				params.put("regDate", regDate);
				params.put("modId", regId);
				params.put("modDate", regDate);

			}


			try{
				//----------------------------------------------------------------------------
				// Service Call
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = b2biService.insertB2BiInterfaceMetaData(params);
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
					comMessage.setResponseObject(params);

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

			}finally{

			}

		}

	}




	/**
	 * <pre>
	 * B2Bi Interface MetaData 수정
	 * API ID : REST-U01-IM-01-04-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author TA
     * @since version 1.0(2016.08)
     */
	@RequestMapping(value = "/b2bi/interface/metadata/update", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Object> updateB2BiInterfaceMetaData(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, Object> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			Map<String,Object> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//String [] field = {"interfaceId","companyNm","docNm","bizNm","direction","protocol"};
				String [] field = {"interfaceId"};
				FieldCheckUtil.checkRequired("B2BiController.updateB2BiInterfaceMetaData", field, params, messageSource, locale);

			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateB2BiInterfaceMetaData requirement param dump:\n", FieldCheckUtil.jsonDump(params)));
			}


			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				params.put("regId", regId);
				params.put("regDate", regDate);
				params.put("modId", regId);
				params.put("modDate", regDate);
			}
			try{
				//----------------------------------------------------------------------------
				// Service Call
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = b2biService.updateB2BiInterfaceMetaData(params);
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
					comMessage.setResponseObject(params);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}

}
