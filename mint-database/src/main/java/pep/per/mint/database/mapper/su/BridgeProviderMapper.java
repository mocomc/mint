/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
 

/**
 * @author whoana
 * @since 2020. 11. 24.
 */
public interface BridgeProviderMapper {

	/**
	 * @param businessId
	 * @return
	 */
	List<Map<String, String>> getInterfaces(Map params) throws Exception;

	/**
	 * @param integrationId
	 * @return
	 */
	List<String> getInterfaceId(@Param("integrationId") String serviceCd) throws Exception;

}
