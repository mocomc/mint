/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import static org.junit.Assert.*;

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

import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * ConfigMngServiceTest.java
 * </pre>
 * @author whoana
 * @date Nov 1, 2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class ConfigMngServiceTest {


	Logger logger = LoggerFactory.getLogger(ConfigMngServiceTest.class);

	@Autowired
	ConfigMngService service;

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#createConfig(pep.per.mint.common.data.basic.ConfigInfo)}.
	 */
	@Test
	public void testCreateConfig() {


		try {
			ConfigInfo info = new ConfigInfo();
			info.setSystemId("SS00000070");
			info.setServerId("SV00000023");
			info.setType("9");
			info.setFileNm("/usr/eai/api/api.cfg");
			info.setProductNm("API");
			info.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			info.setRegId("iip");
			int res = service.createConfig(info);

		} catch (Exception e) {
			logger.error("", e);
			fail("testCreateConfig fail");
		}

	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#updateConfig(pep.per.mint.common.data.basic.ConfigInfo)}.
	 */
	@Test
	public void testUpdateConfig() {
		try {
			ConfigInfo info = new ConfigInfo();
			info.setSystemId("SS00000062");
			info.setServerId("SV00000010");
			info.setType("7");
			info.setSeq(2);
			info.setFileNm("/usr/eai/tmax/tamx1.cfg");
			info.setProductNm("TMAX");
			info.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			info.setModId("iip");

			System.out.println(Util.toJSONPrettyString(info));
			int res = service.updateConfig(info);

		} catch (Exception e) {
			logger.error("", e);
			fail("testUpdateConfig fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#deleteConfig(pep.per.mint.common.data.basic.ConfigInfo)}.
	 */
	@Test
	public void testDeleteConfig() {
		try {
			ConfigInfo info = new ConfigInfo();
			info.setSystemId("SS00000062");
			info.setServerId("SV00000010");
			info.setType("7");
			info.setSeq(1);
			info.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			info.setModId("iip");
			int res = service.deleteConfig(info);

		} catch (Exception e) {
			logger.error("", e);
			fail("testUpdateConfig fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#getConfigList(java.util.Map)}.
	 */
	@Test
	public void testGetConfigList() {
		try {
			Map info = new HashMap();
			info.put("systemId", "SS00000062");
			info.put("serverId", "SV00000010");
			info.put("type","7");
			info.put("seq",1);

			List<ConfigInfo> list = service.getConfigList(info);

			for(ConfigInfo ci : list ) {
				System.out.println(Util.toJSONString(ci));
			}

		} catch (Exception e) {
			logger.error("", e);
			fail("testGetConfigList fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#getInterfaceList(java.util.Map)}.
	 */
	@Test
	public void testGetInterfaceList() {

		try {
			Map info = new HashMap();
			info.put("systemId", "SS00000070");
			info.put("serverId", "SV00000023");
			info.put("type","9");
			info.put("seq",1);

			List<String> list = service.getInterfaceList(info);

			for(String integrationId : list ) {
				System.out.println(Util.toJSONString(integrationId));
			}

		} catch (Exception e) {
			logger.error("", e);
			fail("testGetInterfaceList fail");
		}

	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#getConfigInterfaceList(java.util.Map)}.
	 */
	@Test
	public void testGetConfigInterfaceList() {
		try {
			Map info = new HashMap();
			info.put("systemId", "SS00000070");
			info.put("serverId", "SV00000023");
			info.put("type","9");
			info.put("seq",1);

			List<String> list = service.getConfigInterfaceList(info);

			for(String integrationId : list ) {
				System.out.println(Util.toJSONString(integrationId));
			}

		} catch (Exception e) {
			logger.error("", e);
			fail("testGetInterfaceList fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#storeAndUpdateResult(pep.per.mint.common.data.basic.ConfigInfo)}.
	 */
	@Test
	public void testStoreAndUpdateResult() {
		try {

			ConfigInfo info = new ConfigInfo();
			info.setSystemId("SS00000070");
			info.setServerId("SV00000023");
			info.setType("9");
			info.setSeq(1);

			info.setFileNm("/usr/eai/api/api.cfg");
			info.setProductNm("API");
			info.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			info.setRegId("iip");


			List<String> intfList = new ArrayList<String>();
			info.setIntfList(intfList);
			intfList.add("SBCINCIT30");
			intfList.add("TEST-SBCINCIT30");
			intfList.add("TEST-1");
			intfList.add("TEST-a");
			intfList.add("TEST-100");
			intfList.add("TEST-99");

			service.storeAndUpdateResult(info);

		} catch (Exception e) {
			logger.error("", e);
			fail("testGetInterfaceList fail");
		}
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.im.ConfigMngService#getCompareList(pep.per.mint.common.data.basic.ConfigInfo)}.
	 */
	@Test
	public void testGetCompareList() {
		try {
			ConfigInfo info = new ConfigInfo();
			info.setSystemId("SS00000070");
			info.setServerId("SV00000023");
			info.setType("9");
			info.setSeq(1);

			Map res = service.getCompareList(info);

			System.out.println(Util.toJSONString(res));


		} catch (Exception e) {
			logger.error("", e);
			fail("testGetInterfaceList fail");
		}
	}

}
