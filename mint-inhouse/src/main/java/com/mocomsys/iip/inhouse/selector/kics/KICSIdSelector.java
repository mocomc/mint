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
package com.mocomsys.iip.inhouse.selector.kics;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.selector.IdSelector;

/**
 * <pre>
 * 형사통합 인터페이스ID 채번 클래스
 * -------------------------------------------
 * 채번로직
 * -------------------------------------------
 *
 * </pre>
 * @author Solution TF
 *
 */
public class KICSIdSelector implements IdSelector{

	org.slf4j.Logger logger = LoggerFactory.getLogger(KICSIdSelector.class);

	public KICSIdSelector() throws Exception {
		super();
	}


	public void init() throws Exception {
	}

	@Autowired
	CommonService commonService;



	@Override
	@Transactional
	public String getInterfaceId(Object[] params) throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("TEMP");
		sb.append("-");
		sb.append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

		logger.debug("생성된 InterfaceID["+sb.toString()+"]");
		return sb.toString();
	}
}
