/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.exeception.TaskException;

/**
 *
 * @author whoana
 */
public class GIDTaskExecutor extends TaskExecutor{
    String jdbcDriver;
    String jdbcUrl;
    String jdbcUsername;
    String jdbcPassword;
    String pathForCreateData100;
    String pathForCreateData200; 
    String siteCode;
    
    @Override
    public void initTaskParams() throws Exception {
        Map taskParams = task.getParams(); 
        
        
       
        if(!Util.isEmpty(taskParams.get("jdbcDriver"))){
            jdbcDriver = (String)taskParams.get("jdbcDriver");
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a jdbcDriver param value."));
        }
        
        
        if(!Util.isEmpty(taskParams.get("jdbcUrl"))){
            jdbcUrl = (String)taskParams.get("jdbcUrl");
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a jdbcUrl param value."));
        }
        
        if(!Util.isEmpty(taskParams.get("jdbcUsername"))){
            jdbcUsername = (String)taskParams.get("jdbcUsername");
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a jdbcUsername param value."));
        }
        
        
        if(!Util.isEmpty(taskParams.get("jdbcPassword"))){
            jdbcPassword = (String)taskParams.get("jdbcPassword");
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a jdbcPassword param value."));
        } 
        
        if(!Util.isEmpty(taskParams.get("pathForCreateData100"))){
            pathForCreateData100 = (String)taskParams.get("pathForCreateData100");
            File dir100 = new File(pathForCreateData100);
            if(!dir100.exists()) dir100.mkdirs();
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a pathForCreateData100 param value."));
        }
        
        if(!Util.isEmpty(taskParams.get("pathForCreateData200"))){
            pathForCreateData200 = (String)taskParams.get("pathForCreateData200");
            File dir200 = new File(pathForCreateData200);
            if(!dir200.exists()) dir200.mkdirs();
        }else{
            throw new TaskException(Util.join(getClass().getName() ," need a pathForCreateData200 param value."));
        }
        
        logger.info(Util.join(" ","jdbcDriver:[", jdbcDriver, "]"));
        logger.info(Util.join(" ","jdbcUrl:[", jdbcUrl, "]"));
        logger.info(Util.join(" ","jdbcUsername:[", jdbcUsername, "]"));
        logger.info(Util.join(" ","jdbcPassword:[", jdbcPassword, "]"));
    }
    
    
    public List<Map> getDataTableList(Connection con) throws Exception{
        List<Map> list = new ArrayList<Map>();
        String sql = "SELECT                  \n" +
                     "    DATA_NM             \n" +
                     "   ,SITE_TABLE          \n" +
                     "   ,REF_TABLE           \n" +
                     "   ,DATA_CREATE_SEQ     \n" +
                     "   ,DATA_TYPE           \n" +
                     "   ,SITE_YN             \n" +
                     " FROM TMS0003           \n" + 
                     "WHERE DATA_TYPE IN ('100','200') ORDER BY DATA_CREATE_SEQ\n";
        Statement st = null;
        ResultSet rs = null;
        try{ 
            logger.info(Util.join(" ","설치 데이터 리스트 가져오기 실행"));
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                Map row = new HashMap();
                row.put("DATA_NM",          rs.getObject("DATA_NM"));
                row.put("SITE_TABLE",       rs.getObject("SITE_TABLE"));
                row.put("REF_TABLE",        rs.getObject("REF_TABLE"));
                row.put("DATA_CREATE_SEQ",  rs.getObject("DATA_CREATE_SEQ"));
                row.put("DATA_TYPE",        rs.getObject("DATA_TYPE"));
                row.put("SITE_YN",          rs.getObject("SITE_YN"));
                list.add(row);
                logger.info(Util.join(" ",Util.toJSONString(row)));
            }
            return list;
        }finally{
            logger.info(Util.join(" ","설치 데이터 리스트 가져오기 종료"));
            if(rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            }
            
            if(st != null) try {
                st.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            }
        }
    }
    
     
    
    @Override
    public void execute(Map params) throws Exception {
        Connection con = null; 
        try{
            
            Properties properties = (Properties)params.get("properties");
            siteCode = (String)properties.get("install.site.code");
            
            if(Util.isEmpty(siteCode)){
                throw new TaskException(Util.join(getClass().getName() ," need a install.site.code properties's value."));
            }
            
            Class.forName(jdbcDriver); 
            con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            logger.info(Util.join(" ","jdbc connection[",jdbcUrl,"] 성공"));
            List<Map> res = getDataTableList(con);
            if(!Util.isEmpty(res)){
                for(Map row : res){
                    
                    createData(con, row);
                    
                }
            }
            
        }finally{ 
            
            if(con != null) try {
                con.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            }
        }
    }
    
      

    private void createData(Connection con, Map row) throws SQLException, Exception {
        
        Statement st = null;
        ResultSet rs = null;
        try{
            logger.info(Util.join(" ","데이터 가져오기 시작"));
            String srcTable = (String)row.get("SITE_TABLE");
            String tarTable = (String)row.get("REF_TABLE");
            String dataType = (String)row.get("DATA_TYPE");
            String createSeq = row.get("DATA_CREATE_SEQ").toString();
            String siteYn   = (String)row.get("SITE_YN");
            StringBuffer select = new StringBuffer();
            
            select.append("select * from ").append(srcTable);
            
            if("MSU0208".equalsIgnoreCase(srcTable)){
                select.append(" where P_SITE_ID = '").append(siteCode).append("'");
            }else if("Y".equalsIgnoreCase(siteYn)){
                select.append(" where SITE_ID = '").append(siteCode).append("'");
            }
            
            logger.info(Util.join(" ","sql:", select.toString()));
            
            st = con.createStatement();
            rs = st.executeQuery(select.toString());
            ResultSetMetaData rsmd = rs.getMetaData();            
            int columnCount = rsmd.getColumnCount();
            List<String[]> columnInfos = new ArrayList<String[]>();
            for(int i = 0 ; i < columnCount ; i ++){
                String cn = rsmd.getColumnName(i + 1);
                if("SITE_ID".equals(cn)||"MOD_USER".equals(cn)||"MOD_DATE".equals(cn)) continue; 
                if("P_SITE_ID".equals(cn)) continue; 
                if("C_SITE_ID".equals(cn)) continue; 
                
                String ct = rsmd.getColumnTypeName(i + 1);
                String[] ci = new String[2];
                ci[0] = cn;
                ci[1] = ct;
                columnInfos.add(ci);
                //logger.info(Util.join(" ",(i + 1), ":column name:", cn, ", column type:", ct));
            }
            
            String dataFileName = Util.join(createSeq,"-",tarTable,".sql");
            createDataFile(dataType, dataFileName);
            
            while(rs.next()){
                StringBuffer record = new StringBuffer();
                record.append("insert into ").append(tarTable).append(" (");
                StringBuffer names = new StringBuffer();
                StringBuffer values = new StringBuffer();
                values.append("'");
                int i = 1;
                for(String[] ci : columnInfos){
                    String name = ci[0];
                    String type = ci[1];
                    String value = rs.getString(name);
                    if(!Util.isEmpty(value)) {
                        value = value.replaceAll("\\'","\\'\\'");
                    }else{
                        value = "";
                    }

                    if(i < columnInfos.size()){
                        names.append(name).append(",");
                        values.append(value).append("','");
                    }else{
                        names.append(name);
                        values.append(value).append("'");
                    }                     
                     
                    i ++;
                    
                } 
                record.append(names).append(") values (").append(values).append(");").append(String.format("%s%n", ""));

                addDataToFile(record, dataType, dataFileName);
            }

            
        }finally{
            if(rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            }
            
            if(st != null) try {
                st.close();
            } catch (SQLException ex) {
                logger.error("",ex);
            } 
        }
    }

    private void createDataFile(String dataType, String dataFileName) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        File file = null; 
        if("100".equals(dataType)){            
            file = new File(pathForCreateData100, dataFileName);
        }else if("200".equals(dataType)){
            file = new File(pathForCreateData200, dataFileName);
        }else{
            throw new UnsupportedOperationException("Not supported yet.");             
        }
        
        file.delete();
        
        //file.createNewFile();
        logger.info(Util.join(" ","create data file:", file.toString()));
        
            
    }

    private void addDataToFile(StringBuffer record, String dataType, String dataFileName) throws Exception {
        FileWriter fw = null;
        
        File file = null; 
        if("100".equals(dataType)){            
            file = new File(pathForCreateData100, dataFileName);
        }else if("200".equals(dataType)){
            file = new File(pathForCreateData200, dataFileName);
        }else{
            throw new UnsupportedOperationException("Not supported yet.");             
        }
        
        try{
            fw = new FileWriter(file,true);
            fw.write(record.toString());
            fw.flush();
        }finally{
            if(fw != null) fw.close();
        }
    }
    
}
