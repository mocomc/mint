/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-7105 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.App;
import pep.per.mint.common.data.basic.BasicInfo;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.LoginInfo;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.util.Util;



/**
 * 요건정보
 * 
 */

/**
 * @author noggenfogger
 *
 */
public class RequirementDataBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDataBuilder.class);
	
	@Test
	public void build(){
		
		List<Requirement> list = new ArrayList<Requirement>();
		
		Requirement requirement1 = new Requirement();{
			requirement1.setRequirementId("");
			requirement1.setRequirementNm("");
			requirement1.setStatus("");	
			
			Business business = new Business();{
				business.setBusinessId("");
				business.setBusinessNm("");
			}
			requirement1.setBusiness(business);
			
			Interface interfaceInfo = new Interface();{
				interfaceInfo.setInterfaceId("");
				interfaceInfo.setInterfaceNm("");
				
				List<System> systemList = new ArrayList<System>();{
					System system1 = new System();{
						system1.setSystemId		("");
						system1.setSystemNm		("");
						system1.setSystemCd		("");
						system1.setNodeType		("");
						system1.setResourceCd	("");
						system1.setResourceNm	("");
						system1.setService		("");
						
					}
					systemList.add(system1);
				}
				interfaceInfo.setSystemList(systemList);
			}
			requirement1.setInterfaceInfo(interfaceInfo);;
		}
		//
	
		//------------------------------------------
		//JSON DATA Building
		//------------------------------------------
		//String jsonString = Util.toJSONString(basicInfo);
		//logger.debug(Util.join("data:\n",jsonString));
		String fileName = "REST-R01-AN-01-00.json";
		String path = "./src/main/webapp/WEB-INF/test-data/an";
		
		DataBuilderUtil.saveToLocal(path,fileName, list);
		
		logger.debug(Util.join("build success BasicInfoData:",path,"/",fileName));
		
		
	}
	
	
	
}
