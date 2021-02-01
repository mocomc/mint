/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.front.controller.rt;

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
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeCode;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.AppType;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModelHistory;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.rt.ModelService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * @author whoana
 * @since Jul 9, 2020
 */
@Controller
@RequestMapping("/rt")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModelController {

	private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	private ServletContext servletContext;


	@Autowired
	ModelService modelService;

	/**
	 * <pre>
	 * APP모델 속성 맵 전체 조회
	 * 응답으로 돌려주는 데이터 구성은 APP유형을 키값으로 한 맵 데이터임.
	 * 	Map<"APP유형", List<AppModelAttributeId>>
	 *  "APP유형" 은 공통코드 RT01 값
	 * </pre>
	 * @since 2020.07
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/map", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Map<String,List<AppModelAttributeId>>> getMapAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, Map<String,List<AppModelAttributeId>>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			Map<String,List<AppModelAttributeId>> map = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				map = modelService.getAppModelAttributeIdMap(params);
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
				if (map == null || map.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {map.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(map);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
     * <pre>
	 * APP모델 속성 리스트 조회
	 * </pre>
	 * @since 2020.07
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<AppModelAttributeId>> getListAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<AppModelAttributeId>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<AppModelAttributeId> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.getAppModelAttributeIdList(params);
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
	 *
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> createAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			List<AppModelAttributeId> requestObject = comMessage.getRequestObject();

			if(requestObject != null && requestObject.size() > 0){
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.createAttributes(requestObject, regDate, regId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 *
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/deail", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<AppModelAttributeId, AppModelAttributeId> createAttribute(
			HttpSession httpSession,
			@RequestBody ComMessage<AppModelAttributeId, AppModelAttributeId> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			AppModelAttributeId requestObject = comMessage.getRequestObject();

			if(requestObject != null){
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.createAttribute(requestObject, regDate, regId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/reset", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, List<AppModelAttributeId>>, Map<String, List<AppModelAttributeId>>> resetAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String, List<AppModelAttributeId>>, Map<String, List<AppModelAttributeId>>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String, List<AppModelAttributeId>> requestObject = comMessage.getRequestObject();

			if(requestObject != null && requestObject.size() > 0){
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.resetAttributes(requestObject, regDate, regId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * 모델 속성 리스트를 입력받아 속성들의 이름 순서 등을 변경한다. (모델속성의 코드값 리스트는 업데이트 대상에서 제외)
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> updateAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			List<AppModelAttributeId> requestObject = comMessage.getRequestObject();

			if(requestObject != null && requestObject.size() > 0){
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.updateAttributes(requestObject, modDate, modId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * 모델 속성 상세 업데이트
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/details", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<AppModelAttributeId, AppModelAttributeId> updateAttribute(
			HttpSession httpSession,
			@RequestBody ComMessage<AppModelAttributeId, AppModelAttributeId> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			AppModelAttributeId requestObject = comMessage.getRequestObject();

			if(requestObject != null){
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.updateAttribute(requestObject, modDate, modId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 *
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> deleteAttributes(
			HttpSession httpSession,
			@RequestBody ComMessage<List<AppModelAttributeId>, List<AppModelAttributeId>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			List<AppModelAttributeId> requestObject = comMessage.getRequestObject();

			if(requestObject != null && requestObject.size() > 0){
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.deleteAttributes(requestObject, modId, modDate);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.delete.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 *
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/attributes/cds", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<AppModelAttributeCode>> getListAttributeCds(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<AppModelAttributeCode>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<AppModelAttributeCode> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.getAppModelAttributeCodeList(params);
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
	 * 새로운 모델 구성을 위한 AppModel 리스트 조회 (송신,수신 시스템리스트 포함)
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
	@RequestMapping(value = "/models/apps/new", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<AppModel>> newModelApps(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<AppModel>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<AppModel> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.newAppModels(params);
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
	 * 새로운 모델 구성
	 * 등록되는 데이터
	 * 	TRT0101, TRT0201, TRT0202, TRT0401, TRT0501
	 *
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
	@RequestMapping(value = "/models/interfaces", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<InterfaceModel, InterfaceModel> createInterfaceModel(
			HttpSession httpSession,
			@RequestBody ComMessage<InterfaceModel, InterfaceModel> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			InterfaceModel requestObject = comMessage.getRequestObject();

			if(requestObject != null){
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.createInterfaceModel(requestObject, regDate, regId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 인터페이스 모델 수정
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
	@RequestMapping(value = "/models/interfaces", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<InterfaceModel, InterfaceModel> updateInterfaceModel(
			HttpSession httpSession,
			@RequestBody ComMessage<InterfaceModel, InterfaceModel> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			InterfaceModel requestObject = comMessage.getRequestObject();

			if(requestObject != null){
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				modelService.updateInterfaceModel(requestObject, modDate, modId);
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requestObject);
			} else{
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", null, locale);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 인터페이스 모델 삭제
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
	@RequestMapping(value = "/models/interfaces/{interfaceMid}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteInterfaceModel(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage,
			@PathVariable("interfaceMid") String interfaceMid,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			String modId = comMessage.getUserId();
			String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
			modelService.deleteInterfaceModel(interfaceMid, modDate, modId);
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);

			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 인터페이스 모델 리스트 조회
	 * 파라메터 : interfaceId,
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
	@RequestMapping(value = "/models/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<InterfaceModel>> getInterfaceModelList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<InterfaceModel>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<InterfaceModel> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.getInterfaceModelList(params);
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


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/interfaces/{interfaceMid}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, InterfaceModel> getInterfaceModel(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, InterfaceModel> comMessage,
			@PathVariable("interfaceMid") String interfaceMid,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			InterfaceModel model = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				model = modelService.getInterfaceModel(interfaceMid);
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
				if (model == null) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(model);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/interfaces/history/{interfaceMid}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<InterfaceModelHistory>> getInterfaceModelHistory(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<InterfaceModelHistory>> comMessage,
			@PathVariable("interfaceMid") String interfaceMid,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<InterfaceModelHistory> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.getInterfaceModelHistory(interfaceMid);
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


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/models/apps/types", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<AppType>> getApTypes(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<AppType>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			FieldCheckUtil.checkRequired(this.getClass().getSimpleName(), "resourceCd", params, messageSource, locale);
			String resourceCd = (String)params.get("resourceCd");

			List<AppType> list = null;
			//--------------------------------------------------
			// 조회 실행
			//--------------------------------------------------
			{
				list = modelService.getAppTypes(resourceCd);
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




}
