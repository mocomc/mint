package pep.per.mint.database.service.su;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 
import pep.per.mint.common.data.basic.websocket.WebsocketChannel;
import pep.per.mint.common.data.basic.websocket.WebsocketMessage;
import pep.per.mint.common.data.basic.websocket.WebsocketUser;
import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class AsyncMessageServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AsyncMessageServiceTest.class);

	@Autowired
	AsyncMessageService service;

	@Test
	public void testRegisterUser(){
		try{ 
            String channelId = "default-channel";
            WebsocketUser user = new WebsocketUser();
            user.setLoginDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
            user.setLoginYn("Y");
            user.setUserId("iip");
            user.setChannelId(channelId);
            user.setSessionId(UUID.randomUUID().toString());
            WebsocketChannel channel = new WebsocketChannel();
            channel.setChannelId("default-channel");
            channel.setChannelName("기본메시지채널");
            channel.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			service.registerUser(channel, user);
			
		}catch(Exception e){
			logger.error("",e);
		}
	}
    
    @Test
    public void testPupMessage(){
        try{
            
            WebsocketChannel channel = new WebsocketChannel();
            channel.setChannelId("default-channel");
            channel.setChannelName("기본메시지채널");
            
            WebsocketUser sender = new WebsocketUser();
            sender.setLoginDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
            sender.setLoginYn("Y");
            sender.setUserId("iip");
            sender.setSessionId(UUID.randomUUID().toString());
            
            
            List<WebsocketUser> receivers = new ArrayList<WebsocketUser>();
            WebsocketUser receiver1 = new WebsocketUser();
            receiver1.setUserId("iip"); 
            receivers.add(receiver1);
            WebsocketUser receiver2 = new WebsocketUser();
            receiver2.setUserId("iipdev"); 
            receivers.add(receiver2);
            
            WebsocketMessage msg = new WebsocketMessage();
            msg.setChannel(channel);
            msg.setMessage("가나다라마바사1234567890ertyyyutrewkrtjkj");
            msg.setReceivers(receivers);
            msg.setSender(sender);
            msg.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
            
            service.pubMessage(msg);
            
			
		}catch(Exception e){
			logger.error("",e);
		}
    }
    
    
    @Test
    public void testSubMessage(){
        try{
            
            List<WebsocketMessage> msgs = service.subMessage("default-channel");
            for (WebsocketMessage msg : msgs) {
              
                String sndDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
                String messageId = msg.getMessageId();
                String channelId = msg.getChannel().getChannelId();
                List<WebsocketUser> receivers = msg.getReceivers();
                for (WebsocketUser receiver : receivers) {
                    String receiverId = receiver.getUserId();
                    Map params = new HashMap();
                    params.put("messageId",messageId);
                    params.put("channelId",channelId);
                    params.put("senderId",msg.getSender().getUserId());
                    params.put("receiverId",receiverId);
                    params.put("sndYn","Y");
                    params.put("sndDate", sndDate);
                    params.put("resultCd","0");
                    params.put("errorMsg", "success");
                    
                    logger.info(Util.join("sending sub msg:" , Util.toJSONString(msg)));
                 
                    
                    service.updateSendMsgResult(params);
                }
                
            }
        
            
            
			
		}catch(Exception e){
			logger.error("",e);
		}
    }
    
}
