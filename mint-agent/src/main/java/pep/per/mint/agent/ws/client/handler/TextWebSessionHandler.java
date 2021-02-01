package pep.per.mint.agent.ws.client.handler;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import pep.per.mint.agent.event.ServiceEvent;
import pep.per.mint.agent.event.ServiceListenerImpl;
import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

@Controller
public class TextWebSessionHandler extends TextWebSocketHandler  implements InitializingBean, MintAgentWebSocket
{

	private Logger logger = LoggerFactory.getLogger(TextWebSessionHandler.class);

	IIPAgentInfo agentInfo = null;

	@Autowired
	ServiceListenerImpl serviceListener;

	boolean isConnection = false;

	private static Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();

	MessageHandler  messageHandler  = new MessageHandler();

	private final int AGENT_MESSAGE_SIZE = 2 * 1024 * 1024;

	public TextWebSessionHandler(IIPAgentInfo iipAgentInfo){
		this.agentInfo = iipAgentInfo;
		messageHandler.setDeserializer(new ItemDeserializer());
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		this.isConnection = false;
		sessionSet.remove(session);
		this.logger.info(String.format("remove session ID[%s] status[%d][%s]",session.getId(), status.getCode(), status.getReason()));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		super.afterConnectionEstablished(session);

		this.logger.info(String.format("Connection IIPAgent[%s] [%s] MessageSize[%d]", agentInfo.getAgentNm(), session.getId(),AGENT_MESSAGE_SIZE));
		session.setTextMessageSizeLimit(AGENT_MESSAGE_SIZE);
		session.setBinaryMessageSizeLimit(AGENT_MESSAGE_SIZE);
		this.isConnection = true;

		sessionSet.add(session);

		login(session);
	}

	private void login(WebSocketSession session) throws IOException {
		ComMessage<IIPAgentInfo,?> sendComMsg = new ComMessage();
		sendComMsg.setId(UUID.randomUUID().toString());
		sendComMsg.setUserId(agentInfo.getAgentNm());
		sendComMsg.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

		Extension ext = new Extension();
		ext.setMsgType(Extension.MSG_TYPE_REQ);
		ext.setServiceCd(ServiceCodeConstant.WS0025);
		sendComMsg.setExtension(ext);


		this.logger.info(String.format("login message [%s] ", messageHandler.serialize(sendComMsg)));

		String sendMsg = messageHandler.serialize(sendComMsg);
		session.sendMessage(new TextMessage(sendMsg));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

		this.logger.info("receive message:"+((TextMessage)message).getPayload());
		ComMessage<?,?> response  = null;
		try{
			response = messageHandler.deserialize(((TextMessage)message).getPayload());
		} catch(Exception  e){
			throw new AgentException("Exception:messageHandler.deserialize(message.getPayload())", e);
		}
		this.logger.info(Util.join("MSGTYPE[",response.getExtension().getMsgType()+"] SERVICECD[",response.getExtension().getServiceCd(),"]" ));

		Extension extension = response.getExtension();
		if (extension == null) {
			throw new AgentException("Exception:ComMessage must have Extension field value.");
		}
		String serviceCd = extension.getServiceCd();
		if (Util.isEmpty(serviceCd)) {
			throw new AgentException("Exception:ComMessage must have Extension.serviceCd field value.");
		}

		String msgType = extension.getMsgType();
		if (Util.isEmpty(msgType)) {
			throw new AgentException("Exception:ComMessage must have Extension.msgType field value.");
		}

		try {
			ServiceEvent se = new ServiceEvent();
			se.setSession(session);
			se.setRequest(response);
			se.setType(extension.getMsgType());
			se.setServiceCd(extension.getServiceCd());
			serviceListener.requestService(se);
		} catch (Exception e) {
			logger.error("The exception is throwed on doing business:", e);
		}
	}


	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		this.logger.error("web socket error!", exception);
	}

	@Override
	public boolean supportsPartialMessages() {
		this.logger.info("call method!  supportsPartialMessages["+super.supportsPartialMessages()+"");
		return super.supportsPartialMessages();
	}



	public void sendMessage (String message){
		for (WebSocketSession session: this.sessionSet){
			if (session.isOpen()){
				synchronized (session) {
					try{
						this.logger.info("send message:"+message);
						session.sendMessage(new TextMessage(message));
					}catch (Exception ignored){
						this.logger.error("fail to send message!", ignored);
					}
				}
			}
		}
	}

	public void sendComMessage (ComMessage<?, ?> message){
		for (WebSocketSession session: this.sessionSet){
			if (session.isOpen()){
				synchronized (session) {
					try{
						this.logger.info("send message:"+Util.toJSONPrettyString(message));
						String msg = messageHandler.serialize(message);
						session.sendMessage(new TextMessage(msg));
					}catch (Exception ignored){
						this.logger.error("fail to send message!", ignored);
					}
				}
			}
		}
	}

//	public boolean isConnecion(){
//		return isConnection;
//	}

	public void close(){
		for (WebSocketSession session: this.sessionSet){
			if (session.isOpen()){
				synchronized (session) {
					try{
						session.close();
					}catch (Exception ignored){
						this.logger.error("fail to session close!", ignored);
					}
				}
			}
		}
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean isConnection() {
		return isConnection;
	}
}
