package pep.per.mint.agent.event;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import pep.per.mint.agent.controller.AgentController;
import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.agent.handler.ServiceRoutingHandlerImpl;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.msg.handler.ServiceCodeConstant;

@Component
public class ServiceListenerImpl implements ServiceListener {
	private Logger logger = LoggerFactory.getLogger(ServiceListenerImpl.class);

	@Autowired
	AgentController agentController;

	@Autowired
	ServiceRoutingHandlerImpl serviceRoutingHandler = null;

	public ServiceListenerImpl(AgentController agentController) {
		this.agentController = agentController;
//		serviceRoutingHandler = new ServiceRoutingHandlerImpl(agentController);
	}


	@Override
	public void requestService(ServiceEvent se) throws AgentException {
		ComMessage<?,?> resMsg =  serviceRoutingHandler.route(se.getRequest());

		// TODO 응답 안보내는 케이스를 확인한다.
		if(!(Extension.MSG_TYPE_RES.equalsIgnoreCase(se.getType()) &&
				ServiceCodeConstant.WS0025.equalsIgnoreCase(se.getServiceCd()))
				){

			if(resMsg != null)
			{
				agentController.putQueue(resMsg);
			}
		}
	}

}
