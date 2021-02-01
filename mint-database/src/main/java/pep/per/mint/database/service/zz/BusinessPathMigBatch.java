/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.zz;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.zz.BusinessPathMigBatchMapper;

/**
 * @author Solution TF
 *
 */
@Service
public class BusinessPathMigBatch {
	
	Logger logger = LoggerFactory.getLogger(BusinessPathMigBatch.class);
	
	@Autowired 
	BusinessPathMigBatchMapper businessPathMigBatchMapper;
	
	@Transactional
	public void mig(){
		
		try{
			logger.debug(BusinessPathMigBatch.class + " start");
 
			int addToBusinessIdIndex = 61;
			for(int idx = 88; idx <= 7346; idx ++){
				//businessId : BZ00000088 ~ BZ00007346
				String businessId =  Util.join("BZ", Util.leftPad(idx + "", 8, "0"));
				//addToBusinessId: BZ00000061~BZ00000087
				if(addToBusinessIdIndex > 87){addToBusinessIdIndex = 61;};
				String addToBusinessId = Util.join("BZ000000", "" + (addToBusinessIdIndex ++));
				logger.debug(Util.join("add :  [addToBusinessId:", addToBusinessId, ", businessId:", businessId,"]"));
				businessPathMigBatchMapper.insertL1Business(businessId, addToBusinessId);
				
			}
			
			
			
		}catch(Exception e){
			logger.debug(BusinessPathMigBatch.class + " error:", e);
		}
		
	}
	
	public List<Map<String, String>> selectMap() throws Exception{
		
	 
		List<Map<String, String>> mappingList = businessPathMigBatchMapper.selectMappingList(); 
			
		 return mappingList; 
		
	}

	
	
}
