package pep.per.mint.agent.controller;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import pep.per.mint.agent.AgentInfoChanggeListener;
import pep.per.mint.agent.AgentLauncherInterface;
import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.agent.service.AgentService;
import pep.per.mint.agent.service.FileUploadService;
import pep.per.mint.agent.service.NetstatService;
import pep.per.mint.agent.service.QmgrCheckService;
import pep.per.mint.agent.service.ResourceCheckService;
import pep.per.mint.agent.service.SimulatorService;
import pep.per.mint.agent.util.NHUtil;
import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.SiteInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.MonitorItem;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

@Controller
public class AgentController {

	final static Logger logger = LoggerFactory.getLogger(AgentController.class);

	final static int AGENT_MODE_REMOTE = 0;
	final static int AGENT_MODE_LOCAL = 1;

	public final static String CMD_STATUS_INIT = "1";
	public final static String CMD_STATUS_ING = "2";
	public final static String CMD_STATUS_SUCCESS = "3";
	public final static String CMD_STATUS_FAIL = "9";

	
	@Value("${iip.agent.use.module.netstat.cmd:}")
	String netstatCmd;
	
	
	@Value("${iip.agent.use.module.netstat:N}") 
	String useNetstat = "Y";
	

	@Autowired
	NetstatService netstatService;

	@Autowired
	ResourceCheckService resourceCheckService;
	@Autowired
	QmgrCheckService qmgrCheckService;

	@Autowired
	AgentService  agentService;
	@Autowired
	FileUploadService  fileService;
	@Autowired
	SimulatorService  simulatorService;


	String agentId;
	String agentNm;
	String agentConsumerServiceUrlBasic;
	int agentInitMode = AGENT_MODE_LOCAL;

	IIPAgentInfo agentInfo;
	IIPAgentInfo basicAgentInfo;
	//IIPAgent agent;
	AgentLauncherInterface launcher;
	AgentInfoChanggeListener agentInfoChangeListener;

	List<ResourceInfo> resources;

	List<ProcessInfo> processes;

	List<QmgrInfo> qmgrsForILink;

	List<QmgrInfo> qmgrsForWMQ;

	List<ApplicationInfo> versionInfo;

	SiteInfo siteInfo;

	boolean isLogin = false;
	boolean hasNetStataCheckList = false;

	long getAgentDelay = 5*60000;
	long commonDelay = 10000;
	long cpuCheckDelay = 10000;
	long memCheckDelay = 10000;
	long diskCheckDelay = 10000;
	long processCheckDelay = 10000;
	long qmgrCheckDelay = 10000;
	long deployCheckDelay = 10000;
	long aliveCheckDelay = 60000;
	long managementCommandCheckDelay = 10000;

	long versionCheckDelay = 60000;


	/**
	 *
	 */
	public static int initDelaySec = 5000;


	MessageHandler messageHandler = null;

	LinkedBlockingQueue<ComMessage<?,?>>  lbq = null;
 

	public AgentController(int mode, IIPAgentInfo basicAgentInfo, LinkedBlockingQueue<ComMessage<?,?>> lbq){
		this.agentInitMode = mode;
		this.basicAgentInfo = basicAgentInfo;
		this.lbq = lbq;

		messageHandler = new MessageHandler();

	}


	public void updateAgentInfoByCmd(){
		try{
			init();
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".updateAgentInfo"), t);
		}
	}


	long consumeGetAgentElapsed = System.currentTimeMillis();
	public void updateAgentInfo(){
		try{
			long elapsed = System.currentTimeMillis() - consumeGetAgentElapsed;
			if(elapsed < getAgentDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not init agent info since It has not been ",commonDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					init();
				}finally{
					consumeGetAgentElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".updateAgentInfo"), t);
		}
	}


	public void init(){
		messageHandler = new MessageHandler();
		this.agentNm = basicAgentInfo.getAgentNm();

		logger.debug(Util.join("IIPAgent:",basicAgentInfo.getAgentNm(), " init...[", agentInitMode,"][", AGENT_MODE_LOCAL, "]"));

		if(agentInitMode == AGENT_MODE_LOCAL) {

			logger.debug("IIPAgent  모드가 로컬이므로 빈 설정파일(mint-agent.xml)에서 초기값을 참조한다.");
			logger.debug(Util.join("IIPAgentInfo:",Util.toJSONString(basicAgentInfo)));
			this.agentInfo = basicAgentInfo;
		}else{

			int initTryCnt = 0;

			do {
				try{
					if(initTryCnt > 0){
						logger.debug(Util.join("AgentController.init try ", initTryCnt, " times..."));
						if(Util.isEmpty(agentInfo)) {
							logger.debug("AgentController.init:getAgentInfo == null ");
						}
					}

					initTryCnt ++;
					this.agentId = agentInfo.getAgentId();

					logger.debug(Util.join("agentInfo:",Util.toJSONString(agentInfo)));

					if(Util.isEmpty(agentInfo)) Thread.sleep(initDelaySec);

				}catch(Exception e){
					logger.error("AgentController.init error:getAgentInfo", e);
					try {
						Thread.sleep(initDelaySec);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

			}while(Util.isEmpty(agentInfo));

		}
		List<MonitorItem> items = agentInfo.getMonitorItems();

		for(int i = 0 ; i < items.size() ; i ++){
			MonitorItem item = items.get(i);
			if(MonitorItem.ITEM_TYPE_RESOURCE.equals(item.getItemType())){
				this.resources = item.getResources();
			}else if(MonitorItem.ITEM_TYPE_PROCESS.equals(item.getItemType())){
				this.processes = item.getProcesses();
			}else if(MonitorItem.ITEM_TYPE_QMGR.equals(item.getItemType())){
				qmgrsForILink = new ArrayList<QmgrInfo>();
				qmgrsForWMQ = new ArrayList<QmgrInfo>();
				if(item.getQmgrs() != null && item.getQmgrs().size() > 0){
					for (QmgrInfo qmgrInfo : item.getQmgrs() ) {
						if(QmgrInfo.TYPE_ILINK.equals(qmgrInfo.getType())){
							qmgrsForILink.add(qmgrInfo);
						}else{
							qmgrsForWMQ.add(qmgrInfo);
						}
					}//end of for
				}//end of item if
			}
		}

		{ // NH ApplicationInfo 설정 로딩
			try{
				ObjectMapper mapper = new ObjectMapper();
				TypeReference<List<ApplicationInfo>> mapType = new TypeReference<List<ApplicationInfo>>() {};
				Class clazz = AgentController.class;
			    InputStream inputStream = clazz.getResourceAsStream("/config/SiteInfo.json");
			    String data = NHUtil.readFromInputStream(inputStream);
			    logger.debug(data);
				versionInfo = mapper.readValue(data, mapType);
				for(ApplicationInfo appInfo  : versionInfo){
					appInfo.setServerId(agentInfo.getServer().getServerId());
					appInfo.setRegId("iip");
				}
			}catch(Exception e){
				logger.warn(e.getMessage(),  e);
			}
			// NH interface system home Info
			try{
				ObjectMapper mapper = new ObjectMapper();
				TypeReference<SiteInfo> mapType = new TypeReference<SiteInfo>() {};
				Class clazz = AgentController.class;
			    InputStream inputStream = clazz.getResourceAsStream("/config/SiteSystemInfo.json");
			    String data = NHUtil.readFromInputStream(inputStream);
			    logger.debug(data);
				siteInfo = mapper.readValue(data, mapType);
			}catch(Exception e){
				logger.warn(e.getMessage(),  e);
			}


		}

		{

//			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//			ch.qos.logback.classic.Logger logger = loggerContext.getLogger(packageName);
//			System.out.println(packageName + " current logger level: " + logger.getLevel());
//			System.out.println(" You entered: " + logLevel);
//			logger.setLevel(Level.toLevel(logLevel));
			// Log Level 변경. TODO
//			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
//			root.setLevel(Level.toLevel(agentInfo.getLogLevel()));

		}
		{
			commonDelay = 1000 * agentInfo.getLogDelay();
			logger.info("//--------------------------------------------");
			logger.info(Util.join("// agent commonDelay delay:",commonDelay, "ms"));
			logger.info("//--------------------------------------------");
			this.cpuCheckDelay = commonDelay;
			this.memCheckDelay = commonDelay;
			this.diskCheckDelay = commonDelay;
			this.processCheckDelay = commonDelay;
			this.qmgrCheckDelay = commonDelay;
			this.deployCheckDelay = commonDelay;
			this.aliveCheckDelay = commonDelay;
			this.managementCommandCheckDelay = commonDelay;
			this.aliveCheckDelay = versionCheckDelay;

		}
	}

	long consumeCpuLogElapsed = System.currentTimeMillis();
	/**
	 * CPU 로그 수집 스케줄러
	 * @throws Exception
	 */

	public void consumeCpuUsageLog()  {
		try{
			if(Util.isEmpty(resources)) {
				logger.debug("IIPAgent have not collected CPU resource logs since It have no resource list ");
				return;
			}

			long elapsed = System.currentTimeMillis() - consumeCpuLogElapsed;

			if(elapsed < cpuCheckDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not collected resource logs since It has not been ",commonDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					List<ResourceUsageLog> logs = resourceCheckService.getResourceUsageLog(ResourceInfo.TYPE_CPU, resources);
					logger.debug(Util.join("resource cpu log list:",Util.toJSONString(logs)));


						if(logs == null || logs.size() == 0){
							logger.info(Util.join("resource cpu log size = 0"));
						}else{

							ComMessage<List<ResourceUsageLog>, ?> msg = new ComMessage();
							msg.setId(UUID.randomUUID().toString());
							msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							msg.setUserId(agentNm);
							msg.setCheckSession(false);
							msg.setRequestObject(logs);


							Extension ext = new Extension();
							ext.setMsgType(Extension.MSG_TYPE_PUSH);
							ext.setServiceCd(ServiceCodeConstant.WS0027);
							msg.setExtension(ext);
							this.putQueue(msg);

							logger.info(Util.join("resource cpu log size["+logs.size()+"] send"));
						}

				}finally{
					consumeCpuLogElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeCpuUsageLog"), t);
		}
	}


	long consumeMemoryLogElapsed = System.currentTimeMillis();
	/**
	 * MEMORY 로그 수집 스케줄러
	 * @throws Exception
	 */
	public void consumeMemoryUsageLog()  {
		try{
			if(Util.isEmpty(resources)) {
				logger.debug("IIPAgent have not collected Memory resource logs since It have no resource list ");
				return;
			}

			long elapsed = System.currentTimeMillis() - consumeMemoryLogElapsed;

			if(elapsed < memCheckDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not collected resource logs since It has not been ",memCheckDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					List<ResourceUsageLog> logs = resourceCheckService.getResourceUsageLog(ResourceInfo.TYPE_MEMORY, resources);
					logger.debug(Util.join("resource memory log list:",Util.toJSONString(logs)));


						if(logs == null || logs.size() == 0){
							logger.info(Util.join("resource memory log size = 0"));
						}else{
							ComMessage<List<ResourceUsageLog>, ?> msg = new ComMessage();
							msg.setId(UUID.randomUUID().toString());
							msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							msg.setUserId(agentNm);
							msg.setCheckSession(false);
							msg.setRequestObject(logs);

							Extension ext = new Extension();
							ext.setMsgType(Extension.MSG_TYPE_PUSH);
							ext.setServiceCd(ServiceCodeConstant.WS0028);
							msg.setExtension(ext);

							this.putQueue(msg);

							logger.debug(Util.join("resource memory log size["+logs.size()+"] send"));
						}
				}finally{
					consumeMemoryLogElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeMemoryUsageLog"), t);
		}

	}

	long consumeDiskLogElapsed = System.currentTimeMillis();
	/**
	 * DISK 로그 수집 스케줄러
	 * @throws Exception
	 */
	public void consumeDiskUsageLog() {
		try{
			if(Util.isEmpty(resources)) {
				logger.debug("IIPAgent have not collected Disk resource logs since It have no resource list ");
				return;
			}

			long elapsed = System.currentTimeMillis() - consumeDiskLogElapsed;

			if(elapsed < diskCheckDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not collected resource logs since It has not been ",diskCheckDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					List<ResourceUsageLog> logs = resourceCheckService.getResourceUsageLog(ResourceInfo.TYPE_DISK, resources);
					logger.debug(Util.join("resource disk log list:",Util.toJSONString(logs)));

						if(logs == null || logs.size() == 0){
							logger.info(Util.join("resource disk log size = 0"));
						}else{
							ComMessage<List<ResourceUsageLog>, ?> msg = new ComMessage();
							msg.setId(UUID.randomUUID().toString());
							msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							msg.setUserId(agentNm);
							msg.setCheckSession(false);
							msg.setRequestObject(logs);


							Extension ext = new Extension();
							ext.setMsgType(Extension.MSG_TYPE_PUSH);
							ext.setServiceCd(ServiceCodeConstant.WS0029);
							msg.setExtension(ext);


							this.putQueue(msg);
							logger.debug(Util.join("resource disk log size ["+logs.size()+"] send."));
						}

				}finally{
					consumeDiskLogElapsed = System.currentTimeMillis();
				}
			}

		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeDiskUsageLog"), t);
		}

	}

	long consumeProcessStatusLogElapsed = System.currentTimeMillis();
	public List consumeProcessStatusLog()  {
		List<ProcessStatusLog> logs  = null;
		try{
			if(Util.isEmpty(processes)) {
				logger.debug("IIPAgent have not collected process logs since It have no process list ");
				return null;
			}

			long elapsed = System.currentTimeMillis() - consumeProcessStatusLogElapsed;

			if(elapsed >= processCheckDelay) {
				try{
					logs = resourceCheckService.getProcessCheckLog(processes);
					logger.debug(Util.join("process log list:",Util.toJSONString(logs)));

						if(logs == null || logs.size() == 0){
							logger.info(Util.join("process log size = 0"));
						}else{

							ComMessage<List<ProcessStatusLog>, ?> msg = new ComMessage();
							msg.setId(UUID.randomUUID().toString());
							msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							msg.setUserId(agentNm);
							msg.setCheckSession(false);
							msg.setRequestObject(logs);

							Extension ext = new Extension();
							ext.setMsgType(Extension.MSG_TYPE_PUSH);
							ext.setServiceCd(ServiceCodeConstant.WS0030);
							msg.setExtension(ext);

							this.putQueue(msg);
							logger.info(Util.join("process log size ["+ logs.size()+"] send."));
						}

				}finally{
					consumeProcessStatusLogElapsed = System.currentTimeMillis();
				}
			}else{
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeProcessStatusLog"), t);
		}

		return logs;
	}

	long consumeQmgrLogElapsed = System.currentTimeMillis();

	public void consumeQmgrLog() {
		try{
			if(Util.isEmpty(qmgrsForILink) && Util.isEmpty(qmgrsForWMQ)) {
				logger.debug("IIPAgent have not collected qmgr logs since It have no qmgrs list ");
				return;
			}

			long elapsed = System.currentTimeMillis() - consumeQmgrLogElapsed;

			if(elapsed < qmgrCheckDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not collected qmgrs logs since It has not been ",qmgrCheckDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					List<QmgrLogs> logs = new ArrayList<QmgrLogs>();

					if(!Util.isEmpty(qmgrsForILink)){
						List<QmgrLogs> logsForILink = qmgrCheckService.getQmgrLogs(agentId, qmgrsForILink, QmgrInfo.TYPE_ILINK);
						if(!Util.isEmpty(logsForILink)){
							logs.addAll(logsForILink);
						}
					}

					if(!Util.isEmpty(qmgrsForWMQ)){
						List<QmgrLogs> logsForWMQ = qmgrCheckService.getQmgrLogs(agentId, qmgrsForWMQ, QmgrInfo.TYPE_MQ);
						if(!Util.isEmpty(logsForWMQ)){
							logs.addAll(logsForWMQ);
						}
					}


					logger.debug(Util.join("qmgr log list:",Util.toJSONString(logs)));

						if(logs == null || logs.size() == 0){
							logger.info(Util.join("qmgr log size = 0"));
						}else{

							ComMessage<List<QmgrLogs>,?> msg = new ComMessage();
							msg.setId(UUID.randomUUID().toString());
							msg.setStartTime(Util.getFormatedDate());
							msg.setUserId(agentNm);

							msg.setCheckSession(false);
							msg.setRequestObject(logs);


							Extension ext = new Extension();
							ext.setMsgType(Extension.MSG_TYPE_PUSH);
							ext.setServiceCd(ServiceCodeConstant.WS0031);
							msg.setExtension(ext);

							this.putQueue(msg);

							logger.info(Util.join("qmgr log size ["+ logs.size()+"] send."));
						}
				}finally{
					consumeQmgrLogElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeQmgrLog"), t);
		}
	}


	long consumeVersionLogElapsed = System.currentTimeMillis();

	public void consumeVersionLog() {
		try{
			if(Util.isEmpty(versionInfo)) {
				logger.debug("IIPAgent have not collected version list ");
				return;
			}

			long elapsed = System.currentTimeMillis() - consumeVersionLogElapsed;

			if(elapsed < versionCheckDelay) {
				//if(elapsed > 10 * 1000) logger.debug(Util.join("IIPAgent have not collected qmgrs logs since It has not been ",qmgrCheckDelay ," milli seconds yet.(elapsed:",elapsed,"ms)"));
			} else {
				try{
					List<ApplicationInfo> logs = new ArrayList<ApplicationInfo>();

					if(!Util.isEmpty(versionInfo)){
						List<ApplicationInfo> appInfologs = agentService.getVersionLogs(agentInfo.getServer().getServerId(),agentInfo.getAgentNm(),  versionInfo);
						if(!Util.isEmpty(appInfologs)){
							logs.addAll(appInfologs);
						}
					}


					logger.debug(Util.join("agent version log list:",Util.toJSONString(logs)));

					if(logs == null || logs.size() == 0){
						logger.info(Util.join("agent version log size = 0"));
					}else{

						ComMessage<List<ApplicationInfo>,?> msg = new ComMessage();
						msg.setId(UUID.randomUUID().toString());
						msg.setStartTime(Util.getFormatedDate());
						msg.setUserId(agentNm);

						msg.setCheckSession(false);
						msg.setRequestObject(logs);


						Extension ext = new Extension();
						ext.setMsgType(Extension.MSG_TYPE_PUSH);
						ext.setServiceCd(ServiceCodeConstant.WS0050);
						msg.setExtension(ext);

						this.putQueue(msg);

						logger.info(Util.join("agent version log ["+logs.size()+"]ea send"));
					}
				}finally{
					consumeVersionLogElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeVersionLog"), t);
		}
	}


	long deployCheckElapsed = System.currentTimeMillis();
	public void deployCheck() {
		try{
			long elapsed = System.currentTimeMillis() - deployCheckElapsed;

			if(elapsed < deployCheckDelay) {
			} else {
				try{
					logger.info("//--------------------------------------------");
					logger.info("// IIPAgent start for checking to deploy new source!");
					logger.info("//--------------------------------------------");


					logger.debug("IIPAgentInfo.IIP_AGENT_SERVICE_GET_CMD:" + IIPAgentInfo.IIP_AGENT_SERVICE_GET_CMD);

					String url = basicAgentInfo.getAgentServiceMap().get(IIPAgentInfo.IIP_AGENT_SERVICE_GET_CMD).getServiceUri();
					url = Util.join(agentConsumerServiceUrlBasic, url);
					//					CommandConsole cc = restfulConsumerService.getDeployCommand(agentId, url);
					//					if(cc == null) {
					//						logger.info("//--------------------------------------------");
					//						logger.info("// 배포명령이 존재하지 않아 재배포 처리 SKIP!");
					//						logger.info("//--------------------------------------------");
					//						return;
					//
					//					}
					//					String exeDate = cc.getExecuteDate();
					//Calendar cal = Calendar.getInstance();
					//cal.set(Calendar., value);
					//
					//
					//					cc.setAgentId(agentId);
					//					cc.setResultCd(CMD_STATUS_ING);
					//					cc.setResultMsg("starting to redeploy");
					//					cc.setResultDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
					//					sendCmdStatus(cc);

					//					String repositoryData = Util.toJSONString(cc);
					String repositoryData="";

					// TODO
					//					launcher.saveRepository(AgentLauncherInterface.CMD_DEPLOY_FILE_NAME, repositoryData);?
					//					launcher.deploy();?


				}finally{
					deployCheckElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".deployCheck"), t);
		}
	}

	long aliveCheckElapsed = System.currentTimeMillis();
	private void aliveCheck() {
		try{
			long elapsed = System.currentTimeMillis() - aliveCheckElapsed;

			if(elapsed < aliveCheckDelay) {
			} else {
				try{
					logger.info("//--------------------------------------------");
					logger.info("// IIPAgent start for checking alive !");
					logger.info("//--------------------------------------------");
					// TODO
					launcher.checkAlive();

					logger.info("//--------------------------------------------");
					logger.info("// IIPAgent start for checking alive OK!");
					logger.info("//--------------------------------------------");

				}finally{
					aliveCheckElapsed = System.currentTimeMillis();
				}
			}
		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".aliveCheck"), t);
		}
	}


	public void setAgentLauncherInterface(AgentLauncherInterface launcher) {
		this.launcher = launcher;
	}

	public void setAgentInfoChangeListener(AgentInfoChanggeListener listener){
		this.agentInfoChangeListener = listener;
	}



	public ComMessage<?,?> loadAgentInfo(ComMessage<?,?> request) {
		System.out.println("agent login......");
		isLogin = true;
		return agentService.loadAgentInfo(request);
	}



	public ComMessage loadClass(ComMessage request) {
		ComMessage msg = request;
		msg.setErrorCd("0");
		msg.setCheckSession(false);
		msg.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		Extension ext = msg.getExtension();
		if(ext == null){
			 ext = new Extension();
		}
		msg.setUserId(agentInfo.getAgentNm());

		ext.setMsgType(Extension.MSG_TYPE_RES);
		ext.setServiceCd(ServiceCodeConstant.WS0021);
		msg.setExtension(ext);

		try {
			this.lbq.put(msg);
			// 메시지가 처리 되지 전에  Restart 되는 경우가 발생
			// 2018-08-22
			Thread.sleep(5*1000);

			this.launcher.deploy();
		} catch (Exception e) {
			logger.warn("loadClass error", e);
		}

		return msg;
	}


	public ComMessage<?,?> aliveCheck(ComMessage<?,?> request) {
		return agentService.aliveCheck(request);
	}


	/**
	 * @param request
	 * @return
	 * @deprecated
	 */
	public ComMessage uploadFile(ComMessage request) {

		//fileService.uploadFil(dest, contents);
		return null;
	}


	public ComMessage getFileContents(ComMessage request) {
		ComMessage resMsg = request;
		Extension hm = new Extension();
		hm.setMsgType(Extension.MSG_TYPE_RES);
		hm.setServiceCd(ServiceCodeConstant.WS0023);

		String filePath = (String)((Map)request.getRequestObject()).get("file");

		if(filePath == null || filePath.length()<=0){
			resMsg.setErrorCd("");
			resMsg.setErrorMsg("");
			resMsg.setExtension(hm);

			return resMsg;
		}

		byte[] fileContents= null;
		try {
			fileContents = fileService.getFileContents(filePath);
			HashMap  resMap = new HashMap();
			resMap.put("file", filePath);
			resMap.put("contents", new String(fileContents));
			resMsg.setResponseObject(resMap);
			resMsg.setRequestObject(null);
			resMsg.setErrorCd("0");
		} catch (AgentException e) {
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg(e.getMessage());
			logger.warn("file read Error ["+filePath+"]", e);
		}
		resMsg.setExtension(hm);

		return  resMsg;
	}


	public ComMessage testCall(ComMessage request) {
		ComMessage resMsg = request;

		Extension hm = new Extension();
		hm.setMsgType(Extension.MSG_TYPE_RES);
		hm.setServiceCd(ServiceCodeConstant.WS0034);


		InterfaceCallDetail intfList = ((InterfaceCallDetail)request.getRequestObject());
		InterfaceCallDetail resTest = intfList;


		if(intfList == null){
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg("InterfaceCallDetail is null");
			resMsg.setExtension(hm);
			return resMsg;
		}

		try {
			resTest = simulatorService.testCall(intfList, siteInfo);

			resMsg.setResponseObject(resTest);
//			resMsg.setRequestObject(null);
			resMsg.setErrorCd("0");
		} catch (AgentException e) {
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg(e.getMessage());
			logger.warn("TestCall Error ", e);
		}
		resMsg.setExtension(hm);

		return  resMsg;
	}


	public void putQueue(ComMessage msg){
		this.lbq.add(msg);
	}


	public void setAgentInfo(IIPAgentInfo iipAgentInfo) {
		this.agentInfo = iipAgentInfo;
	}

	public IIPAgentInfo getAgentInfo() {
		return this.agentInfo;
	}


	public ComMessage<?, ?> getConfigFile(ComMessage request) {
		ComMessage resMsg = request;

		Extension hm = new Extension();
		hm.setMsgType(Extension.MSG_TYPE_RES);
		hm.setServiceCd(ServiceCodeConstant.WS0054);


		ConfigInfo reqInfo = ((ConfigInfo)request.getRequestObject());

		reqInfo.setModId(agentInfo.getAgentNm());
		reqInfo.setModDate(Util.getFormatedDate());

		if(reqInfo == null){
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg("RequestObject[ConfigInfo] is null");
			resMsg.setExtension(hm);
			return resMsg;
		}

		ConfigInfo resInfo = reqInfo;
		try {
			//  TODO  FileNm exist 체크  및 getType  체크 할 것.
			if(reqInfo.getFileNm() == null || reqInfo.getTypeNm() == null){
				resMsg.setErrorCd("9");
				resMsg.setErrorMsg("RequestObject[ConfigInfo].getFileNm or getType is null");
				resMsg.setExtension(hm);
				return resMsg;
			}else{

				File f = new File(reqInfo.getFileNm());
				if(!(f.exists() && f.isFile())){
					resMsg.setErrorCd("9");
					resMsg.setErrorMsg("RequestObject[ConfigInfo].getFileNm invalid["+reqInfo.getFileNm()+"]");
					resMsg.setExtension(hm);
					return resMsg;
				}

				if("JAVA".equalsIgnoreCase(reqInfo.getTypeNm()) || "API".equalsIgnoreCase(reqInfo.getTypeNm()) || "TMAX".equalsIgnoreCase(reqInfo.getTypeNm())){

					List<String>  list =  fileService.getConfigFile(reqInfo.getFileNm(), reqInfo.getTypeNm());

					resInfo.setIntfList(list);

					resMsg.setResponseObject(resInfo);
					resMsg.setErrorCd("0");

				}else{
					resMsg.setErrorCd("9");
					resMsg.setErrorMsg("RequestObject[ConfigInfo].getType invalid["+reqInfo.getTypeNm()+"]");
					resMsg.setExtension(hm);
					return resMsg;
				}
			}
		} catch (Exception e) {
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg(e.getMessage());
			logger.warn("getConfigFile Error ", e);
		}
		resMsg.setExtension(hm);

		return  resMsg;
	}


	public void logoff() {

		ComMessage<?,?> sendComMsg = new ComMessage();
		sendComMsg.setId(UUID.randomUUID().toString());
		sendComMsg.setUserId(agentInfo.getAgentNm());
		sendComMsg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

		Extension ext = new Extension();
		ext.setMsgType(Extension.MSG_TYPE_REQ);
		ext.setServiceCd(ServiceCodeConstant.WS0026);
		sendComMsg.setExtension(ext);

		try {
			this.lbq.put(sendComMsg);
		} catch (InterruptedException e) {
			logger.warn(e.getMessage(),e);
		}

		isLogin = false;
		hasNetStataCheckList = false;
	}


	/**
	 * for netstat log
	 */
	long netstatLogElapsed = System.currentTimeMillis();
	
	@Value("${iip.agent.use.module.netstat.delay:11}")
	long netstatCheckDelay = 10;

	
	public void consumeNetstatLog() {

		if(!hasNetStataCheckList) {
			return;
		}

		List<Map<String, String>> logs  = null;
		try{


			long elapsed = System.currentTimeMillis() - netstatLogElapsed;

			if(elapsed < netstatCheckDelay * 1000) {

			} else {
				try{
					logs = netstatService.netstat(netstatCmd);
					if(logs != null){
						//logger.debug(Util.join("netstat log :",Util.toJSONString(logs)));
						//log가 null이어도 이벤트를 전달한다.

						ComMessage<List<Map<String, String>>, ?> msg = new ComMessage();
						msg.setId(UUID.randomUUID().toString());
						msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
						msg.setUserId(agentNm);
						msg.setCheckSession(false);
						msg.setRequestObject(logs);

						Extension ext = new Extension();
						ext.setMsgType(Extension.MSG_TYPE_PUSH);
						ext.setServiceCd(ServiceCodeConstant.WS0043);
						msg.setExtension(ext);

						if(logs.size() > 0 ) {

							this.putQueue(msg);

							for(Map<String, String> log :logs) {
								String serverId = log.get("serverId");
								String state = log.get("state");
								netstatService.updateState(serverId, state);
							}
						}
						logger.info(Util.join("netstat log size ["+ logs.size()+"] send."));
					}

				}finally{
					netstatLogElapsed = System.currentTimeMillis();
				}
			}

		}catch(Throwable t) {

			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".consumeNetstatLog"), t);

		}

	}



	long netstatCheckListElapsed = System.currentTimeMillis();
	@Value("${iip.agent.use.module.netstat.list.check.delay:61}")
	long netstatCheckListDelay = 60;
	
	public void getNetstatCheckList() {
		try{


			long elapsed = System.currentTimeMillis() - netstatCheckListElapsed;

			//logger.info("elapsed:" + elapsed + ", netstatCheckListDelay:"+netstatCheckListDelay + ", netstatCheckListElapsed:" + netstatCheckListElapsed);

			if(elapsed < netstatCheckListDelay * 1000) {

 			} else {

				try{

					ComMessage<List<Map<String, String>>, ?> msg = new ComMessage();
					msg.setId(UUID.randomUUID().toString());
					msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
					msg.setUserId(agentNm);
					msg.setCheckSession(false);

					Extension ext = new Extension();
					ext.setMsgType(Extension.MSG_TYPE_REQ);
					ext.setServiceCd(ServiceCodeConstant.WS0044);
					Map params = new HashMap();
					params.put("agentNm", agentNm);


					ext.setParams(params);
					msg.setExtension(ext);

					this.putQueue(msg);
					logger.info(Util.join("request netstat check list."));


				}finally{
					netstatCheckListElapsed = System.currentTimeMillis();
				}
			}

		}catch(Throwable t) {
			logger.error(Util.join("Excepion:",this.getClass().getSimpleName(),".getNetstatCheckList"), t);
		}

	}

	public void initNetstatCheckList() {
	 
		logger.info("netstatCmd:" + netstatCmd);
		logger.info("useNetstat:" + useNetstat);
		logger.info("netstatCheckDelay:" + netstatCheckDelay);
		logger.info("netstatCheckListDelay:" + netstatCheckListDelay);
		
		if("Y".equalsIgnoreCase(useNetstat)) {
			ComMessage<List<Map<String, String>>, ?> msg = new ComMessage();
			msg.setId(UUID.randomUUID().toString());
			msg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			msg.setUserId(agentNm);
			msg.setCheckSession(false);
	
			Extension ext = new Extension();
			ext.setMsgType(Extension.MSG_TYPE_REQ);
			ext.setServiceCd(ServiceCodeConstant.WS0044);
			Map params = new HashMap();
			params.put("agentNm", agentNm);
	
	
			ext.setParams(params);
			msg.setExtension(ext);
	
			this.putQueue(msg);
			logger.info(Util.join("request netstat check list."));
		}
		
	}

	/**
	 * @param request
	 * @return
	 */
	public ComMessage<?, ?> setNetstatCheckList(ComMessage<?, ?> request) {

		netstatService.setCheckList(request.getResponseObject());
		hasNetStataCheckList = true;
		return null;
	}



//	public ComMessage agentUpdate(ComMessage request) {
//		String serviceCd =request.getExtension().getServiceCd();
//		Object obj   = request.getRequestObject();
//
//		ComMessage msg = request;
//		msg.setCheckSession(false);
//		msg.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//
//		Extension ext = new Extension();
//		ext.setMsgType(Extension.MSG_TYPE_RES);
//		ext.setServiceCd(ServiceCodeConstant.WS0040);
//		msg.setExtension(ext);
//
//		return msg;
//	}
}