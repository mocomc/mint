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
package pep.per.mint.front.controller.im;

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

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.im.SystemPriorityMngService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B> 종합상황판 시스템 : 괌심시스템 맵핑  CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * IM-02-01-003	 조회					REST-R14-IM-02-01
 * IM-02-01-003	 등록					REST-C05-IM-02-01
 * -------------------------------------------------------------
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SystemPriorityMngController {

	private static final Logger logger = LoggerFactory.getLogger(SystemPriorityMngController.class);

	/**
	 * The Infra service.
	 */
	@Autowired
	SystemPriorityMngService systemPriorityMngService;

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;

	/**
	 * <pre>
	 * 맵핑  조회
	 * API ID : REST-R14-IM-02-01
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
			value="/system/priority/",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<QmgrInfo>> getSystemPriorityList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<QmgrInfo>> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List list = null;
			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------


			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			list = systemPriorityMngService.getSystemPriorityList();

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
	 * 맵핑 등록
	 * API ID : REST-C05-IM-02-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(value = "/system/priority/", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<Map>, Map> addSystemPriorityList(
			HttpSession httpSession,
			@RequestBody ComMessage<List<Map>,Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			List<Map> list = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}
			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{

			}
			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					//Map params = new HashMap();
					//params.put("serverCd",server.getServerCd());
					//List<Server> list = engineMonitorService.existServer(params);
					//systemCd 또는 systemNm이 동일한 시스템이 등록되어 있으면 예외처리한다.
//					if(list == null || list.size() == 0) {
//						res = engineMonitorService.createServer(server);
//					}else{
//						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.infra.server.create.already.exist", locale);
//						String[] errorMsgParams = {};
//						errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.infra.server.create.already.exist", errorMsgParams, locale);
//						throw new ControllerException(errorCd, errorMsg);
//					}


					res = systemPriorityMngService.addSystemPriorityList(list);
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

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"EngineMonitorController.createEngineMonitor",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}

}
