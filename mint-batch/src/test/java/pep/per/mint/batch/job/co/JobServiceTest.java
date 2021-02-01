package pep.per.mint.batch.job.co;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.batch.mapper.co.JobMapper;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 12..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        //"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
        "classpath:/config/batch-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class JobServiceTest {

    Logger logger = LoggerFactory.getLogger(JobServiceTest.class);

    @Autowired
    JobService jobService;

    @Test
    public void testGetJobResultList(){
        try{
            Map params = new HashMap();
            //params.put("","");
            List<ZobResult> list = jobService.getJobResultList(params);

            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


}
