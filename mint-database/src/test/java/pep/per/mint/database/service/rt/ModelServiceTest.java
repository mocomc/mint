/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.rt;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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

import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppModelAttribute;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeCode;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.AppType;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModelHistory;
import pep.per.mint.common.data.basic.runtime.MappingModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Jul 10, 2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class ModelServiceTest {

	@Autowired
	ModelService modelService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testGetAppModelAttributeIdMap() throws Exception {

		Map<String, List<AppModelAttributeId>> map = modelService.getAppModelAttributeIdMap(null);

		if(!Util.isEmpty(map)) logger.debug("map:" + Util.toJSONPrettyString(map));

	}


	@Test
	public void testCreateAppModelAttributeIdMap() throws Exception {

		AppModelAttributeId attr = new AppModelAttributeId();
		attr.setAppType("VFS");
		attr.setType("0");
		attr.setCd("addAttr1");
		attr.setComments("가나다라마바사");
		attr.setDelYn("N");
		attr.setInputType("0");
		attr.setName("추가속성1");
		attr.setTagYn("N");
		attr.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		attr.setRegId("iip");

		List<AppModelAttributeCode> cds = new ArrayList<AppModelAttributeCode>();
		AppModelAttributeCode cd1 = new AppModelAttributeCode();
		cd1.setAttrCd("TEST1");
		cd1.setAttrName("테스트코드1");
		cd1.setComments("test");
		cd1.setAttrSeq(1);
		cd1.setDelYn("N");
		cd1.setRegId("iip");
		cd1.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		cds.add(cd1);

		attr.setCds(cds);

		List<AppModelAttributeId> req = new ArrayList<AppModelAttributeId>();
		req.add(attr);
		modelService.createAttributes(req, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");

		logger.debug("attr:" + Util.toJSONPrettyString(attr));

	}


	@Test
	public void testUpdateAppModelAttributeIdMap() throws Exception {
		Map params = new HashMap();
		params.put("appType", "FWS");
		List<AppModelAttributeId> list = modelService.getAppModelAttributeIdList(params);

		logger.debug("\n\n" + Util.toJSONPrettyString(list) + "\n");

		String updateAttrCd = "serviceId";
		AppModelAttributeId updateAttr = null;
		for(AppModelAttributeId attr : list) {
			if( updateAttrCd.equals(attr.getCd())){
				updateAttr = attr;
			}
		}

		if(updateAttr != null) {
			updateAttr.setComments("update success...");
			List<AppModelAttributeCode> cds =  new ArrayList<AppModelAttributeCode>();
			cds.add(new AppModelAttributeCode("A","코드A",1));
			updateAttr.setCds(cds);
		}
		List<AppModelAttributeId> uptList = new ArrayList<AppModelAttributeId>();
		uptList.add(updateAttr);

		modelService.updateAttribute(updateAttr, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");

		modelService.updateAttributes(uptList, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");

	}


	@Test
	public void testDeleteAppModelAttributeIdMap() throws Exception {
		Map params = new HashMap();
		params.put("appType", "VFS");
		List<AppModelAttributeId> list = modelService.getAppModelAttributeIdList(params);
		modelService.deleteAttributes(list, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");

	}

	@Test
	public void testGetAppModelAttributeCodeList() throws Exception {

		Map params = new HashMap();
		params.put("appType", "VDS");
		params.put("aid", "3");

		List<AppModelAttributeCode> list = modelService.getAppModelAttributeCodeList(params);

		logger.debug("AppModelAttributeCode:" + Util.toJSONPrettyString(list));
	}



	@Test
	public void testNewAppModels() throws Exception {
		String interfaceId = "F@00000046";
		Map params = new HashMap();
		params.put("interfaceId", interfaceId);
		List<AppModel> list = modelService.newAppModels(params);
		assertNotNull(list);
		logger.debug("list:" + Util.toJSONPrettyString(list));
	}

	@Test
	public void testUpdateInterfaceModel()throws Exception {
		InterfaceModel model = modelService.getInterfaceModel("103");
		//model.setStd("N");
		modelService.updateInterfaceModel(model, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");
	}


	@Test
	public void testDeleteInterfaceModel()throws Exception {
		modelService.deleteInterfaceModel("102", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");
	}

	@Test
	public void testGetInterfaceModelHistory()throws Exception {
		List<InterfaceModelHistory> history = modelService.getInterfaceModelHistory("102");
		for (InterfaceModelHistory interfaceModelHistory : history) {
			assertNotNull(interfaceModelHistory.getInterfaceModel());
		}
	}


	@Test
	public void testCreateInterfaceModel()throws Exception {

		String interfaceId = "F@00000046";
		String comments = "직무송신인터페이스 실행모델1";
		String name = "직무송신인터페이스 실행모델1";
		String stage = "0";

		List<AppModel> appModels = new ArrayList<AppModel>();

		InterfaceModel interfaceModel = new InterfaceModel();
		interfaceModel.setAppModels(appModels);
		interfaceModel.setComments(comments);
		interfaceModel.setInterfaceId(interfaceId);
		interfaceModel.setName(name);
		interfaceModel.setStage(stage);


		AppModel am1 = new AppModel();
		am1.setCd("VDS");
		am1.setType("VDS");
		am1.setTagInfo("#비트리아#vitria#dbservice");
		am1.setComments("비트리아디비송신서비스");
		am1.setName("비트리아디비송신서비스");
		am1.setServerId("SV00000005");
		am1.setSystemId("SS00000007");
		am1.setSystemSeq(0);
		am1.setSystemType("0");
		appModels.add(am1);


		Map<String, List<AppModelAttribute>> attributes1 = new HashMap<String, List<AppModelAttribute>>();
		am1.setAttributes(attributes1);
		List<AppModelAttribute> attributes1List = Arrays.asList(new AppModelAttribute());
		attributes1List.get(0).setAid("1");
		attributes1List.get(0).setAppType("VDS");
		attributes1List.get(0).setTag("HUSDB");
		attributes1List.get(0).setVal("HUSDB");
		attributes1.put("serviceName", attributes1List);


		AppModel am2 = new AppModel();
		am2.setCd("VFS");
		am2.setType("VFS");
		am2.setTagInfo("#비트리아#vitria#fileservice");
		am2.setComments("비트리아파일수신서비스");
		am2.setName("비트리아파일수신서비스");
		am2.setServerId("SV00000003");
		am2.setSystemId("SS00000004");
		am2.setSystemSeq(1);
		am2.setSystemType("2");
		appModels.add(am2);
		Map<String, List<AppModelAttribute>> attributes2 = new HashMap<String, List<AppModelAttribute>>();
		am2.setAttributes(attributes2);
		List<AppModelAttribute> attributes2List = Arrays.asList(new AppModelAttribute());
		attributes2List.get(0).setAid("1");
		attributes2List.get(0).setAppType("VFS");
		attributes2List.get(0).setTag("/usr/eai");
		attributes2List.get(0).setVal("/usr/eai");
		attributes2.put("filePath", attributes2List);


		MessageModel msgModel = new MessageModel();
		msgModel.setCd("RCV-DATASET-2");
		msgModel.setComments("수신메시지");
		msgModel.setDataSetId("2");
		msgModel.setName("수신메시지");
		msgModel.setType("2");
		am2.setMsgs(Arrays.asList(msgModel));


		MappingModel mappingModel = new MappingModel();
		mappingModel.setCd("RCV-MAP-2");
		mappingModel.setComments("수신매핑");
		mappingModel.setMapId("2");
		mappingModel.setName("수신매핑");
		mappingModel.setType("2");
		am2.setMappings(Arrays.asList(mappingModel));


		logger.debug("inserted interfaceModel:" + Util.toJSONPrettyString(interfaceModel));

		//modelService.createInterfaceModel(interfaceModel, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");

		//logger.debug("inserted interfaceModel:" + Util.toJSONPrettyString(interfaceModel));
	}

	@Test
	public void testGetInterfaceModelList()throws Exception {
		Map params = new HashMap();
		//params.put("interfaceMid", "111");
		List<InterfaceModel> list = modelService.getInterfaceModelList(params);
		logger.debug("interfaceModel list :" + Util.toJSONPrettyString(list));
	}

	@Test
	public void testGetInterfaceModel()throws Exception {

		InterfaceModel model = modelService.getInterfaceModel("144");
		logger.debug("interfaceModel  :" + Util.toJSONPrettyString(model));
	}

	@Test
	public void testGetAppTypes() throws Exception {
		String resourceCd = "0";
		List<AppType> list = modelService.getAppTypes(resourceCd);
		logger.debug("appTypes:"  + Util.toJSONPrettyString(list));

	}

}
