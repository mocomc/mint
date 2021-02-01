/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.scheduler;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.Service;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.op.IIPAgentService;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.event.ServiceEvent;
import pep.per.mint.websocket.handler.ServiceRoutingHandler;
import pep.per.mint.websocket.service.AgentChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.scheduler
 * AgentPushScheduler.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 13.
 */
public class AgentPushScheduler {


	Logger logger = LoggerFactory.getLogger(AgentPushScheduler.class);


	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	AgentChannelService agentChannelService;

	@Autowired
	IIPAgentService iipAgentService;

	@Autowired
	ServiceRoutingHandler serviceRoutingHandler;

	@Autowired
	@Qualifier("agentPushThreadPoolTaskScheduler")
	ThreadPoolTaskScheduler agentPushThreadPoolTaskScheduler;

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	@Qualifier("agentPushThreadPoolTaskExecutor")
	Executor agentPushThreadPoolTaskExecutor;

	@PreDestroy
	public void stopScheduler() {
		agentPushThreadPoolTaskScheduler.shutdown();
        logger.info("-------------------------------------------------------------------");
        logger.info("-- agent push scheduler stop " + Thread.currentThread().getName());
        logger.info("-------------------------------------------------------------------");
    }

	@PostConstruct
	public void startScheduler() {

        List<String> serviceCds = envs.getAgentPushServices();

        logger.info("-------------------------------------------------------------------");
        logger.info("-- agent push scheduler started(schedule size:" + serviceCds.size() + ") " + Thread.currentThread().getName() );
        logger.info("-------------------------------------------------------------------");


        if(serviceCds != null && serviceCds.size() > 0) {
        	long initialDelay = 0;
        	for(String serviceCd : serviceCds) {
        		PeriodicTrigger periodicTrigger  = new PeriodicTrigger(envs.getServiceDelay(serviceCd), TimeUnit.MILLISECONDS);
        		periodicTrigger.setInitialDelay(initialDelay);
        		AgentServiceScheduler agentServiceScheduler = new AgentServiceScheduler(serviceCd);
        		agentPushThreadPoolTaskScheduler.schedule(agentServiceScheduler, periodicTrigger);
        		initialDelay = initialDelay + envs.pushScheduleInitDelayDelta;
        	}
        }

        logger.info("-------------------------------------------------------------------");

        startAgentSessionCheckScheduler();
    }



	/**
	 *
	 */
	private void startAgentSessionCheckScheduler() {
		PeriodicTrigger periodicTrigger  = new PeriodicTrigger(envs.agentSessionCheckDelay, TimeUnit.MILLISECONDS);

		agentPushThreadPoolTaskScheduler.schedule(new Runnable() {

			@Override
			public void run() {

				try {

					Map<String, WebSocketSession> sessionMap = agentChannelService.getSessionMap();

					if(sessionMap != null && sessionMap.size() > 0) {


						Iterator<String> iterator = sessionMap.keySet().iterator();
						logger.info(Util.join("startAgentSessionCheckScheduler start(size:",sessionMap.size(), ")"));
						while(iterator.hasNext()) {

							String sessionId = iterator.next();
							WebSocketSession session = sessionMap.get(sessionId);
							IIPAgentInfo agentInfo = (IIPAgentInfo)session.getAttributes().get("agentInfo");

							if(agentInfo != null) {

								IIPAgentLog log = new IIPAgentLog();
								log.setAgentId(agentInfo.getAgentId());

								String getDate = Util.getFormatedDate("yyyyMMddHHmmssSSS");
								log.setGetDate(getDate);
								log.setRegApp(this.getClass().getName());
								log.setRegDate(getDate);

								if(session.isOpen()) {
									log.setStatus(IIPAgentInfo.AGENT_STAT_LOGON);
									log.setMsg("login ok(sc)");
								}else{
									log.setStatus(IIPAgentInfo.AGENT_STAT_ERROR);
									log.setMsg("logoff ok(sc)");
									agentChannelService.logout(session);
								}

								int res = res = iipAgentService.upsertIIPAgentLog(log);

								logger.info(Util.join("login-check-", sessionId,"-",agentInfo.getAgentId() ,"-", log.getStatus()));
							}else {
								logger.info(Util.join("login-check-", sessionId,"-agentInfoNull"));
							}

						}
						logger.info("startAgentSessionCheckScheduler end");

					}

				}catch(Exception e) {
					logger.error("",e);
				}
			}
		}, periodicTrigger);
	}


	class AgentServiceScheduler implements Runnable{

		String serviceCd;

		public AgentServiceScheduler(String serviceCd) {
			super();
			this.serviceCd = serviceCd;
		}

		public void run() {
			try {


				Map<String, WebSocketSession> sessionMap = agentChannelService.getSessionMap();
				if(sessionMap == null || sessionMap.size() == 0) {
					logger.debug("접속한 에이전트가 없어 AgentPushScheduler 실행은 SKIP...");
					return;
				}

				logger.debug(agentChannelService.getAgentChannelInfo());

				Iterator<String> iterator = sessionMap.keySet().iterator();

				while(iterator.hasNext()) {

					String sessionId = iterator.next();
					WebSocketSession session = sessionMap.get(sessionId);

					AgentPushJob job = new AgentPushJob(serviceCd, sessionId, session);
					//Async 처리
					agentPushThreadPoolTaskExecutor.execute(job);
				}

			} catch (Exception e) {
				logger.error("", e);
			}
		}

	}

	class AgentPushJob implements Runnable{

		String serviceCd;
		String sessionId;
		WebSocketSession session;

		public AgentPushJob(String serviceCd, String sessionId, WebSocketSession session) {
			super();
			this.serviceCd = serviceCd;
			this.sessionId = sessionId;
			this.session = session;
		}

		public void run() {
			try {

				Service service = envs.getService(serviceCd);
				Map params = copyParams(service.getParams());


				ComMessage request = new ComMessage();
				Extension extension = new Extension();
				extension.setServiceCd(serviceCd);
				extension.setMsgType(Extension.MSG_TYPE_REQ);

				//String agentNm = sessionId;
				String agentNm = agentChannelService.getAgentNm(sessionId);
				params.put("agentNm", agentNm);
				extension.setParams(params);

				request.setExtension(extension);
				request.setAppId(this.getClass().getName());
				request.setStartTime(Util.getFormatedDate());



				ServiceEvent se = new ServiceEvent<>(request, session, ServiceEvent.TYPE_AGENT);
				Object []routeParams = {se};
				serviceRoutingHandler.route(Util.join(serviceCd,".",Extension.MSG_TYPE_REQ), routeParams);

				logger.debug(Util.join("serviceCd:", serviceCd, ", sessionId:", sessionId, " push service executing..."));

			}catch(Exception e) {
				logger.error(Util.join("Exception2:reouting:serviceCd:", serviceCd), e);
			}
		}

		/**
		 * @param params
		 * @return
		 */
		private Map copyParams(Map params) {
			Map newParam = new HashMap();
			if(params != null) {
				Iterator iterator = params.keySet().iterator();
				while(iterator.hasNext()) {
					String key = (String)iterator.next();
					Object value = params.get(key);
					newParam.put(key, value);
				}
			}
			return newParam;
		}
	}

}
