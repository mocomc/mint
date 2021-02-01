package pep.per.mint.batch.mapper.co;

import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 5..
 */
public interface JobMapper {

    public List<ZobSchedule> getJobScheduleList() throws Exception;

    public List<ZobSchedule> getJobScheduleListByClass(@Param("implClass") String implClass) throws Exception;

    public int insertJobResult(ZobResult zobResult) throws  Exception;

    public  List<ZobResult> getJobResultList(Map params) throws Exception;

    public String getBatchHostname() throws Exception;

    public String getUseBatchHostname() throws Exception;

    public String getPurgeToDate() throws Exception;

    public Map<String,String> getMIManangerConnectionInfo() throws Exception;

}
