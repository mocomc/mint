package pep.per.mint.batch.mapper.op;

import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * Created by TA on 17. 11. 28
 */
public interface TOP0813JobMapper {

    public int insertTOP0813(Map params) throws Exception;    
    
    public int deleteTOP0813(Map params) throws Exception;
    
    public int purgeTOP0813(@Param("purgeDate") String purgeDate) throws Exception;
    
}
