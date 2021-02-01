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

import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TSU0509Job implements Job {

	Logger log = LoggerFactory.getLogger(TSU0509Job.class);

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

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");
            sqlSession = sqlSessionFactory.openSession(false);


            Map params= new HashMap();
            
            //---------------------------------------------------------------------------------
            // (1) 배치가 최초 수행인지 체크한다.
            //---------------------------------------------------------------------------------
            int checkCnt = sqlSession.<Integer>selectOne("pep.per.mint.batch.mapper.su.TSU0509JobMapper.getMatchingCount", null);
            
            //---------------------------------------------------------------------------------
            // (2) 배치가 최초 수행이면, 인터페이스 등록일자 기준 가장 작은 일자를 조회하여 집계 정보를 생성한다.
            //---------------------------------------------------------------------------------
            if( checkCnt == 0 ) {
            	log.debug("TSU0509 initialize...");
            	String beginDate = sqlSession.<String>selectOne("pep.per.mint.batch.mapper.su.TSU0509JobMapper.getInterfaceBeginRegDate");
            	params.put("beginDate", beginDate);
            	params.put("fromDate", beginDate);
            	
            	sqlSession.insert("pep.per.mint.batch.mapper.su.TSU0509JobMapper.insertInitTSU0509", params);
            } else {
            	log.debug("TSU0509 insert/update...");
                //---------------------------------------------------------------------------------
                // (3) 배치가 최초 수행이 아니면, 현재 월 기준 이전 달 부터 집계 정보를 확인한다.
                //---------------------------------------------------------------------------------
                Calendar cal = new GregorianCalendar();
                cal.add(Calendar.MONTH, -1);
                String beginDate = sqlSession.<String>selectOne("pep.per.mint.batch.mapper.su.TSU0509JobMapper.getBeginRegDate");
                params.put("beginDate", beginDate);
                params.put("fromDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMM") );
            	
            	List<Map> monthlyInterfaceCountList = sqlSession.selectList("pep.per.mint.batch.mapper.su.TSU0509JobMapper.getMonthlyInterfaceCountList",params);
            	
            	for(Map info : monthlyInterfaceCountList ){
            		
            		params.put("stMonth", info.get("ST_MONTH"));
            		params.put("channelId", info.get("CHANNEL_ID"));

                    //---------------------------------------------------------------------------------
                    // (4) 추출된 정보를 기준으로 집계 정보가 존재하면 update, 존재하지 않으면 insert 를 수행한다.
                    //---------------------------------------------------------------------------------            
            		checkCnt = sqlSession.<Integer>selectOne("pep.per.mint.batch.mapper.su.TSU0509JobMapper.getMatchingCount", params);
            		if( checkCnt == 0 ) {
            			log.debug("TSU0509 insert");
            			sqlSession.insert("pep.per.mint.batch.mapper.su.TSU0509JobMapper.insertTSU0509", info);
            		} else {
            			log.debug("TSU0509 update");
            			sqlSession.update("pep.per.mint.batch.mapper.su.TSU0509JobMapper.updateTSU0509", info);
            		}
            	}
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
