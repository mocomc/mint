package pep.per.mint.database.service.su;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.database.mapper.su.PotalStatisticsMapper;


/**
 * @author TA
 *
 */

@Service
public class PotalStatisticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(PotalStatisticsService.class);
	
	@Autowired
	PotalStatisticsMapper potalStatisticsMapper;
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getLoginStatistics1(Map<String, Object> params) throws Exception {
		return potalStatisticsMapper.getLoginStatistics1(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getLoginStatistics2(Map<String, Object> params) throws Exception {
		return potalStatisticsMapper.getLoginStatistics2(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getLoginStatisticsDetail(Map<String, Object> params) throws Exception {
		return potalStatisticsMapper.getLoginStatisticsDetail(params);
	}
	


}
