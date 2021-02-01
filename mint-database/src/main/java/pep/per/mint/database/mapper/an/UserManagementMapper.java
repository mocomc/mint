package pep.per.mint.database.mapper.an;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 16..
 */
public interface UserManagementMapper {

    public List<Map> getInterfaceUserList(Map params) throws Exception;

    public List<Map> getInterfaceList(Map params) throws Exception;

    public int deleteInterfaceUser(Map params) throws Exception;

    public int insertInterfaceUser(Map params) throws Exception;

    public List<Map> getInterfaceListForUserManagement(Map params) throws Exception;

    public String selectInterfaceUser(@Param("interfaceId")String interfaceId, @Param("userId")String userId, @Param("roleType") String roleType) throws Exception;

}
