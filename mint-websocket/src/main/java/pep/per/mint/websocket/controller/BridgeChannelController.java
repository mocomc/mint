/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.controller;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.AgentChannelService;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.controller
 * BridgeChannelController.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 1.
 */
@ServiceRouter
@Controller
public class BridgeChannelController {

	Logger logger = LoggerFactory.getLogger(BridgeChannelController.class);

	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	AgentChannelService agentChannelService;

	@Autowired
	FrontChannelService frontChannelService;

	@Autowired
	@Qualifier("pushThreadPoolTaskExecutor")
	Executor pushThreadPoolTaskExecutor;

	@Autowired
	MessageHandler messageHandler;

	


}
