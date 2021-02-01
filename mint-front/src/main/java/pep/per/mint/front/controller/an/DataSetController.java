/**
 * Copyright 2017 ~ 2020 Mocomsys's TA Team, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package pep.per.mint.front.controller.an;

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
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.dataset.DataSetInterfaceMap;
import pep.per.mint.common.data.basic.dataset.DataSetList;
import pep.per.mint.common.data.basic.dataset.DataSetMap;
import pep.per.mint.common.data.basic.dataset.DataSetTemp;
import pep.per.mint.common.data.basic.dataset.MetaField;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.DataSetService;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>데이터셋[DataSet] CRUD  서비스 제공 RESTful Controller</B>
 * </pre>
 *
 * <blockquote>
 *
 * @author TA(Technical Architecture) team.
 */
@Controller
@RequestMapping("/an")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataSetController {

	private static final Logger logger = LoggerFactory.getLogger(DataSetController.class);

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The DataSet service.
	 * 데이터셋 서비스를 구현한 객체
	 */
	@Autowired
	DataSetService dataSetService;

	@Autowired
	CommonService commonService;


	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	/**
	 * <pre>
	 * 데이터셋 리스트 조회 [REST-R01-AN-05-01]
	 * -------------------------------------
	 * PROBLEM.20170116 : mybatis에서 결과를 List 에 담을 때 objectType 프로퍼티를 추가해 주지 못하고 있다.
	 * Util.toJsonString을 통해 호출된 List<class> 형태로 리턴하는  RESTful 서비스 결과를 프린트 해보면 확인 가능하다.
	 * 이경우 Javascript 영역에서는 현재 문제 없이 처리되고 있다.
	 * 원인 확인되면 초치를 치할 예정임
	 * 해결책1) DataSetList extends ArrayList<DataSet> 을 만들어 WrappingList를 사용한다.
	 *
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
	@RequestMapping(value = "/datasets", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, DataSetList> getDataSetList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, DataSetList> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			if (params != null){
				String paramString = FieldCheckUtil.paramString(params);
				logger.debug(Util.join("\nparamString:[", paramString, "]"));
			}


			DataSetList list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getDataSetList(params);
				//try{logger.debug(Util.join("result>", Util.toJSONString(list))); }catch(Exception e){}
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 데이터셋 조회 [REST-R02-AN-05-01]
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
	@RequestMapping(value = "/datasets/{dataSetId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, DataSet> getDataSet(
			HttpSession httpSession,
			@PathVariable("dataSetId") String dataSetId,
			@RequestBody ComMessage<?, DataSet> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("DataSetController.getDataSet", "dataSetId", dataSetId, messageSource, locale);
			}

			DataSet dataSet = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				dataSet = dataSetService.getDataSet(dataSetId, null);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (dataSet == null) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 데이터셋 등록 [REST-C01-AN-05-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSet, DataSet> createDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSet, DataSet> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSet dataSet = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("DataSetController.createDataSet", dataSet, messageSource, locale);
				logger.debug("DataSet {0}", dataSet);
				logger.debug( dataSet.getIsRoot());
			}


			//----------------------------------------------------------------------------
			//등록실행
			//----------------------------------------------------------------------------
			try{

				{
					dataSet.setRegId(comMessage.getUserId());
					dataSet.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
					dataSet = dataSetService.createDataSetWithHistory(dataSet);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.createDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * 데이터셋 수정 [REST-U01-AN-05-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSet, DataSet> modifyDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSet, DataSet> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSet dataSet = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("DataSetController.createDataSet", dataSet, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				String modId = comMessage.getUserId();
				String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				dataSet.setModId(modId);
				dataSet.setModDate(modDate);
			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					res = dataSetService.modifyDataSetWithHistory(dataSet);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.modifyDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 데이터셋 삭제 [REST-D01-AN-05-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param dataSetId 삭제할 데이터셋아이디
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/{dataSetId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage,
			@PathVariable("dataSetId") String dataSetId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";



			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					String modId = comMessage.getUserId();
					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
					res = dataSetService.deleteDataSetWithHistory(dataSetId, modDate, modId);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
				String[] errorMsgParams = {"DataSetController.deleteDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 데이터셋 임시저장 [REST-S02-AN-05-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/service/save-temporary", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSet, DataSet> saveTemporary(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSet, DataSet> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSet dataSet = comMessage.getRequestObject();



			//----------------------------------------------------------------------------
			//등록실행
			//----------------------------------------------------------------------------
			try{

				{


					DataSetTemp dataSetTemp = new DataSetTemp();
					dataSetTemp.setContents(Util.toJSONString(dataSet));
					dataSetTemp.setRegId(comMessage.getUserId());
					dataSetTemp.setUserId(comMessage.getUserId());
					dataSetTemp.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

					int res = dataSetService.saveTemporary(dataSetTemp);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.saveTemporary",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * 데이터셋 승인 + 히스토리저장[REST-S01-AN-05-01]
	 * 별다른 요구사항이 현재는 없으니 데이터셋 테이블의 use 값을 'Y' 업데이트 시키는 것으로 처리한다.
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param dataSetId 승인할  데이터셋아이디
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/service/approval/{dataSetId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> approvalDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage,
			@PathVariable("dataSetId") String dataSetId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";



			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					String modId = comMessage.getUserId();
					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
					res = dataSetService.approvalDataSetWithHistory(dataSetId, modId, modDate);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
				String[] errorMsgParams = {"DataSetController.approvalDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}



	/**
	 * <pre>
	 * 데이터셋 비교 [REST-S03-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/service/comparison/dataset-map", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<String>, Map<String,DataSet>> getCompareDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<List<String>, Map<String,DataSet>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<String> params = comMessage.getRequestObject();
			if(params == null) params = new ArrayList<String>();


			DataSetMap map = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				map = dataSetService.getCompareDataSet(params);
				//try{logger.debug(Util.join("result>", Util.toJSONString(list))); }catch(Exception e){}
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (map == null || map.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
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
	 * 데이터셋 버전 리스트 조회 [REST-R04-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/versions/version-list/{dataSetId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<String>> getDataSetVersionList(
			HttpSession httpSession,
			@RequestBody ComMessage<?, List<String>> comMessage,
			@PathVariable("dataSetId") String dataSetId,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<String> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getDataSetVersionList(dataSetId);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 특정 버전 데이터셋 조회  [REST-R05-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param dataSetId
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/versions/{dataSetId}/{version}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, DataSet> getDataSetByVersion(
			HttpSession httpSession,
			@PathVariable("dataSetId") String dataSetId,
			@PathVariable("version") String version,
			@RequestBody ComMessage<?, DataSet> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


		{
			String errorCd = "";
			String errorMsg = "";
			DataSet dataSet = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				dataSet = dataSetService.getDataSetByVersion(dataSetId, version);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (dataSet == null) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 버전별, 데이터셋 map 조회  [REST-R06-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/versions/version-map/{dataSetId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, DataSetMap> getDataSetMapByVersion(
			HttpSession httpSession,
			@RequestBody ComMessage<?, DataSetMap> comMessage,
			@PathVariable("dataSetId") String dataSetId,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSetMap map = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				map = dataSetService.getDataSetMapByVersion(dataSetId);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (map == null || map.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
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
	 * 입시 저장 데이터셋 LIST 조회 [REST-R07-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param dataSetId
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/temporary/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, DataSet> getLastTempSavedDataSet(
			HttpSession httpSession,
			@PathVariable("userId") String userId,
			@RequestBody ComMessage<?, DataSet> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


		{
			String errorCd = "";
			String errorMsg = "";
			DataSet dataSet = null;


			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");

			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				dataSet = dataSetService.getLastTempSavedDataSet(user.getUserId());
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (dataSet == null) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}



	/**
	 * <pre>
	 * 입시 저장 데이터셋 리스트 조회 [REST-R08-AN-05-01]
	 * <pre>
	 * @param httpSession
	 * @param dataSetId
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/temporary-list/{userId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<DataSet>> getTemporarySavedDataSetList(
			HttpSession httpSession,
			@PathVariable("userId") String userId,
			@RequestBody ComMessage<Map<String,String>, List<DataSet>> comMessage,
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

			List<DataSet> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getTemporarySavedDataSetList(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 메타필드 검색 [REST-R09-AN-05-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/meta-fields", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<MetaField>> getMetaFieldList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<MetaField>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();


			List<MetaField> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getMetaFieldList(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 데이터셋 검색조건 데이터셋ID, 명 , CD 리스트 조회
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/cds/nms", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map>> getDataSetNameList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		String errorCd = "";
		String errorMsg = "";

		List<Map> list = null;
		//--------------------------------------------------
		//리스트 조회 실행
		//--------------------------------------------------
		{
			Map params = comMessage.getRequestObject();
			if(params == null) {
				params = new HashMap();
				params.put("isRoot","Y");
			}
			list = dataSetService.getDataSetNameList(params);
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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


	/**
	 * 데이터셋 검색조건 데이터셋 인터페이스 ID, 인터페이스 명 조회
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/cds/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map>> getInterfaceList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {
		String errorCd = "";
		String errorMsg = "";

		List<Map> list = null;
		//--------------------------------------------------
		//리스트 조회 실행
		//--------------------------------------------------
		{
			list = dataSetService.getInterfaceList();
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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


	/**
	 * 데이터셋 검색조건 등록자 리스트 조회
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/cds/reg-users" , params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map>> getRegUserList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {
		String errorCd = "";
		String errorMsg = "";

		List<Map> list = null;
		//--------------------------------------------------
		//리스트 조회 실행
		//--------------------------------------------------
		{
			list = dataSetService.getRegUserList();
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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




	/**
	 * <pre>
	 * 데이터셋 인터페이스 맵핑
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/dim", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSetInterfaceMap, DataSetInterfaceMap> setDataSetInterfaceMap(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSetInterfaceMap, DataSetInterfaceMap> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSetInterfaceMap dim = comMessage.getRequestObject();


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					res = dataSetService.setDataSetInterfaceMap(dim);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dim);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.setDataSetInterfaceMap",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}



	/**
	 * 데이터셋인터페이스매핑 리스트 조회
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/dim", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<DataSetInterfaceMap>> getDataSetInterfaceMap(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<DataSetInterfaceMap>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			FieldCheckUtil.checkRequired("DataSetController.getDataSetInterfaceMap","interfaceId", params, messageSource, locale);


			List<DataSetInterfaceMap> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getDataSetInterfaceMap(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
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
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 데이터셋 리스트 조회 - Simple [REST-R01-AN-06-00]
	 * -------------------------------------
	 * PROBLEM.20170116 : mybatis에서 결과를 List 에 담을 때 objectType 프로퍼티를 추가해 주지 못하고 있다.
	 * Util.toJsonString을 통해 호출된 List<class> 형태로 리턴하는  RESTful 서비스 결과를 프린트 해보면 확인 가능하다.
	 * 이경우 Javascript 영역에서는 현재 문제 없이 처리되고 있다.
	 * 원인 확인되면 초치를 치할 예정임
	 * 해결책1) DataSetList extends ArrayList<DataSet> 을 만들어 WrappingList를 사용한다.
	 *
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
	@RequestMapping(value = "/datasets/simple", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, DataSetList> getSimpleDataSetList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, DataSetList> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

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
			// calculating startIndex and endIndex value
			//--------------------------------------------------
			{
				int startIndex = 0;
				int endIndex = 0;

				int page     = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
				int perCount = params.get("perCount") == null ? 20 : Integer.parseInt(params.get("perCount").toString());

				startIndex = (page - 1) * perCount + 1;
				endIndex = startIndex + perCount - 1;

				params.put("startIndex",startIndex);
				params.put("endIndex",endIndex);
				params.put("page",page);
				params.put("perCount",perCount);

			}


			DataSetList list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				int totalCount = params.get("total") == null ? 0 : Integer.parseInt(params.get("total").toString());
				if( totalCount == 0 ) {
					totalCount = dataSetService.getSimpleDataSetListCount(params);
				}

				String pageUsed = commonService.getEnvironmentalValue("layout", "page.used", "Y");
				boolean isPage = !Util.isEmpty(pageUsed) && pageUsed.equals("Y") ? true : false;
				if(totalCount > 0) {
					if(isPage)
						list = dataSetService.getSimpleDataSetListByPage(params);
					else
						list = dataSetService.getSimpleDataSetList(params);
				}

				params.put("total",totalCount);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 데이터셋 조회 [REST-R02-AN-06-00]
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
	@RequestMapping(value = "/datasets/simple/{dataSetId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, DataSet> getSimpleDataSet(
			HttpSession httpSession,
			@PathVariable("dataSetId") String dataSetId,
			@RequestBody ComMessage<?, DataSet> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("DataSetController.getDataSet", "dataSetId", dataSetId, messageSource, locale);
			}

			DataSet dataSet = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				dataSet = dataSetService.getSimpleDataSet(dataSetId, null);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (dataSet == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 메타정보 조회 [REST-R03-AN-06-00]
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
	@RequestMapping(value = "/datasets/simple/metatata/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Map<String,String>>> getSimpleMetaDataList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, List<Map<String,String>>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


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

			List<Map<String, String>> list = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getSimpleMetaDataList(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 메타체크 [REST-R04-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple/metadata/check/{isCamel}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<DataField>, List<DataField>> getSimpleMetaDataCheck(
			HttpSession httpSession,
			@RequestBody ComMessage<List<DataField>, List<DataField>> comMessage,
			@PathVariable("isCamel") boolean isCamel,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<DataField> list = comMessage.getRequestObject();

			//--------------------------------------------------
			// MetaData Check 실행
			//--------------------------------------------------
			{
				Map complexType = FrontEnvironments.complexType;
				list = dataSetService.getSimpleMetaDataCheck(list, complexType, isCamel);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * DataSetCd 존재여부 체크 [REST-R05-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple/exist/dataSetCd/{dataSetCd}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> getExistDataSetCd(
			HttpSession httpSession,
			@PathVariable("dataSetCd") String dataSetCd,
			@RequestBody ComMessage<?, String> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			int result = dataSetService.getExistDataSetCd(dataSetCd);
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {result};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(String.valueOf(result));

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * DataSet 사용여부 체크 [REST-R06-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple/used/dataSetId/{dataSetId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> getUsedDataSet(
			HttpSession httpSession,
			@PathVariable("dataSetId") String dataSetId,
			@RequestBody ComMessage<?, String> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			int result = dataSetService.getUsedDataSet(dataSetId);
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {result};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(String.valueOf(result));

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 데이터셋 등록 [REST-C01-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple", params = {"method=POST", "isTest=false"}, method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSet, DataSet> createSimpleDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSet, DataSet> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSet dataSet = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("DataSetController.createDataSet", dataSet, messageSource, locale);
				logger.debug("DataSet {0}", dataSet);
				logger.debug( dataSet.getIsRoot());
			}


			//----------------------------------------------------------------------------
			//등록실행
			//----------------------------------------------------------------------------
			try{

				{
					dataSet.setRegId(comMessage.getUserId());
					dataSet.setModId(comMessage.getUserId());
					dataSet.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
					dataSet.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

					//dataSet = dataSetService.createDataSetWithHistory(dataSet);
					int res = dataSetService.createSimpleDataSet(dataSet);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.createDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}

	/**
	 * <pre>
	 * 데이터셋 수정 [REST-U01-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataSet, DataSet> modifySimpleDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<DataSet, DataSet> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataSet dataSet = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				FieldCheckUtil.check("DataSetController.createDataSet", dataSet, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				dataSet.setRegId(comMessage.getUserId());
				dataSet.setModId(comMessage.getUserId());
				dataSet.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				dataSet.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					//res = dataSetService.modifyDataSetWithHistory(dataSet);
					res = dataSetService.modifySimpleDataSet(dataSet);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataSet);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.modifyDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 데이터셋 삭제 [REST-D01-AN-06-00]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param dataSetId 삭제할 데이터셋아이디
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datasets/simple/{dataSetId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteSimpleDataSet(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage,
			@PathVariable("dataSetId") String dataSetId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";



			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					String modId = comMessage.getUserId();
					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
					res = dataSetService.deleteSimpleDataSet(dataSetId, modDate, modId);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
				String[] errorMsgParams = {"DataSetController.deleteDataSet",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}



	/**
	 * 데이터셋 검색조건 조회 [REST-R00-AN-06-01]
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/cds", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, List>> getDataMapSearchParameterList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Map<String, List>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		String errorCd = "";
		String errorMsg = "";

		Map list = null;
		//--------------------------------------------------
		//리스트 조회 실행
		//--------------------------------------------------
		{
			Map params = comMessage.getRequestObject();
			if(params == null) {
				params = new HashMap();
			}
			list = dataSetService.getDataMapSearchParameterList(params);
		}
		//--------------------------------------------------
		// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
		//--------------------------------------------------
		{
			comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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

	/**
	 * <pre>
	 * 데이터 맵핑 리스트 조회 - Simple [REST-R01-AN-06-01]
	 * -------------------------------------
	 * PROBLEM.20170116 : mybatis에서 결과를 List 에 담을 때 objectType 프로퍼티를 추가해 주지 못하고 있다.
	 * Util.toJsonString을 통해 호출된 List<class> 형태로 리턴하는  RESTful 서비스 결과를 프린트 해보면 확인 가능하다.
	 * 이경우 Javascript 영역에서는 현재 문제 없이 처리되고 있다.
	 * 원인 확인되면 초치를 치할 예정임
	 * 해결책1) DataSetList extends ArrayList<DataSet> 을 만들어 WrappingList를 사용한다.
	 *
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
	@RequestMapping(value = "/datamaps/simple", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<DataMap>> getSimpleDataMapList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<DataMap>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			if (params != null){
				String paramString = FieldCheckUtil.paramString(params);
				logger.debug(Util.join("\nparamString:[", paramString, "]"));
			}


			List<DataMap> list = null;
			//--------------------------------------------------
			//리스트 조회 실행
			//--------------------------------------------------
			{
				list = dataSetService.getSimpleDataMapList(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
	 * 데이터 맵핑 조회 [REST-R02-AN-06-01]
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
	@RequestMapping(value = "/datamaps/simple/{mapId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Map> getSimpleDataMap(
			HttpSession httpSession,
			@PathVariable("mapId") String mapId,
			@RequestBody ComMessage<?, Map> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


		{
			String errorCd = "";
			String errorMsg = "";

			//--------------------------------------------------
			//파라메터 {requirementId} 체크
			//--------------------------------------------------
			{
				//FieldCheckUtil.checkRequired("DataSetController.getDataSet", "dataSetId", dataSetId, messageSource, locale);
			}

			Map<String, Object> resultMap = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				resultMap = dataSetService.getSimpleDataMap(mapId, null);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (resultMap == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(resultMap);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * MapCd 존재여부 체크 [REST-R03-AN-06-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/simple/exist/mapCd/{mapCd}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> getExistMapCd(
			HttpSession httpSession,
			@PathVariable("mapCd") String mapCd,
			@RequestBody ComMessage<?, String> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			int result = dataSetService.getExistMapCd(mapCd);
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {result};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(String.valueOf(result));

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * DataMap 사용여부 체크 [REST-R04-AN-06-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/simple/used/mapId/{mapId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, String> getUsedDataMap(
			HttpSession httpSession,
			@PathVariable("mapId") String mapId,
			@RequestBody ComMessage<?, String> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			int result = dataSetService.getUsedDataMap(mapId);
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {result};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(String.valueOf(result));

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 데이터매핑 등록 [REST-C01-AN-06-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/simple", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataMap, DataMap> createSimpleDataMap(
			HttpSession httpSession,
			@RequestBody ComMessage<DataMap, DataMap> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataMap dataMap = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("DataSetController.createDataSet", dataSet, messageSource, locale);
				//logger.debug("DataSet {0}", dataSet);
				//logger.debug( dataSet.getIsRoot());
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				dataMap.setRegId(comMessage.getUserId());
				dataMap.setModId(comMessage.getUserId());
				dataMap.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				dataMap.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}


			//----------------------------------------------------------------------------
			//등록실행
			//----------------------------------------------------------------------------
			try {

				{
					int res = dataSetService.createSimpleDataMap(dataMap);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataMap);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.createSimpleDataMap",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 데이터 맵핑 수정 [REST-U01-AN-06-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/simple", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<DataMap, DataMap> modifySimpleDataMap(
			HttpSession httpSession,
			@RequestBody ComMessage<DataMap, DataMap> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			DataMap dataMap = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("DataSetController.createDataSet", dataMap, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				dataMap.setRegId(comMessage.getUserId());
				dataMap.setModId(comMessage.getUserId());
				dataMap.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				dataMap.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					//res = dataSetService.modifyDataSetWithHistory(dataSet);
					res = dataSetService.modifySimpleDataMap(dataMap);
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(dataMap);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DataSetController.modifySimpleDataMap",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 데이터 맵핑 삭제 [REST-D01-AN-06-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param map 삭제할 데이터 맵핑 아이디
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/datamaps/simple/{mapId}", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> deleteSimpleDataMap(
			HttpSession httpSession,
			@RequestBody ComMessage<?, ?> comMessage,
			@PathVariable("mapId") String mapId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					String modId = comMessage.getUserId();
					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

					//TODO :: 필요시 환경설정 처리
					boolean isPhysicalDelete = true;
					if(isPhysicalDelete) {
						res = dataSetService.deleteSimplePhysicalAllDataMap(mapId);
					} else {
						res = dataSetService.deleteSimpleDataMap(mapId, modId, modDate);
					}

				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
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
				String[] errorMsgParams = {"DataSetController.deleteSimpleDataMap",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 인터페이스 - 데이터 맵핑 조회 [REST-R01-AN-06-03]
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
	@RequestMapping(value = "/datamaps/interface-map", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> getSimpleDataMapInterfaceMap(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, Map> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {


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

			Map<String, Object> resultMap = null;
			//--------------------------------------------------
			//조회 실행
			//--------------------------------------------------
			{
				resultMap = dataSetService.getSimpleDataMapInterfaceMap(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (resultMap == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(resultMap);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}




}
