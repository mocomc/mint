package pep.per.mint.database.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.DatabaseServer;
import pep.per.mint.common.data.HTTPServer;
import pep.per.mint.common.data.MQServer;
import pep.per.mint.common.data.Server;
import pep.per.mint.common.data.ServerList;
import pep.per.mint.common.data.SocketServer;
import pep.per.mint.common.data.SystemInfo;
import pep.per.mint.common.exception.NotFoundSystemInfoException;
import pep.per.mint.common.exception.UnsupportedServerTypeException;
import pep.per.mint.database.mybatis.dao.SystemDao;
import pep.per.mint.database.mybatis.persistance.SystemMapper;



@Service
public class SystemService {
	
	@Autowired
	SystemMapper systemMapper;
	
	@Autowired
	SystemDao systemDao;
	
	public SystemInfo getSystemInfo(Object id) throws NotFoundSystemInfoException, UnsupportedServerTypeException{
		
		SystemInfo systemInfo = systemMapper.getSystemInfo(id);
		
		if(systemInfo == null) throw new NotFoundSystemInfoException(id);
		List<Server> servierList = systemInfo.getServerList(); 
		List<Server> tmp = new ArrayList<Server>();
		
		for(Server server : servierList){
			Server s = null;
			switch(server.getType()) {
			case Server.SERVER_TYPE_HTTP: 
				s = systemMapper.getHTTPServer(server.getId()); 
				break;
			case Server.SERVER_TYPE_SOCKET: 
				s = systemMapper.getSocketServer(server.getId()); 
				break;
			case Server.SERVER_TYPE_DATABASE: 
				s = systemMapper.getDatabaseServer(server.getId()); 
				break;
			case Server.SERVER_TYPE_MQ: 
				s = systemMapper.getMQServer(server.getId()); 
				break;				
			default :
				throw new UnsupportedServerTypeException("server[id("+ server.getId() +"),type(" + server.getType() + ")] is not supported!");
			}
			
			tmp.add(s);
		}
		
		systemInfo.setServerList(tmp);
		return systemInfo;
	}
	
	public void loadSystemData(List<SystemInfo> systems, List<ServerList> serverLists, List<Server> servers, boolean rollbackMode ) throws Exception{
		SqlSession session = null;
		try{
			
			session = systemDao.openSession();
			
			systemDao.upsertSystemInfoList(session, systems);
			for(Server server : servers){
				int type = server.getType();
				switch(type){
				case Server.SERVER_TYPE_DATABASE :
					DatabaseServer ds = (DatabaseServer)server;
					systemDao.upsertDatabaseServer(session, ds);
					break;
				case Server.SERVER_TYPE_HTTP :
					HTTPServer https = (HTTPServer)server;
					systemDao.upsertHTTPServer(session, https);
					break;
				case Server.SERVER_TYPE_MQ :
					MQServer mqs = (MQServer)server;
					systemDao.upsertMQServer(session, mqs);
					break;
				case Server.SERVER_TYPE_SOCKET :
					SocketServer ss = (SocketServer)server;
					systemDao.upsertSocketServer(session, ss);
					break;
				default :
					throw new pep.per.mint.common.exception.UnsupportedTypeException("지원하지 않는 서버유형("+ type +")입니다.");
				}
			}
			
			
			systemDao.upsertServerList(session, serverLists);
			
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
