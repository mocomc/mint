/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.alarm.AlarmMessage;
import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.database.mapper.op.AlarmMapper;

/**
 * <pre>
 * pep.per.mint.database.service.op
 * AlarmService.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 17.
 */
@Service
public class AlarmService {

	@Autowired
	AlarmMapper alarmMapper;


	/**
	 * @param alarmMessage
	 * @throws Exception
	 */
	@Transactional
	public void addAlramMessage(AlarmMessage alarmMessage) throws Exception {
		alarmMapper.addAlramMessage(alarmMessage);
		List<String> roles = alarmMessage.getRoles();
		for(String roleId : roles) {
			alarmMapper.addAlramMessageRole(alarmMessage.getMessageId(), roleId);
		}
	}


}
