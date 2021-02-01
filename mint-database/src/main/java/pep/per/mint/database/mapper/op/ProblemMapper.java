/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.op;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.monitor.ProblemAttachFile;
import pep.per.mint.common.data.basic.monitor.ProblemManagement;

 

/**
 * @author noggenfogger
 *
 */
public interface ProblemMapper {

	public List<ProblemManagement> getProblemList(Map arg) throws Exception;
	
	public ProblemManagement getProblemInfo(Map arg) throws Exception;
	
	public Interface getProblemInterfaceInfo(Map arg) throws Exception;
	
	public int insertProblem(ProblemManagement param) throws Exception;
	
	public int updateProblem(ProblemManagement param) throws Exception;
	
	public int deleteProblem(ProblemManagement param) throws Exception;
	
	public int insertProblemAttachFile(ProblemAttachFile param) throws Exception;
	
	public int deleteProblemAttachFile(ProblemAttachFile param) throws Exception;
	
	public List<ProblemManagement> getToDoProblemList(Map arg) throws Exception;
	
	public int insertProblemInterface(Map param) throws Exception;
	
	public int insertProblemInterfaceSystem(Map param) throws Exception;
	
	public int insertProblemBusiness(Map param) throws Exception;
	
	public int insertProblemAdmin(Map param) throws Exception;
	
	public int deleteProblemAdmin(Map param) throws Exception;

	public int deleteProblemBusiness(Map param) throws Exception;
	
	public int deleteProblemSystem(Map param) throws Exception;
	
	public int deleteProblemInterface(Map param) throws Exception;
	
	public int updateProblemStatus(@Param("status") String status, @Param("modDate") String modDate, @Param("modId")String modId, @Param("problemId") String problemId) throws Exception;
}

