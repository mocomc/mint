/**
 * 
 */
package pep.per.mint.database.mapper.su;

import pep.per.mint.common.data.basic.Requirement;
import java.util.List;
import java.util.Map;

/**
 * @author INSEONG
 *
 */
public interface KPIMapper {

	List<Map> getNotUseInterface(Map arg) throws Exception;
	
	List<Map> getNoRegInterfacesInfo(Map arg) throws Exception;
	
	int updateNoRegInterface(Map arg) throws Exception;
	
	//------------------------------------------------------------
	// main - 장애사전 예방 아이템 조회
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 개발 진행 현황 - 개발/테스트/이행/완료 조회
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getExpectationOfProblems(Map param) throws Exception;
	
	
	
	/**
	 * <pre>
	 * 인터페이스 관리 지표 - 재사용 인터페이스 조회 
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getReusedInterfaceList(Map param) throws Exception;
	
	
	//------------------------------------------------------------
	// KPI 현황(차트)
	//------------------------------------------------------------

	/**
	 * <pre>
	 * KPI 현황 - 인터페이스 사용율(월)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getUseInterfaceCountRateForMonth(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI 현황 - 인터페이스 등록율(월)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getRegInterfaceCountRateForMonth(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI 현황 - 인터페이스 재사용율(년)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getReusedInterfaceCountRateForYear(Map arg) throws Exception;
	
	
	/**
	 * <pre>
	 * KPI 현황 - 인터페이스 처리현황(일/월)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getStatusInterfaceCountRate(Map arg) throws Exception;

	/**
	 * <pre>
	 * KPI 현황 - CPU 임계사용율 over Count  
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getOverUsageCountForCPU(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI 현황 - Memory 임계사용율 over Count
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getOverUsageCountForMEMORY(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI 현황 - 납기준수율 현황 조회
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getStatusRequirementComplyRate(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI - 인터페이스 관리 지표 - 그리드 상세(납기 지연(미준수) 요건 리스트 조회)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Requirement> getRequirementNotComplyList(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : 서버 정보 조회
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getServerInfoForResourceCheck(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(그리드)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getOverUsageServerList(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI - 운영관리지표 - CPU/MEMORY 현황 : AP서버별 사용율 조회(차트)
	 * </pre> 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map> getOverUsageListForChart(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * KPI - 인터페이스 비율
	 * </pre>
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	List<Map> getInterfaceRate(Map arg) throws Exception;
	
	/**
	 * <pre>
	 * 인터페이스 최초 등록일 조회.
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	String getInterfaceBeginRegDate() throws Exception;
	
	/**
	 * <pre>
	 * KPI - 데이터 사이즈
	 * </pre>
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	List<Map> getDataSize(Map arg) throws Exception;	
}
