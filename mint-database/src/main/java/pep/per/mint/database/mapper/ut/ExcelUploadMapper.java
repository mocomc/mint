/**
 * 
 */
package pep.per.mint.database.mapper.ut;

import java.util.List;
import java.util.Map;


/**
 * @author TA
 *
 */
public interface ExcelUploadMapper {
	
	public int insertExcelUploadMasterInfo(Map<String,Object> params) throws Exception;
	
	public int insertExcelUploadDetailInfo(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getExcelUploadMasterInfo(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getExcelUploadDetailInfo(Map<String,Object> params) throws Exception;
	
	public List<Map<String,String>> getDetailInfo(Map<String,Object> params) throws Exception;
	
	public int updateExcelUploadMasterInfoStatus(Map<String,Object> params) throws Exception;
	
	public int updateExcelUploadDetailInfo(Map<String,Object> params) throws Exception;
	
	public int deleteExcelUploadMasterInfo(Map<String,Object> params) throws Exception;
	
	public int deleteExcelUploadDetailInfo(Map<String,Object> params) throws Exception;
	
}
