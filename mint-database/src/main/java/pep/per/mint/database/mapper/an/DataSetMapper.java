/**
 *
 */
package pep.per.mint.database.mapper.an;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataMapItem;
import pep.per.mint.common.data.basic.dataset.DataMapItemField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.dataset.DataSetHistory;
import pep.per.mint.common.data.basic.dataset.DataSetInterfaceMap;
import pep.per.mint.common.data.basic.dataset.DataSetList;
import pep.per.mint.common.data.basic.dataset.DataSetTemp;
import pep.per.mint.common.data.basic.dataset.MetaField;

/**
 * @author whoana
 *
 */
public interface DataSetMapper {

	public int insertDataSet(DataSet dataSet) throws Exception;

	public int insertDataField(DataField dataField) throws Exception;

	public DataSetList getSimpleDataSetList(Map<String, String> params) throws Exception;

	public int getSimpleDataSetListCount(Map params) throws Exception;

	public DataSetList getSimpleDataSetListByPage(Map<String, String> params) throws Exception;

	public DataSet getSimpleDataSet(@Param("dataSetId")String dataSetId, @Param("delYn")String delYn) throws Exception;

	public DataSet getSimpleDataSetInterfaceMap(@Param("systemId")String systemId, @Param("interfaceId")String interfaceId, @Param("ioType")String ioType) throws Exception;

	public DataSetList getDataSetList(Map<String, String> params) throws Exception;

	public DataSet getDataSet(@Param("dataSetId")String dataSetId, @Param("delYn")String delYn) throws Exception;

	public void approvalDataSet(@Param("dataSetId")String dataSetId, @Param("requestUserId")String requestUserId, @Param("requestDate")String requestDate) throws Exception;

	public void updateDataSet(DataSet dataSet) throws Exception;

	public void updateDataField(DataField dataField) throws Exception;

	public void deleteDataSet(DataSet dataSet) throws Exception;

	public void deletePhysicalDataSet(@Param("dataSetId")String dataSetId) throws Exception;

	public void deleteDataField(DataField dataField) throws Exception;

	public void deleteAllDataField(@Param("dataSetId")String dataSetId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public void deletePhysicalAllDataField(@Param("dataSetId")String dataSetId) throws Exception;


	public int saveTemporary(DataSetTemp dataSetTemp) throws Exception;

	public List<MetaField> getMetaFieldList(Map<String, String> params) throws Exception;

	public List<MetaField> getMetaCheck(Map<String, String> params) throws Exception;


	public void addDataSetHistory(DataSetHistory dataSetHistory) throws Exception;

	public DataSetTemp getLastTempSavedDataSet(String userId) throws Exception;

	public List<String> getDataSetVersionList(String dataSetId) throws Exception;

	public DataSetHistory getDataSetHistory(@Param("dataSetId")String dataSetId, @Param("version")String version) throws Exception;

	public DataSetHistory getLastVersionDataSetHistory(String dataSetId) throws Exception;

	public List<DataSetHistory> getDataSetHistoryList(String dataSetId) throws Exception;

	public List<DataSetTemp> getTemporarySavedDataSetList(Map<String, String> params) throws Exception;

	public List<Map> getDataSetNameList(Map<String, String> params) throws Exception;

	public List<Map> getInterfaceList() throws Exception;

	public List<Map> getRegUserList() throws Exception;


	public int deleteDataSetInterfaceMap(DataSetInterfaceMap dim) throws Exception;

	public int insertDataSetInterfaceMap(DataSetInterfaceMap dim) throws Exception;

	public List<DataSetInterfaceMap> getDataSetInterfaceMap(Map params) throws Exception;


	public int deleteDataMapInterfaceMap(DataSetInterfaceMap dim) throws Exception;

	public int insertDataMapInterfaceMap(DataSetInterfaceMap dim) throws Exception;

	public List<DataSetInterfaceMap> getDataMapInterfaceMap(Map params) throws Exception;


	public int insertDataMap(DataMap dataMap) throws Exception;

	public int insertDataMapItem(DataMapItem dataMapItem) throws Exception;

	public int insertDataMapItemField(DataMapItemField dataMapItemField) throws Exception;

	public void updateDataMap(DataMap dataMap) throws Exception;

	public void deleteDataMap(@Param("mapId")String dataSetId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public void deleteDataMapItem(@Param("mapId")String dataSetId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public void deleteDataMapItemField(@Param("mapId")String dataSetId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public void deleteEndpointDataMapModel(@Param("mapId")String dataSetId, @Param("modId")String modId, @Param("modDate")String modDate) throws Exception;

	public void deletePhysicalAllDataMap(@Param("mapId")String mapId) throws Exception;

	public void deletePhysicalAllDataMapItem(@Param("mapId")String mapId) throws Exception;

	public void deletePhysicalAllDataMapItemField(@Param("mapId")String mapId) throws Exception;

	public void deletePhysicalAllEndpointDataMapModel(@Param("mapId")String mapId) throws Exception;


	public List<DataMap> getSimpleDataMapList(Map<String, String> params) throws Exception;

	public DataMap getSimpleDataMap(@Param("mapId")String mapId, @Param("delYn")String delYn) throws Exception;

	public DataMap getSimpleDataMapInterfaceMap(Map<String, String> params) throws Exception;

	public List<Map> getDataMapNameList(Map<String, String> params) throws Exception;
	public List<Map> getDataSetSrcList(Map<String, String> params) throws Exception;
	public List<Map> getDataSetTagList(Map<String, String> params) throws Exception;



	public List<Map<String,String>> getSimpleMetaDataList(Map<String, String> params) throws Exception;
	public List<DataField> getSimpleMetaDataCheck(Map params) throws Exception;
	public int getExistDataSetCd(@Param("dataSetCd")String dataSetCd) throws Exception;
	public int getExistMapCd(@Param("mapCd")String mapCd) throws Exception;

	public int getUsedDataSet(@Param("dataSetId")String dataSetId) throws Exception;

	public int getUsedDataMap(@Param("mapId")String mapId) throws Exception;

}
