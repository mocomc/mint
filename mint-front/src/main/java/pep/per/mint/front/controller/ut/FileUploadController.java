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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.StringUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;


/**
 * The type File upload controller.
 */
@Controller
@RequestMapping("/ut")
public class FileUploadController {

	/**
	 * The Logger.
     */
	Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /*@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }*/

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	/**
	 * The Env.
	 */
	@Autowired
	FrontEnvironments env;

	@Autowired
	CommonService commonService;

	/**
	 * Handle file upload.
	 *
	 * @param httpSession the http session
	 * @param file the file
	 * @return the hash map
	 * @throws Exception the exception
	 * @deprecated
     */
	@RequestMapping(
    		value="/upload",
    		method=RequestMethod.POST)
    public @ResponseBody HashMap<String, String> handleFileUpload(
    		HttpSession httpSession,
    		@RequestPart("file") MultipartFile file ) throws Exception {


    	logger.error("파일업로드  들어왔네 ############");

        if (!file.isEmpty()) {
            try {

 				logger.debug("----------------------------------------------------");
 				logger.debug(Util.join("ContentType : ", file.getContentType()));
 				logger.debug(Util.join("ComponentId : ", file.getName()));
 				logger.debug(Util.join("File Name   : ", file.getOriginalFilename()));
 				logger.debug(Util.join("File Size   : ", file.getSize()));
 				logger.debug("----------------------------------------------------");


            	// get UserId
            	User user = (User) httpSession.getAttribute("user");
        		if (user == null) {
        			logger.error("USER Session null !!!!!!!!!!!!!!!!!!!!!:");
        			throw new Exception("USER Session null! when uploading file.");
        		}
        		//logger.debug("USER_ID >> " + user.getUserId() + ".");
        		if(checkExecutableFile(file.getOriginalFilename())){
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	            	HashMap<String, String> map = new HashMap<String, String>();
	            	map.put("errMessage","[파일업로드실패]:업로드 제한 파일입니다.["+file.getOriginalFilename()+"]");

	            	return map;
 				}else{


 					// get server Repository Path
 					String attachFileRepPath = env.getAttachFilePath();
 					logger.error("attachFileRepPath >> " + attachFileRepPath + ".");

 					String realPath = attachFileRepPath;

 					String userRepPath = realPath + File.separator + user.getUserId();

 					File fileRepPath = new File(userRepPath);
 					if (!fileRepPath.isDirectory() || !fileRepPath.exists()) {
 						fileRepPath.mkdirs();
 					}


 					String currentDt = UUID.randomUUID().toString().replaceAll("-", "");
 					File uploadedFile = new File(fileRepPath.getPath() + File.separator + currentDt);

 					/*
            	String currentDt = "";

            	File uploadedFile = null;
	            File tmpFile = null;
            	while(true) {

            		//currentDt = Util.getFormatedDate("yyyyMMddHHmmssSSS");
            		currentDt = UUID.randomUUID().toString().replaceAll("-", "");
            		tmpFile = new File(fileRepPath.getPath() + File.separator + currentDt);

            		if (!tmpFile.exists()) {
            			uploadedFile = tmpFile;
            			break;
            		}
            		else {
            			logger.debug(tmpFile.getPath() + " >>> already exist file name!! ");
            		}
            	}
 					 */


 					// file write
 					file.transferTo(uploadedFile);

 					logger.debug("파일업로드 성공1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + uploadedFile.getPath());

 					HashMap<String, String> map = new HashMap<String, String>();
 					map.put("getOriginalFilename", file.getOriginalFilename());
 					map.put("serverPath", user.getUserId() + File.separator + currentDt);

 					return map;
 				}
            } catch (Exception e) {
            	logger.error("파일업로드 실패!:",e);

            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("errMessage", e.getMessage());

            	return map;
            }
        } else {
            //logger.error("파일이 비어 있어서 실패2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("file errMessage", "You failed to upload   because the file was empty.");

            return map;
        }
    }


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
    		value="/file/upload",
    		method=RequestMethod.POST)
	public @ResponseBody ComMessage<Map<String,Object>, List<Map<String,Object>>> uploadFile(
    		HttpSession httpSession,
    		@RequestParam("userId") String userId,
    		@RequestPart("uploadFile") MultipartFile multipartFile ) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ComMessage<Map<String,Object>, List<Map<String,Object>>> comMessage = new ComMessage<Map<String,Object>, List<Map<String,Object>>>();
			Map<String,Object> params = new HashMap<String,Object>();

			logger.info("[file Upload - start]");

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", userId);
				params.put("regDate", Util.getFormatedDate());
			}

			File uploadFile = null;

	    	try {


	            if (!multipartFile.isEmpty()) {

	 				logger.debug("----------------------------------------------------");
	 				logger.debug(Util.join("ContentType : ", multipartFile.getContentType()));
	 				logger.debug(Util.join("ComponentId : ", multipartFile.getName()));
	 				logger.debug(Util.join("File Name   : ", multipartFile.getOriginalFilename()));
	 				logger.debug(Util.join("File Size   : ", multipartFile.getSize()));
	 				logger.debug("----------------------------------------------------");

	 				if(checkExecutableFile(multipartFile.getOriginalFilename())){
						//----------------------------------------------------------------------------
						// 첨부파일이 비어있으면 에러
						//----------------------------------------------------------------------------
		     			errorCd = "9999";
		     			errorMsg = "[파일업로드실패]:업로드 제한 파일입니다.[" +multipartFile.getOriginalFilename() +"]";
	 				}else{
	 					String envFilePath = env.getAttachFilePath() + File.separator + userId;
	 					String filePostFix = UUID.randomUUID().toString().replaceAll("-", "");

	 					File filePath = new File(envFilePath);
	 					if (!filePath.isDirectory() || !filePath.exists()) {
	 						filePath.mkdirs();
	 					}

	 					uploadFile = new File( filePath.getPath() + File.separator + filePostFix );
	 					multipartFile.transferTo( uploadFile );

	 					//----------------------------------------------------------------------------
	 					// 여기까지 Exception 이 없으면 파일 업로드 성공.
	 					//----------------------------------------------------------------------------
	 					errorCd = "0";
	 					errorMsg = "[파일업로드성공]:파일 업로드를 성공하였습니다.";


	 					Map<String,Object> fileInfo = new LinkedHashMap<String, Object>();
	 					fileInfo.put("fileNm"  , multipartFile.getOriginalFilename());
	 					fileInfo.put("filePath", userId + File.separator + filePostFix);

	 					List<Map<String,Object>> responseObject = new LinkedList<Map<String,Object>>();
	 					responseObject.add(fileInfo);

	 					comMessage.setResponseObject(responseObject);
	 				}


	             } else {
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	     			errorCd = "9999";
	     			errorMsg = "[파일업로드실패]:업로드할 파일이 존재하지 않습니다.";
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
				errorMsg = "[파일업로드실패]:파일 이름, 디렉터리 이름 또는 볼륨 레이블 구문이 잘못되었습니다.";
				errorMsg = e.getMessage();

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
				comMessage.setErrorDetail(errorDetail);

				logger.error(Util.join("FileUploadController", errorDetail));

	    	} finally {
	    		if( !Util.isEmpty(errorMsg) ) {
	    			logger.error(errorMsg);
	    		}
	    		logger.info("[file Upload - end]");
	    	}
	        return comMessage;
		}
    }

	private boolean checkExecutableFile(String originalFilename) {
		boolean isExecutable = false;
		try {
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues("system","file.upload.policy");
			if(environmentalValues == null || environmentalValues.size() == 0){
				return false;
			}

			List<String> listPolicy = environmentalValues.get("system.file.upload.policy");
			String policy = listPolicy.get(0);
			String[] fPolicy = StringUtil.strToStrArray(policy, "=");

			if(fPolicy.length<2 || "0".equalsIgnoreCase(fPolicy[0]) || "".equalsIgnoreCase(fPolicy[0])
				|| "".equalsIgnoreCase(fPolicy[1])){
				return false;
			}
			List<String> tmpList = StringUtil.strToArrayList(fPolicy[1], ",");
			if(tmpList.size()<=0){
				return false;
			}
			String extNm = getFileExtension(originalFilename).toUpperCase();

			if("1".equalsIgnoreCase(fPolicy[0])){  //  화이트 리스트 정책( 해당 확장자만 허용)  없는 경우 모두 허용
				if(tmpList.contains(extNm)){
					return false;
				}else{
					return true;
				}
			}else if("2".equalsIgnoreCase(fPolicy[0])){  // 블랙리스트 정책( 해당 확장자만 제한)  없는 경우는 모두 허용
				if(tmpList.contains(extNm)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}


		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return isExecutable;
	}


	String getFileExtension(String fileNm) {
	    if (fileNm == null) {
	        return "";
	    }
	    int i = fileNm.lastIndexOf('.');
	    String ext = i > 0 ? fileNm.substring(i + 1) : "";
	    return ext;
	}
}