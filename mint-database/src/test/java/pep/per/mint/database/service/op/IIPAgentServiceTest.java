package pep.per.mint.database.service.op;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.agent.Command;
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/config/database-context.xml"})
public class IIPAgentServiceTest {

	Logger logger = LoggerFactory.getLogger(IIPAgentServiceTest.class);

	@Autowired
	IIPAgentService iipAgentService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddResourceLogs() {
		//fail("Not yet implemented");
	}

	@Test
	public void testAddProcessLogs() {
		//fail("Not yet implemented");
	}

	@Test
	public void testAddQmgrLogs() {
		//fail("Not yet implemented");
	}


	@Test
	public void testInsertAgentCommand() {
		try {

			CommandConsole cc = new CommandConsole();
			Command command = new Command();
			command.setCommandCd("RLD");
			cc.setCommand(command);
			cc.setAgentId("AG00000001");
			String date = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
			cc.setCommandDate(date);
			cc.setExecuteDate(date);
			cc.setRegUser("iip");
			int res = iipAgentService.insertAgentCommand(cc);
			logger.info("res:" + res);
		} catch (Exception e) {
			logger.error("",e);
			fail(e.getMessage());
		}
	}


	@Test
	public void testGetAgentDeployCommand() {
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("agentId", "");
			params.put("commandCd", "");
			params.put("fromDate", "");
			params.put("toDate", "");
			List<CommandConsole> cc = iipAgentService.getAgentNotExecuteCommands(params);
			logger.debug(Util.toJSONString(cc));
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
