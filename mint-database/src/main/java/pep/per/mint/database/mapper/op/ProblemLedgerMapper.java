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

import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.monitor.ProblemAttachFile;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;
import pep.per.mint.common.data.basic.monitor.ProblemTemplate;

/**
 * @author INSEONG
 *
 */
public interface ProblemLedgerMapper {
	
	
	public List<ProblemLedger> getProblemManagementList(Map param) throws Exception;
	public List<Map> getProblemManagementListCount(Map param) throws Exception;
	

	public List<ProblemLedger> getProblemList(Map param) throws Exception;

	public List<Map> getProblemListCount(Map param) throws Exception;
	
	public ProblemLedger getProblemInfo(Map param) throws Exception;
	
	public List<ProblemLedger> getProblemRegisterManagementList(Map param) throws Exception;
	
	public List<Map> getProblemRegisterManagementListCount(Map param) throws Exception;	
	
	public List<ProblemLedger> getToDoProblemList(Map param) throws Exception;
	
	public List<ProblemLedger> getProblemListForTrackingDetail(Map param) throws Exception;
	
	public Interface getProblemInterfaceInfo(Map param) throws Exception;
	
	public int insertProblem(ProblemLedger param) throws Exception;
	
	public int deleteProblem(ProblemLedger param) throws Exception;
	
	public int updateProblem(ProblemLedger param) throws Exception;
	
	public int insertProblemInterface(Map param) throws Exception;
	
	public int deleteProblemInterface(Map param) throws Exception;
	
	public int insertProblemAttachFile(ProblemAttachFile param) throws Exception;
	
	public int deleteProblemAttachFile(ProblemAttachFile param) throws Exception;
	
	public int updateProblemStatus(@Param("status") String status, @Param("modDate") String modDate, @Param("modId")String modId, @Param("problemId") String problemId) throws Exception;
	
	public List<Map> getProblemTemplateList(Map param) throws Exception;	
	
	public List<Map> getProblemTemplateDetail(Map param) throws Exception;
	
    public int insertProblemTemplate(ProblemTemplate param) throws Exception;

    public int updateProblemTemplate(ProblemTemplate param)throws Exception;
    
    public int deleteProblemTemplate(ProblemTemplate param)throws Exception;
    
    
	
	
}
