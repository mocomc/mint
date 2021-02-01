package pep.per.mint.batch.job.su;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 *
 * <pre>
 * pep.per.mint.batch.job.su
 * TSU0811Job.java
 * </pre>
 * @author whoana
 * @date Jun 10, 2019
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0811Job implements Job{

    Logger log = LoggerFactory.getLogger(TSU0811Job.class);


    @SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("-----------------------------------------------");
        log.info("JOB[" + jobKey + "] executing at " + new Date());
        log.info("-----------------------------------------------");
        Map params = new HashMap();
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -1);
        long yesterday = cal.getTimeInMillis();

        //String fromDate = Util.join(Util.getFormatedDate(yesterday, "yyyyMMdd"),"00");
        //String toDate   = Util.join(Util.getFormatedDate(yesterday, "yyyyMMdd"),"24");

        String fromDate = Util.join(Util.getFormatedDate(yesterday, "yyyyMM"),"0100");
        String toDate   = Util.join(Util.getFormatedDate(yesterday, "yyyyMM"),"3124");

        String regDate  = Util.getFormatedDate(yesterday, Util.DEFAULT_DATE_FORMAT_MI);

        params.put("fromDate", fromDate);
        params.put("toDate"  , toDate);
        params.put("regApp", "TSU0811Job");
        params.put("regDate"  , regDate);


        ZobSchedule schedule =  (ZobSchedule)context.getJobDetail().getJobDataMap().get("schedule");

        ZobResult zobResult = new ZobResult();
        zobResult.setGroupId(schedule.getZob().getGroupId());
        zobResult.setProcessId(this.getClass().getName());
        zobResult.setJobId(schedule.getZob().getJobId());
        zobResult.setScheduleId(schedule.getScheduleId());
        zobResult.setScheduleValue(schedule.getValue());
        zobResult.setStartDate(Util.getFormatedDate());
        try {
            zobResult.setCaller(Util.join(InetAddress.getLocalHost().getHostName(),":", Thread.currentThread().getName()));
        } catch (UnknownHostException e) {
            zobResult.setCaller(Thread.currentThread().getName());
        }

        SqlSession sqlSession = null;
        try {

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");

            sqlSession = sqlSessionFactory.openSession(false);
            log.info("-----------------------------------------------");
            log.info(Util.join("JOB[", jobKey.toString() ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 이전데이터 삭제 처리")));
            sqlSession.delete("pep.per.mint.batch.mapper.su.TSU080XJobMapper.deleteTSU0811", params);
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 이전데이터 삭제 처리 OK")));
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 집계")));
            sqlSession.insert("pep.per.mint.batch.mapper.su.TSU080XJobMapper.insertTSU0811", params);
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 집계 OK")));


            String subJobName1 = "I/F 별 레코드 처리 건수 집계(월)";
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB1[", subJobName1 ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            TSU0812Job subJob1 = new TSU0812Job();
            context.put("subJobName", subJobName1);
            context.put("isSubJob", new Boolean(true));
            subJob1.execute(context);
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB1[", subJobName1 ,"] 배치 처리 OK"));
            log.info("-----------------------------------------------");


            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());

            sqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
            sqlSession.commit();

            log.info("-----------------------------------------------");
            log.info(Util.join("JOB[", jobKey.toString() ,"]  배치 처리 OK"));
            log.info("-----------------------------------------------");
        } catch (Exception e) {

            if(sqlSession != null) {
                sqlSession.rollback();

                zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
                zobResult.setResultCd(ZobResult.RESULT_CD_ERROR);

                String errorDetail = "";
                PrintWriter pw = null;
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    pw = new PrintWriter(baos);
                    e.printStackTrace(pw);
                    pw.flush();
                    if (pw != null) errorDetail = baos.toString();
                } finally {
                    if (pw != null) pw.close();
                }

                zobResult.setErrorMsg(errorDetail);
                zobResult.setEndDate(Util.getFormatedDate());

                sqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
                sqlSession.commit();

            }
            throw new JobExecutionException("배치실행시예외발생:",e);
        } finally {
            if(sqlSession != null) { sqlSession.close(); }
        }
    }

}
