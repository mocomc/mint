/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.su;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.DataAccessRolePath;
import pep.per.mint.common.data.basic.ItemModel;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.DataAccessRoleMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class DataAccessRoleService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DataAccessRoleMapper dataAccessRoleMapper;

	public List<pep.per.mint.common.data.basic.DataAccessRole> getDataAccessRoleList(Map params) throws Exception{
		return dataAccessRoleMapper.getDataAccessRoleList(params);
	}

	@Transactional
	public int createDataAccessRole(pep.per.mint.common.data.basic.DataAccessRole accessRole) throws Exception {
		int res = 0;
		res = dataAccessRoleMapper.insertDataAccessRole(accessRole);

		List<System> systemList = accessRole.getSystemList();
		for(System system : systemList){
			res = dataAccessRoleMapper.insertDataAccessRoleSystemMap(accessRole.getRoleId(), system);
		}

		List<RelUser> relUsers = accessRole.getRelUsers();
		for(RelUser relUser : relUsers){
			res = dataAccessRoleMapper.insertDataAccessRoleRelativeUser(accessRole.getRoleId(), relUser);
		}

		return res;
	}

	@Transactional
	public int updateDataAccessRole(pep.per.mint.common.data.basic.DataAccessRole accessRole) throws Exception {
		return dataAccessRoleMapper.updateDataAccessRole(accessRole);
	}

	@Transactional
	public int deleteDataAccessRole(Map params) throws Exception{
		int res = 0;
		res = dataAccessRoleMapper.deleteAllDataAccessRoleUsers(params);
		res = dataAccessRoleMapper.deleteDataAccessRoleSystemMaps(params);
		res = dataAccessRoleMapper.deleteDataAccessRole(params);
		res = dataAccessRoleMapper.deleteDataAccessRolePath((String)params.get("roleId"));
		return res;
	}

	public List<pep.per.mint.common.data.basic.DataAccessRole> existDataAccessRole(Map params) throws Exception{
		return dataAccessRoleMapper.existDataAccessRole(params);
	}

	public List<String> useDataAccessRole(@Param("roleId")String roleId) throws Exception{
		return dataAccessRoleMapper.usedDataAccessRole( roleId);
	}

	public List<pep.per.mint.common.data.basic.User> getDataAccessRoleUsersList(Map params) throws Exception{
		return dataAccessRoleMapper.getDataAccessRoleUsersList(params);
	}

	@Transactional
	public int createDataAccessRoleUsers(Map params) throws Exception {
		int res = 0;
		List<String> userlist = (List)params.get("userList");
		String roleId = (String)params.get("roleId");
		for(String userId : userlist){
			Map param = new HashMap();
			param.put("roleId", roleId);
			param.put("userId", userId);
			res = dataAccessRoleMapper.insertDataAccessRoleUsers(param);
		}

		return res;
	}


	@Transactional
	public int deleteDataAccessRoleUsers(Map params) throws Exception{
		int res = 0;
		res = dataAccessRoleMapper.deleteDataAccessRoleUsers(params);
		return res;
	}

	public TreeModel<DataAccessRole> getDataAccessRoleTreeWithModel(Map params) throws Exception {
		TreeModel<pep.per.mint.common.data.basic.DataAccessRole> treeModel = new TreeModel<pep.per.mint.common.data.basic.DataAccessRole>();
		treeModel.setText("DataAccessRole Tree");
		List<Map> list = dataAccessRoleMapper.getDataAccessRoleTreeWithModelParam(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.DataAccessRole>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.DataAccessRole>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.DataAccessRole>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.DataAccessRole>>();

		for(Map map : list) {

			{
				String parentId = (String) map.get("parentId");
				String parentCd = (String) map.get("parentCd");
				String parentNm = (String) map.get("parentNm");
				String parentGrpYn = (String) map.get("parentGrpYn");
				String parentRootYn = (String) map.get("parentRootYn");
				String childId = (String) map.get("childId");
				String childCd = (String) map.get("childCd");
				String childNm = (String) map.get("childNm");
				String childGrpYn = (String) map.get("childGrpYn");
				int depth = (Integer) map.get("depth");

				ItemModel<pep.per.mint.common.data.basic.DataAccessRole> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.DataAccessRole>();
					parentItemModel.setId(parentId);
					parentItemModel.setText(parentNm+" ["+parentCd+"]");
					//parentItemModel.setItem(parent);
					parentItemModel.setIsGroup(Util.isEmpty(parentGrpYn) || "N".equals(parentGrpYn) ? false : true);
					parentItemModel.setObjCode(parentCd);
					parentItemModel.setObjName(parentNm);

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------

					itemModelMap.put(parentId, parentItemModel);
					//---------------------------
					// root 처리
					//---------------------------
					if("Y".equals(parentRootYn)){
						if(!rootItemModelMap.containsKey(parentId)){
							rootItemModelMap.put(parentId,parentItemModel);
							treeModel.addRoot(parentItemModel);
							parentItemModel.setIsRoot(true);
						}
						parentItemModel.setSpriteCssClass("rootfolder");
					}else{
						if(parentItemModel.isGroup()){
							parentItemModel.setSpriteCssClass("folder");
						}else{
							parentItemModel.setSpriteCssClass("html");
						}
					}


				}



				ItemModel<pep.per.mint.common.data.basic.DataAccessRole> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.DataAccessRole>();
					childItemModel.setId(childId);
					childItemModel.setParent(parentId);
					childItemModel.setText(childNm+" ["+childCd+"]");
					//childItemModel.setItem(child);
					childItemModel.setIsRoot(false);
					childItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

					childItemModel.setObjCode(childCd);
					childItemModel.setObjName(childNm);

					if(childItemModel.isGroup()){
						childItemModel.setSpriteCssClass("folder");
					}else{
						childItemModel.setSpriteCssClass("html");
					}

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------
					itemModelMap.put(childId, childItemModel);
				}

				if(depth == 1){
					//---------------------------
					// 부모-자식처리
					//---------------------------
					parentItemModel.addItem(childItemModel);
					parentItemModel.setHasChild(true);

				}

			}
		}
		return treeModel;
	}

	@Transactional
	public int updateDataAccessRole2(DataAccessRole dataAccessRole) throws Exception {
		int res = 0;

		res = dataAccessRoleMapper.deleteDataAccessRoleSystemMapsforUpdate(dataAccessRole.getRoleId());
		List<System> systemList = dataAccessRole.getSystemList();
		for(System system : systemList){
			res = dataAccessRoleMapper.insertDataAccessRoleSystemMap(dataAccessRole.getRoleId(), system);
		}

		res = dataAccessRoleMapper.deleteDataAccessRoleRelativeUsersforUpdate(dataAccessRole.getRoleId());
		List<RelUser> relUsers = dataAccessRole.getRelUsers();
		for(RelUser relUser : relUsers){
			res = dataAccessRoleMapper.insertDataAccessRoleRelativeUser(dataAccessRole.getRoleId(), relUser);
		}

		return dataAccessRoleMapper.updateDataAccessRole(dataAccessRole);
	}

	@Transactional
	public int createDataAccessRolePath(Map params) throws Exception {
		int res = 0;
		String parentId = (String) params.get("pid");
		String childId  = (String) params.get("cid");
		String regDate = (String)params.get("regDate");
		String regId = (String) params.get("regId");

		if(Util.isEmpty(dataAccessRoleMapper.existDataAccessRolePath(parentId))) {
			DataAccessRolePath pPath = new DataAccessRolePath();
			pPath.setPid(parentId);
			pPath.setCid(parentId);
			pPath.setDepth(0);
			pPath.setRegDate(regDate);
			pPath.setRegId(regId);
			res = dataAccessRoleMapper.insertDataAccessRolePath(pPath);
		}

		if(Util.isEmpty(dataAccessRoleMapper.existDataAccessRolePath(childId))) {
			DataAccessRolePath cPath = new DataAccessRolePath();
			cPath.setPid(childId);
			cPath.setCid(childId);
			cPath.setDepth(0);
			cPath.setCid(childId);
			cPath.setRegDate(regDate);
			cPath.setRegId(regId);
			res = dataAccessRoleMapper.insertDataAccessRolePath(cPath);
		}
		if(!childId.equalsIgnoreCase(parentId)){
			res = dataAccessRoleMapper.createDataAccessRolePathRelation(params);
		}

		return res;
	}

	@Transactional
	public int deleteDataAccessRolePath(String roleId) throws Exception {
		int res = 0;
		res = dataAccessRoleMapper.deleteDataAccessRolePath(roleId);
		return res;
	}

	public DataAccessRole getDataAccessRoleDetail(String roleId) throws Exception {
		return dataAccessRoleMapper.getDataAccessRoleDetail(roleId);
	}


	public Map getDataAccessRoleInterface(String interfaceId) throws Exception {
		List<Map<String,String>> list = dataAccessRoleMapper.getDataAccessRoleInterface(interfaceId);

		Map<String,Object> resultMap= new LinkedHashMap<String, Object>();

		String roleId     = "";
		String rolePath   = "";

		for(Map<String,String> map : list) {
			//--------------------------------------------------
			// (1) roleId 가 다르면 새로운 아이템임을 셋팅한다.
			//--------------------------------------------------
			if( !roleId.equals( map.get("cRoleId") ) ) {
				roleId   = "";
				rolePath = "";
			}
			//--------------------------------------------------
			// (2) roleId 와 rolePath 를 resultMap 에 저장 한다.
			//--------------------------------------------------
			roleId = map.get("cRoleId");
			if( rolePath.equals("") ) {
				rolePath = map.get("roleNm");
			} else {
				rolePath = rolePath + " > " + map.get("roleNm");
			}
			resultMap.put(roleId, rolePath);

		}
		return resultMap;
	}

	/**
	 * 데이터접근권한 :: 인터페이스 맵핑 생성
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int createDataAccessRoleInterface(Map params, String interfaceId) throws Exception {
		int res = 0;
		//--------------------------------------------------
		// (1) 기존에 맵핑되어 있는 정보가 있으면 삭제한다
		//--------------------------------------------------
		res = dataAccessRoleMapper.deleteDataAccessRoleInterface(interfaceId);
		//--------------------------------------------------
		// (2) 새로운 정보로 맵핑한다.
		//--------------------------------------------------
		List<Map> list = (List)params.get("list");
		for(Map param : list){
			res = dataAccessRoleMapper.insertDataAccessRoleInterface(param);
		}

		return res;
	}

	/**
	 * 데이터접근권한 :: 인터페이스 맵핑 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteDataAccessRoleInterface(String interfaceId) throws Exception{
		int res = 0;
		res = dataAccessRoleMapper.deleteDataAccessRoleInterface(interfaceId);
		return res;
	}
}
