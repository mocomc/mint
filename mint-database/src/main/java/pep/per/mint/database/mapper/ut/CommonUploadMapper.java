package pep.per.mint.database.mapper.ut;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.upload.UploadDetail;
import pep.per.mint.common.data.basic.upload.UploadSummary;

public interface CommonUploadMapper {

	public int insertUploadSummary(UploadSummary summary) throws Exception;

	public int updateUploadSummary(UploadSummary summary) throws Exception;

	public int insertUploadDetail(@Param("batchId")String batchId, @Param("rowIndex")int rowIndex, @Param("requirementId")String requirementId) throws Exception;
	public int insertUploadDetailError(@Param("batchId")String batchId, @Param("rowIndex")int rowIndex, @Param("integrationId")String integrationId,@Param("resultCd")String resultCd,@Param("resultMsg")String resultMsg) throws Exception;

	public List<UploadSummary> getUploadSummaryList() throws Exception;

	public List<UploadDetail> getUploadDetailList(String batchId) throws Exception;


}
