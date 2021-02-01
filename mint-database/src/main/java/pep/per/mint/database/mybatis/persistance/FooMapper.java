package pep.per.mint.database.mybatis.persistance;

import java.util.List;
import java.util.HashMap;

import pep.per.mint.common.data.Foo;

public interface FooMapper {

	public List<Foo> getFooListByName(String name);
	
	public Foo getFooById(HashMap params);
	
}
