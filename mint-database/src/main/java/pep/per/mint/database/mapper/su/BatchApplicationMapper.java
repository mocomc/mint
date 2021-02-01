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
import pep.per.mint.common.data.basic.BatchMapping;




public interface BatchApplicationMapper {

	public List<BatchMapping> getApplicationList(Map params) throws Exception;

	public int insertApplicationJob(BatchMapping service) throws Exception;

	public int insertApplicationSchedule(BatchMapping service) throws Exception;

	public int insertApplicationMapping(BatchMapping service) throws Exception;

	public int updateApplicationJob(BatchMapping service) throws  Exception;

	public int updateApplicationSchedule(BatchMapping service) throws  Exception;

	public int updateApplicationMapping(BatchMapping service) throws  Exception;

	public int deleteApplication(Map params) throws Exception;

	public List<String> usedApplication(Map params) throws Exception;

	public int insertApplicationMappingUserRole(BatchMapping service)throws Exception;

	public int deleteApplicationMappingUserRole(Map params)throws Exception;

	public List<BatchMapping> existApplication(Map params)throws Exception;

}
