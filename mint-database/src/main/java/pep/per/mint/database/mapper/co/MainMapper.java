/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.co;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.main.DevelopmentProceed;
import pep.per.mint.common.data.basic.main.Statistics;

 

/**
 * @author Solution TF
 *
 */
public interface MainMapper {
	
	
	
	//------------------------------------------------------------
	// main - 오류/지연 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 오류 지연현황 조회 
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<TRLog> getErrorDelayList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 금일 / 전일 처리 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 금일 / 전일 처리 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getProceedList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 업무시스템별 처리현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 업무시스템별 처리현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getSystemList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 인터페이스 별 처리 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 인터페이스 별 처리 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getInterfaceList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 프로세스 별 처리 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 프로세스 별 처리 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getProcessList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 장애발생 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 장애 발생 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getErrorList(Map<String, Object> param) throws Exception;
	
	//------------------------------------------------------------
	// main - 개발 진행 현황 - 개발/테스트/이행/완료 조회
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 개발 진행 현황 - 개발/테스트/이행/완료 조회
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public DevelopmentProceed getDevProceedList(Map<String, Object> param) throws Exception;
}
