/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.batch.job.op;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Date;
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

import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.util.Util;
import pep.per.mint.inhouse.mail.SendMailService;

/**
 * <pre>
 * pep.per.mint.batch.job.su
 * HelloJob.java
 * </pre>
 * @author whoana
 * @date Jan 8, 2019
 */
public class SimpleSendMailJob implements Job {

	Logger log = LoggerFactory.getLogger(SimpleSendMailJob.class);

	static boolean isRunning = false;



	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		if(isRunning) {
			System.out.println(Util.join("The job ", getClass().getSimpleName(), "[", Thread.currentThread().getName() ,"] is running already."));
			log.info(Util.join("The job ", getClass().getSimpleName(), "[", Thread.currentThread().getName() ,"] is running already."));
			return;
		}else {
			isRunning = true;
		}

		JobKey jobKey = context.getJobDetail().getKey();
        log.info(Util.join("The job ", getClass().getSimpleName(), "[", Thread.currentThread().getName() ,"] is starting."));

        System.out.println(getClass().getSimpleName() + ": " + jobKey + " executing at " + new Date());

        ZobSchedule schedule =  (ZobSchedule)context.getJobDetail().getJobDataMap().get("schedule");
        ZobResult zobResult = new ZobResult();
        zobResult.setGroupId(schedule.getZob().getGroupId());
        zobResult.setProcessId(this.getClass().getName());
        zobResult.setJobId(schedule.getZob().getJobId());
        zobResult.setScheduleId(schedule.getScheduleId());
        zobResult.setScheduleValue(schedule.getValue());
        zobResult.setStartDate(Util.getFormatedDate());
        zobResult.setCaller(Thread.currentThread().getName());

        SqlSession sqlSession = null;
        try {

            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");
            sqlSession = sqlSessionFactory.openSession(false);

            Map params= new HashMap();
            String fromDate = Util.join(Util.getFormatedDate("yyyyMMdd"),"000000000");
            int fetchCount = 100;
            params.put("fromDate", fromDate);
            params.put("fetchCount", fetchCount);

            List<Email> emails = sqlSession.selectList("pep.per.mint.batch.mapper.op.SimpleSendMailMapper.getNotYetSendEmails", params);

    		if(!Util.isEmpty(emails)){
    			for (Email email : emails) {
    				try{
    					email.setSndReqDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

    					Map mailParams = new HashMap();

    					mailParams.put("subject", email.getSubject());
    					mailParams.put("contents", email.getContents());
    					mailParams.put("recipients", email.getRecipients());

    					SendMailService sendMailService = (SendMailService)context.getJobDetail().getJobDataMap().get("sendMailService");
    		            sendMailService.sendMail(mailParams);

						email.setResCd("0");
						email.setResMsg("success");
						email.setSndYn("Y");
						log.debug(Util.join(getClass().getSimpleName(),".sendEmail OK:emailId:[",email.getEmailId(),"]"));
    				}catch(Exception ex){
    					email.setResCd("9");
						email.setResMsg(ex.getMessage());
						email.setSndYn("N");
						//change-when : 170927
						//change-what :
						//에러나도 다음메일 계속 보내도록 예외를 던지지 않도록 수정함.
    					//throw ex;
						log.error(Util.join(getClass().getSimpleName(),".sendEmail error:emailId:[",email.getEmailId(),"]"), ex);
    				}finally{
    					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
    					email.setModDate(modDate);
						email.setModId("iip");
						email.setResDate(modDate);
						sqlSession.update("pep.per.mint.batch.mapper.op.SimpleSendMailMapper.updateEmail", email);
    					log.debug("done updateEmailSendResult");
    					sqlSession.commit();
    				}
				}
    		}

            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());

            sqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);

            sqlSession.commit();

        } catch (Exception e) {

        	e.printStackTrace();

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

            isRunning = false;
        }
	}

}