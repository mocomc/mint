/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import pep.per.mint.common.util.Util;
import pep.per.mint.fetch.exception.FetchException;
import pep.per.mint.fetch.exception.HaveNoFetchException;

/**
 * <pre>
 * pep.per.mint.fetch
 * FetchManager.java
 * </pre>
 * @author whoana
 * @date Apr 24, 2019
 */
@Component
public class FetchManager {


	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String INIT = "0";
	public static final String SUCCESS = "1";
	public static final String FAIL = "9";

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("fetchDatabaseRunner")
	FetchRunner fetchDatabaseRunner;

	@Autowired
	private ResourceLoader resourceLoader;

	String fetchConfigFile = "classpath:/fetch/fetch.yml";

	Map<String, Object> params = new HashMap<String, Object>();

	@PostConstruct
	public void start()throws Exception{
		long elapsedTime = System.currentTimeMillis();
		try {
			logger.info("------------------------------------------------------------------------");
			logger.info("startFetch");
			logger.info("------------------------------------------------------------------------");
			loadProperties();
			fetch();
			logger.info("------------------------------------------------------------------------");
			logger.info("okFetch");
			logger.info("패치 작업이 정상적으로 처리되었습니다.");
		}catch(HaveNoFetchException e) {
			logger.info("패치 작업이 존재하지 않습니다.");
		}catch(FetchException e) {
			logger.error("" ,e);
			logger.error("------------------------------------------------------------------------");
			logger.error("failFetch");
			logger.error("패치에 실패하였습니다.");
			logger.error("이전 소스로 복원을 진행하여 주시기 바라며 ");
			logger.error("문제해결을 위해 에러코드 및 fetch.log 파일을 제품 개발자에게 전달해 주십시오.");
			String errorCode = e.getErrorCd();
			logger.error("에러코드 :" + errorCode);
			throw e;
		}catch(Exception e) {
			logger.error("" ,e);
			logger.error("------------------------------------------------------------------------");
			logger.error("failFetch");
			logger.error("패치에 실패하였습니다.");
			logger.error("문제해결을 위해 fetch.log 파일을 제품 개발자에게 전달해 주십시오.");
			throw e;
		}finally {
			logger.info("------------------------------------------------------------------------");
			logger.info("finishFetch (elapsedTime:" + (System.currentTimeMillis() - elapsedTime )+ " ms)");
		}

	}

	/**
	 * <pre>
	 * <code>
	 * ------------------------------------------------------------------------
	 * fetch.yma
	 * ------------------------------------------------------------------------
	 * fetch:
	 *  id: f19042901
	 *  version: 19042901
	 *  author: whoana
	 *  comments: 스키마변경, 데이터 변경, 공통코드, 포털환경설정 등을 업데이트
	 *  database:
	 *   schema:
	 *    - fetch-schema01.sql
	 *    - fetch-schema02.sql
	 *   data:
	 *    -fetch-data01.sql
	 *    -fetch-data02.sql
	 *   tsu0301:
	 *    fetch-tsu0301.sql
	 *   tsu0302:
	 *    fetch-tsu0302.sql
	 * </code>
	 * </pre>
	 */
	private void loadProperties() throws Exception{
		Yaml yaml = new Yaml();
		HashMap<String, Object> root = new HashMap<String, Object>();
		FileInputStream fis = null;


		try {

			String fetchProperties = null;
			try{
				fetchProperties = resourceLoader.getResource(fetchConfigFile).getURI().getPath();
			}catch(Exception e){
				 throw new HaveNoFetchException();
			}

			if(fetchProperties == null) throw new HaveNoFetchException();

			logger.debug("fetchProperties:" + fetchProperties);
			File file = new File(fetchProperties);

			fis = new FileInputStream(file);

			root = yaml.load(fis);

			HashMap<String,Object> fetch = (HashMap<String,Object>)root.get("fetch");

			String id 		= (String)fetch.get("id");
			String version 	= (String)fetch.get("version");
			String comments = (String)fetch.get("comments");
			String author 	= (String)fetch.get("author");

			logger.info("ID:" + id);
			logger.info("VERSION:" + version);
			logger.info("작성자:" + author);
			logger.info("내용:" + comments);

			HashMap<String,Object> database = (HashMap<String,Object>)fetch.get("database");

			List<String> schemaFiles = (List<String>)database.get("schema");

			List<String> dataFiles = (List<String>)database.get("data");

			String tsu0301 = (String)database.get("tsu0301");

			String tsu0302 = (String)database.get("tsu0302");

			params.put("id", id);
			params.put("version", version);
			params.put("comments", comments);
			params.put("author", author);


			params.put("schemaFiles", schemaFiles);
			params.put("dataFiles", dataFiles);
			params.put("tsu0301", tsu0301);
			params.put("tsu0302", tsu0302);

			//System.out.println(Util.toJSONString(params));

		} finally {
			if(fis != null )
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void fetch() throws Exception{

		String fetchId   = (String)params.get("id");
		String fetchDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
		String resultCd  = INIT;
		String resultMsg = "";
		String endDate	 = "";
		String contents	 = (String)params.get("comments");
		String userId 	 = (String)params.get("author");

		try {
			fetchDatabase();
			resultCd  = SUCCESS;
			resultMsg = Util.join("패치처리성공 : 패치ID[",fetchId,"]");
		}catch(Exception e) {
			resultCd  = FAIL;
			String errorMsg = e.getMessage();
			resultMsg = Util.join("패치처리실패 : 패치ID[",fetchId,"], ", errorMsg);
			throw e;
		}finally {
			endDate	 = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
			logFetchResult(fetchId, fetchDate, resultCd, resultMsg, endDate, contents, userId);
		}
	}


	/**
	 * @throws Exception
	 *
	 */
	private void fetchDatabase() throws Exception {
		params.put("dataSource", dataSource);
		fetchDatabaseRunner.run(params);
	}

	/**
	 * @throws Exception
	 * @throws SQLException
	 *
	 */
	private void logFetchResult(String fetchId, String fetchDate, String resultCd, String resultMsg, String endDate, String contents, String userId) throws Exception {
		Connection con = null;
		PreparedStatement logSqlStmt = null;
		try {
			String logSql = "insert into TSU0901 (FETCH_ID, FETCH_DATE, RESULT_CD, RESULT_MSG, END_DATE, CONTENTS, USER_ID) values (?,?,?,?,?,?,?) ";
			con = dataSource.getConnection();
			logSqlStmt = con.prepareStatement(logSql);
			logSqlStmt.setString(1, fetchId);
			logSqlStmt.setString(2, fetchDate);
			logSqlStmt.setString(3, resultCd);
			logSqlStmt.setString(4, resultMsg);
			logSqlStmt.setString(5, endDate);
			logSqlStmt.setString(6, contents);
			logSqlStmt.setString(7, userId);

			int res = logSqlStmt.executeUpdate();
			con.commit();
		}catch(Exception e) {
			try {
				if(con != null) con.rollback();
			} catch (SQLException e1) {}
			throw e;
		}finally{
			if(logSqlStmt != null)
				try {
					logSqlStmt.close();
				} catch (SQLException e) {}
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

}
