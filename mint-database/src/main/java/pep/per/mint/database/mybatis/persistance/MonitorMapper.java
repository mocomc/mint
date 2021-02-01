package pep.per.mint.database.mybatis.persistance;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.TRLog;

public interface MonitorMapper {
	
	public void insertTRLog(TRLog trLog) throws Exception;
	
	public List<TRLog> getTRLogList(Map<String, Object> params) throws Exception;
	
}
