/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.agent.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Sep 7, 2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/agent-context.xml"})
public class SystemResourceUtilBy3PartyTest {

	@Autowired
	SystemResourceUtilBy3Party systemResourceUtil;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.agent.util.SystemResourceUtilBy3Party#getCpuUsage(pep.per.mint.common.data.basic.agent.ResourceInfo)}.
	 */
	@Test
	public void testGetCpuUsage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.agent.util.SystemResourceUtilBy3Party#getMemoryUsage(pep.per.mint.common.data.basic.agent.ResourceInfo)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetMemoryUsage() throws Exception {
		String version = System.getProperty("java.version");
		System.out.println("JAVA Version : " + version);
		ResourceUsageLog log = systemResourceUtil.getMemoryUsage(null);
		System.out.println("log:" + Util.toJSONPrettyString(log));
	}

	/**
	 * Test method for {@link pep.per.mint.agent.util.SystemResourceUtilBy3Party#getDiskUsage(pep.per.mint.common.data.basic.agent.ResourceInfo)}.
	 */
	@Test
	public void testGetDiskUsage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pep.per.mint.agent.util.SystemResourceUtilBy3Party#getProcessStatusLog(pep.per.mint.common.data.basic.agent.ProcessInfo)}.
	 */
	@Test
	public void testGetProcessStatusLog() {
		fail("Not yet implemented");
	}

}
