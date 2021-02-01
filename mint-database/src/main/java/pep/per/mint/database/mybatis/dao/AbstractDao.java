package pep.per.mint.database.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbstractDao {
	@Autowired
	SqlSessionFactoryBean sqlSessionFactioryBean;
	
	/*public <T> void insertBatch(List<T> list, String sqlName) throws Exception{
		//SqlSessionFactory sqlSessionFactiory = null;
	    SqlSession session = null;
	    try {
	    	//sqlSessionFactiory = sqlSessionFactioryBean.getObject();
		    session = openSession();
		    
		    for (T t : list) {
		    	session.insert(sqlName,t);
			}
		    session.commit();
	    } finally {
	        session.close();
	    }
	}*/
	
	public <T> void insertBatch(SqlSession session, List<T> list, String sqlName) throws Exception{
	    for (T t : list) {
	    	session.insert(sqlName,t);
		}
	}
	
	
	public <T> void insertBatch(List<T> list, String sqlName) throws Exception{
		//SqlSessionFactory sqlSessionFactiory = null;
	    SqlSession session = null;
	    try {
	    	//sqlSessionFactiory = sqlSessionFactioryBean.getObject();
		    session = openSession(ExecutorType.BATCH);
		    
		    for (T t : list) {
		    	session.insert(sqlName,t);
			}
		    session.commit();
	    }catch(Exception e){
	    	session.rollback();
	    	throw e;
	    } finally {
	        session.close();
	    }
	}
	
	public SqlSession openSession(ExecutorType executorType) throws Exception{
		SqlSessionFactory sqlSessionFactiory = null;
	    SqlSession session = null;
    	sqlSessionFactiory = sqlSessionFactioryBean.getObject();
    	
	    session = executorType == null ? sqlSessionFactiory.openSession() : sqlSessionFactiory.openSession(ExecutorType.BATCH);
		return session;    
	}
	
	public SqlSession openSession() throws Exception{
		return openSession(null) ;    
	}
	
	public <T> void insertList(SqlSession session, List<T> list, String sqlName) throws Exception{
	    for (T t : list) {
	    	session.insert(sqlName,t);
		}
	}
	
	public <T> void insert(SqlSession session, T t, String sqlName) throws Exception{
		session.insert(sqlName,t);
	}
	
	public <T> void upsertList(SqlSession session, List<T> list, String updateSqlName, String insertSqlName) throws Exception{
	    for (T t : list) {
	    	int res = session.update(updateSqlName,t);
	    	if(res < 1) session.insert(insertSqlName,t);
		}
	}
	
	public <T> void upsert(SqlSession session, T t,String updateSqlName, String insertSqlName) throws Exception{
		int res = session.update(updateSqlName,t);
    	if(res < 1) session.insert(insertSqlName,t);
	}
	
}
