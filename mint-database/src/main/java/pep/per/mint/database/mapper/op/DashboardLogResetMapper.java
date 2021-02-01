package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface DashboardLogResetMapper {

	public List<Map> getMIRunnerLogCnt() throws Exception;
	public int resetMIRunnerLog(Map params) throws Exception;

	public List<Map> getMIAgentLogCnt() throws Exception;
	public int resetMIAgentLog(Map params) throws Exception;

	public List<Map> getIIPAgentLogCnt() throws Exception;
	public int resetIIPAgentLog(Map params) throws Exception;

	public List<Map> getResourceLogCnt() throws Exception;
	public int resetResourceLog(Map params) throws Exception;

	public List<Map> getProcessLogCnt() throws Exception;
	public int resetProcessLog(Map params) throws Exception;

	public List<Map> getQmgrLogCnt() throws Exception;
	public int resetQmgrLog(Map params) throws Exception;

	public List<Map> getChannelLogCnt() throws Exception;
	public int resetChannelLog(Map params) throws Exception;

	public List<Map> getQueueLogCnt() throws Exception;
	public int resetQueueLog(Map params) throws Exception;


}
