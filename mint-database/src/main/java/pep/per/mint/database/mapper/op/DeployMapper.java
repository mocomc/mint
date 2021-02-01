package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.Deployment;

public interface DeployMapper {

	public List<Deployment> getDeployInterfaceResults(Map<String, String> params) throws Exception;

	public Deployment getDeployInterfaceResult(@Param("reqDate") String reqDate, @Param("interfaceId") String interfaceId, @Param("seq") int seq) throws Exception;

	public String getInterfaceLastVersion(@Param("interfaceId") String interfaceId) throws Exception;

	public void insertDeployment(Deployment deployment) throws Exception;
 

}
