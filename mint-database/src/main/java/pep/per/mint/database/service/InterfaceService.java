package pep.per.mint.database.service;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.Interface;
import pep.per.mint.common.data.InterfaceList;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mybatis.dao.InterfaceDao;
import pep.per.mint.database.mybatis.persistance.InterfaceMapper;



@Service
public class InterfaceService {
	
	public static Logger logger = LoggerFactory.getLogger(InterfaceService.class);
	
	@Autowired
	InterfaceMapper interfaceMapper;
	
	@Autowired
	InterfaceDao interfaceDao;
	 
	public Interface getInterface(Object id) throws Exception{
		Interface interfaze = interfaceMapper.getInterface(id);
		if(logger.isInfoEnabled()) logger.info(Util.join("InterfaceService.getInterface:", Util.toJSONString(interfaze)));
		return interfaze;
	}
	
	
	/*public List<Interface> getInterfaceListByName(String name) throws Exception{
		Interface interfaze = new Interface();
		interfaze.setName(name);
		List<Interface> list = interfaceMapper.getInterfaceListByName(interfaze);
		return list;
	}*/
	
	public InterfaceList getInterfaceListByName(String name) throws Exception{
		Interface interfaze = new Interface();
		interfaze.setName(name);
		List<Interface> list = interfaceMapper.getInterfaceListByName(interfaze);
		InterfaceList rs = new InterfaceList();
		rs.setList(list);
		//if(logger.isInfoEnabled()) logger.info(Util.join("InterfaceService.getInterfaceListByName:",Util.toJSONString(rs)));
		return rs;
	}
	
	
	public void loadInterfaceData(List<Interface> interfaces, boolean rollbackMode ) throws Exception{
		SqlSession session = null;
		try{
			session = interfaceDao.openSession();
			interfaceDao.upsertInterfaceList(session, interfaces);
			if(rollbackMode) session.rollback();
			else session.commit();
		}catch(Exception e){
			session.rollback();
			throw e;
		}finally{
			if(session != null) session.close();
		}
	}
}
