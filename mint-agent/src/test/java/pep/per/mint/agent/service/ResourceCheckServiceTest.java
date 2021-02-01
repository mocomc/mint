package pep.per.mint.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.agent.ResourceInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.util.Util;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/agent-context.xml"})
public class ResourceCheckServiceTest {

	@Autowired
	ResourceCheckService resourceCheckSerivce;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetResourceUsageLog() throws Throwable {
		List<ResourceInfo> resources = new ArrayList<ResourceInfo>();
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setLimit("10.0");
		resourceInfo.setServerId("2");
		resourceInfo.setResourceId("1");
		resourceInfo.setResourceName("TotalCpu");
		resourceInfo.setType(ResourceInfo.TYPE_CPU);
		resources.add(resourceInfo);


		ResourceInfo resourceInfo2 = new ResourceInfo();
		resourceInfo2.setLimit("20.0");
		resourceInfo2.setServerId("3");
		resourceInfo2.setResourceId("2");
		resourceInfo2.setResourceName("MEM");
		resourceInfo2.setType(ResourceInfo.TYPE_MEMORY);
		resources.add(resourceInfo2);

		ResourceInfo resourceInfo3 = new ResourceInfo();
		resourceInfo3.setLimit("30.0");
		resourceInfo3.setInfo1("c://");

		resourceInfo3.setServerId("4");
		resourceInfo3.setResourceId("3");
		resourceInfo3.setResourceName("DISK");
		resourceInfo3.setType(ResourceInfo.TYPE_DISK);
		resources.add(resourceInfo3);



		List<ResourceUsageLog> logs = resourceCheckSerivce.getResourceUsageLog(resources);
		System.out.println(Util.join("whoana logs:",Util.toJSONString(logs)));
	}

	@Test
	public void testGetAllResourceUsageLog() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResourceInfoList() {
		//fail("Not yet implemented");
	}

}
