/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.im;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.database.mapper.im.SystemPriorityMngMapper;

/**
 * <pre>
 * pep.per.mint.database.service.im
 * SystemQmgrMngService.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 26.
 */
@Service
public class SystemPriorityMngService {

	@Autowired
	SystemPriorityMngMapper systemPriorityMngMapper;

	public List getSystemPriorityList() throws Exception {
		return systemPriorityMngMapper.getSystemPriorityList();
	}

	@Transactional
	public int addSystemPriorityList(List<Map> list) throws Exception {
		systemPriorityMngMapper.deleteSystemPriority();
		int res= 0;
		if(list !=null && list.size() > 0) {
			for(Map info : list) {
				systemPriorityMngMapper.insertSystemPriority(info);
				res = res +1;
			}
		}
		return res;
	}
}
