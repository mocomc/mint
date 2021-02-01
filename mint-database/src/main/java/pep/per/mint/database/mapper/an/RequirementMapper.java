/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.an;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;


/**
 * @author Solution TF
 *
 */
public interface RequirementMapper {

	public List<String> selectInterfacesByAttributes(Map params)throws Exception;

	/**
	 *
	 *
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getRequirementList(Map arg) throws Exception;

	public List<Requirement> getRequirementListByPage(Map arg) throws Exception;

	public List<Requirement> getRequirementListByPageV2(Map arg) throws Exception;

	public int getRequirementTotalCount(Map arg) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	//public Requirement getRequirementDetail(String requirementId) throws Exception;
	public Requirement getRequirementDetail(Map<String, String> params) throws Exception;

	public String selectRequirementId() throws Exception;

	public int insertFooTest(@Param("fooVarchar") String fooVarchar, @Param("fooInt") int fooInt) throws Exception;


	/**
	 *
	 * @param requirement
	 * @throws Exception
	 */
	public int insertRequirement(Requirement requirement) throws Exception;

	/**
	 *
	 * @param requirement
	 * @throws Exception
	 */
	public int updateRequirement(Requirement requirement) throws Exception;

	/**
	 * 요건상태변경
	 * @param status
	 * @param modDate
	 * @param modId
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public int updateRequirementStatus(@Param("status")String status, @Param("modDate")String modDate, @Param("modId")String modId, @Param("requirementId")String requirementId) throws Exception;


	/**
	 *
	 * @param status
	 * @param modDate
	 * @param modId
	 * @param requirementI
	 * @param finYmd
	 * @return
	 * @throws Exception
	 */
	public int updateRequirementDevelopmentStatus(@Param("status")String status, @Param("modDate")String modDate, @Param("modId")String modId, @Param("requirementId")String requirementI, @Param("finYmd")String finYmd) throws Exception;


	public int updateRequirementDevelopmentCancelStatus(@Param("status")String status, @Param("modDate")String modDate, @Param("modId")String modId, @Param("requirementId")String requirementI) throws Exception;

	/**
	 * 요건심의결재등록
 	 * @param requirementApproval
	 * @return
	 * @throws Exception
	 */
	public int insertRequirementApproval(RequirementApproval requirementApproval) throws Exception;


	/**
	 * 요건심의결재자등록
	 * @param requirementApprovalUser
	 * @return
	 * @throws Exception
	 */
	public int insertRequirementApprovalUser(RequirementApprovalUser requirementApprovalUser) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public int deleteRequirement(@Param("requirementId") String requirementId, @Param("modId") String modId, @Param("modDate") String modDate) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public int restoreRequirement(@Param("requirementId") String requirementId, @Param("modId") String modId, @Param("modDate") String modDate) throws Exception;


	/**
	 *
	 * @param comment
	 * @throws Exception
	 */
	public int insertRequirementComment(RequirementComment comment) throws Exception;

	/**
	 *
	 * @param comment
	 * @throws Exception
	 */
	public int updateRequirementComment(RequirementComment comment) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @param modId
	 * @param modDate
	 * @return
	 * @throws Exception
	 */
	public int deleteAllRequirementComment(@Param("requirementId") String requirementId, @Param("modId") String modId, @Param("modDate") String modDate) throws Exception;


	/**
	 *
	 * @param attatchFile
	 * @throws Exception
	 */
	public int insertRequirementAttatchFile(RequirementAttatchFile attatchFile) throws Exception;

	/**
	 *
	 * @param attatchFile
	 * @throws Exception
	 */
	public int updateRequirementAttatchFile(RequirementAttatchFile attatchFile) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @param modId
	 * @param modDate
	 * @return
	 * @throws Exception
	 */
	public int deleteAllRequirementAttatchFile(@Param("requirementId") String requirementId, @Param("modId") String modId, @Param("modDate") String modDate) throws Exception;


	/**
	 *
	 * @param intf
	 * @param interfaceIdInfo
	 * @return
	 * @throws Exception
	 */
	public int insertInterface(@Param("interface")Interface intf) throws Exception;

	//public int insertInterface(Interface interfaze) throws Exception;

	public String selectInterfaceId(Map interfaceIdInfo) throws Exception;


	/**
	 *
	 * @param intf
	 * @return
	 * @throws Exception
	 */
	public int updateInterface(Interface intf)throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public int deleteInterface(@Param("interfaceId") String interfaceId) throws Exception;


	/**
	 * @param system
	 * @return
	 */
	public int insertInterfaceSystemMap(@Param("interfaceId") String interfaceId, @Param("system")System system) throws Exception;

	/**
	 * @param business
	 * @return
	 */
	public int insertInterfaceBusinessMap(@Param("interfaceId") String interfaceId, @Param("business")Business business) throws Exception;

	/**
	 * @param interfaceTag
	 * @return
	 */
	public int insertInterfaceTag(@Param("interfaceId") String interfaceId, @Param("interfaceTag")InterfaceTag interfaceTag) throws Exception;

	/**
	 * @param relUser
	 * @return
	 */
	public int insertInterfaceRelUser(@Param("interfaceId") String interfaceId, @Param("relUser")RelUser relUser) throws Exception;



	/**
	 * @param system
	 * @return
	 */
	public int updateInterfaceSystemMap(@Param("interfaceId") String interfaceId, @Param("system")System system) throws Exception;

	/**
	 * @param business
	 * @return
	 */
	public int updateInterfaceBusinessMap(@Param("interfaceId") String interfaceId, @Param("business")Business business) throws Exception;

	/**
	 * @param interfaceTag
	 * @return
	 */
	public int updateInterfaceTag(@Param("interfaceId") String interfaceId, @Param("interfaceTag")InterfaceTag interfaceTag) throws Exception;

	/**
	 * @param relUser
	 * @return
	 */
	public int updateInterfaceRelUser(@Param("interfaceId") String interfaceId, @Param("relUser")RelUser relUser) throws Exception;




	/**
	 * @param system
	 * @return
	 */
	public int deleteInterfaceSystemMap(@Param("interfaceId") String interfaceId, @Param("system")System system) throws Exception;

	/**
	 * @param business
	 * @return
	 */
	public int deleteInterfaceBusinessMap(@Param("interfaceId") String interfaceId, @Param("business")Business business) throws Exception;

	/**
	 * @param interfaceTag
	 * @return
	 */
	public int deleteInterfaceTag(@Param("interfaceId") String interfaceId, @Param("interfaceTag")InterfaceTag interfaceTag) throws Exception;

	/**
	 * @param relUser
	 * @return
	 */
	public int deleteInterfaceRelUser(@Param("interfaceId") String interfaceId, @Param("relUser")RelUser relUser) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public String selectInterface(@Param("interfaceId") String interfaceId) throws Exception;


	/**
	 * 요건 설명 ID채번
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public String selectRequirementCommentId(@Param("requirementId") String requirementId) throws Exception;

	/**
	 * 요건 첨부파일 파일 ID 채번
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public String selectRequirementAttatchFileId(@Param("requirementId") String requirementId) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 * @throws Exception
	 */
	public int deleteAllInterfaceSystemMap(
			@Param("interfaceId") String interfaceId,
			@Param("modDate") String modDate,
			@Param("modId") String modId
	) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 * @throws Exception
	 */
	public int deleteAllInterfaceBusinessMap(
			@Param("interfaceId") String interfaceId,
			@Param("modDate") String modDate,
			@Param("modId") String modId
	) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 * @throws Exception
	 */
	public int deleteAllInterfaceRelUsers(
			@Param("interfaceId") String interfaceId,
			@Param("modDate") String modDate,
			@Param("modId") String modId
	) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 * @throws Exception
	 */
	public int deleteAllInterfaceTags(
			@Param("interfaceId") String interfaceId,
			@Param("modDate") String modDate,
			@Param("modId") String modId
	) throws Exception;


	/**
	 *
	 * @param interfaceMapping
	 * @return
	 * @throws Exception
	 */
	public int insertInterfaceMapping(InterfaceMapping interfaceMapping) throws Exception;

	/**
	 *
	 * @param interfaceMapping
	 * @return
	 * @throws Exception
	 */
	public int deleteInterfaceMapping(InterfaceMapping interfaceMapping) throws Exception;

	/**
	 * 인터페이스 멥핑정보 레코드 완전 삭제
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public int removeInterfaceMapping(@Param("interfaceId")String interfaceId) throws Exception;

	/**
	 *
	 * @param interfaceMapping
	 * @return
	 * @throws Exception
	 */
	public String selectInterfaceMapping(InterfaceMapping interfaceMapping) throws Exception;

	/**
	 * TO-DO LIST :: 결재대상 조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalTargetList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;

	/**
	 * TO-DO LIST :: 결재 진행 / 요구 조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalIngList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;

	/**
	 * TO-DO LIST :: 접수반려 조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementApprovalRejectList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;

	/**
	 * TO-DO LIST :: 개발대상건 조회
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementDevelopmentList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;


	/**
	 * TO-DO LIST :: 테스트대상건 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementTestList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;

	/**
	 * TO-DO LIST :: 운영대상건 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementRealList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;

	/**
	 * 요건 Change List 조회
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementChangeList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;


	/**
	 * 요건 개발/테스트/운영이관 리스트 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementDTMList(@Param("userId")String userId, @Param("isInterfaceAdmin")String isInterfaceAdmin) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public RequirementApproval getRequirementApproval(@Param("requirementId")String requirementId) throws Exception;




	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentRequirementCommentList(@Param("requirementId")String requirementId) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentRequirementAttachFileList(@Param("requirementId")String requirementId) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceSystemMapList(@Param("interfaceId")String interfaceId) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceBusinessMapList(@Param("interfaceId")String interfaceId) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceRelUserList(@Param("interfaceId")String interfaceId) throws Exception;



	public String selectSystemGenerateInterfaceId(@Param("interfaceId")String interfaceId) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getInterfaceRefCheck(@Param("requirementId")String requirementId) throws Exception;


	/**
	 * 요건정보임시저장
	 * @param requirementTemp
	 * @return
	 */
	public int insertRequirementTemp(RequirementTemp requirementTemp) throws Exception;


	/**
	 * 요건임시저장정보 적용(삭제) 처리
	 * @param requirementTemp
	 * @return
	 * @throws Exception
	 */
	public int updateRequirementTemp(RequirementTemp requirementTemp) throws Exception;

	public RequirementTemp getRequirementTemp(String requirementId) throws Exception;

	public int deleteRequirementTemp(String requirementId) throws Exception;

	/**
	 * 인터페이스 연계채널별 특성 맵핑 삭제.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int removeChannelPropertyMapping(Map params) throws Exception;

	/**
	 * 인터페이스 연계채널별 특성 맵핑 추가.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertChannelPropertyMapping(Map params) throws Exception;


	/**
	 * 인터페이스 추가 특성 맵핑 삭제.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int removeInterfaceAdditionalPropertyMapping(Map params) throws Exception;

	/**
	 * 인터페이스 추가 특성 맵핑 추가.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertInterfaceAdditionalPropertyMapping(Map params) throws Exception;


	/**
	 * 인터페이스 DataAccess 권한 맵핑 삭제.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int removeDataAccessRoleMapping(Map params) throws Exception;

	/**
	 * 인터페이스 DataAccess 권한 맵핑 추가.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertDataAccessRoleMapping(Map params) throws Exception;


	/**
	 *
	 *
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementPrincipalList(Map arg) throws Exception;

	
	/**
	 * GS 일괄 업로드 update 기능용
	 * 
	 * @param interface_id 
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getRequirementIdForUpdate(@Param("integrationId") String integrationId) throws Exception;
		
	
	/**
	 * @return
	 */
	public int insertInterfacePrincipalMap(@Param("interfaceObj") Map interfaceObj, @Param("principalType")String principalType ,@Param("userId")String userId) throws Exception;

	/**
	 * @return
	 */
	public int updateInterfacePrincipalMap(@Param("interfaceId") String interfaceId, @Param("principalType")String principalType ,
			@Param("userId")String userId, @Param("monitorYn")String monitorYn ) throws Exception;

	/**
	 * @return
	 */
	public int deleteInterfacePrincipalMap(@Param("interfaceId") String interfaceId, @Param("principalType")String principalType ,@Param("userId")String userId ) throws Exception;

	public int initUpdateInterfacePrincipalMap(@Param("principalType")String principalType ,@Param("userId")String userId ) throws Exception;

	public List<Requirement> getNodePathOfRequirementList(Map params)throws Exception;

	public List<Requirement> getRequirementDetailList(Map<String, Object> params)throws Exception;

	public PageInfo getRequirementsPageInfo(Map<String, Object> params)throws Exception;

	public List<String> getRequirementIdList() throws Exception;

	public void deleteTAN0327() throws Exception;
	public void deleteTOP0404() throws Exception;
	public void deleteTAN0103() throws Exception;
	public void deleteTAN0101() throws Exception;
	public void deleteTAN0213() throws Exception;
	public void deleteTAN0218() throws Exception;
	public void deleteTAN0219() throws Exception;
	public void deleteTAN0201() throws Exception;

	public void deleteTAN0327ByIntegrationId(String integrationId) throws Exception;
	public void deleteTOP0404ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0103ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0101ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0213ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0218ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0219ByIntegrationId(String integrationId) throws Exception;
	public void deleteTAN0201ByIntegrationId(String integrationId) throws Exception;

}
