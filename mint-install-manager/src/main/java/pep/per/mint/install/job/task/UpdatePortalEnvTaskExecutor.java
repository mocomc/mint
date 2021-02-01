/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;
 
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.Map;
import java.util.Properties;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.exeception.TaskException;

/**
 *
 * @author whoana
 */
public class UpdatePortalEnvTaskExecutor extends TaskExecutor{
    String jdbcDriver;
    String jdbcUrl;
    String jdbcUsername;
    String jdbcPassword;
    String pathForCreateData100;
    String pathForCreateData200; 
       
   
     
    
    @Override
    public void execute(Map params) throws Exception {
        
        
        Connection con = null; 
        try{
            
            Properties properties = (Properties)params.get("properties");
            jdbcDriver = (String)properties.get("install.jdbc.driver.class.name");
            jdbcUrl = (String)properties.get("install.jdbc.url");
            jdbcUsername = (String)properties.get("install.jdbc.username");
            jdbcPassword = (String)properties.get("install.jdbc.password");   
            String envHostname = (String)properties.get("env.system.batch.basic.hosthame");
            String envUploadPath = (String)properties.get("env.system.file.upload.path");
            
            if(Util.isEmpty(jdbcDriver)){
                throw new TaskException(Util.join(getClass().getName() ," need a install.jdbc.driver.class.name properties's value."));
            }


            if(Util.isEmpty(jdbcUrl)){
                throw new TaskException(Util.join(getClass().getName() ," need a install.jdbc.url properties's value."));
            }

            if(Util.isEmpty(jdbcUsername)){
                throw new TaskException(Util.join(getClass().getName() ," need a install.jdbc.username properties's value."));
            }


            if(Util.isEmpty(jdbcPassword)){
                throw new TaskException(Util.join(getClass().getName() ," need a install.jdbc.password properties's value."));
            }


            if(Util.isEmpty(envHostname)){
                throw new TaskException(Util.join(getClass().getName() ," need a env.system.batch.basic.hosthame properties's value."));
            }


            if(Util.isEmpty(envUploadPath)){
                throw new TaskException(Util.join(getClass().getName() ," need a env.system.file.upload.path properties's value."));
            }
            
            logger.info(Util.join(" ","jdbcDriver:[", jdbcDriver, "]"));
            logger.info(Util.join(" ","jdbcUrl:[", jdbcUrl, "]"));
            logger.info(Util.join(" ","jdbcUsername:[", jdbcUsername, "]"));
            logger.info(Util.join(" ","jdbcPassword:[", jdbcPassword, "]"));

            
            Class.forName(jdbcDriver); 
            con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            logger.info(Util.join(" ","jdbc connection[",jdbcUrl,"] 테스트를 성공하였습니다."));
            con.setAutoCommit(false);
            

            updateEnv(con, "system", "batch.basic.hostname", envHostname);
            updateEnv(con, "system", "file.upload.path", envUploadPath);
            
            con.commit();
        }catch(Exception e){
            if(con != null) con.rollback();
            throw e;
        }finally{ 
            
            if(con != null) try {
                con.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            }
        }
    }
    
    private void updateEnv(Connection con, String pkg, String id, String value) throws Exception {
        Statement st = null;
        int res = -1;
        try{
            
            String sql = "update TSU0302 set ATTRIBUTE_VALUE = '" + value + "' where PACKAGE = '" + pkg + "' and ATTRIBUTE_ID = '" + id + "'";
            
            logger.info(Util.join(" ","포털환경설정 업데이트를 시작합니다.:", sql));
            
            st = con.createStatement();
            
            res = st.executeUpdate(sql);
            
            if(res < 1) throw new TaskException(Util.join("포털환경설정 업데이트를 실패하였습니다.(", res ,"):", sql)); 
            
        }finally{
            logger.info(Util.join(" ","포털환경설정 업데이트를 종료합니다. : result code(",res , ")"));
            if(st != null) st.close();
        }
    }

    @Override
    public void initTaskParams() throws Exception {
        
    }
    
}
