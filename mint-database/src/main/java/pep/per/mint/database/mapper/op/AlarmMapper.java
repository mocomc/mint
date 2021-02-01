/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.alarm.AlarmMessage;
import pep.per.mint.common.data.basic.sms.Sms;

/**
 * <pre>
 * pep.per.mint.database.mapper.op
 * AlarmMapper.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 17.
 */
public interface AlarmMapper {


	/**
	 * @param alarmMessage
	 */
	public void addAlramMessage(AlarmMessage alarmMessage) throws Exception;

	/**
	 * @param messageId
	 * @param role
	 */
	public void addAlramMessageRole(@Param("messageId")String messageId, @Param("roleId")String roleId)throws Exception;


}
