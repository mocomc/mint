package pep.per.mint.database.service.su;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.auth.ApprovalAuthority;
import pep.per.mint.common.data.basic.auth.ApprovalAuthorityGroup;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.DatabaseServiceException;
import pep.per.mint.database.mapper.su.UserRoleManagementMapper;

import java.util.List;
import java.util.Map;


/**
 * 통계 서비스
 * @author isjang
 *
 */

@Service
public class UserRoleManagementService {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleManagementService.class);
	
	@Autowired
	UserRoleManagementMapper userRoleManagementMapper;


	public List<Map> getUserRoleList(Map params) throws Exception{
		return userRoleManagementMapper.getUserRoleList(params);
	}


	public List<Map> getChannelUserList(Map params) throws Exception{
		return userRoleManagementMapper.getChannelUserList(params);
	}

	@Transactional
	public int addChannelUsers(List<Map> channelUsers) throws Exception{
		int res = 0;
		for(Map channelUser : channelUsers){
			res = userRoleManagementMapper.insertChannelUser(channelUser);
		}
		return res;
	}

	@Transactional
	public int updateChannelUser(Map channelUser) throws Exception{
		return userRoleManagementMapper.updateChannelUser(channelUser);
	}

	@Transactional
	public int deleteChannelUser(Map channelUser) throws Exception{
		return userRoleManagementMapper.deleteChannelUser(channelUser);
	}


	public List<Map> getApprovalUserList(Map params) throws  Exception{
		return userRoleManagementMapper.getApprovalUserList(params);
	}

	@Transactional
	public int addApprovalUsers(List<Map> approvalUsers) throws Exception{
		int res = 0;
		for(Map approvalUser : approvalUsers){
			res = userRoleManagementMapper.insertApprovalUser(approvalUser);
		}
		return res;
	}



	@Transactional
	public int updateApprovalUser(Map approvalUser) throws Exception{
		return userRoleManagementMapper.updateApprovalUser(approvalUser);
	}

	@Transactional
	public int deleteApprovalUser(Map approvalUser) throws Exception{
		return userRoleManagementMapper.deleteApprovalUser(approvalUser);
	}

	final static String OP_TYPE_CREATE = "C";
	final static String OP_TYPE_UPDATE = "U";
	final static String OP_TYPE_DELETE = "D";

	@Transactional
	public int alterApprovalUsers(List<Map> approvalUsers) throws Exception{
		int res = 0;
		for(Map approvalUser : approvalUsers){

			String operationType = (String) approvalUser.get("operationType");

			if(OP_TYPE_CREATE.equalsIgnoreCase(operationType)){
				res = userRoleManagementMapper.insertApprovalUser(approvalUser);
			}else if(OP_TYPE_DELETE.equalsIgnoreCase(operationType)){
				res = userRoleManagementMapper.deleteApprovalUser(approvalUser);
			}else{
				throw new DatabaseServiceException(Util.join("Exception:UserRoleManagementService.alterApprovalUsers:지원하지 않는 operationType 입니다.(operationType:",operationType,")"));
			}
		}
		return res;
	}


	@Transactional
	public int alterChannelUsers(List<Map> channelUsers) throws Exception{
		int res = 0;
		for(Map channelUser : channelUsers){

			String operationType = (String) channelUser.get("operationType");

			if(OP_TYPE_CREATE.equalsIgnoreCase(operationType)){
				res = userRoleManagementMapper.insertChannelUser(channelUser);
			}else if(OP_TYPE_UPDATE.equalsIgnoreCase(operationType)){
				res = userRoleManagementMapper.updateChannelUser(channelUser);
			}else if(OP_TYPE_DELETE.equalsIgnoreCase(operationType)){
				res = userRoleManagementMapper.deleteChannelUser(channelUser);
			}else{
				throw new DatabaseServiceException(Util.join("Exception:UserRoleManagementService.alterChannelUsers:지원하지 않는 operationType 입니다.(operationType:",operationType,")"));
			}
		}
		return res;
	}


	public List<ApprovalAuthorityGroup> getApprovalAuthorityGroupList(Map params) throws Exception{
		return userRoleManagementMapper.getApprovalAuthorityGroupList(params);
	}

	@Transactional
	public int alterApprovalAuthorityGroup(List<ApprovalAuthorityGroup> approvalAuthorityGroups) throws Exception{
		int res = 0;
		for(ApprovalAuthorityGroup approvalAuthorityGroup : approvalAuthorityGroups) {
			String operationType = (String) approvalAuthorityGroup.getOperationType();

			if (OP_TYPE_CREATE.equalsIgnoreCase(operationType)) {
				res = userRoleManagementMapper.insertApprovalAuthorityGroup(approvalAuthorityGroup);
			} else if (OP_TYPE_UPDATE.equalsIgnoreCase(operationType)) {
				res = userRoleManagementMapper.updateApprovalAuthorityGroup(approvalAuthorityGroup);
			} else if (OP_TYPE_DELETE.equalsIgnoreCase(operationType)) {
				res = userRoleManagementMapper.deleteApprovalAuthorityGroup(approvalAuthorityGroup);
			} else {
				throw new DatabaseServiceException(Util.join("Exception:UserRoleManagementService.alterApprovalAuthorityGroup:지원하지 않는 operationType 입니다.(operationType:", operationType, ")"));
			}

			/*List<ApprovalAuthority> authorityList = approvalAuthorityGroup.getAuthorityList();
			for (ApprovalAuthority authority : authorityList){
				alterApprovalAuthority(authority);
			}*/
		}

		return res;
	}

	@Transactional
	public int alterApprovalAuthorityList(List<ApprovalAuthority> authorityList) throws Exception {
		int res = 0 ;
		for (ApprovalAuthority authority : authorityList){
			res = alterApprovalAuthority(authority);
		}
		return res;
	}

	@Transactional
	private int alterApprovalAuthority(ApprovalAuthority authority) throws Exception {
		int res = 0;
		String operationType = (String) authority.getOperationType();

		if(OP_TYPE_CREATE.equalsIgnoreCase(operationType)){
			res = userRoleManagementMapper.insertApprovalAuthority(authority);

		} else if(OP_TYPE_UPDATE.equalsIgnoreCase(operationType)){
			res = userRoleManagementMapper.updateApprovalAuthority(authority);

		} else if(OP_TYPE_DELETE.equalsIgnoreCase(operationType)){
			res = userRoleManagementMapper.deleteApprovalAuthority(authority);

		} else {
			throw new DatabaseServiceException(Util.join("Exception:UserRoleManagementService.alterApprovalAuthority:지원하지 않는 operationType 입니다.(operationType:",operationType,")"));
		}

		return res;
	}

}
