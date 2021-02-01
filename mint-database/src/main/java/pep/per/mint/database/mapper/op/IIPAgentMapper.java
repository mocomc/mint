package pep.per.mint.database.mapper.op;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.ChannelStatusLog;
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QmgrStatusLog;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.QueueStatusLog;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;

public interface IIPAgentMapper {

	public int insertResourceUsageLog(ResourceUsageLog log) throws Exception;
	public int updateLastResourceStatus(ResourceInfo resourceInfo) throws Exception;
	public int updateResourceUsageLog(ResourceUsageLog log) throws Exception;

	public int insertProcessLog(ProcessStatusLog log) throws Exception;
	public int updateLastProcessStatus(ProcessInfo processInfo) throws Exception;
	public int updateProcessLog(ProcessStatusLog log) throws Exception;

	public int insertQmgrStatusLog(QmgrStatusLog qmgrStatusLog)throws Exception;
	public void updateLastQmgrStatus(QmgrInfo qmgrInfo)throws Exception;
	public int updateQmgrStatusLog(QmgrStatusLog qmgrStatusLog)throws Exception;

	public int insertChannelStatusLog(ChannelStatusLog channelStatusLog)throws Exception;
	public void updateLastChannelStatus(ChannelInfo channelInfo)throws Exception;
	public int updateChannelStatusLog(ChannelStatusLog channelStatusLog)throws Exception;

	public int insertQueueStatusLog(QueueStatusLog queueStatusLog)throws Exception;
	public void updateLastQueueStatus(QueueInfo queueInfo) throws Exception;
	public int updateQueueStatusLog(QueueStatusLog queueStatusLog)throws Exception;

	public int insertAgentCommand(CommandConsole cc) throws Exception;

	public List<CommandConsole> getAgentNotExecuteCommands(Map<String,String>params) throws Exception;

	public int updateAgentCommandResult(CommandConsole cc) throws Exception;

	public int insertAllAgentCommand(CommandConsole cc) throws Exception;

	public List<CommandConsole> getAgentCommandResults(Map<String,String>params) throws Exception;

	public List<IIPAgentLog> getIIPAgentLogs(Map<String,String>params) throws Exception;

	public int insertIIPAgentLog(IIPAgentLog log) throws Exception;
	public int updateIIPAgentState(IIPAgentLog log) throws Exception;
	public int updateIIPAgentLog(IIPAgentLog log) throws Exception;
	public int updateIIPAgentStateInit(IIPAgentLog log) throws Exception;

	public int getExecuteCommandCount(@Param("agentNm") String agentNm, @Param("cmdCd") String cmdCd) throws Exception;
	/**
	 * @param agentId
	 * @param cmdCd
	 * @param rsCd
	 * @param rsMsg
	 * @param rsDate
	 * @return
	 */
	public int applyAgentCommandResult(@Param("agentId") String agentId, @Param("cmdCd")String cmdCd, @Param("rsCd")String rsCd, @Param("rsMsg")String rsMsg, @Param("rsDate")String rsDate)throws Exception;
	/**
	 * @param agentId
	 * @param serverIp
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int updateServerAddress(@Param("agentId")String agentId, @Param("serverIp")String serverIp, @Param("modDate")String modDate, @Param("modId")String modId);

}
