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
import pep.per.mint.agent.ws.client.handler.OpenWebSocketClientHandler;
import pep.per.mint.agent.ws.client.handler.TextWebSessionHandler;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;

@Component
@Scope("prototype")
public class QueueListenerImpl extends  Thread {


	private Logger logger = LoggerFactory.getLogger(QueueListenerImpl.class);


	LinkedBlockingQueue<ComMessage<?,?>> lbq = null;

	IIPAgentInfo agentInfo = null;

	boolean isRun = true;

	static String agentHome;

	static AgentProperties properties = new AgentProperties();


	String URL = "";

	@Autowired
	AgentController agentController;

	@Autowired
	MQObjectMonitorController mqObjectMonitorController;

	/**
	 *
	 */
	private static int initDelaySec = 30000;

	private static int getQueueSec = 5000;


	public QueueListenerImpl(LinkedBlockingQueue<ComMessage<?,?>> _lbq, IIPAgentInfo _agentInfo, AgentController agentController) {
		this.lbq = _lbq;
		this.agentInfo = _agentInfo;
		this.agentController = agentController ;
		messageHandler.setDeserializer(new ItemDeserializer());
	}


	public void setUrl(String uri) throws Throwable{
		URL = uri;
	}

	@Override
	public void run() {

//		while(isRun){
//			try {
//				if (sessionHandler != null && sessionHandler.isConnecion()){
//					ComMessage<?,?>  msgRes = (ComMessage<?,?>)lbq.poll(getQueueSec, TimeUnit.MILLISECONDS);
//					if(msgRes != null){
//						sessionHandler.sendComMessage(msgRes);
//						Thread.sleep(100);
//					}
//				}else{
//					clearQueue(lbq);
//					login(this.agentInfo);
//				}
//			} catch (Exception e) {
//				logger.warn("BlockingQueue 처리 오류", e );
//			}
//		}

		while(isRun){
			try {
				if (sessionHandler2 != null && sessionHandler2.isConnection()){
					ComMessage<?,?>  msgRes = (ComMessage<?,?>)lbq.poll(getQueueSec, TimeUnit.MILLISECONDS);
					if(msgRes != null){
						sessionHandler2.sendComMessage(msgRes);
						Thread.sleep(1);
					}
				}else{
					clearQueue(lbq);
					login2(this.agentInfo);
				}
			} catch (Exception e) {
				logger.warn("BlockingQueue 처리 오류", e );
			}
		}
	}

	private  synchronized void clearQueue(LinkedBlockingQueue<ComMessage<?, ?>> lbq2) {
		if(lbq != null){
			logger.info(Util.join("[",lbq.size(),"]ea Messsage Clear start!!"));
			lbq.clear();
			logger.info(Util.join("[",lbq.size(),"]ea Messsage Clear end!! "));
		}
	}
//	@Autowired
//	private ApplicationContext _applicationContext;
	@Autowired
	TextWebSessionHandler sessionHandler  = null;

	MessageHandler  messageHandler  = new MessageHandler();

	WebSocketConnectionManager manager = null;

	@Autowired
	OpenWebSocketClientHandler sessionHandler2  = null;

	private void login(IIPAgentInfo req) {

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


				manager = new WebSocketConnectionManager(client, sessionHandler, URL);
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



	private void login2(IIPAgentInfo req) {

		logger.debug("login URL ["+URL +"]");
		int initTryCnt = 0;

		do {
			try{

				if(initTryCnt > 0){
					logger.debug(Util.join("IIPAgent WebSocket.login try ", initTryCnt, " times..." ,URL));
				}
				initTryCnt ++;

				if(sessionHandler2 != null){
					sessionHandler2.close();
				}

				mqObjectMonitorController.clear();

				sessionHandler2.connect(URL);

				if(!sessionHandler2.isConnection()) Thread.sleep(initDelaySec);

			}catch(Exception e){
				logger.error("QueueListenerImpl.init error:getAgentInfo", e);
				try {
					Thread.sleep(initDelaySec);
				} catch (InterruptedException e1) {
				}
			}
		}while(!sessionHandler2.isConnection());

		//clearQueue(lbq);
	}

	public void setStopRun(){
		logger.info(this.getClass().getSimpleName() + " setStopRun...");

		isRun = false; // Thread 종료
		if(manager != null){
			manager.stop();
			manager = null;
		}

		if(sessionHandler != null){
			sessionHandler.close();
		}

		if(sessionHandler2 != null){
			sessionHandler2.close();
		}


		mqObjectMonitorController.clear();
	}




}
