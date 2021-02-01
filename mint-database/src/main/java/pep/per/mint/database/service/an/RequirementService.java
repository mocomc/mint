/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.an;

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

import pep.per.mint.common.data.basic.Approval;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceMapping;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.InterfaceTag;
import pep.per.mint.common.data.basic.PageInfo;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.RequirementApproval;
import pep.per.mint.common.data.basic.RequirementApprovalUser;
import pep.per.mint.common.data.basic.RequirementAttatchFile;
import pep.per.mint.common.data.basic.RequirementComment;
import pep.per.mint.common.data.basic.RequirementTemp;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.RequirementModel;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.exception.DatabaseServiceException;
import pep.per.mint.database.mapper.an.RequirementMapper;
import pep.per.mint.database.mapper.co.ApprovalMapper;
import pep.per.mint.database.mapper.co.CommonMapper;
import pep.per.mint.database.mapper.su.B2BiMapper;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.rt.ModelService;
import pep.per.mint.database.service.selector.IdSelector;

/**
 * 요건 관련 서비스(조회, 입력, 삭제, 수정)
 *
 * @author Solution TF
 */

@Service
public class RequirementService {

	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	/**
	 *
	 */
	@Autowired
	RequirementMapper requirementMapper;

	@Autowired
	CommonMapper commonMapper;

	@Autowired
	IdSelector idSelector;

	@Autowired
	RequirementHerstoryService requirementHerstoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	B2BiMapper b2biMapper;


	/**
	 * <pre>
	 * 요건 리스트를 조회한다.
	 * REST-R01-AN-01-00 요건리스트조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getRequirementList(Map params) throws Exception{
		long startTime = System.currentTimeMillis();
		List<Requirement> requirementList = requirementMapper.getRequirementList(params);
		long endTime = System.currentTimeMillis();
		logger.error("getRequirementList::Query응답시간:"+ (endTime-startTime) +"ms" );
		return requirementList;
	}


	public List<Requirement> getRequirementListByPage(Map params) throws Exception{
		long startTime = System.currentTimeMillis();
		List<Requirement> requirementList = requirementMapper.getRequirementListByPage(params);
		long endTime = System.currentTimeMillis();
		logger.error("getRequirementListByPage::Query응답시간:"+ (endTime-startTime) +"ms" );
		return requirementList;
	}

	public List<Requirement> getRequirementListByPageV2(Map params) throws Exception{
		long startTime = System.currentTimeMillis();
		List<Requirement> requirementList = requirementMapper.getRequirementListByPageV2(params);
		long endTime = System.currentTimeMillis();
		logger.error("getRequirementListByPageV2::Query응답시간:"+ (endTime-startTime) +"ms" );
		return requirementList;
	}

	public int getRequirementTotalCount(Map params) throws Exception{
		long startTime = System.currentTimeMillis();
		int count = requirementMapper.getRequirementTotalCount(params);
		long endTime = System.currentTimeMillis();
		logger.error("getRequirementTotalCount::Query응답시간:"+ (endTime-startTime) +"ms" );
		return count;
	}

	/**
	 * 시스템에 등록된 모든 요건의 전체 건수 와 페이지 정보를 리턴한다.(삭제건 제외)
	 * @param params(int page, int perCount)
	 * @return
	 * @throws Exception
	 */
	public PageInfo getRequirementsPageInfo(Map<String, Object> params)throws Exception{
		return requirementMapper.getRequirementsPageInfo(params);
	}

	/**
	 * <pre>
	 * 요건 상세를 조회한다.
	 * REST-R02-AN-01-00 요건상세조회
	 * </pre>
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public Requirement getRequirementDetail(String requirementId) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("requirementId", requirementId);
		Requirement requirementDetail = requirementMapper.getRequirementDetail(params);
		return requirementDetail;
	}


	public Requirement getRequirementDetail(Map<String,String> params) throws Exception{
		Requirement requirementDetail = requirementMapper.getRequirementDetail(params);
		return requirementDetail;
	}

	public List<Requirement> getRequirementDetailList(Map<String,Object> params) throws Exception{
		List<Requirement> requirementDetailList = requirementMapper.getRequirementDetailList(params);
		return requirementDetailList;
	}

	public List<String> getRequirementIdList() throws Exception{
		return requirementMapper.getRequirementIdList();
	}
	
	
	/**
	 * GS 일괄업로드 Update 기능용
	 * 
	 * @param integrationId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getRequirementIdForUpdate(String integrationId) throws Exception{
		return requirementMapper.getRequirementIdForUpdate(integrationId);		
	}
	
	

	/**
	 * <pre>
	 * 요건을 등록한다.
	 * 요건이 가진 모든 복합유형 멤버들로 하나의 등록 트렌잭션으로 묶어 처리한다.
	 * REST-C01-AN-01-00 요건등록
	 * ---------------------------------------------------------
	 * 요건 등록 처리 프로세스
	 * ---------------------------------------------------------
	 * 1.Requirement(요건) insert
	 * ----------------------------------------------------------
	 * 2.List<RequirementComment>(요건설명 리스트) insert
	 * ----------------------------------------------------------
	 * 3.List<RequirementAttatchFile>(요건첨부파일 리스트) insert
	 *   요건 멀티파트 파일처리 어떻게 하는지 찾아볼것(해본지 오래되서...)
	 * ----------------------------------------------------------
	 * 4.Interface(인터페이스) 가 있다면 insert
	 *   해당 요건으로 기등록된 것이 있다면 Interface reuse 프로세스를 탄다.
	 *   인터페이스 등록 또는 재사용 프로세스는 요건처리 프로세스 1,2,3 보다 먼저 처리해도 무방하다.
	 * 	4.1.Interface(인터페이스) 기본정보 insert
	 * 	4.2.List<System>(인터페이스시스템맵핑 리스트) insert
	 * 	4.3.List<Business>(인터페이스업무맵핑 리스트) insert
	 * 	4.4.List<RelUser>(인터페이스담당자 리스트) insert
	 * 	4.5.List<InterfaceTag>(인터페이스태그 리스트) insert
	 *
	 * </pre>
	 * @param requirement
	 * @throws Exception
	 */
	@Transactional
	public int createRequirement(Requirement requirement) throws Exception{
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try{

			if(logger.isDebugEnabled())
			{
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건등록프로세스[RequirementService.updateRequirement(requirement) 처리시작]"));
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(requirement)); LogUtil.postbar(resultMsg);}catch(Exception e){}

			}

			//----------------------------------------------------------
			//0.Interface(인터페이스) 처리 시작
			//----------------------------------------------------------
			if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) 재사용 또는 신규생성 처리:");
			Interface interfaze = requirement.getInterfaceInfo();
			if(interfaze != null) {

//				String selectInterfceId = requirementMapper.selectInterface(interfaze.getInterfaceId());
				//if(selectInterfceId != null){
				if(!Util.isEmpty(interfaze.getInterfaceId())){
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) 재사용 또는 신규생성 처리 : 기존 인터페이스 존재하므로 Interface 재사용 프로세스 진행 ");
					resultCd = reuseInterface(interfaze, requirement.getRegUser(),resultMsg);
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) 재사용 또는 신규생성 처리 : 기존 인터페이스 없으므로 Interface 등록 진행 ");
					resultCd = createInterface(interfaze, requirement.getRegUser(), resultMsg);
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) 재사용 또는 신규생성 처리 OK") ;

			}

			//----------------------------------------------------------
			//1.Requirement(요건) insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) insert 처리:");
				resultCd = requirementMapper.insertRequirement(requirement);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) insert 처리 실패:requirementMapper.insertRequirement:result:",resultCd);
					throw new Exception(Util.join("Exception:RequirementService.createRequirement:requirementMapper.insertRequirement:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) insert 처리 OK:requiementId:",requirement.getRequirementId());
			}
			//----------------------------------------------------------
			//2.List<RequirementComment>(요건설명 리스트) insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 시작");
				List<RequirementComment> commentList = requirement.getCommentList();
				//2.1.등록할 요건설명 리스트가 존재하면
				if(commentList != null && commentList.size() != 0){
					int cnt = 0;
					for (RequirementComment rc : commentList) {
						rc.setRequirementId(requirement.getRequirementId());
						resultCd = requirementMapper.insertRequirementComment(rc);
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 처리 실패:requirementMapper.insertRequirementComment:result:", resultCd);
							//에외 던질때 파라메터도 더 상세히 던질수 있도록 하자.
							throw new Exception(Util.join("Exception:RequirementService.createRequirement:requirementMapper.insertRequirementComment:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}


					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 OK(총 요건설명 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 -> 등록할 요건설명 리스트가 존재하지 않는다.");
				}
			}
			//----------------------------------------------------------
			//3.List<RequirementAttatchFile>(요건첨부파일 리스트) insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 시작");
				List<RequirementAttatchFile> attatchFileList = requirement.getAttatchFileList();
				//3.1.등록할 요건첨부파일 리스트가 존재하면
				if(attatchFileList != null && attatchFileList.size() != 0){
					int cnt = 0;
					for (RequirementAttatchFile raf : attatchFileList) {
						raf.setRequirementId(requirement.getRequirementId());
						resultCd = requirementMapper.insertRequirementAttatchFile(raf);
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 처리 실패:requirementMapper.insertRequirementAttatchFile:result:", resultCd);
							throw new Exception(Util.join("Exception:RequirementService.createRequirement:requirementMapper.insertRequirementAttatchFile:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}

					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 OK(총 요건설명 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 -> 등록할 요건설명 리스트가 존재하지 않는다.");
				}
			}

			//----------------------------------------------------------
			//4.히스토리 처리
			//----------------------------------------------------------
			{
				//requirementHerstoryService.addRequirementHerstory(requirement);
				requirementHerstoryService.addRequirementHistory(requirement);
			}

			return resultCd;
		}finally{


			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("요건등록프로세스[RequirementService.createRequirement(requirement) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}


	/**
	 * <pre>
	 * 요건을 수정한다.
	 * 요건이 가진 모든 복합유형 멤버들로 하나의 등록 트렌잭션으로 묶어 처리한다.
	 * REST-U01-AN-01-00 요건수정
	 * ---------------------------------------------------------
	 * 요건 수정 처리 프로세스
	 * ---------------------------------------------------------
	 * 0.Interface(인터페이스) 수정 또는 등록 프로세스
	 * 	0.1.Interface(인터페이스) 기본정보 등록 또는 수정(기존 건이 있으면 수정)
	 * 	0.2.List<System>(인터페이스시스템맵핑 리스트) 기존건 전체삭제 및 신규등록
	 * 	0.3.List<Business>(인터페이스업무맵핑 리스트) 기존건 전체삭제 및 신규등록
	 * 	0.4.List<RelUser>(인터페이스담당자 리스트) 기존건 전체삭제 및 신규등록
	 * 	0.5.List<InterfaceTag>(인터페이스태그 리스트) 기존건 전체삭제 및 신규등록
	 * ----------------------------------------------------------
	 * 1.Requirement(요건) update
	 * ----------------------------------------------------------
	 * 2.List<RequirementComment>(요건설명 리스트) 기존건 전체삭제 및 신규등록
	 * ----------------------------------------------------------
	 * 3.List<RequirementAttatchFile>(요건첨부파일 리스트) 기존건 전체삭제 및 신규등록
	 *   요건 멀티파트 파일처리 어떻게 하는지 찾아볼것(해본지 오래되서...)
	 *
	 * </pre>
	 * @param requirement
	 * @throws Exception
	 */
	@Transactional
	public int updateRequirement(Requirement requirement) throws Exception{
		return updateRequirement(requirement, false);
	}
	
	@Transactional
	public int updateRequirement(Requirement requirement, boolean isExcelUpload) throws Exception{

		StringBuffer resultMsg = null;
		int resultCd = 0;
		String herstoryVersion = null;
		try{

			if(logger.isDebugEnabled()){
				resultMsg = new StringBuffer();

				LogUtil.bar2(LogUtil.prefix(resultMsg,"요건수정프로세스[RequirementService.updateRequirement(requirement) 처리시작]"));				
				LogUtil.prefix(resultMsg,"params:",Util.toJSONString(requirement));
				LogUtil.postbar(resultMsg);
			}

			//----------------------------------------------------------
			//0.Interface(인터페이스) upsert 처리 시작
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리:");
				Interface interfaze = requirement.getInterfaceInfo();
				//------------------------------------------------------
				//change
				//------------------------------------------------------
				//사유 :
				//현재 인터페이스 정보 없이 요건 등록이 불가능하도록 되어 있으므로 요건 수정시 인터페이스는 항상 존재하는 것으로 봐도 됨
				//따라서 인터페이스 NULL여부 체크는 의미 없음.
				//------------------------------------------------------
				/*String selectInterfceId = requirementMapper.selectInterface(interfaze.getInterfaceId());
				if(selectInterfceId != null){
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리:기존인터페이스 존재하므로 Interface 수정 프로세스 진행 ");

					if(Environments.entityHerstoryOn) {
						herstoryVersion = requirementMapper.selectRequirementColumnHerstoryVersion(requirement.getRequirementId());
					}

					resultCd = updateInterface(interfaze, resultMsg, requirement.getRequirementId(), herstoryVersion);
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리:기존인터페이스 없으므로 Interface 등록 프로세스 진행 ");
					resultCd = createInterface(interfaze, resultMsg);
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리 OK") ;*/

				if(interfaze != null){
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리:기존인터페이스 존재하므로 Interface 수정 프로세스 진행 ");
					User modUser = commonService.getUser(requirement.getModId());
					resultCd = updateInterface(interfaze, modUser, resultMsg, requirement.getRequirementId(), herstoryVersion);
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface(인터페이스) upsert 처리 OK") ;

			}
			//----------------------------------------------------------
			//1.Requirement(요건) update
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) update 처리:requiementId:",requirement.getRequirementId());
				resultCd = requirementMapper.updateRequirement(requirement);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) update 처리 실패:requirementMapper.updateRequirement:result:",resultCd);
					throw new Exception(Util.join("Exception:RequirementService.updateRequirement:requirementMapper.updateRequirement:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) update 처리 OK:requiementId:",requirement.getRequirementId());
			}
			//----------------------------------------------------------
			//2.List<RequirementComment>(요건설명 리스트) upsert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllRequirementComment(requirement.getRequirementId(), requirement.getModId(), requirement.getModDate());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 시작");
				List<RequirementComment> commentList = requirement.getCommentList();
				//2.1.등록할 요건설명 리스트가 존재하면
				if(commentList != null && commentList.size() != 0){
					int cnt = 0;
					for (RequirementComment rc : commentList) {
						resultCd = requirementMapper.insertRequirementComment(rc);
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 처리 실패:requirementMapper.insertRequirementComment:result:",resultCd);
							throw new Exception(Util.join("Exception:RequirementService.updateRequirement:requirementMapper.insertRequirementComment:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}

					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 OK(총 요건설명 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 등록 -> 등록할 요건설명 리스트가 존재하지 않는다.");
				}

			}
			//----------------------------------------------------------
			//3.List<RequirementAttatchFile>(요건첨부파일 리스트) upsert
			//----------------------------------------------------------
			if( !isExcelUpload ) {
				{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 삭제(DEL_YN = 'Y') 시작");
					resultCd = requirementMapper.deleteAllRequirementAttatchFile(requirement.getRequirementId(), requirement.getModId(), requirement.getModDate());
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 삭제 OK");
				}
				{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 시작");
					List<RequirementAttatchFile> attatchFileList = requirement.getAttatchFileList();
					//3.1.등록할 요건첨부파일 리스트가 존재하면
					if(attatchFileList != null && attatchFileList.size() != 0){
						int cnt = 0;
						for (RequirementAttatchFile raf : attatchFileList) {
							raf.setRequirementId(requirement.getRequirementId());
							resultCd = requirementMapper.insertRequirementAttatchFile(raf);
							if(resultCd != 1) {
								if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 처리 실패:requirementMapper.insertRequirementAttatchFile:result:",resultCd);
								throw new Exception(Util.join("Exception:RequirementService.updateRequirement:requirementMapper.insertRequirementAttatchFile:resultCd:(", resultCd, ")"));
							}
							cnt ++;
						}
						if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 OK(총 요건설명 등록 건수 : ",cnt,")");
					}else{
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 등록 -> 등록할 요건설명 리스트가 존재하지 않는다.");
					}
				}
			}

			//----------------------------------------------------------
			//4.히스토리 처리
			//----------------------------------------------------------
			{
				//requirementHerstoryService.addRequirementHerstory(requirement);
				requirementHerstoryService.addRequirementHistory(requirement);
			}

			//----------------------------------------------------------
			// ExcelUpload의 경우 resultCd 값을 참조하므로  1로 처리
			// 이부분에 올 때까지 예외로 빠지지 않았다면 정상 처리로 판단
			//----------------------------------------------------------
			if(isExcelUpload){	resultCd = 1;	}
			
			return resultCd;


		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("인터페이스수정 프로세스[RequirementService.updateRequirement(requirement) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}	




	/**
	 * <pre>
	 * 요건을 삭제한다.
	 * 요건이 가진 모든 복합유형 멤버들로 하나의 등록 트렌잭션으로 묶어 처리한다.
	 * REST-D01-AN-01-00 요건삭제
	 * ---------------------------------------------------------
	 * 요건 삭제 처리 프로세스
	 * ---------------------------------------------------------
	 * 0.Interface 삭제 부분은 별도의 프로세스에서 처리하는 것으로 한다.
	 * ----------------------------------------------------------
	 * 1.Requirement(요건) delete : DEL_YN = 'Y'
	 * ----------------------------------------------------------
	 * 2.List<RequirementComment>(요건설명 리스트) delete : DEL_YN = 'Y'
	 * ----------------------------------------------------------
	 * 3.List<RequirementAttatchFile>(요건첨부파일 리스트) delete  DEL_YN = 'Y'
	 *   요건 멀티파트 파일처리 어떻게 하는지 찾아볼것(해본지 오래되서...)
	 *
	 * </pre>
	 * @param requirementId
	 * @throws Exception
	 */
	@Transactional
	public int deleteRequirement(String requirementId, String modId, String modDate) throws Exception{

		int resultCd = 0;

		StringBuffer resultMsg = null;

		try{
			if(logger.isDebugEnabled()){
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건삭제프로세스[RequirementService.deleteRequirement(requirementId:", requirementId, ",modId:", modId, ",modDate:", modDate, ") 처리시작]"));
			}


			//----------------------------------------------------------
			//1.Requirement(요건) delete 처리
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) delete 처리시작");
				resultCd = requirementMapper.deleteRequirement(requirementId, modId, modDate);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) delete 처리에러(result:",resultCd,")");
					throw new Exception(Util.join("Exception:RequirementService.deleteRequirement:requirementMapper.deleteRequirement:resultCd:(", resultCd, ")"));

				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) delete 처리성공(result:",resultCd ,")");
			}
			//----------------------------------------------------------
			//2.요건설명 리스트 delete 처리
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 delete 처리");
				resultCd = requirementMapper.deleteAllRequirementComment(requirementId, modId, modDate);
				if(resultCd < 0) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건설명 리스트 delete 처리에러(result:",resultCd,")");
					throw new Exception(Util.join("Exception:RequirementService.deleteRequirement:requirementMapper.deleteAllRequirementComment:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "요건설명 리스트 delete 처리성공(result:", resultCd, ")");
			}
			//----------------------------------------------------------
			//3.요건첨부파일 리스트 delete
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 delete");
				resultCd = requirementMapper.deleteAllRequirementAttatchFile(requirementId, modId, modDate);
				if(resultCd < 0) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 delete 처리에러(result:",resultCd,")");
					throw new Exception(Util.join("Exception:RequirementService.deleteRequirement:requirementMapper.deleteAllRequirementAttatchFile:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"요건첨부파일 리스트 delete 처리성공(result:",resultCd,")");
			}



			//----------------------------------------------------------
			//4.Interface(인터페이스) delete 처리
			//----------------------------------------------------------
//			{
//
//				List<Map<String, String>> refRequirementInfo = requirementMapper.selectRefRequirementInfo(requirementId);
//				if(refRequirementInfo == null || refRequirementInfo.size() == 0){
//					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"삭제할 인터페이스정보가 없음.");
//				}else{
//					if(refRequirementInfo.size() == 1){
//
//					}else{
//						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"삭제할 인터페이스정보가 없음.");
//					}
//				}
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 담당자 삭제.");
//				requirementMapper.deleteInterfaceRelUsers();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 담당자 삭제 OK.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 시스템매핑 삭제.");
//				requirementMapper.deleteInterfaceSystemMaps();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 시스템매핑 삭제.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 업무매핑 삭제.");
//				requirementMapper.deleteInterfaceBusinessMaps();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 업무매핑 삭제OK.");
//
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface TAG 삭제.");
//				requirementMapper.deleteInterfaceTags();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface TAG 삭제Ok.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스매핑");
//				requirementMapper.deleteInterfaceMaps();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스매핑 OK.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 관심인터페이스 삭제.");
//				requirementMapper.deleteInterfaceConcern();
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 관심인터페이스 삭제 OK.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 장애영향시스템 삭제.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Interface 장애영향인터페이스 삭제.");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"연계채널별인터페이스특성매핑");
//
//				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스그룹아이템");
//
//
//
//
//			}


			//----------------------------------------------------------
			//4.Interface(인터페이스) Mapping 정보 삭제 처리
			//  - 레퍼런스 요건 정보가 없을 경우 해당 인터페이스의 맵핑 정보와, B2Bi MetaData 정보는 삭제한다.
			//----------------------------------------------------------

			Map<String,String> refCheckData = requirementMapper.getInterfaceRefCheck(requirementId);

			if( refCheckData != null ) {
				String interfaceId = refCheckData.get("INTERFACE_ID");
				String refYn       = refCheckData.get("REF_YN");

				if( refYn != null && refYn.equals("N") ) {

					if (logger.isDebugEnabled())
					LogUtil.prefix(resultMsg, "인터페이스에 참조되는 요건이 존재하지 않음. InterfaceId : [", interfaceId, "]");

					resultCd = requirementMapper.removeInterfaceMapping(interfaceId);
					if (logger.isDebugEnabled())
					LogUtil.prefix(resultMsg, "인터페이스에 참조되는 요건이 존재하지 않아 InterfaceId : [", interfaceId, "] 의 AS-IS 인터페이스 맵핑 삭제 처리함.(result:", resultCd, ")");

					resultCd = b2biMapper.removeB2BiInterfaceMetaData(interfaceId);
					if (logger.isDebugEnabled())
					LogUtil.prefix(resultMsg, "인터페이스에 참조되는 요건이 존재하지 않아 InterfaceId : [", interfaceId, "] 의 B2Bi MetaData 삭제 처리함.(result:", resultCd, ")");

				}

			}


			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("요건삭제프로세스[RequirementService.deleteRequirement() 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}

	/**
	 * <pre>
	 * 요건을 복원한다.
	 * </pre>
	 * @param requirementId
	 * @throws Exception
	 */
	@Transactional
	public int restoreRequirement(String requirementId, String modId, String modDate) throws Exception{

		int resultCd = 0;

		StringBuffer resultMsg = null;

		try{
			if(logger.isDebugEnabled()){
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건삭제프로세스[RequirementService.restoreRequirement(requirementId:", requirementId, ",modId:", modId, ",modDate:", modDate, ") 처리시작]"));
			}

			//----------------------------------------------------------
			//1.Requirement(요건) update 처리
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) restore 처리시작");
				resultCd = requirementMapper.restoreRequirement(requirementId, modId, modDate);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) restore 처리에러(result:",resultCd,")");
					throw new Exception(Util.join("Exception:RequirementService.restoreRequirement:requirementMapper.restoreRequirement:resultCd:(", resultCd, ")"));

				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Requirement(요건) restore 처리성공(result:",resultCd ,")");
			}

			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("요건복원프로세스[RequirementService.restoreRequirement() 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}




	/**
	 * <pre>
	 * 인터페이스 등록 처리 프로세스
	 * ----------------------------
	 * 1.인터페이스 테이블 등록
	 * 2.인터페이스 시스템 맵핑 리스트 등록
	 * 3.인터페이스 업무 맵핑 리스트 등록
	 * 4.인터페이스 담당자 맵핑 리스트 등록
	 * 5.인터페이스 태그 리스트 등록
	 * 6.인터페이스 B2Bi MetaData 등록
	 * </pre>
	 * @param interfaze
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int createInterface(Interface interfaze, User regUser, StringBuffer resultMsg) throws Exception {
		try {

			int resultCd = 0;

			if(logger.isDebugEnabled() && resultMsg != null){
				LogUtil.prefix(resultMsg,LogUtil.bar(LogUtil.prefix("인터페이스신규생성 프로세스[RequirementService.createInterface(interfaze) 처리시작]")));
				try{  LogUtil.prefix(resultMsg,"params:",Util.toJSONString(interfaze)); LogUtil.postbar(resultMsg);}catch(Exception e){}
			}

			//-------------------------------------
			//1.인터페이스 테이블 등록
			//-------------------------------------
			{
				if (logger.isDebugEnabled()) {
					LogUtil.prefix(resultMsg, "인터페이스 등록 시작");
					LogUtil.prefix(resultMsg, "인터페이스 ID 채번 작업 시작");
				}

				// 인터페이스 ID  값이 업는 경우만 자동 채번설정을 한다.
				if(interfaze.getIntegrationId() == null  || interfaze.getIntegrationId().trim().length()==0){

					Object[] params = {interfaze};

					// TODO
					// HDINS 현대해해상 자동발먼.
					Map envMap =  commonService.getEnvironmentalValues();
					boolean isAutoCreateID = false;
					boolean isInterfaceAdminAutoCreateID = false;
					try{
						List attrList = (List)envMap.get("system.auto.interfaceId.create");
						if(attrList != null && attrList.size()>0){
							isAutoCreateID = Boolean.parseBoolean((String) attrList.get(0));
						}
					}catch(Throwable e){

					}

					//Question: 여기서 'system.auto.interfaceId.interface.admin.create' 환경 변수를  체크할 필요가 있나 고민 해보자
					//			이 부분은 버전 3.0에서 정리하도록 하자. [B747~B759]
					//B747
					{
						try{
							List attrList1 = (List)envMap.get("system.auto.interfaceId.interface.admin.create");
							if(attrList1 != null && attrList1.size()>0){
								isInterfaceAdminAutoCreateID = Boolean.parseBoolean((String) attrList1.get(0));
							}
						}catch(Throwable e){

						}
						if("Y".equalsIgnoreCase(regUser.getRole().getIsInterfaceAdmin())){
							if(isInterfaceAdminAutoCreateID){
								isAutoCreateID = true;
							}
						}
					}//B759
					if(isAutoCreateID){
						try{
							interfaze.setIntegrationId(idSelector.getInterfaceId(params));
						}catch(Throwable e){
							if(logger.isDebugEnabled())LogUtil.prefix(resultMsg,"인터페이스 ID 채번 작업 FAIL:", e.getMessage());
							throw new Exception(Util.join("Exception:RequirementService.createInterface:idSelector.getInterfaceId",e.getMessage()), e);
						}
					}
				}

				//
				interfaze.setStatus("0");
				try {
					// TODO HAS_DATA_MAP 값이 없는 경우 '0'으로 지정.  bill
					if(interfaze.getHasDataMap() == null || interfaze.getHasDataMap().equalsIgnoreCase("")){
						interfaze.setHasDataMap("N");
					}

					resultCd = requirementMapper.insertInterface(interfaze);
				}catch(org.springframework.dao.DuplicateKeyException e){
//					String interfaceId = requirementMapper.selectInterfaceId(interfaceIdInfo);
//					StringBuffer errorMsg = new StringBuffer("기존에 등록된 인터페이스가 존재합니다.\r\n 기존 인터페이스가 "
//							+ "인터페이스 아이디 발번 체계에 맞지않게 발번되었을 수 있으므로 확인이 필요합니다.\r\n");
//					try {
//						if (!Util.isEmpty(interfaceId)) {
//							Interface interfaceExistAlready = commonMapper.getInterfaceDetail(interfaceId);
//							if (interfaceExistAlready != null) {
//								String interfaceNm = interfaceExistAlready.getInterfaceNm();
//								String channelCd = interfaceExistAlready.getChannel().getChannelCd();
//								DisplaySystemInfo receiveSystem = interfaceExistAlready.getReceiverSystemInfoList().get(0);
//								String resourceNm = receiveSystem.getResourceNm();
//								String service = receiveSystem.getService();
//								String systemCd = receiveSystem.getSystemCd();
//
//								errorMsg.append("기존 인터페이스 정보: interfaceId:").append(interfaceId);
//								errorMsg.append(", interface명:").append(interfaceNm);
//								errorMsg.append(", 솔루션코드:").append(channelCd);
//								errorMsg.append(", Provider 서비스 구분(리소스):").append(resourceNm);
//								errorMsg.append(", 서비스제공 시스템코드:").append(systemCd);
//								errorMsg.append(", 서비스:").append(service);
//							}
//
//						}
//					}catch(Exception e1){logger.error("예외처리중 에러발생",e1);}
					throw new DatabaseServiceException(e.getMessage(),e);
				}

				if(resultCd <= 0){
					if(logger.isDebugEnabled())LogUtil.prefix(resultMsg,"인터페이스 등록 실패:createInterface:resultCd:",resultCd);
					throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterface:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled())LogUtil.prefix(resultMsg,"인터페이스 등록 OK:interfaceId:[",interfaze.getInterfaceId(),"]");
			}

			String systemGenerateInterfaceId = null;
			{
				systemGenerateInterfaceId = requirementMapper.selectSystemGenerateInterfaceId(interfaze.getIntegrationId());
				interfaze.setInterfaceId(systemGenerateInterfaceId);
			}

			//-------------------------------------
			//2.인터페이스 시스템 맵핑 리스트 등록
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템 맵핑 리스트 등록  시작");
				List<pep.per.mint.common.data.basic.System> systemList = interfaze.getSystemList();
				for (pep.per.mint.common.data.basic.System system : systemList) {
					system.setRegDate(interfaze.getRegDate());
					system.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceSystemMap(systemGenerateInterfaceId, system);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 실패 : insertInterfaceSystemMap:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceSystemMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 OK");
			}
			//-------------------------------------
			//3.인터페이스 업무 맵핑 리스트 등록
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 시작");
				List<Business> businessList = interfaze.getBusinessList();
				for (Business business : businessList) {
					business.setRegDate(interfaze.getRegDate());
					business.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceBusinessMap(systemGenerateInterfaceId, business);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 실패 : insertInterfaceBusinessMap:resultCd:").append(resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceBusinessMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"\n인터페이스 업무맵핑 등록 OK");
			}
			//-------------------------------------
			//4.인터페이스 담당자 맵핑 리스트 등록
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 시작");
				List<RelUser> relUsers = interfaze.getRelUsers();
				for (RelUser relUser : relUsers) {
					relUser.setRegDate(interfaze.getRegDate());
					relUser.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceRelUser(systemGenerateInterfaceId, relUser);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 실패 : insertInterfaceRelUser:resultCd:").append( resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceRelUser:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 OK");
				//히스토리작업처리
				//if(Environments.entityHerstoryOn) insertInterfaceRelUserHerstory(systemGenerateInterfaceId, interfaze.getRegDate(), interfaze.getRegId());
			}
			//-------------------------------------
			//5.인터페이스 태그 리스트 등록
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 시작");
				List<InterfaceTag> tagList = interfaze.getTagList();
				for (InterfaceTag interfaceTag : tagList) {
					interfaceTag.setRegDate(interfaze.getRegDate());
					interfaceTag.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceTag(systemGenerateInterfaceId, interfaceTag);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 TAG 등록 실패 : insertInterfaceTag:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceTag:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 TAG 등록 OK");
			}

			//-------------------------------------
			//6.인터페이스 맵핑정보 등록
			//-------------------------------------
			{

				InterfaceMapping mapping = interfaze.getInterfaceMapping();
				if(mapping == null || Util.isEmpty(mapping.getAsisInterfaceId())) {
					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보가 없으므로 처리하지 않는다.");
				}else{
					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 시작");

					mapping.setInterfaceId(systemGenerateInterfaceId);
					mapping.setChannelId(interfaze.getChannel().getChannelId());
					mapping.setRegDate(interfaze.getRegDate());
					mapping.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceMapping(mapping);
					if (resultCd <= 0) {
						if (logger.isDebugEnabled())
							LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 실패 : insertInterfaceMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceMapping:resultCd:(", resultCd, ")"));
					}

					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 OK");
				}
			}

			//-------------------------------------
			//7.인터페이스 특성 맵핑. ( 추가 속성 / 연계채널별 )
			//-------------------------------------
			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 삭제 시작");
				resultCd = requirementMapper.removeChannelPropertyMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<InterfaceProperties> propertiesList = interfaze.getProperties();
				for( InterfaceProperties properties : propertiesList ) {
					insertParams.put("channelId"  , interfaze.getChannel().getChannelId());
					insertParams.put("attrId"     , properties.getAttrId());
					insertParams.put("idx"        , 0);
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("attrValue"  , properties.getAttrValue());
					insertParams.put("regDate"    , interfaze.getRegDate());
					insertParams.put("regId"      , interfaze.getRegId());
					insertParams.put("modDate"    , interfaze.getRegDate());
					insertParams.put("modId"      , interfaze.getRegId());

					String type = properties.getType();
					if( type != null && type.equals("1") ) {
						resultCd = requirementMapper.insertInterfaceAdditionalPropertyMapping(insertParams);

						if(resultCd <= 0){
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 특성 맵핑 등록 실패 : insertInterfaceAdditionalPropertyMapping:resultCd:", resultCd);
							throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceAdditionalPropertyMapping:resultCd:(", resultCd, ")"));
						}
					} else {
						resultCd = requirementMapper.insertChannelPropertyMapping(insertParams);

						if(resultCd <= 0){
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 특성 맵핑 등록 실패 : insertChannelPropertyMapping:resultCd:", resultCd);
							throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertChannelPropertyMapping:resultCd:(", resultCd, ")"));
						}
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 등록 OK");
			}
			//-------------------------------------
			//8.DataAccess 권한 맵핑.
			//-------------------------------------
/*			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 시작");
				resultCd = requirementMapper.removeDataAccessRoleMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<DataAccessRole> dataAccessRoleList = interfaze.getDataAccessRoleList();
				int seq = 0;
				for( DataAccessRole dataAccessRole : dataAccessRoleList ) {
					insertParams.put("roleId"     , dataAccessRole.getRoleId());
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("seq"        , seq++);
					resultCd = requirementMapper.insertDataAccessRoleMapping(insertParams);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 DataAccess 권한 맵핑 등록 실패 : insertDataAccessRoleMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.crateInterface:requirementMapper.insertDataAccessRoleMapping:resultCd:(", resultCd, ")"));
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 OK");
			}*/

			return resultCd;

		}finally{
			if(logger.isDebugEnabled()) resultMsg.append(LogUtil.bar(LogUtil.prefix("인터페이스등록 프로세스[RequirementService.createInterface(interfaze) 처리종료]")));
		}
	}



	/**
	 * @param interfaze
	 * @return
	 */
	@Transactional
	public int updateInterface(Interface interfaze, User modUser, StringBuffer resultMsg, String requirementId, String herstoryVersion) throws Exception {

		try{
			int resultCd = 0;

			if(logger.isDebugEnabled() && resultMsg != null){
				LogUtil.prefix(resultMsg,LogUtil.bar(LogUtil.prefix("인터페이스수정 프로세스[RequirementService.updateInterface(interfaze) 처리시작]")));
				try{  LogUtil.prefix(resultMsg,"params:",Util.toJSONString(interfaze)); LogUtil.postbar(resultMsg);}catch(Exception e){}
			}

			//-------------------------------------
			//0.시스템에서 발번한 인터페이스 ID
			//-------------------------------------
			String systemGenerateInterfaceId = interfaze.getInterfaceId();


			//-------------------------------------
			//1.인터페이스 테이블 update
			//-------------------------------------
			{


				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업데이트 시작");

				//--------------------------------------------------------------------------------------------------------
				//1.1.인터페이스ID 변경 여부 체크
				//--------------------------------------------------------------------------------------------------------
				// 인터페이스 ID 자동수정 로직
				//--------------------------------------------------------------------------------------------------------
				// 사용된 포털환경설정 값 :
				//		자동수정 옵션	:system.auto.interfaceId.update
				//		어드민채번 옵션	:system.auto.interfaceId.interface.admin.create
				//--------------------------------------------------------------------------------------------------------
				//	IF [자동수정옵션이 true 일 경우] {
				//		IF [어드민 채번 옵션 false OR 어드민이 아닐 경우] {
				//			자동수정한다.
				//		}ELSE{
				//			IF [인터페이스 ID 가 NULL(의도적으로 빈값을 보낸 경우, 어드민 채번 옵션 true 이고 어드민일때 만 빈값을 보낸수 있음.)] {
				//				자동수정한다.
				//			}
				//		}
				//	}
				//--------------------------------------------------------------------------------------------------------
				{
					Object[] params = {interfaze};
					String integrationId = interfaze.getIntegrationId();
					boolean isAutoUpdateInterfaceId = false;
					{
						Map envMap =  commonService.getEnvironmentalValues("system", "auto.interfaceId.update");
						try{
							List attrList = (List)envMap.get("system.auto.interfaceId.update");
							if(attrList != null && attrList.size() > 0){
								isAutoUpdateInterfaceId = Boolean.parseBoolean((String) attrList.get(0));
							}
						}catch(Throwable e){}
					}

					boolean isInterfaceAdminCreate = false;
					{
						Map envMap =  commonService.getEnvironmentalValues("system", "auto.interfaceId.interface.admin.create");
						try{
							List attrList = (List)envMap.get("system.auto.interfaceId.interface.admin.create");
							if(attrList != null && attrList.size()>0){
								isInterfaceAdminCreate = Boolean.parseBoolean((String) attrList.get(0));
							}
						}catch(Throwable e){}
					}
					boolean isAdminUser = false;
					if("Y".equalsIgnoreCase(modUser.getRole().getIsInterfaceAdmin())){
						isAdminUser = true;
					}

					if(isAutoUpdateInterfaceId){
						if(!isInterfaceAdminCreate || !isAdminUser){
							try{
								interfaze.setIntegrationId(idSelector.getInterfaceId(params));
							}catch(Throwable e){
								if(logger.isDebugEnabled())LogUtil.prefix(resultMsg,"인터페이스 ID 채번 작업 FAIL:", e.getMessage());
								throw new Exception(Util.join("Exception:updateInterface:idSelector.getInterfaceId",e.getMessage()), e);
							}
						}else{
							if(Util.isEmpty(integrationId)){
								try{
									interfaze.setIntegrationId(idSelector.getInterfaceId(params));
								}catch(Throwable e){
									if(logger.isDebugEnabled())LogUtil.prefix(resultMsg,"인터페이스 ID 채번 작업 FAIL:", e.getMessage());
									throw new Exception(Util.join("Exception:updateInterface:idSelector.getInterfaceId",e.getMessage()), e);
								}
							}
						}
					}
				}
				//--------------------------------------------------------------------------------------------------------

				// TODO HAS_DATA_MAP 값이 없는 경우 '0'으로 지정.  bill
				if(interfaze.getHasDataMap() == null || interfaze.getHasDataMap().equalsIgnoreCase("")){
					interfaze.setHasDataMap("N");
				}
				resultCd = requirementMapper.updateInterface(interfaze);
				if(resultCd <= 0){
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업데이트 실패:updateInterface:resultCd:", resultCd);
					throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.updateInterface:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업데이트 OK");
			}

			//-------------------------------------
			//2.인터페이스 시스템 맵핑 리스트 수정/추가처리
			//-------------------------------------
			//이전꺼 무조건 삭제하고 다시 등록하는 로직
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceSystemMap(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId() );
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 전체 삭제 OK");
		 	}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 시작");
				List<pep.per.mint.common.data.basic.System> systemList = interfaze.getSystemList();
				for (pep.per.mint.common.data.basic.System system : systemList) {
					system.setRegDate(interfaze.getRegDate());
					system.setRegId(interfaze.getRegId());

					resultCd = requirementMapper.insertInterfaceSystemMap(systemGenerateInterfaceId, system);

					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 실패 : insertInterfaceSystemMap:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertInterfaceSystemMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 OK");

				//히스토리작업처리
				//if(Environments.entityHerstoryOn) insertInterfaceSystemMapHerstory(systemGenerateInterfaceId,interfaze.getModDate(),interfaze.getModId());

			}
			//-------------------------------------
			//3.인터페이스 업무 맵핑 리스트 수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceBusinessMap(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 시작");
				List<Business> businessList = interfaze.getBusinessList();
				for (Business business : businessList) {
					business.setRegDate(interfaze.getRegDate());
					business.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceBusinessMap(systemGenerateInterfaceId, business);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 업무맵핑 등록 실패 : insertInterfaceBusinessMap:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertInterfaceBusinessMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 OK");
				//히스토리작업처리
				//if(Environments.entityHerstoryOn) insertInterfaceBusinessMapHerstory(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());

			}
			//-------------------------------------
			//4.인터페이스 담당자 맵핑 리스트 수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceRelUsers(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 시작");
				List<RelUser> relUsers = interfaze.getRelUsers();
				for (RelUser relUser : relUsers) {
					relUser.setRegDate(interfaze.getRegDate());
					relUser.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceRelUser(systemGenerateInterfaceId, relUser);

					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 실패 : insertInterfaceRelUser:resultCd:").append(resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertInterfaceRelUser:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 OK");
			}
			//-------------------------------------
			//5.인터페이스 태그 리스트  수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceTags(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 시작");
				List<InterfaceTag> tagList = interfaze.getTagList();
				for (InterfaceTag interfaceTag : tagList) {
					interfaceTag.setRegDate(interfaze.getRegDate());
					interfaceTag.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceTag(systemGenerateInterfaceId, interfaceTag);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 TAG 등록 실패 : insertInterfaceTag:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertInterfaceTag:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 OK");
			}

			//-------------------------------------
			//6.인터페이스 맵핑정보 수정
			//-------------------------------------
			// 기존 키로 맵핑등록  건이 있으면 SKIP
			// 없으면 기존건 모두 DEL_YN = 'Y' 처리하고
			// 신규 INSERT
			//-------------------------------------
			// [변경] 2016/09/02
			// (1) interfaceId 로 맵핑된 asInterfaceId 를 삭제 ( asInterfaceId 를 다른 인터페이스에서 맵핑할 수 있도록 해야함. )
			// (2) 신규 Insert
			//-------------------------------------

			// (1) 무조건 이전 정보는 삭제 처리 한다.
			resultCd = requirementMapper.removeInterfaceMapping(systemGenerateInterfaceId);
			if (logger.isDebugEnabled())
				LogUtil.prefix(resultMsg, "인터페이스[", interfaze.getInterfaceId(), "] 의 기존 매핑정보 완전 삭제처리함.");

			InterfaceMapping mapping = interfaze.getInterfaceMapping();

			// (2) 맵핑정보가 없으면(전사솔루션이 IIB 이거나 맵핑키가 NULL 로 설정되어 있을경우.) SKIP
			if(mapping == null || Util.isEmpty(mapping.getAsisInterfaceId())) {
				if (logger.isDebugEnabled())
					LogUtil.prefix(resultMsg, "인터페이스[", interfaze.getInterfaceId(), "] 의 매핑정보가 없으므로 Skip");
			} else {

				mapping.setChannelId(interfaze.getChannel().getChannelId());
				mapping.setInterfaceId(systemGenerateInterfaceId);
				mapping.setRegDate(interfaze.getRegDate());
				mapping.setRegId(interfaze.getRegId());
				mapping.setModDate(interfaze.getModDate());
				mapping.setModId(interfaze.getModId());

				// 조회를 해볼 이유는 없지만..기존 로직 살려서 한번 해볼까?
				String asInterfaceId = requirementMapper.selectInterfaceMapping(mapping);

				if (Util.isEmpty(asInterfaceId)) {

					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 시작");
					}

					resultCd = requirementMapper.insertInterfaceMapping(mapping);

					if (resultCd <= 0) {
						if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 실패 : insertInterfaceMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceMapping:resultCd:(", resultCd, ")"));
					}

					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 OK");

				} else {
					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보가 기존정보와 동일하여 처리하지 않는다.");
				}

			}

			/* 2016/09/02 주석 처리
			if(mapping == null || Util.isEmpty(mapping.getAsisInterfaceId())) {

				resultCd = requirementMapper.removeInterfaceMapping(systemGenerateInterfaceId);
				if (logger.isDebugEnabled())
					LogUtil.prefix(resultMsg, "인터페이스 맵핑정보가 존재하지 않아 인터페이스[", interfaze.getInterfaceId(), "] 의 기존 매핑정보 완전 삭제처리함.");
			}else{
				//try {
				mapping.setChannelId(interfaze.getChannel().getChannelId());
				mapping.setInterfaceId(systemGenerateInterfaceId);
				mapping.setRegDate(interfaze.getRegDate());
				mapping.setRegId(interfaze.getRegId());
				mapping.setModDate(interfaze.getModDate());
				mapping.setModId(interfaze.getModId());

				String asInterfaceId = requirementMapper.selectInterfaceMapping(mapping);

				if (Util.isEmpty(asInterfaceId)) {


					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보  수정을 위한 삭제(DEL_YN = 'Y') 시작");
					resultCd = requirementMapper.deleteInterfaceMapping(mapping);

					if (logger.isDebugEnabled()) {
						LogUtil.prefix(resultMsg, "인터페이스 맵핑정보  수정을 위한 삭제 OK");
						LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 시작");
					}

					resultCd = requirementMapper.insertInterfaceMapping(mapping);

					if (resultCd <= 0) {
						if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 실패 : insertInterfaceMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.createInterface:requirementMapper.insertInterfaceMapping:resultCd:(", resultCd, ")"));
					}

					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보 등록 OK");

				} else {
					if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 맵핑정보가 기존정보와 동일하여 처리하지 않는다.");
				}

			}
			*/


			//-------------------------------------
			//7.인터페이스 특성 맵핑. ( 추가 속성 / 연계채널별 )
			//-------------------------------------
			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 삭제 시작");
				resultCd = requirementMapper.removeChannelPropertyMapping(deleteParams);
				resultCd = requirementMapper.removeInterfaceAdditionalPropertyMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<InterfaceProperties> propertiesList = interfaze.getProperties();
				for( InterfaceProperties properties : propertiesList ) {
					insertParams.put("channelId"  , interfaze.getChannel().getChannelId());
					insertParams.put("attrId"     , properties.getAttrId());
					insertParams.put("idx"        , 0);
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("attrValue"  , properties.getAttrValue());
					insertParams.put("regDate"    , interfaze.getRegDate());
					insertParams.put("regId"      , interfaze.getRegId());
					insertParams.put("modDate"    , interfaze.getRegDate());
					insertParams.put("modId"      , interfaze.getRegId());

					String type = properties.getType();
					if( type != null && type.equals("1") ) {
						resultCd = requirementMapper.insertInterfaceAdditionalPropertyMapping(insertParams);

						if(resultCd <= 0){
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 특성 맵핑 등록 실패 : insertInterfaceAdditionalPropertyMapping:resultCd:", resultCd);
							throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertInterfaceAdditionalPropertyMapping:resultCd:(", resultCd, ")"));
						}
					} else {
						resultCd = requirementMapper.insertChannelPropertyMapping(insertParams);

						if(resultCd <= 0){
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 특성 맵핑 등록 실패 : insertChannelPropertyMapping:resultCd:", resultCd);
							throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertChannelPropertyMapping:resultCd:(", resultCd, ")"));
						}
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 특성 맵핑 등록 OK");
			}

			//-------------------------------------
			//8.DataAccess 권한 맵핑.
			//-------------------------------------
/*			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 시작");
				resultCd = requirementMapper.removeDataAccessRoleMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<DataAccessRole> dataAccessRoleList = interfaze.getDataAccessRoleList();
				int seq = 0;
				for( DataAccessRole dataAccessRole : dataAccessRoleList ) {
					insertParams.put("roleId"     , dataAccessRole.getRoleId());
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("seq"        , seq++);
					resultCd = requirementMapper.insertDataAccessRoleMapping(insertParams);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 DataAccess 권한 맵핑 등록 실패 : insertDataAccessRoleMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertDataAccessRoleMapping:resultCd:(", resultCd, ")"));
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 OK");
			}*/

			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) resultMsg.append(LogUtil.bar(LogUtil.prefix("인터페이스수정 프로세스[RequirementService.updateInterface(interfaze) 처리종료]")));
		}

	}

	/**
	 * <pre>
	 * 인터페이스 재사용 처리 프로세스
	 * ----------------------------
	 * 0.재사용 프로세스에서 인터페이스 테이블 수정은 없다.
	 * 1.인터페이스 시스템 맵핑 리스트 수정[하면에서는 추가만 있다]
	 * 2.인터페이스 업무 맵핑 리스트 수정[하면에서는 추가만 있다]
	 * 3.인터페이스 담당자 맵핑 리스트 수정[하면에서는 추가만 있다]
	 * 4.인터페이스 태그 리스트 수정[하면에서는 추가만 있다]
	 * 5.인터페이스 B2Bi MetaData 수정.
	 * </pre>
	 * @throws Exception
	 * @param interfaze
	 * @return
	 */
	@Transactional
	public int reuseInterface(Interface interfaze,User regUser, StringBuffer resultMsg) throws Exception{

		try{
			int resultCd = 0;

			if(logger.isDebugEnabled() && resultMsg != null){
				LogUtil.prefix(resultMsg,LogUtil.bar(LogUtil.prefix("인터페이스재사용 프로세스[RequirementService.updateInterface(interfaze) 처리시작]")));
				try{  LogUtil.prefix(resultMsg,"params:",Util.toJSONString(interfaze)); LogUtil.postbar(resultMsg);}catch(Exception e){}
			}

			//-------------------------------------
			//1.인터페이스 테이블 update
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스재사용시 인터페이스 정보는 업데이트 하지 않는다.");
			}

			String systemGenerateInterfaceId = Util.toString(interfaze.getInterfaceId());

			//-------------------------------------
			//2.인터페이스 시스템 맵핑 리스트 수정/추가처리
			//-------------------------------------
			//이전꺼 무조건 삭제하고 다시 등록하는 로직
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceSystemMap(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId() );
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 전체 삭제 OK");
		 	}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 시스템맵핑 등록 시작");
				List<pep.per.mint.common.data.basic.System> systemList = interfaze.getSystemList();
				for (pep.per.mint.common.data.basic.System system : systemList) {
					system.setRegDate(interfaze.getRegDate());
					system.setRegId(interfaze.getRegId());

					resultCd = requirementMapper.insertInterfaceSystemMap(systemGenerateInterfaceId, system);

					if (resultCd <= 0) {
						if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 시스템맵핑 등록 실패 : insertInterfaceSystemMap:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.reuseInterface:requirementMapper.insertInterfaceSystemMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 시스템맵핑 등록 OK");

			}
			//-------------------------------------
			//3.인터페이스 업무 맵핑 리스트 수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceBusinessMap(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId() );
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 시작");
				List<Business> businessList = interfaze.getBusinessList();
				for (Business business : businessList) {
					business.setRegDate(interfaze.getRegDate());
					business.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceBusinessMap(systemGenerateInterfaceId, business);
					if (resultCd <= 0) {
						if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 업무맵핑 등록 실패 : insertInterfaceBusinessMap:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.reuseInterface:requirementMapper.insertInterfaceBusinessMap:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 업무맵핑 등록 OK");

			}
			//-------------------------------------
			//4.인터페이스 담당자 맵핑 리스트 수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceRelUsers(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 시작");
				List<RelUser> relUsers = interfaze.getRelUsers();
				for (RelUser relUser : relUsers) {
					relUser.setRegDate(interfaze.getRegDate());
					relUser.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceRelUser(systemGenerateInterfaceId, relUser);

					if (resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 담당자맵핑 등록 실패 : insertInterfaceRelUser:resultCd:").append(resultCd);
						throw new Exception(Util.join("Exception:RequirementService.reuseInterface:requirementMapper.insertInterfaceRelUser:resultCd:(", resultCd, ")"));
					}
				}
				if (logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 담당자맵핑 등록 OK");
			}
			//-------------------------------------
			//5.인터페이스 태그 리스트  수정/추가처리
			//-------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 전체 삭제(DEL_YN = 'Y') 시작");
				resultCd = requirementMapper.deleteAllInterfaceTags(systemGenerateInterfaceId, interfaze.getModDate(), interfaze.getModId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 전체 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 시작");
				List<InterfaceTag> tagList = interfaze.getTagList();
				for (InterfaceTag interfaceTag : tagList) {
					interfaceTag.setRegDate(interfaze.getRegDate());
					interfaceTag.setRegId(interfaze.getRegId());
					resultCd = requirementMapper.insertInterfaceTag(systemGenerateInterfaceId, interfaceTag);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 실패 : insertInterfaceTag:resultCd:",resultCd);
						throw new Exception(Util.join("Exception:RequirementService.reuseInterface:requirementMapper.insertInterfaceTag:resultCd:(", resultCd, ")"));
					}
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 TAG 등록 OK");
			}

			//-------------------------------------
			//7.인터페이스 연계채널별 특성 맵핑.
			//-------------------------------------
			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 연계채널별 특성 맵핑 삭제 시작");
				resultCd = requirementMapper.removeChannelPropertyMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 연계채널별 특성 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 연계채널별 특성 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<InterfaceProperties> propertiesList = interfaze.getProperties();
				for( InterfaceProperties properties : propertiesList ) {
					insertParams.put("channelId"  , interfaze.getChannel().getChannelId());
					insertParams.put("attrId"     , properties.getAttrId());
					insertParams.put("idx"        , 0);
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("attrValue"  , properties.getAttrValue());
					insertParams.put("regDate"    , interfaze.getRegDate());
					insertParams.put("regId"      , interfaze.getRegId());
					insertParams.put("modDate"    , interfaze.getRegDate());
					insertParams.put("modId"      , interfaze.getRegId());
					resultCd = requirementMapper.insertChannelPropertyMapping(insertParams);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 연계채널별 특성 맵핑 등록 실패 : insertChannelPropertyMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.updateInterface:requirementMapper.insertChannelPropertyMapping:resultCd:(", resultCd, ")"));
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 연계채널별 특성 맵핑 등록 OK");
			}

			//-------------------------------------
			//8.DataAccess 권한 맵핑.
			//-------------------------------------
/*			{
				Map<Object,Object> deleteParams = new HashMap<Object,Object>();
				deleteParams.put("interfaceId", interfaze.getInterfaceId());
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 시작");
				resultCd = requirementMapper.removeDataAccessRoleMapping(deleteParams);
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 삭제 OK");
			}
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 시작");
				Map<Object,Object> insertParams = new HashMap<Object,Object>();
				List<DataAccessRole> dataAccessRoleList = interfaze.getDataAccessRoleList();
				int seq = 0;
				for( DataAccessRole dataAccessRole : dataAccessRoleList ) {
					insertParams.put("roleId"     , dataAccessRole.getRoleId());
					insertParams.put("interfaceId", interfaze.getInterfaceId());
					insertParams.put("seq"        , seq++);
					resultCd = requirementMapper.insertDataAccessRoleMapping(insertParams);
					if(resultCd <= 0){
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg, "인터페이스 DataAccess 권한 맵핑 등록 실패 : insertDataAccessRoleMapping:resultCd:", resultCd);
						throw new Exception(Util.join("Exception:RequirementService.reuseInterface:requirementMapper.insertDataAccessRoleMapping:resultCd:(", resultCd, ")"));
					}
				}

				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"인터페이스 DataAccess 권한 맵핑 등록 OK");
			}*/

			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) resultMsg.append(LogUtil.bar(LogUtil.prefix("인터페이스수정 프로세스[RequirementService.reuseInterface(interfaze) 처리종료]")));
		}
	}


	/**
	 * TO-DO LIST :: 결재대상 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalTargetList(String userId, String isInterfaceAdmin) throws Exception{
		List<Map> toDoList = requirementMapper.getRequirementApprovalTargetList(userId, isInterfaceAdmin);
		return toDoList;
	}

	/**
	 * TO-DO LIST :: 결재 진행 / 요구 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalIngList(String userId, String isInterfaceAdmin) throws Exception{
		List<Map> toDoList = requirementMapper.getRequirementApprovalIngList(userId, isInterfaceAdmin);
		return toDoList;
	}

	/**
	 * TO-DO LIST :: 결재반려 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalRejectList(String userId, String isInterfaceAdmin) throws Exception{
		List<Map> toDoList = requirementMapper.getRequirementApprovalRejectList(userId, isInterfaceAdmin);
		return toDoList;
	}

	/**
	 * TO-DO LIST :: 개발대상건 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementDevelopmentList(String userId,String isInterfaceAdmin) throws Exception{
		List<Map> list = requirementMapper.getRequirementDevelopmentList(userId, isInterfaceAdmin);
		return list;
	}

	/**
	 * TO-DO LIST :: 테스트대상건 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementTestList(String userId,String isInterfaceAdmin) throws Exception{
		List<Map> list = requirementMapper.getRequirementTestList(userId, isInterfaceAdmin);
		return list;
	}

	/**
	 * TO-DO LIST :: 운영대상건 조회
	 * @param userId
	 * @param isInterfaceAdmin
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementRealList(String userId,String isInterfaceAdmin) throws Exception{
		List<Map> list = requirementMapper.getRequirementRealList(userId, isInterfaceAdmin);
		return list;
	}

	public List<Map> getRequirementDTMList(String userId,String isInterfaceAdmin) throws Exception{
		List<Map> list = requirementMapper.getRequirementDTMList(userId, isInterfaceAdmin);
		return list;
	}

	public List<Map> getRequirementChangeList(String userId, String isInterfaceAdmin) throws Exception{
		List<Map> list = requirementMapper.getRequirementChangeList(userId, isInterfaceAdmin);
		return list;
	}

	public RequirementApproval getRequirementApproval(String requirementId) throws Exception{
		RequirementApproval requirementApproval = requirementMapper.getRequirementApproval(requirementId);
		return requirementApproval;
	}


	/**
	 * 요건상태 변경
	 * ApprovalService 작업완료되면 요 함수는 없앤다.
	 * REST-U02-AN-01-00
	 * @param status
	 * @param modDate
	 * @param modId
	 * @param requirementId
	 * @return
	 * @throws Exception
	 * @deprecated 더이상 쓰이지 않는 함수 , TCO0102 Entity CRUD를 처리하는 함수로 대체함
	 */
	@Deprecated
	@Transactional
	public int updateRequirementStatus(String status, String modDate, String modId,String requirementId, RequirementApproval requirementApproval) throws Exception {

		int resultCd = requirementMapper.updateRequirementStatus(status, modDate, modId, requirementId);
		if(resultCd < 1){
			throw new Exception(Util.join("Exception:RequirementService.updateRequirementStatus:requirementMapper.updateRequirementStatus:resultCd:(", resultCd, ")"));
		}else{
			resultCd = requirementMapper.insertRequirementApproval(requirementApproval);
			if(resultCd < 1) {
				throw new Exception(Util.join("Exception:RequirementService.updateRequirementStatus:requirementMapper.insertRequirementApproval:resultCd:(", resultCd, ")"));
			}

			List<RequirementApprovalUser> approvalUsers = requirementApproval.getRequirementApprovalUsers();
			logger.debug(Util.join("approvalUsers's length:", approvalUsers.size()));
			for(RequirementApprovalUser approvalUser : approvalUsers) {
				approvalUser.setSeq(requirementApproval.getSeq());
				resultCd = requirementMapper.insertRequirementApprovalUser(approvalUser);
				if(resultCd < 1) {
					throw new Exception(Util.join("Exception:RequirementService.updateRequirementStatus:requirementMapper.insertRequirementApprovalUser:resultCd:(", resultCd, ")"));
				}
			}

		}
		return resultCd;
	}



	@Transactional
	public int updateRequirementStatus(String status, String modDate, String modId,String requirementId) throws Exception {


		int resultCd = requirementMapper.updateRequirementStatus(status, modDate, modId, requirementId);
		if(resultCd < 1){
			throw new Exception(Util.join("Exception:RequirementService.updateRequirementStatus:requirementMapper.updateRequirementStatus:resultCd:(", resultCd, ")"));
		}

		return resultCd;
	}

	@Autowired
	ApprovalMapper approvalMapper;

	/**
	 * 요건개발테스트이행상태변경 REST-U03-AN-01-00
	 * @param status
	 * @param modDate
	 * @param modId
	 * @param requirementId
	 * @param finYmd
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int updateRequirementDevelopmentStatus(String status, String modDate, String modId,String requirementId, String finYmd) throws Exception {

		int resultCd = requirementMapper.updateRequirementDevelopmentStatus(status, modDate, modId, requirementId, finYmd);
		if(resultCd < 1){
			throw new Exception(Util.join("Exception:RequirementService.updateRequirementDevelopmentStatus:requirementMapper.updateRequirementDevelopmentStatus:resultCd:(", resultCd, ")"));
		}

		//--------------------------------------------------------
		//심의결재내역 등록
		//--------------------------------------------------------
		Approval approval = new Approval();
		approval.setApprovalItemId(requirementId);
		approval.setApprovalItemType(Approval.APPROVAL_ITEM_TYPE_REQUIREMENT);
		approval.setReqType(status);
		approval.setMisid("NONE");
		approval.setReqDate(modDate);
		approval.setReqUserId(modId);

		resultCd = approvalMapper.insertApproval(approval);

		return resultCd;
	}


	/**
	 * 요건개발테스트이행상태변경 REST-U03-AN-01-00
	 * @param status
	 * @param modDate
	 * @param modId
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int updateRequirementDevelopmentCancelStatus(String status, String modDate, String modId,String requirementId) throws Exception {


		int resultCd = requirementMapper.updateRequirementDevelopmentCancelStatus(status, modDate, modId, requirementId);
		if(resultCd < 1){
			throw new Exception(Util.join("Exception:RequirementService.updateRequirementDevelopmentStatus:requirementMapper.updateRequirementDevelopmentStatus:resultCd:(", resultCd, ")"));
		}

		//--------------------------------------------------------
		//심의결재내역 등록
		//--------------------------------------------------------
		Approval approval = new Approval();
		approval.setApprovalItemId(requirementId);
		approval.setApprovalItemType(Approval.APPROVAL_ITEM_TYPE_REQUIREMENT);
		approval.setReqType(status);
		approval.setMisid("NONE");
		approval.setReqDate(modDate);
		approval.setReqUserId(modId);

		resultCd = approvalMapper.insertApproval(approval);

		return resultCd;
	}

	/**
	 * 요건 심의/결재요청
	 * @param requirementApproval
	 * @return
	 * @throws Exception
	 */
	public int insertRequirementApproval(RequirementApproval requirementApproval) throws Exception{
		return requirementMapper.insertRequirementApproval(requirementApproval);
	}



	/**
	 * 요건정보임시저장
	 * @param requirementTemp
	 * @return
	 */
	@Transactional
	public int insertRequirementTemp(RequirementTemp requirementTemp) throws Exception{
		return requirementMapper.insertRequirementTemp(requirementTemp);
	}


	/**
	 * 요건임시저장정보 적용(삭제) 처리
	 * @param requirementTemp
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int updateRequirementTemp(RequirementTemp requirementTemp) throws Exception{
		return requirementMapper.updateRequirementTemp(requirementTemp);
	}

	/**
	 * 임시저장 요건정보 조회
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public RequirementTemp getRequirementTemp(String requirementId) throws Exception{
		return requirementMapper.getRequirementTemp(requirementId);
	}

	/**
	 * 요건임시저장 삭제
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public int deleteRequirementTemp(String requirementId) throws  Exception{
		return requirementMapper.deleteRequirementTemp(requirementId);
	}

	/**
	 * 일괄입력처리
	 * @param requirements
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int batchInsert(List<Requirement> requirements) throws Exception{
		int res = 0;
		for(Requirement requirement : requirements) {
			res = createRequirement(requirement);
		}
		return res;
	}

	/**
	 *
	 *
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementPrincipalList(Map arg) throws Exception{
		return requirementMapper.getRequirementPrincipalList(arg);
	}

	/**
	 * @return
	 */
	public int insertInterfacePrincipalMap(@Param("interfaceObj") Map interfaceObj, @Param("principalType")String principalType ,@Param("userId")String userId ) throws Exception{
		return requirementMapper.insertInterfacePrincipalMap(interfaceObj, principalType,userId);
	}

	/**
	 * @return
	 */
	public int updateInterfacePrincipalMap(@Param("interfaceId") String interfaceId, @Param("principalType")String principalType ,@Param("userId")String userId,@Param("monitorYn")String monitorYn) throws Exception{
		return requirementMapper.updateInterfacePrincipalMap(interfaceId, principalType,userId,monitorYn);
	}

	/**
	 * @return
	 */
	public int deleteInterfacePrincipalMap(@Param("interfaceId") String interfaceId, @Param("principalType")String principalType ,@Param("userId")String userId) throws Exception{
		return requirementMapper.deleteInterfacePrincipalMap(interfaceId, principalType,userId);
	}

	/**
	 * @return
	 */
	public int updateAllInterfacePrincipalMap(List<String> interfaceList, @Param("principalType")String principalType ,@Param("userId")String userId, @Param("monitorYn")String monitorYn) throws Exception{
		requirementMapper.initUpdateInterfacePrincipalMap(principalType, userId);
		int res =0;
		for(String  interfaceId : interfaceList) {
			res =  requirementMapper.updateInterfacePrincipalMap(interfaceId, principalType,userId, monitorYn);
		}
		return res;
	}

	public List<Requirement> getNodePathOfRequirementList(Map params) throws Exception {
		return requirementMapper.getNodePathOfRequirementList(params);
	}

	@Transactional
	public void deleteAllRequirementAndInterface() throws Exception  {
		requirementMapper.deleteTAN0327();
		requirementMapper.deleteTOP0404();
		requirementMapper.deleteTAN0103();
		requirementMapper.deleteTAN0101();
		requirementMapper.deleteTAN0213();
		requirementMapper.deleteTAN0218();
		requirementMapper.deleteTAN0219();
		requirementMapper.deleteTAN0201();
	}

	/**
	 *  delete from TAN0327 where requirement_id = (select a.requirement_id from tan0101 a, tan0201 b where a.interface_id = b.interface_id and b.integration_id = 'HDINS-001');
		delete from TOP0404 where requirement_id = (select a.requirement_id from tan0101 a, tan0201 b where a.interface_id = b.interface_id and b.integration_id = 'HDINS-001');
		delete from TAN0103 where requirement_id = (select a.requirement_id from tan0101 a, tan0201 b where a.interface_id = b.interface_id and b.integration_id = 'HDINS-001');
		delete from TAN0101 where interface_id = (select b.interface_id from tan0201 b where b.integration_id = 'HDINS-001');
		delete from TAN0213 where interface_id = (select b.interface_id from tan0201 b where b.integration_id = 'HDINS-001');
		delete from TAN0218 where interface_id = (select b.interface_id from tan0201 b where b.integration_id = 'HDINS-001');
		delete from TAN0219 where interface_id = (select b.interface_id from tan0201 b where b.integration_id = 'HDINS-001');
		delete from TAN0201 where integration_id = 'HDINS-001';
	 * @param integrationId
	 * @throws Exception
	 */
	@Transactional
	public void deleteRequirementAndInterface(String integrationId) throws Exception{
		requirementMapper.deleteTAN0327ByIntegrationId(integrationId);
		requirementMapper.deleteTOP0404ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0103ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0101ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0213ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0218ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0219ByIntegrationId(integrationId);
		requirementMapper.deleteTAN0201ByIntegrationId(integrationId);
	}



	public List<String> selectInterfacesByAttributes(Map params)throws Exception{
		return requirementMapper.selectInterfacesByAttributes(params);
	}


	@Autowired
	ModelService modelService;
	
	/**
	 * <pre>
	 * 요건등록 + 모델등록 트랜젝션 처리 
	 * </pre>
	 * @param requirementModel
	 * @throws Exception
	 */
	@Transactional
	public int createRequirementModel(RequirementModel requirementModel) throws Exception{
		
		String regDate = requirementModel.getRegDate();
		String regId = requirementModel.getRegId();
		Requirement requirement = requirementModel.getRequirement();
		if(requirement == null) throw new RuntimeException("Have no Requirement data!");
		requirement.setRegDate(regDate);
		requirement.setRegId(regId);
		int res = createRequirement(requirement);
		
		
		if(res > 0) {
			InterfaceModel interfaceModel = requirementModel.getInterfaceModel();
			if(interfaceModel == null) throw new RuntimeException("Have no InterfaceModel data!");
			
			Interface interfaze = requirement.getInterfaceInfo();
			String interfaceId = interfaze.getInterfaceId();
			interfaceModel.setInterfaceId(interfaceId);
			
			
			modelService.createInterfaceModel(interfaceModel, regDate, regId);
		}
		
		return res;
	}


	/**
	 * <pre>
	 * 요건등록 + 모델 업데이트 트랜젝션 처리 
	 * </pre>
	 * @param requirementModel
	 * @throws Exception
	 */
	@Transactional
	public int updateRequirementModel(RequirementModel requirementModel) throws Exception {
		String modDate = requirementModel.getModDate();
		String modId = requirementModel.getModId();
		Requirement requirement = requirementModel.getRequirement();
		if(requirement == null) throw new RuntimeException("Have no Requirement data!");
		requirement.setModDate(modDate);
		requirement.setModId(modId);
		int res = updateRequirement(requirement);
		
		
		InterfaceModel interfaceModel = requirementModel.getInterfaceModel();
		if(interfaceModel == null) throw new RuntimeException("Have no InterfaceModel data!");
		modelService.updateInterfaceModel(interfaceModel, modDate, modId);
	
		return res;
	}

	
}
