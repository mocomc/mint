package pep.per.mint.migration.mapper.ifm;

import java.util.List;
import java.util.Map;

public interface IfmMapper {

	public List<Map> selectSI_STAT_INTF_DAY(Map<String,String> params) throws Exception;
	public List<Map> selectSI_STAT_INTF_MONTH(Map<String,String> params) throws Exception;
}
