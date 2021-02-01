package pep.per.mint.agent.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import javax.print.attribute.HashPrintJobAttributeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;


import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.agent.service.MQObjectMonitorService;
import pep.per.mint.agent.service.MQQueuePushService;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.qmgr.QmgrProperty;
import pep.per.mint.common.data.basic.qmgr.QueueProperty;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

@Controller
public class MQObjectMonitorController {

	final static Logger logger = LoggerFactory.getLogger(MQObjectMonitorController.class);

	@Autowired
	MQObjectMonitorService  mqObjectMonitorService;


    @Autowired
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

	IIPAgentInfo agentInfo;

	/**
	 *
	 */
	public static int initDelaySec = 5000;

	MessageHandler messageHandler = null;

	LinkedBlockingQueue<ComMessage<?,?>>  lbq = null;

	public MQObjectMonitorController( IIPAgentInfo basicAgentInfo, LinkedBlockingQueue<ComMessage<?,?>> lbq){
		this.agentInfo = basicAgentInfo;
		this.lbq = lbq;

		messageHandler = new MessageHandler();
	}


	public void init(){

	}


	public void putQueue(ComMessage msg){
		this.lbq.add(msg);
	}


	public void setAgentInfo(IIPAgentInfo iipAgentInfo) {
		this.agentInfo = iipAgentInfo;
	}

	public IIPAgentInfo getAgentInfo() {
		return this.agentInfo;
	}


	public ComMessage<?, ?> getMQList(ComMessage<?, ?> request, IIPAgentInfo iipAgentInfo ) {
		ComMessage resMsg = request;

		Extension hm = new Extension();
		hm.setMsgType(Extension.MSG_TYPE_RES);
		hm.setServiceCd(ServiceCodeConstant.WS0051);
		hm.setFrontSessionId(request.getExtension().getFrontSessionId());

		long startTime =  System.currentTimeMillis();
//		logger.debug(String.format("MQList-Req-Start[%s][%s]",ServiceCodeConstant.WS0051,Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI)));
		Map agent = ((Map)request.getRequestObject());

		try {
			List<QmgrProperty> mqList = mqObjectMonitorService.getMQList(iipAgentInfo);
			resMsg.setResponseObject(mqList);
			resMsg.setErrorCd("0");
		} catch (AgentException e) {
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg(e.getMessage());
			logger.warn("QmgrList Error ", e);
		}
		resMsg.setExtension(hm);
		long endTime = System.currentTimeMillis();
//		logger.debug(String.format("MQList-Req-End[%s][%s][%d]",ServiceCodeConstant.WS0051,Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI),
//				endTime-startTime));
		return  resMsg;
	}



	public ComMessage<?, ?> getQList(ComMessage<?, ?> request, IIPAgentInfo iipAgentInfo ) {
		ComMessage resMsg = request;

		Extension hm = new Extension();
		hm.setMsgType(Extension.MSG_TYPE_RES);
		hm.setServiceCd(ServiceCodeConstant.WS0052);
		hm.setFrontSessionId(request.getExtension().getFrontSessionId());

		Map agent = ((Map)request.getRequestObject());

		try {
			List<QueueProperty> qList = mqObjectMonitorService.getQList(agent, iipAgentInfo);
			resMsg.setResponseObject(qList);
			resMsg.setErrorCd("0");
		} catch (AgentException e) {
			resMsg.setErrorCd("9");
			resMsg.setErrorMsg(e.getMessage());
			logger.warn("QList Error ", e);
		}
		resMsg.setExtension(hm);

		return  resMsg;
	}


	HashMap<String, MQQueuePushService> hashQPushService = new HashMap();
	public ComMessage<?, ?> pushQStatus(ComMessage<?, ?> request) {
		// TODO Auto-generated method stub
		ComMessage resMsg = request;

		Extension hm = new Extension();

		Map statQ = (Map)request.getRequestObject();
		String qmgrNm = (String)statQ.get("qmgrNm");
		String queueNm = (String)statQ.get("queueNm");

		String monitorkey = qmgrNm+"_"+ queueNm;
		Extension ext =  request.getExtension();
		if(Extension.MSG_TYPE_ON.equalsIgnoreCase(ext.getMsgType())){
			int delay = 	(Integer)statQ.get("delay");
			logger.info("QUEUE MONITRING Start[" + monitorkey +"]" );

			if(mqObjectMonitorService.isConnection(qmgrNm, agentInfo)){
				if(hashQPushService.get(monitorkey) == null){
					MQQueuePushService pushService = new  MQQueuePushService(qmgrNm, queueNm, delay, this, ext.getFrontSessionId());
					taskExecutor.execute(pushService);
					hashQPushService.put(monitorkey, pushService);
				}
			}else{
				resMsg.setErrorCd("9");
				resMsg.setErrorMsg(String.format("Qmgr[%s] is not connected", qmgrNm));
			}

			hm.setMsgType(Extension.MSG_TYPE_ACK);
			hm.setServiceCd(ServiceCodeConstant.WS0053);
			hm.setFrontSessionId(request.getExtension().getFrontSessionId());
		}else if(Extension.MSG_TYPE_OFF.equalsIgnoreCase(ext.getMsgType())){
			// hashmap Thread 종료
			logger.info("QUEUE MONITRING Stop[" + monitorkey +"]" );
			MQQueuePushService pushService = hashQPushService.get(monitorkey);
			if(pushService!= null){
				pushService.setStop();
				 hashQPushService.remove(monitorkey);
				pushService = null;
			}else{
				return null;
			}
			// ACK OFF 전달2
			hm.setMsgType(Extension.MSG_TYPE_ACK_OFF);
			hm.setServiceCd(ServiceCodeConstant.WS0053);
			hm.setFrontSessionId(request.getExtension().getFrontSessionId());
		}else{
//  TODO   오류 처리???
		}
		resMsg.setExtension(hm);

		return resMsg;
	}

	public void clear() {
		if(hashQPushService != null){
			for(Entry entry : hashQPushService.entrySet()){
				MQQueuePushService pushService   = ((MQQueuePushService)entry.getValue());
				pushService.setStop();
				pushService = null;
			}

			hashQPushService.clear();
		}

	}


}