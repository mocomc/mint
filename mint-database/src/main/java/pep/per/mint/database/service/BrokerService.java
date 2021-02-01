package pep.per.mint.database.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.accessory.LogVariables;
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
import pep.per.mint.common.exception.NotFoundException;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mybatis.persistance.BrokerMapper;



@Service
public class BrokerService {
	
	public static Logger logger = LoggerFactory.getLogger(BrokerService.class);
	
	@Autowired
	BrokerMapper mapper;
	
	//@Autowired
	//BrokerDao brokerDao;
	
	
	public BrokerInfo getBrokerInfo(String brokerId) throws Exception{
		
		BrokerInfo brokerInfo = mapper.getBrokerInfo(brokerId);
		
		if(brokerInfo == null) {
			throw new NotFoundException(Util.join("mint:runtime:exception:There is no BrokerInfo(id:", brokerId,")"));
		}
		
		List<ServiceGroup> serviceGroupList = mapper.getServiceGroupList(brokerId);
		
		for (ServiceGroup serviceGroup : serviceGroupList) {
			
			List<ServiceInfo> list = serviceGroup.getServiceInfoList();
			for (ServiceInfo serviceInfo : list) {
				List<TaskInfo> inputTaskList = serviceInfo.getInputTaskInfoList();
				for (TaskInfo taskInfo : inputTaskList) {
					String taskId = Util.toString(taskInfo.getId());
					List<Map<String,String>> propertiyMapList = mapper.getTaskProperties(taskId);
					Properties properties = new Properties();
					for (Map<String, String> map : propertiyMapList) {
						String key = map.get("key");
						String value = map.get("value");
						properties.put(key, value);
					}
					taskInfo.setProperties(properties);
					
					List<OutputTerminalInfo>  otl = mapper.getOutputTerminalInfoList(taskId);
					if(otl != null && otl.size() > 0){
						LinkedHashMap<Object, OutputTerminalInfo> map = new LinkedHashMap<Object, OutputTerminalInfo>();
						for (OutputTerminalInfo outputTerminalInfo : otl) {
							String key = outputTerminalInfo.getName();
							map.put(key, outputTerminalInfo);
							
							//next task setting
							//setNextTaskInfo(outputTerminalInfo);
							List<LinkInfo> linkInfoList = outputTerminalInfo.getLinkInfoList();
							if(linkInfoList != null && linkInfoList.size() > 0) setNextTaskInfo(linkInfoList);
						}
						taskInfo.setOutputTerminalInfoMap(map);
					}
					
					FailTerminalInfo ftmlInfo = taskInfo.getFailTerminalInfo();
					if(ftmlInfo != null){
						Object failTerminalId = ftmlInfo.getId();
						List<LinkInfo> linkInfoList = mapper.getFailTerminalLinkInfoList(Util.toString(failTerminalId));
						ftmlInfo.setLinkInfoList(linkInfoList);
						if(linkInfoList != null && linkInfoList.size() > 0) setNextTaskInfo(linkInfoList);
					}
					
					logger.info(Util.join(LogVariables.logPrefix, "TaskInfo:", Util.toJSONString(taskInfo)));
				}
				
			}
		}
		
		
		
		
		brokerInfo.setServiceGroupList(serviceGroupList);
		
		ListenerGroup listenerGroup = brokerInfo.getListenerGroup();
		if(listenerGroup != null){
			List<ListenerServiceInfo> list = listenerGroup.getListenerServiceInfoList();
			for (ListenerServiceInfo listenerServiceInfo : list) {
			 	String listenerId = Util.toString(listenerServiceInfo.getId());
				List<Map<String,String>> propertiyMapList = mapper.getListenerProperties(listenerId);
				Properties properties = new Properties();
				for (Map<String, String> map : propertiyMapList) {
					String key = map.get("key");
					String value = map.get("value");
					properties.put(key, value);
				}
				listenerServiceInfo.setProperties(properties);
			}
		}
		
		return brokerInfo;
	
	}
	
	
	public ServiceGroup getServiceGroup(String groupId) throws Exception{
		
		ServiceGroup serviceGroup = mapper.getServiceGroup(groupId);
		
		if(serviceGroup == null) {
			throw new NotFoundException(Util.join("mint:runtime:exception:There is no ServiceGroup(groupId:", groupId,")"));
		}
		
		List<ServiceInfo> list = serviceGroup.getServiceInfoList();
		for (ServiceInfo serviceInfo : list) {
			List<TaskInfo> inputTaskList = serviceInfo.getInputTaskInfoList();
			for (TaskInfo taskInfo : inputTaskList) {
				String taskId = Util.toString(taskInfo.getId());
				List<Map<String,String>> propertiyMapList = mapper.getTaskProperties(taskId);
				Properties properties = new Properties();
				for (Map<String, String> map : propertiyMapList) {
					String key = map.get("key");
					String value = map.get("value");
					properties.put(key, value);
				}
				taskInfo.setProperties(properties);
				
				List<OutputTerminalInfo>  otl = mapper.getOutputTerminalInfoList(taskId);
				if(otl != null && otl.size() > 0){
					LinkedHashMap<Object, OutputTerminalInfo> map = new LinkedHashMap<Object, OutputTerminalInfo>();
					for (OutputTerminalInfo outputTerminalInfo : otl) {
						String key = outputTerminalInfo.getName();
						map.put(key, outputTerminalInfo);
						
						//next task setting
						List<LinkInfo> linkInfoList = outputTerminalInfo.getLinkInfoList();
						//setNextTaskInfo(outputTerminalInfo);
						if(linkInfoList != null && linkInfoList.size() > 0) setNextTaskInfo(linkInfoList);
					}
					taskInfo.setOutputTerminalInfoMap(map);
				}
				
				FailTerminalInfo ftmlInfo = taskInfo.getFailTerminalInfo();
				if(ftmlInfo != null){
					Object failTerminalId = ftmlInfo.getId();
					List<LinkInfo> linkInfoList = mapper.getFailTerminalLinkInfoList(Util.toString(failTerminalId));
					ftmlInfo.setLinkInfoList(linkInfoList);
					if(linkInfoList != null && linkInfoList.size() > 0) setNextTaskInfo(linkInfoList);
				}
				
				logger.info(Util.join(LogVariables.logPrefix, "TaskInfo:", Util.toJSONString(taskInfo)));
			}
			
		}
		
		return serviceGroup;
	}

	/*private void setNextTaskInfo(OutputTerminalInfo outputTerminalInfo) throws Exception {
		List<LinkInfo> linkInfoList = outputTerminalInfo.getLinkInfoList();
		for (LinkInfo linkInfo : linkInfoList) {
			InputTerminalInfo inputTerminalInfo = linkInfo.getInputTerminalInfo();
			TaskInfo taskInfo = inputTerminalInfo.getTaskInfo();
			


			
			String taskId = Util.toString(taskInfo.getId());
			List<Map<String,String>> propertiyMapList = mapper.getTaskProperties(taskId);
			Properties properties = new Properties();
			for (Map<String, String> map : propertiyMapList) {
				String key = map.get("key");
				String value = map.get("value");
				properties.put(key, value);
			}
			taskInfo.setProperties(properties);
			
			List<OutputTerminalInfo>  otl = mapper.getOutputTerminalInfoList(taskId);
			if(otl != null && otl.size() > 0){
				LinkedHashMap<Object, OutputTerminalInfo> map = new LinkedHashMap<Object, OutputTerminalInfo>();
				for (OutputTerminalInfo otmlInfo : otl) {
					String key = otmlInfo.getName();
					map.put(key, otmlInfo);
					
					//next task setting
					setNextTaskInfo(otmlInfo);
				}
				taskInfo.setOutputTerminalInfoMap(map);
			}
			
			FailTerminalInfo ftmlInfo = taskInfo.getFailTerminalInfo();
			if(ftmlInfo != null){
				Object failTerminalId = ftmlInfo.getId();
				List<LinkInfo> fialLinkInfoList = mapper.getFailTerminalLinkInfoList(Util.toString(failTerminalId));
				ftmlInfo.setLinkInfoList(fialLinkInfoList);
			}
			
			logger.info(Util.join(LogVariables.logPrefix, "TaskInfo:", Util.toJSONString(taskInfo)));
			
		}
	}*/
	
	
	
	private void setNextTaskInfo(List<LinkInfo> linkInfoList) throws Exception {
		for (LinkInfo linkInfo : linkInfoList) {
			InputTerminalInfo inputTerminalInfo = linkInfo.getInputTerminalInfo();
			TaskInfo taskInfo = inputTerminalInfo.getTaskInfo();
			


			
			String taskId = Util.toString(taskInfo.getId());
			List<Map<String,String>> propertiyMapList = mapper.getTaskProperties(taskId);
			Properties properties = new Properties();
			for (Map<String, String> map : propertiyMapList) {
				String key = map.get("key");
				String value = map.get("value");
				properties.put(key, value);
			}
			taskInfo.setProperties(properties);
			
			List<OutputTerminalInfo>  otl = mapper.getOutputTerminalInfoList(taskId);
			if(otl != null && otl.size() > 0){
				LinkedHashMap<Object, OutputTerminalInfo> map = new LinkedHashMap<Object, OutputTerminalInfo>();
				for (OutputTerminalInfo otmlInfo : otl) {
					if(otmlInfo != null){
						String key = otmlInfo.getName();
						map.put(key, otmlInfo);
						
						//next task setting
						List<LinkInfo> outputTerminalLinkList = otmlInfo.getLinkInfoList();
						if(outputTerminalLinkList != null && outputTerminalLinkList.size() > 0) setNextTaskInfo(outputTerminalLinkList);
					}
				}
				taskInfo.setOutputTerminalInfoMap(map);
			}
			
			FailTerminalInfo ftmlInfo = taskInfo.getFailTerminalInfo();
			if(ftmlInfo != null){
				Object failTerminalId = ftmlInfo.getId();
				List<LinkInfo> fialLinkInfoList = mapper.getFailTerminalLinkInfoList(Util.toString(failTerminalId));
				ftmlInfo.setLinkInfoList(fialLinkInfoList);
				if(fialLinkInfoList != null && fialLinkInfoList.size() > 0) setNextTaskInfo(fialLinkInfoList);
			}
			
			logger.info(Util.join(LogVariables.logPrefix, "TaskInfo:", Util.toJSONString(taskInfo)));
			
		}
	}
	
	
	public ListenerGroup getListenerGroup(String groupId) throws Exception{
		
		ListenerGroup listenerGroup = mapper.getListenerGroup(groupId);
		List<ListenerServiceInfo> list = listenerGroup.getListenerServiceInfoList();
		for (ListenerServiceInfo listenerServiceInfo : list) {
		 	String listenerId = Util.toString(listenerServiceInfo.getId());
			List<Map<String,String>> propertiyMapList = mapper.getListenerProperties(listenerId);
			Properties properties = new Properties();
			for (Map<String, String> map : propertiyMapList) {
				String key = map.get("key");
				String value = map.get("value");
				properties.put(key, value);
			}
			listenerServiceInfo.setProperties(properties);
		}
		
		return listenerGroup;
	}
	
	
	
	/**
	 * 두가지 문제점 체크해 볼것 
	 * 1) TB_RUL_APP에 어플리케이션이ID가 서비스 ID로 먼저 등록되어야 ServiceInfo 등로각능(일단 FK 관계를 없앴다.) : 해결
	 * 2) session.rollback이 먹지 않는다. 무조건 autocommit되어 버린다. : 해결
	 * 3) parameterMap에서 TypeHandler가 먹지 않는다. : 해결 
	 * 4) TB_RUT_BRK_TASK_LIST 에 insert 하는것 빠졌다. insert taskinfo 할때마다 리스트에도 넣어줄것 input 타스크일경우에만.... : 해결 
	 * @param brokerInfo
	 * @throws Exception
	 */
	@Transactional
	public void insertBrokerInfo(BrokerInfo brokerInfo) throws Exception{
		
		
		ListenerGroup listenerGroup = brokerInfo.getListenerGroup();
		if(listenerGroup != null){
			insertListenerGroup(listenerGroup);
		}
		
		mapper.insertBrokerInfo(brokerInfo);
		
		List<ServiceGroup> serviceGroupList = brokerInfo.getServiceGroupList();
		for (ServiceGroup serviceGroup : serviceGroupList) {
			
			insertServiceGroup(serviceGroup);
			
			//insert service group list
			Map<String, String> params = new HashMap<String, String>();
			params = new HashMap<String, String>();
			params.put("brokerId",(String)brokerInfo.getId());
			params.put("groupId", (String)serviceGroup.getId());
			params.put("description", serviceGroup.getName());
			params.put("regId", "mint");
			String date = Util.getFormatedDate();
			params.put("regDate", date);
			params.put("modId", "mint");
			params.put("modDate", date);
			mapper.insertServiceGroupList(params);
		}
	}

	/**
	 * @param session
	 * @param listenerGroup
	 */
	private void insertListenerGroup(ListenerGroup listenerGroup) throws Exception{
		
		mapper.insertListenerGroup(listenerGroup);
	 
		List<ListenerServiceInfo> list = listenerGroup.getListenerServiceInfoList();
		for (ListenerServiceInfo listenerServiceInfo : list) {
			insertListenerServiceInfo(listenerServiceInfo);
		}
	
	}

	/**
	 * 
	 * @param session
	 * @param listenerServiceInfo
	 */
	private void insertListenerServiceInfo(ListenerServiceInfo listenerServiceInfo) throws Exception{
		// TODO Auto-generated method stub
		mapper.insertListenerServiceInfo(listenerServiceInfo);
	
		Properties p = listenerServiceInfo.getProperties();
		if(p != null){
			
			Set keys = p.keySet();
			for (Object key : keys) {
				String value = p.getProperty((String)key);
				Map<String, String> params = new HashMap<String, String>();
				params.put("key", (String)key);
				params.put("value", value);
				params.put("listenerId", (String)listenerServiceInfo.getId());
				params.put("description", (String)key);
				params.put("regId", "mint");
				String date = Util.getFormatedDate();
				params.put("regDate", date);
				params.put("modId", "mint");
				params.put("modDate", date);
				
				mapper.insertListenerProperty(params);
			}
			 
		}
	
	}


	/**
	 * @param session
	 * @param serviceGroup
	 * @throws Exception 
	 */
	private void insertServiceGroup(ServiceGroup serviceGroup) throws Exception {
		mapper.insertServiceGroup(serviceGroup);
		List<ServiceInfo> list = serviceGroup.getServiceInfoList();
		if(list != null && list.size() > 0){
			for (ServiceInfo serviceInfo : list) {
				mapper.insertServiceInfo(serviceInfo);
				
				List<TaskInfo> inputTaskList = serviceInfo.getInputTaskInfoList();
				
				for (TaskInfo taskInfo : inputTaskList) {
					insertTaskInfo(taskInfo);
					
					
					Map<String, String> params = new HashMap<String, String>();
					params.put("serviceId", Util.toString(serviceInfo.getId()));
					params.put("taskId", Util.toString(taskInfo.getId()));
					params.put("type", taskInfo.getType() + "");
					params.put("description", taskInfo.getDescription());
					params.put("regId", "mint");
					String date = Util.getFormatedDate();
					params.put("regDate", date);
					params.put("modId", "mint");
					params.put("modDate", date);
					
					mapper.insertTaskList(params);
				}
				
			}
		}
	 
		
	}



	/**
	 * @param session
	 * @param taskInfo
	 */
	private void insertTaskInfo(TaskInfo taskInfo) throws Exception{
		
		//-------------------------------------------------------------
		//0.insert fail terminal
		//-------------------------------------------------------------
		{
			FailTerminalInfo failTerminalInfo = taskInfo.getFailTerminalInfo();
			if(failTerminalInfo != null){
				mapper.insertFailTerminalInfo(failTerminalInfo);
				List<LinkInfo> linkList = failTerminalInfo.getLinkInfoList();
				for (LinkInfo linkInfo : linkList) {
					
					
					InputTerminalInfo inputTerminal = linkInfo.getInputTerminalInfo();
					if(inputTerminal != null){
						TaskInfo nextTask = inputTerminal.getTaskInfo();
						
						if(nextTask != null){
							
							
							//요거 구현해준다.
							//input이 2개인 node일 경우 이미 선행 input에 의핸 task가 생성되면 
							//다음 input 으로 발생되는 task생성은 dup 나므로 skip
							String res = mapper.existTask((String)nextTask.getId());
							if(res == null){
								//0.3.insertTaskInfo recursion function call
								insertTaskInfo(nextTask);
								
								//0.4.input termianl insert
								System.out.println(Util.join("11111111111111111************inputTerminal:",Util.toJSONString(inputTerminal)));
								mapper.insertInputTerminalInfo(inputTerminal);
							}
						}
					}
					
					
					
					//7.1.insert link for fail 
					mapper.insertLinkInfo(linkInfo);
					
					//7.2.insert link list for fail
					Map<String, String> params = new HashMap<String, String>();
					params = new HashMap<String, String>();
					params.put("linkId",(String)linkInfo.getId());
					params.put("outputTerminalId", null);
					params.put("failTerminalId", (String)failTerminalInfo.getId());
					params.put("description", Util.join(failTerminalInfo.getName(), "'s link"));
					params.put("regId", "mint");
					String date = Util.getFormatedDate();
					params.put("regDate", date);
					params.put("modId", "mint");
					params.put("modDate", date);
					mapper.insertTerminalLinks(params);
					
					
					
					
				}
			}
		}
		
		
		//1.TaskInfo insert
		mapper.insertTaskInfo(taskInfo);
		{
			//2.Task property insert
			Properties p = taskInfo.getProperties();
			if(p != null){
				
				Set keys = p.keySet();
				for (Object key : keys) {
					String value = p.getProperty((String)key);
					Map<String, String> params = new HashMap<String, String>();
					params.put("key", (String)key);
					params.put("value", value);
					params.put("taskId", (String)taskInfo.getId());
					params.put("description", (String)key);
					params.put("regId", "mint");
					String date = Util.getFormatedDate();
					params.put("regDate", date);
					params.put("modId", "mint");
					params.put("modDate", date);
					
					mapper.insertTaskProperty(params);
				}
			}
			//3.output terminal list insert
			Map<Object, OutputTerminalInfo> otm = taskInfo.getOutputTerminalInfoMap();
			if(otm != null && otm.size() > 0){
				
				Collection<OutputTerminalInfo> col = otm.values();
				for (OutputTerminalInfo outputTerminalInfo : col) {
					
					//3.1.output termianl Info insert
					mapper.insertOutputTerminalInfo(outputTerminalInfo);
					
					//3.2.output termianl list insert
					Map<String, String> params = new HashMap<String, String>();
					params.put("terminalId",(String)outputTerminalInfo.getId());
					params.put("taskId", (String)taskInfo.getId());
					params.put("description", Util.join(outputTerminalInfo.getName(), "'s link"));
					params.put("regId", "mint");
					String date = Util.getFormatedDate();
					params.put("regDate", date);
					params.put("modId", "mint");
					params.put("modDate", date);
					mapper.insertOutputTerminalList(params);
					
					//4.link list insert
					List<LinkInfo> linkList = outputTerminalInfo.getLinkInfoList();
					for (LinkInfo linkInfo : linkList) {
						
						
						//link를 insert하기전에 먼저 inputTerminal 정보가 등록되어 있어야 편함.(link정보의 inputTerminal정보를 업데이트 하지 않아도 됨)
						InputTerminalInfo inputTerminal = linkInfo.getInputTerminalInfo();
						if(inputTerminal != null){
							TaskInfo nextTask = inputTerminal.getTaskInfo();
							
							if(nextTask != null){
								//5.insertTaskInfo recursion function call
								
								//요거 구현해준다.
								//input이 2개인 node일 경우 이미 선행 input에 의핸 task가 생성되면 
								//다음 input 으로 발생되는 task생성은 dup 나므로 skip
								String res = mapper.existTask((String)nextTask.getId());
								if(res == null){
									insertTaskInfo(nextTask);
									//6.input termianl insert
									System.out.println(Util.join("222222222222222222222************inputTerminal:",Util.toJSONString(inputTerminal)));
									mapper.insertInputTerminalInfo(inputTerminal);
								}
								
							}
						}
						
						
						//4.1.insert link for output 
						mapper.insertLinkInfo(linkInfo);
						
						//4.2.insert link list for output
						params = new HashMap<String, String>();
						params.put("linkId",(String)linkInfo.getId());
						params.put("outputTerminalId", (String)outputTerminalInfo.getId());
						params.put("failTerminalId", null);
						params.put("description", Util.join(outputTerminalInfo.getName(), "'s link"));
						params.put("regId", "mint");
						date = Util.getFormatedDate();
						params.put("regDate", date);
						params.put("modId", "mint");
						params.put("modDate", date);
						mapper.insertTerminalLinks(params);
						
						
					}
				}
				
			}
		}
		
		//insert task list 
		//
	}
	
}



























