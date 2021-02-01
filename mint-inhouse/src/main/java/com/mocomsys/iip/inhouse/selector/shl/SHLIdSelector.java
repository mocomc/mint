package com.mocomsys.iip.inhouse.selector.shl;

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
 * 신한생명 인터페이스ID 채번 클래스
 * -------------------------------------------
 * 채번로직
 * -------------------------------------------
 * 표준화 팀에서 정의한 인터페이스 채번 룰은 아래와 같습니다.
 * u통합 인터페이스 ID 규칙
 *  : {송신어플리케이션코드(3)} + {수신어플리케이션코드(3)} + DATA처리방식{B(atch)/D(effered)/O(nline)(1)} + {일련번호(5)}
 *  NCSNCSO12345
 *
 * </pre>
 * @author Solution Interface Portal
 *
 */
public class SHLIdSelector implements IdSelector{

	org.slf4j.Logger logger = LoggerFactory.getLogger(SHLIdSelector.class);

	public SHLIdSelector() throws Exception {
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
		pep.per.mint.common.data.basic.System receiverSystem = null;
		List<pep.per.mint.common.data.basic.System> systemList = interfaze.getSystemList();

		//--------------------------------------------------
		// 송/수신 정보가 단일항목임을 가정한다.
		//--------------------------------------------------
		for (pep.per.mint.common.data.basic.System system : systemList) {
			if (pep.per.mint.common.data.basic.System.NODE_TYPE_SENDER.equals(system.getNodeType())) {
				senderSystem = system;
			} else if(pep.per.mint.common.data.basic.System.NODE_TYPE_RECEIVER.equals(system.getNodeType())) {
				receiverSystem = system;
			}
		}
		if (senderSystem == null || receiverSystem == null) {
			String interfaceInfo = "";
			try {
				interfaceInfo = Util.toJSONString(interfaze);
			} catch (Exception e) {
			}
			throw new IdSelectorException(Util.join("[인터페이스ID채번예외]송신 또는 수신 시스템이 NULL 값이라 예외 처리한다. 입력 인터페이스 정보:\r\n", interfaceInfo));
		}

		sb.append(senderSystem.getSystemCd());
		sb.append(receiverSystem.getSystemCd());
		sb.append(interfaze.getDataPrMethodNm().substring(0, 1));

		// 채번테이블에서 순차값 검색.
		logger.debug("인터페이스 Key["+sb.toString()+"]");
		Map params1 = new HashMap();
		params1.put("idKey", sb.toString());

		String maxIndex = commonService.getInterfaceIDMaxSHL(params1);

		sb.append(maxIndex);
		logger.debug("생성된 InterfaceID["+sb.toString()+"]");
		return sb.toString();
	}
}
