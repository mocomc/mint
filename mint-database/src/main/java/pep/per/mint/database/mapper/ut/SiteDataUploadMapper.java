/**
 *
 */
package pep.per.mint.database.mapper.ut;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.BusinessPath;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.ChannelAttribute;
import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.DataAccessRolePath;
import pep.per.mint.common.data.basic.InterfaceAdditionalAttribute;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.OrganizationPath;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.SystemPath;
import pep.per.mint.common.data.basic.User;


/**
 * @author TA
 *
 */
public interface SiteDataUploadMapper {

	public int deleteTCO0103(Map<String,Object> params) throws Exception;
	public int deleteTCO0102(Map<String,Object> params) throws Exception;
	public int deleteTCO0101(Map<String,Object> params) throws Exception;
	public int deleteTSU0220(Map<String,Object> params) throws Exception;
	public int deleteTSU0803(Map<String,Object> params) throws Exception;
	public int deleteTOP0401(Map<String,Object> params) throws Exception;
	public int deleteTAN0227(Map<String,Object> params) throws Exception;
	public int deleteTAN0226(Map<String,Object> params) throws Exception;
	public int deleteTAN0327(Map<String,Object> params) throws Exception;
	public int deleteTAN0111(Map<String,Object> params) throws Exception;
	public int deleteTAN0109(Map<String,Object> params) throws Exception;
	public int deleteTAN0103(Map<String,Object> params) throws Exception;
	public int deleteTAN0107(Map<String,Object> params) throws Exception;
	public int deleteTAN0108(Map<String,Object> params) throws Exception;
	public int deleteTAN0102(Map<String,Object> params) throws Exception;
	public int deleteTAN0101(Map<String,Object> params) throws Exception;
	public int deleteTAN0214(Map<String,Object> params) throws Exception;
	public int deleteTAN0205(Map<String,Object> params) throws Exception;
	public int deleteTAN0322(Map<String,Object> params) throws Exception;
	public int deleteTAN0323(Map<String,Object> params) throws Exception;
	public int deleteTAN0324(Map<String,Object> params) throws Exception;
	public int deleteTAN0325(Map<String,Object> params) throws Exception;
	public int deleteTAN0219(Map<String,Object> params) throws Exception;
	public int deleteTAN0218(Map<String,Object> params) throws Exception;
	public int deleteTAN0213(Map<String,Object> params) throws Exception;
	public int deleteTAN0203(Map<String,Object> params) throws Exception;
	public int deleteTAN0202(Map<String,Object> params) throws Exception;
	public int deleteTAN0201(Map<String,Object> params) throws Exception;


	public int deleteTSU0221(Map<String,Object> params) throws Exception;
	public int deleteTSU0222(Map<String,Object> params) throws Exception;
	public int deleteTSU0219(Map<String,Object> params) throws Exception;
	public int deleteTSU0218(Map<String,Object> params) throws Exception;
	public int deleteTAN0204(Map<String,Object> params) throws Exception;
	public int deleteTIM0003(Map<String,Object> params) throws Exception;
	public int deleteTIM0002(Map<String,Object> params) throws Exception;
	public int deleteTIM0404(Map<String,Object> params) throws Exception;
	public int deleteTIM0402(Map<String,Object> params) throws Exception;
	public int deleteTIM0403(Map<String,Object> params) throws Exception;
	public int deleteTIM0401(Map<String,Object> params) throws Exception;
	public int deleteTOP0807(Map<String,Object> params) throws Exception;
	public int deleteTIM0208(Map<String,Object> params) throws Exception;
	public int deleteTOP0806(Map<String,Object> params) throws Exception;
	public int deleteTIM0207(Map<String,Object> params) throws Exception;
	public int deleteTOP0805(Map<String,Object> params) throws Exception;
	public int deleteTIM0206(Map<String,Object> params) throws Exception;
	public int deleteTOP0802(Map<String,Object> params) throws Exception;
	public int deleteTIM0205(Map<String,Object> params) throws Exception;
	public int deleteTOP0801(Map<String,Object> params) throws Exception;
	public int deleteTIM0204(Map<String,Object> params) throws Exception;
	public int deleteTIM0213(Map<String,Object> params) throws Exception;
	public int deleteTIM0214(Map<String,Object> params) throws Exception;
	public int deleteTOP0810(Map<String,Object> params) throws Exception;
	public int deleteTIM0212(Map<String,Object> params) throws Exception;
	public int deleteTIM0211(Map<String,Object> params) throws Exception;
	public int deleteTOP0808(Map<String,Object> params) throws Exception;
	public int deleteTIM0210(Map<String,Object> params) throws Exception;
	public int deleteTOP0809(Map<String,Object> params) throws Exception;
	public int deleteTIM0209(Map<String,Object> params) throws Exception;
	public int deleteTIM0203(Map<String,Object> params) throws Exception;
	public int deleteTIM0202(Map<String,Object> params) throws Exception;
	public int deleteTIM0201(Map<String,Object> params) throws Exception;
	public int deleteTIM0102(Map<String,Object> params) throws Exception;
	public int deleteTIM0105(Map<String,Object> params) throws Exception;
	public int deleteTIM0101(Map<String,Object> params) throws Exception;
	public int deleteTIM0304(Map<String,Object> params) throws Exception;
	public int deleteTIM0302(Map<String,Object> params) throws Exception;
	public int deleteTIM0301(Map<String,Object> params) throws Exception;
	public int deleteTOP0902(Map<String,Object> params) throws Exception;
	public int deleteTSU0101(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getChannelIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getOrganizationIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getSystemIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getServerIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getBusinessIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getUserIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getUserRoleIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getDataAccessRoleIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getDataAccessRoleParentList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getAdditionalAttributeIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getChannelAttributeIdList(Map<String,Object> params) throws Exception;

	public List<Map<String,String>> getCommonCodeList(@Param("level1") String level1, @Param("level2")  String level2) throws Exception;

	public String getIntegrationId(@Param("integrationId") String integrationId) throws Exception;

	public int insertChannel(Channel channel) throws Exception;

	public int insertChannelAttribute(ChannelAttribute attrObject) throws Exception;



	public int insertAdditionalAttribute(InterfaceAdditionalAttribute attrObject) throws Exception;



	public int insertOrganization(Organization org) throws Exception;

	public int updateOrganization(Organization org) throws Exception;

	public int insertOrganizationPath(OrganizationPath orgPath) throws Exception;

	public int createOrganizationPathRelation(Map params) throws Exception;



	public int insertSystem(pep.per.mint.common.data.basic.System system) throws Exception;

	public int updateSystem(System system) throws  Exception;

	public int insertSystemPath(SystemPath systemPath) throws Exception;

	public int createSystemPathRelation(Map params) throws Exception;



	public int insertOrganizationSystemMap(@Param("organizationId")String organizationId, @Param("system")System system) throws Exception;



	public int insertServer(Server server) throws Exception;



	public int insertSystemServerMap(@Param("systemId")String systemId, @Param("server")Server server) throws Exception;



	public int insertBusiness(Business business) throws Exception;

	public int updateBusiness(Business business) throws  Exception;

	public int insertBusinessPath(BusinessPath orgPath) throws Exception;

	public int createBusinessPathRelation(Map params) throws Exception;



	public int insertUser(User user) throws Exception;




	public int insertDataAccessRole(DataAccessRole accessRole) throws Exception;

	public int updateDataAccessRole(DataAccessRole accessRole) throws  Exception;

	public int insertDataAccessRolePath(DataAccessRolePath pPath)throws Exception;

	public int createDataAccessRolePathRelation(Map params)throws Exception;



	public int insertDataAccessRoleSystemMap(@Param("roleId")String roleId, @Param("system")System system)throws Exception;

	public int insertDataAccessRoleUserMap(@Param("roleId")String  roleId, @Param("relUser")RelUser relUser)throws Exception;



}
