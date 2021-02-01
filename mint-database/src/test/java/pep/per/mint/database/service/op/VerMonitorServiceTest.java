package pep.per.mint.database.service.op;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.util.Util;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class VerMonitorServiceTest {

private static final Logger logger = LoggerFactory.getLogger(VerMonitorServiceTest.class);

	@Autowired
	VerMonitorService monitorService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetTrackingLogList() {
		//리소스 코드 리스트 조회
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("channelId", "CN00000002");
			map.put("statusCodeLv1", "OP");
			map.put("statusCodeLv2", "01");

			map.put("userId", "iip");
					map.put("isInterfaceAdmin", "Y");

			map.put("startIndex",1);
			map.put("endIndex",1000);
			map.put("page",1);


			List<TRLog> list = monitorService.getTrackingLogList(map);

			if(list != null){
				int i = 0;
				logger.debug("getTrackingLogList =====================================================================================");
				for(TRLog trLog : list){
					logger.debug(Util.join("getTrackingLogList[",i ++,"]:",Util.toJSONString(trLog)));
				}
				logger.debug("----------------------------------------------------------------------------------------------------");
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("getTrackingLogList",e);
			fail("Fail getTrackingLogList");
		}
	}

	@Test
	public void testGetTrackingLogDetail() {
		//리소스 코드 리스트 조회
		try {
			Map<String, Object> map = new HashMap<String,Object>();

			map.put("statusCodeLv1", "OP");
			map.put("statusCodeLv2", "01");
			map.put("logKey1", "11");
			map.put("logKey2", "20150730163816797000");


			List<TRLogDetail> list = monitorService.getTrackingLogDetail(map);

			if(list != null){
				int i = 0;
				logger.debug("testGetTrackingLogDetail =====================================================================================");
				for(TRLogDetail trLogDetail : list){
					logger.debug(Util.join("testGetTrackingLogDetail[",i ++,"]:",Util.toJSONString(trLogDetail)));
				}
				logger.debug("----------------------------------------------------------------------------------------------------");
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetTrackingLogDetail",e);
			fail("Fail testGetTrackingLogDetail");
		}
	}
}
