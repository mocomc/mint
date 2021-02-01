package pep.per.mint.front.controller.ut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.excel.tools.excel.ReaderSupport;
import pep.per.mint.front.excel.tools.excel.support.XLSXReaderSupport;
import pep.per.mint.front.excel.tools.excel.xlsx.Cell;
import pep.per.mint.front.excel.tools.excel.xlsx.Sheet;
import pep.per.mint.front.excel.tools.excel.xlsx.SimpleXLSXWorkbook;
import pep.per.mint.front.exception.ControllerException;


/**
 * The type File upload controller.
 */
@Controller
@RequestMapping("/ut")
public class LayoutUploadController {

	/**
	 * The Logger.
     */
	Logger logger = LoggerFactory.getLogger(LayoutUploadController.class);

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
	 * The Common service.
     */
	@Autowired
	CommonService commonService;

	/**
	 * The Common service.
     */
	@Autowired
	RestTemplate restTemplate;
	final static String SUCCESS_CD = "success";
	final static String ERROR_CD = "error";

	/**
	 * Handle file upload.
	 * Spring Multipart 처리시 JSON 데이터도 같이 바인딩 할 수 없는지 확인이 필요함.
	 *
	 * @param httpSession the http session
	 * @param file the file
	 * @return the hash map
	 * @throws Exception the exception
     */
	@RequestMapping(value = "/excel/upload/layout", params = {"method=POST", "isTest=false"}, method = RequestMethod.POST)
	public @ResponseBody ComMessage<Map<String,Object>, List> excelUpload(
    		HttpSession httpSession,
    		@RequestParam("userId") String userId,
    		@RequestPart("fileUpload") MultipartFile multipartFile ) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "0";
			String errorMsg = "";

			ComMessage<Map<String,Object>, List> comMessage = new ComMessage<Map<String,Object>, List>();
			Map<String,Object> params = new HashMap<String,Object>();

			logger.info("----------------------------------------------------");
			logger.info("[Layout Excel Upload - Excel-Import 시작]");
			logger.info("----------------------------------------------------");

			File excelFile = null;

			try {

				//----------------------------------------------------------------------------
				// 등록ID, 등록시간 설정하기, 세션정보가 유효하지 않으면 에러
				//----------------------------------------------------------------------------
				{
					String sessionError = "[Excel-Import 실패]: 사용자 세션 정보가 유효하지 않습니다.";
					if( httpSession != null ) {
						User user = (User)httpSession.getAttribute("user");
						if( user == null ) {
							throw new Exception(sessionError);
						} else {
							if( userId.equals(user.getUserId()) ) {
								comMessage.setUserId(user.getUserId());
							} else {
								throw new Exception(sessionError);
							}
						}
					} else {
						throw new Exception(sessionError);
					}

					params.put("regId", comMessage.getUserId());
					params.put("regDate", Util.getFormatedDate());
				}

	            if ( !multipartFile.isEmpty() ) {

					logger.info("----------------------------------------------------");
					logger.info(Util.join("ContentType : ", multipartFile.getContentType()));
					logger.info(Util.join("ComponentId : ", multipartFile.getName()));
					logger.info(Util.join("Orig-File Name   : ", multipartFile.getOriginalFilename()));
					logger.info(Util.join("Orig-File Size   : ", multipartFile.getSize()));
					logger.info("----------------------------------------------------");


					//----------------------------------------------------------------------------
					// 엑셀 타입인지 체크한다.( XLSX ) 엑셀 타입이 아니면 에러
					//----------------------------------------------------------------------------
	 				{
		 				String originalFileName = multipartFile.getOriginalFilename();
		 				if( !multipartFile.getContentType().equals(EXCEL_TYPE_XLSX)
		 						&& !originalFileName.substring(originalFileName.lastIndexOf(".")+1).equalsIgnoreCase("xlsx")) {
		 					throw new Exception("[Excel-Import 실패]: 올바른 엑셀 포멧이 아닙니다. XLSX 확장자만 지원합니다.");
		 				}
	 				}


					String envFilePath = env.getAttachFilePath() + File.separator + comMessage.getUserId();
					String filePostFix = UUID.randomUUID().toString().replaceAll("-", "");

					File filePath = new File(envFilePath);
					if (!filePath.isDirectory() || !filePath.exists()) {
						filePath.mkdirs();
					}

					//----------------------------------------------------------------------------
					// 원본파일을 UUID 형식으로 rename 하여 파일 생성
					//----------------------------------------------------------------------------
					excelFile = new File( filePath.getPath() + File.separator + filePostFix );
					multipartFile.transferTo( excelFile );

					logger.info("----------------------------------------------------");
					logger.info( Util.join("File-AbsolutePath : ", excelFile.getAbsolutePath()) );
					logger.info( Util.join("File-Path : ", filePath.getPath()) );
					logger.info( Util.join("File-Name : ", filePostFix) );
					logger.info("----------------------------------------------------");


					List excelData = null;

					//----------------------------------------------------------------------------
					// DRM API Service Call
					//----------------------------------------------------------------------------
 					String drmUsed = commonService.getEnvironmentalValue("inhouse", "drm.api.used", "N");
 					String url = commonService.getEnvironmentalValue("inhouse", "drm.api.url", "http://localhost:9080/mint-bridge-apps/drm");
 					String excelLib = commonService.getEnvironmentalValue("system", "excel.lib.poi", "Y");

 					boolean isPOI = excelLib.equals("Y") ? true : false;

	 				logger.info("----------------------------------------------------");
	 				logger.info( Util.join("POI LIB USED [", excelLib, "]") );
	 				logger.info( Util.join("DRM-USED [", drmUsed, "] DRM-URL [", url, "]") );
	 				logger.info("----------------------------------------------------");

 					if( drmUsed.equals("Y") ) {

	 					try {

		 					ComMessage<Map<String,String>, Map<String,String>> request  = new ComMessage<Map<String,String>, Map<String,String>>();

		 					Map<String,String> requestObject = new HashMap<String,String>();
		 					requestObject.put("filePath", filePath.getPath());
		 					requestObject.put("fileName", filePostFix);
		 					request.setRequestObject(requestObject);

		 					ComMessage<Map<String,String>, Map<String,String>> response = restTemplate.postForObject(url, request, request.getClass());

		 					if( response == null ) {
			 					errorCd  = "9999";
			 					errorMsg = "[Excel-Import 실패]: DRM Service 호출시 문제가 있습니다 - STEP1 [응답 NULL]";
		 					} else {

		 						String responseCd = response.getErrorCd();
		 						String responseMsg = response.getErrorMsg();

	 			 				logger.info("----------------------------------------------------");
	 			 				logger.info( Util.join("DRM-CALL-CD [", responseCd, "] DRM-CALL-MSG [", responseMsg, "]") );
	 			 				logger.info("----------------------------------------------------");

		 						if( !Util.isEmpty(responseCd) && responseCd.equals("0") ) {

		 							Map<String, String> responseObject = response.getResponseObject();
		 							String drmFilePath = responseObject.get("decFilePath");

		 							File drmFile = new File(drmFilePath);

		 							if( drmFile.exists() ) {

			 			 				logger.info("----------------------------------------------------");
			 			 				logger.info( Util.join("DRM-PASS, DRM-FILE-PATH : ", drmFile.getPath() ) );
			 			 				logger.info("----------------------------------------------------");

			 			 				if( ! drmFile.canRead() ) {
				 			 				logger.info("----------------------------------------------------");
				 			 				logger.info( Util.join("DRM-FILE-CANNOT-READ") );
				 			 				logger.info("----------------------------------------------------");
			 			 				}
			 			 				if(isPOI) {
			 			 					excelData = getXLSXDataPOI(drmFile);
			 			 				} else {
			 			 					excelData = getXLSXData(drmFile, false);
			 			 				}

		 			 				} else {
			 			 				logger.info("----------------------------------------------------");
			 			 				logger.info( Util.join("DRM-FILE-NOT-FOUND : ", drmFilePath) );
			 			 				logger.info("----------------------------------------------------");
		 			 				}

									if(drmFile != null) {
										if(drmFile.exists()) {
											drmFile.delete();
										}
									}

		 						} else {
		 		 					errorCd  = "9999";
		 		 					errorMsg = Util.join("[Excel-Import 실패]: DRM Service 호출시 문제가 있습니다 - STEP2 [", responseMsg, "]");
		 						}
		 					}
	 					} catch(Exception e) {
 		 					errorCd  = "9999";
 		 					errorMsg = Util.join("[Excel-Import 실패]: DRM Service 호출시 또는 엑셀 파싱에 문제가 있습니다 - STEP3 [", e.getMessage(), "]");
	 					}
 					} else {
 						if(isPOI) {
 							excelData = getXLSXDataPOI(excelFile);
 						} else {
 							excelData = getXLSXData(excelFile, false);
 						}
	 				}

 					comMessage.setResponseObject(excelData);

	             } else {
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	     			errorCd = "9999";
	     			errorMsg = "[Excel-Import 실패]: 업로드할 엑셀파일이 존재하지 않습니다.";
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
				//comMessage.setErrorDetail(errorDetail);

				logger.error(Util.join("LayoutUploadController", errorDetail));

	    	} finally {
				if(excelFile != null) {
					if(excelFile.exists()) {
						excelFile.delete();
					}
				}
				if( !Util.isEmpty(errorMsg) ) {
					logger.error(errorMsg);
				}
				logger.info("----------------------------------------------------");
				logger.info("[Layout Excel Upload - Excel-Import 종료]");
				logger.info("----------------------------------------------------");
	    	}
	        return comMessage;
		}
    }

	/**
	 * POI XLSX Read
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	private List getXLSXDataPOI(File excelFile) throws Exception {
		List list = null;
		//----------------------------------------------------------------------------
		// XSSFWorkbook Call
		//----------------------------------------------------------------------------
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		try {
			//----------------------------------------------------------------------------
			// SheetName Parsing :: "레이아웃"
			//----------------------------------------------------------------------------
			String sheetName = "레이아웃";
			XSSFSheet sheet  = wb.getSheet(sheetName);

			if( sheet == null ) {
				throw new Exception("Excel에 '레이아웃' 시트가 존재하지 않습니다");
			} else {
				list = parsingSheetPOI(sheet);
			}
		} finally {
			fis.close();
		}
		return list;
	}

	/**
	 * POI Sheet Parsing
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private List parsingSheetPOI( XSSFSheet sheet) throws Exception{
		List list = new LinkedList();

		int rowSize = sheet.getPhysicalNumberOfRows();
		for( int i = 0; i < rowSize; i++ ) {

			XSSFRow row = sheet.getRow(i);
			if( row != null ) {
				int cellSize = row.getPhysicalNumberOfCells();

				//----------------------------------------------------------------------------
				// Data Parsing
				//----------------------------------------------------------------------------
				List<Map> cols = new LinkedList<Map>();
				for( int j = 0; j < cellSize; j++ ) {

					Map<String,String> col = new HashMap<String, String>();
					XSSFCell cell = row.getCell(j);

					if( cell == null ) {
						col.put("value", "");
					} else {
						col.put("value", getCellValue(cell));
					}
					cols.add(col);
				}

				Map<String, List<Map>> map = new HashMap<String, List<Map>>();
				map.put("cells", cols);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * POI Cell Value return
	 * @param cell
	 * @return
	 */
	private String getCellValue(XSSFCell cell) {
		String value = "";
		switch ( cell.getCellType() ) {
			case XSSFCell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				value = cell.getErrorCellValue() + "";
				break;
			default  :
				value = "";
		}

		return value;

	}


	/**
	 *
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	private List getXLSXData(File excelFile, boolean isMany) throws Exception {
		List list = null;
		//----------------------------------------------------------------------------
		// XLSXReader Call
		//----------------------------------------------------------------------------
		XLSXReaderSupport rs  = (XLSXReaderSupport) ReaderSupport.newInstance(ReaderSupport.TYPE_XLSX, excelFile );
		rs.open();

		SimpleXLSXWorkbook wb = rs.getWorkbook();
		int sheetCount = wb.getSheetCount();

		logger.info("----------------------------------------------------");
		logger.info( Util.join("[Excel Sheet Count [", sheetCount , "]") );
		logger.info("----------------------------------------------------");

		if( sheetCount != 3 ) {
			//----------------------------------------------------------------------------
			// 우리가 만든 템플릿이 아님( 우리는 3개 시트로 구성됨 :: 비고, 레이아웃, 코드(숨김))
			// 우리가 만든 템플릿이 아니면, 첫번째 시트를 읽어서 처리
			//----------------------------------------------------------------------------
			Sheet sheet = wb.getSheet(0);
			list =  parsingSheet(sheet, 0) ;
		} else {

			for( int idx = 0; idx < sheetCount; idx++ ) {
				//----------------------------------------------------------------------------
				// 2번째 Sheet 만 Parsing
				// TODO :: SheetName 으로 참조하도록 수정 필요.....
				//----------------------------------------------------------------------------
				if( idx == 1 ) {
					Sheet sheet = wb.getSheet(idx);
					list =  parsingSheet(sheet, idx) ;
				}
			}
		}
		rs.close();
		return list;
	}



	/**
	 *
	 * @param sheet
	 * @param sheetIndex
	 * @return
	 * @throws Exception
	 */
	private List parsingSheet( Sheet sheet, int sheetIndex ) throws Exception{
		List list = new LinkedList();

		List<Cell[]> rows = sheet.getRows();
		int rowSize = rows.size();
		for( int i = 0; i < rowSize; i++ ) {

			Cell[] cells = rows.get(i);
			//----------------------------------------------------------------------------
			// Data Parsing
			//----------------------------------------------------------------------------
			List<Map> cols = new LinkedList<Map>();
			for( int j = 0; j < cells.length; j++ ) {

				Map<String,String> col = new HashMap<String, String>();
				if( cells[j] == null ) {
					col.put("value", "");
				} else {
					col.put("value", Util.isEmpty(cells[j].getValue()) ? "" : cells[j].getValue());

				}
				cols.add(col);
			}

			Map<String, List<Map>> map = new HashMap<String, List<Map>>();
			map.put("cells", cols);


			list.add(map);
		}
		return list;
	}

}