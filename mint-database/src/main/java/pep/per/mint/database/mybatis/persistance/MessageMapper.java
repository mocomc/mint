package pep.per.mint.database.mybatis.persistance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.DelimiterFieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.FieldSetDetail;
import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.data.JsonFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.MessageSetDetail;
import pep.per.mint.common.data.SystemField;
import pep.per.mint.common.data.XMLFieldDefinition;
import pep.per.mint.common.data.map.MapFunctionDefinition;
import pep.per.mint.common.data.map.MapPath;
import pep.per.mint.common.data.map.MsgMap;

public interface MessageMapper {
	
	public MessageSet getMessageSet(Object id);
	
	public List<Map<String, String>> getMessageSetProperties(Object messageSetId) throws Exception;
	
	public List<Map<String, Object>> selectMessageSetDetail(Object id);
	
	public FixedLengthFieldDefinition getFixedLengthFieldDefinition(Object id);
	
	public XMLFieldDefinition getXMLFieldDefinition(Object id);
	
	public DelimiterFieldDefinition getDelimiterFieldDefinition(Object id);
	
	public List<Map<String, Object>> selectFieldSetDetail(Object id);
	
	public FieldSetDefinition getFieldSetDefinition(Object id);
	
	public List<SystemField> getSystemField(Object id);
	
	public void insertMessageSet(MessageSet messageSet) throws Exception;

	public void insertMessageSetDetail(MessageSetDetail messageSetDetail) throws Exception;

	public void insertFieldSetDefinition(FieldSetDefinition fieldSetDefinition) throws Exception;
	
	public void insertFieldSetDetail(FieldSetDetail fieldSetDetail) throws Exception;
	
	public void insertFixedLengthFieldDefinition(FixedLengthFieldDefinition fieldDefinition) throws Exception;

	public void insertSystemField(SystemField systemField) throws Exception;

	/**
	 * @param fieldId
	 * @return
	 */
	public JsonFieldDefinition getJsonFieldDefinition(Object fieldId);

	//=====================================================================================================================
	//start of message mapping function
	//---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 메시지맵ID로 MsgMap 조회
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MsgMap getMessageMap(Object id) throws Exception;
	
	/**
	 * 메시지맵ID로 메시지맵과 관련된 매핑컨드롤 정보 리스트 조회
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> selectMapControlList(Object id);
	
	/**
	 * MapControl의 input 또는 output MapPath를 조회한다.
	 * @param controlId
	 * @param type "I":input, "O":output
	 * @return
	 */
	public List<MapPath> selectMapPathList(@Param("controlId") Object controlId,  @Param("type") String type);
	
	/**
	 * parentCotrolId로 자식 매핑컨드롤 정보 리스트 조회
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> selectChildMapControlList(Object parentCotrolId);
	
	/**
	 * FuntionControl 과 관련된 MapFunctionDefinition을 리턴한다.
	 * @param id MapControl id
	 * @return
	 */
	public MapFunctionDefinition selectMapFunction(Object id);

	/**
	 * 함수 인수조회 
	 * @param functionId
	 * @return
	 */
	public List<Map<String, Object>> selectMapFunctionArgs(@Param("functionId") String functionId);
	
	/**
	 * JoinCondition 정보 리스트를 map control Id로 조회
	 * @param controlId
	 * @return
	 */
	public List<Map<String, Object>> selectJoinConditionList(@Param("controlId") Object controlId);
	
	/**
	 * JoinCondition type이 특정 인덱스 조인일 경우 조인 인덱스 리스트를 조회한다.
	 * @param controlId
	 * @param pathId
	 * @return
	 */
	public List<Integer> selectJoinIndexList(@Param("controlId") Object controlId, @Param("pathId") String pathId);
	 
	
}
