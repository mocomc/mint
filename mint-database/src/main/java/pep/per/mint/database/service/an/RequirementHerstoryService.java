/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.an;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.dataset.DataSetHistory;
import pep.per.mint.common.data.basic.herstory.ColumnHerstory;
import pep.per.mint.common.data.basic.herstory.InterfaceColumnHerstory;
import pep.per.mint.common.data.basic.herstory.RequirementColumnHerstory;
import pep.per.mint.common.data.basic.herstory.RequirementColumnHistory;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.Environments;
import pep.per.mint.database.exception.UnChangedDataException;
import pep.per.mint.database.mapper.an.RequirementHerstoryMapper;
import pep.per.mint.database.mapper.an.RequirementMapper;
import pep.per.mint.database.mapper.co.CommonMapper;
import pep.per.mint.database.service.co.CommonService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 요건 히스토리 서비스(조회, 입력, 삭제, 수정)
 *
 * @author Solution TF
 */

@Service
public class RequirementHerstoryService {

	private static final Logger logger = LoggerFactory.getLogger(RequirementHerstoryService.class);

	/**
	 *
	 */
	@Autowired
	RequirementHerstoryMapper requirementHerstoryMapper;

	@Autowired
	RequirementMapper requirementMapper;

	@Autowired
	CommonMapper commonMapper;

	public static int HAVE_NO_VERSION_HERSTORY = 0;

	/**
	 * <pre>
	 *   요건 등록수정삭제시 히스토리를 남길때 호출한다.
	 * </pre>
	 * @param requirement
	 * @throws Exception
	 * @deprecated CLOB 형식의 히스토리 관리로 변경 되면서 더이상 사용하지 않을 예정임. addRequirementHistory 로 대체
	 */
	@Transactional
	public int addRequirementHerstory(Requirement requirement) throws Exception{

		if(!Environments.entityHerstoryOn) return 1;

		int resultCd = 0;

		int version = requirementHerstoryMapper.selectCurrentHerstoryVersion(requirement.getRequirementId());
		//----------------------------------------------------------
		//1.Requirement(요건) insert
		//----------------------------------------------------------
		{
			if(version == HAVE_NO_VERSION_HERSTORY){
				version = 1;
				insertFirstRequirementHerstory(requirement, Integer.toString(version));
			}else{
				version = version + 1;
				insertFirstRequirementHerstory(requirement, Integer.toString(version));
				//insertRequirementHerstory(requirement, Integer.toString(version));
			}

		}

		String modDate = Util.isEmpty(requirement.getModDate()) ? requirement.getRegDate() : requirement.getModDate();
		String modId = Util.isEmpty(requirement.getModId()) ? requirement.getRegId() : requirement.getModId();
		//----------------------------------------------------------
		//2.List<RequirementComment>(요건설명 리스트) insert
		//----------------------------------------------------------
		{
			List<RequirementComment> commentList = requirement.getCommentList();

			if (commentList != null && commentList.size() != 0) {
				insertRequirementCommentHerstory(requirement.getRequirementId(), Integer.toString(version), modDate, modId);
			}
		}
		//----------------------------------------------------------
		//3.List<RequirementAttatchFile>(요건첨부파일 리스트) insert
		//----------------------------------------------------------
		{
			List<RequirementAttatchFile> attatchFileList = requirement.getAttatchFileList();

			if (attatchFileList != null && attatchFileList.size() != 0) {
				insertRequirementAttatchFileHerstory(requirement.getRequirementId(), Integer.toString(version), modDate, modId);
			}
		}

		//----------------------------------------------------------
		//4.Interface(인터페이스) 처리 시작
		//----------------------------------------------------------
		Interface interfaze = requirement.getInterfaceInfo();
		if (interfaze != null) {

			if(version == HAVE_NO_VERSION_HERSTORY){
				insertFirstInterfaceHerstory(requirement.getRequirementId(), Integer.toString(version),interfaze, modDate, modId);
			}else{
				insertFirstInterfaceHerstory(requirement.getRequirementId(), Integer.toString(version),interfaze, modDate, modId);
				//insertInterfaceHerstory(requirement.getRequirementId(), Integer.toString(version),  interfaze, modDate, modId);
			}

			//----------------------------------------------------------
			//5.시스템 매핑 히스토리 insert
			//----------------------------------------------------------
			{
				List<System> systemList = interfaze.getSystemList();
				if (systemList != null && systemList.size() != 0) {
					insertInterfaceSystemMapHerstory(requirement.getRequirementId(), Integer.toString(version), interfaze.getInterfaceId(), modDate, modId);
				}
			}


			//----------------------------------------------------------
			//6.비지니스 매핑 히스토리 insert
			//----------------------------------------------------------
			{
				List<Business> businessList = interfaze.getBusinessList();
				if (businessList != null && businessList.size() != 0) {
					insertInterfaceBusinessMapHerstory(requirement.getRequirementId(), Integer.toString(version), interfaze.getInterfaceId(), modDate, modId);
				}
			}


			//----------------------------------------------------------
			//7.담당자 매핑 히스토리 insert
			//----------------------------------------------------------
			{
				List<RelUser> userList = interfaze.getRelUsers();
				if (userList != null && userList.size() != 0) {
					insertInterfaceRelUserHerstory(requirement.getRequirementId(), Integer.toString(version), interfaze.getInterfaceId(), modDate, modId);
				}
			}

			//----------------------------------------------------------
			//8.B2Bi 기준정보 히스토리 insert
			//----------------------------------------------------------
			{
				if( interfaze.getChannel().getChannelId().equals("CN00000005") ) {
					insertB2BiInterfaceMetaDataHistory(requirement.getRequirementId(), Integer.toString(version), interfaze.getInterfaceId(), modDate, modId);
				}
			}


		}



		return resultCd;
	}





	@Transactional
	private void insertInterfaceSystemMapHerstory(String requirementId, String version, String interfaceId, String modDate, String modId) {
		try{
			requirementHerstoryMapper.insertInterfaceSystemMapHerstory(requirementId, version, interfaceId, modDate, modId);
		}catch(Throwable t){
			logger.error("[인터페이스시스템매핑 히스토리 작업중 예외 발생]",t);
		}
	}

	@Transactional
	private void insertInterfaceBusinessMapHerstory(String requirementId, String version,String interfaceId, String modDate, String modId) {
		try{
			requirementHerstoryMapper.insertInterfaceBusinessMapHerstory(requirementId, version, interfaceId, modDate, modId);
		}catch(Throwable t){
			logger.error("[인터페이스업무매핑 히스토리 작업중 예외 발생]",t);
		}
	}

	@Transactional
	private void insertInterfaceRelUserHerstory(String requirementId, String version,String interfaceId, String modDate, String modId) {
		try{
			requirementHerstoryMapper.insertInterfaceRelUserHerstory(requirementId, version, interfaceId, modDate, modId);
		} catch(Throwable t){
			logger.error("[인터페이스담당자 히스토리 작업중 예외 발생]",t);
		}
	}

	@Transactional
	private void insertB2BiInterfaceMetaDataHistory(String requirementId, String version,String interfaceId, String modDate, String modId) {
		try{
			requirementHerstoryMapper.insertB2BiInterfaceMetaDataHistory(requirementId, version, interfaceId, modDate, modId);
		} catch(Throwable t){
			logger.error("[B2Bi 히스토리 작업중 예외 발생]",t);
		}
	}




	@Transactional
	private void insertInterfaceHerstory(String requirementId, String version, Interface interfaze, String modDate, String modId)  throws Exception{

		String interfaceId = interfaze.getInterfaceId();

		//interfaceNm 비교
		ColumnHerstory ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "interfaceNm");
		String beforeNm = ch == null ? "" : ch.getValue();
		String nowNm = interfaze.getInterfaceNm();
		if(!beforeNm.equals(nowNm)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "interfaceNm", version, ColumnHerstory.COLUMN_TYPE_STRING, nowNm, modId));
		}

		//channel
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "channelId");
		String beforeChannelId = ch == null ? "" : ch.getValue();
		String nowChannelId = interfaze.getChannel().getChannelId();
		if(!beforeChannelId.equals(nowChannelId)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "channelId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowChannelId, modId));
		}

		//cntPerDay
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "cntPerDay");
		int beforeCntPerDay = Integer.parseInt(ch == null ? "0" : ch.getValue());
		int nowCntPerDay = interfaze.getCntPerDay();
		if(beforeCntPerDay != nowCntPerDay) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "cntPerDay", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Integer.toString(nowCntPerDay), modId));
		}

		//cntPerFrequency
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "cntPerFrequency");
		int beforeCntPerFrequency = Integer.parseInt(ch == null ? "0" : ch.getValue());
		int nowCntPerFrequency = interfaze.getCntPerFrequency();
		if(beforeCntPerFrequency != nowCntPerFrequency) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "cntPerFrequency", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Integer.toString(nowCntPerFrequency), modId));
		}

		//comments
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "comments");
		String beforeComments = ch == null ? "" : ch.getValue();
		String nowComments = interfaze.getComments();
		if(!beforeComments.equals(nowComments)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "comments", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowComments, modId));
		}

		//dataFrequency
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "dataFrequency");
		String beforeDataFrequency = ch == null ? "" : ch.getValue();
		String nowDataFrequency = interfaze.getDataFrequency();
		if(!beforeDataFrequency.equals(nowDataFrequency)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataFrequency", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowDataFrequency, modId));
		}

		//dataPrDir
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "dataPrDir");
		String beforeDataPrDir = ch == null ? "" : ch.getValue();
		String nowDataPrDir = interfaze.getDataPrDir();
		if(!beforeDataPrDir.equals(nowDataPrDir)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataPrDir", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataPrDir, modId));
		}

		//dataPrMethod
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "dataPrMethod");
		String beforeDataPrMethod = ch == null ? "" : ch.getValue();
		String nowDataPrMethod = interfaze.getDataPrMethod();
		if(!beforeDataPrMethod.equals(nowDataPrMethod)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataPrMethod", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataPrMethod, modId));
		}

		//dataSeqYn
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "dataSeqYn");
		String beforeDataSeqYn = ch == null ? "" : ch.getValue();
		String nowDataSeqYn = interfaze.getDataSeqYn();
		if(!beforeDataSeqYn.equals(nowDataSeqYn)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataSeqYn", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowDataSeqYn , modId));
		}

		//endNodeCnt
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "endNodeCnt");
		int beforeEndNodeCnt = Integer.parseInt(ch == null ? "0" : ch.getValue());
		int nowEndNodeCnt = interfaze.getEndNodeCnt();
		if(beforeEndNodeCnt != nowEndNodeCnt) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "endNodeCnt", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Integer.toString(nowEndNodeCnt), modId));
		}

		//endResType
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "endResType");
		String beforeEndResType = ch == null ? "" : ch.getValue();
		String nowEndResType = interfaze.getEndResType();
		if(!beforeEndResType.equals(nowEndResType)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "endResType", version, ColumnHerstory.COLUMN_TYPE_STRING, nowEndResType, modId));
		}

		//importance
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "importance");
		String beforeImportance = ch == null ? "" : ch.getValue();
		String nowImportance = interfaze.getImportance();
		if(!beforeImportance.equals(nowImportance)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "importance", version, ColumnHerstory.COLUMN_TYPE_STRING, nowImportance, modId));
		}

		//interfaceMapping
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "asisInterfaceId");
		String beforeInterfaceMappingId = ch == null ? "" : ch.getValue();
		InterfaceMapping nowInterfaceMapping = interfaze.getInterfaceMapping();
		String nowInterfaceMappingId = nowInterfaceMapping == null ? "" : nowInterfaceMapping.getAsisInterfaceId();
		if(!beforeInterfaceMappingId.equals(nowInterfaceMappingId)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "asisInterfaceId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowInterfaceMappingId, modId));
		}

		//maxDelayTime
		String nowMaxDelayTime = interfaze.getMaxDelayTime();
		if(!Util.isEmpty(nowMaxDelayTime)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxDelayTime", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxDelayTime, modId));
		}

		//maxDelayUnit
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "maxDelayUnit");
		String beforeMaxDelayUnit = ch == null ? "" : ch.getValue();
		String nowMaxDelayUnit = interfaze.getMaxDelayUnit();
		if(!beforeMaxDelayUnit.equals(nowMaxDelayUnit)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxDelayUnit", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowMaxDelayUnit, modId));
		}

		//maxErrorTime
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "maxErrorTime");
		String beforeMaxErrorTime = ch == null ? "" : ch.getValue();
		String nowMaxErrorTime = interfaze.getMaxErrorTime();
		if(!beforeMaxErrorTime.equals(nowMaxErrorTime)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxErrorTime", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxErrorTime, modId));
		}

		//maxErrorUnit
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "maxErrorUnit");
		String beforeErrorUnit = ch == null ? "" : ch.getValue();
		String nowMaxErrorUnit = interfaze.getMaxErrorUnit();
		if(!beforeErrorUnit.equals(nowMaxErrorUnit)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxErrorUnit", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxErrorUnit, modId));
		}

		//qttPerDay
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "qttPerDay");
		long beforeQttPerDay = Long.parseLong(ch == null ? "0" : ch.getValue());
		long nowQttPerDay = interfaze.getQttPerDay();
		if(beforeQttPerDay != nowQttPerDay) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "qttPerDay", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Long.toString(nowQttPerDay), modId));
		}

		//refPattern
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "refPatternId");
		String beforeRefPatternId = ch == null ? "" : ch.getValue();
		InterfacePattern nowRefPattern = interfaze.getRefPattern();
		String nowRefPatternId = nowRefPattern == null ? "" : nowRefPattern.getPatternId();
		if(!beforeRefPatternId.equals(nowRefPatternId)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "refPatternId", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowRefPatternId, modId));
		}

		//sizePerTran
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "sizePerTran");
		long beforeSizePerTran = Long.parseLong(ch == null ? "0" : ch.getValue());
		long nowSizePerTran = interfaze.getSizePerTran();
		if(beforeSizePerTran != nowSizePerTran) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "sizePerTran", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Long.toString(nowSizePerTran), modId));
		}
		//startNodeCnt
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "startNodeCnt");
		int beforeStartNodeCnt = Integer.parseInt(ch == null ? "0" : ch.getValue());
		int nowStartNodeCnt = interfaze.getStartNodeCnt();
		if(beforeStartNodeCnt != nowStartNodeCnt) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "startNodeCnt", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Integer.toString(nowStartNodeCnt), modId));
		}

		//startResType
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "startResType");
		String beforeStartResType = ch == null ? "" : ch.getValue();
		String nowStartResType = interfaze.getStartResType();
		if(!beforeStartResType.equals(nowStartResType)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "startResType", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowStartResType, modId));
		}

		//delYn
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "delYn");
		String beforeDelYn = ch == null ? "" : ch.getValue();
		String nowDelYn = interfaze.getDelYn();
		if(!beforeDelYn.equals(nowDelYn)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "delYn", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDelYn, modId));
		}

		//integrationId
		ch = requirementHerstoryMapper.getLastInterfaceColumnHerstory(requirementId, interfaceId, "integrationId");
		String beforeIntegrationId = ch == null ? "" : ch.getValue();
		String nowIntegrationId = interfaze.getIntegrationId();
		if(!beforeIntegrationId.equals(nowIntegrationId)) {
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "integrationId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowIntegrationId, modId));
		}


	}



	@Transactional
	private void insertFirstInterfaceHerstory(String requirementId, String version, Interface interfaze, String modDate, String modId)  throws Exception{

		String interfaceId = interfaze.getInterfaceId();
		//----------------------------------------------------------------------------
		// 기본정보
		//----------------------------------------------------------------------------

		// integrationId
		String integrationId = interfaze.getIntegrationId();
		if(!Util.isEmpty(integrationId)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "integrationId", version, ColumnHerstory.COLUMN_TYPE_STRING, integrationId, modId));
		}

		// channelID
		String nowChannelId = interfaze.getChannel().getChannelId();
		if(!Util.isEmpty(nowChannelId)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "channelId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowChannelId, modId));
		}

		// 인터페이스명
		String nowNm = interfaze.getInterfaceNm();
		if(!Util.isEmpty(nowNm)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "interfaceNm", version, ColumnHerstory.COLUMN_TYPE_STRING, nowNm, modId));
		}

		// Data처리방향(dataPrDir)
		String nowDataPrDir = interfaze.getDataPrDir();
		if(!Util.isEmpty(nowDataPrDir)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataPrDir", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataPrDir, modId));
		}

		// Data처리방식(dataPrMethod)
		String nowDataPrMethod = interfaze.getDataPrMethod();
		if(!Util.isEmpty(nowDataPrMethod)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataPrMethod", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataPrMethod, modId));
		}

		// APP처리방식(appPrMethod)
		String nowAppPrMethod = interfaze.getAppPrMethod();
		if(!Util.isEmpty(nowAppPrMethod)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "appPrMethod", version, ColumnHerstory.COLUMN_TYPE_STRING, nowAppPrMethod, modId));
		}

		// 데이터순차보장(dataSeqYn)
		String nowDataSeqYn = interfaze.getDataSeqYn();
		if(!Util.isEmpty(nowDataSeqYn)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataSeqYn", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataSeqYn, modId));
		}

		// 발생주기(dataFrequency)
		String nowDataFrequency = interfaze.getDataFrequency();
		if(!Util.isEmpty(nowDataFrequency)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataFrequency", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataFrequency, modId));
		}

		// 발생주기상세(dataFrequencyInput)
		String nowDataFrequencyInput = interfaze.getDataFrequencyInput();
		if(!Util.isEmpty(nowDataFrequencyInput)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "dataFrequencyInput", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDataFrequencyInput, modId));
		}


		// 건당사이즈(sizePerTran)
		long nowSizePerTran = interfaze.getSizePerTran();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "sizePerTran", version, ColumnHerstory.COLUMN_TYPE_NUMERIC,  Long.toString(nowSizePerTran), modId));

		// 주기별건수(cntPerFrequency)
		int nowCntPerFrequency = interfaze.getCntPerFrequency();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "cntPerFrequency", version, ColumnHerstory.COLUMN_TYPE_NUMERIC,  Integer.toString(nowCntPerFrequency), modId));

		// 일일발생횟수(cntPerDay)
		int nowCntPerDay = interfaze.getCntPerDay();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "cntPerDay", version, ColumnHerstory.COLUMN_TYPE_NUMERIC,  Integer.toString(nowCntPerDay), modId));

		// 일일총전송량(qttPerDay)
		long nowQttPerDay = interfaze.getQttPerDay();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "qttPerDay", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Long.toString(nowQttPerDay), modId));

		//----------------------------------------------------------------------------
		// 장애영향도
		//----------------------------------------------------------------------------

		// 중요도(importance)
		String nowImportance = interfaze.getImportance();
		if(!Util.isEmpty(nowImportance)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "importance", version, ColumnHerstory.COLUMN_TYPE_STRING,nowImportance, modId));
		}

		// 지연임계시간(maxDelayTime)
		String nowMaxDelayTime = interfaze.getMaxDelayTime();
		if(!Util.isEmpty(nowMaxDelayTime)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxDelayTime", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxDelayTime, modId));
		}

		// 지연임계시간단위(maxDelayUnit)
		String nowMaxDelayUnit = interfaze.getMaxDelayUnit();
		if(!Util.isEmpty(nowMaxDelayUnit)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxDelayUnit", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxDelayUnit, modId));
		}

		// 장애임계시간(maxErrorTime)
		String nowMaxErrorTime = interfaze.getMaxErrorTime();
		if(!Util.isEmpty(nowMaxErrorTime)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxErrorTime", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxErrorTime, modId));
		}

		// 장애임계시간단위(maxErrorUnit)
		String nowMaxErrorUnit = interfaze.getMaxErrorUnit();
		if(!Util.isEmpty(nowMaxErrorUnit)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "maxErrorUnit", version, ColumnHerstory.COLUMN_TYPE_STRING, nowMaxErrorUnit, modId));
		}

		//----------------------------------------------------------------------------
		// 인터페이스 맵핑
		//----------------------------------------------------------------------------

		// 인터페이스맵핑(interfaceMapping)
		InterfaceMapping nowInterfaceMapping = interfaze.getInterfaceMapping();
		String nowInterfaceMappingId = nowInterfaceMapping == null ? "" : nowInterfaceMapping.getAsisInterfaceId();
		if(!Util.isEmpty(nowInterfaceMappingId)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "asisInterfaceId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowInterfaceMappingId, modId));
		}

		//----------------------------------------------------------------------------
		// 인터페이스 패턴
		//----------------------------------------------------------------------------

		// 인터페이스페턴(refPattern)
		InterfacePattern nowRefPattern = interfaze.getRefPattern();
		String nowRefPatternId = nowRefPattern == null ? "" : nowRefPattern.getPatternId();
		if(!Util.isEmpty(nowRefPatternId)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "refPatternId", version, ColumnHerstory.COLUMN_TYPE_STRING, nowRefPatternId, modId));
		}


		// 설명(comments)
		String nowComments = interfaze.getComments();
		if(!Util.isEmpty(nowComments)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "comments", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowComments, modId));
		}

		//----------------------------------------------------------------------------
		// 기타
		//----------------------------------------------------------------------------

		// startNodeCnt
		int nowStartNodeCnt = interfaze.getStartNodeCnt();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "startNodeCnt", version, ColumnHerstory.COLUMN_TYPE_NUMERIC, Integer.toString(nowStartNodeCnt), modId));

		// startResType
		String nowStartResType = interfaze.getStartResType();
		if(!Util.isEmpty(nowStartResType)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "startResType", version, ColumnHerstory.COLUMN_TYPE_STRING,  nowStartResType, modId));
		}

		// endNodeCnt
		int nowEndNodeCnt = interfaze.getEndNodeCnt();
		requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "endNodeCnt", version, ColumnHerstory.COLUMN_TYPE_NUMERIC,Integer.toString(nowEndNodeCnt), modId));

		// endResType
		String nowEndResType = interfaze.getEndResType();
		if(!Util.isEmpty(nowEndResType)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "endResType", version, ColumnHerstory.COLUMN_TYPE_STRING, nowEndResType, modId));
		}

		// 삭제여부(delYn)
		String nowDelYn = interfaze.getDelYn();
		if(!Util.isEmpty(nowDelYn)){
			requirementHerstoryMapper.insertInterfaceColumnHerstory(new ColumnHerstory(requirementId, interfaceId, modDate, "delYn", version, ColumnHerstory.COLUMN_TYPE_STRING, nowDelYn, modId));
		}

	}




	/**
	 * <pre>
	 *     첫번째 요건 이력을 남기는 메소드
	 * </pre>
	 * @param requirement
	 * @return
	 * @throws Throwable
	 */
	@Transactional
	private int insertFirstRequirementHerstory(Requirement requirement, String version) throws Exception{

		int res = 0;
		String requirementId = requirement.getRequirementId();
		String modDate = Util.isEmpty(requirement.getModDate()) ? requirement.getRegDate() : requirement.getModDate();
		String modId = Util.isEmpty(requirement.getModId()) ? requirement.getRegId() : requirement.getModId();



		requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "requirementId", version, ColumnHerstory.COLUMN_TYPE_STRING, requirementId, modId));

		// 요건명
		if (!Util.isEmpty(requirement.getRequirementNm()))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "requirementNm", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getRequirementNm(), modId));
		// 의뢰상태
		if (!Util.isEmpty(requirement.getStatus() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "status", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getStatus(), modId));
		// ARIS 프로세스
		if (!Util.isEmpty(requirement.getBusiness()) && !Util.isEmpty(requirement.getBusiness().getBusinessId()))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "businessId", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getBusiness().getBusinessId(), modId));
		// Description
		if (!Util.isEmpty(requirement.getComments() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "comments", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getComments(), modId));

		//----------------------------------------------------------------------------
		// 스케줄
		//----------------------------------------------------------------------------

		// 개발 예정일
		if (!Util.isEmpty(requirement.getDevExpYmd() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "devExpYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getDevExpYmd(), modId));
		// 개발 완료일
		if (!Util.isEmpty(requirement.getDevFinYmd() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "devFinYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getDevFinYmd(), modId));
		// 테스트 예정일
		if (!Util.isEmpty(requirement.getTestExpYmd()))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "testExpYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getTestExpYmd(), modId));
		// 테스트 완료일
		if (!Util.isEmpty(requirement.getTestFinYmd() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "testFinYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getTestFinYmd(), modId));
		// 운영반영 예정일
		if (!Util.isEmpty(requirement.getRealExpYmd() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "realExpYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getRealExpYmd(), modId));
		// 운영반영 완료일
		if (!Util.isEmpty(requirement.getRealFinYmd() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "realFinYmd", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getRealFinYmd(), modId));


		// 요건 등록일
		if (!Util.isEmpty(requirement.getRegDate() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "regDate", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getRegDate(), modId));
		// 요건 등록자
		if (!Util.isEmpty(requirement.getRegId() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "regId", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getRegId(), modId));
		// 삭제구분자
		if (!Util.isEmpty(requirement.getDelYn() ))
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "delYn", version, ColumnHerstory.COLUMN_TYPE_STRING, requirement.getDelYn(), modId));

		// 인터페이스ID
		Interface interfaze = requirement.getInterfaceInfo();
		String interfaceId = interfaze == null ? "" : interfaze.getInterfaceId();
		if (!Util.isEmpty(interfaceId)){
			res = requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, "interfaceId", version, ColumnHerstory.COLUMN_TYPE_STRING, interfaceId, modId));
		}

		return res;
	}



	@Transactional
	private int insertRequirementHerstory(Requirement requirement, String version) throws Exception{

		int res = 0;


		String requirementId = requirement.getRequirementId();
		String modDate = requirement.getModDate();
		String modId = requirement.getModId();

		requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_ID, version, ColumnHerstory.COLUMN_TYPE_STRING, requirementId, modId));

		ColumnHerstory ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_NM);
		String beforeNm = ch == null ? "" : ch.getValue();
		String nowNm = requirement.getRequirementNm();
		if(!beforeNm.equals(nowNm)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_NM, version, ColumnHerstory.COLUMN_TYPE_STRING, nowNm, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_STATUS);
		String beforeStatus = ch == null ? "" : ch.getValue();
		String nowStatus = requirement.getStatus();
		if(!beforeStatus.equals(nowStatus)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_STATUS, version, ColumnHerstory.COLUMN_TYPE_STRING, nowStatus, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_BIZID);
		String beforeBusinessId = ch == null ? "" : ch.getValue();
		String nowBusinessId = requirement.getBusiness().getBusinessId();
		if(!beforeBusinessId.equals(nowBusinessId)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_BIZID, version, ColumnHerstory.COLUMN_TYPE_STRING, nowBusinessId, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_COMMENTS);
		String beforeComments = ch == null ? "" : ch.getValue();
		String nowComments = requirement.getComments();
		beforeComments = beforeComments == null ? "" : beforeComments;
		nowComments = nowComments == null ? "" : nowComments;
		if(!beforeComments.equals(nowComments)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_COMMENTS, version, ColumnHerstory.COLUMN_TYPE_STRING, nowComments, modId));
		}


		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_DEV_EXP_YMD);
		String beforeDevExpYmd = ch == null ? "" : ch.getValue();
		String nowDevExpYmd = requirement.getDevExpYmd();
		nowDevExpYmd = nowDevExpYmd == null ? "" : nowDevExpYmd;
		if(!beforeDevExpYmd.equals(nowDevExpYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_DEV_EXP_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowDevExpYmd, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_DEV_FIN_YMD);
		String beforeDevFinYmd = ch == null ? "" : ch.getValue();
		String nowDevFinYmd = requirement.getDevFinYmd();
		nowDevFinYmd = nowDevFinYmd == null ? "" : nowDevFinYmd;
		if(!beforeDevFinYmd.equals(nowDevFinYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_DEV_FIN_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowDevFinYmd, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_TEST_EXP_YMD);
		String beforeTestExpYmd = ch == null ? "" : ch.getValue();
		String nowTestExpYmd = requirement.getTestExpYmd();
		nowTestExpYmd = nowTestExpYmd == null ? "" : nowTestExpYmd;
		if(!beforeTestExpYmd.equals(nowTestExpYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_TEST_EXP_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowTestExpYmd, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_TEST_FIN_YMD);
		String beforeTestFinYmd = ch == null ? "" : ch.getValue();
		String nowTestFinYmd = requirement.getTestFinYmd();
		nowTestFinYmd = nowTestFinYmd == null ? "" : nowTestFinYmd;
		if(!beforeTestFinYmd.equals(nowTestFinYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_TEST_FIN_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowTestFinYmd, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_REAL_EXP_YMD);
		String beforeRealExpYmd = ch == null ? "" : ch.getValue();
		String nowRealExpYmd = requirement.getRealExpYmd();
		nowRealExpYmd = nowRealExpYmd == null ? "" : nowRealExpYmd;
		if(!beforeRealExpYmd.equals(nowRealExpYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_REAL_EXP_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowRealExpYmd, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_REAL_FIN_YMD);
		String beforeRealFinYmd = ch == null ? "" : ch.getValue();
		String nowRealFinYmd = requirement.getRealFinYmd();
		nowRealFinYmd = nowRealFinYmd == null ? "" : nowRealFinYmd;
		if(!beforeRealFinYmd.equals(nowRealFinYmd)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_REAL_FIN_YMD, version, ColumnHerstory.COLUMN_TYPE_STRING, nowRealFinYmd, modId));
		}


		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_DEL_YN);
		String beforeDelYn = ch == null ? "" : ch.getValue();
		String nowDelYn = requirement.getDelYn();
		nowDelYn = nowDelYn == null ? "" : nowDelYn;
		if(!beforeDelYn.equals(nowDelYn)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_DEL_YN, version, ColumnHerstory.COLUMN_TYPE_STRING, nowDelYn, modId));
		}

		ch = requirementHerstoryMapper.getLastRequirementColumnHerstory(requirementId, ColumnHerstory.COLUMN_NAME_REQ_INTERFACE_ID);
		String beforeInterfaceId = ch == null ? "" : ch.getValue();
		Interface nowInterface = requirement.getInterfaceInfo();
		String nowInterfaceId = nowInterface == null ? "" : nowInterface.getInterfaceId();
		if(!beforeInterfaceId.equals(nowInterfaceId)){
			requirementHerstoryMapper.insertRequirementColumnHerstory(new ColumnHerstory(requirementId, modDate, ColumnHerstory.COLUMN_NAME_REQ_INTERFACE_ID, version, ColumnHerstory.COLUMN_TYPE_STRING, nowInterfaceId, modId));
		}


		return res;
	}




	@Transactional
	private void insertRequirementCommentHerstory(String requirementId, String version, String modDate, String modId) throws Exception{
		requirementHerstoryMapper.insertRequirementCommentHerstory(requirementId, version, modDate, modId);
	}

	@Transactional
	private void insertRequirementAttatchFileHerstory(String requirementId, String version, String modDate, String modId)throws Exception {
		requirementHerstoryMapper.insertRequirementAttatchFileHerstory(requirementId, version, modDate, modId);
	}




	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementHerstoryList(String requirementId) throws Exception{
		return requirementHerstoryMapper.getRequirementHerstoryList(requirementId);
	}

	/**
	 *
	 * @param requirementId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public RequirementColumnHerstory getRequirementColumnHerstory(String requirementId, String version) throws Exception{
		RequirementColumnHerstory herstory = requirementHerstoryMapper.getRequirementColumnHerstory(requirementId, version);

		herstory.setStatusChanged(Util.isEmpty(herstory.getStatus2()) ? false : true);
		herstory.setRequirementNmChanged(Util.isEmpty(herstory.getRequirementNm2()) ? false : true);
		herstory.setBusinessIdChanged(Util.isEmpty(herstory.getBusinessId2()) ? false : true);
		herstory.setInterfaceIdChanged(Util.isEmpty(herstory.getInterfaceId2()) ? false : true);
		herstory.setCommentsChanged(Util.isEmpty(herstory.getComments2()) ? false : true);
		herstory.setDelYnChanged(Util.isEmpty(herstory.getDelYn2()) ? false : true);
		herstory.setDevExpYmdChanged(Util.isEmpty(herstory.getDevExpYmd2()) ? false : true);
		herstory.setDevFinYmdChanged(Util.isEmpty(herstory.getDevFinYmd2()) ? false : true);
		herstory.setTestExpYmdChanged(Util.isEmpty(herstory.getTestExpYmd2()) ? false : true);
		herstory.setTestFinYmdChanged(Util.isEmpty(herstory.getTestFinYmd2()) ? false : true);
		herstory.setRealExpYmdChanged(Util.isEmpty(herstory.getRealExpYmd2()) ? false : true);
		herstory.setRealFinYmdChanged(Util.isEmpty(herstory.getRealFinYmd2()) ? false : true);

		List<Map> commentList = requirementHerstoryMapper.getCurrentRequirementCommentList(requirementId);
		herstory.setCommentList(commentList);

		List<Map> commentList2 = requirementHerstoryMapper.getRequirementCommentListHerstory(requirementId,version);
		herstory.setCommentList2(commentList2);


		List<Map> attachFileList = requirementHerstoryMapper.getCurrentRequirementAttachFileList(requirementId);
		herstory.setAttatchFileList(attachFileList);

		List<Map> attachFileList2 = requirementHerstoryMapper.getRequirementAttachFileListHerstory(requirementId, version);
		herstory.setAttatchFileList2(attachFileList2);


		String interfaceId = herstory.getInterfaceId();
		if(!Util.isEmpty(interfaceId)){
			InterfaceColumnHerstory ich = getInterfaceColumnHerstory(requirementId, interfaceId, version);
			herstory.setInterfaceColumnHerstory(ich);

			List<Map> systemMapList = requirementHerstoryMapper.getCurrentInterfaceSystemMapList(interfaceId);
			ich.setSystemList(systemMapList);

			List<Map> systemMapList2 = requirementHerstoryMapper.getInterfaceSystemMapListHerstory(requirementId, version, interfaceId);
			ich.setSystemList2(systemMapList2);


			List<Map> businessMapList = requirementHerstoryMapper.getCurrentInterfaceBusinessMapList(interfaceId);
			ich.setBusinessList(businessMapList);

			List<Map> businessMapList2 = requirementHerstoryMapper.getInterfaceBusinessMapListHerstory(requirementId, version, interfaceId);
			ich.setBusinessList2(businessMapList2);


			List<Map> relUsers = requirementHerstoryMapper.getCurrentInterfaceRelUserList(interfaceId);
			ich.setRelUsers(relUsers);

			List<Map> relUsers2 = requirementHerstoryMapper.getInterfaceRelUserListHerstory(requirementId, version, interfaceId);
			ich.setRelUsers2(relUsers2);

			Map b2biInfo = requirementHerstoryMapper.getCurrentInterfaceB2biInfoList(interfaceId);
			if( b2biInfo == null ) {
				b2biInfo = new LinkedHashMap();
				b2biInfo.put("companyNm", "");
				b2biInfo.put("docNm", "");
				b2biInfo.put("bizNm", "");
				b2biInfo.put("direction", "");
				b2biInfo.put("protocol", "");
			}
			ich.setB2biInfo(b2biInfo);

			Map b2biInfo2 = requirementHerstoryMapper.getInterfaceB2biInfoListHerstory(requirementId, version, interfaceId);
			if( b2biInfo2 == null ) {
				b2biInfo2 = new LinkedHashMap();
				b2biInfo2.put("companyNm", "");
				b2biInfo2.put("docNm", "");
				b2biInfo2.put("bizNm", "");
				b2biInfo2.put("direction", "");
				b2biInfo2.put("protocol", "");
			}
			ich.setB2biInfo2(b2biInfo2);

		}
		return herstory;
	}



	/**
	 *
	 * @param interfaceId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public InterfaceColumnHerstory getInterfaceColumnHerstory(String requirementId, String interfaceId, String version) throws Exception{
		InterfaceColumnHerstory herstory = requirementHerstoryMapper.getInterfaceColumnHerstory(requirementId, interfaceId, version);

		herstory.setInterfaceIdChanged(Util.isEmpty(herstory.getInterfaceId2()) ? false : true);
		herstory.setChannelIdChanged(Util.isEmpty(herstory.getChannelId2()) ? false : true);
		herstory.setImportanceChanged(Util.isEmpty(herstory.getImportance2()) ? false : true);
		herstory.setMaxDelayTimeChanged(Util.isEmpty(herstory.getMaxDelayTime2()) ? false : true);
		herstory.setMaxDelayUnitChanged(Util.isEmpty(herstory.getMaxDelayUnit2()) ? false : true);
		herstory.setMaxErrorTimeChanged(Util.isEmpty(herstory.getMaxErrorTime2()) ? false : true);
		herstory.setMaxErrorUnitChanged(Util.isEmpty(herstory.getMaxErrorUnit2()) ? false : true);
		herstory.setRefPatternIdChanged(Util.isEmpty(herstory.getRefPatternId2()) ? false : true);
		herstory.setDataPrDirChanged(Util.isEmpty(herstory.getDataPrDir2()) ? false : true);
		herstory.setAppPrMethodChanged(Util.isEmpty(herstory.getAppPrMethod2()) ? false : true);
		herstory.setDataPrMethodChanged(Util.isEmpty(herstory.getDataPrMethod2()) ? false : true);
		herstory.setDataFrequencyChanged(Util.isEmpty(herstory.getDataFrequency2()) ? false : true);
		herstory.setSizePerTranChanged(Util.isEmpty(herstory.getSizePerTran2()) ? false : true);
		herstory.setCntPerFrequencyChanged(Util.isEmpty(herstory.getCntPerFrequency2()) ? false : true);
		herstory.setCntPerDayChanged(Util.isEmpty(herstory.getCntPerDay2()) ? false : true);
		herstory.setQttPerDayChanged(Util.isEmpty(herstory.getQttPerDay2()) ? false : true);
		herstory.setStartResTypeChanged(Util.isEmpty(herstory.getStartResType2()) ? false : true);
		herstory.setEndResTypeChanged(Util.isEmpty(herstory.getEndResType2()) ? false : true);
		herstory.setStartNodeCntChanged(Util.isEmpty(herstory.getStartNodeCnt2()) ? false : true);
		herstory.setEndNodeCntChanged(Util.isEmpty(herstory.getEndNodeCnt2()) ? false : true);
		herstory.setDataSeqYnChanged(Util.isEmpty(herstory.getDataSeqYn2()) ? false : true);
		herstory.setCommentsChanged(Util.isEmpty(herstory.getComments2()) ? false : true);
		herstory.setDelYnChanged(Util.isEmpty(herstory.getDelYn2()) ? false : true);
		herstory.setAsisInterfaceIdChanged(Util.isEmpty(herstory.getAsisInterfaceId2()) ? false : true);

		return herstory;
	}




	/**
	 * <pre>
	 *   CLOB 형식 히스토리 저장
	 *   인터페이스 등록/수정/삭제시 히스토리를 남길때 호출한다.
	 * </pre>
	 * @param currentRequirement
	 * @return
	 * @throws Exception
	 */
	public int addRequirementHistory(Requirement currentRequirement) throws Exception {
		if(!Environments.entityHerstoryOn) return 1;

		int res = -1;
		String requirementId = currentRequirement.getRequirementId();
		RequirementHistory lastHistory = requirementHerstoryMapper.getLastVersionRequirementHistory(requirementId);
		String sysDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
		if(lastHistory == null){
			RequirementHistory requirementHistory = new RequirementHistory();
			requirementHistory.setRequirementId(requirementId);
			requirementHistory.setContents(Util.toJSONString(currentRequirement));
			//-----------------------------------------
			//change-what:히스토리 등록일 수정일을 시스템 일자로 변경처리
			//change-when:171010
			//change-why:등록일 수정일로 전달받은 값이 현재버전 번호와 달리 과거 일자가 오는 경우가 있다.
			//           예를들어 접수요청(수정) 상태에서 문서보완으로 히스토리가 하나 생성되고
			//			 이후 접수 처리하면 다음 버전의 히스토리가 쌓이면서 수정일자는 접수요청(수정)시점의 과거 날짜가 MOD_DATE에 저장됨.
			//requirementHistory.setRegDate(currentRequirement.getRegDate());
			requirementHistory.setRegDate(sysDate);
			//-----------------------------------------

			requirementHistory.setRegId(currentRequirement.getRegId());
			//-----------------------------------------
			//change-what:히스토리 등록일 수정일을 시스템 일자로 변경처리
			//change-when:171010
			//change-why:등록일 수정일로 전달받은 값이 현재버전 번호와 달리 과거 일자가 오는 경우가 있다.
			//           예를들어 접수요청(수정) 상태에서 문서보완으로 히스토리가 하나 생성되고
			//			 이후 접수 처리하면 다음 버전의 히스토리가 쌓이면서 수정일자는 접수요청(수정)시점의 과거 날짜가 MOD_DATE에 저장됨.
			//requirementHistory.setModDate(currentRequirement.getModDate());
			requirementHistory.setModDate(sysDate);
			//-----------------------------------------

			requirementHistory.setModId(currentRequirement.getModId());
			requirementHerstoryMapper.addRequirementHistory(requirementHistory);
		}else{
			Requirement beforeHistory = Util.jsonToObject(lastHistory.getContents(), Requirement.class);
			if(beforeHistory == null || !currentRequirement.equals(lastHistory)){
				RequirementHistory requirementHistory = new RequirementHistory();
				requirementHistory.setRequirementId(requirementId);
				requirementHistory.setContents(Util.toJSONString(currentRequirement));
				//-----------------------------------------
				//change-what:히스토리 등록일 수정일을 시스템 일자로 변경처리
				//change-when:171010
				//change-why:등록일 수정일로 전달받은 값이 현재버전 번호와 달리 과거 일자가 오는 경우가 있다.
				//           예를들어 접수요청(수정) 상태에서 문서보완으로 히스토리가 하나 생성되고
				//			 이후 접수 처리하면 다음 버전의 히스토리가 쌓이면서 수정일자는 접수요청(수정)시점의 과거 날짜가 MOD_DATE에 저장됨.
				//requirementHistory.setRegDate(currentRequirement.getRegDate());
				requirementHistory.setRegDate(sysDate);
				//-----------------------------------------

				requirementHistory.setRegId(currentRequirement.getRegId());
				//-----------------------------------------
				//change-what:히스토리 등록일 수정일을 시스템 일자로 변경처리
				//change-when:171010
				//change-why:등록일 수정일로 전달받은 값이 현재버전 번호와 달리 과거 일자가 오는 경우가 있다.
				//           예를들어 접수요청(수정) 상태에서 문서보완으로 히스토리가 하나 생성되고
				//			 이후 접수 처리하면 다음 버전의 히스토리가 쌓이면서 수정일자는 접수요청(수정)시점의 과거 날짜가 MOD_DATE에 저장됨.
				//requirementHistory.setModDate(currentRequirement.getModDate());
				requirementHistory.setModDate(sysDate);
				//-----------------------------------------

				requirementHistory.setModId(currentRequirement.getModId());
				requirementHerstoryMapper.addRequirementHistory(requirementHistory);
			}else{
				throw new UnChangedDataException();
			}
		}
		res = 1;
		return res;
	}




	public List<Map> getRequirementHistoryList(String requirementId) {
		return requirementHerstoryMapper.getRequirementHistoryList(requirementId) ;
	}




	/**
	 * @param requirementId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public RequirementColumnHistory getRequirementHistory(String requirementId, String version, String bizPathView) throws Exception {
		RequirementColumnHistory history  = new RequirementColumnHistory();
		RequirementHistory preHistory = requirementHerstoryMapper.getRequirementHistory(requirementId, version);
		Requirement beforeHistory = Util.jsonToObject(preHistory.getContents(), Requirement.class);


		String statusNm = requirementHerstoryMapper.getRequirementHistoryStatusNm(beforeHistory.getStatus());
		if( statusNm != null ) {
			beforeHistory.setStatusNm(statusNm);
		}

		history.setRequirement2(beforeHistory);
		Map<String,String> params = new HashMap<String, String>();
		params.put("requirementId",requirementId);
		if (!Util.isEmpty(bizPathView)) params.put("bizPathView", bizPathView);
		history.setRequirement(requirementMapper.getRequirementDetail(params));
		return history;
	}

	/**
	 *
	 * @param interfaceId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public void getInterfaceColumnHistory(RequirementColumnHistory history) throws Exception{

		history.getInterfaceColumnHistory().setInterfaceIdChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getInterfaceId()) ? false : true);
		history.getInterfaceColumnHistory().setChannelIdChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getChannel().getChannelId()) ? false : true);
		history.getInterfaceColumnHistory().setImportanceChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getImportance()) ? false : true);
		history.getInterfaceColumnHistory().setMaxDelayTimeChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getMaxDelayTime()) ? false : true);
		history.getInterfaceColumnHistory().setMaxDelayUnitChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getMaxDelayUnit()) ? false : true);
		history.getInterfaceColumnHistory().setMaxErrorTimeChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getMaxErrorTime()) ? false : true);
		history.getInterfaceColumnHistory().setMaxErrorUnitChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getMaxErrorUnit()) ? false : true);
		//history.getInterfaceColumnHistory().setRefPatternIdChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getRefPattern().getR RefPatternId()) ? false : true);
		history.getInterfaceColumnHistory().setDataPrDirChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getDataPrDir()) ? false : true);
		history.getInterfaceColumnHistory().setAppPrMethodChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getAppPrMethod()) ? false : true);
		history.getInterfaceColumnHistory().setDataPrMethodChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getDataPrMethod()) ? false : true);
		history.getInterfaceColumnHistory().setDataFrequencyChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getDataFrequency()) ? false : true);
		history.getInterfaceColumnHistory().setSizePerTranChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getSizePerTran()) ? false : true);
		history.getInterfaceColumnHistory().setCntPerFrequencyChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getCntPerFrequency()) ? false : true);
		history.getInterfaceColumnHistory().setCntPerDayChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getCntPerDay()) ? false : true);
		history.getInterfaceColumnHistory().setQttPerDayChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getQttPerDay()) ? false : true);
		history.getInterfaceColumnHistory().setStartResTypeChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getStartResType()) ? false : true);
		history.getInterfaceColumnHistory().setEndResTypeChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getEndResType()) ? false : true);
		history.getInterfaceColumnHistory().setStartNodeCntChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getStartNodeCnt()) ? false : true);
		history.getInterfaceColumnHistory().setEndNodeCntChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getEndNodeCnt()) ? false : true);
		history.getInterfaceColumnHistory().setDataSeqYnChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getDataSeqYn()) ? false : true);
		history.getInterfaceColumnHistory().setCommentsChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getComments()) ? false : true);
		history.getInterfaceColumnHistory().setDelYnChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getDelYn()) ? false : true);
//		history.getInterfaceColumnHistory().setAsisInterfaceIdChanged(Util.isEmpty(history.getInterfaceColumnHistory().getInterface2().getAsisInterfaceId()) ? false : true);

	}


}
