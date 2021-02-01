/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
import java.util.*;

/**
 * Created by TA on 17. 11. 27
 * site     : GSSP
 * contents : 기동감지불가능포스로그[TOP0812] 적재 배치
 * params   : 등록일  : #{regDate}
 *            등록APP : #{regApp}
 *            체크범위(시간) : #{posCheckHour}
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TOP0812Job implements Job{

    Logger log = LoggerFactory.getLogger(TOP0812Job.class);


    public String posCheckHour = "0.5";
    public String isDeadposHistory = "TRUE";

    @SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());


        Map params = new HashMap();
        params.put("regDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
        params.put("regApp", "BatchJob-TOP0812");

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

            posCheckHour = getEnvironment("batch", "dead.pos.checkhour", posCheckHour, sqlSession);
            isDeadposHistory = getEnvironment("batch", "dead.pos.history.on", isDeadposHistory, sqlSession);

        	params.put("posCheckHour", Double.parseDouble(posCheckHour));

            sqlSession.delete("pep.per.mint.batch.mapper.op.TOP0812JobMapper.deleteTOP0812", params);
            sqlSession.insert("pep.per.mint.batch.mapper.op.TOP0812JobMapper.insertTOP0812", params);

            if(isDeadposHistory != null && isDeadposHistory.equalsIgnoreCase("true")) {
            	sqlSession.insert("pep.per.mint.batch.mapper.op.TOP0812JobMapper.insertTOP0817", params);
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

		String value = sqlSession.selectOne("pep.per.mint.batch.mapper.op.TOP0812JobMapper.getEnvironment", param);
		if(value != null) {
			result = value;
		}
		result = Util.isEmpty(result) ? defaultValue : result;
    	return result;
    }


}
