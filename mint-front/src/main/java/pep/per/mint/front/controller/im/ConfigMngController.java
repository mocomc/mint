/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.front.controller.im;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.im.ConfigMngService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <pre>
 * pep.per.mint.front.controller.im
 * ConfigMngController.java
 * </pre>
 * @author whoana
 * @date Nov 1, 2018
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigMngController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigMngController.class);

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	@Autowired
	ConfigMngService configMngService;



	@Autowired
	CommonService commonService;


	@RequestMapping(
			value="/nh/config",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<ConfigInfo, ?> createConfig(
			HttpSession  httpSession,
			@RequestBody ComMessage<ConfigInfo, ?> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		ConfigInfo info = comMessage.getRequestObject();
		if(info == null) {
			throw new Exception(Util.join("인터페이스Config정보등록 서비스 요청에 RequestObject<ConfigInfo> 값이 누락되었음."));
		}
		//List<System> sysList = commonService.getSystemOfServer(info.getServerId());
		//if(sysList.size()<=0){
		//	throw new Exception(Util.join("인터페이스Config정보. System to Server mapping invaild."));
		//}else{
		//	System system = sysList.get(0);
		//	info.setSystemId(system.getSystemId());
			int res = configMngService.createConfig(info);
		//}

		String errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
		String errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
		comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		return comMessage;
	}

	@RequestMapping(
			value="/nh/config",
			params="method=PUT",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<ConfigInfo, ?> updateConfig(
			HttpSession  httpSession,
			@RequestBody ComMessage<ConfigInfo, ?> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		ConfigInfo info = comMessage.getRequestObject();
		if(info == null) {
			throw new Exception(Util.join("인터페이스Config정보수정 서비스 요청에 RequestObject<ConfigInfo> 값이 누락되었음."));
		}
		int res = configMngService.updateConfig(info);
		String errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
		String errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
		comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		return comMessage;
	}

	@RequestMapping(
			value="/nh/config",
			params="method=DELETE",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<ConfigInfo, ?> deleteConfig(
			HttpSession  httpSession,
			@RequestBody ComMessage<ConfigInfo, ?> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		ConfigInfo info = comMessage.getRequestObject();
		if(info == null) {
			throw new Exception(Util.join("인터페이스Config정보삭제 서비스 요청에 RequestObject<ConfigInfo> 값이 누락되었음."));
		}
		int res = configMngService.deleteConfig(info);
		String errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
		String errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
		comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		return comMessage;
	}


	@RequestMapping(
			value="/nh/config",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ConfigInfo>> getConfigList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ConfigInfo>> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;
			List list = null;
			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}

			}

			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			list = configMngService.getConfigList(params);

						//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}

			//-----------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
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

	@RequestMapping(
			value="/nh/config/compare",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<ConfigInfo, Map> getCompareList(
			HttpSession  httpSession,
			@RequestBody ComMessage<ConfigInfo, Map> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			ConfigInfo params = null;
			Map res = null;
			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				params = comMessage.getRequestObject();
				if(params == null) throw new Exception("필수값 ConfigInfo을 전달되지 않았음.");

			}

			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			res = configMngService.getCompareList(params);

						//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}

			//-----------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
			{
				if (res == null || res.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(res);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



}
