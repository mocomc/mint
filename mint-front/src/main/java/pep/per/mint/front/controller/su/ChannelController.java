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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.ChannelService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B> 지원(연계채널) CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * SU-01-11-001	연계채널 리스트 조회		REST-R01-SU-01-11
 * SU-01-11-001	연계채널  등록			REST-C01-SU-01-11
 * SU-01-11-001	연계채널  수정			REST-U01-SU-01-11
 * SU-01-11-001	연계채널  삭제			REST-D01-SU-01-11
 * -------------------------------------------------------------
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ChannelController {

	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

	/**
	 * The Channel Attribute service.
	 */
	@Autowired
	ChannelService channelService;

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;



	/**
	 * <pre>
	 * 연계채널  목록 조회
	 * API ID : REST-R01-SU-01-11
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
			value="/channels",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Channel>> getChannelList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Channel>> comMessage,
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
				params = (Map) comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}


			}

			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			list = channelService.getChannelList(params);

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
	 * 연계채널 등록
	 * API ID : REST-C01-SU-01-11
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(value = "/channels", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Channel, Channel> createChannel(
			HttpSession httpSession,
			@RequestBody ComMessage<Channel, Channel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Channel channel = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createChannel channel param dump:\n", FieldCheckUtil.jsonDump(channel)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				channel.setRegId(regId);
				channel.setRegDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					Map params = new HashMap();
					params.put("channelCd",channel.getChannelCd());
					List<Channel> list = channelService.existChannel(params);
					if(list == null || list.size() == 0) {
						res = channelService.createChannel(channel);
					}else{
						//errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.application.create.already.exist", locale);
						errorCd = "9876";
						String[] errorMsgParams = {};
						//errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.application.create.already.exist", errorMsgParams, locale);
						errorMsg = "error.cd.infra.channel.create.already.exist 임시";
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
					comMessage.setResponseObject(channel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"ChannelController.createChannel",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}


	/**
	 * <pre>
	 * 연계채널  변경
	 * API ID : REST-U01-SU-01-11
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/channels", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Channel, Channel> updateChannel(
			HttpSession httpSession,
			@RequestBody ComMessage<Channel, Channel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Channel channel = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateChannel channel param dump:\n", FieldCheckUtil.jsonDump(channel)));

			}

			//----------------------------------------------------------------------------
			//수정ID, 수정시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				channel.setModId(regId);
				channel.setModDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				Map params = new HashMap();
				params.put("channelCd",channel.getChannelCd());
				params.put("channelId",channel.getChannelId());
				List<Channel> list = channelService.existChannel(params);
				if(list == null || list.size() == 0) {
					channelService.updateChannel(channel);
				}else{
					//errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.application.create.already.exist", locale);
					errorCd = "9877";
					String[] errorMsgParams = {};
					//errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.application.create.already.exist", errorMsgParams, locale);
					errorMsg = "error.msg.infra.channel.create.already.exist 임시";
					throw new ControllerException(errorCd, errorMsg);
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
					comMessage.setResponseObject(channel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"ApplicationController.updateApplication",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}



	/**
	 * <pre>
	 * 연계채널  삭제
	 * API ID : REST-D01-SU-01-11
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/channels/{channelId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteChannel(
			HttpSession httpSession,
			@PathVariable("channelId") String channelId,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					params = (Map) comMessage.getRequestObject();
					params.put("modId", comMessage.getUserId());
					params.put("modDate", Util.getFormatedDate());
					params.put("channelId", channelId);
					res = channelService.deleteChannel(params);
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
				String[] errorMsgParams = {"ChannelController.deleteChannel",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}
}
