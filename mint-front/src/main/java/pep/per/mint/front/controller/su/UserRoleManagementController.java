package pep.per.mint.front.controller.su;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.auth.ApprovalAuthority;
import pep.per.mint.common.data.basic.auth.ApprovalAuthorityGroup;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.UserRoleManagementService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Solution TF on 15. 12. 2..
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes" })
public class UserRoleManagementController {

    @Autowired
    UserRoleManagementService userRoleManagementService;

    private static final Logger logger = LoggerFactory.getLogger(UserRoleManagementController.class);

    /**
     * The Message source.
     */
    // 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    // 서블리컨텍스트 관련정보 참조를 위한 객체
    // 예를 들어 servletContext를 이용하여 웹어플리케이션이
    // 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
    @Autowired
    private ServletContext servletContext;


    /**
     * <pre>
     * 사용자롤리스트조회(이름검색)
     * API ID : REST-R02-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, List<Map>> getUserUserRoleList(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";



            List<Map> list = null;
            //--------------------------------------------------
            //요건 조회 실행
            //--------------------------------------------------
            {
                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                list = userRoleManagementService.getUserRoleList(params);
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
     * 솔루션담당자조회
     * API ID : REST-R03-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/channel", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, List<Map>> getChannelUserList(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";



            List<Map> list = null;
            //--------------------------------------------------
            //필드 체크 및 조회 실행
            //--------------------------------------------------
            {
                Map params = comMessage.getRequestObject();
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", params, messageSource, locale);
                String channelId = (String)params.get("channelId");
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", "channelId", channelId, messageSource, locale);
                list = userRoleManagementService.getChannelUserList(params);
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
     * 결재자리스트조회
     * API ID : REST-R04-SU-01-01-000
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
    /*@RequestMapping(value = "/management/user-roles/approval", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, List<Map>> getApprovalUserList(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, List<Map>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";



            List<Map> list = null;
            //--------------------------------------------------
            //필드 체크 및 조회 실행
            //--------------------------------------------------
            {
                Map params = comMessage.getRequestObject();
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", params, messageSource, locale);
                String approvalRoleType = (String)params.get("approvalRoleType");
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", "approvalRoleType", approvalRoleType, messageSource, locale);

                list = userRoleManagementService.getApprovalUserList(params);
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
    }*/


    /**
     * <pre>
     * 결재자리스트조회
     * API ID : REST-R04-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/approval", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, List<ApprovalAuthorityGroup>> getApprovalUserList(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, List<ApprovalAuthorityGroup>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";



            List<ApprovalAuthorityGroup> list = null;
            //--------------------------------------------------
            //필드 체크 및 조회 실행
            //--------------------------------------------------
            {
                Map params = comMessage.getRequestObject();
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", params, messageSource, locale);
                String approvalRoleType = (String)params.get("approvalRoleType");
                FieldCheckUtil.checkRequired("UserRoleManagementController.getApprovalUserList", "approvalRoleType", approvalRoleType, messageSource, locale);

                list = userRoleManagementService.getApprovalAuthorityGroupList(params);
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
     * 솔루션담당자등록수정삭제
     * API ID : REST-U01-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/channel", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<List<Map>, ?> alterChannelUsers(
            HttpSession httpSession,
            @RequestBody ComMessage<List<Map>, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        //----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        //----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            try{


                //----------------------------------------------------------------------------
                //필드체크
                //----------------------------------------------------------------------------
                {

                }

                //----------------------------------------------------------------------------
                //등록실행
                //----------------------------------------------------------------------------
                int res = 0;
                {
                    List<Map> list = comMessage.getRequestObject();
                    //----------------------------------------------------------------------------
                    // 입력값 JSON 덤프
                    //----------------------------------------------------------------------------
                    {
                        logger.debug(Util.join("alterChannelUsers getRequestObject dump:\n", FieldCheckUtil.jsonDump(list)));

                    }
                    res = userRoleManagementService.alterChannelUsers(list);
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
                    //comMessage.setResponseObject(requirement);

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
     * 결재담당자등록삭제
     * API ID : REST-U02-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/approval", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<List<Map>, ?> alteApprovalUsers(
            HttpSession httpSession,
            @RequestBody ComMessage<List<Map>, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        //----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        //----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            try{


                //----------------------------------------------------------------------------
                //필드체크
                //----------------------------------------------------------------------------
                {

                }

                //----------------------------------------------------------------------------
                //등록실행
                //----------------------------------------------------------------------------
                int res = 0;
                {
                    List<Map> list = comMessage.getRequestObject();
                    //----------------------------------------------------------------------------
                    // 입력값 JSON 덤프
                    //----------------------------------------------------------------------------
                    {
                        logger.debug(Util.join("alteApprovalUsers getRequestObject dump:\n", FieldCheckUtil.jsonDump(list)));

                    }
                    res = userRoleManagementService.alterApprovalUsers(list);
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
                    //comMessage.setResponseObject(requirement);

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
     * 결재그룹 등록/수정/삭제
     * API ID : REST-U03-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/approval-group", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<List<ApprovalAuthorityGroup>, List<ApprovalAuthorityGroup>> alterApprovalAuthorityGroup(
            HttpSession httpSession, @RequestBody ComMessage<List<ApprovalAuthorityGroup>, List<ApprovalAuthorityGroup>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        //----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        //----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            try{


                //----------------------------------------------------------------------------
                //필드체크
                //----------------------------------------------------------------------------
                {

                }
                List<ApprovalAuthorityGroup>  approvalAuthorityGroups = null;
                //----------------------------------------------------------------------------
                //등록실행
                //----------------------------------------------------------------------------
                int res = 0;
                {
                    approvalAuthorityGroups = comMessage.getRequestObject();
                    //----------------------------------------------------------------------------
                    // 입력값 JSON 덤프
                    //----------------------------------------------------------------------------
                    {
                        logger.debug(Util.join("alterApprovalAuthorityGroup getRequestObject dump:\n", FieldCheckUtil.jsonDump(approvalAuthorityGroups)));

                    }
                    res = userRoleManagementService.alterApprovalAuthorityGroup(approvalAuthorityGroups);
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
                    comMessage.setResponseObject(approvalAuthorityGroups);

                    comMessage.setErrorCd(errorCd);
                    comMessage.setErrorMsg(errorMsg);
                    return comMessage;
                }
            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"UserRoleManagementController.alterApprovalAuthorityGroup",errorDetail};
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
     * 결재권한리스트 등록/수정/삭제
     * API ID : REST-U04-SU-01-01-000
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
    @RequestMapping(value = "/management/user-roles/approval-authorities", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<List<ApprovalAuthority>, List<ApprovalAuthority>> alterApprovalAuthorityList(
            HttpSession httpSession, @RequestBody ComMessage<List<ApprovalAuthority>, List<ApprovalAuthority>> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        //----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        //----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            try{


                //----------------------------------------------------------------------------
                //필드체크
                //----------------------------------------------------------------------------
                {

                }


                List<ApprovalAuthority> list = null;
                //----------------------------------------------------------------------------
                //등록실행
                //----------------------------------------------------------------------------
                int res = 0;
                {
                    list = comMessage.getRequestObject();
                    //----------------------------------------------------------------------------
                    // 입력값 JSON 덤프
                    //----------------------------------------------------------------------------
                    {
                        logger.debug(Util.join("alterApprovalAuthorityGroup getRequestObject dump:\n", FieldCheckUtil.jsonDump(list)));

                    }
                    res = userRoleManagementService.alterApprovalAuthorityList(list);
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
                    comMessage.setResponseObject(list);

                    comMessage.setErrorCd(errorCd);
                    comMessage.setErrorMsg(errorMsg);
                    return comMessage;
                }
            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"UserRoleManagementController.alterApprovalAuthorityList",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
                //comMessage.setErrorCd(errorCd);
                //comMessage.setErrorMsg(errorMsg);
                //return comMessage;
            }finally{

            }
        }
    }



}
