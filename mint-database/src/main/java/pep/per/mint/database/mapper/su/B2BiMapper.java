/**
 * 
 */
package pep.per.mint.database.mapper.su;

import java.util.List;
import java.util.Map;


/**
 * @author dhkim
 *
 */
public interface B2BiMapper {
	
	public List<Map<String,Object>> getB2BiCompanyNmByRelation(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiDocNmByRelation(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiBizNmByRelation(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiDirectionByRelation(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiProtocolByRelation(Map<String,Object> params) throws Exception;	
	

	public List<Map<String,Object>> getB2BiInterfaceMetaDataList(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiPeriodSearch(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiStateByDocNm(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getB2BiStateMain(Map<String,Object> params) throws Exception;
	
	public int getMatchingCount(Map<String,Object> params) throws Exception;
	
	public int getMatchingInterfaceId(String interfaceId) throws Exception;	

	public int insertB2BiInterfaceMetaData(Map<String,Object> params) throws Exception;

	public int updateB2BiInterfaceMetaData(Map<String,Object> params) throws  Exception;
	
	public int removeB2BiInterfaceMetaData(String interfaceId) throws  Exception;	
	
}
