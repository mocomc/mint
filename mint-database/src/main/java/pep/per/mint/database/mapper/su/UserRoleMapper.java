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

import pep.per.mint.common.data.basic.Application;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.UserRole;



public interface UserRoleMapper {

	public List<UserRole> getUserRoleList(Map params) throws Exception;

	public int insertUserRole(UserRole accessRole) throws Exception;

	public int updateUserRole(UserRole accessRole) throws  Exception;

	public int deleteUserRole(Map params) throws Exception;

	public List<UserRole> existUserRole(Map params) throws Exception;

	public List<String> usedUserRole(@Param("roleId")String roleId) throws Exception;

	public UserRole getUserRoleDetail(Map params) throws Exception;

	public void updateUserRoleMenu(@Param("roleId")String roleId,@Param("menu")Menu menu) throws Exception;

	public void updateUserRoleApp(@Param("roleId")String roleId,@Param("app")Application app) throws Exception;

	public int insertUserRoleMenu(@Param("roleId")String roleId, @Param("defRoleId")String defRoleId) throws Exception;

	public int insertUserRoleApp(@Param("roleId")String roleId, @Param("defRoleId")String defRoleId) throws Exception;

	public int deleteAllUserRoleMenu(Map params) throws Exception;

	public int deleteAllUserRoleApp(Map params) throws Exception;


}
