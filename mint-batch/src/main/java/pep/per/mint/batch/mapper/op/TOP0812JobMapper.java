package pep.per.mint.batch.mapper.op;

import java.util.Map;

/**
 * Created by TA on 17. 11. 28
 */
public interface TOP0812JobMapper {

    public int insertTOP0812(Map params) throws Exception;

    public int deleteTOP0812(Map params) throws Exception;

    public int insertTOP0817(Map params) throws Exception;

    public String getEnvironment(Map params) throws Exception;
}
