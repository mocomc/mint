package pep.per.mint.database.service.su;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.database.mapper.su.B2BiMapper;


/**
 * @author TA
 *
 */

@Service
public class B2BiService {
	
	private static final Logger logger = LoggerFactory.getLogger(B2BiService.class);
	
	@Autowired
	B2BiMapper b2biMapper;
	
	/**
	 * <pre>
	 * B2Bi 인터페이스 및 관련 메타정보를 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getB2BiInterfaceMetaDataList(Map<String, Object> params) throws Exception {
		return b2biMapper.getB2BiInterfaceMetaDataList(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getB2BiPeriodSearch(Map<String, Object> params) throws Exception {
		return b2biMapper.getB2BiPeriodSearch(params);
	}
	
	
	public List<Map<String,Object>> getB2BiSearchInfo(Map<String,Object> params) throws Exception {
		
		List<Map<String,Object>> companyNmList = b2biMapper.getB2BiCompanyNmByRelation(params);
		
		List<Map<String,Object>> docNmList = b2biMapper.getB2BiDocNmByRelation(params);
		
		List<Map<String,Object>> bizNmList = b2biMapper.getB2BiBizNmByRelation(params);
		
		List<Map<String,Object>> directionList = b2biMapper.getB2BiDirectionByRelation(params);
		
		List<Map<String,Object>> protocolList = b2biMapper.getB2BiProtocolByRelation(params);
		
		Map<String, Object> searchInfoMap = new LinkedHashMap<String, Object>();
		
		searchInfoMap.put("companyNmList", companyNmList);
		searchInfoMap.put("docNmList", docNmList);
		searchInfoMap.put("bizNmList", bizNmList);
		searchInfoMap.put("directionList", directionList);
		searchInfoMap.put("protocolList", protocolList);
		
		
		List<Map<String,Object>> searchInfoList = new LinkedList<Map<String,Object>>();
		searchInfoList.add(searchInfoMap);
		
		return searchInfoList;
		
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getB2BiStateByDocNm(Map<String, Object> params) throws Exception {
		return b2biMapper.getB2BiStateByDocNm(params);
	}
	
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getB2BiStateMain(Map<String, Object> params) throws Exception {
		return b2biMapper.getB2BiStateMain(params);
	}		
	
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getMatchingCount(Map<String,Object> params) throws Exception {
		return b2biMapper.getMatchingCount(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getMatchingInterfaceId(String interfaceId) throws Exception {
		return b2biMapper.getMatchingInterfaceId(interfaceId);
	}	
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int insertB2BiInterfaceMetaData(Map<String, Object> params) throws Exception {
		return b2biMapper.insertB2BiInterfaceMetaData(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int updateB2BiInterfaceMetaData(Map<String, Object> params) throws Exception {
		return b2biMapper.updateB2BiInterfaceMetaData(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeB2BiInterfaceMetaData(String interfaceId) throws Exception {
		return b2biMapper.removeB2BiInterfaceMetaData(interfaceId);
	}		
	


}
