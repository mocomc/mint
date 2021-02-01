/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.im;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.ApplicationInfo;

/**
 * <pre>
 * pep.per.mint.database.mapper.im
 * ServerAppMngMapper.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
public interface ServerAppMngMapper {

	/**
	 * @param applicationInfo
	 */
	public int updateAppInfo(ApplicationInfo applicationInfo) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public List<ApplicationInfo> getAppInfoList(Map params)throws Exception;

	public  int insertAppInfo(ApplicationInfo applicationInfo) throws Exception;

}
