package pep.per.mint.agent.event;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;


import pep.per.mint.agent.AgentProperties;
import pep.per.mint.agent.controller.AgentController;
import pep.per.mint.agent.controller.MQObjectMonitorController;
import pep.per.mint.agent.ws.client.handler.TextWebSessionHandler;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;

@Component
@Scope("prototype")
public class SpringQueueListenerImpl extends  AbstractQueueListener {

	private Logger logger = LoggerFactory.getLogger(SpringQueueListenerImpl.class);


	public SpringQueueListenerImpl(LinkedBlockingQueue<ComMessage<?, ?>> _lbq, IIPAgentInfo _agentInfo,
			TextWebSessionHandler sessionHandler) {
		super(_lbq, _agentInfo, sessionHandler);
	}


	protected void login(IIPAgentInfo req) {

		logger.debug("login URL ["+URL +"]");
		int initTryCnt = 0;

		do {
			try{

				if(initTryCnt > 0){
					logger.debug(Util.join("IIPAgent WebSocket.login try ", initTryCnt, " times..." ,URL));
				}
				initTryCnt ++;

				if(manager != null){
					manager.stop();
					manager = null;
				}

				mqObjectMonitorController.clear();

				WebSocketClient client = new StandardWebSocketClient();


				manager = new WebSocketConnectionManager(client, ((TextWebSessionHandler)sessionHandler), URL);
				manager.setAutoStartup(true);
				manager.start();

				if(!sessionHandler.isConnection()) Thread.sleep(initDelaySec);

			}catch(Exception e){
				logger.error("QueueListenerImpl.init error:getAgentInfo", e);
				try {
					Thread.sleep(initDelaySec);
				} catch (InterruptedException e1) {
				}
			}
		}while(!sessionHandler.isConnection());

		clearQueue(lbq);
	}



	public void setStopRun(){
		logger.info(this.getClass().getSimpleName() + " setStopRun...");

		isRun = false; // Thread 종료
		if(manager != null){
			manager.stop();
			manager = null;
		}

		if(sessionHandler != null){
			((TextWebSessionHandler)sessionHandler).close();
		}

		mqObjectMonitorController.clear();
	}

}
