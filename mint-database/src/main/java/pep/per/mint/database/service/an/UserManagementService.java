package pep.per.mint.database.service.an;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.an.UserManagementMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 20..
 */

@Service
public class UserManagementService {

    @Autowired
    UserManagementMapper userManagementMapper;

    public List<Map> getInterfaceUserList(Map params) throws Exception {
        return userManagementMapper.getInterfaceUserList(params);
    }


    public List<Map> getInterfaceList(Map params) throws Exception{
        return userManagementMapper.getInterfaceList(params);
    }

    @Transactional
    public int moveInterfaceUser(List<Map> list) throws Exception{
        int res = 0;
        for(int i = 0 ; i < list.size() ; i ++){
            Map params = list.get(i);
            res = userManagementMapper.deleteInterfaceUser(params);
            res = userManagementMapper.insertInterfaceUser(params);
        }

        return res;
    }

    public List<Map> getInterfaceListForUserManagement(Map params) throws Exception{
        return userManagementMapper.getInterfaceListForUserManagement(params);
    }


    @Transactional
    public int addInterfaceUsers(List<Map> interfaceUserList) throws Exception{


        int res = 0;
        for(int i = 0 ; i < interfaceUserList.size() ; i ++){
            Map params = interfaceUserList.get(i);
            String userId = (String)params.get("newUserId");
            String interfaceId = (String)params.get("interfaceId");
            String roleType = (String)params.get("roleType");
            String existUser = userManagementMapper.selectInterfaceUser(interfaceId, userId, roleType);
            if(Util.isEmpty(existUser)) {
                res = userManagementMapper.insertInterfaceUser(params);
            }
        }

        return res;
    }


    @Transactional
    public int deleteInterfaceUsers(List<Map> interfaceUserList) throws Exception{
        int res = 0;
        for(int i = 0 ; i < interfaceUserList.size() ; i ++){
            Map params = interfaceUserList.get(i);
            res = userManagementMapper.deleteInterfaceUser(params);
        }

        return res;
    }

}
