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
package pep.per.mint.front.service;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.monitor.FrontLog;
import pep.per.mint.common.data.basic.monitor.FrontLogOption;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.en.FrontLogService;
import pep.per.mint.front.env.FrontEnvironments;

/**
 * @author Solution TF
 *
 */
@Service
@SuppressWarnings({ "rawtypes" })
public class FrontManagementService {

	private static final Logger logger = LoggerFactory.getLogger(FrontManagementService.class);

	//Map<String, FrontLogOption> frontLogOptionMap;

	@Autowired
	FrontEnvironments env;

	@Autowired
	FrontLogService frontLogService;

	public void setEnvironmentValue(String key, Object envValue) throws Exception{

		env.apply(key, envValue);
	}

	public void init() throws Throwable{
		env.init();
	}


	/*@PostConstruct
	public void init(){
		StringBuffer msgBuffer = null;
		try{
			if(logger != null && logger.isInfoEnabled()){
				msgBuffer = new StringBuffer();
				LogUtil.bar2(msgBuffer, "System properties setting start");
			}
			System.setProperty("com.sun.management.jmxremote","");
			System.setProperty("com.sun.management.jmxremote.port","8855");
			System.setProperty("com.sun.management.jmxremote.ssl","false");
			System.setProperty("com.sun.management.jmxremote.authenticate","false");
			if(logger != null && logger.isInfoEnabled()){
				LogUtil.prefix(msgBuffer, "System properties setting OK");
			}
		}catch(Throwable t){
			logger.error("FrontManagementService.init 메소드 실행중 예외:",t);
			if(logger != null && logger.isInfoEnabled()){
				LogUtil.prefix(msgBuffer, "System properties setting FAIL:", t.getMessage());
			}
		}finally {
			if(logger != null && logger.isInfoEnabled()){
				LogUtil.bar(msgBuffer, "System properties setting end");
			}
		}

	}*/

	/**
	 * <pre>
	 * 프로트로그를 저장한다.
	 * 모조건 타게 하지 말고
	 * 포털 설정값에 환경변수를 이용하여
	 * 설정이 Y일때만 로그를 쌓도록 한다.
	 * system.front.logging="Y"
	 * 일단 임시로 log4j 설정 debug 단계로 체크해두자
	 * </pre>
	 * @param frontLog
	 */
	@Async
	public void saveFrontLog(ProceedingJoinPoint joinPoint, FrontLog frontLog, ComMessage reqComMessage, ComMessage resComMessage, HttpServletRequest request) {

		java.lang.System.setProperty("com.sun.management.jmxremote","");

		if("N".equalsIgnoreCase(env.getFrontLoggingYn())){
			logger.debug(Util.join("We did not save frontlog because Portal environment value frontLoggingYn = 'Y'"));
			return;
		}

		FrontLogOption option = null;
		if(env.getFrontLogOptionMap() != null){
			String key = joinPoint.toShortString();
			option = env.getFrontLogOptionMap().get(key);
		}

		option = option == null ? FrontLogOption.OPT_YNN : option;
		if(option.getLoggingYn().equals("N")) return;
		try{
			if(reqComMessage != null) {
				frontLog.setAppId(reqComMessage.getAppId());
				frontLog.setReqDate(Util.isEmpty(reqComMessage.getStartTime()) ? Util.getFormatedDate() : reqComMessage.getStartTime());
				if(option.getSaveRequestMsg().equals("Y"))  frontLog.setReqLog(Util.toJSONString(reqComMessage));
				frontLog.setUserId( Util.isEmpty(reqComMessage.getUserId()) ? "anyuser" : reqComMessage.getUserId());
				frontLog.setServiceId(joinPoint.toShortString());

				if(request != null){
					try{
						frontLog.setHttpRemoteAddr(request.getRemoteAddr());
					}catch(NullPointerException e){
						logger.debug("프론트로그 RemoteAddr은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpRemotePort(request.getRemotePort()+"");
					}catch(NullPointerException e){
						logger.debug("프론트로그 RemotePort은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpLocalAddr(  request.getLocalAddr()); //NullPointException이나서 막음
					}catch(NullPointerException e){
						logger.debug("프론트로그 LocalAddr은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpLocalPort(  request.getLocalPort()+"");//NullPointException이나서 막음
					}catch(NullPointerException e){
						logger.debug("프론트로그 LocalPort은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpServerName( request.getServerName());
					}catch(NullPointerException e){
						logger.debug("프론트로그 ServerName은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpServerPort( request.getServerPort()+"");
					}catch(NullPointerException e){
						logger.debug("프론트로그 ServerPort은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpServletPath(request.getServletPath());
					}catch(NullPointerException e){
						logger.debug("프론트로그 ServletPath은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpRequestURI( request.getRequestURI());
					}catch(NullPointerException e){
						logger.debug("프론트로그 RequestURI은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpMethod( 	 request.getMethod());
					}catch(NullPointerException e){
						logger.debug("프론트로그 Method은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpContentType(request.getContentType());
					}catch(NullPointerException e){
						logger.debug("프론트로그 ContentType은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpCharacterEncoding( request.getCharacterEncoding());
					}catch(NullPointerException e){
						logger.debug("프론트로그 CharacterEncoding은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
					try{
						frontLog.setHttpContextPath( request.getContextPath());
					}catch(NullPointerException e){
						logger.debug("프론트로그 ContextPath은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}

					try{
						frontLog.setHttpQueryString( request.getQueryString());
					}catch(NullPointerException e){
						logger.debug("프론트로그 QueryString은 남지 않음(NULL).");
					}catch(Exception e){
						logger.debug("프론트로그 HttpServletRequest 정보 세팅시 예외발생(무시할수 있는 예외)",e);
					}
				}

				if(resComMessage != null){
					frontLog.setResDate(Util.isEmpty(resComMessage.getEndTime()) ? Util.getFormatedDate() : resComMessage.getEndTime());

					String errCd  = resComMessage.getErrorCd();
					String errMsg = Util.join(resComMessage.getErrorMsg(), ", \nerrorDetail:", resComMessage.getErrorDetail());

					logger.debug(Util.join("프론트로그 errCd:" , errCd));
					logger.debug(Util.join("프론트로그 errMsg:" , errMsg));

					frontLog.setErrCd(errCd);
					frontLog.setErrMsg(errMsg);

					//요청 메시지에 응답까지 포함하므로 주석처리해둠 , 나중에 정리 필요하다.
					//if(option.getSaveResponseMsg().equals("Y")) frontLog.setResLog(Util.toJSONString(resComMessage));
				}else{
					frontLog.setResDate(Util.getFormatedDate());
				}
			}


			frontLogService.insertFrontLog(frontLog);
		}catch(Exception e){
			logger.debug("error occured when saving front log",e);
		}
	}
}
