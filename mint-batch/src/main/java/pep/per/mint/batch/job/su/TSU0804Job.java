/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

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
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by Solution TF on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0804Job implements Job{

    Logger log = LoggerFactory.getLogger(TSU0804Job.class);


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
        params.put("regApp", "TSU0804Job");
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
            sqlSession.delete("pep.per.mint.batch.mapper.su.TSU080XJobMapper.deleteTSU0804", params);
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 이전데이터 삭제 처리 OK")));
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 집계")));
            sqlSession.insert("pep.per.mint.batch.mapper.su.TSU080XJobMapper.insertTSU0804", params);
            log.info(Util.join(Util.join("JOB[", jobKey.toString() ,"] : 집계 OK")));


            String subJobName1 = "I/F 별 처리 건수 집계(월)";
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB1[", subJobName1 ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            TSU0805Job subJob1 = new TSU0805Job();
            context.put("subJobName", subJobName1);
            context.put("isSubJob", new Boolean(true));
            subJob1.execute(context);
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB1[", subJobName1 ,"] 배치 처리 OK"));
            log.info("-----------------------------------------------");

            String subJobName2 = "기관별 처리 건수 집계(일)";
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB2[", subJobName2 ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            TSU0807Job subJob2 = new TSU0807Job();
            context.put("subJobName", subJobName2);
            context.put("isSubJob", new Boolean(true));
            subJob2.execute(context);
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB2[", subJobName2 ,"] 배치 처리 OK"));
            log.info("-----------------------------------------------");

            String subJobName3 = "기관별 처리 건수 집계(월)";
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB3[", subJobName3 ,"] 배치 처리"));
            log.info("-----------------------------------------------");
            TSU0808Job subJob3 = new TSU0808Job();
            context.put("subJobName", subJobName3);
            context.put("isSubJob", new Boolean(true));
            subJob3.execute(context);
            log.info("-----------------------------------------------");
            log.info(Util.join("SUB JOB3[", subJobName3 ,"] 배치 처리 OK"));
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
