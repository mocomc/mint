/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, whoana.j), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.batch.job.op;

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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by whoana on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class LogPurgeJob implements Job{

    Logger log = LoggerFactory.getLogger(LogPurgeJob.class);
    public String toDay = "-10";


    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
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
        Map params = new HashMap();
        try {

            Calendar toCal = new GregorianCalendar();

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");

            sqlSession = sqlSessionFactory.openSession(false);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLog", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevMasterlog", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevMasterlogCustom", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevDetaillog", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevDetaillogCustom", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevDetaillogData", params);
            //sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTrLogDevDetaillogErr", params);

            toDay = getEnvironment("batch", "logpurge.todate", toDay, sqlSession);

            toCal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(toDay));
            long toDate = toCal.getTimeInMillis();
            params.put("toDate", Util.getFormatedDate(toDate, "yyyyMMdd"));


            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTBA0005", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0199", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0801", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0802", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0805", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0806", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0807", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0808", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0809", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0810", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTIM0213", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            try {
            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0200", params);
            } catch(Exception e) {
            	e.printStackTrace();
            }

            DatabaseMetaData dbmd =  sqlSession.getConnection().getMetaData();
            String SCHEMA = dbmd.getUserName();
            {
	            // 테이블 체크  TOP0901 , TOP0903 EMAIL  발송 테이블 추가
	            ResultSet restEmail =  dbmd.getTables(null, SCHEMA, "TOP0901", null);
	            if(restEmail.next()){
	            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0903", params);
	                sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0901", params);
	            }
            }

            // 테이블 체크  TOP0904 , TOP0905 SMS 발송 테이블 추가
            {
	            ResultSet rest =  dbmd.getTables(null, SCHEMA, "TOP0904", null);
	            if(rest.next()){
	            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0905", params);
	                sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTOP0904", params);
	            }
            }


            // 테이블 체크  TIM0601 , TIM0602 농협 테스트 CALL 테이블 추가
            {
	            ResultSet rest =  dbmd.getTables(null, SCHEMA, "TIM0601", null);
	            if(rest.next()){
	            	sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTIM0602", params);
	                sqlSession.delete("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.deleteTIM0601", params);
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

    public String getEnvironment(String pkg, String attributeId, String defaultValue, SqlSession sqlSession) throws Exception {
    	String result = null;
    	Map param = new HashMap();
    	param.put("package", pkg);
    	param.put("attributeId", attributeId);

		String value = sqlSession.selectOne("pep.per.mint.batch.mapper.op.LogPurgeJobMapper.getEnvironment", param);
		if(value != null) {
			result = value;
		}
		result = Util.isEmpty(result) ? defaultValue : result;
    	return result;
    }


}
