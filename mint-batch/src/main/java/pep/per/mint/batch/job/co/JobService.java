package pep.per.mint.batch.job.co;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.batch.mapper.co.JobMapper;
import pep.per.mint.common.data.basic.batch.ZobResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 11..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class JobService {

    @Autowired
    JobMapper jobMapper;

    @Transactional
    public int insertJobResult(ZobResult zobResult) throws Exception{
        return jobMapper.insertJobResult(zobResult);
    }

    public List<ZobResult> getJobResultList(Map params) throws Exception{
        return jobMapper.getJobResultList(params);
    }

    public String getBatchHostname() throws Exception{
        return jobMapper.getBatchHostname();
    }


    public String getUseBatchHostname() throws Exception{
        return jobMapper.getUseBatchHostname();
    }
}
