package pep.per.mint.websocket.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.controller.AgentChannelController;
import pep.per.mint.websocket.handler.ServiceRoutingHandler;

/**
 *
 * <pre>
 * pep.per.mint.websocket.event
 * AgentServiceListener.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
public class AgentServiceListener implements ServiceListener {

	Logger logger = LoggerFactory.getLogger(AgentServiceListener.class);

	@Autowired
	AgentChannelController agentChannelController;

	@Autowired
	ServiceRoutingHandler serviceRoutingHandler;

	@Override
	public void requestService(ServiceEvent<?, ?> se) throws Exception {
		ComMessage<?, ?> request = se.getRequest();
		Extension extension = request.getExtension();
		Object owner = agentChannelController;
		String serviceCd = extension.getServiceCd();
		String msgType = extension.getMsgType();
		serviceCd = Util.join(serviceCd, ".", msgType);
		Object[] params = { se };
		serviceRoutingHandler.route(serviceCd, params);
	}

	@Override
	public void login(ServiceEvent<?, ?> se) throws Exception {

	}

	@Override
	public void logout(ServiceEvent<?, ?> se) throws Exception {
		agentChannelController.logout(se);
	}

}
