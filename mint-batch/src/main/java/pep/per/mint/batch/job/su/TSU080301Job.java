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
public class TSU080301Job implements Job{

	Logger log = LoggerFactory.getLogger(TSU080301Job.class);

	@Override
	//@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {


        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());


		Calendar fromCal = new GregorianCalendar();
		fromCal.add(Calendar.HOUR_OF_DAY, -1);
        long fromDate = fromCal.getTimeInMillis();

        Calendar toCal = new GregorianCalendar();
		toCal.add(Calendar.HOUR_OF_DAY, +1);
        long toDate = toCal.getTimeInMillis();

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fromDate", Util.getFormatedDate(fromDate, "yyyyMMddHH"));
        params.put("toDate", Util.getFormatedDate(toDate, "yyyyMMddHH"));


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

            SqlSessionFactory tagSqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("tagSqlSessionFactory");

            sqlSession = tagSqlSessionFactory.openSession(ExecutorType.BATCH, false);



            List<Map<String,String>> interfaceList = sqlSession.selectList("pep.per.mint.batch.mapper.su.TSU080301JobMapper.selectInterfaces", params);
            for (Map<String, String> interfaze : interfaceList) {
				summarizeTSU0803(sqlSession, sqlSession, interfaze, params);
			}

            insertZeroData(sqlSession, params);

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
            log.error("",e);
            throw new JobExecutionException("배치실행시예외발생:",e);
        } finally {
            if(sqlSession != null) { sqlSession.close(); }
        }
    }

	private void insertZeroData(SqlSession tagSqlSession, Map<String,Object> params) {
		// TODO Auto-generated method stub

		Map<String, String> summaryParams = new HashMap<String, String>();

		String regApp = null;
		try{
			regApp = InetAddress.getLocalHost().getHostName();
		}catch(Exception e){
			regApp = Util.join("bthread-",Thread.currentThread().getName());
			//log.error("",e);
		}


		summaryParams.put("regApp", regApp);
		summaryParams.put("regDate",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

		String fromDate = (String)params.get("fromDate");
		String toDate = (String)params.get("toDate");
		String trDateYmd = fromDate.substring(0, 8);
		int from = Integer.parseInt(fromDate.substring(8));
		int to   = Integer.parseInt(toDate.substring(8));

		for(int i = from ; i <= to ; i ++){
			String hh = Util.leftPad(new Integer(i).toString(), 2, "0");
			String trDate = Util.join(trDateYmd, hh);
			summaryParams.put("trDate", trDate);
			log.debug("insertZeroDataTSU0803.params:"+Util.toJSONString(summaryParams));
			int res = tagSqlSession.update("pep.per.mint.batch.mapper.su.TSU0803JobMapper.insertZeroDataTSU0803", summaryParams);
		}
	}

	private void summarizeTSU0803(SqlSession srcSqlSession, SqlSession tagSqlSession, Map interfaze, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		String interfaceId = (String)interfaze.get("interfaceId");
		String integrationId = (String)interfaze.get("integrationId");
		params.put("integrationId", integrationId);
		List<Map> summaries = selectSummary(srcSqlSession, params);

		for(int i = 0 ; i < summaries.size() ; i ++){

			Map summary = summaries.get(i);

			summary.put("interfaceId",interfaceId);
			summary.put("regDate",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

			String regApp = null;
			try{
				regApp = InetAddress.getLocalHost().getHostName();
			}catch(Exception e){
				regApp = Util.join("bthread-",Thread.currentThread().getName());
				//log.error("",e);
			}
			summary.put("regApp", regApp);

			double dataSize = 0;
			summary.put("dataSize", dataSize);

			int upsertRes = upsertSummary(tagSqlSession,summary);
		}

	}

	private int upsertSummary(SqlSession tagSqlSession, Map params) throws Exception {
		// TODO Auto-generated method stub
		int upsertRes = 0;
		log.debug("params:"+Util.toJSONString(params));
		if(existInterfaceSummary(tagSqlSession, params)){
			upsertRes = tagSqlSession.update("pep.per.mint.batch.mapper.su.TSU0803JobMapper.updateTSU0803", params);
		}else{
			upsertRes = tagSqlSession.insert("pep.per.mint.batch.mapper.su.TSU0803JobMapper.insertTSU0803", params);

		}
		return upsertRes;
	}

	private boolean existInterfaceSummary(SqlSession tagSqlSession, Map params) {
		// TODO Auto-generated method stub
		int exist = (Integer)tagSqlSession.selectOne("pep.per.mint.batch.mapper.su.TSU0803JobMapper.selectOneTSU0803",params);
		return exist >= 1 ? true : false;
	}


	private List<Map> selectSummary(SqlSession srcSqlSession, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> res = srcSqlSession.selectList("pep.per.mint.batch.mapper.su.TSU080301JobMapper.selectInterfaceTransactionSummary", params);
		return res;
	}



}
