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

import pep.per.mint.database.mapper.su.InterfaceAttrMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class InterfaceAttrService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InterfaceAttrMapper interfaceAttrMapper;

	public List<pep.per.mint.common.data.basic.InterfaceAdditionalAttribute> getAttributeList(Map params) throws Exception{
		return interfaceAttrMapper.getAttributeList(params);
	}

	@Transactional
	public int createAttribute(pep.per.mint.common.data.basic.InterfaceAdditionalAttribute service) throws Exception {
		int res = 0;
		res = interfaceAttrMapper.insertAttribute(service);
		return res;
	}

	@Transactional
	public int updateAttribute(pep.per.mint.common.data.basic.InterfaceAdditionalAttribute screenLabel) throws Exception {
		return interfaceAttrMapper.updateAttribute(screenLabel);
	}

	@Transactional
	public int deleteAttribute(Map params) throws Exception{
		int res = 0;
		res = interfaceAttrMapper.deleteAttribute(params);
		return res;
	}

	public List<pep.per.mint.common.data.basic.InterfaceAdditionalAttribute> existAttribute(Map params) throws Exception{
		return interfaceAttrMapper.existAttribute(params);
	}

	public List<String> useAttribute(@Param("attrId")String attrId) throws Exception{
		return interfaceAttrMapper.usedAttribute(attrId);
	}

}
