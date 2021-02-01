/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.rt;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.runtime.AppModelObject;
import pep.per.mint.common.data.basic.runtime.InterfaceModelObject;
import pep.per.mint.common.data.basic.runtime.MapModelObject;
import pep.per.mint.common.data.basic.runtime.MsgModelObject;

import pep.per.mint.database.mapper.rt.ModelObjectMapper;

/**
 * @author iip
 * @since Jul 9, 2020
 */
@Service
public class ModelObjectService {

	private static final Logger logger = LoggerFactory.getLogger(ModelObjectService.class);

	@Autowired
	ModelObjectMapper modelObjectMapper;


	/**
	 * Simple InterfaceModel
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public InterfaceModelObject getSimpleInterfaceModel(Map<String,String> params) throws Exception {
		InterfaceModelObject result = modelObjectMapper.getSimpleInterfaceModel(params);
		return result;
	}

	/**
	 * Msg & Map Model Update
	 * @param interfaceModel
	 * @throws Exception
	 */
	@Transactional
	public void updateMsgMapModel(InterfaceModelObject interfaceModel) throws Exception {

		List<AppModelObject> appModelList = interfaceModel.getAppModelList();

		for(AppModelObject appModel : appModelList) {
			//---------------------------------------------------------
			// MsgModel delete/create
			//---------------------------------------------------------
			{
				List<MsgModelObject> msgModelList = appModel.getMsgModelList();
				deleteMsgModel(interfaceModel.getInterfaceMid(), appModel.getAppMid());
				createMsgModel(msgModelList, interfaceModel.getRegDate(), interfaceModel.getRegId());
			}

			//---------------------------------------------------------
			// MapModel delete/create
			//---------------------------------------------------------
			{
				List<MapModelObject> mapModelList = appModel.getMapModelList();
				deleteMapModel(interfaceModel.getInterfaceMid(), appModel.getAppMid());
				createMapModel(mapModelList, interfaceModel.getRegDate(), interfaceModel.getRegId());
			}
		}
	}

	/**
	 * MsgModel Create
	 * @param modelObjectList
	 * @param regDate
	 * @param regId
	 * @throws Exception
	 */
	@Transactional
	public void createMsgModel(List<MsgModelObject> modelObjectList, String regDate, String regId) throws Exception {
		for(MsgModelObject  modelObject : modelObjectList) {
			modelObject.setRegDate(regDate);
			modelObject.setRegId(regId);
			modelObject.setModDate(regDate);
			modelObject.setModId(regId);
			int res = modelObjectMapper.createMsgModel(modelObject);
		}
	}

	/**
	 * MapModel Create
	 * @param modelObjectList
	 * @param regDate
	 * @param regId
	 * @throws Exception
	 */
	@Transactional
	public void createMapModel(List<MapModelObject> modelObjectList, String regDate, String regId) throws Exception {
		for(MapModelObject  modelObject : modelObjectList) {
			modelObject.setRegDate(regDate);
			modelObject.setRegId(regId);
			modelObject.setModDate(regDate);
			modelObject.setModId(regId);
			int res = modelObjectMapper.createMapModel(modelObject);
		}
	}

	/**
	 * MsgModel Delete
	 * @param interfaceMid
	 * @param appMid
	 * @throws Exception
	 */
	@Transactional
	public void deleteMsgModel(String interfaceMid, String appMid)  throws Exception {
		modelObjectMapper.deleteMsgModel(interfaceMid, appMid);
	}

	/**
	 * MsgModel Delete
	 * @param modelList
	 * @throws Exception
	 */
	@Transactional
	public void deleteMsgModelByMid(List<MsgModelObject> modelList)  throws Exception {
		for(MsgModelObject model : modelList) {
			modelObjectMapper.deleteMsgModelByMid(model);
		}
	}

	/**
	 * MapModel Delete
	 * @param interfaceMid
	 * @param appMid
	 * @throws Exception
	 */
	@Transactional
	public void deleteMapModel(String interfaceMid, String appMid)  throws Exception {
		modelObjectMapper.deleteMapModel(interfaceMid, appMid);
	}


	/**
	 * MapModel Delete
	 * @param modelList
	 * @throws Exception
	 */
	@Transactional
	public void deleteMapModelByMid(List<MapModelObject> modelList)  throws Exception {
		for(MapModelObject model : modelList) {
			modelObjectMapper.deleteMapModelByMid(model);
		}
	}

}
