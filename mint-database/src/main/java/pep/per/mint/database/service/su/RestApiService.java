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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.database.mapper.su.RestApiMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class RestApiService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestApiMapper restApiMapper;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.Service> getRestServiceList(Map params) throws Exception{
		return restApiMapper.getRestServiceList(params);
	}

	/**
	 *
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.Service getRestServiceDetail(String serviceId) throws Exception{
		return restApiMapper.getRestServiceDetail(serviceId);
	}

	@Transactional
	public int createRestService(pep.per.mint.common.data.basic.Service service) throws Exception {
		int res = 0;
		//TODO 작업 검토 작업 필요.
//		List<Server> serverList = server.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		res = restApiMapper.insertRestService(service);

		return res;
	}
	
	public int updateRestService(pep.per.mint.common.data.basic.Service service) throws Exception {

		int res = 0;
//		res = infraMapper.deleteSystemServerMaps(system.getSystemId());
//		List<Server> serverList = system.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//		res = infraMapper.deleteSystemRelativeUsers(system.getSystemId());
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		return restApiMapper.updateRestService(service);
	}
	

	public int deleteRestService(String serviceId, String modId, String modDate) throws Exception{
		int res = 0;
		res = restApiMapper.deleteRestService(serviceId, modId, modDate);
		return res;
	}
	
}
