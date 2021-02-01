package pep.per.mint.websocket.handler;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

public class PrototypeHandler extends TextWebSocketHandler {

	Logger logger = LoggerFactory.getLogger(PrototypeHandler.class);


	static Map<String, WebSocketSession> sessionMap = new LinkedHashMap<String, WebSocketSession>();


	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

		User user = null;
        String userId = "";
        Object userObj = session.getAttributes().get("user");
    	if(userObj != null) {
    		user = (User)userObj;
    		userId = user.getUserId();
    		logger.info("userId:" + userId);
    	}


		String str = message.getPayload();
		logger.info("receive msg from client:" + str);
    	session.sendMessage(new TextMessage("echo msg from server(userId in server session:" + userId + "):" + str));

    	for(int i = 0 ; i < Integer.MAX_VALUE ; i ++) {

    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		session.sendMessage(new TextMessage("repeat to send data(userId in session:" + userId + "):" + new Date().toString()));

    	}



    }


	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        User user = null;
        String userId = "";
        Object userObj = session.getAttributes().get("user");
    	if(userObj != null) {
    		user = (User)userObj;
    		userId = user.getUserId();

    		//기존것을 닫는다.(메모리 정리)
    		if (sessionMap.containsKey(userId)) {
                try{sessionMap.remove(userId).close();}catch(Exception e) {}
            }

    		logger.debug(Util.join("user(",userId,") logout to websocket channel(",this.getClass(),")" ));
    	}

        logger.debug("close session:"+ session.toString());
        logger.debug("session.isOpen:"+ session.isOpen());

    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        logger.debug("***************************************************************************************");
        logger.debug("** afterConnectionEstablished ");
        logger.debug("** thread:" + Thread.currentThread().getName());
        logger.debug("** myHandler:" + this.hashCode());
        logger.debug("***************************************************************************************");
        logger.debug("session.getAttributes():" + session.getAttributes());
        User user = null;
        String userId = "";
        Object userObj = session.getAttributes().get("user");

        logger.debug("user:" + Util.toJSONString(userObj));


    	if(userObj != null) {
    		user = (User)userObj;
    		userId = user.getUserId();

    		//기존것을 닫는다.(메모리 정리)
    		if (sessionMap.containsKey(userId)) {
                try{sessionMap.remove(userId).close();}catch(Exception e) {}
            }
    		sessionMap.put(userId, session);
    		logger.debug(Util.join("user(",userId,") login to websocket channel(",this.getClass(),")" ));

    	}else {
    		//이부분은 세션을 체크할 것인지 실제 핸들러 구현할때 결정하여 예외 또는 다른 방법으로 추가 처리하는 것으로
    		//throw new Exception(Util.join("Websocket Handler:", this.getClass(), " not found user session"));
    	}

        logger.debug("session.getAcceptedProtocol():" + session.getAcceptedProtocol());
        logger.debug("session.getHandshakeHeaders():"+ session.getHandshakeHeaders());

        logger.debug("session.getLocalAddress():" + session.getLocalAddress().toString());
        logger.debug("session.getRemoteAddress():" + session.getRemoteAddress().toString());
        logger.debug("session.getUri():"+ session.getUri().toString());
        logger.debug("***************************************************************************************");

        logger.debug("open session:"+ session.toString());
        logger.debug("session.isOpen:"+ session.isOpen());

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	logger.info("web socket error!");
    	exception.printStackTrace();
    }

    @Override
    public boolean supportsPartialMessages() {
        logger.info("call method!");
        return super.supportsPartialMessages();
    }


}