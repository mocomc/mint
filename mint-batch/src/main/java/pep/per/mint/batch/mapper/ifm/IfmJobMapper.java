package pep.per.mint.batch.mapper.ifm;

import java.util.List;
import java.util.Map;

public interface IfmJobMapper {

	public List<Map<String, String>> selectInterfaces(Map<String, String> params) throws Exception;

	public List<Map<String, String>> selectInterfacesType2(Map<String, String> params) throws Exception;

	public Map selectInterfaceTransactionSummary(Map<String, String> params) throws Exception;

	public Map selectInterfaceTransactionSummaryType2(Map<String, String> params) throws Exception;

	public Map<String, Double> selectDataSize(Map<String, String> params) throws Exception;

	public Map<String, Double> selectDataSizeType2(Map<String, String> params) throws Exception;

}
