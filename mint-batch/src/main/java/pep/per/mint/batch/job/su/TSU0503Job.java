package pep.per.mint.batch.job.su;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.batch.job.JobImpl;
import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TSU0503Job implements Job {

	Logger log = LoggerFactory.getLogger(TSU0503Job.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());

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

            Map params = new HashMap();

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            long stDate = cal.getTimeInMillis();
            params.put("stDate", Util.getFormatedDate(stDate, "yyyyMMdd"));




            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");

            sqlSession = sqlSessionFactory.openSession(false);
            sqlSession.delete("pep.per.mint.batch.mapper.su.TSU0503JobMapper.deleteTSU0503", params);
            sqlSession.insert("pep.per.mint.batch.mapper.su.TSU0503JobMapper.insertTSU0503", params);
            sqlSession.delete("pep.per.mint.batch.mapper.su.TSU0503JobMapper.deleteTSU0504", params);
            sqlSession.insert("pep.per.mint.batch.mapper.su.TSU0503JobMapper.insertTSU0504", params);
            
            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());

            sqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
            sqlSession.commit();

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
