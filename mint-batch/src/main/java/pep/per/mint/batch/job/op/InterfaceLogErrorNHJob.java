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
import pep.per.mint.inhouse.sms.SendSMSService;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by whoana on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class InterfaceLogErrorNHJob implements Job{

    Logger log = LoggerFactory.getLogger(InterfaceLogErrorNHJob.class);


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
        try {
        	SendSMSService sendSMSService = (SendSMSService)context.getJobDetail().getJobDataMap().get("sendSMSService");
        	sendSMSService.init();
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");

            sqlSession = sqlSessionFactory.openSession(false);

            Map params = new HashMap();
            Calendar fromCal = new GregorianCalendar();
            fromCal.add(Calendar.MINUTE, -5);
            long fromHour = fromCal.getTimeInMillis();
            params.put("fromDate", Util.getFormatedDate(fromHour, "yyyyMMddHHmm") +"00000000");

            Calendar toCal = new GregorianCalendar();
            long toHour = toCal.getTimeInMillis();
            params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMddHHmm") +"99999999");


            List<Map> listUser = sqlSession.selectList("pep.per.mint.batch.mapper.op.InterfaceLogErrorNHJobMapper.selectSendUser", params);

            StringBuffer sb = new StringBuffer();

            for(Map userMap : listUser){
            	if(userMap != null  && userMap.get("hpNum") != null && ((String)userMap.get("hpNum")).trim().length()>0){
           			sb.append(((String)userMap.get("hpNum")).trim().replace("-", "")).append(",");
            	}
            }

            List<Map> list = sqlSession.selectList("pep.per.mint.batch.mapper.op.InterfaceLogErrorNHJobMapper.selectErrorTrLog", params);
            for (Map map : list) {
            	double  totalCnt = ((Integer)map.get("totalCnt")).doubleValue();
            	double errorCnt = ((Integer)map.get("errCnt")).doubleValue();
            		log.debug("조건 "  + totalCnt  +"   " +errorCnt  +   "  " + ((errorCnt / totalCnt) * 100 )   );
            	if( ((errorCnt / totalCnt) * 100 ) > 10){

            		String hostId 	= (String)map.get("hostId");
            		String groupId 	= (String)map.get("groupId");
            		String intfId 	= (String)map.get("intfId");

            		Map buildParams = new HashMap();
            		buildParams.put("Status", "9");
            		buildParams.put("ObjectType", "0");
            		buildParams.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
            		buildParams.put("QmgrName", "");
            		buildParams.put("ObjectName", intfId);
            		buildParams.put("Info1", Util.join((int)errorCnt, "/", (int)totalCnt));
            		buildParams.put("HostName", hostId);


            		String contents = sendSMSService.buildContents(buildParams);

//    				StringBuffer sb = new StringBuffer();
//    				SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd HH:mm:ss");
//    				String newRegTime =dt1.format(new Date());
//    				sb.append("[").append(newRegTime).append("] ");
//    				sb.append(intfId).append(" ");
//    				sb.append("[").append(Util.join((int)(errorCnt) , "/", (int)totalCnt)).append("] alert");
//
//    				contents = sb.toString();
            		map.put("smsId", UUID.randomUUID().toString());
            		map.put("type", "0");  // 포털 설정 확인
            		map.put("subject", "InterfaceErrAlarm");
            		map.put("contents", contents);
                	map.put("sender", sendSMSService.getSendSMSAddress());
                	map.put("retry", "1");
                	map.put("format", "0");
                	map.put("sndReqDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

                	map.put("regDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
                	map.put("regId", "iip");
                	map.put("delYn", "N");
                	if(sb.toString().length()<=0){
                		map.put("recipients", sendSMSService.getSendSMSAddress());
                		map.put("sndYn", "Y");
                		map.put("resCd", "7");
                		map.put("resMsg", "recipients is null");
                	}else{
                		map.put("recipients", sb.toString());
                		map.put("sndYn", "N");
                	}

	                sqlSession.insert("pep.per.mint.batch.mapper.op.InterfaceLogErrorNHJobMapper.insertInterfaceErrLogSms", map);
            	}else{
            		log.debug("조건 미적합");
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
        }
    }


}
