package pep.per.mint.websocket.event;

import org.springframework.beans.factory.annotation.Autowired;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.controller.FrontChannelController;
import pep.per.mint.websocket.handler.ServiceRoutingHandler;

/**
 *
 * <pre>
 * pep.per.mint.websocket.event
 * FrontServiceListener.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 */
public class FrontServiceListener implements ServiceListener {

	public final static String QUEUE_OBJ_MON_SERVICE_CD = "WS0053";
	public final static String CHANNEL_OBJ_MON_SERVICE_CD = "WS0054";

	@Autowired
	FrontChannelController frontChannelController;

	@Autowired
	ServiceRoutingHandler serviceRoutingHandler;
 


	@Override
	public void requestService(ServiceEvent<?, ?> se) throws Exception {

		ComMessage<?, ?> request = se.getRequest();
		Extension extension = request.getExtension();
		//Object owner = frontChannelController;
		String serviceCd = extension.getServiceCd();
		String msgType = extension.getMsgType();

		if(QUEUE_OBJ_MON_SERVICE_CD.equalsIgnoreCase(serviceCd)) {

		}else {
			if (Extension.MSG_TYPE_ON.equals(msgType)) {
				serviceCd = "serviceOn";
			} else if (Extension.MSG_TYPE_OFF.equals(msgType)) {
				serviceCd = "serviceOff";
			}
		}

		serviceCd = Util.join(serviceCd, ".", msgType);
		Object[] params = { se };
		serviceRoutingHandler.route(serviceCd, params);


	}

	@Override
	public void login(ServiceEvent<?, ?> se) throws Exception {
		frontChannelController.login(se);
	}

	@Override
	public void logout(ServiceEvent<?, ?> se) throws Exception {
		frontChannelController.logout(se);
	}

}
