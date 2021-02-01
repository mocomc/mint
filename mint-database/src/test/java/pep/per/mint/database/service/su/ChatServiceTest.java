package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.chat.ChatMessage;
import pep.per.mint.common.data.basic.chat.ReceiverInfo; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 12. 18..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        //"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
        "classpath:/config/database-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class ChatServiceTest {

    @Autowired
    ChatService chatService;


    @Test
    public void testGetChatMsgList(){

        try {
            Map params = new HashMap();
            List<ChatMessage> list = chatService.getSendMsgList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testInsertChatMsg(){

        try {
            ChatMessage chatMsg = new ChatMessage();
            chatMsg.setMsg("hello!!!");
            chatMsg.setSenderId("iip");
            List<ReceiverInfo> receiverInfos = new ArrayList<ReceiverInfo>();
            ReceiverInfo receiverInfo = new ReceiverInfo();
            receiverInfo.setReceiverId("11825");
            receiverInfos.add(receiverInfo);

            receiverInfo = new ReceiverInfo();
            receiverInfo.setReceiverId("11826");
            receiverInfos.add(receiverInfo);


            chatMsg.setReceiverInfoList(receiverInfos);
            int res = chatService.insertChatMsg(chatMsg);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
