/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.su;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.an.DataSetMapper;
import pep.per.mint.database.mapper.rt.ModelMapper;
import pep.per.mint.database.mapper.su.BridgeProviderMapper;

/**
 * @author whoana
 * @since 2020. 11. 20.
 */
@Service
public class BridgeProviderService {
	
	@Autowired
	BridgeProviderMapper bridgeMapper;
	
	@Autowired
	DataSetMapper dataSetMapper;
	
	@Autowired 
	ModelMapper modelMapper;
	
	/**
	 * 
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getInterfaces(Map params) throws Exception {
		return bridgeMapper.getInterfaces(params);
	}

	public String getInterfaceId(String integrationId) throws Exception {
		List<String> interfaceIds = bridgeMapper.getInterfaceId(integrationId);
		if(interfaceIds == null || interfaceIds.size() == 0) return null;
		return interfaceIds.get(0);
	}
	
	public Map<String,List<DataSet>> getDataSets(String integrationId, String systemCd) throws Exception {
		
		Map<String,List<DataSet>> res = new HashMap<String,List<DataSet>>();
		List<DataSet> inputs = new ArrayList<DataSet>();
		List<DataSet> outputs = new ArrayList<DataSet>();
		res.put("in", inputs);
		res.put("out", outputs);
		
		String interfaceId = getInterfaceId(integrationId);
		
		if(interfaceId != null) { 
		
			Map<String, String> params = new HashMap<String, String>();
		
			params.put("interfaceId", interfaceId);
			
			List<InterfaceModel> models = modelMapper.getInterfaceModelList(params);
			if(!Util.isEmpty(models)) {
				InterfaceModel model = models.get(0);
				List<AppModel> appModels = model.getAppModels();
				if(!Util.isEmpty(appModels)) {
					for (AppModel appModel : appModels) {
						
						if(! systemCd.equals(appModel.getSystemCd())) continue;
						
						List<MessageModel> msgModels = appModel.getMsgs();
						for (MessageModel msgModel : msgModels) {
							String type = msgModel.getType();
							String dataSetId = msgModel.getDataSetId();
							DataSet dataSet = dataSetMapper.getSimpleDataSet(dataSetId, "N");
							if("0".equals(type)) {
								inputs.add(dataSet);//res.get("0").add(dataSet);
								
							}else if("1".equals(type)) {
								outputs.add(dataSet); //res.get("1").add(dataSet);
							}
						}
					}
				}
			}
		}
		
		
		return res;
	}
	
}
