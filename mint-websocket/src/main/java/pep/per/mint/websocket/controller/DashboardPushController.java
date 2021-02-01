package pep.per.mint.websocket.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.op.DashboardPushService;
import pep.per.mint.websocket.annotation.ServiceCode;
import pep.per.mint.websocket.annotation.ServiceRouter;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 *
 * <pre>
 * pep.per.mint.websocket.controller
 * DashboardPushController.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 2.
 */
@Controller
@ServiceRouter
public class DashboardPushController {

	Logger logger = LoggerFactory.getLogger(DashboardPushController.class);

	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	DashboardPushService dashboardPushService;


	@Autowired
	FrontChannelService frontChannelService;

	@Autowired
	@Qualifier("pushThreadPoolTaskExecutor")
	Executor pushThreadPoolTaskExecutor;

	@Autowired
	MessageHandler messageHandler;


	@ServiceCode(code=Variables.SERVICE_CD_WS0001, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0001(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0001(params);
		broadcast(Variables.SERVICE_CD_WS0001, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0002, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0002(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0002(params);
		broadcast(Variables.SERVICE_CD_WS0002, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0003, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0003(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("max")) params.put("max", 4);
		if(!params.containsKey("checkTime")) params.put("checkTime", "10");
		List<Map> list = dashboardPushService.getWS0003(params);
		broadcast(Variables.SERVICE_CD_WS0003, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0004, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0004(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0004(params);
		broadcast(Variables.SERVICE_CD_WS0004, list, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0005, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0005(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0005(params);
		broadcast(Variables.SERVICE_CD_WS0005, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0006, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0006(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0006(params);
		broadcast(Variables.SERVICE_CD_WS0006, list, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0007, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0007(String roleKey, List<String> userSessionList,Map params) throws Exception{
		Map res = dashboardPushService.getWS0007(params);
		broadcast(Variables.SERVICE_CD_WS0007, res, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0008, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0008(String roleKey, List<String> userSessionList,Map params) throws Exception{
		Map res = dashboardPushService.getWS0008(params);
		broadcast(Variables.SERVICE_CD_WS0008, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0009, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0009(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("type")) params.put("type", ResourceInfo.TYPE_CPU);
		if(!params.containsKey("dDay")) params.put("dDay", 1);
		int res = dashboardPushService.getWS0009(params);
		broadcast(Variables.SERVICE_CD_WS0009, res, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0010, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0010(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("type")) params.put("type", ResourceInfo.TYPE_MEMORY);
		if(!params.containsKey("dDay")) params.put("dDay", 1);
		int res = dashboardPushService.getWS0010(params);
		broadcast(Variables.SERVICE_CD_WS0010, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0011, type=ServiceCode.MSG_TYPE_PUSH)
	public void  pushWS0011(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("type")) params.put("type", ResourceInfo.TYPE_DISK);
		if(!params.containsKey("dDay")) params.put("dDay", 1);
		int res = dashboardPushService.getWS0011(params);
		broadcast(Variables.SERVICE_CD_WS0011, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0012, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0012(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("dDay")) params.put("dDay", 1);
		int res = dashboardPushService.getWS0012(params);
		broadcast(Variables.SERVICE_CD_WS0012, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0013, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0013(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0013(params);
		broadcast(Variables.SERVICE_CD_WS0013, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0014, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0014(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0014(params);
		broadcast(Variables.SERVICE_CD_WS0014, res, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0015, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0015(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0015(params);
		broadcast(Variables.SERVICE_CD_WS0015, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0016, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0016(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0016(params);
		broadcast(Variables.SERVICE_CD_WS0016, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0017, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0017(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0017(params);
		broadcast(Variables.SERVICE_CD_WS0017, res, roleKey, userSessionList);
	}


	@ServiceCode(code=Variables.SERVICE_CD_WS0018, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0018(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0018(params);
		broadcast(Variables.SERVICE_CD_WS0018, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0019, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0019(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0019(params);
		broadcast(Variables.SERVICE_CD_WS0019, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0035, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0035(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0035(params);
		broadcast(Variables.SERVICE_CD_WS0035, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0037, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0037(String roleKey, List<String> userSessionList,Map params) throws Exception{
		int res = dashboardPushService.getWS0037(params);
		broadcast(Variables.SERVICE_CD_WS0037, res, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0038, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0038(String roleKey, List<String> userSessionList,Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0038(params);
		broadcast(Variables.SERVICE_CD_WS0038, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0039, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0039(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("tScope")) params.put("tScope", Integer.valueOf(60));
		List<Map> list = dashboardPushService.getWS0039(params);
		broadcast(Variables.SERVICE_CD_WS0039, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0040, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0040(String roleKey, List<String> userSessionList,Map params) throws Exception{
		if(!params.containsKey("tScope")) params.put("tScope", Integer.valueOf(60));
		List<Map> list = dashboardPushService.getWS0040(params);
		broadcast(Variables.SERVICE_CD_WS0040, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0041, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0041(String roleKey, List<String> userSessionList, Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0041(params);
		broadcast(Variables.SERVICE_CD_WS0041, list, roleKey, userSessionList);
	}

	@ServiceCode(code=Variables.SERVICE_CD_WS0042, type=ServiceCode.MSG_TYPE_PUSH)
	public void pushWS0042(String roleKey, List<String> userSessionList, Map params) throws Exception{
		List<Map> list = dashboardPushService.getWS0042(params);
		broadcast(Variables.SERVICE_CD_WS0042, list, roleKey, userSessionList);
	}


	private void broadcast(final String serviceCd, Object responseObject, String roleKey, final List<String> userSessionList) throws Exception{
		ComMessage comMessage = new ComMessage();
		comMessage.setResponseObject(responseObject);
		comMessage.setAppId(this.getClass().getName());
		comMessage.setUserId(envs.pushAppId);
		comMessage.setStartTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		Extension extension = new Extension();
		extension.setMsgType(Extension.MSG_TYPE_PUSH);
		extension.setServiceCd(serviceCd);
		comMessage.setExtension(extension);


		if(comMessage != null) {
			final String msg = messageHandler.serialize(comMessage);

			frontChannelService.savePushData(serviceCd, roleKey, msg);

			for(final String sessionId : userSessionList) {
				if(envs.pushBasicAccessRoleUser != null && envs.pushAccessRoleSessionId.equals(sessionId)) continue;
				pushThreadPoolTaskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						//Async 처리
						try {
							frontChannelService.sendMessage(sessionId, msg);
						}catch(Exception ex) {
							logger.error(Util.join("Exception1:reouting:serviceCd:", serviceCd), ex);
						}
					}
				});
			}

		}
	}

}
