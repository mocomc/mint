/**
 *
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.statistics.StatisticsDevelopmentStatus;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureRate;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureType;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfacePeriod;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfaceType;
import pep.per.mint.common.data.basic.statistics.SubjectStatus;

/**
 * @author INSEONG
 *
 */
public interface StatisticsMapper {

	public List<Requirement> getSubjectStatusDetail(Map params) throws Exception;

	public List<SubjectStatus> getSubjectStatusGroupByUser(Map params) throws  Exception;

	public List<SubjectStatus> getSubjectStatusGroupByResource(Map params) throws  Exception;

	public List<SubjectStatus> getSubjectStatusGroupBySystem(Map params) throws Exception;


	public List<SubjectStatus> getSubjectStatusGroupByChannel(Map params) throws Exception;

	public List<StatisticsDevelopmentStatus> getDevelopmentStatus(Map arg) throws Exception;

	public List<Requirement> getDevelopmentStatusRequirementList(Map arg) throws Exception;

	public List<StatisticsInterfacePeriod> getStatisticsInterfacePeriod(Map arg) throws Exception;

	public List<Map> getStatisticsInterfacePeriodSummary(Map arg) throws Exception;

	public List<Map> getStatisticsInterfacePeriodList(Map arg) throws Exception;

	public List<StatisticsInterfaceType> getStatisticsInterfaceType(Map arg) throws Exception;

	public List<StatisticsInterfaceType> getStatisticsInterfaceTypeDetail(Map arg) throws Exception;

	public List<Map> getStatisticsInterfacePeriodForCompareDefault(Map arg) throws Exception;

	public List<Map> getStatisticsInterfacePeriodForCompareDetail(Map arg) throws Exception;

	public List<StatisticsFailureRate> getStatisticsFailureRate(Map arg) throws Exception;

	public List<Map> getStatisticsFailureInterfaceList(Map arg) throws Exception;

	public List<StatisticsFailureType> getStatisticsFailureType(Map arg) throws Exception;

	public List<Map> getDevelopmentDailyStatusByChannel(Map params) throws Exception;

	public List<Map> getDevelopmentDailyStatusByProvider(Map params) throws Exception;

	public List<Map> getDevelopmentDailyStatusByResource(Map params) throws Exception;

	public List<Requirement> getDevelopmentDailyStatusList(Map params) throws Exception;

	public List<Map> getMonthlyChangeInterfaceCount(Map params) throws Exception;

	public List<Map> getStatisticsInterfacePeriodTotal(Map params) throws Exception;

	public List<Map> getStatisticsInterfaceOrgTotal(Map params) throws Exception;

	public List<Map> getStatisticsInterfaceOrgYearTotal(Map params) throws Exception;

	public List<Map> getStatisticsInterfaceOrgMonthTotal(Map params) throws Exception;

	public List<Map> getStatisticsInterfaceOrganizationTotal(Map params) throws Exception;

	public List<Map> getSubjectStatusGroupByChannelDev(Map params) throws Exception;

}
