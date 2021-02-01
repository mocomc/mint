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


/**
 * @author isjang
 *
 */
public interface GSSPDashboardMapper {


	/**
	 * 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getDelayListTop(Map params) throws Exception;
    
    /**
     * 통신불가능포스 count
     * @return 
     */
    public Integer getDeadPosCount() throws Exception;
    
    /**
     * 통신불가능 포스 리스트
     * @return 
     */
    public List<Map> getDeadPosList() throws Exception;
    
    /**
     * 거래미발생포스 카운트
     * @return
     * @throws Exception 
     */
    public Integer getNoTransactionPosCount() throws Exception;
    
    /**
     * 거래미발생포스 리스트 
     * @return
     * @throws Exception 
     */
    public List<Map> getNoTransactionPosList() throws Exception;
    
    /**
     * 모든점포포스거래 건수 조회(최근 10분 동안)  
     * @return
     * @throws Exception 
     */
    public Integer getTotalPosTransactionCount(Map params) throws Exception;
    
    
    /**
	 * 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeFavoriteCount(Map params);

}
