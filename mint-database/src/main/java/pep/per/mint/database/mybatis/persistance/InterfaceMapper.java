package pep.per.mint.database.mybatis.persistance;

import java.util.List; 


import pep.per.mint.common.data.Interface;

public interface InterfaceMapper {

	public Interface getInterface(Object interfaceId) throws Exception;
	 
	public List<Interface> getInterfaceListByName(Interface interfaze) throws Exception;
	
	public void insertInterface(Interface interfaze) throws Exception;
	
}

