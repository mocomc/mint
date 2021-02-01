package pep.per.mint.batch.mapper.su;

import java.util.List;
import java.util.Map;
 

public interface TSU080301JobMapper {

	public List<Map<String, String>> selectInterfaces(Map<String, String> params) throws Exception;
	
	public Map selectInterfaceTransactionSummary(Map<String, String> params) throws Exception;

}
