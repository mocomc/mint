/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.FunctionControl;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MapFunctionDefinition;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class FunctionController implements MapController {

	public static Logger logger = LoggerFactory.getLogger(FunctionController.class);
	
	/**
	 * 
	 */
	public FunctionController() {
		super();
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#getType()
	 */
	@Override
	public Integer getType() {
		return MapControl.CTRL_TYPE_FUNCTION;
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
			
			if(inputElementList == null || inputElementList.size() == 0) continue;;

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
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, java.util.Map, pep.per.mint.common.message.Element, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet)
	 */
	@Override
	public void control(MapControl ctrl, Map<String, List<Element>> inputElementsMap, Element outputParentElement, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {

		
		
		List<MapPath> inputPaths = ctrl.getInputPathList();
		MapPath outputPath = ctrl.getOutputPath();
		
		FunctionControl fctrl = (FunctionControl)ctrl;
		MapFunctionDefinition mfd = fctrl.getFunctionDefinition();
		
		if(mfd == null) throw new Exception(Util.join("매핑:예외:FunctionControl에 MapFunctionDefinition 값이 null입니다."));
		
		
		
		
		
		if(inputPaths == null || inputPaths.size() == 0){
			//-------------------------------------------
			//input 필드가 없을 경우 처리
			//SetValue 함수의 경우 소스필드값없이 함수파라메터값으로 타겟필드값을 대체한다. 이경우 input 필드가 필요없다. 
			//-------------------------------------------
			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
					Util.logToBuffer(logBuffer, Util.join("매핑:function ",mfd.getName()," (소스패스:null, 타겟패스:", outputPath.getFullPathString(),")"));
					Util.logToBuffer(logBuffer, Util.join("outputParentElement:",outputParentElement.getFieldPath() ,":",Util.toJSONString(outputParentElement)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
			
			List<Serializable> valueObjects = null;
			Serializable res = FunctionUtil.call(mfd, valueObjects); 
			
			String outputCurrentPathString = outputPath.getCurrent().toString();
			if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
				outputParentElement.setValue(res);
			}else{
				MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(res);
			}
			
			
			
			
			
		}else if(inputPaths.size() == 1){
			//-------------------------------------------
			//input 필드가 하나인 경우 처리
			//-------------------------------------------
			MapPath inputPath = inputPaths.get(0);
			String inputParentPathString = inputPath.getParent().toString();
			
			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
					Util.logToBuffer(logBuffer, Util.join("매핑:function ",mfd.getName()," (소스패스:",inputPath.getFullPathString(),",타겟패스:", outputPath.getFullPathString(),")"));
					Util.logToBuffer(logBuffer, Util.join("inputElementsMap:",Util.toJSONString(inputElementsMap)));
					Util.logToBuffer(logBuffer, Util.join("outputParentElement:",outputParentElement.getFieldPath() ,":",Util.toJSONString(outputParentElement)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
			
			 
			List<Element> elements = inputElementsMap.get(inputPath.getFullPathString());
			
			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, Util.join("map elements:", Util.toJSONString(elements)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
			
			
			if(elements == null || elements.size() == 0) return;
			List<Serializable> valueObjects = new ArrayList<Serializable>();
			for (Element element : elements) {
				Serializable value = element.getValue();
				if(value != null){
					Serializable copy = MessageUtil.copy(value);
					valueObjects.add(copy);
				}
			}
			Serializable res = FunctionUtil.call(mfd, valueObjects);
			//MessageUtil.createElement(tagMsg, res, outputPath.getFullPathString());
			
			String outputCurrentPathString = outputPath.getCurrent().toString();
			if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
				outputParentElement.setValue(res);
			}else{
				MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(res);
			}
			
			
			
		}else {
			//-------------------------------------------
			//input 필드가 둘이상인 경우 처리
			//-------------------------------------------
			//이경우 필드패스에 할당된 Element는 배열 요소가 아니라고 전제한다.
			//단 배열요소이더라도 예외처리하지 않고 첫번째 Element의 값만 활용한다.
			//예를 들면 Concat 함수의 경우 아래 처리 프로세스에 해당됨.
			//output value = Concat(input1 value, input2 value, ...)
			//-------------------------------------------
			
			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
					Util.logToBuffer(logBuffer, Util.join("매핑:function ",mfd.getName()," (소스패스:",Util.toJSONString(inputPaths),",타겟패스:", outputPath.getFullPathString(),")"));
					Util.logToBuffer(logBuffer, Util.join("inputParentElements:",Util.toJSONString(inputElementsMap)));
					Util.logToBuffer(logBuffer, Util.join("outputParentElement:",outputParentElement.getFieldPath() ,":",Util.toJSONString(outputParentElement)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
			
			
			List<Serializable> valueObjects = new ArrayList<Serializable>();
			
			for(MapPath inputPath : inputPaths){
				 

				Serializable value = null;
				List<Element> elements = inputElementsMap.get(inputPath.getFullPathString());
				Element element = elements.get(0);
				if(element != null){
					value = element.getValue();
				}
				
				if(value != null){
					Serializable copy = MessageUtil.copy(value);
					valueObjects.add(copy);
				}
			}
			
			if(logger.isDebugEnabled() && logBuffer != null){
				try{
					Util.logToBuffer(logBuffer, Util.join("map element valueObjects:", Util.toJSONString(valueObjects)));
				}catch(Exception e){
					logger.debug("debug error:",e);
				}
			}
			
			Serializable res = FunctionUtil.call(mfd, valueObjects);

			String outputCurrentPathString = outputPath.getCurrent().toString();
			if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
				outputParentElement.setValue(res);
			}else{
				MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(res);
			}
			
		}
	}

	
	 
 

}
