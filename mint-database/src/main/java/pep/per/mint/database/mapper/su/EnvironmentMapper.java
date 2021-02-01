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


import pep.per.mint.common.data.basic.Environment;




public interface EnvironmentMapper {

	public List<Environment> getEnvironmentList(Map params) throws Exception;

	public int insertEnvironment(Environment service) throws Exception;

	public int updateEnvironment(Environment service) throws  Exception;

	public int deleteEnvironment(Environment service) throws Exception;

	public List<Environment> existEnvironment(Map params) throws Exception;

}
