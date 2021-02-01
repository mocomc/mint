/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppType;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModelTemplate;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.rt.ModelService;

/**
 * @author whoana
 * @since 2020. 10. 26.
 */
@Service
public class ModelTemplateService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final int SOURCE_TYPE_FILE      = 0;
	public static final int SOURCE_TYPE_REFERENCE = 1;
	public static String templatePath = "/Users/whoana/DEV/workspace/mint/mint-endpoint/model";

	@Autowired
	CommonService commonService;
	
	@Autowired 
	ModelService modelService;
	
	public InterfaceModelTemplate getTemplate() throws Exception {
		return getTemplate(SOURCE_TYPE_FILE);
	}
	
	public InterfaceModelTemplate getTemplate(int type) throws Exception {
	
		
		if(SOURCE_TYPE_FILE == type) {
			return getTemplateFromFile();
		}else if(SOURCE_TYPE_REFERENCE == type) {
			return getTemplateFromReference();
		}else {
			throw new Exception("Unsupported type option:(" + type + ")");
		}
	}
	
	private InterfaceModelTemplate getTemplateFromFile() throws Exception {
		InterfaceModelTemplate template = new InterfaceModelTemplate();
		templatePath = commonService.getEnvironmentalValue("system", "model.template.file.path", templatePath);
		File interfaceTemplate = new File(templatePath + File.separator + "interface.json");
		Interface interfaze = (Interface)Util.readObjectFromJson(interfaceTemplate, Interface.class, "UTF-8");			
		File interfaceModelTemplate = new File(templatePath + File.separator + "interfaceModel.json");
		InterfaceModel interfaceModel = (InterfaceModel)Util.readObjectFromJson(interfaceModelTemplate, InterfaceModel.class, "UTF-8");
		template.setInterfaceModel(interfaceModel);
		template.setInterfceInfo(interfaze);
		return template;
	}
	
	private InterfaceModelTemplate getTemplateFromReference() throws Exception {

		  
		
		String integrationId = commonService.getEnvironmentalValue("system", "model.template.integration.id", "DF00000000");
		
		Interface interfaze = commonService.getInterfaceDetail(integrationId);
		if(interfaze == null) {
			throw new Exception("There is no definition of interface(" + integrationId + ") for the model, please check the value of attribute \"model.template.integration.id\" in TSU0302");
		}
		 
		 
		  
		String comments = "표준F/W 인터페이스 실행모델";
		String name = "표준F/W 인터페이스 실행모델";
		String stage = "0";
		
		List<AppModel> appModels = new ArrayList<AppModel>();
		
		InterfaceModel interfaceModel = new InterfaceModel();
		interfaceModel.setAppModels(appModels);
		interfaceModel.setComments(comments);
		interfaceModel.setInterfaceId(interfaze.getIntegrationId());
		interfaceModel.setName(name);
		interfaceModel.setStage(stage); 
		
		
		String appTypeCd = commonService.getEnvironmentalValue("system", "model.template.app.type", "FWS");
		AppType appType = modelService.getAppType(appTypeCd);
		if(appType == null) {
			throw new Exception("There is no appType[" + appTypeCd + "] defined in TSU0302[model.template.app.type]. ");
		}

		AppModel am1 = new AppModel();
		AppModel am2 = new AppModel();
		
		List<System> systems = interfaze.getSystemList();
		for (System system : systems) {
			if("0".equals(system.getNodeType())){
				am1.setName("송신서비스APP");
				am1.setCd(appType.getAppType());
				am1.setType(appType.getAppType());
				am1.setSystemId(system.getSystemId());
				am1.setSystemCd(system.getSystemCd());
				am1.setSystemSeq(system.getSeq());
				am1.setSystemType(system.getNodeType());
				
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("systemId", system.getSystemId());
				List<System> systemDetails = commonService.getSystemList(params);
				List<Server> servers = systemDetails.get(0).getServerList();
				//List<Server> servers = system.getServerList();
				
				if(Util.isEmpty(servers)) throw new Exception("The interface model system[" + system.getSystemNm() + "] must have server. please check whether the system has any server info.");
				
				
				Server server = servers.get(0);
				am1.setServerCd(server.getServerCd());
				am1.setServerId(server.getServerId());
				am1.setServerName(server.getServerNm());
				appModels.add(am1); 
			}else if ("2".equals(system.getNodeType())){
				am2.setName("수신서비스APP");
				am2.setCd(appType.getAppType());
				am2.setType(appType.getAppType());
				am2.setSystemId(system.getSystemId());
				am2.setSystemCd(system.getSystemCd());
				am2.setSystemSeq(system.getSeq());
				am2.setSystemType(system.getNodeType());
				
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("systemId", system.getSystemId());
				List<System> systemDetails = commonService.getSystemList(params);
				List<Server> servers = systemDetails.get(0).getServerList();
				//List<Server> servers = system.getServerList();
				
				
				if(Util.isEmpty(servers)) throw new Exception("The interface model system[" + system.getSystemNm() + "] must have server. please check whether the system has any server info.");
				Server server = servers.get(0);
				am2.setServerCd(server.getServerCd());
				am2.setServerId(server.getServerId());
				am2.setServerName(server.getServerNm());
				appModels.add(am2);
			}
		}
		
		InterfaceModelTemplate imt = new InterfaceModelTemplate();
		imt.setInterfceInfo(interfaze);
		imt.setInterfaceModel(interfaceModel);
		
		logger.debug("InterfaceModelTemplate:" + Util.toJSONPrettyString(imt));
		
		return imt;
	}
	 
}
 

