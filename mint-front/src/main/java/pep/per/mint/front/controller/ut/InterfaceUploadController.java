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
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceMapping;
import pep.per.mint.common.data.basic.InterfacePattern;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.InterfaceTag;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.upload.UploadSummary;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.ut.CommonUploadService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.service.im.InterfaceImportService;
import pep.per.mint.front.util.MessageSourceUtil;


/**
 * The type File upload controller.
 */
@Controller
@RequestMapping("/ut")
public class InterfaceUploadController {

	/**
	 * The Logger.
     */
	Logger logger = LoggerFactory.getLogger(InterfaceUploadController.class);


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
	CommonUploadService cs;

	/**
	 * The Common service.
     */
	@Autowired
	CommonService commonService;


	@Autowired
	InterfaceImportService iis;


    @Autowired
    private ServletContext servletContext;

	/**
	 * Handle file upload.
	 * Spring Multipart 처리시 JSON 데이터도 같이 바인딩 할 수 없는지 확인이 필요함.
	 *
	 * @param httpSession the http session
	 * @param file the file
	 * @return the hash map
	 * @throws Exception the exception
     */
	@RequestMapping(value="/interface/upload", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody ComMessage<Map<String,Object>, Map<String, Integer>> jsonUpload(
    		HttpSession httpSession,
    		Locale locale,
    		@RequestParam("comments") String comments,
    		@RequestParam("userId") String userId,
    		@RequestPart("interfaceFileUpload") MultipartFile multipartFile ) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ComMessage<Map<String,Object>, Map<String, Integer>> comMessage = new ComMessage<Map<String,Object>, Map<String, Integer>>();
			Map<String,Object> params = new HashMap<String,Object>();

			logger.info("[인터페이스 이관등록 - 업로드시작]");

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", userId);
				params.put("regDate", Util.getFormatedDate());
			}

			File jsonFile = null;

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

	 				jsonFile = new File( filePath.getPath() + File.separator + filePostFix );
	 				multipartFile.transferTo( jsonFile );

	 				//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
	 				List list = (List) Util.readObjectFromJson(jsonFile, List.class, "UTF-8");
	 				if(list == null || list.size() == 0){
	 					throw new Exception(Util.join("인터페이스이관파일[",multipartFile.getOriginalFilename(),"]에 이관할 인터페이스 정보가 존재하지 않습니다."));
	 				}
	 				List<Requirement> requirements = new ArrayList<Requirement>();
	 				for (Object obj : list) {
						//logger.debug(Util.join("obj:", Util.toJSONString(obj)));
						Requirement requirement = Util.jsonMapper.convertValue(obj, Requirement.class);


						Interface interfaze = requirement.getInterfaceInfo();
						//-----------------------------------------------------------
						//타겟시스템 기준 업무정보 ID로 변경
						//-----------------------------------------------------------
						{
							List<Business> businesses = interfaze.getBusinessList();
							for (Business business : businesses) {
								String cd = business.getBusinessCd();
								Business tarBiz = commonService.getBusinessByCd(cd);
								if(tarBiz == null) {
									String msg = Util.join("인터페이스[", interfaze.getIntegrationId(),"] 의 업무정보[조회조건코드:",cd,"]가 시스템에 존재하지 않습니다.");
									throw new ControllerException("9999", msg);
								}
								String businessId = tarBiz.getBusinessId();
								business.setBusinessId(businessId);
							}

						}
						//-----------------------------------------------------------
						//타겟시스템 기준 시스템정보 ID로 변경
						//-----------------------------------------------------------
						{
							List<pep.per.mint.common.data.basic.System> systems = interfaze.getSystemList();
							for (pep.per.mint.common.data.basic.System system : systems) {
								String cd = system.getSystemCd();
								pep.per.mint.common.data.basic.System tarSys = commonService.getSystemByCd(cd);
								if(tarSys == null) {
									String msg = Util.join("인터페이스[", interfaze.getIntegrationId(),"] 의 시스템정보[조회조건코드:",cd,"]가 시스템에 존재하지 않습니다.");
									throw new ControllerException("9999", msg);
								}
								String systemId = tarSys.getSystemId();
								system.setSystemId(systemId);
							}


						}
						//-----------------------------------------------------------
						//타겟시스템 기준 채널정보 ID로 변경
						//-----------------------------------------------------------
						{
							pep.per.mint.common.data.basic.Channel channel = interfaze.getChannel();

							String cd = channel.getChannelCd();
							pep.per.mint.common.data.basic.Channel tarChl = commonService.getChannelByCd(cd);
							if(tarChl == null) {
								String msg = Util.join("인터페이스[", interfaze.getIntegrationId(),"] 의 채널정보[조회조건코드:",cd,"]가 시스템에 존재하지 않습니다.");
								throw new ControllerException("9999", msg);
							}
							String channelId = tarChl.getChannelId();
							channel.setChannelId(channelId);
						}

						//-----------------------------------------------------------
						//타겟시스템 기준 데이터액세스롤 정보 ID로 변경
						//-----------------------------------------------------------
						{
							List<DataAccessRole> roles = interfaze.getDataAccessRoleList();
							for (DataAccessRole role : roles) {

								String cd = role.getRoleCd();
								DataAccessRole tagRole = commonService.getDataAccessRoleByCd(cd);
								if(tagRole == null) {
									String msg = Util.join("인터페이스[", interfaze.getIntegrationId(),"] 의 데이터접근권한정보[조회조건코드:",cd,"]가 시스템에 존재하지 않습니다.");
									throw new ControllerException("9999", msg);
								}
								String roleId = tagRole.getRoleId();
								role.setRoleId(roleId);
							}
						}

						//-----------------------------------------------------------
						//타겟시스템 기준 프로퍼티 정보 ID로 변경
						//-----------------------------------------------------------
						{
							String tarChannelId = interfaze.getChannel().getChannelId();
							List<InterfaceProperties> porperties = interfaze.getProperties();
							for (InterfaceProperties property : porperties) {
								String cd = property.getAttrCd();
								String type = property.getType();
								InterfaceProperties tagProperty = commonService.getInterfacePropertyByCd(cd, type, tarChannelId);
								if(tagProperty == null) {
									String msg = Util.join("인터페이스[", interfaze.getIntegrationId(),"] 의 프로퍼티정보[조회조건코드:",cd,"]가 시스템에 존재하지 않습니다.");
									throw new ControllerException("9999", msg);
								}
							}
						}

						//deprecate
						//InterfaceMapping mapping = interfaze.getInterfaceMapping();
						//deprecate
						//InterfacePattern pattern = interfaze.getRefPattern();
						//deprecate
						//List<InterfaceTag> tags = interfaze.getTagList();



						requirements.add(requirement);
						//logger.debug(Util.join("requirement:", Util.toJSONString(requirement)));
					}

	 				UploadSummary summary = new UploadSummary();
	 				summary.setComments(multipartFile.getOriginalFilename());
	 				summary.setBatchCount(requirements.size());
	 				summary.setFileName(File.separator + filePostFix);
	 				summary.setFilePath(filePath.getPath());
	 				summary.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
	 				summary.setRegId(userId);
	 				summary.setComments(comments);

	 				int res = cs.upload(requirements, summary, userId);

					//----------------------------------------------------------------------------
					// 디비에 저장된 Excel Upload Master Data 를 조회한다.
					//----------------------------------------------------------------------------
					//List<UploadSummary> resultList = cs.getUploadSummaryList();
					//comMessage.setResponseObject(resultList);
	 				Map<String, Integer> result = new HashMap<String, Integer>();
	 				result.put("totalCount", summary.getBatchCount());
	 				result.put("resultCount", summary.getResultCount());

	 				comMessage.setResponseObject(result);

	     			errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
	     			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);


	             } else {
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	            	String [] errorParams = {"인터페이스이관파일"};
	     			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
	     			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorParams, locale);

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

				logger.error(Util.join(this.getClass().getSimpleName(), errorDetail));

	    	} finally {
				//----------------------------------------------------------------------------
				// 별도의 요구사항이 없으면 업로드한 파일은 삭제
	    		// - 16.11.04 이규호 부장님 내용 확인 결과.. 일단 저장해두자는 의견이 있어 주석처리함.
				//----------------------------------------------------------------------------
	    		if(jsonFile != null) {
		    		if(jsonFile.exists()) {
		    			//jsonFile.delete();
		    		}
	    		}
	    		if( !Util.isEmpty(errorMsg) ) {
	    			logger.error(errorMsg);
	    		}
	    		logger.info("[인터페이스이관종료]");
	    	}
	        return comMessage;
		}
    }



	/**
	 * to import interface data.
	 * Spring Multipart 처리시 JSON 데이터도 같이 바인딩 할 수 없는지 확인이 필요함.
	 *
	 * @param httpSession the http session
	 * @param file the file
	 * @return the hash map
	 * @throws Exception the exception
     */
	@RequestMapping(value="/interface/import", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody ComMessage<Map<String,Object>, Map<String, Object>> importInterfaces(
    		HttpSession httpSession,
    		Locale locale,
    		@RequestParam("comments") String comments,
    		@RequestParam("userId") String userId,
    		@RequestParam("asyncOption") boolean asyncOption,
    		@RequestPart("interfaceFileUpload") MultipartFile multipartFile ) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			ComMessage<Map<String,Object>, Map<String, Object>> comMessage = new ComMessage<Map<String,Object>, Map<String, Object>>();
			Map<String,Object> params = new HashMap<String,Object>();

			logger.info("[인터페이스 이관등록 - 업로드시작]");

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				params.put("userId", userId);
				params.put("regDate", Util.getFormatedDate());
			}

			File importFile = null;

	    	try {

	            if (!multipartFile.isEmpty()) {

	 				logger.debug("----------------------------------------------------");
	 				logger.debug(Util.join("comments:", comments));
	 				logger.debug(Util.join("userId:", userId));
	 				logger.debug(Util.join("asyncOption:", asyncOption));
	 				logger.debug(Util.join("ContentType : ", multipartFile.getContentType()));
	 				logger.debug(Util.join("ComponentId : ", multipartFile.getName()));
	 				logger.debug(Util.join("File Name   : ", multipartFile.getOriginalFilename()));
	 				logger.debug(Util.join("File Size   : ", multipartFile.getSize()));
	 				logger.debug("----------------------------------------------------");




	 				String importPath = Util.join(servletContext.getRealPath("/"), File.separator, "export");
					Util.delete(new File(importPath));
					new File(importPath).mkdirs();


	        		String filePostFix = "import.zip";

	            	File filePath = new File(importPath);


	 				importFile = new File(importPath , filePostFix);
	 				multipartFile.transferTo( importFile );



	 				File outputFolder = new File(importPath,Util.getFormatedDate());
	 				if(!outputFolder.exists()) outputFolder.mkdirs();
	 				Util.unzip(importFile, outputFolder);


	 				String resultMsg = null;

	 				if(asyncOption){//async 처리
	 					iis.importDataAsync(filePath, filePostFix, userId, comments, outputFolder, multipartFile);
	 					resultMsg = "비동기 처리중중입니다.";
	 				}else{//synck 처리
	 					iis.importData(filePath, filePostFix, userId, comments, outputFolder, multipartFile);
	 					resultMsg = "처리를 완료하였습니다.";
	 				}


	 				/*
  					int totalCount = 0 ;
	 				int resultCount = 0 ;


	 				File [] importFiles = outputFolder.listFiles();
	 				for (File file : importFiles) {
	 					List list = (List) Util.readObjectFromJson(file, List.class, "UTF-8");
		 				if(list == null || list.size() == 0){
		 					throw new Exception(Util.join("인터페이스이관파일[",multipartFile.getOriginalFilename(),"]에 이관할 인터페이스 정보가 존재하지 않습니다."));
		 				}
		 				List<Requirement> requirements = new ArrayList<Requirement>();
		 				for (Object obj : list) {
							//logger.debug(Util.join("obj:", Util.toJSONString(obj)));
							Requirement requirement = Util.jsonMapper.convertValue(obj, Requirement.class);
							requirements.add(requirement);
							//logger.debug(Util.join("requirement:", Util.toJSONString(requirement)));

						}
		 				UploadSummary summary = new UploadSummary();
		 				summary.setComments(multipartFile.getOriginalFilename());
		 				summary.setBatchCount(requirements.size());
		 				summary.setFileName(File.separator + filePostFix);
		 				summary.setFilePath(filePath.getPath());
		 				summary.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		 				summary.setRegId(userId);
		 				summary.setComments(comments == null || comments.length() == 0 ? "import all interface data" : comments);

		 				int res = cs.upload(requirements, summary, userId);

		 				totalCount = totalCount + summary.getBatchCount();
		 				resultCount = resultCount + summary.getResultCount();
					}*/



					//----------------------------------------------------------------------------
					// 디비에 저장된 Excel Upload Master Data 를 조회한다.
					//----------------------------------------------------------------------------
					//List<UploadSummary> resultList = cs.getUploadSummaryList();
					//comMessage.setResponseObject(resultList);
	 				Map<String, Object> result = new HashMap<String, Object>();
	 				//result.put("totalCount", totalCount);
	 				//result.put("resultCount", resultCount);

	 				result.put("resultMsg", resultMsg);

	 				comMessage.setResponseObject(result);

	     			errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
	     			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);


	             } else {
					//----------------------------------------------------------------------------
					// 첨부파일이 비어있으면 에러
					//----------------------------------------------------------------------------
	            	String [] errorParams = {"인터페이스이관파일"};
	     			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.required.param", locale);
	     			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", errorParams, locale);

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

				logger.error(Util.join(this.getClass().getSimpleName(), errorDetail));

	    	} finally {
				//----------------------------------------------------------------------------
				// 별도의 요구사항이 없으면 업로드한 파일은 삭제
	    		// - 16.11.04 이규호 부장님 내용 확인 결과.. 일단 저장해두자는 의견이 있어 주석처리함.
				//----------------------------------------------------------------------------
	    		if(importFile != null) {
		    		if(importFile.exists()) {
		    			//jsonFile.delete();
		    		}
	    		}
	    		if( !Util.isEmpty(errorMsg) ) {
	    			logger.error(errorMsg);
	    		}
	    		logger.info("[인터페이스이관종료]");
	    	}
	        return comMessage;
		}
    }

	@Async
	public void importDataAsync(File filePath, String filePostFix, String userId, String comments, File outputFolder, MultipartFile multipartFile) throws Exception{
		importData(filePath, filePostFix, userId, comments, outputFolder, multipartFile);
	}

	public void importData(File filePath, String filePostFix, String userId, String comments, File outputFolder, MultipartFile multipartFile) throws Exception{

		int totalCount = 0 ;
		int resultCount = 0 ;

		File [] importFiles = outputFolder.listFiles();
		for (File file : importFiles) {
			List list = (List) Util.readObjectFromJson(file, List.class, "UTF-8");
			if(list == null || list.size() == 0){
				throw new Exception(Util.join("인터페이스이관파일[",multipartFile.getOriginalFilename(),"]에 이관할 인터페이스 정보가 존재하지 않습니다."));
			}
			List<Requirement> requirements = new ArrayList<Requirement>();
			for (Object obj : list) {
			//logger.debug(Util.join("obj:", Util.toJSONString(obj)));
			Requirement requirement = Util.jsonMapper.convertValue(obj, Requirement.class);
			requirements.add(requirement);
			//logger.debug(Util.join("requirement:", Util.toJSONString(requirement)));

			}
			UploadSummary summary = new UploadSummary();
			summary.setComments(multipartFile.getOriginalFilename());
			summary.setBatchCount(requirements.size());
			summary.setFileName(File.separator + filePostFix);
			summary.setFilePath(filePath.getPath());
			summary.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			summary.setRegId(userId);
			summary.setComments(comments == null || comments.length() == 0 ? "import all interface data" : comments);

			int res = cs.upload(requirements, summary, userId);

			totalCount = totalCount + summary.getBatchCount();
			resultCount = resultCount + summary.getResultCount();

			makeResultPage(totalCount, resultCount);
		}

	}



	private void makeResultPage(int totalCount, int resultCount) {
		// TODO Auto-generated method stub

	}


}