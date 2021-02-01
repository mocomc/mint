/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.co;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.main.DevelopmentProceed;
import pep.per.mint.common.data.basic.main.Statistics;
import pep.per.mint.database.mapper.co.MainMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 공통조회용 서비스 
 * @author Solution TF
 *
 */
@Service
public class MainService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MainMapper mainMapper;

	
	/**
	 * <pre>
	 * 오류 지연현황 조회 
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<TRLog> getErrorDelayList(Map<String, Object> param)throws Exception{
		List<TRLog> list = mainMapper.getErrorDelayList(param);
		return list;
	}
	
	//------------------------------------------------------------
	// main - 금일 / 전일 처리 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 오류 지연현황 조회 
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getProceedList(Map<String, Object> param) throws Exception {
		List<Statistics> result = mainMapper.getProceedList(param);
		
		return result;
	}
	
	//------------------------------------------------------------
	// main - 업무 시스템 별 처리 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 업무 시스템 별 처리 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getSystemList(Map<String, Object> param) throws Exception {
		List<Statistics> result = mainMapper.getSystemList(param);
		
		return result;
	}
	
	//------------------------------------------------------------
	// main - 인터페이스 별 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 인터페이스 별 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getInterfaceList(Map<String, Object> param) throws Exception {
		List<Statistics> result = mainMapper.getInterfaceList(param);
		
		return result;
	}
	
	//------------------------------------------------------------
	// main - 프로세스 별 현황
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 프로세스 별 현황
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Statistics> getProcessList(Map<String, Object> param) throws Exception {
		List<Statistics> result = mainMapper.getProcessList(param);
		
		return result;
	}
	
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
	public List<Statistics> getErrorList(Map<String, Object> param) throws Exception {
		List<Statistics> result = mainMapper.getErrorList(param);
		
		return result;
	}
	
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
	public DevelopmentProceed getDevProceedList(Map<String, Object> param) throws Exception {
		DevelopmentProceed result = mainMapper.getDevProceedList(param);
		
		return result;
	}
}
