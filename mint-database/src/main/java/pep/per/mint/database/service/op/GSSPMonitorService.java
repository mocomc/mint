/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.GSTRLog;
import pep.per.mint.common.data.basic.GSTRLogDetail;
import pep.per.mint.database.mapper.op.GSSPMonitorMapper;

/**
 * 트래킹 로그 관련 서비스
 * @author noggenfogger
 *
 */

@Service
public class GSSPMonitorService {

	@Autowired
	GSSPMonitorMapper gsspMonitorMapper;

	/**
	 * 트레킹 리스트 조회 - 전체 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTrackingLogListTotalCount(Map params) throws Exception {
		int count = gsspMonitorMapper.getTrackingLogListTotalCount(params);
		return count;
	}

	/**
	 * 트례킹 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<GSTRLog> getTrackingLogList(Map params) throws Exception{

		List<GSTRLog> trackingLogList = null;
		trackingLogList = gsspMonitorMapper.getTrackingLogList(params);
		return trackingLogList;
	}

	/**
	 * 트레킹 상세 조회
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<GSTRLogDetail> getTrackingLogDetail(Map arg) throws Exception {

		List<GSTRLogDetail> trackingLogDetailList = gsspMonitorMapper.getTrackingLogDetail(arg);
		return trackingLogDetailList;
	}
}
