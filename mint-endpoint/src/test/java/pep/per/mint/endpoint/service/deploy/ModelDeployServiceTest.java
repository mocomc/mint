/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.util.Util;
import pep.per.mint.endpoint.Variables;
import pep.per.mint.endpoint.service.deploy.data.description.ModelDescription;

/**
 * @author whoana
 * @since Jul 30, 2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/endpoint-context.xml"})
public class ModelDeployServiceTest {

	@Autowired
	ModelDeployService deployModelService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.endpoint.service.deploy.ModelDeployService#buildModelDescription()}.
	 * @throws Exception Exception
	 */
	@Test
	public void testBuildModelDescription() throws Exception {
		ModelDescription md = deployModelService.buildModelDescription("100");
		System.out.println("model:\n" + Util.toJSONPrettyString(md));

	}

	@Autowired
	Variables var;

	@Test
	public void testGetVariables() throws Exception {
		String stringTypeCode = "0";
		System.out.println("string xsd type :\n" + var.xsdTypeMap.get(stringTypeCode));
	}

	@Test
	public void testGetDeployModelXml() throws Exception {

		String xml  = deployModelService.getDeployModelXml("143", "iip");
		System.out.println("xml:\n" + xml);
	}

	@Test
	public void testBuildMapping() throws Exception {
		deployModelService.buildMapping("1");

	}

	@Test
	public void testBuildMessageLayout() throws Exception {
		//String layout = deployModelService.buildMessageLayout("2");
		String layout = deployModelService.buildMessageLayout("15");
		System.out.println("layout:" + layout);
	}

	@Test
	public void testDeployToFile() throws Exception{
		String dest = "./deploy";
		String interfaceMid = "0";
		deployModelService.deployModelToJsonFile(dest, interfaceMid);
	}

	@Test
	public void testDeployToXmlFile() throws Exception{
		String dest = "./deploy";
		String interfaceMid = "101";
//		String interfaceMid = "0";
		deployModelService.deployModelToXmlFile(dest, interfaceMid, "iip");
	}

}
