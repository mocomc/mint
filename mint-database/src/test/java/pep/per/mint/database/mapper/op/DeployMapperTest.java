package pep.per.mint.database.mapper.op;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.Deployment;
import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/config/database-context.xml","classpath:/config/inhouse-context.xml"}) 
public class DeployMapperTest {
	
	@Autowired 
	DeployMapper deployMapper;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDeployInterfaceResults() throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		
		List<Deployment> list = deployMapper.getDeployInterfaceResults(params);
		
		System.out.println(Util.toJSONString(list));
	}

	@Test
	public void testGetDeployInterfaceResult() throws Exception {
		String reqDate = "20170221102145956";
		String interfaceId = "F@00000033";
		int seq = 1;
		
		Deployment deployment = deployMapper.getDeployInterfaceResult(reqDate, interfaceId, seq);
		
		System.out.println(Util.toJSONString(deployment));
	}

	@Test
	public void testGetInterfaceLastVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertDeployment() {
		fail("Not yet implemented");
	}

}
