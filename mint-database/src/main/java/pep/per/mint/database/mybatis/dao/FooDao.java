package pep.per.mint.database.mybatis.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.Foo;
 

@Component
public class FooDao extends AbstractDao{
	
	@Autowired
	SqlSessionFactoryBean sqlSessionFactioryBean;
	
	public void insertBatch(List<Foo> list) throws Exception {
		//String sqlName = "fooInsert";
		String sqlName = "pep.per.mint.database.mybatis.persistance.FooMapper.fooInsert";
		insertBatch(list, sqlName);
	}
	
	/*public void insertFooList(List<Foo> foos) {
		SqlSessionFactory sqlSessionFactiory = null;
	    SqlSession session = null;
	    try {
	    	sqlSessionFactiory = sqlSessionFactioryBean.getObject();
		    session = sqlSessionFactiory.openSession(ExecutorType.BATCH);
	        for(Foo foo : foos)
	        {
	          session.insert("fooInsert",foo);
	        }
	        session.commit();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	        session.close();
	    }   
	}*/
}
