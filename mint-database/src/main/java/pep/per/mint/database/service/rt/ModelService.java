/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.database.service.rt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.rt.ModelMapper;

/**
 * @author whoana
 * @since Jul 9, 2020
 */
@Service
public class ModelService {

	private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

	@Autowired
	ModelMapper modelMapper;

	/**
	 * <pre>
	 * APP모델속성맵 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<AppModelAttributeId>> getAppModelAttributeIdMap(Map params) throws Exception {
		Map<String,List<AppModelAttributeId>> map = null;

		List<AppModelAttributeId> list = modelMapper.getAppModelAttributeIdList(params);
		if(!Util.isEmpty(list)) {

			map = new HashMap<String,List<AppModelAttributeId>>();

			for(AppModelAttributeId attributeId : list) {
				String appType = attributeId.getAppType();
				if(!map.containsKey(appType)) {
					List<AppModelAttributeId> attributeIds = new ArrayList<AppModelAttributeId>();
					map.put(appType, attributeIds);
				}
				map.get(appType).add(attributeId);
			}
		}

		return map;
	}

	/**
	 * <pre>
	 * APP모델속성리스트 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<AppModelAttributeId> getAppModelAttributeIdList(Map params) throws Exception {
		List<AppModelAttributeId> list = modelMapper.getAppModelAttributeIdList(params);
		return list;
	}

	/**
	 * <pre>
	 * APP모델속성 등록 서비스
	 * </pre>
	 * @param attr
	 * @param regDate
	 * @param regId
	 * @throws Exception
	 */
	@Transactional
	public void createAttribute(AppModelAttributeId attr, String regDate, String regId) throws Exception {

		attr.setRegDate(regDate);
		attr.setRegId(regId);
		int res = modelMapper.createAttribute(attr);
		List<AppModelAttributeCode> cds = attr.getCds();
		if(!Util.isEmpty(cds)) {
			String aid 		= attr.getAid();
			String appType 	= attr.getAppType();
			for(AppModelAttributeCode cd : cds) {
				cd.setRegDate(regDate);
				cd.setRegId(regId);
				modelMapper.createAttributeCode(appType, aid, cd);
			}
		}

	}


	/**
	 * <pre>
	 * APP모델속성 LIST 등록 서비스
	 * </pre>
	 * @param req
	 * @param regDate
	 * @param regId
	 * @throws Exception
	 */
	@Transactional
	public void createAttributes(List<AppModelAttributeId> req, String regDate, String regId) throws Exception {
		for(AppModelAttributeId  attr : req) {
			attr.setRegDate(regDate);
			attr.setRegId(regId);
			int res = modelMapper.createAttribute(attr);
			List<AppModelAttributeCode> cds = attr.getCds();
			if(!Util.isEmpty(cds)) {
				String aid 		= attr.getAid();
				String appType 	= attr.getAppType();
				for(AppModelAttributeCode cd : cds) {
					cd.setRegDate(regDate);
					cd.setRegId(regId);
					modelMapper.createAttributeCode(appType, aid, cd);
				}
			}
		}
	}


	/**
	 * <pre>
	 * 업데이트 APP 모델 속성 순번
	 * </pre>
	 * @param req
	 * @param modDate
	 * @param modId
	 * @throws Exception
	 */
	@Transactional
	public void updateAttributes(List<AppModelAttributeId> req, String modDate, String modId)  throws Exception {
		for(AppModelAttributeId  attr : req) {
			attr.setModDate(modDate);
			attr.setModId(modId);
			int res = modelMapper.updateAttribute(attr);
		}
	}

	/**
	 * <pre>
	 * 업데이트 APP 모델 속성
	 * </pre>
	 * @param attr
	 * @param modDate
	 * @param modId
	 * @throws Exception
	 */
	@Transactional
	public void updateAttribute(AppModelAttributeId attr, String modDate, String modId)  throws Exception {

		attr.setModDate(modDate);
		attr.setModId(modId);
		int res = modelMapper.updateAttribute(attr);

		List<AppModelAttributeCode> cds = attr.getCds();

		String inputType= attr.getInputType();
		if(AppModelAttributeId.INPUT_TYPE_COMBO.equals(inputType)) {
			String aid 		= attr.getAid();
			String appType 	= attr.getAppType();

			modelMapper.deleteAllAttributeCode(appType, aid, modDate, modId);

			if(!Util.isEmpty(cds)) {

				for(AppModelAttributeCode cd : cds) {
					cd.setRegDate(modDate);
					cd.setRegId(modId);
					modelMapper.createAttributeCode(appType, aid, cd);
				}
			}

		}
	}


	/**
	 * <pre>
	 * 업데이트 APP모델속성
	 * </pre>
	 * @param req
	 * @param modDate
	 * @param modId
	 * @throws Exception
	 * @deprecated
	 */
	@Transactional
	public void updateAttributesBackup(List<AppModelAttributeId> req, String modDate, String modId)  throws Exception {
		for(AppModelAttributeId  attr : req) {
			attr.setModDate(modDate);
			attr.setModId(modId);
			int res = modelMapper.updateAttribute(attr);
			List<AppModelAttributeCode> cds = attr.getCds();
			if(!Util.isEmpty(cds)) {
				String aid 		= attr.getAid();
				String appType 	= attr.getAppType();
				for(AppModelAttributeCode cd : cds) {
					if(modelMapper.existAttributeCode(appType, aid, cd.getAttrCd()) > 0) {
						cd.setModDate(modDate);
						cd.setModId(modId);
						modelMapper.updateAttributeCode(appType, aid, cd);
					}else {
						cd.setRegDate(modDate);
						cd.setRegId(modId);
						modelMapper.createAttributeCode(appType, aid, cd);
					}
				}
			}
		}
	}

	/**
	 * <pre>
	 * APP모델속성 삭제
	 * </pre>
	 * @param req
	 * @param modDate
	 * @param modId
	 * @throws Exception
	 */
	@Transactional
	public void deleteAttributes(List<AppModelAttributeId> req, String modDate, String modId)  throws Exception {
		for(AppModelAttributeId  attr : req) {
			attr.setModDate(modDate);
			attr.setModId(modId);
			int res = modelMapper.deleteAttribute(attr);
			List<AppModelAttributeCode> cds = attr.getCds();
			if(!Util.isEmpty(cds)) {
				String aid 		= attr.getAid();
				String appType 	= attr.getAppType();
				modelMapper.deleteAllAttributeCode(appType, aid, modDate, modId);
			}
		}
	}

	/**
	 * <pre>
	 * APP모델속성코드 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<AppModelAttributeCode> getAppModelAttributeCodeList(Map params) throws Exception {
		List<AppModelAttributeCode> list = modelMapper.getAppModelAttributeCodeList(params);
		return list;
	}

	/**
	 * <pre>
	 *  등록된 모든 속성을 삭제하고 초기값으로 다시 등록한다.
	 * </pre>
	 * @param regDate
	 * @param regId
	 */
	@Transactional
	public void resetAttributes(Map<String, List<AppModelAttributeId>> req, String regDate, String regId) throws Exception {
		modelMapper.resetAttributeCodes(regDate, regId);
		modelMapper.resetAttributes(regDate, regId);

		Collection<List<AppModelAttributeId>> values = req.values();
		for (List<AppModelAttributeId> list : values) {
			for(AppModelAttributeId  attr : list) {
				attr.setRegDate(regDate);
				attr.setRegId(regId);
				int res = modelMapper.createAttribute(attr);
				List<AppModelAttributeCode> cds = attr.getCds();
				if(!Util.isEmpty(cds)) {
					String aid 		= attr.getAid();
					String appType 	= attr.getAppType();
					for(AppModelAttributeCode cd : cds) {
						cd.setRegDate(regDate);
						cd.setRegId(regId);
						modelMapper.createAttributeCode(appType, aid, cd);
					}
				}
			}
		}
	}

	/**
	 * <pre>
	 * 새로운 모델등록을 위한 시스템 리스트를 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<AppModel> newAppModels(Map params) throws Exception {
		List<AppModel> res = null;
		res = modelMapper.newAppModels(params);
		return res;
	}

	/**
	 * <pre>
	 * 새로운 모델등록
	 * </pre>
	 * @param requestObject
	 * @param regDate
	 * @param regId
	 */
	@Transactional
	public void createInterfaceModel(InterfaceModel interfaceModel, String regDate, String regId) throws Exception {
		createInterfaceModel( interfaceModel,  regDate,  regId, true);
	}

	/**
	 * <pre>
	 * 새로운 모델등록
	 * </pre>
	 * @param interfaceModel
	 * @param regDate
	 * @param regId
	 * @param historyOn 히스토리 기록 여부
	 * @throws Exception
	 */
	@Transactional
	public void createInterfaceModel(InterfaceModel interfaceModel, String regDate, String regId, boolean historyOn) throws Exception {
		interfaceModel.setRegDate(regDate);
		interfaceModel.setRegId(regId);
		modelMapper.insertInterfaceModel(interfaceModel);

		String interfaceMid = interfaceModel.getMid();

		List<AppModel> appModels = interfaceModel.getAppModels();

		if (!Util.isEmpty(appModels)) {
			for (AppModel appModel : appModels) {

				appModel.setInterfaceMid(interfaceMid);
				appModel.setRegDate(regDate);
				appModel.setRegId(regId);
				modelMapper.insertAppModel(appModel);

				//------------------------------------------------------
				// APP 속성 등록
				//------------------------------------------------------
				String appMid = appModel.getMid();
				{

					Map<String, List<AppModelAttribute>> attributes = appModel.getAttributes();
					if (attributes != null && attributes.size() > 0) {

						Collection<List<AppModelAttribute>> cols = attributes.values();
						for (List<AppModelAttribute> list : cols) {
							int idx = 0;
							for (AppModelAttribute attribute : list) {
								if(!Util.isEmpty(attribute.getVal())) {
									attribute.setInterfaceMid(interfaceMid);
									attribute.setAppMid(appMid);
									attribute.setSeq(idx ++);
									modelMapper.insertAppModelAttribute(attribute);
								}
							}
						}
					}
				}

				//------------------------------------------------------
				// APP 메시지 레이아웃 등록
				//------------------------------------------------------
				List<MessageModel> layouts = appModel.getMsgs();
				if(!Util.isEmpty(layouts)) {
					for (MessageModel messageModel : layouts) {
						messageModel.setInterfaceMid(interfaceMid);
						messageModel.setAppMid(appMid);
						messageModel.setRegDate(regDate);
						messageModel.setRegId(regId);
						modelMapper.insertMessageModel(messageModel);
					}
				}
				//------------------------------------------------------
				// APP 메시지 매핑 등록
				//------------------------------------------------------
				List<MappingModel> mappings = appModel.getMappings();
				if(!Util.isEmpty(mappings)){
					for (MappingModel mappingModel : mappings) {
						mappingModel.setInterfaceMid(interfaceMid);
						mappingModel.setAppMid(appMid);
						mappingModel.setRegDate(regDate);
						mappingModel.setRegId(regId);
						modelMapper.insertMappingModel(mappingModel);
					}
				}

			}

		}

		if(historyOn) modelMapper.insertInterfaceModelHistory(new InterfaceModelHistory(interfaceModel));

	}

	/**
	 * <pre>
	 * 인터페이스 모델 정보 수정
	 * 업데이트 작업 절차
	 * 	TRT0101 업데이트
	 * 	TRT0201, TRT0202, TRT0401, TRT0501 삭제 후 신규 등록 (where interfaceMid = '')
	 *
	 * </pre>
	 * @param requestObject
	 * @param modDate
	 * @param modId
	 */
	@Transactional
	public void updateInterfaceModel(InterfaceModel interfaceModel, String modDate, String modId) throws Exception  {
		updateInterfaceModel( interfaceModel,  modDate,  modId, true) ;
	}

	/**
     * 인터페이스 모델 정보 수정
	 * 업데이트 작업 절차
	 * 	TRT0101 업데이트
	 * 	TRT0201, TRT0202, TRT0401, TRT0501 삭제 후 신규 등록 (where interfaceMid = '')
	 * @param interfaceModel
	 * @param modDate
	 * @param modId
	 * @param historyOn 히스토리 기록 여부
	 * @throws Exception
	 */
	@Transactional
	public void updateInterfaceModel(InterfaceModel interfaceModel, String modDate, String modId, boolean historyOn) throws Exception  {
		interfaceModel.setModDate(modDate);
		interfaceModel.setModId(modId);
		modelMapper.updateInterfaceModel(interfaceModel);

		String interfaceMid = interfaceModel.getMid();
		modelMapper.deleteAppModelAttribute(interfaceMid, modDate, modId);
		modelMapper.deleteAppModel(interfaceMid, modDate, modId);
		modelMapper.deleteMessageModel(interfaceMid, modDate, modId);
		modelMapper.deleteMappingModel(interfaceMid, modDate, modId);

		List<AppModel> appModels = interfaceModel.getAppModels();

		if (!Util.isEmpty(appModels)) {
			for (AppModel appModel : appModels) {

				appModel.setInterfaceMid(interfaceMid);
				appModel.setRegDate(modDate);
				appModel.setRegId(modId);
				appModel.setModDate(modDate);
				appModel.setModId(modId);
				modelMapper.insertAppModel(appModel);

				//------------------------------------------------------
				// APP 속성 등록
				//------------------------------------------------------
				String appMid = appModel.getMid();
				{

					Map<String, List<AppModelAttribute>> attributes = appModel.getAttributes();
					if (attributes != null && attributes.size() > 0) {
						Collection<List<AppModelAttribute>> cols = attributes.values();
						for (List<AppModelAttribute> list : cols) {
							int idx = 0;
							for (AppModelAttribute attribute : list) {
								if(!Util.isEmpty(attribute.getVal())) {
									attribute.setInterfaceMid(interfaceMid);
									attribute.setAppMid(appMid);
									attribute.setSeq(idx ++);
									modelMapper.insertAppModelAttribute(attribute);
								}
							}
						}
					}
				}

				//------------------------------------------------------
				// APP 메시지 레이아웃 등록
				//------------------------------------------------------
				List<MessageModel> layouts = appModel.getMsgs();
				if(!Util.isEmpty(layouts)) {
					for (MessageModel messageModel : layouts) {
						messageModel.setInterfaceMid(interfaceMid);
						messageModel.setAppMid(appMid);
						messageModel.setRegDate(modDate);
						messageModel.setRegId(modId);
						messageModel.setModDate(modDate);
						messageModel.setModId(modId);
						modelMapper.insertMessageModel(messageModel);
					}
				}
				//------------------------------------------------------
				// APP 메시지 매핑 등록
				//------------------------------------------------------
				List<MappingModel> mappings = appModel.getMappings();
				if(!Util.isEmpty(mappings)){
					for (MappingModel mappingModel : mappings) {
						mappingModel.setInterfaceMid(interfaceMid);
						mappingModel.setAppMid(appMid);
						mappingModel.setRegDate(modDate);
						mappingModel.setRegId(modId);
						mappingModel.setModDate(modDate);
						mappingModel.setModId(modId);
						modelMapper.insertMappingModel(mappingModel);
					}
				}

			}

		}

		if(historyOn) modelMapper.insertInterfaceModelHistory(new InterfaceModelHistory(interfaceModel));
	}


	/**
	 * <pre>
	 * 인터페이스 모델 정보 삭제
	 * </pre>
	 * @param interfaceMid
	 * @param modDate
	 * @param modId
	 */
	@Transactional
	public void deleteInterfaceModel(String interfaceMid, String modDate, String modId) throws Exception {
		deleteInterfaceModel(interfaceMid, modDate, modId, true);
	}

	/**
	 * <pre>
	 * 인터페이스 모델 정보 삭제
	 * </pre>
	 * @param interfaceMid
	 * @param modDate
	 * @param modId
	 * @param historyOn 히스토리 기록 여부
	 * @throws Exception
	 */
	@Transactional
	public void deleteInterfaceModel(String interfaceMid, String modDate, String modId, boolean historyOn) throws Exception {

		if(historyOn) {
			InterfaceModel interfaceModel = getInterfaceModel(interfaceMid);
			interfaceModel.setDelYn("Y");
			modelMapper.insertInterfaceModelHistory(new InterfaceModelHistory(interfaceModel));
		}

		modelMapper.deleteAppModel(interfaceMid, modDate, modId);
		modelMapper.deleteAppModelAttribute(interfaceMid, modDate, modId);
		modelMapper.deleteMessageModel(interfaceMid, modDate, modId);
		modelMapper.deleteMappingModel(interfaceMid, modDate, modId);
		modelMapper.deleteInterfaceModel(interfaceMid, modDate, modId);
	}



	/**
	 * <pre>
	 * 인터페이스 모델 정보 조회
	 * </pre>
	 *
	 * @param params
	 * @return
	 */
	public List<InterfaceModel> getInterfaceModelList(Map params) throws Exception {
		return modelMapper.getInterfaceModelList(params);
	}

	/**
	 * <pre>
	 * 인터페이스 모델 상세 정보(앱속성포함) 조회
	 * </pre>
	 * @param interfaceMid
	 * @return
	 * @throws Exception
	 */
	public InterfaceModel getInterfaceModel(String interfaceMid) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceMid", interfaceMid);
		return getInterfaceModel(params);
	}

	/**
	 * <pre>
	 * 인터페이스 모델 상세 정보(앱속성포함) 조회
	 * </pre>
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public InterfaceModel getInterfaceModelByInterfaceId(String interfaceId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceId", interfaceId);
		return getInterfaceModel(params);
	}



	public InterfaceModel getInterfaceModel(Map<String, String> params) throws Exception {

		List<InterfaceModel> list = getInterfaceModelList(params);
		if(Util.isEmpty(list)) return null;
		InterfaceModel model = list.get(0);
		String interfaceMid = model.getMid();
		List<AppModel> appModels = model.getAppModels();


		for (AppModel appModel : appModels) {
			String appMid = appModel.getMid();

			//setting app attributes
			List<AppModelAttribute> attributes = modelMapper.getAppModelAttributes(interfaceMid, appMid);
			if(!Util.isEmpty(attributes))  {

				Map<String, List<AppModelAttribute>> map = new HashMap<String, List<AppModelAttribute>>();
				for (AppModelAttribute attribute  : attributes) {
					String key = attribute.getCd();
					if(!map.containsKey(key)) {
						List<AppModelAttribute> values = new ArrayList<AppModelAttribute>();
						map.put(key, values);
					}
					map.get(key).add(attribute);
				}
				appModel.setAttributes(map);
			}

			//setting msg layout
			List<MessageModel> messageModels = modelMapper.getMessageModels(interfaceMid, appMid);
			if(!Util.isEmpty(messageModels)) {
				appModel.setMsgs(messageModels);
			}

			//setting mapping
			List<MappingModel> mappingModels = modelMapper.getMappingModels(interfaceMid, appMid);
			if(!Util.isEmpty(mappingModels)) {
				appModel.setMappings(mappingModels);
			}


		}
		return model;
	}


	public List<InterfaceModelHistory> getInterfaceModelHistory(String interfaceMid) throws Exception {
		List<InterfaceModelHistory> history = modelMapper.getInterfaceModelHistory(interfaceMid);
		for (InterfaceModelHistory interfaceModelHistory : history) {
			InterfaceModel model = Util.jsonToObject(interfaceModelHistory.getContents(), InterfaceModel.class);
			interfaceModelHistory.setInterfaceModel(model);
		}
		return history;
	}

	/**
	 * 리소스 유형(공통코드 IM04)에 속한 APP유형 코드 리스트 반환
	 * @param resourceCd
	 * @return
	 * @throws Exception
	 */
	public List<AppType> getAppTypes(String resourceCd) throws Exception {
		return modelMapper.getAppTypes(resourceCd);
	}

	/**
	 * @param appTypeCd
	 * @return
	 */
	public AppType getAppType(String appTypeCd) throws Exception {
		return modelMapper.getAppType(appTypeCd);
	}

	/**
	 * 배포 결과 등록
	 * @param md
	 */
	public void createModelDeployment(ModelDeployment md) throws Exception  {
		modelMapper.createModelDeployment(md);
	}
}
