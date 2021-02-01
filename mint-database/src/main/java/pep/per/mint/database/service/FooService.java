package pep.per.mint.database.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.Foo;
import pep.per.mint.database.mybatis.dao.FooDao;
import pep.per.mint.database.mybatis.persistance.FooMapper;

@Service
public class FooService {
	
	@Autowired
	FooMapper fooMapper;
	
	@Autowired
	FooDao fooDao;
	
	public List<Foo> getFooListByName(String name){
		return fooMapper.getFooListByName(name);
	}
	
	public Foo getFooById(String id){
		HashMap params = new HashMap();
		params.put("id", id);
		params.put("table","FOO");
		return fooMapper.getFooById(params);
	}
	
	
	public void insertBatch(List<Foo> list) throws Exception{
		fooDao.insertBatch(list);
	}
}


