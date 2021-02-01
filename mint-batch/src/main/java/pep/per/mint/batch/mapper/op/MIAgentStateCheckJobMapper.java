package pep.per.mint.batch.mapper.op;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.MIAgent;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.miagent.MIAgentState;
import pep.per.mint.common.data.basic.miagent.MIRunnerState;

public interface MIAgentStateCheckJobMapper {

	public int insertAgentState(MIAgentState state) throws Exception;

	public int insertRunnerState(MIRunnerState state) throws Exception;

	public List<MIAgent> getMIAgentList(Map<String, String> params) throws Exception;

	public List<Server> getServerList() throws Exception;


	public int updateAgentLastState(MIAgentState state) throws Exception;

	public int updateRunnerLastState(MIRunnerState state) throws Exception;

}
