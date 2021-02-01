/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.common.data.basic.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

/**
 *
 * @author whoana
 */
public class WebsocketChannel extends CacheableObject{
    
    @JsonProperty
    String channelId = defaultStringValue;
    
    @JsonProperty
    String channelName = defaultStringValue;
    
    @JsonProperty
    String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    
    
}
