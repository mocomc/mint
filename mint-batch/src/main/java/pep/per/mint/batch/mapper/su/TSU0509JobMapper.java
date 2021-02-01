package pep.per.mint.batch.mapper.su;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;

public interface TSU0509JobMapper {

    public int getMatchingCount(Map params) throws Exception;
    
    public String getInterfaceBeginRegDate() throws Exception;
    
    public String getBeginRegDate() throws Exception;
	
    public List<Map> getMonthlyInterfaceCountList(Map params) throws Exception;
    
    public void insertInitTSU0509(Map params) throws Exception;
    
    public int insertTSU0509(Map params) throws Exception;    

    public int updateTSU0509(Map params) throws Exception;
    


}
