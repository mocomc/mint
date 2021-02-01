/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.front.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.op.IIPAgentService;

/**
 * <pre>
 * pep.per.mint.front.service
 * FrontInitializeService.java
 * </pre>
 * @author whoana
 * @date Feb 15, 2019
 */
@Service
public class FrontInitializeService {
	private static final Logger logger = LoggerFactory.getLogger(FrontManagementService.class);

	@Autowired
	IIPAgentService iipAgentService;

	@PostConstruct
	public void init() {

		try {
			IIPAgentLog log = new IIPAgentLog();

			String getDate = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			log.setGetDate(getDate);
			log.setStatus(IIPAgentInfo.AGENT_STAT_ERROR);

			iipAgentService.agentReset(log);
		} catch(Exception e) {
			logger.error("FrontInitializeService.init 메소드 실행중 예외:",e);
		}
	}

}
