package pep.per.mint.database.mybatis.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import pep.per.mint.common.data.Interface;

@Component
public class InterfaceDao extends AbstractDao {
	
	
 

	public void upsertInterface(SqlSession session, Interface interfaze) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.InterfaceMapper.updateInterface";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.InterfaceMapper.insertInterface";
		upsert(session, interfaze, updateSqlName, insertSqlName);
	}

	
	public void upsertInterfaceList(SqlSession session, List<Interface> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.InterfaceMapper.updateInterface";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.InterfaceMapper.insertInterface";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
 
	
}
