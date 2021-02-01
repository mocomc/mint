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

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.batch.Variables;
import pep.per.mint.common.data.basic.MIAgent;
import pep.per.mint.common.data.basic.MIRunner;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.data.basic.miagent.MIAgentState;
import pep.per.mint.common.data.basic.miagent.MIRunnerState;
import pep.per.mint.common.util.Util;
import pep.per.mint.solution.mi.service.MIMonitorService;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class MIAgentStateCheckJob implements Job {

	Logger log = LoggerFactory.getLogger(MIAgentStateCheckJob.class);

	//@Autowired


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		MIMonitorService miMonitor = new MIMonitorService();

		JobKey jobKey = context.getJobDetail().getKey();
		log.info("//----------------------------------------------------");
        log.info(Util.join("// JOB[", jobKey, "] start to execute"));
        log.info("//----------------------------------------------------");
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

            List<Server> serverList = sqlSession.selectList("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.getServerList");


            String ip = Variables.miManagerIp;
            int port =Variables.miManagerPort;
            String userName = Variables.miManagerUserName;
            String password = Variables.miManagerPassword;

            miMonitor.setConnectionInfo(ip, port, userName, password);
            miMonitor.open();
            miMonitor.monitor();
            try{
                log.info(Util.join("// MI MONITOR GET SERVER LIST FROM MANAGER:", Util.toJSONString(miMonitor.getServerMap())));
                log.info(Util.join("// MI MONITOR GET AGENT LIST FROM MANAGER:",  Util.toJSONString(miMonitor.getAgentStateMap())));
                log.info(Util.join("// MI MONITOR GET RUNNER LIST FROM MANAGER:", Util.toJSONString(miMonitor.getRunnerStateMap())));
            }catch(Exception e){

            }

    		for (Server server : serverList) {
    			String serverCd = server.getServerCd();
    			Server monitorServer = miMonitor.getServer(serverCd);

    			if(monitorServer != null){

    				Map params= new HashMap();
        			params.put("serverId", server.getServerId());
        			List<MIAgent> agentList = sqlSession.selectList("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.getMIAgentList",params);

        			for (MIAgent agent : agentList) {
        				String agentName = agent.getAgentNm();
        				String agentKey = Util.join(serverCd,"@",agentName);
        				MIAgentState miAgentState = miMonitor.getAgentStatus(agentKey);
        				if(miAgentState != null){

        					miAgentState.setAgentId(agent.getAgentId());
        					int res = sqlSession.insert("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.insertAgentState", miAgentState);
        	    			res = sqlSession.update("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.updateAgentLastState", miAgentState);

        					List<MIRunner> runnerList = agent.getBrokerList();
        					for (MIRunner miRunner : runnerList) {
        						String runnerName = miRunner.getRunnerNm();
        						String runnerKey = Util.join(serverCd,"@",agentName,"@", runnerName);
        						MIRunnerState runnerState = miMonitor.getBrokerStatus(runnerKey);

        						if(runnerState != null){
        							runnerState.setAgentId(miRunner.getAgentId());
        							runnerState.setRunnerId(miRunner.getRunnerId());
                					res = sqlSession.insert("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.insertRunnerState", runnerState);
                	    			res = sqlSession.update("pep.per.mint.batch.mapper.op.MIAgentStateCheckJobMapper.updateRunnerLastState", runnerState);
        						}else{

        						}
							}

        				}else{

        				}
    				}

    			}else{

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
    		log.info("//----------------------------------------------------");
            log.info(Util.join("// JOB[", jobKey, "] finish to execute"));
            log.info("//----------------------------------------------------");
        }
	}

}