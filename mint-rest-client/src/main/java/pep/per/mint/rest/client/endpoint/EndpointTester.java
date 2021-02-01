/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.rest.client.endpoint;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppModelAttribute;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Jul 14, 2020
 */
public class EndpointTester {

	RestTemplate rest;
 
	
	
	String app = getClass().getName();

	String url = "http://localhost:8080/mint/rt/models/apps/interfaces?method=POST";				

	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		try {
//			File dataFile = new File(EndpointDataUploader.class.getResource("/data/model.json").getPath());
//			RestTemplate rest = new RestTemplate();
//			EndpointTester test = new EndpointTester();
//			test.upload(rest, dataFile);
//			
			
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
			
			System.out.println("inserted interfaceModel:" + Util.toJSONPrettyString(interfaceModel));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void upload(RestTemplate rest, File dataFile) throws Exception { 
		 
		Map<String, List<AppModelAttributeId>> attributeIdMap = (Map<String, List<AppModelAttributeId>>) Util.readObjectFromJson(dataFile, Map.class, "UTF-8");
		
		ComMessage<Map<String, List<AppModelAttributeId>>, Map<String, List<AppModelAttributeId>>> request = new ComMessage<Map<String, List<AppModelAttributeId>>, Map<String, List<AppModelAttributeId>>>();
		request.setAppId(app) ;
		request.setCheckSession(false);
		request.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		request.setUserId("iip");
		 		
		request.setRequestObject(attributeIdMap);
		ComMessage res = rest.postForObject(url, request, ComMessage.class);
		System.out.println("res:" + Util.toJSONString(res));
			
	}
	
	
}
