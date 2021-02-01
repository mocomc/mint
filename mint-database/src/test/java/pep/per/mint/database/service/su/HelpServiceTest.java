package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Requirement;
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
public class HelpServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(HelpServiceTest.class);

    @Autowired
    HelpService helpService;


    @Test
    public void testInsertAndGetHelp(){

        try{

            Help help = new Help();
            help.setAppId("AP00000001");
            help.setHelpId("1");
            help.setLangId("utf-8");
            help.setSubject("인터페이스조회 도움말");
            help.setContents("<html>인터페이스조회 도움말</html>");
            help.setRegDate(Util.getFormatedDate());
            help.setRegId("11825");
            //int res = helpService.createHelp(help);

            help = helpService.getHelp(help.getAppId(), help.getHelpId(),"ko");

            logger.debug(Util.join("help:", Util.toJSONString(help)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testInsertAndGetTooltip(){

        try{

            Tooltip tooltip = new Tooltip();
            tooltip.setAppId("AP00000001");
            tooltip.setTooltipId("3");
            tooltip.setLangId("utf-8");
            tooltip.setTargetId("Grig1");
            tooltip.setFilterId("thead");
            tooltip.setSubject("인터페이스조회-시스템 툴팁");
            tooltip.setContents("<html>인터페이스조회 시스템 툴팀</html>");
            tooltip.setRegDate(Util.getFormatedDate());
            tooltip.setRegId("11825");
            int res = helpService.createTooltip(tooltip);

            tooltip = helpService.getTooltip(tooltip.getAppId(), tooltip.getTooltipId());

            logger.debug(Util.join("tooltip:", Util.toJSONString(tooltip)));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testExportHelp(){

        try{

            List<Map> list = helpService.exportHelp();

            for (int i = 0 ; i < list.size() ; i ++){
                Map el = list.get(i);
                String fileId = (String)el.get("fileId");
                String contents = (String)el.get("contents");
                logger.debug(Util.join("fileId:",fileId, "contents", contents));
            }


        }catch (Exception e){
            logger.error("",e);
        }
    }





}
