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
package pep.per.mint.front.controller.co;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import pep.per.mint.common.data.basic.App;
import pep.per.mint.common.data.basic.Authority;
import pep.per.mint.common.data.basic.BasicInfo;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfacePattern;
import pep.per.mint.common.data.basic.InterfaceTag;
import pep.per.mint.common.data.basic.LoginInfo;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.UserRole;
import pep.per.mint.common.data.basic.monitor.ProblemClass;
import pep.per.mint.common.data.basic.security.LoginHistory;
import pep.per.mint.common.data.basic.security.UserSecurityProperty;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.co.SecurityService;
import pep.per.mint.front.env.FrontEnvironments;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.service.FrontSecurityService;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.HTTPServletUtil;
import pep.per.mint.front.util.MessageSourceUtil;
import pep.per.mint.inhouse.sso.ILoginService;


/**
 * <blockquote>
 * <pre>
 * <B>공통(Common) 서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-S01-CO-00-00-003</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">로그인</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #login(HttpSession, ComMessage, Locale, HttpServletRequest)}</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/login</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-001</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">시스템 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getSystemList(HttpSession, ComMessage, Locale, HttpServletRequest)}</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/systems</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-002</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">서버 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getServerList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/servers</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-003</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">업무 조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getBusinessList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/business</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-004</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">사용자조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getUserList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/users</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-006</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">인터페이스패턴조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfacePatternList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/interface-patterns</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-01-00-008</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">인터페이스조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfaceList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/interfaces</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R02-CO-01-00-008</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">인터페이스상세조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfaceDetail(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/interfaces/{interfaceId}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R03-CO-01-00-008</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">인터페이스상세-요건리스트</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfaceDetailRequiremnets(String, HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/interfaces/{interfaceId}/requirements</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-001</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">시스템코드검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getSystemCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/systems</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-002</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">서버코드검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getServerCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/servers</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-003</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">업무코드검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getBusinessCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/businesses</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R02-CO-02-00-003</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">최상위업무코드검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRootBusinessCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/businesses/root</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R03-CO-02-00-003</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">자식업무코드검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getChildBusinessCdList(HttpSession, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/businesses/{parentBusinessId}/child</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-004</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">인터페이스코드조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfaceCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/interfaces</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-005</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">공통코드조회</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getCommonCodeList(HttpSession, String, String, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/common-codes{level1}/{level2}</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-006</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">채널검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getChannelList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/channels</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-007</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">서비스검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getServiceList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/services</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-008</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">TAG검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getInterfaceTagList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/interface-tags</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-CO-02-00-009</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">요건검색</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getRequirementCdList(HttpSession, ComMessage, Locale, HttpServletRequest)} </td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/co/cds/requirements</td>
 * </tr>
 * </table>
 * </pre>
 * <blockquote>
 * @author Solution TF
 */
@Controller
@RequestMapping("/co")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	String ssoLoginValue = null;
	boolean ssoLogin = false;
	/**
	 * The Common service.
     */
	@Autowired
	CommonService commonService;

	@Autowired
	ILoginService loginService;

	@Autowired
	SecurityService securityService;

	@Autowired
	FrontSecurityService frontSecurityService;

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;

	/**
	 * The constant getSystemList.
     */
	public static final int getSystemList = 111;
	/**
	 * The constant getServerList.
	 */
	public static final int getServerList = 112;
	/**
	 * The constant getBusinessList.
     */
	public static final int getBusinessList = 113;
	/**
	 * The constant getUserList.
     */
	public final static int getUserList = 114;
	/**
	 * The constant getInterfacePatternList.
	 */
	public static final int getInterfacePatternList = 116;
	/**
	 * The constant getInterfaceList.
	 */
	public static final int getInterfaceList = 118;

	/**
	 * The constant getSystemCdList.
     */
	public static final int getSystemCdList = 121;
	/**
	 * The constant getServerCdList.
	 */
	public static final int getServerCdList = 122;
	/**
	 * The constant getBusinessCdList.
	 */
	public static final int getBusinessCdList = 123;
	/**
	 * The constant getRootBusinessCdList.
	 */
	public static final int getRootBusinessCdList = 223;
	/**
	 * The constant getInterfaceCdList.
	 */
	public final static int getInterfaceCdList = 124;
	/**
	 * The constant getChannelList.
	 */
	public static final int getChannelList = 125;
	/**
	 * The constant getServiceList.
	 */
	public static final int getServiceList = 126;
	/**
	 * The constant getInterfaceTagList.
	 */
	public static final int getInterfaceTagList = 127;
	/**
	 * The constant getRequirementCdList.
	 */
	public static final int getRequirementCdList = 128;
	/**
	 * The constant getAppList.
	 */
	public static final int getAppList = 129;


	public static final int getRootSystemCdList = 130;

	public static final int getChildSystemCdList = 131;

	public static final String DEFAULT_DEVELOPER_ROLE_ID = "UR00000000";

	/**
	 * Gets list with params.
	 *
	 * @param comMessage the com message
	 * @param apiId the api id
	 * @param locale the locale
	 * @param httpSession the http session
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	void getListWithParams(ComMessage comMessage, int apiId, Locale locale, HttpSession httpSession) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
            String errorMsg = "";
            Map params = null;
            List list = null;
            //-------------------------------------------------
            //파라메터 체크
            //-------------------------------------------------
			{
				params = (Map) comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}


            }
            //-------------------------------------------------
            //리스트를 조회한다.
            //-------------------------------------------------
            {
            	switch(apiId){
            	case getSystemList :
	            	list = commonService.getSystemList(params);
	            	break;
            	case getServerList :
	            	list = commonService.getServerList(params);
	            	break;
            	case getUserList :
	            	list = commonService.getUserList(params);
	            	break;
            	case getBusinessList :
	            	list = commonService.getBusinessList(params);
	            	break;
            	case getInterfacePatternList :
            		list = commonService.getInterfacePatternList(params);
            		break;
            	case getInterfaceList :
        			//-------------------------------------------------
        			//로그인 사용자 정보 셋팅
        			//-------------------------------------------------
        			if( comMessage.getCheckSession()) {
    					User user = (User) httpSession.getAttribute("user");
    					params.put("userId", user.getUserId());
    					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
        			}else{
        				params.put("userId", comMessage.getUserId());
        				params.put("isInterfaceAdmin", "Y");
        			}

//        			User user = (User) httpSession.getAttribute("user");
//        			params.put("userId", user.getUserId());
//        			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

            		list = commonService.getInterfaceList(params);
            		break;
            	//-------------------------------------------------
            	case getSystemCdList :
	            	list = commonService.getSystemCdList();
	            	break;
            	case getServerCdList :
	            	list = commonService.getServerCdList();
	            	break;
            	case getBusinessCdList :
	            	list = commonService.getBusinessCdList();
	            	break;
            	case getRootBusinessCdList :
	            	list = commonService.getRootBusinessCdList();
	            	break;
            	case getInterfaceCdList :
            		list = commonService.getInterfaceCdList();
            		break;
            	case getChannelList :
            		list = commonService.getChannelList();
            		break;
            	case getServiceList :
	            	list = commonService.getServiceList();
	            	break;
            	case getInterfaceTagList :
            		list = commonService.getInterfaceTagList();
            		break;
            	case getRequirementCdList :
            		list = commonService.getRequirementCdList();
            		break;
				case getAppList :
					list = commonService.getAppList();
					break;

				case getRootSystemCdList :
					list = commonService.getRootSystemCdList();
					break;


					case getChildSystemCdList :
				list = commonService.getChildSystemCdList(params);
					break;



            	}
            }

            //-------------------------------------------------
            //서비스 처리 종료시간을 얻어 CM에 세팅한다.
            //-------------------------------------------------
            {
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
            }
            //-------------------------------------------------
            //통신메시지에 처리결과 코드/메시지를 등록한다.
            //-------------------------------------------------
            {
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
            }
		}
	}

	public final static String SSO_LOGIN_OK = "0";

	/**
	 * <pre>
	 * 로그인
	 *
	 * API ID : REST-S01-CO-00-00-003
	 * </pre>
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
	@RequestMapping(
			value="/login",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, Map> login(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, Map> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {

		StringBuffer debugMsg = new StringBuffer();
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		try{
			String errorCd = "";
			String errorMsg = "";
			LoginInfo loginInfo = null;
			User user = null;


			//------------------------------------------------------
			//세션처리블럭
			//세션을 확인해 보고 이미 로그인되어 있으면 BasicInfo 만 다시 조회하는 것으로 판단한다.
			//최초 로그인이면 로그인 인증후 세션에 로그인 정보 담고 BasicInfo 조회하여 리턴한다.
			//------------------------------------------------------
			if(logger.isDebugEnabled()) LogUtil.bar2(debugMsg,LogUtil.prefix("로그 인프로세스 [CommonController.login] 시작"));
			{


				//-----------------------------------------------
				//loginInfo 필드 체크
				//-----------------------------------------------
				{
					if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "loginInfo 필드 체크");
					loginInfo = comMessage.getRequestObject();
					FieldCheckUtil.check("CommonController.login", loginInfo, messageSource, locale);
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(debugMsg, "loginInfo 필드 체크 OK");
						try {
							String loginInfoJsonStr = (Util.toJSONString(loginInfo));
							debugMsg.append(LogUtil.bar(LogUtil.prefix(loginInfoJsonStr)));
						} catch (Exception e) {
							//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
							logger.error("[무시해도되는에러] : " + e.getMessage());
						}
					}

				}

				//-----------------------------------------------
				//Session에서 기존 로그인 정보 GET
				//-----------------------------------------------
				{
					if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "Session에서 기존 로그인 정보 GET");
					user = (User) httpSession.getAttribute("user");

					//-----------------------------------------------
					//첫번째 접속이면 처리
					//-----------------------------------------------
					if (user == null) {

						if (logger.isDebugEnabled()) {
							LogUtil.prefix(debugMsg, "Session에 기존 로그인 정보 GET : 기존 로그인 정보 없음");
							LogUtil.prefix(debugMsg, "첫번째 접속일경우 처리");
						}

						//-----------------------------------------------
						//get system.sso.login
						//-----------------------------------------------
						Map<String, List<String>> environmentalValues = null;
						{
							environmentalValues = commonService.getEnvironmentalValues();
							List<String> values = environmentalValues.get("system.sso.login");
							if (values != null && values.size() > 0) {
								ssoLoginValue = values.get(0);
								ssoLogin = Boolean.parseBoolean(ssoLoginValue);
							}
						}

						//-------------------------------------------------------------------------------------------
						// subject  : 사용자보안 정보 확인
						// date     : 20190130
						// contents :
						// * 사용자보안 유효성 체크를 위한 정보(TOP0205 테이블)가 없으면 사용자보안 정보 새로 생성
						//   - isLock=N, ipList=null, psFailCnt=0
						// * 아시아나 반영시 추가됨
						//-------------------------------------------------------------------------------------------
						UserSecurityProperty userSecurityProperty = null;
						String clientIp = HTTPServletUtil.getRemoteIP(request);
						{
							if(logger.isDebugEnabled()) {
								LogUtil.prefix(debugMsg, "로그인 시 IP 제한 여부 : " + FrontEnvironments.ipCheck);
								LogUtil.prefix(debugMsg, "로그인시 계정 잠김 체크 : " + FrontEnvironments.passwordFailCheck);
								LogUtil.prefix(debugMsg, "로그인 히스토리 사용 : " + FrontEnvironments.historyYn);
							}

							if(FrontEnvironments.ipCheck || FrontEnvironments.passwordFailCheck) {
								//-----------------------------------------------
								//TOP0205 테이블에서 사용자보안 정보 조회
								//-----------------------------------------------
								userSecurityProperty = securityService.getUserSecurity(loginInfo.getUserId());
								if(logger.isDebugEnabled()) {
									try {
										String toJson = (Util.toJSONString(userSecurityProperty));
										debugMsg.append(LogUtil.bar(LogUtil.prefix(toJson)));
									} catch (Exception e) {
										//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
										logger.error("[무시해도되는에러] : " + e.getMessage());
									}
								}

								//-----------------------------------------------
								//사용자 보안 정보가 없을 경우
								//-----------------------------------------------
								if(userSecurityProperty==null || userSecurityProperty.getUserId().equals("")) {
									if(logger.isDebugEnabled()) {
										LogUtil.prefix(debugMsg, "사용자보안 정보(TOP0205)가 없음");
									}

									userSecurityProperty = new UserSecurityProperty();
									userSecurityProperty.setUserId(loginInfo.getUserId());
									userSecurityProperty.setIsAccountLock("N");
									userSecurityProperty.setPsFailCnt(0);
									userSecurityProperty.setLastLoginDate("-");
									userSecurityProperty.setLastLoginIp("-");
									securityService.insertUserSecurity(userSecurityProperty);

									if(logger.isDebugEnabled()) {
										LogUtil.prefix(debugMsg, "사용자보안 정보(TOP0205) 새로 생성");
									}
								}
							}
						}//end 사용자보안 정보 확인

						//-----------------------------------------------
						//check password & login process
						//-----------------------------------------------
						{
							if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "사용자정보 얻어오기(commonService.getUser(", loginInfo.getUserId(), "))");
							user = commonService.getUser(loginInfo.getUserId(), true);
							//---------------------------------------------
							//사용자가 존재하지 않으면 에러 응답준다.
							//---------------------------------------------
							if (user == null) {

								if(!ssoLogin){
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.userId", locale);
									Object[] errorMsgParams = {loginInfo.getUserId()};
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.userId", errorMsgParams, locale);

									//-------------------------------------------------------------------------------------------
									// subject  : 사용자보안 - Account Lock
									// date     : 20190130
									// contents :
									// * 로그인실패횟수에 따른 Lock 설정(사용자 정보가 없어도 로그인실패횟수 증가)
									// * ssoLogin 사용이 아닐경우만 체크한다
									// * 아시아나 반영시 추가됨
									// * TODO : 존재하지 않은 계정으로 로그인한 이력을 조회/삭제할 수 있는 관리 화면이 필요함
									//-------------------------------------------------------------------------------------------
									{
										if(FrontEnvironments.passwordFailCheck) {
											if(logger.isDebugEnabled()) {
												LogUtil.prefix(debugMsg, "사용자보안 정보 테이블에 실패횟수 업데이트");
											}

											int failCnt = userSecurityProperty.getPsFailCount() + 1;
											userSecurityProperty.setPsFailCnt(failCnt);

											Object errorMsgParam[] = {failCnt, FrontEnvironments.failCheckCount};
											errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.account.check.password.fail", locale);
											errorMsg = Util.join(errorMsg, "\n", MessageSourceUtil.getErrorMsg(messageSource, "error.msg.account.check.password.fail", errorMsgParam, locale) );

											if( failCnt >= FrontEnvironments.failCheckCount ) {
												userSecurityProperty.setIsAccountLock("Y");
												errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.account.locked", locale);
												errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.account.locked", errorMsgParam, locale);
											}

											securityService.updateUserSecurity(userSecurityProperty);
										}
									}//end account lock

								}else{
									//sso 로그인일 경우 별도의 에러 메시지 전송 처리 ("IIP에 계정이 없습니다. 담당자에게 문의 해주십시오.")
									//error.cd.login.user.not.found=1107
									//error.msg.login.user.not.found=IIP에 계정이 없습니다. 담당자에게 문의 해주십시오.
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.user.not.found", locale);
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.user.not.found", null, locale);
								}

								comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
								comMessage.setErrorCd(errorCd);
								comMessage.setErrorMsg(errorMsg);
								if (logger.isDebugEnabled()) {
									LogUtil.prefix(debugMsg, "사용자정보 얻어오기(commonService.getUser(", loginInfo.getUserId(), ")) FAIL");
									LogUtil.prefix(debugMsg, "errorMsg:", errorMsg);
								}
								return comMessage;
							}


							//-----------------------------------------------
							//로그인 권한 체크
							//TODO: 현재 사용되고 있지 않음
							//-----------------------------------------------
							{
								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "로그인 권한 체크");
								Authority authority = commonService.getAuthority(user.getRole());
								if(!authority.getLoginAuthority()) {
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.no.authority", locale);
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.no.authority", null, locale);
									comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
									comMessage.setErrorCd(errorCd);
									comMessage.setErrorMsg(errorMsg);
									if (logger.isDebugEnabled()) {
										LogUtil.prefix(debugMsg, "로그인 권한 체크 FAIL");
										LogUtil.prefix(debugMsg, "errorMsg:", errorMsg);
									}
									return comMessage;
								}
								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "로그인 권한 체크 ok");
							}



							if (logger.isDebugEnabled()){
								LogUtil.prefix(debugMsg, "사용자정보 얻어오기(commonService.getUser(", loginInfo.getUserId(), ")) OK");

								try {
									String userJsonStr = (Util.toJSONString(user));
									debugMsg.append(LogUtil.bar(LogUtil.prefix(userJsonStr)));
								} catch (Exception e) {
									//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
									logger.error("[무시해도되는에러] : " + e.getMessage());
								}
							}



							//--------------------------------------------------------------------------------
							//로그인 정보에 SSO 사용 true 이고 개발자 계정이 아니면  패스워드 체크를 SKIP, SSO 에 로그인 한다.
							//--------------------------------------------------------------------------------
							UserRole role = user.getRole();
							String developerRoleId = DEFAULT_DEVELOPER_ROLE_ID;
							boolean isDeveloper = false;
							if(!Util.isEmpty(role) && !Util.isEmpty(role.getRoleId()) && developerRoleId.equalsIgnoreCase(role.getRoleId())){
								isDeveloper = true;
							}

							//--------------------------------------------------------------------------------
							//로직추가
							//170927
							//특정사용자를 SSO 로그인하지 않도록 적용
							//system.sso.skip.user 옵션에 SSO 로그인하지 않을 사용자 ID를 등록해라.
							//--------------------------------------------------------------------------------
							{
						    	try{
					    			List<String> envs = environmentalValues.get("system.sso.login.skip.user");
					    			if(!Util.isEmpty(envs)){
					    				if(envs.contains(user.getUserId())) {
					    					ssoLogin = false;
					    					logger.debug(Util.join("**현재사용자:[", user.getUserId() , "]는 SSO 로그인 제외 대상[system.sso.login.skip.user]"));
					    				}
					    			}
						    	}catch(Exception e){logger.error("ignorableException:", e);}

							}

							if( ssoLogin && !isDeveloper ) {
								//-------------------------------------------------------------------------------------------
								// ▶ IIP상의 ID/PWD 로 로그인 하지 않은 경우( SSO 로그인 이고, 사용자가 super-user 가 아닐경우 )
								//-------------------------------------------------------------------------------------------

								logger.debug(Util.join("**현재사용자:[", user.getUserId() , "]는 SSO 로그인이며 개발자는 아님"));
								//-----------------------------------------------
								//사이트별 SSO 처리 코드 추가
								//2017.06.29
								//-----------------------------------------------
								//1.적용 사이트 : 현대해상
								//2.적용방법 포털환경변수값을 이용하여 SSO IN-HOUSE 서비스를 처리할지 결정한다.
								//	system.sso.login = true
								//-----------------------------------------------

								Map<String, String> params = new HashMap<String, String>();
								params.put("userId", loginInfo.getUserId());
								params.put("password", loginInfo.getPassword());
								params.put("isSso", loginInfo.getSso());

								//---------------------------------------------
								//change-when : 170830
								//change-what : 클라이언트 아이피 얻는 방법 변경
								//change-why  : request.getRemoteAddr() 를 사용할 경우
								// 				프록시나 방화벽 기타 WAS  의 종류 등 환경적 원인으로 값을얻지 못하는 경우 발생함
								//change-who  : whoana
								//---------------------------------------------
								//String clientIp = HTTPServletUtil.getRemoteIP(request);//20190208 주석처리
								//params.put("clientIp", request.getRemoteAddr());
								params.put("clientIp", clientIp);


								Map loginResult = loginService.login(params);
								if(loginResult == null) {
									comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.sso.no.reply", locale);
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.sso.no.reply", null, locale);
									comMessage.setErrorCd(errorCd);
									comMessage.setErrorMsg(errorMsg);
									return comMessage;
								}
								String loginErrorCd = (String)loginResult.get("errorCd");
								//에러발생시 처리
								if(!SSO_LOGIN_OK.equals(loginErrorCd)){
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.sso.fail", locale);
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.sso.fail", null, locale);
									String loginErrorMsg = Util.join(errorMsg,"[",loginErrorCd,":",(String)loginResult.get("errorMsg"),"]");
									comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
									comMessage.setErrorCd(errorCd);
									comMessage.setErrorMsg(loginErrorMsg);
									return comMessage;
								}
								//에러가 아닐경우 내부 로그인 절차를 위해 sso  flag 설정한다.
								//sso 설정시 패스워드 체크를 하지 않는다.
								loginInfo.setSso("true");
								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "로그인 정보에 SSO 사용 true 라서 패스워드 체크를 SKIP한다.");


							} else {
								//-------------------------------------------------------------------------------------------
								// ▶ IIP상의 ID/PWD 로 로그인 하는 경우(일반적인 로그인, SSO 로그인 이지만 사용자가 super-user 일 경우)
								//-------------------------------------------------------------------------------------------

								logger.debug(Util.join("**현재사용자:[", user.getUserId() , "]는 SSO 로그인을 하지 않는 개발자 계정 이거나 SSO 로그인 제외 대상자임"));
								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "패스워드 체크");

								//-------------------------------------------------------------------------------------------
								// subject  : 패스워드 SHA256 암호화
								// date     : 20190130
								// contents :
								// * DB에 패스워드를 암호화 시켜 저장할 경우, 비교를 위해  평문을 SH256 으로 암호화 시킴
								// * 아시아나 반영시 추가됨
								//-------------------------------------------------------------------------------------------
								if(FrontEnvironments.passwordEncrypt){
									if(logger.isDebugEnabled()) {
										LogUtil.prefix(debugMsg, "패스워드 SHA256 암호화");
									}
									String pswd = securityService.getHashSHA256(loginInfo.getPassword(), "".getBytes());
									loginInfo.setPassword(pswd);
								}

								//---------------------------------------------
								//패스워드가 맞지 않으면 에러 응답준다.
								//---------------------------------------------
								if (!loginInfo.getPassword().equals(user.getPassword())) {
									errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.password", locale);
									errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.password", null, locale);

									//-------------------------------------------------------------------------------------------
									// subject  : 사용자보안 - Account Lock
									// date     : 20190130
									// contents :
									// * 로그인실패횟수에 따른 Lock 설정
									// * 아시아나 반영시 추가됨
									//-------------------------------------------------------------------------------------------
									{
										if(FrontEnvironments.passwordFailCheck) {
											if(logger.isDebugEnabled()) {
												LogUtil.prefix(debugMsg, "사용자보안 정보 테이블에 실패횟수 업데이트");
											}

											int failCnt = userSecurityProperty.getPsFailCount() + 1;
											userSecurityProperty.setPsFailCnt(failCnt);

											Object errorMsgParam[] = {failCnt, FrontEnvironments.failCheckCount};
											errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.account.check.password.fail", locale);
											errorMsg = Util.join(errorMsg, "\n", MessageSourceUtil.getErrorMsg(messageSource, "error.msg.account.check.password.fail", errorMsgParam, locale) );

											if( failCnt >= FrontEnvironments.failCheckCount ) {
												userSecurityProperty.setIsAccountLock("Y");
												errorCd  = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.account.locked", locale);
												errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.account.locked", errorMsgParam, locale);
											}

											securityService.updateUserSecurity(userSecurityProperty);
										}
									}//end account lock

									comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
									comMessage.setErrorCd(errorCd);
									comMessage.setErrorMsg(errorMsg);

									if (logger.isDebugEnabled())
										LogUtil.prefix(debugMsg, "패스워드 체크 FAIL:errorMsg:", errorMsg);

									return comMessage;
								}//end password check : not equals
								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "패스워드 체크 OK");


								//-------------------------------------------------------------------------------------------
								// subject  : 사용자보안 유효성 체크
								// date     : 20190130
								// contents :
								// * 허용IP 및 로그인실패횟수에 따른 Lock 체크
								// * 아시아나 반영시 추가됨
								//-------------------------------------------------------------------------------------------
								//1) 계정 Lock 체크여부 확인 (계정이 Lock 상태일 경우 Error Return)
								//2) IP 체크여부 확인 (허용된 IP 가 아닐 경우 Error Return)
								//-------------------------------------------------------------------------------------------
								{
									//-----------------------------------------------
									// Check AccountLock
									//-----------------------------------------------
									if(FrontEnvironments.passwordFailCheck){
										boolean isLocked = securityService.checkAccountLock(userSecurityProperty, loginInfo.getUserId());

										if(isLocked){
											if(logger.isDebugEnabled()) {
												LogUtil.prefix(debugMsg, "[사용자보안 유효성 체크] - 로그인 계정 잠김");
											}
											errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.account.locked", locale);
											errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.account.locked", null, locale);
											comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
											comMessage.setErrorCd(errorCd);
											comMessage.setErrorMsg(errorMsg);

											return comMessage;
										}
									}

									//-----------------------------------------------
									// Check IP
									//-----------------------------------------------
									if(FrontEnvironments.ipCheck) {
										boolean existIp = securityService.checkIp(userSecurityProperty, clientIp);

										if(!existIp){
											if(logger.isDebugEnabled()) {
												LogUtil.prefix(debugMsg, "[사용자보안 유효성 체크] - 해당 계정에 허용된 IP가 아님");
											}
											errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.ip", locale);
											errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.ip", null, locale);
											comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
											comMessage.setErrorCd(errorCd);
											comMessage.setErrorMsg(errorMsg);

											return comMessage;
										}
									}
								}//end 사용자보안 유효성 체크


								//-------------------------------------------------------------------------------------------
								// subject  : 패스워드 유효기간 체크
								// date     : 20190130
								// contents :
								// * 패스워드 변경 기간이 도래했는지 확인
								// * 아시아나 반영시 추가됨
								//-------------------------------------------------------------------------------------------
								{
									if( FrontEnvironments.passwordCheckYn ) {
										//-----------------------------------------------
										// 포탈 환경설정의 패스워드 변경 기간 값
										//-----------------------------------------------
										int passwordInterval  = FrontEnvironments.passwordInterval;
										//-----------------------------------------------
										// 사용자 정보가 유효하면 패스워드 유효기간 체크
										//-----------------------------------------------
										if( user != null && user.getPassword().equals(loginInfo.getPassword()) ) {
											Calendar today = Calendar.getInstance();
											Calendar modDate = Calendar.getInstance();
											SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

											Map responseObject = new HashMap();
											//-----------------------------------------------
											// 패스워드 변경 이력 조회
											//-----------------------------------------------
											List<Map> changePasswordList = securityService.checkChangePassword(loginInfo.getUserId());
											boolean passwordChangeRequired = false;
											if( changePasswordList != null && changePasswordList.size() > 0 ) {
												//가장 최근 패스워드 변경 이력
												Map latestPasswordChange = changePasswordList.get(0);
												modDate.setTime(sdf.parse(latestPasswordChange.get("modDate").toString()));
												//-----------------------------------------------
												// 패스워드 유효 기한이 지났을 경우 변경 요구
												//-----------------------------------------------
												if( (today.getTimeInMillis()-modDate.getTimeInMillis())/(1000*3600*24) > passwordInterval ) {
													passwordChangeRequired = true;
												}
											} else {
												//패스워드 변경 이력이 없는 경우 변경 요구
												passwordChangeRequired = true;
											}

											if( passwordChangeRequired ) {
												if(logger.isDebugEnabled()) {
													LogUtil.prefix(debugMsg, "패스워드 변경기간 도래 : " + passwordChangeRequired);
												}
												responseObject.put("passwordChangeRequired", true);
												comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
												comMessage.setResponseObject(responseObject);
												return comMessage;
											}
										}
									}
								}//end 패스워드 유효기간 체크


							}//end if 일반적인로그인블럭

							//-------------------------------------------------------------------------------------------
							// 여기까지 에러 없이 진행되었으면 기본적인 인증절차 완료
							//-------------------------------------------------------------------------------------------

							//---------------------------------------------
							//세션값 세팅
							//---------------------------------------------
							{
								httpSession.setAttribute("user", user);
								httpSession.setAttribute("httpSessionId", httpSession.getId());
								//httpSession.setMaxInactiveInterval(15*60);//15분
								//<session-config>
								//	<session-timeout>15</session-timeout> <!-- 15분 -->
								//</session-config>

								//-------------------------------------------------------------------------------------------
								//[임시버전-20200520]
								//- NH농협 보안취약점 대응수준으로 작성된 부분으로, 향후 AccessControl 표준 정리후 보완 필요
								//- CommonInterceptor 에서 화면 접근 권한 체크
								//- TODO : AccessControl 내용 보완
								//-------------------------------------------------------------------------------------------
								{
									httpSession.setAttribute("restrictAccessAppList", commonService.getRestrictAccessAppList(user.getUserId()) );
								}

							}
							//---------------------------------------------
							//로그인 성공 메시지 세팅 & 응답
							//---------------------------------------------
							{
								errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
								errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.login.ok", null, locale);
								comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
								comMessage.setErrorCd(errorCd);
								comMessage.setErrorMsg(errorMsg);

								try{
									Map loginParams = new HashMap();
									loginParams.put("userId",user.getUserId());
									loginParams.put("sessionId",httpSession.getId());
									loginParams.put("loginDate",Util.getFormatedDate());
									loginParams.put("ip", clientIp); //아시아나 반영건으로 추가됨
									//-------------------------------------------------------------------------------------------
									// 기존 로그인 히스토리
									//-------------------------------------------------------------------------------------------
									commonService.insertLoginUser(loginParams);

									//-------------------------------------------------------------------------------------------
									// subject  : 로그인 히스토리(TOP0204)
									// date     : 20190130
									// contents :
									// * 로그인 성공시 히스토리 등록
									// * 아시아나 반영시 추가됨
									// * 로그인 히스토리 정보가 기존 테이블과(TOP0200) 유사함에 따라 향후 정리가 필요할듯 함
									//-------------------------------------------------------------------------------------------
									{
										if(FrontEnvironments.historyYn) {
											//-----------------------------------------------
											//LastLogin 정보 추출
											//-----------------------------------------------
											LoginHistory lastLogin = securityService.getLoginHistory(user.getUserId());
											//-----------------------------------------------
											//이전 로그인 히스토리가 없을 경우 현재 로그인 정보 입력
											//-----------------------------------------------
											if( lastLogin == null || lastLogin.getUserId() == null ) {
												lastLogin = new LoginHistory();
												lastLogin.setUserId((String)loginParams.get("userId"));
												lastLogin.setLoginDate((String)loginParams.get("loginDate"));
												lastLogin.setIp((String)loginParams.get("ip"));
											}

											if(logger.isDebugEnabled()) {
												try {
													String toJson = (Util.toJSONString(lastLogin));
													logger.debug("LastLogin : " + toJson);
												} catch (Exception e) {
													//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
													logger.error("[무시해도되는에러] : " + e.getMessage());
												}
											}

											//-----------------------------------------------
											//UI에서 참조 가능하도록 마지막 접속 정보 설정
											//-----------------------------------------------
											user.setLoginHistory(lastLogin);

											//-----------------------------------------------
											//CurrentLogin 히스토리 반영
											//-----------------------------------------------
											securityService.insertLoginHistory(loginParams);
										}
									}//end Login History

									//-------------------------------------------------------------------------------------------
									// subject  : 중복 로그인 허용 금지
									// date     : 20190130
									// contents :
									// * 중복 로그인 허용 금지
									// * 아시아나 반영시 추가됨
									//-------------------------------------------------------------------------------------------
									{
										if(logger.isDebugEnabled()) {
											logger.debug("중복 로그인 체크 사용 : " + FrontEnvironments.duplicationLoginCheckYn+ "// option : " + FrontEnvironments.duplicationOption);
										}

										if(FrontEnvironments.duplicationLoginCheckYn){
											boolean check = frontSecurityService.checkDuplicationLogin( httpSession, request, FrontEnvironments.duplicationOption );
											if(check) {
												//-----------------------------------------------
												// 중복 로그인의 경우 기존 세션을 종료하고 사용자에게 알림정보로만 제공하기에
												// 추가 로직 구현이 없음
												//-----------------------------------------------
											}
										}
									}//end login dup check

									//-------------------------------------------------------------------------------------------
									// subject  : 로그인 실패 카운트 리셋
									// date     : 20190130
									// contents :
									// * 로그인 성공 시 psFailCnt 리셋
									// * 아시아나 반영시 추가됨
									//-------------------------------------------------------------------------------------------
									{
										if(FrontEnvironments.passwordFailCheck){
											if(logger.isDebugEnabled()) {
												logger.debug("로그인 성공으로 psFailCnt 리셋");
											}
											userSecurityProperty.setLastLoginDate(loginParams.get("loginDate").toString());
											userSecurityProperty.setLastLoginIp(loginParams.get("ip").toString());
											securityService.resetAccount(userSecurityProperty);
										}
									}//end Reset Login Fail Count

								}catch(Exception e){
									logger.error("[무시해도되는예외]로그인 사용자 정보 로깅하다 에러 발생!:",e);
								}

								if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "첫번째 로그인 성공");
								return comMessage;
							}
						}
					} else {

						if (logger.isDebugEnabled()) {
							LogUtil.prefix(debugMsg, "Session에 기존 로그인 정보 GET OK");
							try {
								String userJsonStr = (Util.toJSONString(user));
								debugMsg.append(LogUtil.bar(LogUtil.prefix(userJsonStr)));
							} catch (Exception e) {
								//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
								logger.error("[무시해도되는에러] : " + e.getMessage());
							}
						}



						//-----------------------------------------------
						//기존 세션이 존재하면 처리
						//-----------------------------------------------
						{
							if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "기존 세션이 존재할경우 처리프로세스 시작 ");

							String alreadyUserId = user.getUserId();
							String nowUserId = loginInfo.getUserId();
							//-----------------------------------------------
							//기존 세션 사용자가 로그인 시도한 사용자와 틀리면 처리
							//-----------------------------------------------
							if (!alreadyUserId.equals(nowUserId)) {

								if (logger.isDebugEnabled()) {
									LogUtil.prefix(debugMsg, "기존 로그인 USER 와 틀린 사용자 정보로 로그인 시도");
									LogUtil.prefix(debugMsg, "old user:", user.getUserId(), " connected already. lastAccessedTime:", Util.getFormatedDate(httpSession.getLastAccessedTime(), Util.DEFAULT_DATE_FORMAT));
									LogUtil.prefix(debugMsg, "new user:", nowUserId, " trying to login...");
								}

								errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.already.user", locale);
								Object[] errorMsgParams = {alreadyUserId, nowUserId};
								errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.already.user", errorMsgParams, locale);
								comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
								comMessage.setErrorCd(errorCd);
								comMessage.setErrorMsg(errorMsg);

								if (logger.isDebugEnabled())
									LogUtil.prefix(debugMsg, "기존 세션이 존재할경우 처리프로세스 처리 FAIL:errorMsg: ", errorMsg);

								return comMessage;
							}else{
							//---------------------------------------------
							//로그인 성공 메시지 세팅 & 응답
							//---------------------------------------------
								errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
								errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.relogin.ok", null, locale);
								comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
								comMessage.setErrorCd(errorCd);
								comMessage.setErrorMsg(errorMsg);
								if (logger.isDebugEnabled()) {
									LogUtil.prefix(debugMsg, "user:", user.getUserId(), " connected before. lastAccessedTime:",
											Util.getFormatedDate(httpSession.getLastAccessedTime(), Util.DEFAULT_DATE_FORMAT));
									LogUtil.prefix(debugMsg, "기존 세션이 존재할경우 처리프로세스 처리 OK ");
								}

								return comMessage;
							}


						}
					}
				}
			}
		}finally{
			//-------------------------------------------------------------------------------------------
			// subject  : 보안 코드 추가
			// date     : 20170718
			// contents :
			// 요청시 암호화된 패스워드가 응답시 복호화되어 돌려보내는 부분을 막기위해 requestObject 를 null 처리함
			//-------------------------------------------------------------------------------------------
			comMessage.setRequestObject(null);
			//-------------------------------------------------------------------------------------------

			if(logger.isDebugEnabled()) {
				debugMsg.append(LogUtil.bar2(LogUtil.prefix("로그 인프로세스 [CommonController.login] 종료")));
				logger.debug(debugMsg.toString());
			}
		}
	}
	/*@RequestMapping(
			value="/login",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, BasicInfo> login(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, BasicInfo> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			LoginInfo loginInfo = null;
			User user = null;
			//------------------------------------------------------
			//세션처리블럭
			//세션을 확인해 보고 이미 로그인되어 있으면 BasicInfo 만 다시 조회하는 것으로 판단한다.
			//최초 로그인이면 로그인 인증후 세션에 로그인 정보 담고 BasicInfo 조회하여 리턴한다.
			//------------------------------------------------------
			{

				user = (User) httpSession.getAttribute("user");

				if(user == null){

					//-----------------------------------------------
					//첫번째 접속이면 처리
					//-----------------------------------------------
					{
						loginInfo = comMessage.getRequestObject();
					}
					//-----------------------------------------------
					//loginInfo 필드 체크
					//-----------------------------------------------
					{
						FieldCheckUtil.check("CommonController.login", loginInfo, messageSource, locale);
					}


					//-----------------------------------------------
					//check password & login process
					//-----------------------------------------------
					{
						user = commonService.getUser(loginInfo.getUserId());
						//---------------------------------------------
						//사용자가 존재하지 않으면 에러 응답준다.
						//---------------------------------------------
						if(user == null) {
							errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.userId", locale);
							Object[] errorMsgParams = {loginInfo.getUserId() };
							errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.userId", errorMsgParams, locale);
							comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
							comMessage.setErrorCd(errorCd);
							comMessage.setErrorMsg(errorMsg);
							return comMessage;
						}
						//---------------------------------------------
						//패스워드가 맞지 않으면 에러 응답준다.
						//---------------------------------------------
						if(!loginInfo.getPassword().equals(user.getPassword())){
							errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.login.invalid.password", locale);
							errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.login.invalid.password", null, locale);
							comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
							comMessage.setErrorCd(errorCd);
							comMessage.setErrorMsg(errorMsg);
							return comMessage;
						}

						//---------------------------------------------
						//세션값 세팅
						//---------------------------------------------
						{
							httpSession.setAttribute("user", user);
							//httpSession.setMaxInactiveInterval(15*60);//15분
							//<session-config>
						    //	<session-timeout>15</session-timeout> <!-- 15분 -->
							//</session-config>
						}
					}

				}else{
					//-----------------------------------------------
					//기존 세션이 존재하면 처리
					//-----------------------------------------------
					{
						logger.debug(Util.join("user:",user.getUserId()," connected before. lastAccessedTime:",
						Util.getFormatedDate(httpSession.getLastAccessedTime(),Util.DEFAULT_DATE_FORMAT)));
					}
				}


			}

			//-----------------------------------------------
			//BasicInfo를 얻어온다.
			//-----------------------------------------------
			BasicInfo basicInfo = null;
			{
				basicInfo = commonService.getBasicInfo(user.getUserId());
			}

			//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
			{

				if (basicInfo == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(basicInfo);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}*/


	/**
	 * <pre>
	 * 로그아웃
	 *
	 * API ID : REST-S02-CO-00-00-003
	 * </pre>
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
	@RequestMapping(
			value="/logout",
			params="method=POST",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, BasicInfo> logout(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, BasicInfo> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {

		StringBuffer debugMsg = new StringBuffer();
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		try{
			String errorCd = "";
			String errorMsg = "";
			LoginInfo loginInfo = null;
			User user = null;


			//------------------------------------------------------
			//세션처리블럭
			//세션을 확인해 보고 이미 로그인되어 있으면 BasicInfo 만 다시 조회하는 것으로 판단한다.
			//최초 로그인이면 로그인 인증후 세션에 로그인 정보 담고 BasicInfo 조회하여 리턴한다.
			//------------------------------------------------------
			if(logger.isDebugEnabled()) LogUtil.bar2(debugMsg,LogUtil.prefix("로그아웃 프로세스 [CommonController.logout] 시작"));
			{


				//-----------------------------------------------
				//loginInfo 필드 체크
				//-----------------------------------------------
				/*
			    //FieldCheckUtil.check 에서 sso == false 일경우 패스워드를 체크하고 있는데 logoff 시에는 password 가 없으므로 체크하지 않도록 주석 처리
				{
					if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "loginInfo 필드 체크");
					loginInfo = comMessage.getRequestObject();
					FieldCheckUtil.check("CommonController.logout", loginInfo, messageSource, locale);
					if (logger.isDebugEnabled()) {
						LogUtil.prefix(debugMsg, "loginInfo 필드 체크 OK");
						try {
							String loginInfoJsonStr = (Util.toJSONString(loginInfo));
							debugMsg.append(LogUtil.bar(LogUtil.prefix(loginInfoJsonStr)));
						} catch (Exception e) {
							//[아시아나 보안점검 지적사항 반영] :: 빈공백의 catch 문을 사용하지 말것
							logger.error("[무시해도되는에러] : " + e.getMessage());
						}
					}


				}
				*/

				//-----------------------------------------------
				//Session에서 기존 로그인 정보 GET
				//-----------------------------------------------
				{
					if (logger.isDebugEnabled()) LogUtil.prefix(debugMsg, "Session에서 기존 로그인 정보 GET");
					user = (User) httpSession.getAttribute("user");


					try{
						Map loginParams = new HashMap();
						loginParams.put("userId",user.getUserId());
						loginParams.put("sessionId",httpSession.getId());
						loginParams.put("logoutDate",Util.getFormatedDate());
						commonService.updateLogoutUser(loginParams);

						//-------------------------------------------------------------------------------------------
						// subject  : 로그인 히스토리 테이블에 업데이트(TOP0204)
						// date     : 20190130
						// contents :
						// * 로그아웃 타임 업데이트 수행
						// * 아시아나 반영시 추가됨
						//-------------------------------------------------------------------------------------------
						{
							if(FrontEnvironments.historyYn)  {
								securityService.updateLoginHistory(loginParams);
							}
						}//end loginHistory

					}catch(Exception e){
						logger.error("[무시해도되는예외]로그아웃 사용자 정보 로깅하다 에러 발생!:",e);
					}

					//-------------------------------------------------------------------------------------------
					// subject  : 세션맵 정리
					// date     : 20190130
					// contents :
					// * 중복 로그인 체크를 위한 sessionMap 정리
					// * 아시아나 반영시 추가됨
					//-------------------------------------------------------------------------------------------
					{
						if(FrontEnvironments.duplicationLoginCheckYn){
							String checkKey = (String)httpSession.getAttribute("checkKey");
							try {
								frontSecurityService.removeSession(checkKey);
							}catch(Exception e){
							}
						}
					}//end sessionMap clear

					httpSession.removeAttribute("user");
					httpSession.invalidate();

				}

				{
					//---------------------------------------------
					//로그인 성공 메시지 세팅 & 응답
					//---------------------------------------------
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);

					return comMessage;
				}
			}
		}finally{
			if(logger.isDebugEnabled()) {
				debugMsg.append(LogUtil.bar2(LogUtil.prefix("로그 인프로세스 [CommonController.logout] 종료")));
				logger.debug(debugMsg.toString());
			}
		}
	}


	/**
	 * <pre>
	 * getLoginUserCountInfo
	 * REST-R03-CO-02-00-000 로그인사용자수정보조회
	 * API ID :
	 * </pre>
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
	@RequestMapping(
			value="/login-count-info",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> getLoginUserCountInfo(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, Map> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map res = null;

			{
				int todayLoginUserCnt = commonService.getTodayLoginUserCnt();
				int totalLoginUserCnt = commonService.getTotalLoginUserCnt();
				res = new HashMap();
				res.put("todayLoginUserCnt",todayLoginUserCnt);
				res.put("totalLoginUserCnt",totalLoginUserCnt);
			}

			//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
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
	 * getBasicInfo
	 * REST-R01-CO-02-00-000 기초데이터조회
	 * API ID :
	 * </pre>
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
	@RequestMapping(
			value="/basic-info/{userId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, BasicInfo> getBasicInfo(
			HttpSession  httpSession,
			@PathVariable("userId") String userId,
			@RequestBody ComMessage<Map, BasicInfo> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			BasicInfo basicInfo = null;

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");

			//-----------------------------------------------
			//loginInfo 필드 체크
			//-----------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.getBasicInfo", userId, messageSource, locale);
			}



			//-----------------------------------------------
			//BasicInfo를 얻어온다.
			//-----------------------------------------------
			{
				basicInfo = commonService.getBasicInfo(user.getUserId());
				//basicInfo = commonService.getBasicInfoV2(userId);
			}

			//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
			{

				if (basicInfo == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(basicInfo);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}



	/**
	 * <pre>
	 * getSearchInfo
	 * REST-R02-CO-02-00-000 연관검색조건조회
	 * API ID :
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.09)
     */
	@RequestMapping(
			value="/search-info",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, BasicInfo> getSearchInfo(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, BasicInfo> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			BasicInfo basicInfo = null;

			//-----------------------------------------------
			//BasicInfo를 얻어온다.
			//-----------------------------------------------
			{
				Map params = comMessage.getRequestObject();
				params = params == null ? new HashMap() : params;
				basicInfo = commonService.getSearchInfo(params);
			}

			//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
			{

				if (basicInfo == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(basicInfo);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 로그인 테스트 유닛
	 *
	 * API ID : REST-S01-CO-00-00-003
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value="/login",
			params={"method=POST", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<LoginInfo, BasicInfo> testLogin(
			HttpSession  httpSession,
			@RequestBody ComMessage<LoginInfo, BasicInfo> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/co/REST-S01-CO-00-00-003.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			BasicInfo basicInfo = (BasicInfo) Util.readObjectFromJson(new File(testFilePath), BasicInfo.class, "UTF-8");

			//-----------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------
			{

				if (basicInfo == null) {// 결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(basicInfo);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 시스템 리스트, 시스템 상세, 서버리스트, 서버 상세를 한번에 조회하여 시스템 복합유형을
	 * 구성하여 리스트로 돌려준다.
	 * API ID : REST-R01-CO-01-00-001
	 * </pre>
	 * @param httpSession the http session
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
			value="/systems",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> getSystemList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getSystemList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get system list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/systems",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> testGetSystemList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-01-00-001.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<System> list = (List<System>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 시스템 목록
	 *
	 * API ID : REST-R01-CO-01-00-001
	 * </pre>
	 * @param httpSession the http session
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
			value="/servers",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Server>> getServerList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Server>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getServerList, locale, httpSession);
			return comMessage;
		}
	}



	/**
	 * <pre>
	 * Root 시스템 목록
	 *
	 * API ID : REST-R04-CO-01-00-001
	 * </pre>
	 * @param httpSession the http session
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
			value="/systems/root",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> getRootSystemCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getRootSystemCdList, locale, httpSession);
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * Child 시스템 목록
	 *
	 * API ID : REST-R05-CO-01-00-001
	 * </pre>
	 * @param httpSession the http session
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
			value="/systems/child",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> getChildSystemCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getChildSystemCdList, locale, httpSession);
			return comMessage;
		}
	}



	/**
	 * Test get server list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/servers",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Server>> testGetServerList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Server>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-01-00-002.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Server> list = (List<Server>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets business list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the business list
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(
			value="/businesses",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> getBusinessList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getBusinessList, locale, httpSession);
			return comMessage;
		}
	}





	/**
	 * Test get business list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/businesses",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> testGetBusinessList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-01-00-003.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Business> list = (List<Business>) Util.readObjectFromJson(
					new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets user list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the user list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/users",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<User>> getUserList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<User>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getUserList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get user list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/users",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<User>> testGetUserList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<User>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-01-00-004.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<User> list = (List<User>) Util.readObjectFromJson(
					new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets interface pattern list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface pattern list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/interface-patterns",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<InterfacePattern>> getInterfacePatternList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<InterfacePattern>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getInterfacePatternList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get interface pattern list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interface-patterns",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<InterfacePattern>> testGetInterfacePatternList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<InterfacePattern>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-01-00-006.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<InterfacePattern> list = (List<InterfacePattern>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets interface list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/interfaces",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Interface>> getInterfaceList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Interface>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getInterfaceList, locale, httpSession);
			return comMessage;
		}
	}


	/**
	 * Test get interface list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interfaces",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Interface>> testGetInterfaceList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Interface>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R02-CO-01-00-008.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Interface> list = (List<Interface>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets interface detail.
	 *
	 * @param httpSession the http session
	 * @param interfaceId the interface id
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface detail
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(
			value="/interfaces/{interfaceId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, Interface> getInterfaceDetail(
			HttpSession  httpSession,
			@PathVariable("interfaceId") String interfaceId,
			@RequestBody ComMessage<Map, Interface> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Interface intf = null;

			//-----------------------------------------------------------------------
			//필수 필드 체크하기
			//-----------------------------------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.getInterfaceDetail", "interfaceId", interfaceId, messageSource, locale);
			}

			//-----------------------------------------------------------------------
			//인터페이스 상세 조회
			//-----------------------------------------------------------------------
			{
				intf = commonService.getInterfaceDetail(interfaceId);
			}
			//-----------------------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------------------------------
			{
				if(intf == null){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(intf);
				}


				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * Test get interface detail.
	 *
	 * @param httpSession the http session
	 * @param interfaceId the interface id
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interfaces/{interfaceId}",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, Object> testGetInterfaceDetail(
			HttpSession  httpSession,
			@PathVariable("interfaceId") String interfaceId,
			@RequestBody ComMessage<Map, Object> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R02-CO-01-00-008.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Interface> list = (List<Interface>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}


			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets interface detail requiremnets.
	 *
	 * @param interfaceId the interface id
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface detail requiremnets
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(
			value="/interfaces/{interfaceId}/requirements",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Requirement>> getInterfaceDetailRequiremnets(
			@PathVariable("interfaceId") String interfaceId,
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Requirement>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";
			List<Requirement> list = null;

			//-----------------------------------------------------------------------
			//필수 필드 체크하기
			//-----------------------------------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.getInterfaceDetailRequiremnets", "interfaceId", interfaceId, messageSource, locale);
			}

			//-----------------------------------------------------------------------
			//요건 리스트를 얻어온다.
			//-----------------------------------------------------------------------
			{
				list = commonService.getInterfaceDetailRequiremnets(interfaceId);
			}
			//-----------------------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------------------------------
			{
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * Test get interface detail requiremnets.
	 *
	 * @param interfaceId the interface id
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interfaces/{interfaceId}/requirements",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Requirement>> testGetInterfaceDetailRequiremnets(
			@PathVariable("interfaceId") String interfaceId,
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Requirement>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R03-CO-01-00-008.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Requirement> list = (List<Requirement>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * The Sap list.
	 */
	List<String> sapList = null;

	/**
	 * 인터페이스중복체크 NONE SAP 버전
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interfaces/interface/exist",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody
	ComMessage<Map, String> existInterface(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, String> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			String intfaceId = null;

			Map params = comMessage.getRequestObject();
			//-----------------------------------------------------------------------
			//필수 필드 체크하기
			//-----------------------------------------------------------------------
			{
				//[변경]2016.09.06
				// - 중복체크시 '서비스명' 으로도 조회 가능해야 되므로 receiveService 는 필수항목체크에서 배제.
				// - 전사솔루션별로 중복체크하도록 하기 위해  channelId 는 필수 항목으로 추가
				//String [] fieldList = {"receiveService", "receiveSystemId"};
				String [] fieldList = {"receiveSystemId", "channelId"};
				FieldCheckUtil.checkRequired("CommonController.existInterface", fieldList, params, messageSource, locale);
			}

			//-----------------------------------------------------------------------
			//인터페이스 상세 조회
			//-----------------------------------------------------------------------
			{

				String receiveSystemId = (String)params.get("receiveSystemId");
				String receiveService = (String)params.get("receiveService");
				String receiveServiceDesc = (String)params.get("receiveServiceDesc");

				params.put("nodeType", "2");
				params.put("systemId",receiveSystemId);
				params.put("service",receiveService);
				params.put("serviceDesc",receiveServiceDesc);


			}

			intfaceId = commonService.existInterface(params);

			//-----------------------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------------------------------
			{
				if(Util.isEmpty(intfaceId)){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(intfaceId);
				}


				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
	/*@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interfaces/interface/exist",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody
	ComMessage<Map, String> existInterface(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, String> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			String intfaceId = null;

			Map params = comMessage.getRequestObject();
			//-----------------------------------------------------------------------
			//필수 필드 체크하기
			//-----------------------------------------------------------------------
			{
				String [] fieldList = {"dataPrDir", "receiveService", "receiveSystemId", "sendService", "sendSystemId"};
				FieldCheckUtil.checkRequired("CommonController.existInterface", fieldList, params, messageSource, locale);
			}

			//-----------------------------------------------------------------------
			//인터페이스 상세 조회
			//-----------------------------------------------------------------------
			{

				String sendSystemId = (String)params.get("sendSystemId");
				String receiveSystemId = (String)params.get("receiveSystemId");
				String sendService = (String)params.get("sendService");
				String receiveService = (String)params.get("receiveService");

				//------------------------------------------------
				//프로바이더를 결정한다. 단방향이면 송신이 프로바이더, 양방향이면 수신이 프로바이더.
				//------------------------------------------------
				String dataPrDir = (String) params.get("dataPrDir");
				if(Interface.CD_DATA_PR_DIR_TWOWAY.equals(dataPrDir)){
					params.put("nodeType", "2");
					params.put("systemId",receiveSystemId);
					params.put("service",receiveService);
				}else if(Interface.CD_DATA_PR_DIR_ONEWAY.equals(dataPrDir)){
					params.put("nodeType","0");
					params.put("systemId",sendSystemId);
					params.put("service",sendService);
				}else{
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.invalid.param.value", locale);
					Object[] errorMsgParams = {"DATA처리흐름(dataPrDir)", "[\"0\":단방향, \"1\":양방향]",dataPrDir };
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.invalid.param.value", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}

			}

			intfaceId = commonService.existInterface(params);

			//-----------------------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------------------------------
			{
				if(Util.isEmpty(intfaceId)){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(intfaceId);
				}


				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}*/


	/**
	 * 인터페이스중복체크 SAP CHECK 버전
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/interfaces/interface/exist-sap",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody
	ComMessage<Map, String> existInterfaceIncludeSap(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, String> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			String intfaceId = null;

			Map params = comMessage.getRequestObject();
			//-----------------------------------------------------------------------
			//필수 필드 체크하기
			//-----------------------------------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.existInterface", "dataPrDir", params, messageSource, locale);

				String dataPrDir = (String) params.get("dataPrDir");
				if(Interface.CD_DATA_PR_DIR_TWOWAY.equals(dataPrDir)){
					FieldCheckUtil.checkRequired("CommonController.existInterface", "receiveService", params, messageSource, locale);
					FieldCheckUtil.checkRequired("CommonController.existInterface", "receiveSystemId", params, messageSource, locale);
				}else if(Interface.CD_DATA_PR_DIR_ONEWAY.equals(dataPrDir)){
					FieldCheckUtil.checkRequired("CommonController.existInterface", "sendService", params, messageSource, locale);
					FieldCheckUtil.checkRequired("CommonController.existInterface", "sendSystemId", params, messageSource, locale);
				}else{
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.request.invalid.param.value", locale);
					Object[] errorMsgParams = {"DATA처리흐름(dataPrDir)", "[\"0\":단방향, \"1\":양방향]",dataPrDir };
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.invalid.param.value", errorMsgParams, locale);
					throw new ControllerException(errorCd, errorMsg);
				}
			}

			//-----------------------------------------------------------------------
			//인터페이스 상세 조회
			//-----------------------------------------------------------------------
			{
				intfaceId = commonService.existInterface(params);
			}
			//-----------------------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-----------------------------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-----------------------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-----------------------------------------------------------------------
			{
				if(Util.isEmpty(intfaceId)){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(intfaceId);
				}


				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * Gets system cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the system cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/systems",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> getSystemCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getSystemCdList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get system cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/cds/systems",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<System>> testGetSystemCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-001.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<System> list = (List<System>) Util.readObjectFromJson(
					new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets server cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the server cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/servers",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Server>> getServerCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Server>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getServerCdList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get server cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/cds/servers",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Server>> testGetServerCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Server>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-002.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Server> list = (List<Server>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * Gets business cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the business cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/businesses",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> getBusinessCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getBusinessCdList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get business cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/cds/businesses",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> testGetBusinessCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-003.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Business> list = (List<Business>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets root business cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the root business cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/businesses/root",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> getRootBusinessCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getRootBusinessCdList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Gets child business cd list.
	 *
	 * @param httpSession the http session
	 * @param parentBusinessId the parent business id
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the child business cd list
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/businesses/{parentBusinessId}/child",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Business>> getChildBusinessCdList(
			HttpSession  httpSession,
			@PathVariable("parentBusinessId") String parentBusinessId,
			@RequestBody ComMessage<Map, List<Business>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List<Business> list = null;

			//--------------------------------------------------
			//파라메터 {parentBusinessId} 체크
			//--------------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.getChildBusinessCdList", "parentBusinessId", parentBusinessId, messageSource, locale);
			}

			//--------------------------------------------------
			//자식 업무 리스트 조회
			//--------------------------------------------------
			{
				list = commonService.getChildBusinessCdList(parentBusinessId);
			}
			//--------------------------------------------------
			//응답시간세팅
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//--------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}

	/**
	 * Gets interface cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/interfaces",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Interface>> getInterfaceCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Interface>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getInterfaceCdList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get interface cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
		value="/cds/interfaces",
		params={"method=GET", "isTest=true"},
		method=RequestMethod.POST,
		headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Interface>> testGetInterfaceCdList(
		HttpSession  httpSession,
		@RequestBody ComMessage<Map, List<Interface>> comMessage,
		Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-004.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Interface> list = (List<Interface>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 공통코드 리스트 조회
	 *
	 * API ID : REST-R01-CO-02-00-005
	 * </pre>
	 * @param httpSession 세션
	 * @param level1 레벨1 코드
	 * @param level2 레벨2 코드
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
			value="/cds/common-codes/{level1}/{level2}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map,List<CommonCode>> getCommonCodeList(
			HttpSession  httpSession,
			@PathVariable String level1,
			@PathVariable String level2,
			@RequestBody ComMessage<Map,List<CommonCode>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List<CommonCode> list = null;

			//------------------------------------------------------
			//필수 필드 체크
			//------------------------------------------------------
			{
				FieldCheckUtil.checkRequired("CommonController.getCommonCodeList", level1, messageSource, locale);
				FieldCheckUtil.checkRequired("CommonController.getCommonCodeList", level2, messageSource, locale);
			}
			//------------------------------------------------------
			//공통코드 조회 데이터베이스 서비스를 호출하여 공통코드 리스트를 얻어온다.
			//------------------------------------------------------
			{
				list = commonService.getCommonCodeList(level1, level2);
			}
			//------------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//------------------------------------------------------
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

			//------------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//------------------------------------------------------
			{
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
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
	 * 공통코드 리스트 조회 유닛 테스트 함수
	 * 데이터 서비스 개발전에 프론트로부터의 요청을 검증하고
	 * 데이터 서비스 개발전에 프론트에서 개발진행 가능하도록
	 * JSON 포멧의 테스트 데이터 파일을 작성하여 응답 CM을
	 * 줄수 있도록 한다. 번거롭겠지만 반드시 작성해라! 안그럼 혼난다.
	 *
	 * API ID : REST-R01-CO-02-00-005
	 * </pre>
	 * @param httpSession 세션
	 * @param level1 레벨1 코드
	 * @param level2 레벨2 코드
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
     * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/cds/common-codes/{level1}/{level2}",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map,List<CommonCode>> testGetCommonCodeList(
			HttpSession  httpSession,
			@PathVariable String level1,
			@PathVariable String level2,
			@RequestBody ComMessage<Map,List<CommonCode>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			//테스트 데이터 파일패스를 얻어온다.
			//테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-005.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			//Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<CommonCode> list = (List<CommonCode>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 공통코드 등록
	 * API ID : REST-C01-CO-02-00-001
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(value = "/cds/common-codes", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommonCode, CommonCode> createCommonCode(
			HttpSession httpSession,
			@RequestBody ComMessage<CommonCode, CommonCode> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			CommonCode common = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				//FieldCheckUtil.check("restApiService.createRestService", server, messageSource, locale);
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("createApplication application param dump:\n", FieldCheckUtil.jsonDump(common)));

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
				/*String regId = comMessage.getUserId();
				String regDate = Util.getFormatedDate();*/
			}


			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = commonService.createCommon(common);
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
					comMessage.setResponseObject(common);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"ApplicationController.createApplication",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}

		}

	}

	/**
	 * <pre>
	 * 공통코드 변경
	 * API ID : REST-U01-CO-02-00-001
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
     * @throws Exception the exception
     */
	@RequestMapping(value = "/cds/common-codes", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommonCode, CommonCode> updateCommonCode(
			HttpSession httpSession,
			@RequestBody ComMessage<CommonCode, CommonCode> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			CommonCode common = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}

			//----------------------------------------------------------------------------
			// 입력값 JSON 덤프
			//----------------------------------------------------------------------------
			{
				logger.debug(Util.join("updateCommonCode common param dump:\n", FieldCheckUtil.jsonDump(common)));
			}


			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{
				//시스템 등록자/등록일자 체크및 세팅
//				String regId = comMessage.getUserId();
//				String regDate = Util.getFormatedDate();
//				String modId = regId;
//				String modDate = regDate;
//				common.setModId(modId);
//				common.setModDate(modDate);
			}
			try{
				//----------------------------------------------------------------------------
				//수정실행
				//----------------------------------------------------------------------------
				int res = 0;
				{
					res = commonService.updateCommon(common);
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
					comMessage.setResponseObject(common);

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
	 * 공통코드  삭제
	 * API ID : REST-U01-CO-02-00-001
	 * </pre>
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return com message
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/cds/common-codes", params = "method=DELETE", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommonCode, CommonCode> deleteCommonCode(
			HttpSession httpSession,
			@RequestBody ComMessage<CommonCode, CommonCode> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;

			CommonCode common = comMessage.getRequestObject();

			try{
				//----------------------------------------------------------------------------
				//등록실행
				//----------------------------------------------------------------------------
				int res = 0;
				{

					res = commonService.deleteCommon(common);
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
				String[] errorMsgParams = {"EnvironmentController.deleteEnvironment",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.delete.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{
			}
		}
	}


	/**
	 * Gets channel list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the channel list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/channels",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Channel>> getChannelList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Channel>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getChannelList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get channel list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/channels",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Channel>> testGetChannelList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Channel>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-006.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Channel> list = (List<Channel>) Util.readObjectFromJson(
					new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets service list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the service list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/services",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<String>> getServiceList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<String>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getServerList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get service list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/services",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<String>> testGetServiceList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<String>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-007.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<String> list = (List<String>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets interface tag list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the interface tag list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/interface-tags",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<InterfaceTag>> getInterfaceTagList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<InterfaceTag>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getInterfaceTagList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get interface tag list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/interface-tags",
			params={"method=GET", "isTest=true"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<InterfaceTag>> testGetInterfaceTagList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<InterfaceTag>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-007.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<InterfaceTag> list = (List<InterfaceTag>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}
	}

	/**
	 * Gets requirement cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the requirement cd list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/requirements",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Requirement>> getRequirementCdList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Requirement>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getRequirementCdList, locale, httpSession);
			return comMessage;
		}
	}


	/**
	 * Gets app list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the app list
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/cds/apps",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<App>> getAppList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<App>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			getListWithParams(comMessage, getAppList, locale, httpSession);
			return comMessage;
		}
	}

	/**
	 * Test get requirement cd list.
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(
		value="/cds/requirements",
		params={"method=GET", "isTest=true"},
		method=RequestMethod.POST,
		headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Requirement>> testGetRequirementCdList(
		HttpSession  httpSession,
		@RequestBody ComMessage<Map, List<Requirement>> comMessage,
		Locale locale,
		HttpServletRequest request
	) throws Exception{
		{
			// 테스트 데이터 파일패스를 얻어온다.
			// 테스트 데이터 파일의 기본 위치는 /WEB-INF/test-data/ 로 한다.
			String testFilePath = servletContext
					.getRealPath("/WEB-INF/test-data/co/REST-R01-CO-02-00-009.json");
			logger.debug(Util.join("testFilePath:", testFilePath));

			// Util.readObjectFromJson 메소드를 이용하여 테스트 데이터 파일을 최종 응답객체로 바인딩한다.
			List<Requirement> list = (List<Requirement>) Util.readObjectFromJson(new File(testFilePath), List.class, "UTF-8");

			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			}else{//성공 처리결과
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object errorMsgParam[] = {list.size()};
				errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
				comMessage.setResponseObject(list);
			}
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			return comMessage;
		}

	}


	/**
	 * <pre>
	 * ASIS인터페이스 리스트 조회 REST-R05-CO-01-00-008
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param channelId the channel id
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
	@RequestMapping(value = "/interfaces/asis/{channelId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, List<Map>> getAsisInterfaceList(
			HttpSession httpSession,
			@PathVariable("channelId")String channelId,
			@RequestBody ComMessage<?, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<Map> interfaceList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				interfaceList = commonService.getAsisInterfaceList(channelId);
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
				if (interfaceList == null || interfaceList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(interfaceList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * <pre>
	 * 장애유형 코드 조회 - REST-R01-CO-02-00-011
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
	@RequestMapping(value = "/problems", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<ProblemClass>> getProblemCdList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<ProblemClass>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";


			List<ProblemClass> problemCdList = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				problemCdList = commonService.getProblemCdList(comMessage.getRequestObject());
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
				if (problemCdList == null || problemCdList.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(problemCdList);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}


	/**
	 * REST-R02-CO-02-00-012
	 *
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the com message
	 * @throws Exception the exception
     */

	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/organization/systems/{organizationId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, TreeModel<System>> getSystemToOrganization(
			HttpSession  httpSession,
			@PathVariable("organizationId") String organizationId,
			@RequestBody ComMessage<Map, TreeModel<System>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception{
		{
			String errorCd = "";
			String errorMsg = "";
			TreeModel<System> treeModel = null;
			//-----------------------------------------------
			//필드 체크
			//-----------------------------------------------
			Map params = null;
			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}
				//params.put("organizationId", organizationId);

			}


			//-----------------------------------------------
			//조회
			//-----------------------------------------------
			//treeModel = commonService.getSystemToOrganization(params);
			treeModel = commonService.getSystemOfOrganization(params);

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
				if (treeModel == null) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(treeModel);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 시스템 기관 맵핑정보 조회
	 * API ID : REST-R04-CO-02-00-012
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
     * @since version 3.0(2020.12)
     */
	@RequestMapping(value = "/system-organization", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> getMessages(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, Map> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map map = null;
			//--------------------------------------------------
			//요건 조회 실행
			//--------------------------------------------------
			{
				Map params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				map = commonService.getOrganizationToSystem(params);
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
				if (map == null || map.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
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
	 * API ID : REST-R03-CO-02-00-012
	 * </pre>
	 * @param httpSession the http session
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
			value="/system/organization/{systemId}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Organization>> getOrganizationOfSystemList(
			HttpSession  httpSession,
			@PathVariable("systemId") String systemId,
			@RequestBody ComMessage<Map, List<Organization>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			//----------------------------------------------------------------------------
			//여긴 비지니스 코드만 작성해라.
			//----------------------------------------------------------------------------
			{
				String errorCd = "";
	            String errorMsg = "";
	            Map params = null;
	            List list = null;
	            //-------------------------------------------------
	            //파라메터 체크
	            //-------------------------------------------------
				{
					params = comMessage.getRequestObject();
					if(params == null) params = new HashMap();
					if (params != null){
						String paramString = FieldCheckUtil.paramString(params);
						logger.debug(Util.join("\nparamString:[", paramString, "]"));
					}

					params.put("systemId", systemId);
	            }
	            //-------------------------------------------------
	            //리스트를 조회한다.
	            //-------------------------------------------------

				list = commonService.getOrganizationOfSystemList(params);

	            //-------------------------------------------------
	            //서비스 처리 종료시간을 얻어 CM에 세팅한다.
	            //-------------------------------------------------
	            {
	                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
	            }
	            //-------------------------------------------------
	            //통신메시지에 처리결과 코드/메시지를 등록한다.
	            //-------------------------------------------------
	            {
					if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
						errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
					}else{//성공 처리결과
						errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
						Object errorMsgParam[] = {list.size()};
						errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
						comMessage.setResponseObject(list);
					}
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
	            }
			}
			return comMessage;
		}
	}


	/**
	 * 메뉴 리스트
	 *
	 * APP ID : REST-R01-CO-03-00-001
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the user list
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(
			value="/menus",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Menu>> getUserMenuList(
			HttpSession  httpSession,
			@RequestBody ComMessage<Map, List<Menu>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params != null) {
				String paramString = FieldCheckUtil.paramString(params);
				logger.debug(Util.join("\nparamString:[", paramString, "]"));
			} else {
				params = new HashMap();
			}

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//-------------------------------------------------
			//서비스를 조회한다.
			//-------------------------------------------------
			List list = commonService.getMenuList(user.getUserId());

			//-------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-------------------------------------------------
			{
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {Integer.toString(list.size())};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}



	/**
	 * 화면 ID(화면 CD)에 따른 화면 경로 가져오기
	 *
	 * APP ID : REST-R01-CO-03-00-002
	 * @param httpSession the http session
	 * @param comMessage the com message
	 * @param locale the locale
	 * @param request the request
	 * @return the user list
	 * @throws Exception the exception
	 * @throws Exception the exception
	 */
	@RequestMapping(
			value="/menus/path/{appCd}",
			params="method=GET",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Menu>> getMenuPathList(
			HttpSession  httpSession,
			@PathVariable("appCd") String appCd,
			@RequestBody ComMessage<Map, List<Menu>> comMessage,
			Locale locale,
			HttpServletRequest request
			) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			Map params = null;
			List list = null;
			//-------------------------------------------------
			//파라메터 체크
			//-------------------------------------------------
			{
				params = comMessage.getRequestObject();
				if(params == null) params = new HashMap();
				params.put("appCd", appCd);
				if (params != null){
					String paramString = FieldCheckUtil.paramString(params);
					logger.debug(Util.join("\nparamString:[", paramString, "]"));
				}


			}
			//-------------------------------------------------
			//리스트를 조회한다.
			//-------------------------------------------------

			list = commonService.getApplicationMenuPath(params);

			//-------------------------------------------------
			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//-------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//-------------------------------------------------
			//통신메시지에 처리결과 코드/메시지를 등록한다.
			//-------------------------------------------------
			{
				if(list == null || list.size() == 0){//결과가 없을 경우 비지니스 예외 처리
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object errorMsgParam[] = {list.size()};
					errorMsg= MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParam, locale);
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
	 * HealthCheck
	 * API ID : REST-R01-CO-01-00-000
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/healthcheck", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getHealthCheck(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage, Locale locale, HttpServletRequest request)
			throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{

			String errorCd = "";
			String errorMsg = "";

			try{
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

				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"WidgetController.getWidgetConfig",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);

			}finally{

			}
		}
	}


}
