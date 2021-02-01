package pep.per.mint.front.scheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

public abstract class ScheduleHandler {

	public static final long DEFAULT_FIXED_DELAY_AS_SEC = 60 * 1000;

	Logger logger = LoggerFactory.getLogger(ScheduleHandler.class);

	protected long fixedDelayAsOneSecond = 1000 * 60;

	long elasedTimefixedDelayAsOneSecond = 0L;

	public abstract void doFixedDelaySchedlueAsOneSecond() throws Exception;

	public abstract void setEnvironment() throws Exception;

	@Autowired
	CommonService commonService;

	@Scheduled(fixedDelay = 60 * 1000, initialDelay = 60 * 1000)
	public void launchFixedDelayScheduleAsOneSecond(){

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
			setEnvironment();
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".setEnvironmentFixedDelaySchedlueAsOneSecond"), t);
		}

		long elapsed = System.currentTimeMillis() - elasedTimefixedDelayAsOneSecond;
		if(elapsed < fixedDelayAsOneSecond) {
			//logger.debug("//--------------------------------------------");
			//logger.debug(Util.join("// ", ScheduleHandler.class.getName(), ".launchFixedDelayScheduleAsOneSecond does not reach to the scheduled time."));
			//logger.debug("//--------------------------------------------");
		} else {
			try{
				logger.info("//--------------------------------------------");
				logger.info(Util.join("// ", ScheduleHandler.class.getName(), ".launchFixedDelayScheduleAsOneSecond strart"));
				logger.info("//--------------------------------------------");
				doFixedDelaySchedlueAsOneSecond();
				logger.info("//--------------------------------------------");
				logger.info(Util.join("// ", ScheduleHandler.class.getName(), ".launchFixedDelayScheduleAsOneSecond ok"));
				logger.info("//--------------------------------------------");
			}catch(Throwable t) {
				logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".launchFixedDelayScheduleAsOneSecond"), t);
			}finally{
				elasedTimefixedDelayAsOneSecond = System.currentTimeMillis();
				logger.info("//--------------------------------------------");
				logger.info(Util.join("// ", ScheduleHandler.class.getName(), ".launchFixedDelayScheduleAsOneSecond end"));
				logger.info("//--------------------------------------------");
			}
		}

	}
}
