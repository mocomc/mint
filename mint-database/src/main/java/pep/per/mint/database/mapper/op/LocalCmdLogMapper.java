/**
 * Copyright 2018 mocomsys Inc. All Rights Reserved.
 */
package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * pep.per.mint.database.mapper.op
 * LocalCmdLogMapper.java
 * </pre>
 * @author whoana
 * @date Dec 3, 2019
 */
public interface LocalCmdLogMapper {

	/**
	 * @param agentId
	 */
	public void deleteNetstatLogs(@Param("agentId") String agentId) throws Exception;
 
	public void insertNetstatLog(Map<String,String> log) throws Exception;
	
	public void updateNetstatLog(Map<String,String> log) throws Exception;

	/**
	 * @param ip
	 * @return
	 */
	public List<Map<String, String>> getServerIds(@Param("agentId") String agentId, @Param("agentCd") String agentCd) throws Exception;

	/**
	 * @param log
	 * @return
	 */
	public int exist(Map<String, String> log) throws Exception;

	/**
	 * @return
	 */
	public List<String> getNetstatCheckList() throws Exception;

}
