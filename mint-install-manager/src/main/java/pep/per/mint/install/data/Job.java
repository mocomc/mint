/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import pep.per.mint.common.data.CacheableObject;

/**
 *
 * @author whoana
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Job extends CacheableObject{
    
    @JsonProperty
    String name;
    
    @JsonProperty
    boolean use = true;
    
    @JsonProperty
    boolean doNextOnError = false;
    
    @JsonProperty
    List<Task> tasks;

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

    public boolean isDoNextOnError() {
        return doNextOnError;
    }

    public void setDoNextOnError(boolean doNextOnError) {
        this.doNextOnError = doNextOnError;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }
    
    
    
}
