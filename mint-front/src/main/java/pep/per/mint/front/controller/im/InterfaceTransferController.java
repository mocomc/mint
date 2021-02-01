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

import java.io.File;
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

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.SystemGroup;
import pep.per.mint.common.data.basic.SystemPath;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.UserRole;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.im.InfraService;
import pep.per.mint.database.service.im.InterfaceMovementService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.service.im.InterfaceTransferService;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B>인터페이스 테스트 운영 이관 서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * IM-02-01-001	인터페이스 배포	REST-S01-IM-02-01
 * -------------------------------------------------------------
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class InterfaceTransferController {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceTransferController.class);


	//,'REST-S01-IM-04-03'     : '/im/export-interfaces?method=POST'											//인터페이스 내보내기 서버처리(현대해상)-170711
	//,'REST-S02-IM-04-04'     : '/im/import-interfaces?method=POST'											//인터페이스 들여오기 서버처리(현대해상)-170711

	/**
	 * service.
	 */
	@Autowired
	InterfaceTransferService interfaceTransferService;

	@Autowired
	InterfaceMovementService interfaceMovementService;

	@Autowired
	CommonService commonService;
	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;



	/**
	 * <pre>
	 * 인터페이스이관-전송
	 * API ID : REST-S01-IM-02-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
	 * @since version 1.0(2017.05)
	 */
	@RequestMapping(
			value="/interfaces/transfer/send",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> transferSend(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;

			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				params = (Map) comMessage.getRequestObject();
				String [] fieldNameList = {"requirementId","targetSystem"};
				FieldCheckUtil.checkRequired(Util.join(this.getClass().getName(),".transferSend"), fieldNameList, params, messageSource, locale);

			}

			//-----------------------------------------------
			//비지니스처리
			//-----------------------------------------------
			{
				String requirementId = (String)params.get("requirementId");
				String targetSystem = (String)params.get("targetSystem");


				interfaceTransferService.send(targetSystem, requirementId, comMessage.getUserId());
			}

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
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 인터페이스이관-수신
	 * 이관요청으로 수신된 요건 및 인터페이스 정보를 시스템에 반영한다.
	 * API ID : REST-S02-IM-02-01
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
	 * @since version 1.0(2017.05)
	 */
	@RequestMapping(
			value="/interfaces/transfer/receive",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Requirement, Requirement> transferReceive(
			HttpSession  httpSession,
			@RequestBody ComMessage<Requirement, Requirement> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			//-------------------------------------------------
			// 수신객체 처리
			//-------------------------------------------------
			Requirement requirement = null;
			{
				requirement = comMessage.getRequestObject();
			}

			//-----------------------------------------------
			//비지니스 처리
			//-----------------------------------------------
			requirement = interfaceTransferService.receive(requirement);

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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setResponseObject(requirement);

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 인터페이스내보내기(서버사이드처리)
	 * API ID : REST-S01-OP-04-03
	 * 인터페이스 상세 내역을 JSON 포멧의 일정 건수로 구성된 페이지 개수만큼 파일로 내보낸다.
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
	 * @since version 1.0(2017.07)
	 */
	@RequestMapping(
			value="/export-interfaces",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> exportInterfaces(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, Map> comMessage,
			Locale locale, HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;

			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				//params = (Map) comMessage.getRequestObject();
				//String [] fieldNameList = {"countPerPage"};
				//FieldCheckUtil.checkRequired(Util.join(this.getClass().getName(),".exportInterfaces"), fieldNameList, params, messageSource, locale);

			}

			int countPerPage = 1000;
			{
				//-----------------------------------------------
				//비지니스처리
				//-----------------------------------------------
				Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> countPerPages = environmentalValues.get("system.interface.transfer.per.page");
				if (countPerPages != null && countPerPages.size() > 0) {
					countPerPage = Integer.parseInt(countPerPages.get(0));
				}

				//결과컴럼중 업무의 부모 업무를 보여줄것인지 여부(Y/N) 세팅
				//system.interface.business.path.view
				/*Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> countPerPages = environmentalValues.get("system.interface.transfer.per.page");
				int countPerPage = 0;
				if (countPerPages != null && countPerPages.size() > 0) {
					countPerPage = Integer.parseInt(countPerPages.get(0));
				}else{
					throw new Exception("포털환경설정필수값누락:system.interface.transfer.per.page");
				}

				List<String> exportPaths = environmentalValues.get("system.interface.transfer.export.path");
				String exportPath = "";
				if (exportPaths != null && exportPaths.size() > 0) {
					exportPath = (String)exportPaths.get(0);
				}else{
					throw new Exception("포털환경설정필수값누락:system.interface.transfer.export.path");
				}

				String compressYn = "N";
				List<String> compressYns = environmentalValues.get("system.interface.transfer.zip.yn");
				if (compressYns != null && compressYns.size() > 0) {
					compressYn = (String)compressYns.get(0);
				}else{
					throw new Exception("포털환경설정필수값누락:system.interface.transfer.zip.yn");
				}
				String compressFilePath = null;
				String compressFileName = null;
				if("Y".equalsIgnoreCase(compressYn)){
					List<String> compressFilePaths = environmentalValues.get("system.interface.transfer.zip.path");
					if (compressFilePaths != null && compressFilePaths.size() > 0) {
						compressFilePath = (String)compressFilePaths.get(0);
					}else{
						throw new Exception("포털환경설정필수값누락:system.interface.transfer.zip.path");
					}
					List<String> compressFileNames = environmentalValues.get("system.interface.transfer.zip.name");
					if (compressFileNames != null && compressFileNames.size() > 0) {
						compressFileName = (String)compressFileNames.get(0);
						compressFileName = Util.join(compressFileName, "-",Util.getFormatedDate() ,".zip");
					}else{
						throw new Exception("포털환경설정필수값누락:system.interface.transfer.zip.name");
					}
				}*/
				String compressYn = "Y";
				String exportPath = Util.join(servletContext.getRealPath("/"), File.separator, "export");
				Util.delete(new File(exportPath));

				exportPath = Util.join(servletContext.getRealPath("/"), File.separator, "export", File.separator, "data");

				new File(exportPath).mkdirs();

				String compressFilePath = Util.join(servletContext.getRealPath("/"), File.separator, "export");
				String compressFileName = Util.join("export-",Util.getFormatedDate() ,".zip");

				Map result = interfaceMovementService.exportInterfaces(countPerPage,exportPath,compressYn,compressFilePath, compressFileName);
				//result.put("compressYn", compressYn);
				//result.put("exportPath", exportPath);
				//result.put("compressFilePath",compressFilePath);
				//result.put("compressFileName",compressFileName);

				String exportFilePath = Util.join(servletContext.getContextPath(), "/export/" , compressFileName);
				result.put("exportFile",compressFileName);
				result.put("exportFilePath",exportFilePath);

				comMessage.setResponseObject(result);

			}

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
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


}
