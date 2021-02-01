/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.en;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.monitor.FrontLog;
import pep.per.mint.database.mapper.en.FrontLogMapper;

/**
 * 프론트 로그 관련 서비스
 * @author Solution TF
 *
 */

@Service
public class FrontLogService {
	
	@Autowired
	FrontLogMapper frontLogMapper;
	
	public List<FrontLog> getFrontLogList(Map params) throws Exception{ 
		return frontLogMapper.getFrontLogList(params);
	}
	
	public int insertFrontLog(FrontLog frontLog) throws Exception {
		frontLog.setLogKey(UUID.randomUUID().toString());
		return frontLogMapper.insertFrontLog(frontLog);
	}
}
