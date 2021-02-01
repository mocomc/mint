package pep.per.mint.agent.ws.client.handler;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.agent.event.ServiceEvent;
import pep.per.mint.agent.event.ServiceListenerImpl;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.ItemDeserializer;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

public class OpenWebSocketClient extends WebSocketClient {

	private Logger logger = LoggerFactory.getLogger(OpenWebSocketClient.class);

	IIPAgentInfo agentInfo = null;

	ServiceListenerImpl serviceListener;

	boolean isConnection = false;


	MessageHandler  messageHandler  = new MessageHandler();


	public OpenWebSocketClient(URI serverUri, IIPAgentInfo iipAgentInfo,ServiceListenerImpl _serviceListener) {
		super(serverUri);
		this.agentInfo = iipAgentInfo;
		this.serviceListener = _serviceListener;
		messageHandler.setDeserializer(new ItemDeserializer());
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		isConnection = true;
		try {
			login();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void login() throws IOException {
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
		send(sendMsg);
	}

	@Override
	public void onMessage(String message)  {
		this.logger.info("receive message:"+(message));
		ComMessage<?,?> response  = null;
		try{
			response = messageHandler.deserialize(message);
		} catch(Exception  e){
//			throw new AgentException("Exception:messageHandler.deserialize(message.getPayload())", e);
		}
		this.logger.info(Util.join("MSGTYPE[",response.getExtension().getMsgType()+"] SERVICECD[",response.getExtension().getServiceCd(),"]" ));

		Extension extension = response.getExtension();
		if (extension == null) {
//			throw new AgentException("Exception:ComMessage must have Extension field value.");
		}
		String serviceCd = extension.getServiceCd();
		if (Util.isEmpty(serviceCd)) {
//			throw new AgentException("Exception:ComMessage must have Extension.serviceCd field value.");
		}

		String msgType = extension.getMsgType();
		if (Util.isEmpty(msgType)) {
//			throw new AgentException("Exception:ComMessage must have Extension.msgType field value.");
		}

		try {
			ServiceEvent se = new ServiceEvent();
//			se.setSession(session);
			se.setRequest(response);
			se.setType(extension.getMsgType());
			se.setServiceCd(extension.getServiceCd());
			serviceListener.requestService(se);
		} catch (Exception e) {
			logger.error("The exception is throwed on doing business:", e);
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		isConnection = false;
		this.logger.info(String.format("remove session status[%d][%s]",code, reason));
	}

	@Override
	public void onError(Exception ex) {
		isConnection = false;
	}


	public boolean isConnection(){
		return isConnection;
	}

}
