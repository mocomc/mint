/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.im;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.data.basic.test.InterfaceCallMaster;

/**
 * <pre>
 * pep.per.mint.database.mapper.im
 * TestInterfaceCallMapper.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 1.
 */
public interface TestInterfaceCallMapper {

	public List<InterfaceCallDetail> getTestResultListBySystem(Map params) throws Exception;

	public List<InterfaceCallDetail> getTestResultListByInterface(Map params) throws Exception;

	public List<InterfaceCallDetail> getTestResultListByInterfaceSystem(Map params) throws Exception;

	public List<InterfaceCallDetail> getInterfaceListForTest(Map params) throws Exception;

	public int insertInterfaceCallMaster(InterfaceCallMaster master) throws Exception;

	public int updateInterfaceCallMaster(InterfaceCallMaster master) throws Exception;

	public int insertInterfaceCallDetail(InterfaceCallDetail detail) throws Exception;

	public int updateInterfaceCallDetail(InterfaceCallDetail detail) throws Exception;

	public Map getIngTestCount(Map params) throws Exception;

	/**
	 * @param detail
	 */
	public void updateAllInterfaceCallDetailTimeout(InterfaceCallDetail detail) throws Exception;
}
