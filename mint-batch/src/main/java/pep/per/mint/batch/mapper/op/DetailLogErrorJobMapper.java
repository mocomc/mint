package pep.per.mint.batch.mapper.op;

import java.util.List;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface DetailLogErrorJobMapper {

    public List<Map> selectDelayedTrLog(Map params) throws Exception;

    public int insertDetailLogError(Map params) throws Exception;
    
    public int updateMasterlogStatus(Map params) throws Exception;
    
}
