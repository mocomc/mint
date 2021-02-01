package pep.per.mint.database.service.su;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.auth.ApprovalAuthorityGroup;
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
public class UserRoleManagementServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleManagementServiceTest.class);

    @Autowired
    UserRoleManagementService userRoleManagementService;


    @Test
    public void testGetUserListByUser(){
        try {

            Map params = new HashMap();
            params.put("userNm", "조무흠1");
            List<Map> list = userRoleManagementService.getUserRoleList(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testGetUserListByChannelUser(){
        try {

            Map params = new HashMap();
            params.put("channelId","CN00000001");

            List<Map> list = userRoleManagementService.getChannelUserList(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testGetUserListByApproval(){
        try {

            Map params = new HashMap();
            params.put("approvalRoleType","1");

            List<Map> list = userRoleManagementService.getApprovalUserList(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetApprovalAuthorityGroupList(){
        try {

            Map params = new HashMap();
           // params.put("approvalRoleType","1");

            List<ApprovalAuthorityGroup> list = userRoleManagementService.getApprovalAuthorityGroupList(params);

            logger.debug(Util.join("list:",Util.toJSONString(list)));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAlterChannelUsers(){
        try{
            /**
             user.put("channelId
             user.put("userId
             user.put("seq
             user.put("channelRoleType
             user.put("operationType
             user.put("regDate
             user.put("regId
             user.put("modDate
             user.put("modId
             */
            List<Map> users = new ArrayList<Map>();
            Map user1 = new HashMap();
            user1.put("channelId","CN00000001");
            user1.put("userId","11826");
            user1.put("seq","1");
            user1.put("channelRoleType","1");
            user1.put("operationType","D");
            user1.put("regDate", Util.getFormatedDate());
            user1.put("regId","11826");
            user1.put("modDate",Util.getFormatedDate());
            user1.put("modId","11826");
            users.add(user1);

            Map user2 = new HashMap();
            user2.put("channelId","CN00000001");
            user2.put("userId","11827");
            user2.put("seq","2");
            user2.put("channelRoleType","1");
            user2.put("operationType","D");
            user2.put("regDate", Util.getFormatedDate());
            user2.put("regId","11826");
            user2.put("modDate",Util.getFormatedDate());
            user2.put("modId","11826");
            users.add(user2);


            userRoleManagementService.alterChannelUsers(users);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
