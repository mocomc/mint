package pep.per.mint.front.controller.co;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.LoginInfo;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.co.SecurityService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

@Controller
@RequestMapping("/co/security")
public class SecurityController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SecurityService securityService;

	@Autowired
	CommonService commonService;

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	/**
	 * <pre>
	 * 패스워드 변경
	 *
	 * API ID : REST-U01-CO-04-03
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author psp
     * @since 2018.10.30
     */
	@RequestMapping(
			value="/passwds",
			params="method=PUT",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	@Transactional
	public @ResponseBody ComMessage<Map, Map> changePassword(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, Map> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		String errorCd = "";
		String errorMsg ="";

		Map<String, String> params = comMessage.getRequestObject();
		if(params == null) params = new HashMap();

		//----------------------------------------------------------------------------
		// 입력값 JSON 덤프
		//----------------------------------------------------------------------------
		{
			logger.debug(Util.join("SecurityController.changePassword param dump:\n", FieldCheckUtil.jsonDump(params)));
		}

		try {

			String userId   = params.get("userId");
			String value    = params.get("value");
			String newValue = params.get("newValue");
			String conValue = params.get("conValue");

			//패스워드 정규식 비교용 변수
			String originalNewValue = params.get("newValue");

			if( FrontEnvironments.passwordEncrypt ) {
				value    = securityService.getHashSHA256(value, "".getBytes());
				newValue = securityService.getHashSHA256(newValue, "".getBytes());
				conValue = securityService.getHashSHA256(conValue, "".getBytes());

				logger.error("value:"+value);
				logger.error("newValue:"+newValue);
				logger.error("conValue:"+conValue);
				logger.error("originalNewValue:"+originalNewValue);

				params.put("value", value);
				params.put("newValue", newValue);
			}

			//----------------------------------------------------------------------------
			//TODO: error Code 및 message 정의하여 반영할것(박성필)
			//----------------------------------------------------------------------------

			//----------------------------------------------------------------------------
			//패스워드 변경 정책
			//----------------------------------------------------------------------------
			User user = commonService.getUser(userId, true);

			//----------------------------------------------------------------------------
			//기존 패스워드 유효성 체크
			//----------------------------------------------------------------------------
			if( !value.equals(user.getPassword()) ) {
				logger.error("비밀번호 변경 실패 : 기존 비밀번호 불일치");

				String errorDesc = "기존 비밀번호 불일치";
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				return comMessage;
			}

			//----------------------------------------------------------------------------
			//새 패스워드가 비어 있는지 체크 (가능하면 화면에서 처리할것)
			// - 암호화되지 않은 평문으로 체크
			//----------------------------------------------------------------------------
			if( Util.isEmpty(originalNewValue) ) {
				logger.error("비밀번호 변경 실패: 새로운 비밀번호 없음");

				String errorDesc = "새로운 비밀번호 없음";
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				return comMessage;
			}

			//----------------------------------------------------------------------------
			//새 패스워드와 패스워드 확인이 일치하는지 체크(가능하면 화면에서 처리할것)
			//----------------------------------------------------------------------------
			if( !newValue.equals(conValue) ) {
				logger.error("비밀번호 변경 실패: 비밀번호 확인이 일치하지 않음");

				String errorDesc = "비밀번호 확인이 일치하지 않음";
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				comMessage.setRequestObject(params);
				return comMessage;
			}

			//----------------------------------------------------------------------------
			//패스워드 정규식 체크 (영문, 숫자, 특수문자 포함 8자 이상 기준)
			// - 암호화되지 않은 평문으로 체크
			//----------------------------------------------------------------------------
			Pattern p = Pattern.compile(FrontEnvironments.passwordRgularex);
			Matcher m = p.matcher(originalNewValue);

			if( !m.matches() ) {
				logger.error("비밀번호 변경 실패: "+FrontEnvironments.passwordRgularexMsg);

				String errorDesc = FrontEnvironments.passwordRgularexMsg;
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				comMessage.setRequestObject(params);
				return comMessage;
			}

			//----------------------------------------------------------------------------
			//직전 비밀번호 재사용 여부 체크
			//----------------------------------------------------------------------------
			if( newValue.equals(user.getPassword()) ) {
				logger.error("비밀번호 변경 실패: 직전 비밀번호 사용 금지");

				String errorDesc = "직전 비밀번호 사용 금지";
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				comMessage.setRequestObject(params);
				return comMessage;
			}

			//----------------------------------------------------------------------------
			//패스워드 변경
			//----------------------------------------------------------------------------
			int resultCd = securityService.changePassword(params);

			if( resultCd != 1 ) {
				logger.error("비밀번호 변경 실패 : 처리 오류");

				String errorDesc = "처리 오류";
				Object[] errorMsgParams = {errorDesc};
				errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);

				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				return comMessage;
			} else {
				//----------------------------------------------------------------------------
				//패스워드 변경 이력
				//----------------------------------------------------------------------------
				//securityService.insertPasswordHistory(params);
			}

			logger.info("비밀번호 변경 완료");
			errorCd  = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);

			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			comMessage.setRequestObject(params);

			return comMessage;
		} catch( Throwable e ) {
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
			String errorDetail = e.getMessage();
			String[] errorMsgParams = {"SecurityController.changePassword",errorDetail};
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		} finally {
			//-------------------------------------------------------------------------------------------
			// subject  : 보안 코드 추가
			// date     : 20190130
			// contents :
			// 요청시 암호화된 패스워드가 응답시 복호화되어 돌려보내는 부분을 막기위해 requestObject 를 null 처리함
			//-------------------------------------------------------------------------------------------
			comMessage.setRequestObject(null);
			//-------------------------------------------------------------------------------------------
		}
	}


	/**
	 * <pre>
	 * 패스워드 변경 필요 여부 체크
	 *
	 * API ID : REST-R02-CO-04-03
	 * </pre>
	 * @param httpSession 세션
	 * @param userId 해당 유저 Id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author psp
     * @since 2018.10.30
     * @deprecated CommonController.login 서비스에서 로직 처리함
     */
	@RequestMapping(value="/passwds/check/{userId}", params="method=GET", method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, Map> checkChangePassword(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, Map> comMessage,
			Locale locale,
			HttpServletRequest request
		) throws Exception, ControllerException {

		String errorCd = "";
		String errorMsg = "";

		//패스워드 변경 체크를 하는지 여부 환경설정 조회
		boolean passwordCheckYn = FrontEnvironments.passwordCheckYn;
		LoginInfo loginInfo = comMessage.getRequestObject();
		Map params = new HashMap();
		String userId = loginInfo.getUserId();

		logger.debug("패스워드 변경 체크 여부 : "+ FrontEnvironments.passwordCheckYn);
		logger.debug("패스워드 변경 체크 기간 : "+ FrontEnvironments.passwordInterval+"일");
		try {
			//-----------------------------------------------
			//패스워드 체크시 로직
			//-----------------------------------------------
			if (passwordCheckYn){

				User user = commonService.getUser(userId, true);
				String pswd = loginInfo.getPassword();

				//포탈 환경설정의 패스워드 변경 기간 값
				int passwordInterval = FrontEnvironments.passwordInterval;
				int failCheckCountEnv = FrontEnvironments.failCheckCount;

				//(아시아나) 패스워드 암호화
				if(FrontEnvironments.passwordEncrypt){
					pswd = securityService.getHashSHA256(pswd, "".getBytes());
				}
				//-----------------------------------------------
				//사용자 정보가 유효하면 패스워드 유효기간 체크
				//-----------------------------------------------
				if(user != null && user.getPassword().equals(pswd)){
					Calendar today = Calendar.getInstance();
					Calendar modDate = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					//패스워드 변경 이력 가져오는 메서드
					List<Map> changePasswordList = securityService.checkChangePassword(userId);

					if(changePasswordList != null && changePasswordList.size() > 0){
						//가장 최근 패스워드 변경 이력
						Map latestPasswordChange = changePasswordList.get(0);
						modDate.setTime(sdf.parse(latestPasswordChange.get("modDate").toString()));

						//패스워드 유효 기한이 지났을 경우 변경 요구
						if((today.getTimeInMillis()-modDate.getTimeInMillis())/(1000*3600*24) > passwordInterval){
							params.put("passwordChangeRequired", true);
							comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
							comMessage.setResponseObject(params);
							return comMessage;
						}
					} else {
						//패스워드 변경 이력이 없는 경우 변경 요구
						params.put("passwordChangeRequired", true);
						comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
						comMessage.setResponseObject(params);
						return comMessage;
					}
				}
			}

			//패스워드 변경 체크를 하지 않는 경우 변경 없음
			params.put("passwordChangeRequired", false);

			logger.debug("패스워드 변경 체크 하지 않음");
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			comMessage.setResponseObject(params);
			return comMessage;
		} catch( Throwable e ) {
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
			String errorDetail = e.getMessage();
			String[] errorMsgParams = {"SecurityController.checkChangePassword",errorDetail};
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		} finally {
			//-------------------------------------------------------------------------------------------
			// subject  : 보안 코드 추가
			// date     : 20190130
			// contents :
			// 요청시 암호화된 패스워드가 응답시 복호화되어 돌려보내는 부분을 막기위해 requestObject 를 null 처리함
			//-------------------------------------------------------------------------------------------
			comMessage.setRequestObject(null);
			//-------------------------------------------------------------------------------------------
		}

	}
/*
	@RequestMapping(value="/passwds/check/{userId}", params="method=GET", method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, Map> checkChangePassword(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, Map> comMessage,
			Locale locale,
			HttpServletRequest request
		) throws Exception, ControllerException {

		String errorCd = "";
		String errorMsg = "";

		//패스워드 변경 체크를 하는지 여부 환경설정 조회
		boolean passwordCheckYn = Environments.passwordCheckYn;
		LoginInfo loginInfo = comMessage.getRequestObject();
		Map params = new HashMap();
		String userId = loginInfo.getUserId();

		logger.debug("패스워드 변경 체크 여부 : "+ Environments.passwordCheckYn);
		logger.debug("패스워드 변경 체크 기간 : "+ Environments.passwordInterval+"일");
		try {
			//패스워드 체크시 로직
			if (passwordCheckYn){

				User user = commonService.getUser(userId);
				String pswd = loginInfo.getPassword();

				//포탈 환경설정의 패스워드 변경 기간 값
				int passwordInterval = Environments.passwordInterval;
				int failCheckCountEnv = Environments.failCheckCount;

				//(아시아나) 패스워드 암호화
				if(Environments.passwordEncrypt){
					pswd = securityService.getHashSHA256(pswd, "".getBytes());
				}

				if(user != null && user.getPassword().equals(pswd)){

					Calendar today = Calendar.getInstance();
					Calendar modDate = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					//패스워드 변경 이력 가져오는 메서드
					List<Map> changePasswordList = securityService.checkChangePassword(userId);

					if(changePasswordList != null && changePasswordList.size() > 0){

						//가장 최근 패스워드 변경 이력
						Map latestPasswordChange = changePasswordList.get(0);
						modDate.setTime(sdf.parse(latestPasswordChange.get("modDate").toString()));

						//패스워드 유효 기한이 지났을 경우 변경 요구
						if((today.getTimeInMillis()-modDate.getTimeInMillis())/(1000*3600*24) > passwordInterval){

							params.put("passwordChangeRequired", true);

							//패스워드 이력 필요할 때...
		//					List recentPassword = new ArrayList();
		//					int listIndex = 0;
		//
		//					if(changePasswordList.size()>5){ listIndex = 4;
		//						} else { listIndex = changePasswordList.size(); }
		//
		//					for(int i = listIndex; i==0; i--){
		//						recentPassword.add(changePasswordList.get(i).get("password"));
		//					}
		//
		//					params.put("recentPassword", recentPassword);

							comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
							comMessage.setResponseObject(params);
							return comMessage;
						}

					} else {
						//패스워드 변경 이력이 없는 경우 변경 요구
						params.put("passwordChangeRequired", true);

						comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
						comMessage.setResponseObject(params);
						return comMessage;
					}

				} else {
					logger.debug("아이디 패스워드가 일치하지 않습니다.");
					params.put("passwordChangeRequired", false);

					if( user == null ) {
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.userId", locale);
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.userId", null, locale);
						comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
						comMessage.setErrorCd(errorCd);
						comMessage.setErrorMsg(errorMsg);
						return comMessage;
					}

					//---------------------------------------------
					//Account Lock 관련
					//---------------------------------------------
					if(user != null && Environments.passwordFailCheck){
						UserSecurityProperty userSecurityProperty =
									securityService.getUserSecurity(user.getUserId());

						if(userSecurityProperty!=null && userSecurityProperty.getUserId()!=""){

							logger.debug("패스워드 실패 체크 +1");
							int failCnt=userSecurityProperty.getPsFailCount()+1;

							userSecurityProperty.setPsFailCnt(failCnt);

							Object[] errorMsgParams = {failCnt, failCheckCountEnv};
							errorCd=MessageSourceUtil.getErrorCd(messageSource, "error.cd.change.password.fail", locale);
							errorMsg=MessageSourceUtil.getErrorMsg(messageSource, "error.msg.change.password.fail", errorMsgParams, locale);
							errorCd = "995";
							errorMsg = failCnt+"회 입력 실패 하셨습니다. "+failCheckCountEnv+"회 이상 실패시 계정이 잠깁니다.";

							if(failCnt==failCheckCountEnv){
								userSecurityProperty.setIsAccountLock("Y");

								errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.account.locked", locale);
								errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.account.locked", null, locale);
							}else if(failCnt>failCheckCountEnv){
								errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.account.locked", locale);
								errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.account.locked", null, locale);
							}

							securityService.updatePasswordCheckFail(userSecurityProperty);

							logger.debug(errorMsg);
							comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
							comMessage.setErrorCd(errorCd);
							comMessage.setErrorMsg(errorMsg);

							comMessage.setResponseObject(params);
							return comMessage;
						} else {
							//TOP0205에 해당 userId가 없으면 새로 입력
							logger.debug("top0205에 해당 데이터 없음");

							userSecurityProperty = new UserSecurityProperty();

							userSecurityProperty.setUserId(userId);
							userSecurityProperty.setIpList("-");
							userSecurityProperty.setLastLoginDate("-");
							userSecurityProperty.setLastLoginIp("-");

							securityService.insertPasswordCheckFail(userSecurityProperty);

							logger.debug("top0205에 해당 데이터 입력");
						}
					}
				}
			}

			//패스워드 변경 체크를 하지 않는 경우 변경 없음
			params.put("passwordChangeRequired", false);

			logger.debug("패스워드 변경 체크 하지 않음");
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			comMessage.setResponseObject(params);
			return comMessage;
		} catch( Throwable e ) {
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
			String errorDetail = e.getMessage();
			String[] errorMsgParams = {"SecurityController.checkChangePassword",errorDetail};
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		} finally {
			//-------------------------------------------------------------------------------------------
			// subject  : 보안 코드 추가
			// date     : 20190130
			// contents :
			// 요청시 암호화된 패스워드가 응답시 복호화되어 돌려보내는 부분을 막기위해 requestObject 를 null 처리함
			//-------------------------------------------------------------------------------------------
			comMessage.setRequestObject(null);
			//-------------------------------------------------------------------------------------------
		}

	}
*/


	/**
	 * <pre>
	 * 이전 로그인 정보 출력 위해 직전 로그인 히스토리 조회
	 *
	 * API ID : REST-R01-CO-04-03
	 * </pre>
	 * @param httpSession 세션
	 * @param userId 해당 유저 Id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author psp
     * @since 2018.10.30
     */
	@RequestMapping(
			value="/login/histories/{userId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, LoginHistory> getLoginHistory(
			HttpSession  httpSession,
			@PathVariable("userId") String userId,
			@RequestBody ComMessage<Map, LoginHistory> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		String errorCd = "";
		String errorMsg = "";

		LoginHistory loginHistory = securityService.getLoginHistory(userId);

		//-----------------------------------------------
		//서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//-----------------------------------------------
		comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

		//-----------------------------------------------
		//통신메시지에 처리결과 코드/메시지를 등록한다.
		//-----------------------------------------------
		if (loginHistory == null) {// 결과가 없을 경우 비지니스 예외 처리
			errorCd=MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
			errorMsg=MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
		} else {// 성공 처리결과
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
			comMessage.setResponseObject(loginHistory);
		}
		comMessage.setErrorCd(errorCd);
		comMessage.setErrorMsg(errorMsg);

		return comMessage;

	}
}
