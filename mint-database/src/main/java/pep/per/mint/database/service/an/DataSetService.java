package pep.per.mint.database.service.an;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataMapItem;
import pep.per.mint.common.data.basic.dataset.DataMapItemField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.dataset.DataSetHistory;
import pep.per.mint.common.data.basic.dataset.DataSetInterfaceMap;
import pep.per.mint.common.data.basic.dataset.DataSetList;
import pep.per.mint.common.data.basic.dataset.DataSetMap;
import pep.per.mint.common.data.basic.dataset.DataSetTemp;
import pep.per.mint.common.data.basic.dataset.MetaField;
import pep.per.mint.common.util.CamelUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.DatabaseServiceException;
import pep.per.mint.database.exception.NotFoundDataException;
import pep.per.mint.database.exception.UnChangedDataException;
import pep.per.mint.database.mapper.an.DataSetMapper;
import pep.per.mint.database.service.co.CommonService;

/**
 * 데이터(메시지,전문 등) 구조를 등록하고 조회 관리하는 서비스(조회, 입력, 삭제, 수정)
 *
 * @since 201701
 * @version 1.0
 * @author whoana(Technical Architecture Team)
 */
@Service
public class DataSetService {

	@Autowired
	DataSetMapper dataSetMapper;

	/**
	 *
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataSetService.class);

	/**
	 * <pre>
	 * 데이터셋 리스트 조회 [REST-R01-AN-05-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	public DataSetList getDataSetList(Map<String, String> params) throws Exception{
		String delYn = params.get("delYn");
		if(delYn == null || delYn.equalsIgnoreCase("")){
			params.put("delYn", "N");
		}
		DataSetList list = dataSetMapper.getDataSetList(params);

		if(list != null){
			for(DataSet dataSet : list){
				List<DataField> fieldList = dataSet.getDataFieldList();
				for(DataField field : fieldList){
					String type = field.getType();
					if(DataField.TYPE_COMPLEX.equalsIgnoreCase(type)){
						String fieldSetId = field.getFieldSetId();
						DataSet fieldSet = getDataSet(fieldSetId, delYn);
						dataSet.getComplexTypeMap().put(fieldSetId, fieldSet);
					}
				}
			}
		}

		return list;
		//return dataSetMapper.getDataSetList(params);
	}

	/**
	 * <pre>
	 * 데이터셋 상세조회 [REST-R02-AN-05-01]
	 * </pre>
	 * @param dataSetId String 데이터셋ID
	 * @param delYn String 삭제구분
	 * @return DataSet
	 * @throws Exception
	 * @deprecated
	 */
	public DataSet getDataSet(String dataSetId, String delYn) throws Exception{

		DataSet dataSet = dataSetMapper.getDataSet(dataSetId, delYn);

		if(dataSet == null) throw new NotFoundDataException();

		List<DataField> fieldList = dataSet.getDataFieldList();
		for(DataField field : fieldList){
			String type = field.getType();
			if(DataField.TYPE_COMPLEX.equalsIgnoreCase(type)){
				String fieldSetId = field.getFieldSetId();
				DataSet fieldSet = getDataSet(fieldSetId, delYn);
				dataSet.getComplexTypeMap().put(fieldSetId, fieldSet);
			}
		}
		return dataSet;
	}

	/**
	 * <pre>
	 * 데이터셋 비교 [REST-S03-AN-05-01]
	 * <pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public DataSetMap getCompareDataSet(List<String> params) throws Exception{
		DataSetMap map = new DataSetMap();
		for(String dataSetId : params){
			DataSet dataSet = dataSetMapper.getDataSet(dataSetId, null);
			map.put(dataSetId, dataSet);
		}
		return map;
	}

	/**
	 * <pre>
	 * 데이터셋 버전 리스트 조회 [REST-R04-AN-05-01]
	 * <pre>
	 * @param dataSetId
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getDataSetVersionList(String dataSetId) throws Exception {
		return dataSetMapper.getDataSetVersionList(dataSetId);
	}

	/**
	 * <pre>
	 * 특정 버전 데이터셋 조회  [REST-R05-AN-05-01]
	 * <pre>
	 * @param dataSetId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public DataSet getDataSetByVersion(String dataSetId, String version) throws Exception {
		DataSetHistory dataSetHistory = dataSetMapper.getDataSetHistory(dataSetId,version);
		DataSet dataSet = Util.jsonToObject(dataSetHistory.getContents(), DataSet.class);
		return dataSet;
	}

	/**
	 * <pre>
	 * 버전, 데이터셋 map 조회  [REST-R06-AN-05-01]
	 * <pre>
	 * @param dataSetId
	 * @return
	 * @throws Exception
	 */
	public DataSetMap getDataSetMapByVersion(String dataSetId) throws Exception{
		List<DataSetHistory> dataSetHistoryList = dataSetMapper.getDataSetHistoryList(dataSetId);
		DataSetMap map = null;
		if(dataSetHistoryList != null){
			map = new DataSetMap();
			for(DataSetHistory dataSetHistory : dataSetHistoryList){
				String version = dataSetHistory.getVersion();
				DataSet dataSet = Util.jsonToObject(dataSetHistory.getContents(), DataSet.class);
				map.put(version, dataSet);
			}
		}
		return map;
	}

	/**
	 * <pre>
	 * 입시 저장 데이터셋 조회 [REST-R07-AN-05-01]
	 * <pre>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public DataSet getLastTempSavedDataSet(String userId) throws Exception{
		DataSetTemp dataSetTemp = dataSetMapper.getLastTempSavedDataSet(userId);
		String contents = dataSetTemp.getContents();
		DataSet dataSet = Util.jsonToObject(contents, DataSet.class);
		return dataSet;
	}

	public List<DataSet> getTemporarySavedDataSetList(Map<String, String> params) throws Exception {
		List<DataSetTemp> dataSetTempList = dataSetMapper.getTemporarySavedDataSetList(params);
		List<DataSet> dataSetList = null;
		if(dataSetTempList != null && dataSetTempList.size() != 0){
			dataSetList = new ArrayList<DataSet>();
			for (DataSetTemp dataSetTemp : dataSetTempList) {
				String contents = dataSetTemp.getContents();
				DataSet dataSet = Util.jsonToObject(contents, DataSet.class);
				dataSetList.add(dataSet);
			}
		}
		return dataSetList;
	}

	/**
	 * <pre>
	 * 데이터셋 생성 [REST-C01-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	@Transactional
	private int createDataSet(DataSet dataSet) throws Exception{
		int res = -1;

		dataSetMapper.insertDataSet(dataSet);

		String dataSetId = dataSet.getDataSetId();

		List<DataField> fieldList = dataSet.getDataFieldList();

		for(DataField dataField : fieldList){

			dataField.setDataSetId(dataSetId);
			//-----------------------------------------------------------------
			//repeatDataSetId 값이 프론트측에서부터 전달되어 올 수 있는 값인지 생각해 볼 것.
			//업데이트 함수에서도 자동으로 세팅해 주면 될런지도 같이 생각해 볼 것.
			//현재는 값이 비어 있으면 해당 필드가 속한 데이터셋ID를 기본 값으로 세팅하도록 함.
			if("Y".equalsIgnoreCase(dataField.getHasRepeatCountField()) && Util.isEmpty(dataField.getRepeatDataSetId())){
				dataField.setRepeatDataSetId(dataSetId);
			}

			dataSetMapper.insertDataField(dataField);

		}


		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터셋 생성 + 히스토리저장 [REST-C01-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public DataSet createDataSetWithHistory(DataSet dataSet) throws Exception{

		int res = -1;
		try{
			res = createDataSet(dataSet);
			dataSet = getDataSet(dataSet.getDataSetId(), null);
			res = addDataSetHistory(dataSet);
		}catch(UnChangedDataException ucde){
			logger.error("UnChangedDataException:바뀐게 없어서 히스토리는 저장하지 않는다.");
		}catch(Exception e){
			throw e;
		}
		return dataSet;

	}


	/**
	 * <pre>
	 * 데이터셋 승인 [REST-S01-AN-05-01]
	 * 별다른 요구사항이 현재는 없으니 데이터셋 테이블의 use 값을 'Y' 업데이트 시키는 것으로 처리한다.
	 * </pre>
	 * @param dataSetId
	 * @param requestUserId
	 * @param requestDate
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int approvalDataSet(String dataSetId, String requestUserId, String requestDate) throws Exception{


		int res = -1;
		dataSetMapper.approvalDataSet(dataSetId, requestUserId, requestDate);
		/*DataSet dataSet = getDataSet(dataSetId, null);
		List<DataField> fieldList = dataSet.getDataFieldList();
		for(DataField dataField : fieldList){
			if(dataField.getType().equalsIgnoreCase(DataField.TYPE_COMPLEX)){
				String fieldSetId = dataField.getFieldSetId();
				approvalDataSet(fieldSetId, requestUserId, requestDate);
			}
		}*/
		res = 1;
		return res;


	}

	/**
	 * <pre>
	 * 데이터셋 승인 + 히스토리저장[REST-S01-AN-05-01]
	 * 별다른 요구사항이 현재는 없으니 데이터셋 테이블의 use 값을 'Y' 업데이트 시키는 것으로 처리한다.
	 * </pre>
	 * @param dataSetId
	 * @param requestUserId
	 * @param requestDate
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int approvalDataSetWithHistory(String dataSetId, String requestUserId, String requestDate) throws Exception{
		int res = -1;
		try{
			res = approvalDataSet(dataSetId, requestUserId, requestDate);
			DataSet currentDataSet = getDataSet(dataSetId, null);
			res = addDataSetHistory(currentDataSet);
		}catch(UnChangedDataException ucde){
			logger.error("UnChangedDataException:바뀐게 없어서 히스토리는 저장하지 않는다.");
		}catch(Exception e){
			throw e;
		}
		return res;
	}


	/**
	 * <pre>
	 * 데이터셋 수정 [REST-U01-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	@Transactional
	private int modifyDataSet(DataSet dataSet) throws Exception{
		int res = -1;

		dataSetMapper.updateDataSet(dataSet);

		List<DataField> fieldList = dataSet.getDataFieldList();

		//20170206
		//조창현 요구사항.
		//필드리스트는 삭제 후 재등록으로 업데이이트 로직을 변경한다.
		//for(DataField dataField : fieldList){
		//	dataSetMapper.updateDataField(dataField);

			//complex type 중첩 처리는 하지 않기로 함.(복합유형은 계속 보존, 참조 유형 모델 차용)
			/*if(dataField.getType().equalsIgnoreCase(DataField.TYPE_COMPLEX)){
				String fieldSetId = dataField.getFieldSetId();
				DataSet fieldSet = dataSetMapper.getDataSet(fieldSetId, null);
				modifyDataSet(fieldSet);
			}*/
		//}
		String dataSetId = dataSet.getDataSetId();
		String modDate = dataSet.getModDate();
		String modId = dataSet.getModId();
		//dataSetMapper.deleteAllDataField(dataSetId, modId, modDate);
		dataSetMapper.deletePhysicalAllDataField(dataSetId);
		for(DataField dataField : fieldList){
			dataField.setDataSetId(dataSetId);
			//-----------------------------------------------------------------
			//repeatDataSetId 값이 프론트측에서부터 전달되어 올 수 있는 값인지 생각해 볼 것.
			//업데이트 함수에서도 자동으로 세팅해 주면 될런지도 같이 생각해 볼 것.
			//현재는 값이 비어 있으면 해당 필드가 속한 데이터셋ID를 기본 값으로 세팅하도록 함.
			if("Y".equalsIgnoreCase(dataField.getHasRepeatCountField()) && Util.isEmpty(dataField.getRepeatDataSetId())){
				dataField.setRepeatDataSetId(dataSetId);
			}
			dataSetMapper.insertDataField(dataField);
		}
		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터셋 수정 + 히스토리저장 [REST-U01-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int modifyDataSetWithHistory(DataSet dataSet) throws Exception{
		int res = -1;
		try{
			res = modifyDataSet(dataSet);
			res = addDataSetHistory(dataSet);
		}catch(UnChangedDataException ucde){
			logger.error("UnChangedDataException:바뀐게 없어서 히스토리는 저장하지 않는다.");
		}catch(Exception e){
			throw e;
		}
		return res;
	}

	/**
	 * <pre>
	 * 데이터셋 삭제 [REST-D01-AN-05-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	@Transactional
	private DataSet deleteDataSet(String dataSetId, String modDate, String modId) throws Exception{

		DataSet dataSet = getDataSet(dataSetId, null);
		if(dataSet != null){
			dataSet.setModDate(modDate);
			dataSet.setModId(modId);
			dataSet.setDelYn("Y");
			dataSetMapper.deleteDataSet(dataSet);

			dataSetMapper.deleteAllDataField(dataSetId, modId, modDate);

			//List<DataField> fieldList = dataSet.getDataFieldList();
			//for(DataField dataField : fieldList){
			//	dataField.setModDate(modDate);
			//	dataField.setModId(modId);
			//	dataField.setDelYn("Y");
			//	dataSetMapper.deleteDataField(dataField);
				/*
				if(dataField.getType().equalsIgnoreCase(DataField.TYPE_COMPLEX)){
					String fieldSetId = dataField.getFieldSetId();
					deleteDataSet(fieldSetId, modDate, modId);
				}*/
			//}
			return dataSet;
		}else{
			throw new NotFoundDataException();
		}
	}

	/**
	 * <pre>
	 * 데이터셋 삭제 + 히스토리저장 [REST-D01-AN-05-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteDataSetWithHistory(String dataSetId, String modDate, String modId) throws Exception{
		int res = -1;
		try{
			DataSet currentDataSet = deleteDataSet(dataSetId, modDate, modId);
			res = addDataSetHistory(currentDataSet);
		}catch(UnChangedDataException ucde){
			logger.error("UnChangedDataException:바뀐게 없어서 히스토리는 저장하지 않는다.");
		}catch(Exception e){
			throw e;
		}
		return res;
	}


	/**
	 * <pre>
	 * 데이터셋 임시저장 [REST-S02-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	public int saveTemporary(DataSetTemp dataSetTemp) throws Exception{
		return dataSetMapper.saveTemporary(dataSetTemp);
	}



	/**
	 * <pre>
	 * 메타필드 검색 [REST-R08-AN-05-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	public List<MetaField> getMetaFieldList(Map<String, String> params) throws Exception{
		return dataSetMapper.getMetaFieldList(params);
	}


	/**
	 * <pre>
	 * 데이터셋 HISTORY 추가 [REST-C02-AN-05-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int addDataSetHistory(DataSet currentDataSet) throws Exception{
		int res = -1;
		String dataSetId = currentDataSet.getDataSetId();
		DataSetHistory lastDataSetHistory = dataSetMapper.getLastVersionDataSetHistory(dataSetId);

		if(lastDataSetHistory == null){
			DataSetHistory dataSetHistory = new DataSetHistory();
			dataSetHistory.setDataSetId(dataSetId);
			dataSetHistory.setContents(Util.toJSONString(currentDataSet));
			dataSetHistory.setRegDate(currentDataSet.getRegDate());
			dataSetHistory.setRegId(currentDataSet.getRegId());
			dataSetMapper.addDataSetHistory(dataSetHistory);
		}else{
			DataSet beforeDataSet = Util.jsonToObject(lastDataSetHistory.getContents(), DataSet.class);
			if(beforeDataSet == null || !currentDataSet.equals(beforeDataSet)){
				DataSetHistory dataSetHistory = new DataSetHistory();
				dataSetHistory.setDataSetId(dataSetId);
				dataSetHistory.setContents(Util.toJSONString(currentDataSet));
				dataSetHistory.setModDate(currentDataSet.getModDate());
				dataSetHistory.setModId(currentDataSet.getModId());
				dataSetHistory.setRegDate(currentDataSet.getRegDate());
				dataSetHistory.setRegId(currentDataSet.getRegId());
				dataSetMapper.addDataSetHistory(dataSetHistory);
			}else{
				throw new UnChangedDataException();
			}
		}
		res = 1;
		return res;
	}

	/**
	 * @return
	 */
	public List<Map> getDataSetNameList(Map<String, String> params) throws Exception{
		return dataSetMapper.getDataSetNameList(params);
	}

	/**
	 * @return
	 */
	public List<Map> getInterfaceList() throws Exception{
		// TODO Auto-generated method stub
		return dataSetMapper.getInterfaceList();
	}

	/**
	 * @return
	 */
	public List<Map> getRegUserList() throws Exception{
		// TODO Auto-generated method stub
		return dataSetMapper.getRegUserList();
	}


	public int setDataSetInterfaceMap(DataSetInterfaceMap dim) throws Exception{
		int res = dataSetMapper.deleteDataSetInterfaceMap(dim);
		res = dataSetMapper.insertDataSetInterfaceMap(dim);
		return res;
	}

	public List<DataSetInterfaceMap> getDataSetInterfaceMap(Map params) throws Exception{
		return dataSetMapper.getDataSetInterfaceMap(params);
	}


	/**
	 * 데이터셋 리스트 조회 - 전체 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getSimpleDataSetListCount(Map params) throws Exception {
		int count = dataSetMapper.getSimpleDataSetListCount(params);
		return count;
	}

	/**
	 * <pre>
	 * 데이터셋 리스트 조회 - Simple [REST-R01-AN-06-00]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public DataSetList getSimpleDataSetListByPage(Map<String, String> params) throws Exception{
		String delYn = params.get("delYn");
		if(delYn == null || delYn.equalsIgnoreCase("")){
			params.put("delYn", "N");
		}
		DataSetList list = dataSetMapper.getSimpleDataSetListByPage(params);
		return list;
	}

	/**
	 * <pre>
	 * 데이터셋 리스트 조회 - Simple [REST-R01-AN-06-00]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public DataSetList getSimpleDataSetList(Map<String, String> params) throws Exception{
		String delYn = params.get("delYn");
		if(delYn == null || delYn.equalsIgnoreCase("")){
			params.put("delYn", "N");
		}
		DataSetList list = dataSetMapper.getSimpleDataSetList(params);
		return list;
	}


	/**
	 * <pre>
	 * 데이터셋 상세조회
	 * </pre>
	 * @param dataSetId String 데이터셋ID
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getSimpleDataSet(String dataSetId) throws Exception{
		return getSimpleDataSet(dataSetId, "N");
	}

	/**
	 * <pre>
	 * 데이터셋 상세조회 [REST-R02-AN-06-00]
	 * </pre>
	 * @param dataSetId String 데이터셋ID
	 * @param delYn String 삭제구분
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getSimpleDataSet(String dataSetId, String delYn) throws Exception{
		return getSimpleDataSet(dataSetId, delYn, null);
	}

	/**
	 * <pre>
	 * 데이터셋 상세조회 [REST-R02-AN-06-00]
	 * </pre>
	 * @param dataSetId String 데이터셋ID
	 * @param delYn String 삭제구분
	 * @param conv CamelUtil.TO_CAMEL(1) or CamelUtil.TO_UNDERSCORE(2)
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getSimpleDataSet(String dataSetId, String delYn, String convType) throws Exception{
		DataSet dataSet = dataSetMapper.getSimpleDataSet(dataSetId, delYn);

		if(dataSet == null) throw new NotFoundDataException();

		if( !Util.isEmpty(convType) && (convType.equals(CamelUtil.TO_CAMEL) || convType.equals(CamelUtil.TO_UNDERSCORE)) ) {
			List<DataField> fieldList = dataSet.getDataFieldList();
			for(DataField field : fieldList) {
				field.setName2( CamelUtil.conv(field.getName2(), convType));
				field.setParentNmEn( CamelUtil.conv(field.getParentNmEn(), convType));
			}
		}

		return dataSet;
	}

	/**
	 * <pre>
	 * 데이터셋 코드 존재여부 체크 [REST-R05-AN-06-00]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int getExistDataSetCd(String dataSetCd) throws Exception{
		int existCnt = dataSetMapper.getExistDataSetCd(dataSetCd);
		return existCnt;
	}

	/**
	 * <pre>
	 * 데이터셋 사용여부 체크 [REST-R06-AN-06-00]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int getUsedDataSet(String dataSetId) throws Exception{
		int existCnt = dataSetMapper.getUsedDataSet(dataSetId);
		return existCnt;
	}

	/**
	 * <pre>
	 * 데이터맵 코드 존재여부 체크 [REST-R03-AN-06-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int getExistMapCd(String mapCd) throws Exception{
		int existCnt = dataSetMapper.getExistMapCd(mapCd);
		return existCnt;
	}

	/**
	 * <pre>
	 * 데이터맵 사용여부 체크 [REST-R04-AN-06-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int getUsedDataMap(String mapId) throws Exception{
		int existCnt = dataSetMapper.getUsedDataMap(mapId);
		return existCnt;
	}

	/**
	 * <pre>
	 * 데이터셋 생성 [REST-C01-AN-06-00]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int createSimpleDataSet(DataSet dataSet) throws Exception{
		int res = -1;

		String dataSetCd = dataSet.getCd();
		int existCnt = dataSetMapper.getExistDataSetCd(dataSetCd);

		if(existCnt > 0) {
			throw new DatabaseServiceException("Layout Code : " + dataSetCd + " 가 존재합니다.");
		}

		dataSetMapper.insertDataSet(dataSet);

		String dataSetId = dataSet.getDataSetId();

		List<DataField> fieldList = dataSet.getDataFieldList();

		//-----------------------------------------------------------------
		// 화면에서 입력된 순서대로 seq 부여
		//-----------------------------------------------------------------
		int idx = 0;
		for(DataField dataField : fieldList) {
			dataField.setDataSetId(dataSetId);
			dataField.setSeq(++idx);
			dataField.setRegId(dataSet.getRegId());
			dataField.setRegDate(dataSet.getRegDate());
			dataField.setModId(dataSet.getModId());
			dataField.setModDate(dataSet.getModDate());
			if( Util.isEmpty(dataField.getCd()) ){
				dataField.setCd(dataField.getName2());
			}
			//-----------------------------------------------------------------
			//repeatDataSetId 값이 프론트측에서부터 전달되어 올 수 있는 값인지 생각해 볼 것.
			//업데이트 함수에서도 자동으로 세팅해 주면 될런지도 같이 생각해 볼 것.
			//현재는 값이 비어 있으면 해당 필드가 속한 데이터셋ID를 기본 값으로 세팅하도록 함.
			//-----------------------------------------------------------------
			if("Y".equalsIgnoreCase(dataField.getHasRepeatCountField()) && Util.isEmpty(dataField.getRepeatDataSetId())){
				dataField.setRepeatDataSetId(dataSetId);
			}
			dataSetMapper.insertDataField(dataField);
		}
		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터셋 수정 [REST-U01-AN-06-00]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int modifySimpleDataSet(DataSet dataSet) throws Exception{
		int res = -1;

		dataSetMapper.updateDataSet(dataSet);

		List<DataField> fieldList = dataSet.getDataFieldList();

		String dataSetId = dataSet.getDataSetId();

		dataSetMapper.deletePhysicalAllDataField(dataSetId);

		//-----------------------------------------------------------------
		// 화면에서 입력된 순서대로 seq 부여
		//-----------------------------------------------------------------
		int idx = 0;
		for(DataField dataField : fieldList){
			dataField.setDataSetId(dataSetId);
			dataField.setSeq(++idx);
			dataField.setRegId(dataSet.getRegId());
			dataField.setRegDate(dataSet.getRegDate());
			dataField.setModId(dataSet.getModId());
			dataField.setModDate(dataSet.getModDate());
			//20201209 보완..나중에 영문과, 코드를 달리 사용 가능성이 있을까나...
			//if( Util.isEmpty(dataField.getCd()) ){
				dataField.setCd(dataField.getName2());
			//}
			//-----------------------------------------------------------------
			//repeatDataSetId 값이 프론트측에서부터 전달되어 올 수 있는 값인지 생각해 볼 것.
			//업데이트 함수에서도 자동으로 세팅해 주면 될런지도 같이 생각해 볼 것.
			//현재는 값이 비어 있으면 해당 필드가 속한 데이터셋ID를 기본 값으로 세팅하도록 함.
			//-----------------------------------------------------------------
			if("Y".equalsIgnoreCase(dataField.getHasRepeatCountField()) && Util.isEmpty(dataField.getRepeatDataSetId())){
				dataField.setRepeatDataSetId(dataSetId);
			}
			dataSetMapper.insertDataField(dataField);
		}
		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터셋 삭제 [REST-D01-AN-06-00]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteSimpleDataSet(String dataSetId, String modDate, String modId) throws Exception {
		int res = -1;
		DataSet dataSet = getSimpleDataSet(dataSetId, null);
		if(dataSet != null) {
			dataSetMapper.deletePhysicalAllDataField(dataSetId);
			dataSetMapper.deletePhysicalDataSet(dataSetId);

			//20201220 - 수정, 사용되지 않은 레이아웃은 Clear 처리 하는게 맞지않을까 해서 보완
			/*
			dataSet.setModDate(modDate);
			dataSet.setModId(modId);
			dataSet.setDelYn("Y");
			dataSetMapper.deleteDataSet(dataSet);
			dataSetMapper.deleteAllDataField(dataSetId, modId, modDate);
			*/
			res = 1;
		}

		return res;
	}

	/**
	 * <pre>
	 * 데이터맵핑 생성 [REST-C01-AN-06-01]
	 * </pre>
	 * @param dataSet
	 * @return
	 * @throws Exception
	 *
	 */
	@Transactional
	public int createSimpleDataMap(DataMap dataMap) throws Exception{
		int res = -1;
		//-----------------------------------------------------------------
		// TAN0503
		//-----------------------------------------------------------------
		dataSetMapper.insertDataMap(dataMap);
		String mapId = dataMap.getMapId();

		List<DataMapItem> mapItemList = dataMap.getDataMapItemList();

		//-----------------------------------------------------------------
		// 화면에서 입력된 순서대로 seq 부여
		//-----------------------------------------------------------------
		int idx = 0;
		for(DataMapItem mapItem : mapItemList) {
			mapItem.setMapId(mapId);
			//mapItem.setMapItemId(mapItemId);
			//mapItem.setSeq(++idx); //필드 없음(삭제예정)
			//mapItem.setMapCtrlType("0");
			mapItem.setRegId(dataMap.getRegId());
			mapItem.setRegDate(dataMap.getRegDate());
			mapItem.setModId(dataMap.getModId());
			mapItem.setModDate(dataMap.getModDate());
			//-----------------------------------------------------------------
			// TAN0504
			//-----------------------------------------------------------------
			dataSetMapper.insertDataMapItem(mapItem);

			//int jdx = 0;
			String mapItemId = mapItem.getMapItemId();
			List<DataMapItemField> mapItemFieldList = mapItem.getItems();
			for(DataMapItemField mapItemField : mapItemFieldList) {
				//mapItemField.setDataSetId("");
				//mapItemField.setDataFieldId("");
				//mapItemField.setDataFieldSeq(++jdx);

				mapItemField.setMapId(mapId);
				mapItemField.setMapItemId(mapItemId);
				//mapItemField.setSeq(idx); //삭제예정

				//mapItemField.setIoType("");
				mapItemField.setRegId(dataMap.getRegId());
				mapItemField.setRegDate(dataMap.getRegDate());
				mapItemField.setModId(dataMap.getModId());
				mapItemField.setModDate(dataMap.getModDate());
				//-----------------------------------------------------------------
				// TAN0505
				//-----------------------------------------------------------------
				dataSetMapper.insertDataMapItemField(mapItemField);
			}

		}
		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터 맵핑 수정 [REST-U01-AN-06-01]
	 * </pre>
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int modifySimpleDataMap(DataMap dataMap) throws Exception{
		int res = -1;

		String mapId = dataMap.getMapId();

		dataSetMapper.updateDataMap(dataMap);
		dataSetMapper.deletePhysicalAllDataMapItem(mapId);
		dataSetMapper.deletePhysicalAllDataMapItemField(mapId);

		List<DataMapItem> mapItemList = dataMap.getDataMapItemList();
		//-----------------------------------------------------------------
		// 화면에서 입력된 순서대로 seq 부여
		//-----------------------------------------------------------------
		int idx = 0;
		for(DataMapItem mapItem : mapItemList) {
			mapItem.setMapId(mapId);
			//mapItem.setMapItemId(mapItemId);
			//mapItem.setSeq(++idx); //필드없음(삭제예정)
			//mapItem.setMapCtrlType("0");
			mapItem.setRegId(dataMap.getRegId());
			mapItem.setRegDate(dataMap.getRegDate());
			mapItem.setModId(dataMap.getModId());
			mapItem.setModDate(dataMap.getModDate());

			dataSetMapper.insertDataMapItem(mapItem);

			int jdx = 0;
			String mapItemId = mapItem.getMapItemId();
			List<DataMapItemField> mapItemFieldList = mapItem.getItems();
			for(DataMapItemField mapItemField : mapItemFieldList) {
				//mapItemField.setDataSetId("");
				//mapItemField.setDataFieldId("");
				//mapItemField.setDataFieldSeq(++jdx);

				mapItemField.setMapId(mapId);
				mapItemField.setMapItemId(mapItemId);
				//mapItemField.setSeq(idx); // 삭제예정

				//mapItemField.setIoType("");
				mapItemField.setRegId(dataMap.getRegId());
				mapItemField.setRegDate(dataMap.getRegDate());
				mapItemField.setModId(dataMap.getModId());
				mapItemField.setModDate(dataMap.getModDate());

				dataSetMapper.insertDataMapItemField(mapItemField);
			}

		}
		res = 1;
		return res;
	}

	/**
	 * <pre>
	 * 데이터 맵핑 삭제 [REST-D01-AN-06-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteSimpleDataMap(String mapId, String modId, String modDate) throws Exception {
		int res = -1;
		Map<String, Object> dataMap = getSimpleDataMap(mapId, null);
		if(!Util.isEmpty(dataMap)) {
			dataSetMapper.deleteDataMap(mapId, modId, modDate);
			dataSetMapper.deleteDataMapItem(mapId, modId, modDate);
			dataSetMapper.deleteDataMapItemField(mapId, modId, modDate);

			//TODO :: 인터페이스-레이아웃 맵핑 쪽에서 1차 정리후 삭제하도록 해야하는지 검토 필요.
			dataSetMapper.deleteEndpointDataMapModel(mapId, modId, modDate);
			res = 1;
		}

		return res;
	}

	/*
	 * <pre>
	 * 데이터 맵핑 삭제 [REST-D01-AN-06-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteSimplePhysicalAllDataMap(String mapId) throws Exception {
		int res = -1;
		Map<String, Object> dataMap = getSimpleDataMap(mapId, null);
		if(!Util.isEmpty(dataMap)) {
			dataSetMapper.deletePhysicalAllDataMapItemField(mapId);
			dataSetMapper.deletePhysicalAllDataMapItem(mapId);
			dataSetMapper.deletePhysicalAllDataMap(mapId);

			//TODO :: 인터페이스-레이아웃 맵핑 쪽에서 1차 정리후 삭제하도록 해야하는지 검토 필요.
			dataSetMapper.deletePhysicalAllEndpointDataMapModel(mapId);
			res = 1;
		}

		return res;
	}

	/**
	 * <pre>
	 * 데이터맵핑 조회 [REST-R01-AN-06-01]
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> getSimpleDataMapList(Map<String, String> params) throws Exception{
		List<DataMap> list = dataSetMapper.getSimpleDataMapList(params);

		return list;
	}

	/**
	 * <pre>
	 * 데이터맵핑 상세조회 [REST-R02-AN-06-01]
	 * </pre>
	 * @param mapId
	 * @param delYn
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSimpleDataMap(String mapId, String delYn) throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		DataMap dataMap = dataSetMapper.getSimpleDataMap(mapId, delYn);

		if(dataMap == null) {
			throw new NotFoundDataException();
		} else {

			String srcDataSetId = dataMap.getSrcDataSetId();
			String tagDataSetId = dataMap.getTagDataSetId();

			mapData.put("mapData", dataMap);
			mapData.put("srcDataSet", getSimpleDataSet(srcDataSetId, "N"));
			mapData.put("tagDataSet", getSimpleDataSet(tagDataSetId, "N"));
		}

		return mapData;
	}


	/**
	 * <pre>
	 * 데이터맵핑 상세조회 Of Interface [REST-R02-AN-06-01]
	 * </pre>
	 * @param mapId
	 * @param delYn
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	public Map<String, Object> getSimpleDataMapInterfaceMap(Map<String,String> params) throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		DataMap dataMap = dataSetMapper.getSimpleDataMapInterfaceMap(params);

//		if(dataMap == null) {
//			String srcSystemId = params.get("srcSystemId");
//			String tagSystemId = params.get("tagSystemId");
//			String interfaceId = params.get("interfaceId");
//			String ioType      = params.get("ioType");
//
//			mapData.put("mapData", null);
//			mapData.put("srcDataSet", dataSetMapper.getSimpleDataSetInterfaceMap(srcSystemId, interfaceId, ioType));
//			mapData.put("tagDataSet", dataSetMapper.getSimpleDataSetInterfaceMap(tagSystemId, interfaceId, ioType));
//		} else {
//
//			String srcDataSetId = dataMap.getSrcDataSetId();
//			String tagDataSetId = dataMap.getTagDataSetId();
//
//			mapData.put("mapData", dataMap);
//			mapData.put("srcDataSet", getSimpleDataSet(srcDataSetId, "N"));
//			mapData.put("tagDataSet", getSimpleDataSet(tagDataSetId, "N"));
//		}



		String srcSystemId = params.get("srcSystemId");
		String tagSystemId = params.get("tagSystemId");
		String interfaceId = params.get("interfaceId");
		String ioType      = params.get("ioType");

		mapData.put("mapData", dataMap);
		mapData.put("srcDataSet", dataSetMapper.getSimpleDataSetInterfaceMap(srcSystemId, interfaceId, ioType));
		mapData.put("tagDataSet", dataSetMapper.getSimpleDataSetInterfaceMap(tagSystemId, interfaceId, ioType));


		return mapData;
	}


	/**
	 * <pre>
	 * 데이터맵핑 상세조회 Of Interface [REST-R02-AN-06-01]
	 * </pre>
	 * @param mapId
	 * @param delYn
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getSimpleMetaDataList(Map<String,String> params) throws Exception {
		List<Map<String, String>> metaDataList = new ArrayList<Map<String,String>>();
		metaDataList = dataSetMapper.getSimpleMetaDataList(params);

		return metaDataList;
	}

	public List<DataField> getSimpleMetaDataCheck(List<DataField> list, Map complexType) throws Exception {
		return getSimpleMetaDataCheck(list, complexType, false);
	}

	/**
	 * <pre>
	 * 메타필드 검색 [REST-R08-AN-05-01]
	 * - 20201201 :: 부모필드는 메타처리하지 않는다(from 함대훈B)
	 * - 20201201 :: 복합유형일경우 메타처리하지 않는다(from 함대훈B)
	 * - 20201217 :: 메타기준 리턴할때, isCamel 이면 CamelType, 그렇지 않으면 MetaType 으로 리턴(from 이경묵B)
	 * - 20201229 :: 메타시스템은 숫자 등록을 못하지만, 사용자들은 숫자조합이 필요하다(from 이경묵B, 함대훈B)
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<DataField> getSimpleMetaDataCheck(List<DataField> list, Map complexType, boolean isCamel) throws Exception {

		if( !Util.isEmpty(list) ) {
			//--------------------------------------------------------------------------------
			// DB I/O 를 줄여보고자, 한글명으로 일괄 조회한다
			//--------------------------------------------------------------------------------
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			List<String> names = new ArrayList<String>();
			for(DataField dataField : list) {
				//20201229 - 내용보완 :: 문자+숫자 조합시 숫자 잘라내고 메타조회
				//[문자][숫자] 리턴
				String[] splitStr = splitStringNumber(dataField.getName1());
				names.add(splitStr[0]);

				//20201229 - 주석처리
				//names.add(dataField.getName1());
			}
			params.put("names", names);

			//--------------------------------------------------------------------------------
			// 조회결과를 map 으로 담아둔다
			//--------------------------------------------------------------------------------
			List<DataField> resultSet = dataSetMapper.getSimpleMetaDataCheck(params);

			Map<String, DataField> metaResult = new HashMap<String, DataField>();
			for(DataField result : resultSet) {
				metaResult.put(result.getName1(), result);
			}

			//--------------------------------------------------------------------------------
			// 필드정보를 메타조회결과랑 체크하면서 셋팅한다
			//--------------------------------------------------------------------------------
			for(DataField dataField : list) {
				//--------------------------------------------------------------------------------
				// 20201229 - 내용보완 (from 이경묵B, 함대훈B)
				// - 메타시스템에서 숫자형식 관리 못한다고함
				// - 예시) '위험보험료' O, '위험보험료1' X
				// - 문자열 파싱하여 숫자잘라내고 메타검색 => 다시 조합해서 리턴
				// - 위험보험료1 > DANG_INP_FE_1, 위험보험료09 > DANG_INP_FE_N09 와 같은 규칙 적용
				//--------------------------------------------------------------------------------

				//[문자][숫자] 리턴
				String[] splitStr = splitStringNumber(dataField.getName1());
				DataField metaDataField = metaResult.get(splitStr[0]);

				//20201229 - 주석처리 :: 위 내용으로 보완
				//DataField metaDataField = metaResult.get(dataField.getName1());
				//20201201 - 주석처리 :: 부모필드는 메타처리하지 않는다(from 함대훈B)
				//DataField metaDataFieldParentEn = metaResult.get(dataField.getParentNmKo());

				if(!Util.isEmpty(metaDataField)) {
					if( dataField.getMetaYn().equals("Y") ) {

						//--------------------------------------------------------------------------------
						// 20201201 - 내용보완 :: 복합유형일경우 메타처리하지 않는다(from 함대훈B)
						//--------------------------------------------------------------------------------
						if( !complexType.containsValue(dataField.getTypeNm()) ) {
							//--------------------------------------------------------------------------------
							//20201217 - 내용보완 :: 메타기준 리턴할때, isCamel 이면 CamelType, 그렇지 않으면 MetaType 으로 리턴(from 이경묵B)
							//--------------------------------------------------------------------------------
							{
								if(isCamel) {
									dataField.setName2( CamelUtil.convCamel(metaDataField.getName2()) );
								} else {
									dataField.setName2(metaDataField.getName2());
								}
							}
							//--------------------------------------------------------------------------------
							// 20201229 추가, 메타에서 조회된 영문필드명에 다시 숫자 조합(from 이경묵B, 함대훈B)
							// 20201229 보안, 카멜일때는 "_" 빼달라고 함(from 이경묵B, 함대훈B)
							//--------------------------------------------------------------------------------
							{
								if( splitStr[1] != null && !splitStr[1].equals("") ) {
									if(splitStr[1].length() > 1) {
										if(isCamel)
											dataField.setName2( Util.join(dataField.getName2(), "N", splitStr[1]) );
										else
											dataField.setName2( Util.join(dataField.getName2(), "_N", splitStr[1]) );
									} else {
										if(isCamel)
											dataField.setName2( Util.join(dataField.getName2(), splitStr[1]) );
										else
											dataField.setName2( Util.join(dataField.getName2(), "_", splitStr[1]) );
									}
								}
							}

							//--------------------------------------------------------------------------------
							// 20201230 - 내용보완(from 이경묵B, 함대훈B)
							// - type이 DATE, CLOB 일때 각각 20, 4000 을 default 값으로 설정해야하는데
							// - meta 에서는 아마 해당 타입에 대한 길이 설정을 못할것이고
							// - meta 기준으로 설정되면 0 으로 셋팅 될테니 문제(사용자가 입력한경우 뭉개짐)...
							// - 사용자가 입력한 값이 공백이나 0 이면 default, 그렇지 않으면 사용자 입력값으로 대체 필요
							// - 사용자가 입력한 type 과 meta type 은 동일하다고 가정
							// - 만약 틀리면? meta type 기준으로 세팅 되는게 맞제? 헷갈리네....
							//--------------------------------------------------------------------------------
							{
								if( metaDataField.getTypeNm().equalsIgnoreCase("DATE") || metaDataField.getTypeNm().equalsIgnoreCase("CLOB") ) {

									int dateDefault = 20;   // DATE Default Length
									int clobDefault = 4000; // CLOB Default Length

									if( dataField.getTypeNm().equalsIgnoreCase( metaDataField.getTypeNm() ) ) {
										//--------------------------------------------------------------------------------
										// 사용자 type == meta type
										//--------------------------------------------------------------------------------
										if( dataField.getLength() < 1 ) {
											//--------------------------------------------------------------------------------
											// data type default 설정
											//--------------------------------------------------------------------------------
											if( metaDataField.getTypeNm().equalsIgnoreCase("DATE") ) {
												dataField.setLength(dateDefault);
											}

											if( metaDataField.getTypeNm().equalsIgnoreCase("CLOB") ) {
												dataField.setLength(clobDefault);
											}
										} else {
											//--------------------------------------------------------------------------------
											// 사용자가 원래 입력한 length
											//--------------------------------------------------------------------------------
										}
									} else {
										//--------------------------------------------------------------------------------
										// 사용자 type != meta type
										//--------------------------------------------------------------------------------
										// meta 기준 date type default 설정
										//--------------------------------------------------------------------------------
										if( metaDataField.getTypeNm().equalsIgnoreCase("DATE") ) {
											dataField.setLength(dateDefault);
										}

										if( metaDataField.getTypeNm().equalsIgnoreCase("CLOB") ) {
											dataField.setLength(clobDefault);
										}
									}

								} else {
									// 나머지는 meta 기준으로 설정
									dataField.setLength(metaDataField.getLength());
								}

							}

							//20201201 - 주석처리 :: 부모필드는 메타처리하지 않는다(from 함대훈B)
							//dataField.setParentNmEn(!Util.isEmpty(metaDataFieldParentEn) ? metaDataFieldParentEn.getName2() : "");

							dataField.setCd(metaDataField.getCd());
							dataField.setType(metaDataField.getType());
							dataField.setTypeNm(metaDataField.getTypeNm());
							//20201230 - 주석처리, 위 20201230 내용으로 보완
							//dataField.setLength(metaDataField.getLength());
							dataField.setScale(metaDataField.getScale());
							dataField.setMaskingYn(metaDataField.getMaskingYn());
							dataField.setMaskingPatternCd(metaDataField.getMaskingPatternCd());

							//신한생명에 필요하지 않은 항목으로 주석처리
							//dataField.setEncryptType(metaDataField.getEncryptType());
							//dataField.setEncryptTypeNm(metaDataField.getEncryptTypeNm());
							//dataField.setDefaultValue(metaDataField.getDefaultValue());
							//dataField.setJustify(metaDataField.getJustify());
							//dataField.setJustifyNm(metaDataField.getJustifyNm());
							//dataField.setPadding(metaDataField.getPadding());

						}
					}
					dataField.setValid(true);
				} else {
					if( dataField.getMetaYn().equals("N") ) {
						dataField.setValid(true);
					} else {
						dataField.setValid(false);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 신한생명 MetaCheck 용 Util
	 * - 입력된 문자열의 뒤에서 부터 숫자 체크후
	 * - [문자][숫자] 배열로 리턴
	 * @param str
	 * @return
	 */
	private String[] splitStringNumber(String str) {
		String[] result = new String[2];

        int sz = str.length();

		StringBuilder number = new StringBuilder();
		int lastIndex = -1;
        for (int i=(sz-1); i>=0; i--) {
            if( Character.isDigit(str.charAt(i)) ) {
                number.append( str.charAt(i) );
                lastIndex = i;
            } else {
            	break;
            }
        }

        if(lastIndex != -1) {
        	result[0] = str.substring(0, lastIndex);
        } else {
        	result[0] = str;
        }

        number.reverse();
        result[1] = number.toString();
		return result;
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map getDataMapSearchParameterList(Map<String, String> params) throws Exception {
		Map<String, List> parameter = new HashMap<String, List>();

		parameter.put("mapNm", getDataMapNameList(params));
		parameter.put("srcDataSetNm", getDataSetSrcList(params));
		parameter.put("tagDataSetNm", getDataSetTagList(params));

		return parameter;
	}

	public List<Map> getDataMapNameList(Map<String, String> params) throws Exception {
		return dataSetMapper.getDataMapNameList(params);
	}

	public List<Map> getDataSetSrcList(Map<String, String> params) throws Exception {
		return dataSetMapper.getDataSetSrcList(params);
	}

	public List<Map> getDataSetTagList(Map<String, String> params) throws Exception {
		return dataSetMapper.getDataSetTagList(params);
	}

}
