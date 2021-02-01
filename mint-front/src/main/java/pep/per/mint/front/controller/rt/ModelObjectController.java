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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.InterfaceModelObject;
import pep.per.mint.common.data.basic.runtime.MsgModelObject;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.rt.ModelObjectService;
import pep.per.mint.database.service.rt.ModelService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * @author iip
 * @since Jul 9, 2020
 */
@Controller
@RequestMapping("/rt")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModelObjectController {

	private static final Logger logger = LoggerFactory.getLogger(ModelObjectController.class);

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	private ServletContext servletContext;

	@Autowired
	ModelObjectService modelService;


	/**
	 * <pre>
	 * Simple InterfaceModel 조회 [REST-R01-AN-06-03]
	 * </pre>
	 * @param httpSession
	 * @param dataSetId
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/models/interface/detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, InterfaceModelObject> getSimpleInterfaceModel(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, InterfaceModelObject> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			if (params != null){
				String paramString = FieldCheckUtil.paramString(params);
				logger.debug(Util.join("\nparamString:[", paramString, "]"));
			}
			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				//FieldCheckUtil.checkRequired("DataSetController.getDataSet", "dataSetId", dataSetId, messageSource, locale);
			}

			try {
				InterfaceModelObject interfaceModel = null;
				//--------------------------------------------------
				//조회 실행
				//--------------------------------------------------
				interfaceModel = modelService.getSimpleInterfaceModel(params);

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{
					if (interfaceModel == null) {
						// 결과가 없을 경우 비지니스 예외 처리
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
					} else {
						// 성공 처리결과
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
						comMessage.setResponseObject(interfaceModel);
					}
				}
			} catch(Throwable e) {
				String point = Util.join(this.getClass().getName(), "." + "getSimpleInterfaceModel");
				String[] errorMsgParams = {point, e.getMessage()};
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.common", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.common", errorMsgParams, locale);

				throw new ControllerException(errorCd, errorMsg, e);
			} finally {
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
				}
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
	@RequestMapping(value = "/models/msg/create", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<MsgModelObject>, List<MsgModelObject>> createMsgModel(
			HttpSession httpSession,
			@RequestBody ComMessage<List<MsgModelObject>, List<MsgModelObject>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			List<MsgModelObject> requestObject = comMessage.getRequestObject();

			try {

				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				String regId   = comMessage.getUserId();

				modelService.createMsgModel(requestObject, regDate, regId);

				//--------------------------------------------------
				// response set
				//--------------------------------------------------
				{
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requestObject);
				}
			} catch(Throwable e) {
				String point = Util.join(this.getClass().getName(), "." + "createMsgModel");
				String[] errorMsgParams = {point, e.getMessage()};
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);

				throw new ControllerException(errorCd, errorMsg, e);
			} finally {
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
				}
			}
			return comMessage;
		}
	}

	/**
	 * REST-U01-AN-06-03
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/models/msgmap/update", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<InterfaceModelObject, InterfaceModelObject> updateMsgMapModel (
			HttpSession httpSession,
			@RequestBody ComMessage<InterfaceModelObject, InterfaceModelObject> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			InterfaceModelObject interfaceModel = comMessage.getRequestObject();

			try {

				String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				String regId   = comMessage.getUserId();

				interfaceModel.setRegDate(regDate);
				interfaceModel.setRegId(regId);

				modelService.updateMsgMapModel(interfaceModel);

				//--------------------------------------------------
				// response set
				//--------------------------------------------------
				{
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(interfaceModel);
				}

			} catch(Throwable e) {
				String point = Util.join(this.getClass().getName(), "." + "updateMsgMapModel");
				String[] errorMsgParams = {point, e.getMessage()};
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);

				throw new ControllerException(errorCd, errorMsg, e);
			} finally {
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
				}
			}
			return comMessage;
		}
	}

}
