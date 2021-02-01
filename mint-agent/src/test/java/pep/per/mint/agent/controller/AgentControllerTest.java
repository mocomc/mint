package pep.per.mint.agent.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ 
		"classpath:/config/agent-context.xml"}) 
public class AgentControllerTest {
	
	@Autowired
	AgentController agentController;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConsumeResourceUsageLog() throws Exception {
		//agentController.consumeResourceUsageLog();
		Thread.sleep(Integer.MAX_VALUE);
	}

}
