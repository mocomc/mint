package pep.per.mint.agent.ws.client.handler;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pep.per.mint.agent.event.ServiceListenerImpl;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;

@Controller
public class OpenWebSocketClientHandler  implements MintAgentWebSocket {

	private Logger logger = LoggerFactory.getLogger(OpenWebSocketClientHandler.class);

	IIPAgentInfo agentInfo = null;

	@Autowired
	ServiceListenerImpl serviceListener;

	boolean isConnection = false;

	OpenWebSocketClient client = null;

	MessageHandler  messageHandler  = new MessageHandler();

	public OpenWebSocketClientHandler(IIPAgentInfo iipAgentInfo) {
		this.agentInfo = iipAgentInfo;
	}


	public void connect(String URI) throws URISyntaxException{
		client = new OpenWebSocketClient(new URI(URI), agentInfo, serviceListener);
		//client.setConnectionLostTimeout(0);
		//For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection]
		client.connect();

	}


	public void sendMessage(String sendMsg){
		client.send(sendMsg);
	}

	public void sendComMessage (ComMessage<?, ?> message){
		if(client.isOpen()){
			try{
				this.logger.info("send message:"+Util.toJSONPrettyString(message));
				String msg = messageHandler.serialize(message);
				client.send(msg);
			}catch (Exception ignored){
				this.logger.error("fail to send message!", ignored);
			}
		}
	}

	public void close(){
		if(client != null){
			client.close();
		}
	}



	@Override
	public boolean isConnection() {
		if(client != null){
			return client.isConnection();
		}else{
			return false;
		}
	}
}


