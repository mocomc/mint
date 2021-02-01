package pep.per.mint.database.mapper.su;

import pep.per.mint.common.data.basic.auth.ApprovalAuthority;
import pep.per.mint.common.data.basic.auth.ApprovalAuthorityGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 12. 1..
 */
public interface UserRoleManagementMapper {

    public List<Map> getUserRoleList(Map params) throws Exception;

    public List<Map> getChannelUserList(Map params) throws  Exception;

    public int insertChannelUser(Map params) throws Exception;
    public int deleteChannelUser(Map params) throws Exception;
    public int updateChannelUser(Map params) throws Exception;

    public List<Map> getApprovalUserList(Map params) throws  Exception;

    public List<ApprovalAuthorityGroup> getApprovalAuthorityGroupList(Map params) throws Exception;

    public int insertApprovalUser(Map params) throws Exception;
    public int deleteApprovalUser(Map params) throws Exception;
    public int updateApprovalUser(Map params) throws Exception;


    public int insertApprovalAuthorityGroup(ApprovalAuthorityGroup group) throws Exception;
    public int updateApprovalAuthorityGroup(ApprovalAuthorityGroup group) throws Exception;
    public int deleteApprovalAuthorityGroup(ApprovalAuthorityGroup group) throws Exception;



    public int insertApprovalAuthority(ApprovalAuthority authority) throws Exception;
    public int updateApprovalAuthority(ApprovalAuthority authority) throws Exception;
    public int deleteApprovalAuthority(ApprovalAuthority authority) throws Exception;



}
