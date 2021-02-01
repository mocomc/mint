package pep.per.mint.batch.mapper.op;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whoana on 15. 9. 30..
 */
public interface HourStatisticsJobMapper {

    public int insertSH(Map params) throws Exception;

    public int deleteSH(Map params) throws Exception;
}
