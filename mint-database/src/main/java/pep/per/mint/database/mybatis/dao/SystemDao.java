package pep.per.mint.database.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.DatabaseServer;
import pep.per.mint.common.data.HTTPServer;
import pep.per.mint.common.data.MQServer;
import pep.per.mint.common.data.Server;
import pep.per.mint.common.data.ServerList;
import pep.per.mint.common.data.SocketServer;
import pep.per.mint.common.data.SystemInfo;

@Component
public class SystemDao extends AbstractDao {
	
	
	public void insertSystemInfo(SqlSession session, SystemInfo systemInfo) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSystemInfo";
		insert(session, systemInfo, sqlName);
	}

	
	public void insertSystemInfoList(SqlSession session, List<SystemInfo> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSystemInfo";
		insertList(session, list, sqlName);
	}

	public void upsertSystemInfo(SqlSession session, SystemInfo systemInfo) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateSystemInfo";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSystemInfo";
		upsert(session, systemInfo, updateSqlName, insertSqlName);
	}

	
	public void upsertSystemInfoList(SqlSession session, List<SystemInfo> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateSystemInfo";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSystemInfo";
		upsertList(session, list, updateSqlName, insertSqlName);
	}

	
	public void insertServerList(SqlSession session, List<ServerList> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertServerList";
		insertList(session, list, sqlName);
	}

	public void upsertServerList(SqlSession session, List<ServerList> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateServerList";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertServerList";
		upsertList(session, list, updateSqlName, insertSqlName);
	}

	
	public void insertServer(SqlSession session, Server server) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertServer";
		insert(session, server, sqlName);
	}
	 
	public void upsertServer(SqlSession session, Server server) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertServer";
		upsert(session, server, updateSqlName, insertSqlName);
	}
	
	public void insertHTTPServer(SqlSession session, HTTPServer server) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertHTTPServer";
		insert(session, server, sqlName);
	}
	
	public void insertHTTPServerList(SqlSession session, List<HTTPServer> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertHTTPServer";
		insertList(session, list, sqlName);
	}
	
	public void upsertHTTPServer(SqlSession session, HTTPServer server) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateHTTPServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertHTTPServer";
		upsert(session, server, updateSqlName, insertSqlName);
	}
	
	public void upsertHTTPServerList(SqlSession session, List<HTTPServer> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateHTTPServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertHTTPServer";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	
	public void insertSocketServer(SqlSession session, SocketServer server) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSocketServer";
		insert(session, server, sqlName);
	}
	
	public void insertSocketServerList(SqlSession session, List<SocketServer> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSocketServer";
		insertList(session, list, sqlName);
	}
	
	public void upsertSocketServer(SqlSession session, SocketServer server) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateSocketServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSocketServer";
		upsert(session, server, updateSqlName, insertSqlName);
	}
	
	public void upsertSocketServerList(SqlSession session, List<SocketServer> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateSocketServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertSocketServer";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	public void insertDatabaseServer(SqlSession session, DatabaseServer server) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertDatabaseServer";
		insert(session, server, sqlName);
	}
	
	public void insertDatabaseServerList(SqlSession session, List<DatabaseServer> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertDatabaseServer";
		insertList(session, list, sqlName);
	}

	public void upsertDatabaseServer(SqlSession session, DatabaseServer server) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateDatabaseServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertDatabaseServer";
		upsert(session, server, updateSqlName, insertSqlName);

	}
	
	public void upsertDatabaseServerList(SqlSession session, List<DatabaseServer> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateDatabaseServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertDatabaseServer";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	public void insertMQServer(SqlSession session, MQServer server) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertMQServer";
		insert(session, server, sqlName);
	}
	
	public void insertMQServerList(SqlSession session, List<MQServer> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertMQServer";
		insertList(session, list, sqlName);
	}
	
	
	public void upsertMQServer(SqlSession session, MQServer server) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateMQServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertMQServer";
		upsert(session, server, updateSqlName, insertSqlName);

	}
	
	public void upsertMQServerList(SqlSession session, List<MQServer> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.updateMQServer";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.SystemMapper.insertMQServer";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
}
