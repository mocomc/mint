package pep.per.mint.migration;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.util.Util;


public class MigrationManager {

    Logger log = LoggerFactory.getLogger(MigrationManager.class);


    final static String COMMIT_OPT_MONTH = "month";
    final static String COMMIT_OPT_DAY = "day";

    SqlSessionFactory sqlSessionFactory;

    SqlSessionFactory ifmSqlSessionFactory;

    String commitOpt = COMMIT_OPT_MONTH;



	public String getCommitOpt() {
		return commitOpt;
	}

	public void setCommitOpt(String commitOpt) {
		this.commitOpt = commitOpt;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSessionFactory getIfmSqlSessionFactory() {
		return ifmSqlSessionFactory;
	}

	public void setIfmSqlSessionFactory(SqlSessionFactory ifmSqlSessionFactory) {
		this.ifmSqlSessionFactory = ifmSqlSessionFactory;
	}

	public void migrate(String from, String to) throws Exception{
		SqlSession srcSqlSession = null;
		SqlSession tagSqlSession = null;
		try {

			srcSqlSession = ifmSqlSessionFactory.openSession(true);
			tagSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);


			migSI_STAT_INTF_DAY(srcSqlSession, tagSqlSession, from, to);

			migSI_STAT_INTF_MONTH(srcSqlSession, tagSqlSession, from, to);

			srcSqlSession.commit();
			tagSqlSession.commit();
		} catch (Exception e) {
			tagSqlSession.rollback();
			throw e;
		} finally {
        	if(srcSqlSession != null) { srcSqlSession.close(); }
            if(tagSqlSession != null) { tagSqlSession.close(); }
        }
	}

	private void migSI_STAT_INTF_MONTH(SqlSession srcSqlSession, SqlSession tagSqlSession, String from, String to) throws Exception{
		log.info("----------------------------------------------------------");
		log.info(Util.join("start migSI_STAT_INTF_MONTH:","from:",from,",to:",to));
		log.info("----------------------------------------------------------");
		try{
			/*select
			   GROUP_ID
			  ,INTF_ID
			  ,ST_YEAR || ST_MONTH            as TR_MONTH
			  ,ST_N_COMPSIZE + ST_COMPSIZE    as DATA_SIZE
			  ,ST_FINISHED                    as SUCCESS_CNT
			  ,ST_SEND_ERR + ST_RECV_ERR      as ERROR_CNT
			  ,ST_PROCESSING                  as PROCESS_CNT
			from SI_STAT_INTF_MONTH*/
			Map<String,String> params = new HashMap<String,String>();

			int fromYear = Integer.parseInt(from.substring(0,4));
			int fromMonth = Integer.parseInt(from.substring(4,6));
			int fromDay = Integer.parseInt(from.substring(6));
			int toYear = Integer.parseInt(to.substring(0,4));
			//int toMonth = Integer.parseInt(to.substring(4,6));
			//int toDay = Integer.parseInt(to.substring(6));
			int year = fromYear;
			int month = fromMonth;
			int day = fromDay;


			do{
				params.put("year", Integer.toString(year));
				do{

					params.put("month", Util.leftPad(Integer.toString(month),2,"0"));

					int currentYm = Integer.parseInt(Util.join(params.get("year"), params.get("month")));
					int toYm = Integer.parseInt(to.substring(0,6));
					log.info(Util.join("migSI_STAT_INTF_DAY:currentYm:",Integer.toString(currentYm)));
					if(currentYm > toYm) {
						log.info(Util.join("migSI_STAT_INTF_MONTH:currentYm(",Integer.toString(currentYm),") > toYm(", Integer.toString(toYm), ") 이므로 마이그레이션을 종료한다."));
						return;
					}

					List<Map> records = srcSqlSession.selectList("pep.per.mint.migration.mapper.ifm.IfmMapper.selectSI_STAT_INTF_MONTH", params);
					if(records != null && records.size() >= 0){
						int res = tagSqlSession.delete("pep.per.mint.migration.mapper.su.SummaryMapper.deleteTSU0805", params);
						String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
						String regApp = this.getClass().getName();
						log.info(Util.join("migSI_STAT_INTF_MONTH:record cnt:", records.size()));
						for (Map record : records) {

							String integrationId = Util.join((String)record.get("GROUP_ID"), "_", (String)record.get("INTERFACE_ID"));
							String interfaceId = tagSqlSession.selectOne("pep.per.mint.migration.mapper.su.SummaryMapper.getInterfaceId", integrationId);
							if(Util.isEmpty(interfaceId)){
								interfaceId = integrationId;
								log.info(Util.join("selected interfaceId is NULL!, integrationId:",integrationId));
							}
							record.put("interfaceId", interfaceId);
							record.put("regDate", regDate);
							record.put("regApp", regApp);


							record.put("regDate", regDate);
							record.put("regApp", regApp);
							//log.debug(Util.join("record:", Util.toJSONString(record)));
							res = tagSqlSession.insert("pep.per.mint.migration.mapper.su.SummaryMapper.insertTSU0805", record);
						}

						tagSqlSession.commit();
						log.info(Util.join("migSI_STAT_INTF_MONTH:commit point:", params.get("year"),"-", params.get("month")));

					}else{
						log.info(Util.join("migSI_STAT_INTF_MONTH: no data:", params.get("year"),"-", params.get("month")));
					}

				}while((month = month + 1) < 13);

				month = (month > 12) ? 1 : month;

			}while((year = year + 1) <= toYear);


		}finally{
			log.info("----------------------------------------------------------");
			log.info(Util.join("end migSI_STAT_INTF_MONTH:","from:",from,",to:",to));
			log.info("----------------------------------------------------------");
		}

	}

	private void migSI_STAT_INTF_DAY(SqlSession srcSqlSession, SqlSession tagSqlSession, String from, String to) {
		log.info("----------------------------------------------------------");
		log.info(Util.join("start migSI_STAT_INTF_DAY:","from:",from,",to:",to));
		log.info("----------------------------------------------------------");
		try{
			// TODO Auto-generated method stub
			/*select
			   GROUP_ID
			  ,INTF_ID
			  ,ST_YEAR || ST_MONTH || ST_DAY  as TR_DAY
			  ,ST_N_COMPSIZE + ST_COMPSIZE    as DATA_SIZE
			  ,ST_FINISHED                    as SUCCESS_CNT
			  ,ST_SEND_ERR + ST_RECV_ERR      as ERROR_CNT
			  ,ST_PROCESSING                  as PROCESS_CNT
			from SI_STAT_INTF_DAY*/

			Map<String,String> params = new HashMap<String,String>();

			int fromYear = Integer.parseInt(from.substring(0,4));
			int fromMonth = Integer.parseInt(from.substring(4,6));
			int fromDay = Integer.parseInt(from.substring(6));
			int toYear = Integer.parseInt(to.substring(0,4));
			//int toMonth = Integer.parseInt(to.substring(4,6));
			//int toDay = Integer.parseInt(to.substring(6));
			int year = fromYear;
			int month = fromMonth;
			int day = fromDay;

			do{
				params.put("year", Integer.toString(year));
				do{

					params.put("month", Util.leftPad(Integer.toString(month),2,"0"));
					do{
						params.put("day", Util.leftPad(Integer.toString(day),2,"0"));

						int currentYmd = Integer.parseInt(Util.join(params.get("year"), params.get("month"), params.get("day")));
						int toYmd = Integer.parseInt(to);
						log.info(Util.join("migSI_STAT_INTF_DAY:currentYmd:",Integer.toString(currentYmd)));
						if(currentYmd > toYmd) {
							log.info(Util.join("migSI_STAT_INTF_DAY:currentYmd(",Integer.toString(currentYmd),") > toYmd(", Integer.toString(toYmd), ") 이므로 마이그레이션을 종료한다."));
							return;
						}

						List<Map> records = srcSqlSession.selectList("pep.per.mint.migration.mapper.ifm.IfmMapper.selectSI_STAT_INTF_DAY", params);
						if(records != null && records.size() >= 0){
							int res = tagSqlSession.delete("pep.per.mint.migration.mapper.su.SummaryMapper.deleteTSU0804", params);
							String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
							String regApp = this.getClass().getName();

							log.info(Util.join("migSI_STAT_INTF_DAY:record cnt:", records.size()));
							for (Map record : records) {


								String integrationId = Util.join((String)record.get("GROUP_ID"), "_", (String)record.get("INTERFACE_ID"));
								String interfaceId = tagSqlSession.selectOne("pep.per.mint.migration.mapper.su.SummaryMapper.getInterfaceId", integrationId);
								if(Util.isEmpty(interfaceId)){
									interfaceId = integrationId;
									log.info(Util.join("selected interfaceId is NULL!, integrationId:",integrationId));
								}
								record.put("interfaceId", interfaceId);
								record.put("regDate", regDate);
								record.put("regApp", regApp);


								//log.debug(Util.join("record:", Util.toJSONString(record)));
								res = tagSqlSession.insert("pep.per.mint.migration.mapper.su.SummaryMapper.insertTSU0804", record);
							}
							if(COMMIT_OPT_DAY.equalsIgnoreCase(commitOpt)){
								tagSqlSession.commit();
								log.info(Util.join("migSI_STAT_INTF_DAY:commit point:", params.get("year"),"-", params.get("month"), "-", params.get("day")));
							}
						}else{
							log.info(Util.join("migSI_STAT_INTF_DAY: no data:", params.get("year"),"-", params.get("month"), "-", params.get("day")));
						}

					}while((day = day + 1) < 32);

					day = (day > 31) ? 1 : day;

					if(COMMIT_OPT_MONTH.equalsIgnoreCase(commitOpt)){
						tagSqlSession.commit();
						log.info(Util.join("migSI_STAT_INTF_DAY:commit point:", params.get("year"),"-", params.get("month"), "-", params.get("day")));
					}

				}while((month = month + 1) < 13);

				month = (month > 12) ? 1 : month;

			}while((year = year + 1) <= toYear);

		}finally{
			log.info("----------------------------------------------------------");
			log.info(Util.join("end migSI_STAT_INTF_DAY:","from:",from,",to:",to));
			log.info("----------------------------------------------------------");
		}
	}

}
