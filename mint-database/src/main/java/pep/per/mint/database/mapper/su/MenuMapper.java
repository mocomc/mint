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
import pep.per.mint.common.data.basic.MenuPath;
import pep.per.mint.common.data.basic.SystemPath;





public interface MenuMapper {

	public List<Menu> getMenuList(Map params) throws Exception;

	public int insertMenu(Menu service) throws Exception;

	public int updateMenu(Menu service) throws  Exception;

	public int deleteMenu(Map params) throws Exception;

	public List<Menu> existMenu(Map params) throws Exception;

	public List<String> usedMenu(@Param("menuId")String menuId) throws Exception;

	public List<Map> getMenuTreeWithModelParam(Map params) throws Exception;

	public List<Menu> getNotUseMenuList(Map params) throws Exception;

	public String existPath(String parentMenuId) throws Exception;

	public int insertMenuPath(MenuPath pPath) throws Exception;

	public int createMenuPathRelation(Map params) throws Exception;

	public int deleteMenuChildPath(String menuId)throws Exception;

	public Menu getMenuDetail(String menuId)throws Exception;

	public int deleteMenuRelativeApps(@Param("menuId")String menuId)throws Exception;

	public int insertMenuRelativeApps(@Param("menuId")String menuId, @Param("apps")Application apps)throws Exception;

	public int updateMenuRootYn(String menuId) throws Exception;

	public int insertMenuMappingUserRole(Menu menu) throws Exception;

	public int deleteMenuMappingUserRole(Map params) throws Exception;


}
