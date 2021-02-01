/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;

/**
 * @author Solution TF
 *
 */
public interface MapController {
	 
	public Integer getType();
	
	/**
	 * 
	 * @param ctrl
	 * @param srcMsg
	 * @param tagMsg
	 * @param srcMsgSet
	 * @param tagMsgSet
	 * @throws Exception
	 */
	public void control(MapControl ctrl, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception;
 
	

	
	/**
	 * 
	 * @param ctrl
	 * @param inputElements
	 * @param outputParentElement
	 * @param srcMsg
	 * @param tagMsg
	 * @param srcMsgSet
	 * @param tagMsgSet
	 * @param logBuffer
	 * @throws Exception
	 */
	public void control(MapControl ctrl, Map<String, List<Element>> inputElements, Element outputParentElement, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception;
	
			
	 
}
