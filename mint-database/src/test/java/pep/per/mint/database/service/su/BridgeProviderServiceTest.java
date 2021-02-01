/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.su;



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

import pep.per.mint.common.data.basic.dataset.DataSet;

import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since 2020. 11. 24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class BridgeProviderServiceTest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	BridgeProviderService bridgeService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.su.BridgeProviderService#getServiceCds(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetServiceCds() throws Exception {
		
		Map params = new HashMap();
		params.put("businessCd", "NCS");	 
		params.put("systemCd", "CUI");	 
		List<Map<String, String>> list = bridgeService.getInterfaces(params);
		
		logger.debug("list:\n" + Util.toJSONPrettyString(list));
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.su.BridgeProviderService#getInterfaceId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetInterfaceId() throws Exception {
		String interfaceId = bridgeService.getInterfaceId("CUIPRS0001");
		logger.debug("interfaceId :\n" + interfaceId);
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.su.BridgeProviderService#getDataSets(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetDataSets() throws Exception {
		String integrationId = "CUIPRS0001";
		String systemCd = "CUI";
		Map<String,List<DataSet>> dsc = bridgeService.getDataSets(integrationId, systemCd);
		Util.toJSONPrettyString("datasets\n" + Util.toJSONPrettyString(dsc));
	}

}
