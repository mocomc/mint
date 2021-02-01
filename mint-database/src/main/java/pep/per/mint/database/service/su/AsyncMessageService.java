/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.database.service.su;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.websocket.WebsocketChannel;
import pep.per.mint.common.data.basic.websocket.WebsocketMessage;
import pep.per.mint.common.data.basic.websocket.WebsocketUser;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.AsyncMessageMapper;

/**
 *
 * @author whoana
 */
@Service
public class AsyncMessageService {
    
    @Autowired
    AsyncMessageMapper asyncMessageMapper;
    
    @Transactional
    public void registerUser(WebsocketChannel channel, WebsocketUser user)throws Exception{
        int res = -1;
        WebsocketChannel selectChannel = asyncMessageMapper.getChannnel(channel.getChannelId());
        if (Util.isEmpty(selectChannel)) {
            res = asyncMessageMapper.insertChannel(channel);
        }
        Map params = new HashMap();
        params.put("userId", user.getUserId());
        params.put("channelId", user.getChannelId());
        WebsocketUser selectUser = asyncMessageMapper.getWebsocketUser(params);
        if (Util.isEmpty(selectUser)) {
            res = asyncMessageMapper.insertWebsocketUser(user);
        } else {
            res = asyncMessageMapper.updateWebsocketUser(user);
        }
    }
     
    @Transactional
    public void pubMessage(WebsocketMessage msg)throws Exception{
        int res = asyncMessageMapper.insertMessage(msg);
        List<WebsocketUser> receivers = msg.getReceivers();
        for (WebsocketUser receiver : receivers) {
            Map params = new HashMap();
            params.put("channelId", msg.getChannel().getChannelId());
            params.put("messageId", msg.getMessageId());
            params.put("senderId", msg.getSender().getUserId());
            params.put("receiverId", receiver.getUserId());
            res = asyncMessageMapper.insertSendMsgResult(params);
        }
    }
  
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<WebsocketMessage> subMessage(String channelId) throws Exception {
        Map params = new HashMap();
        params.put("channelId", channelId);
        params.put("sndDate", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
        params.put("sndYn", "P");
        int res = asyncMessageMapper.updateSendMsgProcessing(params);
        return asyncMessageMapper.getMessageList(params);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int updateSendMsgResult(Map params)throws Exception{
        return asyncMessageMapper.updateSendMsgResult(params);
    }
     
//    public int insertWebsocketUser(WebsocketUser user) throws Exception{
//        return asyncMessageMapper.insertWebsocketUser(user);
//    }
//    
//    public int updateWebsocketUser(WebsocketUser user) throws Exception{
//        return asyncMessageMapper.updateWebsocketUser(user);
//    }
//    
//    public WebsocketChannel getChannnel(String channelId) throws Exception{
//        return asyncMessageMapper.getChannnel(channelId);
//    }
//    
//    public int insertChannel(WebsocketChannel channel) throws Exception{
//        return asyncMessageMapper.insertChannel(channel);
//    }
//    
//    public int insertChannelUserMap(Map params)throws Exception{
//        return asyncMessageMapper.insertChannelUserMap(params);
//    }
//    
//    public int insertMessage(WebsocketMessage msg)throws Exception{
//        return asyncMessageMapper.insertMessage(msg);
//    }
//    
//    public int insertSendMsgResult(Map params)throws Exception{
//        return asyncMessageMapper.insertSendMsgResult(params);
//    }
//    
//    public int updateSendMsgResult(Map params)throws Exception{
//        return asyncMessageMapper.updateSendMsgResult(params);
//    } 
//    
//    public List<WebsocketMessage> getMessageList(Map params) throws Exception{
//        return asyncMessageMapper.getMessageList(params);
//    }
    
}
