/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Map; 
import pep.per.mint.common.util.Util;
import pep.per.mint.install.exeception.TaskException;

/**
 *
 * @author whoana
 */
public class CopyFileTaskExecutor extends TaskExecutor{

    String source;
    String destination;
    
    public CopyFileTaskExecutor() throws TaskException {
        super();
    } 
    @Override
    public void execute(Map params) throws Exception {
         
        try{ 
            logger.debug(Util.join(" ","source to copy:[", source, "]"));
            logger.debug(Util.join(" ","destination to copy:[", destination,"]"));
            Path srcPath = Paths.get(source); 
            
            if(!Files.exists(srcPath, LinkOption.NOFOLLOW_LINKS)){
                throw new TaskException(Util.join(this.getClass(), ".execute() exception:Has no src file by name[",srcPath.toAbsolutePath().toString(),"] to copy"));
            }
            Path destFile = Paths.get(destination); 
            Files.copy(srcPath, destFile, REPLACE_EXISTING);
            logger.debug(Util.join(" ","copy 완료"));
        }finally{
             
        }
        
    }
    
    @Override
    public void initTaskParams() throws Exception {
       
        Map taskParams = task.getParams(); 
        
        if(!Util.isEmpty(taskParams.get("source"))){
            source = (String)taskParams.get("source");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a file param value."));
        }
        
        if(!Util.isEmpty(taskParams.get("destination"))){
            destination = (String)taskParams.get("destination");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a destination param value."));
        }
        
        logger.info(Util.join(" ","source:[", source, "]")); 
        logger.info(Util.join(" ","destination:[", destination, "]")); 
        
    }
    
}
