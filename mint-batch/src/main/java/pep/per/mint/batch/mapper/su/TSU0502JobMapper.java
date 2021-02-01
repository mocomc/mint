package pep.per.mint.batch.mapper.su;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.batch.InterfaceCountSummary;

public interface TSU0502JobMapper {
	
    public List<InterfaceCountSummary> selectSummary(Map params) throws Exception;
    
    public int insertTSU0502(InterfaceCountSummary summary) throws Exception;

}
