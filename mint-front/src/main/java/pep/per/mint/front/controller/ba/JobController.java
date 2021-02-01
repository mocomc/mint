package pep.per.mint.front.controller.ba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pep.per.mint.batch.BatchManager;
import pep.per.mint.batch.job.an.TAN0201Service;
import pep.per.mint.batch.job.co.JobService;
import pep.per.mint.batch.job.op.*;
import pep.per.mint.batch.job.su.*;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Solution TF on 15. 11. 10..
 */
@Controller
@RequestMapping("/ba")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JobController {


    Logger logger = LoggerFactory.getLogger(JobController.class);

    /**
     * The Tsu 0505 service.
     */
    @Autowired
    TSU0505Service tsu0505Service;

    /**
     * The Tsu 0509 service.
     */
    @Autowired
    TSU0509Service tsu0509Service;

    /**
     * The Tsu 0601 service.
     */
    @Autowired
    TSU0601Service tsu0601Service;

    /**
     * The Tsu 0602 service.
     */
    @Autowired
    TSU0602Service tsu0602Service;

    @Autowired
    HourStatisticsService hourStatisticsService;


    @Autowired
    DayStatisticsService dayStatisticsService;

    @Autowired
    TAN0201Service tan0201Service;


    @Autowired
    TSU0803Service tsu0803Service;

    @Autowired
    TSU0804Service tsu0804Service;


    /**
     * The Batch manager.
     */
    @Autowired
    BatchManager batchManager;

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
     * The Job service.
     */
    @Autowired
    JobService jobService;

    /**
     * <pre>
     * 배치JOB-리로드
     * API ID : REST-S02-BA-00-00-000
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
    @RequestMapping(value = "/job/reload", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> reloadJob(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                batchManager.reloadJob();

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0602",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }


    @Autowired
    TOP0201Service top0201Service;

    /**
     * <pre>
     * 배치JOB-일별사용자집계
     * API ID : REST-S01-BA-01-02-000
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
    @RequestMapping(value = "/job/run/TOP0201", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTOP0201(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.TOP0201Job");

                String loginDate = (String)params.get("loginDate");
                if(Util.isEmpty(loginDate)) {

                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    long yesterday = cal.getTimeInMillis();
                    params.put("loginDate", Util.getFormatedDate(yesterday, "yyyyMMdd"));
                }

                top0201Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTAN0201",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }



    /**
     * <pre>
     * 배치JOB-거래로그 사이즈 집계
     * API ID : REST-S01-BA-01-05-000
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
    @RequestMapping(value = "/job/run/TAN0201", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTAN0201(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.an.TAN0201Job");

                String year = (String)params.get("year");
                String month = (String)params.get("month");
                if(year == null || month == null) {

                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.MONTH, -1);
                    long beforeYmd = cal.getTimeInMillis();
                    params.put("year", Util.getFormatedDate(beforeYmd, "yyyy"));
                    params.put("month", Util.getFormatedDate(beforeYmd, "MM"));
                    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    params.put("days", days);
                }

                tan0201Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTAN0201",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }


    @Autowired
    LogPurgeService logPurgeService;

    /**
     * <pre>
     * 배치JOB-거래로그 정리(10일이전) 집계
     * API ID : REST-S01-BA-01-10-000
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
    @RequestMapping(value = "/job/run/LogPurge", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runLogPurge(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.LogPurgeJob");

                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(toDate)) {

                    Calendar toCal = new GregorianCalendar();
                    toCal.add(Calendar.DAY_OF_MONTH, -10);
                    long toHour = toCal.getTimeInMillis();
                    params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMdd"));


                }

                logPurgeService.call(params);

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runLogPurge",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }





    @Autowired
    DetailLogErrorService detailLogErrorService;

    /**
     * <pre>
     * 배치JOB-지연처리로그정리 집계
     * API ID : REST-S01-BA-01-06-000
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
    @RequestMapping(value = "/job/run/DetailLogError", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runDetailLogError(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.DetailLogErrorJob");

                String fromDate = (String)params.get("fromDate");
                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(fromDate) || Util.isEmpty(toDate)) {

                    Calendar fromCal = new GregorianCalendar();
                    fromCal.add(Calendar.HOUR_OF_DAY, -24);
                    long fromHour = fromCal.getTimeInMillis();
                    params.put("fromDate", Util.getFormatedDate(fromHour, "yyyyMMddHH"));

                    Calendar toCal = new GregorianCalendar();
                    toCal.add(Calendar.HOUR_OF_DAY, -2);
                    long toHour = toCal.getTimeInMillis();
                    params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMddHH"));


                }

                detailLogErrorService.call(params);

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runDetailLogError",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }



    /**
     * <pre>
     * 배치JOB-I/F별 시간 단위 건수 집계
     * API ID : REST-S01-BA-01-07-000
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
    @RequestMapping(value = "/job/run/HourStatistics", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runHourStatistics(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.HourStatisticsJob");

                String fromDate = (String)params.get("fromDate");
                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(fromDate) || Util.isEmpty(toDate)) {

                    Calendar fromCal = new GregorianCalendar();
                    fromCal.add(Calendar.HOUR_OF_DAY, -3);
                    long fromHour = fromCal.getTimeInMillis();
                    params.put("fromDate", Util.getFormatedDate(fromHour, "yyyyMMddHH"));

                    Calendar toCal = new GregorianCalendar();
                    toCal.add(Calendar.HOUR_OF_DAY, +1);
                    long toHour = toCal.getTimeInMillis();
                    params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMddHH"));



                }

                hourStatisticsService.call(params);

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runHourStatistics",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }




    /**
     * <pre>
     * 배치JOB-I/F별 일 단위 건수 집계
     * API ID : REST-S01-BA-01-08-000
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
    @RequestMapping(value = "/job/run/DayStatistics", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runDayStatistics(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.DayStatisticsJob");

                String fromDate = (String)params.get("fromDate");
                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(fromDate) || Util.isEmpty(toDate)) {

                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DATE, -1);
                    long yesterday = cal.getTimeInMillis();
                    params.put("fromDate", Util.getFormatedDate(yesterday, "yyyyMMdd"));
                    params.put("toDate", Util.getFormatedDate("yyyyMMdd"));

                }

                dayStatisticsService.call(params);

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runDayStatistics",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }

    @Autowired
    MonthStatisticsService monthStatisticsService;

    /**
     * <pre>
     * 배치JOB-I/F별 월 단위 건수 집계
     * API ID : REST-S01-BA-01-09-000
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
    @RequestMapping(value = "/job/run/MonthStatistics", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runMonthStatistics(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.op.MonthStatisticsJob");

                String fromDate = (String)params.get("fromDate");
                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(fromDate) || Util.isEmpty(toDate)) {

                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.MONTH, -1);
                    long yesterday = cal.getTimeInMillis();
                    params.put("fromDate", Util.getFormatedDate(yesterday, "yyyyMM"));
                    params.put("toDate", Util.getFormatedDate("yyyyMM"));

                }

                monthStatisticsService.call(params);

                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runMonthStatistics",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }


    @Autowired
    TSU0501Service tsu0501Service;

    /**
     * <pre>
     * 배치JOB-미등록 I/F 수집
     * API ID : REST-S01-BA-01-11-001
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
    @RequestMapping(value = "/job/run/TSU0501", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0501(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0501Job");

                String fromDate = (String)params.get("fromDate");
                String toDate = (String)params.get("toDate");
                if(Util.isEmpty(fromDate) || Util.isEmpty(toDate)) {

                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    long yesterday = cal.getTimeInMillis();
                    params.put("fromDate", Util.getFormatedDate(yesterday, "yyyyMMdd"));
                    params.put("toDate", Util.getFormatedDate("yyyyMMdd"));

                }

                tsu0501Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0501",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }

    @Autowired
    TSU0502Service tsu0502Service;

    /**
     * <pre>
     * 배치JOB-I/F별 처리 건수 전일대비 증가율 수집
     * API ID : REST-S01-BA-01-12-001
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
    @RequestMapping(value = "/job/run/TSU0502", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0502(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0502Job");

                String yesterday = (String)params.get("yesterday");
                String today = (String)params.get("today");
                if(Util.isEmpty(yesterday) || Util.isEmpty(today)) {

                    Calendar fromCal = new GregorianCalendar();
                    fromCal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("yesterday", Util.getFormatedDate(fromCal.getTimeInMillis(), "yyyyMMddHH"));
                    params.put("today", Util.getFormatedDate("yyyyMMddHH"));

                }

                tsu0502Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0502",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }



    @Autowired
    TSU0503Service tsu0503Service;

    /**
     * <pre>
     * 배치JOB-I/F stage별 건수 집계
     * API ID : REST-S01-BA-01-13-001
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
    @RequestMapping(value = "/job/run/TSU0503", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<Map, ?> runTSU0503(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0503Job");

                String stDate = (String)params.get("stDate");
                if(Util.isEmpty(stDate)) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("stDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMMdd"));

                }

                tsu0503Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0503",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }





    /**
     * <pre>
     * 배치JOB-일별개발진척률집계
     * API ID : REST-S01-BA-01-01-001
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
    @RequestMapping(value = "/job/run/TSU0505", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0505(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0505Job");

                String stDate = (String)params.get("stDate");
                if(Util.isEmpty(stDate)) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("stDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMMdd"));

                }

                tsu0505Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0505",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }

    /**
     * <pre>
     * 배치JOB-월별인터페이스변화량집계
     * API ID : REST-S01-BA-01-01-001
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
    @RequestMapping(value = "/job/run/TSU0509", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0509(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0509Job");

                String stDate = (String)params.get("stDate");
                if(Util.isEmpty(stDate)) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("stDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMMdd"));

                }

                tsu0509Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0509",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }



    /**
     * <pre>
     * 배치JOB-CPU MEM 상태 집계 호출
     * API ID : REST-S01-BA-01-03-000
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
    @RequestMapping(value = "/job/run/TSU0601", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0601(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0601Job");

                String stDate = (String)params.get("stDate");
                if(Util.isEmpty(stDate)) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("stDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMMdd"));

                }

                tsu0601Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0601",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }

    /**
     * <pre>
     * 배치JOB-디스크 상태 집계 호출
     * API ID : REST-S01-BA-01-04-000
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
    @RequestMapping(value = "/job/run/TSU0602", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0602(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) params = new HashMap();
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0602Job");
                String stDate = (String)params.get("stDate");
                if(Util.isEmpty(stDate)) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    params.put("stDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMMdd"));

                }
                tsu0602Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0602",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }


    /**
     * <pre>
     * 배치실행결과조회
     * API ID : REST-R01-BA-00-00-000
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
    @RequestMapping(value = "/job/results", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody ComMessage<Map<String,Object>, List<ZobResult>> getJobResultList(
            HttpSession httpSession,
            @RequestBody ComMessage<Map<String,Object>, List<ZobResult>> comMessage,
            Locale locale, HttpServletRequest request) throws Exception, ControllerException {

        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            Map params = comMessage.getRequestObject();
            if(params == null) params = new HashMap();


            List<ZobResult> list = null;
            //--------------------------------------------------
            //요건 리스트 조회 실행
            //--------------------------------------------------
            {
                list = jobService.getJobResultList(params);
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
     * 배치JOB-I/F별 처리 건수 시간별 집계
     * API ID : REST-S01-BA-01-14-001
     * </pre>
     *
     * @param httpSession 세션
     * @param comMessage 요청응답통신객체
     * @param locale 다국어지원을 위한 로케일
     * @param request the request
     * @return ComMessage 통신객체
     * @throws Exception the exception
     * @throws Exception the exception
     * @author TA
     * @since version 1.0(2017.05)
     */
    @RequestMapping(value = "/job/run/TSU0803", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0803(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{


                Map params = comMessage.getRequestObject();
                if(params == null) throw new Throwable("service params is null!");
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0803Job");

                tsu0803Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0803",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }


    /**
     * <pre>
     * 배치JOB-I/F별 처리 건수 일/월별 집계 및 기관별 일/월 처리건수 집계
     * API ID : REST-S01-BA-01-16-001
     * </pre>
     *
     * @param httpSession 세션
     * @param comMessage 요청응답통신객체
     * @param locale 다국어지원을 위한 로케일
     * @param request the request
     * @return ComMessage 통신객체
     * @throws Exception the exception
     * @throws Exception the exception
     * @author TA
     * @since version 1.0(2017.05)
     */
    @RequestMapping(value = "/job/run/TSU0804", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
    public @ResponseBody
    ComMessage<Map, ?> runTSU0804(
            HttpSession httpSession,
            @RequestBody ComMessage<Map, ?> comMessage, Locale locale, HttpServletRequest request)
            throws Exception, ControllerException {
        // ----------------------------------------------------------------------------
        // 여긴 비지니스 코드만 작성해라.
        // ----------------------------------------------------------------------------
        {
            String errorCd = "";
            String errorMsg = "";

            //--------------------------------------------------
            //배치 실행
            //--------------------------------------------------
            try{

                Map params = comMessage.getRequestObject();
                if(params == null) throw new Throwable("service params is null!");
                params.put("caller",comMessage.getUserId());
                params.put("implClass","pep.per.mint.batch.job.su.TSU0804Job");

                tsu0804Service.call(params);
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
                comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
                comMessage.setErrorCd(errorCd);
                comMessage.setErrorMsg(errorMsg);

            }catch(Throwable e){

                errorCd = MessageSourceUtil.getErrorCd(messageSource,  "error.cd.run.job.fail", locale);
                String errorDetail = e.getMessage();
                String[] errorMsgParams = {"JobController.runTSU0804",errorDetail};
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.run.job.fail", errorMsgParams, locale);
                throw new ControllerException(errorCd, errorMsg, e);
            }

            return comMessage;
        }
    }



}
