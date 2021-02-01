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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Application;
import pep.per.mint.common.data.basic.ItemModel;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.MenuPath;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.su.MenuMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 개발업무 관련 서비스
 * @author Solution TF
 *
 */
@Service
public class MenuService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuMapper menuMapper;

	public List<pep.per.mint.common.data.basic.Menu> getMenuList(Map params) throws Exception{
		return menuMapper.getMenuList(params);
	}

	@Transactional
	public int createMenu(pep.per.mint.common.data.basic.Menu menu) throws Exception {
		int res = 0;

		res = menuMapper.insertMenu(menu);
		if(menu.getRootYn().equalsIgnoreCase("Y")){
			Map params = new HashMap<String, String>();
			params.put("pid", menu.getMenuId());
			params.put("cid", menu.getMenuId());
			params.put("regDate", menu.getRegDate());
			params.put("regId", menu.getRegId());
			createMenuPath(params);
		}
		res = menuMapper.insertMenuMappingUserRole(menu);
		return res;
	}

	@Transactional
	public int updateMenu(pep.per.mint.common.data.basic.Menu menu) throws Exception {
		Menu oldMenu = menuMapper.getMenuDetail(menu.getMenuId());
		if(!oldMenu.getRootYn().equalsIgnoreCase(menu.getRootYn())){
			menuMapper.deleteMenuChildPath(menu.getMenuId());
			if(menu.getRootYn().equalsIgnoreCase("Y")){
				Map params = new HashMap<String, String>();
				params.put("pid", menu.getMenuId());
				params.put("cid", menu.getMenuId());
				params.put("regDate", menu.getRegDate());
				params.put("regId", menu.getRegId());
				createMenuPath(params);
			}
		}
		return menuMapper.updateMenu(menu);
	}

	@Transactional
	public int deleteMenu(Map params) throws Exception{
		int res = 0;
		res = menuMapper.deleteMenuMappingUserRole(params);
		res = menuMapper.deleteMenuChildPath((String)params.get("menuId"));
		res = menuMapper.deleteMenu(params);
		return res;
	}

	public List<pep.per.mint.common.data.basic.Menu> existMenu(Map params) throws Exception{
		return menuMapper.existMenu(params);
	}

	public List<String> useMenu(@Param("menuId")String menuId) throws Exception{
		return menuMapper.usedMenu(menuId);
	}



	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.Menu> getMenuTreeWithModel(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.Menu> treeModel = new TreeModel<pep.per.mint.common.data.basic.Menu>();
		treeModel.setText("Menu Tree");
		List<Map> list = menuMapper.getMenuTreeWithModelParam(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.Menu>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Menu>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.Menu>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.Menu>>();

		for(Map map : list) {
			logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
			{
				String parentId = (String) map.get("parentId");
				String parentNm = (String) map.get("parentNm");
				String parentRootYn = (String) map.get("parentRootYn");
				String childId = (String) map.get("childId");
				String childNm = (String) map.get("childNm");
				int depth = (Integer) map.get("depth");

				ItemModel<pep.per.mint.common.data.basic.Menu> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.Menu>();
					parentItemModel.setId(parentId);
					//parentItemModel.setText(parentNm+" ["+parentCd+"]");
					parentItemModel.setText(parentNm);
					//parentItemModel.setItem(parent);
					//parentItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

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
						if("Y".equals(parentItemModel.isGroup())){
							parentItemModel.setSpriteCssClass("folder");
						}else{
							parentItemModel.setSpriteCssClass("html");
						}
					}


				}



				ItemModel<pep.per.mint.common.data.basic.Menu> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {



					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.Menu>();
					childItemModel.setId(childId);
					childItemModel.setParent(parentId);
					childItemModel.setText(childNm);
					//childItemModel.setItem(child);
					childItemModel.setIsRoot(false);

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

	public List<Menu> getNotUseMenuList(Map params) throws Exception {
		return menuMapper.getNotUseMenuList(params);
	}

	@Transactional
	public int createMenuPath(Map params) throws Exception  {

		int res = 0;
		String parentMenuId = (String) params.get("pid");
		String childMenuId  = (String) params.get("cid");
		String regDate = (String)params.get("regDate");
		String regId = (String) params.get("regId");

		if(Util.isEmpty(menuMapper.existPath(parentMenuId))) {

			MenuPath pPath = new MenuPath();
			pPath.setPid(parentMenuId);
			pPath.setCid(parentMenuId);
			pPath.setDepth(0);
			pPath.setRegDate(regDate);
			pPath.setRegId(regId);
			res = menuMapper.insertMenuPath(pPath);
		}

		if(Util.isEmpty(menuMapper.existPath(childMenuId))) {
			MenuPath cPath = new MenuPath();
			cPath.setPid(childMenuId);
			cPath.setCid(childMenuId);
			cPath.setDepth(0);
			cPath.setRegDate(regDate);
			cPath.setRegId(regId);
			res = menuMapper.insertMenuPath(cPath);
		}
		if(!childMenuId.equalsIgnoreCase(parentMenuId)){
			res = menuMapper.createMenuPathRelation(params);
		}

		return res;
	}

	@Transactional
	public int deleteMenuChildPath(String menuId) throws Exception  {
		int res = 0;
		res = menuMapper.updateMenuRootYn(menuId);
		res = menuMapper.deleteMenuChildPath(menuId);
		return res;
	}

	public Menu getMenuDetail(String menuId) throws Exception {
		return menuMapper.getMenuDetail(menuId);
	}

	@Transactional
	public void updateMenuApps(Menu menu) throws Exception  {
		int res = 0;
		res = menuMapper.deleteMenuRelativeApps(menu.getMenuId());
		List<Application> appLists = menu.getAppList();
		for(Application apps : appLists){
			res = menuMapper.insertMenuRelativeApps(menu.getMenuId(), apps);
		}
	}

}
