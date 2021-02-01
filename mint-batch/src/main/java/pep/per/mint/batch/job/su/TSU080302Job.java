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
public class TSU080302Job implements Job{

	Logger log = LoggerFactory.getLogger(TSU080302Job.class);
	public String fromHour = "-1";

	@Override
	//@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());

		Calendar fromCal = new GregorianCalendar();

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
        	//for IFM datasoruce
            SqlSessionFactory srcSqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("ifmSqlSessionFactory");

            //for IIP datasource
            SqlSessionFactory tagSqlSessionFactory = (SqlSessionFactory)context.getJobDetail().getJobDataMap().get("tagSqlSessionFactory");

            srcSqlSession = srcSqlSessionFactory.openSession(true);

            tagSqlSession = tagSqlSessionFactory.openSession(ExecutorType.BATCH, false);

    		fromHour = getEnvironment("batch", "statistics.from.checkhour", fromHour, tagSqlSession);

    		fromCal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(fromHour));
            long fromDate = fromCal.getTimeInMillis();

            Calendar toCal = new GregorianCalendar();
    		toCal.add(Calendar.HOUR_OF_DAY, +1);
            long toDate = toCal.getTimeInMillis();

            Map<String,Object> params = new HashMap<String,Object>();
            params.put("fromDate", Util.getFormatedDate(fromDate, "yyyyMMddHH"));
            params.put("toDate", Util.getFormatedDate(toDate, "yyyyMMddHH"));

            List<Map<String,String>> interfaceList = srcSqlSession.selectList("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectInterfacesType2", params);
            for (Map<String, String> interfaze : interfaceList) {
				summarizeTSU0803(srcSqlSession, tagSqlSession, interfaze, params);
			}

            insertZeroData(tagSqlSession, params);

            zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
            zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
            zobResult.setEndDate(Util.getFormatedDate());
            tagSqlSession.insert("pep.per.mint.batch.mapper.co.JobMapper.insertJobResult", zobResult);

            srcSqlSession.commit();
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
		//Integer masterLogId = new Integer(interfaze.get("masterLogId").toString());
		String integrationId = (String)interfaze.get("integrationId");
		String groupId = (String)interfaze.get("groupId");
		params.put("integrationId", integrationId);
		List<Map> summaries = selectSummary(srcSqlSession, params);

		interfaze.put("delimeter", "_");
		Map<String, String> interfaceInfo = tagSqlSession.selectOne("pep.per.mint.batch.mapper.su.TSU0803JobMapper.selectInterfaceInfo", interfaze);

		if(interfaceInfo == null){
			log.info(Util.join("summarizeTSU0803:integraionId[",integrationId,"] 에 해당하는 인터페이스를 찾을 수 없어서 인터페이스별집계(시간별)을 수횅하지 않습니다." ));
			return;
		}

		String interfacePrDr = interfaceInfo.get("interfacePrDr");
		String interfaceId = interfaceInfo.get("interfaceId");
		for(int i = 0 ; i < summaries.size() ; i ++){

			Map summary = summaries.get(i);

			summary.put("interfaceId",interfaceId);
			summary.put("regDate",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

			String regApp = null;
			try{
				regApp = InetAddress.getLocalHost().getHostName();
			}catch(Exception e){
				regApp = Util.join("bthread-",Thread.currentThread().getName());
				//log.debug("",e);
			}
			summary.put("regApp", regApp);


			Map<String, Double> dataSizes = selectDataSize(srcSqlSession, params);

			double dataSize = 0;

			if(!Util.isEmpty(dataSizes)) {
				double sndDataSize = Double.parseDouble(dataSizes.get("sndDataSize").toString());
				double rcvDataSize = Double.parseDouble(dataSizes.get("rcvDataSize").toString());
				if(Util.isEmpty(interfacePrDr) || "0".equals(interfacePrDr)){//정보가 존재하지 않을 경우 단방향 처리한다.(기존 로직)
					dataSize = rcvDataSize;
				}else{
					dataSize = sndDataSize + rcvDataSize;
				}
			}

			summary.put("dataSize", dataSize);

			int upsertRes = upsertSummary(tagSqlSession,summary);
			//if(upsertRes != 1){
				//throw new BatchException(Util.join("exception:","summarizeTSU0803 fail:upsertRes(",upsertRes,")"));
			//}
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

	private Map<String, Double> selectDataSize(SqlSession srcSqlSession, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Double> sizes = srcSqlSession.selectOne("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectDataSizeType2", params);
		return sizes;
	}

	private List<Map> selectSummary(SqlSession srcSqlSession, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> res = srcSqlSession.selectList("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectInterfaceTransactionSummaryType2", params);
		return res;
	}

	public String getEnvironment(String pkg, String attributeId, String defaultValue, SqlSession sqlSession) throws Exception {
    	String result = null;
    	Map param = new HashMap();
    	param.put("package", pkg);
    	param.put("attributeId", attributeId);

		String value = sqlSession.selectOne("pep.per.mint.batch.mapper.su.TSU0803JobMapper.getEnvironment", param);
		if(value != null) {
			result = value;
		}
		result = Util.isEmpty(result) ? defaultValue : result;
    	return result;
    }



}
