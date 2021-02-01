package pep.per.mint.database.service.su;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.chat.ChatMessage;
import pep.per.mint.common.data.basic.chat.ReceiverInfo;
import pep.per.mint.database.mapper.su.ChatMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 12. 18..
 */
@Service
public class ChatService {

    @Autowired
    ChatMapper chatMapper;


    public List<ChatMessage> getSendMsgList(Map params) throws Exception{

        return chatMapper.getSendMsgList(params);

    }

    @Transactional
    public int insertChatMsg(ChatMessage chatMessage) throws Exception{
        int res = chatMapper.insertChatMsg(chatMessage);
        List<ReceiverInfo> receiverInfos = chatMessage.getReceiverInfoList();
        for (ReceiverInfo receiverInfo : receiverInfos){
            receiverInfo.setMsgId(chatMessage.getMsgId());
            res = chatMapper.insertReceiverInfo(receiverInfo);
        }
        return res;
    }

    @Transactional
    public int completeChatMsg(ChatMessage chatMessage) throws Exception{
        int res = chatMapper.completeChatMsg(chatMessage);
        List<ReceiverInfo> receiverInfos = chatMessage.getReceiverInfoList();
        for (ReceiverInfo receiverInfo : receiverInfos){
            res = chatMapper.updateReceiverInfo(receiverInfo);
        }
        return res;
    }

    public int deleteChatMsg(ChatMessage chatMessage) throws Exception{
        int res = chatMapper.deleteChatMsg(chatMessage);
        return res;
    }

    public int insertReceiverInfo(ReceiverInfo receiverInfo)throws Exception{
        int res = chatMapper.insertReceiverInfo(receiverInfo);
        return res;
    }

    public int updateReceiverInfo(ReceiverInfo receiverInfo)throws Exception{
        return chatMapper.updateReceiverInfo(receiverInfo);
    }

}
