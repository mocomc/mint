package pep.per.mint.agent.event;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;


import pep.per.mint.agent.AgentProperties;
import pep.per.mint.agent.controller.AgentController;
import pep.per.mint.agent.controller.MQObjectMonitorController;
import pep.per.mint.agent.ws.client.handler.MintAgentWebSocket;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;


public abstract class AbstractQueueListener extends  Thread {


	private Logger logger = LoggerFactory.getLogger(AbstractQueueListener.class);


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

	@Autowired
	MintAgentWebSocket sessionHandler  = null;

	/**
	 *
	 */
	static int initDelaySec = 30000;

	static int getQueueSec = 5000;


	public AbstractQueueListener(LinkedBlockingQueue<ComMessage<?,?>> _lbq, IIPAgentInfo _agentInfo, MintAgentWebSocket sessionHandler) {
		this.lbq = _lbq;
		this.agentInfo = _agentInfo;
		sessionHandler = sessionHandler ;
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
				if (sessionHandler != null && sessionHandler.isConnection()){
					ComMessage<?,?>  msgRes = (ComMessage<?,?>)lbq.poll(getQueueSec, TimeUnit.MILLISECONDS);
					if(msgRes != null){
						sessionHandler.sendComMessage(msgRes);
						Thread.sleep(100);
					}
				}else{
					clearQueue(lbq);
					login(this.agentInfo);
				}
			} catch (Exception e) {
				logger.warn("BlockingQueue 처리 오류", e );
			}
		}
	}

	public  synchronized void clearQueue(LinkedBlockingQueue<ComMessage<?, ?>> lbq2) {
		if(lbq != null){
			logger.info(Util.join("[",lbq.size(),"]ea Messsage Clear start!!"));
			lbq.clear();
			logger.info(Util.join("[",lbq.size(),"]ea Messsage Clear end!! "));
		}
	}



	MessageHandler  messageHandler  = new MessageHandler();

	WebSocketConnectionManager manager = null;

	protected abstract void login (IIPAgentInfo req);

	public abstract void setStopRun();


}
