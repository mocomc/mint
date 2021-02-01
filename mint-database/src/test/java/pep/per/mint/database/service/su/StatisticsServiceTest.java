package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.statistics.SubjectStatus;
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
public class StatisticsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceTest.class);

    @Autowired
    StatisticsService statisticsService;

    @Test
    public void testGetSubjectStatusByChannel(){
        try{
            Map params = new HashMap();
            params.put("channelId","CN00000002");
            List<SubjectStatus> list = statisticsService.getSubjectStatusGroupByChannel(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetSubjectStatusBySystem(){
        try{
            Map params = new HashMap();
            params.put("systemId","SS00000008");
            List<SubjectStatus> list = statisticsService.getSubjectStatusGroupBySystem(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetSubjectStatusByResource(){
        try{
            Map params = new HashMap();
            params.put("resourceType","0");
            List<SubjectStatus> list = statisticsService.getSubjectStatusGroupByResource(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testGetSubjectStatusByUser(){
        try{
            Map params = new HashMap();
            params.put("userNm","조");
            List<SubjectStatus> list = statisticsService.getSubjectStatusGroupByUser(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetSubjectStatusDetail(){
        try{

            Map params = new HashMap();
            params.put("status","7");
            //params.put("channelId","CN00000002");

            List<Requirement> list = statisticsService.getSubjectStatusDetail(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetDevelopmentDailyStatusByChannel(){

        try{
            Map<String,String> params = new HashMap<String, String>();
            params.put("startDate", "20151001");
            params.put("endDate", "20151031");
            params.put("channelId", "CN00000001");

            List<Map> list = statisticsService.getDevelopmentDailyStatusByChannel(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetDevelopmentDailyStatusByProvider(){

        try{
            Map<String,String> params = new HashMap<String, String>();
            params.put("startDate", "20151001");
            params.put("endDate", "20151031");
            params.put("systemId", "SS00000001");
            List<Map> list = statisticsService.getDevelopmentDailyStatusByProvider(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetDevelopmentDailyStatusByResource(){

        try{

            Map<String,String> params = new HashMap<String, String>();
            params.put("startDate", "20151001");
            params.put("endDate", "20151031");
            params.put("resourceCd", "0");
            List<Map> list = statisticsService.getDevelopmentDailyStatusByResource(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testGetDevelopmentDailyStatusList(){

        try{
            Map<String,String> params = new HashMap<String, String>();
            params.put("stDate", "20151022");
            //params.put("channelId", "CN00000001");
            params.put("resourceCd", "0");
            //params.put("systemId", "SS00000001");
            List<Requirement> list = statisticsService.getDevelopmentDailyStatusList(params);
            logger.debug(Util.join("list\n", Util.toJSONString(list)));
        }catch (Exception e){
            logger.error("",e);
        }
    }

}
