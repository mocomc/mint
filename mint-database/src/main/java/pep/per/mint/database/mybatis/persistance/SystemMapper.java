package pep.per.mint.database.mybatis.persistance;

import pep.per.mint.common.data.DatabaseServer;
import pep.per.mint.common.data.HTTPServer;
import pep.per.mint.common.data.MQServer;
import pep.per.mint.common.data.ServerList;
import pep.per.mint.common.data.SocketServer;
import pep.per.mint.common.data.SystemInfo;

public interface SystemMapper {
	
	public SystemInfo getSystemInfo(Object systemId);

	public SocketServer getSocketServer(Object serverId);
	
	public HTTPServer getHTTPServer(Object serverId);
	
	public MQServer getMQServer(Object serverId);
	
	public DatabaseServer getDatabaseServer(Object serverId);
	
	public void insertSystemInfo(SystemInfo systemInfo) throws Exception;
	
	public void insertServerList(ServerList serverList) throws Exception;
	
	public void insertHTTPServer(HTTPServer server) throws Exception;
	
	public void insertSocketServer(SocketServer server) throws Exception;
	
	public void insertDatabaseServer(DatabaseServer server) throws Exception;
	
	public void insertMQServer(MQServer server) throws Exception;
	
}
