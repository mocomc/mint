/**
 * 
 */
package pep.per.mint.front.controller.an;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.UserManagementService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/an")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

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
	 * The User management service.
	 */
	@Autowired
	UserManagementService userManagementService;

	/**
	 * <pre>
	 * Interface User list 조회
	 * API ID : REST-R01-AN-02-19-000
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
	@RequestMapping(value = "/interface-users", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Map>> getInterfaceUserList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
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
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				list = userManagementService.getInterfaceUserList(params);
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
	 * Interface list 조회
	 * API ID : REST-R02-AN-02-19-000
	 * </pre>
	 *
	 * @param userId the user id
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
	@RequestMapping(value = "/interface-users/{userId}/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Map>> getInterfaceList(
			@PathVariable("userId") String userId,
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
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
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				params.put("userId", userId);
				list = userManagementService.getInterfaceList(params);
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
	 * 인터페이스 담당자 업무 이관
	 * API ID : REST-U01-AN-02-19-000
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
	@RequestMapping(value = "/interface-users/{userId}/interfaces/move", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<Map>, ?> moveInterfaceUser(
			HttpSession httpSession,
			@RequestBody ComMessage<List<Map>, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			try{

				//----------------------------------------------------------------------------
				//필드체크
				//----------------------------------------------------------------------------
				{


				}

				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					List<Map> list = comMessage.getRequestObject();
					res = userManagementService.moveInterfaceUser(list);
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
					//comMessage.setResponseObject(requirement);

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
	 * Interface list 조회(인터페이스 기준)
	 * API ID : REST-R03-AN-02-19-000
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
	@RequestMapping(value = "/interface-users/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Map>> getInterfaceListForUserManagement(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
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
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();

				list = userManagementService.getInterfaceListForUserManagement(params);
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
	 * 인터페이스 담당자 업무 일괄 추가
	 * API ID : REST-U02-AN-02-19-000
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
	@RequestMapping(value = "/interface-users/add", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, ?> addInterfaceUsers(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			try{


				//----------------------------------------------------------------------------
				//필드체크
				//----------------------------------------------------------------------------
				{


				}

				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					Map params = comMessage.getRequestObject();



					//----------------------------------------------------------------------------
					// 입력값 JSON 덤프
					//----------------------------------------------------------------------------
					{
						logger.debug(Util.join("createRequirement getRequestObject dump:\n", FieldCheckUtil.jsonDump(params)));

					}




					if(params == null) {
						//예외처리
					}


					List<Map> interfaceList = (List<Map>) params.get("interfaceList");
					List<Map> relUserList = (List<Map>) params.get("relUserList");

					List<Map> interfaceUserList = new ArrayList<Map>();
					for(int i = 0 ; i < interfaceList.size() ; i ++){

						String interfaceId = (String)interfaceList.get(i).get("interfaceId");

						for(int j  = 0 ; j < relUserList.size() ; j ++){
							String userId = (String) relUserList.get(j).get("userId");
							String roleType = (String) relUserList.get(j).get("roleType");
							String systemId = (String) relUserList.get(j).get("systemId");
							String regDate = (String) relUserList.get(j).get("regDate");
							String regId = (String) relUserList.get(j).get("regId");

							Map interfaceUser = new HashMap();
							interfaceUser.put("interfaceId",interfaceId);
							interfaceUser.put("newUserId",userId);
							interfaceUser.put("roleType",roleType);
							interfaceUser.put("systemId",systemId);
							interfaceUser.put("regDate",regDate);
							interfaceUser.put("regId",regId);
							interfaceUserList.add(interfaceUser);
						}

					}

					res = userManagementService.addInterfaceUsers(interfaceUserList);
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
					//comMessage.setResponseObject(requirement);

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
	 * 인터페이스 담당자 업무 일괄 삭제
	 * API ID : REST-U03-AN-02-19-000
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
	@RequestMapping(value = "/interface-users/delete", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, ?> deleteInterfaceUsers(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			try{

				//----------------------------------------------------------------------------
				//필드체크
				//----------------------------------------------------------------------------
				{


				}

				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					Map params = comMessage.getRequestObject();

					//----------------------------------------------------------------------------
					// 입력값 JSON 덤프
					//----------------------------------------------------------------------------
					{
						logger.debug(Util.join("createRequirement getRequestObject dump:\n", FieldCheckUtil.jsonDump(params)));

					}



					if(params == null) {
						//예외처리
					}


					List<Map> interfaceList = (List<Map>) params.get("interfaceList");
					List<Map> relUserList = (List<Map>) params.get("relUserList");

					List<Map> interfaceUserList = new ArrayList<Map>();
					for(int i = 0 ; i < interfaceList.size() ; i ++){

						String interfaceId = (String)interfaceList.get(i).get("interfaceId");

						for(int j  = 0 ; j < relUserList.size() ; j ++){
							String userId = (String) relUserList.get(j).get("userId");
							String roleType = (String) relUserList.get(j).get("roleType");
							String regDate = (String) relUserList.get(j).get("regDate");
							String regId = (String) relUserList.get(j).get("regId");

							Map interfaceUser = new HashMap();
							interfaceUser.put("interfaceId",interfaceId);
							interfaceUser.put("userId",userId);
							interfaceUser.put("roleType",roleType);
							interfaceUser.put("regDate",regDate);
							interfaceUser.put("regId",regId);
							interfaceUserList.add(interfaceUser);
						}

					}

					res = userManagementService.deleteInterfaceUsers(interfaceUserList);
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
					//comMessage.setResponseObject(requirement);

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



}
