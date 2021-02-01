/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.map;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MsgMapTest {

	static Logger logger = LoggerFactory.getLogger(MsgMapTest.class);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMove() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
	
			 
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.interfaceId","."), new MapPath("STDMSGV002.header.interfaceId",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.globalId","."), new MapPath("STDMSGV002.header.gid",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.sendTime","."), new MapPath("STDMSGV002.header.sendTime",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.senderId","."), new MapPath("STDMSGV002.header.senderId",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.data.data","."), new MapPath("STDMSGV002.body.msg",".")));
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	/**
	 * input msgSetId : mid1
	 * output msgSetId : mid2
	 * FunctionControl Demo : function name : Trim
	 */
	@Test
	public void testFunctionOfTrim() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
	
			FunctionControl fc = new FunctionControl(new MapPath("STDMSGV001.head.interfaceId","."), new MapPath("STDMSGV002.header.interfaceId","."));
			MapFunctionDefinition functionDefinition = new MapFunctionDefinition();
			functionDefinition.setName("Trim");
			functionDefinition.setType(MapFunctionDefinition.TYPE_JAVA);
			fc.setFunctionDefinition(functionDefinition);
			mapControls.add(fc);
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.globalId","."), new MapPath("STDMSGV002.header.gid",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.sendTime","."), new MapPath("STDMSGV002.header.sendTime",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.head.senderId","."), new MapPath("STDMSGV002.header.senderId",".")));
			
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.data.data","."), new MapPath("STDMSGV002.body.msg",".")));
			
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1-function-trim.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	@Test
	public void testForeach() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			ForeachControl foreachControl = new ForeachControl(new MapPath("STDMSGV001.head","."), new MapPath("STDMSGV002.header","."));
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","interfaceId"), new MapPath("STDMSGV002.header","interfaceId")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","globalId"), new MapPath("STDMSGV002.header", "gid")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","sendTime"), new MapPath("STDMSGV002.header", "sendTime")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","senderId"), new MapPath("STDMSGV002.header", "senderId")));
			
			mapControls.add(foreachControl);
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.data.data","."),new MapPath("STDMSGV002.body.msg",".")));
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1-foreach.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	@Test
	public void testFieldForeach() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			ForeachControl foreachControl = new ForeachControl(new MapPath("STDMSGV001.head.interfaceId","."), new MapPath("STDMSGV002.header.interfaceId","."));
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head.interfaceId","."), new MapPath("STDMSGV002.header.interfaceId",".")));
			
			mapControls.add(foreachControl);
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1-foreach-field.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	

	@Test
	public void testForeachAndMoveAndFunction() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			ForeachControl foreachControl = new ForeachControl(new MapPath("STDMSGV001.head","."), new MapPath("STDMSGV002.header","."));
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			
			FunctionControl fc = new FunctionControl(new MapPath("STDMSGV001.head","interfaceId"), new MapPath("STDMSGV002.header","interfaceId"));
			
			MapFunctionDefinition functionDefinition = new MapFunctionDefinition();
			functionDefinition.setName("Count");
			functionDefinition.setType(MapFunctionDefinition.TYPE_JAVA);
			
			fc.setFunctionDefinition(functionDefinition);
			subControls.add(fc);
			//subControls.add(new MoveControl(new MapPath("STDMSGV001.head","interfaceId"), new MapPath("STDMSGV002.header","interfaceId")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","globalId"), new MapPath("STDMSGV002.header", "gid")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","sendTime"), new MapPath("STDMSGV002.header", "sendTime")));
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","senderId"), new MapPath("STDMSGV002.header", "senderId")));
			
			mapControls.add(foreachControl);
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.data.data","."),new MapPath("STDMSGV002.body.msg",".")));
			
			 
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1-foreach-field.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	@Test
	public void testForeachAndConcat() {
		try{
			MsgMap map = new MsgMap();
			map.setId("mapid1");
			String inputMsgSetId = "mid1";
			String outputMsgSetId = "mid2";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			ForeachControl foreachControl = new ForeachControl(new MapPath("STDMSGV001.head","."), new MapPath("STDMSGV002.header","."));
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			
			FunctionControl fc = new FunctionControl(new MapPath("STDMSGV001.head","interfaceId"), new MapPath("STDMSGV002.header","interfaceId"));
			MapFunctionDefinition count = new MapFunctionDefinition();
			count.setName("Count");
			count.setType(MapFunctionDefinition.TYPE_JAVA);
			fc.setFunctionDefinition(count);
			subControls.add(fc);

			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","globalId"), new MapPath("STDMSGV002.header", "gid")));
			
			List<MapPath> inputPaths = new ArrayList<MapPath>();
			inputPaths.add(new MapPath("STDMSGV001.head","sendTime"));
			inputPaths.add(new MapPath("STDMSGV001.head","senderId"));
			FunctionControl ccfc = new FunctionControl(inputPaths, new MapPath("STDMSGV002.header","sendTime"));
			MapFunctionDefinition concat = new MapFunctionDefinition();
			concat.setName("Concat");
			concat.setType(MapFunctionDefinition.TYPE_JAVA);
			ccfc.setFunctionDefinition(concat);
			subControls.add(ccfc);

			 
			subControls.add(new MoveControl(new MapPath("STDMSGV001.head","senderId"), new MapPath("STDMSGV002.header", "senderId")));
			
			mapControls.add(foreachControl);
			mapControls.add(new MoveControl(new MapPath("STDMSGV001.data.data","."),new MapPath("STDMSGV002.body.msg",".")));
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/msgmap-mapid1-foreach-concat.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	
	@Test
	public void testCerateCompaniesForeachMap() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-companies");
			String inputMsgSetId = "sample-msgset-companies-src";
			String outputMsgSetId = "sample-msgset-companies-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			MapPath inputPathCompany = new MapPath("companies","company");
			MapPath outputPathCompany = new MapPath("companies","company");
			ForeachControl foreachControl = new ForeachControl(inputPathCompany, outputPathCompany);
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			subControls.add(new MoveControl(new MapPath(inputPathCompany,"name"), new MapPath(outputPathCompany,"name")));
			subControls.add(new MoveControl(new MapPath(inputPathCompany,"phone"), new MapPath(outputPathCompany,"phone")));
			subControls.add(new MoveControl(new MapPath(inputPathCompany,"foundationDate"), new MapPath(outputPathCompany,"foundationDate")));
			
			MapPath inputPathDept = new MapPath(inputPathCompany,"departments.department");
			MapPath outputPathDept = new MapPath(outputPathCompany,"departments.department");
			ForeachControl foreachControl1 = new ForeachControl(inputPathDept, outputPathDept);
			List<MapControl> subControls1 = new ArrayList<MapControl>(); 
			
			foreachControl1.setChildren(subControls1);
			subControls.add(foreachControl1);
			subControls1.add(new MoveControl(new MapPath(inputPathDept,"name"), new MapPath(outputPathDept,"name")));
			
			MapPath inputPathEmployee = new MapPath(inputPathDept,"employees.employee");
			MapPath outputPathEmployee = new MapPath(outputPathDept,"employees.employee");
			
			ForeachControl foreachControl2 = new ForeachControl(inputPathEmployee, outputPathEmployee);
			List<MapControl> subControls2 = new ArrayList<MapControl>(); 
			
			foreachControl2.setChildren(subControls2);
			subControls1.add(foreachControl2);
			subControls2.add(new MoveControl(new MapPath(inputPathEmployee,"name"), new MapPath(outputPathEmployee,"name")));
			 
			mapControls.add(foreachControl);
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-companies.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	
	@Test
	public void testCerateCompaniesForeachAndMoveAndFunctionMap() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-companies");
			String inputMsgSetId = "sample-msgset-companies-src";
			String outputMsgSetId = "sample-msgset-companies-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			MapPath inputPathCompany = new MapPath("companies","company");
			MapPath outputPathCompany = new MapPath("companies","company");
			ForeachControl foreachControl = new ForeachControl(inputPathCompany, outputPathCompany);
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			foreachControl.setChildren(subControls);
			
			
			MapFunctionDefinition functionDefinition = new MapFunctionDefinition();
			functionDefinition.setName("Trim");
			functionDefinition.setType(MapFunctionDefinition.TYPE_JAVA);
			FunctionControl fc = new FunctionControl(new MapPath(inputPathCompany,"name"), new MapPath(inputPathCompany,"name"));
			fc.setFunctionDefinition(functionDefinition);
			subControls.add(fc);

			subControls.add(new MoveControl(new MapPath(inputPathCompany,"phone"), new MapPath(outputPathCompany,"phone")));
			subControls.add(new MoveControl(new MapPath(inputPathCompany,"foundationDate"), new MapPath(outputPathCompany,"foundationDate")));
			
			MapPath inputPathDept = new MapPath(inputPathCompany,"departments.department");
			MapPath outputPathDept = new MapPath(outputPathCompany,"departments.department");
			ForeachControl foreachControl1 = new ForeachControl(inputPathDept, outputPathDept);
			List<MapControl> subControls1 = new ArrayList<MapControl>(); 
			
			foreachControl1.setChildren(subControls1);
			subControls.add(foreachControl1);
			subControls1.add(new MoveControl(new MapPath(inputPathDept,"name"), new MapPath(outputPathDept,"name")));
			
			MapPath inputPathEmployee = new MapPath(inputPathDept,"employees.employee");
			MapPath outputPathEmployee = new MapPath(outputPathDept,"employees.employee");
			
			ForeachControl foreachControl2 = new ForeachControl(inputPathEmployee, outputPathEmployee);
			List<MapControl> subControls2 = new ArrayList<MapControl>(); 
			
			foreachControl2.setChildren(subControls2);
			subControls1.add(foreachControl2);
			subControls2.add(new MoveControl(new MapPath(inputPathEmployee,"name"), new MapPath(outputPathEmployee,"name")));
			 
			mapControls.add(foreachControl);
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-foreach-move-function-companies.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	
	@Test
	public void testCerateCompanyMoveMap() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-company");
			String inputMsgSetId = "sample-msgset-company-src";
			String outputMsgSetId = "sample-msgset-company-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			
			
			
			mapControls.add(new MoveControl(new MapPath("company","name"), new MapPath("company","name")));
			mapControls.add(new MoveControl(new MapPath("company","phone"), new MapPath("company","phone")));
			mapControls.add(new MoveControl(new MapPath("company","foundationDate"), new MapPath("company","foundationDate")));
			 
		 
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-company.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	@Test
	public void testCerateCompanyMoveAndFunctionMap() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-company");
			String inputMsgSetId = "sample-msgset-company-src";
			String outputMsgSetId = "sample-msgset-company-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			
			MapFunctionDefinition functionDefinition = new MapFunctionDefinition();
			functionDefinition.setName("Trim");
			functionDefinition.setType(MapFunctionDefinition.TYPE_JAVA);
			FunctionControl fc = new FunctionControl(new MapPath("company","name"), new MapPath("company","name"));
			fc.setFunctionDefinition(functionDefinition);
			
			
			mapControls.add(fc);
			mapControls.add(new MoveControl(new MapPath("company","phone"), new MapPath("company","phone")));
			mapControls.add(new MoveControl(new MapPath("company","foundationDate"), new MapPath("company","foundationDate")));
			 
		 
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-function-company.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	@Test
	public void testCerateCompaniesJoin() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-companies-join");
			String inputMsgSetId = "sample-msgset-companies2-src";
			String outputMsgSetId = "sample-msgset-companies-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			MapPath inputPathCompany1 = new MapPath("companies","company");
			MapPath inputPathCompany2 = new MapPath("companies","company2");
			MapPath outputPathCompany = new MapPath("companies","company");
			
			
			JoinControl jc = new JoinControl();
			List<MapPath> inputPathList = new ArrayList<MapPath>();
			inputPathList.add(inputPathCompany1);
			inputPathList.add(inputPathCompany2);
			
			jc.setInputPathList(inputPathList);
			jc.setOutputPath(outputPathCompany);
			
			Map<String, JoinCondition> joinConditionMap = new HashMap<String, JoinCondition>();
			jc.setJoinConditionMap(joinConditionMap);
			joinConditionMap.put("companies.company2", new JoinCondition(JoinCondition.JC_SAME_INDEX));
			
			
			List<MapControl> subControls = new ArrayList<MapControl>(); 
			
			jc.setChildren(subControls);
			
			
			
			List<MapPath> inputPaths = new ArrayList<MapPath>();
			inputPaths.add(new MapPath(inputPathCompany1,"name"));
			inputPaths.add(new MapPath(inputPathCompany2,"name"));
			FunctionControl fc = new FunctionControl(inputPaths, new MapPath(outputPathCompany,"name"));
			MapFunctionDefinition concat = new MapFunctionDefinition();
			concat.setName("Concat");
			concat.setType(MapFunctionDefinition.TYPE_JAVA);
			fc.setFunctionDefinition(concat);
			subControls.add(fc);
			 
			subControls.add(new MoveControl(new MapPath(inputPathCompany1,"phone"), new MapPath(outputPathCompany,"phone")));
			subControls.add(new MoveControl(new MapPath(inputPathCompany1,"foundationDate"), new MapPath(outputPathCompany,"foundationDate")));
			
			MapPath inputPathDept = new MapPath(inputPathCompany1,"departments.department");
			MapPath outputPathDept = new MapPath(outputPathCompany,"departments.department");
			ForeachControl foreachControl1 = new ForeachControl(inputPathDept, outputPathDept);
			List<MapControl> subControls1 = new ArrayList<MapControl>(); 
			
			foreachControl1.setChildren(subControls1);
			subControls.add(foreachControl1);
			subControls1.add(new MoveControl(new MapPath(inputPathDept,"name"), new MapPath(outputPathDept,"name")));
			
			MapPath inputPathEmployee = new MapPath(inputPathDept,"employees.employee");
			MapPath outputPathEmployee = new MapPath(outputPathDept,"employees.employee");
			
			ForeachControl foreachControl2 = new ForeachControl(inputPathEmployee, outputPathEmployee);
			List<MapControl> subControls2 = new ArrayList<MapControl>(); 
			
			foreachControl2.setChildren(subControls2);
			subControls1.add(foreachControl2);
			subControls2.add(new MoveControl(new MapPath(inputPathEmployee,"name"), new MapPath(outputPathEmployee,"name")));
			 
			mapControls.add(jc);
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-companies-join.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
	
	
	@Test
	public void testCerateCompanyMoveAndFunctionSetValueMap() {
		try{
			MsgMap map = new MsgMap();
			map.setId("sample-msgmap-company");
			String inputMsgSetId = "sample-msgset-company-src";
			String outputMsgSetId = "sample-msgset-company-tag";
			map.setInputMsgSetId(inputMsgSetId);
			map.setOutputMsgSetId(outputMsgSetId);
			
			List<MapControl> mapControls = new ArrayList<MapControl>();
			map.setMapControls(mapControls);
			
			MapFunctionDefinition functionDefinition = new MapFunctionDefinition();
			functionDefinition.setName("SetValue");
			functionDefinition.setType(MapFunctionDefinition.TYPE_JAVA);
			
			Map params = new LinkedHashMap();
			params.put("company.name", "mocomsys");
			functionDefinition.setParams(params);
			
			MapPath inputPath = null;
			FunctionControl fc = new FunctionControl(inputPath, new MapPath("company","name"));
			fc.setFunctionDefinition(functionDefinition);
			
			
			mapControls.add(fc);
			mapControls.add(new MoveControl(new MapPath("company","phone"), new MapPath("company","phone")));
			mapControls.add(new MoveControl(new MapPath("company","foundationDate"), new MapPath("company","foundationDate")));
			 
		 
			
			String mapstring = Util.toJSONString(map);
			logger.debug(Util.join("msgmap:",mapstring));
			
			FileOutputStream fos = new FileOutputStream("./data/sample/sample-msgmap-function-setvalue-company.json");
			fos.write(mapstring.getBytes("UTF-8"));
			fos.flush();
			fos.close();
			
		}catch(Exception e){
			logger.debug("",e);
		}
	}
	
}
