/**
 * 
 */
package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.database.mapper.su.KPIMapper;

/**
 * 
 * FAQ 관련 서비스(조회, 입력, 삭제, 수정)
 * 
 * @author INSEONG
 *
 */
@Service
public class KPIService {

	private static final Logger logger = LoggerFactory.getLogger(KPIService.class);

	/**
	 * 
	 */
	@Autowired
	KPIMapper kpiMapper;
	
	/**
	 * <pre>
	 * 미사용 인터페이스를 가져온다.
	 * REST-R01-SU-03-05-002  
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getNotUseInterface(Map arg) throws Exception {
		return kpiMapper.getNotUseInterface(arg);
	}
	
	
	public List<Map> getNoRegInterfacesInfo(Map arg) throws Exception {
		return kpiMapper.getNoRegInterfacesInfo(arg);
	}
	
	
	public int updateNoRegInterface(Map arg) throws Exception {
		return kpiMapper.updateNoRegInterface(arg);
	}
	
	//------------------------------------------------------------
	// main - 장애 사전예방 아이템 조회
	//------------------------------------------------------------
	public List<Map> getExpectationOfProblems(Map param) throws Exception {
		return kpiMapper.getExpectationOfProblems(param);
	}
	
	
	
	//
	/**
	 * <pre>
	 * 재사용 인터페이스를 가져온다.
	 * REST-R01-SU-03-05-003  
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getReusedInterfaceList(Map arg) throws Exception {
		return kpiMapper.getReusedInterfaceList(arg);
	}
	
	
	//------------------------------------------------------------
	// KPI 현황(차트)
	//------------------------------------------------------------
	
	/**
	 * <pre>
	 * 인터페이스 사용율(월)을 가져온다.
	 * REST-R02-SU-03-05-002  
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getUseInterfaceCountRateForMonth(Map arg) throws Exception {
		return kpiMapper.getUseInterfaceCountRateForMonth(arg);
	}
	
	
	/**
	 * <pre>
	 * 인터페이스 등록율(월)을 가져온다.
	 * REST-R02-SU-03-05-001  
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getRegInterfaceCountRateForMonth(Map arg) throws Exception {
		return kpiMapper.getRegInterfaceCountRateForMonth(arg);
	}
	
	
	/**
	 * <pre>
	 * 인터페이스 재사용율(년)을 가져온다.
	 * REST-R02-SU-03-05-001  
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getReusedInterfaceCountRateForYear(Map arg) throws Exception {
		return kpiMapper.getReusedInterfaceCountRateForYear(arg);
	}
	
	
	/**
	 * <pre>
	 * 인터페이스 처리현황(일/월)을 가져온다.
	 * REST-R02-SU-03-00-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getStatusInterfaceCountRate(Map arg) throws Exception {
		return kpiMapper.getStatusInterfaceCountRate(arg);
	}
	
	
	/**
	 * <pre>
	 * 시스템(IP)별 CPU 임계사용율 over Count 를 가져온다.
	 * REST-R03-SU-03-00-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getOverUsageCountForCPU(Map arg) throws Exception {
		return kpiMapper.getOverUsageCountForCPU(arg);
	}
	
	/**
	 * <pre>
	 * 시스템(IP)별 Memory 임계사용율 over Count 를 가져온다.
	 * REST-R04-SU-03-00-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getOverUsageCountForMEMORY(Map arg) throws Exception {
		return kpiMapper.getOverUsageCountForMEMORY(arg);
	}
	
	
	/**
	 * <pre>
	 * 납기준수율 현황 정보를 가져온다.
	 * REST-R01-SU-03-05-004 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getStatusRequirementComplyRate(Map arg) throws Exception {
		return kpiMapper.getStatusRequirementComplyRate(arg);
	}
	
	/**
	 * <pre>
	 * KPI - 인터페이스 관리 지표 - 그리드 상세(납기 지연(미준수) 요건 리스트 조회)
	 * REST-R02-SU-03-05-004
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Requirement> getRequirementNotComplyList(Map arg) throws Exception {
		return kpiMapper.getRequirementNotComplyList(arg);
	}
	
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : 서버 정보 조회
	 * REST-R01-SU-03-06-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getServerInfoForResourceCheck(Map arg) throws Exception {
		return kpiMapper.getServerInfoForResourceCheck(arg);
	}
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(그리드)
	 * REST-R02-SU-03-06-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getOverUsageServerList(Map arg) throws Exception {
		return kpiMapper.getOverUsageServerList(arg);
	}
	
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(차트)
	 * REST-R03-SU-03-06-001 
	 *   
	 * </pre>
	 * @param Map
	 * @throws Exception
	 */
	public List<Map> getOverUsageListForChart(Map arg) throws Exception {
		return kpiMapper.getOverUsageListForChart(arg);
	}
	
	
	/**
	 * <pre>
	 * KPI - 인터페이스 비율
	 * </pre>
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Map> getInterfaceRate(Map arg) throws Exception {
		return kpiMapper.getInterfaceRate(arg);
	}
	
	/**
	 * <pre>
	 * 인터페이스 최초 등록일 조회.
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public String getInterfaceBeginRegDate() throws Exception {
		return kpiMapper.getInterfaceBeginRegDate();
	}
	
	/**
	 * <pre>
	 * KPI - 데이터 사이즈
	 * </pre>
	 * 
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDataSize(Map arg) throws Exception {
		return kpiMapper.getDataSize(arg);
	}	
}
