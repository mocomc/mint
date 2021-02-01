package pep.per.mint.batch.mapper.op;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface MonthStatisticsJobMapper {

    public int insertSM(Map params) throws Exception;
    
    public int deleteSM(Map params) throws Exception;
}
