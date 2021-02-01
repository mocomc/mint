package pep.per.mint.database.mybatis.persistance;

import pep.per.mint.common.data.CommonSysConf;


public interface CommonMapper {
	
	public CommonSysConf getCommonSysConf(String key) throws Exception;
	
	
}
