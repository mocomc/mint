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
import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.sms.SendSMSService;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class SendSmsJob implements Job{

	Logger log = LoggerFactory.getLogger(SendSmsJob.class);

	int retryCnt = 1;
	int fetchCnt = 1000;
	String fromDate = "";

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobKey jobKey = context.getJobDetail().getKey();
		log.info("SimpleJob says: " + jobKey + "executing at " + new Date());

		ZobSchedule schedule = (ZobSchedule)context.getJobDetail().getJobDataMap().get("schedule");

		ZobResult zobResult = new ZobResult();
		zobResult.setGroupId(schedule.getZob().getGroupId());
		zobResult.setProcessId(this.getClass().getName());
		zobResult.setJobId(schedule.getZob().getJobId());
		zobResult.setScheduleId(schedule.getScheduleId());
		zobResult.setScheduleValue(schedule.getValue());
		zobResult.setStartDate(Util.getFormatedDate());
		try {
			zobResult.setCaller(Util.join(InetAddress.getLocalHost().getHostName(), ":", Thread.currentThread().getName()));
		} catch (UnknownHostException e) {
			zobResult.setCaller(Thread.currentThread().getName());
		}

		SqlSession sqlSession = null;
		try {

			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("sqlSessionFactory");
			sqlSession = sqlSessionFactory.openSession(false);

			CommonService commonService = (CommonService)context.getJobDetail().getJobDataMap().get("commonService");
			retryCnt = commonService.getEnvironmentalIntValue("support", "schedule.sms.retry.cnt", retryCnt);
			fetchCnt = commonService.getEnvironmentalIntValue("support", "schedule.sms.fetch.tracking.sms.cnt", fetchCnt);

			Map listParams = new HashMap();
			fromDate = Util.join(Util.getFormatedDate("yyyyMMdd"),"000000000");

			listParams.put("retry", retryCnt);
			listParams.put("fetchCount", fetchCnt);
			listParams.put("fromDate", fromDate);

			List<Sms> smses = sqlSession.selectList("pep.per.mint.batch.mapper.op.SendSmsJobMapper.getNotYetSendSmses", listParams);

			if(!Util.isEmpty(smses)){
				SendSMSService sendSMSService = (SendSMSService)context.getJobDetail().getJobDataMap().get("sendSMSService");
				sendSMSService.init();

				for (Sms sms : smses) {
					try {
						sms.setSndReqDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

						String recipientsString = sms.getRecipients();
						String [] recipientsArray = recipientsString.split(",");
						List<String> recipients = Arrays.asList(recipientsArray);
						Map params = new HashMap();
						params.put("subject", sms.getSubject());
						params.put("contents", sms.getContents());
						params.put("sender", sms.getSender());
						params.put("sqlSession", sqlSession);

						for(String recv : recipients){
							if(!(recv == null || recv.length()<=0)){
								params.put("recipients", recv);
								sendSMSService.sendSMS(params);
							}
						}
						sms.setResCd("0");
						sms.setResMsg("success");
						sms.setSndYn("Y");
						log.debug(Util.join(SendSmsJob.class.getName(),".sendSms OK:smsId:[",sms.getSmsId(),"]"));
					} catch(Exception ex) {
						sms.setResCd("9");
    					sms.setRetry(sms.getRetry()+1);
    					sms.setResMsg(ex.getMessage());
    					sms.setSndYn("N");
						log.error(Util.join(SendSmsJob.class.getName(),".sendSms error:smsId:[",sms.getSmsId(),"]"), ex);
					} finally{
    					String modDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
    					sms.setModDate(modDate);
    					sms.setModId("iip");
    					sms.setResDate(modDate);
    					sqlSession.update("pep.per.mint.batch.mapper.op.SendSmsJobMapper.updateSmsSendResult", sms);
    					log.debug("done updateSMSSendResult");
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
