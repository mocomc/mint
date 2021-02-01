/**
 * 
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;


/**
 * @author TA
 *
 */
public interface PotalStatisticsMapper {
	
	public List<Map<String,Object>> getLoginStatistics1(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getLoginStatistics2(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getLoginStatisticsDetail(Map<String,Object> params) throws Exception;
	
}
