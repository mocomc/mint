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


import pep.per.mint.common.data.basic.DashboardManagement;




public interface DashboardManagementMapper {

	public List<DashboardManagement> getDashboardManagementList(Map params) throws Exception;

	public int insertDashboardManagement(DashboardManagement service) throws Exception;

	public int updateDashboardManagement(DashboardManagement service) throws  Exception;

	public int deleteDashboardManagement(DashboardManagement service) throws Exception;

}
