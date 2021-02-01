/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.co;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
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

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class CommonServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CommonServiceTest.class);

	@Autowired
	CommonService commonService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testGetSendReceiveSystemList(){
		try {
			List<Map> res = commonService.getSendReceiveSystemList();
			if(res != null){
				logger.debug(Util.join("getSendReceiveSystemList:",Util.toJSONString(res)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetBasicInfo",e);
			fail("Fail testGetBasicInfo");
		}
	}

	@Test
	public void testGetBasicInfo(){
		try {
			pep.per.mint.common.data.basic.BasicInfo basicInfo = commonService.getBasicInfo("iip");
			if(basicInfo != null){
				logger.debug(Util.join("basicInfo:",Util.toJSONString(basicInfo)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetBasicInfo",e);
			fail("Fail testGetBasicInfo");
		}
	}


	@Test
	public void testGetSearchInfo(){
		try {
			Map params = new HashMap();
			params.put("systemId","SS00000002");

			pep.per.mint.common.data.basic.BasicInfo basicInfo = commonService.getSearchInfo(params);
			if(basicInfo != null){
				logger.debug(Util.join("basicInfo:",Util.toJSONString(basicInfo)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetBasicInfo",e);
			fail("Fail testGetBasicInfo");
		}
	}


	@Test
	public void testGetSystemCdList(){
		try {
			List<pep.per.mint.common.data.basic.System> list = commonService.getSystemCdList();
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.System systemCd : list){
					logger.debug(Util.join("SystemCode[",i ++,"]:",Util.toJSONString(systemCd)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetSystemCdList",e);
			fail("Fail testGetSystemCdList");
		}
	}


	@Test
	public void testGetRootSystemCdList(){
		try {
			List<pep.per.mint.common.data.basic.System> list = commonService.getRootSystemCdList();
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.System systemCd : list){
					logger.debug(Util.join("SystemCode[",i ++,"]:",Util.toJSONString(systemCd)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetSystemCdList",e);
			fail("Fail testGetSystemCdList");
		}
	}

	@Test
	public void testGetChildSystemCdList(){
		try {
			Map params = new HashMap();
			params.put("parentSystemId","SS00000034");
			List<pep.per.mint.common.data.basic.System> list = commonService.getChildSystemCdList(params);
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.System systemCd : list){
					logger.debug(Util.join("SystemCode[",i ++,"]:",Util.toJSONString(systemCd)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetSystemCdList",e);
			fail("Fail testGetSystemCdList");
		}
	}



	@Test
	public void getSystemCdListByRelation(){
		try {
			Map params = new HashMap();
			params.put("channelNm", "IIB");
			List<pep.per.mint.common.data.basic.System> list = commonService.getSystemCdListByRelation(params);
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.System systemCd : list){
					logger.debug(Util.join("SystemCode[",i ++,"]:",Util.toJSONString(systemCd)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("getSystemCdListByRelation",e);
			fail("Fail getSystemCdListByRelation");
		}
	}

	@Test
	public void testGetServerCdList(){
		try {
			List<pep.per.mint.common.data.basic.Server> list = commonService.getServerCdList();
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.Server server : list){
					logger.debug(Util.join("SystemCode[",i ++,"]:",Util.toJSONString(server)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetServerCdList",e);
			fail("Fail testGetServerCdList");
		}
	}

	@Test
	public void testGetBusinessCdList(){
		try {
			List<pep.per.mint.common.data.basic.Business> list = commonService.getBusinessCdList();
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.Business Business : list){
					logger.debug(Util.join("Business[",i ++,"]:",Util.toJSONString(Business)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetBusinessCdList",e);
			fail("Fail testGetBusinessCdList");
		}
	}


	@Test
	public void testGetRootBusinessCdList(){
		try {
			List<pep.per.mint.common.data.basic.Business> list = commonService.getRootBusinessCdList();
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.Business Business : list){
					logger.debug(Util.join("Business[",i ++,"]:",Util.toJSONString(Business)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRootBusinessCdList",e);
			fail("Fail testGetRootBusinessCdList");
		}
	}

	@Test
	public void testGetBusinessRootCdListByRelation(){
		try {
			Map params = new HashMap();

			List<pep.per.mint.common.data.basic.Business> list = commonService.getBusinessRootCdListByRelation(params);
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.Business Business : list){
					logger.debug(Util.join("Business[",i ++,"]:",Util.toJSONString(Business)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRootBusinessCdList",e);
			fail("Fail testGetRootBusinessCdList");
		}
	}


	@Test
	public void testGetChildBusinessCdList(){
		try {
			List<pep.per.mint.common.data.basic.Business> list = commonService.getChildBusinessCdList("BZ00000001");
			if(list != null){
				int i = 0;
				for(pep.per.mint.common.data.basic.Business Business : list){
					logger.debug(Util.join("Business[",i ++,"]:",Util.toJSONString(Business)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetChildBusinessCdList",e);
			fail("Fail testGetChildBusinessCdList");
		}
	}


	@Test
	public void testGetInterfaceCdList(){
		try {
			long elapsed = java.lang.System.currentTimeMillis();
			List<Interface> list = commonService.getInterfaceCdList();
			java.lang.System.out.println("elapaed v1:" + (java.lang.System.currentTimeMillis() - elapsed));
			elapsed = java.lang.System.currentTimeMillis();
			List<Map> list2 = commonService.getInterfaceCdListV2();
			java.lang.System.out.println("elapaed v2:" + (java.lang.System.currentTimeMillis() - elapsed));

			/*if(list != null){
				int i = 0;
				for(Interface interfaceCd : list){
					logger.debug(Util.join("InterfaceCode[",i ++,"]:",Util.toJSONString(interfaceCd)));
				}
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			if(logger != null)
				logger.error("testGetInterfaceCdList",e);
			fail("Fail testGetInterfaceCdList");
		}
	}


	@Test
	public void testGetInterfaceCdListByRelation(){
		try {
			Map params = new HashMap();
			//params.put("systemNm","시스템001");
			List<Interface> list = commonService.getInterfaceCdListByRelation(params);
			if(list != null){
				int i = 0;
				for(Interface interfaceCd : list){
					logger.debug(Util.join("InterfaceCode[",i ++,"]:",Util.toJSONString(interfaceCd)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceCdListByRelation",e);
			fail("Fail testGetInterfaceCdListByRelation");
		}
	}




	@Test
	public void testGetCommonCodeList() {
		//리소스 코드 리스트 조회
		String level1 = "";//null;//"IM";
		String level2 = "";//null;//"04";
		try {
			List<CommonCode> list = commonService.getCommonCodeList(level1, level2);

			if(list != null){
				int i = 0;
				for(CommonCode commonCode : list){
					logger.debug(Util.join("CommonCode[",i ++,"]:",Util.toJSONString(commonCode)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetCommonCodeList",e);
			fail("Fail testGetCommonCodeList");
		}

	}


	@Test
	public void testGetCommonCode() {
		//리소스 코드 리스트 조회
		String level1 = "IM";
		String level2 = "04";
		String cd = "0";
		try {
			CommonCode commonCode = commonService.getCommonCode(level1, level2, cd);

			if(commonCode != null){
				logger.debug(Util.join("CommonCode:",Util.toJSONString(commonCode)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetCommonCode",e);
			fail("Fail testGetCommonCode");
		}

	}

	@Test
	public void testGetChannelList(){
		try {
			List<Channel> list = commonService.getChannelList();
			if(list != null){
				int i = 0;
				for(Channel channel : list){
					logger.debug(Util.join("Channel[",i ++,"]:",Util.toJSONString(channel)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetChannelList",e);
			fail("Fail testGetChannelList");
		}
	}

	@Test
	public void testGetChannelListByRelation(){
		try {
			Map params = new HashMap();

			List<Channel> list = commonService.getChannelListByRelation(params);
			if(list != null){
				int i = 0;
				for(Channel channel : list){
					logger.debug(Util.join("Channel[",i ++,"]:",Util.toJSONString(channel)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetChannelList",e);
			fail("Fail testGetChannelList");
		}
	}



	@Test
	public void testGetServiceList(){
		try {
			List<String> list = commonService.getServiceList();
			if(list != null){
				int i = 0;
				for(String service : list){
					logger.debug(Util.join("Service[",i ++,"]:",Util.toJSONString(service)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetServiceList",e);
			fail("Fail testGetServiceList");
		}
	}

	@Test
	public void testGetInterfaceTagList(){
		try {
			List<InterfaceTag> list = commonService.getInterfaceTagList();
			if(list != null){
				int i = 0;
				for(InterfaceTag interfaceTag : list){
					logger.debug(Util.join("InterfaceTag[",i ++,"]:",Util.toJSONString(interfaceTag)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceTagList",e);
			fail("Fail testGetInterfaceTagList");
		}
	}

	@Test
	public void testGetRequirementCdList(){
		try {
			List<Requirement> list = commonService.getRequirementCdList();
			if(list != null){
				int i = 0;
				for(Requirement requirement : list){
					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirement)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementCodeList",e);
			fail("Fail testGetRequirementCodeList");
		}
	}


	@Test
	public void testGetAppList(){
		try {
			List<App> list = commonService.getAppList();
			if(list != null){
				int i = 0;
				for(App app : list){
					logger.debug(Util.join("App[",i ++,"]:",Util.toJSONString(app)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetAppList",e);
			fail("Fail testGetAppList");
		}
	}


	@Test
	public void testGetUserList(){
		try {
			Map<String,String> params = new HashMap<String,String>();
			//params.put("email", "dh");
			List<User> user = commonService.getUserList(params);
			if(user != null){
				logger.debug(Util.join("user:",Util.toJSONString(user)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetUserList",e);
			fail("Fail testGetUserList");
		}
	}

	@Test
	public void testGetUser(){
		try {
			User user = commonService.getUser("11825");
			if(user != null){
				logger.debug(Util.join("user:",Util.toJSONString(user)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetUser",e);
			fail("Fail testGetUser");
		}
	}




	@Test
	public void testGetSystemList(){
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			//"systemId":"SS00000009","systemNm":"활동비관리시스템","systemCd":"009"
			//"systemId":"SS00000027","systemNm":"품질관리(QM)","systemCd":"027",

			//params.put("systemId","SS00000009");
			//params.put("systemNm","품질");
			params.put("systemCd","02");

			List<System> list = commonService.getSystemList(params);
			if(list != null){
				int i = 0;
				for(System item : list){
					logger.debug(Util.join("System[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetSystemList",e);
			fail("Fail testGetSystemList");
		}
	}


	@Test
	public void testGetServerList(){
		try {
			Map<String, Object> params = new HashMap<String,Object>();

			List<Server> list = commonService.getServerList(params);
			if(list != null){
				int i = 0;
				for(Server item : list){
					logger.debug(Util.join("Server[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetServerList",e);
			fail("Fail testGetServerList");
		}
	}




	@Test
	public void testGetBusinessList(){
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			List<Business> list = commonService.getBusinessList(params);
			if(list != null){
				int i = 0;
				for(Business business : list){
					logger.debug(Util.join("business[",i ++,"]:",Util.toJSONString(business)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetBusinessList",e);
			fail("Fail testGetBusinessList");
		}
	}


	@Test
	public void testGetInterfacePatternList(){
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			List<InterfacePattern> list = commonService.getInterfacePatternList(params);
			if(list != null){
				int i = 0;
				for(InterfacePattern interfacePattern : list){
					logger.debug(Util.join("InterfacePattern[",i ++,"]:",Util.toJSONString(interfacePattern)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfacePatternList",e);
			fail("Fail testGetInterfacePatternList");
		}
	}



	@Test
	public void testGetInterfaceList(){
		try {
			Map<String, String> params = new HashMap<String,String>();
			//params.put("sendService", "Z_MM_EE.RFC");
			//params.put("sendSystemNm", "EDI");
			//params.put("sendResourceNm", "DB");
			//params.put("receiveResourceNm", "F");
			params.put("service", "PERPP0412.RFC");

			List<Interface> list = commonService.getInterfaceList(params);
			if(list != null){
				int i = 0;
				for(Interface intf : list){
					logger.debug(Util.join("Interface[",i ++,"]:",Util.toJSONString(intf)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceList",e);
			fail("Fail testGetInterfaceList");
		}
	}


	@Test
	public void testGetInterfaceDetail(){
		try {
			Interface intf = commonService.getInterfaceDetail("F@00000381");
			if(intf != null){

				logger.debug(Util.join("Interface:",Util.toJSONString(intf)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceDetail",e);
			fail("Fail testGetInterfaceDetail");
		}
	}

	@Test
	public void testGetInterfaceDetailRequiremnets(){
		try {
			List<Requirement> list = commonService.getInterfaceDetailRequiremnets("F@00000001");
			if(list != null){
				int i = 0;
				for(Requirement intf : list){
					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(intf)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceDetailRequiremnets",e);
			fail("Fail testGetInterfaceDetailRequiremnets");
		}
	}

	@Test
	public void testGenerateIntegrationId(){
		try {
			String prefix= "SIFSR";
			int startIndex = 6;
			int endIndex = 11;
			int seqLength = 6;
			String idx = commonService.generateIntegrationId(prefix, startIndex, endIndex, seqLength);
			logger.debug("idx:" + idx);

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGenerateIntegrationId",e);
			fail("Fail testGenerateIntegrationId");
		}
	}

	@Test
	public void testExistInterface(){

		try {
			Map params = new HashMap();
			params.put("sendService","2서3");
			params.put("sendSystemId","SS00000135");
			//params.put("receiveService","");
			//params.put("sendSystemId", "");

			String interfaceId = commonService.existInterface(params);
			if(Util.isEmpty(interfaceId)){
				logger.debug("Interface is not exist!!!");
			}else{
				logger.debug(Util.join("Interface[", interfaceId ,"] is exist!!!"));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetInterfaceDetail",e);
			fail("Fail testGetInterfaceDetail");
		}
	}


	@Test
	public void testGetMenuList(){
		try {
			List<Menu> list = commonService.getMenuList("iip");
			if(list != null){
				int i = 0;
				for(Menu menu : list){
					logger.debug(Util.join("menu[",i ++,"]:",Util.toJSONString(menu)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetMenuList",e);
			fail("Fail testGetMenuList");
		}
	}

	@Test
	public void testGetMenuPathList(){
		try {
			List<Map<String,String>> list = commonService.getMenuPathList("MN00000003");
			if(list != null){
				int i = 0;
				for(Map path : list){
					logger.debug(Util.join("menu path[",i ++,"]:",Util.toJSONString(path)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetMenuList",e);
			fail("Fail testGetMenuList");
		}
	}


	@Test
	public void testGetEnvironmentalValues(){
		try {
			Map<String,List<String>> environmentalValues = commonService.getEnvironmentalValues();
			if(environmentalValues != null){
				Iterator<String> ito = environmentalValues.keySet().iterator();
				for(int i = 0; ito.hasNext() ;){
					String key = ito.next();
					List<String> values = environmentalValues.get(key);
					logger.debug(Util.join("key:",key,", environmentalValues[",i ++,"]:",Util.toJSONString(values)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetMenuList",e);
			fail("Fail testGetMenuList");
		}
	}

	@Test
	public void testGetEnvironmentalValueList() throws Exception{
		String packageId = "layout";
		String attributeId = "xsd.type.mapping.complex";
		List<String> environmentalValues = commonService.getEnvironmentalValueList(packageId, attributeId);
		logger.debug( packageId+"."+attributeId + ":\n" + Util.toJSONString(environmentalValues));

		 
	}



	@Test
	public void testGetAsisInterfaceList(){
		try {
			List<Map> list = commonService.getAsisInterfaceList("CN00000002");

			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("asis interface[",i ++,"]:",Util.toJSONString(item)));
				}
			}
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetMenuList",e);
			fail("Fail testGetMenuList");
		}
	}

}
