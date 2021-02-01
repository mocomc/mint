package pep.per.mint.database.mybatis.persistance;

import java.util.List;

import pep.per.mint.common.data.simulation.SimulationRecord;

public interface SimulatorMapper {
	
	public SimulationRecord getLastSimulationRecord();

	public List<SimulationRecord> getSimulationRecordList(String regDate);
	
	public void insertSimulationRecord(SimulationRecord record) throws Exception;
	
	public void updateSimulationRecord(SimulationRecord record) throws Exception;


}
