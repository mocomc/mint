/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.common.data.basic.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo; 
import pep.per.mint.common.data.CacheableObject; 

/**
 *
 * @author whoana
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class WebsocketUser extends CacheableObject {
    
    
    @JsonProperty
    String userId = defaultStringValue;
    
    @JsonProperty
    String channelId = defaultStringValue;
        
    @JsonProperty
    String sessionId = defaultStringValue;
    
    @JsonProperty
    String logoutDate = defaultStringValue;
    
    @JsonProperty
    String loginDate = defaultStringValue;
    
    @JsonProperty
    String loginYn = "N";
     

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(String logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
 
    
    
}
