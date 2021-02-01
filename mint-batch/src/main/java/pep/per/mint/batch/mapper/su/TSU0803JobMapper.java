package pep.per.mint.batch.mapper.su;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TSU0803JobMapper {

	public int updateTSU0803(Map params) throws Exception;

	public int deleteTSU0803(Map params) throws Exception;

	public int insertTSU0803(Map params) throws Exception;

	public int selectOneTSU0803(Map params) throws Exception;

	public Map<String, Integer> selectInterfaceInfo(@Param("integrationId")String integrationId) throws Exception;

	public List<Map<String, Integer>> selectInterfaceInfoList(Map params) throws Exception;

	public int insertZeroDataTSU0803(Map params)throws Exception;

	public String getEnvironment(Map params) throws Exception;

}
