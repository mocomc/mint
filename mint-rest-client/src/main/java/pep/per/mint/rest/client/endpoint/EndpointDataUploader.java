/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.rest.client.endpoint;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Jul 14, 2020
 */
public class EndpointDataUploader {

	String app = getClass().getName();

	String url = "http://localhost:8080/mint/rt/models/apps/attributes/reset?method=POST";				

	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		try {
			File dataFile = new File(EndpointDataUploader.class.getResource("/data/endpoint.json").getPath());
			RestTemplate rest = new RestTemplate();
			EndpointDataUploader edu = new EndpointDataUploader();
			edu.upload(rest, dataFile);
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
