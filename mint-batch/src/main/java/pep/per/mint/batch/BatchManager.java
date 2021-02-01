package pep.per.mint.batch;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;

import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pep.per.mint.batch.mapper.co.JobMapper;
import pep.per.mint.common.data.basic.batch.Zob;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.mail.SendMailService;
import pep.per.mint.inhouse.sms.SendSMSService;

/**
 * Created by Solution TF on 15. 11. 4..
 */
@Service
public class BatchManager {

    Logger log = LoggerFactory.getLogger(BatchManager.class);

    Logger report = LoggerFactory.getLogger("pep.per.mint.batch.BatchManager.report");

    @Autowired
    JobMapper jobMapper;

    @Autowired
    SendSMSService sendSMSService;

    @Autowired
    @Qualifier("simpleSendMailService")
    SendMailService sendMailService;

    @Autowired
    CommonService commonService;

    SqlSessionFactory sqlSessionFactory;

    SqlSessionFactory ifmSqlSessionFactory;

    SqlSessionFactory gsspSqlSessionFactory;

    boolean standAlone = false;



    SchedulerFactory sf = new StdSchedulerFactory();

    //테스트후 TEST = false 로 해주라 제발!
    boolean TEST = false;
    //boolean TEST = true;

    static StringBuffer reportMsg = new StringBuffer();
    static StringBuffer resetReport() {reportMsg = new StringBuffer(); return reportMsg;}
    static StringBuffer addReport(Object log) {
    	try {
    		reportMsg.append(log).append("\n");
    	}catch(Exception e) {

    	}
    	return reportMsg;
    }
    static String line(String ch, int len) {
    	StringBuffer space = new StringBuffer();
    	for(int i = 0 ; i < len ; i ++ ) space.append(ch);
    	return space.toString();
    }

    void initVariables(){
    	log.info("//⎯⎯⎯⎯⎯⎯⎯init variables start ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");


//    	Runtime.getRuntime().addShutdownHook(new Thread() {
//
//    		@Override
//    		public void run() {
//    			try {
//    				if(sf.getScheduler().isShutdown()) {
//    					//sf.getScheduler().clear();
//    		        	sf.getScheduler().shutdown(true);
//    				}
//
//				} catch (SchedulerException e) {
//				}finally {
//					resetReport();
//			        addReport(line("⎯",80));
//			        addReport("⎯☛ stop BatchManager ");
//			        addReport(line("⎯",80));
//				}
//    		}
//    	});



    	addReport("⎯☛ initializing variables");
    	addReport(line("⎯",80));


    	Map<String, String> miManagerInfo = null;
	    try{
			miManagerInfo = jobMapper.getMIManangerConnectionInfo();
			if(miManagerInfo != null){
				Variables.miManagerUserName = miManagerInfo.get("username");
				if(Variables.miManagerUserName == null){
					log.warn("포털환경변수[solution.mi.manager.username]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");

					addReport("⎯\t☛ 포털환경변수[solution.mi.manager.username]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
				}else{
					//log.info(Util.join("// 포털환경변수[solution.mi.manager.username]",Variables.miManagerUserName));
					addReport(Util.join("⎯\t☛ 포털환경변수[solution.mi.manager.username]:",Variables.miManagerUserName));
				}
				Variables.miManagerPassword = miManagerInfo.get("password");
				if(Variables.miManagerPassword == null){
					log.warn("포털환경변수[solution.mi.manager.password]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
					addReport("⎯\t☛ 포털환경변수[solution.mi.manager.password]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
				}else{
					//log.info(Util.join("// 포털환경변수[solution.mi.manager.password]",Variables.miManagerPassword));
					addReport(Util.join("⎯\t☛ 포털환경변수[solution.mi.manager.password]:",Variables.miManagerPassword));
				}
				Variables.miManagerIp = miManagerInfo.get("ip");
				if(Variables.miManagerIp == null){
					log.warn("포털환경변수[solution.mi.manager.ip]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
					addReport("⎯\t☛ 포털환경변수[solution.mi.manager.ip]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
				}else{
					//log.info(Util.join("// 포털환경변수[solution.mi.manager.ip]",Variables.miManagerIp));
					addReport(Util.join("⎯\t☛ 포털환경변수[solution.mi.manager.ip]:",Variables.miManagerIp));
				}
				String port = miManagerInfo.get("port");
				if(port == null){
					log.warn("포털환경변수[solution.mi.manager.port]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
					addReport("⎯\t☛ 포털환경변수[solution.mi.manager.port]가 등록되어 있지 않습니다. 해당 환경변수를 쓰틑 배치를 실행하지 못할 수 있으니 환경변수를 확인해 주시오.");
				}else{
					Variables.miManagerPort = Integer.parseInt(port);
					log.info(Util.join("// 포털환경변수[solution.mi.manager.port]",Variables.miManagerPort));
					addReport(Util.join("⎯\t☛ 포털환경변수[solution.mi.manager.port]:",Variables.miManagerPort));
				}

				try {
					String purgeToDate = jobMapper.getPurgeToDate();
					if(purgeToDate != null || purgeToDate.trim().length() > 0) {
						Variables.purgeToDate = Integer.parseInt(purgeToDate);
					}
				}catch(Exception e) {
					log.info("Variables.purgeToDate 값 초기화시 예외발생하여 기본값으로 적용함.");
				}


			}
		}catch(Exception e){
			log.error("",e);
		}finally{
			addReport(line("⎯",80));
	    	addReport("⎯☛ finish initializing variables");
	    	//report.info(reportMsg.toString());
			log.info("//⎯⎯⎯⎯⎯⎯⎯init variables end ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
		}
    }

    Runnable batchManagerRunable = new Runnable() {
        @Override
        public void run() {



            try {




                String useBatchHostname = jobMapper.getUseBatchHostname();

                //int tryCnt = 0;
                //while (useBatchHostname == null || "false".equals(useBatchHostname)) {
                //    Thread.sleep(1000);
                //    log.debug(Util.join("useBatchHostname (try:", (tryCnt++), "): ", useBatchHostname));
                //}
                TEST = Boolean.parseBoolean(System.getProperty("batch.test", "false"));
                System.out.println("@@@@@@@@@@ BATCH TEST:" + TEST);


                addReport(line("⎯",80));
    	    	addReport("⎯☛ starting BatchManger thread");
    	    	addReport(line("⎯",80));
                addReport(Util.join("⎯\t☛ 테스트모드 옵션:",TEST));

                if(!TEST){
	                if (useBatchHostname != null && "true".equalsIgnoreCase(useBatchHostname)) {
	                    String batchHostname = jobMapper.getBatchHostname();
	                    String hostname = InetAddress.getLocalHost().getHostName();

	                    addReport(Util.join("⎯\t☛ system.batch.use.basic.hostname:",useBatchHostname));
	                    addReport(Util.join("⎯\t☛ system.batch.basic.hostname:",batchHostname));
	                    addReport(Util.join("⎯\t☛ running hostname:",hostname));

	                    if (!hostname.equals(batchHostname)) {
	                        addReport("⎯☛ batch manager 실행 호스트가 일치하지 않으므로 배치매니저를 실행하지 않는다.");
	                        return;
	                    } else {
	                        addReport("⎯☛ BatchManager 실행 호스트 일치하므로 BatchManager를 실행한다.");
	                    }
	                } else {
	                    addReport("⎯☛ 배치실행호스트옵션을 사용하지 않으므로 BatchManager를 실행한다.");
	                }
                }

                addReport(line("⎯",80));
                addReport("⎯☛ BatchManger 스케줄러 설정 시작");
                addReport(line("⎯",80));

                List<ZobSchedule> scheduleList = jobMapper.getJobScheduleList();

                if (scheduleList == null || scheduleList.size() == 0) {
                	addReport(line("⎯",80));
                	addReport("⎯☛ 등록된 스케줄이 없음.");
                } else {
                    for (ZobSchedule schedule : scheduleList) {
                        addJobSchedule(schedule);
                    }
                    addReport(line("⎯",80));
                    addReport(Util.join("⎯☛ 총",scheduleList.size(),"개의 스케줄 등록 완료."));
                }

                sf.getScheduler().start();
                addReport(line("⎯",80));
                addReport("⎯☛ BatchManger 스케줄러 실행 완료");

            }catch(Exception e){
            	addReport(line("⎯",80));
            	addReport("⎯☛ BatchManger 스케줄러 실행실패(예외발생)");
            	addReport(line("⎯",80));
            	String errorDetail = "";
    			PrintWriter pw = null;
    			try{
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();
    				pw = new PrintWriter(baos);
    				e.printStackTrace(pw);
    				pw.flush();
    				if(pw != null)  errorDetail = baos.toString();
    			}finally{
    				if(pw != null) pw.close();
    			}
            	addReport(errorDetail);

                log.error("",e);
            }finally {
            	addReport(line("⎯",80));
            	report.info(reportMsg.toString());
            }

        }
    };


    public void setEnvironments() throws Exception { 
        	addReport("\n");
        	addReport(line("=",80));
        	addReport("⎯☛ set batch system environments ");
    		addReport(line("⎯",80));
    		
    		//------------------------------------------------------------------------------------
    		//  MI 8버전에서 새롭게 추가 된 통신간 암호화 사용을 할때는 JVM 옵션으로 -DTCP_ENCOD=true를 추가 
    		//------------------------------------------------------------------------------------
    		{
	    		String tcpEncodeOption = commonService.getEnvironmentalValue("solution","mi.manager.properties.tcp.encode", "false");
	    		System.setProperty("TCP_ENCODE", tcpEncodeOption);
	    		addReport(Util.join("⎯☛ MI API 설정(system.batch.run.standalone):", tcpEncodeOption) );
    		}
    		addReport(line("⎯",80));   	
    }
    

    public void loadJob() throws Exception {
    	try {
    		
    		setEnvironments();
    		
        	addReport("\n");
        	addReport(line("=",80));
        	addReport("⎯☛ ready to start BatchManager ");

    		addReport(line("⎯",80));
    		addReport("⎯☛ BatchManger 단독 실행 옵션 체크");
    		addReport(line("⎯",80));
    		boolean runStandAlone = commonService.getEnvironmentalBooleanValue("system","batch.run.standalone", false);
    		//addReport(Util.join("⎯☛ 현재 배치실행옵션(standAlone):",BatchManager.this.standAlone) );
    		addReport(Util.join("⎯☛ 배치단독실행환경설정(system.batch.run.standalone):",runStandAlone) );

    		if(BatchManager.this.standAlone != runStandAlone) {
    			if(BatchManager.this.standAlone) {
    				addReport("⎯☛ 배치단독실행환경 설정 값이 false 이므로 BatchManger를 단독실행하지 않습니다.");
    			}else {
    				addReport("⎯☛ 배치단독실행환경 설정 값이 true 이므로 BatchManger를 프론트통합서버와 함께 실행하지 않습니다.");
    			}
    			return;
    		}

    		if(BatchManager.this.standAlone) {
    			addReport("⎯☛ BatchManger를 단독으로 실행합니다.");
    		}else {
    			addReport("⎯☛ BatchManger를 프론트통합서버와 함께 실행합니다.");
    		}
    		addReport(line("⎯",80));

	    	initVariables();



	        Thread thread = new Thread(batchManagerRunable);
	        thread.start();

    	}finally {

    	}
    }


    public void stopBachManager() throws  Exception {
    	try {
	    	sf.getScheduler().clear();
	        sf.getScheduler().shutdown(true);
	        Thread.sleep(1000);
    	}finally{
	        resetReport();
	        addReport(line("⎯",80));
	        addReport("⎯☛ stop BatchManager ");
	        addReport(line("⎯",80));
    	}
    }

    public void reloadJob() throws Exception {
        stopBatchManager(1000);
        loadJob();
    }

    public void startBatchManager() throws Exception {
    	try {
    		loadJob();
    	}finally {

    	}
    }

    public void stopBatchManager(long delay) throws SchedulerException {
    	try {
	        try {
	            // wait five minutes to show jobs
	            Thread.sleep(delay);
	            // executing...
	        } catch (Exception e) {
	            //
	        }

	        log.info("⎯⎯⎯⎯⎯⎯⎯☛ Shutting Down ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
	        sf.getScheduler().shutdown(true);
	        log.info("⎯⎯⎯⎯⎯⎯⎯☛ Shutdown Complete ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");

	        SchedulerMetaData metaData = sf.getScheduler().getMetaData();
	        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    	}finally {
	        resetReport();
	        addReport(line("⎯",80));
	        addReport("⎯☛ stop BatchManager ");
	        addReport(line("⎯",80));
    	}
    }

    @SuppressWarnings("unchecked")
	private void addJobSchedule(ZobSchedule schedule) throws SchedulerException, ClassNotFoundException {

        String scheduleNm = schedule.getScheduleNm();

        Zob zob = schedule.getZob();

        //for test 20170328
        //if(!"14".equals(zob.getJobId())) return;

        Class clazz = Class.forName(zob.getImplClass());

        JobDataMap dataMap = new JobDataMap();
        dataMap.put("sqlSessionFactory", sqlSessionFactory);
        dataMap.put("tagSqlSessionFactory", sqlSessionFactory);
        dataMap.put("ifmSqlSessionFactory", ifmSqlSessionFactory);
        dataMap.put("gsspSqlSessionFactory", gsspSqlSessionFactory);
        dataMap.put("sendSMSService", sendSMSService);	   // inhouse sms send
        dataMap.put("sendMailService", sendMailService);//inhouse sendMailService param
        dataMap.put("commonService", commonService);

        dataMap.put("schedule",schedule);
        String jobIdentity = Util.join(zob.getJobId(), ".", zob.getJobNm());
        JobDetail job = newJob(clazz).withIdentity(jobIdentity, zob.getGroupId()).setJobData(dataMap).build();

        String scheduleIdentity = Util.join(schedule.getScheduleId(), ".",schedule.getScheduleNm());


        String type = schedule.getType();

        //System.out.println("ZobSchedule:" + schedule.getScheduleNm() + ", type:"+ type);

        if(ZobSchedule.TYPE_CRONTAB.equals(type)) {
        	CronTrigger trigger = newTrigger().withIdentity(scheduleIdentity, zob.getGroupId()).withSchedule(cronSchedule(schedule.getValue())).build();
        	Date ft = sf.getScheduler().scheduleJob(job, trigger);
        	//log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
        	addReport(Util.join("⎯\t☛ ", job.getKey() ," has been scheduled to run at: " , ft , " and repeat based on expression: " , trigger.getCronExpression()));
        }else if(ZobSchedule.TYPE_INTERVAL_IN_SEC.equals(type)) {
        	Trigger trigger
	        	= newTrigger().withIdentity(scheduleIdentity, zob.getGroupId()).withSchedule(
	        			simpleSchedule().withIntervalInSeconds(Integer.parseInt(schedule.getValue())).repeatForever()
	        			).build();
        	Date ft = sf.getScheduler().scheduleJob(job, trigger);
        	//log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getStartTime().toString());
        	addReport(Util.join("⎯\t☛ ", job.getKey() , " has been scheduled to run at: " , ft , " and repeat based on expression: delay(sec) : " , schedule.getValue()));
        }else if(ZobSchedule.TYPE_INTERVAL_IN_MIN.equals(type)) {

        	Trigger trigger
	        	= newTrigger().withIdentity(scheduleIdentity, zob.getGroupId()).withSchedule(
	        			simpleSchedule().withIntervalInMinutes(Integer.parseInt(schedule.getValue())).repeatForever()
	        			).build();
	    	Date ft = sf.getScheduler().scheduleJob(job, trigger);
	    	//log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getStartTime().toString());
	    	addReport(Util.join("⎯\t☛ ", job.getKey() , " has been scheduled to run at: " , ft , " and repeat based on expression: delay(min) : " , schedule.getValue()));

        }else if(ZobSchedule.TYPE_INTERVAL_IN_HOUR.equals(type)) {
        	Trigger trigger
	        	= newTrigger().withIdentity(scheduleIdentity, zob.getGroupId()).withSchedule(
	        			simpleSchedule().withIntervalInHours(Integer.parseInt(schedule.getValue())).repeatForever()
	        			).build();
	    	Date ft = sf.getScheduler().scheduleJob(job, trigger);
	    	//log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getStartTime().toString());
	    	addReport(Util.join("⎯\t☛ ", job.getKey() , " has been scheduled to run at: " , ft , " and repeat based on expression: delay(hour) : " , schedule.getValue()));

        }else {

        }


    }

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSessionFactory getIfmSqlSessionFactory() {
		return ifmSqlSessionFactory;
	}

	public void setIfmSqlSessionFactory(SqlSessionFactory ifmSqlSessionFactory) {
		this.ifmSqlSessionFactory = ifmSqlSessionFactory;
	}

    public SqlSessionFactory getGsspSqlSessionFactory() {
		return gsspSqlSessionFactory;
	}

	public void setGsspSqlSessionFactory(SqlSessionFactory gsspSqlSessionFactory) {
		this.gsspSqlSessionFactory = gsspSqlSessionFactory;
	}


	public boolean isStandAlone() {
		return standAlone;
	}


	public void setStandAlone(boolean standAlone) {
		this.standAlone = standAlone;
	}



}
