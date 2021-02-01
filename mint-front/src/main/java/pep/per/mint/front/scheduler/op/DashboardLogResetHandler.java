package pep.per.mint.front.scheduler.op;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.DashboardLogResetService;

@Component
public class DashboardLogResetHandler {

	private static final Logger logger = LoggerFactory.getLogger(DashboardLogResetHandler.class);

	@Autowired
	DashboardLogResetService dashboardLogResetService;

	@Autowired
	CommonService commonService;

	/**
	 * @deprecated 170930
	 */
	//로그정리는 배치잡인 LogPurgeJob 가 이미 하고 있으므로 DashboardLogResetHandler 는 스케줄 제외 시켜 놓는다.
	//@Scheduled(cron="00 10 01 * * *")
	public void resetLog(){



		//system.batch.basic.hostname
		//system.batch.use.basic.hostname
		//--------------------------------------------------------------
    	// set system.batch.use.basic.hostname var
    	//--------------------------------------------------------------
		boolean useBasicHostName = false;
		String basicHostName = "";
    	try{
    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("system", "batch.use.basic.hostname");
    		if(envm != null){
    			List<String> envs = envm.get("system.batch.use.basic.hostname");
    			if(!Util.isEmpty(envs)){
    				useBasicHostName = Boolean.parseBoolean((envs.get(0)));
    			}
    		}
    	}catch(Exception e){logger.error("ignorableException:", e);}
    	logger.debug(Util.join("The env [system.batch.use.basic.hostname]:[",useBasicHostName,"]"));
    	if(useBasicHostName) {
    		//--------------------------------------------------------------
        	// set system.batch.basic.hostname var
        	//--------------------------------------------------------------
	    	try{
	    		Map<String,List<String>> envm = commonService.getEnvironmentalValues("system", "batch.basic.hostname");
	    		if(envm != null){
	    			List<String> envs = envm.get("system.batch.basic.hostname");
	    			if(!Util.isEmpty(envs)){
	    				basicHostName = envs.get(0);
	    			}
	    		}
	    	}catch(Exception e){logger.error("ignorableException:", e);}
	    	logger.debug(Util.join("The env [system.batch.basic.hostname]:[",basicHostName,"]"));

	    	if(Util.isEmpty(basicHostName)){
	    		logger.debug(Util.join("There is no the env [system.batch.basic.hostname] so schedule job ", this.getClass().getName() , " is not launched."));
	    		return;
	    	}else{
	    		try {
		            String hostname = InetAddress.getLocalHost().getHostName();
		            logger.debug(Util.join("hostname:", hostname));
		            if (!hostname.equals(basicHostName)) {
		                logger.debug(Util.join("The hostname[", hostname,"] not match the env [system.batch.basic.hostname:", basicHostName ,"]",
		                		 " so schedule job ", this.getClass().getName() , " is not launched."));
		                return;
		            } else {
		                logger.debug(Util.join("The hostname[", hostname,"] matchs the env [system.batch.basic.hostname:", basicHostName ,"]"));
		            }

	    		} catch (Exception e) {
	                logger.error(Util.join("I can't know a hostname so schedule job ", this.getClass().getName() , " is not launched."), e);
	                return;
	    		}

	    	}
    	}



		try{
			logger.info("//--------------------------------------------");
			logger.info(Util.join("// ", DashboardLogResetHandler.class.getName(), ".resetLog strart"));
			logger.info("//--------------------------------------------");
			resetMIRunnerLog();
			resetMIAgentLog();
			resetIIPAgentLog();
			resetResourceLog();
			resetProcessLog();
			resetQmgrLog();
			resetChannelLog();
			resetQueueLog();
			logger.info("//--------------------------------------------");
			logger.info(Util.join("// ", DashboardLogResetHandler.class.getName(), ".resetLog ok"));
			logger.info("//--------------------------------------------");
		}catch(Throwable t){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetLog"), t);
		}finally{
			logger.info("//--------------------------------------------");
			logger.info(Util.join("// ", DashboardLogResetHandler.class.getName(), ".resetLog end"));
			logger.info("//--------------------------------------------");
		}

	}

	private void resetQueueLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getQueueLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String qmgrId = (String)map.get("qmgrId");
					String queueId = (String)map.get("queueId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getQueueLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("qmgrId", qmgrId);
					params.put("queueId", queueId);
					dashboardLogResetService.resetQueueLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetQueueLog"), e);
		}
	}

	private void resetChannelLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getChannelLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String qmgrId = (String)map.get("qmgrId");
					String channelId = (String)map.get("channelId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getChannelLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("qmgrId", qmgrId);
					params.put("channelId", channelId);
					dashboardLogResetService.resetChannelLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetChannelLog"), e);
		}
	}

	private void resetQmgrLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getQmgrLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String qmgrId = (String)map.get("qmgrId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getQmgrLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("qmgrId", qmgrId);
					dashboardLogResetService.resetQmgrLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetQmgrLog"), e);
		}

	}

	private void resetProcessLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getProcessLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String processId = (String)map.get("processId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getProcessLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("processId", processId);
					dashboardLogResetService.resetProcessLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetProcessLog"), e);
		}

	}

	private void resetResourceLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getResourceLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String resourceId = (String)map.get("resourceId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getResourceLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("resourceId", resourceId);
					dashboardLogResetService.resetResourceLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetResourceLog"), e);
		}
	}

	private void resetIIPAgentLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getIIPAgentLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String agentId = (String)map.get("agentId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getIIPAgentLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("agentId", agentId);
					dashboardLogResetService.resetIIPAgentLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetIIPAgentLog"), e);
		}
	}

	private void resetMIAgentLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getMIAgentLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String agentId = (String)map.get("agentId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getMIAgentLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("agentId", agentId);
					dashboardLogResetService.resetMIAgentLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetMIAgentLog"), e);
		}
	}

	private void resetMIRunnerLog() {
		// TODO Auto-generated method stub
		try{
			List<Map> res = dashboardLogResetService.getMIRunnerLogCnt();
			if(!Util.isEmpty(res)){
				for (Map map : res) {
					String agentId = (String)map.get("agentId");
					String runnerId = (String)map.get("runnerId");
					int cnt = ((BigDecimal)map.get("cnt")).setScale(0,RoundingMode.DOWN).intValueExact();
					logger.debug(Util.join("getMIRunnerLogCnt:", Util.toJSONString(map)));
					Map<String, String> params = new HashMap<String, String>();
					params.put("agentId", agentId);
					params.put("runnerId", runnerId);
					dashboardLogResetService.resetMIRunnerLog(params);
				}
			}
		}catch(Exception e){
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".resetMIRunnerLog"), e);
		}
	}

}
