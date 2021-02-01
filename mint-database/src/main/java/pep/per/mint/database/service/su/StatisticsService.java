package pep.per.mint.database.service.su;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.statistics.StatisticsDevelopmentStatus;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureRate;
import pep.per.mint.common.data.basic.statistics.StatisticsFailureType;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfacePeriod;
import pep.per.mint.common.data.basic.statistics.StatisticsInterfaceType;
import pep.per.mint.common.data.basic.statistics.SubjectStatus;
import pep.per.mint.database.mapper.su.StatisticsMapper;

/**
 * 통계 서비스
 * @author isjang
 *
 */

@Service
public class StatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);

	@Autowired
	StatisticsMapper statisticsMapper;

	public List<Requirement> getSubjectStatusDetail(Map params) throws Exception {
		return statisticsMapper.getSubjectStatusDetail(params);
	}

	public List<SubjectStatus> getSubjectStatusGroupByUser(Map params) throws  Exception{
		return statisticsMapper.getSubjectStatusGroupByUser(params);
	}

	public List<SubjectStatus> getSubjectStatusGroupByResource(Map params) throws  Exception{
		return statisticsMapper.getSubjectStatusGroupByResource(params);
	}


	/**
	 * <pre>
	 * 과제종합 현황을 채널별로 그룹핑하여 조회한다.
	 * REST-R01-AN-08-01 개발 진척 현황 조회
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<SubjectStatus> getSubjectStatusGroupByChannel(Map params) throws Exception {
		return statisticsMapper.getSubjectStatusGroupByChannel(params);
	}

	/**
	 * <pre>
	 * 과제종합 현황을 시스템별로 그룹핑하여 조회한다.
	 * REST-R01-AN-08-02 개발 진척 현황 조회
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<SubjectStatus> getSubjectStatusGroupBySystem(Map params) throws Exception {
		return statisticsMapper.getSubjectStatusGroupBySystem(params);
	}

	/**
	 * <pre>
	 * 개발 진척 현황을 조회한다.
	 * REST-R01-AN-03-01 개발 진척 현황 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsDevelopmentStatus> getDevelopmentStatus(Map params) throws Exception  {

		List<StatisticsDevelopmentStatus> statisticsDevelopmentStatusList = statisticsMapper.getDevelopmentStatus(params);

		return statisticsDevelopmentStatusList;
	}

	/**
	 * <pre>
	 * 개발 진천 현황 - 상태별 요건 리스트를 조회한다.
	 * REST-R02-AN-03-01 개발 진척 현황 - 상태별 요건 리스트 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getDevelopmentStatusRequirementList(Map params) throws Exception  {
		return statisticsMapper.getDevelopmentStatusRequirementList(params);
	}


	/**
	 * <pre>
	 * 인터페이스 기간별 통계를 조회한다.
	 * REST-R01-SU-03-02 인터페이스 기간별 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStatisticsInterfacePeriodSummary(Map params) throws Exception  {

		List<Map> statisticsInterfacePeriodList = statisticsMapper.getStatisticsInterfacePeriodSummary(params);

		return statisticsInterfacePeriodList;
	}

	public List<Map> getStatisticsInterfacePeriodList(Map params) throws Exception  {

		List<Map> statisticsInterfacePeriodList = statisticsMapper.getStatisticsInterfacePeriodList(params);

		return statisticsInterfacePeriodList;
	}

	/**
	 * <pre>
	 * 인터페이스 기간별 통계를 조회한다.
	 * REST-R01-SU-03-02 인터페이스 기간별 조회
	 * </pre>
	 * @deprecated
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<StatisticsInterfacePeriod> getStatisticsInterfacePeriod(Map params) throws Exception  {

		List<StatisticsInterfacePeriod> statisticsInterfacePeriodList = statisticsMapper.getStatisticsInterfacePeriod(params);

		return statisticsInterfacePeriodList;
	}



	/**
	 * <pre>
	 * 인터페이스 유형별 통계를 조회한다.
	 * REST-R02-SU-03-02 인터페이스 유형별 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsInterfaceType> getStatisticsInterfaceType(Map params) throws Exception  {

		List<StatisticsInterfaceType> statisticsInterfaceTypeList = statisticsMapper.getStatisticsInterfaceType(params);

		return statisticsInterfaceTypeList;
	}

	/**
	 * <pre>
	 * 인터페이스 유형별 통계 - 상세를 조회한다.
	 * REST-R04-SU-03-02 인터페이스 유형별 조회 - 상세
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsInterfaceType> getStatisticsInterfaceTypeDetail(Map params) throws Exception  {

		List<StatisticsInterfaceType> statisticsInterfaceTypeDetailList = statisticsMapper.getStatisticsInterfaceTypeDetail(params);

		return statisticsInterfaceTypeDetailList;
	}
	/**
	 * <pre>
	 * 인터페이스 기간별(비교) 통계를 조회한다.
	 * REST-R03-SU-03-02 인터페이스 기간별(비교) 기본 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStatisticsInterfacePeriodForCompareDefault(Map params) throws Exception {
		return statisticsMapper.getStatisticsInterfacePeriodForCompareDefault(params);
	}


	/**
	 * <pre>
	 * 인터페이스 기간별(비교) 통계를 조회한다.
	 * REST-R03-SU-03-02 인터페이스 기간별(비교) 상세 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStatisticsInterfacePeriodForCompareDetail(Map params) throws Exception {
		return statisticsMapper.getStatisticsInterfacePeriodForCompareDetail(params);
	}



	/**
	 * <pre>
	 * 장애 발생율 통계를 조회한다.
	 * REST-R01-SU-03-04 장애발생율 통계 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsFailureRate> getStatisticsFailureRate(Map params) throws Exception  {

		List<StatisticsFailureRate> statisticsFailureRateList = statisticsMapper.getStatisticsFailureRate(params);

		return statisticsFailureRateList;
	}


	/**
	 * <pre>
	 * 기준 조건별 장애 발생 인터페이스 리스트를 조회한다.
	 * REST-R03-SU-03-04 기준 조건별 장애발생 인터페이스 리스트 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStatisticsFailureInterfaceList(Map params) throws Exception {

		return statisticsMapper.getStatisticsFailureInterfaceList(params);
	}



	/**
	 * <pre>
	 * 장애 유형별 통계를 조회한다.
	 * REST-R02-SU-03-04 장애발생율 통계 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsFailureType> getStatisticsFailureType(Map params) throws Exception  {

		List<StatisticsFailureType> statisticsFailureTypeList = statisticsMapper.getStatisticsFailureType(params);







		return statisticsFailureTypeList;
	}

	/**
	 * <pre>
	 *     일별 솔루션별 개발진척률을 조회한다, REST-R01-SU-05-05-000
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDevelopmentDailyStatusByChannel(Map params) throws Exception {
		List<Map> list = statisticsMapper.getDevelopmentDailyStatusByChannel(params);
		return list;
	}

	/**
	 * <pre>
	 *     일별 리소스별 개발진척률을 조회한다, REST-R02-SU-05-05-000
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDevelopmentDailyStatusByResource(Map params) throws Exception {
		List<Map> list = statisticsMapper.getDevelopmentDailyStatusByResource(params);
		return list;
	}

	/**
	 * <pre>
	 *     일별 시스템별 개발진척률을 조회한다, REST-R03-SU-05-05-000
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDevelopmentDailyStatusByProvider(Map params) throws Exception {
		List<Map> list = statisticsMapper.getDevelopmentDailyStatusByProvider(params);
		return list;
	}


	/**
	 * <pre>
	 *     일별개발진척률리스트조회, REST-R04-SU-05-05-000
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getDevelopmentDailyStatusList(Map params) throws Exception{
		List<Requirement> list = statisticsMapper.getDevelopmentDailyStatusList(params);
		return list;
	}

	/**
	 * <pre>
	 * 	월별 인터페이스 변화량을 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getMonthlyChangeInterfaceCount(Map params) throws Exception {
		List<Map> list = statisticsMapper.getMonthlyChangeInterfaceCount(params);
		return list;
	}

	public List<Map> getStatisticsInterfacePeriodTotal(Map params) throws Exception{
		return statisticsMapper.getStatisticsInterfacePeriodTotal(params);
	}

	public List<Map> getStatisticsInterfaceOrgTotal(Map params) throws Exception{
		//return statisticsMapper.getStatisticsInterfaceOrgTotal(params);
		List<Map>  resultList = new ArrayList<Map>();

		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("INTERFACE_ID", "F@00000007");
		res.put("201703", 15120);
		res.put("201704", 4444444);

		resultList.add(res);
		return resultList;
	}

	public List<Map> getStatisticsInterfaceOrgYearTotal(Map params) throws Exception{
		return statisticsMapper.getStatisticsInterfaceOrgYearTotal(params);
	}

	public List<Map> getStatisticsInterfaceOrgMonthTotal(Map params) throws Exception{
		return statisticsMapper.getStatisticsInterfaceOrgMonthTotal(params);
	}

	public List<Map> getStatisticsInterfaceOrganizationTotal(Map params) throws Exception{
		return statisticsMapper.getStatisticsInterfaceOrganizationTotal(params);
	}

	public List<Map> getSubjectStatusGroupByChannelDev(Map params) throws Exception{
		return statisticsMapper.getSubjectStatusGroupByChannelDev(params);
	}

}
