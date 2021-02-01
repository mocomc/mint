package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.database.mapper.op.DashboardLogResetMapper;

/**
 * Dashboard log reset service 작성
 * @author whoana
 *
 */
@SuppressWarnings("rawtypes")
@Service
public class DashboardLogResetService {

	Logger logger = LoggerFactory.getLogger(DashboardLogResetService.class);

	@Autowired
	DashboardLogResetMapper dashboardLogRestMapper;

	public List<Map> getMIRunnerLogCnt() throws Exception{
		return dashboardLogRestMapper.getMIRunnerLogCnt();
	}
	public int resetMIRunnerLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetMIRunnerLog(params);
	}

	public List<Map> getMIAgentLogCnt() throws Exception{
		return dashboardLogRestMapper.getMIAgentLogCnt();
	}
	public int resetMIAgentLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetMIAgentLog(params);
	}

	public List<Map> getIIPAgentLogCnt() throws Exception{
		return dashboardLogRestMapper.getIIPAgentLogCnt();
	}
	public int resetIIPAgentLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetIIPAgentLog(params);
	}

	public List<Map> getResourceLogCnt() throws Exception{
		return dashboardLogRestMapper.getResourceLogCnt();
	}
	public int resetResourceLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetResourceLog(params);
	}

	public List<Map> getProcessLogCnt() throws Exception{
		return dashboardLogRestMapper.getProcessLogCnt();
	}
	public int resetProcessLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetProcessLog(params);
	}

	public List<Map> getQmgrLogCnt() throws Exception{
		return dashboardLogRestMapper.getQmgrLogCnt();
	}
	public int resetQmgrLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetQmgrLog(params);
	}

	public List<Map> getChannelLogCnt() throws Exception{
		return dashboardLogRestMapper.getChannelLogCnt();
	}
	public int resetChannelLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetChannelLog(params);
	}

	public List<Map> getQueueLogCnt() throws Exception{
		return dashboardLogRestMapper.getQueueLogCnt();
	}
	public int resetQueueLog(Map params) throws Exception{
		return dashboardLogRestMapper.resetQueueLog(params);
	}

}
