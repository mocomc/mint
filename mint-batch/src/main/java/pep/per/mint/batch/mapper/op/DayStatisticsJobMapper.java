package pep.per.mint.batch.mapper.op;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface DayStatisticsJobMapper {

    public int insertSD(Map params) throws Exception;

    public int deleteSD(Map params) throws Exception;
}
