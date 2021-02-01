package pep.per.mint.database.service.op;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.dashboard.Trigger;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.op.DashboardMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"}
public class DashboardServiceTest extends DashboardService {

	@Autowired
	DashboardService dashbordService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFavoriteInterfaceList() throws Exception {
		List<String> list = dashbordService.getFavoriteInterfaceList("iip");
		System.out.println(Util.toJSONString(list));
	}


	@Test
	public void testGetTriggerInfo() throws Exception {
		int cnt = dashbordService.getTriggerLimitCount(null);
		System.out.println(cnt);

		List<Trigger> list = dashbordService.getTriggerLimitList(null);
		System.out.println(Util.toJSONString(list));
	}


}
