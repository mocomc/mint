/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.su;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.DataAccessRolePath;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.User;



public interface DataAccessRoleMapper {

	public List<DataAccessRole> getDataAccessRoleList(Map params) throws Exception;

	public int insertDataAccessRole(DataAccessRole accessRole) throws Exception;

	public int updateDataAccessRole(DataAccessRole accessRole) throws  Exception;

	public int deleteDataAccessRole(Map params) throws Exception;

	public List<DataAccessRole> existDataAccessRole(Map params) throws Exception;

	public List<String> usedDataAccessRole(@Param("roleId")String roleId) throws Exception;

	public List<User> getDataAccessRoleUsersList(Map params) throws Exception;

	public int insertDataAccessRoleUsers(Map params) throws Exception;

	public int deleteDataAccessRoleUsers(Map params) throws Exception;

	public int deleteAllDataAccessRoleUsers(Map params) throws Exception;

	public String existDataAccessRolePath(@Param("roleId")String roleId)throws Exception;

	public int deleteDataAccessRolePath(String roleId)throws Exception;

	public DataAccessRole getDataAccessRoleDetail(String roleId)throws Exception;

	public int insertDataAccessRolePath(DataAccessRolePath pPath)throws Exception;

	public int createDataAccessRolePathRelation(Map params)throws Exception;

	public int deleteDataAccessRoleRelativeUsersforUpdate(String roleId)throws Exception;

	public int insertDataAccessRoleRelativeUser(@Param("roleId")String  roleId, @Param("relUser")RelUser relUser)throws Exception;

	public List<Map> getDataAccessRoleTreeWithModelParam(Map params)throws Exception;

	public int insertDataAccessRoleSystemMap(@Param("roleId")String roleId, @Param("system")System system)throws Exception;

	public int deleteDataAccessRoleSystemMaps(Map params)throws Exception;

	public int deleteDataAccessRoleSystemMapsforUpdate(@Param("roleId")String roleId)throws Exception;

	public int insertDataAccessRoleInterface(Map params) throws Exception;

	public int deleteDataAccessRoleInterface(@Param("interfaceId")String  interfaceId) throws Exception;

	public List<Map<String,String>> getDataAccessRoleInterface(@Param("interfaceId")String  interfaceId) throws Exception;

}
