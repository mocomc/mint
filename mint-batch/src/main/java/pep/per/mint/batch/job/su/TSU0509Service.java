package pep.per.mint.batch.job.su;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.su.TSU0501JobMapper;
import pep.per.mint.batch.mapper.su.TSU0509JobMapper;
import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;
import pep.per.mint.common.util.Util;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0509Service extends JobServiceCaller {

	@Autowired
	TSU0509JobMapper tsu0509JobMapper;
	
    @Transactional
	public void run(Map params) throws Exception {
    	if( params == null )
    		params= new HashMap();
        
        //---------------------------------------------------------------------------------
        // (1) 배치가 최초 수행인지 체크한다.
        //---------------------------------------------------------------------------------
        int checkCnt = tsu0509JobMapper.getMatchingCount(null);
        
        //---------------------------------------------------------------------------------
        // (2) 배치가 최초 수행이면, 인터페이스 등록일자 기준 가장 작은 일자를 조회하여 집계 정보를 생성한다.
        //---------------------------------------------------------------------------------
        if( checkCnt == 0 ) {
        	
        	String beginDate = tsu0509JobMapper.getInterfaceBeginRegDate();
        	params.put("beginDate", beginDate);
        	params.put("fromDate", beginDate);
        	
        	tsu0509JobMapper.insertInitTSU0509(params);
        } else {

            //---------------------------------------------------------------------------------
            // (3) 배치가 최초 수행이 아니면, 현재 월 기준 이전 달 부터 집계 정보를 확인한다.
            //---------------------------------------------------------------------------------
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.MONTH, -1);
            String beginDate = tsu0509JobMapper.getBeginRegDate();
            params.put("beginDate", beginDate);
            params.put("fromDate", Util.getFormatedDate(cal.getTimeInMillis(), "yyyyMM") );
        	
        	List<Map> monthlyInterfaceCountList = tsu0509JobMapper.getMonthlyInterfaceCountList(params);
        	
        	for(Map info : monthlyInterfaceCountList ){
        		
        		params.put("stMonth", info.get("ST_MONTH"));
        		params.put("channelId", info.get("CHANNEL_ID"));

                //---------------------------------------------------------------------------------
                // (4) 추출된 정보를 기준으로 집계 정보가 존재하면 update, 존재하지 않으면 insert 를 수행한다.
                //---------------------------------------------------------------------------------            
        		checkCnt = tsu0509JobMapper.getMatchingCount(params);
        		if( checkCnt == 0 ) {
        			tsu0509JobMapper.insertInitTSU0509(info);
        		} else {
        			tsu0509JobMapper.updateTSU0509(info);	
        		}
        	}
        }
	}

}
