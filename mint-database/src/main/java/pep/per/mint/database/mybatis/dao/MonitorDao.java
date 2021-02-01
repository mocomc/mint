package pep.per.mint.database.mybatis.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.TRLog;

@Component
public class MonitorDao extends AbstractDao {
	
	
	public void insertTRLogBatch(List<TRLog> records) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MonitorMapper.insertTRLog";
		insertBatch(records, sqlName);
	}
	
	public void insertTRLogBatch(SqlSession session, List<TRLog> records) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MonitorMapper.insertTRLog";
		insertBatch(session, records, sqlName);
	}
}
