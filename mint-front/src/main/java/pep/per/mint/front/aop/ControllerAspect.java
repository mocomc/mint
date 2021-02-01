/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.front.aop;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.monitor.FrontLog;
import pep.per.mint.common.exception.RequiredFieldsException;
import pep.per.mint.common.util.RequiredFields;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.service.FrontManagementService;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * 메소드 실행전후 로그처리 Aspect
 * @author Solution TF
 *
 */
@Component
@Aspect
public class ControllerAspect {

	public static String ERR_CD_SYSTEM_DB_CON_FAIL = "2000";

	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	public static boolean detailErrorMsgOn = true;

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	
	
	@Autowired
	FrontManagementService frontManagementService;

	@Autowired
	CommonService commonService;

	@Around(
		"execution(public * pep.per.mint.*.controller.*.*Controller.*(..)) && " +
	    "!execution(public * pep.per.mint.front.controller.ut.*Controller.*(..)) && " +
        "!execution(public * pep.per.mint.front.controller.en.PortalController.getFrontLogList(..)) && " +
        "!execution(public * pep.per.mint.front.controller.op.DeployController.deployInterfaceResponse(..))"
	)
	public Object doSomthingAround(ProceedingJoinPoint joinPoint) {

		Object res = null;
		HttpSession session = null;
		ComMessage<?,?> comMessage = null;
		Locale locale = null;
		HttpServletRequest request = null;
		FrontLog frontLog = new FrontLog();

		try {

			//-----------------------------------------------------------
			//파라메터 GET
			//-----------------------------------------------------------
			{
				Object [] args = joinPoint.getArgs();
				for (Object object : args) {
					if(object instanceof HttpSession) session = (HttpSession) object;
					else if(object instanceof ComMessage) comMessage = (ComMessage<?,?>) object;
					else if(object instanceof HttpServletRequest) request = (HttpServletRequest) object;
				}
			}

			//-----------------------------------------------------------
			//Cross-Site Scripting Contents Checking
			//-----------------------------------------------------------
			{
				checkXSS(request, comMessage, locale);
			}

			//-----------------------------------------------------------
			//ComMessage 체크
			//-----------------------------------------------------------
			{
				checkCommessage(joinPoint, comMessage, locale);
			}



			//-----------------------------------------------------------
			//세션체크
			//-----------------------------------------------------------
			//login 서비스 호출 등과 같은 로그인 전에도 접근가능한 서비스를 제외하고는
			//로그인 여부를 체크하기 위해 세션값을 체크하여 접근유효성을 확인한다.
			//-----------------------------------------------------------
			{
				if (!Util.isEmpty(comMessage) && comMessage.getCheckSession()) {
					checkSession(joinPoint, session, comMessage, locale);
					logger.debug(Util.join("comMessage.userId:", comMessage.getUserId(), ", checkSession:", comMessage.getCheckSession()));
				}else{
					logger.debug(Util.join("checkSession is false so skip to check session."));
				}

			}
			//-----------------------------------------------------------
			//로그 before proceeding
			//-----------------------------------------------------------
			{
				logBeforeProceeding(joinPoint, session, locale, comMessage, request);
			}
			//-----------------------------------------------------------
			//proceed target
			//-----------------------------------------------------------
			{
				res = joinPoint.proceed();
			}
			//-----------------------------------------------------------
			//로그 after proceeding
			//-----------------------------------------------------------
			{
				logAfterProceeding(joinPoint, session, locale, (ComMessage)res, request);
			}

			//-----------------------------------------------------------
			//프론트 로그 처리
			//-----------------------------------------------------------
			{
				frontManagementService.saveFrontLog(joinPoint, frontLog , comMessage, (ComMessage)res, request);
			}
			return res;

		} catch (ControllerException e) {
			logger.error(Util.join("exception occur: when procceding ", joinPoint.getSignature().toLongString()), e);
			checkDetailErrorOn();
			if (comMessage != null) {
				//-----------------------------------------------------------
				//front에 예외전달
				//-----------------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(e.getErrorCd());

					String errorMsg = e.getMessage();
					comMessage.setErrorMsg((errorMsg));
					String errorDetail = e.getStackTraceMsg();
					comMessage.setErrorDetail((errorDetail));
				}
				//-----------------------------------------------------------
				//프론트 로그 처리
				//-----------------------------------------------------------
				{
					frontManagementService.saveFrontLog(joinPoint, frontLog, comMessage, comMessage, request);
				}

				if(!detailErrorMsgOn) {
					comMessage.setErrorMsg(MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.common", null, locale));
					comMessage.setErrorDetail("");
				}

				return comMessage;
			} else {
				//-----------------------------------------------------------
				//comMessage를 통해 전달된 예외가 아니면 시스템에러페이지로 리다이렉션시킨다.
				//-----------------------------------------------------------
				Map<String, Object> model = new HashMap<String, Object>();
				{
					model.put("errorCd", e.getErrorCd());
					model.put("errorMsg", e.getMessage());
					String errorDetail = e.getStackTraceMsg();
					model.put("errorDetail", (errorDetail));
				}

				//-----------------------------------------------------------
				//프론트 로그 처리
				//-----------------------------------------------------------
				{
					frontManagementService.saveFrontLog(joinPoint, frontLog, comMessage, comMessage, request);
				}

				if(!detailErrorMsgOn) {
					model.put("errorMsg", MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.common", null, locale));
					model.put("errorDetail", "");
				}

				return new ModelAndView("errorpage", model);
			}
		} catch (Throwable e){

			checkDetailErrorOn();
			logger.error(Util.join("exception occur: when procceding  ", joinPoint.getSignature().toLongString()),e);

			String errorCd = null;
			String errorMsg= "";
			String errorMsgId = "error.msg.system.common";

			Throwable cause = e.getCause();

			if(cause != null) {
				errorMsgId = "error.msg.system.db.con.fail";
				if (cause instanceof CannotGetJdbcConnectionException) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.db.con.fail", locale);
					Object[] params = {joinPoint.getSignature().toShortString(), e != null ? e.getMessage() : ""};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, errorMsgId, params, locale);
				}else {
					int checkMaxCauseCnt = 10;
					for (int i = 0; i < checkMaxCauseCnt; i++) {
						cause = cause.getCause();
						if (cause == null) break;
						if (cause instanceof CannotGetJdbcConnectionException) {
							errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.db.con.fail", locale);
							Object[] params = {joinPoint.getSignature().toShortString(), e != null ? e.getMessage() : ""};
							errorMsg = MessageSourceUtil.getErrorMsg(messageSource, errorMsgId, params, locale);
							break;
						}
					}
				}
			}


			if(errorCd == null){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
				Object[] params = {joinPoint.getSignature().toShortString(), e!= null ? e.getMessage() : "" };
				errorMsgId = "error.msg.system.unexpected";
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, errorMsgId, params, locale);
			}

			String errorDetail = "";
			PrintWriter pw = null;
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pw = new PrintWriter(baos);
				e.printStackTrace(pw);
				pw.flush();
				if(pw != null)  errorDetail = baos.toString();
			}finally{
				if(pw != null) pw.close();
			}
			if(comMessage != null){
				//-----------------------------------------------------------
				//front에 예외전달
				//-----------------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					comMessage.setErrorDetail((errorDetail));
				}
				//-----------------------------------------------------------
				//프론트 로그 처리
				//-----------------------------------------------------------
				{
					if(errorCd != null && !errorCd.equals(ERR_CD_SYSTEM_DB_CON_FAIL)) frontManagementService.saveFrontLog(joinPoint, frontLog , comMessage, comMessage, request);
				}

				if(!detailErrorMsgOn) {
					Object[] params = {"", ""};
					comMessage.setErrorMsg(MessageSourceUtil.getErrorMsg(messageSource, errorMsgId, params, locale));
					comMessage.setErrorDetail("");
				}

				return comMessage;
			}else{
				//-----------------------------------------------------------
				//comMessage를 통해 전달된 예외가 아니면 시스템에러페이지로 리다이렉션시킨다.
				//-----------------------------------------------------------
				Map<String, Object> model = new HashMap<String, Object>();
				{
					model.put("errorCd", errorCd);
					model.put("errorMsg", errorMsg);
					model.put("errorDetail", errorDetail);

				}
				//-----------------------------------------------------------
				//프론트 로그 처리
				//-----------------------------------------------------------
				{
					if(errorCd != null && !errorCd.equals(ERR_CD_SYSTEM_DB_CON_FAIL)) frontManagementService.saveFrontLog(joinPoint, frontLog , comMessage, comMessage, request);
				}

				if(!detailErrorMsgOn) {
					Object[] params = {"", ""};
					model.put("errorMsg", MessageSourceUtil.getErrorMsg(messageSource, errorMsgId, params, locale));
					model.put("errorDetail", "");
				}

				return new ModelAndView("errorpage",model);
			}


		} finally{

		}


	}

	private void checkDetailErrorOn() {
        try {
        	Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
            List<String> errorMsgOnValues = environmentalValues.get("system.detail.errorMsg.on");
            if (errorMsgOnValues == null || errorMsgOnValues.isEmpty()) {
            	detailErrorMsgOn = true;
            }else {
            	detailErrorMsgOn = Boolean.parseBoolean(errorMsgOnValues.get(0));
            }
        } catch (Exception e) {
        	detailErrorMsgOn = true;
        }
	}

	private void checkXSS(HttpServletRequest request, ComMessage<?, ?> comMessage, Locale locale) throws ControllerException {
		// TODO Auto-generated method stub
		//uri 따라 분기 처리할 부분이 있으면 사용하라.
		/*String uri = request.getRequestURI();
		logger.info("request.getRequestURI():" + uri);

		Object reqObj = comMessage.getRequestObject();
		if(reqObj instanceof CacheableObject){
			String objectType = ((CacheableObject)reqObj).getObjectType();
		}else if(reqObj instanceof Map){

		}else{

		}*/
		if(comMessage == null || comMessage.getRequestObject() == null) return;
		String str = Util.toJSONString(comMessage.getRequestObject());
		FieldCheckUtil.checkIncludeNotPermitScript(str, messageSource, locale);

		if(request == null) return;
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = (String)paramNames.nextElement();
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				FieldCheckUtil.checkIncludeNotPermitScript(value, messageSource, locale);
			}
		}
	}

	/**
	 *
	 * @param joinPoint
	 * @param comMessage
	 * @param locale
	 *
	 */
	private void checkCommessage(ProceedingJoinPoint joinPoint, ComMessage<?,?> comMessage, Locale locale) throws ControllerException {



		if(comMessage == null){
			String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] params = {joinPoint.getSignature().toShortString(), "ComMessage" };
			String errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		try {

			RequiredFields.check(comMessage, "userId", "startTime");


		} catch (RequiredFieldsException e){
			String failFieldName = e.getCheckFailField();
			String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] params = {joinPoint.getSignature().toShortString(), Util.join("ComMessage.",failFieldName) };
			String errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
			throw new ControllerException(errorCd, errorMsg);

		} catch (Exception e) {

			String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
			Object[] params = {joinPoint.getSignature().toShortString(), "ComMessage 체크중 예상치못한 예외 발생" };
			String errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		}



	}

	/**
	 * @param joinPoint
	 * @param session
	 * @param locale
	 * @param comMessage
	 */
	private void logAfterProceeding(ProceedingJoinPoint joinPoint,
			HttpSession session, Locale locale, ComMessage<?,?> comMessage, HttpServletRequest request) {
		logger.debug(Util.join("after procceding:",joinPoint.getSignature().toShortString()));
	}


	/**
	 * @param joinPoint
	 * @param session
	 * @param locale
	 * @param comMessage
	 */
	private void logBeforeProceeding(ProceedingJoinPoint joinPoint,
			HttpSession session, Locale locale, ComMessage<?,?> comMessage, HttpServletRequest request) {
		logger.debug(Util.join("before procceding:",joinPoint.getSignature().toShortString()));
	}


	/**
	 * @param joinPoint
	 * @param session
	 * @param comMessage
	 */
	private void checkSession(ProceedingJoinPoint joinPoint, HttpSession session, ComMessage<?,?> comMessage, Locale locale) throws ControllerException {


		if((joinPoint.toShortString()).contains("login")) {
			logger.debug( Util.join("login 서비스에서는 세션 체크를 skip한다.") );
			return;
		}
		// 아시아나 보완점검 사항으로 서비스 추가
		if((joinPoint.toShortString()).contains("checkChangePassword")) {
			logger.debug( Util.join("checkChangePassword 서비스에서 세션 체크를 skip한다.") );
			return;
		}
		// 아시아나 보완점검 사항으로 서비스 추가
		if((joinPoint.toShortString()).contains("changePassword")) {
			logger.debug( Util.join("changePassword 서비스에서 세션 체크를 skip한다.") );
			return;
		}

		if(session != null) {
			String sessionInfo = Util.join(
					"\nsession id:",session.getId(),
					"\nCreationTime:", new Date(session.getCreationTime()),
					"\nLastAccessedTime:", new Date(session.getLastAccessedTime()),
					"\nMaxInactiveInterval(sec):", session.getMaxInactiveInterval()
			);

			User user = (User)session.getAttribute("user");
			if(user == null) {
				String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.session.timeout", locale);
				Object[] params = {joinPoint.getSignature().toShortString(), sessionInfo };
				String errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.session.timeout", params, locale);
				logger.debug( sessionInfo );
				throw new ControllerException(errorCd, errorMsg);
			} else {
				comMessage.setUserId(user.getUserId());
			}
			//-----------------------------------------------------------
			// Append - 2019.01.31
			// - 중복로그인여부는 Login 시점에 체크하며, 중복 발생시 최초 한번만 Alert 되도록
			// - 세션에 담긴 중복로그인정보를 초기화 한다
			// TODO
			// - 파라메터 이름 수정
			//-----------------------------------------------------------
			{
				session.setAttribute("duplicationDetect", false);
				session.setAttribute("duplicationLoginHistory", null);
			}


			logger.debug( Util.join(sessionInfo,"\nuser id:",user.getUserId()) );
		}else{
			String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.service.required.param.none", locale);
			Object[] params = {joinPoint.getSignature().toShortString(), "서비스함수가 HttpSession을 파라미터로 가지지 않고 있음. 개발자에게 추가요청 할것!" };
			String errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.service.required.param.none", params, locale);
			logger.debug( Util.join("Controller " , joinPoint.toShortString(), " HttpSession을 파라미터로 가지지 않고 있음. 개발자에게 추가요청할것!") );
			throw new ControllerException(errorCd, errorMsg);
		}
	}



}
