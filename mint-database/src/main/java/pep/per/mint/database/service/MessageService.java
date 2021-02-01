package pep.per.mint.database.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.DelimiterFieldDefinition;
import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.FieldSetDetail;
import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.data.JsonFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.MessageSetDetail;
import pep.per.mint.common.data.SystemField;
import pep.per.mint.common.data.XMLFieldDefinition;
import pep.per.mint.common.data.map.ForeachControl;
import pep.per.mint.common.data.map.FunctionControl;
import pep.per.mint.common.data.map.JoinCondition;
import pep.per.mint.common.data.map.JoinControl;
import pep.per.mint.common.data.map.MapControl;
import pep.per.mint.common.data.map.MapFunctionDefinition;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.data.map.MoveControl;
import pep.per.mint.common.data.map.MsgMap;
import pep.per.mint.common.exception.NotFoundException;
import pep.per.mint.common.exception.UnsupportedTypeException;
import pep.per.mint.common.mapper.MapController;
import pep.per.mint.common.message.PathInitializer;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mybatis.dao.MessageDao;
import pep.per.mint.database.mybatis.persistance.MessageMapper;

@Service
public class MessageService {
	
	final static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired
	MessageDao messageDao;
	
	/**
	 * MessageSet 정로를 메시지셋 id값으로 조회하여 리턴한다.
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public MessageSet getMessageSet(Object id) throws Exception{
		
		MessageSet msgSet = messageMapper.getMessageSet(id);
		
		if(msgSet == null) { 
			Object [] arguments = {getClass(),"getMessageSet", MessageSet.class, id};
			String errmsg = ExceptionResource.getString("DBE0001", arguments);
			throw new NotFoundException(errmsg);
		}

		//msgset properties 설정 
		List<Map<String,String>> propertiyMapList = messageMapper.getMessageSetProperties(id);
		if(propertiyMapList != null ){
			Properties properties = new Properties();
			for (Map<String, String> map : propertiyMapList) {
				String key = map.get("key");
				String value = map.get("value");
				properties.put(key, value);
			}
			msgSet.setProperties(properties);
		}
		
		List<Map<String, Object>> fieldDefinitionMapList = messageMapper.selectMessageSetDetail(id);
		int type = msgSet.getType();
		switch(type){
		case MessageSet.MSG_FORMAT_TYPE_FIXED :
			setFixedTypeFieldDefinitionMap(fieldDefinitionMapList, msgSet);
			break;
		case MessageSet.MSG_FORMAT_TYPE_XML :
			setXMLTypeFieldDefinitionMap(fieldDefinitionMapList, msgSet);
			//throw new UnsupportedTypeException("XML(" + type + ") TYPE 메시지셋은 아직 지원하지 않는다잉.");
			break;
		case MessageSet.MSG_FORMAT_TYPE_DELIM :
			setDelimiterTypeFieldDefinitionMap(fieldDefinitionMapList, msgSet);
			//throw new UnsupportedTypeException("DELIM(" + type + ") TYPE 메시지셋은 아직 지원하지 않는다잉.");
			break;
		case MessageSet.MSG_FORMAT_TYPE_JSON :
			setJsonTypeFieldDefinitionMap(fieldDefinitionMapList, msgSet);
			//throw new UnsupportedTypeException("JSON(" + type + ") TYPE 메시지셋은 아직 지원하지 않는다잉.");
			break;
		default:
			throw new UnsupportedTypeException("미정의된 메시지셋 타입(" + type + ") 타입 코드값 확인해봐바");
			//break;
		}
		PathInitializer.initializePath(msgSet, this, null);
		
		setSystemFieldInfo(msgSet);
		
		return msgSet;
	}
 

	/**
	 * 파라메터로 전달받은 ID의 MsgMap 인스턴스를 조회하여 리턴한다. 
	 * @param id MsgMap ID
	 * @return MsgMap Instance
	 * @throws Exception
	 */
	public MsgMap getMessageMap(Object id) throws Exception{
		//--------------------------------------------------
		//1.메시지맵 마스터 조회
		//--------------------------------------------------
		MsgMap msgMap = messageMapper.getMessageMap(id);
		if(msgMap == null) { 
			Object [] arguments = {getClass(),"getMessageMap", MsgMap.class, id};
			String errmsg = ExceptionResource.getString("DBE0001", arguments);
			throw new NotFoundException(errmsg);
		}
		
		List<MapControl> mapControls = msgMap.getMapControls();
		//--------------------------------------------------
		//2.맵컨트롤 리스트 조회
		//--------------------------------------------------
		List<Map<String,Object>> controlList = messageMapper.selectMapControlList(id);
		for (Map<String, Object> map : controlList) {
			MapControl control = createMapControl(map);
			mapControls.add(control);
		}
		 
		return msgMap;
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	private MapControl createMapControl(Map<String, Object> map) throws Exception {
		MapControl mapControl = null;

		String controlId = (String)map.get("id");
		int controlType = (Integer)map.get("type");
		
		switch(controlType){
		case MapControl.CTRL_TYPE_MOVE :
		{
			mapControl = new MoveControl();
			List<MapPath> inputPathList = messageMapper.selectMapPathList(controlId, "I");
			if(inputPathList == null || inputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 inputPath 가 존재하지 않습니다."));
			}
			mapControl.setInputPathList(inputPathList);
			List<MapPath> outputPathList = messageMapper.selectMapPathList(controlId, "O");
			if(outputPathList == null || outputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 outputPath 가 존재하지 않습니다."));
			}
			mapControl.setOutputPath(outputPathList.get(0));
		}
		break;
		case MapControl.CTRL_TYPE_FOREACH : 
		{
			mapControl = new ForeachControl();
			List<MapPath> inputPathList = messageMapper.selectMapPathList(controlId, "I");
			if(inputPathList == null || inputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 inputPath 가 존재하지 않습니다."));
			}
			mapControl.setInputPathList(inputPathList);
			List<MapPath> outputPathList = messageMapper.selectMapPathList(controlId, "O");
			if(outputPathList == null || outputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 outputPath 가 존재하지 않습니다."));
			}
			mapControl.setOutputPath(outputPathList.get(0));
			
			{
				List<Map<String,Object>> childControlList = messageMapper.selectChildMapControlList(controlId);
				if(childControlList != null && childControlList.size() > 0){
					List<MapControl> children = new ArrayList<MapControl>();
					for (Map<String, Object> childMap : childControlList) {
						MapControl control = createMapControl(childMap);
						children.add(control);
					}
					mapControl.setChildren(children);
				}
			}
		}
		break;
		case MapControl.CTRL_TYPE_JOIN :
		{
			mapControl = new JoinControl();
			List<MapPath> inputPathList = messageMapper.selectMapPathList(controlId, "I");
			if(inputPathList == null || inputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 inputPath 가 존재하지 않습니다."));
			}
			mapControl.setInputPathList(inputPathList);
			List<MapPath> outputPathList = messageMapper.selectMapPathList(controlId, "O");
			if(outputPathList == null || outputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 outputPath 가 존재하지 않습니다."));
			}
			mapControl.setOutputPath(outputPathList.get(0));
			
			//--------------------------------------------------------------------------
			//join condition setting
			//--------------------------------------------------------------------------
			{
				JoinControl jc = (JoinControl)mapControl;
				Map<String,JoinCondition> joinConditionMap = getJoinConditionMap(controlId);
				jc.setJoinConditionMap(joinConditionMap);
			}
			
			//--------------------------------------------------------------------------
			//child map controlsetting
			//--------------------------------------------------------------------------
			{
				List<Map<String,Object>> childControlList = messageMapper.selectChildMapControlList(controlId);
				if(childControlList != null && childControlList.size() > 0){
					List<MapControl> children = new ArrayList<MapControl>();
					for (Map<String, Object> childMap : childControlList) {
						MapControl control = createMapControl(childMap);
						children.add(control);
					}
					mapControl.setChildren(children);
				}
			}
			
			
			
		}
		break;
		
		case MapControl.CTRL_TYPE_FUNCTION:
		{
			mapControl = new FunctionControl();
			List<MapPath> inputPathList = messageMapper.selectMapPathList(controlId, "I");
			//20141127
			//SetValue 함수의 경우 input이 필요없으므로 이부분의 예외 처리는 주석처리함.
			/*if(inputPathList == null || inputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 inputPath 가 존재하지 않습니다."));
			}*/
			mapControl.setInputPathList(inputPathList);
			List<MapPath> outputPathList = messageMapper.selectMapPathList(controlId, "O");
			if(outputPathList == null || outputPathList.size() == 0) {
				throw new Exception(Util.join("MapControl[id:",controlId,"]의 outputPath 가 존재하지 않습니다."));
			}
			mapControl.setOutputPath(outputPathList.get(0));
			
			//---------------------------------------------------
			//함수정의 세팅
			//---------------------------------------------------
			{
				FunctionControl fc = (FunctionControl)mapControl;
				MapFunctionDefinition fmfd = messageMapper.selectMapFunction(controlId);
				if(fmfd == null) {
					throw new Exception(Util.join("FunctionControl[id:",controlId,"]의 MapFunctionDefinition 가 존재하지 않습니다."));
				}
				String functionId = (String)fmfd.getId();
				List<Map<String,Object>> propertiyMapList  = messageMapper.selectMapFunctionArgs(functionId);
				if(propertiyMapList != null && propertiyMapList.size() > 0) {
					Map params = new LinkedHashMap();
					for (Map<String, Object> paramMap : propertiyMapList) {
						Object key = paramMap.get("key");
						Object value = paramMap.get("value");
						params.put(key, value);
					}
					fmfd.setParams(params);
				}
				
				fc.setFunctionDefinition(fmfd);
			}
		}
		break;
		
		default :
			throw new Exception(Util.join("UnsupportedMapContrl:MapControl[",controlId,"]:Type[",controlType,"]은 지원하지 않는 컨트롤 타입입니다."));
			
		}
		
		
		
		return mapControl;
	}

	/**
	 * @param controlId
	 * @return
	 * @throws Exception 
	 */
	private Map<String, JoinCondition> getJoinConditionMap(String controlId) throws Exception {
		Map<String, JoinCondition> joinContionMap = new HashMap<String, JoinCondition>();
		List<Map<String, Object>> conditionMapList = messageMapper.selectJoinConditionList(controlId);
		for (Map<String, Object> map : conditionMapList) {
			int type = (Integer)map.get("type");
			String pathId = (String)map.get("pathId");
			String parent = (String)map.get("parent");
			String current = (String)map.get("current");
			MapPath path = new MapPath(parent, current);
			String pathString = path.getFullPathString();
			JoinCondition jc = new JoinCondition(type);
			
			if(type == JoinCondition.JC_SOME_INDEX){
				List<Integer> someIndeces = messageMapper.selectJoinIndexList(controlId, pathId);
				jc.setSomeIndeces(someIndeces);
			}else if(type == JoinCondition.JC_FILTER){
				throw new Exception(Util.join("UnsupportedJoinCondition:MapControl[",controlId,"]:JoinCondition.JC_FILTER는 지원하지 않는 조인컨디션입니다."));
			}
			 
			
			joinContionMap.put(pathString, jc);
			
		}
		
		return joinContionMap;
	}


	private void setSystemFieldInfo(MessageSet msgSet) {
		LinkedHashMap <Integer,List<SystemField>> map = msgSet.getSystemFieldMap();
		Object msgSetId = msgSet.getId();
		List<SystemField> list = messageMapper.getSystemField(msgSetId);
		for (SystemField systemField : list) {
			Integer key = systemField.getUsage();
			List<SystemField> value = map.get(key);
			if(value == null){
				value = new ArrayList<SystemField>();
				map.put(key, value);
			}
			value.add(systemField);
		}
	}


	private void setFixedTypeFieldDefinitionMap(List<Map<String, Object>> fieldDefinitionMapList, MessageSet msgSet) {
		LinkedHashMap<Object, FieldDefinition> fdm = msgSet.getFieldDefinitionMap();
		for (Map<String, Object> map : fieldDefinitionMapList) {
			setFixedTypeFieldDefinition(map, fdm);
		}	
	}


	private void setFixedTypeFieldDefinition(Map<String, Object> map, LinkedHashMap<Object, FieldDefinition> fdm) {
		String fieldId = (String)map.get("id");
		Boolean isFieldSet = (Boolean)map.get("isFieldSet");
		Boolean required = (Boolean)map.get("required");
		String lengthFieldName = (String)map.get("lengthFieldName");
		String repeatFieldName = (String)map.get("repeatFieldName");
		int repeatCount  = (Integer)map.get("repeatCount");
		int occursType = (Integer)map.get("occursType");
		int seq = (Integer)map.get("seq");
		//logger.info("fieldId:"+fieldId + ", isFieldSet:" + isFieldSet);
		if(isFieldSet){
			
			FieldSetDefinition fsd = messageMapper.getFieldSetDefinition(fieldId);
			fsd.setRequired(required);
			fsd.setRepeatFieldName(repeatFieldName);
			fsd.setRepeatCount(repeatCount);
			fsd.setSeq(seq);
			fsd.setOccursType(occursType);			

			String fieldSetName = fsd.getName();
			fdm.put(fieldSetName, fsd);
			
			LinkedHashMap<Object, FieldDefinition> fsfdm = fsd.getFieldDefinitionMap();
			
			List<Map<String, Object>> fieldSetDefinitionMapList = messageMapper.selectFieldSetDetail(fieldId);
			for (Map<String, Object> m : fieldSetDefinitionMapList) {
				setFixedTypeFieldDefinition(m, fsfdm);
			}

		}else{
			FixedLengthFieldDefinition flfd = messageMapper.getFixedLengthFieldDefinition(fieldId);
			flfd.setRequired(required);
			flfd.setLengthFieldName(lengthFieldName);
			flfd.setRepeatFieldName(repeatFieldName);
			flfd.setRepeatCount(repeatCount);
			flfd.setSeq(seq);
			flfd.setOccursType(occursType);
			fdm.put(flfd.getName(), flfd);
			
		}
	}
	
	
	private void setXMLTypeFieldDefinitionMap(List<Map<String, Object>> fieldDefinitionMapList, MessageSet msgSet) {
		LinkedHashMap<Object, FieldDefinition> fdm = msgSet.getFieldDefinitionMap();
		for (Map<String, Object> map : fieldDefinitionMapList) {
			setXMLTypeFieldDefinition(map, fdm);
		}	
	}


	private void setXMLTypeFieldDefinition(Map<String, Object> map, LinkedHashMap<Object, FieldDefinition> fdm) {
		
		String fieldId = (String)map.get("id");
		Boolean isFieldSet = (Boolean)map.get("isFieldSet");
		Boolean required = (Boolean)map.get("required");
		String repeatFieldName = (String)map.get("repeatFieldName");
		int repeatCount  = (Integer)map.get("repeatCount");
		int occursType = (Integer)map.get("occursType");
		int seq = (Integer)map.get("seq");
		
		//logger.info("fieldId:"+fieldId + ", isFieldSet:" + isFieldSet);
		if(isFieldSet){
			
			FieldSetDefinition fsd = messageMapper.getFieldSetDefinition(fieldId);
			fsd.setRequired(required);
			fsd.setRepeatFieldName(repeatFieldName);
			fsd.setRepeatCount(repeatCount);
			fsd.setSeq(seq);
			fsd.setOccursType(occursType);		
			
			String fieldSetName = fsd.getName();
			fdm.put(fieldSetName, fsd);
			
			LinkedHashMap<Object, FieldDefinition> fsfdm = fsd.getFieldDefinitionMap();
			
			List<Map<String, Object>> fieldSetDefinitionMapList = messageMapper.selectFieldSetDetail(fieldId);
			for (Map<String, Object> m : fieldSetDefinitionMapList) {
				setXMLTypeFieldDefinition(m, fsfdm);
			}

		}else{
			XMLFieldDefinition flfd = messageMapper.getXMLFieldDefinition(fieldId);
			flfd.setRequired(required);
			flfd.setRepeatFieldName(repeatFieldName);
			flfd.setRepeatCount(repeatCount);
			flfd.setSeq(seq);
			flfd.setOccursType(occursType);
			
			fdm.put(flfd.getName(), flfd);
			
		}
	}
	
	
	private void setJsonTypeFieldDefinitionMap(List<Map<String, Object>> fieldDefinitionMapList, MessageSet msgSet) {
		LinkedHashMap<Object, FieldDefinition> fdm = msgSet.getFieldDefinitionMap();
		for (Map<String, Object> map : fieldDefinitionMapList) {
			setJsonTypeFieldDefinition(map, fdm);
		}	
	}


	private void setJsonTypeFieldDefinition(Map<String, Object> map, LinkedHashMap<Object, FieldDefinition> fdm) {
		
		String fieldId = (String)map.get("id");
		Boolean isFieldSet = (Boolean)map.get("isFieldSet");
		Boolean required = (Boolean)map.get("required");
		String repeatFieldName = (String)map.get("repeatFieldName");
		int repeatCount  = (Integer)map.get("repeatCount");
		int occursType = (Integer)map.get("occursType");
		int seq = (Integer)map.get("seq");
		
		//logger.info("fieldId:"+fieldId + ", isFieldSet:" + isFieldSet);
		if(isFieldSet){
			
			FieldSetDefinition fsd = messageMapper.getFieldSetDefinition(fieldId);
			fsd.setRequired(required);
			fsd.setRepeatFieldName(repeatFieldName);
			fsd.setRepeatCount(repeatCount);
			fsd.setSeq(seq);
			fsd.setOccursType(occursType);
			
			String fieldSetName = fsd.getName();
			fdm.put(fieldSetName, fsd);
			
			LinkedHashMap<Object, FieldDefinition> fsfdm = fsd.getFieldDefinitionMap();
			
			List<Map<String, Object>> fieldSetDefinitionMapList = messageMapper.selectFieldSetDetail(fieldId);
			for (Map<String, Object> m : fieldSetDefinitionMapList) {
				setJsonTypeFieldDefinition(m, fsfdm);
			}

		}else{
			JsonFieldDefinition flfd = messageMapper.getJsonFieldDefinition(fieldId);

			flfd.setRequired(required);
			flfd.setRepeatFieldName(repeatFieldName);
			flfd.setRepeatCount(repeatCount);
			flfd.setSeq(seq);
			flfd.setOccursType(occursType);
			
			fdm.put(flfd.getName(), flfd);
			
		}
	}
	
	
	private void setDelimiterTypeFieldDefinitionMap(List<Map<String, Object>> fieldDefinitionMapList, MessageSet msgSet) {
		LinkedHashMap<Object, FieldDefinition> fdm = msgSet.getFieldDefinitionMap();
		for (Map<String, Object> map : fieldDefinitionMapList) {
			setDelimiterTypeFieldDefinition(map, fdm);
		}	
	}


	private void setDelimiterTypeFieldDefinition(Map<String, Object> map, LinkedHashMap<Object, FieldDefinition> fdm) {
		
		String fieldId = (String)map.get("id");
		Boolean isFieldSet = (Boolean)map.get("isFieldSet");
		Boolean required = (Boolean)map.get("required");
		String repeatFieldName = (String)map.get("repeatFieldName");
		int repeatCount  = (Integer)map.get("repeatCount");
		int occursType = (Integer)map.get("occursType");
		int seq = (Integer)map.get("seq");
		
		//logger.info("fieldId:"+fieldId + ", isFieldSet:" + isFieldSet);
		if(isFieldSet){
			
			FieldSetDefinition fsd = messageMapper.getFieldSetDefinition(fieldId);
			fsd.setRequired(required);
			fsd.setRepeatFieldName(repeatFieldName);
			fsd.setRepeatCount(repeatCount);
			fsd.setSeq(seq);
			fsd.setOccursType(occursType);
			
			String fieldSetName = fsd.getName();
			fdm.put(fieldSetName, fsd);
			
			LinkedHashMap<Object, FieldDefinition> fsfdm = fsd.getFieldDefinitionMap();
			
			List<Map<String, Object>> fieldSetDefinitionMapList = messageMapper.selectFieldSetDetail(fieldId);
			for (Map<String, Object> m : fieldSetDefinitionMapList) {
				setDelimiterTypeFieldDefinition(m, fsfdm);
			}

		}else{
			DelimiterFieldDefinition flfd = messageMapper.getDelimiterFieldDefinition(fieldId);
			flfd.setRequired(required);
			flfd.setRepeatFieldName(repeatFieldName);
			flfd.setRepeatCount(repeatCount);
			flfd.setSeq(seq);
			flfd.setOccursType(occursType);		
			fdm.put(flfd.getName(), flfd);
			
		}
	}
	
	public void loadMessageSetData(
			List<MessageSet> messageSets, 
			List<MessageSetDetail> messageSetDetails,
			List<FieldSetDefinition> fieldSetDefinitions,
			List<FieldSetDetail> fieldSetDetails,
			List<FixedLengthFieldDefinition> fieldDefinitions,
			List<SystemField> systemFields, boolean rollbackMode) throws Exception{
		
		SqlSession session = null;
		try{
			session = messageDao.openSession();
			messageDao.upsertMessageSetList(session, messageSets);
			messageDao.upsertFixedLengthFieldDefinitionList(session, fieldDefinitions);
			messageDao.upsertFieldSetDefinitionList(session, fieldSetDefinitions);
			messageDao.upsertFieldSetDetailList(session, fieldSetDetails);
			messageDao.upsertMessageSetDetailList(session, messageSetDetails);
			messageDao.upsertSystemFieldList(session, systemFields);
			if(rollbackMode) session.rollback();
			else session.commit();
		}catch(Exception e){
			session.rollback();
			throw e;
		}finally{
			if(session != null) {
				session.close();
			}
		}
		
		
	}
	
	
}
