/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.data.Task;

/**
 *
 * @author whoana
 */
public abstract class TaskExecutor {

    Logger logger = LoggerFactory.getLogger(this.getClass());    
    
    Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    public void run(Map params) throws Exception{

        execute(params);
        
    }
    
    public void initalize() throws Exception{
        try{
            logger.info(Util.join(" ","task executor[",getClass().getName(),"] 파라메터 초기화 시작합니다.")); 
            initTaskParams();
        }finally{
            logger.info(Util.join(" ","task executor[",getClass().getName(),"] 파라메터 초기화 종료하였습니다.")); 
        }
    }
    public abstract void initTaskParams() throws Exception;
    public abstract void execute(Map params) throws Exception;
    
    
}
