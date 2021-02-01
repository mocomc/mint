/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.im;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.MonitorItem;
import pep.per.mint.common.data.basic.agent.ProcessInfo;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.database.mapper.im.EngineMonitorMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class EngineMonitorService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EngineMonitorMapper engineMonitorMapper;
	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.agent.IIPAgentInfo> getAgentList(Map params) throws Exception{
		return engineMonitorMapper.getAgentList(params);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.agent.IIPAgentInfo> existAgent (Map params) throws Exception{
		return engineMonitorMapper.existAgent(params);
	}

	@Transactional
	public int createAgent(pep.per.mint.common.data.basic.agent.IIPAgentInfo agent) throws Exception {
		int res = 0;
		res = engineMonitorMapper.insertAgent(agent);
		return res;
	}

	public int updateAgent(pep.per.mint.common.data.basic.agent.IIPAgentInfo agent) throws Exception {
		int res = 0;
		res = engineMonitorMapper.updateEngineMonitorResourceforServer(agent.getAgentId(), agent.getServer().getServerId());
		res = engineMonitorMapper.updateEngineMonitorProcessforServer(agent.getAgentId(), agent.getServer().getServerId());
		res = engineMonitorMapper.updateEngineMonitorQmgrforServer(agent.getAgentId(), agent.getServer().getServerId());
		return engineMonitorMapper.updateAgent(agent);
	}

	public int deleteAgent(String agentId, String modId, String modDate) throws Exception{
		int res = 0;
		res = engineMonitorMapper.deleteAllEngineMonitorResource(agentId);
		res = engineMonitorMapper.deleteAllEngineMonitorProcess(agentId);
		res = engineMonitorMapper.deleteAllEngineMonitorChannel(agentId);
		res = engineMonitorMapper.deleteAllEngineMonitorQueue(agentId);
		res = engineMonitorMapper.deleteAllEngineMonitorQmgr(agentId);
		res = engineMonitorMapper.deleteAllEngineMonitorMapping(agentId);
		res = engineMonitorMapper.deleteAgent(agentId, modId, modDate);
		return res;
	}


	public int createEngineMonitor(IIPAgentInfo agent) {
		int res = 0;
		for(MonitorItem monitorItem : agent.getMonitorItems()){

			if("0".equalsIgnoreCase(monitorItem.getItemType())){ // 서버 자원
				List<ResourceInfo> serverResourceList = monitorItem.getResources();
				for(ResourceInfo serverResource : serverResourceList){
					res = engineMonitorMapper.insertEngineMonitorResource(agent.getAgentId(), agent.getServer().getServerId(), serverResource);
					res = engineMonitorMapper.insertEngineMonitorMapping(agent.getAgentId(), serverResource.getResourceId(), monitorItem.getItemType());
				}
			}else if("1".equalsIgnoreCase(monitorItem.getItemType())){ // 프로세스
				List<ProcessInfo> serverProcessList = monitorItem.getProcesses();
				for(ProcessInfo serverProcess : serverProcessList){
					res = engineMonitorMapper.insertEngineMonitorProcess(agent.getAgentId(), agent.getServer().getServerId(), serverProcess);
					res = engineMonitorMapper.insertEngineMonitorMapping(agent.getAgentId(), serverProcess.getProcessId(), monitorItem.getItemType());
				}
			}else if("2".equalsIgnoreCase(monitorItem.getItemType())){ // 큐관리자
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					res = engineMonitorMapper.insertEngineMonitorQmgr(agent.getAgentId(), agent.getServer().getServerId(), qmgr);
					res = engineMonitorMapper.insertEngineMonitorMapping(agent.getAgentId(),  qmgr.getQmgrId(), monitorItem.getItemType());
					if(qmgr.getSystem() != null){
						res = engineMonitorMapper.insertEngineMonitorQmgrSystemMapping(qmgr.getSystem().getSystemId(),  qmgr.getQmgrId());
					}
				}
			}else if("3".equalsIgnoreCase(monitorItem.getItemType())){ // 채널
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<ChannelInfo> channelist = qmgr.getChannels();
					for(ChannelInfo channel : channelist){
						res = engineMonitorMapper.insertEngineMonitorChannel(agent.getAgentId(), agent.getServer().getServerId(), qmgr.getQmgrId(), channel);
						res = engineMonitorMapper.insertEngineMonitorMapping(agent.getAgentId(),  channel.getChannelId(), monitorItem.getItemType());
					}
				}
			}else if("4".equalsIgnoreCase(monitorItem.getItemType())){ // 큐
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<QueueInfo> queueList = qmgr.getQueues();
					for(QueueInfo queue : queueList){
						res = engineMonitorMapper.insertEngineMonitorQueue(agent.getAgentId(), agent.getServer().getServerId(), qmgr.getQmgrId(), queue);
						res = engineMonitorMapper.insertEngineMonitorMapping(agent.getAgentId(),queue.getQueueId(), monitorItem.getItemType());
					}
				}
			}


		}

		return res;
	}

	public int deleteEngineMonitor(IIPAgentInfo agent, String modId, String modDate) {
		int res = 0;
		for(MonitorItem monitorItem : agent.getMonitorItems()){
			if("0".equalsIgnoreCase(monitorItem.getItemType())){ // 서버 자원
				List<ResourceInfo> serverResourceList = monitorItem.getResources();
				for(ResourceInfo serverResource : serverResourceList){
					res = engineMonitorMapper.deleteEngineMonitorMapping(agent.getAgentId(),  serverResource.getResourceId(), monitorItem.getItemType());
					res = engineMonitorMapper.deleteEngineMonitorResource(agent.getAgentId(),
							serverResource.getResourceId(), modId, modDate);
				}
			}else if("1".equalsIgnoreCase(monitorItem.getItemType())){ // 프로세스
				List<ProcessInfo> serverProcessList = monitorItem.getProcesses();
				for(ProcessInfo serverProcess : serverProcessList){
					res = engineMonitorMapper.deleteEngineMonitorMapping(agent.getAgentId(),  serverProcess.getProcessId(), monitorItem.getItemType());
					res = engineMonitorMapper.deleteEngineMonitorProcess(agent.getAgentId(), serverProcess.getProcessId(), modId, modDate);
				}
			}else if("2".equalsIgnoreCase(monitorItem.getItemType())){ // 큐관리자
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					res = engineMonitorMapper.deleteEngineMonitorMappingChannel(agent.getAgentId(),  qmgr.getQmgrId());
					res = engineMonitorMapper.deleteEngineMonitorMappingQueue(agent.getAgentId(),  qmgr.getQmgrId());
					res = engineMonitorMapper.deleteEngineMonitorQueueOfQmgr(agent.getAgentId(),  qmgr.getQmgrId(), modId, modDate);
					res = engineMonitorMapper.deleteEngineMonitorChannelOfQmgr(agent.getAgentId(),  qmgr.getQmgrId(), modId, modDate);
					res = engineMonitorMapper.deleteEngineMonitorMapping(agent.getAgentId(),  qmgr.getQmgrId(), monitorItem.getItemType());
					if(qmgr.getSystem() != null){
						res = engineMonitorMapper.deleteEngineMonitorQmgrWidthSystem(qmgr.getQmgrId());
					}
					res = engineMonitorMapper.deleteEngineMonitorQmgr(agent.getAgentId(),  qmgr.getQmgrId(), modId, modDate);
				}
			}else if("3".equalsIgnoreCase(monitorItem.getItemType())){ // 채널
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<ChannelInfo> channelist = qmgr.getChannels();
					for(ChannelInfo channel : channelist){
						res = engineMonitorMapper.deleteEngineMonitorMapping(agent.getAgentId(),   channel.getChannelId(), monitorItem.getItemType());
						res = engineMonitorMapper.deleteEngineMonitorChannel(agent.getAgentId(),  qmgr.getQmgrId(), channel.getChannelId(), modId, modDate);
					}
				}
			}else if("4".equalsIgnoreCase(monitorItem.getItemType())){ // 큐
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<QueueInfo> queueList = qmgr.getQueues();
					for(QueueInfo queue : queueList){
						res = engineMonitorMapper.deleteEngineMonitorMapping(agent.getAgentId(), queue.getQueueId(), monitorItem.getItemType());
						res = engineMonitorMapper.deleteEngineMonitorQueue(agent.getAgentId(), qmgr.getQmgrId(), queue.getQueueId(), modId, modDate);
					}
				}
			}
		}
		return res;
	}

	public int updateEngineMonitor(IIPAgentInfo agent) {
		int res = 0;
		for(MonitorItem monitorItem : agent.getMonitorItems()){

			if("0".equalsIgnoreCase(monitorItem.getItemType())){ // 서버 자원
				List<ResourceInfo> serverResourceList = monitorItem.getResources();
				for(ResourceInfo serverResource : serverResourceList){
					serverResource.setModDate(agent.getModDate());
					serverResource.setModId(agent.getModId());
					res = engineMonitorMapper.updateEngineMonitorResource(agent.getAgentId(), agent.getServer().getServerId(), serverResource);
				}
			}else if("1".equalsIgnoreCase(monitorItem.getItemType())){ // 프로세스
				List<ProcessInfo> serverProcessList = monitorItem.getProcesses();
				for(ProcessInfo serverProcess : serverProcessList){
					serverProcess.setModDate(agent.getModDate());
					serverProcess.setModId(agent.getModId());
					res = engineMonitorMapper.updateEngineMonitorProcess(agent.getAgentId(), agent.getServer().getServerId(), serverProcess);
				}
			}else if("2".equalsIgnoreCase(monitorItem.getItemType())){ // 큐관리자
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					qmgr.setModDate(agent.getModDate());
					qmgr.setModId(agent.getModId());
					res = engineMonitorMapper.updateEngineMonitorQmgr(agent.getAgentId(), agent.getServer().getServerId(), qmgr);
					if(qmgr.getSystem() != null){
						res = engineMonitorMapper.updateEngineMonitorQmgrSystemMapping(qmgr.getSystem().getSystemId(),  qmgr.getQmgrId());
					}
				}
			}else if("3".equalsIgnoreCase(monitorItem.getItemType())){ // 채널
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<ChannelInfo> channelist = qmgr.getChannels();
					for(ChannelInfo channel : channelist){
						channel.setModDate(agent.getModDate());
						channel.setModId(agent.getModId());
						res = engineMonitorMapper.updateEngineMonitorChannel(agent.getAgentId(), agent.getServer().getServerId(), qmgr.getQmgrId(), channel);
					}
				}
			}else if("4".equalsIgnoreCase(monitorItem.getItemType())){ // 큐
				List<QmgrInfo> qmgrList = monitorItem.getQmgrs();
				for(QmgrInfo qmgr : qmgrList){
					List<QueueInfo> queueList = qmgr.getQueues();
					for(QueueInfo queue : queueList){
						queue.setModDate(agent.getModDate());
						queue.setModId(agent.getModId());
						res = engineMonitorMapper.updateEngineMonitorQueue(agent.getAgentId(), agent.getServer().getServerId(), qmgr.getQmgrId(), queue);
					}
				}
			}
		}
		return res;
	}

	public IIPAgentInfo getEngineMoitor(IIPAgentInfo agent) throws Exception {
		return engineMonitorMapper.getEngineMonitor(agent);
	}

	public List<ResourceInfo> getEngineMoitorResource(IIPAgentInfo agent) throws Exception {
		return engineMonitorMapper.getEngineMoitorResource(agent);
	}

	public List<ProcessInfo> getEngineMoitorProcess(IIPAgentInfo agent) throws Exception {
		return engineMonitorMapper.getEngineMoitorProcess(agent);
	}

	public List<QmgrInfo> getEngineMoitorQmgr(IIPAgentInfo agent) throws Exception {
		return engineMonitorMapper.getEngineMoitorQmgr(agent);
	}

	public List<ChannelInfo> getEngineMoitorChannel(Map params) throws Exception {
		return engineMonitorMapper.getEngineMoitorChannel(params);
	}

	public List<QueueInfo> getEngineMoitorQueue(Map params) throws Exception {
		return engineMonitorMapper.getEngineMoitorQueue(params);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo> getAgentTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo> treeModel = new TreeModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>();
		treeModel.setText("IIPAgentInfo Tree");
		List<IIPAgentInfo> list = engineMonitorMapper.getAgentList(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>>();

		for(IIPAgentInfo agent : list) {
			{
				ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo> parentItemModel = itemModelMap.get(agent.getAgentId());

				if(!itemModelMap.containsKey(agent.getAgentId())){


					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>();
					parentItemModel.setId(agent.getAgentId());
					parentItemModel.setText(agent.getAgentNm());
					parentItemModel.setObjCode("9");  // IIPAGENT
					parentItemModel.setItem(agent);

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------

					itemModelMap.put(agent.getAgentId(), parentItemModel);
					//---------------------------
					// root 처리
					//---------------------------
					if(!rootItemModelMap.containsKey(agent.getAgentId())){
						rootItemModelMap.put(agent.getAgentId(),parentItemModel);
						treeModel.addRoot(parentItemModel);
						parentItemModel.setIsRoot(true);
					}
					parentItemModel.setSpriteCssClass("rootfolder");



					ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo> childItemModel = null;


						//---------------------------
						// childItemModel 생성
						//---------------------------
						childItemModel = new ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>();
						childItemModel.setId(agent.getAgentId()+"-"+"R");
						childItemModel.setParent(agent.getAgentId());
						childItemModel.setText("시스템자원");
						childItemModel.setIsRoot(false);
						childItemModel.setSpriteCssClass("html");
						childItemModel.setObjCode("0");  // Resource
						//---------------------------
						// temp map에 parentItemModel 등록
						//---------------------------
						itemModelMap.put(agent.getAgentId()+"-"+"R", childItemModel);

						parentItemModel.addItem(childItemModel);


						//---------------------------
						// childItemModel 생성
						//---------------------------
						childItemModel = new ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>();
						childItemModel.setId(agent.getAgentId()+"-"+"P");
						childItemModel.setParent(agent.getAgentId());
						childItemModel.setText("프로세스");
						childItemModel.setIsRoot(false);
						childItemModel.setSpriteCssClass("html");
						childItemModel.setObjCode("1");  // 프로세스
						//---------------------------
						// temp map에 parentItemModel 등록
						//---------------------------
						itemModelMap.put(agent.getAgentId()+"-"+"R", childItemModel);

						parentItemModel.addItem(childItemModel);




						//---------------------------
						// childItemModel 생성
						//---------------------------
						childItemModel = new ItemModel<pep.per.mint.common.data.basic.agent.IIPAgentInfo>();
						childItemModel.setId(agent.getAgentId()+"-"+"Q");
						childItemModel.setParent(agent.getAgentId());
						childItemModel.setText("큐관리자");
						childItemModel.setIsRoot(false);
						childItemModel.setSpriteCssClass("folder");
						childItemModel.setObjCode("2");  // 큐관리자

						List<QmgrInfo> qmgrList = engineMonitorMapper.getEngineMoitorQmgr(agent);
						for(QmgrInfo qmgr : qmgrList) {
							ItemModel	qmgrItemModel = new ItemModel<pep.per.mint.common.data.basic.dashboard.QueueManager>();
							qmgrItemModel.setId(qmgr.getQmgrId());
							qmgrItemModel.setParent(agent.getAgentId()+"-"+"Q");
							qmgrItemModel.setText(qmgr.getQmgrNm());
							qmgrItemModel.setItem(qmgr);
							qmgrItemModel.setIsRoot(false);
							qmgrItemModel.setSpriteCssClass("html");
							qmgrItemModel.setObjCode("2");  // 큐관리자
							qmgrItemModel.setObjName(qmgr.getQmgrNm());  // 큐관리자

							ItemModel	channelItemModel = new ItemModel<pep.per.mint.common.data.basic.dashboard.QmgrChannel>();
							channelItemModel.setId(qmgr.getQmgrId() +"-C");
							channelItemModel.setParent(qmgr.getQmgrId());
							channelItemModel.setText("채널");
							channelItemModel.setIsRoot(false);
							channelItemModel.setSpriteCssClass("pdf");
							channelItemModel.setObjCode("3");  // 채널

							qmgrItemModel.addItem(channelItemModel);

							ItemModel	queueItemModel = new ItemModel<pep.per.mint.common.data.basic.dashboard.Queue>();
							queueItemModel.setId(qmgr.getQmgrId() +"-Q");
							queueItemModel.setParent(qmgr.getQmgrId());
							queueItemModel.setText("큐");
							queueItemModel.setIsRoot(false);
							queueItemModel.setSpriteCssClass("pdf");
							queueItemModel.setObjCode("4");  // 큐

							qmgrItemModel.addItem(queueItemModel);


							qmgrItemModel.setHasChild(true);

							childItemModel.addItem(qmgrItemModel);
						}
						childItemModel.setHasChild(true);
						// 큐관리자 모델.


						parentItemModel.addItem(childItemModel);



						parentItemModel.setHasChild(true);




				}



			}
		}
		return treeModel;
	}


	public IIPAgentInfo getAgentDetailInfo(String agentId) throws Exception {
		IIPAgentInfo agent = new IIPAgentInfo();
		agent.setAgentId(agentId);
		Map params = new HashMap<String, String>();
		params.put("agentId",agentId);
		if(engineMonitorMapper.getAgentList(params) !=null && engineMonitorMapper.getAgentList(params).size()>0)
		{
			agent = (IIPAgentInfo) engineMonitorMapper.getAgentList(params).get(0);
			List<MonitorItem> monitorItems = new LinkedList<MonitorItem>() ;

			List<ResourceInfo> resourceInfo = engineMonitorMapper.getEngineMoitorResource(agent);
			MonitorItem resourceItem = new MonitorItem();
			resourceItem.setItemType(MonitorItem.ITEM_TYPE_RESOURCE);
			resourceItem.setResources(resourceInfo);

			List<ProcessInfo> processInfo = engineMonitorMapper.getEngineMoitorProcess(agent);
			MonitorItem processItem = new MonitorItem();
			processItem.setItemType(MonitorItem.ITEM_TYPE_PROCESS);
			processItem.setProcesses(processInfo);

			List<QmgrInfo> qmgrInfo = engineMonitorMapper.getEngineMoitorQmgr(agent);
			for(QmgrInfo qmgr: qmgrInfo){
				params.put("agentId", agent.getAgentId());
				params.put("qmgrId", qmgr.getQmgrId());
				List<ChannelInfo> channelInfo = engineMonitorMapper.getEngineMoitorChannel(params);
				qmgr.setChannels(channelInfo);

				List<QueueInfo> queueInfo = engineMonitorMapper.getEngineMoitorQueue(params);
				qmgr.setQueues(queueInfo);
			}

			MonitorItem qmgrItem = new MonitorItem();
			qmgrItem.setItemType(MonitorItem.ITEM_TYPE_QMGR);
			qmgrItem.setQmgrs(qmgrInfo);


			monitorItems.add(resourceItem);
			monitorItems.add(processItem);
			monitorItems.add(qmgrItem);
			agent.setMonitorItems(monitorItems);
			return agent;
		}else{
			return null;
		}

	}


	public IIPAgentInfo getAgentDetailInfoWithQmgrInfoMap(String agentId) throws Exception {
		IIPAgentInfo agent = new IIPAgentInfo();
		agent.setAgentId(agentId);
		Map params = new HashMap<String, String>();
		params.put("agentId",agentId);
		if(engineMonitorMapper.getAgentList(params) !=null && engineMonitorMapper.getAgentList(params).size()>0)
		{
			agent = (IIPAgentInfo) engineMonitorMapper.getAgentList(params).get(0);
			List<MonitorItem> monitorItems = new LinkedList<MonitorItem>() ;

			List<ResourceInfo> resourceInfo = engineMonitorMapper.getEngineMoitorResource(agent);
			MonitorItem resourceItem = new MonitorItem();
			resourceItem.setItemType(MonitorItem.ITEM_TYPE_RESOURCE);
			resourceItem.setResources(resourceInfo);

			List<ProcessInfo> processInfo = engineMonitorMapper.getEngineMoitorProcess(agent);
			MonitorItem processItem = new MonitorItem();
			processItem.setItemType(MonitorItem.ITEM_TYPE_PROCESS);
			processItem.setProcesses(processInfo);

			List<QmgrInfo> qmgrInfos = engineMonitorMapper.getEngineMoitorQmgr(agent);
			Map<String, QmgrInfo> qmgrInfoMap = new HashMap<String, QmgrInfo>();
			if(qmgrInfos != null && qmgrInfos.size() > 0) {

				for(QmgrInfo qmgrInfo: qmgrInfos){
					params.put("agentId", agent.getAgentId());
					params.put("qmgrId", qmgrInfo.getQmgrId());
					List<ChannelInfo> channelInfos = engineMonitorMapper.getEngineMoitorChannel(params);
					if(channelInfos != null && channelInfos.size() > 0) {
						qmgrInfo.setChannels(channelInfos);
						Map<String, ChannelInfo> channelMap = new HashMap<String, ChannelInfo>();
						for(ChannelInfo channelInfo : channelInfos) {
							channelMap.put(channelInfo.getChannelId(), channelInfo);
						}
						qmgrInfo.setChannelMap(channelMap);
					}

					List<QueueInfo> queueInfos = engineMonitorMapper.getEngineMoitorQueue(params);
					if(queueInfos != null && queueInfos.size() > 0) {
						qmgrInfo.setQueues(queueInfos);
						Map<String, QueueInfo> queueMap = new HashMap<String, QueueInfo>();
						for(QueueInfo queueInfo : queueInfos) {
							queueMap.put(queueInfo.getQueueId(), queueInfo);
						}
						qmgrInfo.setQueueMap(queueMap);
					}

					qmgrInfoMap.put(qmgrInfo.getQmgrId(), qmgrInfo);
				}
			}


			MonitorItem qmgrItem = new MonitorItem();
			qmgrItem.setItemType(MonitorItem.ITEM_TYPE_QMGR);
			qmgrItem.setQmgrs(qmgrInfos);
			qmgrItem.setQmgrInfoMap(qmgrInfoMap);

			monitorItems.add(resourceItem);
			monitorItems.add(processItem);
			monitorItems.add(qmgrItem);
			agent.setMonitorItems(monitorItems);
			return agent;
		}else{
			return null;
		}

	}




	public String getAgentInfo(String agentNm) {
		return engineMonitorMapper.getAgentIDInfo(agentNm);
	}

	public List<QmgrInfo> getEngineMoitorAllQmgr()throws Exception {
		return engineMonitorMapper.getEngineMoitorAllQmgr();
	}

	public List<QmgrInfo> getEngineMoitorQmgrWithSystem(IIPAgentInfo agent) throws Exception {
		return engineMonitorMapper.getEngineMoitorQmgrWithSystem(agent);
	}

}
