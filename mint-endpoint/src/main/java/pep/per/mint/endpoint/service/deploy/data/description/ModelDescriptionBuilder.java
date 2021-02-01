/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.MappingModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.util.Util;

/**
 * @author whoana
 * @since Aug 18, 2020
 */
public class ModelDescriptionBuilder {
	
	
	public ModelDescription build(InterfaceModel model)throws Exception {
		
		 
		ModelDescription md = new ModelDescription();
		md.setId(model.getMid());
		md.setCreateDate(Util.getFormatedDate());
		md.setName(model.getName());
		md.setVersion("1.0");
		md.setStage(model.getStage());
		md.setInterfaceId(model.getIntegrationId());	
		
		
		List<AppModel> appModels = model.getAppModels();
		if(!Util.isEmpty(appModels)) {
			List<App> apps = new ArrayList<App>();
			
			for (AppModel appModel : appModels) {
				App app = new App();
				app.setId(appModel.getMid());
				app.setName(appModel.getName());
				app.setType(new AppType(appModel.getType(), appModel.getTypeName()));
				 
				app.setSystem(new SystemInfo(appModel.getSystemCd(), appModel.getSystemName(), appModel.getSystemType(), appModel.getSystemSeq()));
				app.setServer(new ServerInfo(appModel.getServerCd(), appModel.getServerName()));
				
				
				List<MessageModel> msgs = appModel.getMsgs();
				if(!Util.isEmpty(msgs)) {
					for (MessageModel msgModel : msgs) {
						String dataSetId = msgModel.getDataSetId();
						String type = msgModel.getType();
						String name = msgModel.getName();
						String cd   = msgModel.getCd();
						String definition = cd + ".xsd";
						Map<String, String> msgLayout = new HashMap<String, String>();
						msgLayout.put("id", dataSetId);
						msgLayout.put("type", type);
						app.getLayouts().add(msgLayout);
						
						Layout layout = new Layout(dataSetId, name, type, definition);
						md.getLayouts().put(dataSetId, layout);
					}
				}
				
				
				List<MappingModel> mappings = appModel.getMappings();
				if(!Util.isEmpty(mappings)) {
					for (MappingModel mappingModel : mappings) {
						String mappingId = mappingModel.getMapId();
						String type = mappingModel.getType();
						String name = mappingModel.getName();
						String cd   = mappingModel.getCd();
						String definition = cd + ".xml";
						Mapping mapping = new Mapping(mappingId, name, type, definition);
						md.getMappings().put(mappingId, mapping);
					}
				}
				
				
				apps.add(app);
			}
			md.setApps(apps);
		}
		
		return md;
	}
	
}
