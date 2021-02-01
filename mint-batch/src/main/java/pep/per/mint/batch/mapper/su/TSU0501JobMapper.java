package pep.per.mint.batch.mapper.su;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;

public interface TSU0501JobMapper {
	
    public List<UnknownInterfaceInfo> getUnknownInterfaceInfoList(Map params) throws Exception;
    
    public int getMatchingCount(String interfaceId) throws Exception;

    public int updateUnknownInterfaceInfo(UnknownInterfaceInfo info) throws Exception;
    
    public int insertUnknownInterfaceInfo(UnknownInterfaceInfo info) throws Exception;

}
