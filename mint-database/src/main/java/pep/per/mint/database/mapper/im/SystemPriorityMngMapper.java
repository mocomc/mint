/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.im;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.System;
/**
 * <pre>
 * pep.per.mint.database.mapper.im
 * ServerAppMngMapper.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
public interface SystemPriorityMngMapper {

	public List<System>  getSystemPriorityList()throws Exception;

	public void deleteSystemPriority()throws Exception;

	public void insertSystemPriority(Map params)throws Exception;
}
