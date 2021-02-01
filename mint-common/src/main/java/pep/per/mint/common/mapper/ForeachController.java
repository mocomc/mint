/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.message.PathUtil;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * 하위에 recursive하게 처리해야할 매핑 컨트롤러 리스트를 가진다.
 * 리스트를 구성하는  매핑 컨트롤러는 또다시 하위 컨트롤러를 가질수 있다.
 * control 함수는 이러한 recursive 로직으로 구현된다.
 * 
 * </blockquote>
 * @author Solution TF
 *
 */
public class ForeachController implements MapController {

	public static Logger logger = LoggerFactory.getLogger(ForeachController.class);


	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#getType()
	 */
	@Override
	public Integer getType() {
		return MapControl.CTRL_TYPE_FOREACH;
	}
	 
	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet)
	 */
	@Override
	public void control(MapControl ctrl, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {
		//---------------------------------------------------
		//ForeachControl은 하나의 복합유형 input만을 허용한다.
		//따라서 input 리스트의 첫번째를 input으로 사용한다.
		//---------------------------------------------------
		MapPath inputPath = ctrl.getInputPathList().get(0);
		
		FieldPath inputFieldPath = inputPath.getFullPath();
		
		List<Element> inputElementList = MessageUtil.getElements(srcMsg, inputFieldPath);
		
		if(inputElementList == null || inputElementList.size() == 0) return;
		
		MapPath outputPath = ctrl.getOutputPath();
		
		FieldPath outputParentFieldPath = outputPath.getParent();
		Element outputParentElement = null;
		if(outputParentFieldPath.size() == 1 && FieldPath.ROOT.toString().equals(outputParentFieldPath.toString())){
			outputParentElement = tagMsg.getMsgElement();
		} else{
			outputParentElement = MessageUtil.createElement(tagMsg, outputParentFieldPath, false);
		}
		
		
		Map<String, List<Element>>  inputElementsMap = new LinkedHashMap<String, List<Element>>();
		
		inputElementsMap.put(inputFieldPath.toString(), inputElementList);
		
		control(ctrl, inputElementsMap, outputParentElement, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
		
		
	}
	 

	

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, java.util.Map, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet, java.lang.StringBuffer)
	 */
	@Override
	public void control(MapControl ctrl,
			Map<String, List<Element>> inputElementsMap, Element outputParentElement, Message srcMsg,
			Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet,
			StringBuffer logBuffer) throws Exception {
		// TODO Auto-generated method stub
		
		MapPath inputPath = ctrl.getInputPathList().get(0);
		
		MapPath outputPath = ctrl.getOutputPath();
		
		String inputPathString = inputPath.getFullPathString();

		List<Element> iel = inputElementsMap.get(inputPathString);
		 
		List<MapControl> childCtrls = ctrl.getChildren();
		
		for (Element ie : iel) {

			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
					Util.logToBuffer(logBuffer, Util.join("매핑:foreach(소스패스:",inputPath.getFullPathString(),",타겟패스:", outputPath.getFullPathString(),")"));
					Util.logToBuffer(logBuffer, Util.join("inputElement:",Util.toJSONString(ie)));
					Util.logToBuffer(logBuffer, Util.join("outputParentElement:",outputParentElement.getFieldPath() ,":",Util.toJSONString(outputParentElement)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
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
			for (MapControl mapControl : childCtrls) {
				Map<String, List<Element>> ielm = new HashMap<String, List<Element>>(); 
				 
				List<MapPath> impl = mapControl.getInputPathList();
				for (MapPath mapPath : impl) {
					FieldPath childPath = mapPath.getCurrent();
					List<Element> children = MessageUtil.getElements(ie, childPath);
					ielm.put(PathUtil.getFullPathString(mapPath.getParent().toString(), ".", mapPath.getCurrent().toString()), children);//getFullPathString 에  마지막에 "." 이 포함되어 있으면 빼버린다. 이건 getFullPathString 함수 내에서 구현해 준다.
				}
				
				int controlType = mapControl.getType();
				MapController controller = MapControllerFacade.getInstance().getController(controlType);
				controller.control(mapControl, ielm, ope, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
			}
		}
		
	}

}
