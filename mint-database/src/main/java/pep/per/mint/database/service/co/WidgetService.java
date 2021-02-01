package pep.per.mint.database.service.co;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.ItemModel;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.co.WidgetMapper;

/**
 * @author ta
 *
 */

@Service
public class WidgetService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WidgetMapper widgetMapper;

	/**
	 * getWidgetConfig
	 * Widget Config Info
	 * @param params
	 * @return
	 */
	public List<Map> getWidgetConfig(Map params) {
		return widgetMapper.getWidgetConfig(params);
	}

	/**
	 * getWS0040Target
	 * 시스템별 거래 현황 목록
	 * @param params
	 * @param params
	 * @return
	 */
	public List<Map> getWS0040Target(Map params) {
		return widgetMapper.getWS0040Target(params);
	}

	/**
	 * getWS0042Target
	 * WMQ종합상황판 대상시스템조회
	 * @param params
	 * @param params
	 * @return
	 */
	public List<Map> getWS0042Target(Map params) {
		return widgetMapper.getWS0042Target(params);
	}

	/**
	 * getSystemTreeWithModel
	 * WMQ Explorer Tree - SystemList
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSystemTreeWithModel(Map params) throws Exception{

		//----------------------------------------------------
		// 시스템 리스트 조회
		//----------------------------------------------------
		List<Map<String,Object>> list = widgetMapper.getSystemTreeWithModel(params);

		//----------------------------------------------------
		// 임시저장 맵
		//----------------------------------------------------
		Map<String,Map<String,Object>> rootItemModelMap = new LinkedHashMap<String,Map<String,Object>>();
		Map<String,Map<String,Object>> itemModelMap     = new LinkedHashMap<String,Map<String,Object>>();



		Map<String,Object> treeModel = new HashMap<String,Object>();
		List<Map<String,Object>> roots = new LinkedList();
		treeModel.put("text", "System Tree");
		treeModel.put("roots", roots);

		for( Map<String,Object> map : list) {

			String parentId     = (String) map.get("parentId");
			String parentCd     = (String) map.get("parentCd");
			String parentNm     = (String) map.get("parentNm");
			String parentGrpYn  = (String) map.get("parentGrpYn");
			String parentRootYn = (String) map.get("parentRootYn");
			String childId      = (String) map.get("childId");
			String childCd      = (String) map.get("childCd");
			String childNm      = (String) map.get("childNm");
			String childGrpYn   = (String) map.get("childGrpYn");
			int depth           = (Integer) map.get("depth");

			//----------------------------------------------------
			// parentItemModel
			//----------------------------------------------------
			Map<String,Object> parentItemModel = null;
			if( !itemModelMap.containsKey(parentId) ) {

				parentItemModel = new HashMap();
				parentItemModel.put("id", parentId);
				parentItemModel.put("text", parentNm + " ["+parentCd+"]");
				parentItemModel.put("group", Util.isEmpty(parentGrpYn) || parentGrpYn.equals("N") ? false : true);
				parentItemModel.put("hasChild", true);
				parentItemModel.put("parentId", null);

				parentItemModel.put("objType", "SYSTEM");
				parentItemModel.put("objCd", parentCd);
				parentItemModel.put("objNm", parentNm);
				parentItemModel.put("items", new LinkedList<Map<String,Object>>());

				if( parentRootYn.equals("Y") ) {
					if(!rootItemModelMap.containsKey(parentId)){
						rootItemModelMap.put(parentId,parentItemModel);
						parentItemModel.put("root", true);
						roots.add(parentItemModel);
					}
				}

				parentItemModel.put("systemId", parentCd);
				parentItemModel.put("systemNm", parentNm);
				parentItemModel.put("serverId", "");
				parentItemModel.put("serverNm", "");
				parentItemModel.put("agentId", "");
				parentItemModel.put("agentNm", "");
				parentItemModel.put("qmgrId", "");
				parentItemModel.put("qmgrNm", "");

				itemModelMap.put(parentId, parentItemModel);
			} else {
				parentItemModel = itemModelMap.get(parentId);
			}


			//----------------------------------------------------
			// childItemModel
			//----------------------------------------------------
			Map<String,Object> childItemModel = null;
			if( !itemModelMap.containsKey(childId) ) {
				childItemModel = new HashMap();
				childItemModel.put("id", childId);
				childItemModel.put("text", childNm + " ["+childCd+"]");
				childItemModel.put("group", Util.isEmpty(childGrpYn) || childGrpYn.equals("N") ? false : true);
				childItemModel.put("hasChild", true);
				childItemModel.put("parentId", parentId);

				childItemModel.put("objType", "SYSTEM");
				childItemModel.put("objCd", childCd);
				childItemModel.put("objNm", childNm);
				childItemModel.put("items", new LinkedList<Map<String,Object>>());

				childItemModel.put("systemId", "");
				childItemModel.put("systemNm", "");
				childItemModel.put("serverId", "");
				childItemModel.put("serverNm", "");
				childItemModel.put("agentId", "");
				childItemModel.put("agentNm", "");
				childItemModel.put("qmgrId", "");
				childItemModel.put("qmgrNm", "");

				childItemModel.put("root", false);

				itemModelMap.put(childId, childItemModel);
			} else {

			}

			//----------------------------------------------------
			// Parent - Child Mapping
			//----------------------------------------------------
			if( depth == 1 ){
				List items = (List) parentItemModel.get("items");
				items.add(childItemModel);
				parentItemModel.put("hasChild", true);
			}
		}
		return treeModel;
	}

	/**
	 * getServerTreeWithModel
	 * WMQ Explorer Tree - ServerList
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getServerTreeWithModel(Map params) throws Exception{

		//----------------------------------------------------
		// 서버 리스트 조회
		//----------------------------------------------------
		List<Map<String,Object>> list = widgetMapper.getServerList(params);

		//----------------------------------------------------
		// 임시저장 맵
		//----------------------------------------------------
		Map<String,Map<String,Object>> rootItemModelMap = new LinkedHashMap<String,Map<String,Object>>();
		Map<String,Map<String,Object>> itemModelMap     = new LinkedHashMap<String,Map<String,Object>>();



		Map<String,Object> treeModel = new HashMap<String,Object>();
		List<Map<String,Object>> roots = new LinkedList();
		treeModel.put("text", "Server Tree");
		treeModel.put("roots", roots);

		for( Map<String,Object> map : list) {

			String systemId    = (String) map.get("systemId");
			String systemCd    = (String) map.get("systemCd");
			String systemNm    = (String) map.get("systemNm");
			String serverId    = (String) map.get("serverId");
			String serverCd    = (String) map.get("serverCd");
			String serverNm    = (String) map.get("serverNm");
			String agentId     = (String) map.get("agentId");
			String agentCd     = (String) map.get("agentCd");
			String agentNm     = (String) map.get("agentNm");

			//----------------------------------------------------
			// parentItemModel
			//----------------------------------------------------
			Map<String,Object> parentItemModel = new HashMap();
			parentItemModel.put("id", serverId);
			parentItemModel.put("text", serverNm + " [" + serverCd + "]");
			parentItemModel.put("root", true);
			parentItemModel.put("group", true);
			parentItemModel.put("hasChild", true);
			parentItemModel.put("parentId", null);

			parentItemModel.put("objType", "SERVER");
			parentItemModel.put("objCd", serverCd);
			parentItemModel.put("objNm", serverNm);
			parentItemModel.put("items", new LinkedList<Map<String,Object>>());

			parentItemModel.put("systemId",systemId);
			parentItemModel.put("systemNm", systemNm);
			parentItemModel.put("serverId", serverId);
			parentItemModel.put("serverNm", serverNm);
			parentItemModel.put("agentId", agentId);
			parentItemModel.put("agentNm", agentNm);
			parentItemModel.put("qmgrId", "");
			parentItemModel.put("qmgrNm", "");

			roots.add(parentItemModel);

		}
		return treeModel;
	}

	public int insertPersonalizationByUser(Map params) throws Exception{
		return widgetMapper.insertPersonalizationByUser(params);
	}

	public int updatePersonalizationByUser(Map params) throws Exception{
		return widgetMapper.updatePersonalizationByUser(params);
	}

	public int deletePersonalizationByUser(Map params) throws Exception{
		return widgetMapper.deletePersonalizationByUser(params);
	}

	/**
	 * getPersonalizationByUser
	 * Widget Config Info
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getPersonalizationByUserList(Map params) {
		return widgetMapper.getPersonalizationByUserList(params);
	}

	/**
	 * getPersonalizationBySharedList
	 * Widget Config Info
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getPersonalizationBySharedList(Map params) {
		return widgetMapper.getPersonalizationBySharedList(params);
	}

	/**
	 * getPersonalizationByUser
	 * Widget Config Info
	 * @param params
	 * @return
	 */
	public Map getPersonalization(Map params) {
		return widgetMapper.getPersonalization(params);
	}

}
