/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, whoana.j), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.batch.job.op;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.op.DayStatisticsJobMapper;
import pep.per.mint.batch.mapper.op.LogPurgeJobMapper;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class LogPurgeService extends JobServiceCaller{

    @Autowired
    LogPurgeJobMapper logPurgeJobMapper;

    @Transactional
    public void run(Map params) throws Exception{
    	//logPurgeJobMapper.deleteTrLog(params);
    	//Dev
    	//logPurgeJobMapper.deleteTrLogDevMasterlog(params);
    	//logPurgeJobMapper.deleteTrLogDevMasterlogCustom(params);
    	//logPurgeJobMapper.deleteTrLogDevDetaillog(params);
    	//logPurgeJobMapper.deleteTrLogDevDetaillogCustom(params);
    	//logPurgeJobMapper.deleteTrLogDevDetaillogData(params);
    	//logPurgeJobMapper.deleteTrLogDevDetaillogErr(params);

    	 logPurgeJobMapper.deleteTOP0199(params);
         logPurgeJobMapper.deleteTOP0801(params);
         logPurgeJobMapper.deleteTOP0802(params);
         logPurgeJobMapper.deleteTOP0805(params);
         logPurgeJobMapper.deleteTOP0806(params);
         logPurgeJobMapper.deleteTOP0807(params);
         logPurgeJobMapper.deleteTOP0808(params);
         logPurgeJobMapper.deleteTOP0809(params);
         logPurgeJobMapper.deleteTOP0810(params);

         try {
	         //email 삭제
		     logPurgeJobMapper.deleteTOP0903(params);
		     logPurgeJobMapper.deleteTOP0901(params);
         }catch(Exception e) {
        	 e.printStackTrace();
         }

         try {
        	 //sms 삭제
		     logPurgeJobMapper.deleteTOP0905(params);
		     logPurgeJobMapper.deleteTOP0904(params);
         }catch(Exception e) {
        	 e.printStackTrace();
         }
         try {
		     //test call삭제
		     logPurgeJobMapper.deleteTIM0602(params);
		     logPurgeJobMapper.deleteTIM0601(params);
         }catch(Exception e) {
        	 e.printStackTrace();
         }



    }

}
