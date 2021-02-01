package pep.per.mint.batch.mapper.ifm;

import java.util.List;
import java.util.Map;

public interface IfmNHJobMapper {

	public List<Map<String, String>> selectInterfaces(Map<String, String> params) throws Exception;

	public Map selectInterfaceTransactionSummary(Map<String, String> params) throws Exception;

	public Map selectInterfaceTransactionSummaryAll(Map<String, String> params) throws Exception;

	public Map<String, Double> selectDataSize(Map<String, String> params) throws Exception;

}
