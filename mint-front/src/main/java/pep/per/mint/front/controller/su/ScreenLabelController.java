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
package pep.per.mint.front.controller.su;

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
import pep.per.mint.common.data.basic.ScreenLabel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.ScreenLabelService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B> 지원(서비스) CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * SU-99-02-001	화면라벨 조회		REST-R01-SU-10-01
 * SU-99-02-002	화면라벨 상세조회		REST-R02-SU-10-01
 * SU-99-02-003	화면라벨 등록 		REST-C01-SU-10-01
 * SU-99-02-004	화면라벨 수정		REST-U01-SU-10-01
 * SU-99-02-005	화면라벨 삭제		REST-D01-SU-10-01
 * -------------------------------------------------------------
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ScreenLabelController {

	private static final Logger logger = LoggerFactory.getLogger(ScreenLabelController.class);

	/**
	 * The Infra service.
	 */
	@Autowired
	ScreenLabelService screenLabelService;

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;



	/**
	 * <pre>
	 * 화면 라벨  목록 조회
	 * API ID : REST-R01-SU-10-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
	 * @since version 1.0(2016.03)
	 */
	@RequestMapping(
			value="/labels",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<ScreenLabel>> getScreenLabelList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<ScreenLabel>> comMessage,
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
			list = screenLabelService.getScreenLabelList(params);

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

	/**
	 * <pre>
	 * 화면라벨 상세 조회
	 * API ID : REST-R02-SU-10-01
	 * </pre>
	 * @param httpSession the http session
	 * @param systemId the system id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value="/labels/{langId}/{labelId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, ScreenLabel> getScreenLabelDetail(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, ScreenLabel> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			ScreenLabel service = null;
			Map params = null;
			//-----------------------------------------------
			//필드 체크
			//-----------------------------------------------
			{
				//FieldCheckUtil.checkRequired("restApiService.getRestServiceDetail", "serviceId", serviceId, messageSource, locale);

				params = comMessage.getRequestObject();
			}

			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			service = screenLabelService.getScreenLabelDetail(params);

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
				if (service == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(service);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 화면라벨 등록
	 * API ID : REST-C01-SU-10-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(value = "/labels", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<ScreenLabel, ScreenLabel> createScreenLabel(
			HttpSession httpSession,
			@RequestBody ComMessage<ScreenLabel, ScreenLabel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ScreenLabel screenLabel = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("screenLabelService.createScreenLabel", ScreenLabel, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createScreenLabel ScreenLabel param dump:\n", FieldCheckUtil.jsonDump(screenLabel)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				screenLabel.setRegId(regId);
				screenLabel.setRegDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					// TODO 2016-12-30  다음코드 살릴것인가?
					/*Map params = new HashMap();
					params.put("systemNm",system.getSystemNm());
					params.put("systemCd",system.getSystemCd());
					List<System> list = infraService.existSystem(params);
					//systemCd 또는 systemNm이 동일한 시스템이 등록되어 있으면 예외처리한다.
					if(list == null || list.size() == 0) {

						res = infraService.createSystem(system);

					}else{

						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.system.create.already.exist", locale);
						String[] errorMsgParams = {};
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.system.create.already.exist", errorMsgParams, locale);
						throw new ControllerException(errorCd, errorMsg);

					}*/
					//삼성전기는 아래 예외처리 제외
					//20160414
					//함대훈 부장님 요청
					//systemCd 또는 systemNm이 동일한 시스템이 등록되어 있으면 예외처리한다.
					res = screenLabelService.createScreenLabel(screenLabel);
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
					comMessage.setResponseObject(screenLabel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RestApiController.createRestService",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}


	/**
	 * <pre>
	 * 화면라벨 수정
	 * API ID : REST-U01-SU-10-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/labels", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<ScreenLabel, ScreenLabel> updateScreenLabel(
			HttpSession httpSession,
			@RequestBody ComMessage<ScreenLabel, ScreenLabel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ScreenLabel screenLabel = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("RestApiController.createSystem", system, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateScreenLabel ScreenLabel param dump:\n", FieldCheckUtil.jsonDump(screenLabel)));

			}

			//----------------------------------------------------------------------------
			//수정ID, 수정시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				//server.setRegId(regId);
				//server.setRegDate(regDate);
				screenLabel.setModId(regId);
				screenLabel.setModDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				// MSG_ID  중복 체크
				if(screenLabel.getMsgId().equalsIgnoreCase(screenLabel.getOrgMsgId())){
					res = screenLabelService.updateScreenLabel(screenLabel);
				}else{
					Map params = new HashMap();
					params.put("msgId",screenLabel.getMsgId());
					params.put("langId",screenLabel.getLangId());
					List<ScreenLabel> list = screenLabelService.existScreenLabel(params);
					if(list == null || list.size() == 0) {
					{
							res = screenLabelService.updateScreenLabel(screenLabel);
					}
					}else{
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.screenlabel.create.already.exist", locale);
						String[] errorMsgParams = {};
						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.screenlabel.create.already.exist", errorMsgParams, locale);
						throw new ControllerException(errorCd, errorMsg);
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
					comMessage.setResponseObject(screenLabel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RestApiController.updateRestService",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}



	/**
	 * <pre>
	 * 화면라벨  삭제
	 * API ID : REST-D01-SU-10-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/labels/{langId}/{labelId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteRestService(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;

			// TODO 2016-12-30 bill 삭제시 체크해야 할 항목   삭제 하는경우 동시에 지워야 할 테이블

//			List<String> usedInterfaceIds = infraService.usedServer(serverId);
//			if(usedInterfaceIds != null && usedInterfaceIds.size() > 0){
//				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.system.delete.used", locale);
//				String usedInterfaceIdList = usedInterfaceIds.get(0);
//				for(int i = 1 ; i < usedInterfaceIds.size() ; i ++){
//					if(i >= 3) break;
//					String usedInterfaceId = usedInterfaceIds.get(i);
//					usedInterfaceIdList = Util.join(usedInterfaceIdList, ",", usedInterfaceId);
//				}
//				String[] errorMsgParams = {usedInterfaceIdList};
//				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.system.delete.used", errorMsgParams, locale);
//				throw new ControllerException(errorCd, errorMsg);
//			}

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					params = (Map) comMessage.getRequestObject();
					params.put("modId", comMessage.getUserId());
					params.put("modDate", Util.getFormatedDate());
					res = screenLabelService.deleteScreenLabel(params);
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

					comMessage.setResponseObject(null);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RestApiController.deleteRestService",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}
}
