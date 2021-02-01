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
package com.mocomsys.iip.inhouse.selector.hdins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.IdSelectorException;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.selector.IdSelector;

/**
 * <pre>
 * 현대해상 인터페이스ID 채번 클래스
 * -------------------------------------------
 * 채번로직
 * -------------------------------------------
 * 표준화 팀에서 정의한 인터페이스 채번 룰은 아래와 같습니다.
 * u통합 인터페이스 ID 규칙
 *  : I{맵핑(M)/라우팅(R)(1)} + {송신 시스템 코드 level1(3)} + {송신 시스템 코드 level2(2)} + {일련번호(4)}
 *  IM_CIS-AC_0001
 *
 * 20170405
 *  : 시스템 레벨이 3레벨로 정의 된 경우, 1-2 레벨 ID 로 생성
 *
 * 20170523
 * 	: 인터페이스 Mapping/Routing 정보
 * 	:  인터페이스 hasDataMap (N)  Routing  (Y) Mapping 으로 체크
 *
 * </pre>
 * @author Solution TF
 *
 */
public class HDINSIdSelector implements IdSelector{

	org.slf4j.Logger logger = LoggerFactory.getLogger(HDINSIdSelector.class);

	public HDINSIdSelector() throws Exception {
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


		Interface interfaze = (Interface) params[0];

		pep.per.mint.common.data.basic.System senderSystem = null;
		List<pep.per.mint.common.data.basic.System> systemList = interfaze.getSystemList();

		//--------------------------------------------------
		// 송신을 프로바이더로 선택
		//--------------------------------------------------
		for (pep.per.mint.common.data.basic.System system : systemList) {
			if (pep.per.mint.common.data.basic.System.NODE_TYPE_SENDER.equals(system.getNodeType())) {
				senderSystem = system;
				break;//첫번째 송신신시스템코드로 인터페이스 ID 채번
			}
		}
		if (senderSystem == null) {
			String interfaceInfo = "";
			try {
				interfaceInfo = Util.toJSONString(interfaze);
			} catch (Exception e) {
			}
			throw new IdSelectorException(Util.join("[인터페이스ID채번예외]송신 시스템이 NULL 값이라 예외 처리한다. 입력 인터페이스 정보:\r\n", interfaceInfo));
		}
		String intfGubun = "";
		if(interfaze.getHasDataMap().equalsIgnoreCase("") ||
				interfaze.getHasDataMap().equalsIgnoreCase("N")){
			Map envMap =  commonService.getEnvironmentalValues();
			String attrCd = "";
			List attrList = (List)envMap.get("system.auto.interfaceId.create.attrCd");
			if(attrList != null && attrList.size()>0){
				attrCd = (String) attrList.get(0);
			}else{
				throw new IdSelectorException(Util.join("[인터페이스ID채번예외]인터페이스 구분 : 채널 특성이  NULL 값이라 예외 처리한다."));
			}


			List<InterfaceProperties> proplist =  interfaze.getProperties();
			for (InterfaceProperties props : proplist) {
				if (attrCd.equals(props.getAttrCd())) {
					intfGubun = props.getAttrValue();
					break;
				}
			}

			if(intfGubun == null || intfGubun.length()==0){
				throw new IdSelectorException(Util.join("[인터페이스ID채번예외]인터페이스 구분 : 채널 특성이  NULL 값이라 예외 처리한다."));
			}

			intfGubun = intfGubun.toUpperCase();
			if(!("M".equals(intfGubun) || "R".equals(intfGubun))){
				throw new IdSelectorException(Util.join("[인터페이스ID채번예외]인터페이스 구분 : 채널 특성이 M, R 값이 아니라 예외 처리한다."));
			}

		}else{
			if("0".equalsIgnoreCase(interfaze.getHasDataMap())){
				intfGubun = "R";
			}else if("1".equalsIgnoreCase(interfaze.getHasDataMap())){
				intfGubun = "M";
			}else if("2".equalsIgnoreCase(interfaze.getHasDataMap())){
				intfGubun = "P";
			}else if("3".equalsIgnoreCase(interfaze.getHasDataMap())){
				intfGubun = "G";
			}else{
				throw new IdSelectorException(Util.join("[인터페이스ID채번예외]인터페이스 종류 : 지정된 값이 아니라 예외 처리한다."));
			}
		}

		// 상위 시스템 조회
		//
		sb.append("I");
		sb.append(intfGubun); //  인터페이스 맵핑/라우팅 여부 (M, R) 현재는 연계특성에 있음.
		sb.append("_");
		Map<String,String> paramsSystem = new HashMap<String, String>();
		paramsSystem.put("systemId", senderSystem.getSystemId());
		List<System> systemPathList = commonService.getSystemPathList(paramsSystem);
		if(systemPathList.size()<=0){
			throw new IdSelectorException(Util.join("[인터페이스ID채번예외] system경로가 올바르지 않습니다."));
		}
		//sb.append(systemPathList.get(0).getSystemCd());  // 송수시스템 level 1
		//sb.append("-");
		sb.append(systemPathList.get(1).getSystemCd());  // 송수시스템 level 2

//
//		pep.per.mint.common.data.basic.System pSystem = commonService.getParentSystem(senderSystem.getSystemId());
//		sb.append(pSystem.getParent().getSystemCd());  // 송수시스템 level 1
//		sb.append("-");
//		sb.append(senderSystem.getSystemCd());  // 송수시스템 level 2


		// 채번테이블에서 순차값 검색.
//
		sb.append("_");
		logger.debug("인터페이스 Key["+sb.toString()+"]");
		//String maxIndex = commonService.getInterfaceID(sb.toString());
		Map params1 = new HashMap();
		params1.put("idKey", sb.toString());
		params1.put("seqLen", "4");
		//commonService.updateInterfaceIDMax(sb.toString());
		//String maxIndex = commonService.getInterfaceIDMax(params1);

		String maxIndex = commonService.getInterfaceIDMaxHDINS(params1);

		sb.append(maxIndex);
		logger.debug("생성된 InterfaceID["+sb.toString()+"]");
		return sb.toString();
	}
}
