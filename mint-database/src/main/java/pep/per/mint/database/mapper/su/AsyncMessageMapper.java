/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.websocket.WebsocketChannel;
import pep.per.mint.common.data.basic.websocket.WebsocketMessage;
import pep.per.mint.common.data.basic.websocket.WebsocketUser;

/**
 *
 * @author whoana
 */
public interface AsyncMessageMapper {
    
    public int insertWebsocketUser(WebsocketUser user) throws Exception;
    public WebsocketUser getWebsocketUser(Map params) throws Exception;
    public int updateWebsocketUser(WebsocketUser user) throws Exception;
    
    public WebsocketChannel getChannnel(@Param("channelId")String channelId) throws Exception;
    
    public int insertChannel(WebsocketChannel channel) throws Exception; 
    
    public int insertMessage(WebsocketMessage msg)throws Exception;
    
    public int insertSendMsgResult(Map params)throws Exception;
    
    public int updateSendMsgResult(Map params)throws Exception; 
    
    public int updateSendMsgProcessing(Map params)throws Exception; 
    
    public List<WebsocketMessage> getMessageList(Map params) throws Exception;
    
   
    
    
}
