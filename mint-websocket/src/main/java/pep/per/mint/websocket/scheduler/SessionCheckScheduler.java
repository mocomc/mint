/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.scheduler;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.scheduler
 * SessionCheckScheduler.java
 * </pre>
 * @author whoana
 * @date 2018. 9. 12.
 */
public class SessionCheckScheduler {


	Logger logger = LoggerFactory.getLogger(SessionCheckScheduler.class);


	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	FrontChannelService frontChannelService;


	@Autowired
	@Qualifier("pushThreadPoolTaskScheduler")
	ThreadPoolTaskScheduler pushThreadPoolTaskScheduler;

	@PostConstruct
	public void startFrontSessionCheckScheduler() {
		PeriodicTrigger periodicTrigger  = new PeriodicTrigger(envs.frontSessionCheckDelay, TimeUnit.MILLISECONDS);

		pushThreadPoolTaskScheduler.schedule(new Runnable() {

			@Override
			public void run() {

				try {

					Map<String, WebSocketSession> sessionMap = frontChannelService.getSessionMap();

					if(sessionMap != null && sessionMap.size() > 0) {


						Iterator<String> iterator = sessionMap.keySet().iterator();
						logger.info(Util.join("startFronSessionCheckScheduler start(size:",sessionMap.size(), ")"));
						logger.info("-------------------------------------------------------");
						while(iterator.hasNext()) {

							String sessionId = iterator.next();
							WebSocketSession session = sessionMap.get(sessionId);

							try {
								frontChannelService.sendMessage(sessionId, "ping");//비정상 세션일 경우 여기서 예외떨어지지 않고 close  이벤트 발생함.
								logger.info(Util.join("startFrontSessionCheckScheduler:" , sessionId , ":alive"));
							}catch(Exception e) {
								e.printStackTrace();
								logger.info(Util.join("startFrontSessionCheckScheduler:" , sessionId , ":zombi"), e);
								frontChannelService.logout(session);
							}

							/* isOpen은 체크 안됨
							if(session.isOpen()) {
								logger.info("***startFrontSessionCheckScheduler:" + sessionId +":alive");
							}else {
								frontChannelService.logout(session);
							}*/
						}
						logger.info("startFrontSessionCheckScheduler end");

					}

				}catch(Exception e) {
					logger.error("",e);
				}
			}
		}, periodicTrigger);
	}

}
