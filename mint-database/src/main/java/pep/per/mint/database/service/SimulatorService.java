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
import pep.per.mint.common.data.simulation.SimulationRecord;
import pep.per.mint.common.exception.NotFoundSystemInfoException;
import pep.per.mint.common.exception.UnsupportedServerTypeException;
import pep.per.mint.database.mybatis.dao.SystemDao;
import pep.per.mint.database.mybatis.persistance.SimulatorMapper;
import pep.per.mint.database.mybatis.persistance.SystemMapper;



@Service
public class SimulatorService {
	
	@Autowired
	SimulatorMapper simulatorMapper;
	
	
	public SimulationRecord getLastSimulationRecord() {
		SimulationRecord record = simulatorMapper.getLastSimulationRecord();
		return record;
	}
	
	public List<SimulationRecord> getSimulationRecordList(String regDate) {
		List<SimulationRecord> list = simulatorMapper.getSimulationRecordList(regDate);
		return list;
	}
	 
	
	public void insertSimulationRecord(SimulationRecord record) throws Exception{
		simulatorMapper.insertSimulationRecord(record);
	}
	
	public void updateSimulationRecord(SimulationRecord record) throws Exception{
		simulatorMapper.updateSimulationRecord(record);
	}
}
