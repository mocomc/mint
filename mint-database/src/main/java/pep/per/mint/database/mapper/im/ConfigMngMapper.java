/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.im;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.ConfigInfo;


/**
 * <pre>
 * pep.per.mint.database.mapper.im
 * ConfigMngMapper.java
 * </pre>
 * @author whoana
 * @date Oct 31, 2018
 */
public interface ConfigMngMapper {

	public int createConfig(ConfigInfo info) throws Exception;

	public int updateConfig(ConfigInfo info) throws Exception;

	public int deleteConfig(ConfigInfo info) throws Exception;

	public List<ConfigInfo> getConfigList(Map params)throws Exception;

	public List<String> getInterfaceList(Map params) throws Exception;

	public List<String> getConfigInterfaceList(Map params) throws Exception;

	public int insertConfigInterface(Map params)throws Exception;

	public int deleteConfigInterfaceList(Map params)throws Exception;

}
