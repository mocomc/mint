/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.data.basic.test.InterfaceCallMaster;
import pep.per.mint.common.util.Util;


/**
 * <pre>
 * pep.per.mint.database.service.im
 * TestInterfaceCallServiceTest.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class TestInterfaceCallServiceTest {

	Logger logger = LoggerFactory.getLogger(TestInterfaceCallServiceTest.class);

	@Autowired
	TestInterfaceCallService testInterfaceCallService;

	@Test
	public void testGetInterfaceListForTest()  {
		try {
			Map params = new HashMap();
			List<InterfaceCallDetail> list = testInterfaceCallService.getInterfaceListForTest(params);

			if(list != null && list.size() > 0) {

				InterfaceCallMaster master = new InterfaceCallMaster();
				master.setTestCount(10);
				String reqDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
				master.setRegDate(reqDate);
				master.setInterfaceCount(list.size());
				master.setRegId("whoana");
				master.setStatus(InterfaceCallMaster.STATUS_ING);
				master.setTestDate(Util.getFormatedDate("yyyyMMdd"));

				int res = testInterfaceCallService.insertInterfaceCallMaster(master);

				if(res > 0) {
					String testId = master.getTestId();
					int testCount = master.getTestCount();
					String testDate = master.getTestDate();

					for(InterfaceCallDetail detail: list) {
						int seq = 1;
						for(int i = 0 ; i < master.getTestCount() ; i ++) {

							detail.setSeq(seq);
							detail.setReqDate(reqDate);
							detail.setTestDate(testDate);
							detail.setTestId(testId);
							seq ++;
							try {
								String sessionId = detail.getAgentNm();
								if(Util.isEmpty(sessionId)) throw new Exception("에이전트요청실패:테스트에이전트값없음.");
								detail.setStatus(InterfaceCallMaster.STATUS_ING);
							}catch(Exception e) {
								detail.setStatus(InterfaceCallMaster.STATUS_FAIL);
								String emsg = e.getMessage();
								detail.setResDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
								detail.setMessage(Util.join("에이전트요청실패:",emsg));
							}finally {
								res  = testInterfaceCallService.insertInterfaceCallDetail(detail);
								detail.setStatus("9");
								detail.setMessage("test error");
								detail.setResDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
								res  = testInterfaceCallService.updateInterfaceCallDetail(detail);
							}

						}

					}

				}


				boolean ok = testInterfaceCallService.finishTest(master);
				logger.debug("is test-call finished? :" + ok);

				master.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				master.setModId("iip");
				testInterfaceCallService.timeoutTest(master);


				Map paramsVal = new HashMap();
				paramsVal.put("testDate", master.getTestDate());
				paramsVal.put("testId", "1");
				List<InterfaceCallDetail> listResult = testInterfaceCallService.getTestResultListBySystem(paramsVal);
				logger.debug("\nresult\n" + Util.toJSONString(listResult));

				listResult = testInterfaceCallService.getTestResultListByInterface(paramsVal);
				logger.debug("\nresult\n" + Util.toJSONString(listResult));

			}


		}catch(Throwable e) {
			e.printStackTrace();
			fail("Exception occur:" + e.getMessage());
		}
	}

	@Test
	public void testFinishTest() {
		try {
			InterfaceCallMaster master = new InterfaceCallMaster();
			master.setTestId("1");
			master.setTestDate(Util.getFormatedDate("yyyyMMdd"));
			boolean ok = testInterfaceCallService.finishTest(master);
			logger.debug("is test-call finished? :" + ok);
		}catch(Throwable e) {
			logger.error("",e);
			fail("Exception occur:" + e.getMessage());
		}
	}


	@Test
	public void testGetTestResultListBySystem() {
		try {
			Map params = new HashMap();
			params.put("testDate", "20180824");
			List<InterfaceCallDetail> list = testInterfaceCallService.getTestResultListBySystem(params);
			logger.debug("\nresult\n" + Util.toJSONString(list));

		}catch(Throwable e) {
			logger.error("",e);
			fail("Exception occur:" + e.getMessage());
		}
	}

	@Test
	public void testGetTestResultListByInterface() {
		try {
			Map params = new HashMap();
			params.put("testDate", "20180802");
			params.put("testId", "1");
			List<InterfaceCallDetail> list = testInterfaceCallService.getTestResultListByInterface(params);
			logger.debug("\nresult\n" + Util.toJSONString(list));

		}catch(Throwable e) {
			logger.error("",e);
			fail("Exception occur:" + e.getMessage());
		}
	}


}
