package pep.per.mint.agent.handler;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pep.per.mint.agent.controller.AgentController;
import pep.per.mint.agent.controller.MQObjectMonitorController;
import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;
import pep.per.mint.common.util.Util;

@Component
public class ServiceRoutingHandlerImpl implements ServiceRoutineHandler {

	@Autowired
	AgentController agentController;


	@Autowired
	MQObjectMonitorController mqObjectMonitorController;

	public ServiceRoutingHandlerImpl(AgentController agentController) {
		this.agentController = agentController;


	}


	@Override
	public ComMessage<?,?> route(ComMessage<?,?> request) throws AgentException {
		ComMessage<?,?> req = null;

		String serviceCd = request.getExtension().getServiceCd();
		mqObjectMonitorController.setAgentInfo(agentController.getAgentInfo());

		if(ServiceCodeConstant.WS0025.equalsIgnoreCase(serviceCd) || ServiceCodeConstant.WS0020.equalsIgnoreCase(serviceCd) ){
			req  = agentController.loadAgentInfo(request);
			agentController.initNetstatCheckList();
		}else if(ServiceCodeConstant.WS0024.equalsIgnoreCase(serviceCd)){
			req  = agentController.aliveCheck(request);
		}else if(ServiceCodeConstant.WS0021.equalsIgnoreCase(serviceCd)){
			req  = agentController.loadClass(request);
		}else if(ServiceCodeConstant.WS0022.equalsIgnoreCase(serviceCd)){
			req  = agentController.uploadFile(request);
		}else if(ServiceCodeConstant.WS0023.equalsIgnoreCase(serviceCd)){
			req  = agentController.getFileContents(request);
		}else if(ServiceCodeConstant.WS0034.equalsIgnoreCase(serviceCd)){
			req  = agentController.testCall(request);
		}else if(ServiceCodeConstant.WS0051.equalsIgnoreCase(serviceCd)){
			req  = mqObjectMonitorController.getMQList(request , agentController.getAgentInfo());
		}else if(ServiceCodeConstant.WS0052.equalsIgnoreCase(serviceCd)){
			req  = mqObjectMonitorController.getQList(request , agentController.getAgentInfo());
		}else if(ServiceCodeConstant.WS0053.equalsIgnoreCase(serviceCd)){
			req  = mqObjectMonitorController.pushQStatus(request);
		}else if(ServiceCodeConstant.WS0054.equalsIgnoreCase(serviceCd)){
			req  = agentController.getConfigFile(request);
		}else if(ServiceCodeConstant.WS0044.equalsIgnoreCase(serviceCd)){
			
			//System.out.println(">>>>serviceCd:" + serviceCd + ", setNetstatCheckList:" + Util.toJSONPrettyString(request));
			req  = agentController.setNetstatCheckList(request);	
			
			
			
		}else{
			req = request;
			req.setErrorCd("9");
			req.setErrorMsg("IIPAgent 처리 항목  ["+serviceCd+ "] 오류");

			Extension ext = request.getExtension();
			ext.setMsgType(Extension.MSG_TYPE_RES);
		}
		if(req != null){
			req.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		}
		return req;
	}

}
