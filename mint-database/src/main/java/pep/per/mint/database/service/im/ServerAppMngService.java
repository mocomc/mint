/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.database.mapper.im.ServerAppMngMapper;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * ServerAppMngService.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
@Service
public class ServerAppMngService {

	@Autowired
	ServerAppMngMapper serverAppMngMapper;

	@Transactional
	public void upsertAppInfo(ApplicationInfo applicationInfo) throws Exception{
		int res = serverAppMngMapper.updateAppInfo(applicationInfo);
		if(res < 1) {
			serverAppMngMapper.insertAppInfo(applicationInfo);
		}
	}

	public List<ApplicationInfo> getAppInfoList(Map params) throws Exception{
		return serverAppMngMapper.getAppInfoList(params);
	}

	/**
	 * @param infos
	 */
	@Transactional
	public void upsertAppInfos(List<ApplicationInfo> infos) throws Exception{
		if(infos !=null && infos.size() > 0) {
			for(ApplicationInfo info : infos) {
				upsertAppInfo(info);
			}
		}
	}

}
