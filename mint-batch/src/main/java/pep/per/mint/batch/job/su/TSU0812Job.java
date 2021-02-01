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
 * TSU0812Job.java
 * 인터페이스별 레코드처리건수 월집계 배치
 * </pre>
 * @author whoana
 * @date Jun 10, 2019
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0812Job implements Job{

    Logger log = LoggerFactory.getLogger(TSU0812Job.class);


    @SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
            throws JobExecutionException {

    	boolean isSubJob = ((Boolean)context.get("isSubJob")).booleanValue();
        JobKey jobKey = context.getJobDetail().getKey();
        String jobName = "";
    	if(isSubJob){
    		String subJobName = (String)context.get("subJobName");
    		jobName = Util.join(jobKey.getName(),"-", subJobName) ;
    	}


        log.info("-----------------------------------------------");
        log.info(Util.join("JOB[" , jobName , "] executing at " + new Date()));
        log.info("-----------------------------------------------");
        Map params = new HashMap();
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -1);
        long yesterday = cal.getTimeInMillis();
        String fromDay = Util.join(Util.getFormatedDate(yesterday, "yyyyMM"),"01");
        String toDay   = Util.join(Util.getFormatedDate(yesterday, "yyyyMM"),"31");
        String regDate  = Util.getFormatedDate(yesterday, Util.DEFAULT_DATE_FORMAT_MI);

        params.put("fromDay", fromDay);
        params.put("toDay"  , toDay);
        params.put("regApp", "TSU0812Job");
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


            log.info("-----------------------------------------------");
            log.info(Util.join("JOB[", jobName ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            sqlSession = sqlSessionFactory.openSession(false);
            log.info(Util.join(Util.join("JOB[", jobName ,"] : 이전데이터 삭제 처리")));
            sqlSession.delete("pep.per.mint.batch.mapper.su.TSU080XJobMapper.deleteTSU0812", params);
            log.info(Util.join(Util.join("JOB[", jobName ,"] : 이전데이터 삭제 처리 OK")));
            log.info(Util.join(Util.join("JOB[", jobName ,"] : 집계")));
            sqlSession.insert("pep.per.mint.batch.mapper.su.TSU080XJobMapper.insertTSU0812", params);
            log.info(Util.join(Util.join("JOB[", jobName ,"] : 집계 OK")));

            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());

            sqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
            sqlSession.commit();

            log.info("-----------------------------------------------");
            log.info(Util.join("JOB[", jobName ,"]  배치 처리 OK"));
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
