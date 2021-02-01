/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Environment;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.im.EventNotiMapper;
import pep.per.mint.database.mapper.su.EnvironmentMapper;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * EventNotiService.java
 * </pre>
 * @author bill
 * @date 2018. 7. 26.
 */
@Service
public class EventNotiService {

	@Autowired
	EventNotiMapper eventNotiMapper;


	@Autowired
	EnvironmentMapper environmentMapper;

	public List getEventList(Map params) throws Exception {
		return eventNotiMapper.getEventList(params);
	}

	public List<Map> getEventNotiList(Map params) throws Exception  {
		return eventNotiMapper.getEventNotiList(params);
	}

	@Transactional
	public int updateEventNotiList(Map list) throws Exception {
		int res = 0;
		List existL = eventNotiMapper.existEventNotiList(list);
		if(existL !=null && existL.size() > 0) {
			eventNotiMapper.updateEventNotiList(list);
		}else{
			eventNotiMapper.insertEventNotiList(list);
		}
		return res;
	}

	public List<Map> getEventNotiQmgrList()  throws Exception  {
		return eventNotiMapper.getEventNotiQmgrList();
	}

	public int updateEventNotiEnv(Map list) throws Exception {
		Map params = new HashMap();
		params.put("pack", "inhouse");
		params.put("attrId", "sms.control.type.nh");
		List exlist = environmentMapper.existEnvironment(params);
		Environment env = new Environment();
		env.setPack("inhouse");
		env.setAttrId("sms.control.type.nh");
		env.setAttrNm("inhouse.sms.control.type.nh");
		env.setAttrVal((String)list.get("items"));
		env.setComments("NH Event Type Control checkValue P:Process/M:QManager/Q:Queue/C:Channel/I:Interface");
		env.setRegId("iip");
		env.setRegDate(Util.getFormatedDate());
		env.setModId("iip");
		env.setModDate(Util.getFormatedDate());
		env.setIdx("1");
		if(exlist !=null && exlist.size() > 0) {
			environmentMapper.updateEnvironment(env);
		}else{

			environmentMapper.insertEnvironment(env);
		}
		return 0;
	}
}
