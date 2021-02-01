package pep.per.mint.batch.job.su;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
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
import pep.per.mint.common.data.basic.batch.InterfaceCountSummary;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TSU0502Job implements Job {

	Logger log = LoggerFactory.getLogger(TSU0502Job.class);

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

        String curHour = new SimpleDateFormat("HH").format(new Date());
        SqlSession sqlSession = null;
        try {

            Map params = new HashMap();
            Calendar fromCal = new GregorianCalendar();
            fromCal.add(Calendar.DAY_OF_MONTH, -1);
            long yesterday = fromCal.getTimeInMillis();
            params.put("yesterday", Util.getFormatedDate(yesterday, "yyyyMMddHH"));

            params.put("today", Util.getFormatedDate("yyyyMMddHH"));


            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");

            sqlSession = sqlSessionFactory.openSession(false);
            List<Object> objList = sqlSession.selectList("pep.per.mint.batch.mapper.su.TSU0502JobMapper.selectSummary", params);
    		for (Object obj : objList) {
    			
    			InterfaceCountSummary ics = (InterfaceCountSummary)obj; 
    			
    			int hFactor = 0, dFactor = 0;
    			float hDelta = ics.getTodayCurHour() - ics.getYesterdayCurHour();
    			float dDelta = ics.getTodayTotal() - ics.getYesterdayTotal();
    			
    			if (hDelta > 0 && ics.getYesterdayCurHour() > 0) {
    				hFactor = (int)(hDelta / ics.getYesterdayCurHour() * 100);
    			}
    			if (dDelta > 0 && ics.getYesterdayTotal() > 0) {
    				dFactor = (int)(dDelta / ics.getYesterdayTotal() * 100);
    			}

    			String subject = ics.getIntegrationId();
    			String content = ics.getRequirementName();
    			if (hFactor >= 30 && dFactor < 30) {
    				subject = subject + " 동시간대 처리량 " + hFactor + "% 증가";
    				content = content + " " + curHour + "시 기준 (어제 " + ics.getYesterdayCurHour() + "건 / 오늘 " + ics.getTodayCurHour() + "건)";
    			} else if (dFactor >= 30 && hFactor < 30) {
    				subject = subject + " 전체 처리량 " + hFactor + "% 증가";
    				content = content + " " + curHour + "시 까지 (어제 " + ics.getYesterdayTotal() + "건 / 오늘 " + ics.getTodayTotal() + "건)";
    			} else if (hFactor >= 30 && dFactor >= 30) {
    				subject = subject + " 처리량 증가";
    				content = content + " " + curHour + "시 동시간대 (어제 " 
    						+ ics.getYesterdayCurHour() + "건 / 오늘 " + ics.getTodayCurHour() + "건), "
    						+ curHour + "까지 전체 (어제 " + ics.getYesterdayTotal() + "건 / 오늘 " + ics.getTodayTotal() + "건)";
    			} else {
    				continue;
    			}
    			ics.setSubject(subject);
    			ics.setContent(content);
    			
    			sqlSession.insert("pep.per.mint.batch.mapper.su.TSU0502JobMapper.insertTSU0502", obj);
    		}
            
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
