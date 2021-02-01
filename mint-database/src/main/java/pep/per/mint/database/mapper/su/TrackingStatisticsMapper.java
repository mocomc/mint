/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * pep.per.mint.database.mapper.su
 * TrackingStatisticsMapper.java
 * </pre>
 * @author whoana
 * @date Jun 14, 2019
 */
public interface TrackingStatisticsMapper {

	public List<Map> getRecordCount(Map params) throws Exception;

	public List<Map> getStatisticsTimePeriod(Map params) throws Exception;

	public List<Map> getStatisticsTimePeriodDetail(Map params) throws Exception;
}
