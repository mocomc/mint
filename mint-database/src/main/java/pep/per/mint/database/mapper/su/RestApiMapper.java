/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.su;


import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.Service;

import java.util.List;
import java.util.Map;


/**
 * @author noggenfogger
 *
 */
public interface RestApiMapper {
	
	/**
	 * REST-R01-SU-10-02  서비스  리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Service> getRestServiceList(Map params) throws Exception;
	
	/**
	 * REST-R02-SU-10-02  서버상세조회
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public Service getRestServiceDetail(String serviceId) throws Exception;

	public int insertRestService(Service service) throws Exception;
	
	public int updateRestService(Service service) throws  Exception;

	public int deleteRestService(@Param("serviceId")String serviceId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	

}
