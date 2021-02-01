/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.im;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.sms.Sms;
/**
 * <pre>
 * pep.per.mint.database.mapper.im
 * EventNotiMapper.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
public interface EventNotiMapper {

	public List<Sms>  getEventList(Map params)throws Exception;

	public List<Map> getEventNotiList(Map params)throws Exception;

	public void updateEventNotiList(Map info) throws Exception;

	public void insertEventNotiList(Map info) throws Exception;

	public List<Map> existEventNotiList(Map params)throws Exception;

	public List<Map> getEventNotiQmgrList() throws Exception;


}
