/**
 *
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;

/**
 * @author INSEONG
 *
 */
public interface StatisticsMapperKICS {


	public List<Map> getStatisticsInterfacePeriodTotalsYears(Map params)throws Exception;

	public List<Map> getStatisticsInterfacePeriodTotalsHour(Map params)throws Exception;

	public List<Map> getStatisticsInterfacePeriodTotalsDay(Map params)throws Exception;

	public List<Map> getStatisticsInterfacePeriodTotalsMonth(Map params)throws Exception;


}
