/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.database.mapper.su.ApplicationMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class ApplicationService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationMapper applicationMapper;

	public List<pep.per.mint.common.data.basic.Application> getApplicationList(Map params) throws Exception{
		return applicationMapper.getApplicationList(params);
	}

	@Transactional
	public int createApplication(pep.per.mint.common.data.basic.Application service) throws Exception {
		int res = 0;
		res = applicationMapper.insertApplication(service);
		res = applicationMapper.insertApplicationMappingUserRole(service);
		return res;
	}

	@Transactional
	public int updateApplication(pep.per.mint.common.data.basic.Application screenLabel) throws Exception {
		return applicationMapper.updateApplication(screenLabel);
	}

	@Transactional
	public int deleteApplication(Map params) throws Exception{
		int res = 0;
		res = applicationMapper.deleteApplicationMappingUserRole(params);
		res = applicationMapper.deleteApplication(params);
		return res;
	}

	public List<pep.per.mint.common.data.basic.Application> existApplication(Map params) throws Exception{
		return applicationMapper.existApplication(params);
	}

	public List<String> useApplication(@Param("appId")String appId) throws Exception{
		return applicationMapper.usedApplication(appId);
	}

}
