package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 10. 23..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        //"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
        "classpath:/config/database-context.xml","classpath:/config/inhouse-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class KPIServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(KPIServiceTest.class);

    @Autowired
    KPIService kpiService;

    @Test
    public void testGetReusedInterfaceCountRateForYear(){

        try{

            Map params = new HashMap();
            List<Map> list = kpiService.getReusedInterfaceCountRateForYear(params);
            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testGetStatusRequirementComplyRate(){
        try{

            Map params = new HashMap();
            params.put("statusId","1");
            List<Map> list = kpiService.getStatusRequirementComplyRate(params);
            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testGetRequirementNotComplyList(){
        try{

            Map params = new HashMap();
            params.put("statusId","1");
            List<Requirement> list = kpiService.getRequirementNotComplyList(params);

            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }

}
