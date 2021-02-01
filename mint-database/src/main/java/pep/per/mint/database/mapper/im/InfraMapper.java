/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.im;


import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.BusinessPath;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.OrganizationPath;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.SystemPath;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.UserRole;

import java.util.List;
import java.util.Map;


/**
 * @author noggenfogger
 *
 */
public interface InfraMapper {

	/**
	 * REST-R01-IM-01-01  시스템 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemList(Map params) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<System> existSystem(Map params) throws Exception;

	/**
	 * REST-R05-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<System> getRootSystemList(Map params) throws Exception;

	/**
	 * REST-R06-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<System> getChildSystemList(Map params) throws Exception;

	/**
	 * REST-R02-IM-01-01 시스템상세조회
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public System getSystemDetail(String systemId) throws Exception;

	/**
	 * REST-R03-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getSystemTreeWithModel() throws Exception;
	
	public List<Map> getSystemTreeWithModelParam(Map params) throws Exception;

	/**
	 * REST-R04-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getSystemTree() throws Exception;


	public int insertSystem(System system) throws Exception;

	public int updateSystem(System system) throws  Exception;

	public List<String> usedSystem(@Param("systemId")String systemId) throws Exception;


	public int deleteSystem(@Param("systemId")String systemId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public int deleteSystemRelativeUsers(@Param("systemId")String systemId) throws Exception;
	
	public int deleteSystemRelativeUsersforUpdate(@Param("systemId")String systemId) throws Exception;

	public int insertSystemRelativeUser(@Param("systemId")String systemId, @Param("relUser")RelUser relUser) throws Exception;

	public int deleteSystemServerMaps(@Param("systemId")String systemId) throws Exception;
	
	public int deleteSystemServerMapsforUpdate(@Param("systemId")String systemId) throws Exception;

	public int insertSystemServerMap(@Param("systemId")String systemId, @Param("server")Server server) throws Exception;

	public int deleteSystemPath(@Param("systemId")String systemId) throws Exception;
	
	public int deleteSystemAsOranization(@Param("systemId")String systemId) throws Exception;

	/**
	 * REST-R03-IM-01-03 업무트리조회(전체 조회)
	 * @return
	 * @throws Exception
	 */
	public List<Map> getBusinessTree() throws Exception;

	/**
	 * REST-R04-IM-01-03 최상위 업무코드 트리 검색
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRootBusinessTree()throws Exception;

	/**
	 * REST-R05-IM-01-03 자식 업무코드트리 검색
	 * @param parentBusinessId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getChildBusinessTree(@Param("parentBusinessId")String parentBusinessId)throws Exception;


	public int insertSystemPath(SystemPath systemPath) throws Exception;


	public int createSystemPathRelation(Map params) throws Exception;

	public String existPath(@Param("systemId")String systemId) throws Exception;
	
	/**
	 * REST-R01-IM-01-02  서버 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Server> getServerList(Map params) throws Exception;
	
	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Server> existServer(Map params) throws Exception;

	/**
	 * REST-R02-IM-01-02 서버상세조회
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public Server getServerDetail(String serverId) throws Exception;

	public int insertServer(Server server) throws Exception;
	
	public int updateServer(Server server) throws  Exception;

	public int deleteServerAsSystem(@Param("serverId")String serverId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;
	
	public int deleteServer(@Param("serverId")String serverId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	
	/**
	 * REST-R01-IM-01-05  사용자 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(Map params) throws Exception;
	
	/**
	 * REST-R02-IM-01-05 사용자상세조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User getUserDetail(String userId) throws Exception;
	
	public int insertUser(User user) throws Exception;
	
	public int updateUser(User user) throws  Exception;

	public List<String> usedUser(@Param("userId")String userId) throws Exception;

	public int deleteUser(@Param("userId")String userId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	
	/**
	 * REST-R04-IM-01-05  사용자 Role 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> getUserRoleList(Map params) throws Exception;
	
	
	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Organization> existOrganization(Map params) throws Exception;
	/**
	 * REST-R05-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getRootOrganizationList(Map params) throws Exception;

	public List<Map> getOrganizationTreeWithModelParam(Map params) throws Exception;
	
	public List<Map> getOrganizationSystemMap(Map params) throws Exception;
	
	public List<Map> getOrganizationOfSystemMap( @Param("system")System system) throws Exception;
	/**
	 * REST-R02-IM-01-06 기관 상세조회
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public Organization getOrganizationDetail(String organizationId) throws Exception;

	

	public int insertOrganization(Organization system) throws Exception;

	public int updateOrganization(Organization system) throws  Exception;

	public List<String> usedOrganization(@Param("organizationId")String organizationId) throws Exception;

	public int deleteOrganization(@Param("organizationId")String organizationId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public int deleteOrganizationRelativeUsers(@Param("organizationId")String organizationId) throws Exception;
	
	public int deleteOrganizationRelativeUsersforUpdate(@Param("organizationId")String organizationId) throws Exception;

	public int insertOrganizationRelativeUser(@Param("organizationId")String organizationId, @Param("relUser")RelUser relUser) throws Exception;

	public int deleteOrganizationSystemMaps(@Param("organizationId")String organizationId) throws Exception;
	
	public int deleteOrganizationSystemMapsforUpdate(@Param("organizationId")String organizationId) throws Exception;

	public int insertOrganizationSystemMap(@Param("organizationId")String organizationId, @Param("system")System system) throws Exception;

	public int deleteOrganizationPath(@Param("organizationId")String organizatioId) throws Exception;
	
	public int insertOrganizationPath(OrganizationPath orgPath) throws Exception;

	public int createOrganizationPathRelation(Map params) throws Exception;

	public String existOrganizationPath(@Param("organizationId")String organizatioId) throws Exception;
	
	
	
	/**
	 * REST-R05-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<Business> getRootBusinessList(Map params) throws Exception;

	public List<Map> getBusinessTreeWithModelParam(Map params) throws Exception;
	

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Business> existBusiness(Map params) throws Exception;

	/**
	 * REST-R02-IM-01-06 기관 상세조회
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public Business getBusinessDetail(String businessId) throws Exception;

	

	public int insertBusiness(Business system) throws Exception;

	public int updateBusiness(Business system) throws  Exception;

	public List<String> usedBusiness(@Param("businessId")String businessId) throws Exception;

	public int deleteBusiness(@Param("businessId")String businessId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public int deleteBusinessRelativeUsers(@Param("businessId")String businessId) throws Exception;
	
	public int deleteBusinessRelativeUsersforUpdate(@Param("businessId")String businessId) throws Exception;

	public int insertBusinessRelativeUser(@Param("businessId")String businessId, @Param("relUser")RelUser relUser) throws Exception;


	public int deleteBusinessPath(@Param("businessId")String businessId) throws Exception;
	
	public int insertBusinessPath(BusinessPath orgPath) throws Exception;

	public int createBusinessPathRelation(Map params) throws Exception;

	public String existBusinessPath(@Param("businessId")String businessId) throws Exception;
	
	
}
