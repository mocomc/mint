package pep.per.mint.database.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.RecoveryInfo;

@Component
public class RecoveryDao extends AbstractDao {

	
	public int insertRecoveryInfo(SqlSession session, RecoveryInfo recoveryInfo) throws Exception{
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RecoveryMapper.insertRecoveryInfo";
		
		insert(session, recoveryInfo, insertSqlName);
		int result = session.insert(insertSqlName, recoveryInfo);
		
		return result;
	}
}
