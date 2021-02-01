package pep.per.mint.batch.job.op;

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

import org.apache.ibatis.session.ExecutorType;
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
import pep.per.mint.common.util.Util;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TOP0811AndTOP0813Job implements Job{

	Logger log = LoggerFactory.getLogger(TOP0811AndTOP0813Job.class);

    int commitCount = 100;
    boolean useCommitCount = false;

	@Override
	//@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {


        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());

		Calendar fromCal = new GregorianCalendar();
		fromCal.add(Calendar.MINUTE, -10);
        long fromDate = fromCal.getTimeInMillis();

        Calendar toCal = new GregorianCalendar();
		toCal.add(Calendar.MINUTE, +10);
        long toDate = toCal.getTimeInMillis();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fromDate", Util.join(Util.getFormatedDate(fromDate, "yyyyMMddHHmm"),"00"));
        params.put("toDate", Util.join(Util.getFormatedDate(toDate, "yyyyMMddHHmm"),"00"));

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

        SqlSession srcSqlSession = null;
        SqlSession tagSqlSession = null;
        try {
        	//for GSSP datasoruce
            SqlSessionFactory srcSqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("gsspSqlSessionFactory");

            //for IIP datasource
            SqlSessionFactory tagSqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("tagSqlSessionFactory");

            srcSqlSession = srcSqlSessionFactory.openSession(true);

            tagSqlSession = tagSqlSessionFactory.openSession(ExecutorType.BATCH, false);

            List<Map<String,Object>> transactions = srcSqlSession.selectList("pep.per.mint.batch.mapper.gssp.GSSPJobMapper.selectPosTransaction", params);
            int processCount = 0;
            for (Map<String, Object> transaction : transactions) {
				summarizeTOP0813(srcSqlSession, tagSqlSession, transaction, params);
                processCount ++;
                if(useCommitCount){
                    if(processCount % commitCount == 0) {
                        srcSqlSession.commit();
                        tagSqlSession.commit();
                    }
                }else{
                    srcSqlSession.commit();
                    tagSqlSession.commit();
                }
			}

//            Map<String,String> top0811Params = new HashMap<String,String>();
//            String checkDate = Util.getFormatedDate("yyyyMMdd");
//            top0811Params.put("checkDate", checkDate);
//            top0811Params.put("regDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//            top0811Params.put("regApp", getClass().getName());
//            summarizeTOP0811(tagSqlSession, top0811Params);
//            tagSqlSession.commit();

            Calendar purgeCal = new GregorianCalendar();
            //데이터 량이 많이 발생하여 삭제 주기를 당일 -4시간 전 데이터 삭제로 변경한다.
            //purgeCal.add(Calendar.DAY_OF_MONTH, -7);
            purgeCal.add(Calendar.HOUR_OF_DAY, -4);
            String purgeDate = Util.getFormatedDate(purgeCal.getTimeInMillis(), "yyyyMMddHHmm");
            purgeTOP0813(tagSqlSession, purgeDate);
            tagSqlSession.commit();

            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());
            tagSqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
            tagSqlSession.commit();

        } catch (Exception e) {



            if(tagSqlSession != null) {
            	tagSqlSession.rollback();

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

                tagSqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);
                tagSqlSession.commit();

            }
            log.error("",e);
            throw new JobExecutionException("배치실행시예외발생:",e);
        } finally {
        	if(srcSqlSession != null) { srcSqlSession.close(); }
            if(tagSqlSession != null) { tagSqlSession.close(); }
        }
    }


	private void summarizeTOP0813(SqlSession srcSqlSession, SqlSession tagSqlSession, Map transaction, Map params) throws Exception {
		// TODO Auto-generated method stub

        String getDate = (String)transaction.get("getDate");//'yyyymmddhh24mi'
        String storeCd = (String)transaction.get("storeCd");
        String posNo = (String)transaction.get("posNo");
        Integer cnt = new Integer(transaction.get("cnt").toString());


        params.put("getDate", getDate);
        params.put("storeCd", storeCd);
        params.put("posNo", posNo);
        params.put("cnt", cnt);
        params.put("regDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
        params.put("regApp", getClass().getName());

        int res = 0;
        res = tagSqlSession.delete("pep.per.mint.batch.mapper.op.TOP0813JobMapper.deleteTOP0813", params);
		res = tagSqlSession.insert("pep.per.mint.batch.mapper.op.TOP0813JobMapper.insertTOP0813", params);

	}

    private void purgeTOP0813(SqlSession sqlSession, String purgeDate) {
        try{
            int res = sqlSession.insert("pep.per.mint.batch.mapper.op.TOP0813JobMapper.purgeTOP0813", purgeDate);
        }catch(Exception e){
            log.error("fail to purge table TOP0813", e);
        }
    }

//    private void summarizeTOP0811(SqlSession sqlSession, Map params) throws Exception {
//        int res = 0;
//        res = sqlSession.delete("pep.per.mint.batch.mapper.op.TOP0811JobMapper.deleteTOP0811");
//		res = sqlSession.insert("pep.per.mint.batch.mapper.op.TOP0811JobMapper.insertTOP0811", params);
//
//    }




}
