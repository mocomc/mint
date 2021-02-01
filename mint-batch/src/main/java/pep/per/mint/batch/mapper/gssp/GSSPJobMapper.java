package pep.per.mint.batch.mapper.gssp;
 
import java.util.List;
import java.util.Map;

public interface GSSPJobMapper {
    
    public List<Map> selectPosTransaction(Map params) throws Exception;
	
}
