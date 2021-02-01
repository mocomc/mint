/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.data.Job;
import pep.per.mint.install.data.Task;
import pep.per.mint.install.job.task.TaskExecutor;
import static pep.per.mint.install.manager.Zoon.getLogBar;

/**
 *
 * @author whoana
 */
public abstract class JobExecutor {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    Job job;
    
    List<TaskExecutor> taskExecutors;
    
    public JobExecutor(Job job) throws Exception{
         

        try{
            logger.info(Util.join(" ","JOB[", job.getName() ,"] 설정을 시작합니다.")); 
            logger.info(getLogBar(80,"-"));

            this.job = job;        
            List<Task> tasks = job.getTasks();
            if(tasks != null){
                taskExecutors = new ArrayList<TaskExecutor>();
                for(Task task : tasks){
                    TaskExecutor te = buildTaskExecutor(task);
                    taskExecutors.add(te);
                }
            }
            logger.info(getLogBar(80,"-"));
            logger.info(Util.join(" ","JOB[", job.getName() ,"] 설정을 완료하였습니다.")); 
        }catch(Exception e){
            logger.info(getLogBar(80,"-"));
            logger.error(Util.join(" ","JOB[", job.getName() ,"] 설정 중 예외가 발생되었습니다.:"),e);            
            throw e;
        }
        
    }
    
    abstract public void execute(Map params) throws Exception; 

    public void run(Map params) throws Exception {
        execute(params);
    }
    
    public Job getJob() {
        return job;
    }

    private TaskExecutor buildTaskExecutor(Task task) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {

        try{
            logger.info(Util.join(" ","TASK[", task.getName() ,"] 설정을 시작합니다.")); 
            logger.info(getLogBar(80,"-"));
            
            String executor = task.getExecutor();
            TaskExecutor te = null;
            if(!Util.isEmpty(executor)){
                Class executorClass = Class.forName(executor);            
                te = (TaskExecutor)executorClass.newInstance();            
                te.setTask(task);
                te.initalize();
            }
            logger.info(getLogBar(80,"-"));
            logger.info(Util.join(" ","TASK[", task.getName() ,"] 설정을 완료하였습니다."));        
            return te;
        }catch(Exception e){
            logger.info(getLogBar(80,"-"));
            logger.error(Util.join(" ","TASK[", task.getName() ,"] 설정 중 예외가 발생되었습니다.:"),e);     
            throw e;
        }
    }  

    public List<TaskExecutor> getTaskExecutors() {
        return taskExecutors;
    }
    
    
     
}
