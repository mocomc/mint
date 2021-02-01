/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.database.mapper.im.ConfigMngMapper;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * ConfigMngService.java
 * </pre>
 * @author whoana
 * @date Oct 31, 2018
 */
@Service
public class ConfigMngService {

	@Autowired
	ConfigMngMapper configMngMapper;

	public int createConfig(ConfigInfo info) throws Exception{
		return configMngMapper.createConfig(info);
	}

	public int updateConfig(ConfigInfo info) throws Exception{
		return configMngMapper.updateConfig(info);
	}

	public int deleteConfig(ConfigInfo info) throws Exception{
		return configMngMapper.deleteConfig(info);
	}

	public List<ConfigInfo> getConfigList(Map params) throws Exception{
		return configMngMapper.getConfigList(params);
	}

	public List<String> getInterfaceList(Map params) throws Exception{
		return configMngMapper.getInterfaceList(params);
	}

	public List<String> getConfigInterfaceList(Map params) throws Exception{
		return configMngMapper.getConfigInterfaceList(params);
	}

	@Transactional
	public void storeAndUpdateResult(ConfigInfo info) throws Exception{
		List<String> configList = info.getIntfList();
		if(configList == null || configList.size() == 0) throw new Exception("Config file have no interface list.");

		Map params = new HashMap();
		params.put("systemId", info.getSystemId());
		params.put("serverId", info.getServerId());
		params.put("type", info.getType());
		params.put("seq", info.getSeq());


		configMngMapper.deleteConfigInterfaceList(params);
		for(String integrationId : configList) {
			params.put("integrationId", integrationId);
			configMngMapper.insertConfigInterface(params);
		}


		List<String> interfaceList = configMngMapper.getInterfaceList(params);
		if(interfaceList == null || interfaceList.size() == 0) {
			info.setResult(ConfigInfo.RS_DIFFERENT);
			configMngMapper.updateConfig(info);
			return;
		}

		if(interfaceList.size() != configList.size()) {
			info.setResult(ConfigInfo.RS_DIFFERENT);
			configMngMapper.updateConfig(info);
			return;
		}

		String[] a = new String[interfaceList.size()]; interfaceList.toArray(a); Arrays.sort(a);

		String[] b = new String[configList.size()]; configList.toArray(b); Arrays.sort(b);

		for(int i = 0 ; i < a.length ; i ++) {
			if(!a[i].equals(b[i])) {
				info.setResult(ConfigInfo.RS_DIFFERENT);
				configMngMapper.updateConfig(info);
				return;
			}
		}

		info.setResult(ConfigInfo.RS_EQUAL);
		configMngMapper.updateConfig(info);
	}

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map getCompareList(ConfigInfo info) throws Exception {

		Map params = new HashMap();
		params.put("systemId", info.getSystemId());
		params.put("serverId", info.getServerId());
		params.put("type", info.getType());
		params.put("seq", info.getSeq());

		List<String> l1 = configMngMapper.getInterfaceList(params);
		List<String> l2 = configMngMapper.getConfigInterfaceList(params);

		Map rs = new HashMap();
		rs.put("L1", l1);
		rs.put("L2", l2);

		return rs;
	}
}
