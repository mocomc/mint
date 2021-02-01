package pep.per.mint.database.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.CommonSysConf;
import pep.per.mint.common.exception.NotFoundException;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mybatis.persistance.CommonMapper;



@Service
public class CommonService {
	
	@Autowired
	CommonMapper commonMapper;
	
	public CommonSysConf getCommonSysConf(String id) throws Exception{
		
		CommonSysConf commonSysConf = commonMapper.getCommonSysConf(id);
		
		if(commonSysConf == null) throw new NotFoundException(Util.join("can't found CommonSysConf[id=",id,"]"));
		
		return commonSysConf;
	}
}
