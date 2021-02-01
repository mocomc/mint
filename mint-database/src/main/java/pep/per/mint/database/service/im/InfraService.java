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
package pep.per.mint.database.service.im;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.BusinessPath;
import pep.per.mint.common.data.basic.ItemModel;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.OrganizationPath;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.SystemPath;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.im.InfraMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class InfraService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InfraMapper infraMapper;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.System> getSystemList(Map params) throws Exception{
		return infraMapper.getSystemList(params);
	}

	public List<pep.per.mint.common.data.basic.System> existSystem(Map params) throws Exception{
		return infraMapper.existSystem(params);
	}

	/**
	 * REST-R05-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<System> getRootSystemList(Map params) throws Exception{
		return infraMapper.getRootSystemList(params);
	}

	/**
	 * REST-R06-IM-01-01 시스템트리조회
	 * @return
	 * @throws Exception
	 */
	public List<System> getChildSystemList(Map params) throws Exception{
		return infraMapper.getChildSystemList(params);
	}

	/**
	 *
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.System getSystemDetail(String systemId) throws Exception{
		return infraMapper.getSystemDetail(systemId);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.System> getSystemTreeWithModel() throws Exception{
		TreeModel<pep.per.mint.common.data.basic.System> treeModel = new TreeModel<pep.per.mint.common.data.basic.System>();
		treeModel.setText("System Tree");
		List<Map> list = infraMapper.getSystemTreeWithModel();
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();


		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
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

				ItemModel<pep.per.mint.common.data.basic.System> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					parentItemModel.setId(parentId);
					parentItemModel.setText(parentNm);
					//parentItemModel.setItem(parent);
					parentItemModel.setIsGroup(Util.isEmpty(parentGrpYn) || "N".equals(parentGrpYn) ? false : true);
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
					}
				}



				ItemModel<pep.per.mint.common.data.basic.System> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					childItemModel.setId(childId);
					childItemModel.setParent(parentId);
					childItemModel.setText(childNm);
					//childItemModel.setItem(child);
					childItemModel.setIsRoot(false);
					childItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);
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



	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.System> getSystemTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.System> treeModel = new TreeModel<pep.per.mint.common.data.basic.System>();
		treeModel.setText("System Tree");
		List<Map> list = infraMapper.getSystemTreeWithModelParam(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();

		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
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

				ItemModel<pep.per.mint.common.data.basic.System> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
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
				}else{// 해당하는 항목의 Group설정을 확인하여 변경
					parentItemModel = itemModelMap.get(parentId);
					if(!parentItemModel.isGroup()){
						parentItemModel.setIsGroup(Util.isEmpty(parentGrpYn) || "N".equals(parentGrpYn) ? false : true);
					}

					if(parentItemModel.isGroup()){
						parentItemModel.setSpriteCssClass("folder");
					}else{
						parentItemModel.setSpriteCssClass("html");
					}
				}



				ItemModel<pep.per.mint.common.data.basic.System> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
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

	public List<Map> getSystemTree() throws Exception{
		List<Map> list = infraMapper.getSystemTree();
		return list;
	}


	@Transactional
	public int createSystem(pep.per.mint.common.data.basic.System system) throws Exception {
		int res = 0;

		res = infraMapper.insertSystem(system);

		List<Server> serverList = system.getServerList();
		for(Server server : serverList){
			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
		}

		List<RelUser> relUsers = system.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
		}


		return res;
	}

	public List<String> usedSystem(String systemId) throws Exception{
		return infraMapper.usedSystem(systemId);
	}

	public int deleteSystem(String systemId, String modId, String modDate) throws Exception{
		int res = 0;
		res = infraMapper.deleteSystemRelativeUsers(systemId);
		res = infraMapper.deleteSystemServerMaps(systemId);
		res = infraMapper.deleteSystemAsOranization(systemId);
		res = infraMapper.deleteSystem(systemId, modId, modDate);
		res = infraMapper.deleteSystemPath(systemId);
		return res;
	}

	public int deleteSystemPath(String systemId) throws Exception{
		int res = 0;
		res = infraMapper.deleteSystemPath(systemId);
		return res;
	}

	public int updateSystem(System system) throws Exception {

		int res = 0;
//		res = infraMapper.deleteSystemServerMaps(system.getSystemId());
		res = infraMapper.deleteSystemServerMapsforUpdate(system.getSystemId());
		List<Server> serverList = system.getServerList();
		for(Server server : serverList){
			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
		}
//		res = infraMapper.deleteSystemRelativeUsers(system.getSystemId());
		res = infraMapper.deleteSystemRelativeUsersforUpdate(system.getSystemId());
		List<RelUser> relUsers = system.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
		}

		return infraMapper.updateSystem(system);
	}


	public List<Map> getBusinessTree() throws Exception{
		List<Map> list = infraMapper.getBusinessTree();
		return list;
	}

	public List<Map> getRootBusinessTree() throws Exception{
		List<Map> list = infraMapper.getRootBusinessTree();
		return list;
	}

	public List<Map> getChildBusinessTree(String parentBusinessId) throws Exception{
		List<Map> list = infraMapper.getChildBusinessTree(parentBusinessId);
		return list;
	}


	public int createSystemPath(Map params) throws Exception{


		int res = 0;
		String parentSystemId = (String) params.get("pid");
		String childSystemId  = (String) params.get("cid");
		String regDate = (String)params.get("regDate");
		String regId = (String) params.get("regId");

		if(Util.isEmpty(infraMapper.existPath(parentSystemId))) {

			SystemPath pPath = new SystemPath();
			pPath.setPid(parentSystemId);
			pPath.setCid(parentSystemId);
			pPath.setDepth(0);
			pPath.setRegDate(regDate);
			pPath.setRegId(regId);
			res = infraMapper.insertSystemPath(pPath);
		}

		if(Util.isEmpty(infraMapper.existPath(childSystemId))) {
			SystemPath cPath = new SystemPath();
			cPath.setPid(childSystemId);
			cPath.setCid(childSystemId);
			cPath.setDepth(0);
			cPath.setRegDate(regDate);
			cPath.setRegId(regId);
			res = infraMapper.insertSystemPath(cPath);
		}
		if(!childSystemId.equalsIgnoreCase(parentSystemId)){
			res = infraMapper.createSystemPathRelation(params);
		}

		return res;
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.Server> getServerList(Map params) throws Exception{
		return infraMapper.getServerList(params);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.Server> existServer(Map params) throws Exception{
		return infraMapper.existServer(params);
	}
	/**
	 *
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.Server getServerDetail(String serverId) throws Exception{
		return infraMapper.getServerDetail(serverId);
	}

	@Transactional
	public int createServer(pep.per.mint.common.data.basic.Server server) throws Exception {
		int res = 0;
		//TODO 작업 검토 작업 필요.
//		List<Server> serverList = server.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		res = infraMapper.insertServer(server);

		return res;
	}

	public int updateServer(Server server) throws Exception {

		int res = 0;
//		res = infraMapper.deleteSystemServerMaps(system.getSystemId());
//		List<Server> serverList = system.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//		res = infraMapper.deleteSystemRelativeUsers(system.getSystemId());
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		return infraMapper.updateServer(server);
	}

	public int deleteServer(String serverId, String modId, String modDate) throws Exception{
		int res = 0;
		res = infraMapper.deleteServerAsSystem(serverId, modId, modDate);
		res = infraMapper.deleteServer(serverId, modId, modDate);
		return res;
	}


	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.User> getUserList(Map params) throws Exception{
		return infraMapper.getUserList(params);
	}
	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.User getUserDetail(String userId) throws Exception{
		return infraMapper.getUserDetail(userId);
	}


	@Transactional
	public int createUser(pep.per.mint.common.data.basic.User user) throws Exception {
		int res = 0;
		//TODO 작업 검토 작업 필요.
//		List<Server> serverList = server.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		res = infraMapper.insertUser(user);

		return res;
	}

	public int updateUser(User user) throws Exception {

		int res = 0;
//		res = infraMapper.deleteSystemServerMaps(system.getSystemId());
//		List<Server> serverList = system.getServerList();
//		for(Server server : serverList){
//			res = infraMapper.insertSystemServerMap(system.getSystemId(), server);
//		}
//		res = infraMapper.deleteSystemRelativeUsers(system.getSystemId());
//		List<RelUser> relUsers = system.getRelUsers();
//		for(RelUser relUser : relUsers){
//			res = infraMapper.insertSystemRelativeUser(system.getSystemId(), relUser);
//		}

		return infraMapper.updateUser(user);
	}

	public List<String> usedUser(String userId) throws Exception{
		return infraMapper.usedUser(userId);
	}

	public int deleteUser(String userId, String modId, String modDate) throws Exception{
		int res = 0;
		res = infraMapper.deleteUser(userId, modId, modDate);

		return res;
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.UserRole> getUserRoleList(Map params) throws Exception{
		return infraMapper.getUserRoleList(params);
	}


	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.Organization> existOrganization(Map params) throws Exception{
		return infraMapper.existOrganization(params);
	}
	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.Organization> getOrganizationTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.Organization> treeModel = new TreeModel<pep.per.mint.common.data.basic.Organization>();
		treeModel.setText("Organization Tree");
		List<Map> list = infraMapper.getOrganizationTreeWithModelParam(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.Organization>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Organization>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.Organization>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Organization>>();

		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
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

				ItemModel<pep.per.mint.common.data.basic.Organization> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.Organization>();
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



				ItemModel<pep.per.mint.common.data.basic.Organization> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.Organization>();
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



	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.Organization> getOrganizationSystemTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.Organization> treeModel = new TreeModel<pep.per.mint.common.data.basic.Organization>();
		treeModel.setText("Organization Tree");
		List<Map> list = infraMapper.getOrganizationTreeWithModelParam(params);
//		List<Map> list = infraMapper.organizationSystemTreeWithModel(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.Organization>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Organization>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.Organization>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Organization>>();

		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
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
				Map  systemList =(Map) map.get("systemList");

				ItemModel<pep.per.mint.common.data.basic.Organization> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.Organization>();
					parentItemModel.setId(parentId);
					parentItemModel.setText(parentNm+" ["+parentCd+"]");
					//parentItemModel.setItem(parent);
					parentItemModel.setIsGroup(Util.isEmpty(parentGrpYn) || "N".equals(parentGrpYn) ? false : true);

					parentItemModel.setObjCode(parentCd);
					parentItemModel.setObjName(parentNm);
					// System 등록

					// SystemAdd

					//organizationId
					Map<String, String> params1 = new HashMap<String, String>();
					params1.put("organizationId", childId);
					List<Map> listSystem = infraMapper.getOrganizationSystemMap(params1);
					for(Map map1 : listSystem) {
						if(map1 != null)
						{
							String systemId = (String) map1.get("systemId");
							String systemCd = (String) map1.get("systemCd");
							String systemNm = (String) map1.get("systemNm");
							ItemModel<pep.per.mint.common.data.basic.Organization> childItemModel1 = new ItemModel<pep.per.mint.common.data.basic.Organization>();

							childItemModel1.setId(systemId);
							childItemModel1.setParent(childId);
							childItemModel1.setText(systemNm+" ["+systemCd+"]");
							childItemModel1.setIsRoot(false);
							childItemModel1.setIsGroup(false);
							childItemModel1.setObjCode(systemCd);
							childItemModel1.setObjName(systemNm);

							childItemModel1.setSpriteCssClass("pdf");
							parentItemModel.addItem(childItemModel1);
							parentItemModel.setHasChild(true);
						}

					}

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
						if("Y".equals(parentItemModel.isGroup())){
							parentItemModel.setSpriteCssClass("folder");
						}else{
							parentItemModel.setSpriteCssClass("html");
						}
					}


				}



				ItemModel<pep.per.mint.common.data.basic.Organization> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.Organization>();
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

					// SystemAdd

					//organizationId
					Map<String, String> params1 = new HashMap<String, String>();
					params1.put("organizationId", childId);
					List<Map> listSystem = infraMapper.getOrganizationSystemMap(params1);
					for(Map map1 : listSystem) {
						if(map1 != null)
						{
							String systemId = (String) map1.get("systemId");
							String systemCd = (String) map1.get("systemCd");
							String systemNm = (String) map1.get("systemNm");
							ItemModel<pep.per.mint.common.data.basic.Organization> childItemModel1 = new ItemModel<pep.per.mint.common.data.basic.Organization>();

							childItemModel1.setId(systemId);
							childItemModel1.setParent(childId);
							childItemModel1.setText(systemNm+" ["+systemCd+"]");
							childItemModel1.setIsRoot(false);
							childItemModel1.setIsGroup(false);
							childItemModel1.setObjCode(systemCd);
							childItemModel1.setObjName(systemNm);

							childItemModel1.setSpriteCssClass("pdf");
							childItemModel.addItem(childItemModel1);
							childItemModel.setHasChild(true);
						}

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


	/**
	 *
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.Organization getOrganizationDetail(String organizationId) throws Exception{
		return infraMapper.getOrganizationDetail(organizationId);
	}



	@Transactional
	public int createOrganization(pep.per.mint.common.data.basic.Organization organization) throws Exception {
		int res = 0;

		res = infraMapper.insertOrganization(organization);

		List<System> systemList = organization.getSystemList();
		for(System system : systemList){
			res = infraMapper.insertOrganizationSystemMap(organization.getOrganizationId(), system);
		}

		List<RelUser> relUsers = organization.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertOrganizationRelativeUser(organization.getOrganizationId(), relUser);
		}


		return res;
	}

	public List<String> usedOrganization(String organizationId) throws Exception{
		return infraMapper.usedOrganization(organizationId);
	}

	public List<Map> usedOrganizationToSystem(Organization organization) throws Exception{
		List<Map> systemList = new ArrayList<Map>();
		for(System system : organization.getSystemList()){
			systemList.addAll(infraMapper.getOrganizationOfSystemMap(system));
		}

		return systemList;
	}

	public int deleteOrganization(String organizationId, String modId, String modDate) throws Exception{
		int res = 0;
		res = infraMapper.deleteOrganizationRelativeUsers(organizationId);
		res = infraMapper.deleteOrganizationSystemMaps(organizationId);
		res = infraMapper.deleteOrganization(organizationId, modId, modDate);
		res = infraMapper.deleteOrganizationPath(organizationId);
		return res;
	}

	public int deleteOrganizationPath(String organizationId) throws Exception{
		int res = 0;
		res = infraMapper.deleteOrganizationPath(organizationId);
		return res;
	}

	public int updateOrganization(Organization organization) throws Exception {
		logger.debug(organization.toString());
		int res = 0;
		//res = infraMapper.deleteOrganizationSystemMaps(organization.getOrganizationId());
		res = infraMapper.deleteOrganizationSystemMapsforUpdate(organization.getOrganizationId());
		List<System> systemList = organization.getSystemList();
		for(System system : systemList){
			List<Map> tmpMap = infraMapper.getOrganizationOfSystemMap(system);
			if(tmpMap == null || tmpMap.size()>0){
				throw new Exception(system.getSystemNm() + " already exist.");
			}
			res = infraMapper.insertOrganizationSystemMap(organization.getOrganizationId(), system);
		}
		//res = infraMapper.deleteOrganizationRelativeUsers(organization.getOrganizationId());
		res = infraMapper.deleteOrganizationRelativeUsersforUpdate(organization.getOrganizationId());
		List<RelUser> relUsers = organization.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertOrganizationRelativeUser(organization.getOrganizationId(), relUser);
		}

		return infraMapper.updateOrganization(organization);
	}

	public int createOrganizationPath(Map params) throws Exception{


		int res = 0;
		String parentId = (String) params.get("pid");
		String childId  = (String) params.get("cid");
		String regDate = (String)params.get("regDate");
		String regId = (String) params.get("regId");

		if(Util.isEmpty(infraMapper.existOrganizationPath(parentId))) {
			OrganizationPath pPath = new OrganizationPath();
			pPath.setPid(parentId);
			pPath.setCid(parentId);
			pPath.setDepth(0);
			pPath.setRegDate(regDate);
			pPath.setRegId(regId);
			res = infraMapper.insertOrganizationPath(pPath);
		}

		if(Util.isEmpty(infraMapper.existOrganizationPath(childId))) {
			OrganizationPath cPath = new OrganizationPath();
			cPath.setPid(childId);
			cPath.setCid(childId);
			cPath.setDepth(0);
			cPath.setCid(childId);
			cPath.setRegDate(regDate);
			cPath.setRegId(regId);
			res = infraMapper.insertOrganizationPath(cPath);
		}
		if(!childId.equalsIgnoreCase(parentId)){
			res = infraMapper.createOrganizationPathRelation(params);
		}

		return res;
	}



	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.Business> getBusinessTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.Business> treeModel = new TreeModel<pep.per.mint.common.data.basic.Business>();
		treeModel.setText("Business Tree");
		List<Map> list = infraMapper.getBusinessTreeWithModelParam(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.Business>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Business>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.Business>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Business>>();


		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
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

				ItemModel<pep.per.mint.common.data.basic.Business> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.Business>();
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



				ItemModel<pep.per.mint.common.data.basic.Business> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.Business>();
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


	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<pep.per.mint.common.data.basic.Business> existBusiness(Map params) throws Exception{
		return infraMapper.existBusiness(params);
	}
	/**
	 *
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public pep.per.mint.common.data.basic.Business getBusinessDetail(String businessId) throws Exception{
		return infraMapper.getBusinessDetail(businessId);
	}



	@Transactional
	public int createBusiness(pep.per.mint.common.data.basic.Business business) throws Exception {
		int res = 0;

		res = infraMapper.insertBusiness(business);

		List<RelUser> relUsers = business.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertBusinessRelativeUser(business.getBusinessId(), relUser);
		}


		return res;
	}

	public List<String> usedBusiness(String businessId) throws Exception{
		return infraMapper.usedBusiness(businessId);
	}

	public int deleteBusiness(String businessId, String modId, String modDate) throws Exception{
		int res = 0;
		res = infraMapper.deleteBusinessRelativeUsers(businessId);
		res = infraMapper.deleteBusiness(businessId, modId, modDate);
		res = infraMapper.deleteBusinessPath(businessId);
		return res;
	}

	public int deleteBusinessPath(String businessId) throws Exception{
		int res = 0;
		res = infraMapper.deleteBusinessPath(businessId);
		return res;
	}

	public int updateBusiness(Business business) throws Exception {
		logger.debug(business.toString());
		int res = 0;

		//res = infraMapper.deleteBusinessRelativeUsers(business.getBusinessId());
		res = infraMapper.deleteBusinessRelativeUsersforUpdate(business.getBusinessId());
		List<RelUser> relUsers = business.getRelUsers();
		for(RelUser relUser : relUsers){
			res = infraMapper.insertBusinessRelativeUser(business.getBusinessId(), relUser);
		}

		return infraMapper.updateBusiness(business);
	}

	public int createBusinessPath(Map params) throws Exception{
		int res = 0;
		String parentId = (String) params.get("pid");
		String childId  = (String) params.get("cid");
		String regDate = (String)params.get("regDate");
		String regId = (String) params.get("regId");

		if(Util.isEmpty(infraMapper.existBusinessPath(parentId))) {

			BusinessPath pPath = new BusinessPath();
			pPath.setPid(parentId);
			pPath.setCid(parentId);
			pPath.setDepth(0);
			pPath.setRegDate(regDate);
			pPath.setRegId(regId);
			res = infraMapper.insertBusinessPath(pPath);
		}

		if(Util.isEmpty(infraMapper.existBusinessPath(childId))) {
			BusinessPath cPath = new BusinessPath();
			cPath.setPid(childId);
			cPath.setCid(childId);
			cPath.setDepth(0);
			cPath.setRegDate(regDate);
			cPath.setRegId(regId);
			res = infraMapper.insertBusinessPath(cPath);
		}
		if(!childId.equalsIgnoreCase(parentId)){
			res = infraMapper.createBusinessPathRelation(params);
		}

		return res;
	}

}
