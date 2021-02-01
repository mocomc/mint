/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Map;
import pep.per.mint.common.data.CacheableObject;

/**
 *
 * @author whoana
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Task extends CacheableObject{
    @JsonProperty
    String name;
    
    @JsonProperty
    boolean use;
    
    @JsonProperty    
    String executor;
    
    @JsonProperty
    boolean doNextOnError = false;
    
    @JsonProperty
    Map params;
    
    @JsonProperty
    String init;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public boolean isDoNextOnError() {
        return doNextOnError;
    }

    public void setDoNextOnError(boolean doNextOnError) {
        this.doNextOnError = doNextOnError;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }
    
    
}
