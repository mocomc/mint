/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.database.mapper.su.TrackingStatisticsMapper;

/**
 * <pre>
 * pep.per.mint.database.service.su
 * TrackingStatisticsService.java
 * </pre>
 * @author whoana
 * @date Jun 14, 2019
 */
@Service
public class TrackingStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(TrackingStatisticsService.class);

	@Autowired
	private TrackingStatisticsMapper trackingStatisticsMapper;


	public List<Map> getRecordCount(Map params) throws Exception{
		return trackingStatisticsMapper.getRecordCount(params);

	}

	public List<Map> getStatisticsTimePeriod(Map params) throws Exception{
		return trackingStatisticsMapper.getStatisticsTimePeriod(params);
	}

	public List<Map> getStatisticsTimePeriodDetail(Map params) throws Exception{
		return trackingStatisticsMapper.getStatisticsTimePeriodDetail(params);
	}




}
