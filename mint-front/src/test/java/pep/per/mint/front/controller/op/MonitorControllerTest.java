package pep.per.mint.front.controller.op;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.test.RestUtil;

public class MonitorControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MonitorControllerTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetTrackingLogList() {
		
		try{
		
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetTrackingLogList");
			
			Map<String, String> requestObject = new HashMap<String, String>();
			requestObject.put("channelId", "CN00000002");
			request.setRequestObject(requestObject);
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/op/tracking/logs", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}
	
	
	@Test
	public void testGetTrackingLogDetail() {
		
		try{
		
			//-------------------------------------------------
			//request 세팅
			//-------------------------------------------------
			ComMessage request = new ComMessage();
			request.setUserId("Solution TF");
			request.setAppId("testGetTrackingLogList");
			
			Map<String, String> requestObject = new HashMap<String, String>();
			requestObject.put("statusCodeLv1", "OP");
			requestObject.put("statusCodeLv2", "01");
			
			request.setRequestObject(requestObject);
			
			//-------------------------------------------------
			//REST 호출 
			//-------------------------------------------------
			ComMessage response = RestUtil.postForObject("http://127.0.0.1:8080/mint/op/tracking/logs/11/20150730163816797000", request, "GET", false);
			
			logger.debug(Util.join("response:\n", Util.toJSONString(response)));
			
		}catch(Exception e){
			logger.error("",e);
			fail("fail");
		}
	}
}
