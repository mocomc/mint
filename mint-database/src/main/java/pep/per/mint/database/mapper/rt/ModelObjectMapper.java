/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.mapper.rt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.runtime.AppModelAttributeCode;
import pep.per.mint.common.data.basic.runtime.AppModelAttributeId;
import pep.per.mint.common.data.basic.runtime.InterfaceModelObject;
import pep.per.mint.common.data.basic.runtime.MapModelObject;
import pep.per.mint.common.data.basic.runtime.MsgModelObject;

/**
 * @author iip
 * @since Jul 9, 2020
 */
public interface ModelObjectMapper {

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public InterfaceModelObject getSimpleInterfaceModel(Map<String, String> params) throws Exception;

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MsgModelObject> getMsgModelList(Map params) throws Exception;

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MapModelObject> getMapModelList(Map params) throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int createMsgModel(MsgModelObject object) throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int createMapModel(MapModelObject object) throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int updateMsgModel(MsgModelObject object)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int updateMapModel(MapModelObject object)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int removeMsgModel(MsgModelObject object)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int removeMapModel(MapModelObject object)  throws Exception;


	/**
	 * @param object
	 * @return
	 */
	public int deleteMsgModel(@Param("interfaceMid") String interfaceMid, @Param("appMid") String appMid)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int deleteMsgModelByMid(MsgModelObject object)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int deleteMapModel(@Param("interfaceMid") String interfaceMid, @Param("appMid") String appMid)  throws Exception;

	/**
	 * @param object
	 * @return
	 */
	public int deleteMapModelByMid(MapModelObject object)  throws Exception;
}
