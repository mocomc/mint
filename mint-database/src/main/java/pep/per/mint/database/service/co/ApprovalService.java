/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.database.service.co;

//import com.mocomsys.iip.inhouse.wrapper.ApprovalRouteVO;
//import com.mocomsys.iip.inhouse.wrapper.MySingleServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.Environments;
import pep.per.mint.database.exception.DatabaseServiceException;
import pep.per.mint.database.mapper.co.ApprovalMapper;
import pep.per.mint.database.service.an.RequirementHerstoryService;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.database.service.op.ProblemLedgerService;

import java.util.*;

/**
 * Created by Solution TF on 15. 9. 10..
 */
@Service
public class ApprovalService {

    Logger logger = LoggerFactory.getLogger(ApprovalService.class);

    @Autowired
    ApprovalMapper approvalMapper;

    @Autowired
    //ProblemService problemService;
    ProblemLedgerService problemLedgerService;

	@Autowired
	RequirementHerstoryService requirementHerstoryService;

    @Autowired
    RequirementService requirementService;



    @Autowired
    DataSourceTransactionManager transactionManager;

    //public static boolean USE_EXTERN_APPROVAL = true;

    /**
     * 결재요청(수정)
     * - 수정된 정보를 임시테이블에 저장한다.
     * - 라이프사이클 이력 정보를 심의결재내역 테이블에 등록( 상태 정보 히스토리 관리 )
     * @param approval
     * @return
     * @throws Exception
     */
    public int requestApprovalWithUpdate(Approval approval) throws Exception {

        int resultCd = 0;

        DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);

        try {

            String status = approval.getReqType();
            String modDate = approval.getReqDate();
            String modId = approval.getReqUserId();

            if (Approval.APPROVAL_ITEM_TYPE_REQUIREMENT.equals(approval.getApprovalItemType())) {
                String requirementId = approval.getApprovalItemId();
                Requirement requirement = approval.getRequirement();
                requirement.setStatus(status);

				//--------------------------------------------------------
				// 결재요청(수정) 이면 승인/반려 전, 임시저장 한다.
				//--------------------------------------------------------
                String requirementString = Util.toJSONString(requirement);
                RequirementTemp requirementTemp = new RequirementTemp();
                requirementTemp.setRequirementId(requirement.getRequirementId());
                requirementTemp.setContents(requirementString);
                requirementTemp.setRegDate(requirement.getModDate());
                requirementTemp.setRegId(requirement.getModId());
                resultCd = requirementService.insertRequirementTemp(requirementTemp);
                resultCd = requirementService.updateRequirementStatus(status,modDate,modId,requirementId);

    			//----------------------------------------------------------
    			//4.히스토리 처리
    			//----------------------------------------------------------
    			{
    				requirementHerstoryService.addRequirementHistory(requirement);
    			}

            } else {
                String problemId = approval.getApprovalItemId();
                //ProblemManagement problemManagement =  approval.getProblemManagement();
                //problemManagement.setApprovalStatus(status);
                //resultCd = problemService.updateProblem(problemManagement);
                ProblemLedger problemLedger =  approval.getProblemLedger();
                problemLedger.setApprovalStatus(status);
                resultCd = problemLedgerService.updateProblem(problemLedger);
            }

            resultCd = approvalMapper.updateApprovalLink(approval.getLinkKey(), approval.getApprovalItemId(), approval.getApprovalItemType());
            if (resultCd < 1) {
                throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.updateApprovalLink:resultCd:(", resultCd, ")"));
            }

            resultCd = approvalMapper.insertApproval(approval);
            if (resultCd < 1) {
                throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.insertApproval:resultCd:(", resultCd, ")"));
            }


			//--------------------------------------------------------
			// TODO: 2017.01.22 주석처리, 향후 설계 보완하여 추가 개발할것.
			//--------------------------------------------------------
            /*
            List<ApprovalUser> approvalUsers = approval.getApprovalUsers();

            if (approvalUsers == null || approvalUsers.size() == 0) {
                String errorMsg = "[ApprovalService.requestApproval 예외발생]결재선(결재자들)정보가 존재하지 않습니다.";
                throw new DatabaseServiceException(errorMsg);
            }

            logger.debug(Util.join("approvalUsers's length:", approvalUsers.size()));
            for (ApprovalUser approvalUser : approvalUsers) {
                approvalUser.setSeq(approval.getSeq());
                resultCd = approvalMapper.insertApprovalUser(approvalUser);
                if (resultCd < 1) {
                    throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.insertApprovalUser:resultCd:(", resultCd, ")"));
                }
            }
			*/

            //--------------------------------------------------------
            // 외부 결재서비스 연동
            // TODO: 향후 설계 보완하여 추가 개발할것.
            //--------------------------------------------------------
            if (Environments.useExternApproval) {
               // callYouSingle(approval);
            }

            transactionManager.commit(ts);

            return resultCd;

        }catch(Throwable t){
            transactionManager.rollback(ts);
            throw new Exception(t);
        }


    }


    /**
     * 결재요청(등록)/결재요청(삭제)/결재요청(이행)
     * - 인터페이스 상태 정보를 업데이트한다.
     * - 라이프사이클 이력 정보를 심의결재내역 테이블에 등록( 상태 정보 히스토리 관리 )
     * REST-C01-CO-02-00-010
     * @param approval
     * @return
     * @throws Exception
     */
    //@Transactional
    public int requestApproval(Approval approval) throws Exception {

        int resultCd = 0;

        DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);

        try {

            String status = approval.getReqType();
            String modDate = approval.getReqDate();
            String modId = approval.getReqUserId();
            if (Approval.APPROVAL_ITEM_TYPE_REQUIREMENT.equals(approval.getApprovalItemType())) {
                String requirementId = approval.getApprovalItemId();
                resultCd = requirementService.updateRequirementStatus(status, modDate, modId, requirementId);
            }else{
                String problemId = approval.getApprovalItemId();
                //resultCd = problemService.updateProblemStatus(status, modDate, modId, problemId);
                resultCd = problemLedgerService.updateProblemStatus(status, modDate, modId, problemId);
            }

            resultCd = approvalMapper.updateApprovalLink(approval.getLinkKey(), approval.getApprovalItemId(), approval.getApprovalItemType());
            if (resultCd < 1) {
                throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.updateApprovalLink:resultCd:(", resultCd, ")"));
            }

            resultCd = approvalMapper.insertApproval(approval);
            if (resultCd < 1) {
                throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.insertApproval:resultCd:(", resultCd, ")"));
            }

			//--------------------------------------------------------
			// TODO: 2017.01.22 주석처리, 향후 설계 보완하여 추가 개발할것.
			//--------------------------------------------------------
            /*
            List<ApprovalUser> approvalUsers = approval.getApprovalUsers();

            if (approvalUsers == null || approvalUsers.size() == 0) {
                String errorMsg = "[ApprovalService.requestApproval 예외발생]결재선(결재자들)정보가 존재하지 않습니다.";
                throw new DatabaseServiceException(errorMsg);
            }

            logger.debug(Util.join("approvalUsers's length:", approvalUsers.size()));
            for (ApprovalUser approvalUser : approvalUsers) {
                approvalUser.setSeq(approval.getSeq());
                resultCd = approvalMapper.insertApprovalUser(approvalUser);
                if (resultCd < 1) {
                    throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.insertApprovalUser:resultCd:(", resultCd, ")"));
                }
            }
			*/

            //--------------------------------------------------------
            // 외부 결재서비스 연동
            // TODO: 향후 설계 보완하여 추가 개발할것.
            //--------------------------------------------------------
            if (Environments.useExternApproval) {
                //callYouSingle(approval);
            }

            transactionManager.commit(ts);

            return resultCd;

        }catch(Throwable t){
            transactionManager.rollback(ts);
            throw new Exception(t);
        }


    }

    /**
     * 결재처리(승인/반려)
     * @param apply
     * @param params
     * @return
     * @throws Exception
     */
	@Transactional
	public int applyApproval(boolean apply, Approval approval) throws Exception {

		int resultCd = 0;

		String requirementId = approval.getApprovalItemId();
		String modId         = approval.getReqUserId();
		String modDate       = approval.getReqDate();
		String status        = approval.getReqType();
		String statusType    = status.substring(1);

		if( apply ) {
			//--------------------------------------------------------
			// 승인 프로세스
			//--------------------------------------------------------
			status = Util.join("F", statusType);

			if( statusType.equals("2") ) {
				//--------------------------------------------------------
				// 결재요청(수정) 에 대한 승인 이면 임시저장된 정보로 업데이트 한다.
				//--------------------------------------------------------
				RequirementTemp requirementTemp = requirementService.getRequirementTemp(requirementId);
				Requirement requirement = Util.jsonToObject(requirementTemp.getContents(), Requirement.class);
				requirement.setStatus(status);
				resultCd = requirementService.updateRequirement(requirement);
				if(resultCd < 1){
					//throw new Exception(Util.join("Exception:ApprovalService.applyApproval:requirementService.updateRequirement:resultCd:(", resultCd, ")"));
				}
			} else {
				//--------------------------------------------------------
				// 결재요청(수정) 이외에 대한 승인 이면 인터페이스 상태 정보만 업데이트 한다.
				//--------------------------------------------------------
				resultCd = requirementService.updateRequirementStatus(status, modDate, modId, requirementId);
				if(resultCd < 1){
					//throw new Exception(Util.join("Exception:ApprovalService.applyApproval:requirementService.updateRequirementStatus:resultCd:(", resultCd, ")"));
				}
			}

		} else {
			//--------------------------------------------------------
			// 반려 프로세스
			//--------------------------------------------------------
			status = Util.join("E", statusType);
			//--------------------------------------------------------
			// 라이프사이클 상의 반려 처리 이면 인터페이스 상태 정보만 업데이트 한다.
			//--------------------------------------------------------
			resultCd = requirementService.updateRequirementStatus(status, modDate, modId, requirementId);
			if(resultCd < 1){
				//throw new Exception(Util.join("Exception:ApprovalService.applyApproval:requirementService.updateRequirementStatus:resultCd:(", resultCd, ")"));
			}
		}

		//--------------------------------------------------------
		// 결재요청(수정) 에 대한 프로세스일 경우 임시저장 정보를 삭제하도록 한다.
		//--------------------------------------------------------
		if( statusType.equals("2") ) {
			resultCd = requirementService.deleteRequirementTemp(requirementId);
			if(resultCd < 1){
				//throw new Exception(Util.join("Exception:ApprovalService.applyApproval:requirementService.deleteRequirementTemp:resultCd:(", resultCd, ")"));
			}
		}

		//--------------------------------------------------------
		// 라이프사이클 이력 정보를 심의결재내역 테이블에 등록( 상태 정보 히스토리 관리 )
		//--------------------------------------------------------
		approval.setReqType(status);
		approval.setReqDate(modDate);
		approval.setReqUserId(modId);

		resultCd = approvalMapper.insertApproval(approval);
		if(resultCd < 1){
			//throw new Exception(Util.join("Exception:ApprovalService.applyApproval:approvalMapper.insertApproval:resultCd:(", resultCd, ")"));
		}

		return resultCd;
	}


	/**
	 * @deprecated
	 * @param approval
	 * @return
	 * @throws Exception
	 */
    @Transactional
    private int callInnerProcess(Approval approval) throws Exception{
        int resultCd = 0;

        String modDate = approval.getReqDate();
        String modId   = approval.getReqUserId();

        if ( Approval.APPROVAL_ITEM_TYPE_REQUIREMENT.equals(approval.getApprovalItemType() ) ) {

            String status = approval.getReqType();
            status = Util.join("F", status.substring(1));
            logger.debug(Util.join("결재아이템[",approval.getApprovalItemId(),"]를 외부결재프로세스를 수행하지 않아 완료상태값[", status,"]으로 바로 변경한다."));

            approval.setReqType(status);

            String requirementId = approval.getApprovalItemId();
            resultCd = requirementService.updateRequirementStatus(status, modDate, modId, requirementId);

            //요건 삭제처리한다.
            if("F3".equals(status)){
                resultCd = requirementService.deleteRequirement(requirementId,modId,modDate);
            }

        } else {
            String status = approval.getReqType();

            status = "1";

            logger.debug(Util.join("결재아이템[",approval.getApprovalItemId(),"]를 외부결재프로세스를 수행하지 않아 완료상태값[", status,"]으로 바로 변경한다."));

            approval.setReqType(status);


            String problemId = approval.getApprovalItemId();
            resultCd = problemLedgerService.updateProblemStatus(status, modDate, modId, problemId);


        }

        resultCd = approvalMapper.insertApproval(approval);
        if (resultCd < 1) {
            throw new Exception(Util.join("Exception:ApprovalService.requestApproval:approvalMapper.insertApproval:resultCd:(", resultCd, ")"));
        }


        return resultCd;

    }


    /**
     * 가장최근 결재내역 조회
     * REST-R01-CO-02-00-010
     * @param approvalItemId
     * @param approvalItemType
     * @return
     * @throws Exception
     */
    public Approval getApproval(String approvalItemId, String approvalItemType) throws Exception {
        return approvalMapper.getApproval(approvalItemId, approvalItemType);
    }

    /**
     * 결재바디에 첨부할 페이지 링크 키 발번
     * @param userId
     * @return
     * @throws Exception
     */
    public String selectApprovalLink(String userId) throws Exception{
        String linkKey = Util.join(userId,"-", UUID.randomUUID());
        String reqDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT);
        approvalMapper.insertApprovalLink(linkKey,userId, reqDate);
        return linkKey;
    }


    /**
     * 결재링크를를 주기적으로 삭제해줄때 사용되는 서비스
     * 스프링 백그라운드 서비스로 처리하도록 한다.
     * @return
     * @throws Exception
     */
    public int clearApprovalLink()throws Exception{
        return approvalMapper.clearApprovalLink();
    }

    /**
     * 마이싱글 결재내용의 링크를 클릭하고 들어온 조회에 대해 조회페이지 라우팅을 위힌
     * 링크정보 조회
     * @param linkKey
     * @return
     * @throws Exception
     */
    public Map getApprovalLinkInfo(String linkKey) throws Exception{
        return approvalMapper.getApprovalLinkInfo(linkKey);
    }


    public List<ApprovalUser> getApprovalUserList(String interfaceId, String channelId) throws  Exception{
        return approvalMapper.getApprovalUserList(interfaceId, channelId);
    }


    public List<ApprovalUser> getApprovalLineUserList(String channelId) throws  Exception{
        return approvalMapper.getApprovalLineUserList(channelId);
    }



    /**
     * 임시저장된 요건 정보를 실제 요건 정보로 수정 반영하고
     * 임시 저장된 정보는 삭제한다
     * @param requirementId
     * @return
     * @throws Exception
     */
    @Transactional
    public int applyRequirementTemp(String requirementId, boolean apply) throws Exception {

        int res = 0;
        RequirementTemp temp = requirementService.getRequirementTemp(requirementId);
        Requirement requirement = Util.jsonToObject(temp.getContents(), Requirement.class);
        String modId = requirement.getModId();
        String modDate = requirement.getModDate();

        Approval approval = new Approval();
        approval.setApprovalItemId(requirementId);
        approval.setReqDate(Util.getFormatedDate());
        approval.setReqUserId(modId);
        approval.setMisid("NODEFINE");
        approval.setApprovalItemType(Approval.APPROVAL_ITEM_TYPE_REQUIREMENT);

        if(apply) {
            requirement.setStatus("F2");
            res = requirementService.updateRequirement(requirement);
            approval.setReqType("F2");
        } else {
            res = requirementService.updateRequirementStatus("E2",modDate, modId, requirementId);
            approval.setReqType("E2");
        }
        res = approvalMapper.insertApproval(approval);

        //임시저장 업데이트 실행
        //temp.setApplyYn("Y");
        //res = requirementMapper.updateRequirementTemp(temp);
        //임시저장 완전 삭제시 실행
        //삼성전기는 데이터 정리를 위한 백엔드가 존재하지 않으므로 일단 삭제 하는 것으로 정리한다
        res = requirementService.deleteRequirementTemp(requirementId);

        return res;
    }


}
