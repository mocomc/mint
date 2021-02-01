package pep.per.mint.database.service.op;
 
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import pep.per.mint.database.mapper.op.GSSPDashboardMapper; 

/**
 * 대시보드 관련 서비스
 * @author whoana
 *
 */

@Service
public class GSSPDashboardService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Autowired
	GSSPDashboardMapper gsspDashboardMapper; 
 

	/**
	 * 지연건수 - TOP num
	 * @param params
	 * @return
	 */
	public List<Map> getDelayListTop(Map params) throws Exception {
		return gsspDashboardMapper.getDelayListTop(params) ;
	}

     /**
     * 통신불가능포스 count
     * @return 
     */
    public Integer getDeadPosCount() throws Exception{
        return gsspDashboardMapper.getDeadPosCount();
    }
    
    /**
     * 통신불가능 포스 리스트
     * @return 
     */
    public List<Map> getDeadPosList() throws Exception{
        return gsspDashboardMapper.getDeadPosList();
    }
    
    
     /**
     * 거래미발생포스 카운트
     * @return
     * @throws Exception 
     */
    public Integer getNoTransactionPosCount() throws Exception{
        return gsspDashboardMapper.getNoTransactionPosCount();
    }
    
    /**
     * 거래미발생포스 리스트 
     * @return
     * @throws Exception 
     */
    public List<Map> getNoTransactionPosList() throws Exception{
        return gsspDashboardMapper.getNoTransactionPosList();
    }
    
    /**
     * 모든점포포스거래 건수 조회(최근 10분 동안)  
     * @return
     * @throws Exception 
     */
    public Integer getTotalPosTransactionCount(Map params) throws Exception{
        return gsspDashboardMapper.getTotalPosTransactionCount(params);
    }

    /**
	 * 실시간 처리건수 - 관심인터페이스
	 * @param params
	 * @return
	 */
	public List<Map> getRealTimeFavoriteCount(Map params) {
		return gsspDashboardMapper.getRealTimeFavoriteCount(params);
	}
    
}
