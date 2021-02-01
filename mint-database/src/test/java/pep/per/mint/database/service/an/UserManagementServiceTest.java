package pep.per.mint.database.service.an;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.Interface;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

import java.util.ArrayList;
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
public class UserManagementServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceTest.class);

    @Autowired
    UserManagementService userManagementService;


    @Test
    public void testGetInterfaceUserList(){

        try{
            Map params = new HashMap();

            params.put("onlyRetiredEmp", "Y");
            params.put("userNm", "조");

            List<Map> list = userManagementService.getInterfaceUserList(params);

            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetInterfaceListForUserManagement(){

        try{
            Map params = new HashMap();

            //params.put("userNm", "조");
            //params.put("interfaceId", "L011R0001");
            //params.put("interfaceNm", "민권09046");
            //params.put("systemId", "SS00000001");
            params.put("systemNm", "시스");

            List<Map> list = userManagementService.getInterfaceListForUserManagement(params);

            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testGetInterfaceList(){

        try{
            Map params = new HashMap();

            params.put("userId", "11825");

            List<Map> list = userManagementService.getInterfaceList(params);

            logger.debug(Util.join("list:", Util.toJSONString(list)));

        }catch (Exception e){
            logger.error("",e);
        }
    }




    @Test
    public void testAddInterfaceUsers(){

        try{
            List<Map> interfaceUserList = new ArrayList<Map>();
            Map user = new HashMap();
            user.put("interfaceId", "PQM1D0534");
            user.put("newUserId", "iip");
            user.put("roleType", "0");
            user.put("regId", "iip");
            user.put("regDate", Util.getFormatedDate());
            interfaceUserList.add(user);


            int res = userManagementService.addInterfaceUsers(interfaceUserList);

            logger.debug(Util.join("result:", res));

        }catch (Exception e){
            logger.error("",e);
        }
    }


    @Test
    public void testDeleteInterfaceUsers(){

        try{
            List<Map> interfaceUserList = new ArrayList<Map>();
            Map user = new HashMap();
            user.put("interfaceId", "PQM1D0534");
            user.put("userId", "iip");
            user.put("roleType", "0");
            user.put("regId", "iip");
            user.put("regDate", Util.getFormatedDate());
            interfaceUserList.add(user);


            int res = userManagementService.deleteInterfaceUsers(interfaceUserList);

            logger.debug(Util.join("result:", res));

        }catch (Exception e){
            logger.error("",e);
        }
    }

}
