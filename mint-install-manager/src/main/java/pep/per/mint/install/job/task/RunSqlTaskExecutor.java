/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.exeception.TaskException;

/**
 *
 * @author whoana
 */
public class RunSqlTaskExecutor extends TaskExecutor{
    final static String SRC_FILES = "files";
    final static String SRC_DIR = "directory";

    final static String ORDER_ASC = "asc";
    final static String ORDER_DESC = "desc";
    final static String ORDER_NONE = "NONE";


    final static String ON_ERR_STOP = "stop";
    final static String ON_ERR_CONTINUE = "continue";

    String source;
    String onError;
    List<String> files;
    String path;
    String order;

    String jdbcDriver;
    String jdbcUrl;
    String jdbcUsername;
    String jdbcPassword;

    String currentSql;

    public RunSqlTaskExecutor(){
        super();
    }

    @Override
    public void execute(Map params) throws Exception {
        Connection con = null;
        String currentSqlFile = "";
        try{
            if(source.equals(SRC_DIR)){
                files = new ArrayList<String>();
                String [] list = new File(path).list();
                for(String fileName : list){
                    String file = new File(path,fileName).getPath();
                    files.add(file);
                }
                Collections.sort(files);
            }

            if(ORDER_DESC.equals(order)){
                Collections.sort(files, Collections.reverseOrder());
            }else if(ORDER_ASC.equals(order)){
                Collections.sort(files);
            }


            for(String sqlFile : files){
            	Class.forName(jdbcDriver);
            	con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            	con.setAutoCommit(false);
            	logger.info(Util.join(" ","jdbc connection[",jdbcUrl,"] 성공"));

                logger.info(Util.join(" ","SQL 파일 실행 시작:[", sqlFile, "]"));
                currentSqlFile = sqlFile;
                try{
                    runScript(con, sqlFile);
                    con.commit();
                }catch(Exception e){
                	if(con != null)con.rollback();
                    if(ON_ERR_STOP.equals(onError)){
                        throw e;
                    }
                    logger.error(Util.join(" ","SQL 파일 실행 예외발생(옵션에 따라 다음 SQL 파일 실행): "), e);
                }finally{
                    logger.info(Util.join(" ","SQL 파일 실행 종료:[", sqlFile, "]"));
                    if(con != null)con.close();
                }
            }
            logger.info(Util.join(" ", "TASK[", getTask().getName(), "] 성공"));
        }catch(Exception e){
            logger.info(Util.join(" ", "TASK[", getTask().getName(), "] 실패"));
            throw new TaskException(Util.join("error occur when execute sql file:", currentSqlFile), e);
        }finally{
            /*if(con != null)con.close();*/
        }
    }

    @Override
    public void initTaskParams() throws Exception {
        Map taskParams = task.getParams();

        if(!Util.isEmpty(taskParams.get("source"))){
            source = (String)taskParams.get("source");

            if(source.equals(SRC_FILES)){
                if(!Util.isEmpty(taskParams.get("files"))){
                    files = (List)taskParams.get("files");
                }else{
                    throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a files param value."));
                }
            }else if(source.equals(SRC_DIR)){

                if(!Util.isEmpty(taskParams.get("path"))){
                    path = (String)taskParams.get("path");

                    if(!Files.exists(Paths.get(path), LinkOption.NOFOLLOW_LINKS)){
                        throw new TaskException(Util.join("패스값[",path,"]으로 파일이 존재하지 않습니다."));
                    }

                }else{
                    throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a path param value."));
                }

                if(!Util.isEmpty(taskParams.get("order"))){
                    order = (String)taskParams.get("order");

                    if(ORDER_ASC.equals(order) || ORDER_DESC.equals(order)){

                    }else{
                        throw new TaskException(Util.join("order[입력값:",order,"] 값은 다음중 하나여야 합니다. asc, desc (설정하지 않을 경우 등록된 파일순으로 SQL 실행)"));
                    }

                }else{
                    order = ORDER_NONE;
                }

            }else{
                throw new TaskException("RunSqlTaskExecutor의 source 파라메터 값은 files 또는 directory 둘 중 하나여여함.");
            }
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a source param value."));
        }

        if(!Util.isEmpty(taskParams.get("onError"))){
            onError = (String)taskParams.get("onError");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a onError param value."));
        }


        if(!Util.isEmpty(taskParams.get("jdbcDriver"))){
            jdbcDriver = (String)taskParams.get("jdbcDriver");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a jdbcDriver param value."));
        }


        if(!Util.isEmpty(taskParams.get("jdbcUrl"))){
            jdbcUrl = (String)taskParams.get("jdbcUrl");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a jdbcUrl param value."));
        }

        if(!Util.isEmpty(taskParams.get("jdbcUsername"))){
            jdbcUsername = (String)taskParams.get("jdbcUsername");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a jdbcUsername param value."));
        }


        if(!Util.isEmpty(taskParams.get("jdbcPassword"))){
            jdbcPassword = (String)taskParams.get("jdbcPassword");
        }else{
            throw new TaskException(Util.join(CopyFileTaskExecutor.class.getName() ," need a jdbcPassword param value."));
        }
        logger.info(Util.join(" ","source:[", source, "]"));
        logger.info(Util.join(" ","files:[", files, "]"));
        logger.info(Util.join(" ","path:[", path, "]"));
        logger.info(Util.join(" ","order:[", order, "]"));
        logger.info(Util.join(" ","onError:[", onError, "]"));

        logger.info(Util.join(" ","jdbcDriver:[", jdbcDriver, "]"));
        logger.info(Util.join(" ","jdbcUrl:[", jdbcUrl, "]"));
        logger.info(Util.join(" ","jdbcUsername:[", jdbcUsername, "]"));
        logger.info(Util.join(" ","jdbcPassword:[", jdbcPassword, "]"));

    }

    String delimiter = ";";
    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param conn - the connection to use for the script
     * @param reader - the source of the script
     * @throws SQLException if any SQL errors occur
     * @throws IOException if there is an error reading from the Reader
     */
    private void runScript(Connection conn, String sql) throws Exception {
        StringBuffer command = null;
        Reader reader = null;
        LineNumberReader lineReader = null;
        try {
            reader = new FileReader(new File(sql));
            lineReader = new LineNumberReader(reader);

            String line = null;
            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                String trimmedLine = line.trim();
                if (trimmedLine.startsWith("--")) {
                    //println(trimmedLine);
                } else if (trimmedLine.length() < 1
                    || trimmedLine.startsWith("//")) {
                    // Do nothing
                } else if (trimmedLine.length() < 1
                    || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (trimmedLine.endsWith(delimiter)) {
                    command.append(line.substring(0, line.lastIndexOf(delimiter)));
                    command.append(" ");
                    Statement statement = conn.createStatement();

                    boolean hasResults = false;
                    if (ON_ERR_STOP.equals(onError)) {
                        hasResults = statement.execute(command.toString());
                    } else {
                        try {
                            statement.execute(command.toString());
                        } catch (SQLException e) {
                            logger.error(Util.join("error sql:", command.toString()),e);
                        }
                    }


                    command = null;
                    try {
                        statement.close();
                    } catch (Exception e) {
                        // Ignore to workaround a bug in Jakarta DBCP
                    }
                    Thread.yield();
                } else {
                    command.append(line);
                    //===================================================
                    //change-date:20180309
                    //---------------------------------------------------
                    //왜 개행을 스페이스로 교체했지?, 다시 개행으로 바꿔 테스트해보자.
                    //old source
                    //---------------------------------------------------
                    //command.append(" ");
                    //new source
                    //---------------------------------------------------
                    String cr = String.format("%s%n", "");
                    command.append(cr);
                }
            }
        } finally {
            if(reader != null){
                reader.close();
            }

            if(lineReader!=null){
                lineReader.close();
            }
        }
    }
}
