/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.RestServiceUriMap;
import pep.per.mint.common.data.RuleSet;
import pep.per.mint.common.data.broker.BrokerInfo;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
@Service
public class InstallService {

	@Autowired
	BrokerService brokerService;
	
	@Autowired
	RuleService ruleService;
	
	/**
	 * 
	 */
	public InstallService() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * json형식의 각 데이터 파일 위치가 자바 시스템 프로퍼티로 설정되어 있어야 한다. 
	 * 필수 시스템 프로퍼티
	 * restServiceUriMapFile
	 * gatewayInstallFile
	 * defaultRuleSetFile
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void install() throws Exception {
		//1.RESTful URI 등록 
		String restServiceUriMapFile = System.getProperty("restServiceUriMapFile");
		if(restServiceUriMapFile == null) throw new Exception("system property[restServiceUriMapFile is null");
		RestServiceUriMap map = (RestServiceUriMap)Util.readObjectFromJson(new File(restServiceUriMapFile), RestServiceUriMap.class, "UTF-8");
		ruleService.insertRestServiceUriMap(map);
		
		//2.Gateway 브로커 어플리케이션 등록 
		String gatewayInstallFile = System.getProperty("gatewayInstallFile");
		if(gatewayInstallFile == null) throw new Exception("system property[gatewayInstallFile is null");
		BrokerInfo brokerInfo = (BrokerInfo)Util.readObjectFromJson(new File(gatewayInstallFile), BrokerInfo.class, "UTF-8");
		brokerService.insertBrokerInfo(brokerInfo);
		
		//3.공통코드등록
		
		//4.기본 RuleSet 등록
		String defaultRuleSetFile = System.getProperty("defaultRuleSetFile");
		if(defaultRuleSetFile == null) throw new Exception("system property[defaultRuleSetFile is null");
		RuleSet ruleSet = (RuleSet)Util.readObjectFromJson(new File(defaultRuleSetFile), RuleSet.class, "UTF-8");
		ruleService.insertRuleSet(ruleSet);
		

	}

}
