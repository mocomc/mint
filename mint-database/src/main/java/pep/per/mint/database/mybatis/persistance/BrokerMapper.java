package pep.per.mint.database.mybatis.persistance;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.broker.BrokerInfo;
import pep.per.mint.common.data.broker.FailTerminalInfo;
import pep.per.mint.common.data.broker.InputTerminalInfo;
import pep.per.mint.common.data.broker.LinkInfo;
import pep.per.mint.common.data.broker.ListenerGroup;
import pep.per.mint.common.data.broker.ListenerServiceInfo;
import pep.per.mint.common.data.broker.OutputTerminalInfo;
import pep.per.mint.common.data.broker.ServiceGroup;
import pep.per.mint.common.data.broker.ServiceInfo;
import pep.per.mint.common.data.broker.TaskInfo;

public interface BrokerMapper {
	
	public List<ServiceGroup> getServiceGroupList(String brokerId) throws Exception;
	
	public ServiceGroup getServiceGroup(String groupId) throws Exception;
	
	public List<Map<String, String>> getTaskProperties(String taskId) throws Exception;
	
	public List<OutputTerminalInfo> getOutputTerminalInfoList(String taskId) throws Exception; 
	
	public List<LinkInfo> getFailTerminalLinkInfoList(String terminalId) throws Exception;
	
	//public List<LinkInfo> getLinkInfoList(String outputTerminalId) throws Exception;
	
	/*public List<String> getServiceInfoList(String groupId) throws Exception;
	
	public ServiceInfo getServiceInfo(String serviceId) throws Exception;
	
	public List<Map> getTaskInfoList(String serviceId) throws Exception;
	
	public List<Map> getInputTaskInfoList(String serviceId) throws Exception;
	
	public TaskInfo getTaskInfo(String taskId) throws Exception;
	
	public Map getTaskProperties(String taskId) throws Exception;*/
	
	
	public ListenerGroup getListenerGroup(String groupId) throws Exception;
	
	public List<Map<String, String>> getListenerProperties(String listenerId) throws Exception;
	
	public BrokerInfo getBrokerInfo(String brokerId)  throws Exception;
	
	//===================================================================
	//insert broker info
	//-------------------------------------------------------------------
	public int insertListenerGroup(ListenerGroup group) throws Exception;
	
	public int insertListenerServiceInfo(ListenerServiceInfo listenerServiceInfo) throws Exception;
	
	public int insertListenerProperty(Map params) throws Exception;
	
	public int insertBrokerInfo(BrokerInfo brokerInfo) throws Exception;
	
	public int insertServiceGroup(ServiceGroup serviceGroup) throws Exception;
	
	public int insertServiceInfo(ServiceInfo serviceInfo) throws Exception;
	
	public int insertTaskInfo(TaskInfo taskInfo) throws Exception;
	
	public int insertTaskProperty(Map params) throws Exception;
	
	public int insertOutputTerminalInfo(OutputTerminalInfo outputTerminalInfo) throws Exception;
	
	public int insertOutputTerminalList(Map params) throws Exception;
	
	public int insertFailTerminalInfo(FailTerminalInfo failTerminalInfo) throws Exception;
	
	public int insertLinkInfo(LinkInfo linkInfo) throws Exception;
	
	public int insertInputTerminalInfo(InputTerminalInfo inputTerminalInfo) throws Exception;
	
	public int insertTerminalLinks(Map params) throws Exception;
	
	public int insertServiceGroupList(Map params) throws Exception;
	
	public String existTask(String id) throws Exception;
	
	public int insertTaskList(Map params) throws Exception;
}
