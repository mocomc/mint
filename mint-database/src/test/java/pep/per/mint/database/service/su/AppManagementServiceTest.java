package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Tooltip;
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
public class AppManagementServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AppManagementServiceTest.class);

    @Autowired
    AppManagementService appManagementService;


    @Test
    public void testGetMessages(){

        try{
            Map params = new HashMap();
            params.put("langId","KO");
            Map<String,String> map = appManagementService.getMessages(params);

            logger.debug(Util.join("map:" , Util.toJSONString(map)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testGetMessagesByLangId(){

        try{
            Map params = new HashMap();
            params.put("langId","en");
            Map<String,Map<String,String>> map = appManagementService.getMessagesByLangId(params);

            logger.debug(Util.join("map:" , Util.toJSONString(map)));

        }catch (Exception e){
            logger.error("",e);
        }
    }




}
