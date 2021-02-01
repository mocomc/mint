/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.rt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppModelAttribute;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeCode;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.AppType;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModelHistory;
import pep.per.mint.common.data.basic.runtime.MappingModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.data.basic.runtime.ModelDeployment;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
public interface ModelMapper {
	
	public List<AppModelAttributeId> getAppModelAttributeIdList(Map params) throws Exception;

	/**
	 * @param attr
	 * @return
	 */
	public int createAttribute(AppModelAttributeId attr) throws Exception;
 
	/**
	 * @param attr
	 * @return
	 */
	public int updateAttribute(AppModelAttributeId attr)  throws Exception;

	/**
	 * 
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public int deleteAttribute(AppModelAttributeId attr)  throws Exception;
	
	/**
	 * 
	 * @param appType
	 * @param aid
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public int createAttributeCode(@Param("appType") String appType, @Param("aid") String aid, @Param("cd") AppModelAttributeCode cd) throws Exception;

	 
	/**
	 * 
	 * @param appType
	 * @param aid
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public int updateAttributeCode(@Param("appType") String appType, @Param("aid") String aid, @Param("cd") AppModelAttributeCode cd) throws Exception;
	
	/**
	 * 
	 * @param appType
	 * @param aid
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public int deleteAttributeCode(@Param("appType") String appType, @Param("aid") String aid, @Param("cd") AppModelAttributeCode cd) throws Exception;

	/**
	 * @param appType
	 * @param aid
	 * @param cd
	 * @return
	 */
	public int existAttributeCode(@Param("appType") String appType, @Param("aid") String aid, @Param("cd") String cd) throws Exception;

	/**
	 * @param appType
	 * @param aid
	 */
	public void deleteAllAttributeCode(@Param("appType") String appType, @Param("aid") String aid, @Param("modDate") String modDate, @Param("modId") String modId) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public List<AppModelAttributeCode> getAppModelAttributeCodeList(Map params) throws Exception;

	/**
	 * @param regDate
	 * @param regId
	 */
	public void resetAttributeCodes(String regDate, String regId) throws Exception;

	/**
	 * @param regDate
	 * @param regId
	 */
	public void resetAttributes(String regDate, String regId) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public List<AppModel> newAppModels(Map params) throws Exception;

	/**
	 * @param interfaceModel
	 */
	public void insertInterfaceModel(InterfaceModel interfaceModel) throws Exception;

	/**
	 * @param appModel
	 */
	public void insertAppModel(AppModel appModel) throws Exception;

	/**
	 * @param attribute
	 */
	public void insertAppModelAttribute(AppModelAttribute attribute) throws Exception;

	/**
	 * @param messageModel
	 */
	public void insertMessageModel(MessageModel messageModel) throws Exception;

	/**
	 * @param mappingModel
	 */
	public void insertMappingModel(MappingModel mappingModel) throws Exception;

	/**
	 * @param interfaceModel
	 */
	public void updateInterfaceModel(InterfaceModel interfaceModel) throws Exception;

	/**
	 * @param interfaceMid
	 */
	public void deleteAppModelAttribute(@Param("interfaceMid") String interfaceMid, @Param("modDate") String modDate, @Param("modId") String modId) throws Exception;

	/**
	 * @param interfaceMid
	 */
	public void deleteAppModel(@Param("interfaceMid") String interfaceMid, @Param("modDate") String modDate, @Param("modId") String modId) throws Exception;

	/**
	 * @param interfaceMid
	 */
	public void deleteMessageModel(@Param("interfaceMid")String interfaceMid, @Param("modDate") String modDate, @Param("modId") String modId) throws Exception;

	/**
	 * @param interfaceMid
	 */
	public void deleteMappingModel(@Param("interfaceMid") String interfaceMid, @Param("modDate") String modDate, @Param("modId") String modId) throws Exception;

	/**
	 * @param interfaceMid
	 * @param modDate
	 * @param modId
	 */
	public void deleteInterfaceModel(@Param("interfaceMid") String interfaceMid, @Param("modDate") String modDate, @Param("modId") String modId)throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public List<InterfaceModel> getInterfaceModelList(Map params) throws Exception;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<AppModelAttribute> getAppModelAttributes(@Param("interfaceMid") String interfaceMid, @Param("appMid") String appMid) throws Exception;

	/**
	 * @param interfaceMid
	 * @param appMid
	 * @return
	 */
	public List<MessageModel> getMessageModels(@Param("interfaceMid") String interfaceMid, @Param("appMid") String appMid) throws Exception;

	/**
	 * @param interfaceMid
	 * @param appMid
	 * @return
	 */
	public List<MappingModel> getMappingModels(@Param("interfaceMid") String interfaceMid, @Param("appMid") String appMid) throws Exception;

	/**
	 * @param interfaceModelHistory
	 */
	public int insertInterfaceModelHistory(InterfaceModelHistory interfaceModelHistory) throws Exception;

	/**
	 * 
	 * @param interfaceMid
	 * @return
	 * @throws Exception
	 */
	public List<InterfaceModelHistory> getInterfaceModelHistory(@Param("mid") String interfaceMid)throws Exception;
	
	/**
	 * 
	 * @param resourceCd
	 * @return
	 * @throws Exception
	 */
	public List<AppType> getAppTypes(@Param("resourceCd") String resourceCd) throws Exception;

	/**
	 * @param appTypeCd
	 * @return
	 */
	public AppType getAppType(@Param("appType") String appTypeCd)throws Exception;

	/**
	 * @param md
	 */
	public void createModelDeployment(ModelDeployment modelDeployment)throws Exception;
	
	
}
