/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.common.data.basic.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import pep.per.mint.common.data.CacheableObject;

/**
 *
 * @author whoana
 */
public class WebsocketMessage extends CacheableObject{
    
    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_MESSAGE = 1;
    
    @JsonProperty
    String messageId = defaultStringValue;
    
    @JsonProperty
    WebsocketChannel channel;
    
    @JsonProperty
    WebsocketUser sender;
    
    @JsonProperty
    String message = defaultStringValue;
    
    @JsonProperty
    int type = TYPE_MESSAGE;
    
    @JsonProperty
    String regDate = defaultStringValue;
    
    @JsonProperty
    List<WebsocketUser> receivers;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public WebsocketChannel getChannel() {
        return channel;
    }

    public void setChannel(WebsocketChannel channel) {
        this.channel = channel;
    }

    public WebsocketUser getSender() {
        return sender;
    }

    public void setSender(WebsocketUser sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public List<WebsocketUser> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<WebsocketUser> receivers) {
        this.receivers = receivers;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    
}
