package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.database.mapper.su.StatisticsMapperKICS;

/**
 * 통계 서비스
 * @author isjang
 *
 */

@Service
public class StatisticsServiceKICS {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceKICS.class);

	@Autowired
	StatisticsMapperKICS statisticsMapperkics;

	public List<Map> getStatisticsInterfacePeriodTotalsYears(Map params) throws Exception{
		return statisticsMapperkics.getStatisticsInterfacePeriodTotalsYears(params);
	}

	public List<Map> getStatisticsInterfacePeriodTotalsHour(Map params)  throws Exception{
		return statisticsMapperkics.getStatisticsInterfacePeriodTotalsHour(params);
	}

	public List<Map> getStatisticsInterfacePeriodTotalsDay(Map params)  throws Exception{
		return statisticsMapperkics.getStatisticsInterfacePeriodTotalsDay(params);
	}

	public List<Map> getStatisticsInterfacePeriodTotalsMonth(Map params)  throws Exception{
		return statisticsMapperkics.getStatisticsInterfacePeriodTotalsMonth(params);
	}

}
