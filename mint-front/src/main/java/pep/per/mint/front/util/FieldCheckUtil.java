/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.exception.RequiredFieldsException;
import pep.per.mint.common.util.RequiredFields;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;

/**
 * @author Solution TF
 *
 */
@SuppressWarnings({ "rawtypes" })
public class FieldCheckUtil {

	private static final Logger logger = LoggerFactory.getLogger(FieldCheckUtil.class);

	/**
	 *
	 * @param serviceId
	 * @param requirement
	 * @param messageSource
	 * @param locale
	 * @throws ControllerException
	 */
	public static void check(String serviceId, Requirement requirement, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {
		String errorCd = "";
		String errorMsg = "";
		if(requirement == null){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "Requirement" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}



		if(Util.isEmpty(requirement.getBusiness())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "Requirement.business" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);

		}

		if(Util.isEmpty(requirement.getRequirementNm())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "Requirement.requirementNm" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		if(Util.isEmpty(requirement.getStatus())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "Requirement.status" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		//각종 날자 및 필수필드들을 추가 체크한다.
	}



	public static void check(String serviceId, LoginInfo loginInfo, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {
		String errorCd = "";
		String errorMsg = "";
		if(loginInfo == null){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "LoginInfo" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		if(Util.isEmpty(loginInfo.getUserId())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "LoginInfo.userId" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}else{
			String userId = loginInfo.getUserId();
			checkIncludeNotPermitScript(userId, messageSource, locale);
		}

		if(Util.isEmpty(loginInfo.getSso())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "LoginInfo.sso" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}else {
			if(!"true".equals(loginInfo.getSso())) {
				if (Util.isEmpty(loginInfo.getPassword())) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
					Object[] errorMsgParams = {serviceId, "LoginInfo.password"};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}else{
					String password = loginInfo.getPassword();
					checkIncludeNotPermitScript(password, messageSource, locale);
				}
			}
		}
	}



	public static void checkRequired(String serviceId, Object field, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {
		String errorCd = "";
		String errorMsg = "";
		if(Util.isEmpty(field)){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, field.getClass().getName() };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

	}


	public static void checkRequired(String serviceId, String fieldName, Map params, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {

		String errorCd = "";
		String errorMsg = "";
		if(params == null || !params.containsKey(fieldName)){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, fieldName };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}
	}



	public static void checkRequired(String serviceId, String []fieldNameList, Map params, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {

		String errorCd = "";
		String errorMsg = "";
		for (String fieldName : fieldNameList) {
			if (params == null || !params.containsKey(fieldName)) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
				Object[] errorMsgParams = {serviceId, fieldName};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg);
			}
		}
	}


	public static void checkRequired(String serviceId, String fieldName, Object field, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {
		String errorCd = "";
		String errorMsg = "";
		if(Util.isEmpty(field)){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, fieldName };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}
	}


	/**
	 *
	 * @param param
	 * @return
	 */
	public static String jsonDump(Object param) {
		// TODO Auto-generated method stub
		try{
			return Util.toJSONString(param);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}



	/**
	 * @param params
	 */
	public static void checkNullAndSkip(Map params) {
		// TODO Auto-generated method stub
		if(params == null) {
			params = new HashMap();
		}
	}



	/**
	 * @param params
	 */
	public static String paramString(Map params) {
		String paramString = "";
		try{
			if(params != null) {
				Iterator iterator = params.keySet().iterator();
				while ( iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = params.get(key);
					paramString = Util.join(paramString, "\n",key,":",value);
				}
			}
		}catch(Exception e){
			logger.debug("거래와 무관한 에러-요청들어온 파라메터 프린트하다 발생됨!",e);
		}

		return paramString;
	}


	public static void check(String serviceId, RequirementApproval requirementApproval, ReloadableResourceBundleMessageSource messageSource, Locale locale) throws ControllerException {
		String errorCd;
		String errorMsg;

		if(requirementApproval == null){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "ComMessage.requestObject.RequirementApproval" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		try {
			RequiredFields.check(requirementApproval,"requirementId", "reqUserId", "requirementApprovalUsers", "reqDate", "reqType");
		} catch (RequiredFieldsException e){
			String failFieldName = e.getCheckFailField();
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] params = {serviceId, Util.join("ComMessage.requestObject.RequirementApproval.",failFieldName) };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
			throw new ControllerException(errorCd, errorMsg);

		} catch (Exception e) {

			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
			Object[] params = {serviceId, "ComMessage.requestObject.RequirementApproval 필수필드 체크중 예상치못한 예외 발생" };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		}

		List<RequirementApprovalUser> approvalUsers = requirementApproval.getRequirementApprovalUsers();
		for(RequirementApprovalUser user : approvalUsers){
			try {
				RequiredFields.check(user,"requirementId", "admUserId", "roleType");
			} catch (RequiredFieldsException e){
				String failFieldName = e.getCheckFailField();
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
				Object[] params = {serviceId, Util.join("ComMessage.requestObject.RequirementApproval.RequirementApprovalUser",failFieldName) };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
				throw new ControllerException(errorCd, errorMsg);

			} catch (Exception e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
				Object[] params = {serviceId, "ComMessage.requestObject.RequirementApproval.RequirementApprovalUser 필수필드 체크중 예상치못한 예외 발생" };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}


	}





	public static void check(String serviceId, Approval approval, ReloadableResourceBundleMessageSource messageSource, Locale locale) throws ControllerException {
		String errorCd;
		String errorMsg;

		if(approval == null){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "ComMessage.requestObject.Approval" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		try {
			RequiredFields.check(approval,"approvalItemId", "reqUserId", "approvalItemType", "approvalUsers", "reqDate", "reqType");
		} catch (RequiredFieldsException e){
			String failFieldName = e.getCheckFailField();
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] params = {serviceId, Util.join("ComMessage.requestObject.Approval.",failFieldName) };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
			throw new ControllerException(errorCd, errorMsg);

		} catch (Exception e) {

			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
			Object[] params = {serviceId, "ComMessage.requestObject.Approval 필수필드 체크중 예상치못한 예외 발생" };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		}

		List<ApprovalUser> approvalUsers = approval.getApprovalUsers();
		for(ApprovalUser user : approvalUsers){
			try {
				RequiredFields.check(user,"approvalItemId", "admUserId", "roleType");
			} catch (RequiredFieldsException e){
				String failFieldName = e.getCheckFailField();
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
				Object[] params = {serviceId, Util.join("ComMessage.requestObject.Approval.ApprovalUser",failFieldName) };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
				throw new ControllerException(errorCd, errorMsg);

			} catch (Exception e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
				Object[] params = {serviceId, "ComMessage.requestObject.Approval.ApprovalUser 필수필드 체크중 예상치못한 예외 발생" };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}


	}



	public static void check(String serviceId, DataSet dataSet, ReloadableResourceBundleMessageSource messageSource, Locale locale) throws ControllerException {
		String errorCd;
		String errorMsg;
		if(dataSet == null){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] errorMsgParams = {serviceId, "ComMessage.requestObject.DataSet" };
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorMsgParams, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		try{
			RequiredFields.check(dataSet, "name1", "name2", "dataFormat", "isStandard","use","regDate", "dataFieldList");
		} catch (RequiredFieldsException e){
			String failFieldName = e.getCheckFailField();
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
			Object[] params = {serviceId, Util.join("ComMessage.requestObject.DataSet.",failFieldName) };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
			throw new ControllerException(errorCd, errorMsg);

		} catch (Exception e) {

			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
			Object[] params = {serviceId, "ComMessage.requestObject.DataSet 필수필드 체크중 예상치못한 예외 발생" };
			errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
			throw new ControllerException(errorCd, errorMsg, e);
		}

		List<DataField> dataFieldList = dataSet.getDataFieldList();
		for(DataField field : dataFieldList){
			try {
				RequiredFields.check(field,"name1", "name2", "type");
			} catch (RequiredFieldsException e){
				String failFieldName = e.getCheckFailField();
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
				Object[] params = {serviceId, Util.join("ComMessage.requestObject.DataSet.dataFieldList",failFieldName) };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", params, locale);
				throw new ControllerException(errorCd, errorMsg);

			} catch (Exception e) {

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.system.unexpected", locale);
				Object[] params = {serviceId, "ComMessage.requestObject.DataSet.dataFieldList 필수필드 체크중 예상치못한 예외 발생" };
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.system.unexpected", params, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}

	}


	//시간날때 클래스로 빼고 XSS 처리 라이브러리 개발해두자.
	public static void checkIncludeNotPermitScript(String msg, ReloadableResourceBundleMessageSource messageSource, Locale locale) throws ControllerException {

		if(!Util.isEmpty(msg)) {
			for(String filter : FrontEnvironments.frontScriptFilters){
				if(msg.toLowerCase().contains(filter)){

					logger.debug(Util.join("msg:",msg,"\nfilter:",filter));


					String errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.nonpermission.param.value", locale);

					//Object[] errorMsgParams = {filter.replaceAll("<", "").replaceAll(">", "")};

					String errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.nonpermission.param.value", null, locale);

					logger.debug(Util.join("errorCd:", errorCd, "errorMsg:", errorMsg));

					throw new ControllerException(errorCd, errorMsg);


				}
			}
		}
	}

	public static String removeNotPermitScriptTag(String msg) {
		if(!Util.isEmpty(msg)) {
			for(String filter : FrontEnvironments.frontScriptFilters){
				msg = msg.replaceAll(filter,"");
			}
		}
		return msg;
	}
}
