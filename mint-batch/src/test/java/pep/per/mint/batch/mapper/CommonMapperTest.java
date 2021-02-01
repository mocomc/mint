package pep.per.mint.batch.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.batch.mapper.co.JobMapper;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;

import java.util.List;

/**
 * Created by Solution TF on 15. 11. 5..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        //"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
        "classpath:/config/batch-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class CommonMapperTest {

    Logger logger = LoggerFactory.getLogger(CommonMapperTest.class);

    @Autowired
    JobMapper jobMapper;

    @Test
    public void testGetJobSchedule() throws Exception {

        List<ZobSchedule> list = jobMapper.getJobScheduleList();

        Assert.assertNotNull(list);

        if(list != null){
            logger.debug(Util.join("list:", Util.toJSONString(list)));
        }

    }

    @Test
    public  void testInsertJobResult() throws Exception{
        //pep.per.mint.batch.job.su.TSU0505Job(String), 1(String), 1(String), 1(String), 0(Integer), 20151111154900(String), 20151111154900(String), 0 49 15 * * ?(String), 1(String), -1(String), (String)

        ZobResult zobResult = new ZobResult();
        zobResult.setGroupId("1");
        zobResult.setProcessId("pep.per.mint.batch.job.su.TSU0505Job");
        zobResult.setJobId("1");
        zobResult.setScheduleId("1");
        zobResult.setScheduleValue("0 49 15 * * ?");
        zobResult.setResultCd("-1");
        zobResult.setProcessStatus("1");
        zobResult.setStartDate(Util.getFormatedDate());
        zobResult.setErrorMsg("error");
        zobResult.setEndDate(Util.getFormatedDate());
        jobMapper.insertJobResult(zobResult);


    }

}
