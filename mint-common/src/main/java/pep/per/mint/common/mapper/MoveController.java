/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.data.map.MoveControl;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.MessageSetUtil;
import pep.per.mint.common.message.MessageUtil;
import pep.per.mint.common.message.PathUtil;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MoveController implements MapController {
	
	public static Logger logger = LoggerFactory.getLogger(MoveController.class);

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message)
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
	 * @see pep.per.mint.common.mapper.MapController#control(pep.per.mint.common.data.map.MapControl, pep.per.mint.common.message.Element, pep.per.mint.common.message.Message, pep.per.mint.common.message.Message, pep.per.mint.common.data.MessageSet, pep.per.mint.common.data.MessageSet)
	 */
	@Override
	public void control(MapControl ctrl, Map<String, List<Element>> inputElementsMap, Element outputParentElement, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {
		
		
		MapPath inputPath = ctrl.getInputPathList().get(0);
		String current = inputPath.getCurrent().toString();
		MapPath outputPath = ctrl.getOutputPath();
		
		if(logger.isDebugEnabled() && logBuffer != null){
			
			try{
				Util.logToBuffer(logBuffer, LogVariables.logSerperator1);
				Util.logToBuffer(logBuffer, Util.join("매핑:move(소스패스:",inputPath.getFullPathString(),",타겟패스:", outputPath.getFullPathString(),")"));
				Util.logToBuffer(logBuffer, Util.join("inputElementsMap:",Util.toJSONString(inputElementsMap)));
				Util.logToBuffer(logBuffer, Util.join("outputParentElement:",outputParentElement.getFieldPath() ,":",Util.toJSONString(outputParentElement)));
			}catch(Exception e){
				logger.debug("debug error:",e);
			}
		}
		
	
		FieldDefinition sfd = MessageSetUtil.getFieldDefinition(srcMsgSet, PathUtil.getPathString(inputPath.getFullPath()));
		
		if(sfd == null) {
			throw new Exception(
					Util.join(
							"매핑예외:move(소스패스:",
							inputPath.getFullPathString(),
							",타겟패스:", 
							outputPath.getFullPathString(),
							"):소스필드정의에 해당하는 필드 정의가 존재하지 않습니다."
					));
		}
		
		if(sfd.isFieldSet()){
			throw new Exception(Util.join("매핑예외:move(소스패스:",inputPath.getFullPathString(),",타겟패스:", outputPath.getFullPathString(),"):move 컨트롤 매핑은 소스필드가 필드셋일경우는 허용하지 않는다."));
		}
		
		
		FieldDefinition tfd = MessageSetUtil.getFieldDefinition(tagMsgSet, PathUtil.getPathString(outputPath.getFullPath()));
		if(tfd == null) {
			throw new Exception(
					Util.join(
							"매핑예외:move(소스패스:",
							inputPath.getFullPathString(),
							",타겟패스:", 
							outputPath.getFullPathString(),
							"):타겟필드정의에 해당하는 필드 정의가 존재하지 않습니다."
							));
		}
		
		
		if(tfd.isFieldSet()){
			throw new Exception(Util.join("매핑예외:move(소스패스:",inputPath.getFullPathString(),",타겟패스:", outputPath.getFullPathString(),"):move 컨트롤 매핑은 타겟필드가 필드셋일경우는 허용하지 않는다."));
		}
		
		int inputOccurs = sfd.getOccursType();
		int outputOccurs = tfd.getOccursType();
		 
		
		String inputPathString = inputPath.getFullPathString();
		List<Element> elements = inputElementsMap.get(inputPathString);//MoveContrller로는 Element가 하나만 전달된:1:1 매핑만 허용됨
		 
		if(logger.isDebugEnabled() && logBuffer != null){
			try{
				Util.logToBuffer(logBuffer, Util.join("map elements:", Util.toJSONString(elements)));
			}catch(Exception e){
				logger.debug("debug error:",e);
			}
		} 
		
		
		switch(inputOccurs){
		case FieldDefinition.OCCURS_TYPE_01 :
		case FieldDefinition.OCCURS_TYPE_11 :
			//------------------------------------------------------
			//MaxOccurs가 1번이하로 발생할 경우 처리
			//------------------------------------------------------
			if(elements != null && elements.size() > 0){
				Element element = elements.get(0);
				Serializable value = element.getValue();
				if(value != null){
					Serializable copy = MessageUtil.copy(value);
					String outputCurrentPathString = outputPath.getCurrent().toString();
					if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
						outputParentElement.setValue(copy);
					}else{
						MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(copy);
					}
				}
			}
	
			break;
		case FieldDefinition.OCCURS_TYPE_0N :
		case FieldDefinition.OCCURS_TYPE_1N :
			//------------------------------------------------------
			//MaxOccurs가 2번이상으로 발생할 경우 처리
			//------------------------------------------------------
			//input 필드요소가 배열일경우 처리로 만약 
			//만약 output 필드정의가 0,1 발생유형이면, input 필드데이터이의 첫번째만 move 하도록 한다.
			//output 필드정의가 N이상 발생유형이면, input필드데이터이의 모든 배열요소를 move한다.
			//향휴에 FieldPath 속성에 배열 인덱스 표현을 넣고 input : output 이 N:1 일 경우
			//input필드의 패스에 지정된 특정 인덱스가 지정하는 값을 output 에 move 하는 것도 고려해보자 
			if(outputOccurs == FieldDefinition.OCCURS_TYPE_01 || outputOccurs == FieldDefinition.OCCURS_TYPE_11){
				//1)output 필드정의가 0,1 발생유형이면, input 필드데이터이의 첫번째만 move 하도록 한다.
				if(elements != null && elements.size() > 0){
					Element element = elements.get(0);
					Serializable value = element.getValue();
					if(value != null){
						Serializable copy = MessageUtil.copy(value);
						String outputCurrentPathString = outputPath.getCurrent().toString();
						if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
							outputParentElement.setValue(copy);
						}else{
							MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(copy);
						}
					}
				}
			}else{
				//2)output 필드정의가 N이상 발생유형이면, input필드데이터이의 모든 배열요소를 move한다.
				if(elements != null && elements.size() > 0){
					for (Element element : elements) {
						Serializable value = element.getValue();
						if(value != null){
							Serializable copy = MessageUtil.copy(value);
							String outputCurrentPathString = outputPath.getCurrent().toString();
							if(MapPath.PATH_CHAR_CURRENT.equals(outputCurrentPathString)){
								outputParentElement.setValue(copy);
							}else{
								MessageUtil.createElement(outputParentElement, outputCurrentPathString, true).setValue(copy);
							}
							
						}
					}
				}
			}
			break;
			
		default:
			break;
		}		
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.MapController#getType()
	 */
	@Override
	public Integer getType() {
		// TODO Auto-generated method stub
		return MoveControl.CTRL_TYPE_MOVE;
	}

}
