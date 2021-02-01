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
package pep.per.mint.front.controller.an;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.RequirementApproval;
import pep.per.mint.common.data.basic.RequirementTemp;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.herstory.RequirementColumnHistory;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.MappingModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.data.basic.runtime.RequirementModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.Environments;
import pep.per.mint.database.service.an.DataSetService;
//import pep.per.mint.database.service.an.RequirementHerstoryService;
import pep.per.mint.database.service.an.RequirementHerstoryService;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.database.service.co.ApprovalService;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.rt.ModelService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>인터페이스요건 CRUD  서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 리스트 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRequirementList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R02-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 상세 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRequirementDetail(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements/{requirementId}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R06-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 히스토리 버전 리스트 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRequirementHistoryVersionList(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements/{requirementId}/versions</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R07-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 히스토리 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRequirementHistory(HttpSession, String, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements/{requirementId}/versions/{version}{requirementId}</td>
 * </tr>*
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-C01-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 등록</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #createRequirement(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-U01-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 변경</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #updateRequirement(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements/{requirementId}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-D01-AN-01-00</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건 삭제</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #deleteRequirement(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements/{requirementId}</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author Solution TF
 */
@Controller
@RequestMapping("/an")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RequirementController {

	private static final Logger logger = LoggerFactory
			.getLogger(RequirementController.class);

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Requirement service.
	 * 인터페이스요건관련 데이터 서비스를 구현한 객체
	 */
	@Autowired
	RequirementService requirementService;

	@Autowired
	CommonService commonService;

	@Autowired
	ApprovalService approvalService;

	@Autowired
	RequirementHerstoryService requirementHerstoryService;

	@Autowired
	DataSetService dataSetService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;

	/**
	 * <pre>
	 * 요건 리스트 조회
	 * API ID : REST-R01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getRequirementList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
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
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			{
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}
			}
			List<Requirement> list = null;
			// DEL_YN 설정 추가.
			String delYn = "N";
			delYn= (String)params.get("delYn");
			if(delYn == null || delYn.equalsIgnoreCase("") || !delYn.equalsIgnoreCase("Y")){
				params.put("delYn", "N");
			}

			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
			if (businessPathViewList != null && businessPathViewList.size() > 0) {
				String bizPathView = businessPathViewList.get(0);
				params.put("bizPathView", bizPathView);
			}

			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = requirementService.getRequirementList(params);
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
	@RequestMapping(value = "/requirements/by-page", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getRequirementListByPageV2(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Requirement> list = null;
			// DEL_YN 설정 추가.
			String delYn = "N";
			delYn= (String)params.get("delYn");
			if(delYn == null || delYn.equalsIgnoreCase("") || !delYn.equalsIgnoreCase("Y")){
				params.put("delYn", "N");
			}

			//calculating startIndex and endIndex value
			{
				int startIndex = 0;
				int endIndex = 0;
				int page = params.get("page") == null ? 1 : (Integer)params.get("page");
				int pageCount = params.get("pageCount") == null ? 0 : (Integer)params.get("pageCount");
				int totalCount = params.get("totalCount") == null ? 0 : (Integer)params.get("totalCount");
				int perCount = params.get("perCount") == null ? 20 : (Integer)params.get("perCount");
				int tailCount = params.get("tailCount") == null ? 0 : (Integer)params.get("tailCount");

				if(page == 1){
					pageCount = 0;
					totalCount = 0;
					tailCount = 0;
				}

				startIndex = (page - 1) * perCount + 1;
				endIndex = startIndex + perCount - 1;


				params.put("startIndex",startIndex);
				params.put("endIndex",endIndex);
				params.put("page",page);
				params.put("pageCount",pageCount);
				params.put("totalCount",totalCount);
				params.put("perCount",perCount);
				params.put("tailCount",tailCount);

			}
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			{
				List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
				if (businessPathViewList != null && businessPathViewList.size() > 0) {
					String bizPathView = businessPathViewList.get(0);
					params.put("bizPathView", bizPathView);
				}
			}

			//조회 리스트 오더링 필드 세팅
			//포털환경변수 값 세팅시  "{필드명} {DESC | ASC}" 값 등록  --> 등록예: INTEGRATION_I DESC
			//등록하지 않으면 TAN0101.MOD_DATE DESC 를 기준 오더링 값으로 세팅됨.
			{
				List<String> orderByFieldList = environmentalValues.get("analysis.interface.list.order.by.field");
				if (orderByFieldList != null && orderByFieldList.size() > 0) {
					String orderByField = orderByFieldList.get(0);
					params.put("orderByField", orderByField);
				}
			}

			//엔드포인트 적용 옵션 (system.endpoint.implementation = true|false)
			//true 일경우 url/파일/경로 와 테이블/파일/서비스명 검색 조건 타겟 테이블을 엔드포인트 관련 테이블로 변경한다.
			{
				String endpointOpt = "false";
				List<String> endpointOptList = environmentalValues.get("system.endpoint.implementation");
				if (endpointOptList != null && endpointOptList.size() > 0) {
					endpointOpt = endpointOptList.get(0);
				}
				params.put("endpointOpt", endpointOpt);
			}

			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				int totalCount = requirementService.getRequirementTotalCount(params);

				if(totalCount > 0) {
					list = requirementService.getRequirementListByPageV2(params);
				} else {
					list = new ArrayList();
				}
				params.put("total",totalCount);
				comMessage.setResponseObject(list);
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
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * @deprecated from 201707
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/by-page-v1", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getRequirementListByPage(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());


			List<Requirement> list = null;
			// DEL_YN 설정 추가.
			String delYn = "N";
			delYn= (String)params.get("delYn");
			if(delYn == null || delYn.equalsIgnoreCase("") || !delYn.equalsIgnoreCase("Y")){
				params.put("delYn", "N");
			}

			//calculating startIndex and endIndex value
			{
				int startIndex = 0;
				int endIndex = 0;
				int page = params.get("page") == null ? 1 : (Integer)params.get("page");
				int pageCount = params.get("pageCount") == null ? 0 : (Integer)params.get("pageCount");
				int totalCount = params.get("totalCount") == null ? 0 : (Integer)params.get("totalCount");
				int perCount = params.get("perCount") == null ? 20 : (Integer)params.get("perCount");
				int tailCount = params.get("tailCount") == null ? 0 : (Integer)params.get("tailCount");

				if(page == 1){
					pageCount = 0;
					totalCount = 0;
					tailCount = 0;
				}

				startIndex = (page - 1) * perCount + 1;
				endIndex = startIndex + perCount - 1;


				params.put("startIndex",startIndex);
				params.put("endIndex",endIndex);
				params.put("page",page);
				params.put("pageCount",pageCount);
				params.put("totalCount",totalCount);
				params.put("perCount",perCount);
				params.put("tailCount",tailCount);

			}
			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
			if (businessPathViewList != null && businessPathViewList.size() > 0) {
				String bizPathView = businessPathViewList.get(0);
				params.put("bizPathView", bizPathView);
			}

			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				int totalCount = requirementService.getRequirementTotalCount(params);

				if(totalCount > 0) {
					list = requirementService.getRequirementListByPage(params);
				} else {
					list = new ArrayList();
				}
				params.put("total",totalCount);
				comMessage.setResponseObject(list);

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
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 요건 리스트 조회 테스트 유닛 메소드
	 * API ID : REST-R01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements", params = { "method=GET",
			"isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> testGetRequirementList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------

		{
			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/an/REST-R01-AN-01-00.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------


			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Requirement> list = (List<Requirement>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

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
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 요건 상세 조회
	 * API ID : REST-R02-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Requirement> getRequirementDetail(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("RequirementController.getRequirementDetail", "requirementId", requirementId, messageSource, locale);
			}


			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			Map<String, String> params = new HashMap<String, String>();
			params.put("requirementId", requirementId);
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
			if (businessPathViewList != null && businessPathViewList.size() > 0) {
				String bizPathView = businessPathViewList.get(0);
				params.put("bizPathView", bizPathView);
			}


			Requirement requirementDetail = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				//requirementDetail = requirementService.getRequirementDetail(requirementId);
				requirementDetail = requirementService.getRequirementDetail(params);
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
				if (requirementDetail == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requirementDetail);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	@Autowired
	ModelService modelService;
	/**
	 * <pre>
	 * 요건+모델 상세 조회
	 * API ID : REST-R02-AN-01-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirement-models/{requirementId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, RequirementModel> getRequirementModelDetail(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, RequirementModel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("RequirementController.getRequirementModelDetail", "requirementId", requirementId, messageSource, locale);
			}


			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			Map<String, String> params = new HashMap<String, String>();
			params.put("requirementId", requirementId);
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
			if (businessPathViewList != null && businessPathViewList.size() > 0) {
				String bizPathView = businessPathViewList.get(0);
				params.put("bizPathView", bizPathView);
			}

			RequirementModel requirementModel = new RequirementModel();


			//--------------------------------------------------
			//요건 모델 조회 실행
			//--------------------------------------------------
			{
				Requirement requirementDetail = requirementService.getRequirementDetail(params);
				requirementModel.setRequirement(requirementDetail);
				String interfaceId = requirementDetail.getInterfaceInfo().getInterfaceId();
				InterfaceModel interfaceModel = modelService.getInterfaceModelByInterfaceId(interfaceId);
				requirementModel.setInterfaceModel(interfaceModel);
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
				if (requirementModel == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requirementModel);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	
	/**
	 * <pre>
	 * 요건+모델 상세 조회 integrationId 기준으로 조히 
	 * API ID : REST-R02-AN-01-02
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2020.11)
     */
	@RequestMapping(value = "/requirement-models-by-integration-id", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String, String>, RequirementModel> getRequirementModelDetailByIntegrationId(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String, String>, RequirementModel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String, String> params = comMessage.getRequestObject();
			if(params == null || params.get("integrationId") == null) {
				throw new Exception("RequirementController.getRequirementModelDetailByIntegrationId service have no parameter value : integrationId");
			}
			String integrationId = params.get("integrationId");
			
			String requirementId = null;
			//--------------------------------------------------
			//파라메터 {requirementId} 조회 
			//--------------------------------------------------
			{
				List<Requirement> requirements = commonService.getRequirementCdListByRelation(params);
				if(Util.isEmpty(requirements)) {
					throw new Exception(Util.join("integrationId:", integrationId, " 값에 해당하는 Requirement 정보를 조회할 수 없습니다.") );
				}
				requirementId = requirements.get(0).getRequirementId();
			}
			
			//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
			//system.interface.business.path.view
			params.put("requirementId", requirementId);
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
			if (businessPathViewList != null && businessPathViewList.size() > 0) {
				String bizPathView = businessPathViewList.get(0);
				params.put("bizPathView", bizPathView);
			}

			RequirementModel requirementModel = new RequirementModel();


			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				Requirement requirementDetail = requirementService.getRequirementDetail(params);
				requirementModel.setRequirement(requirementDetail);
				String interfaceId = requirementDetail.getInterfaceInfo().getInterfaceId();
				InterfaceModel interfaceModel = modelService.getInterfaceModelByInterfaceId(interfaceId);
				requirementModel.setInterfaceModel(interfaceModel);
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
				if (requirementModel == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requirementModel);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
	

	/**
	 * <pre>
	 * 요건 상세 조회
	 * API ID : REST-R02-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/temp-save/{requirementId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Requirement> getRequirementDetailTempData(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("RequirementController.getRequirementDetailTempData", "requirementId", requirementId, messageSource, locale);
			}


			Requirement requirementDetail = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{

		        RequirementTemp temp = requirementService.getRequirementTemp(requirementId);
		        if( temp != null ) {
		        	requirementDetail = Util.jsonToObject(temp.getContents(), Requirement.class);
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
				if (requirementDetail == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requirementDetail);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 요건 상세 조회 테스트 유닛 메소드
	 * API ID : REST-R02-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author noggenfogger
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}", params = {
			"method=GET", "isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Object> testGetRequirementDetail(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<Map<String,Object>, Object> comMessage, Locale locale, HttpServletRequest request)
			throws Exception {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/an/REST-R02-AN-01-00.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			Requirement requirement = (Requirement) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

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
				if (requirement == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(requirement);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 요건 버전 리스트 조회
	 * API ID : REST-R06-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId the requirement id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/{requirementId}/versions", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getRequirementHistoryVersionList(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				//list = requirementHerstoryService.getRequirementHerstoryList(requirementId);
				list = requirementHerstoryService.getRequirementHistoryList(requirementId);
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
	 * 요건 히스토리 조회
	 * API ID : REST-R07-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 상세 조회할 요건ID
	 * @param version the version
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}/versions/{version}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, RequirementColumnHistory> getRequirementHistory(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@PathVariable("version") String version,
			@RequestBody ComMessage<?, RequirementColumnHistory> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			RequirementColumnHistory history = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
				//system.interface.business.path.view
				Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
				String bizPathView = null;
				if (businessPathViewList != null && businessPathViewList.size() > 0) {
					bizPathView = businessPathViewList.get(0);
				}
				history = requirementHerstoryService.getRequirementHistory(requirementId, version, bizPathView);
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
				if (history == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(history);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 요건 상세 리스트 조회
	 * API ID : REST-R09-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
     * @author TA
     * @since version 1.0(2017.06)
     */
	@RequestMapping(value = "/requirements/detail", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<String>, List<Requirement>> getRequirementDetailList(
			HttpSession httpSession,
			@RequestBody ComMessage<List<String>, List<Requirement>> comMessage,
			Locale locale,
			HttpServletRequest request) throws Exception, ControllerException {
		{
			String errorCd = "";
			String errorMsg = "";

			List<Requirement> list = null;
			List<String> ids = comMessage.getRequestObject();
			if(ids != null && ids.size() > 0){

				Map<String,Object> params = new HashMap<String, Object>();
				//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
				//system.interface.business.path.view
				Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> businessPathViewList = environmentalValues.get("system.interface.business.path.view");
				if (businessPathViewList != null && businessPathViewList.size() > 0) {
					String bizPathView = businessPathViewList.get(0);
					params.put("bizPathView", bizPathView);
				}

				list = new ArrayList<Requirement>();

				params.put("requirementIds", ids);
				list = requirementService.getRequirementDetailList(params);
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
	 * 요건 등록 + 모델 등록
	 * API ID : REST-C01-AN-01-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Interface Portal Team
     * @since 2020.11
     */
	@RequestMapping(value = "/requirement-models", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<RequirementModel, RequirementModel> createRequirementModel(
			HttpSession httpSession,
			@RequestBody ComMessage<RequirementModel, RequirementModel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			RequirementModel requirementModel = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
//				FieldCheckUtil.check("RequirementController.createRequirementAndModel", requirementModel, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createRequirement requirement param dump:\n", FieldCheckUtil.jsonDump(requirementModel)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				requirementModel.setRegId(regId);
				requirementModel.setRegDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.createRequirementModel(requirementModel);
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
					comMessage.setResponseObject(requirementModel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirementAndModel",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}



	/**
	 * <pre>
	 * 요건 등록
	 * API ID : REST-C01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Requirement, Requirement> createRequirement(
			HttpSession httpSession,
			@RequestBody ComMessage<Requirement, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Requirement requirement = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("RequirementController.createRequirement", requirement, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createRequirement requirement param dump:\n", FieldCheckUtil.jsonDump(requirement)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				requirement.setRegId(regId);
				requirement.setRegDate(regDate);
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.createRequirement(requirement);
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
					comMessage.setResponseObject(requirement);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.createRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}



	/**
	 * <pre>
	 * 요건 등록 테스트 유닛 메소드
	 * API ID : REST-C01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements", params = { "method=POST",
			"isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Requirement> testCreateRequirement(
			HttpSession httpSession,
			@RequestBody ComMessage<?, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{


			return null;
		}
	}

	/**
	 * <pre>
	 * 요건 수정
	 * API ID : REST-U01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Requirement, Requirement> updateRequirement(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<Requirement, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			Requirement requirement = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("RequirementController.updateRequirement", requirement, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateRequirement requirement param dump:\n", FieldCheckUtil.jsonDump(requirement)));
			}


			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				String modId = regId;
				String modDate = regDate;
				requirement.setModId(modId);
				requirement.setModDate(modDate);
				//requirement.setRegId(regId);
				//requirement.setRegDate(regDate);
			}
			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.updateRequirement(requirement);
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
					comMessage.setResponseObject(requirement);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 요건 + 인터페이스 모델 수정
	 * API ID : REST-U01-AN-01-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirement-models/{requirementId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<RequirementModel, RequirementModel> updateRequirementModel(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<RequirementModel, RequirementModel> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			RequirementModel requirementModel = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateRequirementModel requirement param dump:\n", FieldCheckUtil.jsonDump(requirementModel)));
			}


			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				String modId = regId;
				String modDate = regDate;
				requirementModel.setModId(modId);
				requirementModel.setModDate(modDate);

			}
			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.updateRequirementModel(requirementModel);
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
					comMessage.setResponseObject(requirementModel);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementModel",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}

	/**
	 * <pre>
	 * 요건 수정 테스트 유닛 메소드
	 * API ID : REST-U01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}", params = {
			"method=PUT", "isTest=true" }, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Requirement> testUpdateRequirement(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{

			return null;
		}
	}


	/**
	 * <pre>
	 * 요건 상태 수정
	 * API ID : REST-U02-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param status the status
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}/{status}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<RequirementApproval, RequirementApproval> updateRequirementStatus(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@PathVariable("status") String status,
			@RequestBody ComMessage<RequirementApproval, RequirementApproval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			RequirementApproval requirementApproval = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("RequirementController.updateRequirementStatus", requirementApproval, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateRequirement requirement param dump:\n", FieldCheckUtil.jsonDump(requirementApproval)));
			}



			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.updateRequirementStatus(status,comMessage.getStartTime(),comMessage.getUserId(),requirementId,requirementApproval);
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
					comMessage.setResponseObject(requirementApproval);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementStatus",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 요건개발테스트이행상태변경
	 * API ID : REST-U03-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param status the status
	 * @param finYmd the fin ymd
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}/{status}/{finYmd}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> updateRequirementDevelopmentStatus(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@PathVariable("status") String status,
			@PathVariable("finYmd") String finYmd,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";



			try{
				//----------------------------------------------------------------------------
				//등록ID, 등록시간 설정하기
				//----------------------------------------------------------------------------
				String regId   = comMessage.getUserId();
				String regDate = Util.getFormatedDate();

				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{

					//finYmd = finYmd.equalsIgnoreCase("null") ? "" : finYmd;

					logger.debug(Util.join("updateRequirementDevelopmentStatus->finYmd:",finYmd));

					if("null".equals(finYmd)){
						res = requirementService.updateRequirementDevelopmentStatus(status, regDate, regId, requirementId, "");
					}else{
						res = requirementService.updateRequirementDevelopmentStatus(status, regDate, regId, requirementId, finYmd);
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
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementStatus",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 요건개발테스트이행상태변경
	 * API ID : REST-U04-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param status the status
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}/{status}/cancel", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> updateRequirementDevelopmentCancelStatus(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@PathVariable("status") String status,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";



			try{
				//----------------------------------------------------------------------------
				//등록ID, 등록시간 설정하기
				//----------------------------------------------------------------------------
				String regId   = comMessage.getUserId();
				String regDate = Util.getFormatedDate();
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.updateRequirementDevelopmentCancelStatus(status, regDate, regId, requirementId);
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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementStatus",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 요건 임시저장정보 반영
	 * API ID : REST-U05-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
	 * @since version 1.0(2015.07)
	 */
	@RequestMapping(value = "/requirements/save-temp/{requirementId}/{apply}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> applyRequirementTemp(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@PathVariable("apply") boolean apply,
			@RequestBody ComMessage<?, ?> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = approvalService.applyRequirementTemp(requirementId, apply);
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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 요건 삭제
	 * API ID : REST-D01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
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
			value = "/requirements/{requirementId}",
			params = "method=DELETE",
			method = RequestMethod.POST,
			headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteRequirement(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, ?> comMessage,
			Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("RequirementController.deleteRequirement", "requirementId", requirementId, messageSource, locale);
			}

			try{
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate();
				//----------------------------------------------------------------------------
				//요건삭제실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.deleteRequirement(requirementId, modId, modDate );
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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.delete.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.deleteRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				//------------------------------------------------------
				// change :
				// date : 2015-08-24-04:00:00
				// 변경사유:
				// 직접리턴하지 않고 예외를 던져서 처리하도록 소스 변경한 사유는
				// AOP 클래스인 ControllerAspect 에서 에러로깅 및 예외를 잡아서
				// 프론트로 응답을 주도록 하는 공통처리를 하기 위함.
				//------------------------------------------------------
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;
				throw new ControllerException(errorCd, errorMsg, e);

			}
		}
	}

	/**
	 * <pre>
	 * 요건 삭제 테스트 유닛 메소드
	 * API ID : REST-D01-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value = "/requirements/{requirementId}",
			params = {"method=DELETE", "isTest=true" },
			method = RequestMethod.POST,
			headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Requirement> testDeleteRequirement(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, Requirement> comMessage, Locale locale, HttpServletRequest request)
			throws Exception {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{

			return null;
		}
	}

	/**
	 * <pre>
	 * 요건 복원
	 * API ID : REST-U05-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId 요건ID
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
			value = "/requirements/{requirementId}/restore",
			params = "method=PUT",
			method = RequestMethod.POST,
			headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> restoreRequirement(
			HttpSession httpSession,
			@PathVariable("requirementId") String requirementId,
			@RequestBody ComMessage<?, ?> comMessage,
			Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("RequirementController.restoreRequirement", "requirementId", requirementId, messageSource, locale);
			}

			try{
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate();
				//----------------------------------------------------------------------------
				//요건삭제실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = requirementService.restoreRequirement(requirementId, modId, modDate );
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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.restoreRequirement",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}
		}
	}


	/**
	 * <pre>
	 * TO-DO LIST(결재대상)
	 * APP ID : REST-R01-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/approval-target/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementApprovalTargetList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");



			List<Map> todoList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				todoList = requirementService.getRequirementApprovalTargetList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (todoList == null || todoList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(todoList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * TO-DO LIST(결재진행)
	 * APP ID : REST-R02-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/approval-ing/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementApprovalIngList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");


			List<Map> todoList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				todoList = requirementService.getRequirementApprovalIngList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (todoList == null || todoList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(todoList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * TO-DO LIST(결재반려)
	 * APP ID : REST-R03-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/approval-reject/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementApprovalRejectList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");


			List<Map> todoList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				todoList = requirementService.getRequirementApprovalRejectList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (todoList == null || todoList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(todoList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}





	/**
	 * <pre>
	 * TO-DO LIST(개발대상)
	 * APP ID : REST-R04-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/development-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementDevelopmentList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<Map> changeList = null;

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");


			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				changeList = requirementService.getRequirementDevelopmentList(user.getUserId(),user.getRole().getIsInterfaceAdmin());
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
				if (changeList == null || changeList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(changeList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * TO-DO LIST(테스트대상)
	 * APP ID : REST-R05-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/test-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementTestList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");


			List<Map> changeList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				changeList = requirementService.getRequirementTestList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (changeList == null || changeList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(changeList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * TO-DO LIST(이행대상)
	 * APP ID : REST-R06-AN-04-00-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/todo-list/real-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementRealList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");

			List<Map> changeList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				changeList = requirementService.getRequirementRealList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (changeList == null || changeList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(changeList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 요건상태변경  List 조회 REST-R04-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/change-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementChangeList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");

			List<Map> changeList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				changeList = requirementService.getRequirementChangeList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (changeList == null || changeList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(changeList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
	/**
	 * <pre>
	 * 개발/테스트/운영이관 대상요건 List 조회 REST-R11-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/dtm-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getRequirementDTMList(
			HttpSession httpSession,
			@PathVariable("userId")String userId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");

			List<Map> changeList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				changeList = requirementService.getRequirementDTMList(user.getUserId(), user.getRole().getIsInterfaceAdmin());
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
				if (changeList == null || changeList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(changeList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 *
	 *
	 * <pre>
	 * 요건승인정보 조회 REST-R05-AN-01-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param requirementId the requirement id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/{requirementId}/approval-info", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, RequirementApproval> getRequirementApprovalInfo(
			HttpSession httpSession,
			@PathVariable("requirementId")String requirementId,
			@RequestBody ComMessage<?, RequirementApproval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			RequirementApproval approval = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				approval = requirementService.getRequirementApproval(requirementId);
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
				if (approval == null ) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(approval);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}




	/**
	 * <pre>
	 * 관심/주요인터페이스  조회
	 * API ID : REST-R01-AN-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requirements/principal/{typeCd}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getRequirementPrincipalList(
			HttpSession httpSession,
			@PathVariable("typeCd")String principalType,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			params.put("principalType",principalType);
			{
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}


			}
			List<Map> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = requirementService.getRequirementPrincipalList(params);
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
	 * 관심/주요배치 등록
	 * API ID : REST-C01-AN-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/principal/{typeCd}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> addRequirementPrincipal(
			HttpSession httpSession,
			@PathVariable("typeCd")String principalType,
			@RequestBody ComMessage<Map, Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List<Map> interfaceList = null;

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("addRequirementPrincipal principal param dump:\n", FieldCheckUtil.jsonDump(comMessage.getRequestObject())));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
			}
			String regId = comMessage.getUserId();
			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					Map param = comMessage.getRequestObject();
					interfaceList = (List<Map>) param.get("interfaceList");
					for(Map interfaceId : interfaceList)
					{
						res = requirementService.insertInterfacePrincipalMap(interfaceId, principalType, regId);
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
					comMessage.setResponseObject(null);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.addRequirementPrincipal",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}

	/**
	 * <pre>
	 * 관심/주요배치 수정
	 * API ID : REST-U01-AN-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/principal/{interfaceId}/{typeCd}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> updateRequirementPrincipal(
			HttpSession httpSession,
			@PathVariable("interfaceId")String interfaceId,
			@PathVariable("typeCd")String principalType,
			@RequestBody ComMessage<Map, Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			String regId = comMessage.getUserId();
			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				//logger.debug(Util.join("updateRequirementPrincipal requirement param dump:\n", FieldCheckUtil.jsonDump(requirement)));

			}

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				Map param = comMessage.getRequestObject();
				String monitorYn ="N";
				if(param.get("monitorYn") != null){
					monitorYn = (String)param.get("monitorYn");
				}
				int res = 0;
				{
					res = requirementService.updateInterfacePrincipalMap(interfaceId, principalType,regId,monitorYn);
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
				String[] errorMsgParams = {"RequirementController.updateRequirementPrincipal",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}

	/**
	 * <pre>
	 * 관심/주요배치 일괄 수정
	 * API ID : REST-U02-AN-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/principal/{typeCd}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> updateAllRequirementPrincipal(
			HttpSession httpSession,
			@PathVariable("typeCd")String principalType,
			@RequestBody ComMessage<Map, Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			String regId = comMessage.getUserId();
			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				//logger.debug(Util.join("createRequirement requirement param dump:\n", FieldCheckUtil.jsonDump(requirement)));

			}

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				Map param = comMessage.getRequestObject();
				String monitorYn ="N";
				if(param.get("monitorYn") != null){
					monitorYn = (String)param.get("monitorYn");
				}
				int res = 0;
				{
					res = requirementService.updateAllInterfacePrincipalMap( (List<String>)param.get("interfaceList"), principalType,regId,monitorYn);
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
				String[] errorMsgParams = {"RequirementController.updateAllRequirementPrincipal",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}

	/**
	 * <pre>
	 * 관심/주요배치  삭제
	 * API ID : REST-D01-AN-02-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/requirements/principal/{typeCd}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> deleteRequirementPrincipal(
			HttpSession httpSession,
			@PathVariable("typeCd")String principalType,
			@RequestBody ComMessage<Map, Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List<Map> interfaceList = null;
			String regId = comMessage.getUserId();
			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("addRequirementPrincipal principal param dump:\n", FieldCheckUtil.jsonDump(comMessage.getRequestObject())));

			}

			try{
				int res = 0;
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				{
					Map param = comMessage.getRequestObject();
					interfaceList = (List<Map>) param.get("interfaceList");
					for(Map interfaceId : interfaceList)
					{
						res = requirementService.deleteInterfacePrincipalMap((String)interfaceId.get("interfaceId"), principalType, regId);
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
					comMessage.setResponseObject(null);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.deleteRequirementPrincipal",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
				//comMessage.setErrorCd(errorCd);
				//comMessage.setErrorMsg(errorMsg);
				//return comMessage;

			}finally{

			}

		}

	}




	/**
	 * <pre>
	 *  연계경로관리 조회
	 * API ID : REST-R01-AN-02-03
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
	@RequestMapping(value = "/requirements/nodes/paths", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Requirement>> getNodePathList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Requirement>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			{
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}


			}

			List<Requirement> list = null;
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{
				list = requirementService.getNodePathOfRequirementList(params);
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
