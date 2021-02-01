package pep.per.mint.database.service.inhouse;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.database.service.co.CommonService;

public interface IExcelUploadService {


	public List<Requirement> validate(List<String[]> list, Map<String,Object> params) throws Exception;

	public List<Requirement> validate(List<String[]> list, List<String[]> headerlist, Map<String,Object> params) throws Exception;


	public List<Map<String,Object>> getExcelUploadMasterInfo(Map<String,Object> params) throws Exception;

	public List<Map<String,Object>> getExcelUploadDetailInfo(Map<String,Object> params) throws Exception;

	public int insertExcelUpload(Map<String, Object> master, List<String[]> detail) throws Exception;

	@Transactional
	public int createRequirement(List<Requirement> requirementList, Map<String,Object> params) throws Exception;

	@Transactional
	public int deleteExcelUpload(Map<String, Object> params) throws Exception;
}
