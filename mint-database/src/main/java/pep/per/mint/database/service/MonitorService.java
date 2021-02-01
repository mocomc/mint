package pep.per.mint.database.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.TRLog;
import pep.per.mint.common.data.TRLogList;
import pep.per.mint.database.mybatis.dao.MonitorDao;
import pep.per.mint.database.mybatis.persistance.MonitorMapper;



@Service
public class MonitorService {
	 
	@Autowired
	MonitorDao monitorDao;
	
	@Autowired
	MonitorMapper monitorMapper;
	
	public void insertTRLogBatch(List<TRLog> records) throws Exception{
		monitorDao.insertTRLogBatch(records);
	}
	
	public void insertTRLogBatch(SqlSession session, List<TRLog> records) throws Exception{
		monitorDao.insertTRLogBatch(session, records);
	}
	
	public SqlSession getBatchSession() throws Exception{
		return monitorDao.openSession(ExecutorType.BATCH);
	}
	
	
	public TRLogList getTRLogList(Map<String, Object> params) throws Exception{
		List<TRLog> list = monitorMapper.getTRLogList(params);
		TRLogList rs = new TRLogList();
		rs.setList(list);
		return rs;
	}
	
}
