/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job;

import java.util.Map; 
import pep.per.mint.common.util.Util;
import pep.per.mint.install.data.Job;
import pep.per.mint.install.data.Task;
import pep.per.mint.install.job.task.TaskExecutor;
import static pep.per.mint.install.manager.Zoon.getLogBar;

/**
 *
 * @author whoana
 */
public class DefaultJobExecutor extends JobExecutor{

    public DefaultJobExecutor(Job jobInfo) throws Exception {
        super(jobInfo);
    }

    @Override
    public void execute(Map params) throws Exception {
        logger.info(Util.join(" ","JOB[",job.getName(),"]을 실행합니다."));
        logger.info(getLogBar(80,"-"));
        try{
            if(taskExecutors != null){
                for(TaskExecutor executor : taskExecutors){
                    logger.info(Util.join(" ","TASK[", executor.getTask().getName(),"]를 실행합니다."));
                    logger.info(getLogBar(80,"-"));
                    try{
                        Task task = executor.getTask();

                        if(task.isUse()){
                            if(task.isDoNextOnError()){
                                try{
                                    executor.run(params);
                                }catch(Exception e){
                                    logger.error(getLogBar(80,"-"));
                                    logger.error(Util.join(" ","TASK[", task.getName(),"] 실행시 예외가 발생되었으나 옵션에 따라 다음 TASK 계속 수행합니다.: "), e);
                                    continue;
                                }
                            }else{
                                executor.run(params);
                            }

                        }
                    }finally{
                        logger.info(getLogBar(80,"-"));
                        logger.info(Util.join(" ","TASK[", executor.getTask().getName(),"]를 종료합니다."));
                    }
                }
            }
        }finally{
            logger.info(getLogBar(80,"-"));
            logger.info(Util.join(" ","JOB[",job.getName(),"]을 종료합니다."));
        }
    }
    
}
