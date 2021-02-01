package pep.per.mint.agent.event;


import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import pep.per.mint.agent.ws.client.handler.OpenWebSocketClientHandler;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.util.Util;

@Component
@Scope("prototype")
public class OpenQueueListenerImpl extends  AbstractQueueListener {


	public OpenQueueListenerImpl(LinkedBlockingQueue<ComMessage<?, ?>> _lbq, IIPAgentInfo _agentInfo,
			OpenWebSocketClientHandler sessionHandler) {
		super(_lbq, _agentInfo, sessionHandler);
	}
	private Logger logger = LoggerFactory.getLogger(OpenQueueListenerImpl.class);


	protected void login(IIPAgentInfo req) {

		logger.debug("login URL ["+URL +"]");
		int initTryCnt = 0;

		do {
			try{

				if(initTryCnt > 0){
					logger.debug(Util.join("IIPAgent WebSocket.login try ", initTryCnt, " times..." ,URL));
				}
				initTryCnt ++;

				if(sessionHandler != null){
					((OpenWebSocketClientHandler)sessionHandler).close();
				}

				mqObjectMonitorController.clear();

				((OpenWebSocketClientHandler)sessionHandler).connect(URL);

				if(!sessionHandler.isConnection()) Thread.sleep(initDelaySec);

			}catch(Exception e){
				logger.error(this.getClass().getSimpleName()+"init error:getAgentInfo", e);
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
			((OpenWebSocketClientHandler)sessionHandler).close();
		}

		mqObjectMonitorController.clear();
	}




}
