package pep.per.mint.agent;

import java.io.Serializable;

import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;

public interface AgentLauncherInterface extends Serializable{

	public static final String CMD_DEPLOY_FILE_NAME = "0.dat";

	public void saveRepository(String name, String data) throws Exception;

	public void clearRepository(String name, String data) throws Exception;

	public void setAgentInfo(IIPAgentInfo agentInfo) ;

	public void deploy() throws Exception;

	public void pause()throws Exception;

	public void resume()throws Exception;

	public void checkAlive()throws Exception;

	public void changeInfo()throws Exception;

	public void exit();

	public void sendAgentStateLog(IIPAgentLog log);


}