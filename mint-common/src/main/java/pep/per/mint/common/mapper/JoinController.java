/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.JoinCondition;
import pep.per.mint.common.data.map.JoinControl;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.mapper.MapController;
import pep.per.mint.common.mapper.MapControllerFacade;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.message.PathUtil;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class JoinController implements MapController {

	public static Logger logger = LoggerFactory.getLogger(JoinController.class);

	/**
	 * 
	 */
	public JoinController() {
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#getType()
	 */
	@Override
	public Integer getType() {
		return MapControl.CTRL_TYPE_JOIN;
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet)
	 */
	@Override
	public void control(MapControl ctrl, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {

		List<MapPath> impl = ctrl.getInputPathList();

		Map<String, List<Element>>  inputElementsMap = new LinkedHashMap<String, List<Element>>();
		
		for (MapPath mapPath : impl) {
			
			FieldPath inputFieldPath = mapPath.getFullPath();
			
			List<Element> inputElementList = MessageUtil.getElements(srcMsg, inputFieldPath);
			
			if(inputElementList == null || inputElementList.size() == 0) continue;

			inputElementsMap.put(inputFieldPath.toString(), inputElementList);
		}
		
	 
		
		MapPath outputPath = ctrl.getOutputPath();
		FieldPath outputParentFieldPath = outputPath.getParent();
		Element outputParentElement = null;
		if(outputParentFieldPath.size() == 1 && FieldPath.ROOT.toString().equals(outputParentFieldPath.toString())){
			outputParentElement = tagMsg.getMsgElement();
		} else{
			outputParentElement = MessageUtil.createElement(tagMsg, outputParentFieldPath, false);
		}
		
		control(ctrl, inputElementsMap, outputParentElement, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
		
	}

	 

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, java.util.Map, pep.per.mint.common.message.Element, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet, java.lang.StringBuffer)
	 */
	@Override
	public void control(MapControl ctrl,
			Map<String, List<Element>> inputElements,
			Element outputParentElement, Message srcMsg, Message tagMsg,
			MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer)
			throws Exception {
		 
		JoinControl jc = (JoinControl)ctrl;
		
		List<MapPath> impl = jc.getInputPathList();

		String drivingJoinPath = impl.get(0).getFullPathString();
		
		List<Element> drivingJoinElementList = inputElements.get(drivingJoinPath);	
		
		Map<String, List<Element>> joinElementListMap = new LinkedHashMap<String, List<Element>>();
		
		List<Element> elements = new ArrayList<Element>();
		
		elements.addAll(drivingJoinElementList);
		
		joinElementListMap.put(drivingJoinPath, elements);
		
		List<MapPath> drivedJoinPathList = impl.subList(1, impl.size());
		  
		List<Map<String, Element>> joinElementMapList = new ArrayList<Map<String, Element>>();
		for(Element driving : drivingJoinElementList){
			Map<String, Element> map = new HashMap<String, Element>();
			map.put(drivingJoinPath, driving);
			joinElementMapList.add(map);
		}
		
		for (int i = 0 ; i < drivedJoinPathList.size() ; i ++) {
			
			MapPath drivedJoinMapPath = drivedJoinPathList.get(i);
			
			String drivedJoinPath = drivedJoinMapPath.getFullPathString();
			
			List<Element> drivedJoinElementList = inputElements.get(drivedJoinPath);
			
			if(drivedJoinElementList == null || drivedJoinElementList.size() == 0) continue;
			
			JoinCondition condition = jc.getJoinCondition(drivedJoinPath);
			
			int type = condition.getType();

			switch(type){
			
			case JoinCondition.JC_SAME_INDEX :
				int drivingSize = joinElementMapList.size();
				int drivedSize = drivedJoinElementList.size();
				int size = (drivingSize >= drivedSize) ? drivedSize : drivingSize;
				for (int j = 0; j < size; j++) {
					joinElementMapList.get(j).put(drivedJoinPath, drivedJoinElementList.get(j));
				}
				joinElementMapList = joinElementMapList.subList(0, size);
				break;
			case JoinCondition.JC_SOME_INDEX :
				List<Integer> indeces = condition.getSomeIndeces();
				List<Map<String, Element>> tmp = new ArrayList<Map<String, Element>>();
				for (int index : indeces) {
					Element drived = drivedJoinElementList.get(index);
					if(drived != null) {
						Map<String, Element> map = joinElementMapList.get(index);
						if(map != null) {
							map.put(drivedJoinPath, drived);
							tmp.add(map);
						}
					}
				}
				joinElementMapList = tmp;
				break;
			case JoinCondition.JC_FILTER :
				throw new Exception("매핑:JoinControl:예외:JoinCondition.JC_FILTER[필드값에따른필터링] 조건은 아직 구현하지되지 않았습니다.^^");
			case JoinCondition.JC_NONE :
				List<Map<String, Element>> tmpElementMapList = new ArrayList<Map<String, Element>>();
				for(int k = 0 ; k < joinElementMapList.size() ;  k ++){
					Map<String, Element> map = joinElementMapList.get(k);
					
					for(int l  = 0 ; l < drivedJoinElementList.size() ; l ++){
						Map<String, Element> newMap = new HashMap<String, Element>();
						
						Set<String> keys = map.keySet();
						for (String key : keys) {
							Element drivingElement = map.get(key);
							newMap.put(drivingElement.getFieldPath().toString(), drivingElement);
						}
						Element drivedElement = drivedJoinElementList.get(l);
						newMap.put(drivedElement.getFieldPath().toString(), drivedElement);
						tmpElementMapList.add(newMap);
					}
				}
				joinElementMapList = tmpElementMapList;
				break;
				
			default :
				throw new Exception(Util.join("매핑:JoinControl:예외:JoinCondition의 유형이 정의되지 않은 값입니다.(",type,")"));
			}
			
		}

		 
		for (Map<String, Element> inputEmelmentMap : joinElementMapList) {
			join(jc, inputEmelmentMap, outputParentElement,srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
		}
		
		
	}
	
	
	/**
	 * @param children
	 * @param inputEmelmentMap
	 * @param outputParentElement
	 * @param srcMsg
	 * @param tagMsg
	 * @param srcMsgSet
	 * @param tagMsgSet
	 * @param logBuffer
	 * @throws Exception 
	 */
	private void join(JoinControl ctrl,
			Map<String, Element> inputEmelmentMap,
			Element outputParentElement, Message srcMsg, Message tagMsg,
			MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {
		// TODO Auto-generated method stub
		
		
		if(logger.isDebugEnabled() && logBuffer != null){
			try{
				Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
				Util.logToBuffer(logBuffer, Util.join("매핑:join(joinControl:",Util.toJSONString(ctrl)));
			}catch(Exception e){
				logger.debug("debug error:",e);
			}
		}
		
		MapPath outputPath = ctrl.getOutputPath();
		
		//----------------------------------------------------------------
		//setting output parent element for calling sub control function.
		//----------------------------------------------------------------
		FieldPath outputCurrentPath = outputPath.getCurrent();
		Element ope = null;
		if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPath.toString())){
			ope = outputParentElement;
		}else{
			ope = MessageUtil.createElement(outputParentElement, outputCurrentPath, true);
		}
		//----------------------------------------------------------------
		
		List<MapControl> childCtrls = ctrl.getChildren();
		for (MapControl mapControl : childCtrls) {

			Map<String, List<Element>> ielm = new HashMap<String, List<Element>>(); 
			
			List<MapPath> impl = mapControl.getInputPathList();
			for (MapPath mapPath : impl) {
				FieldPath parentPath = mapPath.getParent();
				FieldPath childPath = mapPath.getCurrent();
				Element parentElement = inputEmelmentMap.get(parentPath.toString());
				List<Element> children = MessageUtil.getElements(parentElement, childPath);
				ielm.put(PathUtil.getFullPathString(mapPath.getParent().toString(), ".", mapPath.getCurrent().toString()), children);//getFullPathString 에  마지막에 "." 이 포함되어 있으면 빼버린다. 이건 getFullPathString 함수 내에서 구현해 준다.
			}
			
			int controlType = mapControl.getType();
			MapController controller = MapControllerFacade.getInstance().getController(controlType);
			controller.control(mapControl, ielm, ope, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
		}
	}
	
	 
}
