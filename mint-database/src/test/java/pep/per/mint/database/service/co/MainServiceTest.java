package pep.per.mint.database.service.co;

import static org.junit.Assert.fail;

import java.util.ArrayList;
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

import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.main.Statistics;
import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"})
public class MainServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceTest.class);
	
	@Autowired
	MainService mainService;

	@Test
	public void testGetErrorDelayList() {
		try {
			Map param = new HashMap();
			param.put("userId", "iip");
			List<TRLog> res = mainService.getErrorDelayList(param);
			
			if(res != null){
				logger.debug(Util.join("testGetErrorDelayList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetErrorDelayList",e);
			fail("Fail testGetErrorDelayList");
		}
	}

	@Test
	public void testGetProceedList() {
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("searchCnt", "4");
			param.put("searchDate", "2015080510");
			
			List<Statistics> res = mainService.getProceedList(param);
			
			if(res != null){
				logger.debug(Util.join("testGetProceedList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetProceedList",e);
			fail("Fail testGetProceedList");
		}
	}

	@Test
	public void testGetSystemList() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("searchCnt", "4");
			param.put("searchDate", "2015080510");
			param.put("searchSystem", "ST00000001");
			
			List<Statistics> res = mainService.getSystemList(param);
			
			if(res != null){
				logger.debug(Util.join("testGetSystemList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetSystemList",e);
			fail("Fail testGetSystemList");
		}
	}

	@Test
	public void testGetInterfaceList() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			List<String> interfaceList = new ArrayList<String>();
			interfaceList.add("A0CM1007_AO");
			interfaceList.add("A0CM0001_SO");
			
			param.put("searchCnt", "4");
			param.put("searchDate", "2015080510");
			param.put("searchInterface", interfaceList);
			
			List<Statistics> res = mainService.getInterfaceList(param);
			
			if(res != null){
				logger.debug(Util.join("testGetInterfaceList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceList",e);
			fail("Fail testGetInterfaceList");
		}
	}

	@Test
	public void testGetErrorList() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("searchCnt", "5");
			
			List<Statistics> res = mainService.getErrorList(param);
			
			if(res != null){
				logger.debug(Util.join("testGetErrorList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetErrorList",e);
			fail("Fail testGetErrorList");
		}
	}
}
