package pep.per.mint.websocket.scheduler;

import java.util.Date;
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

import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.Service;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.handler.ServiceRoutingHandler;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 *
 * <pre>
 * pep.per.mint.websocket.scheduler
 * DashboardScheduler.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
public class DashboardScheduler {

	Logger logger = LoggerFactory.getLogger(DashboardScheduler.class);

	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	FrontChannelService frontChannelService;

	@Autowired
	ServiceRoutingHandler serviceRoutingHandler;

	@Autowired
	@Qualifier("pushThreadPoolTaskScheduler")
	ThreadPoolTaskScheduler pushThreadPoolTaskScheduler;

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	@Qualifier("pushThreadPoolTaskExecutor")
	Executor pushThreadPoolTaskExecutor;

	@PreDestroy
	public void stopScheduler() {
        pushThreadPoolTaskScheduler.shutdown();
        logger.info("-------------------------------------------------------------------");
        logger.info("-- service scheduler stop " + Thread.currentThread().getName());
        logger.info("-------------------------------------------------------------------");
    }

	@PostConstruct
	public void startScheduler() {

        List<String> serviceCds = envs.getFrontPushServices();

        logger.info("-------------------------------------------------------------------");
        logger.info("-- service scheduler started(schedule size:" + serviceCds.size() + ") " + Thread.currentThread().getName() );
        logger.info("-------------------------------------------------------------------");


        if(serviceCds != null && serviceCds.size() > 0) {
        	long initialDelay = 0;
        	for(String serviceCd : serviceCds) {

        		PeriodicTrigger periodicTrigger  = new PeriodicTrigger(envs.getServiceDelay(serviceCd), TimeUnit.MILLISECONDS);
        		periodicTrigger.setInitialDelay(initialDelay);
        		DashboardPushScheduler dashboardPushScheduler = new DashboardPushScheduler(serviceCd);
        		pushThreadPoolTaskScheduler.schedule(dashboardPushScheduler, periodicTrigger);
        		initialDelay = initialDelay + envs.pushScheduleInitDelayDelta;
        		logger.info("-- " + envs.getServiceInfo(serviceCd));
        	}
        }
        logger.info("-------------------------------------------------------------------");
    }

	class DashboardPushScheduler implements Runnable{

		String serviceCd;

		public DashboardPushScheduler(String serviceCd) {
			super();
			this.serviceCd = serviceCd;
		}



		public void run() {
			try {

				Map<String, List<String>> accessRoleMap = frontChannelService.getAccessRoleMap(serviceCd);

				if(accessRoleMap == null || accessRoleMap.size() == 0) return;

				Service service = envs.getService(serviceCd);
				Map params = copyParams(service.getParams());



				Iterator<String> keys = accessRoleMap.keySet().iterator();
				while(keys.hasNext()) {
					String roleKey = keys.next();
					List<String> userSessionList = accessRoleMap.get(roleKey);
					if(userSessionList != null && userSessionList.size() > 0) {

						DashboardPushJob job = new DashboardPushJob(serviceCd, userSessionList,  roleKey, params);
						if(!envs.pushServiceParallelOn) {
							//동기 처리
							job.run();

							//동기 테스트
							//System.out.println("서비스" + serviceCd + "동기처리 수행:" + new Date());
							//Thread.sleep(Integer.MAX_VALUE);
						}else {
							//비동기 처리
							pushThreadPoolTaskExecutor.execute(job);

						}
					}
				}
			} catch (Exception e) {
				logger.error("", e);
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

	class DashboardPushJob implements Runnable{

		String serviceCd;
		List<String> userSessionList;
		String roleKey;
		Map params;



		public DashboardPushJob(String serviceCd, List<String> userSessionList, String roleKey, Map params) {
			super();
			this.serviceCd = serviceCd;
			this.userSessionList = userSessionList;
			this.roleKey = roleKey;
			this.params = params;
		}



		public void run() {
			try {
				String userId = frontChannelService.getUserId(userSessionList.get(0));
				params.put("userId", userId);
				Object []routeParams = {roleKey, userSessionList, params};
				serviceRoutingHandler.route(Util.join(serviceCd,".",Extension.MSG_TYPE_PUSH), routeParams);

			}catch(Exception e) {
				logger.error(Util.join("Exception2:reouting:serviceCd:", serviceCd), e);
			}
		}

	}

}
