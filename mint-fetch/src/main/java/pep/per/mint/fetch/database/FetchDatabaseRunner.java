/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import pep.per.mint.common.util.Util;
import pep.per.mint.fetch.FetchRunner;
import pep.per.mint.fetch.exception.FetchException;

/**
 * <pre>
 * pep.per.mint.fetch.database
 * FetchSchemaRunner.java
 * </pre>
 * @author whoana
 * @date Apr 24, 2019
 */
public class FetchDatabaseRunner implements FetchRunner{

	Logger logger = LoggerFactory.getLogger(this.getClass());


    final static String ORDER_ASC = "asc";
    final static String ORDER_DESC = "desc";
    final static String ORDER_NONE = "NONE";


    final static String ON_ERR_STOP = "stop";
    final static String ON_ERR_CONTINUE = "continue";

    String onError = ON_ERR_STOP;

    String order;
    String fetchConfigFile = "classpath:/fetch/fetch.yml";
    @Autowired
	private ResourceLoader resourceLoader;


    @Override
    public void run(Map<String, Object> params) throws Exception {
    	Connection con = null;
    	try {
	    	DataSource ds = (DataSource)params.get("dataSource");
	    	con = ds.getConnection();
	    	//---------------------------------------------------------------------
	    	//스키마 추가
	    	//---------------------------------------------------------------------
	    	{
		    	List<String> schemaFiles = (List<String>)params.get("schemaFiles");
		    	if(schemaFiles != null) {

			    	for(String file : schemaFiles) {
			    		String schemaFile = null;
			    		try {
			    			schemaFile = resourceLoader.getResource("classpath:/fetch/" + file).getURI().getPath();
							logger.info("schemaFile 처리 시작:" + schemaFile);
							logger.info("------------------------------------------------------------------------");
				    		runScript(con, schemaFile);
			    		}catch(FileNotFoundException e) {
			    			logger.info("fetch.yml에 설정한 파일이 존재하지 않습니다. 패치 처리에서 제외됩니다.(" + file + ")");
			    		}catch(Exception e) {
				    		logger.info("schemaFile 처리 실패:" + schemaFile);

				    		throw new FetchException(FetchException.ERROR_SCHEMA, e);
			    		}
			    	}
		    	}
	    	}
	    	//---------------------------------------------------------------------
	    	//데이터 추가
	    	//---------------------------------------------------------------------
	    	{
		    	List<String> dataFiles = (List<String>)params.get("dataFiles");
		    	if(dataFiles != null) {

			    	for(String file : dataFiles) {
			    		String dataFile = null;
			    		try {
				    		dataFile = resourceLoader.getResource("classpath:/fetch/" + file).getURI().getPath();
							logger.info("dataFile 처리 시작:" + dataFile);
							logger.info("------------------------------------------------------------------------");
				    		runScript(con, file);
			    		}catch(FileNotFoundException e) {
			    			logger.info("fetch.yml에 설정한 파일이 존재하지 않습니다. 패치 처리에서 제외됩니다.(" + file + ")");
			    		}catch(Exception e) {
				    		logger.info("dataFile 처리 실패:" + dataFile);
				    		throw new FetchException(FetchException.ERROR_DATA, e);
				    	}
			    	}
		    	}
	    	}

	    	//---------------------------------------------------------------------
	    	//공통코드 데이터 추가 : TSU0301
	    	//---------------------------------------------------------------------
	    	{
		    	String tsu0301Script = (String)params.get("tsu0301");
		    	if(tsu0301Script != null) {
		    		logger.info("tsu0301 처리 시작:");
			    	try {
			    		String tsu0301 = resourceLoader.getResource("classpath:/fetch/" + tsu0301Script).getURI().getPath();
			    		logger.info("------------------------------------------------------------------------");
				    	runScript(con, tsu0301);
				    	logger.info("tsu0301 처리 완료");
			    	}catch(FileNotFoundException e) {
		    			logger.info("fetch.yml에 설정한 파일이 존재하지 않습니다. 패치 처리에서 제외됩니다.(" + tsu0301Script + ")");
			    	}catch(Exception e) {
		    			logger.info("tsu0301 처리 실패");
		    			throw new FetchException(FetchException.ERROR_TSU0301, e);
		    		}
		    	}
	    	}

	    	//---------------------------------------------------------------------
	    	//포털환경설정 데이터 추가 : TSU0302
	    	//---------------------------------------------------------------------
	    	{
		    	String tsu0302Script = (String)params.get("tsu0302");
		    	if(tsu0302Script != null) {
		    		logger.info("tsu0302 처리 시작:");
		    		try{
				    	String tsu0302 = resourceLoader.getResource("classpath:/fetch/" + tsu0302Script).getURI().getPath();
				    	logger.info("------------------------------------------------------------------------");
				    	runScript(con,tsu0302);
				    	logger.info("tsu0302 처리 완료");
			    	}catch(FileNotFoundException e) {
		    			logger.info("fetch.yml에 설정한 파일이 존재하지 않습니다. 패치 처리에서 제외됩니다.(" + tsu0302Script + ")");
		    		}catch(Exception e) {
		    			logger.info("tsu0302 처리 실패");
		    			throw new FetchException(FetchException.ERROR_TSU0302, e);
		    		}
		    	}
	    	}

	    	if(con != null) con.commit();

    	}catch(Exception e) {
    		if(con != null) con.rollback();
	    	throw e;
    	}finally {
    	}
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
                    	logger.info(command.toString());
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
