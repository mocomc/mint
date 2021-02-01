/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.op;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.database.mapper.an.RequirementMapper;

/**
 * 요건 관련 서비스(조회, 입력, 삭제, 수정)
 *
 * @author Solution TF
 */

@Service
public class SchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

	/**
	 *
	 */
	@Autowired
	RequirementMapper requirementMapper;

	/**
	 * <pre>
	 * 요건 리스트를 조회한다.
	 * REST-R01-AN-01-00 요건리스트조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getRequirementList(Map params) throws Exception{

		List<Requirement> requirementList = requirementMapper.getRequirementList(params);

		String fromHour = (String)params.get("fromHour");
		String toHour = (String)params.get("toHour");
		List<Requirement>  sendList = new ArrayList();
		for(Requirement req : requirementList){
			if(req.getInterfaceInfo().getDataFrequencyInput().trim().equals("")){

			}else{
				try{
					CronExpression ce = new org.quartz.CronExpression(req.getInterfaceInfo().getDataFrequencyInput().trim());
					boolean isRun = false;
					logger.debug(ce.getCronExpression() +"");
					logger.debug(ce.getExpressionSummary() +"");
					for(int i=Integer.parseInt(fromHour); i<(Integer.parseInt(toHour)+1)  ;i++)
					{
						java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
						logger.trace(String.format("%s%02d%s", params.get("fromDate") , i ,"0000"));
						java.util.Date date = format.parse( String.format("%s%02d%s", params.get("fromDate") , i ,"0000"));

						if(ce.isSatisfiedBy(date)){
							isRun = true;
						}
						if(isRun){
							sendList.add(req);
							break;
						}
					}

				}catch(Exception e){
					logger.warn(e.getMessage());
				}
			}
		}

		return sendList;
	}

}
