package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.ChannelStatusLog;
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.QmgrStatusLog;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.QueueStatusLog;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.database.mapper.op.IIPAgentMapper;

@Service
public class IIPAgentService {

	@Autowired
	IIPAgentMapper iipAgentMapper;

	@Transactional
	public int addResourceLogs(List<ResourceUsageLog> logs) throws Exception {
		int res = 0;
		for(ResourceUsageLog log : logs){
			res = res + iipAgentMapper.insertResourceUsageLog(log);
			String modDate = log.getGetDate();
			log.getResourceInfo().setModDate(modDate);
			iipAgentMapper.updateLastResourceStatus(log.getResourceInfo());
		}
		return res;
	}

	@Transactional
	public int upsertResourceLogs(List<ResourceUsageLog> logs) throws Exception {
		int res = 0;
		for(ResourceUsageLog log : logs){
			res = iipAgentMapper.updateResourceUsageLog(log);
			if(res < 1) {
				res = iipAgentMapper.insertResourceUsageLog(log);
			}
			String modDate = log.getGetDate();
			log.getResourceInfo().setModDate(modDate);
			iipAgentMapper.updateLastResourceStatus(log.getResourceInfo());
		}
		return res;
	}

	@Transactional
	public int addProcessLogs(List<ProcessStatusLog> logs) throws Exception {
		int res = 0;
		for(ProcessStatusLog log : logs){
			res = res + iipAgentMapper.insertProcessLog(log);
			String modDate = log.getGetDate();
			log.getProcessInfo().setModDate(modDate);
			iipAgentMapper.updateLastProcessStatus(log.getProcessInfo());
		}
		return res;
	}

	@Transactional
	public int upsertProcessLogs(List<ProcessStatusLog> logs) throws Exception {
		int res = 0;
		for(ProcessStatusLog log : logs){
			res =  iipAgentMapper.updateProcessLog(log);
			if(res < 1) {
				res = iipAgentMapper.insertProcessLog(log);
			}
			String modDate = log.getGetDate();
			log.getProcessInfo().setModDate(modDate);
			iipAgentMapper.updateLastProcessStatus(log.getProcessInfo());
		}
		return res;
	}

	@Transactional
	public int addQmgrLogs(List<QmgrLogs> logs) throws Exception {
		int res = 0;

		for(QmgrLogs log : logs){
			QmgrStatusLog qmgrStatusLog = log.getQmgrStatusLog();
			if(qmgrStatusLog != null) {

				res = res + iipAgentMapper.insertQmgrStatusLog(qmgrStatusLog);
				//-------------------------------------------------------------------
				//에이전트에서 보내주지 않도록 수정하여 서비스내에서 QmgrInfo는 새로 만들도록 한다.
				//-------------------------------------------------------------------
				//update : 20180705
				//log.getQmgrInfo().setModDate(qmgrStatusLog.getGetDate());
				//iipAgentMapper.updateLastQmgrStatus(log.getQmgrInfo());
				QmgrInfo qmgrInfo = new QmgrInfo();
				qmgrInfo.setQmgrId(qmgrStatusLog.getQmgrId());
				qmgrInfo.setStatus(qmgrStatusLog.getStatus());
				qmgrInfo.setModDate(qmgrStatusLog.getGetDate());
				qmgrInfo.setModId(qmgrStatusLog.getRegApp());
				iipAgentMapper.updateLastQmgrStatus(qmgrInfo);
			}
			List<ChannelStatusLog> channelStatusLogs = log.getChannelStatusLogs();
			if(channelStatusLogs != null) {
				for(ChannelStatusLog channelStatusLog : channelStatusLogs){
					res = res + iipAgentMapper.insertChannelStatusLog(channelStatusLog);
					//-------------------------------------------------------------------
					//에이전트에서 보내주지 않도록 수정하여 서비스내에서 ChannelInfo는 새로 만들도록 한다.
					//-------------------------------------------------------------------
					//update : 20180705
					channelStatusLog.getChannelInfo().setModDate(channelStatusLog.getGetDate());
					iipAgentMapper.updateLastChannelStatus(channelStatusLog.getChannelInfo());
//					ChannelInfo channelInfo = new ChannelInfo();
//					channelInfo.setQmgrId(channelStatusLog.getQmgrId());
//					channelInfo.setChannelId(channelStatusLog.getChannelId());
//					channelInfo.setStatus(channelStatusLog.getStatus());
//					channelInfo.setModDate(channelStatusLog.getGetDate());
//					channelInfo.setModId(channelStatusLog.getRegApp());
//					iipAgentMapper.updateLastChannelStatus(channelInfo);
				}
			}

			List<QueueStatusLog> queueStatusLogs = log.getQueueStatusLogs();
			if(queueStatusLogs != null) {
				for(QueueStatusLog queueStatusLog : queueStatusLogs){
					res = res + iipAgentMapper.insertQueueStatusLog(queueStatusLog);
					//-------------------------------------------------------------------
					//에이전트에서 보내주지 않도록 수정하여 서비스내에서 QueueInfo는 새로 만들도록 한다.
					//-------------------------------------------------------------------
					//update : 20180705
					queueStatusLog.getQueueInfo().setModDate(queueStatusLog.getGetDate());
					iipAgentMapper.updateLastQueueStatus(queueStatusLog.getQueueInfo());
//					QueueInfo queueInfo = new QueueInfo();
//					queueInfo.setQmgrId(queueStatusLog.getQmgrId());
//					queueInfo.setQueueId(queueStatusLog.getQueueId());
//					queueInfo.setStatus(queueStatusLog.getRelativeDepth() < 100 ? QueueStatusLog.STATUS_NORMAL : QueueStatusLog.STATUS_ABNORMAL);
//					queueInfo.setModDate(queueStatusLog.getGetDate());
//					queueInfo.setModId(queueStatusLog.getRegApp());
//					iipAgentMapper.updateLastQueueStatus(queueStatusLog.getQueueInfo());
				}
			}
		}

		return res;
	}

	@Transactional
	public int upsertQmgrLogs(List<QmgrLogs> logs) throws Exception {
		int res = 0;

		for(QmgrLogs log : logs){
			QmgrStatusLog qmgrStatusLog = log.getQmgrStatusLog();
			if(qmgrStatusLog != null) {

				res = iipAgentMapper.updateQmgrStatusLog(qmgrStatusLog);
				if(res < 1) {
					res = iipAgentMapper.insertQmgrStatusLog(qmgrStatusLog);
				}
				QmgrInfo qmgrInfo = new QmgrInfo();
				qmgrInfo.setQmgrId(qmgrStatusLog.getQmgrId());
				qmgrInfo.setStatus(qmgrStatusLog.getStatus());
				qmgrInfo.setModDate(qmgrStatusLog.getGetDate());
				qmgrInfo.setModId(qmgrStatusLog.getRegApp());
				iipAgentMapper.updateLastQmgrStatus(qmgrInfo);
			}

			List<ChannelStatusLog> channelStatusLogs = log.getChannelStatusLogs();
			if(channelStatusLogs != null) {
				for(ChannelStatusLog channelStatusLog : channelStatusLogs){
					res = iipAgentMapper.updateChannelStatusLog(channelStatusLog);
					if(res < 1) {
						res = iipAgentMapper.insertChannelStatusLog(channelStatusLog);
					}
					channelStatusLog.getChannelInfo().setModDate(channelStatusLog.getGetDate());
					iipAgentMapper.updateLastChannelStatus(channelStatusLog.getChannelInfo());
				}
			}

			List<QueueStatusLog> queueStatusLogs = log.getQueueStatusLogs();
			if(queueStatusLogs != null) {
				for(QueueStatusLog queueStatusLog : queueStatusLogs){
					res = iipAgentMapper.updateQueueStatusLog(queueStatusLog);
					if(res < 1) {
						res = iipAgentMapper.insertQueueStatusLog(queueStatusLog);
					}
					queueStatusLog.getQueueInfo().setModDate(queueStatusLog.getGetDate());
					iipAgentMapper.updateLastQueueStatus(queueStatusLog.getQueueInfo());
				}
			}
		}

		return res;
	}



	public int insertAgentCommand(CommandConsole cc) throws Exception{
		return iipAgentMapper.insertAgentCommand(cc);
	}

	public List<CommandConsole> getAgentNotExecuteCommands(Map<String,String>params) throws Exception{
		// TODO Auto-generated method stub
		return iipAgentMapper.getAgentNotExecuteCommands(params);
	}


	public int updateAgentCommandResult(CommandConsole cc)throws Exception{
		// TODO Auto-generated method stub
		return iipAgentMapper.updateAgentCommandResult(cc);
	}


	public List<CommandConsole> getAgentCommandResults(Map<String,String>params) throws Exception{
		return iipAgentMapper.getAgentCommandResults(params);
	}

	public int insertAllAgentCommand(CommandConsole cc) throws Exception{
		return iipAgentMapper.insertAllAgentCommand(cc);
	}

	public List<IIPAgentLog> getIIPAgentLogs(Map<String,String>params) throws Exception{
		return iipAgentMapper.getIIPAgentLogs(params);
	}

	@Transactional
	public int insertIIPAgentLog(IIPAgentLog log) throws Exception{

		int res = iipAgentMapper.insertIIPAgentLog(log);
		res = iipAgentMapper.updateIIPAgentState(log);
		return res;
	}

	@Transactional
	public int upsertIIPAgentLog(IIPAgentLog log) throws Exception{
		int res = iipAgentMapper.updateIIPAgentLog(log);
		if(res < 1) {
			res = iipAgentMapper.insertIIPAgentLog(log);
		}
		res = iipAgentMapper.updateIIPAgentState(log);
		return res;
	}

	public int updateIIPAgentState(IIPAgentLog log) throws Exception{
		return iipAgentMapper.updateIIPAgentState(log);
	}

	public int getExecuteCommandCount(String agentNm, String cmdCd) throws Exception {
		return iipAgentMapper.getExecuteCommandCount(agentNm, cmdCd);
	}

	/**
	 * @param agentId
	 * @param cmdCd
	 * @param rsCd
	 * @param rsMsg
	 * @param rsDate
	 * @throws Exception
	 */
	public int applyAgentCommandResult(String agentId, String cmdCd, String rsCd, String rsMsg, String rsDate) throws Exception {
		// TODO Auto-generated method stub
		return iipAgentMapper.applyAgentCommandResult(agentId, cmdCd, rsCd, rsMsg, rsDate);
	}


	/**
	 * @param agentId
	 * @param serverId
	 * @param modDate
	 * @param modId
	 */
	public int updateServerAddress(String agentId, String serverIp, String modDate, String modId) {
		return iipAgentMapper.updateServerAddress(agentId, serverIp, modDate, modId);
	}

	public int agentReset(IIPAgentLog log) throws Exception {
		return iipAgentMapper.updateIIPAgentStateInit(log);
	}

}
