package pep.per.mint.database.service.ut;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.upload.UploadDetail;
import pep.per.mint.common.data.basic.upload.UploadSummary;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.DatabaseServiceException;
import pep.per.mint.database.mapper.ut.CommonUploadMapper;
import pep.per.mint.database.service.an.RequirementService;

@Service
public class CommonUploadService {

	Logger logger = LoggerFactory.getLogger(CommonUploadService.class);

	@Autowired
	RequirementService rs;

	@Autowired
	CommonUploadMapper cm;

	//boolean isTest = false;
	/**
	 * 인터페이스 들여오기(건별) 서비스
	 * @param requirements
	 * @param summary
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int upload(List<Requirement> requirements, UploadSummary summary,String userId) throws Exception{
		int resultCount = 0;
		int res = cm.insertUploadSummary(summary);
		String batchId = summary.getBatchId();
		int rowIndex = 1;
		String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
		String orgInterfaceId = null;
		String orgRquirementId = null;
		for (Requirement requirement : requirements) {
			orgRquirementId = requirement.getRequirementId();
			requirement.setRequirementId("");//신규건으로처리위해 ID null 처리
			requirement.setRegDate(regDate);
			requirement.setRegId(userId);

			Interface interfaze = requirement.getInterfaceInfo();
			orgInterfaceId = interfaze.getInterfaceId();

			//if(isTest) interfaze.setIntegrationId(Util.join("MV_",interfaze.getIntegrationId()));

			interfaze.setInterfaceId("");//신규건으로처리위해 ID null 처리 할지  ID 그대로 처리할지 고민해 볼것
			/*
			 * 현재는 아래 에러나고 있음.
			 *### Error updating database. Cause: java.sql.SQLIntegrityConstraintViolationException: ORA-00001: 무결성 제약 조건(IIP.UK_TAN0201)에 위배됩니다 ### The error may involve defaultParameterMap ### The error occurred while setting parameters ### Cause: java.sql.SQLIntegrityConstraintViolationException: ORA-00001: 무결성 제약 조건(IIP.UK_TAN0201)에 위배됩니다 ; SQL []; ORA-00001: 무결성 제약 조건(IIP.UK_TAN0201)에 위배됩니다 ; nested exception is java.sql.SQLIntegrityConstraintViolationException: ORA-00001: 무결성 제약 조건(IIP.UK_TAN0201)에 위배됩니다
			 */
			interfaze.setRegDate(regDate);
			interfaze.setRegId(userId);

			List<pep.per.mint.common.data.basic.System>  systems = interfaze.getSystemList();
			for (pep.per.mint.common.data.basic.System system : systems) {
				system.setRegDate(regDate);
				system.setRegId(userId);
			}

			List<Business> businesses = interfaze.getBusinessList();
			for (Business business : businesses) {
				business.setRegDate(regDate);
				business.setRegId(userId);
			}

			List<DataAccessRole> roles = interfaze.getDataAccessRoleList();
			for (DataAccessRole dataAccessRole : roles) {
				dataAccessRole.setRegDate(regDate);
				dataAccessRole.setRegId(userId);
			}


			List<RelUser> relUsers = interfaze.getRelUsers();
			for (RelUser relUser : relUsers) {
				relUser.setRegDate(regDate);
				relUser.setRegId(userId);
			}


			String resultMsg = "success";
			String resultCd = "0";

			try {
				//주석처리 로직 ,20170724
				//처리하기전에 있으면 삭제하는 로직을 넣고 싶으면 주석처리한 부분을 풀어주세요.
				//일단 존재하면 처리하지 않는 것으로 합시다.
				//{ rs.deleteRequirementAndInterface(interfaze.getIntegrationId()); }

				res = rs.createRequirement(requirement);
				String requirementId = requirement.getRequirementId();
				res = cm.insertUploadDetail(batchId, rowIndex, requirementId);
				resultCount ++;
			}catch(DatabaseServiceException e){

				Throwable t = e.getCause();
				logger.debug("exception type:" + DuplicateKeyException.class.getName());
				if(t instanceof DuplicateKeyException){
					logger.info(Util.join("Interface[",interfaze,"] already exist, continue to move interface"),e);
					resultCd = "1";
					resultMsg = Util.join("skip(interface[",interfaze,"] already exist)");
				}else{
					resultCd = "9";
					resultMsg = Util.join("error",e  != null ? ":" + e.getMessage() : "");
					//throw e;
				}
				res = cm.insertUploadDetailError(batchId, rowIndex, interfaze.getIntegrationId(), resultCd, resultMsg);
				continue;
			}catch(Throwable e){
				resultCd = "9";
				resultMsg = Util.join("error",e  != null ? ":" + e.getMessage() : "");
				res = cm.insertUploadDetailError(batchId, rowIndex, interfaze.getIntegrationId(), resultCd, resultMsg);
				continue;
			}
			rowIndex ++;
		}
		summary.setResultCount(resultCount);
		summary.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		summary.setModId(userId);
		summary.setResultMsg("success");
		summary.setResultCd("0");
		res = cm.updateUploadSummary(summary);
		return resultCount;

	}


	public List<UploadSummary> getUploadSummaryList() throws Exception{
		return cm.getUploadSummaryList();
	}

	public List<UploadDetail> getUploadDetailList(String batchId) throws Exception{
		return cm.getUploadDetailList(batchId);
	}

	@Transactional
	public void deleteAllBeforeUpload() throws Exception {
		// TODO Auto-generated method stub
		rs.deleteAllRequirementAndInterface();
	}

}
