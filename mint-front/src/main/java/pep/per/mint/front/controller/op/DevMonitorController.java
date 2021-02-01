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
package pep.per.mint.front.controller.op;

import java.util.ArrayList;
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
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.data.basic.TRLogDetailDiagram;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.DevMonitorService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B>모니터링 CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * OP-01-01-001	트레킹 로그 리스트 조회	REST-R01-OP-01-01
 * OP-01-01-002	트레킹 로그 상세 조회	REST-R02-OP-01-01
 *
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/op")
public class DevMonitorController {
	private static final Logger logger = LoggerFactory.getLogger(DevMonitorController.class);

	/**
	 * The Message source.
	 */
	// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Monitor service.
	 */
	// 인터페이스요건관련 데이터 서비스를 구현한 객체
	@Autowired
	DevMonitorService monitorService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;

	@Autowired
	CommonService commonService;

	/**
	 * <pre>
	 * 트레킹 로그 리스트 조회
	 * API ID : REST-R01-OP-01-01-DEV
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
	 * @throws ControllerException
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dev/tracking/logs", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<TRLog>> getTrackingLogList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<TRLog>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 * 1.pep.per.mint.database.service.an.RequirementService.method 작성
		 * 2.pep.per.mint.front.controller.an.RequirementController.method 작성
		 * 3.pep.per.mint.database.mapper.an.RequirementMapper.method 작성
		 * 4.pep.per.mint.database.mapper.an.RequirementMapper.xml 상의 맵핑 sql 작성
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null)
				params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// calculating startIndex and endIndex value
			//--------------------------------------------------
			{
				int startIndex = 0;
				int endIndex = 0;
				int page     = params.get("page") == null ? 1 : (Integer)params.get("page");
				int perCount = params.get("perCount") == null ? 20 : (Integer)params.get("perCount");

				startIndex = (page - 1) * perCount + 1;
				endIndex = startIndex + perCount - 1;

				params.put("startIndex",startIndex);
				params.put("endIndex",endIndex);
				params.put("page",page);
				params.put("perCount",perCount);
			}

			List<TRLog> list = null;
			{

				int totalCount = params.get("total") == null ? 0 : (Integer)params.get("total");
				if( totalCount == 0 ) {
					totalCount = monitorService.getTrackingLogListTotalCount(params);
				}

				if(totalCount > 0) {
					list = monitorService.getTrackingLogList(params);
				} else {
					list = new ArrayList();
				}
				params.put("total",totalCount);
				comMessage.setResponseObject(list);
			}

			if (list == null || list.size() == 0) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {list.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(list);
			}

			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 트레킹 로그 상세 - 트레킹 로그 상세 정보 조회
	 * API ID : REST-R02-OP-01-01-DEV
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param logKey1 the log key 1
	 * @param logKey2 the log key 2
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value="/dev/tracking/logs/{logKey1}/{logKey2}", params={"method=GET"}, method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, List<TRLogDetail>> getTrackingLogDetail(
			HttpSession  httpSession,
			@PathVariable("logKey1") String logKey1,
			@PathVariable("logKey2") String logKey2,
			@RequestBody ComMessage<Map<String, Object>, List<TRLogDetail>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			comMessage.getRequestObject().put("logKey1", logKey1);
			comMessage.getRequestObject().put("logKey2", logKey2);

			List<TRLogDetail> list = monitorService.getTrackingLogDetail(comMessage.getRequestObject());


			for(TRLogDetail detail : list) {
				if(detail.getMsg() != null) {
					byte[] bdata = detail.getMsg().getBytes(1, (int) detail.getMsg().length());
					detail.setMsgToString(new String(bdata));
				}
				if(detail.getMsgToByte() != null) {
					logger.debug(new String(detail.getMsgToByte()));
					detail.setMsgToString(new String(detail.getMsgToByte(), "UTF-8"));
				}
			}

			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
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

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 트레킹 로그 상세 - 트레킹 로그 노드 정보 조회
	 * API ID : REST-R05-OP-01-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param logKey1 the log key 1
	 * @param logKey2 the log key 2
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value="/dev/tracking/diagrams/{logKey1}/{logKey2}", params={"method=GET"}, method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, TRLogDetailDiagram> getTrackingDiagramInfo(
			HttpSession  httpSession,
			@PathVariable("logKey1") String logKey1,
			@PathVariable("logKey2") String logKey2,
			@RequestBody ComMessage<Map<String, Object>, TRLogDetailDiagram> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			comMessage.getRequestObject().put("logKey1", logKey1);
			comMessage.getRequestObject().put("logKey2", logKey2);

			TRLogDetailDiagram list = monitorService.getTrackingDiagramInfo(comMessage.getRequestObject());


			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if (list == null ) {// 결과가 없을 경우 비지니스 예외 처리
				//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {// 성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;
		}
	}

}
