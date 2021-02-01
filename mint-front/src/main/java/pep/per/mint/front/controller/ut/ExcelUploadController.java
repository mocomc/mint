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
package pep.per.mint.front.controller.ut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jxl.Workbook;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.inhouse.IExcelUploadService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.excel.tools.excel.ReaderSupport;
import pep.per.mint.front.excel.tools.excel.support.XLSReaderSupport;
import pep.per.mint.front.excel.tools.excel.support.XLSXReaderSupport;
import pep.per.mint.front.excel.tools.excel.xlsx.Cell;
import pep.per.mint.front.excel.tools.excel.xlsx.Sheet;
import pep.per.mint.front.excel.tools.excel.xlsx.SimpleXLSXWorkbook;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * The type File upload controller.
 */
@Controller
@RequestMapping("/ut")
public class ExcelUploadController {

	/**
	 * The Logger.
     */
	Logger logger = LoggerFactory.getLogger(ExcelUploadController.class);

	private final String EXCEL_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private final String EXCEL_TYPE_XLS  = "application/vnd.ms-excel";

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Env.
	 */
	@Autowired
	FrontEnvironments env;

	/**
	 * Requirement Service
	 */
	@Autowired
	RequirementService requirementService;

	/**
	 * The Common service.
     */
	@Autowired
	CommonService commonService;

	@Autowired
	//@Qualifier("inHouseExcelUploadService")
	IExcelUploadService excelUploadService;


	/**
	 * Handle file upload.
	 * Spring Multipart 처리시 JSON 데이터도 같이 바인딩 할 수 없는지 확인이 필요함.
	 *
	 * @param httpSession the http session
	 * @param file the file
	 * @return the hash map
	 * @throws Exception the exception
     */
	@RequestMapping(
    		value="/excel/upload",
    		method=RequestMethod.POST)
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> excelUpload(
    		HttpSession httpSession,
    		@RequestParam("comments") String comments,
    		@RequestParam("userId") String userId,
    		@RequestPart("excelFileUpload") MultipartFile multipartFile ) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage = new ComMessage<Map<String,Object>, List<Map<String,Object>>>();
			Map<String,Object> params = new HashMap<String,Object>();

			logger.info("[인터페이스 일괄등록 - 엑셀업로드시작]");

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", userId);
				params.put("regDate", Util.getFormatedDate());
			}

			File excelFile = null;

	    	try {
	            if (!multipartFile.isEmpty()) {

	 				logger.debug("----------------------------------------------------");
	 				logger.debug(Util.join("comments:", comments));
	 				logger.debug(Util.join("ContentType : ", multipartFile.getContentType()));
	 				logger.debug(Util.join("ComponentId : ", multipartFile.getName()));
	 				logger.debug(Util.join("File Name   : ", multipartFile.getOriginalFilename()));
	 				logger.debug(Util.join("File Size   : ", multipartFile.getSize()));
	 				logger.debug("----------------------------------------------------");


	        		String envFilePath = env.getAttachFilePath() + File.separator + userId;
	        		String filePostFix = UUID.randomUUID().toString().replaceAll("-", "");

	            	File filePath = new File(envFilePath);
	            	if (!filePath.isDirectory() || !filePath.exists()) {
	            		filePath.mkdirs();
	        		}

	 				excelFile = new File( filePath.getPath() + File.separator + filePostFix );
	 				multipartFile.transferTo( excelFile );

					//----------------------------------------------------------------------------
					// 엑셀 타입인지 체크한다.( XLSX or XLS )
					//----------------------------------------------------------------------------
	 				if( multipartFile.getContentType().equals(EXCEL_TYPE_XLSX) || multipartFile.getContentType().equals(EXCEL_TYPE_XLS) ) {

 						//----------------------------------------------------------------------------
 						// 엑셀 데이터를 배열로 파싱 한다.
 						//----------------------------------------------------------------------------
	 					List<String[]> excelList = null;
	 					List<String[]> excelheaderList = null;

	 					if( multipartFile.getContentType().equals(EXCEL_TYPE_XLSX) ) {
	 						excelList = getXLSXData(excelFile);
	 						excelheaderList = getXLSXHeader(excelFile);
	 					} else if ( multipartFile.getContentType().equals(EXCEL_TYPE_XLS) ) {
	 						excelList = getXLSData(excelFile);
	 						excelheaderList = getXLSHeader(excelFile);
	 					}

	 					if(excelheaderList == null || excelList == null) throw new Exception("Exception:excelheaderList == null || excelList == null");

	 					logger.debug("headersize::"+ excelheaderList.size());
 						//----------------------------------------------------------------------------
 						// validation check 를 진행하고 데이터의 문제가 없을 경우 Requirement Object 를 생성한다.
 						//----------------------------------------------------------------------------
	 					//List<Requirement> requirementList = excelUploadService.validate(excelList, params);
	 					List<Requirement> requirementList = excelUploadService.validate(excelList, excelheaderList, params);

 						//----------------------------------------------------------------------------
 						// 여기까지 Exception 이 없으면 엑셀 정보를 DB 에 인서트 한다.
 						// ( 처음 파싱된 엑셀 정보를 바로 DB 에 반영하지 않은 이유는 어느 정도 정제된 데이터를 저장하기 위함 )
 						//----------------------------------------------------------------------------
 						Map<String,Object> master = new HashMap<String, Object>();

 						master.put("batchId", "");
 						master.put("comments", comments);
 						master.put("batchCnt", Integer.valueOf(excelList == null ? 0 : excelList.size()));
 						master.put("resultCd",  "0");
 						master.put("resultMsg", "");
 						master.put("fileNm", multipartFile.getOriginalFilename());
 						master.put("filePath", filePath.getPath() + File.separator + filePostFix);
 						master.put("regUser", userId);
 						master.put("regDate", Util.getFormatedDate());
 						master.put("modUser", userId);
 						master.put("modDate", Util.getFormatedDate());

 						//----------------------------------------------------------------------------
 						// 디비 인서트가 정상적으로 성공하면 master.batchId 에 값이 셋팅된다.
 						//----------------------------------------------------------------------------
 						int resultCd = excelUploadService.insertExcelUpload(master, excelList);

 						//----------------------------------------------------------------------------
 						// 인터페이스 요건 정보를 생성한다.
 						//----------------------------------------------------------------------------
 						try {
	 						if( resultCd == 1 ) {
	 							resultCd = excelUploadService.createRequirement(requirementList, master);
	 						}
 						} catch(Exception e) {
 							errorCd = "9999";
 							errorMsg = e.getMessage();
 	 						//----------------------------------------------------------------------------
 	 						// 요건/인터페이스 생성중 에러발생시 디비에 저장한 데이터도 모두 삭제 하자 또는 향후 트렌젝션을 묶던가
 	 						//----------------------------------------------------------------------------
 							String[] strId =  { (String)master.get("batchId")};
 							params.put("batchId", strId);
 							//params.put("batchId", master.get("batchId"));
 							excelUploadService.deleteExcelUpload(params);
 						}

 						//----------------------------------------------------------------------------
 						// 디비에 저장된 Excel Upload Master Data 를 조회한다.
 						//----------------------------------------------------------------------------
						List<Map<String,Object>> masterInfo = excelUploadService.getExcelUploadMasterInfo(params);
						comMessage.setResponseObject(masterInfo);


 						/*
 						//----------------------------------------------------------------------------
 						// 엑셀 파싱후 디비에 바로 인서트 후 리턴시에 로직
 						//----------------------------------------------------------------------------

 						//----------------------------------------------------------------------------
 						// 여기까지 Exception 이 없으면 엑셀 정보를 DB 에 인서트 한다.
 						//----------------------------------------------------------------------------
 						Map<String,Object> master = new HashMap<String, Object>();

 						master.put("batchId", "");
 						master.put("comments", comments);
 						master.put("batchCnt", Integer.valueOf(excelList.size()));
 						master.put("resultCd",  "0");
 						master.put("errorMsg", "");
 						master.put("fileNm", "");
 						master.put("regUser", userId);
 						master.put("regDate", Util.getFormatedDate());
 						master.put("modUser", userId);
 						master.put("modDate", Util.getFormatedDate());

						excelUploadService.insertExcelUpload(master, excelList);
						List<Map<String,Object>> masterInfo = excelUploadService.getExcelUploadMasterInfo(params);
						comMessage.setResponseObject(masterInfo);
						*/

	 				} else {
 						//----------------------------------------------------------------------------
 						// 엑셀 포맷이 아니면 에러
 						//----------------------------------------------------------------------------
	 					errorCd  = "9999";
	 					errorMsg = "[엑셀업로드실패]:올바른 엑셀 포멧이 아닙니다.";
	 				}

	             } else {
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	     			errorCd = "9999";
	     			errorMsg = "[엑셀업로드실패]:업로드할 엑셀파일이 존재하지 않습니다.";
	             }

	             comMessage.setErrorCd(errorCd);
	             comMessage.setErrorMsg(errorMsg);

	    	} catch(Throwable e) {

				String errorDetail = "";
				PrintWriter pw = null;
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					pw = new PrintWriter(baos);
					e.printStackTrace(pw);
					pw.flush();
					if(pw != null) errorDetail = baos.toString();
				} finally {
					if(pw !=null) pw.close();
				}

				errorCd = "9999";
				errorMsg = e.getMessage();

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				comMessage.setErrorDetail(errorDetail);

				logger.error(Util.join("ExcelUploadController", errorDetail));

	    	} finally {
				//----------------------------------------------------------------------------
				// 별도의 요구사항이 없으면 업로드한 파일은 삭제
	    		// - 16.11.04 이규호 부장님 내용 확인 결과.. 일단 저장해두자는 의견이 있어 주석처리함.
				//----------------------------------------------------------------------------
	    		if(excelFile != null) {
		    		if(excelFile.exists()) {
		    			//excelFile.delete();
		    		}
	    		}
	    		if( !Util.isEmpty(errorMsg) ) {
	    			logger.error(errorMsg);
	    		}
	    		logger.info("[인터페이스 일괄등록 - 엑셀업로드종료]");
	    	}
	        return comMessage;
		}
    }

	/**
	 * XLSX Parsing
	 * @param excelFile
	 * @return
	 */
	private List<String[]> getXLSXData(File excelFile) {
		// 엑셀업로드 버전 체크.
		Map envMap;
		String upVer = "";
		try {
			envMap = commonService.getEnvironmentalValues();
			List attrList = (List)envMap.get("system.excel.upload.version");
			if(attrList != null && attrList.size()>0){
				upVer = (String)attrList.get(0);
			}
		} catch (Exception e) {
		}

		XLSXReaderSupport rs  = (XLSXReaderSupport) ReaderSupport.newInstance(ReaderSupport.TYPE_XLSX, excelFile );

		rs.open();
		SimpleXLSXWorkbook wb = rs.getWorkbook();

		Sheet sheet = wb.getSheet(2);
		List<Cell[]> rows = sheet.getRows();

		List<String[]> list = new LinkedList<String[]>();
		for( int i = 0; i < rows.size(); i++ ) {
			// Header 부분은 skip 한다.
			if("1".equals(upVer)){
				if( i < 5 ) {
					continue;
				}
			}else{
				if( i < 2 ) {
					continue;
				}
			}

			// Data 부분 설정.
			Cell[] cells = rows.get(i);
			String[] cols = new String[cells.length];
			for( int j = 0; j < cells.length; j++ ) {
				logger.trace(cells[j] +"");
				if(cells[j]== null){
					cols[j]= "";
				}else{
					cols[j] = Util.isEmpty(cells[j].getValue()) ? "" : cells[j].getValue();
				}

			}
			list.add(cols);

		}
		return list;
	}

	/**
	 * XLSX Parsing
	 * @param excelFile
	 * @return
	 */
	private List<String[]> getXLSXHeader(File excelFile) {
		Map envMap;
		String upVer = "";
		try {
			envMap = commonService.getEnvironmentalValues();
			List attrList = (List)envMap.get("system.excel.upload.version");
			if(attrList != null && attrList.size()>0){
				upVer = (String)attrList.get(0);
			}
		} catch (Exception e) {
		}

		XLSXReaderSupport rs  = (XLSXReaderSupport) ReaderSupport.newInstance(ReaderSupport.TYPE_XLSX, excelFile );

		rs.open();
		SimpleXLSXWorkbook wb = rs.getWorkbook();

		Sheet sheet = wb.getSheet(2);
		List<Cell[]> rows = sheet.getRows();

		List<String[]> list = new LinkedList<String[]>();
		for( int i = 0; i < rows.size(); i++ ) {
			// Data 부분 설정.
			Cell[] cells = rows.get(i);
			String[] cols = new String[cells.length];
			if("1".equals(upVer)){
				if(i==3 || i==2){
					for( int j = 0; j < cells.length; j++ ) {
						logger.trace(cells[j] +"");
						if(cells[j]== null){
							cols[j]= "";
						}else{
							cols[j] = Util.isEmpty(cells[j].getValue()) ? "" : cells[j].getValue();
						}
					}
					list.add(cols);
				}
				if( i >= 5 ) {
					break;
				}
			}else{
				for( int j = 0; j < cells.length; j++ ) {
					logger.trace(cells[j] +"");
					if(cells[j]== null){
						cols[j]= "";
					}else{
						cols[j] = Util.isEmpty(cells[j].getValue()) ? "" : cells[j].getValue();
					}
				}
				list.add(cols);
				if( i >= 1 ) {
					break;
				}
			}
		}
		return list;
	}
	/**
	 * XLS Parsing
	 * @param excelFile
	 * @return
	 */
	private List<String[]> getXLSData(File excelFile) {
		XLSReaderSupport rs = (XLSReaderSupport) ReaderSupport.newInstance(ReaderSupport.TYPE_XLS, excelFile );
		Workbook wb = rs.getWorkbook();
		return null;
	}

	/**
	 * XLS Parsing
	 * @param excelFile
	 * @return
	 */
	private List<String[]> getXLSHeader(File excelFile) {
		XLSReaderSupport rs = (XLSReaderSupport) ReaderSupport.newInstance(ReaderSupport.TYPE_XLS, excelFile );
		Workbook wb = rs.getWorkbook();
		return null;
	}



	/**
	 * <pre>
	 * Excel-Template Download
	 * </pre>
	 * @param request the request
	 * @param response the response
	 * @throws IOException the iO exception
	 * @throws IOException the iO exception
	 * @throws IOException the iO exception
     */
	@RequestMapping(value="/excel/download/template", method=RequestMethod.GET)
	public void handleFileDownload(
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception, ControllerException {


		FileInputStream fileInputStream = null;
		OutputStream outStream = null;
		URL url = null;

		try {

//			String filePath = env.getAttachFilePath();
			String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/mint/template/";
			String fileName = request.getParameter("filename");
			// TODO 파일 일음은 서버에서 정의해서 전송한다.
			Map envMap;
			String upVer = "";
			try {
				envMap = commonService.getEnvironmentalValues();
				List attrList = (List)envMap.get("system.excel.upload.version");
				if(attrList != null && attrList.size()>0){
					upVer = (String)attrList.get(0);
				}

				String templateFileName = String.format("[IIP]DE_%03d_UPLOAD_TEMPLATE.xlsx", Integer.parseInt(upVer));
				fileName = templateFileName;

			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}

			String rootPath= request.getSession().getServletContext().getRealPath("/");
			logger.debug("절대경로 다운로드"  + rootPath);

			/* -----------------------------------------------------------------------------
			 * 일괄등록 엑셀 템플릿 파일은 attachfile path경로가 아닌 template경로에 있는 파일로 관리한다.
			 * http url 호출을 통해 template 경로의 파일로 다운로드 하도록 수정함. kesowga - 2018/01/26 */
			/*
			url = new URL(filePath + fileName);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(100000);
			con.setUseCaches(false);
			*/
			/*-----------------------------------------------------------------------------*/

			//File downloadFile = new File(filePath + File.separator + fileName);
			File downloadFile = new File(rootPath + "/template/" + File.separator + fileName);

			if (!downloadFile.exists()) {
				logger.error("download file is Not Exist. ["+downloadFile.getPath()+"]");
				throw new IOException();
			}

			String originalFileName = fileName;
			{
				response.setContentType("application/octet-stream; charset=utf-8;");
//				response.setContentLength((int) con.getContentLength());
				response.setContentLength((int) downloadFile.length());

				String userAgent = request.getHeader("User-Agent");

				if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
					fileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
				}
				else if (userAgent.indexOf("Chrome") > -1) {

					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < originalFileName.length(); i++) {
						char c = originalFileName.charAt(i);
						if (c > '~') {
							sb.append(URLEncoder.encode("" + c, "UTF-8"));
						} else {
							sb.append(c);
						}
					}
					fileName = sb.toString();
				}
				else if (userAgent.indexOf("Opera") > -1) {

					fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
				}
				else { // fireFox... etc...
					fileName = "\"" + new String(originalFileName.getBytes("UTF-8"), "8859_1") + "\"";
				}



				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
				response.setHeader("Content-Transfer-Encoding", "binary");


				outStream = response.getOutputStream();

				fileInputStream = new FileInputStream(downloadFile);

//				FileCopyUtils.copy(con.getInputStream(), outStream);
				FileCopyUtils.copy(fileInputStream, outStream);
				outStream.flush();
			}
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			if (fileInputStream != null) { try { fileInputStream.close(); } catch (Exception e) { e.printStackTrace(); } }
			if (outStream != null) { try { outStream.close(); } catch (Exception e) { e.printStackTrace(); } }
		}

	}



	/**
	 * <pre>
	 * API ID : REST-R01-AN-01-00-008
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/excel/masterinfo/search", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getExcelUploadMasterInfo(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {


		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", comMessage.getUserId());
				params.put("regDate", Util.getFormatedDate());
			}

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = excelUploadService.getExcelUploadMasterInfo(params);
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
	 * API ID : REST-R02-AN-01-00-008
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/excel/detailinfo/search", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getExcelUploadDetailInfo(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {


		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", comMessage.getUserId());
				params.put("regDate", Util.getFormatedDate());
			}

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				list = excelUploadService.getExcelUploadDetailInfo(params);
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
	 * API ID : REST-D01-AN-01-00-008
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/excel/delete", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getDeleteExcelUploadData(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {


		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", comMessage.getUserId());
				params.put("regDate", Util.getFormatedDate());
			}

			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				excelUploadService.deleteExcelUpload(params);
				list = excelUploadService.getExcelUploadMasterInfo(params);
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
	 * API ID : REST-C01-AN-01-00-008
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/excel/requirement/create", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> getCreateRequirement(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {


		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,Object> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap<String,Object>();

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", comMessage.getUserId());
				params.put("regDate", Util.getFormatedDate());
			}


			List<Map<String,Object>> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				//excelUploadService.createRequirement(params);
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
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author TA
     * @since version 1.0(2016.08)
     */
	@RequestMapping(value = "/excel/validate-check", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<List<String[]>, List<Requirement>> getValidateCheck(
			HttpSession httpSession,
			@RequestBody ComMessage<List<String[]>, List<Requirement>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {


		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			//Map<String,List<String[]>> params = comMessage.getRequestObject();
			//if(params == null) params = new HashMap<String,List<String[]>>();

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			Map<String,Object> reqParams = new HashMap<String,Object>();
			{
				reqParams.put("userId", comMessage.getUserId());
				reqParams.put("regDate", Util.getFormatedDate());
			}


			List<String[]> excelList = comMessage.getRequestObject();
			List<Requirement> list = null;
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			{
				try {
					list = excelUploadService.validate(excelList, reqParams);
				} catch(Throwable e){

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














}