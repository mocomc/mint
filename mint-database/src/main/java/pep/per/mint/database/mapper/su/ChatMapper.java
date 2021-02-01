package pep.per.mint.database.mapper.su;

import pep.per.mint.common.data.basic.chat.ChatMessage;
import pep.per.mint.common.data.basic.chat.ReceiverInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 12. 18..
 */
public interface ChatMapper {

    public List<ChatMessage> getSendMsgList(Map params) throws Exception;

    public int insertChatMsg(ChatMessage chatMessage) throws Exception;

    public int completeChatMsg(ChatMessage chatMessage) throws Exception;

    public int deleteChatMsg(ChatMessage chatMessage) throws Exception;

    public int insertReceiverInfo(ReceiverInfo receiverInfo)throws Exception;

    public int updateReceiverInfo(ReceiverInfo receiverInfo)throws Exception;
}
