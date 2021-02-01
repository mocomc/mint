/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.controller.rest.im;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.data.basic.test.InterfaceCallMaster;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.im.TestInterfaceCallService;
import pep.per.mint.websocket.env.Variables;
import pep.per.mint.websocket.env.WebsocketEnvironments;
import pep.per.mint.websocket.service.AgentChannelService;


/**
 * <pre>
 * pep.per.mint.websocket.controller.im
 * TestInterfaceCallController.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 7.
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestCallController {


	private static final Logger logger = LoggerFactory.getLogger(TestCallController.class);

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	@Autowired
	WebsocketEnvironments envs;

	@Autowired
	AgentChannelService agentChannelService;

	@Autowired
	TestInterfaceCallService testInterfaceCallService;

	@Autowired
	@Qualifier("pushThreadPoolTaskExecutor")
	Executor pushThreadPoolTaskExecutor;

	@Autowired
	MessageHandler messageHandler;



	@RequestMapping(value = "/interface-tests", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, ?> requestTest(HttpSession httpSession, @RequestBody ComMessage<Map<String,String>, ?> comMessage, Locale locale, HttpServletRequest request) throws Exception {

			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			if (params != null){
				String paramString = paramString(params);
				logger.debug(Util.join("\nparamString:[", paramString, "]"));
			}

			if(!params.containsKey("testCount")) params.put("testCount", "10");



			String testCountString = params.get("testCount");
			//testCountString = testCountString == null || testCountString.trim().length() == 0 || testCountString.trim().equalsIgnoreCase("null")? "0" : testCountString;
			testCountString = testCountString == null || testCountString.trim().length() == 0 ? "0" : testCountString;
			List<InterfaceCallDetail> list = testInterfaceCallService.getInterfaceListForTest(params);

			if(list != null && list.size() > 0) {



				InterfaceCallMaster master = new InterfaceCallMaster();
				master.setTestCount(Integer.parseInt(testCountString));
				master.setRegDate(comMessage.getStartTime());
				master.setInterfaceCount(list.size());
				master.setRegId(comMessage.getUserId());
				master.setStatus(InterfaceCallMaster.STATUS_ING);
				master.setTestDate(Util.getFormatedDate("yyyyMMdd"));

				int res = testInterfaceCallService.insertInterfaceCallMaster(master);

				if(res > 0) {
					String testId = master.getTestId();
					String testDate = master.getTestDate();

					for(InterfaceCallDetail detail: list) {
						int seq = 1;
						for(int i = 0 ; i < master.getTestCount() ; i ++) {
							detail.setSeq(seq);
							String reqDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
							detail.setReqDate(reqDate);
							detail.setTestDate(testDate);
							detail.setTestId(testId);
							seq ++;
							ComMessage requestMessage = new ComMessage();
							requestMessage.setRequestObject(detail);
							requestMessage.setStartTime(reqDate);
							Extension ext = new Extension();
							ext.setServiceCd(Variables.SERVICE_CD_WS0034);
							ext.setMsgType(Extension.MSG_TYPE_REQ);
							requestMessage.setExtension(ext);

							try {
								String msg = messageHandler.serialize(requestMessage);
								//String sessionId = detail.getAgentNm();
								String agentNm = detail.getAgentNm();
								String sessionId = agentChannelService.findSessionIdAtFirst(agentNm);
								if(Util.isEmpty(sessionId)) throw new Exception("에이전트요청실패:테스트에이전트값없음.");
								agentChannelService.sendMessage(sessionId, msg);
								detail.setStatus(InterfaceCallMaster.STATUS_ING);
							}catch(Exception e) {
								detail.setStatus(InterfaceCallMaster.STATUS_FAIL);
								String emsg = e.getMessage();
								detail.setMessage(Util.join("에이전트요청실패:",emsg));
								detail.setResDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

								logger.error("",e);

							}finally {
								res  = testInterfaceCallService.insertInterfaceCallDetail(detail);
							}
						}
					}
					if(envs.useTestCallMonitor)startTestCallMonitor(master);

				}
				errorCd = messageSource.getMessage("success.cd.ok", null, locale);
				errorMsg = messageSource.getMessage("success.msg.ok", null, locale);
			}else{
				errorCd = messageSource.getMessage("error.cd.retrieve.fail", null, locale);
				Object[] errorMsgParams = {"테스트CALL","테스트 대상 인터페이스가 존재하지 않습니다."};
				errorMsg = messageSource.getMessage("error.msg.retrieve.fail", errorMsgParams, locale);
			}

			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);

			return comMessage;


	}

	/**
	 * @param master
	 */
	private void startTestCallMonitor(final InterfaceCallMaster master) {
		pushThreadPoolTaskExecutor.execute(new Runnable() {

			long elapsedTimeMillis = 0;

			@Override
			public void run() {

				long startTime = System.currentTimeMillis();
				//Async 처리
				while(true){
					try {

						if(elapsedTimeMillis > envs.testCallTimeoutMillis) {
							master.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							master.setModId(this.getClass().getSimpleName());
							testInterfaceCallService.timeoutTest(master);
							logger.info(Util.join("TEST CALL TIMEOUT:testId:", master.getTestId(),", testDate:",master.getTestDate()));
							break;
						}


						Thread.sleep(envs.delayForMonitorTestCall);
						if(testInterfaceCallService.finishTest(master)) {
							master.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
							master.setModId(this.getClass().getSimpleName());
							logger.info(Util.join("TEST CALL FINISH:testId:", master.getTestId(),", testDate:",master.getTestDate()));
							break;
						}
					} catch (Exception e) {
						logger.error("",e);
					} finally {
						elapsedTimeMillis = System.currentTimeMillis() - startTime;
					}
				}


			}
		});
	}


	/**
	 * @param params
	 */
	private  String paramString(Map params) {
		String paramString = "";
		try{
			if(params != null) {
				Iterator iterator = params.keySet().iterator();
				while ( iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = params.get(key);
					paramString = Util.join(paramString, "\n",key,":",value);
				}
			}
		}catch(Exception e){
			logger.debug("거래와 무관한 에러-요청들어온 파라메터 프린트하다 발생됨!",e);
		}

		return paramString;
	}




}
