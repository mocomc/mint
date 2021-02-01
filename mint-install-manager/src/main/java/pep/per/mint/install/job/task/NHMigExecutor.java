/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.job.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import pep.per.mint.common.util.Util;
import pep.per.mint.install.exeception.TaskException;

/**
 *
 * @author whoana
 */
public class NHMigExecutor extends TaskExecutor{
    String jdbcDriver;
    String jdbcUrl;
    String jdbcUsername;
    String jdbcPassword;
    String fromDay;
    String toDay;
    int commitCount = 500;
    int summarizeDayCount = 0;
    int summarizeMonCount = 0;


    @Override
    public void execute(Map params) throws Exception {


        Connection con = null;
        try{

            Properties properties = (Properties)params.get("properties");
            jdbcDriver = (String)properties.get("install.jdbc.driver.class.name");
            jdbcUrl = (String)properties.get("install.jdbc.url");
            jdbcUsername = (String)properties.get("install.jdbc.username");
            jdbcPassword = (String)properties.get("install.jdbc.password");



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


            fromDay = (String)task.getParams().get("fromDay");
            if(Util.isEmpty(fromDay)){
                throw new TaskException(Util.join(getClass().getName() ," need a fromDay task param value.(install-info.json)"));
            }


            toDay = (String)task.getParams().get("toDay");
            if(Util.isEmpty(toDay)){
            	throw new TaskException(Util.join(getClass().getName() ," need a toDay task param value.(install-info.json)"));
            }


            try {
            	commitCount = (int)task.getParams().get("commitCount");
            }catch(Exception e) {
            	commitCount = 500;
            	logger.info("에러시 기본값으로 대체:",e);
            }



            logger.info(Util.join(" ","jdbcDriver:[", jdbcDriver, "]"));
            logger.info(Util.join(" ","jdbcUrl:[", jdbcUrl, "]"));
            logger.info(Util.join(" ","jdbcUsername:[", jdbcUsername, "]"));
            logger.info(Util.join(" ","jdbcPassword:[", jdbcPassword, "]"));

            logger.info(Util.join(" ","fromDay:[", fromDay, "]"));
            logger.info(Util.join(" ","toDay:[", toDay, "]"));
            logger.info(Util.join(" ","commitCount:[", commitCount, "]"));

            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            logger.info(Util.join(" ","jdbc connection[",jdbcUrl,"] 테스트를 성공하였습니다."));
            con.setAutoCommit(false);


            logger.info("----------------------------------------");
            logger.info("mig day sum:MASTERLOG_STAT_DAY -> TSU0804");
            logger.info("----------------------------------------");
            long elapsed = System.currentTimeMillis();
            try {
            	summarizeDay(con, fromDay, toDay);
            	logger.info("result success");
        		logger.info(Util.join("total summarize day count:", summarizeDayCount,""));
            }catch(Exception e) {
            	logger.info("result fail");
        		logger.info(Util.join("total summarize day count:", summarizeDayCount,""));
            }finally {
            	logger.info("elapsed:" + ((System.currentTimeMillis() - elapsed)/1000) + " sec");
            }

            logger.info("----------------------------------------");
            logger.info("mig month sum:TSU0804 -> TSU0805");
            logger.info("----------------------------------------");
            elapsed = System.currentTimeMillis();
            try {
            	summarizeMon(con, fromDay, toDay);
            	logger.info("result success");
        		logger.info(Util.join("total summarize month count:", summarizeMonCount,""));
            }catch(Exception e) {
            	logger.info("result fail");
        		logger.info(Util.join("total summarize month count:", summarizeMonCount,""));
           }finally {
        	    logger.info("elapsed:" + ((System.currentTimeMillis() - elapsed)/1000) + " sec");
           }

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



	/**
	 * @param con
     * @param toDay
     * @param fromDay
     * @throws SQLException
	 */
	private void summarizeDay(Connection con, String fromDay, String toDay) throws Exception {

		StringBuffer fetchSql = new StringBuffer();
		fetchSql.append("   SELECT                                                        \n");
		fetchSql.append("	 MSG_INTF_ID 								AS INTEGRATION_ID \n");
		fetchSql.append("	,PR_DATE     								AS TR_DAY         \n");
		fetchSql.append("   ,SUM(decode(MSG_STATUS, '01', MSG_COUNT,0)) AS SUCCESS_CNT    \n");
		fetchSql.append("   ,SUM(decode(MSG_STATUS, '99', MSG_COUNT,0)) AS ERROR_CNT      \n");
		fetchSql.append("   ,SUM(decode(MSG_STATUS, '00', MSG_COUNT,0)) AS PROCESS_CNT    \n");
		fetchSql.append("FROM MASTERLOG_STAT_DAY                                          \n");
		fetchSql.append("WHERE PR_DATE BETWEEN ? AND ?                  				  \n");
		fetchSql.append("GROUP BY MSG_INTF_ID, PR_DATE	                                  \n");

		PreparedStatement fetchPstmt = con.prepareStatement(fetchSql.toString());

		String selectInterfaceIdSql = "SELECT INTERFACE_ID FROM TAN0201 WHERE INTEGRATION_ID = ?";
		PreparedStatement selectInterfaceIdPstmt = con.prepareStatement(selectInterfaceIdSql);

		String deleteTSU0804 = "DELETE FROM TSU0804 WHERE INTERFACE_ID = ? AND TR_DAY = ?";
		PreparedStatement deletePstmt = con.prepareStatement(deleteTSU0804);

		String insertTSU0804 = "INSERT INTO TSU0804 (INTERFACE_ID, TR_DAY, DATA_SIZE, SUCCESS_CNT, ERROR_CNT, PROCESS_CNT, REG_DATE, REG_APP) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement insertPstmt = con.prepareStatement(insertTSU0804);

		//fetch data
		fetchPstmt.setString(1, fromDay);
		fetchPstmt.setString(2, toDay);
		ResultSet rs = fetchPstmt.executeQuery();


		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
		String regApp  = "zoon";


		while(rs.next()) {

			String intergrationId = rs.getString("INTEGRATION_ID");
			String trDay 		  = rs.getString("TR_DAY");
			int successCnt 		  = rs.getInt("SUCCESS_CNT");
			int errorCnt 		  = rs.getInt("ERROR_CNT");
			int processCnt 		  = rs.getInt("PROCESS_CNT");
			String interfaceId    = null;
			selectInterfaceIdPstmt.setString(1, intergrationId);

			ResultSet interfaceIdRs = selectInterfaceIdPstmt.executeQuery();
			if(interfaceIdRs.next()) {
				interfaceId = interfaceIdRs.getString("INTERFACE_ID");
			}else {
				continue;
			}

			if(interfaceId == null) continue;
			deletePstmt.setString(1, interfaceId);
			deletePstmt.setString(2, trDay);
			int resDelete = deletePstmt.executeUpdate();

			insertPstmt.setString(1, interfaceId);
			insertPstmt.setString(2, trDay);
			insertPstmt.setInt	 (3, 0);
			insertPstmt.setInt	 (4, successCnt);
			insertPstmt.setInt	 (5, errorCnt);
			insertPstmt.setInt	 (6, processCnt);
			insertPstmt.setString(7, regDate);
			insertPstmt.setString(8, regApp);
			int resInsert = insertPstmt.executeUpdate();

			summarizeDayCount ++;

			if(summarizeDayCount % commitCount == 0) {
				con.commit();
				logger.info("commit point:summarizeDayCount:" + summarizeDayCount);
			}

		}
		con.commit();
		logger.info("commit point:summarizeDayCount:" + summarizeDayCount);
	}

	/**
	 *
	 * @param con
	 * @param fromDay
	 * @param toDay
	 */
	private void summarizeMon(Connection con, String fromDay, String toDay) throws Exception  {
		StringBuffer fetchSql = new StringBuffer();
		fetchSql.append("	SELECT  										\n");
		fetchSql.append("	 INTERFACE_ID                                   \n");
		fetchSql.append("	,SUBSTR(TR_DAY,1,6) AS TR_MONTH                 \n");
		fetchSql.append("	,SUM(DATA_SIZE) 	AS DATA_SIZE                \n");
		fetchSql.append("	,SUM(SUCCESS_CNT)	AS SUCCESS_CNT              \n");
		fetchSql.append("	,SUM(ERROR_CNT)     AS ERROR_CNT                \n");
		fetchSql.append("	,SUM(PROCESS_CNT)   AS PROCESS_CNT              \n");
		fetchSql.append(" FROM TSU0804                                      \n");
		fetchSql.append("WHERE TR_DAY BETWEEN ? AND ?	    				\n");
		fetchSql.append("GROUP BY INTERFACE_ID, SUBSTR(TR_DAY,1,6)          \n");

		PreparedStatement fetchPstmt = con.prepareStatement(fetchSql.toString());

		String deleteTSU0805 = "DELETE FROM TSU0805 WHERE INTERFACE_ID = ? AND TR_MONTH = ?";
		PreparedStatement deletePstmt = con.prepareStatement(deleteTSU0805);

		String insertTSU0805 = "INSERT INTO TSU0805 (INTERFACE_ID, TR_MONTH, DATA_SIZE, SUCCESS_CNT, ERROR_CNT, PROCESS_CNT, REG_DATE, REG_APP) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement insertPstmt = con.prepareStatement(insertTSU0805);

		//fetch data
		fetchPstmt.setString(1, fromDay);
		fetchPstmt.setString(2, toDay);
		ResultSet rs = fetchPstmt.executeQuery();


		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
		String regApp  = "zoon";


		while(rs.next()) {

			String interfaceId    = rs.getString("INTERFACE_ID");
			String trMonth 		  = rs.getString("TR_MONTH");
			int successCnt 		  = rs.getInt("SUCCESS_CNT");
			int errorCnt 		  = rs.getInt("ERROR_CNT");
			int processCnt 		  = rs.getInt("PROCESS_CNT");

			deletePstmt.setString(1, interfaceId);
			deletePstmt.setString(2, trMonth);
			int resDelete = deletePstmt.executeUpdate();

			insertPstmt.setString(1, interfaceId);
			insertPstmt.setString(2, trMonth);
			insertPstmt.setInt	 (3, 0);
			insertPstmt.setInt	 (4, successCnt);
			insertPstmt.setInt	 (5, errorCnt);
			insertPstmt.setInt	 (6, processCnt);
			insertPstmt.setString(7, regDate);
			insertPstmt.setString(8, regApp);
			int resInsert = insertPstmt.executeUpdate();

			summarizeMonCount ++;

			if(summarizeMonCount % commitCount == 0) {
				con.commit();
				logger.info("commit point:summarizeMonCount:" + summarizeMonCount);
			}


		}
		con.commit();
		logger.info("commit point:summarizeMonCount:" + summarizeMonCount);

	}


    @Override
    public void initTaskParams() throws Exception {

    }

}
