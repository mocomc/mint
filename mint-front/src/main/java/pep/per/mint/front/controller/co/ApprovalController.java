/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */
package pep.per.mint.front.controller.co;

// com.mocomsys.iip.inhouse.wrapper.MySingleServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.ApprovalService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


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
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-C01-CO-02-00-010</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">결재요청</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #requestApproval(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/an/requirements</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author Solution TF
 */
@Controller
@RequestMapping("/co")
public class ApprovalController {

	private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);

	/**
	 * The Message source.
	 */
	//비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Approval service.
	 */
	//승인요청 서비스를 구현한 객체
	@Autowired
	ApprovalService approvalService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;



	/**
	 * <pre>
	 * 결재요청(등록), 결재요청(삭제), 결재요청(이행)
	 * API ID : REST-C01-CO-02-00-010
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
	@RequestMapping(value = "/approval", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Approval, Approval> requestApproval(
			HttpSession httpSession,
			@RequestBody ComMessage<Approval, Approval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			Approval approval = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 200303 임시 주석처리 :: Util.isEmpty() 에 List size == 0 추가 로직으로, 아래 수행시 에러 처리
			// 추가로직에 따른 영향도 체크후 재변경 예정
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("ApprovalController.requestApproval", approval, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("ApprovalController.requestApproval param dump:\n", FieldCheckUtil.jsonDump(approval)));
			}



			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					//삼성 MISID 세팅
					{
						//httpSession.getId();
						StringBuffer misid = new StringBuffer();

						//String prefix = Util.rightPad(Util.join("iip", approval.getReqUserId(), approval.getApprovalItemType()), 15, "0");

						//--------------------------------------------------------------------------------------
						// 사용자아이디 길이가 100 바이트라 TCO0101.MISID Varchar(32) 보다 길어질경우 에러발생하여 임시방편으로 userId
						// prefix를 제거함 ,
						// 향후 테이블 필드 TCO0101.MISID 길이를 130 으로 늘려주어라
						//--------------------------------------------------------------------------------------
						String prefix = Util.rightPad(Util.join("iip", approval.getApprovalItemType()), 15, "0");
						misid.append(prefix).append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));//17
						approval.setMisid(misid.toString());
						approval.setReqDate(Util.getFormatedDate());
						approval.setReqUserId(comMessage.getUserId());
					}

					{
						res = approvalService.requestApproval(approval);
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
					comMessage.setResponseObject(approval);

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
	 * 결재요청(수정)
	 * API ID : REST-C02-CO-02-00-010
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
	@RequestMapping(value = "/approval-and-update", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Approval, Approval> requestApprovalWithUpdate(
			HttpSession httpSession,
			@RequestBody ComMessage<Approval, Approval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			Approval approval = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				if(approval != null && logger.isDebugEnabled())
					logger.debug(Util.join("ApprovalController.requestApprovalWithUpdate param dump:\n", FieldCheckUtil.jsonDump(approval)));
			}


			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("ApprovalController.requestApprovalWithUpdate", approval, messageSource, locale);
			}



			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					//삼성 MISID 세팅
					{
						//httpSession.getId();
						StringBuffer misid = new StringBuffer();

						//String prefix = Util.rightPad(Util.join("iip", approval.getReqUserId(), approval.getApprovalItemType()), 15, "0");

						//--------------------------------------------------------------------------------------
						// 사용자아이디 길이가 100 바이트라 TCO0101.MISID Varchar(32) 보다 길어질경우 에러발생하여 임시방편으로 userId
						// prefix를 제거함 ,
						// 향후 테이블 필드 TCO0101.MISID 길이를 130 으로 늘려주어라
						//--------------------------------------------------------------------------------------
						String prefix = Util.rightPad(Util.join("iip", approval.getApprovalItemType()), 15, "0");
						misid.append(prefix).append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));//17

						approval.setMisid(misid.toString());
					}

					{
						res = approvalService.requestApprovalWithUpdate(approval);
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
					comMessage.setResponseObject(approval);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementStatus",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);

				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}

	/**
	 * <pre>
	 * 결재처리
	 * API ID : REST-C03-CO-02-00-010
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
	@RequestMapping(value = "/approval/{apply}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Approval, Approval> applyApproval(
			HttpSession httpSession,
			@PathVariable("apply") boolean apply,
			@RequestBody ComMessage<Approval, Approval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			Approval approval = comMessage.getRequestObject();

			try{

				//----------------------------------------------------------------------------
				//등록ID, 등록시간 설정하기
				//----------------------------------------------------------------------------
				String regId   = comMessage.getUserId();
				String regDate = Util.getFormatedDate();

				approval.setReqUserId(regId);
				approval.setReqDate(regDate);
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					//삼성 MISID 세팅
					{
						//httpSession.getId();
						StringBuffer misid = new StringBuffer();

						//String prefix = Util.rightPad(Util.join("iip", approval.getReqUserId(), approval.getApprovalItemType()), 15, "0");

						//--------------------------------------------------------------------------------------
						// 사용자아이디 길이가 100 바이트라 TCO0101.MISID Varchar(32) 보다 길어질경우 에러발생하여 임시방편으로 userId
						// prefix를 제거함 ,
						// 향후 테이블 필드 TCO0101.MISID 길이를 130 으로 늘려주어라
						//--------------------------------------------------------------------------------------
						String prefix = Util.rightPad(Util.join("iip", approval.getApprovalItemType()), 15, "0");
						misid.append(prefix).append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));//17
						approval.setMisid(misid.toString());
					}
					res = approvalService.applyApproval(apply, approval);
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
					comMessage.setResponseObject(approval);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"RequirementController.updateRequirementStatus",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


	/**
	 * <pre>
	 * 가장최근 결재내역 조회
	 * REST-R01-CO-02-00-010
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param approvalItemType the approval item type
	 * @param approvalItemId the approval item id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(value = "/approval/{approvalItemType}/{approvalItemId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Approval> getApproval(
			HttpSession httpSession,
			@PathVariable("approvalItemType")String approvalItemType,
			@PathVariable("approvalItemId")String approvalItemId,
			@RequestBody ComMessage<?, Approval> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			Approval approval = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				approval = approvalService.getApproval(approvalItemId, approvalItemType);
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
	 * 결재 링크 키 발번
	 * REST-R02-CO-02-00-010
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
	@RequestMapping(value = "/approval/link/select", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> selectApprovalLink(
			HttpSession httpSession,
			@RequestBody ComMessage<?, String> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			String linkKey = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{

				linkKey = approvalService.selectApprovalLink(comMessage.getUserId());
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
				if (Util.isEmpty(linkKey)) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(linkKey);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 결재 링크 정보 조회
	 * REST-R03-CO-02-00-010
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
	@RequestMapping(value = "/approval/link", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<?,?>, Map<?,?>> getApprovalLinkInfo(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<?,?>, Map<?,?>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<?,?> res = null;
			String linkKey = null;

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				Map<?,?> params = comMessage.getRequestObject();
				FieldCheckUtil.checkRequired("ApprovalController.getApprovalLinkInfo", "linkKey", params, messageSource, locale);
				linkKey = (String)params.get("linkKey");
			}


			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{

				res = approvalService.getApprovalLinkInfo(linkKey);
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
				if (res == null) {// 결과가 없을 경우 비지니스 예외 처리

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


	/**
	 * <pre>
	 * 결재자  List 조회 REST-R04-CO-02-00-010
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
	@RequestMapping(value = "/approval/users", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<?,?>, List<ApprovalUser>> getApprovalUserList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<?,?>, List<ApprovalUser>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<ApprovalUser> list = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				Map<?,?> params = comMessage.getRequestObject();

				String [] fields = {"interfaceId", "channelId"};
				FieldCheckUtil.checkRequired("ApprovalController.getApprovalUserList", fields, params, messageSource, locale);
				String interfaceId = (String)params.get("interfaceId");
				String channelId = (String)params.get("channelId");
				list = approvalService.getApprovalUserList(interfaceId, channelId);
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
	 * 결재라인 조회 REST-R05-CO-02-00-010
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
	@RequestMapping(value = "/approval/line/users", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<?,?>, List<ApprovalUser>> getApprovalLineUserList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<?,?>, List<ApprovalUser>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<ApprovalUser> list = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				Map<?,?> params = comMessage.getRequestObject();

				String [] fields = {"channelId"};
				FieldCheckUtil.checkRequired("ApprovalController.getApprovalUserList", fields, params, messageSource, locale);
				String channelId = (String)params.get("channelId");
				list = approvalService.getApprovalLineUserList(channelId);
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
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


}
