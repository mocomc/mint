package pep.per.mint.agent.service;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/agent-context.xml"})
public class SimulatorServiceTest {

	@Autowired
	SimulatorService simulatorService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testTestCall() {

		InterfaceCallDetail src = new InterfaceCallDetail();
		src.setIntegrationId("TEST");

		try {
			InterfaceCallDetail res = simulatorService.testCall(src, null);
			System.out.println(res.toString());
			System.out.println(res.toString());
			System.out.println(res.getStatus());
		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testTestCallList() {
		fail("Not yet implemented");
	}

}
