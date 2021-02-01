package pep.per.mint.migration.mapper.su;

import java.util.Map;

public interface SummaryMapper {

	public String getInterfaceId(String integrationId)  throws Exception;
	public int deleteTSU0804(Map<String,String> params) throws Exception;
	public int insertTSU0804(Map params) throws Exception;
	public int deleteTSU0805(Map<String,String> params) throws Exception;
	public int insertTSU0805(Map params) throws Exception;

}
