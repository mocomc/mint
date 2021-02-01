package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.database.mapper.op.KABDashboardMapper;

/**
 * 한국감정원 대시보드 관련 서비스
 * @author kesowga
 *
 */

@Service
public class KABDashboardService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
	KABDashboardMapper kabDashboardMapper;


     /**
     * TIME OUT count
     * @return
     */
    public Integer getTimeoutCount() throws Exception{
        return kabDashboardMapper.getTimeoutCount();
    }

    /**
     * 연계기관 통신상태 LIST
     * @return
     */
    public List<Map> getConnectStatus() throws Exception {
		return kabDashboardMapper.getConnectStatus() ;
	}

}
