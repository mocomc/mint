/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.util.Util;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * ServerAppMngServiceTest.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class ServerAppMngServiceTest {


	@Autowired
	ServerAppMngService serverAppMngService;

	@Test
	public void testGetAppInfoList() throws Exception {
		try {
		Map params = new HashMap();
		params.put("applicationNm", "WMQ");
		List<ApplicationInfo> list = serverAppMngService.getAppInfoList(params);
		System.out.println("res:"+Util.toJSONPrettyString(list));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@Test
	public void testUpsertAppInfo() throws Exception {

		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setServerId("SV00000001");
		applicationInfo.setApplicationNm("WMQ");
		applicationInfo.setType(ApplicationInfo.TYPE_WMQ);
		applicationInfo.setHome("/var/mqm");
		applicationInfo.setVersion("8.1.3");
		applicationInfo.setModDate(Util.getFormatedDate());
		applicationInfo.setModId("eai");
		applicationInfo.setRegDate(Util.getFormatedDate());
		applicationInfo.setRegId("eai");
		serverAppMngService.upsertAppInfo(applicationInfo);



	}

}


