/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.map.MapControl; 
import pep.per.mint.common.message.Message;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MapControllerFacade {
	
	public static Logger logger = LoggerFactory.getLogger(MapControllerFacade.class);

	Map<Integer,MapController> controllerMap = new HashMap<Integer,MapController>();
	 
	private static MapControllerFacade mcf;
	
	static Object mon = new Object();
	
	public static MapControllerFacade getInstance(){
		
		synchronized(mon){
			if(mcf == null){
				mcf = new MapControllerFacade();
				mcf.initialize();
			}
		}
		return mcf;
	}
	
	private MapControllerFacade(){
		super();
	}
	
	/**
	 * 
	 */
	public void initialize() {
		// TODO 초기 매핑 컨트롤러를 controllerMap에 등록하는 작업을 진행한다.
		// 신규 MapController가 추가되면 소스 수정이 없도록하기위해 
		// 설정파일에서 읽어와 등록될수 있도록 방안 모색
		/*MapController move = new MoveController();
		Integer key = move.getType();
		controllerMap.put(key, move);*/
		 
		controllerMap.put(MapControl.CTRL_TYPE_MOVE, new MoveController());
		controllerMap.put(MapControl.CTRL_TYPE_FOREACH, new ForeachController());
		controllerMap.put(MapControl.CTRL_TYPE_FUNCTION, new FunctionController());
		controllerMap.put(MapControl.CTRL_TYPE_JOIN, new JoinController());
	}

	public void control(MapControl ctrl, Message srcMsg, Message tagMsg, MessageSet srcMsgSet, MessageSet tagMsgSet, StringBuffer logBuffer) throws Exception {
		int controlType = ctrl.getType();
		MapController mc = controllerMap.get(controlType);
		if(mc == null) {
			Exception e = new Exception(Util.join("지원하지 않는 매핑컨트롤 유형입니다.[매핑컨트롤유형값:",controlType,"]"));
			if(logger.isDebugEnabled() && logBuffer != null){
				logBuffer
				.append(LogVariables.logPrefix)
				.append("[").append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS")).append("] ")
				.append(Util.join("예외:", e.getMessage()));
			}
			throw e;
		}
		mc.control(ctrl, srcMsg, tagMsg, srcMsgSet, tagMsgSet, logBuffer);
	}

	/**
	 * @param controlType
	 * @return
	 */
	public MapController getController(int controlType) {
		return controllerMap.get(controlType);
	} 
	
}
